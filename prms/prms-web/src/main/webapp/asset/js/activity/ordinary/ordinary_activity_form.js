
// 数据验证
function validate(){
	// 活动标题
	var title = $("#title").val();
	if("" == title || null == title){
		$.frontEngineDialog.executeDialogContentTime("活动标题不能为空",2000);
		return false;
	}
	// 置顶序号
	var topSore = $("#topSore").val();
	if ("" == topSore || null == topSore) {
		$.frontEngineDialog.executeDialogContentTime("活动置顶序号不能为空",2000);
		return false;
	}
	// App是否显示时间-赋值
	if ($("input[type='checkbox']").is(':checked')) {
		$("#showCountDown").val("1");
	} else {
		$("#showCountDown").val("0");
	}
	
	var imgPath = $("#picPath").val();
	if ("" == imgPath || null == picPath) {
		$.frontEngineDialog.executeDialogContentTime("活动主页图片不能为空",2000);
		return false;
	}
	
	var startDateStr=$("#startDateStr").val();//活动商品开始时间
	var endDateStr=$("#endDateStr").val();//活动商品结束时间
	if(Date.parse(startDateStr.replace(/-/g, "/")) == Date.parse(endDateStr.replace(/-/g, "/"))){
		$.frontEngineDialog.executeDialogOK('提示信息','新增活动失败，活动的结束时间必须晚于开始时间，请修改后重试！','300px');
		return false;
	}
	// 活动描述
	if((UE.getEditor('remarks').getContentTxt().length)>=300){
		$.frontEngineDialog.executeDialogOK('提示信息','活动描述300字以内！','300px');
		return false;
	}
	
	return true;
}


//图片上传成功   业务处理事件
function showImg(data){	
	var res = eval('(' + data + ')');	
	if(res.status == "0"){
		$("#picPath").val(res.path);		//path  图片地址用于数据库存储
		$("#photoPath_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
	}
	
	//上传成功
	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
}


