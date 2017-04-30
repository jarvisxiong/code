package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.ChoiceShareOrder;
import com.ffzx.promotion.mapper.ChoiceShareOrderMapper;
import com.ffzx.promotion.service.ChoiceShareOrderService;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ffzx
 * @date 2016-10-24 11:24:09
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("choiceShareOrderService")
public class ChoiceShareOrderServiceImpl extends BaseCrudServiceImpl implements ChoiceShareOrderService {

    @Resource
    private ChoiceShareOrderMapper choiceShareOrderMapper;

    @Override
    public CrudMapper init() {
        return choiceShareOrderMapper;
    }

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveChoiceOrder(ChoiceShareOrder choice) throws ServiceException {
		int result=0;
		try {
			SysUser user=RedisWebUtils.getLoginUser();
			if(StringUtils.isNotEmpty(choice.getId())){
				//编辑
				choice.setCreateName(user.getName());
				choice.setLastUpdateBy(user);
				choice.setCreateBy(user);
				choice.setUpdateDate(new Date());
				result=this.choiceShareOrderMapper.updateByPrimaryKeySelective(choice);
			}else{
				//新增
				choice.setId(UUIDGenerator.getUUID());
				choice.setCreateBy(user);
				choice.setCreateDate(new Date());
				choice.setCreateName(user.getName());
				choice.setLastUpdateBy(user);
				choice.setUpdateDate(new Date());
				result=this.choiceShareOrderMapper.insertSelective(choice);
			}
		} catch (ServiceException e) {
			logger.error("精选晒单编辑", e);
			throw e;
		}
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}
}