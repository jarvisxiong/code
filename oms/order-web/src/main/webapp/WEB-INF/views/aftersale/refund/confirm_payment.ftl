<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>确认付款页面</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active">

		<!--新增1--start-->
		<div class="row">
			<div class="col-lg-10 col-md-12 col-sm-12">
				<form id="myform" method="post" action="${BasePath !}/aftersalerefund/confirmPayment.do">
					<input type="hidden" name="refundId" value="${(refund.id) !}" />
					<input type="hidden" name="orderId" value="${(apply.order.id) !}" />
					<input type="hidden" name="orderNo" value="${(apply.orderNo) !}" />
					<input type="hidden" name="payPrice" value="${totalAmount !}" />
					<div class="addForm1">
						<div id="error_con" class="tips-form">
							<ul></ul>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<table class="table table-hover table-striped table-common">
					                <thead>
					                <tr>
					                    <th style="text-align:center;">编号</th>
					                    <th style="text-align:center;">商品图片</th>
					                    <th style="text-align:center;">商品名称</th>
                    					<th style="text-align:center;">商品属性</th>
					                    <th style="text-align:center;">条形码</th>
					                    <th style="text-align:center;">规格</th>
					                    <th style="text-align:center;">计量单位</th>
					                    <th style="text-align:center;">单价（元）</th>
					                    <th style="text-align:center;">实付金额（元）</th>
					                    <th style="text-align:center;">购买类型</th>
					                    <th style="text-align:center;">售后</th>
					                    <th style="text-align:center;">购买数量</th>
			                    		<th style="text-align:center;">退货数量</th>
					                </tr>
					                </thead>
					                <tbody>
					               <#if aftersaleApplyItemList?? > 
									<#list aftersaleApplyItemList as item >
									<tr>
										<td align="center">${(item_index+1)!}</td>
										<!-- <td> </td> -->
										<td align="center"> <img  width="80" height="80" <#if (item.commodityPic) ??>src="${(image_path)!}/${item.commodityPic?replace('size','origin')}"</#if> onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" alt="商品图片"></td>
										<td>${(item.commodityName) !} </td>
										<td align="center">${(item.commodityAttributeValues) !} </td>
										<td>${(item.commodityBarcode) !} </td>
										<td>${(item.commoditySpecifications) !} </td>
										<td>${(item.commodityUnit) !} </td>
										<td>${(item.commodityPrice) !} </td>
										<td>${(item.actPayAmount) !} </td>
										<#if item.commodityBuyType??>
											<#if item.commodityBuyType=='COMMON_BUY'>
												<td>普通</td>
											<#elseif item.commodityBuyType=='PRE_SALE'>
												<td>预售</td>
											<#elseif item.commodityBuyType=='PANIC_BUY'>
												<td>抢购</td>
											<#elseif  item.commodityBuyType=='NEWUSER_VIP'>
												<td>新用户专享</td>
											<#elseif  item.commodityBuyType=='WHOLESALE_MANAGER'>
												<td>批发</td>
											<#elseif  item.commodityBuyType=='ORDINARY_ACTIVITY'>
												<td>活动</td>
											<#else>
												<td></td>
											</#if>
										<#else>
											<td></td>
										</#if>
										<#if item.aftersaleStatus?? && item.aftersaleStatus=="0">
											<td>正常</td>
										<#elseif item.aftersaleStatus=="1">
											<td>退款处理中</td>
										<#elseif item.aftersaleStatus=="2">
											<td>换货处理中</td>
										<#elseif item.aftersaleStatus=="3">
											<td>退款成功 </td>
										<#else>
											<td>换货成功</td>
										</#if>	
										<td>${(item.commodityBuyNum) !} </td>
										<td>${(item.returnNum) !} </td>
										</tr>				
									</#list>
									</#if>               
					                <tr>
					                    <td colspan="14" align="right">商品总金额：${actPayAmountSum !}元 + 运费：${sendCost !}元 - 优惠：${favorablePrice !}元 - 红包：${redPacketAmount !}</td>
					                </tr>
					                </tbody>
					            </table>
					            <div class="payment">退货商品支付总金额：<span class="txt">¥ ${totalAmount !}</span></div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--新增1--end-->

	</div>
</div>
<script type="text/javascript">
	$(function(){
		executeValidateFrom('myform',"",paymentCallBack);
	});
	
	function butsubmit(){
		$("#myform").submit();
	}
	
	function paymentCallBack(result){
		if(result && result.status=="success"){
			$.frontEngineDialog.executeDialogContentTime(result.infoStr,1000);
			// 延迟1.5秒更新数据
			setTimeout(function () { 
				parent.location.reload(false);
		    }, 1500);
	    } else if(result && result.status=="error"){
	    	$.frontEngineDialog.executeDialogContentTime(result.infoStr,1000);
	    }
	}
</script>
<#include "../../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/>
</body>
</html>