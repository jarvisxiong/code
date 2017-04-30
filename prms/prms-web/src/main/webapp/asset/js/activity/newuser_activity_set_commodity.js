function toSelectCommdity(){
		var url = rootPath + '/newUserActivity/toCommdity.do';		
		var dlg=dialog({
            id: "toSelectCommdityForm",
            title: '商品设置-新增',
            lock: false,
            content:"<iframe  name='toSelectCommdityForm,"+window.location.href+"' src='"+url+"' width='950px' height='350px' frameborder='0' scrolling='auto' id='toSelectCommdityForm' style='overflow-x:hidden; '></iframe>",       
		}).showModal();
}


var flag = true;
function caclTotalNum(){
	var limitCount=0;
	$("#dateTr tr").each(function(){
		var limitNum =$(this).find("input.limitNum").val();
		var userCanBuy = $(this).find("input.userCanBuy").val();
		if(limitNum && limitNum>=0){
			if(parseInt(limitNum) > parseInt(userCanBuy)){
				flag = false;
				$.frontEngineDialog.executeDialogContentTime("限定数量不能大于用户可购买数量",2000);
				$(this).find("input.limitNum").focus();
				
			}else{
					flag = true;
				}
			limitCount=parseInt(limitCount)+parseInt(limitNum);
			$("#limitCount").val(parseInt(limitCount));
			}
			
	});
}
function caclTotalprice(){
var total=[];
$("#dateTr tr").each(function(){		
	var acprice = $(this).find("input.acprice").val();
	if(acprice && parseFloat(acprice)>0){		
		total.push(acprice);		
	}				
});
total = total.sort(function(a,b){
	return a-b;
});
console.log(total);
if(total.length==1){
	$("#showPrice").val(total[0]);
}else if(total.length>1){
	$("#showPrice").val(total[0]+"-"+total[(total.length-1)]);
}
}


function resultData(result){
	 if(result && result.status=="success"){
		 parent.location.reload(false);
     }else{
    	 $.frontEngineDialog.executeDialogContentTime(result.infoStr,2000);
     }    
}

function butsubmit(){
	var idLimit = $("#idLimitCount").val();
	var limitCount = $("#limitCount").val();
	if(idLimit!=null && idLimit!=""){
		if(!(/^(\+|-)?\d+$/.test( idLimit ))||parseInt(idLimit)<0){  
			$.frontEngineDialog.executeDialogOK('提示信息','用户限购量必须是大于0的整数！','300px');
			return false;
	    }
	}
	if(parseInt(idLimit)>parseInt(limitCount)){
		$.frontEngineDialog.executeDialogContentTime("Id限购量不能大于限定数量",2000);
		$("#idLimitCount").focus();
		return false;
	}
	var enableSpecialCount=$('input[name="enableSpecialCount"]:checked').val();//是否启用特殊增量
	var islimit=$('input[name="islimit"]:checked').val();//是否app显示限购数量
	var jsonStr ="[";				
	$('#dateTr').find('tr').each(function(index){// 过滤克隆tr
			jsonStr+="{";
			jsonStr+="'commoditySkuId':'"+$(this).find("input.acprice").attr("skuId")+"',";
			jsonStr+="'commoditySkuNo':'"+$(this).find("input.acprice").attr("skuCode")+"',";
			jsonStr+="'commoditySkuBarcode':'"+$(this).find("td").eq(2).text()+"',";
			jsonStr+="'commoditySkuPrice':'"+$(this).find("input.acprice").attr("skuprice")+"',";
			jsonStr+="'attrGroup':'"+$(this).find("td").eq(1).text()+"',";
			jsonStr+="'activityPrice':'"+$(this).find("input.acprice").val()+"',";
			jsonStr+="'limitCount':'"+$(this).find("input.limitNum").val()+"',";
			jsonStr+="'commoditySkuTitle':'"+$(this).find("td").eq(0).text()+"',";
			jsonStr +="},";
	});				

	if(jsonStr.indexOf(",")!=-1){
		jsonStr=jsonStr.substring(0,jsonStr.length-1);
	}
	jsonStr+="]";
	$("#activityitemDate").val(jsonStr);
	if (flag) {
		$("#myform").submit();
	}else{
		$.frontEngineDialog.executeDialogContentTime("限定数量不能大于用户可购买数量",2000);
	}
}

/**
 * 返回
 */
function toBack(){
	var actId = $("#activityId").val(); // 活动id
	var actNo = $("#activityNo").val(); // 活动No
	window.location.href = rootPath + '/newUserActivity/commoditylist.do?activityId='+actId+'&activityNo='+actNo;
}
