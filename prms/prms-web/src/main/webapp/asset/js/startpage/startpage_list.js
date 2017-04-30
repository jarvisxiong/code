/**
 * 加载
 */
$(function(){
	
});

// 清空
function cleanParams() {	
	$("#terminal").val(""); // 终端
	$("#status").val(""); // 状态
	$("#effectiveStartDate").val(""); // 生效开始时间
	$("#effectiveEndDate").val(""); // 生效结束时间
	$("#expiredStartDate").val(""); // 过期开始时间
	$("#expiredEndDate").val(""); // 过去结束时间
}

// 删除
function deleteItem(id) {
	$.frontEngineDialog.executeDialog(
			'selMenu', 
			'提示信息', 
			'删除启动页信息后，不可恢复，请谨慎操作！', 
			"200px", 
			"35px", 
			function() {
				console.log("ajax"+rootPath);
		$.ajax({
			url : rootPath + "/startpage/datadelete.do",
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
							+ '/startpage/datalist.do"', 1000);
				} else {
					dialog({
						quickClose : true,
						content : result.infoStr
					}).show();
					setTimeout('window.location.href="' + rootPath
							+ '/startpage/datalist.do"', 2000);
				}
			}
		});
	});
}


