
/**
 *  加载
 */
$(function(){
	
});

/**
 * 保存app推荐
 */
function saveAppRecommend(){
	// 获取选中的活动id
	var id = getCheckValue();
	if (id != "") {
		$.ajax({
			url : rootPath + "/panicbuyActivity/saveAppRecommend.do",
			data : {
				id : id
			},// 给服务器的参数
			type : "POST",
			dataType : "json",
			async : false,
			cache : false,
			success : function(result) {
				if (result.status == 'success' || result.code == 0) {
					dialog({
						quickClose : true,
						content : '操作成功！'
					}).show();
					setTimeout('parent.window.location.href="' + rootPath
							+ '/panicbuyActivity/appRecommend.do"', 1000);
				} else {
					$.frontEngineDialog.executeDialogOK('提示信息',result.infoStr,'300px');
				}
			}
		});
	} else {
		$.frontEngineDialog.executeDialogOK('提示信息','请选择抢购活动','300px');
	}
}


function getCheckValue(){
	var actId = "";
	$("#activityList tr").each(function(index){
		if ($(this).find("input[type='checkbox']").is(':checked')) {
			var activityId = $(this).find("input[type='hidden']").val();
			actId += activityId + ",";
		}
	});
	// 返回活动id
	return actId.substring(0, actId.length - 1);
}

