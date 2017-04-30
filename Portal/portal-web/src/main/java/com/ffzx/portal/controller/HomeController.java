package com.ffzx.portal.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.portal.service.CatelogDataService;
import com.ffzx.portal.service.CatelogService;
import com.ffzx.portal.service.PageTabService;

/**
 * 
 * @author ying.cai
 * @date 2017年4月12日下午5:08:11
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
@Controller
@RequestMapping("api")
public class HomeController extends BaseController{

	@Autowired
	private PageTabService pageTabService;
	
	@Autowired
	private CatelogService catelogService ;
	
	@Autowired
	private CatelogDataService catelogDataService;
	
	/***
	 *  获取所有页签的接口
	 * @param response
	 * @author ying.cai
	 * @date 2017年4月12日下午5:22:02
	 * @email ying.cai@ffzxnet.com
	 */
	@RequestMapping(value="pageTab")
	public void pageTab(HttpServletResponse response){
		JSONArray pageTabJson = pageTabService.getPageTabJson();
		outPrint(response, pageTabJson);
	}
	
	/***
	 * 获取页签下面的栏目数据
	 * @param pageTabId
	 * @param response
	 * @author ying.cai
	 * @date 2017年4月12日下午5:25:03
	 * @email ying.cai@ffzxnet.com
	 */
	@RequestMapping(value="catelog/{pageTabId}")
	public void catelog(@PathVariable("pageTabId") String pageTabId,HttpServletResponse response){
		JSONArray catelogJson = catelogService.getCatelogJson(pageTabId);
		outPrint(response, catelogJson);
	}
	
	/***
	 * 获取栏目下面启用的子栏目
	 * @param childId
	 * @param response
	 * @author ying.cai
	 * @date 2017年4月12日下午5:30:25
	 * @email ying.cai@ffzxnet.com
	 */
	@RequestMapping(value="child/{childId}")
	public void child(@PathVariable("childId") String childId,HttpServletResponse response){
		JSONArray catelogJson = catelogService.getChildCatelogJson(childId);
		outPrint(response, catelogJson);
	}
	
	/***
	 * 获取栏目下面的数据内容
	 * @param number
	 * @param sort
	 * @param response
	 * @author ying.cai
	 * @date 2017年4月12日下午5:35:34
	 * @email ying.cai@ffzxnet.com
	 */
	@RequestMapping(value="data/{number}")
	public void data(@PathVariable("number") String number,@RequestParam(value="sort",required=false,defaultValue="DATEDESC") String sort,HttpServletResponse response){
		JSONArray dataJson = catelogDataService.getDataJson(number, sort);
		outPrint(response, dataJson);
	}
	
	/***
	 * 获取栏目下面的数据内容,分页获取
	 * @param number
	 * @param page
	 * @param size
	 * @param sort
	 * @param response
	 * @author ying.cai
	 * @date 2017年4月12日下午5:46:39
	 * @email ying.cai@ffzxnet.com
	 */
	@RequestMapping(value="datapag/{number}")
	public void datapag(@PathVariable("number") String number,
			@RequestParam(value="page",required=false,defaultValue="1") int page,
			@RequestParam(value="size",required=false,defaultValue="20") int size,
			@RequestParam(value="sort",required=false,defaultValue="DATEDESC") String sort,
			HttpServletResponse response){
		Page pageObj =new Page(page, size);
		JSONObject dataJson = catelogDataService.getDataJsonPag(number, sort, pageObj);
		outPrint(response, dataJson);
	}
	
	/***
	 * 获取某个数据栏目内容的数据
	 * @param id
	 * @param response
	 * @author ying.cai
	 * @date 2017年4月13日下午2:02:14
	 * @email ying.cai@ffzxnet.com
	 */
	@RequestMapping(value="dataview/{id}")
	public void dataview(@PathVariable("id") String id,HttpServletResponse response){
		JSONObject result = catelogDataService.newGetCatelogDataInfo(id);
		outPrint(response,result);
	}
	
	/***
	 * 获取某个页签下面的栏目和数据
	 * @param pageTabId
	 * @param response
	 * @author ying.cai
	 * @date 2017年4月13日下午2:02:33
	 * @email ying.cai@ffzxnet.com
	 */
	@RequestMapping(value="pageTabData/{pageTabId}")
	public void pageTabData(@PathVariable("pageTabId")String pageTabId,HttpServletResponse response){
		JSONArray pageTabDataJson = catelogDataService.getPageTabData(pageTabId);
		outPrint(response, pageTabDataJson);
	}
}
