/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 * 
 */
package com.ffzx.bi.common.cache;

import org.springframework.stereotype.Service;

import com.ff.common.cache.FFAbstractCache;
import com.ffzx.bi.common.constant.RptTimeDimEnum;
import com.ffzx.bi.common.model.RptDim;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月10日
 */
@Service
public class RptDimCache extends FFAbstractCache<String,RptDim>
{

	/* (non-Javadoc)
	 * @see com.fuego.common.cache.FFAbstractCache#load()
	 */
	@Override
	public void load()
	{
 		for(RptTimeDimEnum e : RptTimeDimEnum.values())
 		{
 			add(e);
 		}
 	}
	
	private void add(RptTimeDimEnum dimEnum)
	{
		RptDim dim = new RptDim();
		dim.setDim(dimEnum.getDim());
		dim.setField_name(dimEnum.getField_name());
 		dim.setDatasource(dimEnum.getDatasource());
		dim.setSupplement(true);
		dim.setName(dimEnum.getName());
		super.add(dimEnum.getDim(), dim);
	}

}
