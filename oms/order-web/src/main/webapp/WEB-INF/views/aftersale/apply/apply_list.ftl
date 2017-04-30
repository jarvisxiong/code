
<html>
<head>
<meta name="decorator" content="list" />
<title>售后列表</title>
</head>
<body>
<style>
body{overflow-y:scroll !important}
.wrapper-aftersales-status{display:inline;position:relative;}
.logisticspage{position:absolute;right:0;top:22px;height:200px;}
</style>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="brandList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form"  
					<#if tblshow?? && tblshow == "0">
							<#assign action="${BasePath !}/aftersaleapply/afterList.do?tblshow=0"/>									
					<#elseif tblshow?? && tblshow == "1">
							<#assign action="${BasePath !}/aftersaleapickup/afterPickupList.do?tblshow=1"/>
					<#elseif tblshow?? && tblshow == "2">
							<#assign action="${BasePath !}/aftersalerefund/refundList.do?tblshow=2"/>
					</#if>
					action="${action !}"
					 method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>订单编号：</label> 
								<div class="div-form">
									<input id="orderNo" name="orderNo" value="${(aftresal.orderNo) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							
							<div class="form-td">
								<label>手机号：</label> 
								<div class="div-form">
									<input id="applyPersonPhone" name="applyPersonPhone" value="${(aftresal.applyPersonPhone) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<div class="form-td">
							<#if tblshow?? && tblshow == "0">
								<label>申请单状态：</label> 
							<#elseif tblshow?? && tblshow == "1">
								<label>取货单状态：</label> 
							<#elseif tblshow?? && tblshow == "2">
								<label>退款单状态：</label> 
							<#else>
							</#if>
								<div class="div-form">										
										<#if tblshow?? && tblshow == "0">
										  <select class="form-control input-sm txt_mid input-select" id="applyStatus" name="applyStatus">
                                            <option value="">不限</option>        
											<option value="0" <#if (aftresal.applyStatus) ??><#if aftresal.applyStatus == '0'>selected="selected"</#if></#if> >未审核</option>
											<option value="1" <#if (aftresal.applyStatus) ??><#if aftresal.applyStatus == '1'>selected="selected"</#if></#if> >已通过</option>
											<option value="2" <#if (aftresal.applyStatus) ??><#if aftresal.applyStatus == '2'>selected="selected"</#if></#if> >已驳回</option> 
										 </select>										
										<#elseif tblshow?? && tblshow == "1">
										 <select class="form-control input-sm txt_mid input-select" id="pickupStatus" name="pickupStatus">
                                            <option value="">不限</option>  
											<option value="0" <#if (pickup.pickupStatus) ??><#if pickup.pickupStatus == '0'>selected="selected"</#if></#if> >未审核</option>
											<option value="1" <#if (pickup.pickupStatus) ??><#if pickup.pickupStatus == '1'>selected="selected"</#if></#if> >已通过</option>
										 </select>
										<#elseif tblshow?? && tblshow == "2">
										 <select class="form-control input-sm txt_mid input-select" id="refundStatus" name="refundStatus">
                                            <option value="">不限</option>  
											<option value="0" <#if (refund.refundStatus) ??><#if refund.refundStatus == '0'>selected="selected"</#if></#if> >未审核</option>
											<option value="1" <#if (refund.refundStatus) ??><#if refund.refundStatus == '1'>selected="selected"</#if></#if> >已通过</option>
										    <option value="2" <#if (refund.refundStatus) ??><#if refund.refundStatus == '2'>selected="selected"</#if></#if> >已退款</option>
										  
										</select>
										</#if>
								</div>
							</div>
							<div class="form-td">
									<label>申请时间：</label> 
									<div class="div-form"><input name="applyStartDate" id="applyStartDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'applyEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(aftresal.applyStartDate) !}">
                                    -
                                    <input name="applyEndDate" id="applyEndDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'applyStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(aftresal.applyEndDate) !}">
                               		</div>
								</div>	
							<div class="form-td">
									<label>客服审核时间：</label> 
									<div class="div-form"><input name="serviceApproveStartDate" id="serviceApproveStartDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'serviceApproveEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(aftresal.serviceApproveStartDate) !}">
                                    -
                                    <input name="serviceApproveEndDate" id="serviceApproveEndDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'serviceApproveStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(aftresal.serviceApproveEndDate) !}">
                               		</div>
							</div>	

							<div class="form-td">
								<label>售后服务类型：</label> 
								<div class="div-form">
									<select class="form-control input-sm txt_mid input-select" id="applyType" name="applyType">
										<option value="">不限</option>
										<option value="0" <#if (aftresal.applyType) ??><#if aftresal.applyType == '0'>selected="selected"</#if></#if> >退款(没收到货)</option>
										<option value="1" <#if (aftresal.applyType) ??><#if aftresal.applyType == '1'>selected="selected"</#if></#if> >退货(已收到货)</option>
										<#if tblshow?? && tblshow != "2">
										<option value="2" <#if (aftresal.applyType) ??><#if aftresal.applyType == '2'>selected="selected"</#if></#if> >换货(已收到货)</option>
										</#if>
<!-- 										<option value="3" <#if (aftresal.applyType) ??><#if aftresal.applyType == '3'>selected="selected"</#if></#if> >订单分配错误</option>										
 -->									</select>
								</div>
							</div>
							<div class="form-td">
									<label>仓储审核时间：</label> 
									<div class="div-form"><input name="storageApproveStartDate" id="storageApproveStartDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'storageApproveEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(aftresal.storageApproveStartDate) !}">
                                    -
                                    <input name="storageApproveEndDate" id="storageApproveEndDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'storageApproveStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(aftresal.storageApproveEndDate) !}">
                               		</div>
							</div>	
							<div class="form-td">
								<label>申请人：</label> 
								<div class="div-form">
									<input id="applyPersonName" name="applyPersonName" value="${(aftresal.applyPersonName) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<div class="form-td">
								<label>售后申请单号：</label> 
								<div class="div-form">
									<input id="applyNo" name="applyNo" value="${(aftresal.applyNo) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<div class="form-td">
								<label>售后取货单号：</label> 
								<div class="div-form">
									<input id="pickupNo" name="pickupNo" value="${(aftresal.pickupNo) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<div class="form-td">
								<label>退款单号：</label> 
								<div class="div-form">
									<input id="refundNo" name="refundNo" value="${(aftresal.refundNo) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<#if tblshow?exists && tblshow == "0">
							<div class="form-td">
								<label>换货订单号：</label> 
								<div class="div-form">
									<input id="exchangeOrderNo" name="exchangeOrderNo" value="${(aftresal.exchangeOrderNo) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							
							<div class="form-td">
								<label>配送服务点：</label> 
								<div class="div-form">
									<input id="order.servicePoint" name="order.servicePoint" value="${(aftresal.order.servicePoint) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							</#if>
							
						<#if tblshow?exists && tblshow == "2">
							<div class="form-td">
								<label>商品条形码：</label> 
								<div class="div-form">
									<input id="aftersaleApply.commodityBarcode" name="aftersaleApply.commodityBarcode" value="${(aftresal.aftersaleApply.commodityBarcode) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							</#if>
						</div>
					</div>
					<div class="btn-div3">
						<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
						<a id="table-cleanBtn" class="btn btn-primary btn-sm" onclick="onEmpty(${tblshow !});"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a>
						<#if tblshow?? && tblshow == "0">
						<a href="javascript:void(0);" onclick="importApplyList();" class="btn btn-primary btn-sm"><i class="fa fa-pencil"></i>&nbsp;&nbsp;批量导出</a>
						<@permission name="oms:aftersale:importRefundList">
						<a href="javascript:void(0);" onclick="importRefundList();" class="btn btn-primary btn-sm"><i class="fa fa-pencil"></i>&nbsp;&nbsp;财务批量导出</a>
						</@permission>
						</#if>
					</div>
					</form>
				</div>
			</div>
			<div class="navtabs-title">
				<ul class="nav nav-tabs" style="padding-left: 1%;">
				  <@permission name="oms:aftersale:applyOrder">
						<li <#if tblshow?? && tblshow == "0">class="active"</#if>><a href="${BasePath !}/aftersaleapply/afterList.do?tblshow=0">售后申请单</a></li>
				  </@permission>	
				  
				  <@permission name="oms:aftersale:pickup">
						<li <#if tblshow?? && tblshow == "1">class="active"</#if>><a href="${BasePath !}/aftersaleapickup/afterPickupList.do?tblshow=1" >售后取货单</a></li>
				  </@permission>
				  
				  <@permission name="oms:aftersale:refundList">
						<li <#if tblshow?? && tblshow == "2">class="active"</#if>><a href="${BasePath !}/aftersalerefund/refundList.do?tblshow=2" >退款单</a></li>
				  </@permission>
				  
				  <@permission name="oms:aftersale:changeOrder">
						<li <#if tblshow?? && tblshow == "3">class="active"</#if>><a href="${BasePath !}/exchangeorder/exchangeOrderList.do?tblshow=3" >换货订单</a></li>		
				  </@permission>				
				</ul>
			</div>
			
			<div class="tab-content">
		<table class="table table-hover table-striped bor2 table-common">
			<thead>
				<tr>
					<th class="tab-td-center"></th>
					<th class="tab-td-center"><input type="checkbox" name="check"></th>
					<#if tblshow?? && tblshow == "0">
						<th class="tab-td-center">售后申请单号</th>
						<th class="tab-td-center">关联订单</th>
						<th class="tab-td-center">关联售后取货单</th>
						<th class="tab-td-center">关联退款单</th>
					<#elseif tblshow?? && tblshow == "1">
						<th class="tab-td-center">售后取货单</th>
						<th class="tab-td-center">关联订单</th>
						<th class="tab-td-center">关联售后申请单号</th>
						<th class="tab-td-center">关联退款单</th>
					<#elseif tblshow?? && tblshow == "2">
						<th class="tab-td-center">退款单</th>
						<th class="tab-td-center">关联订单</th>
						<th class="tab-td-center">关联售后申请单号</th>
						<th class="tab-td-center">关联售后取货单</th>
					<#else>
					</#if>					
						<th class="tab-td-center">关联换货订单</th>
						<th class="tab-td-center">申请人</th>
						<th class="tab-td-center">手机号</th>
						<th class="tab-td-center">申请时间</th>
						<#if tblshow?? && tblshow == "2">
							<th class="tab-td-center">审核人</th>
							<th class="tab-td-center">审核时间</th>
						</#if>
						<th class="tab-td-center">售后服务类型</th>
						<th class="tab-td-center">状态</th>
						<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<#if aftersaleApplyList?? > 
				<#list aftersaleApplyList as item >
				<tr>
					<td align="center">${item_index + 1}</td>
					<td class="tab-td-center"><input type="checkbox" name="check" value="${(item.id) !}"></td>
					<#if tblshow?? && tblshow == "0">
						<td align="center"><a href="${BasePath !}/aftersaleapply/toApprove.do?id=${(item.id) !}">${(item.applyNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/order/view.do?id=${(item.orderId) !}&pageType=${(action)!}">${(item.orderNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/aftersaleapickup/toApprove.do?pickupNo=${(item.pickupNo) !}">${(item.pickupNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/aftersalerefund/findRefundInfo.do?refundNo=${(item.refundNo) !}&pageType=AftersaleRefund">${(item.refundNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/order/view.do?orderNo=${(item.exchangeOrderNo) !}">${(item.exchangeOrderNo) !}</a></td>
						<td align="center">${(item.applyPersonName) !}</td>
						<td align="center">${(item.applyPersonPhone) !}</td>
						<td align="center">${(item.createDate?string("yyyy-MM-dd HH:mm:ss")) !}</td>					
						<#if item.applyType?? && item.applyType=="0">
								<td align="center">退款(没收到货)</td>
						<#elseif item.applyType=="1">
								<td align="center">退货(已收到货)</td>
						<#elseif item.applyType=="2">
								<td align="center">换货(已收到货)</td>
						<#else>
								<!-- <td align="center">订单分配错误</td> -->
						</#if>		
						<#if item.applyStatus?? && item.applyStatus=="0">
								<td align="center">未审核</td>
						<#elseif item.applyStatus=="1">
								<td align="center">已通过</td>
						<#elseif item.applyStatus=="2">
								<td align="center">已驳回</td>
						<#else>
								<td align="center"></td>
						</#if>		
						<td>
							<#if item.applyStatus?? && item.applyStatus=="0">
								  <@permission name="oms:aftersale:approve">
								 <a href="${BasePath !}/aftersaleapply/toApprove.do?id=${(item.id) !}">审核</a> |
								</@permission>	
								</#if>								 	
							<#if (tblshow?exists && tblshow == "0") && (item.applyStatus?exists && item.applyStatus=="1") && ((!(item.refundNo??)) || item.refundNo=='' ) && (item.pickupStatus?exists && item.pickupStatus=="0")>
								 <@permission name="oms:aftersale:cancelAudit">
									<a id="cancelAudit" style="cursor: pointer;" href="${BasePath !}/aftersaleapply/toApprove.do?id=${(item.id) !}" >取消审核 </a> |
								 </@permission>	
							</#if>
<!-- 							tblshow:${tblshow! } -->
<!-- 							item.applyStatus:${item.applyStatus! } -->
<!-- 							item.refundNo:${item.refundNo! }<#if ((!(item.refundNo??)) || item.refundNo=='' )> 111</#if> -->
<!-- 							item.pickupStatus:${item.pickupStatus! }			 -->
							  <@permission name="oms:aftersale:view">
								 <div class="wrapper-aftersales-status">
								 <a href="${BasePath !}/aftersaleapply/toApprove.do?id=${(item.id) !}"  title="${(item.orderNo) !}" class="viewlogistics logistics-hover" >查看售后状态</a>
								<div class="logisticspage" id="status${(item.orderNo) !}"></div>
								</div>
								</@permission>																			
						</td>
					<#elseif tblshow == "1">
						<td align="center"><a href="${BasePath !}/aftersaleapickup/toApprove.do?id=${(item.id) !}&pageType=AftersalePickup">${(item.pickupNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/order/view.do?id=${(item.aftersaleApply.orderId) !}&pageType=${(action)!}">${(item.aftersaleApply.orderNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/aftersaleapply/toApprove.do?id=${(item.aftersaleApply.id) !}">${(item.aftersaleApply.applyNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/aftersalerefund/findRefundInfo.do?refundNo=${(item.aftersaleApply.refundNo) !}&pageType=AftersaleRefund">${(item.aftersaleApply.refundNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/order/view.do?orderNo=${(item.aftersaleApply.exchangeOrderNo) !}">${(item.aftersaleApply.exchangeOrderNo) !}</a></td>
						<td align="center">${(item.aftersaleApply.applyPersonName) !}</td>
						<td align="center">${(item.aftersaleApply.applyPersonPhone) !}</td>
						<td align="center">${(item.createDate?string("yyyy-MM-dd HH:mm:ss")) !}</td>
						<#if item.aftersaleApply.applyType?? && item.aftersaleApply.applyType=="0">
								<td align="center">退款(没收到货)</td>
						<#elseif item.aftersaleApply.applyType=="1">
								<td align="center">退货(已收到货)</td>
						<#elseif item.aftersaleApply.applyType=="2">
								<td align="center">换货(已收到货)</td>
						<#else>
								<!-- <td align="center">订单分配错误</td> -->
						</#if>		
						<#if item.pickupStatus?? && item.pickupStatus=="0">
								<td align="center">未审核</td>
						<#elseif item.pickupStatus=="1">
								<td align="center">已通过</td>							
						<#else>
								<td align="center"></td>
						</#if>	
						<td>
							<#if item.pickupStatus?? && item.pickupStatus=="0">
							 <@permission name="oms:aftersale:printer">
								 <a href="${BasePath !}/aftersaleapickup/toPrinter.do?id=${(item.aftersaleApply.id) !}">打印</a> |		
								</@permission>																				
							</#if>				
							<@permission name="oms:aftersale:view">
								 <div class="wrapper-aftersales-status">
								 <a href="${BasePath !}/aftersaleapickup/toApprove.do?id=${(item.id) !}" title="${(item.orderNo) !}"  class="viewlogistics logistics-hover" >查看售后状态</a>
								<div class="logisticspage" id="status${(item.orderNo) !}"></div>
								</div>
								</@permission>												
								
						</td>
					<#elseif tblshow == "2">
						<td align="center"><a href="${BasePath !}/aftersalerefund/findRefundInfo.do?id=${(item.id) !}&pageType=AftersaleRefund">${(item.refundNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/order/view.do?id=${(item.aftersaleApply.orderId) !}&pageType=${(action)!}">${(item.aftersaleApply.orderNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/aftersaleapply/toApprove.do?id=${(item.aftersaleApply.id) !}">${(item.aftersaleApply.applyNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/aftersaleapickup/toApprove.do?pickupNo=${(item.aftersaleApply.pickupNo) !}">${(item.aftersaleApply.pickupNo) !}</a></td>
						<td align="center"><a href="${BasePath !}/order/view.do?orderNo=${(item.aftersaleApply.exchangeOrderNo) !}">${(item.aftersaleApply.exchangeOrderNo) !}</a></td>
						<td align="center">${(item.aftersaleApply.applyPersonName) !}</td>
						<td align="center">${(item.aftersaleApply.applyPersonPhone) !}</td>
						<td align="center">${(item.createDate?string("yyyy-MM-dd HH:mm:ss")) !}</td>
						<td align="center">${(item.auditingName) !}</td>
						<td align="center">${(item.auditingTime?string("yyyy-MM-dd HH:mm:ss")) !}</td>
						<#if item.aftersaleApply.applyType?? && item.aftersaleApply.applyType=="0">
								<td align="center">退款(没收到货)</td>
						<#elseif item.aftersaleApply.applyType=="1">
								<td align="center">退货(已收到货)</td>
						<#elseif item.aftersaleApply.applyType=="2">
								<td align="center">换货(已收到货)</td>
						<#else>
								<!-- <td align="center">订单分配错误</td> -->
						</#if>	
						<#if item.refundStatus?? && item.refundStatus=="0">
								<td align="center">未审核</td>
						<#elseif item.refundStatus=="1">
								<td align="center">已通过</td>
						<#elseif item.refundStatus=="2">
								<td align="center">已退款</td>
						<#else>
								<td align="center"></td>
						</#if>	
						<td>
							<#if item.refundStatus?? && item.refundStatus=="0">
								<@permission name="oms:aftersale:printer">
									 <a href="${BasePath !}/aftersalerefund/toPrinter.do?id=${(item.aftersaleApply.id) !}">打印</a> |		
								</@permission>
								<@permission name="oms:aftersale:approve">
									<a href="${BasePath !}/aftersalerefund/findRefundInfo.do?id=${(item.id) !}">审核</a> | 	
								</@permission>										
							<#elseif item.refundStatus=="1">
								<@permission name="oms:aftersale:refund">
									<a href="${BasePath !}/aftersalerefund/findRefundInfo.do?id=${(item.id) !}">确认退款</a>	| 	
								</@permission>													
							</#if>				
								<@permission name="oms:aftersale:view">
									<div class="wrapper-aftersales-status">
									<a href="${BasePath !}/aftersalerefund/findRefundInfo.do?id=${(item.id) !}"  title="${(item.aftersaleApply.orderNo) !}" class="viewlogistics logistics-hover" >查看售后状态</a>
									<div class="logisticspage" id="status${(item.aftersaleApply.orderNo) !}"></div>
									</div>
								</@permission>												
								
						</td>
					<#else>
					</#if>																	
				</tr>
				</#list> 
				</#if>
			</tbody> 
		</table>
			</div>
			<#include "../../common/page_macro.ftl" encoding="utf-8"> 
			<@my_page pageObj/>
		</div>
	</div>
	<div style="display:none;"  ><iframe id="orderImport_iframe" ></iframe></div> 
<#include "../../common/tree.ftl" encoding="utf-8">
<#include "../../common/css.ftl" encoding="utf-8">
<#include "../../common/select.ftl" encoding="utf-8">
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
<script type="text/javascript" src="${BasePath}/asset/js/aftersale/apply/apply_list.js?v=${ver !}"></script>
<script type="text/javascript">
$(function(){
	$(".input-select2").select2();
	$('.viewlogistics').hover(function(e){
		var $detail = $(this).next('.logisticspage').show();
		
		if($.trim($detail.html()) == ''){			
			var lists=$(this).parents("tr").children(0);
			$.ajax({
				url : rootPath + "/aftersaleapply/getOmsOrderStatus.do",
				data : {
					orderNo : $(lists[3]).text()
				},
				type : "POST",
				dataType : "json",
				async : false,
				cache : false,
				success : function(result) {		
					var length = result.length;
					var showStr='';
					if(length>0){
						for (var i = 0; i < length; i++)
						{				
							showStr+='<h3>关联订单号：'+result[i].orderNo+'  售后状态</h3>'
		                    +'<dl><dt class="orangetxt">●'+result[i].description+'</dt>'
		                    +'<dd>'+result[i].createDate+'</dd></dl>'                
						}
						showStr+='<dl><dt>●以上为售后状态信息</dt></dl>';
						$("#status"+$(lists[3]).text()).html(showStr);
					}else{
						showStr='<h3>暂时没有售后状态</h3>';
						$("#status"+$(lists[3]).text()).html(showStr);
					}					
				}
			});	
		}
	});
	
	$(document).on('mouseleave', '.wrapper-aftersales-status', function(){
		$(this).find('.logisticspage').hide();
	});
});

function onEmpty(tblshow) {
	var url = "${BasePath !}/aftersaleapply/afterList.do?tblshow=0";
	if(tblshow == 1){
		url = "${BasePath !}/aftersaleapickup/afterPickupList.do?tblshow=1"
	} else if(tblshow == 2){
		url = "${BasePath !}/aftersalerefund/refundList.do?tblshow=2"
	}
    window.location.href=url;            
}

</script>
</body>
</html>
