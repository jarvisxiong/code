<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="v2"/>
<title>用户组编辑</title>
</head>
<body>

<form action="${BasePath !}/userGroup/save.do" method="post" id="myform" name="myform" class="ff-form">
	<div id="error_con" class="tips-form">
        <ul></ul>
    </div>    
    <div class="tips-form"></div>    
	<input type="hidden" id="id" value="${(userGroup.id) !}" name="id">
	
     <div class="form-group">
         <label>上级用户组：</label>
         <div class="f7">
			<input type="hidden" id="parentId" name="parent.id" value="${(userGroup.parent.id)  !}">
          	<input class="txt_big2" type="text" id="parentName" value="${(userGroup.parent.name) !}" onfocus='showTree("选择用户组","/userGroup/ajaxList.do","parentName","parentId")'>
         	<span class="selectBtn">选</span>
         </div>
     </div>								

     <div class="form-group">
         <label><i>*</i>名称：</label>
         <div>
         	<input class="txt_big2" type="text" id="name" name="name" value="${(userGroup.name) !}" 
         	data-rule-required="true" data-rule-rangelength="[1,100]" data-msg-required="名称不能为空" data-msg-rangelength="名称长度必须小 100个字符">
         </div>
     </div>

     <div class="form-group">
         <label><i>*</i>编码：</label>
         <div>
         	<input class="txt_big2" type="text" id="code" name="code" value="${(userGroup.code) !}"
         	data-rule-required="true" data-rule-rangelength="[1,100]" data-msg-required="编码不能为空" data-msg-rangelength="名称长度必须小 100个字符">
         </div>
     </div>
 
     <div class="form-group">
         <label>类型：</label>
         <div>
         	<select class="input-select2" id="type" name="type">
			  <option value="">--请选择--</option>
                <#if userGroupTypes ??>
                <#list userGroupTypes as item >                                      
                    <option value="${(item.value) !}" <#if (userGroup.type) ??><#if (userGroup.type) == (item.value)>selected="selected"</#if></#if>>${(item.label) !}</option>
                </#list>
                </#if>
			</select> 
         </div>
     </div>
  
      <div class="form-group single-row">
          <label>分配角色/用户：</label>
          <div>                       
              <ul id="left_role_tree" class="ztree pull-left"></ul>
              <input type="hidden" id="roleIds" name="roleIds" value=""/>                 
          </div>
      </div> 
	 
     <div class="wrapper-btn">
	     <input type="submit" class="ff-btn" value="保存" />
	     <input type="button" class="ff-btn white" value="返回" onclick="isReturn('${BasePath !}/userGroup/tableList.do')" />
     </div>
     
 </form>

<script src="${BasePath !}/asset/js/pms/userGroup/userGroup_form.js?v=${ver !}"></script>
<script type="text/javascript">
    var roleNodes = ${roleNodes !};
	//var userNodes = ${userNodes !};
	
	$(function(){	

		ffzx.ui(['validate', 'select2'], function(){
        	executeValidateFrom('myform', 'loadIds');
			$(".input-select2").select2();
		});
	});
</script> 
</body>
</html>