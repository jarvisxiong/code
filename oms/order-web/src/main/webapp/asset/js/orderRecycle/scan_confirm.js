function switchInput(obj){
	var currentMode = obj;
	if("scan" == currentMode){
		$("#input_mode").text('"被扫描"的客户签收单条形码：');
		$("#barCodeSigned").val("");
		$("#opFlag").val('S');
		
	}else{
		$("#input_mode").text('"手动输入"客户签收单条形码：');
		$("#barCodeSigned").val("");
		$("#opFlag").val('M');
	}
}

document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		
		var params = {};
		params['barCodeSigned'] = $("#barCodeSigned").val();
		params['flag'] = $("#flag").val();
		
		var opFlag = $("#opFlag").val();
		$.post(rootPath + "/order/doUpdateStatus.do", params, function(data) {
			
			$.frontEngineDialog.executeDialogContentTime(data, 2500);
			if("S" == opFlag){
				$("#barCodeSigned").val("");
			}
		});
	}
}; 

