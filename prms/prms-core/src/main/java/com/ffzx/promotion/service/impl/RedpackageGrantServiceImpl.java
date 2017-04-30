package com.ffzx.promotion.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.basedata.api.service.CodeRuleApiService;
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
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.member.api.dto.MemberLabel;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.api.dto.RedpackageGrant;
import com.ffzx.promotion.api.dto.RedpackageHandle;
import com.ffzx.promotion.api.dto.UserLable;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.service.consumer.MemberLableApiConsumer;
import com.ffzx.promotion.exception.BussinessException;
import com.ffzx.promotion.mapper.RedpackageGrantMapper;
import com.ffzx.promotion.mapper.UserLableMapper;
import com.ffzx.promotion.service.RedpackageGrantService;
import com.ffzx.promotion.service.RedpackageHandleService;
import com.ffzx.promotion.service.RedpackageService;
import com.ffzx.promotion.util.AppMapUtils;

/**
 * 
 * @author ffzx
 * @date 2016-11-07 09:41:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("redpackageGrantService")
public class RedpackageGrantServiceImpl extends BaseCrudServiceImpl implements RedpackageGrantService {

    @Resource
    private RedpackageGrantMapper redpackageGrantMapper;


    @Autowired
    private RedpackageService redpackageService;
    @Autowired
    private UserLableMapper userLableMapper;
	@Autowired
	private CodeRuleApiService codeRuleApiService;
    @Autowired
    private RedpackageHandleService redpackageHandleService;
    @Autowired
    private MemberLableApiConsumer memberLableApiConsumer;
    
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public CrudMapper init() {
        return redpackageGrantMapper;
    }

	@Override
	public List<RedpackageGrant> findList(Page pageObj, RedpackageGrant redpackageGrant) {
		// TODO Auto-generated method stub
	    Map<String, Object> params = AppMapUtils.toMapObjcet(redpackageGrant);
		return init().selectByPage(pageObj, PrmsConstant.NUMBER, Constant.ORDER_BY, params);
	}

	@Override
	@Transactional
	public ServiceCode saveOrUpdate(RedpackageGrant redpackageGrant) throws ServiceException {
		// TODO Auto-generated method stub
		String trylock="RedpackageGrantServiceImpl"+redpackageGrant.getRedpackageId();
		if(redisUtil.tryLock(trylock, 5000)){
			try{
				logger.info(redpackageGrant.getId()+"saveOrUpdateXXXXX====="+redpackageGrant.getState()+" end:"+redpackageGrant.getEndDate()+"userLable"+redpackageGrant.getUserlableList());
				int result = 0;
				if(StringUtil.isNotNull(redpackageGrant.getId())){   //有ID则为修改
					redpackageGrant.setDelFlag(null);
					result=init().updateByPrimaryKeySelective(redpackageGrant);
					UserLable userLable=new UserLable();
					userLable.setRedpackageGrantId(redpackageGrant.getId());
					userLableMapper.deleteByPrimarayKeyForModel(userLable);
					saveUserlable(redpackageGrant.getUserlableList(), redpackageGrant.getId());//更新会员表情
					savehandle(redpackageGrant,PrmsConstant.UPDATE, redpackageGrant.getLastUpdateName());
				}else{
					ResultDto rsDto=codeRuleApiService.getCodeRule("prms", "prms_hbff_number");
					String code=(String)rsDto.getData();
					Redpackage redpackage=redpackageService.findById(redpackageGrant.getRedpackageId());
					logger.info("红包创建选择"+redpackage.getId()+" isgrant"+redpackage.getIsGrant());
					if(StringUtil.isEmpty(code)){
						throw new BussinessException("生成红包发放编码失败");
					}else if(redpackage.getIsGrant().equals(Constant.YES)){
						throw new BussinessException("请重新选择红包，该红包已经被选择");
					}else if(redpackage.getDelFlag().equals(Constant.YES)){
						throw new BussinessException("请重新选择红包，该红包已经被删除");
					}else if(redpackage.getEndDate()!=null && redpackageGrant.getEndDate()!=null && DateUtil.diffDateTime(redpackageGrant.getEndDate(),redpackage.getEndDate())>0){
						throw new BussinessException("领取截止时间必须小于红包有效期截止时间");
					}
					redpackageGrant.setNumber(code);
					redpackageGrant.setId(UUIDGenerator.getUUID());
					savehandle(redpackageGrant, PrmsConstant.ADD, redpackageGrant.getLastUpdateName());
					result=init().insertSelective(redpackageGrant);
					updateredpackageisGrant(redpackageGrant.getRedpackageId(), Constant.YES);//更新红包是否发放
					saveUserlable(redpackageGrant.getUserlableList(), redpackageGrant.getId());//更新会员表情
				}
				return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
			}catch(BussinessException e){

				throw new BussinessException(e.getErrorMsg());
			}
			catch(RuntimeException e){
				throw new RuntimeException(e);
			}finally{
			redisUtil.remove(trylock);
			}
		}else{
			throw new BussinessException("保存失败");
		}
	}
	//用户标签保存
	private void saveUserlable(String userlableList,String redpackageGrantId){
		List<String> userlables=StringUtil.isEmpty(userlableList)?(new ArrayList<String>())
				:Arrays.asList(userlableList.split(","));
		for (String lableid : userlables) {
			if(!StringUtil.isEmpty(lableid)){
				UserLable userLable=new UserLable(UUIDGenerator.getUUID());
				userLable.setRedpackageGrantId(redpackageGrantId);
				userLable.setLableId(lableid);
				userLableMapper.insertSelective(userLable);
			}
		}
	}
	private void updateredpackageisGrant(String redpackageId,String isGrant){
		Redpackage redpackage=new Redpackage(redpackageId);
		redpackage.setIsGrant(isGrant);
		redpackageService.modifyById(redpackage);
	}
	private void savehandle(RedpackageGrant redpackageGrant,String  state,String createname){
		RedpackageHandle redpackageHandle=new RedpackageHandle();
		redpackageHandle.setId(UUIDGenerator.getUUID());
		redpackageHandle.setHandleDate(new Date());
		redpackageHandle.setHandleMessage(state);
		redpackageHandle.setHandleName(createname);
		redpackageHandle.setRedpackageGrantId(redpackageGrant.getId());
		redpackageHandleService.add(redpackageHandle);
	}
	@Override
	public RedpackageGrant findBydetail(String id,boolean isHandleInfo) {
		// TODO Auto-generated method stub
		RedpackageGrant redpackageGrant=init().selectByPrimaryKey(id);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("redpackageGrantId", id);
		if(isHandleInfo){//ture查处理信息
			params.put("orderByField", "handle_date");
			params.put("orderBy", Constant.ORDER_BY);
			List<RedpackageHandle> list=redpackageHandleService.findByBiz(params);
			redpackageGrant.setRedpackageHandles(list);
			params.clear();
			params.put("redpackageGrantId", id);//查询用户标签
		}

		Map<String, Object> paramsRedpackage=new HashMap<String, Object>();
		paramsRedpackage.put("id", redpackageGrant.getRedpackageId());//查询红发管理数据
		List<Redpackage> redpackages=redpackageService.findByBiz(paramsRedpackage);
		Redpackage redpackage=(redpackages!=null && redpackages.size()>0)?redpackages.get(0):new Redpackage();
		redpackageGrant.setRedpackage(redpackage);
		redpackageGrant.setRedpackageId(redpackage.getId());
		setUserLable(redpackageGrant, params);//新增用吧标签
		return redpackageGrant;
	}
	private void setUserLable(RedpackageGrant redpackageGrant,Map<String, Object> params){
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
		redpackageGrant.setUserlableList((returnuserLableStrings!=null && returnuserLableStrings.size()>0)
				?returnuserLableStrings.toString().substring(1, returnuserLableStrings.toString().length()-1).replace(" ", "")+",":"");
		redpackageGrant.setUserLables(userLables);
	}
	private void  lastUpdateMember(RedpackageGrant redpackage){
		SysUser sysUser = RedisWebUtils.getLoginUser();
		redpackage.setLastUpdateBy(sysUser);
		redpackage.setLastUpdateName(sysUser.getName());
		redpackage.setLastUpdateDate(new Date());
	}
	@Override
	@Transactional
	public ServiceCode updateState(RedpackageGrant redpackageGrant) {
		// TODO Auto-generated method stub
		int result = 0;
		lastUpdateMember(redpackageGrant);
		
		savehandle(redpackageGrant, redpackageGrant.getState().equals(Constant.NO)
				?PrmsConstant.Disable:PrmsConstant.Enable, redpackageGrant.getLastUpdateName());
		result=init().updateByPrimaryKeySelective(redpackageGrant);//更新
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ServiceCode updateRepackageGrant(RedpackageGrant redpackageGrant) {
		// TODO Auto-generated method stub
		logger.info("updateRepackageGrant"+redpackageGrant.getId()+" "+redpackageGrant.getIsGrant());
		int result=init().updateByPrimaryKeySelective(redpackageGrant);
		logger.info(result+" ");
		return null;
	}

	@Override
	@Transactional
	public ServiceCode deleteRedpackage(RedpackageGrant redpackageGrant) {
		// TODO Auto-generated method stub
		int result = 0;
		lastUpdateMember(redpackageGrant);
		savehandle(redpackageGrant, PrmsConstant.DELETE, redpackageGrant.getLastUpdateName());
		result=init().updateByPrimaryKeySelective(redpackageGrant);//更新
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
}