<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="edit"/>
<title>用户组编辑</title>
</head>
<body>
<div class="tab-content">
		<div class="tab-pane fade in active" id="addOffice">
			<div class="row">
                     <div class="col-lg-10 col-md-12 col-sm-12">
                     		<div class="addForm1">
                     			<form  action="${BasePath !}/userGroup/save.do" method="post" id="myform" name="myform">
                     				<div id="error_con" class="tips-form">
	                            <ul></ul>
	                        </div>
                              <div class="tips-form">
                              </div>
                     				<input type="hidden" id="id" value="${(userGroup.id) !}" name="id">
							<div class="width-input-div">
							<div class="form-tr">
                                     <div class=form-td">
                                         <label>上级用户组：</label>
                                         <div class="div-form">
										<input type="hidden" id="parentId" name="parent.id" value="${(userGroup.parent.id)  !}">
                                          	<input class="form-control input-sm txt_big2" type="text" id="parentName" value="${(userGroup.parent.name) !}" onfocus='showTree("选择用户组","/userGroup/ajaxList.do","parentName","parentId")'>
                                         	</div>
                                     </div>
                                 </div>									
							<div class="form-tr">
                                     <div class="form-td">
                                         <label><i>*</i>名称：</label>
                                         <div class="div-form">
                                         	<input class="form-control input-sm txt_big2" type="text" id="name" name="name" value="${(userGroup.name) !}" 
                                         	data-rule-required="true" data-rule-rangelength="[1,100]" data-msg-required="名称不能为空" data-msg-rangelength="名称长度必须小 100个字符">
                                         	</div>
                                     </div>
                                 </div>
							<div class="form-tr">
                                     <div class="form-td">
                                         <label><i>*</i>编码：</label>
                                         <div class="div-form">
                                         	<input class="form-control input-sm txt_big2" type="text" id="code" name="code" value="${(userGroup.code) !}"
                                         	data-rule-required="true" data-rule-rangelength="[1,100]" data-msg-required="编码不能为空" data-msg-rangelength="名称长度必须小 100个字符">
                                         	</div>
                                     </div>
                                 </div>
                                 <div class="form-tr">
                                     <div class="form-td">
                                         <label>类型：</label>
                                         <div class="div-form">
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
                                 </div>
                                  <div class="form-tr">
                                      <div class="form-td">
                                          <label>分配角色/用户：</label>
                                          <div class="div-form">
                                              <input type="hidden" class="form-control" id="roleIds" name="roleIds" value=""/>
                                              <div class="zTreeDemoBackground left col-md-4">
                                                  <ul id="left_role_tree" class="ztree"></ul>
                                              </div>
                                             <!-- <input type="hidden" class="form-control" id="userIds" name="userIds" value=""/>
                                              <div class="zTreeDemoBackground left col-md-4">
                                                  <ul id="left_user_tree" class="ztree"></ul>
                                              </div>-->
                                          </div>
                                      </div>
                                  </div>
                                 <div class="form-tr">
	                <div class="btn-div">
                                         <input type="submit" class="btn btn-primary" value="保存">
                                         <input type="button" class="btn btn-default" value="返回" onclick="isReturn('${BasePath !}/userGroup/tableList.do')">
                                     </div>
                                 </div>
                                 
                                 </div>
                         		</form>
                         </div>
                     </div>
                  </div>
              </div>
	</div>
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
<script src="${BasePath !}/asset/js/pms/userGroup/userGroup_form.js?v=${ver !}"></script>
<script type="text/javascript">
    var roleNodes = ${roleNodes !};
//    var userNodes = ${userNodes !};
	$(function(){
        executeValidateFrom('myform', 'loadIds');
		$(".input-select2").select2();
	});
</script> 
</body>
</html>