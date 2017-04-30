package com.ffzx.promotion.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commodity.api.dto.CommoditySku;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.dto.MemberMessage;
import com.ffzx.member.api.enums.MessTypeEnum;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.member.api.service.MemberMessageApiService;
import com.ffzx.promotion.api.dto.ActivityCommoditySku;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponAdminCouponGrant;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.dto.CouponVcode;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.enums.ActivityTypeEnum;
import com.ffzx.promotion.api.service.CouponReceiveApiService;
import com.ffzx.promotion.api.service.consumer.ActivityApiConsumer;
import com.ffzx.promotion.api.service.consumer.CategoryApiConsumer;
import com.ffzx.promotion.api.service.consumer.CommoditySkuApiConsumer;
import com.ffzx.promotion.api.service.consumer.CouponReceiveApiConsumer;
import com.ffzx.promotion.exception.CallInterfaceException;
import com.ffzx.promotion.mapper.CouponAdminCategoryMapper;
import com.ffzx.promotion.mapper.CouponAdminCouponGrantMapper;
import com.ffzx.promotion.mapper.CouponAdminMapper;
import com.ffzx.promotion.mapper.CouponGrantMapper;
import com.ffzx.promotion.mapper.CouponReceiveMapper;
import com.ffzx.promotion.mapper.CouponVcodeMapper;
import com.ffzx.promotion.util.StringappUtil;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Transactional
@Service("couponReceiveApiService")
public class CouponReceiveApiServiceImpl extends BaseCrudServiceImpl implements CouponReceiveApiService {

	@Autowired
	private CategoryApiConsumer categoryApiConsumer;
    @Resource
    private CouponReceiveMapper couponReceiveMapper;
    @Resource
    private CouponAdminCategoryMapper couponAdminCategoryMapper;
	@Autowired
	private CouponReceiveApiConsumer couponReceiveApiConsumer;

	@Autowired
	private ActivityApiConsumer activityApiConsumer;
	@Autowired
	private CommoditySkuApiConsumer commoditySkuApiConsumer;
    @Autowired
    private MemberApiService memberApiService;
    @Resource
    private CouponVcodeMapper couponVcodeMapper;

    @Resource
    private CouponAdminCouponGrantMapper couponAdminCouponGrantMapper;
    @Resource
    private CouponGrantMapper couponGrantMapper;
    @Resource
    private CouponAdminMapper couponAdminMapper;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
	private MemberMessageApiService memberMessageApiService;

    @Override
    public CrudMapper init() {
        return couponReceiveMapper;
    }

	@Override
	public ResultDto getCoupReceive(String coureceiveid) {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		try{
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			CouponReceive couponReceive=couponReceiveMapper.selectByPrimaryKeyApp(coureceiveid);
			if(couponReceive==null){
				throw new RuntimeException("不存在此领取优惠券数据:"+coureceiveid);
			}
			if(couponReceive.getCouponAdmin()!=null){

				if(!StringUtil.isEmpty(couponReceive.getCouponAdmin().getGoodsSelect())){
					//指定商品 or 指定类目 or 活动
					if((couponReceive.getCouponAdmin().getGoodsSelect().equals(Constant.COMM_FIXED)
							|| couponReceive.getCouponAdmin().getGoodsSelect().equals(Constant.COMM_CATEGORY)
							|| couponReceive.getCouponAdmin().getGoodsSelect().equals("1"))
							&& !StringUtil.isEmpty(couponReceive.getCouponAdmin().getId()) ){
						List<String> list = null;
						List<String> dataList = null;
						// 获取缓存数据
						String key = "coupon:couponReceiveId_"+coureceiveid;
						try{
							dataList = (List<String>) redisUtil.get(key);
							if(dataList!=null && dataList.size()>0){
								list = dataList;
							} else {
								Map<String, Object> map=new HashMap<String, Object>();
								map.put("goodsSelect", couponReceive.getCouponAdmin().getGoodsSelect());
								map.put("couponAdminId", couponReceive.getCouponAdmin().getId());
								list = couponAdminCategoryMapper.selectByCategoryCommodity(map);
								redisUtil.set(key, list,new Long(691200));//缓存10秒
							}
						}catch(Exception e){
							logger.error("CouponReceiveApiServiceImpl-getCoupReceive=》用户领取条件缓存-redis" ,e);
							throw new RuntimeException("redis读取 用户优惠券基本出错");
						}
						
						CouponAdmin couponAdmin=couponReceive.getCouponAdmin();
						couponAdmin.setList(list);
						couponReceive.setCouponAdmin(couponAdmin);
					}
				}
			}
			rsDto.setData(couponReceive);
		}catch(RuntimeException e){

			logger.error("CouponGrantApiServiceImpl-getCoupReceive-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "faild:" + e);
			throw e;
		}
		catch(Exception e){

			logger.error("CouponGrantApiServiceImpl-getCoupReceive-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto updateReceiveState(String id, String state,String memberId) {
		// TODO Auto-generated method stub
		boolean flag=false;
		
		ResultDto rsDto = null;
		try{
			if(StringUtil.isEmpty(state) || StringUtil.isEmpty(id)){
			throw new Exception("两个value都不能为null");
			}
			CouponReceive couponReceive=new CouponReceive();
			couponReceive.setId(id);
			couponReceive.setUseState(state);
			couponReceive.setUseDate(new Date());
			couponReceive.setIsReceive(Constant.YES);
			CouponReceive isExec=couponReceiveMapper.selectByPrimaryKey(id);//是否存在领取id，不存在就更新管理id
			
			if(isExec!=null && !StringUtil.isEmpty(isExec.getId())){//如果存在receiveid  ios不存在此id
				logger.info(id+" updateByPrimaryKeySelective优惠券更新 ");
				couponReceiveMapper.updateByPrimaryKeySelective(couponReceive);
			}else{
				logger.info(id+" updateiosRecive优惠券更新 ");
				couponReceive.setId(null);
				CouponAdmin couponAdmin=new CouponAdmin();
				couponAdmin.setId(id);
				couponReceive.setCouponAdmin(couponAdmin);
				couponReceive.setMemberId(memberId);
				couponReceiveMapper.updateiosRecive(couponReceive);
			}
			
			flag=true;
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(flag);
		}catch(Exception e){
			logger.error("CouponGrantApiServiceImpl-getCoupReceive-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
//			rsDto.setData(flag);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto isCommodityCategory(String type, String id) {
		// TODO Auto-generated method stub
		boolean flag=false;
		
		ResultDto rsDto = null;
		try{
			if(StringUtil.isEmpty(type) || StringUtil.isEmpty(id)){
			throw new Exception("两个value都不能为null");
			}
			Map<String, Object> map=new HashMap<String, Object>();
			if(type.equals(Constant.COMM_FIXED)){//指定商品
				map.put("commodityId", id);
			}else if(type.equals(Constant.COMM_CATEGORY)){
				map.put("categoryId", id);
			}
			Integer count=couponAdminCategoryMapper.selectCount(map);
			if(count!=null && count>0)
				flag=true;
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(flag);
		}catch(Exception e){

			logger.error("CouponGrantApiServiceImpl-getCoupReceive-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
		
	}

	@Override
	public ResultDto getCouponReceiveList(Map<String, Object> params) {
		ResultDto rsDto = null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			params.put("isOrderBy", "1"); // 排序规则(1：接口排序规则；其他 则是默认排序规则)
			List<CouponReceive> list = couponReceiveMapper.findByPage(null, Constant.ORDER_BY_FIELD_CREATE,  Constant.ORDER_BY, params,true);
			rsDto.setData(list);
		} catch (Exception e) {
			logger.error("CouponGrantApiServiceImpl-getCoupReceive-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	public ResultDto findCouponCount(String uid, String amount) {
		// TODO Auto-generated method stub
		ResultDto rsDto = null;
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			if(!StringUtil.isNotNull(uid)){
				throw new Exception("用户uid不能为null");
			}
			params.put("memberId",uid);//会员id
			params.put("amount",amount);
			params.put("useState", Constant.NO);//未使用
			params.put("stateenddate", new Date());//非过期
			int count = couponReceiveMapper.selectCount(params);
			rsDto.setData(count);
		} catch (Exception e) {
			logger.error("CouponGrantApiServiceImpl-getCoupReceive-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto findCoupon(String uid, String couponId) {
		ResultDto rsDto=null;
		try {
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("memberId", uid);
			params.put("couponId", couponId);
			logger.info("使用领取ID查询优惠券");
			CouponReceive receive=this.couponReceiveMapper.selectByPrimaryKeyAll(couponId);
			if(receive==null){
				logger.info("使用优惠券ID查询优惠券");
				List<CouponReceive> receiveList=new ArrayList<CouponReceive>();
				receiveList=this.couponReceiveMapper.selectReceiveCoupon(params);
				if(receiveList!=null && receiveList.size()!=0){
					receive=receiveList.get(0);
				}
			}					
			rsDto.setData(receive);
		} catch (Exception e) {
			logger.error("CouponGrantApiServiceImpl-getCoupReceive-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto getCoupon(String uid, String adminId, String grantId) {
		ResultDto rsDto=null;
		try {
//			logger.info("查询获取会员信息");
			ResultDto result = memberApiService.getByIdMember(uid);
			Member member = (Member) result.getData();
			if(member == null){
				logger.info(isNotMember+uid);
				return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, isNotMember+uid);
			}
//			logger.info("使用领取ID查询优惠券");
			Map<String, Object> params=new HashMap<String, Object>();
			// 优惠券
			CouponAdmin couponAdmin = couponAdminMapper.selectByPrimaryKey(adminId);
			if (couponAdmin == null) {
    			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "没有找到对应的优惠券"+adminId);
			}

			// 优惠券发放
    		CouponGrant couponGrant = couponGrantMapper.selectByPrimaryKey(grantId);
    		if (couponGrant == null) {
    			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "没有找到对应的优惠券发放"+grantId);
			} 
    		
    		params.put("couponGrant",couponGrant);
    		params.put("couponAdmin",couponAdmin);
    		// 获取领取数--优惠券管理和发放管理中间表数据
    		List<CouponAdminCouponGrant> couponAdminGrantList = couponAdminCouponGrantMapper.selectByParams(params);
    		// 发放领取限制
    		if (couponAdminGrantList != null && couponAdminGrantList.size() > 0) {
    			CouponAdminCouponGrant coupon = couponAdminGrantList.get(0);
				int receiveNum = coupon.getReceiveNum()==null?0:coupon.getReceiveNum();
				if (couponGrant.getGrantNum() <= receiveNum) {
					logger.info("该优惠券已领完");
					return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "该优惠券已领完");
				}
				// 修改发放领取数--优惠券管理和发放管理中间表数据
				coupon.setReceiveNum(receiveNum+1);
				couponAdminCouponGrantMapper.updateByPrimaryKey(coupon);
				//TODO 更新或放入缓存
    		} else {
				logger.error("adminId："+couponAdmin.getId()+"和grantId："+couponGrant.getId()+"找不到对应的优惠券管理优惠券发放中间表数据");
				throw new Exception();
			}
    		
    		// 获取用户领取数
    		params.put("memberId", uid);
    		int count=couponReceiveMapper.selectCount(params);
    		// 单个用户领取限制 TODO 改缓存
    		if(couponGrant.getReceiveLimit()!=null && couponGrant.getReceiveLimit()!=-1) {
	    		if(count >= couponGrant.getReceiveLimit()){
	        		logger.info( "已领取"+count+"次");
	    			return  new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "领取次数超出限领次数");
	    		}
    		}
			
			// 保存至领取表
			CouponReceive receive=new CouponReceive();
			receive.setId(UUIDGenerator.getUUID());
			receive.setMemberId(uid);
			receive.setNickName(member.getNickName());
			receive.setPhone(member.getPhone());
			receive.setUseState(Constant.NO);
			receive.setCreateDate(new Date());
			receive.setCouponAdmin(couponAdmin);
			receive.setCouponGrant(couponGrant);
			receive.setReceiveDate(new Date());
			Date effective_date_end=couponAdmin.getEffectiveDateEnd();//优惠券有效期结束时间
			BigDecimal effective_date_num=couponAdmin.getEffectiveDateNum();//优惠券有效期天数
			Date receiveDate=new Date();//领取时间
			if(StringUtil.isNotNull(effective_date_end)){
				receive.setOverDate(effective_date_end);
			}
			//领取多少天后
			if(StringUtil.isNotNull(effective_date_num)){
				receive.setOverDate(DateUtil.getNextDay(receiveDate,effective_date_num.intValue()));
			}
			// 保存领取记录
			couponReceiveMapper.insertSelective(receive);
			
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(null);
		} catch (Exception e) {
			logger.error("机构dubbo调用-CouponGrantApiServiceImpl", e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	private String receiveNum="你已兑换过了";
	private String yiduiphuanApi="优惠码已失效";
	private String errorApi="优惠码已兑换";
	private String wuxiaoApi="无效的兑换码";
	private String successApi="可在“我的优惠券”查看";
	private String pipeiebudao="无匹配到任何编码";
	private String duoci="生成多个重复编码，注意注意，警报警报，找蔡英写的生成编码规则";
	private String isNoGrant="未到发放时间";
	private String endGrant="已过了结束时间";
	private String yesuse="已经使用";
	private String isNotMember="没有找到对应的会员";
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto getVcode(String uid, String vcode) {
		if(redisUtil.tryLock("getVcode"+vcode, 1000*15)){
			ResultDto rsDto=null;
			try {
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("vcode", vcode);
				List<CouponVcode> couponVcodes=couponVcodeMapper.selectByParams(params);
				if(couponVcodes==null || couponVcodes.size()==0){
					rsDto = new ResultDto(ResultDto.BUSINESS_ERROR_CODE, wuxiaoApi);//无匹配到任何编码
					logger.info(pipeiebudao);
					return rsDto;
				}else if(couponVcodes.size()>=2){
					logger.error(duoci);//生成多个重复编码
				}
		    	CouponVcode couponVcode=couponVcodes.get(0);
				CouponGrant couponGrant=couponGrantMapper.selectByPrimaryKey(couponVcode.getCouponGrantId());
				rsDto=getCouponGrantyanzheng(couponVcode,vcode,couponGrant,uid);//获取验证发放验证
				if(rsDto!=null){
					return rsDto;
				}
				rsDto=saveCouponRevice(couponVcode,uid,couponGrant,vcode);
				if(rsDto!=null){
					return rsDto;
				}
			// TODO Auto-generated method stub
			} catch (Exception e) {
				logger.error("CouponGrantApiServiceImpl-getCoupReceive-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
	//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
				throw new ServiceException(e);
			}finally{
				redisUtil.remove("getVcode"+vcode);
			}
	
			rsDto = new ResultDto(ResultDto.OK_CODE, successApi);
			return rsDto;
		}else{

			logger.info("lock被占用15s,稍等");
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, "系统繁忙,稍等");
		}
	}
	private ResultDto saveCouponRevice(CouponVcode couponVcode,String uid,CouponGrant couponGrant
			,String vcode){
		List<String> memberIdList=new ArrayList<>();

    	//获取发放的优惠券ID
    	List<CouponAdminCouponGrant> couponGrantList=this.couponAdminCouponGrantMapper.selectByGrantId(couponGrant.getId());
		memberIdList.add(uid);
		ResultDto result=memberApiService.getMemberList(memberIdList);
		List<Member> memberList=(List<Member>) result.getData();
		if(memberList==null || memberList.size()==0){
			logger.info(isNotMember+uid);
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, isNotMember+uid);
		}
		Member member=memberList.get(0);
		

		CouponAdmin couponAdmin2=null;
		int i=0;
    	for (CouponAdminCouponGrant couponAdminCouponGrant : couponGrantList) {
    		if(i==0){
    			i++;
	    		couponAdmin2=couponAdminCouponGrant.getCouponAdmin();//查询判断的优惠券管理
	    		Map<String, Object> params=new HashMap<String, Object>();
	    		CouponGrant couponGrant2=new CouponGrant();
	    		couponGrant2.setId(couponGrant.getId());
	    		params.put("couponGrant",couponGrant2);
	    		params.put("couponAdmin",couponAdmin2);
	    		params.put("memberId", uid);
	    		int count=couponReceiveMapper.selectCount(params);
	    		if(couponGrant.getReceiveLimit()!=null && couponGrant.getReceiveLimit()!=-1)
	    		if(count>=couponGrant.getReceiveLimit()){
	        		logger.info( receiveNum+count+"次");
	        		
	    			return  new ResultDto(ResultDto.BUSINESS_ERROR_CODE, receiveNum);
	    		}

    		}
			 CouponReceive receive=new CouponReceive();
			 receive.setGrantVcode(vcode);
			 receive.setId(UUIDGenerator.getUUID());
			 receive.setMemberId(uid);
			 receive.setNickName(member.getNickName());
			 receive.setPhone(member.getPhone());
			 receive.setUseState(Constant.NO);
			 receive.setCreateDate(new Date());
			 receive.setCouponAdmin(couponAdminCouponGrant.getCouponAdmin());
			 receive.setCouponGrant(couponGrant);
			 receive.setReceiveDate(new Date());
			 CouponAdmin coupon=couponAdminCouponGrant.getCouponAdmin();
			 Date effective_date_end=coupon.getEffectiveDateEnd();//优惠券有效期结束时间
			 BigDecimal effective_date_num=coupon.getEffectiveDateNum();//优惠券有效期天数
			 Date receiveDate=new Date();//领取时间
			 if(StringUtil.isNotNull(effective_date_end)){
				 receive.setOverDate(effective_date_end);
			 }
			 //领取多少天后
			 if(StringUtil.isNotNull(effective_date_num)){
				 receive.setOverDate(DateUtil.getNextDay(receiveDate,effective_date_num.intValue()));
			 }
			 couponReceiveMapper.insertSelective(receive);
    	}
    	
    	couponVcode.setStart(Constant.YES);
    	couponVcodeMapper.updateByPrimaryKeySelective(couponVcode);
		 return null;
	}
	/**
	 * 获取验证发放验证
	 * @return
	 */
    private ResultDto getCouponGrantyanzheng(CouponVcode couponVcode,String vcode
    		,CouponGrant couponGrant,String uid){
    	if(couponVcode.getStart().equals(Constant.YES)){
    		logger.info(yesuse);
			return  new ResultDto(ResultDto.BUSINESS_ERROR_CODE, errorApi);//已使用
    	}
		if(DateUtil.diffDateTime(couponGrant.getGrantDate(),new Date())>=0){//未发放  10月发放，现在1月
    		logger.info(isNoGrant);
			return new ResultDto(ResultDto.BUSINESS_ERROR_CODE, wuxiaoApi);
		}
		if(DateUtil.diffDateTime(couponGrant.getGrantEndDate(), new Date())<=0){//已结束   10月结束  现在11月
    		logger.info(endGrant);
			return  new ResultDto(ResultDto.BUSINESS_ERROR_CODE, yiduiphuanApi);
		}
		
    	return null;
    }
    
    @Override
	public ResultDto getCommodityName(List<String> goodsIds, String couponAdminId) {
		// TODO Auto-generated method stub
    	logger.info("CouponGrantApiServiceImpl-getCommodityName-Exception=》机构dubbo调用-CouponGrantApiServiceImpl -- start");
		ResultDto rsDto = null;
		try{
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("goodsIds", goodsIds);
			params.put("couponAdminId", couponAdminId);
			List<String> list=couponAdminCategoryMapper.findCommodityName(params);
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(list);
		} catch(Exception e){
			logger.error("CouponGrantApiServiceImpl-getCommodityName-Exception=》机构dubbo调用-CouponGrantApiServiceImpl", e);
			throw new ServiceException(e);
		}
		return rsDto;
		
	}

	@Override
	public ResultDto getMyCouponList(Map<String, Object> params) {
		ResultDto rsDto = null;
		try {
			List<CouponReceive> list = couponReceiveMapper.findMyCouponList(null, params);
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(list);
		} catch (Exception e) {
			logger.error("机构dubbo调用-CouponGrantApiServiceImpl", e);
//			rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		return rsDto;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResultDto remindCouponOverDate() {
		logger.info("即将过期的优惠券（3天内-包括3天）提醒--start");
		ResultDto rsDto = null;
		boolean flag=false;
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			// 查询即将过期的优惠券（3天内-包括3天）
			params.put("remindDate", DateUtil.getNextDay(new Date(), 3));
			// 获取即将过期的优惠券
			List<CouponReceive> couponReceiveList = couponReceiveMapper.findListByOverDate(params);
			logger.info("获取即将过期的优惠券："+couponReceiveList);
			// 记录会员即将过期的优惠券数
			Map<String, Object> couponCount = new HashMap<String, Object>();
			
			if (couponReceiveList != null && couponReceiveList.size() > 0) {
				logger.info("获取即将过期的优惠券数："+couponReceiveList.size());
				// 遍历列表
				for (CouponReceive couponReceive : couponReceiveList) {
					// 记录用户的过期优惠券数
					if (couponCount.containsKey(couponReceive.getMemberId())) {
						int count = (int) (couponCount.get(couponReceive.getMemberId()));
						couponCount.put(couponReceive.getMemberId(), ++count);
					} else {
						couponCount.put(couponReceive.getMemberId(), 1);
					}
					logger.info("修改已提醒的优惠券：memberId"+couponReceive.getMemberId()+",couponReceiveId"+couponReceive.getId());
					// 修改是否已提醒(0：未提醒，1：已提醒)
					couponReceive.setIsRemind(Constant.YES);
					couponReceiveMapper.updateByPrimaryKeySelective(couponReceive);
				}
				flag=true;
			}
			
			if (couponCount != null && couponCount.size() > 0) {
				logger.info("加入消息中心--start");
				// 遍历会员的优惠券过期数量
				for (String key : couponCount.keySet()) {
					logger.info("用户Id："+key+",有"+couponCount.get(key)+"张优惠券即将过期");
					// 加入消息中心
					MemberMessage memberMessage = new MemberMessage();
					String sendMsgStr = "您有"+couponCount.get(key)+"张优惠券即将过期，赶快去使用吧!";
					memberMessage.setMemberId(key); // 会员id
					memberMessage.setType(MessTypeEnum.SYSTEM);
					memberMessage.setTitle("优惠券过期提醒");
					memberMessage.setStatus("0");
					memberMessage.setContent(sendMsgStr);
					memberMessageApiService.addMess(memberMessage);
				}
				logger.info("加入消息中心--end");
				flag=true;
			}
			logger.info("即将过期的优惠券（3天内-包括3天）提醒--end");
			rsDto = new ResultDto(ResultDto.OK_CODE, "success");
			rsDto.setData(flag);
		}catch(Exception e){
			logger.error("机构dubbo调用-CouponGrantApiServiceImpl", e);
			throw new ServiceException(e);
		}
		return rsDto;
	}
	
//	@Override
//	public Map<String, Object> findCouponList(String uid, String couponStatus,List<Map<String, Object>> lMaps,String path) {
//		if (lMaps != null) {//如果是下单则访问之前的接口(原接口为findCoupon，为支持指定活动做了改造，改造后接口findCouponForOrder)
//			return findCouponForOrder(uid, lMaps);
//		} else {
//			List<CouponReceive> list = couponReceiveApiConsumer.findCouponList(uid, couponStatus);
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//			List<Object> resultList = new ArrayList<Object>();
//			if (list != null && list.size() != 0) {
//				for (CouponReceive coupon : list) {
//					Map<String, Object> temp = new HashMap<String, Object>();
//					// 获取商品类型和金额限制
//					getGoodsTypeAndLowPrice(coupon, temp);
//					temp.put("id", coupon.getCouponAdmin().getId());
//					temp.put("title", coupon.getCouponAdmin().getName());
//					int amount = coupon.getCouponAdmin().getFaceValue().intValue();
//					temp.put("amount", amount);
//					// 获取优惠券使用状态，计算数优惠券状态和开始过期时间
//					getCouponUseState(coupon, temp);
//					temp.put("goodsSelect", coupon.getCouponAdmin().getGoodsSelect());
//					temp.put("useCondition", "订单满" + getLowPrice(temp.get("lowPrice")) + "元使用");
//					temp.put("useDate", "有效期:" + temp.get("startline") + "至" + temp.get("deadline"));
//					temp.put("receiveId", coupon.getId());
//					//封装url数据
//					temp.put("url",path+"/coupon/toCouponInfo.do?couponId="+coupon.getId());
//					//优惠券状态，0：未使用，1：已使用，2：已过期 (YETTIMEOUT(已过期),NOTUSE（未使用）,YETUSE（已使用)
//					if("0".equals(couponStatus)){
//						temp.put("status","NOTUSE");
//					}else if("1".equals(couponStatus)){
//						temp.put("status","YETUSE");
//					}else if("2".equals(couponStatus)){
//						temp.put("status","YETTIMEOUT");
//					}
//					resultList.add(temp);
//				}
//			}
//			resultMap.put("items", resultList);
//			return resultMap;
//		}
//	}
	/**
	 * 
	* @Title: findCouponForOrder 2016-10-13
	* @Description: 下单时获取可用优惠券（在原接口findCoupon基础上改造，支持指定活动） 2016-10-13，添加虚拟商品盘点
	* @param uid
	* @param lMaps
	* @return
	 */
	@Override
	public Map<String, Object> findCouponForOrder(String uid, List<Map<String, Object>> lMaps) {
		logger.info("开始使用优惠券--订单--"+uid);
		List<CouponReceive> list =couponReceiveApiConsumer.findCoupon(uid, lMaps);
		List<String> skuIdList=new ArrayList<String>();//skuId集合表
		Map<String, Object> skuIdkeyValue=new HashMap<String,Object>();

		Map<String,Object> resultMap = new HashMap<String, Object>();//返回的数据
		getSkuIdsAmount(lMaps,skuIdList,skuIdkeyValue);//填充skuId List 和key为skuId，value为Map<String,Object>
		if(skuIdList==null || skuIdList.size()==0){//如果没有传商品集合条件
			logger.info("进行中优惠券0"+resultMap);
			return resultMap;
		}else{
			logger.info("进行中优惠券1"+resultMap);
			getCouponReceiveGoodsListNew(list, resultMap,skuIdList,skuIdkeyValue);
		}
		List<Object> redislist=new ArrayList<Object>();
		redislist.add(resultMap);
		logger.info("执行完优惠券"+resultMap);
//		redisUtil.set("findCoupon"+uid+((lMaps==null || lMaps.size()==0)?"":lMaps.toString()), redislist,new Long(6));
		return resultMap;

	}
	/**
	 * 
	* @Title: getCouponReceiveGoodsListNew 2016-10-13
	* @Description: 原getCouponReceiveGoodsList 改造，支持指定活动2016-10-13
	* @param list
	* @param resultMap
	* @param skuIdList
	* @param skuIdkeyValue
	* @return
	 */
	public Integer getCouponReceiveGoodsListNew(List<CouponReceive> list,Map<String,Object> resultMap,List<String> skuIdList
			,Map<String, Object> skuIdkeyValue){

		List<CommoditySku> skusList=commoditySkuApiConsumer.getListCommoditySkus(skuIdList);
		listExceptionNumm(skusList, "根据skuid集合查不到商品");
		List<Object> resultList = new ArrayList<Object>();

		Map<String, Object> mapDouble=new HashMap<String, Object>();//skuId和价格*num
		//获取每个商品对应价格
		getPriceMapNew(mapDouble,skusList,skuIdkeyValue);
		double mapDoubleNum=getMapDoubleNum(mapDouble);//总金额
		if(list !=null && list.size() != 0){
			for(CouponReceive coupon : list){
				Map<String,Object> temp = new HashMap<String, Object>();
				//获取商品类型和金额限制
				if(!getGoodsTypeLowPriceShopping(coupon, temp,skusList,skuIdkeyValue,mapDouble,mapDoubleNum))//如果返回false，就不添加返回优惠券
				{
					continue;
				}
				//获取优惠券使用状态，计算数优惠券状态和开始过期时间
				getCouponUseState(coupon,temp);

				temp.put("id",coupon.getId());//更新冗余字段为领取id
				temp.put("title",coupon.getCouponAdmin().getName());
				int amount=coupon.getCouponAdmin().getFaceValue().intValue();
				temp.put("amount",amount);
				String goodsType="";
				if(!temp.get("goodsType").toString().equals("全部商品")){
					goodsType="【"+temp.get("goodsType")+"】";
				} else {
					goodsType="【预售、抢购不使用】";
				}
				temp.put("useCondition", "订单满"+getLowPrice(temp.get("lowPrice"))+"元使用");
				temp.put("useDate", "有效期:"+temp.get("startline")+"至"+temp.get("deadline"));
				temp.put("receiveId", coupon.getId());
				resultList.add(temp);
			}
		}
		resultMap.put("items",resultList);
		return resultList.size();
		
	}
	
	/**
	 * 获取所有商品和活动商品对应的价格 2016-10-13
	 * @param mapDouble  skuId对应的价格
	 * @param skusList  skuList所有商品的基本信息
	 * @param skuIdkeyValue app传递的参数
	 */
	private void getPriceMapNew(Map<String, Object> mapDouble,List<CommoditySku> skusList,
			Map<String, Object> skuIdkeyValue){
		List<Object> activityIdList=new ArrayList<Object>();//除去批发所有活动id
		Map<String,Object> pifaMap=new HashMap<String,Object>();;//批发所有活动id
		for (CommoditySku commoditySku : skusList) {
			if(isVartualCommodity(commoditySku)){
				continue;
			}
			//根据商品skuId，获取数量，还有goodsType，activityId
			Map<String,Object> value=(Map<String, Object>) skuIdkeyValue.get(commoditySku.getId());
			Object goodsTypeObject=value.get("goodsType");
			String goodsType="";
			if(goodsTypeObject!=null)
				goodsType=goodsTypeObject.toString();
			int num=(Integer)value.get("num");
			if(StringUtil.isEmpty(goodsType) || goodsType.equals("COMMON_BUY")){//普通商品
				//skuIdkeyValue  添加商品节本信息 skuId为key，商品id，类目id，价格
				putskuIdkeyValue(commoditySku, num*commoditySku.getFavourablePrice().doubleValue(), mapDouble);
			}else if(goodsType.equals(ActivityTypeEnum.WHOLESALE_MANAGER.getValue())){//如果是批发
				String  activityId=(String)value.get("activityId");
				stringException(activityId, "activityId");//不为null
				Map<String, Object> pafavalue=new HashMap<String, Object>();//统计一个商品包含多个sku的总数量
				pafavalue.put("commoditySku", commoditySku);
				pafavalue.put("num", num);
				pifaMap.put(commoditySku.getId(), pafavalue);
				
//				BigDecimal wahlePrice=activityApiConsumer.getPifaPrice(commoditySku.getCommodity().getCode(), num);
//				objectException(wahlePrice, "批发价格取不到为null");
//				putskuIdkeyValue(commoditySku, wahlePrice.doubleValue()*num, mapDouble);
				//如果是非批发的活动
			}else{
				//抢购预售不可用
				if(goodsType.equals(ActivityTypeEnum.PANIC_BUY.getValue()) || 
						goodsType.equals(ActivityTypeEnum.PRE_SALE.getValue())){
					continue;
				}
				String  activityId=(String)value.get("activityId");
				stringException(activityId, "activityId");//不为null
				Map<String, String> activitymap=new HashMap<String, String>();
				activitymap.put("activityId", activityId);
				activitymap.put("skuId", commoditySku.getId());
				activityIdList.add(activitymap);
			}
		}
		//获取非活动批发数据的价格集合
		if(activityIdList!=null && activityIdList.size()>0){
			List<ActivityCommoditySku> activityCommoditySkus=  activityApiConsumer.selectActivitySkuPrice(activityIdList);
			//根据sku活动集合获取skuIdkeyValue的数据
			getActivitySkusputskuIdkeyValueNew(activityCommoditySkus, skuIdkeyValue, skusList,mapDouble);
		}
		//获取批发的价格
		if(pifaMap!=null && pifaMap.size()>0){
			getPifaSkuputskuIdkeyValue(pifaMap,mapDouble);
		}
	}
	
	/**
	 * 是否虚拟商品，true是，0否
	 * @param commoditySku
	 * @return
	 */
	private boolean isVartualCommodity(CommoditySku commoditySku){
		boolean flag=false;
		objectException(commoditySku.getCommodity(), "sku没有对应的商品对象，所以找不到虚拟商品");
		stringException(commoditySku.getCommodity().getCommodityDummyType(), "skuid"+commoditySku.getId()+"对应的商品id"+commoditySku.getCommodity().getId()+"的虚拟商品字段是null");
		if(commoditySku.getCommodity().getCommodityDummyType().equals(PrmsConstant.isVartual)){
			flag=true;
		}
		return flag;
	}
	

	/**
	 * @desc根据sku活动集合获取skuIdkeyValue的数据,填充非批发的价格 2016-10-13
	 * @param activityCommoditySkus
	 * @param skuIdkeyValue  app返回数据
	 * @param  mapDouble  map商品对应价格商品id  类目
	 * @param skusList
	 */
	private void  getActivitySkusputskuIdkeyValueNew(List<ActivityCommoditySku> activityCommoditySkus,Map<String, Object> skuIdkeyValue
			,List<CommoditySku> skusList,Map<String, Object> mapDouble){
		for (ActivityCommoditySku activityCommoditySku : activityCommoditySkus) {
			//活动管理id，activityCommoditySku.getActivityCommodity().getActivityId();
			String activityManageId = null;
			CommoditySku csku=null;
			for (CommoditySku commoditySku : skusList) {
				if(activityCommoditySku.getCommoditySkuId().equals(commoditySku.getId())){
					activityManageId=activityCommoditySku.getActivity().getId();//判断正确的商品管理id
					csku=commoditySku;
					break;
				}
			}
			objectException(csku, "活动skuid，匹配不到商品sku数据");
			objectException(activityCommoditySku.getActivityPrice(), "活动skuid，匹配不到商品sku数据");

			Map<String,Object> value=(Map<String, Object>) skuIdkeyValue.get(activityCommoditySku.getCommoditySkuId());
			int num=(Integer)value.get("num");
			putskuIdkeyValueActivityManage(csku, activityCommoditySku.getActivityPrice().doubleValue()*num, mapDouble,activityManageId);
		}
		
	}
	/**
	 * desc 添加商品节本信息 skuId为key，商品id，类目id，价格 ，活动管理id 2016-10-13
	 * @param commoditySku  sku信息
	 * @param price  商品总价格
	 * @param skuIdkeyValue
	 */
	private void putskuIdkeyValueActivityManage(CommoditySku commoditySku,double price,Map<String, Object> mapDouble
			,String activityManageId){

		//skuId为key，商品id，类目id，价格
		Map<String, Object> skuvalue=new HashMap<String, Object>();
		skuvalue.put("goodsId", commoditySku.getCommodity().getId());
		skuvalue.put("cid", commoditySku.getCommodity().getCategoryId());
		skuvalue.put("activityManageId", activityManageId);
		
		skuvalue.put("price", price);
		mapDouble.put(commoditySku.getId(), skuvalue);
	}
	/**
	 * 获取商品类型
	 * @param coupon
	 * @param temp
	 */
	private void getGoodsTypeAndLowPrice(CouponReceive coupon,Map<String,Object> temp) {
		if(coupon.getCouponAdmin().getGoodsSelect() == null ||Constant.COMM_ALL.equals(coupon.getCouponAdmin().getGoodsSelect())){
			temp.put("goodsType","该券除预售、抢购商品不使用");
		}else if(Constant.COMM_FIXED.equals(coupon.getCouponAdmin().getGoodsSelect())){
			CouponReceive cReceive = couponReceiveApiConsumer.getCouponReceive(coupon.getId());
			List<String> goodsIds = cReceive.getCouponAdmin().getList();
			if(goodsIds==null || goodsIds.size()==0 ){
				temp.put("goodsType", "");
			} else {
				String str = couponReceiveApiConsumer.getCommodityName(goodsIds, coupon.getCouponAdmin().getId());
				temp.put("goodsType", "仅限商品："+str);
			}
		}else if(Constant.COMM_CATEGORY.equals(coupon.getCouponAdmin().getGoodsSelect())){
			CouponReceive cReceive = couponReceiveApiConsumer.getCouponReceive(coupon.getId());
			List<String> categoryIds = cReceive.getCouponAdmin().getList();
			if (categoryIds == null || categoryIds.size() == 0) {
				temp.put("goodsType", "");
			} else {
				String str= categoryApiConsumer.getCategoryName(categoryIds);
				temp.put("goodsType","仅限商品分类："+str);
			}
		}else if("1".equals(coupon.getCouponAdmin().getGoodsSelect())){
			CouponReceive cReceive = couponReceiveApiConsumer.getCouponReceive(coupon.getId());
			List<String> activityIds = cReceive.getCouponAdmin().getList();
			if(activityIds==null || activityIds.size()==0 ){
				temp.put("goodsType", "");
			} else {
				String str = couponReceiveApiConsumer.getActivityName(activityIds);
				temp.put("goodsType", "仅限活动："+str);
			}
		}
		if(coupon.getCouponAdmin().getConsumptionLimit() ==null || -1 == coupon.getCouponAdmin().getConsumptionLimit().intValue()){
			temp.put("lowPrice","0");
		}else{
			temp.put("lowPrice",coupon.getCouponAdmin().getConsumptionLimit());
		}		
	}
	public Integer getCouponReceiveGoodsList(List<CouponReceive> list,Map<String,Object> resultMap,List<String> skuIdList
			,Map<String, Object> skuIdkeyValue){

		List<CommoditySku> skusList=commoditySkuApiConsumer.getListCommoditySkus(skuIdList);
		listExceptionNumm(skusList, "根据skuid集合查不到商品");
		List<Object> resultList = new ArrayList<Object>();

		Map<String, Object> mapDouble=new HashMap<String, Object>();//skuId和价格*num
		//获取每个商品对应价格
		getPriceMap(mapDouble,skusList,skuIdkeyValue);
		double mapDoubleNum=getMapDoubleNum(mapDouble);//总金额
		if(list !=null && list.size() != 0){
			for(CouponReceive coupon : list){
				Map<String,Object> temp = new HashMap<String, Object>();
				//获取商品类型和金额限制
				if(!getGoodsTypeLowPriceShopping(coupon, temp,skusList,skuIdkeyValue,mapDouble,mapDoubleNum))//如果返回false，就不添加返回优惠券
				{
					continue;
				}
				//获取优惠券使用状态，计算数优惠券状态和开始过期时间
				getCouponUseState(coupon,temp);

				temp.put("id",coupon.getCouponAdmin().getId());
				temp.put("title",coupon.getCouponAdmin().getName());
				int amount=coupon.getCouponAdmin().getFaceValue().intValue();
				temp.put("amount",amount);
				String goodsType="";
				if(!temp.get("goodsType").toString().equals("全部商品")){
					goodsType="【"+temp.get("goodsType")+"】";
				} else {
					goodsType="【预售、抢购不使用】";
				}
				temp.put("useCondition", goodsType+"订单满"+getLowPrice(temp.get("lowPrice"))+"元使用");
				temp.put("useDate", "使用期限:"+temp.get("startline")+"至"+temp.get("deadline"));
				temp.put("receiveId", coupon.getId());
				resultList.add(temp);
			}
		}
		resultMap.put("items",resultList);
		return resultList.size();
		
	}
	/**
	skuvalue.put("price", price);
	 * 获取商品map Double总价格
	 * @param mapDouble
	 * @return
	 */
	private double getMapDoubleNum(Map<String, Object> mapDouble){
		double num=0.0;
		for (String key : mapDouble.keySet()) {
			Map<String, Object> skuvalue=(Map<String, Object>) mapDouble.get(key);
			objectException(skuvalue.get("price"), "getMapDoubleNum skuvalue.get(price)");
			num+=Double.parseDouble(skuvalue.get("price").toString());
		}
		return num;
	}
	/**
	 *  desc object不为null
	 * @param object object类型
	 * @param message  信息
	 */
	private void objectException(Object object,String message){
		StringappUtil.objectException(object, message);
	}
	private <T> void listExceptionNumm(List<T> list,String message){
		StringappUtil.listExceptionNumm(list, message);
	}
	
	/**
	 * 获取商品类型和金额限制,根据购物车商品查询金额，和部分商品，类目限制进行剔除
	 * @param coupon  优惠券领取
	 * @param temp  存入临时变量
	 * @param skuIdList  skuId集合
	 * @param skuIdkeyValue  skuId对应的value
	 * @param mapDouble  key skuId对应的value goodsId cid price
	 */
	public boolean getGoodsTypeLowPriceShopping(CouponReceive coupon,Map<String,Object> temp,
			List<CommoditySku> skusList,Map<String, Object> skuIdkeyValue,Map<String, Object> mapDouble,
			double mapDoubleNum) {
		boolean flag=true;
		if(coupon.getCouponAdmin().getGoodsSelect() == null ||Constant.COMM_ALL.equals(coupon.getCouponAdmin().getGoodsSelect())){
			temp.put("goodsType","全部商品");
		}else if(Constant.COMM_FIXED.equals(coupon.getCouponAdmin().getGoodsSelect())){

			CouponReceive cReceive = couponReceiveApiConsumer.getCouponReceive(coupon.getId());
			List<String> goodsIds = cReceive.getCouponAdmin().getList();
			Map<String, Object> mapDoubleCopy=new HashMap<String, Object>();

			listExceptionNumm(goodsIds, "getGoodsTypeLowPriceShopping 此优惠券没有符合任何goodsIds商品，脏数据  "+cReceive.getId());
			
			for (String key : mapDouble.keySet()) {
				Map<String, Object> skuValue=(Map<String, Object>) mapDouble.get(key);
				if(goodsIds.contains(skuValue.get("goodsId").toString())){//如果
					mapDoubleCopy.put(key, skuValue);
				}
			}
			if(mapDoubleCopy==null || mapDoubleCopy.size()==0){
				flag=false;
				return flag;
			}
			mapDoubleNum=getMapDoubleNum(mapDouble);//总金额
			String str = couponReceiveApiConsumer.getCommodityName(goodsIds, coupon.getCouponAdmin().getId());
			temp.put("goodsType", str);
		}else if(Constant.COMM_CATEGORY.equals(coupon.getCouponAdmin().getGoodsSelect())){
			CouponReceive cReceive = couponReceiveApiConsumer.getCouponReceive(coupon.getId());
			List<String> categoryIds = cReceive.getCouponAdmin().getList();
			Map<String, Object> mapDoubleCopy=new HashMap<String, Object>();
			
			for (String key : mapDouble.keySet()) {
				Map<String, Object> skuValue=(Map<String, Object>) mapDouble.get(key);
				listExceptionNumm(categoryIds, "getGoodsTypeLowPriceShopping 此优惠券没有符合任何类目，脏数据  "+cReceive.getId());
				if(categoryIds.contains(skuValue.get("cid").toString())){//如果
					mapDoubleCopy.put(key, skuValue);
				}
			}
			if(mapDoubleCopy==null || mapDoubleCopy.size()==0){
				flag=false;
				return flag;
			}
			mapDoubleNum=getMapDoubleNum(mapDouble);//总金额
			String str= categoryApiConsumer.getCategoryName(categoryIds);
			temp.put("goodsType",str);
		}else if("1".equals(coupon.getCouponAdmin().getGoodsSelect())){
			CouponReceive cReceive = couponReceiveApiConsumer.getCouponReceive(coupon.getId());
			List<String> activityIds = cReceive.getCouponAdmin().getList();
			
			Map<String, Object> mapDoubleCopy=new HashMap<String, Object>();
			for (String key : mapDouble.keySet()) {
				Map<String, Object> skuValue=(Map<String, Object>) mapDouble.get(key);
				listExceptionNumm(activityIds, " 此优惠券没有符合任何活动管理id，脏数据  "+cReceive.getId());
				if(skuValue.get("activityManageId") != null){//如果下单时没有选择指定活动的商品，则skuValue.get("activityManageId")为null
					if(activityIds.contains(skuValue.get("activityManageId").toString())){//如果存在该活动id，加入map
						mapDoubleCopy.put(key, skuValue);
					}
				}				
			}
			//没有符合的活动id，返回false
			if(mapDoubleCopy==null || mapDoubleCopy.size()==0){
				flag=false;
				return flag;
			}
			mapDoubleNum=getMapDoubleNum(mapDouble);//总金额
			
			if(activityIds==null || activityIds.size()==0 ){
				temp.put("goodsType", "");
			} else {
				String activityManageStr = couponReceiveApiConsumer.getActivityName(activityIds);
				temp.put("goodsType", "仅限活动："+activityManageStr);
			}
		}
		if(coupon.getCouponAdmin().getConsumptionLimit() ==null || -1 == coupon.getCouponAdmin().getConsumptionLimit().intValue()){
			temp.put("lowPrice","0");
		}else{
			objectException(coupon.getCouponAdmin().getConsumptionLimit(), "getGoodsTypeLowPriceShopping-coupon.getCouponAdmin().getConsumptionLimit()");
			if(mapDoubleNum<coupon.getCouponAdmin().getConsumptionLimit().doubleValue()){
				flag= false;
			}
			temp.put("lowPrice",coupon.getCouponAdmin().getConsumptionLimit());
		}
		if(mapDoubleNum<=coupon.getCouponAdmin().getFaceValue().doubleValue()){
			flag=false;
		}
		
		return flag;
	}
	
	/**
	 * 获取优惠券使用状态，计算数优惠券状态和开始过期时间
	 * @param coupon
	 * @param temp
	 */
	public void  getCouponUseState(CouponReceive coupon,Map<String,Object> temp){
		if(Constant.NO.equals(coupon.getUseState()) ){//未使用
			if(Constant.YES.equals(coupon.getCouponAdmin().getEffectiveDateState())){    //指定有效期
				if(coupon.getCouponAdmin().getEffectiveDateEnd().getTime()>new Date().getTime()){
					temp.put("status","NOTUSE");//未过期
					temp.put("startline",DateUtil.format(coupon.getCouponAdmin().getEffectiveDateStart(), DateUtil.FORMAT_DATE));
					temp.put("deadline",DateUtil.format(coupon.getCouponAdmin().getEffectiveDateEnd(), DateUtil.FORMAT_DATE));
				}else{
					temp.put("status","YETTIMEOUT");//已过期
					temp.put("startline",DateUtil.format(coupon.getCouponAdmin().getEffectiveDateStart(), DateUtil.FORMAT_DATE));
					temp.put("deadline",DateUtil.format(coupon.getCouponAdmin().getEffectiveDateEnd(), DateUtil.FORMAT_DATE));
				}
			}else if(Constant.NO.equals(coupon.getCouponAdmin().getEffectiveDateState())){
				Date date = DateUtil.addDate(coupon.getReceiveDate(),coupon.getCouponAdmin().getEffectiveDateNum().intValue());
				if(date.getTime() > new Date().getTime()){
					temp.put("status","NOTUSE");//未过期
					
					temp.put("startline",DateUtil.format(coupon.getReceiveDate(), DateUtil.FORMAT_DATE));
					temp.put("deadline",DateUtil.format(date, DateUtil.FORMAT_DATE));
				}else{
					temp.put("status","YETTIMEOUT");//已过期
					temp.put("startline",DateUtil.format(coupon.getReceiveDate(), DateUtil.FORMAT_DATE));
					temp.put("deadline",DateUtil.format(date, DateUtil.FORMAT_DATE));
				}
			}
		}
		if(Constant.YES.equals(coupon.getUseState()) ){//已使用
			if(Constant.YES.equals(coupon.getCouponAdmin().getEffectiveDateState())){    //指定有效期1:指定有效期，0:自定义有效期:
				if(coupon.getCouponAdmin().getEffectiveDateEnd().getTime()>new Date().getTime()){
					temp.put("status","YETUSE");//已使用
					temp.put("startline",DateUtil.format(coupon.getCouponAdmin().getEffectiveDateStart(), DateUtil.FORMAT_DATE));
					temp.put("deadline",DateUtil.format(coupon.getCouponAdmin().getEffectiveDateEnd(), DateUtil.FORMAT_DATE));
				}else{
					temp.put("status","YETTIMEOUT");//过期
					temp.put("startline",DateUtil.format(coupon.getCouponAdmin().getEffectiveDateStart(), DateUtil.FORMAT_DATE));
					temp.put("deadline",DateUtil.format(coupon.getCouponAdmin().getEffectiveDateEnd(), DateUtil.FORMAT_DATE));
				}
			}else if(Constant.NO.equals(coupon.getCouponAdmin().getEffectiveDateState())){//指定有效期1:指定有效期，0:自定义有效期:
				Date date = DateUtil.addDate(coupon.getReceiveDate(),coupon.getCouponAdmin().getEffectiveDateNum().intValue());
				if(date.getTime() > new Date().getTime()){
					temp.put("status","YETUSE");
					temp.put("startline",DateUtil.format(coupon.getReceiveDate(), DateUtil.FORMAT_DATE));
					temp.put("deadline",DateUtil.format(date, DateUtil.FORMAT_DATE));
				}else{
					temp.put("status","YETTIMEOUT");
					temp.put("startline",DateUtil.format(coupon.getReceiveDate(), DateUtil.FORMAT_DATE));
					temp.put("deadline",DateUtil.format(date, DateUtil.FORMAT_DATE));
				}
			}
		}
	}
	/**
	 * 获取所有商品和活动商品对应的价格
	 * @param mapDouble  skuId对应的价格
	 * @param skusList  skuList所有商品的基本信息
	 * @param skuIdkeyValue app传递的参数
	 */
	private void getPriceMap(Map<String, Object> mapDouble,List<CommoditySku> skusList,
			Map<String, Object> skuIdkeyValue){
		List<Object> activityIdList=new ArrayList<Object>();//除去批发所有活动id
		Map<String,Object> pifaMap=new HashMap<String,Object>();;//批发所有活动id
		for (CommoditySku commoditySku : skusList) {
			//根据商品skuId，获取数量，还有goodsType，activityId
			Map<String,Object> value=(Map<String, Object>) skuIdkeyValue.get(commoditySku.getId());
			Object goodsTypeObject=value.get("goodsType");
			String goodsType="";
			if(goodsTypeObject!=null)
				goodsType=goodsTypeObject.toString();
			int num=(Integer)value.get("num");
			if(StringUtil.isEmpty(goodsType) || goodsType.equals("COMMON_BUY")){//普通商品
				//skuIdkeyValue  添加商品节本信息 skuId为key，商品id，类目id，价格
				putskuIdkeyValue(commoditySku, num*commoditySku.getFavourablePrice().doubleValue(), mapDouble);
			}else if(goodsType.equals(ActivityTypeEnum.WHOLESALE_MANAGER.getValue())){//如果是批发
				String  activityId=(String)value.get("activityId");
				stringException(activityId, "activityId");//不为null
				Map<String, Object> pafavalue=new HashMap<String, Object>();//统计一个商品包含多个sku的总数量
				pafavalue.put("commoditySku", commoditySku);
				pafavalue.put("num", num);
				pifaMap.put(commoditySku.getId(), pafavalue);
				
//				BigDecimal wahlePrice=activityApiConsumer.getPifaPrice(commoditySku.getCommodity().getCode(), num);
//				objectException(wahlePrice, "批发价格取不到为null");
//				putskuIdkeyValue(commoditySku, wahlePrice.doubleValue()*num, mapDouble);
				//如果是非批发的活动
			}else{
				//抢购预售不可用
				if(goodsType.equals(ActivityTypeEnum.PANIC_BUY.getValue()) || 
						goodsType.equals(ActivityTypeEnum.PRE_SALE.getValue())){
					continue;
				}
				String  activityId=(String)value.get("activityId");
				stringException(activityId, "activityId");//不为null
				Map<String, String> activitymap=new HashMap<String, String>();
				activitymap.put("activityId", activityId);
				activitymap.put("skuId", commoditySku.getId());
				activityIdList.add(activitymap);
			}
		}
		//获取非活动批发数据的价格集合
		if(activityIdList!=null && activityIdList.size()>0){
		List<ActivityCommoditySku> activityCommoditySkus=  activityApiConsumer.selectActivitySkuPrice(activityIdList);
		//根据sku活动集合获取skuIdkeyValue的数据
		getActivitySkusputskuIdkeyValue(activityCommoditySkus, skuIdkeyValue, skusList,mapDouble);
		}
		//获取批发的价格
		if(pifaMap!=null && pifaMap.size()>0){
			getPifaSkuputskuIdkeyValue(pifaMap,mapDouble);
		}
	}
	/**
	 * @desc根据sku活动集合获取skuIdkeyValue的数据,填充非批发的价格
	 * @param activityCommoditySkus
	 * @param skuIdkeyValue  app返回数据
	 * @param  mapDouble  map商品对应价格商品id  类目
	 * @param skusList
	 */
	private void  getActivitySkusputskuIdkeyValue(List<ActivityCommoditySku> activityCommoditySkus,Map<String, Object> skuIdkeyValue
			,List<CommoditySku> skusList,Map<String, Object> mapDouble){
		for (ActivityCommoditySku activityCommoditySku : activityCommoditySkus) {
			CommoditySku csku=null;
			for (CommoditySku commoditySku : skusList) {
				if(activityCommoditySku.getCommoditySkuId().equals(commoditySku.getId())){
					csku=commoditySku;
					break;
				}
			}
			objectException(csku, "活动skuid，匹配不到商品sku数据");
			objectException(activityCommoditySku.getActivityPrice(), "活动skuid，匹配不到商品sku数据");

			Map<String,Object> value=(Map<String, Object>) skuIdkeyValue.get(activityCommoditySku.getCommoditySkuId());
			int num=(Integer)value.get("num");
			putskuIdkeyValue(csku, activityCommoditySku.getActivityPrice().doubleValue()*num, mapDouble);
		}
		
	}
	/**
	 * desc 添加商品节本信息 skuId为key，商品id，类目id，价格
	 * @param commoditySku  sku信息
	 * @param price  商品总价格
	 * @param skuIdkeyValue
	 */
	private void putskuIdkeyValue(CommoditySku commoditySku,double price,Map<String, Object> mapDouble){

		//skuId为key，商品id，类目id，价格
		Map<String, Object> skuvalue=new HashMap<String, Object>();
		skuvalue.put("goodsId", commoditySku.getCommodity().getId());
		skuvalue.put("cid", commoditySku.getCommodity().getCategoryId());
		
		skuvalue.put("price", price);
		mapDouble.put(commoditySku.getId(), skuvalue);
	}
	/**
	 * 
	 * @param pifaMap  批发的信息  commoditySku商品 commoditySku商品   num数量
	 * @param mapDouble   sku商品价格信息  
	 */
	private void getPifaSkuputskuIdkeyValue(Map<String,Object> pifaMap,Map<String, Object> mapDouble){
		Map<String,Integer> pifaMapGoods=new  HashMap<String, Integer>();//统计同一个商品批发数量,key 商品code
		Map<String,BigDecimal> pifaMapGoodsPrice=new  HashMap<String, BigDecimal>();//统计同一个商品批发价格  key 商品code
		for (String key : pifaMap.keySet()) {
			Map<String,Object> pifaMapitem=(Map<String, Object>) pifaMap.get(key);
			CommoditySku commoditySku=(CommoditySku) pifaMapitem.get("commoditySku");
			Integer num=(Integer) pifaMapitem.get("num");
			if(pifaMapGoods.keySet().contains(commoditySku.getCommodity().getCode())){//判断是否重复的商品编码
				Integer numcontains=pifaMapGoods.get(commoditySku.getCommodity().getCode());//获取存在Map
				pifaMapGoods.put(commoditySku.getCommodity().getCode(), numcontains+num);
			}else{
				pifaMapGoods.put(commoditySku.getCommodity().getCode(), num);//某个商品的数量
			}
		}
		for (String key  : pifaMapGoods.keySet()) {//计算价格，赋予value
			BigDecimal wahlePrice=activityApiConsumer.getPifaPrice(key, pifaMapGoods.get(key));//商品编码和数量
			objectException(wahlePrice, "批发价格取不到为null");
			pifaMapGoodsPrice.put(key, wahlePrice);
		}
		for (String key : pifaMap.keySet()) {
			Map<String,Object> pifaMapitem=(Map<String, Object>) pifaMap.get(key);
			CommoditySku commoditySku=(CommoditySku) pifaMapitem.get("commoditySku");
			BigDecimal wahlePrice=pifaMapGoodsPrice.get(commoditySku.getCommodity().getCode());
			int num=(Integer)pifaMapitem.get("num");
			putskuIdkeyValue(commoditySku, wahlePrice.doubleValue()*num, mapDouble);
		}
	}
	/**
	 *  desc 字符串不为null
	 * @param string 字符串
	 * @param message  信息
	 */
	private void stringException(String string,String message){
		StringappUtil.stringException(string, message);
	}
	public Integer getCouponReceive(List<CouponReceive> list,Map<String,Object> resultMap){
		List<Object> resultList = new ArrayList<Object>();
		if(list !=null && list.size() != 0){
			for(CouponReceive coupon : list){
				Map<String,Object> temp = new HashMap<String, Object>();
				//获取商品类型和金额限制
				getGoodsTypeLowPrice(coupon, temp);
				temp.put("id",coupon.getId());//更新冗余字段为领取id
				temp.put("title",coupon.getCouponAdmin().getName());
				int amount=coupon.getCouponAdmin().getFaceValue().intValue();
				temp.put("amount",amount);
				//获取优惠券使用状态，计算数优惠券状态和开始过期时间
				getCouponUseState(coupon,temp);
				String goodsType="";
				if(!temp.get("goodsType").toString().equals("全部商品")){
					goodsType="【"+temp.get("goodsType")+"】";
				} else {
					goodsType="【预售、抢购不使用】";
				}
				temp.put("useCondition", goodsType+"订单满"+getLowPrice(temp.get("lowPrice"))+"元使用");
				temp.put("useDate", "使用期限:"+temp.get("startline")+"至"+temp.get("deadline"));
				temp.put("receiveId", coupon.getId());
				resultList.add(temp);
			}
		}
		resultMap.put("items",resultList);
		return resultList.size();
	}
	/**
	 * 获取商品类型和金额限制
	 * @param coupon
	 * @param temp
	 */
	public void getGoodsTypeLowPrice(CouponReceive coupon,Map<String,Object> temp) {
		if(coupon.getCouponAdmin().getGoodsSelect() == null ||Constant.COMM_ALL.equals(coupon.getCouponAdmin().getGoodsSelect())){
			temp.put("goodsType","全部商品");
		}else if(Constant.COMM_FIXED.equals(coupon.getCouponAdmin().getGoodsSelect())){
			CouponReceive cReceive = couponReceiveApiConsumer.getCouponReceive(coupon.getId());
			List<String> goodsIds = cReceive.getCouponAdmin().getList();
			if(goodsIds==null || goodsIds.size()==0 ){
				temp.put("goodsType", "");
			} else {
				String str = couponReceiveApiConsumer.getCommodityName(goodsIds, coupon.getCouponAdmin().getId());
				temp.put("goodsType", str);
			}
		}else if(Constant.COMM_CATEGORY.equals(coupon.getCouponAdmin().getGoodsSelect())){
			CouponReceive cReceive = couponReceiveApiConsumer.getCouponReceive(coupon.getId());
			List<String> categoryIds = cReceive.getCouponAdmin().getList();
			if (categoryIds == null || categoryIds.size() == 0) {
				temp.put("goodsType", "");
			} else {
				String str= categoryApiConsumer.getCategoryName(categoryIds);
				temp.put("goodsType",str);
			}
		}else if("1".equals(coupon.getCouponAdmin().getGoodsSelect())){
			CouponReceive cReceive = couponReceiveApiConsumer.getCouponReceive(coupon.getId());
			List<String> activityIds = cReceive.getCouponAdmin().getList();
			if(activityIds==null || activityIds.size()==0 ){
				temp.put("goodsType", "");
			} else {
				String str = couponReceiveApiConsumer.getActivityName(activityIds);
				temp.put("goodsType", "仅限活动："+str);
			}
		}
		if(coupon.getCouponAdmin().getConsumptionLimit() ==null || -1 == coupon.getCouponAdmin().getConsumptionLimit().intValue()){
			temp.put("lowPrice","0");
		}else{
			temp.put("lowPrice",coupon.getCouponAdmin().getConsumptionLimit());
		}
	}
	//lowPriceObj是整数则取整，否则可带小数
		private Object getLowPrice(Object lowPriceObj){
			if( !(lowPriceObj instanceof BigDecimal) ){
				return new BigDecimal(lowPriceObj.toString());
			}
			BigDecimal a=(BigDecimal)lowPriceObj;
			int tempInt = a.intValue();
			BigDecimal b = new BigDecimal(tempInt);
			if(a.compareTo(b)==0){//是整数
				return tempInt;
			}else{
				return a;
			}
		}
	/**
	 * 获取商品总金额，获取所有skuId集合
	 * @param lMaps
	 * @param skuIdList
	 * @param amount
	 * @return
	 */
	private void getSkuIdsAmount(List<Map<String, Object>> lMaps,List<String> skuIdList
			,Map<String, Object> skuIdkeyValue){

		if(lMaps!=null && lMaps.size()>0){
			for (Map<String, Object> mapAmonut : lMaps) {
				if(mapAmonut.get("skuId")==null){
					throw new CallInterfaceException("skuId 不可为null");
				}
				skuIdList.add(mapAmonut.get("skuId").toString());
				Map<String, Object> message=new HashMap<String, Object>();
				message.put("num", Integer.parseInt(mapAmonut.get("buycount").toString()));
				message.put("goodsType", mapAmonut.get("goodsType"));
				message.put("activityId", mapAmonut.get("activityId"));
				skuIdkeyValue.put(mapAmonut.get("skuId").toString(), message);
			}
		}
	}
}