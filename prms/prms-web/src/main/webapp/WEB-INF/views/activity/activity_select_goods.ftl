<html>
<head>
<meta name="decorator" content="list" />
<title>商品列表</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/presellActivity/toCommdity.do" method="post">
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>商品名称：</label> 
										<div class="div-form"><input id="name" name="name" value="${(commodity.name) !}" class="form-control txt_mid input-sm" type="text" placeholder="" ">							
									</div>
								</div>
								<div class="form-td">
									<label>商品条形码：</label> 
										<div class="div-form"><input id="barCode" name="barCode" value="${(commodity.barCode) !}" class="form-control txt_mid input-sm" type="text" placeholder="" ">							
									</div>
								</div>
								<div class="form-td">
									<div class="btn-div3">
										<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
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
						<th width="15%">商品标题</th>
				        <th width="15%">商品条形码</th>
						<th width="15%">优惠价/元</th>
					</tr>
				</thead>
				<tbody>
					<#if commodityList?? > 
					<#list commodityList as item >
					<tr id="${(item.id) !}" ondblclick="getUpdata('${(item.id) !}','${(item.title) !}','${(item.barCode) !}','${(item.preferentialPrice) !}','${(item.code) !}','${(item.buyType) !}')" >
						<td>${item_index + 1}</td>
						<td>${(item.title) !}</td>						
						<td>${(item.barCode) !}</td>
						<td>${(item.preferentialPrice) !}</td>			 	
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
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
	<script type="text/javascript" src="${BasePath !}/asset/js/activity/activity_select_goods.js?v=${ver !}"></script>
</body>
</html>
