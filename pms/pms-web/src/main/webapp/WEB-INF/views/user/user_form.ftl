<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>新增用户</title>
</head>
<body>
	<style>
	.form-group .ztree{max-height:100%;margin-top:5px;margin-left:-5px;}

	</style>
    <form id="myform" action="${BasePath !}/user/save.do" method="post" class="ff-form">
       <input type="hidden" id="id" name="id" value="${(user.id) !}">                    
        
       <div id="error_con" class="tips-form">
           <ul></ul>
       </div>            
           
        <div class="form-group">
            <label>姓名：</label>
            <div>
                <input readOnly="readOnly" data-rule-required="true" data-msg-required="姓名不能为空"
                type="text" name="name" value="${(user.name) !}" ${((user.id)??)?string('readonly="readOnly"','id="name"')} >                     
 			</div>
        </div>
        
        <div class="form-group">
            <label><i>*</i>登录名：</label>
            <div>
                <input data-rule-required="true" data-msg-required="登录名不能为空" type="text" id="loginName" name="loginName" value="${(user.loginName) !}">                     
 			</div>
        </div>
    
        <div class="form-group">
            <label><i>*</i>密码：</label>
            <div>
                <input type="text" id="password" name="password" value="">                     
            </div>
        </div>
        
        <div class="form-group">
            <label><i>*</i>工号：</label>
            <div>
                <input data-rule-required="true" data-msg-required="工号不能为空"
                type="text" id="workNo" name="workNo" value="${(user.workNo) !}" ${((user.id)??)?string('readonly="readOnly"','')}>                     
            </div>
        </div>

        <div class="form-group col-span-2">
            <label><i>*</i>归属公司：</label>
            <div>
                  <input type="hidden" id="companyId" name="company.id" value="${(user.company.id)  !}">
                  <#if (user.id)?? >
                      <input value="${(user.company.name) !}" type="text" id="companyName" name="companyName" data-rule-required="true" data-msg-required="归属公司不能为空" disabled="disabled">
                  <#else>
                      <input value="${(user.company.name) !}" type="text" id="companyName" name="companyName" data-rule-required="true" data-msg-required="归属公司不能为空"
                      onClick='showTree("选择公司","/user/ajaxList.do?type=1","companyName","companyId")'
                      onkeydown='showTree("选择公司","/user/ajaxList.do?type=1","companyName","companyId")' >
                  </#if>
             </div>
        </div>
        
		<div class="form-group">
            <label><i>*</i>归属部门：</label>
            <div>   
                 <input type="hidden" id="officeId" name="office.id" value="${(user.office.id)  !}">
                 <#if (user.id)?? >
                     <input value="${(user.office.name) !}" type="text" id="officeName" name="officeName" data-rule-required="true" data-msg-required="归属部门不能为空" disabled="disabled">
                 <#else>
                     <input value="${(user.office.name) !}" type="text" id="officeName" name="officeName" data-rule-required="true" data-msg-required="归属部门不能为空"
                      onClick='showOffice()'
                      onkeydown='showOffice()'>
                 </#if>
            </div>
        </div>

        <div class="form-group">
            <label>电话：</label>
            <div>
                <input type="text" id="phone" name="phone" value="${(user.phone) !}" ${((user.id)??)?string('readonly="readOnly"','')}>                     
            </div>
        </div>
        
        <div class="form-group">
            <label><i>*</i>手机：</label>
            <div>
                <input data-rule-required="true" data-msg-required="手机不能为空" data-rule-isMobile="true" data-msg-isMobile="请输入正确的手机号"
                type="text" id="mobile" name="mobile" value="${(user.mobile) !}" ${((user.id)??)?string('readonly="readOnly"','')}>                     
            </div>
        </div>

        <div class="form-group">
            <label><i>*</i>邮箱：</label>
            <div>
                <input data-rule-required="true" data-msg-required="邮箱不能为空" data-rule-email="true" data-msg-email="必须输入正确格式的电子邮件"
                type="text" id="email" name="email" value="${(user.email) !}">                     
            </div>
        </div>
        
        <div class="form-group">
            <label><i>*</i>用户类型：</label>
            <div>              
                <select id="userType" name="userType">
                    <option value="">--请选择--</option>
                    <#if userTypes ??>
                    <#list userTypes as item >                                      
                        <option value="${(item.value) !}" <#if (user.userType) ??><#if (user.userType) == (item.value)>selected="selected"</#if></#if>>${(item.label) !}</option>
                    </#list>
                    </#if>
                </select> 
            </div>
        </div>

        <div class="form-group col-span-2">
            <label><i>*</i>是否启用：</label>
            <div>
                <label class="radio-inline" ><input name="loginFlag" type="radio" value="1" <#if (user.loginFlag) ??><#if user.loginFlag == '1'>checked="checked"</#if><#else>checked="checked"</#if>/>启用</label>
                <label class="radio-inline" ><input name="loginFlag" type="radio" value="0" <#if (user.loginFlag) ??><#if user.loginFlag == '0'>checked="checked"</#if></#if>/>禁用</label>
            </div>
        </div>

        <div class="form-group col-span-2">
            <label>供应商：</label>
            <div class="f7">
                <input type="hidden" id="vendorId" name="vendorId" value="${(user.vendorId)  !}">
                <input type="hidden" id="vendorCode" name="vendorCode" value="${(user.vendorCode)  !}">
                <input readOnly="readOnly" type="text" id="vendorName" name="vendorName" value="${(vendor.name) !}">
                <span class="selectBtn">选</span>
 			</div>
        </div>
        
        <div class="form-group col-span-2">
            <label>备注：</label>
            <div>    
                 <input type="text" id="remarks" name="remarks" value="${(user.remarks) !}">           
            </div>
        </div>

		<div class="clearfix"></div>
		
        <div class="form-group single-row">
           <label>分配公司：</label>
           <input type="hidden" id="userOfficeIds" name="userOfficeIds" value=""/>
           <div>
                <div>
                    <label class="radio-inline"><input name="allOfficeFlag" type="radio" value="1" onclick="changeAllOfficeFlag('1')" ${((user.allOfficeFlag)?default('1')== '1')?string('checked','')}/>所有公司</label>
                    <label class="radio-inline"><input name="allOfficeFlag" type="radio" value="0" onclick="changeAllOfficeFlag('0')" ${((user.allOfficeFlag)?default('1')== '0')?string('checked','')}/>部分公司</label>
                </div>
                <ul id="left_office_tree" class="ztree" style="${((user.allOfficeFlag)?default('1')!='0')?string('display:none;','')}overflow: auto;"></ul>
            </div>
        </div>
        
        <div class="form-group single-row">
        	<label>分配地址：</label>
           <input type="hidden" id="userAddressIds" name="userAddressIds" value=""/>
           <div>
                <div>
                    <label class="radio-inline"><input name="allAddressFlag" type="radio" value="1" onclick="changeAllAddressFlag('1')" ${((user.allAddressFlag)?default('1')== '1')?string('checked','')}/>所有地址</label>
                    <label class="radio-inline"><input name="allAddressFlag" type="radio" value="0" onclick="changeAllAddressFlag('0')" ${((user.allAddressFlag)?default('1')== '0')?string('checked','')}/>部分地址</label>
                </div>
                <ul id="left_address_tree" class="ztree" style="${((user.allAddressFlag)?default('1')!='0')?string('display:none;','')}overflow: auto;"></ul>
            </div>
        </div>
        
        <div class="form-group single-row">
        	<label>分配仓库：</label>
           <input type="hidden" class="form-control" id="userWarehouseIds" name="userWarehouseIds" value=""/>
           <div>
                <div>
                    <label class="radio-inline"><input name="allWarehouseFlag" type="radio" value="1" onclick="changeAllWarehouseFlag('1')" ${((user.allWarehouseFlag)?default('1')== '1')?string('checked','')}/>所有仓库</label>
                    <label class="radio-inline"><input name="allWarehouseFlag" type="radio" value="0" onclick="changeAllWarehouseFlag('0')" ${((user.allWarehouseFlag)?default('1')== '0')?string('checked','')}/>部分仓库</label>
                </div>            
                <ul id="left_warehouse_tree" class="ztree" style="${((user.allWarehouseFlag)?default('1')!='0')?string('display:none;','')}overflow: auto;"></ul>
            </div>
        </div>
        
        <div class="form-group single-row">
           <label>分配权限：</label>
           <input type="hidden" id="userMenuIds" name="userMenuIds" value=""/>
           <div>
               <ul id="left_menu_tree" class="ztree" style="padding-top:0;"></ul>
           </div>
        </div>
        
        <div class="form-group single-row">
           <label>分配角色：</label>
           <input type="hidden" id="roleIds" name="roleIds" value=""/>
           <div>           
                <ul id="left_role_tree" class="ztree" style="padding-top:0;"></ul>
           </div>
        </div>
		 
		<div class="form-group single-row">
            <label>分配用户组：</label>
            <div>
              <input type="hidden" id="userGroupIds" name="userGroupIds" value=""/>              
              <ul id="left_userGroup_tree" class="ztree" style="padding-top:0;"></ul>
            </div>
        </div>
    
        <div class="wrapper-btn">
            <input type="submit" class="ff-btn" value="保存">
            <input type="button" class="ff-btn white btn-close-iframeFullPage" value="返回">
        </div>           

   </form>

<script src="${BasePath !}/asset/js/pms/user/user_form.js?v=${ver !}"></script>
<script type="text/javascript">
    var userGroupNodes = ${userGroupNodes !};
    var roleNodes = ${roleNodes !};
    var OfficeNodes = ${OfficeNodes !};
    var addressNodes = ${addressNodes !};
    var warehouseNodes = ${warehouseNodes !};
    var menuNodes = ${menuTreeList !};
    $(function(){
    
    	ffzx.ui(['validate'], function(){
	        executeValidateFrom('myform','loadIds', '', function(){
				parent.reloadData('user_list');			
				parent.closeIframeFullPage('user_list');
			});
		});
        
		ffzx.ui(['select2'], function(){
			$(".input-select").select2();
		});		
    });
     
    function showOffice(){
        var companyId = $("#companyId").val();
        
        ffzx.ui(['dialog'], function(){
        
	        if(companyId == ''){
	            $.frontEngineDialog.executeDialogContentTime("请先选择公司",2000);
	        }else{
	            showTree("选择部门","/user/ajaxList.do?type=2&parent="+companyId,"officeName","officeId");
	        }
        });
    }
</script>
</body>
</html>