function exportData(){
	var params = $("#find-page-orderby-form").serialize()
	$("#orderExport_iframe").attr("src",rootPath+"/order/exportExcel.do?"+params);
}

function toConfirmScan(obj){
	var type = obj;
	var title = "财务扫描确认";
	if("L" == type)
		title = "物流扫描确认";
	
	$.frontEngineDialog.executeIframeDialog('scan_page', title, rootPath
		+ '/order/to_scan_confirm.do?flag='+type, '850', '300');
}

$('a[key="viewWms"]').hover(function(e){
	var orderNoSigned =  $(this).attr("orderNoSigned");
	if($(".logisticspage",this).css("display")=="block"){	
		$.post(rootPath+"/order/querySalesNo.do?orderNoSigned="+orderNoSigned,function(data){
			for(var i=0; i<data.length; i++){
				data[i] += "<br/>";
			}
			$("#status"+orderNoSigned+"").html(data);
	    });
	}
});

//清空输入
function onEmpty() {
	$("#searchValue").val('');
	$("#logisticsRecyleStatus").val('');
	$("#financeRecyleStatus").val('');
	$("#deliveryStartDate").val('');
	$("#deliveryEndDate").val('');
	$("#recyleStartDate").val('');
	$("#recyleEndDate").val('');
}

$(document).on('click', '.art-dialog-close', function() {
	parent.window.location.reload();
});