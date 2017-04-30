<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>红包领取记录</title>
	<#include "../common/share_macro.ftl" encoding="utf-8">
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
		            <form id="find-page-orderby-form" action="${BasePath !}/redpackageReceive/list2.do" method="post">
                           <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
                        <input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
                           <div class="inquire-ul">
                               <div class="form-tr">
				               <div class="form-td">
                                    <label>会员账号：</label>
                                    <div class="div-form">
                                    <input class="form-control input-sm txt_mid" type="text" id="phone" name="phone" value="${(redpackageReceive.phone) !}">
                                    </div>
                                </div>
                                <div class="form-td">
				                    <label>领取时间：</label>
				                    <div class="div-form">
					                    <input id="receiveDateStartStr" name="receiveDateStartStr"  class="form-control txt_mid input-sm"  
													onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'receiveDateEndStr\')}'})" 
													value="${(redpackageReceive.receiveDateStartStr) !}" /> -
										<input name="receiveDateEndStr" id="receiveDateEndStr" class="form-control txt_mid input-sm"  
										onfocus="WdatePicker({minDate:'#F{$dp.$D(\'receiveDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										 value="${(redpackageReceive.receiveDateEndStr) !}"> 									
									</div>
				                </div>
                                <div class="form-td">
				                    <label>使用时间：</label>
				                   <div class="div-form">
					                    <input id="useDateStartStr" name="useDateStartStr"  class="form-control txt_mid input-sm"  
													onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'useDateEndStr\')}'})" 
													value="${(redpackageReceive.useDateStartStr) !}" /> -
										<input name="useDateEndStr" id="useDateEndStr" class="form-control txt_mid input-sm"  
										onfocus="WdatePicker({minDate:'#F{$dp.$D(\'useDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										 value="${(redpackageReceive.useDateEndStr) !}"> 									
									</div>
				                   </div>
				                <div class="form-td">
				                    <label>使用状态：</label>
				                    <div class="div-form">
										<select class="input-select2" id="useStateStr" name="useStateStr">  
				                          <option value="">--全部--</option>
				                          <option value="0">未使用</option>
				                          <option value="1">使用</option>
				                          <option value="2">已过期</option>
				                        </select>
			                        </div>
				                </div>
				                     <div class="btn-div3">
                            
							
                            	<!-- 查询按钮的 type 改为 button -->
								<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="button"><i ></i>查询</button>
								
								<!-- SSUI: 注意添加 class: btn-clear-keyword 和 data-target="DataTable的ID"-->
		                        <button class="btn btn-primary btn-sm btn-clear-keyword"  data-target="redpackageReceive_list2"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>
							                     
                           </div>
                           
                    </form>
		        		</div>
				</div>

                <!-- SSUI: 只需定义 DataTable 外围的 DIV ID，注意添加统一的 class: ff_DataTable 便于全局控制 -->
				<div id="redpackageReceive_list2" class="ff_DataTable"></div> 
    </div> 
</div>
<#-- <#include "../common/select.ftl" encoding="utf-8"> -->

<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css" type="text/css"> 
<link rel="stylesheet"  href="${BasePath !}/asset/js/control/ztree/css/tree_artdialog.css" type="text/css"> 

<script type="text/javascript">
	var useStateType = {"1" : "使用", "0":"未使用" ,"2":"已过期"};
	var receiveModeType = {"1" : "消息推送", "2":"天降"};
	$(document).ready(function() {   
	    requirejs(['ff/init_datatable'], function(initDataTable) {
	        dt_data_list = new initDataTable({
	        div_id: 'redpackageReceive_list2',
	        show_checkbox: false,
	        show_action: false,
	        url: rootPath + "/redpackageReceive/list2.do?redpackageId=${(redpackageReceive.redpackageId) !}",
	        columns:[
	            { data: "redpackageNumber", label: '红包编码',},
	            { data: "redpackage.name", label: '红包名称', },
	            { data: "redpackage.fackValue", label: '面值', },
	            { data: "phone", label: '领取会员账号', },
	            { data: "receiveDate", label: '领取时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'}},
	            { data: "effectiveStr", label: '有效期'},
	            { data: "useStateStr", label: '使用状态',data_dict: useStateType},
	            { data: "useDate", label: '使用时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'}},
	            { data: "receiveMode", label: '领取方式',data_dict: receiveModeType }
			]
	        });
	    });
	});
</script>
</body>
