<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>差价管理</title>
	<#include "../common/page_macro.ftl" encoding="utf-8"> 
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
				<form id="find-page-orderby-form" action="${BasePath !}/pricesettlement/listData.do" method="post">
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
					<input id="find-page-count" type="hidden"  value="${(pageObj.pageCount) !}" />
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>结算单号：</label> 
								<div class="div-form"><input id="psNoLike" name="psNoLike" class="form-control txt_mid input-sm" type="text" placeholder="" value=""></div>
							</div>
							
							<div class="form-td">
								<label>状态：</label> 
									<div class="div-form">                                        
                                        <select class="input-select2" id="status" name="status">
                                          <option value="">--全部--</option>
                                          <option value="0">待审核</option>
				                          <option value="1">待结算</option>
				                          <option value="2">已结算</option>
                                       </select>                    
                                    </div>
								</div>
							
							<div class="form-td">
								<label>合伙人ID：</label> 
								<div class="div-form"><input id="partnerCodeLike" name="partnerCodeLike" class="form-control txt_mid input-sm" type="text" placeholder="" value=""></div>
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
						    
						     <div class="form-td">
					    		<label>结算时间：</label> 
								<div class="div-form">
                                    <input name="cutOffTimeStart" id="cutOffTimeStart" class="form-control txt_mid input-sm"  readonly="readonly"
                                     onfocus="WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd'})"
                                     value="">
                                   </div> -         
                                   <div class="div-form">
                                     <input name="cutOffTimeEnd" id="cutOffTimeEnd" class="form-control txt_mid input-sm"  readonly="readonly"
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
                         <@permission name="oms:pricesettlement:examine">
                        <button  onclick="batchOptStatus('1','审核');" class="btn btn-primary btn-sm" type="button"><i class="fa fa-certificate"></i>&nbsp;&nbsp;审核</button>
						</@permission>
						<@permission name="oms:pricesettlement:balance">
						<button  onclick="batchOptStatus('2','结算');" class="btn btn-primary btn-sm" type="button"><i class="fa fa-certificate"></i>&nbsp;&nbsp;结算</button>
						</@permission>
						<@permission name="oms:pricesettlement:print">
						<a xhref="${BasePath !}/pricesettlement/batchPrint.do" onclick="batchPrint(this)" class="btn btn-primary btn-sm" ><i class="fa fa-certificate"></i>&nbsp;&nbsp;打印</a>	
						</@permission>
						<@permission name="oms:pricesettlement:export">
						<a onclick="exportExcel()" class="btn btn-primary btn-sm" ><i class="fa fa-arrow-circle-down"></i>&nbsp;&nbsp;导出</a>
						</@permission>
						
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
var dt_demo_list;
$(document).ready(function(){		
		requirejs(['ff/init_datatable'], function(initDataTable){
			dt_demo_list = new initDataTable({
			div_id: 'data_list',
			url: rootPath + "/pricesettlement/listData.do",
			columns:[
				{ data: "psNo", label: '结算单号'},
				{ data: "totalCount", label: '总数量' },
				{ data: "totaSaleAmount", label: '总销售金额'},
				{ data: "totalBalance", label: '总差价' },
				{ data: "serviceStationName", label: '关联服务站' },
				{ data: "partnerCode", label: '关联合伙人ID' },
				{ data: "partnerName", label: '关联合伙人' },
				{ data: "createDate", label: '创建时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'} },
				{ data: "statusName", label: '状态'},
				{ data: "cutOffTime", label: '结算时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'} }
			],
			gen_permission: function(){
				
				// SSUI: 生成权限
				var map = [];
				
				/* oms:pricesettlement:examine  审核

				oms:pricesettlement:balance  结算

				oms:pricesettlement:print  打印

				oms:pricesettlement:dataildelete  明细删除

				oms:pricesettlement:edit 修改*/
				/* map.push('examine'); 
				map.push('balance');
				map.push('edit');
				map.push('print'); */
			    <@permission name="oms:pricesettlement:examine">
				map.push('examine'); 
				</@permission> 
				<@permission name="oms:pricesettlement:balance">
				map.push('balance'); 
				</@permission> 
				<@permission name="oms:pricesettlement:edit">
				map.push('edit'); 
				</@permission> 
				<@permission name="oms:pricesettlement:print">
				map.push('print'); 
				</@permission> 
				map.push('view');

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
			examine:[],
			balance:[],
			edit:[],
			print:[],
			view:[]
	};
	//待审核
	if (item.status == '0') {
		objAction['examine'].push({
			label: '审核',
			href: "javascript:statusOpt('"+item.id+"','1','审核')"
		});
		
		objAction['edit'].push({
			label: '修改',
			href: "javascript:iframeFullPage('"+ rootPath + "/pricesettlementdetail/list.do?psNo=" + item.psNo + "')",
			target: '_blank',
			class: 'testCSS'
		});
	}
	

	//待结算
	if (item.status == '1') {
		objAction['balance'].push({
			label: '结算',
			href: "javascript:statusOpt('"+item.id+"','2','结算')"
		});		
	}
	
	//已计算
	if (item.status =='2') {
		objAction['print'].push({
			label: '打印',
			target: '_blank',
			href: rootPath + "/pricesettlement/print.do?psNo=" + item.psNo
		});
	}	
		objAction['view'].push({
			label: '查看',
			href: "javascript:iframeFullPage('"+ rootPath + "/pricesettlementdetail/list.do?psNo=" + item.psNo + "&viewState=VIEW')",
			
		});
	return objAction;
}

function statusOpt(ids,status,optName) {
	
	$.frontEngineDialog.executeDialog(
		'statusOpt',
		optName,
		'是否'+optName+'？',
		"250px",
		"35px",
		function() {
			$.ajax({
				url : rootPath + "/pricesettlement/statusOpt.do",
				data : {
					ids : ids,
					status : status
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
							$("#find-page-orderby-button").click();
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

function batchPrint(a){
	var data_selected = dt_demo_list.getSelectedData();
	var itemList = data_selected.raw;
	if(itemList.length<1){
		$.frontEngineDialog.executeDialogContentTime('请选择可'+ $(a).text() +'项！！',2000);
		return;
	}
	var action=$(a).attr('xhref');
	var psNos=null;
	for ( var i =0;i<itemList.length;i++) {
		var obj = itemList[i];
		if(!psNos){
			psNos = "psNos="+obj.psNo;
		}else{
			psNos =psNos+"&"+"psNos="+obj.psNo;
		}
		
		if(obj.status!='2'){
			$.frontEngineDialog.executeDialogContentTime('已选中的选项包含了不可打印项',2000);
			return;
		}
	}
	
	var url=action+"?"+psNos;

	window.open(url);
}


function batchOptStatus(status,optName){
	var data_selected = dt_demo_list.getSelectedData();
	var itemList = data_selected.raw;
	if(itemList.length<1){
		tips("请至少选择一项");
	return;
	}
    var itemStatus = itemList[0].status;
    var ids = "";
	//判断 状态是否一致
	for ( var i =0;i<itemList.length;i++) {
		var obj = itemList[i];
		if(itemStatus!=obj.status){
			tips("所选数据状态不一致");
	    	return;
		}
		ids+=obj.id+";";
	}
	 if(status=='1'&&itemStatus!='0'){
	    	//处于非待审核状态
	    	tips("请选择待审核状态的数据");
	    	return;
	     }
	    if(status=='2'&&itemStatus!='1'){
	    	//处于非待审核状态
	    	tips("请选择待结算状态的数据");
	    	return;
	     }
	    
	   ids = ids.substr(0,(ids.length-1));
	  statusOpt(ids,status,optName);
}
function tips(msg){
	$.frontEngineDialog.executeDialogContentTime(msg,1000);
}

function exportExcel() {
	if($(".dataTables_empty").length==1){
	tips("无数据可导出");
	return;
	}
	var data_info = $("#dt_data_list_info").html();
	var dataCount = parseInt(data_info.substring(data_info.indexOf("共")+1,data_info.indexOf("条")));
	if(dataCount>=10){
		tips("一次性导出数据不能超过8000条，请调整过滤条件");
		return ;
	}
	var url=rootPath+"/pricesettlement/priceSettlementExport.do";
	var param=$("#find-page-orderby-form").form2Json();
	FFZX.download(url,param);
}

</script>
</body>
</html>