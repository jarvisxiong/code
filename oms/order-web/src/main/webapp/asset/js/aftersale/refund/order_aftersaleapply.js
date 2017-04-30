/**
 * 雷---2016-06-28---加载
 */
$(function() {
/**
 * 交易完成可以选---退货(已收到货)、 换货(已收到货)
 * 
 * 待收货可以选----退款(没收到货)
 * 
 *      * 订单状态.
	 *  0：待付款，1：待收货，2：退款申请中，3：交易关闭(说明有售后)，4：交易完成，5：订单已取消
 * 
 */
	var orderStatus=$("#orderStatus").val();
	//console.log("orderStatus________"+orderStatus);
	//orderStatus="4";
	switch (orderStatus) {
	case "1":
		//console.log("orderStatus____1____"+orderStatus);
		$("#refund").attr("checked", true);
		$("#returnGoods").attr("disabled", true);
		$("#exchangeGoods").attr("disabled", true);
		$("#reason").html("");
		$("#reason").html(getRefundReason());
		break;
	case "4":
		$("#refund").attr("disabled", true);
		$("#returnGoods").attr("checked", true);
		$("#reason").html("");
		$("#reason").html(getReturnGoodsReason());
		break;
	};
	$("#refund").click(function(){
		$("#reason").html("");
		$("#reason").html(getRefundReason());
	});
	$("#returnGoods").click(function(){
		$("#reason").html("");
		$("#reason").html(getReturnGoodsReason());
	});
	$("#exchangeGoods").click(function(){
		$("#reason").html("");
		$("#reason").html(getExchangeGoods());
	});
});
/**
 * 雷---2016-06-28
 * 退款(没收到货)
 * @returns {String}
 */
function getRefundReason(){
	var str=" <label style='width: auto;' class='radio-inline'><input type='radio'  value='0' name='reason'> 长时间没收到货　</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='1' name='reason'> 信息填写错误，没收到货　</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='2' name='reason'> 没收到货，不想要了　</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='3' name='reason'> 其他原因　</label></br>"
		;
	return str;
}
/**
 * 雷---2016-06-28
 * 退货(已收到货)
 * @returns {String}
 */
function getReturnGoodsReason(){
	var str=" <label style='width: auto;' class='radio-inline'><input type='radio'  value='14' name='reason'> 收到商品破损　</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='15' name='reason'> 商品错发/漏发</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='16' name='reason'> 收到商品不符　</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='17' name='reason'> 商品质量问题</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='18' name='reason'>商品比周边商贵</label></br>"
		;
	return str;
}
/**
 * 雷---2016-06-28
 * 换货(已收到货)
 * @returns {String}
 */
function getExchangeGoods(){
	var str=" <label style='width: auto;' class='radio-inline'><input type='radio'  value='31' name='reason'> 收到商品破损　</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='32' name='reason'> 商品错发/漏发　</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='33' name='reason'> 商品质量问题　</label></br>"
		+" <label style='width: auto;' class='radio-inline'><input type='radio'  value='30' name='reason'> 其他原因　</label></br>"
		;
	return str;
}
/**
 * 雷---2016-09-18
 * 计算赠品的申请售后时信息
 */
function calculationGifts(mainCommodityId,returnMainNum){
    $.ajax({ 
        url:rootPath + "/aftersaleapply/calculationGifts.do",
		type : "POST",
		dataType : "json",
		data:{mainCommodityId:mainCommodityId,returnMainNum:returnMainNum},
		async : false,
		cache : true, 
        success:function(data){ 
        	$("tr[name='orderdetail_tr']").remove();
        	replaceGifts(data);
        }, 
        error:function(XMLHttpRequest, textStatus, errorThrown){ 
            alert(XMLHttpRequest.status); 
            alert(XMLHttpRequest.readyState); 
            alert(textStatus); 
        } 
    }); 
}
/**
 * 雷---2016-09-18
 * 重置赠品信息
 */
function replaceGifts(data){
	var trbody="";
	data=data.data;
	for (var i = 0; i < data.length; i++) {
			trbody+="<tr name='orderdetail_tr'>";
			trbody+="    <td valign='middle'>"+data[i].commodityTitle+"</td>"
			trbody+="    <td valign='middle' style='text-align:center;'>"+data[i].commodityTitle+"</td>"
			trbody+="    <td valign='middle' style='text-align:center;'>"+data[i].commoditySpecifications+""+data[i].commodityUnit+"</td>"
			trbody+="    <td valign='middle'>￥"+data[i].actSalePrice+"</td>"
			trbody+="    <td valign='middle' style='text-align:center;'><input type='text' readonly='readonly' value='"+data[i].buyNum+"' style='float:left;width:40px; text-align:center;' class='form-control input-sm txt_mid'></td>"
			trbody+="</tr>";
	}
	$(".table tbody").append(trbody);
}