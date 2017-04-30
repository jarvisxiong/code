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
					<form id="myform" action="${BasePath !}/order/partner_dataPicker.do" method="post">
					        <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
							<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<div class="inquire-ul" style="margin-top:0;">
							<div class="div-form"><input id="code" name="code"    placeholder="商品编码"  onblur="this.value=$.trim(this.value);" value="${(params.code) !}"
							type="text"  class="form-control txt_mid input-sm" /></div>
							<div class="div-form"><input id="commodityname" name="commodityname" onblur="this.value=$.trim(this.value);"   placeholder="商品名称"  value="${(params.commodityname) !}"
							type="text"  class="form-control txt_mid input-sm" /></div>
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
						<th></th>
						<th>商品编号</th>
						<th>商品名称</th>
						<th>商品标题</th>
						<th>类别名称</th>
						
					</tr>
				</thead>
				<tbody>
					<#if list?? > 
					<#list list as item >
					<tr id="${item.id}" code="${(item.name) !}" title="${(item.title) !}"   ondblclick="getSelected(this)">
						<td>${item_index + 1}</td>
						<td>${(item.code) !}</td>
						<td>${(item.name) !}</td>
						<td>${(item.title) !}</td>
						<td>${(item.categoryname) !}</td>
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
   			location.href="${BasePath !}/order/partner_dataPicker.do?clearParams=YES";  
   		}
   		
    	function searchData() {
    		var commodityname = $("#commodityname").val();
    		if("商品名称" == commodityname) {
    			$("#commodityname").val("");
    		}
    		document.myform.submit();
    	}
    	
		//弹窗页面返回结果
		function getSelected(obj) {
			var art=$(parent.document.getElementById('title:commodity_dataPicker')).prev();
			//父窗口调用扩展
			window.parent.getSelectCommodity(obj,art);
			/* if(window.parent && window.parent.getSelectCommodity){
			window.parent.getSelectCommodity(obj,art);
			} */
		}

	</script>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
</body>
</html>