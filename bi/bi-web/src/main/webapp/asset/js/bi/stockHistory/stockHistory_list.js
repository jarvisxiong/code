$(document).ready(function() {
	isChecked();
	
	// Date - format
	if (!Date.prototype.format) {
	    Date.prototype.format = function(fmt) {
	        var o = {
	            'M+': this.getMonth() + 1,
	            'd+': this.getDate(),
	            'h+': this.getHours(),
	            'm+': this.getMinutes(),
	            's+': this.getSeconds(),
	            'q+': Math.floor((this.getMonth() + 3) / 3),
	            'S': this.getMilliseconds()
	        }
	
	        if (/(y+)/.test(fmt))
	            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
	
	        for (var k in o)
	            if (new RegExp('(' + k + ')').test(fmt))
	
	                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
	
	        return fmt;
	    }
	}
	changeData($('.chooseDate label.active input[type="radio"]')[0].id);
	
	$('.chooseDate label').unbind('click').on('click', function(e) {
		var $chooseDate = $(this).find('input[type="radio"]');
		var oldchooseDateActive=$('.chooseDate label.active input[type="radio"]')[0].id;
		if(oldchooseDateActive===$chooseDate[0].id) return;
		changeData($chooseDate[0].id);
	});
	

	
		//渲染数据表格
	requirejs(['ff/datatable','echarts'], function() {
		var table = $('#ff_DataTable').DataTable($.extend({}, OPT_DATATABLE, {
			"ajax": rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize(),
			"columns": [
			    { data: "clm_checkbox", 
					visible: ('show_checkbox' in obj) ? obj.show_checkbox : true,
							class: 'clm_checkbox', 
							render: function ( data, type, item, meta ) {		
								var rowNum = meta.row + 1;
								return '<input type="checkbox" id=tr_'+ rowNum +' />';
							}
			    },
				{ "render": function ( data, type, row, meta ) {
		      		return meta.row + 1;
		    	}},
				{ "data": "vendorName" },
				{ "data": "totleStockNum" },
				{ "data": "transferTime" }
			],
			"footerCallback": function ( row, data, start, end, display ) {
				selectAll(data);
			},
			"createdRow": function ( row, data, index ) {
				clickRow(row, data);
	        }
		}));
		
		var table2 = $('#ff_DataTable').DataTable();
		 
		$('#ff_DataTable').on( 'page.dt', function () {
		    var info = table2.page.info();
		    console.log( 'Showing page: '+info.page+' of '+info.pages );
		} );
		
	/*	$('#ff_DataTable')
		.on( 'init.dt', function () {
			console.log( 'Table initialisation complete: '+new Date().getTime() );
		} )
		.dataTable();*/
		
//		var myChart5 = echarts.init(document.getElementById('myChart5'));
	 
		
	 
	 
	 
	changeChart();
	
	$('.changeChart label').unbind('click').on('click', function(e) {
		var $changeChart = $(this).find('input[type="radio"]');
		var oldchangeChartDateActive=$('.changeChart label.active input[type="radio"]')[0].id;
		if(oldchangeChartDateActive===$changeChart[0].id) return;
		changeChart($changeChart[0].id);
	});
	
		$('#find-page-orderby-button').unbind('click').on('click', function(e) {
			var vendorInputCheckbox = $("#vendorInput").get(0).checked;
			var categoryInputCheckbox = $("#categoryInput").get(0).checked;
			var vendorCode = $("#vendorCode").val();
			var categoryId = $("#categoryId").val();
			if (vendorInputCheckbox && categoryInputCheckbox) {
				if (vendorCode == '' && categoryId == '') {
					$.frontEngineDialog.executeDialogOK('提示信息', "请选择一个供应商或者商品类目", "200");
					return;
				}
			}
			if ($("input[type='checkbox']:checked").length == 0) {
				$.frontEngineDialog.executeDialogOK('提示信息', "请勾选一个复选框", "150");
				return;
			} 
			
			$('#ff_DataTable').DataTable().destroy();
//			table.destroy();
			$("#vendorCodeArrStr").val("");
			$("#categoryIdArrStr").val("");
			$("#skuBarcodeArrStr").val("");
			/*var selectednum=document.getElementById('date')
			var selectednumindex = selectednum.selectedIndex; // 选中索引
			var selectednumtext = selectednum.options[selectednumindex].text; // 选中文本
			var selectednumvalue = selectednum.options[selectednumindex].value; // 选中值
		 
			if (selectednumvalue === 'week') {
		    
			} else if (selectednumvalue === 'month') {
			 
			} else if (selectednumvalue === 'year') {
			 
			}*/
			loadtr();
			//table.ajax.url(rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize()).load();
			getDataTable();
			changeChart();
		});
	});
	
	function selectAll(data, num) {
		$('.table tr th input[type="checkbox"]').unbind('click').on('click', function(e) {
			var evt = arguments[0] || window.event;
			var chkbox = evt.srcElement || evt.target;
			var checkboxes = $(".table tr td input[type='checkbox']");
			if (chkbox.checked) {
				checkboxes.prop('checked', true);
			} else {
				checkboxes.prop('checked', false);
			}
			if (num == 3) {
				var skuBarcodeArrStr = $("#skuBarcodeArrStr").val();
				if ($("#checkboxAll").get(0).checked) {
					$.each(data, function(n, value) {
						var skuBarcode = value.skuBarcode;
						skuBarcodeArrStr = skuBarcodeArrStr.concat(skuBarcode).concat(',');
					})
					$("#skuBarcodeArrStr").val(skuBarcodeArrStr);
					$("#init").val("1");
				} else {
					$("#skuBarcodeArrStr").val('');
					$("#init").val("0");
				}
			} else if (num == 2) {
				var categoryIdArrStr = $("#categoryIdArrStr").val();
				if ($("#checkboxAll").get(0).checked) {
					$.each(data, function(n, value) {
						var categoryId;
						if ($("#categoryLevel").val() == 0) {
							categoryId = value.categoryIdOneLevel;
						} else if ($("#categoryLevel").val() == 1) {
							categoryId = value.categoryIdTwoLevel;
						} else {
							categoryId = value.categoryIdThreeLevel;
						}
						categoryIdArrStr = categoryIdArrStr.concat(categoryId).concat(',');
					})
					$("#categoryIdArrStr").val(categoryIdArrStr);
					$("#init").val("1");
				} else {
					$("#skuBarcodeArrStr").val('');
					$("#init").val("0");
				}
			} else {
				var vendorCodeArrStr = $("#vendorCodeArrStr").val();
				if ($("#checkboxAll").get(0).checked) {
					$.each(data, function(n, value) {
						var vendorCode = value.vendorCode;
						vendorCodeArrStr = vendorCodeArrStr.concat(vendorCode).concat(',');
					})
					$("#vendorCodeArrStr").val(vendorCodeArrStr);
					$("#init").val("1");
				} else {
					$("#vendorCodeArrStr").val('');
					$("#init").val("0");
				}
			}
			changeChart();
		});
	}
	
	/*
	 * 数据行的复选框被选中，触发图表改变
	 */
	function clickRow(row, data, num) {
		$(row.cells[0].childNodes[0]).click(function() {
			if (num == 3) {
				var skuBarcodeArrStr = $("#skuBarcodeArrStr").val();
				var skuBarcode = data.skuBarcode;
				if (this.checked) {
//					debugger;
					if (skuBarcodeArrStr != null && skuBarcodeArrStr != '') {
						skuBarcodeArrStr = skuBarcodeArrStr.concat(skuBarcode).concat(',');
					} else {
						skuBarcodeArrStr = skuBarcode.concat(',');
					}
					$("#init").val("1");
				} else {
					if (skuBarcodeArrStr != null && skuBarcodeArrStr != '') {
						skuBarcodeArrStr = skuBarcodeArrStr.replace(skuBarcode.concat(','), '')
					} 
//					$("#init").val("0");
				}
				$("#skuBarcodeArrStr").val(skuBarcodeArrStr);
			} else if (num == 2) {
				//没有选择类目时， 点击行复选框拼接类目ID
				if ($("#categoryLevel").val() == '') {
					var categoryIdArrStr = $("#categoryIdArrStr").val();
					var categoryId = data.categoryIdThreeLevel;
					if (this.checked) {
						if (categoryIdArrStr != null && categoryIdArrStr != '') {
							categoryIdArrStr = categoryIdArrStr.concat(categoryId).concat(',');
						} else {
							categoryIdArrStr = categoryId.concat(',');
						}
						$("#init").val("1");
					} else {
						if (categoryIdArrStr != null && categoryIdArrStr != '') {
							categoryIdArrStr = categoryIdArrStr.replace(categoryId.concat(','), '')
						} 
//						$("#init").val("0");
					}
					$("#categoryIdArrStr").val(categoryIdArrStr);
				}
			} else {
				var vendorCodeArrStr = $("#vendorCodeArrStr").val();
				var vendorCode = data.vendorCode;
				if (this.checked) {
//					debugger;
					if (vendorCodeArrStr != null && vendorCodeArrStr != '') {
						vendorCodeArrStr = vendorCodeArrStr.concat(vendorCode).concat(',');
					} else {
						vendorCodeArrStr = vendorCode.concat(',');
					}
					$("#init").val("1");
				} else {
					if (vendorCodeArrStr != null && vendorCodeArrStr != '') {
						vendorCodeArrStr = vendorCodeArrStr.replace(vendorCode.concat(','), '')
					} 
//					$("#init").val("0");
				}
				$("#vendorCodeArrStr").val(vendorCodeArrStr);
			}
			
			changeChart();
		})
	}
	
	
	function changeChart() {
		var myChart5 = echarts.init(document.getElementById('myChart5'));
		myChart5.clear();
		var myChart5Op={};
		var chartData = getChartJsonData();
		var seriesData = [];
		$.each(chartData.stocknums, function (n, value) {
			seriesData[n] = {
	            name: chartData.vendorNames[n],
	            type: 'line',
	            data: value
			};
        });
	  	myChart5Op = {
  			//标题组件，包含主标题和副标题。
    		title: {
    	        left: 'center'
    	    },
    	    //提示框组件。
    	    tooltip: {
    	        trigger: 'axis'
    	    },
    	    xAxis: {
    	    	type : 'category',
    	        name: '日期',
    	        data: chartData.days
    	    },
    	   
    	    /*直角坐标系 grid 中的 y 轴，一般情况下单个 grid 组件最多只能放左右两个 y 轴，
    	            多于两个 y 轴需要通过配置 offset 属性防止同个位置多个 Y 轴的重叠。
    	     */
    	    yAxis: {
    	        type: 'value',
//    	        interval: 1000,
//    	        min: '0',
//    	        max: '10000',
    	        name: '库存数量'
    	    },
    	    grid: {
    	        left: '1%',
    	        right: '3%',
    	        bottom: '3%',
    	        containLabel: true
    	    },
    	    series: seriesData
    	 };
	  	myChart5.setOption(myChart5Op);
	}	
		
	function changeData(chooseDateId){
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
	
	function queryByAjax() {
	    //table.ajax.url(rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize()).load();
		//changeChart();
	};
	
	
	function getChartJsonData() {
		var _url = rootPath+"/stockHistory/getStockNum.do";
		var rsData = '';
		FFZX.mask('show');
		$.ajax({
            type: 'post',
            async: false,
            data:  $("#find-page-orderby-form").serialize(),
            url: _url,
            dataType: 'json',
            success: function(d){
            	rsData = d;
            },
			complete: function(){
				FFZX.mask('hide');
			},
            error: function(e){               
				// 如果超时退出
				if (e.readyState == 0 && e.responseText == '' && e.status == 0 && e.statusText == 'error') {
					$.frontEngineDialog.executeDialogContentTime('请重新登录！', 5000);
				}
            }
	    });
		return rsData;
	}
	
	$(".selectDel").on("click", function (event) {
		event.stopImmediatePropagation();//阻止剩余的事件处理函数执行并且防止事件冒泡到DOM树上
		$(this).siblings("input").each(function () {
			$(this).val("");
	    });
	});
	
	/*
	 * 选中的日期
	 */
	function selectedDate() {
		var _selectednumvalue = "";
		var selectednum = document.getElementById('date')
		var selectednumindex = selectednum.selectedIndex; // 选中索引
		// var selectednumtext = selectednum.options[selectednumindex].text; // 选中文本
		var selectednumvalue = selectednum.options[selectednumindex].value; // 选中值
		if (selectednumvalue === 'day') {
			_selectednumvalue = "transferTime";
		} else if (selectednumvalue === 'week') {
			_selectednumvalue = "week";
		} else if (selectednumvalue === 'month') {
			_selectednumvalue = "month";
		} else if (selectednumvalue === 'year') {
			_selectednumvalue = "year";
		}
		return _selectednumvalue;
	}
	
	function getDataTable() {
		var vendorInputCheckbox = $("#vendorInput").get(0).checked;
		var categoryInputCheckbox = $("#categoryInput").get(0).checked;
		var skuBarcodeInputCheckbox = $("#skuBarcodeInput").get(0).checked;
		var categoryLevel = $("#categoryLevel").val();
		var _selectednumvalue = selectedDate();
		if (vendorInputCheckbox && categoryInputCheckbox) {
			$('#ff_DataTable').DataTable($.extend({}, OPT_DATATABLE, {
				"ajax": rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize(),
				"columns": [
					{ data: "clm_checkbox", 
						//visible: ('show_checkbox' in obj) ? obj.show_checkbox : true,
								class: 'clm_checkbox', 
								render: function ( data, type, item ) {			
									return '<input type="checkbox"  />';
								}
					},
					{ "render": function ( data, type, row, meta ) {
			      		return meta.row + 1;
			    	}},
			    	{ "data": "vendorName" },
			    	{ "data": "categoryNameOneLevel" },
			    	{ "data": "categoryNameTwoLevel" },
			    	{ "data": "categoryNameThreeLevel" },
			    	{ "data": "totleStockNum" },
			    	{ "data": _selectednumvalue }
				],
				"footerCallback": function ( row, data, start, end, display ) {
					selectAll(data);
				},
				"createdRow": function ( row, data, index ) {
					clickRow(row, data);
		        }
			}));
		} else if (skuBarcodeInputCheckbox) {
			$('#ff_DataTable').DataTable($.extend({}, OPT_DATATABLE, {
				"ajax": rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize(),
				"columns": [
					{ data: "clm_checkbox", 
						//visible: ('show_checkbox' in obj) ? obj.show_checkbox : true,
								class: 'clm_checkbox', 
								render: function ( data, type, item ) {			
									return '<input type="checkbox"  />';
								}
					},
					{ "render": function ( data, type, row, meta ) {
			      		return meta.row + 1;
			    	}},
			    	{ "data": "skuBarcode" },
			    	{ "data": "commodityName" },
			    	{ "data": "skuCode" },
			    	{ "data": "categoryNameOneLevel" },
					{ "data": "categoryNameTwoLevel" },
					{ "data": "categoryNameThreeLevel" },
					{ "data": "vendorName" },
					{ "data": "totleStockNum" },
					{ "data": _selectednumvalue }
				],
				"footerCallback": function ( row, data, start, end, display ) {
					selectAll(data, 3);
				},
				"createdRow": function ( row, data, index ) {
					clickRow(row, data, 3);
		        }
			}));
		} else if (vendorInputCheckbox) {
			$('#ff_DataTable').DataTable($.extend({}, OPT_DATATABLE, {
				"ajax": rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize(),
				"columns": [
					{ data: "clm_checkbox", 
						//visible: ('show_checkbox' in obj) ? obj.show_checkbox : true,
								class: 'clm_checkbox', 
								render: function ( data, type, item ) {			
									return '<input type="checkbox"  />';
								}
					},
					{ "render": function ( data, type, row, meta ) {
			      		return meta.row + 1;
			    	}},
					{ "data": "vendorName" },
					{ "data": "totleStockNum" },
					{ "data": _selectednumvalue }
				],
				"footerCallback": function ( row, data, start, end, display ) {
					selectAll(data);
				},
				"createdRow": function ( row, data, index ) {
					clickRow(row, data);
		        }
			}));
		} else if (categoryInputCheckbox) {
			if (categoryLevel == "0") {
				$('#ff_DataTable').DataTable($.extend({}, OPT_DATATABLE, {
					"ajax": rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize(),
					"columns": [
						{ data: "clm_checkbox", 
									class: 'clm_checkbox', 
									render: function ( data, type, item ) {			
										return '<input type="checkbox"  />';
									}
						},
						{ "render": function ( data, type, row, meta ) {
				      		return meta.row + 1;
				    	}},
						{ "data": "categoryNameOneLevel" },
						{ "data": "totleStockNum" },
						{ "data": _selectednumvalue }
					],
					"footerCallback": function ( row, data, start, end, display ) {
						selectAll(data, 2);
					},
					"createdRow": function ( row, data, index ) {
						clickRow(row, data, 2);
			        }
				}));
			} else if (categoryLevel == "1") {
				$('#ff_DataTable').DataTable($.extend({}, OPT_DATATABLE, {
					"ajax": rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize(),
					"columns": [
						{ data: "clm_checkbox", 
									class: 'clm_checkbox', 
									render: function ( data, type, item ) {			
										return '<input type="checkbox"  />';
									}
						},
						{ "render": function ( data, type, row, meta ) {
				      		return meta.row + 1;
				    	}},
						{ "data": "categoryNameTwoLevel" },
						{ "data": "totleStockNum" },
						{ "data": _selectednumvalue }
					],
					"footerCallback": function ( row, data, start, end, display ) {
						selectAll(data, 2);
					},
					"createdRow": function ( row, data, index ) {
						clickRow(row, data, 2);
			        }
				}));
			} else if (categoryLevel == "2") {
				$('#ff_DataTable').DataTable($.extend({}, OPT_DATATABLE, {
					"ajax": rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize(),
					"columns": [
						{ data: "clm_checkbox", 
							//visible: ('show_checkbox' in obj) ? obj.show_checkbox : true,
									class: 'clm_checkbox', 
									render: function ( data, type, item ) {			
										return '<input type="checkbox"  />';
									}
						},
						{ "render": function ( data, type, row, meta ) {
				      		return meta.row + 1;
				    	}},
						{ "data": "categoryNameOneLevel" },
						{ "data": "categoryNameTwoLevel" },
						{ "data": "categoryNameThreeLevel" },
						{ "data": "totleStockNum" },
						{ "data": _selectednumvalue }
					],
					"footerCallback": function ( row, data, start, end, display ) {
						selectAll(data, 2);
					},
					"createdRow": function ( row, data, index ) {
						clickRow(row, data, 2);
			        }
				}));
			} else {
				$('#ff_DataTable').DataTable($.extend({}, OPT_DATATABLE, {
					"ajax": rootPath+"/stockHistory/listTable.do?" + $("#find-page-orderby-form").serialize(),
					"columns": [
						{ data: "clm_checkbox", 
							//visible: ('show_checkbox' in obj) ? obj.show_checkbox : true,
									class: 'clm_checkbox', 
									render: function ( data, type, item ) {			
										return '<input type="checkbox"  />';
									}
						},
						{ "render": function ( data, type, row, meta ) {
				      		return meta.row + 1;
				    	}},
						{ "data": "categoryNameOneLevel" },
						{ "data": "categoryNameTwoLevel" },
						{ "data": "categoryNameThreeLevel" },
						{ "data": "totleStockNum" },
						{ "data": _selectednumvalue }
					],
					"footerCallback": function ( row, data, start, end, display ) {
						selectAll(data, 2);
					},
					"createdRow": function ( row, data, index ) {
						clickRow(row, data, 2);
			        }
				}));
			}
		}
	}
});

/*
 * 是否选中供应商， 商品分类, sku条形码复选框
 */
function isChecked() {
//	$("#groupByVendor").attr("value", "checked")
	$("input[type='checkbox']").unbind('click').on('click', function(e) {
		if ($("#vendorInput").get(0).checked) {
			$("#groupByVendor").attr("value", "checked")
		} else {
			$("#groupByVendor").attr("value", "")
		}
		if ($("#categoryInput").get(0).checked) {
			$("#groupByCategory").attr("value", "checked")
		} else {
			$("#groupByCategory").attr("value", "")
		}
		if ($("#skuBarcodeInput").get(0).checked) {
			$("#isCheckedBarcode").attr("value", "checked")
		} else {
			$("#isCheckedBarcode").attr("value", "")
		}
	});
}


/*
 * 供应商选择框
 */
function vendorPopupFrom() {
    $.frontEngineDialog.executeIframeDialog('vendor_select_commodity', '选择供应商',  rootPath + '/stockHistory/selectVendor.do', '1000', '600');
}


/**
 * 弹出树形选择并且验证是否有子节点
 * title:弹出的标题
 * url：树形地址
 * id：操作元素弹出树形的id
 * id1：选择树形id保存在id1元素上
 * call：单击确定调用的函数
 * pId:父节点id，默认pId
 */
function showTreeAndisParent(title,url,id,id1,call,pId,errorData) {
	pId = (pId==null || pId=="" || typeof(pId)=="undefined")? "pId" : pId;
	var tree_setting = {
		data : {// 数据
			simpleData : {
				enable : true,// true / false 分别表示 使用 / 不使用 简单数据模式
								// 默认false，一般使用简单数据方式
				idKey : "id",// 节点数据中保存唯一标识的属性名称 默认值："id"
				pIdKey : pId// 点数据中保存其父节点唯一标识的属性名称 默认值："pId"
			},
			key : {
				url : ""
			}
		},
		view:{
			selectedMulti: false
		}
	};
	$("#"+id).blur();
	$.frontEngineAjax.executeAjaxPost(
					rootPath + url,
					"",
					function(ret) {
						var content = '<div class="left"><div><input type="text" class="form-control input-sm txt_mid" style="height:31px;" id="shwo_tree_search_text"><button id="show_tree_search_btn" class="btn btn-primary btn-sm" style="margin-left:5px;" onclick="showTreeSearchClick()"><i class="fa fa-search"></i></button><div><ul id="show_tree" class="ztree"></ul></div>';
						$.frontEngineDialog.executeDialog(
								'showTree',
								title,
								content,
								"400px",
								"450px",
								function(){
									var treeObj = $.fn.zTree.getZTreeObj("show_tree");
									var nodes = treeObj.getSelectedNodes();
 									if (nodes.length > 0) {
 										var level = nodes[0].level;
 										if(level == 0) {
 											$("#"+id1).attr("name", "stockHistoryCustom.categoryIdOneLevel");
 										} else if (level == 1) {
 											$("#"+id1).attr("name", "stockHistoryCustom.categoryIdTwoLevel");
 										} else if (level == 2) {
 											$("#"+id1).attr("name", "stockHistoryCustom.categoryIdThreeLevel");
 										}
 										$("#categoryLevel").val(level);
 									}
									if(nodes != null && nodes != ""){
										$("#"+id).val(nodes[0].name);
										$("#"+id1).val(nodes[0].id);
									}
								}
						);
						$.fn.zTree.init($("#show_tree"), tree_setting, ret);
					}, function(err) {
						$.frontEngineDialog.executeDialogOK('错误', err);
					});
};

/*
 * 改变table的表头
 */
function loadtr() {
	$("#ff_DataTable").find("tbody").empty();
	var vendorInputCheckbox = $("#vendorInput").get(0).checked;
	var categoryInputCheckbox = $("#categoryInput").get(0).checked;
	var skuBarcodeInputCheckbox  = $("#skuBarcodeInput").get(0).checked;
	var categoryLevel = $("#categoryLevel").val();
	var tableHtml = "";
	if (vendorInputCheckbox && categoryInputCheckbox) {
		tableHtml = "<tr>" +
						"<th width='15px'><input id='checkboxAll' type='checkbox' ></th>" +
		                "<th>序号</th>" +
		                "<th>供应商</th>" +
		                "<th>一级类目</th>" +
		                "<th>二级类目</th>" +
		                "<th>三级类目</th>" +
		                "<th>库存数</th>" +
		                "<th>日期</th>" +
 					"</tr>";
	} else if ((vendorInputCheckbox && skuBarcodeInputCheckbox) || (categoryInputCheckbox && skuBarcodeInputCheckbox)) {
		tableHtml = "<tr>" +
						"<th width='15px'><input id='checkboxAll' type='checkbox' ></th>" +
					    "<th>序号</th>" +
					    "<th>sku条形码</th>" +
					    "<th>商品名称</th>" +
					    "<th>SKU编码</th>" +
					    "<th>一级类目</th>" +
					    "<th>二级类目</th>" +
					    "<th>三级类目</th>" +
					    "<th>供应商</th>" +
					    "<th>库存数</th>" +
					    "<th>日期</th>" +
					"</tr>";
	} else if (vendorInputCheckbox) {
		tableHtml = "<tr>" +
						"<th width='15px'><input id='checkboxAll' type='checkbox' ></th>" +
						"<th>序号</th>" +
			            "<th>供应商</th>" +
			            "<th>库存数</th>" +
			            "<th>日期</th>" +
					"</tr>";
	} else if (categoryInputCheckbox) {
		if (categoryLevel == "0") {
			tableHtml = "<tr>" +
						"<th width='15px'><input id='checkboxAll' type='checkbox' ></th>" +
			            "<th>序号</th>" +
			            "<th>一级类目</th>" +
			            "<th>库存数</th>" +
			            "<th>日期</th>" +
					"</tr>";
		} else if (categoryLevel == "1") {
			tableHtml = "<tr>" +
						"<th width='15px'><input id='checkboxAll' type='checkbox' ></th>" +
			            "<th>序号</th>" +
			            "<th>二级类目</th>" +
			            "<th>库存数</th>" +
			            "<th>日期</th>" +
					"</tr>";
		} else if (categoryLevel == "2") {
			tableHtml = "<tr>" +
							"<th width='15px'><input id='checkboxAll' type='checkbox' ></th>" +
				            "<th>序号</th>" +
				            "<th>一级类目</th>" +
				            "<th>二级类目</th>" +
				            "<th>三级类目</th>" +
				            "<th>库存数</th>" +
				            "<th>日期</th>" +
						"</tr>";
		} else {
			tableHtml = "<tr>" +
						"<th width='15px'><input id='checkboxAll' type='checkbox' ></th>" +
			            "<th>序号</th>" +
			            "<th>一级类目</th>" +
			            "<th>二级类目</th>" +
			            "<th>三级类目</th>" +
			            "<th>库存数</th>" +
			            "<th>日期</th>" +
					"</tr>";
		}
	} else {
		tableHtml = "<tr>" +
						"<th width='15px'><input id='checkboxAll' type='checkbox' ></th>" +
					    "<th>序号</th>" +
					    "<th>sku条形码</th>" +
					    "<th>商品名称</th>" +
					    "<th>SKU编码</th>" +
					    "<th>一级类目</th>" +
					    "<th>二级类目</th>" +
					    "<th>三级类目</th>" +
					    "<th>供应商</th>" +
					    "<th>库存数</th>" +
					    "<th>日期</th>" +
					"</tr>";
	}
	$("#ff_DataTable").find("thead").html(tableHtml);
}

/*
 * 导出
 */
function exportExcel() {
	document.forms[0].action = rootPath + "/stockHistory/exportExcel.do";
	
	document.forms[0].submit();
	
	document.forms[0].action = rootPath + "/stockHistory/list.do";
}
