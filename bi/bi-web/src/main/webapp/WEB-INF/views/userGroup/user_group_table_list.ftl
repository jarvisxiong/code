<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<head>
<#include "../common/css.ftl" encoding="utf-8">
</head>
<body style="overflow: hidden;">
<div class="row">
    <div class="col-md-12">
        <div class="box-body">
       
            <div class="btn-div3"><a href="${BasePath !}/userGroup/form.do" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a></div>
				<div class="row" style="float: left; margin-left: 0px;width: 100%;">
					
					<div class="box-header" style="float: left;padding-left: 0px;width: 99%; ">
                        <table class="table table-hover table-striped bor2 table-common" id="TreeTable">
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
                    </div>  
                </div>
            </div>
			
        </div>
    </div>
</div>
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css">
<script src="${BasePath !}/asset/js/pms/userGroup/userGroup_list.js?v=${ver !}"/></script>
<script type="text/javascript">
</script>
<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/> 
</body>

</html>
