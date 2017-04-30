/**
 * @Description 
 * @author tangjun
 * @date 2016年8月8日
 * 
 */
package com.ffzx.bi.model;

import com.ff.common.dao.annotation.FFColumn;
import com.ff.common.dao.annotation.FFTable;
import com.ffzx.bi.common.constant.RptCalcEnum;
import com.ffzx.bi.common.model.RptCalcAnno;
import com.ffzx.bi.common.model.RptIndicatorAnno;
import com.ffzx.bi.common.model.RptTimeBaseEntity;

/**
 * @Description 
 * @author tangjun
 * @date 2016年8月8日
 */
@FFTable(name="oms_order_repository")
public class SalesRpt extends RptTimeBaseEntity
{
 
	private String sku_id;
	private String commodity_barcode;
	
	private String category_id;
	private String category_parent_ids;
	
	@FFColumn(exclude=true)
	private String category_name;
	
	private String order_id;


	private String vendor_id;
	private String vendor_name;
	
	private String region_id;

	private String address_parent_ids;
	@FFColumn(exclude=true)
	private String region_name;

	@RptIndicatorAnno(name="销售数量(个)")
	@RptCalcAnno(calc=RptCalcEnum.sum)
	private long sale_num;
	
	@RptIndicatorAnno(name="销售金额(元)")
	@RptCalcAnno(calc=RptCalcEnum.sum)
	private double sale_amount;
	
	@RptIndicatorAnno(name="退款金额(元)")
	@RptCalcAnno(calc=RptCalcEnum.sum)
	private double refund_amount;
	
	@RptIndicatorAnno(name="销售订单数(个)")
	@RptCalcAnno(calc=RptCalcEnum.complex,formula="ifnull(count(distinct(#{order_finish_num})),0)")
	private String order_finish_num = "0";
	
	@RptIndicatorAnno(name="退款商品数(个)")
	@RptCalcAnno(calc=RptCalcEnum.sum)
	private long order_cancel_num;
	
	@FFColumn(exclude=true)
	@RptIndicatorAnno(name="销售数量环比(%)")
	@RptCalcAnno(calc=RptCalcEnum.mom,formula="(#{sale_num}/#{other.sale_num}-1)*100")
	private double num_mom_ratio;
	
	@FFColumn(exclude=true)
	@RptIndicatorAnno(name="销售金额环比(%)")
	@RptCalcAnno(calc=RptCalcEnum.mom,formula="(#{sale_amount}/#{other.sale_amount}-1)*100")
	private double amount_mom_ratio;
	
	@FFColumn(exclude=true)
	@RptIndicatorAnno(name="销售数量同比(%)")
	@RptCalcAnno(calc=RptCalcEnum.yoy,formula="(#{sale_num}/#{other.sale_num}-1)*100")
	private double num_yoy_ratio;
	
	@FFColumn(exclude=true)
	@RptIndicatorAnno(name="销售金额同比(%)")
	@RptCalcAnno(calc=RptCalcEnum.yoy,formula="(#{sale_amount}/#{other.sale_amount}-1)*100")
	private double amount_yoy_ratio;

	


	/**
	 * @return the sku_id
	 */
	public String getSku_id()
	{
		return sku_id;
	}

	/**
	 * @param sku_id the sku_id to set
	 */
	public void setSku_id(String sku_id)
	{
		this.sku_id = sku_id;
	}

	/**
	 * @return the commodity_barcode
	 */
	public String getCommodity_barcode()
	{
		return commodity_barcode;
	}

	/**
	 * @param commodity_barcode the commodity_barcode to set
	 */
	public void setCommodity_barcode(String commodity_barcode)
	{
		this.commodity_barcode = commodity_barcode;
	}

	
	
	public String getOrder_id()
	{
		return order_id;
	}

	public void setOrder_id(String order_id)
	{
		this.order_id = order_id;
	}
	/**
	 * @return the category_id
	 */
	public String getCategory_id()
	{
		return category_id;
	}

	/**
	 * @param category_id the category_id to set
	 */
	public void setCategory_id(String category_id)
	{
		this.category_id = category_id;
	}

	/**
	 * @return the category_parent_ids
	 */
	public String getCategory_parent_ids()
	{
		return category_parent_ids;
	}

	/**
	 * @param category_parent_ids the category_parent_ids to set
	 */
	public void setCategory_parent_ids(String category_parent_ids)
	{
		this.category_parent_ids = category_parent_ids;
	}

	/**
	 * @return the category_name
	 */
	public String getCategory_name()
	{
		return category_name;
	}

	/**
	 * @param category_name the category_name to set
	 */
	public void setCategory_name(String category_name)
	{
		this.category_name = category_name;
	}

	/**
	 * @return the vendor_id
	 */
	public String getVendor_id()
	{
		return vendor_id;
	}

	/**
	 * @param vendor_id the vendor_id to set
	 */
	public void setVendor_id(String vendor_id)
	{
		this.vendor_id = vendor_id;
	}

	/**
	 * @return the vendor_name
	 */
	public String getVendor_name()
	{
		return vendor_name;
	}

	/**
	 * @param vendor_name the vendor_name to set
	 */
	public void setVendor_name(String vendor_name)
	{
		this.vendor_name = vendor_name;
	}

	/**
	 * @return the region_id
	 */
	public String getRegion_id()
	{
		return region_id;
	}

	/**
	 * @param region_id the region_id to set
	 */
	public void setRegion_id(String region_id)
	{
		this.region_id = region_id;
	}

	/**
	 * @return the address_parent_ids
	 */
	public String getAddress_parent_ids()
	{
		return address_parent_ids;
	}

	/**
	 * @param address_parent_ids the address_parent_ids to set
	 */
	public void setAddress_parent_ids(String address_parent_ids)
	{
		this.address_parent_ids = address_parent_ids;
	}

	/**
	 * @return the region_name
	 */
	public String getRegion_name()
	{
		return region_name;
	}

	/**
	 * @param region_name the region_name to set
	 */
	public void setRegion_name(String region_name)
	{
		this.region_name = region_name;
	}

	/**
	 * @return the sale_num
	 */
	public long getSale_num()
	{
		return sale_num;
	}

	/**
	 * @param sale_num the sale_num to set
	 */
	public void setSale_num(long sale_num)
	{
		this.sale_num = sale_num;
	}

	/**
	 * @return the sale_amount
	 */
	public double getSale_amount()
	{
		return sale_amount;
	}

	/**
	 * @param sale_amount the sale_amount to set
	 */
	public void setSale_amount(double sale_amount)
	{
		this.sale_amount = sale_amount;
	}

	/**
	 * @return the refund_amount
	 */
	public double getRefund_amount()
	{
		return refund_amount;
	}

	/**
	 * @param refund_amount the refund_amount to set
	 */
	public void setRefund_amount(double refund_amount)
	{
		this.refund_amount = refund_amount;
	}

	/**
	 * @return the order_finish_num
	 */
	public String getOrder_finish_num()
	{
		return order_finish_num;
	}

	/**
	 * @param order_finish_num the order_finish_num to set
	 */
	public void setOrder_finish_num(String order_finish_num)
	{
		this.order_finish_num = order_finish_num;
	}

	/**
	 * @return the order_cancel_num
	 */
	public long getOrder_cancel_num()
	{
		return order_cancel_num;
	}

	/**
	 * @param order_cancel_num the order_cancel_num to set
	 */
	public void setOrder_cancel_num(long order_cancel_num)
	{
		this.order_cancel_num = order_cancel_num;
	}

	/**
	 * @return the num_mom_ratio
	 */
	public double getNum_mom_ratio()
	{
		return num_mom_ratio;
	}

	/**
	 * @param num_mom_ratio the num_mom_ratio to set
	 */
	public void setNum_mom_ratio(double num_mom_ratio)
	{
		this.num_mom_ratio = num_mom_ratio;
	}

	/**
	 * @return the amount_mom_ratio
	 */
	public double getAmount_mom_ratio()
	{
		return amount_mom_ratio;
	}

	/**
	 * @param amount_mom_ratio the amount_mom_ratio to set
	 */
	public void setAmount_mom_ratio(double amount_mom_ratio)
	{
		this.amount_mom_ratio = amount_mom_ratio;
	}

	/**
	 * @return the num_yoy_ratio
	 */
	public double getNum_yoy_ratio()
	{
		return num_yoy_ratio;
	}

	/**
	 * @param num_yoy_ratio the num_yoy_ratio to set
	 */
	public void setNum_yoy_ratio(double num_yoy_ratio)
	{
		this.num_yoy_ratio = num_yoy_ratio;
	}

	/**
	 * @return the amount_yoy_ratio
	 */
	public double getAmount_yoy_ratio()
	{
		return amount_yoy_ratio;
	}

	/**
	 * @param amount_yoy_ratio the amount_yoy_ratio to set
	 */
	public void setAmount_yoy_ratio(double amount_yoy_ratio)
	{
		this.amount_yoy_ratio = amount_yoy_ratio;
	}

	

	
 
	
  
}
