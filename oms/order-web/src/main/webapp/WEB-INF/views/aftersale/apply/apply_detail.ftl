<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="edit"/>
<title>售后申请单详情</title>
</head>
<body class="box-body">
<#assign pageAnswer="http://${(orderDomain) !}:${(orderPort) !}/order-web/aftersaleapply/toApprove.do?id=${(applyID) !}"/>
<#assign action="${BasePath !}/aftersaleapply/toApprove.do?id=${(applyID) !}"/>
<div class="row">
       <div class="col-md-12">
         <div class="box-body">
      
<div class="tab-content">
    <div class="ordertop">
        <div class="orderleft">
            <ul>
                <li>售后申请单号：${(apply.applyNo) !}</li>
                <li>申请时间：${(apply.createDate?string("yyyy-MM-dd HH:mm:ss")) !}</li>
                <li>状态：<span class="txt">
               			 <#if apply.applyStatus?? && apply.applyStatus=="0">
								未审核
						<#elseif apply.applyStatus=="1">
								已通过
						<#else>
								已驳回
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
            <@permission name="oms:aftersale:cancelAudit">
            <#if (apply.applyStatus?exists && apply.applyStatus=="1") && ((!(apply.refundNo??)) || apply.refundNo=='' ) &&(apply.pickupStatus?exists && apply.pickupStatus=="0")>
           		<a class="btn btn-primary" href="#sale-state" data-toggle="tab" id="cancelAudit" onclick="cancelAudit('${(apply.pickupNo) !}','${(apply.orderNo) !}','${(apply.id) !}');">取消审核</a> 
            </#if>  
            </@permission>	  
            <@permission name="oms:aftersale:view">
            	<a class="btn btn-primary" href="#sale-state" data-toggle="tab" onclick="forwardSaleState()">查看售后状态</a>
            </@permission>
<!-- 							apply.applyStatus:${apply.applyStatus! }  -->
<!--  							apply.refundNo:${apply.refundNo! }<#if ((!(apply.refundNo??)) || apply.refundNo=='' )> 111</#if>  -->
<!--  							apply.pickupStatus:${apply.pickupStatus! }			 -->
                <button class="btn btn-primary btn-go-back" type="submit" onclick="back();">返回</button>
            </div>
        </div>
    </div>
    <div class="orderline">
        <div class="ordertitle" style="font-size: 14px">基本信息</div>
        <div class="ordertitle2">关联信息</div>
        <div class="addpege">
            <ul>
                <li>关联订单：<a href="${BasePath !}/order/view.do?id=${(apply.order.id) !}&pageType=${(action)!}">${(apply.orderNo) !}</a></li>
                <!--                 <li>收货仓库：${(apply.order.storageName) !}</li>重新定义为县仓 -->
                 			                <!--                 <li>收货仓库：${(apply.order.storageName) !}</li>重新定义为县仓 -->
			    <#if (apply.order.countyStoreName)?exists>
			                	<li>收货仓库：${(apply.order.countyStoreName) !}</li>
			            <#else>
			                	<li>收货仓库：${(apply.order.storageName) !}</li>
			     </#if>
                <li>客服审核时间：${(apply.serviceApproveDate?string("yyyy-MM-dd HH:mm:ss")) !}</li>
                <li>仓库审核时间：${(apply.storageApproveDate?string("yyyy-MM-dd HH:mm:ss")) !}</li>
                <li>关联取货单：<a href="${BasePath !}/aftersaleapickup/toApprove.do?pickupNo=${(apply.pickupNo) !}">${(apply.pickupNo) !}</a></li>
                <li>关联退款单：<a href="${BasePath !}/aftersalerefund/findRefundInfo.do?refundNo=${(apply.refundNo) !}">${(apply.refundNo) !}</a></li>
                <li>关联换货单：<a href="${BasePath !}/order/view.do?orderNo=${(apply.exchangeOrderNo) !}">${(apply.exchangeOrderNo) !}</a></li>
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
                <li>发票信息：
                	<#if apply.order.isInvoice ??>
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
        <div class="tab-pane fade in active"  id="myAccount">
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
					<td align="center"> <img width="80" height="80"    <#if (item.commodityPic) ??>src="${(image_path)!}/${item.commodityPic?replace('size','origin')}"</#if> onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" alt="商品图片"></td>
					<td>${(item.commodityName) !} </td>
					<td>${(item.commodityAttributeValues) !} </td>
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
            <div class="payment">退货商品支付总金额：<span class="txt">¥${totalAmount !}</span></div>
            <form id="myform" method="post" action="${BasePath !}/aftersaleapply/approveAftersaleapply.do">
            <div class="check-description mgb20">
                <p>审核描述：</p>
                <textarea class="form-control" rows="3"  id="serviceApproveDesc" name="serviceApproveDesc">${(apply.serviceApproveDesc) !}</textarea>
            </div>
            <div class="ordertop">
		        <div class="orderright">
		            <div class="btn-div3"> 		            	
		            		<input type="hidden" name="id"  id="id" value="${(apply.id) !}">
		            		<input type="hidden" name="applyStatus" id="applyStatus" >
		            		<input type="hidden" name="pickupNo" id="pickupNo"  value="${(apply.pickupNo) !}">
		            		<input type="hidden" name="refundNo" id="refundNo"  value="${(apply.refundNo) !}">
		            		<input type="hidden" name="orderNo" id="orderNo"  value="${(apply.orderNo) !}">
			            	<#if apply.applyStatus?? && apply.applyStatus=="0">
			            		<div id="adopt">	
			            	 <@permission name="oms:aftersale:approve">
            					<a class="btn btn-primary "  onclick="changeStatus('1')">通过</a>
           					 </@permission>			
			            	<@permission name="oms:aftersale:rebut">
            					<a class="btn btn-primary "  onclick="changeStatus('2')">驳回</a>		
           					 </@permission>	
			                	</div>	
							</#if>
						</form>        		               
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
<!--  架构加了js的警告 -->
<script type="text/javascript" src="${BasePath}/asset/js/aftersale/apply/apply_list.js?v=${ver !}"></script>
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
	
	
	
	function changeStatus(value){
		$("#adopt").hide();
		executeValidateFrom('myform');
		$("#applyStatus").val(value);
		$("#myform").submit();
	}
	
	function back(){
		$.frontEngineDialog.executeDialog('isReturn_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否确定放弃当前录入信息？　　','100%','100%',
					function(){
						window.location.href= rootPath + "/aftersaleapply/afterList.do";
					}
				);
		}
	
	function forwardSaleState(){
		$('#active-sale a').click();
		var t = $('#active-sale').offset().top;
        $(window).scrollTop(t);
        
	}
</script>
<#include "../../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/>
</body>
</html>
