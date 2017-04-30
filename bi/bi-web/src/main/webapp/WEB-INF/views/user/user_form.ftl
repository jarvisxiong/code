<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>新增用户</title>
</head>
<body>

    <div class="tab-content">
         <div class="tab-pane fade in active" id="addUser">
	<!--新增1--start-->
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/user/save.do" method="post">
	        <input type="hidden" id="id" name="id" value="${(user.id) !}">                    
	        <div class="addForm1">
	       <div id="error_con" class="tips-form">
	           <ul></ul>
	       </div>
	            <div class="tips-form"></div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>姓名：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" readOnly="readOnly" data-rule-required="true" data-msg-required="姓名不能为空"
	                        type="text" id="name" name="name" value="${(user.name) !}">                     
	       	 			</div>
	                </div>
	                <div class="form-td">
	                    <label><i>*</i>登录名：</label>
	                    <div class="div-form">
		                    <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="登录名不能为空"
		                    type="text" id="loginName" name="loginName" value="${(user.loginName) !}">                     
	       	 			</div>
	                </div>
	            </div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>密码：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid"
	                        type="test" id="password" name="password" value="">                     
	                    </div>
	                </div>
	                <div class="form-td">
	                    <label><i>*</i>工号：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="工号不能为空"
	                        type="text" id="workNo" name="workNo" value="${(user.workNo) !}">                     
	                    </div>
	                </div>
	            </div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>归属公司：</label>
		                  <div class="div-form">
	                          <input type="hidden" id="companyId" name="company.id" value="${(user.company.id)  !}">
	                          <input class="form-control input-sm txt_mid" value="${(user.company.name) !}" type="text" id="companyName" name="companyName" data-rule-required="true" data-msg-required="归属公司不能为空"
	                          onClick='showTree("选择公司","/user/ajaxList.do?type=1","companyName","companyId")'
	                          onkeydown='showTree("选择公司","/user/ajaxList.do?type=1","companyName","companyId")' >
		                   </div>
	                </div>
	                     <div class="form-td">
	                    <label><i>*</i>归属部门：</label>
	                    <div class="div-form">   
	                         <input type="hidden" id="officeId" name="office.id" value="${(user.office.id)  !}">
	                         <input class="form-control input-sm txt_mid" value="${(user.office.name) !}" type="text" id="officeName" name="officeName" data-rule-required="true" data-msg-required="归属部门不能为空"
	                          onClick='showOffice()'
	                          onkeydown='showOffice()'>
	                    </div>
	                </div>
	            </div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>电话：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid"
	                        type="text" id="phone" name="phone" value="${(user.phone) !}">                     
	                    </div>
	                </div>
	                <div class="form-td">
	                    <label><i>*</i>手机：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" 
	                        data-rule-required="true" data-msg-required="手机不能为空" data-rule-isMobile="true" data-msg-isMobile="请输入正确的手机号"
	                        type="text" id="mobile" name="mobile" value="${(user.mobile) !}">                     
	                    </div>
	                </div>
	            </div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>邮箱：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="邮箱不能为空" data-rule-email="true" data-msg-email="必须输入正确格式的电子邮件"
	                        type="text" id="email" name="email" value="${(user.email) !}">                     
	                    </div>
	                </div>
	                <div class="form-td">
	                    <label><i>*</i>用户类型：</label>
	                    <div class="div-form">              
                            <select class="form-control input-sm txt_mid input-select" id="userType" name="userType">
                            <option value="">--请选择--</option>
                            <#if userTypes ??>
                            <#list userTypes as item >                                      
                                <option value="${(item.value) !}" <#if (user.userType) ??><#if (user.userType) == (item.value)>selected="selected"</#if></#if>>${(item.label) !}</option>
                            </#list>
                            </#if>
                            </select> 
	                    </div>
	                </div>
	            </div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>是否启用：</label>
	                    <div class="div-form">  
	                    <div class="radio wMonospaced">
	                        <label class="radio-inline" style="width: auto;"><input name="loginFlag" type="radio" value="1" <#if (user.loginFlag) ??><#if user.loginFlag == '1'>checked="checked"</#if><#else>checked="checked"</#if>/>启用</label>
	                        <label class="radio-inline" style="width: auto;"><input name="loginFlag" type="radio" value="0" <#if (user.loginFlag) ??><#if user.loginFlag == '0'>checked="checked"</#if></#if>/>禁用</label>
	                    </div></div>
	                </div>
	                <div class="form-td">
	                    <label>备注：</label>
	                    <div class="div-form">    
	                         <input class="form-control input-sm txt_mid" type="text" id="remarks" name="remarks" value="${(user.remarks) !}">           
	                    </div>
	                </div>
	            </div>   
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>分配角色/用户组：</label>
	                        <input type="hidden" class="form-control" id="roleIds" name="roleIds" value=""/>
		                       <div class="div-form">
			                        <div class="zTreeDemoBackground left col-md-4">
			                            <ul id="left_role_tree" class="ztree"></ul>
			                        </div>
		                        </div>
	                </div>
	                        <div class="form-td">
	                        <label>&nbsp;</label>
	                         <div class="div-form">
		                         <input type="hidden" class="form-control" id="userGroupIds" name="userGroupIds" value=""/>
		                         <div class="zTreeDemoBackground left col-md-3">
		                            <ul id="left_userGroup_tree" class="ztree"></ul>
		                         </div>
	                         </div>
	               			</div>
	                </div>
	                   
	            <div class="form-tr">
	                <div class="form-group btn-div">
	                    <input type="submit" class="btn btn-primary" value="保存">
	                    <input type="button" class="btn btn-default" value="返回" onclick="isReturn('${BasePath !}/user/tableList.do')">
	                </div>
	            </div>
	        </div>
	        </form>
	    </div>
	</div>
	<!--新增1--end-->	
                                      
     </div>
 </div>
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
<script src="${BasePath !}/asset/js/pms/user/user_form.js?v=${ver !}"></script>
<script type="text/javascript">
    var userGroupNodes = ${userGroupNodes !};
    var roleNodes = ${roleNodes !};
    $(function(){
        executeValidateFrom('myform','loadIds');
        $(".input-select").select2();
    });    
    function showOffice(){
        var companyId = $("#companyId").val();
        if(companyId == ''){
            $.frontEngineDialog.executeDialogContentTime("请先选择公司",2000);
        }else{
            showTree("选择部门","/user/ajaxList.do?type=2&parent="+companyId,"officeName","officeId");
        }
    }
</script>
</body>
</html>