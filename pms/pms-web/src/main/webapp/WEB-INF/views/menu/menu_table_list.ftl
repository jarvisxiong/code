<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>演示添加页面</title>
</head>
<body>

	<a href="${BasePath !}/menu/form.do" class="ff-btn sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
	
	<table class="ff-tbl" id="TreeTable" style="display:none;">
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

<script src="${BasePath !}/asset/js/pms/menu/menu_list.js?v=${ver !}" /></script>
<script>
	
	$(window).load(function(){
		var $treeTbl = $('#TreeTable');
		
		var watchTbl = setInterval(function(){
				
			if (typeof $treeTbl.find('tr:last').attr('id') != 'undefined') {
				$treeTbl.show();							
				return false;
			}
			
			if ($treeTbl.is(':visible')) {
				clearInterval(watchTbl);
			}
						
		}, 500);
	});
	
</script>
</body>
</html>