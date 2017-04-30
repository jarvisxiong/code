function toSelectCommdity(){
		var url = rootPath + '/ordinaryActivity/toSelectCommdity.do';		
		var dlg=dialog({
            id: "toSelectCommdityForm",
            title: '商品设置-新增',
            lock: false,
            content:"<iframe  name='toSelectCommdityForm,"+window.location.href+"' src='"+url+"' width='950px' height='350px' frameborder='0' scrolling='auto' id='toSelectCommdityForm' style='overflow-x:hidden; '></iframe>",       
		}).showModal();
}


function caclTotal(){
		$(".tab-content tr").each(function(){
			calcTotalOne($(this));
		});
}
function calcTotalOne(parent){
	var count=0;
	var total=0.0;
	var acprice = parent.find("input[name='acprice']").val();
	var limitNum = parent.find("input[name='limitNum']").val();
	if(limitNum && limitNum>0){		
		count=parseInt(count)+parseInt(limitNum);
		$("#limitCount").val(count);
	}
	if(acprice && acprice>0){		
		total=parseFloat(total)+parseFloat(acprice);
		$("#showPrice").val(total.toFixed(2));
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
	// 赋值
	setValues();
	// 表单验证
	$("#myform").submit();
}


function setValues(){
	// 优惠价的最低值、最高值
	var maxVal = 0;
	var minVal = 0;
	
	var jsonObj = "[";
	// 遍历表格
	$("#dateTr tr").each(function(index){
		var skuId = $(this).find("input[name='skuId']").val();	// item的商品id
		var skuCode = $(this).find("input[name='skuCode']").val();	// item的商品编码
		var activityPrice = $(this).find("input[name='activityPrice']").val();	// item的商品活动价
		
		var skuTitle = $(this).find("td").eq(0).text();	// 商品标题
		var skuAttrGroup = $(this).find("td").eq(1).text();	// 属性组合
		var skuBarcode = $(this).find("td").eq(2).text();	// 条形码
		var skuPrice = $(this).find("td").eq(3).text();	// 商品价格
		
		jsonObj += "{commoditySkuId:'"+skuId+"',commoditySkuNo:'"+skuCode+"'," +
					"commoditySkuBarcode:'"+skuBarcode+"',commoditySkuTitle:'"+skuTitle+"'," +
					"attrGroup:'"+skuAttrGroup+"',commoditySkuPrice:"+skuPrice+",activityPrice:"+activityPrice+"},";
		// 获取最低价、最高价
		if (index == 0) {
			minVal = parseFloat(activityPrice);
			maxVal = parseFloat(activityPrice);
		} else {
			if (minVal > parseFloat(activityPrice)) {
				minVal = parseFloat(activityPrice)
			} else if(maxVal < parseFloat(activityPrice)) {
				maxVal = parseFloat(activityPrice);
			}
		}
		
		
	});
	jsonObj = jsonObj.substring(0, jsonObj.length-1);
	jsonObj += "]";
	// 赋值
	$("#activityitemDate").val(jsonObj);
//	// 优惠价
//	if (minVal == maxVal) {
//		$("#showPrice").val(minVal);
//	} else {
//		$("#showPrice").val(minVal + "-" + maxVal);
//	}
}


function changPrice(){
	// 最高价格、最低价格
	var min = "0";
	var max = "0";
	// 遍历，获取最大值、最小值
	$("#dateTr tr").each(function(index){
		var price = $(this).find("input[name='activityPrice']").val();
		if ("" != price && null != price) {
			if (index == 0) {
				min = price;
				max = price;
			} else {
				if (parseFloat(min) > parseFloat(price)) {
					min = price;
				}
				if(parseFloat(max) < parseFloat(price)) {
					max = price;
				}
			}
		}
	});
	// 给隐藏域赋值：最大值最小值相同，则给一个；否则给区间值
	if (min == max) {
		$("#showPrice").val(min);
	} else {
		$("#showPrice").val(min+" - "+max);
	}
}

/**
 * 返回
 */
function toBack(){
	var actId = $("#activityId").val(); // 活动id
//	var actNo = $("#activityNo").val(); // 活动No
	window.location.href = rootPath + '/ordinaryActivity/commodityList.do?activityId='+actId;
}

