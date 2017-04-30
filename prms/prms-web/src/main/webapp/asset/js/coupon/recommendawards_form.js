
// 数据验证
function validate(){
	// 引导分享页面X取值
	var pageXValue = $("#pageXValue").val();
	if("" == pageXValue || null == pageXValue){
		$.frontEngineDialog.executeDialogContentTime("引导分享页面X取值不能为空",2000);
		return false;
	}
	// 引导分享页面规则
	var pageRule = UE.getEditor('pageRule').getContentTxt();
	if("" == pageRule || null == pageRule){
		$.frontEngineDialog.executeDialogContentTime("引导分享页面规则不能为空",2000);
		return false;
	} else if(pageRule.length > 1000){
		$.frontEngineDialog.executeDialogContentTime("引导分享页面规则文字长度不能超出1000个汉字",2000);
		return false;
	}
	// 引导分享页面Y取值
	var pageYValue = $("#pageYValue").val();
	if("" == pageYValue || null == pageYValue){
		$.frontEngineDialog.executeDialogContentTime("引导分享页面Y取值不能为空",2000);
		return false;
	}
	// 分享过程标题
	var shareTitle = $("#shareTitle").val();
	if("" == shareTitle || null == shareTitle){
		$.frontEngineDialog.executeDialogContentTime("分享过程标题不能为空",2000);
		return false;
	}
	// 分享过程描述
	var shareDescription = $("#shareDescription").val();
	if("" == shareDescription || null == shareDescription){
		$.frontEngineDialog.executeDialogContentTime("分享过程描述不能为空",2000);
		return false;
	}
	// 分享后页面标题
	var shareafterTitle = $("#shareafterTitle").val();
	if("" == shareafterTitle || null == shareafterTitle){
		$.frontEngineDialog.executeDialogContentTime("分享后页面标题不能为空",2000);
		return false;
	}
	// 分享后页面X取值
	var shareafterXValue = $("#shareafterXValue").val();
	if("" == shareafterXValue || null == shareafterXValue){
		$.frontEngineDialog.executeDialogContentTime("分享后页面X取值不能为空",2000);
		return false;
	}
	// 分享后页面规则
	var shareafterRule = UE.getEditor('shareafterRule').getContentTxt();
	if("" == shareafterRule || null == shareafterRule){
		$.frontEngineDialog.executeDialogContentTime("分享后页面规则不能为空",2000);
		return false;
	} else if(shareafterRule.length > 1000){
		$.frontEngineDialog.executeDialogContentTime("分享后页面规则文字长度不能超出1000个汉字",2000);
		return false;
	}
	// 新用户注册红包发放编码
	var newuserGrantcode = $("#newuserGrantcode").val();
	if("" == newuserGrantcode || null == newuserGrantcode){
		$.frontEngineDialog.executeDialogContentTime("新用户注册红包发放编码不能为空",2000);
		return false;
	}
	// 老用户反推荐红包发放编码
	var olduserGrantcode = $("#olduserGrantcode").val();
	if("" == olduserGrantcode || null == olduserGrantcode){
		$.frontEngineDialog.executeDialogContentTime("老用户反推荐红包发放编码不能为空",2000);
		return false;
	}
	return true;
}

/**
 * 返回
 */
function backPage(){
	//确认放弃当前录入内容
	$.frontEngineDialog.executeDialog('isReturn_table_info','提示信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　确认放弃本次修改么？　　','100%','100%',
		function(){
			$("#back").click();
		}
	);    
}

$(function(){
	
	var dataList = {
			"appState":"状态为启用时，APP个人中心页面显示“邀请好友,立刻赚钱”区块；禁用时，APP个人中心页面不显示“邀请好友,立刻赚钱”区块；",
			"appTitle":"APP个人中心页面“邀请好友,立刻赚钱”区块右侧显示内容为设置的内容。",
			"pXValue":"引导分享页面，X取值显示内容为设置的内容。",
			"pRule":"引导分享页面，规则显示内容为设置的内容。",
			"pYValue":"引导分享页面，Y取值显示内容为设置的内容。",
			"sTitle":"分享过程页面标题显示为设置的内容。",
			"sDescription":"分享过程页面描述显示为设置的内容。",
			"sfTitle":"分享后页面标题显示为设置的内容。",
			"sfXValue":"分享后页面X取值显示为设置的内容。",
			"sfRule":"分享后页面规则显示为设置的内容。",
			"nuGrantcode":"【新用户注册红包】，启用时，被推荐用户（新用户）——用户填写未注册或绑定过非凡大麦场账户的手机号码，" +
					"即可获得发放编码对应的大麦场红包；登记后7天内凭登记的手机号码注册大麦场账户（使用手机号码绑定已有账户将无法领券），红包将自动到账；",
			"ouGrantcode":"【老用户反推荐红包】，启用时，被推荐用户领红包后30天内首次下单付款且成功签收，则老用户可以获得发放编码对应的大麦场红包；" +
					"若被推荐用户取消订单、拒收或退货，老用户将无法获得；",
	}
	
	$('.partnerfc').hover(function(){
		
		var _name = $(this).attr("name");
		
		partnerfc = dialog({
			id: 'partnerfcArt',
	        title: '',
	        content: dataList[_name],
	        width: 200
	    });
		//$.frontEngineDialog.executeClickLableDialog("","",this);
		partnerfc.show(this);
		
	},function() {
		partnerfc.close().remove();
		
	});

})
