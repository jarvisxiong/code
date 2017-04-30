<html>
<head>
<meta name="decorator" content="list" />
<title>会员标签列表</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/couponGrant/memberlist.do" method="post">
					<input  id="find-page-index-noidList"  name="noidList" type="hidden" value="${noidList!""}"  />
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<input id="noidList" type="hidden" value="${noidList !}" />
						<div class="inquire-ul">
							
				             <div class="form-tr">
								<div class="form-td">
                                    <label>编号：</label>
									<div class="div-form"><input id="labelCode" name="labelCode" 
									 class="form-control txt_mid input-sm" type="text">
									</div>
							   </div>
							
							   <div class="form-td">
                                    <label>标签名称：</label>
									<div class="div-form"><input id="labelName" name="labelName"  
									 class="form-control txt_mid input-sm" type="text">
									</div>
							   </div>
							
							  <div class="form-td">
				                    <label>创建时间：</label>
				                    <div class="div-form">
				                         <input id="createDateMin" name="createDateMin"  class="form-control txt_mid input-sm"  
													onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
									</div> -
									<div class="div-form">
										<input name="createDateMax" id="createDateMax" class="form-control txt_mid input-sm"  
										onfocus="WdatePicker({minDate:'#F{$dp.$D(\'createDateMin\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> 									
									</div>
		                    
				               </div>
							   <div class="form-td">
                                    <label>备注说明：</label>
				                  <div class="div-form">
									<input id="remarks" name="remarks"  
									 class="form-control txt_mid input-sm" type="text">
								  </div>
							  </div>
								
                           <div class="btn-div3">
                           	<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="button"><i ></i>查询</button>
								
								<!-- SSUI: 注意添加 class: btn-clear-keyword 和 data-target="DataTable的ID"-->
								<!--<button id="clean" onclick=cleanRest() class="btn btn-primary btn-sm" type="button"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button>-->
		                        <button class="btn btn-primary btn-sm btn-clear-keyword"  data-target="member_list2"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>
							</div>
                           
						</div>
					</form>
				</div>
			</div>
                <!-- SSUI: 只需定义 DataTable 外围的 DIV ID，注意添加统一的 class: ff_DataTable 便于全局控制 -->
				<div id="member_list2" class="ff_DataTable"></div> 
				
    </div> 
</div>
				
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css" type="text/css"> 
<link rel="stylesheet"  href="${BasePath !}/asset/js/control/ztree/css/tree_artdialog.css" type="text/css"> 
<script type="text/javascript" src="${BasePath !}/asset/js/redpackage/redpackageGrant_memberlabel.js"></script>

<script type="text/javascript">
	var labelStatusType = {"1" : "已使用", "0":"未使用"};
	$(document).ready(function() {   
	    requirejs(['ff/init_datatable'], function(initDataTable) {
	        dt_data_list = new initDataTable({
	        div_id: 'member_list2',
	        show_checkbox: true, 
	        show_action: false,
	        url: rootPath + "/redpackageGrant/memberlabel2.do",
	        columns:[
	            { data: "labelCode", label: '编号'},
	            { data: "labelName", label: '标签名称', },
	            { data: "lastUpdateDate", label: '修改时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'} },
	            { data: "lastUpdateBy.name", label: '修改人', },
	            { data: "remarks", label: '备注'},
	            { data: "labelStatus", label: '状态',data_dict: labelStatusType}
			]
	        });
	    });
	});
</script>
</body>
</html>
