package com.ffzx.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.promotion.api.dto.AdvertRegion;
import com.ffzx.promotion.mapper.AdvertRegionMapper;
import com.ffzx.promotion.service.AdvertRegionService;

/**
 * @className AdvertRegionServiceImpl
 *
 * @author hyl
 * @date 2016-05-03 14:48:15
 * @version 1.0.0
 */
@Service("advertRegionService")
public class AdvertRegionServiceImpl extends BaseCrudServiceImpl implements AdvertRegionService {

	@Resource
	private AdvertRegionMapper advertRegionMapper;
	
	@Override
	public CrudMapper init() {
		return advertRegionMapper;
	}

	
	
	/**
	 * 保存机构
	 * 
	 */
	  @Transactional(rollbackFor=Exception.class)
		public ServiceCode save(AdvertRegion data) throws ServiceException {
			int result = 0;
			String id = data.getId();
			//设置parentIds
			if(!StringUtil.isNotNull(data.getParent().getId())){
				data.getParent().setId("0");
			}
			AdvertRegion parentData = this.findById(data.getParent().getId());
			String parentIds = "";
			if(StringUtil.isNotNull(parentData)){
				parentIds = parentData.getParentIds() + data.getParent().getId() + ",";
			}else{
				parentIds = "0,";
			}
			data.setParentIds(parentIds);
			
			// 有Id为修改
			if (StringUtil.isNotNull(data.getId())) {
				AdvertRegion oldData = this.findById(data.getId());// 旧的数据
				data.setLastUpdateDate(new Date());
				result = advertRegionMapper.updateByPrimaryKeySelective(data);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("like_parentIds",data.getId() );
				// 维护所有子集的ids
				List<AdvertRegion> list = advertRegionMapper.selectByParams(params);
				if(StringUtil.isNotNull(list)){
					for(AdvertRegion item : list){
						item.setParentIds(item.getParentIds().replace(oldData.getParentIds(), data.getParentIds()));
						item.setDelFlag(null);
						advertRegionMapper.updateByPrimaryKeySelective(item);
					}
				}
			} else {
				id = UUIDGenerator.getUUID();
				data.setId(id);
				data.setCreateDate(new Date());
				result = advertRegionMapper.insertSelective(data);
			}
			return result > 0 ? ServiceResultCode.SUCCESS : ServiceResultCode.FAIL;
		}

	@Override
	public List<Object> getAdvertRegionSimpleTree(Map<String, Object> params) throws ServiceException {
		// TODO Auto-generated method stub
		List<AdvertRegion> dataGroup = this.findByBiz(params);
		List<Object> listResult = new ArrayList<Object>();
		listResult = dataToSimpleTreeMap(dataGroup, listResult);
		return listResult;
	}	
	
	/**
	 * 格式化树形
	 * @param dataGroup
	 * @param listResult
	 * @return
	 */
	public List<Object> dataToSimpleTreeMap(List<AdvertRegion> dataGroup, List<Object> listResult) {
		for (AdvertRegion data : dataGroup) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", data.getId());
			row.put("name", data.getName());
			row.put("pId", data.getParentId());
			row.put("pIds", data.getParentIds());
			row.put("open", true);
			listResult.add(row);
		}
		return listResult;
	}
}