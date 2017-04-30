package com.ffzx.promotion.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.api.dto.RedpackageHandle;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.exception.BussinessException;
import com.ffzx.promotion.mapper.RedpackageMapper;
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
@Service("redpackageService")
public class RedpackageServiceImpl extends BaseCrudServiceImpl implements RedpackageService {

    @Resource
    private RedpackageMapper redpackageMapper;
    
    @Autowired
    private RedpackageHandleService redpackageHandleService;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public CrudMapper init() {
        return redpackageMapper;
    }

	@Autowired
	private CodeRuleApiService codeRuleApiService;
	@Override
	public List<Redpackage> findList(Page pageObj, Redpackage redpackage) {
		// TODO Auto-generated method stub
		if(!StringUtil.isEmpty(redpackage.getState())){
			redpackage.setState(redpackage.getState().replace(",", ""));
		}
	    Map<String, Object> params = AppMapUtils.toMapObjcet(redpackage);
		return redpackageMapper.selectByPage(pageObj, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY, params);
	}

	@Override
	public Redpackage findBydetail(String id) {
		// TODO Auto-generated method stub
		Redpackage redpackage=redpackageMapper.selectByPrimaryKey(id);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("redpackageId", id);
		params.put("orderByField", "handle_date");
		params.put("orderBy", Constant.ORDER_BY);
		List<RedpackageHandle> list=redpackageHandleService.findByBiz(params);
		redpackage.setRedpackageHandles(list);
		return redpackage;
	}

	@Override
	@Transactional
	public ServiceCode saveOrUpdate(Redpackage redpackage) throws ServiceException {
		// TODO Auto-generated method stub
		int result = 0;
		if(StringUtil.isNotNull(redpackage.getId())){   //有ID则为修改
			redpackage.setDelFlag(null);
			redpackage.setDateStartupdate(redpackage.getDateStart());
			if(redpackage.getDateStart().equals(PrmsConstant.RECEIVEEFFECTIVE))//领取后有效
			{
				redpackage.setStartDate(null);
				redpackage.setEndDate(null);
			}else if (redpackage.getDateStart().equals(PrmsConstant.CUSTEMEFFECTIVE)){//自定义
				redpackage.setReceivedate(null);
			}
			result=redpackageMapper.updateByPrimaryKeySelective(redpackage);
			
			savehandle(redpackage,PrmsConstant.UPDATE, redpackage.getLastUpdateName());
		}else{
			ResultDto rsDto=codeRuleApiService.getCodeRule("prms", "prms_hbcj_number");
			String code=(String)rsDto.getData();
			if(StringUtil.isEmpty(code)){
				throw new RuntimeException("生成红包编码失败");
			}
			redpackage.setNumber(code);
			redpackage.setId(UUIDGenerator.getUUID());
			savehandle(redpackage, PrmsConstant.ADD, redpackage.getLastUpdateName());
			result=redpackageMapper.insertSelective(redpackage);
			
		}
		//去更新红包发放总量缓存
		String key=PrmsConstant.getRedpackageGrantNum(redpackage.getId());
		redisUtil.set(key, redpackage.getGrantNum());
		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
	
	private void savehandle(Redpackage redpackage,String  state,String createname){
		RedpackageHandle redpackageHandle=new RedpackageHandle();
		redpackageHandle.setId(UUIDGenerator.getUUID());
		redpackageHandle.setHandleDate(new Date());
		redpackageHandle.setHandleMessage(state);
		redpackageHandle.setHandleName(createname);
		redpackageHandle.setRedpackageId(redpackage.getId());
		redpackageHandleService.add(redpackageHandle);
	}
	private void  lastUpdateMember(Redpackage redpackage){
		SysUser sysUser = RedisWebUtils.getLoginUser();
		redpackage.setLastUpdateBy(sysUser);
		redpackage.setLastUpdateName(sysUser.getName());
		redpackage.setLastUpdateDate(new Date());
	}
	@Override
	@Transactional
	public ServiceCode updateState(Redpackage redpackage) {
		// TODO Auto-generated method stub
		int result = 0;
		lastUpdateMember(redpackage);
		Redpackage redpackage2=redpackageMapper.selectByPrimaryKey(redpackage.getId());
		if(redpackage2.getIsGrant().equals(Constant.YES)){
			throw new BussinessException("禁用失败，原因：当前该红包已被加入【红包发放管理】，请先从【红包发放管理】删除，请再试");
		}else if(redpackage2.getDelFlag().equals(Constant.YES)){
			throw new BussinessException("已删除");
		}else if(redpackage2.getState().equals(Constant.YES)){
			throw new BussinessException("已启用不能更改状态");
		}
		savehandle(redpackage2, redpackage.getState().equals(Constant.NO)
				?PrmsConstant.Disable:PrmsConstant.Enable, redpackage.getLastUpdateName());
		result=redpackageMapper.updateByPrimaryKeySelective(redpackage);//更新
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	@Transactional
	public ServiceCode deleteRedpackage(Redpackage redpackage) {
		// TODO Auto-generated method stub
		int result = 0;
		lastUpdateMember(redpackage);
		Redpackage redpackage2=redpackageMapper.selectByPrimaryKey(redpackage.getId());
		if(redpackage2.getIsGrant().equals(Constant.YES)){
			throw new BussinessException("删除失败，原因：当前该红包已被加入【红包发放管理】，请先从【红包发放管理】删除，请再试");
		}else if(redpackage2.getState().equals(Constant.YES)){
			throw new BussinessException("已启用不能删除");
		}
		savehandle(redpackage2, PrmsConstant.DELETE, redpackage.getLastUpdateName());
		redpackage.setIsGrant(Constant.NO);
		result=redpackageMapper.updateByPrimaryKeySelective(redpackage);//更新
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
}