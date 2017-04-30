<html>
<head>
<meta name="decorator" content="v2" />
<title>栏目数据管理</title>
</head>
<body>

	<form id="find-page-orderby-form" action="${BasePath !}/catelogData/list.do" method="post" class="ff-form">
		<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
		<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
		<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
		<input id="catelogId" name="catelogId" type="hidden" value="${(catelogId)! }"/>
		
		<div class="form-group">
			<label>标题：</label> 
			<div><input id="title" name="title" type="text" placeholder="" value="${(catelogData.title) !}"></div>
		</div>
		
		<div class="form-group">
			<label>发布类型：</label>
			<div> 
				<select id="isLaunch" name="isLaunch"> 
					<option value="">--请选择--</option>
					<option value="1">已发布</option>
					<option value="0">未发布</option>
				</select>
			</div>
		</div>

		<button id="find-page-orderby-button" class="ff-btn sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
		<a href="javascript:iframeFullPage('${BasePath !}/catelogData/form.do?catelogId=${(catelogId)! }')" class="ff-btn sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
		<button class="ff-btn sm white btn-clear-keyword" data-target="catelogData_list"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>
		<a href="javascript:batchOpertionRelease('RELEASE')" class="ff-btn sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;批量发布</a>
		<a href="javascript:batchOpertionRelease('CANCEL')" class="ff-btn sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;批量取消</a>

	</form>
	
	<div id="catelogData_list" class="ff_DataTable"></div>

	<script type="text/javascript" src="${BasePath !}/asset/js/portal/catelogData/catelogData_list.js?v=${ver !}"></script>
	<style>.clm_action{width:150px !important;}</style>
	<script>
		var catelogId = $("#catelogId").val();
		var dt_catelogData_list = null;
		$(document).ready(function(){		
			ffzx.ui([
	         'dialog', 
	         'select2', 
	         ], function(){
				$('select').select2();
				
	  	    });
			ffzx.ui(['datatable'], function(){
				dt_catelogData_list = new initDataTable({
					div_id: 'catelogData_list',
					url: rootPath + "/catelogData/listData.do?catelogIdTwo="+catelogId,
					columns:[
						{ data: "title", label: '标题', class:'text-nowrap'},
						{ data: "isLaunch", label: '状态', class:'text-nowrap', data_dict:{"0":"未发布", "1":"已发布"}},
						{ data: "createTime", label: '创建时间', class:'text-nowrap',format:{datetime:'yyyy-MM-dd hh:mm:ss'}}
					],
					show_checkbox: true,
					show_index:false,
					gen_permission: function(){
						var map = [];
						map.push('edit');
						map.push('delete');
						map.push('isRelease');
						map.push('isTop');
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
