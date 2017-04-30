<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>演示页面</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
				<form id="find-page-orderby-form" action="${BasePath !}/help/test/listDemo.do" method="post">
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>用户名：</label> 
								<div class="div-form">
								<input id="name" name="name" value="${(testPage.name) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<div class="form-td">
								<label>年龄：</label> 
								<div class="div-form">
								<input id="beginAgeStr" name="beginAgeStr" value="${(testPage.beginAgeStr) !}" class="form-control txt_mid2 input-sm" type="text">-
								<input id="endAgeStr" name="endAgeStr" value="${(testPage.endAgeStr) !}" class="form-control txt_mid2 input-sm" type="text">
							</div>
							</div>
							<div class="form-td">
								<label>邮箱：</label> 
								<div class="div-form"><div class="email-input"><@spring.formInput "testPage.email" />	</div></div>
							</div>
							<div class="form-td">
								<label>创建人：</label> 
								<div class="div-form">
								<input id="createdBy" name="createBy.name" value="${(testPage.createBy.name) !}" class="form-control txt_mid input-sm" type="text">
							     </div></div>
							<div class="form-td">
								<label>创建时间：</label> 
                                    <div class="div-form"><input name="beginCreateDateStr" id="beginCreateDateStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endCreateDateStr\')}'})"
                                        value="${(testPage.beginCreateDateStr) !}">
                                    -
                                    <input name="endCreateDateStr" id="endCreateDateStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginCreateDateStr\')}'})"
                                        value="${(testPage.endCreateDateStr) !}">
                               </div> </div>
                            </div>
                            <div class="form-tr">
                                <div class="form-td">
                                    <label>最后修改人：</label> 
                                    <div class="div-form"><input id="lastUpdatedBy" name="lastUpdateBy.name" value="${(testPage.lastUpdateBy.name) !}" class="form-control txt_mid input-sm" type="text">
                               </div> </div>
                                <div class="form-td">
                                    <label>最后修改时间：</label> 
                                   <div class="div-form"> <input name="beginLastUpdateDateStr" id="beginLastUpdateDateStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endLastUpdateDateStr\')}'})"
                                        value="${(testPage.beginLastUpdateDateStr) !}">
                                    -
                                    <input name="endLastUpdateDateStr" id="endLastUpdateDateStr" class="form-control txt_mid input-sm"
                                    onfocus="WdatePicker({maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginLastUpdateDateStr\')}'})"
                                        value="${(testPage.endLastUpdateDateStr) !}">								
							</div></div>
							
						</div>
					</div>
					<div class="btn-div3">
						<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
						<button id="table-deleteBtn" class="btn btn-primary btn-sm" table_action="/help/test/deletes.do"><i class="fa fa-remove"></i>&nbsp;&nbsp;删除</button>
						<a href="${BasePath !}/help/test/form.do"  class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
					</div>
				</form>
			</div>
		</div>
		
		<#if message?? >
		<div id="messageBox" class="alert alert-success alert-dismissable col-md-12" message="${message !}">
			<button type="button" class="close" data-dismiss="alert" 
		      aria-hidden="true">
		      &times;
		   </button>
			${message !}
		</div>
		</#if>
		
		<!--表格按钮--start-->
		
		<!--表格按钮--end-->

		<!--表格修改2--start-->
		<table class="table table-hover table-striped bor2 table-common">
			<thead>
				<tr>
					<th width="1px"></th>
					<th width="15px"><input type="checkbox"></th>
					<th>用户名</th>
					<th>年龄</th>
					<th>邮件</th>
					<th>备注</th>
					<th class="tab-td-center">创建人</th>
					<th>创建时间</th>
					<th class="tab-td-center">最后修改人</th>
					<th>最后修改时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<#if testList?? > 
				<#list testList as item >
				<tr>
					<td>${item_index + 1}</td>
					<td><input type="checkbox" value="${(item.id) !}"></td>
					<td>${(item.name) !}</td>
					<td>${(item.age) !}</td>
					<td>${(item.email) !}</td>
					<td>${(item.remarks) !}</td>
					<td align="center">${(item.createBy.name) !}</td>
					<td>${(item.createDate?string("yyyy-MM-dd HH:mm")) !}</td>
					<td align="center">${(item.lastUpdateBy.name) !}</td>
					<td>${(item.lastUpdateDate?string("yyyy-MM-dd")) !}</td>
					<td>
						<@permission name="pms:demo:viewTest">
							<a href="${BasePath !}/help/test/form.do?id=${(item.id) !}">查看</a>
						</@permission>
						<a href="${BasePath !}/help/test/form.do?id=${(item.id) !}">编辑</a>
						| <a name="delete_item" href="${BasePath !}/help/test/delete.do?id=${(item.id) !}">删除</a>
					</td>
				</tr>
				</#list> 
				</#if>
			</tbody>
		</table>

		<#include "../common/page_macro.ftl" encoding="utf-8"> 
		<@my_page pageObj/>
	</div>
</div>
</body>
</html>