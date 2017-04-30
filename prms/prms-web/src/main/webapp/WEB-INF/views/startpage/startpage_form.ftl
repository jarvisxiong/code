<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>编辑</title>
</head>
<body>

<div class="tab-content">
  <div class="tab-pane fade in active" id="addUser">
	<!--新增1--start-->
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/startpage/datasave.do" method="post">
	        <input type="hidden" id="id" name="id" value="${(data.id) !}">
	        <input type="hidden" id="imgPath1" name="imgPath1" value="${(data.imgPath1) !}" data-rule-required="true" data-msg-required="请上传广告图片1"/>   
	        <input type="hidden" id="imgPath2" name="imgPath2" value="${(data.imgPath2) !}" data-rule-required="true" data-msg-required="请上传广告图片2"/> 
	        <input type="hidden" id="imgPath3" name="imgPath3" value="${(data.imgPath3) !}" data-rule-required="true" data-msg-required="请上传广告图片3"/> 
	        <input type="hidden" id="imgPath4" name="imgPath4" value="${(data.imgPath4) !}" data-rule-required="false" data-msg-required="请上传广告图片4"/> 
	        <div class="addForm1">
		       <div id="error_con" class="tips-form">
		           <ul></ul>
		       </div>
	            <div class="tips-form"></div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>终端：</label>
	                    <div class="div-form">
	                    	<select id="terminal" name="terminal" class="form-control txt_mid input-sm" onchange="changeTerminal();"> 
								<option value="Android" <#if (data.terminal)??><#if data.terminal=='Android'>selected="selected"</#if></#if>>安卓</option>
								<option value="IOS" <#if (data.terminal)??><#if data.terminal=='IOS'>selected="selected"</#if></#if>>IOS</option>
							</select>
	       	 			</div>
	                </div>
	              </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>生效时间：</label>
	                    <div class="div-form">
		                    <input name="effectiveDate" id="effectiveDate" class="form-control txt_mid input-sm" data-rule-required="true" data-msg-required="生效时间不能为空" 
								onfocus="WdatePicker({minDate:'%y-%M-%d %H:%m:%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(data.effectiveDate?string('yyyy-MM-dd HH:mm:ss')) !}" >                     
	       	 			</div>
	       	 			<label><i>*</i>过期时间：</label>
	                    <div class="div-form">
		                    <input name="expiredDate" id="expiredDate" class="form-control txt_mid input-sm" data-rule-required="true" data-msg-required="过期时间不能为空" 
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'effectiveDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(data.expiredDate?string('yyyy-MM-dd HH:mm:ss')) !}">                     
	       	 			</div>
	                </div>
	            </div>
	            <div class="form-tr">
					<div class="form-td">
						<label>广告类型 ：</label>
						<div class="div-form">
							<select class="input-select2" id="advertType" name="advertType" onchange="changeAdvertType('1');">
							<#if advertTypeList ??>
                                <#list advertTypeList as item > 
                                <#if item.value != 'MENU_TYPE'>
                                <option value="${item.value}"  <#if (data.advertType) ??><#if data.advertType == item.value>selected="selected"</#if></#if> >${item.name}</option>
                                </#if>
                                </#list>
		                    </#if>
							</select>
						</div>
					</div> 
                 </div>
                 <div class="form-tr" id="tr_obj_f7">
                 	<div class="form-td" id="td_obj_f7">
                         <label><i>*</i><em id="objLabel"> </em>：</label>
                          <div class="div-form">
                          	 <div class="f7" id="obj_f7">
                                 <input type="hidden"  id="objId" name="objId" value="${(data.objId) !}"/>
                                 <input class="form-control input-sm txt_mid" 
                                 type="text" id="objName" readonly="readonly"  name="objName" value="${(data.objName) !}" data-rule-required="true" data-msg-required="关联数据不能为空" >
                                 <span class="selectDel" onclick="$(this).parent().find('input').val('');">×</span>
                                 <span class="selectBtn" id="selectBtn_obj_f7" onclick="alert('正在建设中……');">选</span>
                             </div>
                          </div>
                     </div>
                 </div>
                 <div class="form-tr" id="td_obj_web" style="display:none;">
                 	<div class="form-td">
                        <label><i>*</i>WEB链接：</label>
                        <div class="div-form"><input class="form-control input-sm txt_mid"
                          type="text" id="url" name="url" value="${(data.url) !}" data-rule-required="false" data-msg-required="WEB链接不能为空" />
           	 			</div>
                    </div>
                 </div>
                 <div class="form-tr" id="td_obj_secondView" style="display:none;">
                     <div class="form-td">
                     	<label><i>*</i>会场选择：</label>
                     	<select id="secondViewType" onchange="getSecondViewType(this);">
                               <option value="FFCHD_1" <#if (data.objId) ??><#if data.objId== 'FFCHD_1'>selected="selected"</#if></#if>>第一分会场</option>
                               <option value="FFCHD_2" <#if (data.objId) ??><#if data.objId== 'FFCHD_2'>selected="selected"</#if></#if>>第二分会场</option>
                               <option value="FFCHD_3" <#if (data.objId) ??><#if data.objId== 'FFCHD_3'>selected="selected"</#if></#if>>第三分会场</option>
                               <option value="FFCHD_4" <#if (data.objId) ??><#if data.objId== 'FFCHD_4'>selected="selected"</#if></#if>>第四分会场</option>
                               <option value="FFCHD_5" <#if (data.objId) ??><#if data.objId== 'FFCHD_5'>selected="selected"</#if></#if>>第五分会场</option>
                               <option value="FFCHD_6" <#if (data.objId) ??><#if data.objId== 'FFCHD_6'>selected="selected"</#if></#if>>第六分会场</option>
                               <option value="ZHC" <#if (data.objId) ??><#if data.objId== 'ZHC'>selected="selected"</#if></#if>>主会场</option>
						</select>
					  </div>
                 </div>
                 <!-- 安卓 -->
	             <div class="form-tr" id="Android_img1" style="display:none;">
					<div class="form-td" style="margin-left: 4%;">
						<label style="clear: both;width: 100%;"><i>*</i>广告图片（480*800）：<span style="color: red;">请上传尺寸为480*800的图片</span></label>
						<div class="div-form" style="clear: both;margin-left: 10%;"> 
							<div style="border:1px solid #ccc; width:100px; height:100px;">
                                <img id="Android1_img" style="width:100px; height:100px;" 
                                <#if (data.imgPath1)?? >src="${(image_path)!}/${data.imgPath1?replace('size','origin')}"<#else>src=""</#if> 
                                  onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
                            </div>
                           	<input  type="hidden" id="Android1" name="Android1" value="${(data.imgPath1) !}">
                           	<input type="hidden"  id="androidBtn1">
                           	<span style="color: red;">图片大小200KB以内，图片格式为jpg、png</span>
                         </div>
                        
					</div>
	            </div>
	            <div class="form-tr" id="Android_img2" style="display:none;">
					<div class="form-td" style="margin-left: 4%;">
						<label style="clear: both;width: 100%;"><i>*</i>广告图片（720*1280）：<span style="color: red;">请上传尺寸为720*1280的图片</span></label>
						<div class="div-form" style="clear: both;margin-left: 10%;"> 
							<div style="border:1px solid #ccc; width:100px; height:100px;">
                                <img id="Android2_img" style="width:100px; height:100px;" 
                                <#if (data.imgPath2)?? >src="${(image_path)!}/${data.imgPath2?replace('size','origin')}"<#else>src=""</#if> 
                                  onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
                            </div>
                            <input  type="hidden" id="Android2" name="Android2" value="${(data.imgPath2) !}">
                           	<input type="hidden"  id="androidBtn2">
                           	<span style="color: red;">图片大小200KB以内，图片格式为jpg、png</span>
                         </div>
                         
					</div>
	            </div>
	            <!-- 安卓 -->
	            
	            <!-- IOS -->
	            <div class="form-tr" id="IOS_img1" style="display:none;">
					<div class="form-td" style="margin-left: 4%;">
						<label style="clear: both;width: 100%;"><i>*</i>广告图片（640*960）：<span style="color: red;">请上传尺寸为640*960的图片</span></label>
						<div class="div-form" style="clear: both;margin-left: 10%;"> 
							<div style="border:1px solid #ccc; width:100px; height:100px;">
                                <img id="IOS1_img" style="width:100px; height:100px;" 
                                <#if (data.imgPath1)?? >src="${(image_path)!}/${data.imgPath1?replace('size','origin')}"<#else>src=""</#if> 
                                  onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
                            </div>
                            <input  type="hidden" id="IOS1" name="IOS1" value="${(data.imgPath1) !}">
                           	<input type="hidden"  id="IOSBtn1">
                           	<span style="color: red;">图片大小200KB以内，图片格式为jpg、png</span>
                         </div>
                         
					</div>
	            </div>
	            <div class="form-tr" id="IOS_img2" style="display:none;">
					<div class="form-td" style="margin-left: 4%;">
						<label style="clear: both;width: 100%;" ><i>*</i>广告图片（640*1136）：<span style="color: red;">请上传尺寸为640*1136的图片</span></label>
						<div class="div-form" style="clear: both;margin-left: 10%;"> 
							<div style="border:1px solid #ccc; width:100px; height:100px;">
                                <img id="IOS2_img" style="width:100px; height:100px;" 
                                <#if (data.imgPath2)?? >src="${(image_path)!}/${data.imgPath2?replace('size','origin')}"<#else>src=""</#if> 
                                  onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
                            </div>
                            <input  type="hidden" id="IOS2" name="IOS2" value="${(data.imgPath2) !}">
                           	<input type="hidden"  id="IOSBtn2">
                           	<span style="color: red;">图片大小200KB以内，图片格式为jpg、png</span>
                        </div>
                        
					</div>
	            </div>
	            <div class="form-tr" id="IOS_img3" style="display:none;">
					<div class="form-td" style="margin-left: 4%;">
						<label style="clear: both;width: 100%;" ><i>*</i>广告图片（750*1334）：<span style="color: red;">请上传尺寸为750*1334的图片</span></label>
						<div class="div-form" style="clear: both;margin-left: 10%;"> 
							<div style="border:1px solid #ccc; width:100px; height:100px;">
                                <img id="IOS3_img" style="width:100px; height:100px;" 
                                <#if (data.imgPath3)?? >src="${(image_path)!}/${data.imgPath3?replace('size','origin')}"<#else>src=""</#if>  
                                onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
                            </div>
                            <input  type="hidden" id="IOS3" name="IOS3" value="${(data.imgPath3) !}">
                            <input type="hidden"  id="IOSBtn3">
                            <span style="color: red;">图片大小200KB以内，图片格式为jpg、png</span>
                        </div>
                       
					</div>
	            </div>
	            <!-- IOS -->
	            
	            <!-- 安卓、IOS共有 -->
	            <div class="form-tr">
					<div class="form-td" style="margin-left: 4%;">
						  <label style="clear: both;width: 100%;" class="descLabel"><i>*</i>广告图片（1080*1920）：<span style="color: red;">请上传尺寸为1080*1920的图片</span></label> 
						<div class="div-form" style="clear: both;margin-left: 10%;"> 
							<div style="border:1px solid #ccc; width:100px; height:100px;">
                                <#if (data.terminal)??><#if data.terminal=='Android'>
                                	<#if (data.imgPath3)?? >
                                	<img id="imgPath_img" style="width:100px; height:100px;" 
                                	src="${(image_path)!}/${data.imgPath3?replace('size','origin')}" onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
                                	</#if> 
                                <#else>
                                 	<#if (data.imgPath4)?? >
                                 	<img id="imgPath_img" style="width:100px; height:100px;" 
                                	src="${(image_path)!}/${data.imgPath4?replace('size','origin')}" onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
                                 	</#if>
                                </#if>
                                <#else><img id="imgPath_img" style="width:100px; height:100px;" 
                                	src="" onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
                                </#if>
                            </div>
                            	<#if (data.terminal)??><#if data.terminal=='Android'>
                            		<input  type="hidden" id="imgPath" name="imgPath" value="${(data.imgPath3)! }">
                                <#else>
                                	<input  type="hidden" id="imgPath" name="imgPath" value="${(data.imgPath4)! }">
                                </#if>
                                <#else><input  type="hidden" id="imgPath" name="imgPath" value="">
                                </#if>
                           	<input type="hidden"  id="photoPathBtn">
                           	<span style="color: red;">图片大小200KB以内，图片格式为jpg、png</span>
                        </div>
                        
					</div>
				</div>
	            <div class="form-tr">
	                <div class="btn-div2">
	                 	 <a onclick="buttonsubmit()" class="btn btn-primary" >保存</a>
	                    <input type="button" class="btn btn-default" value="返回" onclick="goBack()">
	                </div>
	            </div>
	        </div>
	       </form>
	    </div>
	</div>
  </div>
</div>
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">

<script src="${BasePath !}/asset/js/common/util.js"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/startpage/startpage_form.js"></script>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/swfupload.js"></script>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/plugins/swfupload.queue.js"></script>
<script type="text/javascript">
	var image_path = "${image_path}";
</script>
</body>
</html>