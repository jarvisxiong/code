
/**
 * 加载数据
 */
$(function(){
	$("#recommends tr").each(function(index){
		if (index != 0) {
			$(this).find("input[name='photoPathBtn']").attr("id","photoPathBtn"+index);
			$(this).find("div[class='div-form']").attr("id","photoPath"+index);
			var btn = $(this).find("input[name='photoPathBtn']").attr("id"); // 按钮id
			var photo = $(this).find("div[class='div-form']").attr("id"); // 图片显示div的id
			// 上传图片
			uploadImage("activityImage",btn,photo,100);
		}
	});
});

/**
 * 保存验证
 */
function validate(){
	// 验证结果
	var valid = true;
	// 数据
	var dataStr = "[";
	var path = ""; // 图片地址
	var id = ""; // 活动商品id
	var sort = ""; // 排序
	// 遍历列表
	$("#recommends tr").each(function(index){
		if (index != 0) { // 过滤克隆tr
			path = $(this).find("input[name='picPath']").val();
			id = $(this).find("input[name='id']").val();
			sort = $(this).find("input[name='recommendSort']").val();
			if ("" == path || null == path) {
				$.frontEngineDialog.executeDialogContentTime("商品banner图片不能为空",1000);
				valid = false;
				return;
			}
			if ("" == id || null == id) {
				$.frontEngineDialog.executeDialogContentTime("商品选择不能为空",1000);
				valid = false;
				return;
			}
			if ("" == sort || null == sort) {
				$.frontEngineDialog.executeDialogContentTime("排序号不能为空",1000);
				valid = false;
				return;
			}
			dataStr += "{isRecommend:'1',picPath:'"+path+"',id:'"+id+"',recommendSort:'"+sort+"'},";
		}
	});
	if (dataStr.length > 1) {
		dataStr = dataStr.substring(0, dataStr.length-1);
	}
	dataStr += "]";
	// 赋值
	$("#commoditys").val(dataStr);
	
	return valid;
}

/**
 * 保存回调函数
 * @param result
 */
function resultData(result){
	if(result && result.status=="success"){
		$.frontEngineDialog.executeDialogContentTime(result.infoStr,1000);
    } else if(result && result.status=="error"){
    	$.frontEngineDialog.executeDialogContentTime(result.infoStr,1000);
    }
	window.location.href = rootPath + result.url;
}


/**
 * 添加行(注意：有一隐藏行)
 */
function addItem(){
	// 列表行数
	var index = $("#recommends tr").length;
	// 克隆一行
	$("#recommends").append($("#recommends tr:eq(0)").clone());
	// 设置序号
	$("#recommends tr:eq("+(index)+")").children("td").eq(0).text(index);
	$("#recommends tr:eq("+(index)+")").attr("style","");
	$("#recommends tr:eq("+(index)+")").find("input[name='photoPathBtn']").attr("id","photoPathBtn"+index);
	$("#recommends tr:eq("+(index)+")").find("div[class='div-form']").attr("id","photoPath"+index);
	var btn = $("#recommends tr:eq("+(index)+")").find("input[name='photoPathBtn']").attr("id"); // 按钮id
	var photo = $("#recommends tr:eq("+(index)+")").find("div[class='div-form']").attr("id"); // 图片显示div的id
	// 上传图片
	uploadImage("activityImage",btn,photo,100);
}

/**
 * 删除行(注意：有一隐藏行)
 * @param obj 对象
 */
function deleteItem(obj){
	// 删除行
	$(obj).parent().parent().remove();
	// 遍历更新序号
	$("#recommends tr").each(function(index){
		$(this).children("td").eq(0).text(index);
		$(this).find("input[name='photoPathBtn']").attr("id","photoPathBtn"+index);
	});
	
//	// 获取id
//	var id = $(obj).parent().parent().find("input[name='id']").val();
//	var isRe = $(obj).parent().parent().find("input[name='isRe']").val();
//	if ("" == id || "0" == isRe) {
//		
//	} else {
//		$.frontEngineDialog.executeDialog('delete_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　删除之后该信息会被彻底删除,是否继续？　　','100%','100%',
//			function(){
//				$.post(rootPath+"/ordinaryActivity/recommendDelete.do",{id:id},function(res){
//					 if(res.STATUS=="SUCCESS"){
//						 $.frontEngineDialog.executeDialogContentTime(res.infoStr,1000);
//						 removeTr(obj);
//					 }else{
//						 $.frontEngineDialog.executeDialogContentTime(res.infoStr,1000);
//					 }
//				 },'json');
//			}
//		);
//	}
}

/**
 * 普通活动选择器
 */
var seletedObj = null;
function toSelectOrdinary(id,obj){
	seletedObj = $(obj).parent().parent();
	var url = rootPath + '/ordinaryActivity/selectOrdinary.do?activityId='+id;		
	var dlg=dialog({
        id: "toSelectActivityForm",
        title: '商品选择',
        lock: false,
        content:"<iframe  name='toSelectActivityForm,"+window.location.href+"' src='"+url+"' width='700px' height='500px' frameborder='0' scrolling='auto' id='toSelectActivityForm' style='overflow-x:hidden; '></iframe>",
        }).showModal();
}

function afterSelectedActivity(obj,dlg){
	dlg.click();
	//设置返回值存放位置
	var $id = $(obj).attr("id");
	var $title = $(obj).attr("title");
	var $actId = $(obj).attr("actId");
	var $isRe = $(obj).attr("isRecommend"); // 是否推荐
	// 遍历判断是否有相同的
	var diff = false;
	// 判断选中商品是否已推荐
	if ("1" == $isRe) {
		$.frontEngineDialog.executeDialogContentTime("该活动商品已推荐,请勿重复选择",1000);
		diff = true;
	} else {
		$("#recommends tr").each(function(index){
			var id = $(this).find("input[name='id']").val();
			if (id == $id) {
				$.frontEngineDialog.executeDialogContentTime("该活动商品已选中,请勿重复选择",1000);
				diff = true;
				return;
			}
		});
	}
	
	// 存在相同的，不改变；否则，改变
	if (!diff) {
		// 隐藏id--活动商品id
		$(seletedObj).find("input[name='id']").val($id);
		// 标题
		$(seletedObj).find("input[name='activityTitle']").val($title);
		// 是否推荐
		$(seletedObj).find("input[name='isRe']").val($isRe);
	}
}

/**
 *  图片上传
 * @param obj
 * @param type
 * @param button_id
 * @param file_size
 * @param path
 */
function uploadImage(type,button_id,div_id,file_size){
	var IMAGE_SIZE_HUNDRED = "50";
	if(file_size != null && file_size!=''){
		IMAGE_SIZE_HUNDRED = file_size;
	}
	
	var upload = new SWFUpload({
			upload_url: rootPath + '/uploadImage/upload.do;jsessionid='+$('#jsessionid').val(),
			file_post_name:'image',
			post_params: {type:type},
			file_size_limit : IMAGE_SIZE_HUNDRED+"kb",
			file_types : "*.jpg;*.gif;*.png",
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
//				var percent = getFormatPercent((bytesComplete/bytesTotal).toFixed(4)*100)+"%";
//				if(bytesComplete == bytesTotal){
//				}
			},
			upload_error_handler : function(file, errorCode, message){
				$.frontEngineDialog.executeDialogContentTime("图片上传报错，请重新上传！异常详细信息："+message);
			},
			upload_success_handler : function(file, serverData, responseReceived){
				//上传成功回调
				var res = eval('(' + serverData + ')');	
				if(res.status == "0"){
					$("#"+div_id).parent().find("input[name='picPath']").val(res.path);//path  图片地址用于数据库存储
					$("#"+div_id).parent().find("img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
				}
				//上传成功
				$.frontEngineDialog.executeDialogContentTime(res.infoStr);
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
