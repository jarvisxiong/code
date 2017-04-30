/**
 * 
 */
package com.ffzx.portal.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.remoting.ExecutionException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.ffzx.commerce.framework.controller.BaseController;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.FTPUtil;
import com.ffzx.commerce.framework.utils.ImageAttribute;
import com.ffzx.commerce.framework.utils.ImageUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.commerce.framework.utils.UUIDGenerator;
import com.ffzx.parentweb.controller.UploadImageController;

/**
 * 
 * @author ying.cai
 * @date 2017年4月14日下午1:46:26
 * @email ying.cai@ffzxnet.com
 * @version V1.0
 */
@Controller
@RequestMapping("/portal")
public class UplaodImageController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(UploadImageController.class);
	@Autowired
	private MessageChannel ftpUploadChannel;
	private GenericService dictApiService;

	@RequestMapping(value={"upload"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	public void upload(@RequestParam("file") MultipartFile file, @RequestParam(value="fileName", required=false) String ordingName, @RequestParam(value="sizeType", required=false) String sizeType, String path, HttpServletResponse response)
	     throws IOException, InterruptedException, ExecutionException{
		Map<String, Object> output = new HashMap();
	    try{
	       byte[] bytes = file.getBytes();
	       String fileName = file.getOriginalFilename();
	       String suffix = fileName.substring(fileName.lastIndexOf("."));
	       List<ImageAttribute> list = new ArrayList();
	       String savePath = DateUtil.formatMonthDate(new Date());
	 
	       	 ImageAttribute imgattr = new ImageAttribute(null, null, true);
	       	 list.add(new ImageAttribute(150, 150, true));
	       	 list.add(new ImageAttribute(499, 334, true));
	       	 list.add(new ImageAttribute(1000, 700, true));
	       	 list.add(new ImageAttribute(1180, 840, true));
	       	list.add(imgattr);
	       	 String imgType = null;
	       	  if(null!=sizeType){
	       		  imgType = getDictByType("sys_img_type", sizeType);
	       	  }
	    	  if ((StringUtil.isNotNull(imgType)) && (!imgType.equals("-1"))){
		          String[] sizeList = imgType.split(";");
		           
		          for (String string : sizeList) {
		        	  String[] itemSize = string.split("X");
		        	  imgattr = new ImageAttribute(Integer.valueOf(Integer.parseInt(itemSize[0].trim())), Integer.valueOf(Integer.parseInt(itemSize[1].trim())), true);
		        	  list.add(imgattr);
		          }
		          savePath = sizeType + "/" + DateUtil.formatMonthDate(new Date());
		       }
	       if (StringUtil.isNotNull(path)) {
	    	   savePath = savePath + "/" + path;
	       }
	       
	       savePath = "new_2016/" + savePath;
	       
	       String imgUrl = compressSave(bytes, savePath, suffix, list);
	 
	       String imgPath = "/" + savePath + "/" + imgUrl + "_size" + suffix;
	       if (StringUtils.isBlank(imgUrl)) {
	       		output.put("status", "-1");
	       		output.put("infoStr", "上传文件失败");
	       		output.put("path", imgPath);
	       		output.put("imgPath", System.getProperty("image.web.server") + imgPath);
	      } else {
	    	  	output.put("status", "0");
	    	  	output.put("infoStr", "上传文件成功");
	    	  	output.put("path", imgPath);
	    	  	output.put("imgPath", System.getProperty("image.web.server") + imgPath);
	      }
	   }catch (Exception e) {
       output.put("status", "1");
	       output.put("infoStr", "上传文件失败");
	       logger.error("UploadImageController-upload=》图片上传-ServiceException", e);
	   }
	   responseWrite(response, getJsonByObject(output));
	}
	
	
	public String getDictByType(String type, String label) {
		try {
			Map<?, ?> genericResult = (Map) dictApiService.$invoke("getDictByParams",
					new String[] { "java.lang.String", "java.lang.String" }, new Object[] { type, label });
			ResultDto resultDto = new ResultDto();
			resultDto.setCode((String) genericResult.get("code"));
			resultDto.setMessage((String) genericResult.get("message"));

			if ((resultDto != null) && (resultDto.getCode().equals("0"))) {
				return (String) genericResult.get("data");
			}
		} catch (Exception e) {
			logger.error("UploadImageController-getDictByType-Exception=》-获取图片类型-Exception", e);
		}
		return null;
	}
	
	public String compressSave(byte[] bytes, String path, String suffix, List<ImageAttribute> list) {
		String fileId = UUIDGenerator.getUUID();
		Boolean result = Boolean.valueOf(false);
		for (ImageAttribute imgattr : list) {

			byte[] compressedImage = ImageUtil.resize(bytes, imgattr.getWidth(), imgattr.getHeight(),
					imgattr.isEqualRatio());
			String nameSuffix ="";
			if(null==imgattr.getWidth()){
				nameSuffix = "_origin";
			}else{
				nameSuffix = "_" + imgattr.getWidth() + "X" + imgattr.getHeight();
			}

			String tmpname = fileId + nameSuffix + suffix;

			String targetFilePath = path;

			try {
				result = Boolean.valueOf(FTPUtil.ftpUpload(ftpUploadChannel, compressedImage, tmpname, targetFilePath));
				if (!result.booleanValue()) {
					break;
				}
			} catch (Exception e) {
				logger.error("UploadImageController-compressSave=》图片上传-compressSave-ServiceException", e);
				result = Boolean.valueOf(false);
				break;
			}
		}
		if (result.booleanValue()) {
			return fileId;
		}
		return null;
	}
}
