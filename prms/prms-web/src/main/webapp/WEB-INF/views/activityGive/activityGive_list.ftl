<html>
<head>
<meta name="decorator" content="list" />
<title>买赠管理</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/activityGive/list.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>活动 ID</label> 
									<div class="div-form"><input id="giveNo" name="giveNo" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(activityGive.giveNo) !}">							
									</div>
								</div>
								
								<div class="form-td">
									<label>标题</label> 
										<div class="div-form"><input id="giveTitle" name="giveTitle" value="${(activityGive.giveTitle) !}" class="form-control txt_mid input-sm" type="text" placeholder="" ">							
									</div>
								</div>
								<div class="form-td">
									<label>主商品编码</label> 
										<div class="div-form"><input id="commodityCode" name="commodityCode" value="${(activityGive.commodityCode) !}" class="form-control txt_mid input-sm" type="text" placeholder="" ">							
									</div>
								</div>
								<div class="form-td">
									<label>主商品条形码</label> 
										<div class="div-form"><input id="commodityBarcode" name="commodityBarcode" value="${(activityGive.commodityBarcode) !}" class="form-control txt_mid input-sm" type="text" placeholder="" ">							
									</div>
								</div>
							</div>
						</div>
						
						<div class="btn-div3">
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							<button id="clean" onclick="onEmpty()" class="btn btn-primary btn-sm" type="button"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button> 
							
								<@permission name="prms:mz:export">
							<a id="" onclick="batchExport()" class="btn btn-primary btn-sm btn-inquire" ><i class="fa fa-search" ></i>&nbsp;&nbsp;批量导出</a>
								</@permission>
								<@permission name="prms:mz:add">
							<a href="${BasePath !}/activityGive/form.do?viewstatus=save" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a>
								</@permission>
						</div>
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="4%"><input type="checkbox"></th>
						<th width="3%">序号</th>
						<th width="8%">活动 ID</th>
				        <th width="8%">标题</th>
						<th width="8%">主商品编码</th>
						<th width="8%">主商品条形码</th>
						<th width="8%">主商品优惠价</th>
						<th width="8%">创建人</th>
						<th width="10%">创建时间</th>
						<th width="16%">操作</th>
					</tr>
				</thead>
				<tbody>
					<#if activityGives?? > 
					<#list activityGives as item >
					<tr id="${(item.id) !}" >
						
						<td><input type="checkbox" value="${(item.id) !}"   name="check" ></td>
						<td>${item_index + 1}</td>
						<td>${(item.giveNo) !}</td>
						<td>${(item.giveTitle) !}</td>
						<td>${(item.commodityCode) !}</td>
						<td>${(item.commodityBarcode) !}</td>
						<td>${(item.commodityprice) !}</td>
						<td>${(item.createName) !}</td>
						<td>${(item.createDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>
						
						<td>
							
								<@permission name="prms:mz:view">
								<a href="${BasePath !}/activityGive/formdetail.do?id=${(item.id) !}&viewstatus=view">查看 </a>|
								</@permission>
								<@permission name="prms:mz:edit">
								<#if item.actFlag=='1'>
								编辑
								<#else>
									<a href="${BasePath !}/activityGive/form.do?id=${(item.id) !}&viewstatus=edit">编辑</a> | 
								</#if>
									</@permission>
									
									
									<@permission name="prms:mz:enable">
										<#if item.actFlag=='0'><a href="javascript:void(0);" onclick="isAlterstart('${(item.id) !}','${(item.actFlag) !}','${(item.commodityId) !}')">启用</a></#if>
										<#if item.actFlag=='1'><a href="javascript:void(0);" onclick="isAlterstart('${(item.id) !}','${(item.actFlag) !}','${(item.commodityId) !}')">禁用</a></#if> |
									</@permission>
									
									<@permission name="prms:mz:delete">
									<#if item.actFlag=='1'>
								删除
									<#else>
										<a name="delete_item"  href="${BasePath !}/activityGive/delete.do?id=${(item.id) !}">删除</a>
									</#if>
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
	<div style="display:none;"  ><iframe id="export_iframe" ></iframe></div> 
	<#include "../common/tree.ftl" encoding="utf-8">
	<#include "../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
	<script type="text/javascript" src="${BasePath !}/asset/js/activityGive/activityGive_list.js?v=${ver !}"></script>
	<script type="text/javascript">

	
	
	 function onEmpty() {
         location.href="${BasePath !}/activityGive/list.do";            
     }
	//查看
	function view(id) {
		window.location.href = '${BasePath !}/couponGrant/viewDetail.do?id=' + id;
	}
	//批量导出
	function batchExport(){
		//var params = $("#find-page-orderby-form").serialize();
		var selectRows= $("input:checkbox[name='check']:checked") ;
		var ids ="";
		if(!selectRows || selectRows.length==0){
			$.frontEngineDialog.executeDialogContentTime("请先勾选要导出的数据！");
			return false;
		}
		$.each(selectRows,function(index,obj){
			ids += $(obj).val()+",";
		});
		ids=ids.substring(0,ids.length-1);
		$("#export_iframe").attr("src",rootPath+"/activityGive/export.do?ids="+ids);
	}
	</script>
</body>
</html>
