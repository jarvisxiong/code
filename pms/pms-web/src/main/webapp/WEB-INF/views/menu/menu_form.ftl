<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>菜单编辑</title>
</head>
<body>

<form  action="${BasePath !}/menu/save.do" method="post" id="myform" name="myform" class="ff-form">
	<div id="error_con" class="tips-form">
        <ul></ul>
    </div>
	<input type="hidden" id="id" value="${(menu.id) !}" name="id">
	<input type="hidden" id="parentId" name="parent.id" value="${pMenu.id}">

    <div class="form-group">
        <label>上级菜单：</label>
        <div>
        	<input type="text" id="parentName" value="${(pMenu.name) !}" onfocus='showTree("选择仓库","/menu/ajaxgetMenuList.do","parentName","parentId")' />
        </div>
    </div>

    <div class="form-group">
        <label><i>*</i>菜单名称：</label>
        <div>
        	<input type="text" id="name" name="name" value="${(menu.name) !}" data-rule-required="true" data-rule-rangelength="[1,32]" data-msg-required="菜单名称不能为空" data-msg-rangelength="菜单名称长度必须小 32个字符">
        </div>        	
    </div>

    <div class="form-group col-span-2">
        <label>菜单地址：</label>
        <div>
	        <input type="text" id="href" name="href" value="${(menu.href) !}" data-rule-rangelength="[1,2000]" data-msg-rangelength="菜单地址长度必须小 2000个字符" placeholder="点击菜单跳转的页面" />	        
        </div>
    </div>

    <div class="form-group col-span-2">
        <label>权限标识：</label>
        <div>
	        <input type="text" id="permission" name="permission" value="${(menu.permission) !}" data-rule-rangelength="[0,2000]"  data-msg-rangelength="权限标识长度必须小 200个字符" placeholder="控制器中定义的权限标识，格式如:pms:menu:view">
        </div>
    </div>

    <div class="form-group col-span-2">
        <label>是否显示：</label>
        <div>
	        <#if menu ??>
	         <label class="radio-inline"><input type="radio" id="isShow"
			<#if menu.isShow == '1'>
				checked="checked"
			</#if>
			name="isShow" value="1" /> 显示</label>
			<label class="radio-inline"><input type="radio" id="isShow"
			<#if menu.isShow == '0'>
				checked="checked"
			</#if>
			name="isShow" value="0" />  隐藏</label>
	        
	        <#else>
	        		<label class="radio-inline"><input type="radio" id="isShow" name="isShow" value="1" checked="checked"/>显示</label>
	        		<label class="radio-inline"><input type="radio" id="isShow" name="isShow" value="0"/>不显示</label>
	        </#if>
	        <span class="help-block">显示:表示为菜单,隐藏:表示为功能</span>
        </div>
    </div>

    <div class="form-group col-span-2">
        <label>排序：</label>
        <div>
	        <input type="text" id="sort" name="sort" value="${(menu.sort) !"99"}" data-rule-required="true" data-msg-required="排序不能为空" data-rule-range="[1,999999999]" data-msg-range="排序必须在1-999999999之间" placeholder="排列顺序，升序" />
        </div>
    </div>

    <div class="form-group col-span-2">
        <label>所属子系统：</label>
        <div>
	        <select class="input-select2" id="subSystemCode" name="subSystemCode">
			<option>--请选择--</option>
			 <#if subSystemConfig ??>
	            <#list subSystemConfig as item >                                      
	                <option value="${(item.subSystemCode) !}" <#if (menu.subSystemCode) ??><#if (menu.subSystemCode) == (item.subSystemCode)> selected="selected"</#if></#if>>${(item.subSystemName) !}</option>
	            </#list>
			  </#if>
			</select> 
	        <span class="help-block">所属子系统</span>
        </div>
    </div>

    <div class="form-group single-row">
        <label>备注：</label>
        <div>
        	<textarea name="remarks">${(menu.remarks) !}</textarea>
        </div>
    </div>

    <div class="wrapper-btn">
        <input type="submit" class="ff-btn" value="保存">
        <input type="button" class="ff-btn white" value="返回" onclick="isReturn('${BasePath !}/menu/tableList.do')">
    </div>

</form>
	                        		
<script src="${BasePath !}/asset/js/pms/menu/menu_form.js?v=${ver !}"></script>
<script type="text/javascript">
	$(function(){
	
		ffzx.ui(['validate', 'select2'], function(){
			executeValidateFrom('myform');
			$(".input-select2").select2();
		});
	});
</script> 

</body>
</html>
