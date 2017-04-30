<#include "../global.ftl" encoding="utf-8">

<!DOCTYPE html>
<html class="${sys !} ${mod !}">
<head>
	<title>演示ueditor页面</title>
</head>
<body>
<div class="row">
       <div class="col-md-12">
         <div class="box-body">
           <div class="tab-content">      
		     	<h5 style="font-size: 11px">测试上传图片</h5>
		     	<a href="javascript:void(0);"id="btn_upload" key="" class="btn_upload" style="margin-top: 10px">浏览</a>                    
		     	<br><span style="color: red;  ">注：图片内存大小，30KB以内</span>   
           </div>
         </div>
       </div>
   </div>
   
<script type="text/javascript" src="${BasePath !}/asset/js/jquery-1.10.2.min.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/control/SWFUpload/swfupload.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/control/artDialog-6.0.4/dialogUtils.js?v=${ver !}"></script>


    <link rel="stylesheet" href="${BasePath !}/asset/js/control/artDialog-6.0.4/css/ui-dialog.css?v=${ver !}" />
    <script src="${BasePath !}/asset/js/control/artDialog-6.0.4/dist/dialog-min.js?v=${ver !}"></script>

    <script src="${BasePath !}/asset/js/control/artDialog-6.0.4/dist/dialog-plus.js?v=${ver !}"></script>
<script type="text/javascript">
function getPath(){
	if(typeof(window.top.getBaseUrl)=='function'){
		return window.top.getBaseUrl();
	}
	var u = window.location;/*      http://localhost:8888/web/meal/restaurant/list    */
	var p = window.location.pathname;/*    /web/meal/restaurant/list                 */
	var ps = p.split("/");
	var pth = "";
	for(var i = 0; i < ps.length; i++){
		if(ps[i]!=null && ps[i]!=''){
			pth = ps[i];
			break;
		}
	}
	return window.location.protocol /* http:  */
		+ "//" 
		+ window.location.host /*  localhost:8888   */
		+ "/"
		+ pth;  /*  web  */
}
var upload;
var uploadImages = {};
var gardenId="";
$(document).ready(function(){
	initUploadImage("btn_upload");
});

function deletePhoto(className){
	$("."+className).empty();
	$("#iconPath").val("");
//	var imgPath = $("#iconPath").val();
//	if(imgPath==""){
//		return;
//	}
//	//根据路径移除本地图片
//	$.post(getPath() + '/ebbase/goodsCategory/deleteByPath', {
//		path : imgPath
//	}, function(res) {
//		if(res.STATE=='SUCCESS'){
//			$("."+className).empty();
//			$("#iconPath").val("");
//		}else{
//			art.dialog.alert("移除图片失败，请重试");
//		}
//	}, 'json');
}

function getFormatPercent(value){
	value += "";
	var re = /([0-9]+\.[0-9]{2})[0-9]*/;
	return value.replace(re,"$1");
}

/**
 * 上传项目图片的方法
 * @param button_id
 * @param fileName_id
 * @param filePath_id
 */
function initUploadImage(button_id){	
	$('#'+button_id).empty();
	var IMAGE_SIZE_THIRTY = $("#IMAGE_SIZE_THIRTY").val();
	var upload = new SWFUpload({
			upload_url: getPath() + '/upload.do',
			file_post_name:'image',
			post_params: {direct:'upload'},
			file_size_limit : IMAGE_SIZE_THIRTY+"kb",
			file_types : "*.jpg;*.gif;*.png",
			file_types_description : "images/*",
			file_upload_limit : "0",
			file_queue_limit : "0",
			file_queued_handler: function(file){
//				$("#loading_firstPicName").css("display","inline");
			},
			file_queue_error_handler : function(file, errorCode, message){
				try {
					if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
						dialog({quickClose: true,content: '上传的文件太多啦'}).show();
						return;
					}
					switch (errorCode) {
						case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
						dialog({quickClose: true,content: '所选择的图片中，存在内存大小超出设定范围的图片'}).show();
							break;
						case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
							dialog({quickClose: true,content:'上传的文件为空:'+file.name}).show();
							break;
						case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
							dialog({quickClose: true,content: '上传的文件类型不符:'+file.name}).show();
							break;
						case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
							dialog({quickClose: true,content: '上传的文件太多啦:'+file.name}).show();
							break;
						default:
							dialog({quickClose: true,content: '未知错误'}).show();
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
				var percent = getFormatPercent((bytesComplete/bytesTotal).toFixed(4)*100)+"%";
				if(bytesComplete == bytesTotal){
				}
			},
			upload_error_handler : function(file, errorCode, message){				
				dialog({quickClose: true,content: "图片上传报错，请重新上传！异常详细信息："+message}).show();
			},
			upload_success_handler : function(file, serverData, responseReceived){
				//上传成功
				var res = eval('(' + serverData + ')');
				var html = '<img class="bor3" src="'+base+'/images/'+res.PATH.replace('size','150X100')+'" width="120" height="120" />';
				  	html += '<span class="delBtn" onclick="deletePhoto(\'innerImage\');"><img src="'+getPath()+'/asset/img/icon_close.png"></span>';
			  	deletePhoto("innerImage");
				$('.innerImage').append(html);
				$("#iconPath").val(res.PATH);
			},
			upload_complete_handler : null,
			button_image_url:getPath()+'/asset/img/liulan_btn.png',
			button_placeholder_id : button_id,
			button_width: 48,
			button_height: 22,
			button_cursor:SWFUpload.CURSOR.HAND,
			flash_url : getPath()+"/asset/js/control/SWFUpload/Flash/swfupload.swf",
			custom_settings : {
			},
			debug: false
		});
}

</script>
</body>
</html>