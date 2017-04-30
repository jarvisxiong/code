package com.ffzx.promotion.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.ActivityPreSaleTag;
import com.ffzx.promotion.mapper.ActivityPreSaleTagMapper;
import com.ffzx.promotion.model.PreSaleTag;
import com.ffzx.promotion.service.ActivityPreSaleTagService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ffzx
 * @date 2016-08-17 17:55:51
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("activityPreSaleTagService")
public class ActivityPreSaleTagServiceImpl extends BaseCrudServiceImpl implements ActivityPreSaleTagService {

    @Resource
    private ActivityPreSaleTagMapper activityPreSaleTagMapper;

    @Override
    public CrudMapper init() {
        return activityPreSaleTagMapper;
    }

	@Override
	public List<ActivityPreSaleTag> findList(Page pageObj, String orderByField, String orderBy,Map<String, Object> params)
			throws ServiceException {
		return this.activityPreSaleTagMapper.selectByPage(pageObj, orderByField, orderBy, params);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode saveAndUpdatePreSaleTag(ActivityPreSaleTag tag) throws ServiceException {
		int result=0;
		try {			
			if(StringUtils.isNotEmpty(tag.getId())){
				/*int number=tag.getNumber();//排序号				
				//编辑的时候查询该排序号是否已经被现有标签占用
				ActivityPreSaleTag oldTag=this.activityPreSaleTagMapper.getTagByNumber(String.valueOf(number));
				if(oldTag!=null){
					String oldNumber=tag.getOldNumber();//需要编辑的排序号
					//如果占用就需要2个标签排序号互换 修改
					oldTag.setNumber(Integer.valueOf(oldNumber));
					this.activityPreSaleTagMapper.updateByPrimaryKeySelective(oldTag);
				}*/
				result=this.activityPreSaleTagMapper.updateByPrimaryKeySelective(tag);
			}else{
				tag.setCreateDate(new Date());
				tag.setId(UUIDGenerator.getUUID());
				result=this.activityPreSaleTagMapper.insertSelective(tag);
			}
		} catch (Exception e) {
			logger.error("预售标签设置"+ e);
			throw new ServiceException(e);
		}
		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServiceCode deletePreSaleTag(String id) throws ServiceException {
		int result=0;
		try {
			result=this.activityPreSaleTagMapper.deleteById(id);
		} catch (Exception e) {
			logger.error("预售标签删除"+ e);
			throw new ServiceException(e);
		}		
		return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
	}

	@Override
	public List<ActivityPreSaleTag> findPresaleTagByNumberOrName(PreSaleTag tag) throws ServiceException {
		return this.activityPreSaleTagMapper.selectByNameOrId(tag);
	}
}