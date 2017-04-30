
/**
 *  加载
 */
$(function(){
	// 审批表单提交验证
	executeValidateFrom('approveform',"", 'approveCallback');
	
	// 审核描述
	$("#remark").blur(function(){
		var remk = $(this).val();
		$("#remarks").val(remk);
	});
	
	// 确认付款表单提交
//	executeValidateFrom('paymentform',"", paymentCallback);
	
});

/**
 * 审批回调函数
 */
function approveCallback(result){
	if(result && result.status=="success"){
		$.frontEngineDialog.executeDialogContentTimeNoQC(result.infoStr,1500);
		// 延迟1.5秒更新数据
		setTimeout(function () { 
	        var url=rootPath+"/"+result.url;
	        window.location.href=url;
	    }, 1500);
    } else if(result && result.status=="error"){
    	$.frontEngineDialog.executeDialogContentTime(result.infoStr,1500);
    }
}

/**
 * 确认付款
 */
function confirmPayment(applyId){
	var url = rootPath + '/aftersalerefund/findPaymentInfo.do?applyId='+applyId;		
	var dlg=dialog({
        id: "paymentForm",
        title: '确认付款',
        lock: false,
        content:"<iframe  id='paymentForm' name='paymentForm,"+window.location.href+"' src='"+url+"' width='1100px' height='300px' frameborder='0' scrolling='auto' id='paymentForm' style='overflow-x:hidden; '></iframe>",
		button: [
		         {
		             value: '取消',
		             callback: function () {
		             },
		             focus: true
		         },{
		             value: '确认付款',
		             callback: function () {
		            	 $('button[i-id="确认付款"]')[0].style.visibility="hidden";
		            	 document.getElementById("paymentForm").contentWindow.butsubmit();
		            	 setTimeout(function () {
		            		 $('button[i-id="确认付款"]')[0].style.visibility="visible";
		             	}, 2000);
		            	 return false;
		             },
		             focus: true
		         }
		     ]
        }).showModal();
}


//function paymentCallback(result){
//	if(result && result.status=="success"){
//		$.frontEngineDialog.executeDialogContentTime(result.infoStr,1000);
//    } else if(result && result.status=="error"){
//    	$.frontEngineDialog.executeDialogContentTime(result.infoStr,1000);
//    }
//}