<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>差价明细管理</title>
	<#include "../common/page_macro.ftl" encoding="utf-8"> 
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
				<form id="find-page-orderby-form" action="${BasePath !}/pricesettlementdetail/list.do?psNo=${(psNo) !}" method="post">
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>合伙人ID：</label> 
								<div class="div-form"><input id="partnerCodeLike" name="partnerCodeLike" class="form-control txt_mid input-sm" type="text" placeholder="" value=""></div>
						    </div>
						    
						    <div class="form-td">
								<label>总销售额：</label> 
								<div class="div-form"><input id="saleAmount" readOnly="readOnly" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(pricesettlement.totaSaleAmount) !}"></div>
						    </div>
						    
						    <div class="form-td">
								<label>总补差金额：</label> 
								<div class="div-form"><input id="balance"  readOnly="readOnly" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(pricesettlement.totalBalance) !}"></div>
						    </div>
						    
						    <div class="form-td">
								<label>合伙人名称：</label> 
								<div class="div-form"><input id="partnerNameLike" name="partnerNameLike" class="form-control txt_mid input-sm" type="text" placeholder="" value=""></div>
						    </div>
						    <div class="form-td">
					    		<label>创建时间：</label> 
								<div class="div-form">
                                    <input name="createDateStart" id="createDateStart" class="form-control txt_mid input-sm"  readonly="readonly"
                                     onfocus="WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd'})"
                                     value="">
                                   </div> -         
                                   <div class="div-form">
                                     <input name="createDateEnd" id="createDateEnd" class="form-control txt_mid input-sm"  readonly="readonly"
                                     onfocus="WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd'})"
                                     value="">
                                </div>
						    </div>
                    
                         </div>
					</div>
					<div class="btn-div3">
					
						<!-- SSUI: 查询按钮的 type 改为 button -->
						<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
						<!-- SSUI: 注意添加 class: btn-clear-keyword 和 data-target="DataTable的ID"-->
						<!--  <a href="#" class="btn btn-primary btn-sm" id="cleanAll"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</a> -->
                        <button class="btn btn-primary btn-sm btn-clear-keyword"  data-target="data_list"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>
                        <input type="button" class="btn btn-default btn-close-iframeFullPage" value="后退">
					</div>
					</div> 
				</form>
			</div>
		</div>	
		<!-- SSUI: 只需定义 DataTable 外围的 DIV ID，注意添加统一的 class: ff_DataTable 便于全局控制 -->
		<div id="data_list" class="ff_DataTable"></div>
	</div>
</div>

<#include "../common/tree.ftl" encoding="utf-8">

<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css" type="text/css"> 
<link rel="stylesheet"  href="${BasePath !}/asset/js/control/ztree/css/tree_artdialog.css" type="text/css"> 
<script>
var viewState = '${(viewState) !}';

var dt_demo_list;
$(document).ready(function(){		
		requirejs(['ff/init_datatable'], function(initDataTable){
			dt_demo_list = new initDataTable({
			div_id: 'data_list',
			url: rootPath + "/pricesettlementdetail/listData.do?psNo=${(psNo) !}",
			columns:[
				{ data: "psNo", label: '结算单号'},
				{ data: "orderNo", label: '销售订单号' },
				{ data: "commodityTitle", label: '商品名称' },
				{ data: "buyNum", label: '数量' },
				{ data: "commodityUnit", label: '单位'},
				{ data: "actPayAmount", label: '总金额' },
				{ data: "pifaPrice", label: '批发价' },
				{ data: "memberName", label: '购买人' },
				{ data: "serviceStationName", label: '关联服务站' },
				{ data: "partnerCode", label: '关联合伙人ID' },
				{ data: "partnerName", label: '关联合伙人' },
				{ data: "createDate", label: '创建时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'} },
				{ data: "priceSettlement.statusName", label: '状态'}
			],
			gen_permission: function(){
				
				// SSUI: 生成权限
				var map = [];
			    <@permission name="oms:pricesettlement:dataildelete">
				map.push('_delete');
				</@permission> 
				return map;		
			},
			clm_action: function(item){
				// SSUI: 方法 gen_action 的具体逻辑，在外链的 demo_list.js 中定义
				return gen_action(item);
			},
			row_dblclick:function(row,data){
				 // iframeFullPage(rootPath + "/commodity/form.do?commodityCode="+data.code+"&type=1&newPage=1");
			}
		});
			
	 	$('#data_list').on('draw.dt',function() {
			
       });
			
			
	});
});
	
	
function gen_action(item) {
	var objAction = {
			_delete:[]
	};
	 //console.log(JSON.stringify(item)+"===================================================================");
	// 待审核
	if (item.priceSettlement.status == '0'&&viewState!='VIEW') {
		objAction['_delete'].push({
			label: '删除',
			href: "javascript:deleteDetail('"+item.id+"')"
		});
	}
	
	return objAction;
}

function deleteDetail(id) {
	$.frontEngineDialog.executeDialog(
		'statusOpt',
		'删除',
		'是否删除？',
		"250px",
		"35px",
		function() {
			$.ajax({
				url : rootPath + "/pricesettlementdetail/delete.do",
				data : {
					id: id
				},// 给服务器的参数
				type : "POST",
				dataType : "json",
				async : false,
				cache : false,
				success : function(result) {
					if (result.status == 'success' || result.code == 0) {
						dialog({
							quickClose : true,
							content : result.infoStr
						}).show();
						setTimeout(function(){
							window.location.href= rootPath + "/pricesettlementdetail/list.do?psNo=${(psNo) !}";
						}, 1000);
					} else {
						dialog({
							quickClose : true,
							content : result.infoStr
						}).show();
					}
				}
			});
		});
}

function tips(msg){
	$.frontEngineDialog.executeDialogContentTime(msg,1000);
}
</script>
</body>
</html>