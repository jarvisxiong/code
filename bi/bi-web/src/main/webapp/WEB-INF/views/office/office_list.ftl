<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">

<body style="overflow: hidden;"> 
<div class="row">
	<div class="col-md-12">
		<div class="zTreeDemoBackground left ztree_table_zt" >
			<ul id="left_tree" class="ztree"></ul>
		</div>
		<div id="openClose" class="closeac" ></div>
		<div class="box-body ztree_table" >
			<ul class="nav nav-tabs" style="padding-left: 1%;">
				<li class="active"><a href="${BasePath !}/office/list.do">机构列表</a></li>
				<li><a href="${BasePath !}/office/form.do">机构添加</a></li>
			</ul>
			<div class="row" style="float: left; margin-left: 0px;width: 100%;">
				
				<div class="box-header" style="float: left;padding-left: 0px;width: 99%; ">
	 					<table class="table table-hover table-striped bor2 table-common" id="TreeTable">
	                        <thead>
	                        <tr>
	                            <th width="20%">名称</th>
	                            <th width="25%">归属区域</th>
	                            <th width="7%">机构编码</th>
	                            <th width="8%">机构类型</th>
	                            <th>操作</th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        <#if treeTable?? >
	                        <#list treeTable as item >
	                    	<tr id="${(item.id) !}" pId="${(item.pId) !}">
	                            <td>${(item.name) !}</td>
	                            <td>${(item.area) !}</td>
	                            <td>${(item.code) !}</td>
	                            <td>
	                            <#if (item.type) ??>
	                            <#if officeTypes ??>
	                            <#list officeTypes as itemType >                                      
	                                <#if (item.type) == (itemType.value)>${(itemType.label) !}</#if>
	                            </#list>
	                            </#if>
	                            </#if>	                            	
	                            </td>
	                            <td>
		                            <@permission name="pms:office:edit">
		                            <a href="${BasePath !}/office/form.do?id=${(item.id) !}">编辑</a> |
		                            <a href="${BasePath !}/office/form.do?parentId=${(item.id) !}">添加下级机构</a> |
		                            </@permission> 
		                            <@permission name="pms:office:delete">
		                            <a name="delete_item" href="${BasePath !}/office/delete.do?id=${(item.id) !}">删除</a>
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
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<link rel="stylesheet"	href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
<script type="text/javascript">
	var zNodes = ${result};
</script>
<script src="${BasePath !}/asset/js/pms/office/office_list.js?v=${ver !}" /></script>
<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/> 
</body>

</html>