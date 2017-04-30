<html>
	<head>
		<meta name="decorator" content="list" />
		<title>客户订单回收列表</title>
	</head>

	<body>
		<style>
			body {
				overflow-y: scroll !important
			}
		</style>
		<#assign pageAnswer="http://${(orderDomain) !}:${(orderPort) !}/order-web/order/orderRecycleList.do"/>
		<div class="tab-content">
			<div class="tab-pane fade in active" id="brandList">
				<div class="col-md-12">
					<div class="search">
						<form id="find-page-orderby-form" action="${BasePath !}/order/orderRecycleList.do" method="post">
							<input id="find-page-index" type="hidden" name="pageIndex" value="1" />
							<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
							<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
							<div class="inquire-ul">
								<div class="form-tr">
									<div class="form-td">
										<div class="div-form">
											<input id="searchValue" name="searchValue" value="${(params.searchValue) !}" placeholder="打印单号/打印条形码/销售订单号/合伙人/合伙人电话" class="form-control txt_mid input-sm" style="height: 30px;width: 380px;" type="text">
										</div>
									</div>
									<div class="form-td">
										<div class="div-form">
											<select class="form-control input-sm txt_mid input-select" id="logisticsRecyleStatus" name="logisticsRecyleStatus">
	                                            <option value="">物流回收状态</option>        
												<option value="0" <#if (params.logisticsRecyleStatus) ??><#if params.logisticsRecyleStatus == "0">selected="selected"</#if></#if>>未回收</option>
												<option value="1" <#if (params.logisticsRecyleStatus) ??><#if params.logisticsRecyleStatus == "1">selected="selected"</#if></#if>>已回收</option>
										 </select>
										</div>
									</div>
									<div class="form-td">
										<div class="div-form">
											<select class="form-control input-sm txt_mid input-select" id="financeRecyleStatus" name="financeRecyleStatus">
	                                            <option value="">财务回收状态</option>        
												<option value="0" <#if (params.financeRecyleStatus) ??><#if params.financeRecyleStatus == "0">selected="selected"</#if></#if>>未回收</option>
												<option value="1" <#if (params.financeRecyleStatus) ??><#if params.financeRecyleStatus == "1">selected="selected"</#if></#if>>已回收</option>
										 </select>
										</div>
									</div>
									<div class="form-td">
										<label>出库时间:</label>
										<div class="div-form">
										    <input name="deliveryStartDate" id="deliveryStartDate" class="form-control txt_mid input-sm" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'deliveryEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(params.deliveryStartDate) !}"> -
											<input name="deliveryEndDate" id="deliveryEndDate" class="form-control txt_mid input-sm" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'deliveryStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(params.deliveryEndDate) !}">
										</div>
									</div>
									<div class="form-td">
										<label>回收确认时间:</label>
										<div class="div-form"><input name="recyleStartDate" id="recyleStartDate" class="form-control txt_mid input-sm" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'recyleEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(params.recyleStartDate) !}">											-
											<input name="recyleEndDate" id="recyleEndDate" class="form-control txt_mid input-sm" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'recyleStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(params.recyleEndDate) !}">
										</div>
									</div>
								</div>
							</div>
							<p>&nbsp;</p>
							<p>&nbsp;</p>
							<div class="btn-div3">
								<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
								<a id="table-cleanBtn" class="btn btn-primary btn-sm" onclick="onEmpty();"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a>
								<@permission name="oms:order:rexport">
									<a href="javascript:void(0);" onclick="exportData();" class="btn btn-primary btn-sm"><i class="fa fa-pencil"></i>&nbsp;&nbsp;导出</a>
								</@permission>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<@permission name="oms:order:lscan">
								<a href="javascript:void(0);" onclick="toConfirmScan('L');" class="btn btn-primary btn-sm">&nbsp;&nbsp;物流扫描确认&nbsp;&nbsp;</a>
								</@permission>
								<@permission name="oms:order:fscan">
								<a href="javascript:void(0);" onclick="toConfirmScan('F');" class="btn btn-primary btn-sm">&nbsp;&nbsp;财务扫描确认&nbsp;&nbsp;</a>
								</@permission>
							</div>
						</form>
					</div>
				</div>
				<p>&nbsp;</p>
				<div class="tab-content">
					<table class="table table-hover table-striped table-common">
						<thead>
							<tr>
								<th>序号</th>
								<th>打印单号</th>
								<th>打印单条形码</th>
								<th>发货仓库</th>
								<th>合伙人地址</th>
								<th>合伙人/合伙人电话</th>
								<th>物流回收状态</th>
								<th>财务回收状态</th>
								<th>销售订单号</th>
								<th>出库时间</th>
								<th>回收确认时间</th>
							</tr>
						</thead>
						<tbody>
						<#if orderRecycleList ??>
                             <#list orderRecycleList as item > 
							<tr>
								<td>${item_index+1}</td>
								<td>${(item.orderNoSigned) !}</td>
								<td>${(item.barCodeSigned) !}</td>
								<td>${(item.warehouseCalled) !}</td>
								<td>${(item.partnerAddress) !}</td>
								<td>${(item.partner) !}/${(item.partnerPhone) !}</td>
								<td>${(item.logisticsRecyleStatus) !}</td>
								<td>${(item.financeRecyleStatus) !}</td>
								<td>
									<a href="javascript:void(0);"  key="viewWms" orderNoSigned="${(item.orderNoSigned) !}"  class="viewlogistics logistics-hover" >${(item.saleNo) !}
										<div class="logisticspage" style="height:200px;width:150px;z-index:3;"  id="status${(item.orderNoSigned) !}"></div>
									</a> 
						        </td>
								<td><#if (item.deliveryDate) ??>${(item.deliveryDate)?string('yyyy-MM-dd') !}</#if></td>
								<td><#if (item.recyleDate) ??>${(item.recyleDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if></td>
							</tr>
							</#list>
                         </#if>
                         
						</tbody>
					</table>
				</div>
				<#include "../common/page_macro.ftl" encoding="utf-8"> 
                <@my_page pageObj/>  
			</div>
		</div>
		<#include "../common/tree.ftl" encoding="utf-8">
		<#include "../common/css.ftl" encoding="utf-8">
		<#include "../common/select.ftl" encoding="utf-8">
		<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css">
		<script type="text/javascript" src="${BasePath}/asset/js/orderRecycle/order_recycle_list.js?v=${ver !}"></script>
		
		<div style="display:none;"  ><iframe id="orderExport_iframe" ></iframe></div> 
	</body>

</html>