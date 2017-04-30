package com.ffzx.order.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.order.api.dto.DailySalesVo;
import com.ffzx.order.api.service.DailySalesApiService;
import com.ffzx.order.mapper.DailySalesMapper;
import com.ffzx.order.mapper.DailySkuSalesMapper;
import com.ffzx.order.mapper.SummarySalesMapper;
import com.ffzx.order.mapper.SummarySkuSalesMapper;
import com.ffzx.order.model.DailySales;
import com.ffzx.order.model.DailySkuSales;
import com.ffzx.order.model.SummarySales;
import com.ffzx.order.model.SummarySkuSales;

@Service
public class DailySalesApiServiceImpl implements DailySalesApiService{
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(DailySalesApiServiceImpl.class);
	
	@Resource
	private SummarySalesMapper summarySalesMapper;
	
	@Resource
	private SummarySkuSalesMapper summarySkuSalesMapper;
	
	@Resource
	private DailySalesMapper dailySalesMapper;
	
	@Resource
	private DailySkuSalesMapper dailySkuSalesMapper;

	@Override
	public ResultDto queryDailySalesData(DailySalesVo vo) throws ServiceException {
		
		ResultDto  rsDto= null;
		try {
			rsDto=new ResultDto(ResultDto.OK_CODE,Constant.SUCCESS);
			List<DailySalesVo> resList=null;
			if("0".equals(vo.getSummaryType())){//查询日销量
				resList=getDailyData(vo);
			}else{//其他默认查询汇总销量
				resList=getSummaryData(vo);
			}
			rsDto.setData(resList);
		} catch (Exception e) {
			logger.error("DailySalesApiServiceImpl-queryDailySalesData-Exception=》dubbo调用-queryDailySalesData", e);
			//rsDto = new ResultDto(ResultDto.ERROR_CODE, "faild:" + e);
			throw new ServiceException(e);
		}
		
		
		return rsDto;
	}
    
	/**
	 * 查询 汇总销量
	 * @param vo
	 * @return
	 */
	private List<DailySalesVo> getSummaryData(DailySalesVo vo) {
		List<DailySalesVo> res=null;
		Map<String, Object> params=new HashMap<String, Object>();
		if("0".equals(vo.getDataType())){//根据skuCode查询
			params.put("skuCodeList", vo.getSkuCodeList());
			List<SummarySkuSales> skuList=this.summarySkuSalesMapper.selectByParams(params);
			if(null != skuList && skuList.size()>0){
				res=new ArrayList<DailySalesVo>();
				for(SummarySkuSales sku:skuList){
					DailySalesVo obj=new DailySalesVo();
					obj.setSaleNums(sku.getSaleNum());
					obj.setSkuCode(sku.getSkuCode());
					res.add(obj);
				}
			}
		}else{//其他默认根据commodityCode查询
			params.put("commodityCodeList", vo.getCommodityCodeList());
			List<SummarySales> commodityList=this.summarySalesMapper.selectByParams(params);
			if(null != commodityList && commodityList.size()>0){
				res=new ArrayList<DailySalesVo>();
				for(SummarySales commodity:commodityList){
					DailySalesVo obj=new DailySalesVo();
					obj.setSaleNums(commodity.getSaleNum());
					obj.setCommodityCode(commodity.getCommodityCode());
					res.add(obj);
				}
			}
		}
		return res;
	}
    
	/**
	 * 查询日销量
	 * @param vo
	 * @return
	 */
	private List<DailySalesVo> getDailyData(DailySalesVo vo) {
		List<DailySalesVo> res=null;
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("startDate", vo.getStartDate());
		params.put("endDate", vo.getEndDate());
        if("0".equals(vo.getDataType())){//根据skuCode查询
			params.put("skuCodeList", vo.getSkuCodeList());
			List<DailySkuSales> skuList=this.dailySkuSalesMapper.selectSkuCodeGroup(params);
			if(null != skuList && skuList.size()>0){
				res=new ArrayList<DailySalesVo>();
				for(DailySkuSales sku:skuList){
					DailySalesVo obj=new DailySalesVo();
					obj.setSkuCode(sku.getSkuCode());
					obj.setSaleNums(sku.getSaleNum());
					res.add(obj);
				}
			}
		}else{//其他默认根据commodityCode查询
			params.put("commodityCodeList", vo.getCommodityCodeList());
			List<DailySales> commodityList=this.dailySalesMapper.selectCommodityCodeGroup(params);
			if(null != commodityList && commodityList.size()>0){
				res=new ArrayList<DailySalesVo>();
				for(DailySales commodity:commodityList){
					DailySalesVo obj=new DailySalesVo();
					obj.setSaleNums(commodity.getSaleNum());
					obj.setCommodityCode(commodity.getCommodityCode());
					res.add(obj);
				}
			}
		}
		return res;
	}

}
