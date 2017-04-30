<html>
<head>
<meta name="decorator" content="v2" />
<title>角色管理</title>
</head>
<body>

    <input id="current_role_id" type="hidden" name="current_role_id" value="${(role.id) !}" />

	<form class="ff-form">
		<div class="form-group txt">
			<label>角色名：</label> 
			<div>${(role.name) !}</div>
		</div>
		<div class="form-group txt">
			<label>数据范围：</label> 
			<div>${(role.dataScopeInfo) !}</div>
		</div>
		<div class="form-group txt">
			<label>是否启用：</label> 
			<div>${((role.useable)?default('1')=='1')?string('是','否')}</div>
		</div>
	</form>

	<div class="wrapper-btn">
		<button id="assign_user_to_role" class="ff-btn sm">
			<i class="fa fa-search"></i>&nbsp;&nbsp;分配用户
		</button>
	</div>
	<!--表格按钮--end-->
	<table class="ff-tbl">
		<thead>
			<tr>
				<th width="2%"></th>
				<th width="19%">姓名</th>
				<th width="19%">登录名</th>
				<th width="10%">工号</th>
				<th width="20%">归属公司</th>
				<th width="20%">归属部门</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<#if userList?? > 
			<#list userList as item >
			<tr>
				<td>${item_index + 1}</td>
				<td>${(item.name) !}</td>
				<td>${(item.loginName) !}</td>
				<td>${(item.no) !}</td>
				<td>${(item.company.name) !}</td>
				<td>${(item.office.name) !}</td>
				<td>
					<@permission name="pms:role:delete">
					<a name="delete_item" href="${BasePath !}/role/deleteUser.do?roleId=${(role.id) !}&userId=${(item.id) !}" class="action-delete-item">移除</a>
					</@permission>
				</td>
			</tr>
			</#list> 
			</#if>
		</tbody>
	</table>

	<!--表格按钮--start-->
	<div class="wrapper-btn">
		<button id="assign_userGroup_to_role" class="ff-btn sm">
			<i class="fa fa-search"></i>&nbsp;&nbsp;分配用户组
		</button>
	</div>
	
	<!--表格按钮--end-->
	<table class="ff-tbl">
		<thead>
			<tr>
				<th width="2%"></th>
				<th width="50%">名称</th>
				<th width="20%">编码</th>
				<th width="18%">类型</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<#if userGroupList?? > 
			<#list userGroupList as item >
			<tr>
				<td>${item_index + 1}</td>
				<td>${(item.name) !}</td>
				<td>${(item.code) !}</td>
				<td><#if item.type=='1'>公司<#elseif item.type == '2'>部门<#elseif item.type == '3'>小组<#else>其它</#if></td>
				<td>
					<@permission name="pms:role:delete">
					<a name="delete_item" href="${BasePath !}/role/deleteUserGroup.do?roleId=${(role.id) !}&userGroupId=${(item.id) !}" class="action-delete-item">移除</a>
					</@permission>
				</td>
			</tr>
			</#list> 
			</#if>
		</tbody>
	</table>

	<input type="button" class="btn btn-default btn-close-iframeFullPage" value="返回">

	<script src="${BasePath !}/asset/js/pms/role/role_user_list.js?v=${ver !}" /></script>
</body>
</html>
