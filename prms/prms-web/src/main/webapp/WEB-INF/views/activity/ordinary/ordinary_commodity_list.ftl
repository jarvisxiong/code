<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>普通活动-商品设置</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
				<form id="find-page-orderby-form" name="myform" action="${BasePath !}/ordinaryActivity/commodityList.do" method="post">
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<input id="size" type="hidden" name="size" value="${size !}" /> 
					<input id="activity.id" name="activity.id" type="hidden" value="${(activityCommodity.activity.id) !}" />
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>标题：</label> 
								<div class="div-form">
									<input id="activityTitle" name="activityTitle" value="${(activityCommodity.activityTitle) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<div class="form-td">
								<label>商品条形码：</label> 
								<div class="div-form">
									<input id="commdityBarcode" name="commodityBarcode" value="${(activityCommodity.commodityBarcode) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
						</div>
					</div>
					<div class="btn-div3">
						<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
						<a href="javascript:ordinaryCommodityForm('','${(activityCommodity.activity.id) !}')"  class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a>
						<a id="table-deleteBtn" class="btn btn-primary btn-sm" table_action="/ordinaryActivity/commodityDeletes.do"><i class="fa fa-remove"></i>&nbsp;&nbsp;批量删除</a>
						<a href="#" onClick='showMember("${(activityCommodity.activity.id) !}","ORDINARY_ACTIVITY")' class="btn btn-primary btn-sm" ><i class="fa fa-plus" ></i>&nbsp;&nbsp;批量导入</a>
						<a id="" onclick="exportData()" class="btn btn-primary btn-sm btn-inquire" ><i class="fa fa-search" ></i>&nbsp;&nbsp;批量导出</a>
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
					<th width="15px"><input type="checkbox"></th>
					<th width="1px"></th>
					<th>商品标题</th>
					<th>商品条形码</th>
					<th>优惠价</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<#if dataList?? > 
				<#list dataList as item >
				<tr id="${(item.id) !}" ondblclick="view('${(item.id) !}','${(item.activity.id) !}','view')">
					<td><input type="checkbox" value="${(item.id) !}"></td>
					<td>${item_index + 1}</td>
					<td>${(item.activityTitle) !}</td>
					<td>${(item.commodityBarcode) !}</td>
					<td>${(item.showPrice) !}</td>
					<td>
						<a href="javascript:ordinaryCommodityForm('${(item.id) !}','${(activityCommodity.activity.id) !}')">编辑</a>
						| <a name="delete_item" href="${BasePath !}/ordinaryActivity/commodityDelete.do?id=${(item.id) !}">删除</a>
					</td>
				</tr>
				</#list> 
				</#if>
			</tbody>
		</table>

		<#include "../../common/page_macro.ftl" encoding="utf-8"> 
		<@my_page pageObj/>
	</div>
</div>
	<#include "../../common/tree.ftl" encoding="utf-8">
	<#include "../../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
	<script type="text/javascript" src="${BasePath !}/asset/js/activity/panicbuy_commodity.js?v=${ver !}"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/activity/ordinary/ordinary_commodity_list.js?v=${ver !}"></script>
</body>
</html>