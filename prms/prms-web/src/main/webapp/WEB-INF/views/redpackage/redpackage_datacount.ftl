<html>
<head>
<meta name="decorator" content="list" />
<title>统计</title>
</head>
<body>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="8%">发放数量</th>
				        <th width="8%">领取张数</th>
						<th width="10%">领取次数</th>
						<th width="8%">使用人数</th>
						<th width="8%">使用次数</th>
						<th width="8%">使用率</th>
						<th width="8%">使用红包总金额</th>
						<th width="8%">使用红包总订单流水</th>
						<th width="8%">使用红包客单价</th>
					</tr>
				</thead>
				<tbody>
					<#if redpackageCount?? > 
					<#list redpackageCount as item>
					<tr >
						<td>${(item.grantNum) !}</td>
						<td>${(item.receiveNum) !}</td>
						<td>${(item.receiveTime) !}</td>
						<td>${(item.usePerson) !}</td>
						<td>${(item.useNum) !}</td>
						<td>${(item.useChange) !}</td>
						<td>${(item.useRedpackagePrice) !}</td>
						<td>${(item.useOrderPrice) !}</td>
						<td>${(item.useOrderPersonPrice) !}</td>
						
					</tr>
					</#list>
					</#if>
				</tbody>
			</table>
		</div>
	</div>
	<#include "../common/tree.ftl" encoding="utf-8">
	<#include "../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
	
</body>
</html>
