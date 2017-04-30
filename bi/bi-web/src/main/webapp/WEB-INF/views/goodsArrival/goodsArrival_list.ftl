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
			<form id="find-page-orderby-form" action="${BasePath !}/goodsArrival/list.do" method="post">
				<input type="hidden" id="isCheckedVendor" name="goodsArrivalCustom.isCheckedVendor" value="checked" /> 
				<input type="hidden" id="isCheckedBarcode" name="goodsArrivalCustom.isCheckedBarcode" value="" />
				<div class="col-md-12">
					<div class="search">
						<div class="inquire-ul">
							<div class="form-tr">
								<br />
								<div class="form-td chooseDate">
									<div class="btn-group" data-toggle="buttons">
										<label class="btn btn-default btn-sm"> 
											<input type="radio" name="options" id="oneWeekChart" autocomplete="off">近一周
										</label> 
										<label class="btn btn-default btn-sm active"> 
											<input type="radio" name="options" id="monthChart" autocomplete="off">近30天
										</label> 
									</div>
								</div>
								<div class="form-td">
									<div class="div-form">
										<input name="goodsArrivalCustom.searchMinDate" id="searchMinDate"
											class="form-control txt_mid input-sm" readonly="readonly"
											onfocus="WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchMaxDate\')}'})"
											value="${(goodsArrivalCustom.searchMinDate)!}">
									</div>
									-
									<div class="div-form">
										<input name="goodsArrivalCustom.searchMaxDate" id="searchMaxDate"
											class="form-control txt_mid input-sm" readonly="readonly"
											onfocus="WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchMinDate\')}'})"
											value="${(goodsArrivalCustom.searchMinDate)!}">
									</div>
								</div>
								<div class="form-tr">
									<div class="form-td">
										<label><input id="vendorInput" type="checkbox" checked="checked">供应商：</label> 
										<div class="div-form">
											<div class="f7" id="vendorPartner" onclick="vendorPopupFrom()">
												<input type="hidden" id="vendorId" name="goodsArrivalCustom.supplierId" value=""/>
										    	<input class="form-control input-sm txt_mid" type="text" id="vendorName" name="vendorName" value="" readonly="readonly">
										    	<span class="selectDel">×</span>
										    	<span class="selectBtn">选</span>
											</div>
										</div>
									</div>
									<div class="form-td">
										<label><input id="skuBarcodeInput" type="checkbox">sku条形码：</label>
										<div class="div-form">
											<input id="skuBarcode" name="goodsArrivalCustom.skuBarcode" class="form-control txt_mid input-sm"
												type="text" placeholder="" value="">
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="btn-div3">
                             <button id="find-page-orderby-button"  class="btn btn-primary btn-sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
                             <a href="${BasePath !}/goodsArrival/list.do" class="btn btn-primary btn-sm"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a>
<!--                              <button class="btn btn-primary btn-sm btn-clear-keyword" data-target="demo_list"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;重置</button> -->
							 <@permission name="bi:goodsArrival:export">
							 	<a onclick="exportExcel()" class="btn btn-primary btn-sm" ><i class="fa fa-arrow-circle-up"></i>&nbsp;&nbsp;导出</a>
							 </@permission>  
						</div>
					</div>
				</div>
			</form>
			<div id="demo_list" class="ff_DataTable"></div>
			<!-- <div class="tab-content">
				DataTable HTML
				<table id="ff_DataTable" class="table table-hover table-striped bor2 table-common">
					<thead>
						<tr>
			                <th>序号</th>
			                <th>供应商</th>
			                <th>采购数量</th>
			                <th>到货数量</th>
			                <th>质检良品数</th>
			                <th>退货数</th>
			                <th>到货率</th>
			                <th>良品率</th>
			                <th>退货率</th>
		             	</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				END: DataTable HTML
			</div> -->
		</div>
	</div>
<#include "../common/tree.ftl" encoding="utf-8">
<link rel="stylesheet"	href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
<script>
	$(document).ready(function() {		
		isChecked();
		requirejs(['ff/init_datatable'],function(initDataTable) {
			var dataTable = new initDataTable({
				div_id: 'demo_list',
				url: rootPath + "/goodsArrival/listTable.do",
				columns:[
					{ data: "supplierName", label: '供应商', class:'text-nowrap'},
					{ data: "totalPurchaseQuantity", label: '采购数量'},
					{ data: "totalReceivingQuantity", label: '到货数量' },
					{ data: "totalcStorageQuantity", label: '质检良品数', class:'text-nowrap'},
					{ data: "totalcRejectedQuantity", label: '退货数', class:'text-nowrap'},
					{ data: "arrivalRate", label: '到货率', class:'text-nowrap'
						/* , 
						render: function ( data, type, item ) {
							//总的到货数量
							var totalReceivingQuantity = item.totalReceivingQuantity;
							 //总的采购数量
							var totalPurchaseQuantity = item.totalPurchaseQuantity;
							return totalPurchaseQuantity <= 0 ? "0%" : (totalReceivingQuantity / totalPurchaseQuantity * 10000 / 100.00 + "%"); 
						} */
					},
					{ data: "goodProductsRate", label: '良品率', class:'text-nowrap'
						/* , 
							render: function ( data, type, item ) {
								//总的质检良品数
								var totalcStorageQuantity = item.totalcStorageQuantity;
								 //总的质检数
								var totalReceivingQuantity = item.totalReceivingQuantity;
								return totalReceivingQuantity <= 0 ? "0%" : (totalcStorageQuantity / totalReceivingQuantity * 10000 / 100.00 + "%"); 
							} */
					},
					{ data: "refundRate", label: '退货率', class:'text-nowrap'
						/* , 
							render: function ( data, type, item ) {
							//总的退货数
							var totalcRejectedQuantity = item.totalcRejectedQuantity;
							 //总的质检数量
							var totalReceivingQuantity = item.totalReceivingQuantity;
							return totalReceivingQuantity <= 0 ? "0%" : (totalcRejectedQuantity / totalReceivingQuantity * 10000 / 100.00 + "%"); 
						} */
					}
				],
				show_action: false,
				show_checkbox: false
			})
		});
		
		changeDate($('.chooseDate label.active input[type="radio"]')[0].id);
		
		$('.chooseDate label').unbind('click').on('click', function(e) {
			var $chooseDate = $(this).find('input[type="radio"]');
			var oldchooseDateActive=$('.chooseDate label.active input[type="radio"]')[0].id;
			if(oldchooseDateActive===$chooseDate[0].id) return;
			changeDate($chooseDate[0].id);
		});
		
		function changeDate(chooseDateId) {
			var prvDate = new Date();
			if(chooseDateId==='yesterdayChart'){
				prvDate = new Date(prvDate.valueOf() - 1*24*60*60*1000);
			}else if(chooseDateId==='oneWeekChart'){
				prvDate = new Date(prvDate.valueOf() - 7*24*60*60*1000);
			}else if(chooseDateId==='monthChart'){
				prvDate = new Date(prvDate.valueOf() - 30*24*60*60*1000);
			}else if(chooseDateId==='sixMonthsChart'){
				prvDate = new Date(prvDate.valueOf() - 182*24*60*60*1000);
			}
			document.getElementById('searchMinDate').value=new Date(prvDate).format('yyyy-MM-dd'); 
			document.getElementById('searchMaxDate').value=new Date().format('yyyy-MM-dd');
		};
		
		$(".selectDel").on("click", function (event) {
			event.stopImmediatePropagation();//阻止剩余的事件处理函数执行并且防止事件冒泡到DOM树上
			$(this).siblings("input").each(function () {
				$(this).val("");
		    });
		});
		
		
		$('#find-page-orderby-button').unbind('click').on('click', function(e) {
			if ($("input[type='checkbox']:checked").length == 0) {
				$.frontEngineDialog.executeDialogOK('提示信息', "请勾选一个复选框", "150");
				return;
			} 
			FFZX.DT.dt_demo_list.destroy();
			$('#demo_list').empty();
			getDataTable();
		});
		
	});
	
	function getDataTable() {
		var v = $("#vendorInput").get(0).checked;
		var s = $("#skuBarcodeInput").get(0).checked;
		if ((v && s) || s) {
			requirejs(['ff/init_datatable'],function(initDataTable) {
				var dataTable = new initDataTable({
					div_id: 'demo_list',
					url: rootPath + "/goodsArrival/listTable.do",
					columns:[
						{ data: "supplierName", label: '供应商', class:'text-nowrap'},
						{ data: "skuBarcode", label: 'SKU条形码'},
						{ data: "commodityName", label: '商品名称'},
						{ data: "skuCode", label: ' SKU编码 '},
						{ data: "totalPurchaseQuantity", label: '采购数量'},
						{ data: "totalReceivingQuantity", label: '到货数量' },
						{ data: "totalcStorageQuantity", label: '质检良品数', class:'text-nowrap'},
						{ data: "totalcRejectedQuantity", label: '退货数', class:'text-nowrap'},
						{ data: "arrivalRate", label: '到货率', class:'text-nowrap'
							/* , 
							render: function ( data, type, item ) {
								//总的到货数量
								var receivingQuantity = item.receivingQuantity;
								 //总的采购数量
								var purchaseQuantity = item.purchaseQuantity;
								return purchaseQuantity <= 0 ? "0%" : (receivingQuantity / purchaseQuantity * 10000 / 100.00 + "%"); 
							} */
						},
						{ data: "goodProductsRate", label: '良品率', class:'text-nowrap'
							/* , 
								render: function ( data, type, item ) {
									//总的质检良品数
									var cStorageQuantity = item.cStorageQuantity;
									 //总的质检数
									var receivingQuantity = item.receivingQuantity;
									return receivingQuantity <= 0 ? "0%" : (cStorageQuantity / receivingQuantity * 10000 / 100.00 + "%"); 
								} */
						},
						{ data: "refundRate", label: '退货率', class:'text-nowrap'
							/* , 
								render: function ( data, type, item ) {
								//总的退货数
								var cRejectedQuantity = item.cRejectedQuantity;
								 //总的质检数量
								var receivingQuantity = item.receivingQuantity;
								return receivingQuantity <= 0 ? "0%" : (cRejectedQuantity / receivingQuantity * 10000 / 100.00 + "%"); 
							} */
						}
					],
					show_action: false,
					show_checkbox: false
				});
			});
		} else {
			requirejs(['ff/init_datatable'],function(initDataTable) {
				var dataTable = new initDataTable({
					div_id: 'demo_list',
					url: rootPath + "/goodsArrival/listTable.do",
					columns:[
						{ data: "supplierName", label: '供应商', class:'text-nowrap'},
						{ data: "totalPurchaseQuantity", label: '采购数量'},
						{ data: "totalReceivingQuantity", label: '到货数量' },
						{ data: "totalcStorageQuantity", label: '质检良品数', class:'text-nowrap'},
						{ data: "totalcRejectedQuantity", label: '退货数', class:'text-nowrap'},
						{ data: "arrivalRate", label: '到货率', class:'text-nowrap'
							/* , 
							render: function ( data, type, item ) {
								//总的到货数量
								var totalReceivingQuantity = item.totalReceivingQuantity;
								 //总的采购数量
								var totalPurchaseQuantity = item.totalPurchaseQuantity;
								return totalPurchaseQuantity <= 0 ? "0%" : (totalReceivingQuantity / totalPurchaseQuantity * 10000 / 100.00 + "%"); 
							} */
						},
						{ data: "goodProductsRate", label: '良品率', class:'text-nowrap'
							/* , 
								render: function ( data, type, item ) {
									//总的质检良品数
									var totalcStorageQuantity = item.totalcStorageQuantity;
									 //总的质检数
									var totalReceivingQuantity = item.totalReceivingQuantity;
									return totalReceivingQuantity <= 0 ? "0%" : (totalcStorageQuantity / totalReceivingQuantity * 10000 / 100.00 + "%"); 
								} */
						},
						{ data: "refundRate", label: '退货率', class:'text-nowrap'
							/* , 
								render: function ( data, type, item ) {
								//总的退货数
								var totalcRejectedQuantity = item.totalcRejectedQuantity;
								 //总的质检数量
								var totalReceivingQuantity = item.totalReceivingQuantity;
								return totalReceivingQuantity <= 0 ? "0%" : (totalcRejectedQuantity / totalReceivingQuantity * 10000 / 100.00 + "%"); 
							} */
						}
					],
					show_action: false,
					show_checkbox: false
				});
			});
		}  
	}
	
	/*
	 * 供应商选择框
	 */
	function vendorPopupFrom() {
	    $.frontEngineDialog.executeIframeDialog('vendor_select_commodity', '选择供应商',  rootPath + '/goodsArrival/selectVendor.do', '1000', '600');
	}
	
	/*
	 * 是否选中供应商， 商品分类, sku条形码复选框
	 */
	function isChecked() {
//		$("#groupByVendor").attr("value", "checked")
		$("input[type='checkbox']").unbind('click').on('click', function(e) {
			if ($("#vendorInput").get(0).checked) {
				$("#isCheckedVendor").attr("value", "checked")
			} else {
				$("#isCheckedVendor").attr("value", "")
			}
			if ($("#skuBarcodeInput").get(0).checked) {
				$("#isCheckedBarcode").attr("value", "checked")
			} else {
				$("#isCheckedBarcode").attr("value", "")
			}
		});
	}
	/*
	 * 导出
	 */
	function exportExcel() {
		document.forms[0].action = rootPath + "/goodsArrival/exportExcel.do";
		
		document.forms[0].submit();
		
		document.forms[0].action = rootPath + "/goodsArrival/list.do";
	}
</script>
</body>
</html>
