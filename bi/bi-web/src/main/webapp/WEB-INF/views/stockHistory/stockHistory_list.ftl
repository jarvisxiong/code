<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="list" />

<title>报表管理</title>

<style>
.getnumbercount{width: 20% !important;text-align: -webkit-center;text-align: center;border: 1px solid #ccc;padding-top: 5px;}
</style>
</head>
<body>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="brandList">
			<form id="find-page-orderby-form" action="${BasePath !}/stockHistory/list.do" method="post">
				<div class="col-md-12">
					<div class="search">
						<input type="hidden" id="groupByVendor" name="stockHistoryCustom.groupByVendor" value="checked" /> 
						<input type="hidden" id="groupByCategory" name="stockHistoryCustom.groupByCategory" value="${(stockHistoryCustom.groupByCategory) !}" />
						<input type="hidden" id="isCheckedBarcode" name="stockHistoryCustom.isCheckedBarcode" value="${(stockHistoryCustom.isCheckedBarcode) !}" />
						<input type="hidden" id="vendorCodeArrStr" name="vendorCodeArrStr" value="" />
						<input type="hidden" id="categoryIdArrStr" name="categoryIdArrStr" value="" />
						<input type="hidden" id="skuBarcodeArrStr" name="skuBarcodeArrStr" value="" />
						<input type="hidden" id="init" name="init" value="0" />
						<div class="inquire-ul">
							<div class="form-tr">
								<br />
								<div class="form-td chooseDate">
									<div class="btn-group" data-toggle="buttons">
										<label class="btn btn-default btn-sm"> 
											<input type="radio" name="options" id="todayChart" autocomplete="off" checked>今天
										</label> 
										<label class="btn btn-default btn-sm"> 
											<input type="radio" name="options" id="yesterdayChart" autocomplete="off">昨天
										</label> 
										<label class="btn btn-default btn-sm"> 
											<input type="radio" name="options" id="oneWeekChart" autocomplete="off">近一周
										</label> 
										<label class="btn btn-default btn-sm active"> 
											<input type="radio" name="options" id="monthChart" autocomplete="off">近30天
										</label> 
<!-- 										<label class="btn btn-default btn-sm">  -->
<!-- 											<input type="radio" name="options" id="sixMonthsChart" autocomplete="off">近半年 -->
<!-- 										</label> -->
									</div>
								</div>
								<div class="form-td">
									<div class="div-form">
										<input name="stockHistoryCustom.searchMinDate" id="searchMinDate"
											class="form-control txt_mid input-sm" readonly="readonly"
											onfocus="WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchMaxDate\')}'})"
											value="${(stockHistoryCustom.searchMinDate)!}">
									</div>
									-
									<div class="div-form">
										<input name="stockHistoryCustom.searchMaxDate" id="searchMaxDate"
											class="form-control txt_mid input-sm" readonly="readonly"
											onfocus="WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchMinDate\')}'})"
											value="${(stockHistoryCustom.searchMinDate)!}">
									</div>
								</div>
								<div class="form-tr">
									<div class="form-td">
										<label><input id="vendorInput" type="checkbox" checked="checked">供应商：</label> 
										<div class="div-form">
											<div class="f7" id="vendorPartner" onclick="vendorPopupFrom()">
												<input type="hidden" id="vendorCode" name="stockHistoryCustom.vendorCode" value=""/>
										    	<input class="form-control input-sm txt_mid" type="text" id="vendorName" name="vendorName" value="" readonly="readonly">
										    	<span class="selectDel">×</span>
										    	<span class="selectBtn">选</span>
											</div>
										</div>
									</div>
									<div class="form-td">
										<label><input id="categoryInput" type="checkbox">商品类目：</label>
									  	<div class="div-form">
									    	<div class="f7" onclick='showTreeAndisParent("请选择商品类别","/stockHistory/selectCategory.do","parentName", "categoryId","","parentId")' >
												<input type="hidden" id="categoryId" name="stockHistoryCustom.categoryIdThreeLevel" value="">
												<input type="hidden" id="categoryLevel" name="stockHistoryCustom.categoryLevel" value="">
										   		<input class="form-control input-sm txt_mid" type="text" id="parentName" name="parentName" value="" readonly="readonly">
										   		<span class="selectDel">×</span>
										        <span class="selectBtn">选</span>
									   		</div>
									   </div>
									</div>
									<div class="form-td">
										<label><input id="skuBarcodeInput" type="checkbox">sku条形码：</label>
										<div class="div-form">
											<input id="skuBarcode" name="stockHistoryCustom.skuBarcode" class="form-control txt_mid input-sm"
												type="text" placeholder="" value="">
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="btn-div3">
                             <button id="find-page-orderby-button"  class="btn btn-primary btn-sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
                             <a href="${BasePath !}/stockHistory/list.do" class="btn btn-primary btn-sm"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a>
							 <@permission name="bi:stockHistory:export">
							 	<a onclick="exportExcel()" class="btn btn-primary btn-sm" ><i class="fa fa-arrow-circle-up"></i>&nbsp;&nbsp;导出</a>
							 </@permission>  
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-bottom: 10px;">
					<select class="form-control input-sm txt_mid input-select" id="date" name="stockHistoryCustom.date">
						<option value="day">按天统计</option>
						<option value="week">按周统计</option>
						<option value="month">按月统计</option>
						<option value="year">按年统计</option>
					</select>
				</div>
			</form>
<!-- 			暂时留着 -->
			<div class="col-md-12">
				<div id="myChart5" style="height: 400px;"></div>
			</div>
			<div class="tab-content">
				<!-- DataTable HTML -->
				<table id="ff_DataTable" class="table table-hover table-striped bor2 table-common">
					<thead>
						<!-- <tr>
			                <th>序号</th>
			                <th>sku条形码</th>
			                <th>商品名称</th>
			                <th>SKU编码</th>
			                <th>一级类目</th>
			                <th>二级类目</th>
			                <th>三级类目</th>
			                <th>供应商</th>
			                <th>库存数</th>
			                <th>日期</th>
		             	</tr> -->
						<tr>
							<th width="15px"><input id="checkboxAll" type="checkbox" ></th>
							<th>序号</th>
							<th>供应商</th>
							<th>库存数</th>
							<th>日期</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				
				<!-- END: DataTable HTML -->
			</div>
			
		</div>
	</div>
<#include "../common/tree.ftl" encoding="utf-8">
<link rel="stylesheet"	href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
<script src="${BasePath !}/asset/js/bi/stockHistory/stockHistory_list.js?v=${ver !}"></script>
</body>
</html>
