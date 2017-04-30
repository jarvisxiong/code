<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>机构编辑</title>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${BasePath !}/office/list.do">机构列表</a></li>
	<li class="active"><a href="${BasePath !}/office/form<#if office ?? && office.id??>?id=${(office.id) !}</#if>.do">机构<#if office ?? && office.id??>编辑<#else>添加</#if></a></li>
</ul>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addOffice">
		<div class="row">
                    <div class="col-lg-10 col-md-12 col-sm-12">
                    		<div class="addForm1">
                    			<form  action="${BasePath !}/office/save.do" method="post" id="myform" name="myform">
                    				<div id="error_con" class="tips-form">
                            <ul></ul>
                        </div>
                             <div class="tips-form">
                             </div>
                    				<input type="hidden" id="id" value="${(office.id) !}" name="id">
						<div class="form-horizontal">
                                    <div class="form-group">
                                        <label>上级机构：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<#if office??>
                                        		<#if office.parent??>
										<input type="hidden" id="parentId" name="parent.id" value="${(office.parent.id)  !}">
                                          	<input class="form-control input-sm txt_big2" type="text" id="parentName" value="${(office.parent.name) !}" onfocus='showTree("选择机构","/office/ajaxList.do","parentName","parentId")'>
                                        		<#else>
										<input type="hidden" id="parentId" name="parent.id" value="0">
                                          	<input class="form-control input-sm txt_big2" type="text" id="parentName" value="上级机构" onfocus='showTree("选择机构","/office/ajaxList.do","parentName","parentId")'  data-rule-required="true" data-msg-required="请选择上级机构">
                                        		</#if>
                                        	</#if>
                                        	</div>
                                    </div>
                                </div>
						<div class="form-horizontal">
                                    <div class="form-group">
                                        <label>归属区域：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<#if office??>
                                        		<#if office.area??>
										<input type="hidden" id="parentAreaId" name="area.id" value="${(office.area.id)  !}">
                                          	<input class="form-control input-sm txt_big2" type="text" id="parentAreaName" value="${(office.area.name) !}" onfocus='showTree("选择区域","/area/ajaxList.do","parentAreaName","parentAreaId")'>
                                        		<#else>
										<input type="hidden" id="parentAreaId" name="area.id" value="0">
                                          	<input class="form-control input-sm txt_big2" type="text" id="parentAreaName" value="归属区域" onfocus='showTree("选择区域","/area/ajaxList.do","parentAreaName","parentAreaId")'  data-rule-required="true" data-msg-required="请选择区域">
                                        		</#if>
                                        	</#if>
                                        	</div>
                                    </div>
                                </div>
						<div class="form-horizontal">
                                    <div class="form-group">
                                        <label><i>*</i>机构名称：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="name" name="name" value="${(office.name) !}" data-rule-required="true" data-rule-rangelength="[1,100]" data-msg-required="机构名称不能为空" data-msg-rangelength="名称长度必须小 100个字符">
                                        	</div>
                                    </div>
                                </div>
						<div class="form-horizontal">
                                    <div class="form-group">
                                        <label><i>*</i>机构编码：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="code" name="code" value="${(office.code) !}" data-rule-required="true" data-rule-rangelength="[1,50]" data-msg-required="机构编码不能为空" data-msg-rangelength="编码长度必须小 50个字符">
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label>机构类型：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<select class="input-select2" id="type" name="type" data-rule-required="true"  data-msg-required="机构类型不能为空">
											  <option>--请选择--</option>
											  <#if officeTypes ??>
					                            <#list officeTypes as item >                                      
					                                <option value="${(item.value) !}" <#if (office.type) ??><#if (office.type) == (item.value)>selected="selected"</#if></#if>>${(item.label) !}</option>
					                            </#list>
                            				  </#if>
											</select> 
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label>机构级别：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        <select class="input-select2" id="grade" name="grade" data-rule-required="true"  data-msg-required="机构级别不能为空">
								  		<option>--请选择--</option>
											  <#if officeGrades ??>
					                            <#list officeGrades as item >                                      
					                                <option value="${(item.value) !}" <#if (office.grade) ??><#if (office.grade) == (item.value)>selected="selected"</#if></#if>>${(item.label) !}</option>
					                            </#list>
                            				  </#if>								  		
										</select> 
                                        </div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label>联系地址：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="address" name="address" value="${(office.address) !}">
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label>邮政编码：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="zipCode" name="zipCode" value="${(office.zipCode) !}">
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label>负责人：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="master" name="master" value="${(office.master) !}">
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label>电话：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="phone" name="phone" value="${(office.phone) !}">
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label>传真：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="address" name="fax" value="${(office.fax) !}">
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <label>邮箱：</label>
                                        <div class="col-lg-6 col-md-7 col-sm-10">
                                        	<input class="form-control input-sm txt_big2" type="text" id="email" name="email" value="${(office.email) !}">
                                        	</div>
                                    </div>
                                </div>
                                <div class="form-horizontal">
	                                    <div class="form-group">
	                                        <label>备注：</label>
	                                        <div class="col-md-6 col-sm-10">
	                                        <textarea rows="3" class="form-control input-sm txt_big2" name="remarks">${(office.remarks) !}</textarea>
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
<script src="${BasePath !}/asset/js/pms/office/office_form.js?v=${ver !}"></script>
</body>
</html>
