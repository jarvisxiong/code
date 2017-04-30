<html>
<head>
<meta name="decorator" content="list" />
<title>角色管理</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="roleList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/role/list.do" method="post">
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>角色名：</label> 
									<div class="div-form"><input id="name" name="name" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(role.name) !}"></div>
								</div>
								<div class="form-td">
									<label>数据范围：</label> 
									<div class="div-form">
									<select id="dataScope" name="dataScope" class="form-control txt_mid input-sm"> 
										<option value="">--请选择--</option>
										<#list dataScopes as item>
										<option value="${item.getValue()}" ${((role.dataScope)?default('')==item.getValue())?string('selected','')}>${item.getInfo()}</option>
										</#list>
									</select>
									</div>
								</div>
								<div class="form-td">
									<label>是否启用：</label>
									<div class="div-form"> 
									<select id="useable" name="useable" class="form-control txt_mid input-sm"> 
										<option value="">--请选择--</option>
										<option value="1" ${((role.useable)?default('')== '1')?string('selected','')}>是</option>
										<option value="0" ${((role.useable)?default('')== '0')?string('selected','')}>否</option>
									</select>
									</div>
								</div>
	
							</div>
						</div>
						<div class="btn-div3">
						<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit">
										<i class="fa fa-search"></i>&nbsp;&nbsp;查询
									</button>
						<a href="${BasePath !}/role/form.do" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a></div>
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="1%"></th>
				        <th width="25%">角色名称</th>
						<th width="20%">数据范围</th>
						<th width="15%">是否启用</th>
						<th width="20%">最后更新时间</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
				<tbody>
					<#if roleList?? > 
					<#list roleList as item >
					<tr>
						<td>${item_index + 1}</td>
						<td>${(item.name) !}</td>
						<td>${(item.dataScopeInfo) !}</td>
						<td>${((item.useable)?default('1')=='1')?string('是','否')}</td>
						<td>${item.lastUpdateDate?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td>
							<@permission name="pms:role:edit">
							<a href="${BasePath !}/role/form.do?id=${(item.id) !}">编辑</a>
							</@permission>
							<@permission name="pms:role:delete">
							| <a name="delete_item" href="${BasePath !}/role/delete.do?id=${(item.id) !}">删除</a>
							</@permission>
							<@permission name="pms:role:assign">
							| <a href="${BasePath !}/role/roleUserList.do?id=${(item.id) !}">分配</a>
							</@permission>
						</td>
					</tr>
					</#list> 
					</#if>
				</tbody>
			</table>
			<#include "../common/page_macro.ftl" encoding="utf-8"> 
			<@my_page pageObj/>
		</div>
	</div>
	<#include "../common/tree.ftl" encoding="utf-8">
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
	<script type="text/javascript" src="${BasePath !}/asset/js/pms/role/role_list.js?v=${ver !}"></script>
</body>
</html>
