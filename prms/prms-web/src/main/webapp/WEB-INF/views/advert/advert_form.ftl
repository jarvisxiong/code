<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>广告</title>
</head>
<body>
            <ul class="nav nav-tabs" style="padding-left: 1%;">
                <li class="active"><a href="#">广告<#if data ??>
                	<#if viewtype??><#if viewtype=='1'>查看<#else>编辑</#if><#else>编辑</#if>
                	<#else>添加</#if></a></li>
            </ul>

           <div class="tab-content">
               <div class="tab-pane fade in active" id="addUser">

                   <!--新增1--start-->
                   <div class="row">
                       <div class="col-lg-10 col-md-12 col-sm-12">
                           <form id="myform" action="${BasePath !}/advert/advert_save.do" method="post">
                           <input type="hidden" id="id" name="id" value="${(data.id) !}"> 
                           <input type="hidden" id="activityId" name="activityId" value="${(data.activityId) !}"> 
                                              
                           <div class="addForm1">
		                        <div id="error_con" class="tips-form">
		                            <ul></ul>
		                        </div>
                               <div class="tips-form"></div>
                               <div class="form-tr">
                                   <div class="form-td">
                                       <label><i>*</i>广告名称/标题：</label>
                                       <div class="div-form" width="500px;"><input width="500px;" class="form-control input-sm txt_mid"
                                         type="text" id="title" name="title" value="${(data.title) !}" data-rule-required="true" data-msg-required="名称不能为空" />
                          	 			</div>
                                   </div>
                               </div>
                                <div class="form-tr">
                                   <div class="form-td">
                                       <label><i>*</i>广告区域：</label>
                                       
                                        <div class="div-form">
                                        <div class="f7" >
                                               <input type="hidden"  id="regionId" name="region.id" value="${(data.region.id) !}"/>
                                               <input class="form-control input-sm txt_mid" 
                                               type="text" id="regionName" readonly="readonly"  name="region.name" value="${(data.region.name) !}" data-rule-required="true" data-msg-required="广告区域不能为空">
                                               <!-- <span class="selectDel" onclick="$(this).parent().find('input').val('');">×</span> 
                                               <span class="selectBtn" onclick='simpleTreeDataPiker("选择区域","/advert/ajax_advertRegionSimpleTree.do","regionName","regionId")'>选</span> -->
                                           </div>
                                        </div>
                                   </div>
                                   <div class="form-td">
                                       <div class="div-form">
                                       <div id="advertTypes_checkboxs" class="checkbox wMonospaced">
                                                <label class="checkbox-inline" style="width: auto;" onclick="checkisBackup();">
                                            	<input name="input_isBackup"  id="input_isBackup" type="checkbox"   <#if (data.isBackup) ??><#if data.isBackup == 1>checked="checked"</#if></#if> /> 替补广告
                                            	<input name="isBackup"  id="isBackup" type="hidden"  value="${data.isBackup!'0'}"  />
                                             </label>
                                        </div>
                                        </div>
                               		</div>
                               </div>
                               <div class="form-tr">
                                   <div class="form-td">
                                       <label><i>*</i>编码/序号：</label>
                                       <div class="div-form"><input class="form-control input-sm txt_mid"
                                         type="text" id="locationIndex" name="locationIndex" value="${(data.locationIndex) !}" onkeyup="onlyInputNum(this);" data-rule-required="true" data-msg-required="编码/序号不能为空"/>
                          	 			</div>
                                   </div>
                                   <div class="form-td">
                                       <div class="div-form">
                                       <div id="advertTypes_checkboxs" class="checkbox wMonospaced">
                                                <label class="checkbox-inline" style="width: auto;" onclick="checkSystem();">
                                            	<input name="input_isSystem"  id="input_isSystem" type="checkbox"  <#if (data.isSystem) ??><#if data.isSystem == 1>checked="checked"</#if></#if>  /> 系统数据 
                                            	<input name="isSystem"  id="isSystem" type="hidden" value="${(data.isSystem)! }" />
                                             </label>
                                        </div>
                                        </div>
                               		</div>
                               </div>  
                               <div class="form-tr">
                                   <div class="form-td">
                                       <label><i>*</i>开始时间：</label>
                                       <div class="div-form"> 
                                        <input name="startDate" id="startDate" class="form-control input-sm txt_mid"   readonly="readonly"
                                        data-rule-required="true" data-msg-required="开始时间不能为空"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                            value="<#if (data.startDate) ??>${(data.startDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if>" /> 
                                        </div>
                                   </div>
                                   <div class="form-td">
                                       <label><i>*</i>结束时间：</label>
                                       <div class="div-form"> 
                                        <input name="endDate" id="endDate" class="form-control input-sm txt_mid"   readonly="readonly"
                                        data-rule-required="true" data-msg-required="结束时间不能为空" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                            value="<#if (data.endDate) ??>${(data.endDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if>"> 
                                        </div>
                                   </div>
                               </div>  
                               <div class="form-tr">
                               <div class="form-td">
								<label>广告类型 ：</label>
								<div class="div-form">
								<select class="input-select2" id="advertType" name="advertType" onchange="changeAdvertType();">
								 <#if advertTypeList ??>
                                            <#list advertTypeList as item > 
                                            <option value="${item.value}"  <#if (data.advertType) ??><#if data.advertType == item.value>selected="selected"</#if></#if> >${item.name}</option>
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
                                               type="text" id="objName" readonly="readonly"  name="objName" value="${(data.objName) !}" data-rule-required="false" data-msg-required="关联数据不能为空" >
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
								<div class="form-tr" id="tr_img" >
	                                    <div class="form-td" id="td1_img">
	                                        <label>广告图片1：</label>
	                                        <div class="div-form"> 
	                                        	<div style="border:1px solid #ccc; width:
px; height:100px;">
					                                <img id="photoPath_img" style="width:100px; height:100px;" <#if (data.photoPath)?? >src="${(image_path)!}/${data.photoPath?replace('size','origin')}"</#if>   onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
					                            </div>
	                                        	<input  type="hidden" id="photoPath" name="photoPath" value="${(data.photoPath) !}"  data-rule-required="true" data-msg-required="请上传广告图片1" >
	                                        		<input type="hidden"  id="photoPathBtn">
	                                        	</div>
	                                        	<br><span style="color: red;  ">注：图片内存大小，200KB以内</span>   
	                                    </div>
	                                     <div class="form-td" id="td2_img">
	                                        <label>广告图片2：</label>
	                                        <div class="div-form"> 
	                                        	<div style="border:1px solid #ccc; width:100px; height:100px;">
					                                <img id="photoPath2_img" style="width:100px; height:100px;" <#if (data.photoPath2)?? >src="${(image_path)!}/${data.photoPath2?replace('size','origin')}"</#if> onerror="this.src='${BasePath !}/asset/img/noPic.jpg'"/>
					                            </div>
	                                        	<input  type="hidden" id="photoPath2" name="photoPath2" value="${(data.photoPath2) !}"  data-rule-required="false" data-msg-required="请上传广告图片2"  />
	                                        		<input type="hidden"  id="photoPathBtn2">
	                                        	</div>
	                                    </div>
	                                </div>
                               <div class="form-tr">
                                   <div class="form-td">
                                       <label>备注：</label>
                                       <div class="div-form" width="250px">
                                         <textarea  style="height:98px;width:98%" id="remarks" name="remarks">${(data.remarks) !}</textarea>
                                        </div>
                                   </div>
                               </div>                
                               <div class="form-tr">
                                   <div class="orm-group btn-div">
                                 	   <#if viewtype??>
                                 	   <#if viewtype == '1'> 
                                 	   <input type="button" class="btn btn-default" id ="toBack" value="返回">
                                 	   <#else>
                                 	   <input type="button" onclick="submit_form();" class="btn btn-primary" value="保存"/>
                                 	   <input type="button" class="btn btn-default" value="返回" onclick="back()">
                                 	   </#if>  
                                 	   <#else>
                                 	   <input type="button" onclick="submit_form();" class="btn btn-primary" value="保存"/>
                                 	   <input type="button" class="btn btn-default" value="返回" onclick="back()">
                                 	   </#if>                     
                                   </div>
                               </div>
                           </div>
                           </form>
                       </div>                   </div>
                   <!--新增1--end-->
                                      
               </div>
           </div>
   
<#include "../common/select.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<script src="${BasePath !}/asset/js/common/util.js?v=${ver !}"></script>
<script src="${BasePath !}/asset/js/control/validation/additionalMethods.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/swfupload.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/plugins/swfupload.queue.js?v=${ver !}"></script>

<script type="text/javascript">
var image_path = "${image_path}";
    $(function(){
        executeValidateFrom('myform');  
        $(".input-select2").select2();
        showByAdvertType();
        initUploadImage(showImg1,"advertImage","photoPathBtn",200);	
        initUploadImage(showImg2,"advertImage","photoPathBtn2",200);
        // 初始化 查看页面 返回 按钮
        $("#toBack").click(function(){window.location.href= rootPath + "/advert/advert_list.do";return false});
        // 如果广告类型是 会场类型
        if($("#advertType").val()=="SECOND_VIEW"){
        	// 初始化会场类型下拉框
        	getSecondViewType($("#secondViewType"));
        }
    });
    function checkSystem(){
    	if($('#input_isSystem').is(':checked')) {
    		$('#isSystem').val(1);
    	}else{
    		$('#isSystem').val(0);
    	}
    }
    function checkisBackup(){
    	if($('#input_isBackup').is(':checked')) {
    		$('#isBackup').val(1);
    	}else{
    		$('#isBackup').val(0);
    	}
    }
    
    // 下拉框change事件
    function changeAdvertType(){
    	$("#obj_f7").find('input').val('');
    	showByAdvertType();
    }
    
    function showByAdvertType(){
    	var advertType = $("#advertType").val();
    	var adevertTypeName = $("#advertType").find("option:selected").text();
    	$("#objLabel").html(adevertTypeName); //修复bug4779
		if(advertType=='MENU_TYPE' || advertType =='BACKGROUND_IMAGE'){
			$("#objName").attr("data-rule-required","false");
	    	$("#objId").attr("data-rule-required","false");
    	}else{
    		$("#objName").attr("data-rule-required","true");
        	$("#objId").attr("data-rule-required","true");
    	}   
    	$("#url").attr("data-rule-required","false");
    	$("#photoPath2").attr("data-rule-required","false");
    	$("#td2_img").hide();
    	$("#tr_obj_f7").hide();
    	if(!$("#td_obj_web").is(":hidden")){
    		$("#td_obj_web").hide();
    	} 
    	if(!$("#td_obj_secondView").is(":hidden")){
    		$("#td_obj_secondView").hide();
    	}
    	$("#url").attr("data-rule-required","false");
    	/* 
    		PRE_SALE("预售","PRE_SALE"),
	PANIC_BUY("抢购","PANIC_BUY"),
	NEWUSER_VIP("新手专享","NEWUSER_VIP"),
	WHOLESALE_MANAGER("批发","WHOLESALE_MANAGER"),
	ORDINARY_ACTIVITY("普通活动","ORDINARY_ACTIVITY");
    	*/
    	if(advertType=="CATEGORY_LIST"){
    		//商品类目
    		$("#tr_obj_f7").show();
    		//simpleTreeDataPiker("选择区域","/advert/ajax_advertRegionSimpleTree.do","regionName","regionId")
    		
    		//$("#selectBtn_obj_f7").attr("onclick",'showTreeAndisParent("请选择商品类别","/dataPicker/ajax_categorySimpleTree.do","objName","objId","","objId")');
    		$("#selectBtn_obj_f7").attr("onclick",'simpleTreeDataPiker("请选择商品类别","/dataPicker/ajax_categorySimpleTree.do")');
    	}else if(advertType=="PRESALE_LIST"){
    		//预售列表
    		//$("#tr_obj_f7").show();
    		//$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activity_dataPicker', '预售列表', '${BasePath}/dataPicker/activity_dataPicker.do?activityType=PRE_SALE', '700', '550');");
    		$("#obj_f7").find('input').val('');
    		$("#objName").attr("data-rule-required","false");
    		$("#objId").attr("data-rule-required","false");
    	}else if(advertType=="PRESALE_DETAIL"){
    		$("#tr_obj_f7").show();
    		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activityCommodity_dataPicker', '预售详情', '${BasePath}/dataPicker/activityCommodity_dataPicker.do?activityType=PRE_SALE', '700', '550');");
    	}else if(advertType=="PANICBUYING_LIST"){
    		//抢购列表
    		//$("#tr_obj_f7").show();
    		//$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activity_dataPicker', '抢购列表', '${BasePath}/dataPicker/activity_dataPicker.do?activityType=PANIC_BUY', '700', '550');");
    		$("#obj_f7").find('input').val('');
    		$("#objName").attr("data-rule-required","false");
    		$("#objId").attr("data-rule-required","false");
    	}else if(advertType=="PANICBUYING_DETAIL"){
    		//抢购详情
    		$("#tr_obj_f7").show();
    		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activityCommodity_dataPicker', '抢购详情', '${BasePath}/dataPicker/activityCommodity_dataPicker.do?activityType=PANIC_BUY', '700', '550');");
    	}else if(advertType=="ACTIVITY_LIST"){
    		//活动列表
    		$("#tr_obj_f7").show();
    		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activity_dataPicker', '活动列表', '${BasePath}/dataPicker/activity_dataPicker.do?activityType=ORDINARY_ACTIVITY', '700', '550');");
    	}else if(advertType=="WHOLESALE_LIST"){
    		//批发列表
    		$("#tr_obj_f7").show();
    		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activity_dataPicker', '批发列表', '${BasePath}/dataPicker/activity_dataPicker.do?activityType=WHOLESALE_MANAGER', '700', '550');");
    	}else if(advertType=="NEWUSER_LIST"){
    		//新用户专享
    		$("#tr_obj_f7").show();
    		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activity_dataPicker', '新用户列表', '${BasePath}/dataPicker/activity_dataPicker.do?activityType=NEWUSER_VIP', '700', '550');");
    	}else if(advertType=="ORDINARYGOODS_DETAIL"){
    		//普通商品详情
    		$("#tr_obj_f7").show();
    		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('commodity_dataPicker', '商品列表', '${BasePath}/dataPicker/commodity_dataPicker.do', '600', '550');");
    	}else if(advertType=="MENU_TYPE"){
    		//菜单类型
    		$("#td2_img").show();
    		$("#photoPath2").attr("data-rule-required","false");
    	}else if(advertType=="BACKGROUND_IMAGE"){
    		//背景图片
    		$("#obj_f7").find('input').val('')
    		$("#objName").attr("data-rule-required","false");
    		$("#objId").attr("data-rule-required","false");
    		
    	}else if(advertType=="WEB_LINK"){
    		//WEB链接
    		$("#obj_f7").find('input').val('')
    		$("#objName").attr("data-rule-required","false");
    		$("#objId").attr("data-rule-required","false");
    		$("#td_obj_web").show();
    		$("#url").attr("data-rule-required","true");
    	}else if(advertType=="SECOND_VIEW"){//会场广告（二级页面）
    		$("#obj_f7").find('input').val('')
    		$("#objName").attr("data-rule-required","false");
    		$("#objId").attr("data-rule-required","false");
    		$("#objId").val("FFCHD_1");
    		$("#objName").val("第一分会场");
    		$("#td_obj_secondView").show();
    	}else if(advertType=="COUPON_VIEW"){
    		//优惠券
    		$("#tr_obj_f7").show();
    		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('coupon_dataPicker', '选择优惠券', '${BasePath}/dataPicker/coupon_dataPicker.do', '1000', '550');");
    	}
    }
   	function getSecondViewType(obj){
   		$("#objId").val($(obj).val());
		$("#objName").val($(obj).find("option:selected").text());
   	}
    function getSelectCommodity(obj,dlg){
    	var  id = $(obj).attr("id");
    	var  title = $(obj).attr("title");
    	$("#title").val(title);
    	$("#objId").val(id);
    	$("#objName").val(title);
    	dlg.click();
    }
    
    function getSelectAcitvity(obj,dlg){
    	var  id = $(obj).attr("id");
    	var  title = $(obj).attr("title");
    	$("#title").val(title);
    	$("#objId").val(id);
    	$("#objName").val(title);
    	dlg.click();
    }
    function getSelectAcitvityCommodity(obj,dlg){
    	var  id = $(obj).attr("id");
    	var  title = $(obj).attr("title");
    	var  activityId = $(obj).attr("activityId");
    	
    	$("#title").val(title);
    	$("#objId").val(id);
    	$("#objName").val(title);
    	$("#activityId").val(activityId);
    	dlg.click();
    }
    // 获取优惠券信息
    function getSelectCouponGrant(obj,dlg){
    	var  id = $(obj).attr("id");
    	var  title = $(obj).attr("title");
    	$("#title").val(title);
    	$("#objId").val(id);
    	$("#objName").val(title);
    	dlg.click();
    }
    
    //设置为查看
    var viewtype = "${viewtype!'0'}";
    if(viewtype == '1'){
        $('form').find('input').attr("readonly","readonly");
        $('form').find('radio').attr("readonly","readonly");
        $('form').find('select').attr("readonly","readonly");
    }
    function submit_form(){
    	var photoPath = $("#photoPath").val();
    	if(photoPath==null||photoPath==""){
    		$.frontEngineDialog.executeDialogContentTime('请上传广告图片1');
    		return;
    	}
    	$("#myform").submit();
    }

    
  
    //图片上传成功   业务处理事件
    function showImg1(data){	
    	var res = eval('(' + data + ')');	
    	if(res.status == "0"){
    		$("#photoPath").val(res.path);		//path  图片地址用于数据库存储
    		$("#photoPath_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
    	}
    	
    	//上传成功
    	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
    }
  //图片上传成功   业务处理事件
    function showImg2(data){	
    	
    	var res = eval('(' + data + ')');	
    	if(res.status == "0"){
    		$("#photoPath2").val(res.path);		//path  图片地址用于数据库存储
    		$("#photoPath2_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
    	}
    	
    	//上传成功
    	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
    }
    
  //类目
  
      function simpleTreeDataPiker(title,url) {
    	var tree_setting = {
    		data : {// 数据
    			simpleData :{
    				enable: true,
    				idKey: "id",
    				pIdKey: "pId",
    				rootPId: null
    			},
    			key : {
    				url : ""
    			}
    		},
    		view:{
    			selectedMulti: false
    		}
    	};
    	
    	$.post(rootPath+ url,{},function(res){
    		if(res.status=="SUCCESS"){
				var content = '<div style="height: 350px;    overflow: scroll;" class="zTreeDemoBackground left"><ul id="show_tree" class="ztree"></ul></div>';
				$.frontEngineDialog.executeDialog(
						'showTree',
						title,
						content,
						"400px",
						"350px",
						function(){
							var treeObj = $.fn.zTree.getZTreeObj("show_tree");
							var nodes = treeObj.getSelectedNodes();
							if(nodes != null && nodes != ""){
								/* if(nodes[0].isParent == true){
									art_tips("请选择叶子节点",2000);
									return false;
								}else{
								} */
								$("#title").val(nodes[0].name);
								$("#objName").val(nodes[0].name);
								$("#objId").val(nodes[0].id);
			                    return true;
							}
						}
				);

				$.fn.zTree.init($("#show_tree"), tree_setting,res.data);
    		}
    	},'json');
    }
 
      var upload;
      var uploadImages = {};


      /**
       * 图片上传公共方法   --需初始化绑定浏览按钮
       * @param showImg		上传成功后  执行的js方法  用户自定义（必填）		
       * @param type			上传图片的类型====在基础数据系统-数据字典中设置-上传图片类型中添加（必填）		
       * @param button_id		上图图片的按钮（必填）	
       * @param file_size     图片大小,单位kb，默认50（选填）
       * @param path			追加自定义path（选填) 
       * 		最终URL：type/时间/path/name.jsp 			
       */
      function initUploadImage(showImg,type,button_id,file_size,path){
      	var IMAGE_SIZE_HUNDRED = "50";
      	if(file_size != null && file_size!=''){
      		IMAGE_SIZE_HUNDRED = file_size;
      	}
      	if(path != null && path!=''){
      		path = "path:" + path;
      	}else{
      		path = "";
      	}
      	var upload = new SWFUpload({
      			upload_url: rootPath + '/uploadImage/upload.do;jsessionid='+$('#jsessionid').val(),
      			file_post_name:'image',
      			post_params: {type:type,path:path},
      			file_size_limit : IMAGE_SIZE_HUNDRED+"kb",
      			file_types : "*.jpg;*.gif;*.png",
      			file_types_description : "images/*",
      			file_upload_limit : "0",
      			file_queue_limit : "0",
      			file_queued_handler: function(file){
//      				$("#loading_firstPicName").css("display","inline");
      				var f;
      				f = $('<dd></dd>').appendTo($("#DL_"+type));
      				$('<dt><img src="'+rootPath+'/asset/css/images/loading44.gif" /></dt>').appendTo(f);
      			uploadImages[file.id] = f;
      			},
      			file_queue_error_handler : function(file, errorCode, message){
      				try {
      					if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
      						$.frontEngineDialog.executeDialogContentTime('上传的文件太多啦');
      						return;
      					}
      					switch (errorCode) {
      						case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
      							$.frontEngineDialog.executeDialogContentTime('<br>上传失败:<br><br>&nbsp;所选择的图片中，存在内存大小超出设定范围的图片');
      							break;
      						case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
      							$.frontEngineDialog.executeDialogContentTime('上传的文件为空:'+file.name);
      							break;
      						case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
      							$.frontEngineDialog.executeDialogContentTime('上传的文件类型不符:'+file.name);
      							break;
      						case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
      							$.frontEngineDialog.executeDialogContentTime('上传的文件太多啦:'+file.name);
      							break;
      						default:
      							$.frontEngineDialog.executeDialogContentTime('未知错误');
      							break;
      					}
      				} catch (ex) {
      			        this.debug(ex);
      			    }
      			},
      			file_dialog_complete_handler : function(selectNum,queueNum){
      				if(selectNum > 0 && queueNum > 0){
      					upload.startUpload();
      				}
      			},
      			upload_start_handler : function(){
      				//上传开始
      			},
      			upload_progress_handler : function(file, bytesComplete, bytesTotal){
      				//上传进程
      				//var percent = getFormatPercent((bytesComplete/bytesTotal).toFixed(4)*100)+"%";
      				if(bytesComplete == bytesTotal){
      				}
      			},
      			upload_error_handler : function(file, errorCode, message){
      				$.frontEngineDialog.executeDialogContentTime("图片上传报错，请重新上传！异常详细信息："+message);
      			},
      			upload_success_handler : function(file, serverData, responseReceived){
      				//上传成功回调
      				var serverDataJson = eval('(' + serverData + ')');
      				serverDataJson.buttonId = button_id;
      				serverData = JSON.stringify(serverDataJson);
      				showImg(serverData);
      			},
      			upload_complete_handler : null,
      			button_image_url:rootPath+"/asset/css/images/liulan_btn.png",
      			button_placeholder_id : button_id,
      			button_width: 48,
      			button_height: 22,
      			button_cursor:SWFUpload.CURSOR.HAND,
      			flash_url : rootPath+"/asset/js/control/SWFUpload/Flash/swfupload.swf",
      			custom_settings : {
      			},
      			debug: false
      		});
      }
       function back(){
    	   console.log("back");
   			$.frontEngineDialog.executeDialog('isReturn_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否确定放弃当前录入信息？　　','100%','100%',
   					function(){
   						window.location.href= rootPath + "/advert/advert_list.do";
   					}
   				);
   		}
</script>
</body>
</html>