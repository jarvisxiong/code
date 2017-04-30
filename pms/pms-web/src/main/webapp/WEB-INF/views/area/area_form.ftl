<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>区域编辑</title>
</head>
<body>

<ul class="nav nav-tabs">
	<li><a href="${BasePath !}/area/list.do">区域列表</a></li>
	<li class="active"><a href="${BasePath !}/area/form<#if area ?? && area.id??>?id=${(area.id) !}</#if>.do"><#if area ?? && area.id??>区域编辑<#else>区域添加</#if></a></li>
</ul>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addArea">
		<div class="row">
                    <div class="col-lg-10 col-md-12 col-sm-12">
                    		<div class="addForm1">
                    			<form  action="${BasePath !}/area/save.do" method="post" id="myform" name="myform">
                    				<div id="error_con" class="tips-form">
                            <ul></ul>
                        </div>
                             <div class="tips-form">
                             </div>
                    				<input type="hidden" id="id" value="${(area.id) !}" name="id">
						<div class="form-horizontal">
                                    <div class="form-group">
                                        <label>上级区域：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<#if area??>
                                        		<#if area.parent??>
										<input type="hidden" id="parentId" name="parent.id" value="${(area.parent.id)  !}">
                                          	<input class="form-control input-sm txt_big2" type="text" id="parentName" value="${(area.parent.name) !}" onfocus='showTree("选择区域","/area/ajaxList.do","parentName","parentId")'>
                                        		<#else>
										<input type="hidden" id="parentId" name="parent.id" value="0">
                                          	<input class="form-control input-sm txt_big2" type="text" id="parentName" value="上级区域" onfocus='showTree("选择区域","/area/ajaxList.do","parentName","parentId")'  data-rule-required="true" data-msg-required="请选择上级区域">
                                        		</#if>
                                        	</#if>
                                        	</div>
                                    </div>
                                </div>
						<div class="form-horizontal">
                                    <div class="form-group">
                                        <label><i>*</i>区域名称：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="name" name="name" value="${(area.name) !}" data-rule-required="true" data-rule-rangelength="[1,100]" data-msg-required="区域名称不能为空" data-msg-rangelength="名称长度必须小 100个字符">
                                        	</div>
                                    </div>
                                </div>
						<div class="form-horizontal">
                                    <div class="form-group">
                                        <label><i>*</i>区域编码：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="code" name="code" value="${(area.code) !}" data-rule-required="true" data-rule-rangelength="[1,50]" data-msg-required="区域编码不能为空" data-msg-rangelength="编码长度必须小50个字符">
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label>区域类型：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<select class="input-select2" id="type" name="type" data-rule-required="true"  data-msg-required="区域类型不能为空">
											  <option>--请选择--</option>
											  <#if areaTypes ??>
					                            <#list areaTypes as item >                                      
					                                <option value="${(item.value) !}" <#if (area.type) ??><#if (area.type) == (item.value)>selected="selected"</#if></#if>>${(item.label) !}</option>
					                            </#list>
                            				  </#if>
											</select> 
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
	                                    <div class="form-group">
	                                        <label>备注：</label>
	                                        <div class="col-md-6 col-sm-10">
	                                        <textarea rows="3" class="form-control input-sm txt_big2" name="remarks">${(area.remarks) !}</textarea>
	                                        </div>
	                                    </div>
	                                </div>	
                                <div class="form-inline">
                                    <div class="form-group btn-div">
                                        <input type="submit" class="btn btn-primary" value="保存">
                                        <input type="button" class="btn btn-default" value="返回" onclick="<#if viewtype??><#if viewtype != '0'>history.go(-1);<#else>isReturn()</#if><#else>isReturn()</#if>">
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
<script src="${BasePath !}/asset/js/pms/area/area_form.js?v=${ver !}"></script>
</body>
</html>
