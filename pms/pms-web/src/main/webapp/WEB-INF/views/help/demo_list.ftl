<#include "../global.ftl" encoding="utf-8">

<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>演示页面</title>	
</head>
<body>

	<form id="find-page-orderby-form" action="${BasePath !}/help/test/listDemo.do" method="post" class="ff-form">
		<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
		<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
		<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
		
			
		<div class="form-group">
			<label>用户名：</label> 
			<div><input id="name" name="name" type="text" /></div>
		</div>
		
		<div class="form-group">
			<label>年龄：</label>
			<div>
				<input id="beginAgeStr" name="beginAgeStr" type="text" />
				-
				<input id="endAgeStr" name="endAgeStr" type="text" />
			</div>
		</div>
		
		<div class="form-group">
			<label>邮箱：</label> 
			<div><input id="email" name="email" type="text"></div>
		</div>
		
		<div class="form-group">
			<label>创建人：</label> 
			<div><input id="createdBy" name="createBy.name" type="text" /></div>
		</div>
		
		<div class="form-group">
			<label>创建时间：</label>
			<div>
	            <input name="beginCreateDateStr" id="beginCreateDateStr" type="text" />
	            -
	            <input name="endCreateDateStr" id="endCreateDateStr" type="text" />
	    	</div>
	    </div>
	
	    <div class="form-group">
	        <label>最后修改人：</label> 
	        <div><input id="lastUpdatedBy" name="lastUpdateBy.name" type="text" /></div>
	    </div>
	
	    <div class="form-group col-span-2">
	        <label>最后修改时间：</label>
	        <div>
	            <input name="beginLastUpdateDateStr" id="beginLastUpdateDateStr" type="text" />
	            -
	            <input name="endLastUpdateDateStr" id="endLastUpdateDateStr" type="text" />								
			</div>
		</div>
	
	    <div class="form-group">
	        <label>生日：</label>
	        <div>
	            <input name="birthday" id="birthday" type="text" />						
			</div>
		</div>
		
		<div class="form-group">
			<label>省份：</label>
			<div>
				<select id="province" name="province" data-hint="请选择省份" data-option='<@JSONArray dataDict="sys_office_grade"/>' data-selected=""></select>
			</div>
		</div>
		
		<button id="find-page-orderby-button" class="ff-btn sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
		
		<button id="table-deleteBtn" class="ff-btn sm btn_delete_batch" table_action="/help/test/deletes.do" data-target="demo_list"><i class="fa fa-remove"></i>&nbsp;&nbsp;删除</button>
		
		<a href="javascript:iframeFullPage('${BasePath !}/help/test/form.do')" class="ff-btn sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
		
		<button class="ff-btn sm white btn-clear-keyword" data-target="demo_list"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>
		
	</form>
	

	<div id="demo_list" class="ff_DataTable"></div>


<script type="text/javascript" src="${BasePath !}/asset/js/pms/help/demo_list.js?v=${ver !}"></script>

<script>
	
	$(document).ready(function(){
	
		var initDT = function(){
		
			var dt_demo_list = new initDataTable({
				div_id: 'demo_list',
				url: rootPath + "/help/test/queryData.do",
				columns:[
					{ data: "name", label: '用户名', class:'text-nowrap', render: function(data){
						return 'Hi, ' + data;
						//return 10;
					}},
					{ data: "age", label: '年龄', data_dict: <@JSONObject dataDict="sys_office_grade"/>},
					{ data: "email", label: '邮件' },
					{ data: "remarks", label: '备注', class:'text-nowrap'},
					{ data: "createBy.name", label: '创建人' },
					{ data: "createDate", label: '创建时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd EE hh:mm:ss'}},
					{ data: "lastUpdateBy.name", label:'最后修改人' },
					{ data: "lastUpdateDate", label: '最后修改时间', class:'text-nowrap'}
				],
				gen_permission: function(){
				
					// SSUI: 生成权限
					var map = [];
					
					<@permission name="pms:demo:viewTest">
						map.push('info');
					</@permission>
					map.push('edit');
					map.push('delete');

					return map;		
				},
				clm_action: function(item){
					// SSUI: 方法 gen_action 的具体逻辑，在外链的 demo_list.js 中定义
					return gen_action(item);
				}/*,
				initFooter: {
					sumup: {
						data: [0],
						decimal:1
					}
				}
				*/
			});
		}

		ffzx.ui(['datatable'], initDT);
		
		ffzx.ui(['datepicker'], function(){
			    
	      	ffzx.init.dateRange({
	      		id_from: 'beginCreateDateStr',
	      		id_to: 'endCreateDateStr'
	      	});
			    
	      	ffzx.init.dateRange({
	      		id_from: 'beginLastUpdateDateStr',
	      		id_to: 'endLastUpdateDateStr',
	      		showTime: true	      		
	      	});
	      	
	      	ffzx.init.dateInput({
	      		id_input: 'birthday'
	      		//, showCalendar: false
	      		//, timeFormat: 'HH:mm'
	      		//, showTime: true
	      	});
		});
	});

</script>
</body>
</html>