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
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.promotion.api.dto.RedpackageGrant;
import com.ffzx.promotion.api.dto.UserLable;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.service.RedpackageGrantApiService;
import com.ffzx.promotion.api.service.consumer.DictApiConsumer;
import com.ffzx.promotion.api.service.consumer.MemberApiConsumer;
import com.ffzx.promotion.mapper.RedpackageGrantMapper;
import com.ffzx.promotion.mapper.RedpackageMapper;
import com.ffzx.promotion.mapper.UserLableMapper;
import com.ffzx.promotion.service.RedpackageGrantService;
import com.ffzx.promotion.util.JpushMessageUtil;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("redpackageGrantApiService")
public class RedpackageGrantApiServiceImpl extends BaseCrudServiceImpl implements RedpackageGrantApiService {

	private final String lockkey="RedpackageGrantApiServiceImpl_redpackagegrant_null";//一次一条进程
	private final long locktime=20*60*1000;//20分钟

	@Autowired
	private DictApiConsumer dictApiConsumer;
	@Resource
	private RedpackageMapper redpackageMapper;

	@Autowired
	private MemberApiConsumer memberApiConsumer;
	@Autowired
	private UserLableMapper userLableMapper;
	@Autowired
	private RedpackageGrantMapper redpackageGrantMapper;
	@Autowired
	private RedpackageGrantService redpackageGrantService;

   @Autowired
   private RedisUtil redisUtil;
	@Override
	public CrudMapper init() {
		// TODO Auto-generated method stub
		return redpackageGrantMapper;
	}

	@Override
	@Transactional
	public ResultDto redpackagegrant() throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("我进来执行红包推送了redpackagegrant");
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("isGrant", Constant.NO);
		params.put("state",Constant.YES);
		params.put("delFlag", Constant.NO);
		params.put("startGrantDate", new Date());//开始发放时间
		params.put("grantMode", PrmsConstant.RedpackageMessagegrantMode);
		List<RedpackageGrant> redpackageGrants=init().selectByParams(params);
		if(redpackageGrants!=null && redpackageGrants.size()>0){
			if(redisUtil.tryLock(lockkey, new Long(locktime))){
				logger.info("我进来tryLock执行红包推送了redpackagegrant");
				// 获取标签下的所有会员（已排重） * @param labelIds  getMemberByLabels(List<String> labelIds);
				
				try{
					sendMessage(redpackageGrants);
					logger.info("我执行完tryLock执行红包推送了redpackagegrant");
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
	private void sendMessage(List<RedpackageGrant> redpackageGrants){
		if(redpackageGrants!=null && redpackageGrants.size()>0){
			Map<String, Object> params=new HashMap<String, Object>();
			for (RedpackageGrant redpackageGrant : redpackageGrants) {
				params.clear();
				params.put("redpackageGrantId", redpackageGrant.getId());
				Set<String>  usersphone=getUserPhone(params);//获取用户手机号
				RedpackageGrant updateGrant=new RedpackageGrant();
				updateGrant.setId(redpackageGrant.getId());
				updateGrant.setIsGrant(Constant.YES);
				redpackageGrantService.updateRepackageGrant(updateGrant);//发放
				if(usersphone!=null  && usersphone.size()>0){
					Iterator<String> iterator=usersphone.iterator();
					while(iterator.hasNext()){
						String phone=iterator.next();//极光推送"18588440756,1";//
//						String phone="18588440756,1";//"13691788450,2";//"18588440756,1";
						String id=null;
						String url=null;
						String type=null;
						String message=dictApiConsumer.getMessage(PrmsConstant.RedpacageMessageCode);
						JpushMessageUtil.sendjpushMessage(phone, PrmsConstant.RedPackagetitle,
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
			if(!StringUtil.isEmpty(userLable.getLableId())){
				userslist.add(userLable.getLableId());
			}
		}
		Set<String> setUserPhone=memberApiConsumer.getMemberByLabels(userslist);
		return setUserPhone;
	}



}