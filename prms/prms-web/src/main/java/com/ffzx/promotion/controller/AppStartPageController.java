package com.ffzx.promotion.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.BeanUtils;
import com.ffzx.commerce.framework.utils.ImageAttribute;
import com.ffzx.commerce.framework.utils.ImageUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.promotion.api.dto.AppStartPage;
import com.ffzx.promotion.api.enums.AdvertTypeEnum;
import com.ffzx.promotion.service.AppStartPageService;

/**
 * @author ss
 *  2016/09/09
 */
@Controller
@RequestMapping("startpage/*")
public class AppStartPageController extends BaseController {

	// 记录日志
	protected final Logger logger = LoggerFactory.getLogger(AppStartPageController.class);
	
	@Autowired
	private AppStartPageService appStartPageService;
	
	
	@RequestMapping(value="datalist")
	public String dataList(AppStartPage appStartPage, ModelMap map){
		// 分页数据
		Page pageObj = this.getPageObj();
		
		// 列表数据
		List<AppStartPage> startPageList = new ArrayList<AppStartPage>();
		logger.info("=======获取APP启动页列表数据=======Start");
		try {
			startPageList = appStartPageService.findList(pageObj, appStartPage);
		} catch (ServiceException e) {
			logger.error("AppStartPageController.dataList() ServiceException=》APP启动页--列表数据", e);
			throw e;
		} catch (Exception e) {
			logger.error("AppStartPageController.dataList() Exception=》APP启动页--列表数据", e);
			throw e;
		}
		// 设置页面显示
		map.put("appStartPage", appStartPage);
		map.put("startPageList", startPageList);
		map.put("pageObj", pageObj);
		
		return "startpage/startpage_list";
	}
	
	/**
	* 编辑启动页
	* @param id
	* @param map
	* @return String    返回类型
	 */
	@RequestMapping(value = "dataform")
//	@Permission(privilege = {"prms:activity:edit", "prms:activity:tg"})
	public String dataForm(String id, ModelMap map){
		AppStartPage appStartPage = null;
		// 广告类型
		List<Map<String, String>> advertTypeList = AdvertTypeEnum.toList();
		
		try {
			// 判断是编辑还是新增
			if(StringUtil.isNotNull(id)){
				appStartPage = appStartPageService.findById(id);
			}
			// 返回显示结果
			map.put("data", appStartPage);
		} catch (ServiceException e) {
			logger.error("AppStartPageController.dataForm() ServiceException=》APP启动页--编辑数据", e);
			throw e;
		} catch (Exception e) {
			logger.error("AppStartPageController.dataForm() Exception=》APP启动页--编辑数据", e);
			throw new ServiceException(e);
		}
		
		map.put("advertTypeList", advertTypeList);
		
		map.put("image_path",System.getProperty("image.web.server"));
		
		return "startpage/startpage_form";
	}
	
	
	@RequestMapping("datasave")
	@ResponseBody
	public ResultVo dataSave(HttpServletResponse response) {
		ResultVo resultVo = new ResultVo();
		AppStartPage data = null;
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			data = BeanUtils.fillentity(this.getParamMap(), data, AppStartPage.class);
			params.put("terminal", data.getTerminal());
			params.put("effectiveDateStr", data.getEffectiveDate());
			params.put("expiredDateStr", data.getExpiredDate());
			
			List<AppStartPage> list = this.appStartPageService.findListByDate(params);
			if (null != list && list.size() >0) {
				if (list.size() > 1 || !list.get(0).getId().equals(data.getId())) {
					logger.info("dataSave =》验证同终端类型存在重叠时间的启动页数据");
					resultVo.setStatus(Constant.RESULT_EXC);
					resultVo.setInfoStr("验证同终端类型存在重叠时间的启动页数据");
					return resultVo;
				}
			}
			// 执行写操作
			if (StringUtil.isNotNull(data.getId())) {
				// 编辑
				data.setLastUpdateBy(RedisWebUtils.getLoginUser());
				data.setLastUpdateDate(new Date());
				data.setOperator(RedisWebUtils.getLoginUser().getName());
				appStartPageService.updateBySelective(data);
			} else {
				// 新增
				data.setCreateBy(RedisWebUtils.getLoginUser());
				data.setCreateDate(new Date());
				data.setLastUpdateBy(RedisWebUtils.getLoginUser());
				data.setLastUpdateDate(new Date());
				data.setOperator(RedisWebUtils.getLoginUser().getName());
				data.setId(UUIDGenerator.getUUID());
				appStartPageService.add(data);
			}
			resultVo.setResult(ServiceResultCode.SUCCESS, "/startpage/datalist.do");
		} catch (ServiceException e) {
			logger.error("dataSave ServiceException=》广告管理-APP启动页操作数据", e);
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("dataSave Exception=》广告管理-APP启动页操作数据", e);
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(e.getMessage());
		}
		return resultVo;
	}
	
	@RequestMapping("datadelete")
	public void dataDelete(HttpServletResponse response) {
		try {
			String id = this.getString("id");
			AppStartPage data = this.appStartPageService.findById(id);
			if (StringUtil.isNotNull(data)) {
				AppStartPage startPage = new AppStartPage();
				startPage.setId(data.getId());
				startPage.setDelFlag(Constant.DELTE_FLAG_YES);
				this.appStartPageService.modifyById(startPage);
			} else {
				throw new Exception("数据不存在");
			}
			getOutputMsg().put("STATUS", ServiceResultCode.SUCCESS);
			getOutputMsg().put("MSG", "操作成功");
		} catch (ServiceException e) {
			logger.error("ajax_advertDelete-ServiceException", e);
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			throw e;
		} catch (Exception e) {
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			logger.error("ajax_advertDelete-Exception", e);
			throw new ServiceException(e);
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
	}
	
	
	/**
	 * 图片上传时name必须为image,必须指定图片存放相对路径direct
	 * 
	 * @param file
	 * @param dir
	 * @param response
	 * @throws IOException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	/*
	@RequestMapping(value = "compressUpload", method = RequestMethod.POST)
	public void compressUpload(@RequestParam(value = "image") MultipartFile file, 
			@RequestParam(value = "fileName", required = false) String ordingName, 
			@RequestParam(value = "direct", required = false) String dir, HttpServletResponse response) throws IOException, InterruptedException, ExecutionException {
		try {
			final byte[] bytes = file.getBytes();
			final String fileName = file.getOriginalFilename();
			final String path = dir;
			final String suffix = fileName.substring(fileName.lastIndexOf("."));
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			List<ImageAttribute> list = new ArrayList<ImageAttribute>();
			// width 或 height 属性为空则原图保存
			ImageAttribute imgattr = new ImageAttribute(null, null, true);
			list.add(imgattr);

//			imgattr = new ImageAttribute(100, 75, true);
//			list.add(imgattr);
			String url = ImageUtil.compressSave(bytes, path, suffix, list);

			getOutputMsg().put("STATUS", ServiceResultCode.SUCCESS);
			getOutputMsg().put("MSG", "上传文件成功");
			// 文件路径跟你设置的图片压缩高宽度有关
			getOutputMsg().put("PATH", dir + "/" + url + "_size" + suffix);
		} catch (Exception e) {
			logger.error("AdvertController-compressUpload-Exception=》商品品牌-上传LOGO", e);
			getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
			getOutputMsg().put("MSG", "上传文件失败");
			throw new ServiceException(e);
		}
		response.setContentType("text/html");
		outPrint(response, getJsonByObject(getOutputMsg()));
	}*/
	
}
