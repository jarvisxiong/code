<html>
<head>
<meta name="decorator" content="list" />
<title>优惠码</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="10%"></th>
						<th width="40%">优惠码</th>
				        <th width="40%">状态</th>
					</tr>
				</thead>
				<tbody>
					<#if couponVcodesList?? > 
					<#list couponVcodesList as item >
					<tr>
						<td>${item_index + 1}</td>
						<td>${(item.vcode) !}</td>
						<td>
						<#if (item.start) ??><#if item.start == '0'> 未领取  </#if></#if> 
						<#if (item.start) ??><#if item.start == '1'> 已领取  </#if></#if> 
						</td>
					</tr>
					</#list> 
					</#if>
				</tbody>
			</table>
			
					<form id="find-page-orderby-form" action="${BasePath !}/couponVcode/list.do?couponGrantId=${(couponGrantId) !}" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<button id="find-page-orderby-button" type="submit" ><i ></i></button>
					</form>
					
			<#include "../common/page_macro.ftl" encoding="utf-8"> 
			<@my_page pageObj/>
		</div>
	</div>
	<#include "../common/tree.ftl" encoding="utf-8">
	<#include "../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
	<script type="text/javascript">
	 function onEmpty() {
         location.href="${BasePath !}/couponGrant/list.do";            
     }
	//查看
	function view(id) {
		window.location.href = '${BasePath !}/couponGrant/viewDetail.do?id=' + id;
	}
	</script>
</body>
</html>
