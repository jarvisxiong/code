<html>
<head>
<meta name="decorator" content="list" />
<title>主商品列表</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/activityGive/findMainCommodity.do" method="post">
					
					<input  id="find-page-index-noidList"  name="noidList" type="hidden" value="${noidList!""}"  />
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>商品名称：</label> 
									<div class="div-form"><input id="name" name="name" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(name) !}"></div>
								</div>
								<div class="form-td">
									<label>商品编码：</label> 
									<div class="div-form"><input id="code" name="code" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(code) !}"></div>
								</div>
								<div class="form-td">
									<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="5%">序号</th>
				        <th width="30%">商品名称</th>
						<th width="30%">商品编码</th>
						<th width="30%">优惠价</th>
					</tr>
				</thead>
				<tbody>
					<#if list?? > 
					<#list list as item >
					<tr ondblclick="getMainSuper('${(item.id) !}','${(item.name) !}','${(item.barCode) !}','${(item.preferentialPrice) !}')">
						<td>${item_index + 1}</td>
						<td>${(item.name) !}</td>
						<td>${(item.code) !}</td>
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
	<script type="text/javascript" src="${BasePath !}/asset/js/activityGive/findMainCommodity.js?v=${ver !}"></script>
	<script type="text/javascript">
	 
	</script>
</body>
</html>
