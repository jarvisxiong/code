/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 * 
 */
package com.ffzx.bi.common.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ff.common.cache.FFAbstractCache;
import com.ff.common.dao.model.ConditionTypeEnum;
import com.ff.common.dao.model.FFCondition;
import com.ff.common.service.BaseService;
import com.ff.common.service.model.FFExcelMeta;
import com.ff.common.util.log.FFLogFactory;
import com.ff.common.util.meta.ReflectionUtil;
import com.ff.common.util.spring.SpringContextUtil;
import com.ff.common.util.validate.ValidatorUtil;
import com.ff.common.web.json.FFSysUser;
import com.ffzx.bi.common.constant.RptTimeDimEnum;
import com.ffzx.bi.common.model.RptCalc;
import com.ffzx.bi.common.model.RptCalcAnno;
import com.ffzx.bi.common.model.RptDataRspJson;
import com.ffzx.bi.common.model.RptDim;
import com.ffzx.bi.common.model.RptDimDataJson;
import com.ffzx.bi.common.model.RptIndicator;
import com.ffzx.bi.common.model.RptIndicatorAnno;
import com.ffzx.bi.common.service.axis.RptAxisDataSource;
import com.ffzx.bi.common.service.calc.RptCalcService;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月9日
 */
@Service
@Scope("prototype")
public class RptBaseService<E> extends BaseService<E>
{
	
	private Logger log = FFLogFactory.getLog(getClass());
	@Autowired
	private FFAbstractCache<String,RptDim> dimCache;
	
	@Autowired
	private FFAbstractCache<String,RptIndicator>  indicatorCache;
	
	public E loadSum(FFSysUser user,List<RptIndicator> indicatorList,List<FFCondition> conditionList)
	{
		
		// check repeat indicator
		Set<RptIndicator> indicatorSet = new HashSet<RptIndicator>();
		List<RptIndicator> indiList = new ArrayList<RptIndicator>();
 		for(RptIndicator e : indicatorList)
 		{
  			RptIndicator indicator = getIndicator(e.getIndicator());
			if(null == indicator)
			{
				log.warn("the indicator have not been config " + e);
				continue;
			}
			if(!indicatorSet.contains(e))
			{
				indiList.add(indicator);
				indicatorSet.add(indicator);
			}
			
 		}
 		
 		E sum = this.getAllDataBySum(user,indiList, conditionList);
		
		
		return sum;
	}
	
	/**
	 * load rpt data by dim and indicator information with condition
	 * @param user
	 * @param dimList
	 * @param conditionList
	 * @return
	 */
	public RptDataRspJson loadRpt(FFSysUser user,List<RptDimDataJson> dimList,List<FFCondition> conditionList)
	{
		RptDataRspJson rsp = new RptDataRspJson();
		
		if(ValidatorUtil.isEmpty(dimList))
		{
			log.error("there is no dim and indicator data");
			return rsp;
		}
  
		// use set remove repeat dim and indicator
  		Map<RptDim,List<RptIndicator>> dimMapIndiSet = getDimIndicator(dimList);

		
		Map<RptDim,List<?>> dimMapXData = new HashMap<RptDim,List<?>>();
		Map<RptDim,List<?>> dimMapYAllData = new HashMap<RptDim,List<?>>();

		//get indicator data from database
		for(RptDim dim : dimMapIndiSet.keySet())
		{
  			//filter condition
			List<E> dataFromDB = getAllDataByDim(user, dim,dimMapIndiSet.get(dim),conditionList);
			List<E> yAllData = new ArrayList<E>();
			List<?> xdata;
			//是否要补充数据
			if(dim.isSupplement())
			{
				RptAxisDataSource axisService = (RptAxisDataSource) SpringContextUtil.getBean(dim.getDatasource());
 
				xdata = axisService.getXAxisData(dim,conditionList,this);
 		 
  				Map<Object,E> origin = list2TimeMap(dim,dataFromDB);
    				
				for(Object obj :xdata)
				{
					E e = origin.get(obj);
					if(null == e)
					{
						e = (E) ReflectionUtil.newInstance(this.getEntityClass());
						ReflectionUtil.setObjectField(e, dim.getField_name(), obj);
		 			}
					yAllData.add(e);
				}
 			}
			else
			{
				yAllData = dataFromDB;
				xdata =  ReflectionUtil.getListByFieldName(dataFromDB,dim.getField_name());
			}
			//handle  ratio calc
			calcWithRatio(user, dim,  dimMapIndiSet.get(dim),conditionList, yAllData);
			
			dimMapXData.put(dim, xdata);
			dimMapYAllData.put(dim, yAllData);
			rsp.getTable_data().put(dim.getDim(), yAllData);
		}
		
 		for(RptDimDataJson dimData : dimList)
		{
 			RptDim dim = new RptDim();
 			dim.setDim(dimData.getDim());
  			List<RptIndicator> indiList = dimMapIndiSet.get(dim); 
 			if(null == indiList)
			{
				continue;
			}
 			RptIndicator indicator = new RptIndicator();
 			indicator.setIndicator(dimData.getIndicator());
  			if(!indiList.contains(indicator))
 			{
 				continue;
 			}
  			else
  			{
  				for(RptIndicator indi : indiList)
  				{
  					if(indi.getIndicator().equals(indicator.getIndicator()))
  					{
  						indicator = indi;
  	  					 break;
  					}
  					
  				}
  			}
  			
			dimData.setX_data(dimMapXData.get(dim));
			List<?> ydata = ReflectionUtil.getListByFieldName(dimMapYAllData.get(dim),indicator.getField_name());
			dimData.setY_data(ydata);
			dimData.setY_name(indicator.getName());
			
			rsp.getChart_data().add(dimData);
		}
		
  		return rsp;
	}
	
	public List<FFExcelMeta> getExportMeta(List<RptDimDataJson> dimList)
	{
		List<FFExcelMeta> excelMetaList = new ArrayList<FFExcelMeta>();

		if(ValidatorUtil.isEmpty(dimList))
		{
			log.warn("the rpt dim information is empty");
			return excelMetaList;
		}
		
		//we have remove the repeat dim and indicator 
		Map<RptDim,List<RptIndicator>> dimIndiMap = getDimIndicator(dimList);

		RptDim dim = this.getDim(dimList.get(0).getDim()); //just support one dim for export excel  
		
		if(null == dim)
		{
			log.warn("the dim is not config " + dimList.get(0).getDim());
			return excelMetaList;
		}
		
		List<RptIndicator> indiList = dimIndiMap.get(dim);

		FFExcelMeta meta = new FFExcelMeta();
		meta.setName(dim.getName());
		meta.setField(dim.getField_name());
		excelMetaList.add(meta);
			
		if(null != indiList)
		{   
			for(RptIndicator indicator : indiList)
			{
				FFExcelMeta e = new FFExcelMeta();
				e.setField(indicator.getField_name());
				e.setName(indicator.getName());
				excelMetaList.add(e);
			}
		}
		return excelMetaList;
	}

 
	
	public List<RptIndicator> getIndicator(List<String> indicatorIDList)
	{
		List<RptIndicator> indicatorList = new ArrayList<RptIndicator>();
		
		if(!ValidatorUtil.isEmpty(indicatorIDList))
		{
			for(String id : indicatorIDList)
			{
				RptIndicator indicator = getIndicator(id);
				if (null != indicator)
				{
					indicatorList.add(indicator);
				}
			}
 		}
 		return indicatorList;
	}
	
	public List<RptDim> getDim(List<String> dimIDList)
	{
		List<RptDim> dimList = new ArrayList<RptDim>();
		
		if(!ValidatorUtil.isEmpty(dimIDList))
		{
			for(String id : dimIDList)
			{
				RptDim dim = getDim(id);
				if (null != dim)
				{
					dimList.add(dim);
				}
			}
 		}
 		return dimList;
	}

	
	/**
	 * we will remove the repeat dim and indicator
	 * @Title: getDimIndicator 
	 * @Description:   
	 * @param @param dimList
	 * @param @return    
	 * @return Map<RptDim,List<RptIndicator>>
	 */
	private Map<RptDim,List<RptIndicator>> getDimIndicator(List<RptDimDataJson> dimList)
	{
		Map<RptDim, Set<RptIndicator>> dimMapIndiSet = new HashMap<RptDim, Set<RptIndicator>>();

		//for keep sort
		Map<RptDim, List<RptIndicator>> dimMapIndiList = new HashMap<RptDim, List<RptIndicator>>();

		
		for (RptDimDataJson rpt : dimList)
		{
			RptDim dim = getDim(rpt.getDim());

			if (null == dim)
			{
				log.warn("the dim have not been config " + rpt.getDim());
				continue;
			}

			RptIndicator indicator = getIndicator(rpt.getIndicator());
			if (null == indicator)
			{
				log.warn("the indicator have not been config " + rpt.getIndicator());
				continue;
			}

			Set<RptIndicator> indiSet = dimMapIndiSet.get(dim);
			List<RptIndicator> indiList = dimMapIndiList.get(dim);

			if (null == indiSet)
			{
				indiSet = new HashSet<RptIndicator>();
				indiSet.add(indicator);
				dimMapIndiSet.put(dim, indiSet);
				
				indiList = new ArrayList<RptIndicator>();
				indiList.add(indicator);
				dimMapIndiList.put(dim, indiList);
			} else
			{
				if(!indiSet.contains(indicator))
				{
					indiList.add(indicator);
					indiSet.add(indicator);
				}
  			}
		}
		return dimMapIndiList;
	}


	/**
	 * @param user
	 * @param conditionList
	 * @param dim
	 * @param yAllData
	 * @param indiList
	 */
	private void calcWithRatio(FFSysUser user,RptDim dim,  List<RptIndicator> indiList,List<FFCondition> conditionList, List<E> yAllData)
	{
		for(RptIndicator indi : indiList)
		{
			
			if(indi.hasRatioCalc())
			{
				List<RptCalc> calcList = indi.getRatioCalc();
				for(RptCalc calc : calcList)
				{
					
					RptCalcService calcService = SpringContextUtil.getBean(calc.getCalc().getService(), RptCalcService.class);

				    RptAxisDataSource axisDataService = (RptAxisDataSource) SpringContextUtil.getBean(dim.getDatasource());
					String dim_filter_field = axisDataService.getDimMapField(dim);
					List<FFCondition> cmpConditionList = new ArrayList<FFCondition>();
					
					//get compare dim value as new condition to get all data
					for(FFCondition query : conditionList)
					{
						if(dim_filter_field.equals(query.getAttrName()))
						{
							
							Object value = calcService.getDimCmpValue(query.getAttrValue(),dim, calc);
							if(null != value)
							{
								FFCondition newCondition = new FFCondition(query.getConditionType(),query.getAttrName(),value);
								cmpConditionList.add(newCondition);
							}
							
						}
						else
						{
							cmpConditionList.add(query);
						}
					}
					// get compare data 
					List<E> cmpDataFromDB = getAllDataByDim(user, dim,indiList,cmpConditionList);
					Map<Object,E> cmpDataFromDBMap = list2TimeMap(dim,cmpDataFromDB);

					//calc with formula
					for(E e : yAllData)
					{
						Object dimValue = ReflectionUtil.getValueByFieldName(e, dim.getField_name());
						Object cmpDimValue = calcService.getDimCmpValue(dimValue,dim, calc);
						RptTimeDimEnum dimEnum = RptTimeDimEnum.getEnumByName(dim.getDim());
						if(null != dimEnum)
						{
							cmpDimValue = dimEnum.format((Date)cmpDimValue);
						}
						 
						Object otherValue = cmpDataFromDBMap.get(cmpDimValue);
						if(null == otherValue)
						{
							otherValue = ReflectionUtil.newInstance(this.getEntityClass());
						}
						Object calcValue = calcService.calc(e, otherValue, calc, indi);
						ReflectionUtil.setObjectField(e, indi.getField_name(), calcValue);
					}
 				}
				
			}
		}
	}
	
	
	protected List<E> calcCallback(RptDim dim,List<RptIndicator> indiList, List<FFCondition> conditionList,List<E> dataFromDB)
	{
		return dataFromDB;
	}
	
	/**
	 * 
	 * @param user
	 * @param indiList
	 * @param conditionList
	 * @return
	 */
	private E getAllDataBySum(FFSysUser user,List<RptIndicator> indiList, List<FFCondition> conditionList)
	{
		List<E> dataList =  getAllDataByDim(user,null,indiList,conditionList);
		
		E result = null;
		if(!ValidatorUtil.isEmpty(dataList))
		{
			result = dataList.get(0);
  		}
		if(null == result)
		{
			result = (E) ReflectionUtil.newInstance(this.getEntityClass());
		}
		return result;
		
	}
	/**
	 * get all indicator data from data base and calculate by code formular
	 * @param user
	 * @param conditionList
	 * @param indiList
	 * @param dim
	 * @param nowCon
	 * @return
	 */
	protected List<E> getAllDataByDim(FFSysUser user,RptDim dim,List<RptIndicator> indiList, List<FFCondition> conditionList)
	{
		List<FFCondition> nowCon = new ArrayList<FFCondition>();
		nowCon.addAll(conditionList);
		if(null != dim)
		{
			nowCon.add(new FFCondition(ConditionTypeEnum.GROUP_BY,dim.getField_name()));
		}
		nowCon.addAll(getDbCalcCondition(indiList));
		List<E> dataFromDB = super.load(user, nowCon);   //get data from db 
 
		

		//calc data by code formula
		List<RptIndicator> codeIndiList = getCodeCalcIndi(indiList);
		if(!ValidatorUtil.isEmpty(codeIndiList))
		{
			for(RptIndicator indi : codeIndiList)
			{
  				List<RptCalc> calcList = indi.getCodeCalc();
					
  				for(RptCalc calc : calcList)
				{
  					for(E e : dataFromDB)
  					{

  						RptCalcService calcService = SpringContextUtil.getBean(calc.getCalc().getService(), RptCalcService.class);
  						Object value = calcService.calc(e,null,calc,indi);
  						ReflectionUtil.setObjectField(e, indi.getField_name(), value);
  					}

 				}
 			}
		}
		dataFromDB = calcCallback(dim, indiList, conditionList, dataFromDB);
		return dataFromDB;
	}
	
	
	
	private RptDim getDim(String dimID)
	{
			RptDim dim = dimCache.get(dimID);
			if(null == dim)
			{
				
				log.info("the dim is not in cache,so get default by field name.  " + dimID);

				Field field = ReflectionUtil.getFieldByName(this.getEntityClass(), dimID);
				
 				if(null != field)
 				{
  					dim = new RptDim();
 					dim.setDim(dimID);
 					
 					dim.setField_name(dimID);
  				}
 			}
			return dim;
	}
	
	
	private Field getDefualtIndicatorField(String indicator_id)
	{
		Field[] fields = ReflectionUtil.getAllFields(this.getEntityClass());
		for(Field field :fields)
		{
			RptIndicatorAnno indiAnno = field.getAnnotation(RptIndicatorAnno.class);

			if(null != indiAnno)
			{
				if(indicator_id.equals(indiAnno.indicator()))
				{
					return field;
				}
			}
		}
		Field field = ReflectionUtil.getFieldByName(this.getEntityClass(), indicator_id);
		return field;
	}
	private RptIndicator getIndicator(String indicator_id)
	{
		RptIndicator indicator = indicatorCache.get(indicator_id);
		if (null == indicator)
		{
			
			log.trace("the indicator is not in cache,so get default by field name.  " + indicator_id);
			Field field = getDefualtIndicatorField(indicator_id);
			if (null != field)
			{
				indicator = new RptIndicator();
				indicator.setIndicator(indicator_id);
				indicator.setField_name(field.getName());
				log.trace("the indicator never config calc formula,try to get the default calc by RptIndicatorAnno.  " + indicator_id);
				RptIndicatorAnno indiAnno = field.getAnnotation(RptIndicatorAnno.class);
 				RptCalcAnno calcAnno = field.getAnnotation(RptCalcAnno.class);
 				
 				List<RptCalcAnno> calcAnnoList = new ArrayList<RptCalcAnno>();
				
 				if (null != calcAnno)
				{
					calcAnnoList.add(calcAnno);
				}
 				if (null != indiAnno)
				{
					calcAnnoList.addAll(Arrays.asList(indiAnno.calcList()));
					indicator.setName(indiAnno.name());
				}
				
 				List<RptCalc> calcList = new ArrayList<>();
				for(RptCalcAnno e : calcAnnoList)
				{
					RptCalc calc = new RptCalc();
					calc.setCalc(e.calc());
					if(ValidatorUtil.isEmpty(e.formula()))
					{
						calc.setFormula(e.calc().getFormula());
					}
					else
					{
						calc.setFormula(e.formula());
					}
					calcList.add(calc);
 				}
				indicator.setCalcList(calcList);

			}
		}
 

		return indicator;
	}
	
	
	

	
	private Map<Object,E> list2TimeMap(RptDim dim,List<E> dataList)
	{
		Map<Object,E> map = new HashMap<Object,E>();
		for(E e : dataList)
		{
			Object dimValue = ReflectionUtil.getValueByFieldName(e, dim.getField_name());
			map.put(dimValue, e);
		}
		return map;
	}
 
	/**
	 * get code calculate indicator 
	 * @param indicatorList
	 * @return
	 */
	private List<RptIndicator> getCodeCalcIndi(Collection<RptIndicator> indicatorList)
	{
		List<RptIndicator> resList = new ArrayList<RptIndicator>();
		for(RptIndicator indi :indicatorList)
		{
 			if(indi.hasCodeCalc())
			{
				resList.add(indi);
			}
		}
		return resList;
	}
	/**
	 * 认为所有的指标，都有一个独立的数据库字段对应相应的数据,配备独立公式
	 * 获取依靠数据计算的公式
	 * @param indicatorList
	 * @return
	 */
	private List<FFCondition> getDbCalcCondition(Collection<RptIndicator> indicatorList)
	{
		List<FFCondition> conditionList = new ArrayList<FFCondition>();
 
		for(RptIndicator indicator : indicatorList)
		{
 			
			RptCalc calc = indicator.getDbCalc();
			if(null != calc)
			{
				ConditionTypeEnum type = ConditionTypeEnum.getEnumByStr(calc.getCalc().getName());
				if(null != type)
				{
					FFCondition condition = new FFCondition(type,indicator.getField_name());
					if(!ValidatorUtil.isEmpty(calc.getFormula()))
					{
						condition.setMyBatisFlag(calc.getFormula());
					}
					conditionList.add(condition);

				}
 			}
 		}
 
  		return conditionList;
	}
 
	
}
