<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<head>
<#include "../common/css.ftl" encoding="utf-8">
<link rel="stylesheet"	href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
</head>
<body style="overflow-x: hidden;"> 
<div class="row">
	<div class="col-md-12">
		<div class="box-body" >

			<div class="btn-div3"><a href="${BasePath !}/menu/form.do" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a></div>
			<div class="row" style="float: left; margin-left: 0px;width: 100%;">
				
				<div class="box-header" style="float: left;padding-left: 0px;width: 99%; ">
	 					<table class="table table-hover table-striped bor2 table-common" id="TreeTable">
	                        <thead>
	                        <tr>
	                            <th width="20%">名称</th>
	                            <th width="25%">地址</th>
	                            <th width="7%">排序</th>
	                            <th width="8%">是否显示</th>
	                            <th width="20%">权限标识</th>
	                            <th>操作</th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        <#if treeTable?? >
	                        <#list treeTable as item >
	                    	<tr id="${(item.id) !}" pId="${(item.pId) !}">
	                            <td>${(item.name) !}</td>
	                            <td>${(item.href) !}</td>
	                            <td>${(item.sort) !}</td>
	                            <td><#if item.isShow = '1'>
	                            		显示
	                            	<#else>
	                            		隐藏
	                            	</#if></td>
	                            <td>${(item.permission) !}</td>
	                            <td>
	                            	<@permission name="pms:menu:edit">
		                            <a href="${BasePath !}/menu/form.do?id=${(item.id) !}">编辑</a> | 
		                            <a href="${BasePath !}/menu/form.do?parentId=${(item.id) !}">添加下级菜单</a> |
									</@permission>
									<@permission name="pms:menu:delete">
		                            <a name="delete_item" href="${BasePath !}/menu/delete.do?id=${(item.id) !}">删除</a> 
		                            </@permission> 
	                            </td>
	                        </tr>
	                        </#list>
	                        </#if>
	                        </tbody>
	                    </table>
					</div>	
				</div>
			</div>
		</div>
	</div>
</div>

<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<script src="${BasePath !}/asset/js/pms/menu/menu_list.js?v=${ver !}" /></script>

<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="big"/>

</body>
</html>