
/**
 *  加载
 */
$(function(){
	// 显示页签样式
	var status = $("#releaseStatus").val();
	if ("" == status) {
		$("li:eq(0)").attr("class","active");
	} else if("0" == status) {
		$("li:eq(2)").attr("class","active");
	} else if("1" == status) {
		$("li:eq(1)").attr("class","active");
	}
	
});

function isEnabled(id,releaseStatus) {
	var titleStr = "发布";
	if (releaseStatus == 0) {
		titleStr = "撤销";
	}

	$.frontEngineDialog.executeDialog(
			'selMenu', 
			titleStr, 
			'是否确定' + titleStr+ '？', 
			"200px", 
			"35px", 
			function() {
				console.log("ajax"+rootPath);
		$.ajax({
			url : rootPath + "/ordinaryActivity/updateStatus.do",
			data : {
				id : id,
				releaseStatus : releaseStatus
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
					setTimeout('window.location.href="' + rootPath
							+ '/ordinaryActivity/dataList.do"', 1000);
				} else {
					$.frontEngineDialog.executeDialogOK('提示信息',result.infoStr,'300px');
				}
			}
		});
	});
}
function deleteItem(id) {
	$.frontEngineDialog.executeDialog(
			'selMenu', 
			'提示信息', 
			'删除活动信息后，不可恢复，请谨慎操作！', 
			"200px", 
			"35px", 
			function() {
				console.log("ajax"+rootPath);
		$.ajax({
			url : rootPath + "/ordinaryActivity/dataDelete.do",
			data : {
				id : id,
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
					setTimeout('window.location.href="' + rootPath
							+ '/ordinaryActivity/dataList.do"', 1000);
				} else {
					dialog({
						quickClose : true,
						content : result.infoStr
					}).show();
					setTimeout('window.location.href="' + rootPath
							+ '/ordinaryActivity/dataList.do"', 2000);
				}
			}
		});
	});
}

/**
 *  清空检索条件
 */
function cleanParams(){
	$("#title").val(""); // 标题
	$("#releaseStatus").val(""); // 发布状态
	$("#activityStatus").val(""); // 活动状态
	$("#operator").val(""); // 操作人
}

/**
 *  根据状态检索列表数据
 * @param status 状态
 */
function findBystatus(status,obj){
	$(".navtabs-title li").attr("class",""); // 清理所有li的样式
	$(obj).parent().attr("class","active"); // 添加当前对象所在li的样式
	$("#releaseStatus").val(status);
	$("form").submit();
}

/**
 *  商品设置-弹出窗
 */
function ordinaryCommodity(id) {
    $.frontEngineDialog.executeIframeDialog('test', '活动商品设置', rootPath
            + '/ordinaryActivity/commodityList.do?activityId='+id, '1200', '600');
}

/**
 *  活动商品推荐-弹出窗
 */
function ordinaryRecommend(id) {
	var url = rootPath + '/ordinaryActivity/recommendList.do?acrivityId='+id;		
	var dlg=dialog({
        id: "setRecommendForm",
        title: '活动商品推荐',
        lock: false,
        content:"<iframe  name='setRecommendForm,"+window.location.href+"' src='"+url+"' width='900px' height='600px' frameborder='0' scrolling='auto' id='setRecommendForm' style='overflow-x:hidden; '></iframe>",
		button: [
		         {
		             value: '确定',
		             callback: function () {
		            	 document.getElementById("setRecommendForm").contentWindow.butsubmit();
		            	 return false;
		             },
		             focus: true
		         },{
		             value: '关闭',
		             callback: function () {
		             },
		             focus: true
		         }
		     ]
        }).showModal();
}


