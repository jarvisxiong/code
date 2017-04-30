//弹窗页面返回结果
function getUpdata(commodityId,commName,commBarCode,commPrice,code,buyType) {
	if(buyType!='COMMON_BUY'){
		$.frontEngineDialog.executeDialogContentTime('该商品已经参加了其他活动，请先在其他活动删除该商品！',2000);
		return false;
	}else{
		var showStr='';
		//设置返回tr
		$.ajax({
			url : rootPath + "/ordinaryActivity/getCommoditySku.do",
			data : {
				commodityCode : code
			},
			type : "POST",
			dataType : "json",
			async : false,
			cache : false,
			success : function(result) {
				//把商品SKU返回
				var length = result.length;
				for (var i = 0; i < length; i++)
				{				
					showStr+='<tr>'
							+'<td>'+commName+'</td>'
							+(result[i].commodityAttributeValues==null?'<td></td>':'<td>'+result[i].commodityAttributeValues+'</td>')
							+'<td>'+result[i].barcode+'</td>'
							+'<td>'+result[i].favourablePrice+'</td>'
							+'<td><input  type="text" name="activityPrice" onblur="changPrice()" onkeyup="onlyInputPrice(this);" id="acprice" style="width:80px;">'
							+'<input type="hidden" name="skuId" value="'+result[i].id+'" /><input type="hidden" name="skuCode" value="'+result[i].skuCode+'" /></td>'
							+'</tr>';
				}
				
			}
		});	
	
		$(parent.document.getElementById('dateTr')).html(showStr);
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
		//关闭弹窗窗
	    var $close=$(parent.document.getElementById('title:toSelectCommdityForm')).prev();
	    $close.click();
	}
}

