package com.ffzx.promotion.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ffzx.commerce.framework.annotation.Permission;
import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.enums.ServiceCode;
import com.ffzx.commerce.framework.enums.ServiceResultCode;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.utils.BeanUtils;
import com.ffzx.commerce.framework.utils.ImageAttribute;
import com.ffzx.commerce.framework.utils.ImageUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.RedisWebUtils;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.commerce.framework.vo.ResultVo;
import com.ffzx.promotion.api.dto.Advert;
import com.ffzx.promotion.api.dto.AdvertRegion;
import com.ffzx.promotion.api.enums.AdvertStatus;
import com.ffzx.promotion.api.enums.AdvertTypeEnum;
import com.ffzx.promotion.service.AdvertRegionService;
import com.ffzx.promotion.service.AdvertService;

/**
 * @author hyl
 * 
 *         2016/05/05
 */
@Controller
@RequestMapping("advert/*")
public class AdvertController extends BaseController {

	// 记录日志
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	// private @Value("${image_path}")String image_path;
	@Autowired
	private AdvertRegionService advertRegionService;
	@Autowired
	private AdvertService advertService;
	@Resource
	private RedisUtil redisUtil;

	/**
	 * 列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index(ModelMap map) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String tree_data = "[]";
			// 掩藏的不显示
			params.put("orderBy", "d.sort asc");
			List<Object> listResult;
			listResult = advertRegionService.getAdvertRegionSimpleTree(params);
			tree_data = this.getJsonByObject(listResult);
			map.put("result", tree_data);
		} catch (ServiceException e) {
			logger.error("", e);
			throw new ServiceException(e);
		}
		return "advert/advert_index";
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "advert_list")
	public String advert_list(ModelMap map) {

		try {
			advertService.autoUpdateStatus();
		} catch (ServiceException e1) {
			logger.error("", e1);
			throw e1;
		}

		List<Advert> advertList = null;
		// 查询所有且不分页
		Map<String, Object> params = this.getParaMap();
		Page pageObj = this.getPageObj();
		if (params.size() == 0) {
			params = (Map<String, Object>) redisUtil.get("advert_list_params_" + RedisWebUtils.getLoginUser().getId());
		} else {
			redisUtil.set("advert_list_params_" + RedisWebUtils.getLoginUser().getId(), params);
		}
		params.put("orderBy", "d.location_index asc ");
		// 查询数据
		try {
			advertList = advertService.findByPage(pageObj, Constant.ORDER_BY_FIELD, Constant.ORDER_BY, params);
		} catch (ServiceException e) {
			logger.error("AdvertController ServiceException=》广告管理-列表演示", e);
			throw e;
		} catch (Exception e) {
			logger.error("AdvertController Exception=》系统管理-广告演示", e);
			throw new ServiceException(e);
		}
		if (advertList != null) {
			for (Advert advert : advertList) {
				advert.setAdvertTypeName(AdvertTypeEnum.valueOf(advert.getAdvertType()).getName());
				if (StringUtil.isNotNull(advert.getStatus())) {
					advert.setStatusName(advert.getStatus().getName());
					;
				}
			}
		}
		map.put("list", advertList);

		map.put("pageObj", pageObj);

		map.put("params", params);
		map.put("advertTypeList", AdvertTypeEnum.toList());
		map.put("advertStatusList", AdvertStatus.toList());

		return "advert/advert_list";
	}

	/**
	 * 广告编辑界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "advert_form")
	@Permission(privilege = { "prms:advert:view", "prms:advert:edit" })
	public String advert_form(ModelMap map) {
		String regionId = getString("regionId");
		String dataId = getString("dataId");
		Advert data = new Advert();
		AdvertRegion advertRegion = null;
		List<Map<String, String>> advertTypeList = AdvertTypeEnum.toList();
		try {
			if (StringUtil.isNotNull(dataId)) {
				data = advertService.findById(dataId);
				if (StringUtil.isNotNull(data.getRegion())) {
					advertRegion = advertRegionService.findById(data.getRegion().getId());
				}
			} else if (StringUtil.isNotNull(regionId)) {
				advertRegion = advertRegionService.findById(regionId);
				if (StringUtil.isNotNull(advertRegion)) {
					data = new Advert();
					data.setRegion(advertRegion);
				}
			}
		} catch (ServiceException e) {
			logger.error("", e);
			throw e;

		}
		if (StringUtil.isNotNull(advertRegion)) {
			String[] advertTypes = advertRegion.getSupportTypes().split(",");
			advertTypeList.clear();
			for (String string : advertTypes) {
				if (StringUtils.isNotBlank(string)) {
					Map<String, String> advertTypeMap = new HashMap<String, String>();
					advertTypeMap.put("value", AdvertTypeEnum.valueOf(string).toString());
					advertTypeMap.put("name", AdvertTypeEnum.valueOf(string).getName());
					advertTypeList.add(advertTypeMap);
				}
			}
		}
		map.put("advertTypeList", advertTypeList);
		map.put("data", data);
		map.put("viewtype", getString("viewtype"));
		map.put("jsessionId", this.getSession().getId());
		map.put("image_path", System.getProperty("image.web.server"));
		return "advert/advert_form";
	}

	@RequestMapping("advert_save")
	@ResponseBody
	public ResultVo advert_save(HttpServletResponse response) {// 说明：在紧跟@Valid参数添加BindingResult参数
		ResultVo resultVo = new ResultVo();
		Advert data = null;
		String dataId = this.getString("id");
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			if (StringUtil.isNotNull(dataId)) {
				data = this.advertService.findById(dataId);
			}
			data = BeanUtils.fillentity(this.getParamMap(), data, Advert.class);
			AdvertRegion arData = advertRegionService.findById(data.getRegion().getId());
			if (StringUtil.isNotNull(arData)) {
				if (StringUtil.isNotNull(data.getId())) {
					params.put("neqId", data.getId());
				}
				params.put("regionId", arData.getId());
				params.put("isBackup", "0");
				List<Advert> advertList  =  this.advertService.findByBiz(params);
				if(data.getIsBackup()!=1&&advertList!=null&&(advertList.size()>=arData.getLimitCount())){
					throw new Exception("广告超过限定数量。");
				}
				params.remove("isBackup");
				
				params.put("locationIndex", data.getLocationIndex());
				List<Advert> advertList2 = this.advertService.findByBiz(params);
				if (advertList2 != null && advertList2.size() >= 2) {
					throw new Exception("同一个广告位最多只能添加两个广告。");
				}
				if (data.getIsBackup() == 0) {
					// 当数据为元广告时，若数据库存在一条广告时
					if (advertList2 != null && advertList2.size() == 1) {
						// 若已存在的是元广告
						if (advertList2.get(0).getIsBackup() == 0) {
							throw new Exception("保存失败，原因：当前广告位已存在原始广告，只能设置替补广告。");
						}

					}
					Date now = new Date();
					if (now.compareTo(data.getStartDate()) < 0) {
						throw new Exception("保存失败，原因：原始广告的开始时间必须早于当前时间。");
					}
				} else {
					// 当数据为替补广告时,若数据库存在一条广告时
					if (advertList2 == null || advertList2.size() == 0) {
						throw new Exception("保存失败，原因：当前广告位没有设置过广告，只能设置原始广告。");
					} else if (advertList2 != null && advertList2.size() == 1) {
						// 若数已存在一条广告时
						if (advertList2.get(0).getIsBackup() == 1) {
							throw new Exception("该位置已经存在替补广告数据。");
						}

					}
					Date now = new Date();
					if (now.compareTo(data.getStartDate()) > 0) {
						throw new Exception("保存失败，原因：替补广告的开始时间必须晚于当前时间。");
					}
				}

			} else {
				throw new Exception("所属区域不存在。");
			}
			if (data.getEndDate().compareTo(data.getStartDate()) < 0) {
				throw new Exception("保存失败，原因：广告的结束时间必须晚于开始时间。");
			}
			
			// 优惠券类型广告需要拼接Url地址，用于H5页面跳转
			if (AdvertTypeEnum.COUPON_VIEW.getValue().equals(data.getAdvertType())) {
				String url = "/coupon/toCouponReceiveList.do?couponGrantId="+data.getObjId()+"&uid=";
				data.setUrl(url);
			}
			
			// 执行写操作
			if (StringUtil.isNotNull(data.getId())) {
				// 编辑
				data.setLastUpdateBy(RedisWebUtils.getLoginUser());
				data.setLastUpdateDate(new Date());
				if (data.getStatus().toString().equals(AdvertStatus.END.toString())) {
					data.setStatus(AdvertStatus.SOONSTART);
				}
				advertService.modifyById(data);
			} else {
				// 新增
				data.setCreateBy(RedisWebUtils.getLoginUser());
				data.setCreateByName(RedisWebUtils.getLoginUser().getName());
				data.setCreateDate(new Date());
				data.setStatus(AdvertStatus.SOONSTART);
				data.setId(UUIDGenerator.getUUID());
				advertService.add(data);
			}
			resultVo.setResult(ServiceResultCode.SUCCESS, "/advert/advert_list.do");
		} catch (ServiceException e) {
			logger.error("advert_save ServiceException=》广告管理-广告操作数据", e);
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(Constant.RESULT_EXC_MSG);
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("advert_save Exception=》广告管理-广告操作数据", e);
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(e.getMessage());
		}
		return resultVo;
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "advertRegion_edit")
	@Permission(privilege = { "prms:advertRegion:view", "prms:advertRegion:edit" })
	public String advertRegion_edit(ModelMap map) {
		map.put("advertTypeList", AdvertTypeEnum.toList());
		String advertRegion_id = getString("advertRegion_id");
		String dataId = getString("id");
		AdvertRegion data = null;
		if (StringUtil.isNotNull(advertRegion_id)) {
			AdvertRegion parent;
			try {
				parent = advertRegionService.findById(advertRegion_id);
				if (StringUtil.isNotNull(parent)) {
					data = new AdvertRegion();
					data.setParent(parent);
				}
			} catch (ServiceException e) {
				logger.error("", e);
				throw e;
			}
		} else if (StringUtil.isNotNull(dataId)) {
			try {
				data = advertRegionService.findById(dataId);
			} catch (ServiceException e) {
				logger.error("", e);
				throw e;
			}

		}
		map.put("data", data);
		map.put("viewtype", getString("viewtype"));
		return "advert/advertRegion_form";
	}

	@RequestMapping(value = "ajax_advertDelete")
	public void ajax_advertDelete(HttpServletResponse response) {
		try {
			String id = getString("id");
			Advert data = advertService.findById(id);
			if (StringUtil.isNotNull(data.getIsSystem()) && data.getIsSystem() == 1) {
				getOutputMsg().put("STATUS", ServiceResultCode.FAIL);
				getOutputMsg().put("MSG", "系统数据不允许删除");
//				throw new Exception("系统数据不允许删除");
			} else {
				if (StringUtil.isNotNull(data)) {
					// 查询替补广告
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("locationIndex", data.getLocationIndex());
					params.put("isBackup", 1);
					List<Advert> advertList = advertService.findByBiz(params);
					Advert alternate = new Advert();
					if (StringUtil.isNotNull(advertList) && advertList.size() > 0) {
						alternate = advertList.get(0);
					}
					// 更新广告（删除广告、修改替补广告为原始广告）
					advertService.updateAdvertDate(data, alternate);
				} else {
					throw new Exception("数据不存在");
				}
				getOutputMsg().put("STATUS", ServiceResultCode.SUCCESS);
				getOutputMsg().put("MSG", "操作成功");
			}
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

	@RequestMapping("advertRegion_save")
	@ResponseBody
	public ResultVo advertRegion_save(@Valid AdvertRegion data, BindingResult result) {// 说明：在紧跟@Valid参数添加BindingResult参数
		// 返回结果对象
		ResultVo resultVo = new ResultVo();
		// 执行更新
		try {
			if (StringUtil.isNotNull(data.getId())) {
				data.setLastUpdateBy(RedisWebUtils.getLoginUser());
				data.setLastUpdateDate(new Date());
			} else {
				data.setCreateBy(RedisWebUtils.getLoginUser());
				data.setCreateDate(new Date());
			}
			ServiceCode serviceCode = null;
			// 判断编码唯一性
			Map<String, Object> params = new HashMap<String, Object>();
			if (StringUtil.isNotNull(data.getId())) {
				params.put("neqId", data.getId());
			}
			params.put("number", data.getNumber());
			List<AdvertRegion> arList = advertRegionService.findByBiz(params);
			if (arList != null && arList.size() > 0) {
				throw new Exception("该编码已存在");
			}

			serviceCode = advertRegionService.save(data);
			resultVo.setResult(ServiceResultCode.SUCCESS, "/advert/index.do");
		} catch (ServiceException e) {
			logger.error("advertRegion_save ServiceException=》广告管理-广告区域操作数据", e);
			throw e;
		} catch (Exception e) {
			logger.error("advertRegion_save Exception=》广告管理-广告区域操作数据", e);
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(e.getMessage());
			throw new ServiceException(e);
		}
		return resultVo;
	}

	/**
	 * 查看所有广告区域
	 * 
	 * @return
	 */
	@RequestMapping(value = "ajax_advertRegionSimpleTree")
	public void ajax_advertRegionSimpleTree(HttpServletResponse response) {
		try {
			// 查询所有且不分页
			Map<String, Object> params = this.getParaMap();
			// 排序方式
			params.put("orderBy", "d.sort asc");
			List<Object> listResult = advertRegionService.getAdvertRegionSimpleTree(params);
			getOutputMsg().put("status", ServiceResultCode.SUCCESS);
			getOutputMsg().put("data", listResult);
		} catch (ServiceException e) {
			logger.error("AdvertController-ajaxList-ServiceException=》广告管理--查看所有区域-ServiceException", e);
			getOutputMsg().put("status", ServiceResultCode.FAIL);
			throw e;
		} catch (Exception e) {
			getOutputMsg().put("status", ServiceResultCode.FAIL);
			logger.error("AdvertController-ajaxList-Exception=》广告管理--查看所有区域-Exception", e);
			throw new ServiceException(e);
		}
		outPrint(response, this.getJsonByObject(this.getOutputMsg()));
	}

	/**
	 * 删除区域--逻辑删除
	 */
	@RequestMapping(value = "advertRegion_delete")
	@ResponseBody
	public ResultVo advertRegion_delete(String id, HttpSession session, String ids) {
		logger.debug("AdvertController-delete=》广告区域管理-删除");
		ResultVo resultVo = new ResultVo();
		try {
			AdvertRegion data = new AdvertRegion();
			data.setId(id);
			data=advertRegionService.findById(data);
			if (StringUtil.isNotNull(data.getIsSystem()) && data.getIsSystem() == 1) {
				resultVo.setStatus(Constant.RESULT_ERR);
				resultVo.setInfoStr("系统数据不允许删除");
				return resultVo;
			}

			advertRegionService.deleteById(data);
			resultVo.setResult(ServiceResultCode.SUCCESS, "/advert/index.do");
		} catch (ServiceException e) {
			logger.error("AdvertController-delete=》广告管理区域删除-ServiceException", e);
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(e.getMessage().toString());
			throw e;
		} catch (Exception e) {
			logger.error("AdvertController-delete=》广告管理区域删除-Exception", e);
			resultVo.setStatus(Constant.RESULT_EXC);
			resultVo.setInfoStr(e.getMessage().toString());
//			return resultVo;
			throw new ServiceException(e);
		}
		return resultVo;
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
	@RequestMapping(value = "compressUpload", method = RequestMethod.POST)
	public void compressUpload(@RequestParam(value = "image") MultipartFile file, @RequestParam(value = "fileName", required = false) String ordingName, @RequestParam(value = "direct", required = true) String dir, HttpServletResponse response) throws IOException, InterruptedException, ExecutionException {
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

			imgattr = new ImageAttribute(100, 75, true);
			list.add(imgattr);
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
	}

}
