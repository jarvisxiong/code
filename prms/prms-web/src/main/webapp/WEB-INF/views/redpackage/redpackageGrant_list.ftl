<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>红包发放管理</title>
	<#include "../common/share_macro.ftl" encoding="utf-8">
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
		            <form id="find-page-orderby-form" action="${BasePath !}/redpackageGrant/list2.do" method="post">
                           <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
                        <input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
                           <div class="inquire-ul">
                               <div class="form-tr">
				               <div class="form-td">
                                    <label>发放名称：</label>
                                    <div class="div-form">
                                    <input class="form-control input-sm txt_mid" type="text" id="name" name="name" value="${(redpackageGrant.name) !}">
                                    </div>
                                </div>
                                <div class="form-td">
				                    <label>发放编号：</label>
				                    <div class="div-form"><input class="form-control input-sm txt_mid" maxlength="30" type="text" id="number" name="number" value="${(redpackageGrant.number) !}"></div>
				                </div>
				                
				                <div class="form-td">
				                    <label>发放开始时间：</label>
				                    <div class="div-form">
					                    <input id="startDateStartStr" name="startDateStartStr"  class="form-control txt_mid input-sm"  
													onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
													value="${(redpackageGrant.startDateStartStr) !}" /> -
													
										 <input name="startDateEndStr" id="startDateEndStr" class="form-control txt_mid input-sm"  
										onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										 value="${(redpackageGrant.startDateEndStr) !}" /> 
										 								
									</div>
				                </div>
				                
                                <div class="form-td">
				                    <label>领取截止时间：</label>
				                         <input id="endDateStartStr" name="endDateStartStr"  class="form-control txt_mid input-sm"  
													onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDateEndStr\')}'})" 
													value="${(redpackageGrant.endDateStartStr?string('yyyy-MM-dd HH:mm:ss')) !}" /> -
													
													
				                         <input id="endDateEndStr" name="endDateEndStr"  class="form-control txt_mid input-sm"  
													onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
													value="${(redpackageGrant.endDateEndStr) !}" />
													
		                    
				                </div>
                                <div class="form-td">
				                    <label>红包金额：</label>
				                    <div class="div-form"><input  style="width: 66px;" onBlur="floatNum(this,'红包金额');" class="form-control input-sm txt_mid"  type="text" id="fackValuestart" name="fackValuestart" value="${(redpackageGrant.fackValuestart) !}"></div>
				                    -
				                    <div class="div-form"><input  style="width: 66px;" onBlur="floatNum(this,'红包金额');" class="form-control input-sm txt_mid"  type="text" id="fackValueend" name="fackValueend" value="${(redpackageGrant.fackValueend) !}"></div>
				                </div>
				                <div class="form-td">
				                    <label>状态：</label>	
				                    <div class="div-form">
										<select class="input-select2" id="state" name="state">  
				                          <option value="">--全部--</option>
				                          <option value="1" <#if redpackageGrant??><#if redpackageGrant.state?? && redpackageGrant.state == "1">selected="selected"</#if></#if> >启用</option>
				                          <option value="0" <#if redpackageGrant??><#if redpackageGrant.state?? && redpackageGrant.state == "0">selected="selected"</#if></#if>>禁用</option>
				                        </select>
			                        </div>
				                </div>
				                
				                <div class="form-td">
				                    <label>最近操作时间：</label>
				                         <input id="lastUpdateDateStartStr" name="lastUpdateDateStartStr"  class="form-control txt_mid input-sm"  
													onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'lastUpdateDateEndtStr\')}'})" 
													value="${(redpackageGrant.lastUpdateDateStartStr?string('yyyy-MM-dd HH:mm:ss')) !}" /> -
										<input name="lastUpdateDateEndtStr" id="lastUpdateDateEndtStr" class="form-control txt_mid input-sm"  
										onfocus="WdatePicker({minDate:'#F{$dp.$D(\'lastUpdateDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										 value="${(redpackageGrant.lastUpdateDateEndtStr?string('yyyy-MM-dd HH:mm:ss')) !}"> 									
										
		                    
				                </div>
                                <div class="form-td">
				                    <label>操作人：</label>
				                    <div class="div-form"><input class="form-control input-sm txt_mid"  type="text" id="lastUpdateName" name="lastUpdateName" value="${(redpackageGrant.lastUpdateName) !}"></div>
                               </div>                              
                           </div>
                           
                           <div class="btn-div3">
                            
							
                            <@permission name="prms:redpackagegrant:list">
                            	<!-- 查询按钮的 type 改为 button -->
                            	<!--<button id="find-page-orderby-button" class="btn btn-primary btn-sm" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>-->
								<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="button"><i ></i>查询</button>
								
								<!-- SSUI: 注意添加 class: btn-clear-keyword 和 data-target="DataTable的ID"-->
								<!--<button id="clean" onclick=cleanRest() class="btn btn-primary btn-sm" type="button"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button>-->
		                        <button class="btn btn-primary btn-sm btn-clear-keyword"  data-target="redpackageGrant_list2"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>
							</@permission>
      
                            <!-- 在全幅新窗口打开页面，使用 iframeFullPage('path/to/page') -->
                            <!-- <a href="${BasePath !}/redpackageGrant/form.do" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a> -->
							<@permission name="prms:redpackagegrant:add">
								<a href="javascript:iframeFullPage('${BasePath !}/redpackageGrant/form.do')" class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>新增</a>
                            </@permission>
                            
                         <!--直接返回会导致点多次返回但是返回不了 -->
                            <input type="button" class="btn btn-default redpackagePage"  value="返回&nbsp;" />
			            </div>
                    </form>
		        		</div>
				</div>

                <!-- SSUI: 只需定义 DataTable 外围的 DIV ID，注意添加统一的 class: ff_DataTable 便于全局控制 -->
				<div id="redpackageGrant_list2" class="ff_DataTable"></div> 
    </div> 
</div>
<#-- <#include "../common/select.ftl" encoding="utf-8"> -->

<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css" type="text/css"> 
<link rel="stylesheet"  href="${BasePath !}/asset/js/control/ztree/css/tree_artdialog.css" type="text/css"> 
<script type="text/javascript" src="${BasePath !}/asset/js/redpackage/redpackageGrant_list.js?ver=${ver !}"></script>

<script type="text/javascript">
	var grantModeType = {"1" : "消息红包推送", "2":"天降红包推送"};
	var isGrantType = {"1" : "是", "0":"否"};
	var stateType = {"1" : "启用", "0":"禁用"};
	$(document).ready(function() {   
	    requirejs(['ff/init_datatable'], function(initDataTable) {
	        dt_data_list = new initDataTable({
	        div_id: 'redpackageGrant_list2',
	        show_checkbox: false,
	        url: rootPath + "/redpackageGrant/list2.do",
	        columns:[
	            { data: "number", label: '发放编码',render: function( data, type, full, meta){return toDetailed( data, type, full, meta)}},
	            { data: "name", label: '发放名称', },
	            { data: "grantMode", label: '发放方式',data_dict: grantModeType },
	            { data: "startDate", label: '发放开始时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'}  },
	            { data: "endDate", label: '领取截止时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'}},
	            { data: "redpackage.number", label: '红包明细'},
	            { data: "redpackage.fackValue", label: '红包金额'},
	            { data: "redpackage.grantNum", label: '发放数量'},
	            { data: "state", label: '状态',data_dict: stateType},
	            { data: "lastUpdateName", label: '操作人'},
	            { data: "lastUpdateDate", label: '最近操作时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'} }
			],
	        gen_permission: function(data){
	            // SSUI: 生成权限
	            var map = [];	
	            //禁用
				<@permission name="prms:redpackagegrant:isEnabled">
					map.push('isEnableds');
				</@permission>	
	        	//编辑
				<@permission name="prms:redpackagegrant:edit">
					map.push('edit');
				</@permission>
				//删除
				<#--<@permission name="prms:redpackagegrant:delete">
					map.push('deletes');
				</@permission>-->
				
	            return map;     
	        },
	        clm_action: function(item){
	            // SSUI: gen_action() 在外链的 user_list.js 中详细定义
	            return gen_action(item);
	        },
	        row_dblclick:function(row,data){
	             iframeFullPage(rootPath+"/redpackageGrant/detail.do?viewType=1&id=" + data.id);
	        }
	        });
	    });
	});
</script>
</body>
