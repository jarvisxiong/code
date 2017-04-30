<html>
<head>
<meta name="decorator" content="v2" />
<title>角色管理</title>
</head>
<body>

	<form id="find-page-orderby-form" action="${BasePath !}/role/list.do" method="post" class="ff-form">
		<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
		<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
		<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
		
		<div class="form-group">
			<label>角色名：</label> 
			<div><input id="name" name="name" type="text" placeholder="" value="${(role.name) !}"></div>
		</div>
		
		<!-- 
		<div class="form-group">
			<label>数据范围：</label> 
			<div>
				<select id="dataScope" name="dataScope"> 
					<option value="">--请选择--</option>
					<#list dataScopes as item>
					<option value="${item.getValue()}" ${((role.dataScope)?default('')==item.getValue())?string('selected','')}>${item.getInfo()}</option>
					</#list>
				</select>
			</div>
		</div>
		-->
		
		<div class="form-group">
			<label>是否启用：</label>
			<div> 
				<select id="useable" name="useable"> 
					<option value="">--请选择--</option>
					<option value="1" ${((role.useable)?default('')== '1')?string('selected','')}>是</option>
					<option value="0" ${((role.useable)?default('')== '0')?string('selected','')}>否</option>
				</select>
			</div>
		</div>

		<button id="find-page-orderby-button" class="ff-btn sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
		<a href="javascript:iframeFullPage('${BasePath !}/role/form.do')" class="ff-btn sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
		<button class="ff-btn sm white btn-clear-keyword" data-target="role_list"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>

	</form>
	
	<div id="role_list" class="ff_DataTable"></div>

	<script type="text/javascript" src="${BasePath !}/asset/js/pms/role/role_list.js?v=${ver !}"></script>
	<style>.clm_action{width:150px !important;}</style>
	<script>
	
		$(document).ready(function(){		
		
			requirejs(['ff/select2'], function(){
				$('select').select2();
			});
			
			requirejs(['ff/init_datatable'], function(initDataTable){
				
				var dt_role_list = new initDataTable({
					div_id: 'role_list',
					url: rootPath + "/role/roleQueryData.do",
					columns:[
						{ data: "name", label: '角色名称', class:'text-nowrap'},
						//{ data: "dataScopeInfo", label: '数据范围', class:'text-nowrap'},
						{ data: "useable", label: '是否启用', class:'text-nowrap', data_dict:{"1":"是", "0":"否"}},
						{ data: "lastUpdateDate", label: '最后更新时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd hh:mm:ss'}}
					],
					show_checkbox: false,
					gen_permission: function(){
						var map = [];
						
						<@permission name="pms:role:edit">
							map.push('edit');
						</@permission>
						<@permission name="pms:role:delete">
							map.push('delete');
						</@permission>
						<@permission name="pms:role:assign">
							map.push('roleUser');
						</@permission>
		
						return map;		
					},
					clm_action: function(item){
						return gen_action(item);
					}
				});
			});
		});
	
	</script>
</body>
</html>
