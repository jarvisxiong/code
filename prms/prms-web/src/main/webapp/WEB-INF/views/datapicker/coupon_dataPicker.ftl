<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
</head>
<body>
	<div class="tab-content">
		<div class="tab-pane fade in active">
			<!--新增1--start-->
			<div class="row">
				<div class="col-lg-10 col-md-12 col-sm-12">
					<form id="myform" action="${BasePath !}/dataPicker/coupon_dataPicker.do" method="post">
					        <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
					        <input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
							<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<div class="inquire-ul" style="margin-top:0;">
							<div class="div-form">
							<input id="grantStr" name="grantStr" placeholder="发放名称/发放编码" onblur="this.value=$.trim(this.value);" value="${(grantStr) !}" 
							type="text" class="form-control txt_mid input-sm" /></div>
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							<button id="table-deleteBtn" class="btn btn-primary btn-sm" onclick="$(this).parent().find('input').val(''); "><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button>
						</div>
					</form>
				</div>
			</div>
			<!--新增1--end-->

			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="45px"></th>
						<th class="tab-td-center">发放编码</th>
						<th class="tab-td-center">发放名称</th>
						<th class="tab-td-center">发放时间</th>
						<th class="tab-td-center">结束时间</th>
						<th class="tab-td-center">发放方式</th>
						<th class="tab-td-center">用户类型</th>
						<th class="tab-td-center">发放类型</th>
						<th class="tab-td-center">发放状态</th>
					</tr>
				</thead>
				<tbody>
					<#if list?? > 
					<#list list as item >
					<tr id="${item.id}" code="${(item.number) !}" title="${(item.name) !}" ondblclick="getSelected(this)">
						<td align="center">${item_index + 1}</td>
						<td align="center">${(item.number) !}</td>
						<td align="center">${(item.name) !}</td>
						<td align="center">${(item.grantDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>
						<td align="center">${(item.grantEndDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>
						<td align="center">
							<#if (item.grantMode) ??><#if item.grantMode == '0'>  系统分配  </#if></#if> 
							<#if (item.grantMode) ??><#if item.grantMode == '1'>  用户领取  </#if></#if> 
							<#if (item.grantMode) ??><#if item.grantMode == '2'>  指定用户  </#if></#if>
						</td>
						<td align="center">
							<#if (item.userType) ??><#if item.userType == '0'> 所有用户 </#if></#if> 
							<#if (item.userType) ??><#if item.userType == '1'> 指定用户  </#if></#if> 
							<#if (item.userType) ??><#if item.userType == '2'> 新用户 </#if></#if>
						</td>
						<td align="center">
							<#if (item.grantType) ??><#if item.grantType == '0'> 优惠券  </#if></#if> 
							<#if (item.grantType) ??><#if item.grantType == '1'> 注册  </#if></#if> 
							<#if (item.grantType) ??><#if item.grantType == '2'> 分享  </#if></#if> 
							<#if (item.grantType) ??><#if item.grantType == '3'> 优惠码  </#if></#if> 
						</td>
						<td align="center">
							<#if (item.isGrant) ??><#if item.isGrant == '0'>  未开始  </#if></#if> 
							<#if (item.isGrant) ??><#if item.isGrant == '1'>  已结束  </#if></#if> 
							<#if (item.isGrant) ??><#if item.isGrant == '2'>  进行中 </#if></#if>
						</td>
					</tr>
					</#list> 
					</#if>
				</tbody>
			</table>
			<#include "../common/page_macro.ftl" encoding="utf-8">
			<@my_page pageObj/>   
		</div>
	</div>
	<script type="text/javascript">
	
	/*
	调用方式
	$.frontEngineDialog.executeIframeDialog('commodity_dataPicker', 'shangpin', rootPath
			+ '/dataPicker/commodity_dataPicker.do', '600', '550'); */
   		function onEmpty() {
   			location.href="${BasePath !}/dataPicker/coupon_dataPicker.do?clearParams=YES";  
   		}
   		
    	function searchData() {
    		var grantStr = $("#grantStr").val();
    		if("发放名称/发放编码" == grantStr) {
    			$("#grantStr").val("");
    		}
    		document.myform.submit();
    	}
    	
		//弹窗页面返回结果
		function getSelected(obj) {
			var art=$(parent.document.getElementById('title:coupon_dataPicker')).prev();
			//父窗口调用扩展
			window.parent.getSelectCouponGrant(obj,art);
		}

	</script>
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
</body>
</html>