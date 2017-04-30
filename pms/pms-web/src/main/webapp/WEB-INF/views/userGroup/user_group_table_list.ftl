<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>演示添加页面</title>	
</head>
<body>
<a href="${BasePath !}/userGroup/form.do" class="ff-btn sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
	
<table class="ff-tbl" id="TreeTable">
    <thead>
	    <tr>
	        <th width="20%">名称</th>
	        <th width="7%">编码</th>
	        <th width="8%">类型</th>
	        <th>操作</th>
	    </tr>
    </thead>
    <tbody>
	    <#if treeTable?? >
	    <#list treeTable as item >
	    <tr id="${(item.id) !}" pId="${(item.pId) !}">
	        <td>${(item.name) !}</td>
	        <td>${(item.code) !}</td>
	        <td>                                    
	        <#if (item.type) ??>
	        <#if userGroupTypes ??>
	        <#list userGroupTypes as itemType >                                      
	            <#if (item.type) == (itemType.value)>${(itemType.label) !}</#if>
	        </#list>
	        </#if></#if>
	        </td>                                
	        <td>
	            <@permission name="pms:userGroup:edit">
	            <a href="${BasePath !}/userGroup/form.do?id=${(item.id) !}">编辑</a> | 
	            <a href="${BasePath !}/userGroup/form.do?parentId=${(item.id) !}">添加下级用户组</a>
	            </@permission>
	            <@permission name="pms:userGroup:delete">
	            |  <a name="delete_item" href="${BasePath !}/userGroup/delete.do?id=${(item.id) !}">删除</a>
	            </@permission>
	            <@permission name="pms:userGroup:assign">
	            | <a href="javascript:showAllUser('${(item.id) !}')">分配用户</a> | 
	           <a href="javascript:showAllrole('${(item.id) !}')">分配角色</a>
	            </@permission>
	        </td>
	    </tr>
	    </#list>
	    </#if>
    </tbody>
</table>

<script src="${BasePath !}/asset/js/pms/userGroup/userGroup_list.js?v=${ver !}"/></script>
</body>
</html>