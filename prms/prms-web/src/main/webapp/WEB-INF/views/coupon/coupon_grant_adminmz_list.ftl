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
					<form id="find-page-orderby-form" action="${BasePath !}/couponGrant/couponAdminMzList.do" method="post">
					
					<input  id="find-page-index-noidList"  name="noidList" type="hidden" value="${noidList!""}"  />
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<input id="noidList" type="hidden" value="${noidList !}" />
						<input id="grantDate" name="grantDate" type="hidden" value="${grantDate !}" />
						<input id="isDate" name="isDate" type="hidden" value="${isDate !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>优惠券名称：</label> 
									<div class="div-form"><input id="couponName" name="couponName" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(couponName) !}"></div>
								</div>
								<div class="form-td">
									<label>优惠券编码：</label> 
									<div class="div-form"><input id="number" name="number" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(number) !}"></div>
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
						<th width="1%"><input type="checkbox"></th>
						<th width="5%"></th>
				        <th width="15%">优惠券编码</th>
						<th width="19%">优惠券名称</th>
						<th width="8%">面值</th>
						<th width="10%">消费限制</th>
						<th width="19%">有效期</th>
					</tr>
				</thead>
				<tbody>
					<#if list?? > 
					<#list list as item >
					<tr>
						<td><input type="checkbox" value="${(item.id) !},${(item.number) !},${(item.name) !},${(item.faceValue) !},${(item.consumptionLimit) !},
						<#if (item.effectiveDateState) ??><#if item.effectiveDateState =='0'>
							${(item.effectiveDateNum) !}天
						<#else>
							${(item.effectiveDateStart?string('yyyy-MM-dd HH:mm:ss')) !}-${(item.effectiveDateEnd?string('yyyy-MM-dd HH:mm:ss')) !}			
						</#if>
						</#if>
						" name="check"></td>
						<td>${item_index + 1}</td>
						<td>${(item.number) !}</td>
						<td>${(item.name) !}</td>
						<td>${(item.faceValue) !}</td>
						<td>
						<#if (item.consumptionLimit) ??><#if item.consumptionLimit == -1>  无限制
						<#else>
						满${(item.consumptionLimit) !}元使用
						</#if>
						</#if> 
						</td>
						<#if (item.effectiveDateState) ??><#if item.effectiveDateState =='0'>
							<td>${(item.effectiveDateNum) !}天</td>
						<#else>
							<td>${(item.effectiveDateStart?string('yyyy-MM-dd HH:mm:ss')) !}-${(item.effectiveDateEnd?string('yyyy-MM-dd HH:mm:ss')) !}</td>			
						</#if>
						</#if>
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
	<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_grant_adminmz_list.js?v=${ver !}"></script>
	<script type="text/javascript">
	 
	</script>
</body>
</html>
