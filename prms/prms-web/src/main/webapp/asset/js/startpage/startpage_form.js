/**
 * 加载
 */
$(function(){
	executeValidateFrom('myform'); 
	// 终端加载
	showByTerminal();
	// 广告类型加载
	showByAdvertType("0");
	// 上传图片
	initUploadImage(AndroidshowImg1,"startPageImage","androidBtn1",200);	
    initUploadImage(AndroidshowImg2,"startPageImage","androidBtn2",200);
    initUploadImage(IOSshowImg1,"startPageImage","IOSBtn1",200);
    initUploadImage(IOSshowImg2,"startPageImage","IOSBtn2",200);
    initUploadImage(IOSshowImg3,"startPageImage","IOSBtn3",200);	
    initUploadImage(showImg,"startPageImage","photoPathBtn",200);
});

// 终端下拉框chang事件
function changeTerminal(){
	var content = "您已经填写了部分必填信息，此时变更终端类型，已录入的信息将被清空！确认要变更终端类型吗？";
	dialog({
        id: 'isReturn_table_info',
        icon:'succeed',
        title: '信息',
        width: '400',
        height: '100',
        content: '<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>' + content,
        button: [
                 {
                     value: '确定',
                     callback: function(){
                    	clearAll();
            			showByTerminal();
            		}
                 },
                 {
                     value: '取消',
                     callback: function () {
                    	var terminal = $("#terminal").val();
                 		if (terminal == "Android") {
                 			$("#terminal").val("IOS");
                 		} else {
                 			$("#terminal").val("Android");
                 		}
                     }
                 }
                 
             ]
    }).showModal();
}

function showByTerminal(){
	// 终端值
	var terminal = $("#terminal").val();
	// 上传图片控件显示\隐藏
	if (terminal == 'Android') {
		// Android的显示
		$("#Android_img1").show();
		$("#Android_img2").show();
		// IOS的隐藏
		$("#IOS_img1").hide();
		$("#IOS_img2").hide();
		$("#IOS_img3").hide();
		$("#imgPath4").attr("data-rule-required","false");
		//改变提示描述
		$(".descLabel").empty();
		$(".descLabel").append('<i>*</i>广告图片（1080*1920）：<span style="color: red;">请上传尺寸为1080*1920的图片</span>');
	} else {
		// IOS的显示
		$("#IOS_img1").show();
		$("#IOS_img2").show();
		$("#IOS_img3").show();
		// Android的隐藏
		$("#Android_img1").hide();
		$("#Android_img2").hide();
		$("#imgPath4").attr("data-rule-required","true");
		//改变提示描述
		$(".descLabel").empty();
		$(".descLabel").append('<i>*</i>广告图片（1242*2208）：<span style="color: red;">请上传尺寸为1242*2208的图片</span>');
	}
}

// 清空所有数据
function clearAll(){
	// 清空图片
	$("#imgPath1").val("");
	$("#imgPath2").val("");
	$("#imgPath3").val("");
	$("#imgPath4").val("");
	//$("#myform input").val("");
	// 清空时间
	$("#effectiveDate").val("");
	$("#expiredDate").val("");
	// 清空广告
	$("#objId").val("");
	$("#objName").val("");
	$("#url").val("");
	// 清空图片隐藏值
	$("#myform img").attr("src","");
	$("#Android1").val("");
	$("#Android2").val("");
	$("#IOS1").val("");
	$("#IOS2").val("");
	$("#IOS3").val("");
	$("#imgPath").val("");
}

//下拉框change事件
function changeAdvertType(item){
	$("#obj_f7").find('input').val('');
	showByAdvertType(item);
}

function showByAdvertType(item){
	var advertType = $("#advertType").val();
	var adevertTypeName = $("#advertType").find("option:selected").text();
	$("#objLabel").html(adevertTypeName);
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
	// 清空活动信息
	clearValue(item);
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
		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activityCommodity_dataPicker', '预售详情', '"+rootPath+"/dataPicker/activityCommodity_dataPicker.do?activityType=PRE_SALE', '700', '550');");
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
		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activityCommodity_dataPicker', '抢购详情', '"+rootPath+"/dataPicker/activityCommodity_dataPicker.do?activityType=PANIC_BUY', '700', '550');");
	}else if(advertType=="ACTIVITY_LIST"){
		//活动列表
		$("#tr_obj_f7").show();
		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activity_dataPicker', '活动列表', '"+rootPath+"/dataPicker/activity_dataPicker.do?activityType=ORDINARY_ACTIVITY', '700', '550');");
	}else if(advertType=="WHOLESALE_LIST"){
		//批发列表
		$("#tr_obj_f7").show();
		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activity_dataPicker', '批发列表', '"+rootPath+"/dataPicker/activity_dataPicker.do?activityType=WHOLESALE_MANAGER', '700', '550');");
	}else if(advertType=="NEWUSER_LIST"){
		//新用户专享
		$("#tr_obj_f7").show();
		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('activity_dataPicker', '新用户列表', '"+rootPath+"/dataPicker/activity_dataPicker.do?activityType=NEWUSER_VIP', '700', '550');");
	}else if(advertType=="ORDINARYGOODS_DETAIL"){
		//普通商品详情
		$("#tr_obj_f7").show();
		$("#selectBtn_obj_f7").attr("onclick","$.frontEngineDialog.executeIframeDialog('commodity_dataPicker', '商品列表', '"+rootPath+"/dataPicker/commodity_dataPicker.do', '600', '550');");
	}else if(advertType=="MENU_TYPE"){
		//菜单类型
		$("#td2_img").show();
		$("#photoPath2").attr("data-rule-required","true");
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
	}
}

function clearValue(item){
	if(item == "1"){
		// 清空广告
		$("#objId").val("");
		$("#objName").val("");
		$("#url").val("");
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

// 验证
function setImage(){
	var terminal = $("#terminal").val();
	// 图片
	if (terminal == 'Android') {
		$("#imgPath1").val($("#Android1").val());
		$("#imgPath2").val($("#Android2").val());
		$("#imgPath3").val($("#imgPath").val());
	} else {
		$("#imgPath1").val($("#IOS1").val());
		$("#imgPath2").val($("#IOS2").val());
		$("#imgPath3").val($("#IOS3").val());
		$("#imgPath4").val($("#imgPath").val());
	}
	
}

// 保存
function buttonsubmit(){
	// 图片设值
	setImage();
	// 保存
	$("#myform").submit();
}

//返回
function goBack(){
	$.frontEngineDialog.executeDialog('isReturn_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否确定放弃当前录入信息？　　','100%','100%',
		function(){
			window.location.href= rootPath + "/startpage/datalist.do";
		}
	);
}


//图片上传成功   安卓图片1
function AndroidshowImg1(data){	
	var res = eval('(' + data + ')');	
	if(res.status == "0"){
		$("#Android1").val(res.path);		//path  图片地址用于数据库存储
		$("#Android1_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
	}
	
	//上传成功
	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
}
//图片上传成功   安卓图片2
function AndroidshowImg2(data){	
	
	var res = eval('(' + data + ')');	
	if(res.status == "0"){
		$("#Android2").val(res.path);		//path  图片地址用于数据库存储
		$("#Android2_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
	}
	
	//上传成功
	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
}

//图片上传成功   IOS图片1
function IOSshowImg1(data){	
	var res = eval('(' + data + ')');	
	if(res.status == "0"){
		$("#IOS1").val(res.path);		//path  图片地址用于数据库存储
		$("#IOS1_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
	}
	
	//上传成功
	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
}

//图片上传成功   IOS图片2
function IOSshowImg2(data){	
	
	var res = eval('(' + data + ')');	
	if(res.status == "0"){
		$("#IOS2").val(res.path);		//path  图片地址用于数据库存储
		$("#IOS2_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
	}
	
	//上传成功
	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
}

//图片上传成功   IOS图片3
function IOSshowImg3(data){	
	var res = eval('(' + data + ')');	
	if(res.status == "0"){
		$("#IOS3").val(res.path);		//path  图片地址用于数据库存储
		$("#IOS3_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
	}
	
	//上传成功
	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
}

//图片上传成功   --安卓、IOS共有
function showImg(data){	
	
	var res = eval('(' + data + ')');	
	if(res.status == "0"){
		$("#imgPath").val(res.path);		//path  图片地址用于数据库存储
		$("#imgPath_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
	}
	
	//上传成功
	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
}

// 全局对象
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
		path = "advert/appStartPage/";
	}
	var upload = new SWFUpload({
		upload_url: rootPath + '/uploadImage/upload.do;jsessionid='+$('#jsessionid').val(),
		file_post_name:'image',
		post_params: {type:type,direct:path},
		file_size_limit : IMAGE_SIZE_HUNDRED+"kb",
		file_types : "*.jpg;*.png",
		file_types_description : "images/*",
		file_upload_limit : "0",
		file_queue_limit : "0",
		file_queued_handler: function(file){
//				$("#loading_firstPicName").css("display","inline");
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
