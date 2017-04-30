<html>
<head>
<meta name="decorator" content="v2" />
<title>角色管理</title>
</head>
<body >

<div class="page-dialog" style="height:300px;">

	<div class="col-xs-4 full-height">
		<p>所在部门：</p>
		<ul id="officeTree" class="ztree"></ul>
	</div>
	
	<div class="col-xs-4">
		<p>待选人员：</p>
		<ul id="userTree" class="ztree"></ul>
	</div>
	
	<div class="col-xs-4">
		<p>已选人员：</p>
		<ul id="selectedTree" class="ztree"></ul>
	</div>
	
	<form action="${BasePath !}/role/saveUser.do" method="post" id="myform" class="form-inline" style="position: absolute;bottom:5px;right:5px;">
		<input type="hidden" id="roleId" name="roleId" value="${(role.id) !}" />
		<input type="hidden" id="userIds" name="userIds" value="" />		
        
    	<@permission name="pms:role:edit">
        <input type="button" class="ff-btn" value="保存" onclick="submitRoleUser();">
		</@permission>                
        <input type="button" class="ff-btn white" value="返回" onclick="onClose()">
    </form>
    
</div>

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

</body>
</html>