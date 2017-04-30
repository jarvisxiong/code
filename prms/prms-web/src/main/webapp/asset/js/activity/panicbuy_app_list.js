
/**
 *  加载
 */
$(function(){
	
});

/**
 * 取消app推荐
 * @param id 活动id
 */
function isEnabled(id) {
	$.frontEngineDialog.executeDialog(
			'selMenu', 
			'提示信息', 
			'取消推荐后，APP将不再显示该抢购活动！确认要取消推荐吗？', 
			"270px", 
			"35px", 
			function() {
				console.log("ajax"+rootPath);
		$.ajax({
			url : rootPath + "/panicbuyActivity/updateAppRecommend.do",
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
					setTimeout('window.location.href="' + rootPath
							+ '/panicbuyActivity/appRecommend.do"', 1000);
				} else {
					$.frontEngineDialog.executeDialogOK('提示信息',result.infoStr,'300px');
				}
			}
		});
	});
}
//查看
function view(id) {
	window.location.href = rootPath + '/panicbuyActivity/dataView.do?id=' + id;
}


/**
 *  新增app推荐-弹出窗
 */
function setAppRecommend() {
	var url = rootPath + '/panicbuyActivity/findActivityList.do';		
	var dlg=dialog({
        id: "addAppRecommendForm",
        title: '选择抢购活动',
        lock: false,
        content:"<iframe  name='addAppRecommendForm,"+window.location.href+"' src='"+url+"' width='900px' height='400px' frameborder='0' scrolling='auto' id='addAppRecommendForm' style='overflow-x:hidden; '></iframe>",
		button: [
		         {
		             value: '确定',
		             callback: function () {
		            	 document.getElementById("addAppRecommendForm").contentWindow.saveAppRecommend();
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

