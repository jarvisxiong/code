<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">

<body>
	<div id="assignRole" class="row-fluid span12">

		<div class="span4" style="width: 35%;float:left;border-right: 1px solid #A8A8A8;">
			<p>所在部门：</p>
			<div id="officeTree" class="ztree"></div>
		</div>
		<div class="span3" style="float:left;width: 30%;">
			<p>待选人员：</p>
			<div id="userTree" class="ztree"></div>
		</div>
		<div class="span3" style="width: 35%;float:left;padding-left:16px;border-left: 1px solid #A8A8A8;">
			<p>已选人员：</p>
			<div id="selectedTree" class="ztree"></div>
		</div>
		
		<form action="${BasePath !}/role/saveUser.do" method="post" id="myform">
		<input type="hidden" class="form-control" id="roleId" name="roleId" value="${(role.id) !}" />
		<input type="hidden" class="form-control" id="userIds" name="userIds" value="" />
		<div class="form-inline" style="float:none;position: absolute;margin-bottom: 10%;top: 90%;left: 75%;">
            <div class="form-group btn-div">
            	<@permission name="pms:role:edit">
                <input type="button" class="btn btn-primary" value="保存" onclick="submitRoleUser();">
				</@permission>                
                <input type="button" class="btn btn-default" value="返回" onclick="onClose()">
            </div>
        </div>
        </form>
	</div>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<script type="text/javascript" src="${BasePath !}/asset/js/pms/role/role_user_form.js?v=${ver !}"></script>
<script type="text/javascript">
		
		var officeNodes=[
		                 <#list officeList as  office>
		                     {id:"${(office.id)!}",
		    	             pId:"${(office.parent.id)!'0'}", 
		    	             name:"${(office.name)!}"},
		                 </#list>
	            ]; 
	
		var pre_selectedNodes =[
				<#list userList as  user>
					{id:"${(user.id)!}",
	   		         pId:"0",
	   		         name:"<font color='red' style='font-weight:bold;'>${(user.name)!}</font>"},
				</#list>
   		        ];
		
		var selectedNodes =[
				<#list userList as  user>
					{id:"${(user.id)!}",
			         pId:"0",
			         name:"<font color='red' style='font-weight:bold;'>${(user.name)!}</font>"},
				</#list>
		         ];
		
		var pre_ids = "${selectIds!}".split(",");
		var ids = "${selectIds!}".split(",");
		
	</script>
	<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
	<@load_content content="small"/> 
</body>

</html>