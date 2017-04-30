//弹窗页面返回结果
function getUpdata(commodityId,commName,commBarCode,commPrice,code,buyType) {
	if(buyType!='COMMON_BUY'){
		$.frontEngineDialog.executeDialogContentTime('该商品已经参加了其他活动，请先在其他活动删除该商品！',2000);
		return false;
	}else{
		var showStr='';
		//设置返回tr
		$.ajax({
			url : rootPath + "/newUserActivity/getCommoditySku.do",
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
							+'<td><input  type="text" name="acprice'+i+'" onkeyup="onlyInputPrice(this);" class="acprice" skuId="'+result[i].id+'" skuprice="'+result[i].favourablePrice+'" skuCode="'+result[i].skuCode+'" style="width:80px;" onBlur="caclTotalprice()"data-msg-isFloatGtZero="SKU优惠价大于0" data-rule-decimal="true"   data-msg-decimal="小数点不能超过两位数"  data-rule-required="true"  data-msg-required="SKU优惠价不能为空"></td>'
							+'<td><input  type="hidden" name="userCanBuy'+i+'"  class="userCanBuy" value="'+result[i].userCanBuy+'">'+result[i].userCanBuy+'</td>'
							+'<td><input  type="text" name="limitNum'+i+'"  class="limitNum" style="width:80px;" onkeyup="onlyInputNum(this);" onBlur="caclTotalNum()" data-rule-required="true"  data-msg-required="SKU限定数量不能为空"  data-rule-isIntGteZero="true"  data-msg-isIntGteZero="限定数量为大于等于0的整数"></td>'
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

