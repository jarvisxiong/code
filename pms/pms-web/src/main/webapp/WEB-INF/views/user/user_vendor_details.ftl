<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>供应商搜索</title>
</head>
<body>
<form id="find-page-orderby-form" method="post" action="${BasePath !}/user/vendorDetails.do" class="ff-form">
	<input id="find-page-index" type="hidden" name="pageIndex" value="1" />
	<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
	<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />

	<div class="form-group no-padding">
		<label>供应商名称：</label>
		<div>
			<input id="keyword" name="keyword" value="" type="text" placeholder="请输入职员姓名">
		</div>
	</div>
	
	<button id="find-page-orderby-button" class="ff-btn sm btn-inquire btn-search" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
</form>
		
<!-- SSUI: 定义 DataTable 的 HTML 节点 -->
<div id="pop_list" class="ff_DataTable"></div>

<style>body{padding:0;}.ff_DataTable{table-layout:fixed;}.clm_code{width:100px !important;}</style>
<script type="text/javascript">
	
	$(document).ready(function(){		
		
		ffzx.ui(['datatable'], function(){
		
			var dt_pop_list = new initDataTable({
				div_id: 'pop_list',
				url: rootPath + "/user/vendorQueryData.do",
				columns:[
					{ data: "code", label: '编码', class:'clm_code'},
					{ data: "name", label: '名称'},
					{ data: "address", label: '地址'}
				],
				show_checkbox: false,
				show_action: false,
				row_dblclick: function(row, data){
					// SSUI: data 为当前行的 JSON 数据
					getSelected(data);
				}
			});
		});
	});
	
	//弹窗页面返回结果
	function getSelected(obj) {
		var art=$(parent.document.getElementById('title:vendorDetails')).prev();
		//父窗口调用扩展
		window.parent.afterSelectedVendor(obj,art);
	}
</script>
</body>
</html>