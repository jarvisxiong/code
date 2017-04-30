package com.ffzx.promotion.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.promotion.api.dto.AppRecommendAwards;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.RedpackageGrant;
import com.ffzx.promotion.api.dto.UserLable;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.service.CouponGrantApiService;
import com.ffzx.promotion.api.service.consumer.DictApiConsumer;
import com.ffzx.promotion.api.service.consumer.MemberApiConsumer;
import com.ffzx.promotion.constant.CouponConstant;
import com.ffzx.promotion.mapper.AppRecommendAwardsMapper;
import com.ffzx.promotion.mapper.CouponGrantMapper;
import com.ffzx.promotion.mapper.UserLableMapper;
import com.ffzx.promotion.service.CouponGrantService;
import com.ffzx.promotion.util.JpushMessageUtil;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("couponGrantApiService")
public class CouponGrantApiServiceImpl extends BaseCrudServiceImpl implements CouponGrantApiService {

    @Resource
    private CouponGrantMapper couponGrantMapper;
    @Resource
    private AppRecommendAwardsMapper appRecommendAwardsMapper;
    @Autowired
    private CouponGrantService couponGrantService;
	@Autowired
	private UserLableMapper userLableMapper;

	@Autowired
	private DictApiConsumer dictApiConsumer;
	@Autowired
	private MemberApiConsumer memberApiConsumer;
    @Autowired
    private RedisUtil redisUtil;
	private final String lockkey="CouponGrantApiServiceImpl_coupongrantMessage_null";//一次一条进程
	private final long locktime=30*60*1000;//20分钟
    @Override
    public CrudMapper init() {
        return couponGrantMapper;
    }
	@Override
	public ResultDto updateGrant() throws ServiceException {
		ResultDto rsDto = null;
		try{
			this.couponGrantService.sendCouponAdmin();
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
		}catch(Exception e){

			logger.error("CouponGrantApiServiceImpl-updateGrant-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
	@Override
	public ResultDto updateNewUserGrant() throws ServiceException {
		// TODO Auto-generated method stub
		couponGrantService.updateNewUserGrant();
		return null;
	}
	public ResultDto updateNewUserGrantDate() throws ServiceException {
		// TODO Auto-generated method stub
		couponGrantService.updateNewUserGrantDate();
		return null;
	}
	
	@Override
	public ResultDto coupongrantMessage() throws ServiceException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				logger.info("我进来执行优惠券推送了coupongrantMessage");
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("isGrant", Constant.NO);
//				params.put("state",Constant.YES);
				params.put("delFlag", Constant.NO);
				params.put("grantMode", CouponConstant.CouponMessageGrantMode);
				params.put("grantMessageDate", new Date());
				List<CouponGrant> couponGrants=init().selectByParams(params);
				if(couponGrants!=null && couponGrants.size()>0){
					if(redisUtil.tryLock(lockkey, new Long(locktime))){
						logger.info("我进来tryLock执行优惠券推送了coupongrantMessage");
						// 获取标签下的所有会员（已排重） * @param labelIds  getMemberByLabels(List<String> labelIds);
						
						try{
							
							sendMessage(couponGrants);
									
							logger.info("我执行完tryLock执行优惠券推送了coupongrantMessage");
						}
						catch(Exception e){
							logger.error("",e);
						}finally {
							redisUtil.remove(lockkey);
						}
					}
				}
				return null;
	}

	private void sendMessage(List<CouponGrant> couponGrants){
		if(couponGrants!=null && couponGrants.size()>0){
			Map<String, Object> params=new HashMap<String, Object>();
			for (CouponGrant couponGrant : couponGrants) {
				params.clear();
				params.put("couponGrantId", couponGrant.getId());
				Set<String>  usersphone=getUserPhone(params);//获取用户手机号
				CouponGrant updateGrant=new CouponGrant();
				updateGrant.setId(couponGrant.getId());
				updateGrant.setIsGrant(Constant.STATUS_OVERDUR);
				couponGrantService.updateGrantCoupon(updateGrant);//发放
				if(usersphone!=null && usersphone.size()>0){
					Iterator<String> iterator=usersphone.iterator();
					while(iterator.hasNext()){
						String phoneORiosan=iterator.next();//极光推送"13691788450,1";//
//						String phoneORiosan="18588440756,1";//"13691788450,2";//"18588440756,1";
						String id=null;
						String url=null;
						String type=null;
						String message=dictApiConsumer.getMessage(PrmsConstant.CouponMessageCode);
						JpushMessageUtil.sendjpushMessage(phoneORiosan, PrmsConstant.Coupontitle,
								message, id, url, type);
//						break;
						
					}
				}
			}
		}
	}
	/**
	 * 获取用户手机号码
	 * @param params
	 * @return
	 */
	private Set<String>   getUserPhone(Map<String, Object> params){
		List<UserLable> userLables=userLableMapper.selectByParams(params);
		if(userLables==null || userLables.size()==0){
			return null;
		}
		List<String> userslist=new ArrayList<String>();//用户列表
		for (UserLable userLable : userLables) {
			if(!StringUtil.isEmpty(userLable.getId())){
				userslist.add(userLable.getLableId());
			}
		}
		Set<String> setUserPhone=memberApiConsumer.getMemberByLabels(userslist);
		return setUserPhone;
	}
	@Override
	public ResultDto getCouponGrantList(String uid, String grantId) throws ServiceException {
		ResultDto rsDto = null;
		try {
			Map<String,Object> result = new HashMap<String,Object>();
//			Page pageObject = null;
//			pageObject = new Page();
//		    pageObject.setPageSize(pageSize);
//		    pageObject.setPageIndex(page);
			// 获取优惠券发放信息
			CouponGrant couponGrant = couponGrantMapper.selectByPrimaryKey(grantId);
			
//			result.put("couponGrant",couponGrant); // 优惠券发放对象
			List<Map<String, Object>> couponAdminList = new ArrayList<Map<String, Object>>();
			if (couponGrant != null) {
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("uId", uid);
				params.put("grantId", grantId);
				// 获取领取的优惠券列表
				couponAdminList = couponGrantMapper.findCouponAdminByGrantId(null, params);
			}
			
			result.put("couponAdminList",couponAdminList);
//	    	if (couponAdminList != null && couponAdminList.size() > 0) {
//	    		result.put("page", pageObject.getPageCount());
//		    	result.put("pageSize",pageObject.getPageSize());
//		    	result.put("recordCount", pageObject.getTotalCount());
//			}
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(result);
		} catch (Exception e) {
			logger.error("机构dubbo调用-CouponGrantApiServiceImpl", e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
	
	@Override
	public ResultDto findAppRecommendAwardsInfo() throws ServiceException {
		ResultDto rsDto = null;
		try{
			Map<String,Object> result = new HashMap<String,Object>();
			List<AppRecommendAwards> appRecommendAwardsList = appRecommendAwardsMapper.selectByParams(null);
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			if (appRecommendAwardsList != null && appRecommendAwardsList.size() > 0) {
				result.put("item",appRecommendAwardsList.get(0));
			}
			rsDto.setData(result);
		} catch(Exception e){
			logger.error("机构dubbo调用-CouponGrantApiServiceImpl--获取推荐有奖数据", e);
			throw new ServiceException(e);
		}
		
		return rsDto;
	}

}