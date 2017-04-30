/*package com.ffzx.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ffzx.commerce.framework.annotation.MyBatisDao;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.order.api.dto.CommodityAddress;

*//**
 * @className CommodityAddressMapper
 *
 * @author liujunjun
 * @date 2016-05-13
 * @version 1.0.0
 *//*
@MyBatisDao
public interface CommodityAddressMapper extends CrudMapper {

	*//**
	 * 根据商品编码找出所有地址编码
	 * @param CommodityCode
	 * @return
	 * @throws ServiceException
	 *//*
	public List<CommodityAddress>  getCommodityAddressByCommodityCode(String CommodityCode)throws ServiceException;
	
	*//**
	 * 根据商品编码删除该商品所有地址
	 * @param commodityCode
	 * @return
	 *//*
	public int deleteByCommodityCode(@Param("commodityCode")String commodityCode);
	
	*//**
	 * 通过id集,查询地址名称集合
	 * @param menu
	 * @return
	 *//*
	public String getAddressNames(@Param("codeList") List<String> codeList,@Param("commodityCode")String commodityCode) throws ServiceException;
}
*/