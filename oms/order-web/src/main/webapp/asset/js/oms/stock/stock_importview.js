
function submitImport(){ 
	var isTrue=true;
	var filePath=$("#filePath").val();
	if((filePath==null || filePath=="" || typeof(filePath)=="undefined")){
		$.frontEngineDialog.executeDialogOK("上传失败","请选择上传文件！","200px");
		isTrue=false;
		return;
	}
	
	$("#stockImportForm").submit();
}

function downloadFile(type) {
	var path = "/template/stock/importStock.xlsx";
	window.open(rootPath + "/stock/downFile.do?path=" + path);
}