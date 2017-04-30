package com.ffzx.promotion.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.api.service.CouponReceiveApiService;
import com.ffzx.promotion.service.CouponReceiveService;

/**
 * 优惠券领取控制器
 * 
 * @className CouponGrantController.java
 * @author zi.luo
 * @email  zi.luo@ffzxnet.com
 * @date 2016年5月5日 下午5:57:20
 * @version 1.0
 */
@Controller
@RequestMapping("/couponReceive")
public class CouponReceiveController extends BaseController {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(CouponGrantController.class);

	@Autowired
	private CouponReceiveService couponReceiveService;
	
	@Autowired
	private CouponReceiveApiService couponReceiveApiService;

	/**
	 * 优惠券领取列表
	 * @param member
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String couponGrantList(CouponReceive couponReceive,ModelMap map) {
		Page pageObj = this.getPageObj();
		List<CouponReceive> couponReceives=null;
		try {
			couponReceives=couponReceiveService.findList(pageObj, null,  null, couponReceive);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error("CouponGrantController-couponGrantList-Exception=》促销系统-优惠券发放列表-Exception", e);
			throw e;
			
		}
		map.put("size", couponReceives.size());
		map.put("couponReceiveList", couponReceives);
		map.put("couponReceive", couponReceive);
		map.put("pageObj",pageObj);
		return "coupon/coupon_receive_list";
	}

	/**
	 * 优惠券领取记录限制
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/couponReceiveLimitExport")
	public void couponReceiveLimitExport(CouponReceive couponReceive,HttpServletRequest request,HttpServletResponse response){
		try {
			int num=50000;
			String returnDate=PrmsConstant.SUCCESS;
			int receiveCount=couponReceiveService.selectReceiveCount( couponReceive);
			if(receiveCount>num){
				 returnDate=PrmsConstant.ERROR;
			}else if(receiveCount==0){
				 returnDate=PrmsConstant.ZERO;
			}
			this.responseWrite(response, this.getJsonByObject(returnDate));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			throw new ServiceException(e);
		}
	}
	/**
	 * 优惠券领取记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/couponReceiveExport")
	public void couponReceiveExport(CouponReceive couponReceive,HttpServletRequest request,HttpServletResponse response){
		final String[] headers = new String[]{"发放名称","发放编码","优惠券名称","面值","优惠券编码","用户昵称","联系电话","有效期","领取时间","使用状态","使用时间","领取方式","优惠码"};
		final String[] properties = new String[]{"couponGrant.name","couponGrant.number","couponAdmin.name","couponAdmin.faceValue"
		,"couponAdmin.number","nickName","phone","couponAdmin.effectiveStr","receiveDate[yyyy-MM-dd HH:mm:ss]","userStateStr","useDate[yyyy-MM-dd HH:mm:ss]"
		,"grantModeReceiveStr","grantVcode"
		};
		final String fileName = "优惠券领取记录";
		try {
			final List<CouponReceive> couponReceives=couponReceiveService.findList(null, null,  null, couponReceive);
				ExcelExportUtil.exportXls(new ExportModel() {
					public String[] getProperties() {
						return properties;
					}
					public String[] getHeaders() {
						return headers;
					}
					public List getData() {
						return couponReceives;
					}
					public String getFileName() {
						return fileName;
					}
				},response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			throw new ServiceException(e);
		}
	}
}
