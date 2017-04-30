/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 * 
 */
package com.ffzx.bi.common.service.axis;

import java.util.List;

import com.ff.common.dao.model.FFCondition;
import com.ff.common.service.BaseService;
import com.ffzx.bi.common.model.RptDim;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 */
public interface RptAxisDataSource
{
	public List<?> getXAxisData(RptDim dim,List<FFCondition> conditionList,BaseService service);
 
	public String getDimMapField(RptDim dim);

}
