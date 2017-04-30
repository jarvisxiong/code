<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>预售标签设置</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
				<form id="find-page-orderby-form"  name="myform"  action="${BasePath !}/presaletag/tagList.do" method="post">
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<div class="btn-div3">									
					<@permission name="prms:presale:addTag"> 
						<a href="javascript:void(0);"  onclick="setTag('${(presaleTag.id) !}','save')" class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a>		
					</@permission>
					<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit" style="display:none;"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
					</div>
				</form>
			</div>
		</div>
		<table class="table table-hover table-striped bor2 table-common">
			<thead>
				<tr>
					<th width="1%">序号</th>
					<th width="8%">标签名称</th>
				    <th width="8%">排序号</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<#if dataList?? > 					
					<#list dataList as item >		
							<tr>
								<td align="center">${item_index + 1}</td>
								<td>${(item.name) !}</td>													
								<td>${(item.number) !}</td>
								<td>								
								     <@permission name="prms:presale:editTag">
									<a href="javascript:void(0);"  onclick="setTag('${(item.id) !}','edit')" >编辑</a>
									 </@permission>
									<@permission name="prms:presale:deleteTag">
									<a name="delete_item"  href="${BasePath !}/presaletag/deleteTag.do?id=${(item.id) !}">删除</a>
									</@permission>
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
	<#include "../../common/tree.ftl" encoding="utf-8">
	<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css" type="text/css"> 
	<script type="text/javascript" src="${BasePath !}/asset/js/activity/presaletag/presale_tag_list.js"></script>
</body>
</html>