<html>
<head>
<meta name="decorator" content="list" />
<title>优惠券列表</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/coupon/toSelectptActivity.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<input id="id" name="id" type="hidden" value="${id !}">
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>活动名称：</label> 
										<div class="div-form"><input id="title" name="title" class="form-control txt_mid input-sm" value="${(activity.title) !}" type="text" placeholder="" ">							
									</div>
								</div>
								<div class="form-td">
									<div class="btn-div3">
										<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
									</div>
								</div>
							</div>
						</div>					
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="1%"></th>
						<th width="3%"><input type="checkbox"></th>
						<th width="15%">活动编号</th>
				        <th width="15%">活动名称</th>
						<th width="9%">发布状态</th>
						<th width="9%">活动状态</th>
						<th width="15%">开始时间</th>
						<th width="15%">结束时间</th>
					</tr>
				</thead>
				<tbody>
					
				<#if activityList?? > 
				<#list activityList as item >
					<tr>
						<td>${item_index + 1}</td>
						<td><input type="checkbox" value="${(item.id) !}"   coupon="${(item.title)! }"   name="check"></td>
						
					<td align="center">${(item.activityNo) !}</td>
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
			<#include "../common/page_macro.ftl" encoding="utf-8"> 
			<@my_page pageObj/>
		</div>
	</div>
	<#include "../common/tree.ftl" encoding="utf-8">
	<#include "../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css" type="text/css">
	<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_ptActivity.js"></script>
</body>
</html>
