<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>预售-商品设置列表</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
				<form id="find-page-orderby-form"  name="myform"  action="${BasePath !}/presellActivity/commoditylist.do" method="post">
					<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
					<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<input id="size" type="hidden" name="size" value="${size !}" /> 
					<input type="hidden" id="activityId" name="activityId" value="${(activityCommodity.activity.id) !}"/>    
	       			 <input type="hidden" id="activityNo" name="activityNo" value="${(activityCommodity.activity.activityNo) !}"/>
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>标题：</label> 
								<div class="div-form">
									<input id="activityTitle" name="activityTitle" value="${(activityTitle)!}" class="form-control txt_mid input-sm" type="text">
									<input type="hidden" name="tblshow" value="${(tblshow)!}">
								</div>
							</div>
							<div class="form-td">
								<label>商品条形码：</label> 
								<div class="div-form">
									<input id="commodityBarcode" name="commodityBarcode" value="${(commodityBarcode)!}" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
						</div>
					</div>
					<div class="btn-div3">
					<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
					<@permission name="prms:presale:goodsconfigadd">
					<a href="javascript:void(0);"  onclick="setCommdity('${(activityCommodity.activity.id) !}','${(activityCommodity.activity.activityNo) !}','save')" class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a>
					</@permission>
					
					<@permission name="prms:presale:commoditydeletes">
					<a id="table-deleteBtn" class="btn btn-primary btn-sm" table_action="/presellActivity/commodityDeletes.do"><i class="fa fa-remove"></i>&nbsp;&nbsp;批量删除</a>
					</@permission>
					
					<@permission name="prms:presale:importview">
					<a href="#" onClick='showMember("${(activityCommodity.activity.id) !}","PRE_SALE")' class="btn btn-primary btn-sm" ><i class="fa fa-plus" ></i>&nbsp;&nbsp;批量导入</a>
					</@permission>
					
					<@permission name="prms:presale:importexport">
					<a id="" onclick="exportData()" class="btn btn-primary btn-sm btn-inquire" ><i class="fa fa-search" ></i>&nbsp;&nbsp;批量导出</a>
					</@permission>
					
					</div>
				</form>
			</div>
		</div>
		<div class="navtabs-title">
				<ul class="nav nav-tabs" style="padding-left: 1%;">
					<li <#if tblshow?? && tblshow == "-1">class="active"</#if>><a href="${BasePath !}/presellActivity/commoditylist.do?tblshow=-1&activityId=${(activityCommodity.activity.id) !}&activityNo=${(activityCommodity.activity.activityNo) !}">全部</a></li>
					<li <#if tblshow?? && tblshow == "0">class="active"</#if>><a href="${BasePath !}/presellActivity/commoditylist.do?tblshow=0&activityId=${(activityCommodity.activity.id) !}&activityNo=${(activityCommodity.activity.activityNo) !}&activityStatus=0 " >即将开始</a></li>
					<li <#if tblshow?? && tblshow == "1">class="active"</#if>><a href="${BasePath !}/presellActivity/commoditylist.do?tblshow=1&activityId=${(activityCommodity.activity.id) !}&activityNo=${(activityCommodity.activity.activityNo) !}&activityStatus=1 " >进行中</a></li>
					<li <#if tblshow?? && tblshow == "3">class="active"</#if>><a href="${BasePath !}/presellActivity/commoditylist.do?tblshow=3&activityId=${(activityCommodity.activity.id) !}&activityNo=${(activityCommodity.activity.activityNo) !}" >已抢完</a></li>
					<li <#if tblshow?? && tblshow == "2">class="active"</#if>><a href="${BasePath !}/presellActivity/commoditylist.do?tblshow=2&activityId=${(activityCommodity.activity.id) !}&activityNo=${(activityCommodity.activity.activityNo) !}&activityStatus=2 " >已结束</a></li>
				</ul>
		</div>
		<table class="table table-hover table-striped bor2 table-common">
			<thead>
				<tr>
					<th width="1%"></th>
					<th width="3%"><input type="checkbox"></th>
					<th width="8%">商品标题</th>
					<th width="8%">所属标签</th>
				    <th width="8%">状态</th>
					<th width="15%">置顶序号</th>
					<th width="10%">排序号</th>
					<th width="15%">商品条形码</th>
					<th width="8%">优惠价/元</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<#if commodityList?? > 					
					<#list commodityList as item >		
							<tr id="${(item.id) !}" ondblclick="view('${(item.activity.id) !}','${(item.activity.activityNo) !}','${(item.id) !}','view')">
								<td align="center">${item_index + 1}</td>
								<td><input type="checkbox" value="${(item.id) !}"  name="check"></td>
								<td>${(item.activityTitle) !}</td>	
								<td>${(item.preSaleTag.name) !}</td>		
									<#if item.commodityStatus?? >				
										<#if item.commodityStatus=='0'>
												<td>即将开始</td>
										<#elseif item.commodityStatus=='1'>
												<td>进行中</td>
										<#elseif item.commodityStatus=='2'>
												<td>已结束</td>
										<#elseif item.commodityStatus=='3'>
												<td>已抢完</td>
										<#else>
												<td></td>
										</#if>
									<#else>
											<td></td>
									</#if>						
								<td>${(item.sortTopNo) !}</td>
								<td>${(item.sortNo) !}</td>
								<td>${(item.commodityBarcode) !}</td>
								<td>${(item.showPrice) !}</td>
								<td>
								
								    <@permission name="prms:presale:commoditydelete">
									<a href="javascript:void(0);"  onclick="setCommdity('${(item.activity.id) !}','${(item.activity.activityNo) !}','${(item.id) !}','edit')" >编辑</a>
									</@permission>
									<@permission name="prms:presalecommodity:edit">
									<a name="delete_item"  href="${BasePath !}/presellActivity/commodityDelete.do?id=${(item.id) !}">删除</a>
									</@permission>
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
	<#include "../common/tree.ftl" encoding="utf-8">
	<#include "../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
	<script type="text/javascript" src="${BasePath !}/asset/js/activity/presale_commodity_list.js?v=${ver !}"></script>
	<script type="text/javascript">
	function showMember(id,type){
	    var url = rootPath + '/panicbuyActivity/importView.do?id='+id+'&type='+type;
	    console.log(url);
	    var dlg=dialog({
	        id: "printerForm",
	        title: '导入excel',
	        lock: false,
	        content:"<iframe src="+url+" width='500px' height='210px' frameborder='0' scrolling='no' id='printerForm' name='printerForm,"+window.location.href+"' ></iframe>",
	        button: [
	            
	            {
	                value: '关闭',
		             callback: function () {
		            	 console.log("t");
		            	 myform.submit();
		            	 return true;
		             }
	            }
	        ]
	    }).showModal();
	}
	</script>
</body>
</html>