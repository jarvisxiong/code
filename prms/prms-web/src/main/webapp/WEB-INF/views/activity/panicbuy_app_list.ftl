<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>抢购活动管理-活动列表页面</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
			<form id="find-page-orderby-form" action="${BasePath !}/panicbuyActivity/appRecommend.do" method="post">
				<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
				<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
				<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
				<div class="btn-div3">
					<@permission name="prms:panicbuy:add">
					<a href="javascript:setAppRecommend()"  class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a>
					</@permission>
					<button id="find-page-orderby-button" style="display: none;" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
				</div>
			</form>
			</div>
		</div>
		<!--表格修改2--start-->
		<div class="tab-content">
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th class="tab-td-center">序号</th>
						<th class="tab-td-center">活动编号</th>
						<th class="tab-td-center">活动名称</th>
						<th class="tab-td-center">发布状态</th>
						<th class="tab-td-center">活动状态</th>
						<th class="tab-td-center">开始时间</th>
						<th class="tab-td-center">结束时间</th>
						<th class="tab-td-center">操作</th>
					</tr>
				</thead>
				<tbody>
					<#if dataList?? > 
					<#list dataList as item >
					<tr ondblclick="view('${(item.id) !}')">
						<td align="center">${item_index + 1}</td>
						<td align="center"><a href="${BasePath !}/panicbuyActivity/dataView.do?id=${(item.id) !}">${(item.activityNo) !}</a></td>
						<td align="center">${(item.title) !}</td>
						<td align="center"><#if (item.releaseStatus)??><#if item.releaseStatus == '0'>未发布<#else>已发布</#if></#if></td>
						<td align="center"><#if (item.activityStatus)??><#if item.activityStatus == '0'>即将开始<#elseif item.activityStatus == '1'>进行中<#else>已结束</#if></#if></td>
						<td align="center">${(item.startDate?string("yyyy-MM-dd HH:mm:ss")) !}</td>
						<td align="center">${(item.endDate?string("yyyy-MM-dd HH:mm:ss")) !}</td>
						<td align="center"><a href="javascript:isEnabled('${(item.id) !}') ">取消推荐</a></td>
					</tr>
					</#list> 
					</#if>
				</tbody>
			</table>
		</div>
		<#include "../common/page_macro.ftl" encoding="utf-8"> 
		<!-- <#include "../common/select.ftl" encoding="utf-8"> -->
		<@my_page pageObj/>
	</div>
</div>
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
<script type="text/javascript" src="${BasePath !}/asset/js/activity/panicbuy_app_list.js"></script>
</body>
</html>