package com.ffzx.promotion.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.ExcelExportUtil;
import com.ffzx.commerce.framework.utils.ExcelExportUtil.ExportModel;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.promotion.api.dto.ChoiceShareOrder;
import com.ffzx.promotion.api.dto.JoinRecord;
import com.ffzx.promotion.api.dto.RewardLuckNo;
import com.ffzx.promotion.api.dto.RewardManager;
import com.ffzx.promotion.api.dto.ShareOrder;
import com.ffzx.promotion.api.dto.constant.PrmsConstant;
import com.ffzx.promotion.mapper.RewardLuckNoMapper;
import com.ffzx.promotion.model.OperateRecord;
import com.ffzx.promotion.service.ChoiceShareOrderService;
import com.ffzx.promotion.service.JoinRecordService;
import com.ffzx.promotion.service.OperateRecordService;
import com.ffzx.promotion.service.RewardLuckNoService;
import com.ffzx.promotion.service.RewardManagerService;
import com.ffzx.promotion.service.ShareOrderService;

/**********
 * 免费抽奖管理控制器
 * @author xiaochengzi
 *
 */
@Controller
@RequestMapping("reward/*")
public class RewardManagerController extends BaseController {
	/**
	 * 日志
	 */
	protected final Logger logger = LoggerFactory.getLogger(RewardManagerController.class);
	
	@Autowired
	private RewardManagerService rewardManagerService;
	@Autowired
	private ShareOrderService shareOrderService;
	@Autowired
	private RewardLuckNoService rewardLuckNoService;
	@Autowired
	private ChoiceShareOrderService choiceShareOrderService;
	@Autowired
	private JoinRecordService joinRecordService;
	@Autowired
	private OperateRecordService operateRecordService;
	@Autowired
	private RewardLuckNoMapper rewardLuckNoMapper;
	
	
	/*
	 * 进入列表页面
	 */
	@RequestMapping("rewardList")
	public String rewardList(ModelMap map){
		//每次进入列表页面查询有多少个活动等待开奖未计算幸运号的给予页面提示
		Integer count=this.rewardManagerService.selectLuckNoCount();
		if(null!=count && count>0){
			map.put("count", count);
		}
		return "reward/reward_list";
	}
	
	
	/*
	 * 获取列表数据
	 */
	@RequestMapping(value = "/rewardListDate")
	@ResponseBody
	public ResultVo rewardListDate(RewardManager rewardManager){
		
	    List<RewardManager> rewardList = null;
	    Page pageObj = this.getPageObj();		
	    ResultVo resultVo = new ResultVo();
		//查询数据
	    try {
	    	Map<String, Object>params=new HashMap<String, Object>();
	    	getParamsMap(rewardManager, params);
	    	rewardList=this.rewardManagerService.findByPage(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, params);
	    	resultVo.setRecordsTotal(pageObj.getTotalCount());
	 	    resultVo.setInfoData(rewardList);
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("PresellActivityController-save-ServiceException=》商品设置-保存", e);
			throw e;
		}		
		//设置页面显示
		if (resultVo.isHasException()) {
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
		}	   	    
		return resultVo;
	}
	
	/*
	 * 跳转新增页面
	 */
	@RequestMapping(value = "/form")
	//@Permission(privilege = "pms:demo:view")
	public String form(String id, String viewStatus,ModelMap map){
		
		RewardManager reward = null;
		//查询编辑数据
		List<String> imageList=null;
		List<OperateRecord> operateRecordList=null;
		if(StringUtils.isNotEmpty(id)){
			reward = this.rewardManagerService.findById(id);
			if(null!=reward){
				String detailImage=reward.getDetailImage();
				 imageList=this.getImageList(detailImage);	
				 //获取操作日志
				 Map<String, Object> params=new HashMap<String, Object>();
				 params.put("rewardId", id);
				 params.put("orderByField","record_date");
				 params.put("orderBy", Constant.ORDER_BY);
				 operateRecordList=this.operateRecordService.findByBiz(params);				 
			}			
		}		
		map.put("reward", reward);
		map.put("viewStatus", viewStatus);
		map.put("operateRecordList", operateRecordList);
		map.put("imageList", imageList);
		map.put("image_path",System.getProperty("image.web.server"));
		return "reward/reward_form";
	}
	
	/*
	 * 跳转设置晒单页面
	 */
	@RequestMapping(value = "/shareOrder")
	//@Permission(privilege = "pms:demo:view")
	public String shareOrder(String id, ModelMap map){
		
		ShareOrder shareOrder= this.shareOrderService.findShareOrderById(id);
		
		map.put("shareOrder", shareOrder);
		map.put("image_path",System.getProperty("image.web.server"));
		return "reward/share_order_form";
	}
	
	
	@RequestMapping("/isShareOrder")
	@ResponseBody
	//@Permission(privilege = "pms:demo:edit")
	public ResultVo isShareOrder(String id, ModelMap map) {
		ResultVo resultVo = new ResultVo(); 
		try {					
			RewardLuckNo luck=this.rewardLuckNoMapper.selectluckNoByid(id);
			if(luck==null){
				//说明没有计算过幸运号
				resultVo.setStatus(Constant.RESULT_EXC);
				resultVo.setInfoStr(PrmsConstant.ERRORMESSAGE);
			}
		} catch (Exception e) {
			logger.error("计算幸运号", e);
			throw e;
		}		
		return resultVo;
	}
	
	/*
	 * 跳转精选晒单页面
	 */
	@RequestMapping(value = "/choiceShareOrder")
	//@Permission(privilege = "pms:demo:view")
	public String choiceShareOrder(ModelMap map){
		
		Map<String, Object> params=new HashMap<String, Object>();
		List<ChoiceShareOrder> choiceList= this.choiceShareOrderService.findByBiz(params);
		ChoiceShareOrder choice=null;
		List<String> imageList=null;
		if(null!=choiceList&& choiceList.size()!=0){
			choice=choiceList.get(0);
			if(null!=choice){
				String detailImage=choice.getChoiceImage();
				 imageList=this.getImageList(detailImage);				
			}			
		}		
		map.put("choice", choice);
		map.put("imageList", imageList);
		map.put("image_path",System.getProperty("image.web.server"));
		return "reward/choice_share_order";
	}
	
	/*
	 * 跳转计算幸运号页面
	 */
	@RequestMapping(value = "/luckNo")
	//@Permission(privilege = "pms:demo:view")
	public String luckNo(String id, ModelMap map){
		
		RewardLuckNo luckNo = null;
		//查询编辑数据
		if(StringUtils.isNotEmpty(id)){
			luckNo = this.rewardLuckNoService.findLuckNoByRewardId(id);
		}		
		map.put("luckNo", luckNo);
		return "reward/reward_luck_no";
	}
	
	
	@RequestMapping("/drawLuckNo")
	@ResponseBody
	//@Permission(privilege = "pms:demo:edit")
	public ResultVo drawLuckNo(RewardLuckNo luckNo, ModelMap map) {
		ResultVo resultVo = new ResultVo(); 
		try {					
			//计算幸运号			
			ServiceCode serviceCode =this.rewardLuckNoService.saveRewardLuckNo(luckNo);
			resultVo.setResult(serviceCode, "/reward/luckNo.do?id="+luckNo.getRewardId());		
		} catch (Exception e) {
			logger.error("计算幸运号", e);
			throw e;
		}
		
		return resultVo;
	}

	
	/*
	 *新增/编辑精选晒单 
	 * @param reward
	 * @param result
	 * @return
	 */
	@RequestMapping("/saveChoiceShareOrder")
	@ResponseBody
	//@Permission(privilege = "pms:demo:edit")
	public ResultVo saveChoiceShareOrder(ChoiceShareOrder choice, BindingResult result) {
		
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		//执行更新
		if (result.hasErrors()) {
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		try {		
			ServiceCode serviceCode =this.choiceShareOrderService.saveChoiceOrder(choice);
			resultVo.setResult(serviceCode, "/reward/rewardList.do");
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("新增/编辑精选晒单", e);
			throw e;
		}
		return resultVo;
	}
	
	/*
	 * 新增/编辑免费抽奖
	 */
	@RequestMapping("/saveReward")
	@ResponseBody
	//@Permission(privilege = "pms:demo:edit")
	public ResultVo saveReward(RewardManager reward, BindingResult result) {
		
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		//执行更新
		if (result.hasErrors()) {
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		try {		
			String dateNo=reward.getDateNo();//期号
			//验证期号是否重复
			List<RewardManager> listRecord=null;
			if(!StringUtils.isNotEmpty(reward.getId())){
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("dateNo", dateNo);
				listRecord=this.rewardManagerService.findByBiz(params);
			}			
			if(null!=listRecord && listRecord.size()!=0){
				//说明已经存在该期号
				resultVo.setStatus(Constant.RESULT_EXC);
				resultVo.setInfoStr("抽奖期号不允许重复");
			}else{
				ServiceCode serviceCode =this.rewardManagerService.saveReward(reward);
				resultVo.setResult(serviceCode, "/reward/rewardList.do");
			}
			
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("新增/编辑免费抽奖", e);
			throw e;
		}
		return resultVo;
	}
	
	
	/*
	 * 新增/编辑设置晒单
	 */
	@RequestMapping("/addShareOrder")
	@ResponseBody
	//@Permission(privilege = "pms:demo:edit")
	public ResultVo addShareOrder(ShareOrder share, BindingResult result) {
		
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		//执行更新
		if (result.hasErrors()) {
			resultVo.setStatus(Constant.RESULT_VALID);
			resultVo.setInfoMap(this.getValidErrors(result.getFieldErrors()));
			return resultVo;
		}
		try {		
			ServiceCode serviceCode =this.shareOrderService.saveShareOrder(share);
			resultVo.setResult(serviceCode, "/reward/rewardList.do");
		} catch (Exception e) {
			resultVo.setHasException(true);
			logger.error("新增/编辑设置晒单", e);
			throw e;
		}
		return resultVo;
	}
	
	/*
	 * 删除免费抽奖
	 */
	@RequestMapping("/delete")
	@ResponseBody	
	//@Permission(privilege = "pms:demo:delete")
	public ResultVo delete(String id,String sendStatus) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		int resultInt = 0;
		
		//执行操作
		try {
			RewardManager reward=new RewardManager();
			reward.setDelFlag(Constant.YES);
			reward.setId(id);
			resultInt = this.rewardManagerService.deleteReward(reward,PrmsConstant.DELETE);
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("免费抽奖删除", e);
			throw e;
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_DEL_MSG);
			resultVo.setUrl("/reward/rewardList.do");
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}		
		return resultVo;
	}
	
	
	/***
	 * 发布与撤销
	 * @param id
	 * @param sendStatus
	 * @return
	 */
	@RequestMapping("/rewardSend")
	@ResponseBody	
	//@Permission(privilege = "pms:demo:delete")
	public ResultVo rewardSend(String id,String sendStatus) {
		//返回结果对象
		ResultVo resultVo = new ResultVo();
		int resultInt = 0;
		
		//执行操作
		try {
			RewardManager reward=new RewardManager();
			reward.setSendStaus(sendStatus);
			reward.setId(id);
			String type=null;
			if(sendStatus.equals(Constant.NO)){
				type=PrmsConstant.CANCEL;
			}else{
				type=PrmsConstant.SEND;
			}
			resultInt = this.rewardManagerService.deleteReward(reward,type);
		} catch (ServiceException e) {
			resultVo.setHasException(true);
			logger.error("免费抽奖删除", e);
			throw e;
		}
		
		//判断执行结课
		if(resultInt > 0) {
			resultVo.setStatus(Constant.RESULT_SCS);
			resultVo.setInfoStr(Constant.RESULT_SCS_MSG);
			resultVo.setUrl("/reward/rewardList.do");
		}else{
			resultVo.setStatus(Constant.RESULT_ERR);
			resultVo.setInfoStr(Constant.RESULT_ERR_MSG);
		}		
		return resultVo;
	}
	
	/**
	 * 导出最后50个时间数值
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/joinRecordExport")
	public void joinRecordExport(String rewardId,HttpServletRequest request,HttpServletResponse response){
		final String[] headers = new String[]{"参与时间","时间组合值","会员账号"};
		final String[] properties = new String[]{"createDate","sumTime","phone"};
		final String fileName = "50个时间数值";
		try {
			
			Page pageObj = new Page(1, 50);
			Map<String, Object>params=new HashMap<String, Object>();
			params.put("rewardId", rewardId);
			//获取最后50个参与的记录
			final List<JoinRecord> recordList=this.joinRecordService.findByPage(pageObj, Constant.ORDER_BY_FIELD_CREATE, Constant.ORDER_BY,params);
			if(recordList!=null && recordList.size()!=0){
				this.getSumTime(recordList);
				ExcelExportUtil.exportXls(new ExportModel() {
						public String[] getProperties() {
							return properties;
						}
						public String[] getHeaders() {
							return headers;
						}
						public List getData() {
							return recordList;
						}
						public String getFileName() {
							return fileName;
						}
					},response);
			}		
		} catch (IOException e) {
			logger.error("", e);
			throw new ServiceException(e);
		}
	}
	
	private Integer getSumTime(List<JoinRecord> recordList){
		Integer count=0;
		try {
			if(recordList!=null && recordList.size()!=0){			
				for(JoinRecord record:recordList){		
					Calendar cal = Calendar.getInstance();
					cal.setTime(record.getCreateDate());
					Integer hour = cal.get(Calendar.HOUR_OF_DAY);
					Integer minute = cal.get(Calendar.MINUTE);
					Integer second = cal.get(Calendar.SECOND);
					Integer mm = cal.get(Calendar.MILLISECOND);
					String sumTime=this.getTimeStr(hour)+this.getTimeStr(minute)+this.getTimeStr(second)+this.getmmStr(mm);
					record.setSumTime(sumTime);
				}
			}
		} catch (Exception e) {
			logger.error("计算50个时间值之和", e);
			throw new ServiceException(e);
		}
		return count;		
	}

	private String getTimeStr(Integer count){
		String countStr=null;
		if(count<10){
			countStr= "0"+count.toString();
		}else{
			countStr=count.toString();
		}
		return countStr;
	}
	
	private String getmmStr(Integer count){
		String countStr=null;
		if(count<10){
			countStr= "00"+count.toString();
		}else if(count<100){
			countStr= "0"+count.toString();
		}else{
			countStr=count.toString();
		}
		return countStr;
	}
	
	
	private void getParamsMap(RewardManager rewardManager, Map<String, Object> params) {
				
		if(StringUtils.isNotEmpty(rewardManager.getTitle())){
			params.put("title", rewardManager.getTitle());
		}
		if(StringUtils.isNotEmpty(rewardManager.getDateNo())){
			params.put("dateNo", rewardManager.getDateNo());
		}
		if(StringUtils.isNotEmpty(rewardManager.getRewardNo())){
			params.put("rewardNo", rewardManager.getRewardNo());
		}
		if(StringUtils.isNotEmpty(rewardManager.getSendStaus())){
			params.put("sendStaus", rewardManager.getSendStaus());
		}
		if(StringUtils.isNotEmpty(rewardManager.getRewardStatus())){
			params.put("rewardStatus", rewardManager.getRewardStatus());
		}
		if(StringUtils.isNotEmpty(rewardManager.getCreateName())){
			params.put("createName", rewardManager.getCreateName());
		}
		if(StringUtils.isNotEmpty(rewardManager.getStartDateStartStr())){
			params.put("startDateStartStr", rewardManager.getStartDateStartStr());
		}
		if(StringUtils.isNotEmpty(rewardManager.getStartDateEndStr())){
			params.put("startDateEndStr", rewardManager.getStartDateEndStr());
		}
		if(StringUtils.isNotEmpty(rewardManager.getEndDateStartStr())){
			params.put("endDateStartStr", rewardManager.getEndDateStartStr());
		}
		if(StringUtils.isNotEmpty(rewardManager.getEndDateEndStr())){
			params.put("endDateEndStr", rewardManager.getEndDateEndStr());
		}
		if(StringUtils.isNotEmpty(rewardManager.getRewardDateStartStr())){
			params.put("rewardDateStartStr", rewardManager.getRewardDateStartStr());
		}
		if(StringUtils.isNotEmpty(rewardManager.getRewardDateEndStr())){
			params.put("rewardDateEndStr", rewardManager.getRewardDateEndStr());
		}
		params.put(Constant.DELTE_FLAG, Constant.NO);
	}
	
	private List<String> getImageList(String images){
		List<String> imageList =null;
		if (StringUtils.isNotEmpty(images)) {
			imageList = new ArrayList<>();
			for (String img : images.split(",")) {
				imageList.add(img);
			}
		}
		return imageList;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");		
		try {
			Date e=sdf.parse(sdf.format(new Date()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(e);
			Integer hour = cal.get(Calendar.HOUR_OF_DAY);
			Integer minute = cal.get(Calendar.MINUTE);
			Integer second = cal.get(Calendar.SECOND);
			Integer mm = cal.get(Calendar.MILLISECOND);
			System.out.println(hour.toString()+minute.toString()+second.toString()+mm.toString()+"");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
