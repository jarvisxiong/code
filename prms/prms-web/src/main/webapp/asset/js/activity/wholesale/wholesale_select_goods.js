//弹窗页面返回结果
function getUpdata(commodityId,commName,commBarCode,commPrice,code,buyType) {
	if(buyType!='COMMON_BUY'){
		$.frontEngineDialog.executeDialogContentTime('该商品已经参加了其他活动，请先在其他活动删除该商品！',2000);
		return false;
	}else{
		var activityTitle=parent.document.getElementById('activityTitle');
		$(activityTitle).val(commName);
		var comm=parent.document.getElementById('commName');
		$(comm).val(commName);
		var commId=parent.document.getElementById('newCommodityId');
		$(commId).val(commodityId);
		var commId=parent.document.getElementById('commodityId');
		$(commId).val(commodityId);
		var commdityNo=parent.document.getElementById('commodityNo');
		$(commdityNo).val(code);
		var commdityBarcode=parent.document.getElementById('commodityBarcode');
		$(commdityBarcode).val(commBarCode);
		var commdityprice=parent.document.getElementById('price');
		$(commdityprice).val(commPrice);
		//关闭弹窗窗
	    var $close=$(parent.document.getElementById('title:toSelectCommdityForm')).prev();
	    $close.click();
	}
	
}

