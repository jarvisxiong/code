$(function() {
	/*if(viewStatus!="" && viewStatus=="save"){
	//$('#specialCount').val("");
		if($('#enableSpecialCount').val()!=1){
			$("#enableSpecialCount").attr("clicked",false); 
		}else{
			$("#enableSpecialCount").attr("clicked",true); 
		}
		if($('#specialCount').val()==null)
			radioAmount();
		else{
			$("#enableSpecialCount").prop("checked",true); 
			$('#saleIncrease').attr("readonly", true);
		}
	}*/
});

/**
 *  判断销量增量值、特殊增量值只能选择一个 
 */
function radioAmount(){
	if($("#enableSpecialCount").is(':checked')){
		$('#saleIncrease').val("");
		$('#saleIncrease').attr("readonly", true);
		$("#specialCount").removeAttr("readonly");
	}else {
		$('#saleIncrease').removeAttr("readonly");
		$('#specialCount').val("");
		$('#specialCount').attr("readonly",true);
	}

}
function toSelectCommdity(){
		var url = rootPath + '/panicbuyActivity/toCommdity.do';		
		var dlg=dialog({
            id: "toSelectCommdityForm",
            title: '选择商品',
            lock: false,
            content:"<iframe   name='toSelectCommdityForm,"+window.location.href+"' src='"+url+"' width='920px' height='300px' frameborder='0' scrolling='auto' id='toSelectCommdityForm' style='overflow-x:hidden; '></iframe>",       
		}).showModal();
}

function caclTotalNum(){
		var limitCount=0;
		$("#dateTr tr").each(function(){
			var limitNum =$(this).find("input.limitNum").val();
			var userCanBuy=$(this).find("input.userCanBuy").val();
			if(parseInt(limitNum)>=0){		
				if(userCanBuy!=null && userCanBuy!=""){
					if(parseInt(limitNum)>parseInt(userCanBuy)){						
						$.frontEngineDialog.executeDialogOK('提示信息','商品限定数量量必须小于等于用户可购买量！','300px');		
						$(this).find("input.limitNum").val("");
						$("#limitCount").val("");
						return false;
					}
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
	
	if(total.length==1){
		$("#showPrice").val(total[0]);
	}else if(total.length>1){
		$("#showPrice").val(total[0]+"-"+total[(total.length-1)]);
	}
}


function resultData(result){
	 if(result && result.status=="success"){
		 $.frontEngineDialog.executeDialogContentTime(result.infoStr,2000);
		 setTimeout("parent.window.location.href=\'" + rootPath + result.url +"'", 1000);
     }else{
    	 $.frontEngineDialog.executeDialogContentTime(result.infoStr,2000);
     }    
}

function butsubmit(){
	var enableSpecialCount=$('input[name="enableSpecialCount"]:checked').val(); ;//是否启用特殊增量
	var islimit=$('input[name="islimit"]:checked').val(); ;//是否app显示限购数量
	var activityStartDate=$("#activityStartDate").val();//活动开始时间
	var activityEndDate=$("#activityEndDate").val();//活动结束时间
	var startDateStr=$("#startDateStr").val();//活动商品开始时间
	var endDateStr=$("#endDateStr").val();//活动商品结束时间
	var idLimitCount=$("#idLimitCount").val();//id限购量
	if(Date.parse(startDateStr.replace(/-/g, "/")) < Date.parse(activityStartDate.replace(/-/g, "/")) ||
			Date.parse(startDateStr.replace(/-/g, "/")) > Date.parse(activityEndDate.replace(/-/g, "/"))){
		$.frontEngineDialog.executeDialogOK('提示信息','开始时间必须在抢购活动开始结束时间之内！','300px');
		return false;
	}
	if(Date.parse(endDateStr.replace(/-/g, "/")) > Date.parse(activityEndDate.replace(/-/g, "/")) ||
			Date.parse(endDateStr.replace(/-/g, "/")) < Date.parse(activityStartDate.replace(/-/g, "/"))){
		$.frontEngineDialog.executeDialogOK('提示信息','结束时间必须在抢购活动开始结束时间之内！','300px');
		return false;
	}
	if(idLimitCount!=null && idLimitCount!=""){
		if(!(/^(\+|-)?\d+$/.test( idLimitCount ))||parseInt(idLimitCount)<0){  
			$.frontEngineDialog.executeDialogOK('提示信息','用户限购量必须是大于0的整数！','300px');
			return false;
	    }
	}
	var jsonStr ="[";				
	$("#dateTr tr").each(function(index){// 过滤克隆tr
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
	$("#myform").submit();
}

/**
 * 返回
 */
function toBack(){
	var actId = $("#activityId").val(); // 活动id
	var actNo = $("#activityNo").val(); // 活动No
	window.location.href = rootPath + '/panicbuyActivity/commoditylist.do?activityId='+actId+'&activityNo='+actNo;
}
