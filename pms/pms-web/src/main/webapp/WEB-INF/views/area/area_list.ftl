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
				<li class="active"><a href="${BasePath !}/area/list.do">区域列表</a></li>
				<li><a href="${BasePath !}/area/form.do">区域添加</a></li>
			</ul>
			<div class="row" style="float: left; margin-left: 0px;width: 100%;">
				<div class="box-header" style="float: left;padding-left: 0px;width: 99%; ">
	 					<table class="table table-hover table-striped bor2 table-common" id="TreeTable">
	                        <thead>
	                        <tr>
	                            <th width="20%">名称</th>
	                            <th width="25%">区域编码</th>
	                            <th width="7%">区域类型</th>
	                            <th width="8%">备注</th>
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
	                            <#if areaTypes ??>
	                            <#list areaTypes as itemType >                                      
	                                <#if (item.type) == (itemType.value)>${(itemType.label) !}</#if>
	                            </#list>
	                            </#if>
	                            </#if>	
	                            </td>
	                            <td>${(item.remarks) !}</td>
	                            <td>
									<@permission name="pms:area:edit">
		                            <a href="${BasePath !}/area/form.do?id=${(item.id) !}">编辑</a> | 
		                            <a href="${BasePath !}/area/form.do?parentId=${(item.id) !}">添加下级区域</a> |
		                            </@permission>
									<@permission name="pms:area:delete">
		                            <a name="delete_item" href="${BasePath !}/area/delete.do?id=${(item.id) !}">删除</a>
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
<script src="${BasePath !}/asset/js/pms/area/area_list.js?v=${ver !}" /></script>
<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/> 
</body>

</html>
