<#include "../../global.ftl" encoding="utf-8">

<!DOCTYPE html>
<html class="${sys !} ${mod !}">
<head>
<meta name="decorator" content="edit"/>
<title>售后申请单打印</title>
<style type="text/css">
.box-body .orderline{padding:0;}
.box-body .orderline .addpege{padding-top:0;}
.box-body .ordertitle2{position: absolute;top: 170px; right:0;    padding: 0px 0 8px 10px;    border-top:none;}
.box-body .tab-content{position: relative;}
.salesTitle{text-align:center; font-size:14px; border-bottom:1px solid #ccc; line-height:30px; font-weight:bold;}
</style>
</head>
<body class="box-body">
<div class="row">
       <div class="col-md-12">
         <div class="box-body">
      
<div class="tab-content">
	<div class="salesTitle">售后取货单</div>
    <div class="orderline">
        <div class="addpege">
            <ul>
                <li>售后取货单号：${(apply.pickupNo) !}</li>
                <li>关联售后申请单号：${(apply.applyNo) !}</li>
                <li>关联订单号：${(apply.orderNo) !}</li>
                <li>申请时间：${(apply.createDate?string("yyyy-MM-dd HH:mm:ss")) !}</li>
                <li>申请人：${(apply.applyPersonName) !}</li>
                <li>申请人联系方式：${(apply.applyPersonPhone) !}</li>
                <li>申请人地址：${(apply.applyPersonAddress) !}</li>
                <li>配送人：${(apply.order.sendPersonName) !}</li>
                <li>合伙人联系方式：${(apply.order.sendPersonPhone) !}</li>
                <li>合伙人地址：${(apply.order.servicePoint) !}</li>
                <li>收货仓库：${(apply.order.storageName) !}</li>
                
                <li>售后服务类型:
                	<#if (apply.applyType)?exists && (apply.applyType) == '0'>退款(没收到货)
                	<#elseif (apply.applyType)?exists && (apply.applyType) == '1'>退货(已收到货)
                	<#elseif (apply.applyType)?exists && (apply.applyType) == '2'>换货(已收到货)
                	</#if>
                </li>
                <li id="orderStatus">${(apply.reasonSelect) !}</li>
                <li>原因说明：${(apply.reasonExplain) !}</li>
                <li>审核描述：${(apply.serviceApproveDesc) !}</li>
            </ul>
        </div>
    </div>
    <div class="ordertitle2" align="right" id="buttonName">
    	<@permission name="oms:aftersale:printer">
				<button class="btn btn-primary " onclick="printerPickup();">打印</button>
		</@permission>
   	 
   	 <button class="btn btn-primary btn-go-back" type="submit" onclick="isReturn();">返回</button>
    </div>
    <div class="tab-content">
        <div class="tab-pane fade in active" id="myAccount">
            <table class="table table-hover table-striped table-common">
                <thead>
                <tr>
                    <th style="text-align:center;">编号</th>
                    <th style="text-align:center;">商品名称</th>
                    <th style="text-align:center;">商品属性</th>
                    <th style="text-align:center;">条形码</th>
                    <th style="text-align:center;">规格</th>
                    <th style="text-align:center;">购买数量</th>
                    <th style="text-align:center;">退货数量</th>
                </tr>
                </thead>
                <tbody>
               <#if aftersaleApplyItemList?? > 
				<#list aftersaleApplyItemList as item >
					<tr>
					<td align="center">${item_index + 1}</td>
					<td align="center">${(item.commodityName) !} </td>
					<td align="center">${(item.commodityAttributeValues) !} </td>
					<td align="center">${(item.commodityBarcode) !} </td>
					<td align="center">${(item.commoditySpecifications) !} </td>
					<td  align="center">${(item.commodityBuyNum) !} </td>
					<td  align="center">${(item.returnNum) !} </td>	
					</tr>					
				</#list>
				</#if>               
                
                </tbody>
            </table>
            <div style="font-size: 16px;padding: 5px 10px;margin: 0 0 20px 0;text-align: left;">
	            客户：<input type="text" style="border: none;width:90px;"> 
	            合伙人：<input type="text" style="border: none;width:90px;">
	            物流：<input type="text" style="border: none;width:90px;">
	            仓库管理员：<input type="text" style="border: none;width:90px;">
            </div>
        </div>
    </div>
</div>
  </div>
        </div>
 </div>
 <#include "../../common/css.ftl" encoding="utf-8">
 <#include "../../common/js.ftl" encoding="utf-8">
<#include "../../common/select.ftl" encoding="utf-8">
<script type="text/javascript" src="${BasePath !}/asset/js/common/common.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/aftersale/pickup/apply_printer.js?v=${ver !}"></script>
<script type="text/javascript">
	var buyType='${(apply.order.buyType) !}';
	var payType='${(apply.order.payType) !}';
	var typeObj={COMMON_BUY:'普通',PRE_SALE:'预售',PANIC_BUY:'抢购',NEWUSER_VIP:'新手专享',WHOLESALE_MANAGER:'批发',
			ORDINARY_ACTIVITY:'活动',ALIPAYPC:'支付宝PC',ALIPAYSM:'支付宝扫码',WXPAY:'微信支付',ALIPAYAPP:'支付宝APP'};
	$(function(){
		$("#payType").text("支付方式:"+typeObj[payType]);
	});
		
	function changeStatus(value){
		$("#applyStatus").val(value);
		$("#myform").submit();
	}
	
	function printerPickup(){
		$("#buttonName").css("display","none");
		window.print();
		$("#buttonName").css("display","block");
	}
</script>
<#include "../../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/>
</body>
</html>
