<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>红包管理</title>
	<#include "../common/share_macro.ftl" encoding="utf-8">
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
		            <form id="find-page-orderby-form" action="${BasePath !}/redpackage/list2.do" method="post">
                           <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
                        <input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
                           <div class="inquire-ul">
                               <div class="form-tr">
				               <div class="form-td">
                                    <label>红包名称：</label>
                                    <div class="div-form">
                                    <input class="form-control input-sm txt_mid" type="text" id="name" name="name" value="${(redpackage.name) !}">
                                    </div>
                                </div>
                                <div class="form-td">
				                    <label>红包编号：</label>
				                    <div class="div-form"><input class="form-control input-sm txt_mid" maxlength="30" type="text" id="number" name="number" value="${(redpackage.number) !}"></div>
				                </div>
                                <div class="form-td">
				                    <label>面值：</label>
				                    <div class="div-form"><input  style="width: 66px;" class="form-control input-sm txt_mid"  onBlur="floatNum(this);"  type="text" id="fackValuestart" name="fackValuestart" value="${(redpackage.fackValuestart) !}"></div>
				                    -
				                    <div class="div-form"><input  style="width: 66px;" class="form-control input-sm txt_mid" onBlur="floatNum(this);"  type="text" id="fackValueend" name="fackValueend" value="${(redpackage.fackValueend) !}"></div>
				                </div>
				                <div class="form-td">
				                    <label>有效期：</label>
				                    <div class="div-form">
					                    <input id="startDateStr" name="startDateStr"  class="form-control txt_mid input-sm"  
													onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
													value="${(redpackage.startDateStr) !}" /> -
										<input name="endDateStr" id="endDateStr" class="form-control txt_mid input-sm"  
										onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										 value="${(redpackage.endDateStr) !}"> 									
									</div>
				                </div>
				                <div class="form-td">
				                    <label>状态：</label>	
				                    <div class="div-form">
										<select class="input-select2" id="state" name="state">  
				                          <option value="">--全部--</option>
				                          <option value="1" <#if redpackage??><#if redpackage.state?? && redpackage.state == "1">selected="selected"</#if></#if> >启用</option>
				                          <option value="0" <#if redpackage??><#if redpackage.state?? && redpackage.state == "0">selected="selected"</#if></#if>>禁用</option>
				                        </select>
			                        </div>
				                </div>
				                
                                <div class="form-td">
				                    <label>最近操作时间：</label>
				                         <input id="lastUpdateDateStartStr" name="lastUpdateDateStartStr"  class="form-control txt_mid input-sm"  
													onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
													value="${(redpackage.lastUpdateDateStartStr?string('yyyy-MM-dd HH:mm:ss')) !}" /> -
										<input name="lastUpdateDateEndtStr" id="lastUpdateDateEndtStr" class="form-control txt_mid input-sm"  
										onfocus="WdatePicker({minDate:'#F{$dp.$D(\'lastUpdateDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										 value="${(redpackage.lastUpdateDateEndtStr?string('yyyy-MM-dd HH:mm:ss')) !}"> 									
										
		                    
				                </div>
				                
                                <div class="form-td">
				                    <label>操作人：</label>
				                    <div class="div-form"><input class="form-control input-sm txt_mid"  type="text" id="lastUpdateName" name="lastUpdateName" value="${(redpackage.lastUpdateName) !}"></div>
				                </div>
                               </div>                              
                           </div>
                           
                           <div class="btn-div3">
                            
							
                            <@permission name="prms:redpackage:list">
                            	<!-- 查询按钮的 type 改为 button -->
                            	<!--<button id="find-page-orderby-button" class="btn btn-primary btn-sm" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>-->
								<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="button"><i ></i>查询</button>
								
								<!-- SSUI: 注意添加 class: btn-clear-keyword 和 data-target="DataTable的ID"-->
								<!--<button id="clean" onclick=cleanRest() class="btn btn-primary btn-sm" type="button"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button>-->
		                        <button class="btn btn-primary btn-sm btn-clear-keyword"  data-target="redpackage_list2"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>
							</@permission>
      
                            <!-- 在全幅新窗口打开页面，使用 iframeFullPage('path/to/page') -->
                            <!-- <a href="${BasePath !}/redpackage/form.do" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a> -->
							<@permission name="prms:redpackage:add">
								<a href="javascript:iframeFullPage('${BasePath !}/redpackage/form.do')" class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>新增</a>
                            </@permission>
                            
                          <!-- 在全幅新窗口打开页面，使用 iframeFullPage('path/to/page') -->
                            <!-- <a href="${BasePath !}/redpackage/form.do" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a> -->
							<@permission name="prms:redpackagegrant:list">
								<a href="javascript:;" class="btn btn-primary btn-sm redpackageGrantPage" ><i class="fa fa-plus"></i>红包发放管理</a>
                            </@permission>
			            </div>
                    </form>
		        		</div>
				</div>

                <!-- SSUI: 只需定义 DataTable 外围的 DIV ID，注意添加统一的 class: ff_DataTable 便于全局控制 -->
				<div id="redpackage_list2" class="ff_DataTable"></div> 
    </div> 
</div>
<#-- <#include "../common/select.ftl" encoding="utf-8"> -->

<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css" type="text/css"> 
<link rel="stylesheet"  href="${BasePath !}/asset/js/control/ztree/css/tree_artdialog.css" type="text/css"> 
<script type="text/javascript" src="${BasePath !}/asset/js/redpackage/redpackage_list.js?ver=${ver !}"></script>

<script type="text/javascript">
	var stateType = {"1" : "启用", "0":"禁用"};
	var isGrantType = {"1" : "是", "0":"否"};
	$(document).ready(function() {   
	    requirejs(['ff/init_datatable'], function(initDataTable) {
	        dt_data_list = new initDataTable({
	        div_id: 'redpackage_list2',
	        show_checkbox: true,
	        url: rootPath + "/redpackage/list2.do",
	        columns:[
	            { data: "number", label: '红包编号',render: function( data, type, full, meta){return toDetailed( data, type, full, meta)}},
	            { data: "name", label: '红包名称', },
	            { data: "fackValue", label: '面值(元)', },
	            { data: "effectiveStr", label: '有效期', },
	            { data: "grantNum", label: '发放数量'},
	            { data: "state", label: '状态',data_dict: stateType},
	            { data: "isGrant", label: '是否加入红包发放管理',data_dict: isGrantType},
	            { data: "lastUpdateName", label: '操作人'},
	            { data: "lastUpdateDate", label: '最近操作时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'} }
			],
	        gen_permission: function(data){
	            // SSUI: 生成权限
	            var map = [];	
	            //禁用
				<@permission name="prms:redpackage:isEnabled">
					map.push('isEnableds');
				</@permission>	
	        	//编辑
				<@permission name="prms:redpackage:edit">
					map.push('edit');
				</@permission>
				//删除
				<@permission name="prms:redpackage:delete">
					map.push('deletes');
				</@permission>
				
	            return map;     
	        },
	        clm_action: function(item){
	            // SSUI: gen_action() 在外链的 user_list.js 中详细定义
	            return gen_action(item);
	        },
	        row_dblclick:function(row,data){
	             iframeFullPage(rootPath+"/redpackage/allform.do?viewType=1&id=" + data.id);
	        }
	        });
	    });
	});
</script>
</body>
