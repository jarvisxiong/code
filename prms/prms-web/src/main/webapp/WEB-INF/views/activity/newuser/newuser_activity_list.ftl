<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>活动管理-新用户专享活动列表页面</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
				<form id="find-page-orderby-form" action="${BasePath !}/newUserActivity/list.do" method="post">
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>标题：</label> 
								<div class="div-form">
									<input id="title" name="title" value="${(activity.title) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<div class="form-td">
								<label>发布状态：</label> 
								<div class="div-form">
									<select class="form-control txt_mid input-sm" id="releaseStatus" name="releaseStatus">
										<option value="">不限</option>
										<option value="0" <#if (activity.releaseStatus) ??><#if activity.releaseStatus == '0'>selected="selected"</#if></#if> >未发布</option>
										<option value="1" <#if (activity.releaseStatus) ??><#if activity.releaseStatus == '1'>selected="selected"</#if></#if> >已发布</option>
									</select>
								</div>
							</div>
							<div class="form-td">
								<label>操作人：</label> 
								<div class="div-form">
									<input id="operator" name="operator" value="${(activity.operator) !}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
						</div>
					</div>
					<div class="btn-div3">
						<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
						<button id="clean" onclick="onEmpty()" class="btn btn-primary btn-sm" type="button"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button> 
						<@permission name="prms:newuser:add">
						<a href="${BasePath !}/newUserActivity/form.do"  class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
						</@permission>
					</div>
				</form>
			</div>
		</div>
		<!--状态按钮--start-->
		<div class="navtabs-title">
			<ul class="nav nav-tabs" style="padding-left: 1%;">
				<li><a href="javascript:findBystatus('',this)">&nbsp;&nbsp;全部</a></li>
				<li><a href="javascript:findBystatus('1',this)" >&nbsp;&nbsp;已发布</a></li>
				<li><a href="javascript:findBystatus('0',this)" >&nbsp;&nbsp;未发布</a></li>
			</ul>
		</div>
		<!--状态按钮--end-->
		<!--表格修改2--start-->
		<table class="table table-hover table-striped bor2 table-common">
			<thead>
				<tr>
					<th></th>
					<th class="tab-td-center">活动编号</th>
					<th class="tab-td-center">活动名称/标题</th>
					<th class="tab-td-center">发布状态</th>
					<th class="tab-td-center">操作人</th>
					<th class="tab-td-center">最近操作时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<#if dataList?? > 
				<#list dataList as item >
				<tr id="${(item.id) !}" pId="${(item.pId) !}" ondblclick="view('${(item.id) !}')">
					<td align="center">${item_index + 1}</td>
					<td align="center"><a href="${BasePath !}/newUserActivity/view.do?id=${(item.id) !}">${(item.activityNo) !}</a></td>
					<td align="center">${(item.title) !}</td>
					<td align="center">
						<#if item.releaseStatus ??><#if (item.releaseStatus) = '0'>
							未发布
						<#else>
							已发布
						</#if>
						</#if>
					</td>
					<td align="center">${(item.operator) !}</td>
					<td align="center">${(item.lastUpdateDate?string("yyyy-MM-dd HH:mm:ss")) !}</td>
					<td>
						<a href="javascript:isEnabled('${(item.id) !}',<#if item.releaseStatus == '0'>1<#else>0</#if>) ">
						<#if item.releaseStatus == '0'>
						     <@permission name="prms:newuser:release">
						                              发布
						     </@permission>
						  <#else>
						     <@permission name="prms:newuser:revoke">
						                            撤销
						     </@permission>
						  </#if></a> 
						<#if item.releaseStatus ??><#if (item.releaseStatus) = '0'>
						    <@permission name="prms:newuser:edit">
							| <a href="${BasePath !}/newUserActivity/form.do?id=${(item.id) !}">编辑</a>
							</@permission>
							<@permission name="prms:panicbuy:goodsconfig">
							| <a href="javascript:ordinaryCommodity('${(item.id) !}');">商品设置</a>
							</@permission>
							<@permission name="prms:panicbuy:delete">
							| <a href="javascript:deleteItem('${(item.id) !}')">删除</a>
							</@permission>
						<#else>
						    <@permission name="prms:newuser:change">
							| <a href="${BasePath !}/newUserActivity/form.do?id=${(item.id) !}">特改</a>
							</@permission>
							<@permission name="prms:panicbuy:goodsconfig">
							| <a href="javascript:ordinaryCommodity('${(item.id) !}','${(item.activityNo)! }');">商品设置</a>
							</@permission>
						</#if>
						</#if>
					</td>
				</tr>
				</#list> 
				</#if>
			</tbody>
		</table>

		<#include "../../common/page_macro.ftl" encoding="utf-8"> 
		<@my_page pageObj/>
	</div>
</div>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/newuser_activity_list.js?v=${ver !}"></script>
<script type="text/javascript">
	 function onEmpty() {
         location.href="${BasePath !}/newUserActivity/list.do";            
     }
	//查看
	function view(id) {
		window.location.href = '${BasePath !}/newUserActivity/view.do?id=' + id;
	}
	</script>
</body>
</html>