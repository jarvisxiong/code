<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>菜单编辑</title>
</head>
<body>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="addMenu">
			<div class="row">
	                    <div class="col-lg-10 col-md-12 col-sm-12">
	                    		<div class="addForm1">
	                    			<form  action="${BasePath !}/menu/save.do" method="post" id="myform" name="myform">
	                    				<div id="error_con" class="tips-form">
	                            <ul></ul>
	                        </div>
	                             <div class="tips-form">
	                             </div>
	                    				<input type="hidden" id="id" value="${(menu.id) !}" name="id">
							<input type="hidden" id="parentId" name="parent.id" value="${pMenu.id}">
							<div class="width-input-div">
	                    				<div class="form-tr">
	                                    <div class="form-td">
	                                        <label>上级菜单：</label>
	                                        <div class="div-form">
	                                        	<input class="form-control input-sm txt_big2" type="text" id="parentName" value="${(pMenu.name) !}" onfocus='showTree("选择仓库","/menu/ajaxgetMenuList.do","parentName","parentId")'>
	                                        	</div>
	                                    </div>
	                                </div>
	                    				<div class="form-tr">
	                                    <div class="form-td">
	                                        <label><i>*</i>菜单名称：</label>
	                                        <div class="div-form helpInlineDiv">
	                                        	<input class="form-control input-sm txt_big2" type="text" id="name" name="name" value="${(menu.name) !}" data-rule-required="true" data-rule-rangelength="[1,32]" data-msg-required="菜单名称不能为空" data-msg-rangelength="菜单名称长度必须小 32个字符">
	                                        	</div>
	                                        	
	                                    </div>
	                                </div>
	                                <div class="form-tr">
	                                    <div class="form-td">
	                                        <label>菜单地址：</label>
	                                        <div class="div-form">
	                                        <div class="helpInlineDiv">
	                                        <input class="form-control input-sm txt_big2" type="text" id="href" name="href" value="${(menu.href) !}" data-rule-rangelength="[1,2000]" data-msg-rangelength="菜单地址长度必须小 2000个字符">
	                                        <span style="color:#aaa; display:block;">点击菜单跳转的页面</span>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="form-tr">
	                                    <div class="form-td">
	                                        <label>权限标识：</label>
	                                        <div class="div-form">
	                                        <div class="helpInlineDiv">
	                                        <input class="form-control input-sm txt_big2" type="text" id="permission" name="permission" value="${(menu.permission) !}" data-rule-rangelength="[0,2000]"  data-msg-rangelength="权限标识长度必须小 200个字符">
	                                        <span style="color:#aaa; display:block;">控制器中定义的权限标识，格式如:pms:menu:view</span>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="form-tr">
	                                    <div class="form-td">
	                                        <label>是否显示：</label>
	                                        <div class="div-form">
	                                        <div class="helpInlineDiv">
	                                        <#if menu ??>
	                                         <input type="radio" id="isShow"
										<#if menu.isShow == '1'>
											checked="checked"
										</#if>
										name="isShow" value="1" /> 显示
										<input type="radio" id="isShow"
										<#if menu.isShow == '0'>
											checked="checked"
										</#if>
										name="isShow" value="0" />  隐藏
	                                        
	                                        <#else>
	                                        		<input type="radio" id="isShow" name="isShow" value="1" checked="checked"/>显示
	                                        		<input type="radio" id="isShow" name="isShow" value="0"/>不显示
	                                        </#if>
	                                        <span style="color:#aaa; display:block;">显示:表示为菜单,隐藏:表示为功能</span>
	                                        </div>
	                                    </div>
	                                    </div>
	                                </div>
	                                <div class="form-tr">
	                                    <div class="form-td">
	                                        <label>排序：</label>
	                                        <div class="div-form">
	                                        <div class="helpInlineDiv">
	                                        <input class="form-control input-sm txt_big2" type="text" id="sort" name="sort" value="${(menu.sort) !"99"}" data-rule-required="true" data-msg-required="排序不能为空" data-rule-range="[1,999999999]" data-msg-range="排序必须在1-999999999之间"/>
	                                        <span style="color:#aaa; display:block;">排列顺序，升序</span>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="form-tr">
	                                    <div class="form-td">
	                                        <label>所属子系统：</label>
	                                        <div class="div-form">
	                                        <div class="helpInlineDiv">
	                                        <select class="input-select2" id="subSystemCode" name="subSystemCode">
											<option>--请选择--</option>
											 <#if subSystemConfig ??>
					                            <#list subSystemConfig as item >                                      
					                                <option value="${(item.subSystemCode) !}" <#if (menu.subSystemCode) ??><#if (menu.subSystemCode) == (item.subSystemCode)> selected="selected"</#if></#if>>${(item.subSystemName) !}</option>
					                            </#list>
                            				  </#if>
											</select> 
	                                        <span style="color:#aaa;">所属子系统</span>
	                                        </div>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="form-tr">
	                                    <div class="form-td">
	                                        <label>备注：</label>
	                                        <div class="div-form">
	                                        <textarea rows="3" class="form-control input-sm txt_big2" name="remarks">${(menu.remarks) !}</textarea>
	                                        </div>
	                                    </div>
	                                </div>
	                                </di>
	                                <div class="form-tr">
	                                    <div class="form-group btn-div">
	                                        <input type="submit" class="btn btn-primary" value="保存">
	                                        <input type="button" class="btn btn-default" value="返回" onclick="isReturn('${BasePath !}/menu/tableList.do')">
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
<link rel="stylesheet"	href="${BasePath !}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
<script type="text/javascript">
	$(function(){
		executeValidateFrom('myform');
		$(".input-select2").select2();
	});
</script> 
<script src="${BasePath !}/asset/js/pms/menu/menu_form.js?v=${ver !}"></script>
</body>
</html>
