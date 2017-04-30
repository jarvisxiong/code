
function submitimport(){
	var isTrue=true;
	var filePath=$("#filePath").val();
	if(filePath == null || filePath == ''){
		$.frontEngineDialog.executeDialogOK("导入失败","请选择导入文件！","200px");
		isTrue=false;
	}
	//var saveUrl=rootPath+"/panicbuyActivity/importExcel.do?id="+id;
	 
	impotpic.submit();
	
	//return isTrue;
}

function downloadFile(type){
	var path;
	console.log(type);
	if(type=='PANIC_BUY'){
		path = "/WEB-INF/template/activity/excelpanicbuy.xlsx" ;
	}else if(type=='PRE_SALE'){
		path = "/WEB-INF/template/activity/excelpresale.xlsx" ;
	}else if(type=='NEWUSER_VIP'){
		path = "/WEB-INF/template/activity/excelNewUserActivity.xlsx" ;
	}else if(type=='ORDINARY_ACTIVITY'){
		path = "/WEB-INF/template/activity/excelOrdinary.xlsx" ;
	}else if(type=='WHOLESALE_MANAGER'){
		path = "/WEB-INF/template/activity/excelWholesale.xlsx" ;
	}
	window.open(rootPath+"/panicbuyActivity/downFile.do?path="+path);
}