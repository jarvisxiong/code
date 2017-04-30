<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>选择抢购活动</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
				<form id="find-page-orderby-form" action="${BasePath !}/panicbuyActivity/findActivityList.do" method="post">
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>活动名称：</label> 
								<div class="div-form">
									<input id="title" name="title" value="${(activity.title) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--表格修改2--start-->
		<div class="tab-content">
		<table class="table table-hover table-striped bor2 table-common">
			<thead>
				<tr>
					<th><input type="checkbox"></th>
					<th class="tab-td-center">序号</th>
					<th class="tab-td-center">活动编号</th>
					<th class="tab-td-center">活动名称</th>
					<th class="tab-td-center">发布状态</th>
					<th class="tab-td-center">活动状态</th>
					<th class="tab-td-center">开始时间</th>
					<th class="tab-td-center">结束时间</th>
				</tr>
			</thead>
			<tbody id="activityList">
				<#if dataList?? > 
				<#list dataList as item >
				<tr ondblclick="view('${(item.id) !}')">
					<td align="center"><input type="checkbox" name="check">
						<input type="hidden" value="${(item.id) !}" /> 
					</td>
					<td align="center">${item_index + 1}</td>
					<td align="center"><a href="${BasePath !}/panicbuyActivity/dataView.do?id=${(item.id) !}">${(item.activityNo) !}</a></td>
					<td align="center">${(item.title) !}</td>
					<td align="center"><#if (item.releaseStatus)??><#if item.releaseStatus == '0'>未发布<#else>已发布</#if></#if></td>
					<td align="center"><#if (item.activityStatus)??><#if item.activityStatus == '0'>即将开始<#elseif item.activityStatus == '1'>进行中<#else>已结束</#if></#if></td>
					<td align="center">${(item.startDate?string("yyyy-MM-dd HH:mm:ss")) !}</td>
					<td align="center">${(item.endDate?string("yyyy-MM-dd HH:mm:ss")) !}</td>
				</tr>
				</#list> 
				</#if>
			</tbody>
		</table>
		</div>
		<#include "../common/page_macro.ftl" encoding="utf-8"> 
		<@my_page pageObj/>
	</div>
</div>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/panicbuy_app_form.js"></script>
</body>
</html>