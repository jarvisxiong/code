<html>
<head>
<meta name="decorator" content="list" />
<title>会员列表</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/couponGrant/memberlist.do" method="post">
					<input  id="find-page-index-noidList"  name="noidList" type="hidden" value="${noidList!""}"  />
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<input id="noidList" type="hidden" value="${noidList !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									
									昵称
									<div class="div-form"><input id="nickName" name="nickName" <#if phoneOrNick??>value="${(nickName) !}"<#else>value=""</#if>
									 class="form-control txt_mid input-sm" type="text"></div>
									手机账号
									<div class="div-form"><input id="phone" name="phone"   <#if phoneOrNick??>value="${(phone) !}"<#else>value=""</#if>
									 class="form-control txt_mid input-sm" type="text"></div>
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
						<th width="1%"></th>
				        <th width="15%">昵称</th>
						<th width="19%">手机账号</th>
					</tr>
				</thead>
				<tbody>
					<#if list?? > 
					<#list list as item >
					<tr>
						<td><input type="checkbox" value="${(item.id) !},${(item.nickName) !},${(item.phone) !}" name="check"></td>
						
						<td>${item_index + 1}</td>
						<td>${(item.nickName) !}</td>
						<td>${(item.phone) !}</td>
						
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
	<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_grant_member_list.js?v=${ver !}"></script>
	<script type="text/javascript">
	 
	</script>
</body>
</html>
