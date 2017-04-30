<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="edit"/>
<title>售后退款单详情</title>
</head>
<body class="box-body">
<#if applyID?exists >
<#assign pageAnswer="http://${(orderDomain) !}:${(orderPort) !}/order-web/aftersalerefund/findRefundInfo.do?id=${(applyID) !}&refundNo=${refundNo! }"/>
<#assign action="${BasePath !}/aftersalerefund/findRefundInfo.do?id=${(applyID) !}&refundNo=${refundNo! }"/>
<#else>
<#assign pageAnswer="http://${(orderDomain) !}:${(orderPort) !}/order-web/aftersalerefund/findRefundInfo.do?refundNo=${refundNo! }"/>
<#assign action="${BasePath !}/aftersalerefund/findRefundInfo.do?refundNo=${refundNo! }"/>
</#if>
<div class="row">
       <div class="col-md-12">
         <div class="box-body">
			<div class="tab-content">
			    <div class="ordertop">
			        <div class="orderleft">
			            <ul>
			                <li>退款单号：${(refund.refundNo) !}</li>
			                <li>申请时间：${(apply.createDate?string("yyyy-MM-dd HH:mm:ss")) !}</li>
			                <li>状态：<span class="txt">
			               			<#if refund.refundStatus?? && refund.refundStatus=="0">
											未审核
									<#elseif refund.refundStatus=="1">
											已通过
									<#else>
											已退款
									</#if>	
			                </span></li>
			                <li>售后服务类型：
			                		<#if apply.applyType?? && apply.applyType=="0">
											退款(没收到货)
									<#elseif apply.applyType=="1">
											退货(已收到货)
									<#elseif apply.applyType=="2">
											换货(已收到货)
									<#else>
											订单分配错误
									</#if>
			                </li>
			                <li>原因选择：<span id="reason">${(apply.reasonSelect) !}</span></li>
			                <li>原因说明：${(apply.reasonExplain) !}</li>
			                <li></li>
			            </ul>
			        </div>
			        <div class="orderright">
			            <div class="btn-div3">
				            <#if refund.refundStatus?? && refund.refundStatus=="0">			
				        	   	 <@permission name="oms:aftersale:printer">
									<a class="btn btn-primary"   href="${BasePath !}/aftersalerefund/toPrinter.do?id=${(apply.id) !}" >打印</a>	
								</@permission>		
	            				 									
							</#if>
							<@permission name="oms:aftersale:view">
            					  <a class="btn btn-primary" href="#sale-state" data-toggle="tab" onclick="forwardSaleState()">查看售后状态</a>
          					 </@permission>		              
			             	<#if pageType?exists && pageType=='AftersaleRefund'>
		               			<button class="btn btn-primary btn-go-back" type="submit" onclick="back();">返回</button>
			               		<#else>
			               		<button class="btn btn-primary btn-go-back" type="submit" onclick="back();">返回</button>
			                </#if>
			            </div>
			        </div>
			    </div>
			    <div class="orderline">
			        <div class="ordertitle" style="font-size: 14px">基本信息</div>
			        <div class="ordertitle2">关联信息</div>
			        <div class="addpege">
			            <ul>
			                <li>关联订单：<a href="${BasePath !}/order/view.do?id=${(apply.orderId) !}&pageType=${(action)!}">${(apply.orderNo) !}</a></li>
			                <!--                 <li>收货仓库：${(apply.order.storageName) !}</li>重新定义为县仓 -->
			                <#if (apply.order.countyStoreName)?exists>
			                	<li>收货仓库：${(apply.order.countyStoreName) !}</li>
			                	<#else>
			                	<li>收货仓库：${(apply.order.storageName) !}</li>
			                </#if>
                  			
			                <li>关联售后申请订单：<a href="${BasePath !}/aftersaleapply/toApprove.do?id=${(applyId) !}">${(apply.applyNo) !}</a></li>
			                <li>财务审核时间：${(refund.approveDate?string("yyyy-MM-dd HH:mm:ss")) !}</li>
			                <li>售后取货单：<a href="${BasePath !}/aftersaleapickup/toApprove.do?pickupNo=${(apply.pickupNo) !}">${(apply.pickupNo) !}</a></li>
			                <li>客服审核时间：${(apply.serviceApproveDate?string("yyyy-MM-dd HH:mm:ss")) !}</li>
			                <li>仓库审核时间：${(apply.storageApproveDate?string("yyyy-MM-dd HH:mm:ss")) !}</li>
			            </ul>
			        </div>
			        <div class="ordertitle2">收货人信息</div>
			        <div class="addpege">
			            <ul>
               				<li>会员账号：<a href="http://${(ucDomain)!}:${(ucPort)!}/uc-web/member/form.do?id=${(apply.order.memberId) !}&viewstatus=view&pageType=${pageAnswer!}">${(apply.order.memberPhone) !}</a></li>
			            	<li>客户姓名：${(apply.order.consignName) !}</li>
                			<li>收货地址：${(apply.order.addressInfo) !}</li>
               				 <li>联系方式：${(apply.order.consignPhone) !}</li>
			            </ul>
			        </div>
			        <div class="ordertitle2">其他信息</div>
			        <div class="addpege">
			            <ul>
			                <li>订单类型：普通</li>
			                <li>配送人：${(apply.order.sendPersonName) !}</li>
			                <li>下单时间：${(apply.order.createDate?string("yyyy-MM-dd HH:mm:ss")) !}</li>
                			<li id="payType">支付方式：微信支付</li>
                			<li>配送人联系方式：${(apply.order.sendPersonPhone) !}</li>
			                <li>付款时间：${(apply.order.payTime?string("yyyy-MM-dd HH:mm:ss")) !}</li>
			                <li>发票信息：<#if apply.order.isInvoice ??>
			                		<#if apply.order.isInvoice=='1'>
			                			有
			                		<#else>
			                			无
			                		</#if>
		                		</#if>
                			</li>
			                <li>配送服务点：${(apply.order.servicePoint) !}</li>
			            </ul>
			        </div>
			        <div class="ordertitle2">申请信息</div>
			        <div class="addpege">
			            <ol>
			            	<#if imageList?? && (imageList?size>0)> 
								<#list imageList as item >
									<li><img width="100" height="100"  src="${(image_path)!}/${item?replace('size','origin')}"> </li>
								</#list>
							<#else>
								<li><img src="${BasePath !}/asset/img/noPic.jpg"> </li>
							</#if>
			            </ol>
			        </div>
			    </div>
			    <div class="ordertitle2">售后商品清单</div>
			    <div class="tab-content mgb10">
			        <div class="tab-pane fade in active" id="myAccount">
			            <table class="table table-hover table-striped table-common">
			                <thead>
			                <tr>
			                    <th style="text-align:center;">编号</th>
			                    <th style="text-align:center;">商品图片</th>
			                    <th style="text-align:center;">商品名称</th>
			                    <th style="text-align:center;">销售属性</th>
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
								<td align="center">${item_index + 1}</td>
								<td align="center"> <img  width="80" height="80"   <#if (item.commodityPic) ??>src="${(image_path)!}/${item.commodityPic?replace('size','origin')}"</#if> onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" alt="商品图片"></td>
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
			                    <td colspan="13" align="right">商品总金额：${actPayAmountSum !}元 + 运费：${sendCost !}元 - 优惠：${favorablePrice !}元 - 红包：${redPacketAmount !}</td>
			                </tr>
			                </tbody>
			            </table>
			            <div class="payment">退货商品支付总金额：<span class="txt">¥ ${totalAmount !}</span></div>
			            <div class="check-description mgb20">
			                <p>审核描述：</p>
			                <textarea class="form-control" rows="3" id="remark"></textarea>
			            </div>
			            <div class="ordertop">
					        <div class="orderright">
					            <div class="btn-div3">  
					            	<#if refund.refundStatus?? && refund.refundStatus=="0">					
					            	<form id="approveform" method="post" action="${BasePath !}/aftersalerefund/approveRefund.do">
					            		<input type="hidden" name="refundId" value="${(refund.id) !}"/>
					            		<input type="hidden" name="orderId" value="${(apply.order.id) !}" />
					            		<input type="hidden" name="orderNo" value="${(apply.orderNo) !}" />
					            		<input type="hidden" id="remarks" name="remarks" value=""/>
					            		<button class="btn btn-primary btn-sm" type="submit">通过</button>
									</form>       		               
					            	<#elseif refund.refundStatus=="1" >
					            	<@permission name="oms:aftersale:pay">
            							  <button class="btn btn-primary btn-sm" onclick="confirmPayment('${(apply.id) !}')">确认付款</button>	
          							 </@permission>
					              
									</#if> 
					            </div>
					        </div>
			    		</div>
			            <div class="navtabs-title">
			                <ul class="nav nav-tabs">
			                    <li class="active" id="active-sale"><a href="#sale-state" data-toggle="tab">售后状态</a></li>
			                    <li><a href="#sale-tracking" data-toggle="tab">订单追踪（物流状态）</a></li>
			                </ul>
			            </div>
			            <div class="mgb10"></div>
			            <div class="tab-content">
			                <div class="tab-pane fade in active" id="sale-state">
			                    <table class="table table-hover table-striped table-common">
			                        <colgroup>
			                            <col class="tab-order-2-col0">
			                            <col class="tab-order-2-col1">
			                            <col class="tab-order-2-col2">
			                        </colgroup>
			                        <thead>
			                        <tr>
			                            <th>处理时间</th>
			                            <th>处理信息</th>
			                            <th>操作人</th>
			                        </tr>
			                        </thead>
			                        <tbody>
			                        	  <#if omsOrderRecordList?? > 
											<#list omsOrderRecordList as item >
												<#if item.recordType?? && item.recordType=='0'>
													 <tr>
														<td>${(item.createDate?string("yyyy-MM-dd HH:mm:ss")) !} </td>
														<td>${(item.description) !} </td>
														<td>${(item.oprName) !} </td>	
													 </tr>
												 </#if>	
											</#list>
										</#if>             
			                        </tbody>
			                    </table>
			                </div>
			                <div class="tab-pane fade in" id="sale-tracking">
			                    <table class="table table-hover table-striped table-common">
			                        <colgroup>
			                            <col class="tab-order-2-col0">
			                            <col class="tab-order-2-col1">
			                            <col class="tab-order-2-col2">
			                        </colgroup>
			                        <thead>
			                        <tr>
			                            <th>处理时间</th>
			                            <th>处理信息</th>
			                            <th>操作人</th>
			                        </tr>
			                        </thead>
			                        <tbody>
			                          <#if omsOrderRecordList?? > 
								<#list omsOrderRecordList as item >
									<#if item.recordType?? && item.recordType=='1'>
										 <tr>
											<td>${(item.createDate?string("yyyy-MM-dd HH:mm:ss")) !} </td>
											<td>${(item.description) !} </td>
											<td>${(item.oprName) !} </td>	
										 </tr>
									 </#if>	
								</#list>
							</#if>    
			                        </tbody>
			                    </table>
			                </div>
			            </div>
			        </div>
			    </div>
			</div>
  		</div>
     </div>
 </div>

<#include "../../common/select.ftl" encoding="utf-8">
<script type="text/javascript" src="${BasePath !}/asset/js/aftersale/refund/refund_detail.js?v=${ver !}"></script>
<#include "../../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/>
<script type="text/javascript">
var buyType='${(apply.order.buyType) !}';
var payType='${(apply.order.payType) !}';
var reason='${(apply.reasonSelect) !}';
var typeObj={COMMON_BUY:'普通',PRE_SALE:'预售',PANIC_BUY:'抢购',NEWUSER_VIP:'新手专享',WHOLESALE_MANAGER:'批发',
		ORDINARY_ACTIVITY:'活动',ALIPAYPC:'支付宝PC',ALIPAYSM:'支付宝扫码',WXPAY:'微信支付',ALIPAYAPP:'支付宝APP',TFTFASTPAY:'快捷支付',18:'商品比周边商贵',16:'收到商品不符',17:'商品质量问题',
		15:'商品错发/漏发',14:'收到商品破损',0:'长时间没收到货',1:'信息填写错误没收到货',2:'没收到货不想要了',3:'其他原因',31:'收到商品破损',32:'商品错发/漏发',33:'商品质量问题',30:'其他原因'
		};
$(function(){
	executeValidateFrom('myform');
	$("#payType").text("支付方式:"+typeObj[payType]);
	$("#combuyType").text(typeObj[buyType]);
	$("#reason").text(typeObj[reason]);
	$("#showAfter").click(function(){
		$("#showAftersta").click();
		$("#afteraction").click();		
	});
	
	
});
function forwardSaleState(){
	$('#active-sale a').click();
	var t = $('#active-sale').offset().top;
    $(window).scrollTop(t);
    
}

function back(){
	window.location.href= rootPath + "/aftersalerefund/refundList.do?tblshow=2";
}

</script>
</body>
</html>
