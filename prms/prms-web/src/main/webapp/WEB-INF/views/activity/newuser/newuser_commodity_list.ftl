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
				<form id="find-page-orderby-form" name="myform" action="${BasePath !}/newUserActivity/commoditylist.do" method="post">
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<input id="size" type="hidden" name="size" value="${size !}" /> 
					<input id="activity.id" type="hidden" name="activity.id" value="${(activityCommodity.activity.id) !}">
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
									<input id="commodityBarcode" name="commodityBarcode" value="${(activityCommodity.commodityBarcode) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
						</div>
					</div>
					<div class="btn-div3">
                                <button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
                                <a href="javascript:void(0);" onclick="setCommdity('${(activityCommodity.activity.id) !}','${(activityCommodity.activity.activityNo) !}')" class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
                                <a id="table-deleteBtn" class="btn btn-primary btn-sm" table_action="newUserActivity/commodityDeletes.do"><i class="fa fa-remove"></i>&nbsp;&nbsp;批量删除</a>
                                <a href="#" onClick='showMember("${activityCommodity.activity.id}","NEWUSER_VIP")' class="btn btn-primary btn-sm" ><i class="fa fa-plus" ></i>&nbsp;&nbsp;批量导入</a>
                                <a id="" onclick="exportData()" class="btn btn-primary btn-sm btn-inquire" ><i class="fa fa-search" ></i>&nbsp;&nbsp;批量导出</a>
                    </div>
				</form>
			</div>
		</div>

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
				<tr id="${(item.id) !}" ondblclick="view('${(item.activity.id) !}','${(item.activity.activityNo) !}','${(item.id) !}','view')">
					<td><input type="checkbox" value="${(item.id) !}" name="check"></td>
					<td>${item_index + 1}</td>
					<td align="center">${(item.activityTitle) !}</td>
					<td align="center">${(item.commodityBarcode) !}</td>
					<td align="center">${(item.showPrice)}</td>
					<td align="center">
						<a href="javascript:void(0);"  onclick="setCommdity('${(item.activity.id) !}','${(item.activity.activityNo) !}','${(item.id) !}','edit')" >编辑</a>
						<a name="delete_item" href="${BasePath !}/newUserActivity/commodityDelete.do?id=${(item.id) !}">删除</a>
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
<script type="text/javascript" src="${BasePath !}/asset/js/activity/newuser_commodity_list.js"></script>
</body>
</html>