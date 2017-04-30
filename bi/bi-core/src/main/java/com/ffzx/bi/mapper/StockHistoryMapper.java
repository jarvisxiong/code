package com.ffzx.bi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ffzx.bi.model.StockHistory;
import com.ffzx.bi.vo.StockHistoryVo;
import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.page.Page;

/**
 * stock_repository数据库操作接口
 * 
 * @author ffzx
 * @date 2016-08-15 14:24:06
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@MyBatisDao
public interface StockHistoryMapper extends CrudMapper {

	List<StockHistory> selectByList(@Param("page") Page page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("stockHistoryVo")StockHistoryVo stockHistoryVo);

	List<StockHistory> selectStockNum(@Param("page") Page page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy, @Param("stockHistoryVo")StockHistoryVo stockHistoryVo);
	
	StockHistory groupByVendorCode();

	StockHistory groupByCategory();

	List<String> getYear();
	
	List<String> getMonth();
	
	List<String> getWeek();

	StockHistory groupByVendorWhereCategoryOneLevel(String categoryId);

	StockHistory groupByVendorWhereCategoryTwoLevel(String categoryIdTwoLevel);

	StockHistory groupByVendorWhereCategoryThreeLevel(String categoryIdThreeLevel);
}