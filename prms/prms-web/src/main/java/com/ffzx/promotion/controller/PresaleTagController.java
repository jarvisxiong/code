package com.ffzx.promotion.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.promotion.api.dto.ActivityCommodity;
import com.ffzx.promotion.api.dto.ActivityPreSaleTag;
import com.ffzx.promotion.mapper.ActivityCommodityMapper;
import com.ffzx.promotion.model.PreSaleTag;
import com.ffzx.promotion.service.ActivityCommodityService;
import com.ffzx.promotion.service.ActivityPreSaleTagService;

/**
 * 预售标签设置
 * 
 * @author lushi.guo
 * @date 2016年08月18日
 * @version V1.0
 */
@Controller
@RequestMapping("presaletag/*")
public class PresaleTagController extends BaseController {
	/**
	 * 日志
	 */
	protected final Logger logger = LoggerFactory.getLogger(PresaleTagController.class);

	@Autowired
	private ActivityPreSaleTagService activityPreSaleTagService;
	@Autowired
	private ActivityCommodityMapper activityCommodityMapper;

	@RequestMapping(value = "tagList")
	public String tagList(ModelMap map) {
		// 分页数据
		Page pageObj = this.getPageObj();

		List<ActivityPreSaleTag> presaleTagList = null;

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("delFlag", "0");
			presaleTagList = activityPreSaleTagService.findList(pageObj, "number", Constant.ORDER_BY, params);
		} catch (ServiceException e) {
			logger.error("促销系统管理-预售标签列表", e);
			throw e;
		} catch (Exception e) {
			logger.error("促销系统管理-预售标签列表", e);
			throw new ServiceException(e);
		}
		map.put("dataList", presaleTagList);
		map.put("pageObj", pageObj);

		return "activity/presaletag/presale_tag_list";
	}

	@RequestMapping(value = "toSaveAndUpdate")
	public String toSaveAndUpdate(String id, ModelMap map, String viewStatus) {
		ActivityPreSaleTag presaleTag = new ActivityPreSaleTag();
		if (StringUtils.isEmpty(viewStatus)) {
			viewStatus = "save";
		}
		if (viewStatus.equals("edit") || viewStatus.equals("view")) {
			presaleTag = this.activityPreSaleTagService.findById(id);
			map.put("presaleTag", presaleTag);
		}
		map.put("viewStatus", viewStatus);
		return "activity/presaletag/presale_tag_form";
	}

	@RequestMapping(value = "saveAndUpdate")
	@ResponseBody
	public ResultVo saveAndUpdate(ActivityPreSaleTag presaleTag, String view) {
		ResultVo resultVo = new ResultVo();
		try {
			String name = presaleTag.getName();// 新编辑名称
			int number = presaleTag.getNumber();// 新编辑排序号
			String oldName = presaleTag.getOldName();// 编辑之前的名称
			String oldNumber=presaleTag.getOldNumber();//编辑之前的排序号
			List<ActivityPreSaleTag> presaletagList = null;

			PreSaleTag tag =null;
			//新增的时候必须验证是否重复
			if (view.equals("save")) {
				tag =new PreSaleTag();
				tag.setName(name);
				tag.setNumber(number);
			}

			//编辑的时候 如果编辑前和编辑后值不一样要验证是否重复
			if (view.equals("edit")) {	
				tag =new PreSaleTag();
				if (!name.equals(oldName)) {						
					tag.setName(name);
				}
				if (number!=Integer.parseInt(oldNumber)) {						
					tag.setNumber(number);
				}
			}
			if(tag!=null){
				tag.setDelFlag("0");
				presaletagList = this.activityPreSaleTagService.findPresaleTagByNumberOrName(tag);	
			}				
			if (presaletagList != null && presaletagList.size() != 0) {
				resultVo.setStatus(Constant.RESULT_EXC);
				resultVo.setInfoStr("标签名称或者排序号不允许重复");
			} else {
				ServiceCode serviceCode = this.activityPreSaleTagService.saveAndUpdatePreSaleTag(presaleTag);
				resultVo.setResult(serviceCode, "/presaletag/tagList.do");
			}
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("预售标签设置" + e);
			throw e;
		}
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}

	@RequestMapping(value = "deleteTag")
	@ResponseBody
	public ResultVo deleteTag(String id) {
		ResultVo resultVo = new ResultVo();
		try {
			if (StringUtils.isNotEmpty(id)) {
				List<ActivityCommodity> commodityList = new ArrayList<ActivityCommodity>();
				PreSaleTag tag =new PreSaleTag();
				tag.setPresaleTagId(id);
				commodityList = this.activityCommodityMapper.getActivityCommodityByPresaleTag(tag);
				if (commodityList != null && commodityList.size() != 0) {
					resultVo.setStatus(Constant.RESULT_EXC);
					resultVo.setInfoStr("删除失败，原因：商品设置有商品选择该标签");
				} else {
					ServiceCode serviceCode = this.activityPreSaleTagService.deletePreSaleTag(id);
					resultVo.setResult(serviceCode, "/presaletag/tagList.do");
				}
			}
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("预售标签删除" + e);
			throw e;
		}
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			return resultVo;
		}
		return resultVo;
	}
}
