package com.ffzx.promotion.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.basedata.api.dto.Dict;
import com.ffzx.basedata.api.service.CodeRuleApiService;
import com.ffzx.basedata.api.service.DictApiService;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisLock;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.dto.MemberLabel;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.order.api.dto.OmsOrder;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.api.service.OrderApiService;
import com.ffzx.promotion.api.dto.AppRecommendAwards;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponAdminCouponGrant;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.dto.CouponVcode;
import com.ffzx.promotion.api.dto.RedpackageGrant;
import com.ffzx.promotion.api.dto.UserLable;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.service.consumer.MemberLableApiConsumer;
import com.ffzx.promotion.api.service.consumer.MemberApiConsumer;
import com.ffzx.promotion.constant.CouponConstant;
import com.ffzx.promotion.io.CounponGrandSelectMemberThread;
import com.ffzx.promotion.io.MemberExcelCall;
import com.ffzx.promotion.io.SenNewUserThread;
import com.ffzx.promotion.io.SendNewUserCouponThread;
import com.ffzx.promotion.mapper.AppRecommendAwardsMapper;
import com.ffzx.promotion.mapper.CouponAdminCouponGrantMapper;
import com.ffzx.promotion.mapper.CouponGrantMapper;
import com.ffzx.promotion.mapper.CouponReceiveMapper;
import com.ffzx.promotion.mapper.UserLableMapper;
import com.ffzx.promotion.service.CouponAdminCouponGrantService;
import com.ffzx.promotion.service.CouponAdminService;
import com.ffzx.promotion.service.CouponGrantMemberService;
import com.ffzx.promotion.service.CouponGrantService;
import com.ffzx.promotion.service.CouponReceiveService;
import com.ffzx.promotion.service.CouponVcodeService;
import com.ffzx.promotion.util.CodeUtils;
import com.ffzx.promotion.util.StringappUtil;

/**
 * 
 * @author ffzx
 * @date 2016-05-03 17:58:03
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("couponGrantService")
public class CouponGrantServiceImpl extends BaseCrudServiceImpl implements CouponGrantService {

	@Autowired
	private DictApiService dictApiService;
    @Resource
    private CouponGrantMapper couponGrantMapper;

    @Autowired
    private UserLableMapper userLableMapper;
    @Autowired
    private CodeRuleApiService codeRuleApiService;
    @Autowired
    private CouponGrantMemberService couponGrantMemberService;
    @Autowired
    private CouponAdminCouponGrantService couponAdminCouponGrantService;

	protected static Vector<String> phoneList ;
	protected static StringBuffer errorVaildate;//错误验证
	protected static StringBuffer successDate;//成功数据
	protected static AtomicInteger successnum;
	protected static AtomicInteger failnum;
    @Autowired
    private CouponAdminService couponAdminService;
    @Autowired
    private MemberApiService memberApiService;
    @Autowired
    private MemberApiConsumer memberApiConsumer;
   @Autowired
   private CouponReceiveService couponReceiveService;
   @Resource
   private CouponReceiveMapper couponReceiveMapper;
   @Resource
   private AppRecommendAwardsMapper appRecommendAwardsMapper;
   @Autowired
   private CouponVcodeService couponVcodeService;

   @Autowired
   private MemberLableApiConsumer memberLableApiConsumer;
	@Autowired
	private OrderApiService orderApiService;

   @Autowired
   private RedisUtil redisUtil;
    @Resource
    private CouponAdminCouponGrantMapper couponAdminCouponGrantMapper;
    
    @Override
    public CrudMapper init() {
        return couponGrantMapper;
    }

	private final String lockkey="onecall";//一次一条进程
	private final String newlockkey="onecallnew";//一次一条进程
	private final String newUserLockkey="newonecall";
	private final String newUserCoupon="newUserCoupon";

	private final int pageIndex=1;
	private final int newpagesize=50;
	private final int  newio=5;
	private final int pageSize=10;
	private final int io=5;
	private boolean  flag=true;//true为无异常
	private final long locktime=3*60*60*1000;
	private final String CouponCode="3";
	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveOrUpdateCoupon(CouponGrant couponGrant,String userList,String couponAdminList) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		boolean isdelete=false;
		boolean isadd=false;
		//参数唯一性验证
		if(couponGrant.getUrl()==null){	
			couponGrant.setUrl(Constant.NO);
		}
		if(StringUtil.isNotNull(couponGrant.getGrantDateStr())){	              
			couponGrant.setGrantDate(DateUtil.parseTime(couponGrant.getGrantDateStr())); 
		}

//		if(StringUtil.isNotNull(couponGrant.getGrantMode()) && couponGrant.getGrantMode().equals(CouponCode)){//如果是用户领取，默认已经领取
//
//			couponGrant.setIsGrant(Constant.YES);//用户领取默认已发放，不跟定时混乱
//		}else{
		if(StringUtil.isEmpty(couponGrant.getId())){//无id新增，有id修改不做操作
			couponGrant.setIsGrant(Constant.NO);
		}
//		}
		if(StringUtil.isNotNull(couponGrant.getGrantEndDateStr())){	              
			couponGrant.setGrantEndDate(DateUtil.parseTime(couponGrant.getGrantEndDateStr())); 
		}
		if(StringUtil.isNotNull(couponGrant.getId())){   //有ID则为修改
			isdelete=true;
			couponGrant.setDelFlag(null);//不更新此字段
			result=couponGrantMapper.updateByPrimaryKeySelective(couponGrant);
		}else{
			couponGrant.setId(UUIDGenerator.getUUID());
			if(StringUtil.isNotNull(couponGrant.getGrantType()) && couponGrant.getGrantType().equals(CouponCode)){//如果是用户领取，默认已经领取
				saveUserReceive(couponGrant);
				
			}
			ResultDto numberDto = codeRuleApiService.getCodeRule("prms", "prms_coupon_grant_number");

			if(!numberDto.getCode().equals(ResultDto.OK_CODE) || StringUtil.isEmpty((String)numberDto.getData()) ){
				throw new  Exception("优惠券编码生成异常，请确认是否添加  规则键值  prms_coupon_grant_number");
			}
			couponGrant.setNumber(numberDto.getData().toString());
			couponGrant.setSurplusNum(couponGrant.getGrantNum());
			couponGrant.setReceiveNum(0);
			
			result=couponGrantMapper.insertSelective(couponGrant);
		}
		if(couponGrant.getUserType()!=null){
			if(couponGrant.getUserType().equals(Constant.YES)){
				isadd=true;
			}
		}
		saveUpdateUser(couponGrant,isdelete,isadd,userList);

		couponAdminCouponGrantService.manyInsertOrdelete(couponAdminList, couponGrant.getId(), isdelete);
		
		//去更新优惠券发放总量缓存
		String key=PrmsConstant.getCouponGrantNum(couponGrant.getId());
		redisUtil.set(key, couponGrant.getGrantNum());
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
	private void saveUpdateUser(CouponGrant couponGrant,boolean isdelete,boolean isadd,String userList){
		//消息或者天降
		if((StringUtil.isNotNull(couponGrant.getGrantMode())) && (couponGrant.getGrantMode().equals(CouponConstant.CouponMessageGrantMode) || 
				couponGrant.getGrantMode().equals(CouponConstant.CouponFallingGrantMode))){
			if(isdelete){
				UserLable userLable=new UserLable();
				userLable.setCouponGrantId(couponGrant.getId());
				userLableMapper.deleteByPrimarayKeyForModel(userLable);
			}
			saveUserlable(couponGrant.getUserlableList(), couponGrant.getId());//更新会员表情
		}else{
			couponGrantMemberService.manyInsertOrdelete(userList, couponGrant.getId(), isdelete,isadd);
		}
	}
	//用户标签保存
		private void saveUserlable(String userlableList,String couponGrantId){
			List<String> userlables=StringUtil.isEmpty(userlableList)?(new ArrayList<String>())
					:Arrays.asList(userlableList.split(","));
			for (String lableid : userlables) {
				if(!StringUtil.isEmpty(lableid)){
					UserLable userLable=new UserLable(UUIDGenerator.getUUID());
					userLable.setCouponGrantId(couponGrantId);
					userLable.setLableId(lableid);
					userLableMapper.insertSelective(userLable);
				}
			}
		}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int delete(String id) {
		// TODO Auto-generated method stub
		int res=this.deleteById(id);
		boolean isdelete=true;
		couponGrantMemberService.manyInsertOrdelete(null, id, isdelete,false);
		couponAdminCouponGrantService.manyInsertOrdelete(null, id, isdelete);
		CouponVcode couponVcode=new CouponVcode();
		couponVcode.setCouponGrantId(id);
		couponVcodeService.deleteById(couponVcode);
		return res;
	}

	/**
	 * 用户领取的
	 * @param couponGrant
	 */
	private Random random=new Random();
	private void saveUserReceive(CouponGrant couponGrant){
		couponGrant.setIsGrant(Constant.YES);//用户领取默认已发放，不跟定时混乱
		for(int i=0;i<couponGrant.getGrantNum();i++){
			CouponVcode couponVcode=new CouponVcode(UUIDGenerator.getUUID(),CodeUtils.generatorNumber(random),couponGrant.getId());
			couponVcodeService.add(couponVcode);
		}
	}
	@Override
	public List<CouponGrant> findList(Page page, String orderByField, String orderBy, CouponGrant couponGrant) throws ServiceException {
		// TODO Auto-generated method stub
	    Map<String, Object> params = new HashMap<String, Object>();
		//筛选条件-名称
		if(StringUtil.isNotNull(couponGrant.getName())){
			params.put("name", couponGrant.getName());
		}//筛选条件-用户类型
		if(StringUtil.isNotNull(couponGrant.getUserType())){
			params.put("userType", couponGrant.getUserType());
		}
		//筛选 - 发放时间start
		if(StringUtil.isNotNull(couponGrant.getBeginGrantDateStr())){
			params.put("beginGrantDateStr", couponGrant.getBeginGrantDateStr());
		}
		//筛选 - 发放时间end
		if(StringUtil.isNotNull(couponGrant.getEndGrantDateStr())){
			params.put("endGrantDateStr", couponGrant.getEndGrantDateStr());
		}
		//筛选 - 发放方式
		if(StringUtil.isNotNull(couponGrant.getGrantMode())){
			params.put("grantMode", couponGrant.getGrantMode());
		}
		//筛选 - 领取状态
		if(StringUtil.isNotNull(couponGrant.getIsGrant())){
			//params.put("isGrant", couponGrant.getIsGrant());
			
			params.put("userGrant", couponGrant.getIsGrant());
		}
		//筛选 - 发放类型
		if(StringUtil.isNotNull(couponGrant.getGrantType())){
			params.put("grantType", couponGrant.getGrantType());
		}
		//筛选 - 发放编码-模糊查询
		if(StringUtil.isNotNull(couponGrant.getNumber())){
			params.put("grantNumber", couponGrant.getNumber());
		}
		return couponGrantMapper.selectByPage(page, orderByField, orderBy, params);
	}

	/**
	 * 获取优惠券管理优惠券id
	 * @return
	 */
	private List<String> getNewUserCoupon(){
		ResultDto  result =dictApiService.getDictByType(newUserCoupon);
		List<Dict> dictList=(List<Dict>) result.getData();

		List<String> list=new ArrayList<String>();
		if(dictList==null || dictList.size()==0){
			return null;
		}else{
			for (Dict dict : dictList) {
				list.add(dict.getValue());
			}
		}
		return list;
	}
	
    @Override
	@Transactional(rollbackFor=Exception.class)
	public void updateNewUserCouopon(Member newMember) throws ServiceException {
		// TODO Auto-generated method stub
    	
		List<CouponGrant> list=getCouponGrantNewUser(newMember);
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				CouponGrant grant=list.get(i);
			List<CouponAdminCouponGrant> couponGrantList=this.couponAdminCouponGrantMapper.selectByGrantId(grant.getId());
			 sendNewUserGrant(couponGrantList,newMember, grant);
			
			}
		}
	}
    @Override
	@Transactional(rollbackFor=Exception.class)
	public void updateBuyCouopon(OmsOrder omsOrder) throws ServiceException {
		// TODO Auto-generated method stub
    	StringappUtil.objectException(omsOrder, "下单信息传过来是nullomsOrder");
    	StringappUtil.listExceptionNumm(omsOrder.getDetailList(), "omsOrder里面的对象omsOrder.getDetailList()是null，导致娶不到虚拟商品id");
    	OmsOrderdetail omsOrderdetail=omsOrder.getDetailList().get(0);
    	CouponAdmin couponAdmin=couponAdminService.findById(omsOrderdetail.getVirtualGdId());//购买单个优惠券的信息
    	logger.info("是null就不是虚拟商品couponAdmin="+couponAdmin);
    	sendBuyCoupon(couponAdmin,omsOrder);//发送购买的优惠券
    	logger.info("开始取确认货");
    	ResultDto resultDto= orderApiService.confirmOrder(omsOrder.getPartnerId(), omsOrder.getId());
		if(resultDto==null || !resultDto.getCode().equals(ResultDto.OK_CODE) ){
	    	logger.info("开始取确认货error resultDto="+resultDto);
			throw new ServiceException("确认收货失败，回滚优惠券,orderApiService出异常");
		}
    	logger.info("开始取确认货success");
	}
    private void sendBuyCoupon(CouponAdmin couponAdmin,OmsOrder omsOrder){
		List<CouponReceive> couponReceives=new ArrayList<CouponReceive>();
		
    	for(int i=0;i<omsOrder.getBuyCount();i++){
    		CouponReceive receive=new CouponReceive();
    		receive.setOrderNo(omsOrder.getOrderNo());
			 receive.setId(UUIDGenerator.getUUID());
			 receive.setMemberId(omsOrder.getMemberId());
			 try{
				 Member member=memberApiConsumer.getMember(omsOrder.getMemberPhone());
				 receive.setNickName(member.getNickName());
			 }catch(RuntimeException e){
				 logger.error("",e);
				 receive.setNickName(omsOrder.getMemberName());
			 }
			 receive.setPhone(omsOrder.getMemberPhone());
			 receive.setUseState(Constant.NO);
			 receive.setCreateDate(new Date());
			 receive.setCouponAdmin(couponAdmin);
			 CouponGrant couponGrant=new CouponGrant();
			 couponGrant.setId(CouponConstant.ORDERCOUPONID);
			 receive.setCouponGrant(couponGrant);
			 receive.setReceiveDate(new Date());
			 Date effective_date_end=couponAdmin.getEffectiveDateEnd();//优惠券有效期结束时间
			 BigDecimal effective_date_num=couponAdmin.getEffectiveDateNum();//优惠券有效期天数
			 Date receiveDate=new Date();//领取时间
			 if(StringUtil.isNotNull(effective_date_end) && couponAdmin.getEffectiveDateState().equals(Constant.DATE_FIXED)){
				 receive.setOverDate(effective_date_end);
			 }
			 //领取多少天后
			 else if(StringUtil.isNotNull(effective_date_num)){
				 receive.setOverDate(DateUtil.getNextDay(receiveDate,effective_date_num.intValue()));
			 }
			 couponReceives.add(receive);
			 logger.info("购买优惠券领取id"+receive.getId());
    	}
    	couponReceiveService.insertBuyCoponUpdateUser(couponReceives);
    }

	/*
     *单个会员 发放优惠券
     */
    private void sendNewUserGrant(List<CouponAdminCouponGrant> couponGrantList,Member newMember,CouponGrant grant){
    	for (CouponAdminCouponGrant couponAdminCouponGrant : couponGrantList) {
    		List<CouponReceive> couponReceives=new ArrayList<CouponReceive>();
			List<Member> updateMembers=new ArrayList<Member>();
    			
	    		if(grant.getReceiveLimit()!=null){
	    			for(int i=0;i<grant.getReceiveLimit();i++){
							CouponReceive receive=new CouponReceive();
							 receive.setId(UUIDGenerator.getUUID());
							 receive.setMemberId(newMember.getId());
							 receive.setNickName(newMember.getNickName());
							 receive.setPhone(newMember.getPhone());
							 receive.setUseState(Constant.NO);
							 receive.setCreateDate(new Date());
							 receive.setCouponAdmin(couponAdminCouponGrant.getCouponAdmin());
							 receive.setCouponGrant(couponAdminCouponGrant.getCouponGrant());
							 receive.setReceiveDate(new Date());
							 CouponAdmin coupon=couponAdminCouponGrant.getCouponAdmin();
							 Date effective_date_end=coupon.getEffectiveDateEnd();//优惠券有效期结束时间
							 BigDecimal effective_date_num=coupon.getEffectiveDateNum();//优惠券有效期天数
							 Date receiveDate=new Date();//领取时间
							 if(StringUtil.isNotNull(effective_date_end) && coupon.getEffectiveDateState().equals(Constant.DATE_FIXED)){
								 receive.setOverDate(effective_date_end);
							 }
							 //领取多少天后
							 else if(StringUtil.isNotNull(effective_date_num)){
								 receive.setOverDate(DateUtil.getNextDay(receiveDate,effective_date_num.intValue()));
							 }
							 couponReceives.add(receive);
							 logger.info("单个会员 发放优惠券领取id"+receive.getId());
	    				}
	    			}
	    		
	    	couponReceiveService.insertUserGrantCoponUpdateUser(couponReceives, updateMembers,false);
		}
    }

    private List<CouponGrant> getCouponGrantNewUser(Member member){
    	Map<String, Object> params=new HashMap<String, Object>();
		params.put("endGrantDateStr", member.getRegisterDate());//发放时间
		params.put("beginendGrantDateStr", member.getRegisterDate());
		params.put("grantMode", CouponConstant.zero);//0是系统发放
		params.put("userType", CouponConstant.two);
		return this.couponGrantMapper.selectByPage(null,null,null,params);
    }
	@Override
	public void updateNewUserGrantDate() throws ServiceException {
		// TODO Auto-generated method stub
		logger.info("开始新用户根据发放时间发放优惠券");
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("isGrant", CouponConstant.zero);//未发放
		params.put("endGrantDateStr", new Date());//发放时间
		params.put("endendGrantDateStr", new Date());
		params.put("grantMode", CouponConstant.zero);//0是系统发放
		params.put("userType", CouponConstant.two);
		//未发放的
		
		try{
			List<CouponGrant> list=this.couponGrantMapper.selectByPage(null,null,null,params);
			if(list!=null && list.size()>0){
				if(redisUtil.tryLock(newlockkey, new Long(locktime))){
					try{
		
					logger.info("进入新用户根据发放时间发放优惠券");
					this.sendUserAdminTran(list);
					}catch(ServiceException e){
						redisUtil.remove(lockkey);
						logger.error("",e);
						throw new ServiceException(e);
					}catch(Exception e){
						redisUtil.remove(lockkey);
						logger.error("",e);
						throw new ServiceException(e);
					}finally{
						redisUtil.remove(lockkey);
					}
				}
			}
		}catch(Exception e){
			logger.error("",e);
			throw new ServiceException(e);
		}

		logger.info("结束新用户根据发放时间发放优惠券");
	}

	@Override
	public void updateNewUserGrant() throws ServiceException {
		logger.info("开始新用户发放优惠券");
		//未发放的
		try{
			List<String> list=getNewUserCoupon();//数据字段的发放管理id
			if(list==null){
				return;
			}
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("listid", list);
			params.put("delFlag", Constant.YES);
			List<CouponAdmin> couponAdmins=couponAdminService.findByBiz(params);
			if(couponAdmins!=null && couponAdmins.size()>0){
				if(redisUtil.tryLock(newUserLockkey, new Long(locktime))){
					try{
						logger.info("进入新用户发放优惠券");
						sendNewUser(couponAdmins);
					}catch(ServiceException e){
						logger.error("",e);
					}catch(Exception e){
						logger.error("",e);
						throw new ServiceException(e);
					}finally{
						redisUtil.remove(newUserLockkey);
					}
				}
			}
		}catch(Exception e){
			logger.error("",e);
			throw new ServiceException(e);
		}

		logger.info("结束新用户发放优惠券");
	}

    /**
     * 获取count数量和List数据公用的Map参数
     * @param map
     */
    private void  getCountAndDateMap(Map<String, Object> map){

    	map.put("isCoupon", Constant.NO);
    }
    private void getNewUserGrant(Map<String, Object> map){
    	getCountAndDateMap(map);
    	map.put("grantdate", new Date());
    }
    private int getNewUserCount(){
    	//系统推送（所有用户）
    	Map<String, Object> mapCount=new HashMap<String, Object>(); 
		getCountAndDateMap(mapCount);
		ResultDto resultCount=memberApiService.getMemberCount(mapCount);
		Object objectCount=resultCount.getData();
		logger.info("count 发放数量："+objectCount);
		if(objectCount==null)
			throw new  ServiceException("this.memberApiService.getMemberCount()为null");
		return Integer.parseInt(objectCount.toString());
    }
    
    private int getNewUserCouponCount(){
    	//系统推送（所有用户）
    	Map<String, Object> mapCount=new HashMap<String, Object>(); 
    	getNewUserGrant(mapCount);
		ResultDto resultCount=memberApiService.getMemberCount(mapCount);
		Object objectCount=resultCount.getData();
		logger.info("count 发放数量："+objectCount);
		if(objectCount==null)
			return 0;
		return Integer.parseInt(objectCount.toString());
    }
    private void sendNewUser(List<CouponAdmin> couponAdmins){
		ExecutorService es = null;
		try{
//			do{
				es = Executors.newFixedThreadPool(newio); 
				int count=getNewUserCount();
				Page page=new Page(pageIndex, newpagesize);
				page.setTotalCount(count);
				senCallNewUser(page,couponAdmins,es);
//			}while(getNewUserCount()!=0);
			
		}catch(Exception e){
			logger.error("",e);
		}finally{
			if(es!=null)
				es.shutdown();
		}
    }

    private void senCallNewUserCoupon(Page page,List<CouponAdminCouponGrant> couponGrantList,
    		CouponGrant grant,ExecutorService es){
    
    	List<SendNewUserCouponThread> excelCalls=new ArrayList<SendNewUserCouponThread>();//多线程集合
		List<Future<String>> fList = new ArrayList<Future<String>>();
		for(int i=1;i<=page.getPageCount();i++){
			page.setPageIndex(i);
    		Map<String, Object> memberParams=new HashMap<String, Object>(); 
			getCountAndDateMap(memberParams);
    		memberParams.put("limitstart", page.getFirstResult());
    		memberParams.put("limitend", page.getPageSize());
    		getNewUserGrant(memberParams);
    		
    		SendNewUserCouponThread sendNewUserCouponThread=null;
       	 if(StringUtil.isNotNull(couponGrantList)){
       		sendNewUserCouponThread=new SendNewUserCouponThread(memberApiService,
	    				couponGrantList,couponReceiveService
	    				,memberParams,grant);
	    		excelCalls.add(sendNewUserCouponThread); 
       	 }
		}
		for (SendNewUserCouponThread sendNewUserCouponThread : excelCalls) {
			Future<String> future=es.submit(sendNewUserCouponThread);
			fList.add(future);
		}

		for (Future<String> future : fList) {
			try {
				future.get();
			} catch (InterruptedException e) {
				flag=false;
				// TODO Auto-generated catch block
				logger.error("InterruptedException",e);
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				flag=false;
				logger.error("ExecutionException",e);
			} catch (Exception e) {
				flag=false;
				// TODO Auto-generated catch block
				logger.error("ExecutionException",e);
			}
		}
    }
    
    private void senCallNewUser(Page page,List<CouponAdmin> couponAdmins,ExecutorService es){

		List<SenNewUserThread> excelCalls=new ArrayList<SenNewUserThread>();//多线程集合
		List<Future<String>> fList = new ArrayList<Future<String>>();
		for(int i=1;i<=page.getPageCount();i++){
			page.setPageIndex(i);
    		Map<String, Object> memberParams=new HashMap<String, Object>(); 
			getCountAndDateMap(memberParams);
    		memberParams.put("limitstart", page.getFirstResult());
    		memberParams.put("limitend", page.getPageSize());
//    		memberParams.put("isNew", Constant.YES);//无需判断是否新用户，因变成新用户之前也可领取优惠券
    		SenNewUserThread senNewUserThread=null;
        	 if(StringUtil.isNotNull(couponAdmins)){
        		 senNewUserThread=new SenNewUserThread(memberApiService,couponAdmins,couponReceiveService
	    				,memberParams);
	    		excelCalls.add(senNewUserThread); 
        	 }
		}
		for (SenNewUserThread senNewUserThread2 : excelCalls) {
			Future<String> future=es.submit(senNewUserThread2);
			fList.add(future);
		}

		for (Future<String> future : fList) {
			try {
				future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error("InterruptedException",e);
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				logger.error("ExecutionException",e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("ExecutionException",e);
			}
		}
    }
	private static int istryLock=0;
	/**
	 * 使用分布式锁，因事务回滚之前线程查询数据导致数据不一致，所以把事务分离开来，另一事物没回滚之前不进入发放优惠券
	 * @throws ServiceException
	 */
	@Override
	public void sendCouponAdmin() throws ServiceException{

		logger.info("开始发放优惠券");

		Map<String, Object> params=new HashMap<String, Object>();
		params.put("isGrant", CouponConstant.zero);
		params.put("endGrantDateStr", new Date());
		params.put("grantMode", CouponConstant.zero);//0是系统发放
		params.put("userType", CouponConstant.one);//指定用户
			//未发放的

		try{
			List<CouponGrant> list=this.couponGrantMapper.selectByPage(null,null,null,params);
			if(list!=null && list.size()>0){
				if(redisUtil.tryLock(lockkey, new Long(locktime))){
					try{
		
					logger.info("进入发放优惠券");
					this.sendCouponAdminTran(list);
					}catch(ServiceException e){
						redisUtil.remove(lockkey);
						logger.error("",e);
						throw new ServiceException(e);
					}catch(Exception e){
						redisUtil.remove(lockkey);
						logger.error("",e);
						throw new ServiceException(e);
					}finally{
						redisUtil.remove(lockkey);
					}
				}
			}
		}catch(Exception e){
			logger.error("",e);
			throw new ServiceException(e);
		}

		logger.info("结束发放优惠券");
	}
	

	public void sendUserAdminTran(List<CouponGrant> list)  throws ServiceException {
		SysUser suser = new SysUser();
		suser.setName("Admin");
		if(StringUtil.isNotNull(list)){
			// 创建一个执行任务的服务
			ExecutorService es = null;
			try{
				es = Executors.newFixedThreadPool(io); 
				for(int i=0;i<list.size();i++){
					logger.info("i="+i);
					CouponGrant grant=list.get(i);
					logger.info("start");
				    	String granId=grant.getId();
				    	//获取发放的优惠券ID
				    	List<CouponAdminCouponGrant> couponGrantList=this.couponAdminCouponGrantMapper.selectByGrantId(granId);
			    		sendNewUserCoupon(grant.getId(),es,couponGrantList,grant);
//					    	if(grantMode.equals("0")){
//					    		//多线程方法，是否补偿，补偿加判断，不补偿直接插入
//					    		sendUserCoupon(grant.getId(),es,couponGrantList,grant);
//					    	}else if(grantMode.equals("2")){
//					    		this.updateMember(grant,granId,suser,couponGrantList);
//					    	}
					    }
			
			}catch(Exception e){
				logger.error("",e);
			}finally{
				if(es!=null)
					es.shutdown();
			}

		}
	}
	public void sendCouponAdminTran(List<CouponGrant> list)  throws ServiceException {
		SysUser suser = new SysUser();
		suser.setName("Admin");
		if(StringUtil.isNotNull(list)){
			// 创建一个执行任务的服务
			ExecutorService es = null;
			try{
				es = Executors.newFixedThreadPool(io); 
				for(int i=0;i<list.size();i++){
					logger.info("i="+i);
					CouponGrant grant=list.get(i);
					logger.info("start");
				    	String granId=grant.getId();
				    	//获取发放的优惠券ID
				    	List<CouponAdminCouponGrant> couponGrantList=this.couponAdminCouponGrantMapper.selectByGrantId(granId);
			    		sendSelectUserCoupon(grant.getId(),es,couponGrantList,grant);
//					    	if(grantMode.equals("0")){
//					    		//多线程方法，是否补偿，补偿加判断，不补偿直接插入
//					    		sendUserCoupon(grant.getId(),es,couponGrantList,grant);
//					    	}else if(grantMode.equals("2")){
//					    		this.updateMember(grant,granId,suser,couponGrantList);
//					    	}
					    }
			
			}catch(Exception e){
				logger.error("",e);
			}finally{
				if(es!=null)
					es.shutdown();
			}

		}
	}

	

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateMember(CouponGrant grant,String granId,SysUser suser,List<CouponAdminCouponGrant> couponGrantList) {
		// TODO Auto-generated method stub
		//指定用户（获取指定的用户ID集合）
		List<String> memberIdList=this.couponGrantMemberService.selectMemberid(granId);
		if(memberIdList==null || memberIdList.size()==0){


			 grant.setIsGrant(Constant.YES);
			 grant.setLastUpdateDate(new Date());
			 couponReceiveService.updateGrant(grant);
		}else{
			ResultDto result=memberApiService.getMemberList(memberIdList);
			List<Member>memberList=(List<Member>) result.getData();
			if(memberIdList==null || memberList.size()==0){
			    //修改发放状态

				 grant.setIsGrant(Constant.YES);
				 grant.setLastUpdateDate(new Date());
				 couponReceiveService.updateGrant(grant);
			    
			}else{
				List<CouponReceive> receiveList=new ArrayList<CouponReceive>();
				 getCouponReceiveList(suser, couponGrantList, memberList, receiveList);
				 if(receiveList!=null && receiveList.size()>0)
				 {
					 couponReceiveService.insertAllCoupon(receiveList);
				 }
				  //修改发放状态
				 grant.setIsGrant(Constant.YES);
				 grant.setLastUpdateDate(new Date());
				 couponReceiveService.updateGrant(grant);
				 //this.modifyById(grant);//couponGrantMapper.updateByPrimaryKey(grant);
			}
		}
	}
	
	private void initimport(List<String[]> listRow){
		errorVaildate=new StringBuffer();
		successDate=new StringBuffer();
		phoneList = new Vector<String>();// 比较条形码
		successnum=new AtomicInteger(0);//成功数量
		failnum=new AtomicInteger(0);//失败数量
		if(listRow.size()<=1){
			throw new RuntimeException("至少两行数据，一行标题");
		}
	}
	private String getMemberExcelCall(List<String[]> listRow) throws Exception{

		int ionum=30;
		if(listRow.size()<30){
			ionum=listRow.size();
		}
		// 创建一个执行任务的服务
		ExecutorService es = Executors.newFixedThreadPool(ionum);
		try{
			List<MemberExcelCall> excelCalls=new ArrayList<MemberExcelCall>();
//			List<Future<String>> fList = new ArrayList<Future<String>>();
		
			MemberExcelCall excelCall=null;
			for(int i=0;i<listRow.size();i++){
				excelCall=new MemberExcelCall(i, listRow.get(i), memberApiService);//这个多线程可以接受异常
				excelCalls.add(excelCall);
			}
			es.invokeAll(excelCalls);
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			es.shutdown();
			es.awaitTermination(2, TimeUnit.MINUTES);
		}
		return "成功导入"+successnum.get()+"条,失败"+failnum.get()+"条!";
	}
	private final String importlockkey="CouponGrantServiceImpl-importExcel";
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> importExcel(List<String[]> listRow) throws Exception {
		// TODO Auto-generated method stub
		
		RedisLock redisLock=new RedisLock(redisUtil, importlockkey, 30 * 1000, 30 * 1000, 2000);
		try{
			if(redisLock.acquire()){
				Map<String, Object> map=new HashMap<String, Object>();
				initimport(listRow);//初设化数据
				String message=getMemberExcelCall(listRow);
				map.put(CouponConstant.message, message);
				map.put(CouponConstant.errorVaildate,errorVaildate.toString());
				map.put(CouponConstant.date,successDate.toString());
				return map;
			}
		}catch(Exception e){
			logger.error("",e);
			throw new  Exception(e);
		}finally{
			redisLock.release();
		}
		return null;
	}
	private Integer getUserCount(){
		Map<String, Object> mapCount=new HashMap<String, Object>(); 
		ResultDto resultCount=memberApiService.getMemberCount(mapCount);
		Object objectCount=resultCount.getData();
		if(objectCount==null)
			throw new  ServiceException("this.memberApiService.getMemberCount()为null");
		int count=Integer.parseInt(objectCount.toString());
		return count;
	}
	private void sendNewUserCoupon(String grantId,ExecutorService es
			,List<CouponAdminCouponGrant> couponGrantList,CouponGrant grant){
		 int count=getNewUserCouponCount();//发放数量
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("couponGrant", grant);
		if(count>0){
			//系统推送（所有用户）
			Page page=new Page(pageIndex, pageSize);
			page.setTotalCount(count);
			
			senCallNewUserCoupon(page,couponGrantList,grant,es);
		}
		
	}
	private void sendSelectUserCoupon(String grantId,ExecutorService es
			,List<CouponAdminCouponGrant> couponGrantList,CouponGrant grant){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("couponGrant", grant);
		List<String> memberIdList=this.couponGrantMemberService.selectMemberid(grant.getId());
		if(memberIdList==null || memberIdList.size()==0){
			updateGrant( grant);
		}else{
			//系统推送（所有用户）
			int count=memberIdList.size();
			Page page=new Page(pageIndex, pageSize);
			page.setTotalCount(count);
			int pageCount=page.getPageCount();//总页数
			int count2=couponReceiveService.findCount(params);//领取表是否有数据
			logger.info(count2+" couponGrant= "+grant.getId());
			if(count2>0){//发放失败继续发放
				flag=true;//ture为无异常
	    		if(pageCount>=1){
	    			//从第二页开始再次走线程发放优惠券
	    			flag=secondPageRunThread(couponGrantList, page, pageCount,es,true,memberIdList,grant);
	    		}
				
			}else{
				flag=true;//ture为无异常.
	    		if(pageCount>=1){
	    			//从第二页开始再次走线程发放优惠券
	    			flag=secondPageRunThread(couponGrantList, page, pageCount,es,false,memberIdList,grant);
	    		}
			}
			updateGrant( grant);
		}
		
		
	}
	private void updateGrant(CouponGrant grant){
		//修改发放状态
	    grant.setIsGrant(Constant.YES);
	    if(flag)
	    {
			 grant.setIsGrant(Constant.YES);
			 grant.setLastUpdateDate(new Date());
			 couponReceiveService.updateGrant(grant);
	    }
	}
	
	/*private void sendUserCoupon(String grantId,ExecutorService es
			,List<CouponAdminCouponGrant> couponGrantList,CouponGrant grant){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("couponGrant", grant);
		//系统推送（所有用户）
		int count=getUserCount();
		Page page=new Page(pageIndex, pageSize);
		page.setTotalCount(count);
		int pageCount=page.getPageCount();//总页数
		int count2=couponReceiveService.findCount(params);//领取表是否有数据
		logger.info(count2+" couponGrant= "+grant.getId());
		if(count2>0){//发放失败继续发放
			flag=true;//ture为无异常
    		if(pageCount>=1){
    			//从第二页开始再次走线程发放优惠券
    			flag=secondPageRunThread(couponGrantList, page, pageCount,es,true,null);
    		}
			
		}else{
			flag=true;//ture为无异常.
    		if(pageCount>=1){
    			//从第二页开始再次走线程发放优惠券
    			flag=secondPageRunThread(couponGrantList, page, pageCount,es,false,null);
    		}
		}

	    //修改发放状态
	    grant.setIsGrant("1");
	    if(flag)
	    {
			 grant.setIsGrant(Constant.YES);
			 grant.setLastUpdateDate(new Date());
			 couponReceiveService.updateGrant(grant);
	    }
	}*/
	private boolean secondPageRunThread(List<CouponAdminCouponGrant> couponGrantList, Page page, int pageCount,
			ExecutorService es,boolean isException,List<String> memberIdList,CouponGrant grant)  {


		List<CounponGrandSelectMemberThread> excelCalls=new ArrayList<CounponGrandSelectMemberThread>();//多线程集合
		List<Future<String>> fList = new ArrayList<Future<String>>();
		for(int i=1;i<=pageCount;i++){
			page.setPageIndex(i);
    		List<String> memberParams=new ArrayList<String>(); 
    		int num=memberIdList.size();
    		if((i-1)*page.getPageSize()==num){
    			break;
    		}
    		int length=i*page.getPageSize();
    		if(length>num){
    			length=num;
    		}
    		memberParams.addAll(memberIdList.subList((i-1)*page.getPageSize(),length));
    		CounponGrandSelectMemberThread counponGrandMemberThread=null;
        	 if(StringUtil.isNotNull(couponGrantList)){
	    		counponGrandMemberThread=new CounponGrandSelectMemberThread(memberApiService,couponGrantList,couponReceiveService
	    				,memberParams,isException,grant);
	    		excelCalls.add(counponGrandMemberThread); 
        	 }
		}
		for (CounponGrandSelectMemberThread counponGrandMemberThread : excelCalls) {
			Future<String> future=es.submit(counponGrandMemberThread);
			fList.add(future);
		}

		for (Future<String> future : fList) {
			try {
				future.get();
			} catch (InterruptedException e) {
				flag=false;
				// TODO Auto-generated catch block
				logger.error("InterruptedException",e);
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				flag=false;
				logger.error("ExecutionException",e);
			} catch (Exception e) {
				flag=false;
				// TODO Auto-generated catch block
				logger.error("ExecutionException",e);
			}
		}
		return flag;
	}

	private void getCouponReceiveList(SysUser suser, List<CouponAdminCouponGrant> couponGrantList,
			List<Member> memberList, List<CouponReceive> receiveList) {
		for(Member user:memberList){
			 for(CouponAdminCouponGrant cougrant:couponGrantList){
				 CouponReceive receive=new CouponReceive();
				 receive.setId(UUIDGenerator.getUUID());
				 receive.setMemberId(user.getId());
				 receive.setCreateBy(suser);
				 receive.setNickName(user.getNickName());
				 receive.setPhone(user.getPhone());
				 receive.setUseState("0");
				 receive.setCreateDate(new Date());
				 receive.setReceiveDate(new Date());
				 receive.setCouponAdmin(cougrant.getCouponAdmin());
				 receive.setCouponGrant(cougrant.getCouponGrant());
				 CouponAdmin coupon=cougrant.getCouponAdmin();
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
				 receiveList.add(receive);
			 }
		 }
	}



private void getCouponReceiveList(List<Member> memberList,List<CouponAdminCouponGrant> couponGrantList,List<CouponReceive> receiveList) {
	for(Member user:memberList){
		 for(CouponAdminCouponGrant cougrant:couponGrantList){
			 CouponReceive receive=new CouponReceive();
			 receive.setId(UUIDGenerator.getUUID());
			 receive.setMemberId(user.getId());
			 receive.setNickName(user.getNickName());
			 receive.setPhone(user.getPhone());
			 receive.setUseState("0");
			 receive.setCreateDate(new Date());
			 receive.setCouponAdmin(cougrant.getCouponAdmin());
			 receive.setCouponGrant(cougrant.getCouponGrant());
			 receive.setReceiveDate(new Date());
			 CouponAdmin coupon=cougrant.getCouponAdmin();
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
			 receiveList.add(receive);
		 }
	 }
}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveOrUpdateRecommend(AppRecommendAwards appRecommendAwards) throws Exception {
		int result = 0;
		// 编辑
		if (StringUtil.isNotNull(appRecommendAwards.getId())) {
			result = appRecommendAwardsMapper.updateByPrimaryKeySelective(appRecommendAwards);
		} else {
			appRecommendAwards.setId(UUIDGenerator.getUUID());
			result = appRecommendAwardsMapper.insertSelective(appRecommendAwards);
		}
		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	public List<CouponGrant> findDataPicker(Page page, String grantStr) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		//筛选条件-名称
		if(StringUtil.isNotNull(grantStr)){
			params.put("grantStr", grantStr);
		}
		// 方式为用户领取
		params.put("grantMode", Constant.GRANT_USERREC);
		// 用户类型为所有用户(0所有用户，1指定用户，2新用户)
		params.put("userType", "0");
		// 发放类型为优惠券(0普通--优惠券，1注册，2分享，3优惠码)
		params.put("grantType", "0");
		// 发放状态为 非结束
		params.put("endDate", new Date());
		// 未删除的
		params.put("delFlag", Constant.DELTE_FLAG_NO);
		
		return couponGrantMapper.selectByPage(page, null, null, params);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateGrantCoupon(CouponGrant couponGrant) {
		// TODO Auto-generated method stub
		couponGrantMapper.updateByPrimaryKeySelective(couponGrant);
	}
	@Override
	public CouponGrant findBydetail(String id) {
		// TODO Auto-generated method stub
		CouponGrant couponGrant=couponGrantMapper.selectByPrimaryKey(id);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("couponGrantId", id);
		setUserLable(couponGrant, params);//新增用吧标签
		return couponGrant;
	}
	private void setUserLable(CouponGrant couponGrant,Map<String, Object> params){
		List<UserLable> userLables=new ArrayList<UserLable>();
		List<String> userLableStrings=userLableMapper.selectByParamStrings(params);
		List<String> returnuserLableStrings=new ArrayList<String>();
		if(userLableStrings==null || userLableStrings.size()==0){
			return;
		}
		List<MemberLabel> memberLabels=memberLableApiConsumer.selectLabelByParams(userLableStrings);
		if(memberLabels!=null &&  memberLabels.size()>0){
			for (MemberLabel memberLabel : memberLabels) {
				returnuserLableStrings.add(memberLabel.getId());
				UserLable userLable=new UserLable(memberLabel.getId(), 
						 memberLabel.getLabelName(), memberLabel.getLabelCode());
				userLables.add(userLable);
			}
		}
		if(returnuserLableStrings==null || returnuserLableStrings.size()==0){
			return;
		}

		couponGrant.setUserlableList((returnuserLableStrings!=null && returnuserLableStrings.size()>0)
				?returnuserLableStrings.toString().substring(1, returnuserLableStrings.toString().length()-1).replace(" ", "")+",":"");
		couponGrant.setUserLables(userLables);
	}

}






