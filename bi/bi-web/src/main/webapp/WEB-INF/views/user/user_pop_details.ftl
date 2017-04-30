<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>职员搜索</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
			<div class="col-lg-10 col-md-12 col-sm-12">
				<form id="myform" method="post" action="${BasePath !}/user/popDetails.do">
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" />
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>职员姓名：</label>
								<div class="div-form">
									<input id="keyword" name="keyword" value="" class="form-control input-sm txt_mid" type="text" placeholder="请输入职员姓名">
								</div>
							</div>
							<div class="form-td">
								<div class="div-form">
									<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire btn-search" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		<!--新增1--end-->
    	<!--表格修改2--start-->
        <table class="table table-hover table-striped bor2 table-common">
			<thead>
				<tr>
					<th width="1%"></th>
					<th>姓名</th>
					<th>公司</th>
					<th>部门</th>
				</tr>
			</thead>
			<tbody id="dataList">
				<#if employees?? >
				<#list employees as employee>
				<tr name="${employee.name !}" id="${employee.id}"  phone ="${employee.phone !}" <#if employee.company??>company="${employee.company.name !}" companyId="${employee.company.id !}"</#if> <#if employee.office??>office="${employee.office.name !}" officeId="${employee.office.id !}"</#if> workNo="${employee.code !}" email="${employee.email !}" ondblclick="getSelected(this)">
					<td>${employee_index + 1}</td>
					<td>${(employee.name) !}</td>
					<td><#if employee.company??>${(employee.company.name) !}</#if></td>
					<td><#if employee.office??>${(employee.office.name) !}</#if></td>
				</tr>
				</#list> 
				</#if>
			</tbody>
		</table>
		<#include "../common/page_macro.ftl" encoding="utf-8">
        <@my_page pageObj/>
	</div>
</div>
<script type="text/javascript">
	//弹窗页面返回结果
	function getSelected(obj) {
		var art=$(parent.document.getElementById('title:popDetails')).prev();
		//父窗口调用扩展
		window.parent.afterSelectedEmployee(obj,art);
	}
</script>
</body>
</html>