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
					<form id="myform" action="${BasePath !}/dataPicker/activity_dataPicker.do" method="post">
					        <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
					        <input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
							<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
							<input id="activityType" type="hidden" name="activityType"  value="${(params.activityType)}"/>
						<div class="inquire-ul" style="margin-top:0;">
							<div class="div-form"><input id="activityNo" name="activityNo" placeholder="活动编号"  onblur="this.value=$.trim(this.value);" value="${(params.activityNo) !}"
							type="text"  class="form-control txt_mid input-sm" /></div>
							<div class="div-form"><input id="title" name="title" onblur="this.value=$.trim(this.value);"   placeholder="活动名称"  value="${(params.title) !}"
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
						<th>活动编号</th>
						<th>活动标题</th>
						<th>发布状态</th>
						<th>活动状态</th>
						
					</tr>
				</thead>
				<tbody>
					<#if list?? > 
					<#list list as item >
					<tr  id="${(item.id) !}" title="${(item.title) !}"  activityId ="${(item.activity.id) !}"  ondblclick="getSelected(this)">
						<td>${item_index + 1}</td>
						<td>${(item.activityNo) !}</td>
						<td>${(item.title) !}</td>
						<td><#if (item.releaseStatus)??><#if item.releaseStatus == '0'>未发布<#else>已发布</#if></#if></td>
					    <td><#if (item.activityStatus)??><#if item.activityStatus == '0'>即将开始<#elseif item.activityStatus == '1'>进行中<#else>已结束</#if></#if></td>
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
   			location.href="${BasePath !}/dataPicker/activity_dataPicker.do?clearParams=YES";  
   		}
   		
    	function searchData() {
    		var commodityname = $("#title").val();
    		var placeholder = $("#title").attr("placeholder");
    		if( placeholder== commodityname) {
    			$("#title").val("");
    		}
    		document.myform.submit();
    	}
    	
		//弹窗页面返回结果
		function getSelected(obj) {
			var art=$(parent.document.getElementById('title:activity_dataPicker')).prev();
			//父窗口调用扩展
			window.parent.getSelectAcitvity(obj,art);
		}

	</script>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
</body>
</html>