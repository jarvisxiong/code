function runCoupon(id){
	$.frontEngineDialog.executeDialog(
			'selMenu', 
			'启用', 
			'是否确定启用该优惠券？', 
			"200px", 
			"35px", 
			function() {
				console.log("ajax"+rootPath);
		$.ajax({
			url : rootPath + "/coupon/runCoupon.do",
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
							+ '/coupon/list.do"', 1000);
				} else {
					dialog({
						quickClose : true,
						content : result.msg
					}).show();
				}
			}
		});
	});
}
/*function onSelect() {
	 var faceValue=$("#faceValue").val();
	 var maxFaceValue=$("#maxFaceValue").val();
	 var ex = /^\d+$/;
	 var isTrue=true;
	 if(faceValue!=null && faceValue!=""){
		 if(!ex.test(faceValue)){
				$.frontEngineDialog.executeDialogOK('提示信息','面值只能是整数！','300px');
				isTrue=false;
			 }
	 }else if(maxFaceValue!=null && maxFaceValue!=""){
		 if(!ex.test(maxFaceValue)){
				$.frontEngineDialog.executeDialogOK('提示信息','面值只能是整数！','300px');
				isTrue=false;
			 }
	 }
	 if(faceValue!=null && maxFaceValue!=null){
		 if(parseInt(maxFaceValue)<parseInt(faceValue)){
			$.frontEngineDialog.executeDialogOK('提示信息','面值第二次输入不得小于第一次！','300px');
			isTrue=false;
		 }	
	 }
	 
	 return isTrue;
	//$("#myform").submit();
}*/