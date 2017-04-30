<html>
<head>
<meta name="decorator" content="list" />
<title>商品列表</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/ordinaryActivity/selectOrdinary.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="activity.id" name="activity.id" type="hidden" value="${(commodity.activity.id) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>标题：</label>
									<div class="div-form"><input id="activityTitle" name="activityTitle" value="${(commodity.activityTitle) !}" class="form-control txt_mid input-sm" type="text" /></div>							
								</div>
								<div class="form-td">
									<label>编码：</label>
									<div class="div-form"><input id="commodityBarcode" name="commodityBarcode" value="${(commodity.commodityBarcode) !}" class="form-control txt_mid input-sm" type="text" /></div>							
								</div>
								<div class="form-td">
									<div class="btn-div3">
										<div class="div-form"><button id="find-page-orderby-button" type="submit" class="btn btn-primary btn-sm btn-inquire" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button></div>
									</div>
								</div>
							</div>
						</div>					
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="1%"></th>
						<th width="15%">商品标题</th>
				        <th width="15%">商品条形码</th>
						<th width="15%">优惠价/元</th>
					</tr>
				</thead>
				<tbody>
					<#if dataList?? > 
					<#list dataList as item >
					<tr id="${(item.id) !}" title="${(item.activityTitle) !}" actId="${(acrivityId) !}" isRecommend="${(item.isRecommend) !}" ondblclick="getSelected(this)">
						<td>${item_index + 1}</td>
						<td>${(item.activityTitle) !}</td>						
						<td>${(item.commodityBarcode) !}</td>
						<td>${(item.showPrice) !}</td>			 	
					</tr>
					</#list> 
					</#if>
				</tbody>
			</table>
			<#include "../../common/page_macro.ftl" encoding="utf-8"> 
			<@my_page pageObj/>
		</div>
	</div>
	<script type="text/javascript">
		//弹窗页面返回结果
		function getSelected(obj) {
			var art=$(parent.document.getElementById('title:toSelectActivityForm')).prev();
			//父窗口调用扩展
			window.parent.afterSelectedActivity(obj,art);
		}
	</script>
	<#include "../../common/css.ftl" encoding="utf-8">
	<#include "../../common/tree.ftl" encoding="utf-8">
	<#include "../../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
</body>
</html>
