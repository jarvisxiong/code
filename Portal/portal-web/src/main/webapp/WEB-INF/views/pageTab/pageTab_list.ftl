<html>
<head>
<meta name="decorator" content="v2" />
<title>页签管理</title>
</head>
<body>

	<form id="find-page-orderby-form" action="${BasePath !}/pageTab/list.do" method="post" class="ff-form">
		<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
		<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
		<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
		
		<div class="form-group">
			<label>编码：</label> 
			<div><input id="number" name="number" type="text" placeholder="" value="${(pageTab.number) !}"></div>
		</div>
		<div class="form-group">
			<label>名称：</label> 
			<div><input id="name" name="name" type="text" placeholder="" value="${(pageTab.name) !}"></div>
		</div>
		<div class="form-group">
			<label>关键字：</label> 
			<div><input id="key" name="key" type="text" placeholder="" value="${(pageTab.key) !}"></div>
		</div>
		
		<div class="form-group">
			<label>页签类型：</label>
			<div> 
				<select id="pageTabType" name="pageTabType"> 
					<option value="">--请选择--</option>
					<option value="DYNAMIC">动态页签</option>
					<option value="OUTER">外部页签</option>
				</select>
			</div>
		</div>

		<button id="find-page-orderby-button" class="ff-btn sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
		<a href="javascript:iframeFullPage('${BasePath !}/pageTab/form.do')" class="ff-btn sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
		<button class="ff-btn sm white btn-clear-keyword" data-target="pageTab_list"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>

	</form>
	
	<div id="pageTab_list" class="ff_DataTable"></div>

	<script type="text/javascript" src="${BasePath !}/asset/js/portal/pageTab/pageTab_list.js?v=${ver !}"></script>
	<style>.clm_action{width:150px !important;}</style>
	<script>
	
		$(document).ready(function(){		
		
			requirejs(['ff/select2'], function(){
				$('select').select2();
			});
			
			requirejs(['ff/init_datatable'], function(initDataTable){
				var dt_pageTab_list = new initDataTable({
					div_id: 'pageTab_list',
					url: rootPath + "/pageTab/listData.do",
					columns:[
						{ data: "number", label: '编码', class:'text-nowrap'},
						{ data: "name", label: '中文名称', class:'text-nowrap'},
						{ data: "pageTabType", label: '页面类型', class:'text-nowrap', data_dict:{"DYNAMIC":"动态页签", "OUTER":"外部页签"}},
						{ data: "sort", label: '序号', class:'text-nowrap'},
						{ data: "key", label: '关键字', class:'text-nowrap'}
					],
					show_checkbox: false,
					gen_permission: function(){
						var map = [];
						map.push('edit');
						map.push('delete');
						return map;		
					},
					clm_action: function(item){
						return gen_action(item);
					}
				});
			});
		});
	
	</script>
</body>
</html>
