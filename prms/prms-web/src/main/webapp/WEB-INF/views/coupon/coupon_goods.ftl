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
					<form id="find-page-orderby-form" action="${BasePath !}/coupon/toSelectGoods.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<input id="id" name="id" type="hidden" value="${id !}">
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>商品名称：</label> 
										<div class="div-form"><input id="name" name="name" class="form-control txt_mid input-sm" type="text" placeholder="" ">							
									</div>
								</div>
								<div class="form-td">
									<label>商品条形码：</label> 
										<div class="div-form"><input id="barCode" name="barCode" class="form-control txt_mid input-sm" type="text" placeholder="" ">							
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
						<th width="15%">商品名称</th>
				        <th width="15%">商品条形码</th>
						<th width="19%">零售价/元</th>
						<th width="15%">优惠价/元</th>
					</tr>
				</thead>
				<tbody>
					<#if commodityList?? > 
					<#list commodityList as item >
					<tr>
						<td>${item_index + 1}</td>
						<td><input type="checkbox" value="${(item.id) !}"  coupon="${(item.name)! }"  name="check"></td>
						<td>${(item.name) !}</td>						
						<td>${(item.barCode) !}</td>
						<td>${(item.retailPrice) !}</td>
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
	<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_goods.js?v=${ver !}"></script>
</body>
</html>
