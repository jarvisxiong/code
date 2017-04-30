<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="edit"/>
<title>角色编辑</title>
</head>
<body>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="addRole">
			<div class="row">
				<div class="col-lg-10 col-md-12 col-sm-12">
					<div class="addForm1">
						<form action="${BasePath !}/role/save.do" method="post" id="myform">
							<input type="hidden" class="form-control" id="id" name="id" value="${(role.id) !}" />
							<div id="error_con" class="tips-form">
								<ul></ul>
							</div>
							<div class="tips-form"></div>
							<div class="width-input-div">
							<div class="form-tr">
								<div class="form-td">
									<label><i>*</i>角色名称：</label>
									<div class="div-form">
										<input class="form-control input-sm txt_big2" type="text" id="name" name="name" value="${(role.name) !}" data-rule-required="true" data-msg-required="角色名称不能为空">
									</div>
								</div>
							</div>
							<div class="form-tr">
								<div class="form-td">
									<label>角色备注：</label>
									<div class="div-form">
										<input class="form-control input-sm txt_big2" type="text" id="remarks" name="remarks" value="${(role.remarks) !}">
									</div>
								</div>
							</div>
							<div class="form-tr">
								<div class="form-td">
									<label>是否启用：</label>
									<div class="div-form"> 
									<input type="radio" id="useable" name="useable" value="1" ${((role.useable)?default('1')== '1')?string('checked','')}/> 是 
									<input type="radio" id="useable" name="useable" value="0" ${((role.useable)?default('1')== '0')?string('checked','')}/> 否
									</div>
								</div>
							</div>
							<div class="form-tr">
								<div class="form-td">
									<label>数据范围：</label>
									<div class="div-form">
										<select id="dataScope" name="dataScope" onchange="changeDataScope(this.value)" class="form-control txt_mid input-sm"> 
											<#list dataScopes as item>
											<option value="${item.getValue()}" ${((role.dataScope)?default('')==item.getValue())?string('selected','')}>${item.getInfo()}</option>
											</#list>
										</select>
									</div>
								</div>
							</div>
							<div class="form-tr">
								<div class="form-td">
									<label>角色授权：</label>
									<div class="div-form">
										<input type="hidden" class="form-control" id="roleMenuIds" name="roleMenuIds" value="" />
										<input type="hidden" class="form-control" id="roleOfficeIds" name="roleOfficeIds" value="" />
										<div class="zTreeDemoBackground left col-md-4">
											<ul id="left_Menu_tree" class="ztree"></ul>
										</div>
										<div class="zTreeDemoBackground right col-md-4">
											<ul id="right_office_tree" class="ztree" style="${((role.dataScope)?default('')!='9')?string('display:none;','')}"></ul>
										</div>
									</div>
								</div>
							</div>
							<div class="form-tr">
	                <div class="form-group btn-div">
									<@permission name="pms:role:assign">
									<input type="submit" class="btn btn-primary" value="保存">
									</@permission>
									<input type="button" class="btn btn-default" value="返回" onclick="isReturn('${BasePath !}/role/list.do')">
								</div>
							</div>
							
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<#include "../common/tree.ftl" encoding="utf-8">
	<script type="text/javascript" src="${BasePath !}/asset/js/pms/role/role_form.js?v=${ver !}"></script>
	<script type="text/javascript">
	
		var menuNodes = ${menuTreeList !};
		var officeNodes = ${officeTreeList !};
	
		$(function() {
			executeValidateFrom('myform', 'roleMenuOffice');
		});

	</script>
</body>
</html>
