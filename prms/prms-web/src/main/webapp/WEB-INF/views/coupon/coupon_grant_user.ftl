<html>
<head>
<meta name="decorator" content="list" />
<title>指定用户</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/member/list.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>用户昵称/手机号：</label> 
									<div class="div-form"><input id="nickName" name="nickName" class="form-control txt_mid input-sm" type="text" placeholder="" value="${() !}">							
									</div>
								</div>
							</div>
						</div>
						
						<div class="btn-div3">
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
						</div>
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="1%"></th>
						<th width="15px"><input type="checkbox"></th>
						<th width="15%">昵称</th>
				        <th width="15%">手机</th>
					</tr>
				</thead>
				<tbody>
					<#if xxxx?? > 
					<#list xxxx as item >
					<tr>
						<td>${item_index + 1}</td>
						<td><input type="checkbox" value="${(item.id) !}"  phone="${(item.) !}" name="check"></td>
						<td>${(item.) !}</td>						
						<td>${(item.) !}</td>				
					</tr>
					</#list> 
					</#if>
				</tbody>
			</table>
			<#include "../common/page_macro.ftl" encoding="utf-8"> 
			<@my_page pageObj/>
		</div>
	</div>
	<#include "../common/css.ftl" encoding="utf-8">
	<#include "../common/tree.ftl" encoding="utf-8">
	<#include "../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
	<script type="text/javascript">
	 function onEmpty() {
         location.href="${BasePath !}/member/list.do";            
     }
	 function sort() {
		 $("#orderFlag").val("1");
		 $("#find-page-orderby-form").submit();
     }
	</script>
</body>
</html>
