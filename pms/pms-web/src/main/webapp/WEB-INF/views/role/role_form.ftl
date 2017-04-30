<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="v2"/>
<title>角色编辑</title>
</head>
<body>
	
	<form action="${BasePath !}/role/save.do" method="post" id="myform" class="ff-form">
		<input type="hidden" class="form-control" id="id" name="id" value="${(role.id) !}" />
		<div id="error_con" class="tips-form">
			<ul></ul>
		</div>

		<div class="form-group">
			<label><i>*</i>角色名称：</label>
			<div>
				<input type="text" id="name" name="name" value="${(role.name) !}" data-rule-required="true" data-msg-required="角色名称不能为空">
			</div>
		</div>

		<div class="form-group">
			<label>角色备注：</label>
			<div>
				<input type="text" id="remarks" name="remarks" value="${(role.remarks) !}">
			</div>
		</div>

		<div class="form-group">
			<label>是否启用：</label>
			<div> 
				<label class="radio-inline">
					<input type="radio" id="useable" name="useable" value="1" ${((role.useable)?default('1')== '1')?string('checked','')} /> 是 
				</label>
				<label class="radio-inline">
					<input type="radio" id="useable" name="useable" value="0" ${((role.useable)?default('1')== '0')?string('checked','')} /> 否
				</label>
			</div>
		</div>

		<input type="hidden" name="dataScope" value="1">
		
		<!-- 
		<div class="form-tr">
			<div class="form-group">
				<label>数据范围：</label>
				<div>
					<select id="dataScope" name="dataScope" onchange="changeDataScope(this.value)"> 
						<#list dataScopes as item>
						<option value="${item.getValue()}" ${((role.dataScope)?default('')==item.getValue())?string('selected','')}>${item.getInfo()}</option>
						</#list>
					</select>
				</div>
			</div>
		</div>
		-->		
		
		<div class="form-group single-row">
			<label>角色授权：</label>
			<div>
				<input type="hidden" class="form-control" id="roleMenuIds" name="roleMenuIds" value="" />
				<input type="hidden" class="form-control" id="roleOfficeIds" name="roleOfficeIds" value="" />
				<ul id="left_Menu_tree" class="ztree pull-left"></ul>
				<ul id="right_office_tree" class="ztree pull-left" style="${((role.dataScope)?default('')!='9')?string('display:none;','')}"></ul>
			</div>
		</div>

		<div class="wrapper-btn">
			<@permission name="pms:role:assign">
			<input type="submit" class="ff-btn" value="保存">
			</@permission>
			<input type="button" class="ff-btn white btn-close-iframeFullPage" value="返回">
		</div>	

	</form>

	<script type="text/javascript" src="${BasePath !}/asset/js/pms/role/role_form.js?v=${ver !}"></script>
	<script type="text/javascript">
	
		var menuNodes = ${menuTreeList !};
		var officeNodes = ${officeTreeList !};
	
		$(function() {
		
			requirejs(['ff/select2'], function(){
				$("select").select2();
			});
			
			requirejs(['ff/validate'], function(){			
				executeValidateFrom('myform', 'roleMenuOffice', '', function(){
					parent.reloadData('role_list');
					parent.closeIframeFullPage('role_list');
				});
			});
		});

	</script>
</body>
</html>
