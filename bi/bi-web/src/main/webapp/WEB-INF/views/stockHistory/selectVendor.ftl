<html>
<head>
<meta name="decorator" content="list" />
<title>供应商</title>
</head>
<body>
	<div class="col-md-12">
		<div class="tab-content">
			<div class="tab-pane fade in active" id="myAccount">
				<div class="col-md-12">
					<div class="search">
						<form id="find-page-orderby-form"
							action="${BasePath !}/stockHistory/selectVendor.do" method="post">
							<input id="find-page-index" type="hidden" name="pageIndex"
								value="1" /> <input id="find-page-count" type="hidden"
								value="${(pageObj.pageCount) !}" /> <input id="find-page-size"
								type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
							<div class="inquire-ul" style="margin-top: 0;">
								<div class="form-tr">
									<div class="form-td">
										<label>供应商编码：</label>
										<div class="div-form">
											<input class="form-control input-sm txt_mid" type="text"
												id="code" name="code" value="${(vendor.code) !}">
										</div>
									</div>
									<div class="form-td">
										<label>供应商名称：</label>
										<div class="div-form">
											<input class="form-control input-sm txt_mid" type="text"
												id="name" name="name" value="${(vendor.name) !}">
										</div>
									</div>
									<div class="form-td">
										<button id="find-page-orderby-button"
											class="btn btn-primary btn-sm btn-inquire" type="submit">
											<i class="fa fa-search"></i>&nbsp;&nbsp;查询
										</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>

				<!--表格修改2--start-->
				<table class="table table-hover table-striped bor2 table-common">
					<thead>
						<tr>
							<th>序号</th>
							<th>供应商编码</th>
							<th>供应商名称</th>
						</tr>
					</thead>
					<tbody>
						<#if vendorList?? > 
						<#list vendorList as item >
						<tr
							ondblclick="getUpdata('${(item.id)!}','${(item.name)!}','${(item.code)!}')">
							<td>${(item_index+1)}</td>
							<td>${(item.code) !}</td>
							<td>${(item.name) !}</td>
						</tr>
						</#list> 
						</#if>
					</tbody>
				</table>
				<#include "../common/page_macro.ftl" encoding="utf-8"> <@my_page
				pageObj/>
			</div>
		</div>
	</div>
	<#include "../common/tree.ftl" encoding="utf-8"> 
	<#include "../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet"
		href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}"
		type="text/css">
	<script type="text/javascript">
		//弹窗页面返回结果
		function getUpdata(v_id, v_name, v_code) {
			//设置返回值存放位置
			var $vendorId = parent.document.getElementById('vendorId');
			var $vendorName = parent.document.getElementById('vendorName');
			var $vendorCode = parent.document.getElementById('vendorCode');
			$($vendorId).val(v_id);
			$($vendorName).val(v_name);
			$($vendorCode).val(v_code);
			//关闭弹窗窗
			var $close = $(
					parent.document
							.getElementById('title:vendor_select_commodity'))
					.prev();
			$close.click();
		}
	</script>
</body>
</html>