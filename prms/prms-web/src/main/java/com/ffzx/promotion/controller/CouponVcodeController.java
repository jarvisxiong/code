package com.ffzx.promotion.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.commerce.framework.system.entity.SysUser;
import com.ffzx.commerce.framework.utils.ConfigUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.promotion.api.dto.CouponAdmin;
import com.ffzx.promotion.api.dto.CouponGrant;
import com.ffzx.promotion.api.dto.CouponReceive;
import com.ffzx.promotion.api.dto.CouponVcode;
import com.ffzx.promotion.api.service.consumer.DictApiConsumer;
import com.ffzx.promotion.api.service.consumer.MemberApiConsumer;
import com.ffzx.promotion.service.CouponAdminCouponGrantService;
import com.ffzx.promotion.service.CouponAdminService;
import com.ffzx.promotion.service.CouponGrantMemberService;
import com.ffzx.promotion.service.CouponGrantService;
import com.ffzx.promotion.service.CouponVcodeService;



 /**
 * @Description: TODO
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年7月27日 下午2:42:57
 * @version V1.0 
 *
 */
@Controller
@RequestMapping("/couponVcode")
public class CouponVcodeController extends BaseController {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(CouponVcodeController.class);

	@Autowired
	private CouponVcodeService couponVcodeService;
	/**
	 * 优惠券发放列表
	 * @param member
	 * @param map
	 * @author yuzhao.xu
	 * @email  yuzhao.xu@ffzxnet.com
	 * @return
	 */
	@RequestMapping(value = "/list")
//	@Permission(privilege = {"prms:grant:edit","prms:grant:add","prms:grant:stop","prms:grant:delete","prms:grant:details"})
	public String list(CouponVcode couponVcode,ModelMap map) {
		Page pageObj = this.getPageObj();
		List<CouponVcode> couponVcodes=null;
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("couponGrantId", couponVcode.getCouponGrantId());
			couponVcodes=couponVcodeService.findByPage(pageObj,null, null, params, true);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			logger.error("CouponGrantController-couponGrantList-Exception=》促销系统-优惠券发放列表-Exception", e);
			throw e;
			
		}
		map.put("couponGrantId", couponVcode.getCouponGrantId());
		map.put("couponVcodesList", couponVcodes);
		map.put("pageObj",pageObj);
		return "coupon/coupon_vcode_list";
	}
	

	@RequestMapping(value = "/couponVcodeExport")
//	@Permission(privilege = {"prms:grant:edit","prms:grant:add","prms:grant:stop","prms:grant:delete","prms:grant:details"})
	public void couponVcodeExport(CouponVcode couponVcode,HttpServletRequest request,HttpServletResponse response) {
		final String[] headers = new String[]{"优惠码","状态"};
		final String[] properties = new String[]{"vcode","startStr"
		};
		final String fileName = "优惠码列表";
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("couponGrantId", couponVcode.getCouponGrantId());
			
			final List<CouponVcode> couponVcodes=couponVcodeService.findByPage(null,null, null, params, false);
				ExcelExportUtil.exportXls(new ExportModel() {
					public String[] getProperties() {
						return properties;
					}
					public String[] getHeaders() {
						return headers;
					}
					public List getData() {
						return couponVcodes;
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
