package com.ffzx.order.utils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.ffzx.basedata.api.dto.Dict;
import com.ffzx.order.model.PriceSettlement;

import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class OrderHelper {

	/**
	 * 
	 * @Title: jasperExportPdf @Description: 采购退货单打印（输出pdf） @param os @param
	 * param @throws Exception    设定文件 @return void    返回类型 @throws
	 */
	public static void jasperExportPdf(OutputStream os, Map<String, Object> param) throws Exception {

		// String
		// classPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String templatePath = "";
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource head = resolver
				.getResource("classpath:/com/ffzx/order/controller/template/order_template.jasper");
		templatePath = head.getFile().getAbsolutePath();
		PriceSettlement priceSettlement = (PriceSettlement) param.get("priceSettlement");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("items", ((PriceSettlement) priceSettlement).getDetailList());
		// p.add(items);
		List<PriceSettlement> orders=new ArrayList<PriceSettlement>();
		orders.add(priceSettlement);
		byte[] bytes = JasperRunManager.runReportToPdf(templatePath, parameters,
				new JRBeanCollectionDataSource(orders));

		os.write(bytes);
		os.flush();

	}
	/**
	 * 
	* @Title: batchExportPdf 
	* @Description: 批量导出pdf 
	* @param  os
	* @param  param 
	* @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void batchExportPdf(OutputStream os, Map<String, Object> param) throws Exception {

		// String
		// classPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String templatePath = "";
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource head = resolver
				.getResource("classpath:/com/ffzx/order/controller/template/order_template.jasper");
		templatePath = head.getFile().getAbsolutePath();
		List<PriceSettlement> orders=(List<PriceSettlement>)param.get("priceSettlements");
		if (orders != null) {
			for (PriceSettlement entity : orders) {
				entity.setPrintDate(new Date());
				Map<String, Object> parameters = new HashMap<>();
//				parameters.put("items", ((PriceSettlement) entity).getDetailList());
				byte[] bytes = JasperRunManager.runReportToPdf(templatePath, null,
						new JRBeanCollectionDataSource(orders));
				os.write(bytes);
				os.flush();
			}}
	}
}
