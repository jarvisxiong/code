<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>演示添加页面</title>	
</head>
<body>
	<style>.md-loading{display:none;}</style>
	<#include "../common/share_macro.ftl" encoding="utf-8">

	<div id="myAccount">
	        
         <form id="find-page-orderby-form" action="${BasePath !}/catelog/tableList.do" method="post" class="ff-form">
         	<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
            <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
			<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
                
            <div class="form-group">
                <label>关键字：</label>
                <div>
                	<input type="text" id="nameOrNumber" name="nameOrNumber" value="${(data.nameOrNumber) !}">
                </div>                  
            </div>                                 
            <div class="form-group col-span-2">
                <label>是否启用：</label>				                    
                <div>
                    <label class="radio-inline"><input name="isEnable" type="radio" value="1" <#if (data.isEnable) ??><#if data.isEnable == '1'>checked="checked"</#if></#if>/>启用</label>
                    <label class="radio-inline"><input name="isEnable" type="radio" value="0" <#if (data.isEnable) ??><#if data.isEnable == '0'>checked="checked"</#if></#if>/>禁用</label>
                </div>
            </div>

        <button id="find-page-orderby-button" class="ff-btn sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
        <a class="ff-btn sm"  href="javascript:iframeFullPage('${BasePath !}/catelog/form.do')"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
		<button class="ff-btn sm white btn-clear-keyword" data-target="data_list"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>

 </form>
	
	<!-- SSUI: 定义 DataTable 的 HTML 节点 -->
	<div id="data_list" class="ff_DataTable"></div>
         
</div>






<script>
	// SSUI: 生成各种字典
	var dict_isEnable = {"1":"启用", "0":"禁用"};
	var dict_catelogDataType = {"SINGLE":"单条", "BONUS":"多条"};
	var dict_catelogSortType = {"DATE":"日期正序", "DATEDESC":"日期逆序"};

	$(document).ready(function(){
		requirejs(['ff/select2'], function(){
			$('select').select2();
		});
		console.log("==========="+"${(pId) !}");
		ffzx.ui(['datatable'], function(){
		
			var dt_user_list = new initDataTable({
				div_id: 'data_list',
				url: rootPath + "/catelog/listData.do?parent.id=${(pId)!}",
				columns:[
					{ data: "name", label: '名称', class:'text-nowrap'},
					{ data: "number", label: '页签编码'},
					{ data: "pageTab.name", label: '所属页签'},
					{ data: "parent.name", label: '父节点'},
					{ data: "catelogDataType", label: '数据类型', class:'text-nowrap',data_dict:dict_catelogDataType},
					{ data: "catelogSortType", label: '排序方式', class:'text-nowrap',data_dict:dict_catelogSortType},
					{ data: "isEnable", label: '是否启用', class:'text-nowrap', data_dict: dict_isEnable }
				],
				gen_permission: function(){
					var map = [];
					map.push('_delete');
					map.push('edit');
					map.push('isEnable');

					return map;
				},
				clm_action: function(item){
					// SSUI: gen_action() 在外链的 user_list.js 中详细定义
					return gen_action(item);
				}
			});
		});
	});

	
	function gen_action(item) {
		var id = item.id;
		var objAction = {
			edit: [
				{
					label: '编辑',
					href: "javascript: iframeFullPage('"+ rootPath +"/catelog/form.do?id="+ id +"')"
				}
			],
			isEnable: [
				{
					// SSUI: 前面如果有 render 过此列数据，要用转换后的数据来判断！
					label: (item.isEnable == dict_isEnable['1']) ? dict_isEnable['0'] : dict_isEnable['1'],
					href: "javascript:isEnabled('"+id+"', '"+(item.isEnabled == dict_isEnable['1'] ? '0' : '1')+"')"
				}
			],
			_delete: [
				{
					label: '删除',
					href: "javascript:isdelete('"+id+"')"
				}
			]
		};
		
		return objAction;
	}

	function isEnabled(id, type) {
		var titleStr = "启用";
		if (type == 0) {
			titleStr = "禁用";
		}

		$.frontEngineDialog.executeDialog(
				'selMenu', 
				titleStr, 
				'是否确定' + titleStr+ '？', 
				"300px", 
				"35px", 
				function() {
			$.ajax({
				url : rootPath + "/catelog/isEnabled.do",
				data : {
					id : id,
					isEnable : type
				},// 给服务器的参数
				type : "POST",
				dataType : "json",
				async : false,
				cache : false,
				success : function(result) {
					if (result.status == 'success' || result.code == 0) {
						dialog({
							quickClose : true,
							content : '操作成功！'
						}).show();
						
						// SSUI: 重新载入当前页的数据
						//reloadData('data_list');
					    //setTimeout('window.location.href="' + rootPath+ '/catelog/tableList.do"', 1000);
					    setTimeout(function(){parent.reload();}, 1000);
					    
					} else {
						dialog({
							quickClose : true,
							content : result.msg
						}).show();
					}
				}
			});
		});
	}

	function isdelete(id) {
	    $.frontEngineDialog.executeDialog(
	            'isdelete', 
	            '删除', 
	            '是否确定删除？', 
	            "300px", 
	            "35px", 
	            function() {
	        $.ajax({
	            url : rootPath + "/catelog/delete.do",
	            data : {
	                id : id
	            },// 给服务器的参数
	            type : "POST",
	            dataType : "json",
	            async : false,
	            cache : false,
	            success : function(result) {
	                if (result.status == 'success' || result.code == 0) {
	                    dialog({
	                        quickClose : true,
	                        content : '操作成功！'
	                    }).show();
	                    setTimeout(function(){parent.reload();}, 1000);
	                    //setTimeout('window.location.href="' + rootPath+ '/catelog/tableList.do"', 1000);
	                } else {
	                    dialog({
	                        quickClose : true,
	                        content : result.infoStr
	                    }).show();
	                }
	            }
	        });
	    });
	}


	var setting = {
		    check : {enable : true,chkboxType : {"Y" : "ps", "N" : "ps"} },
			view:{selectedMulti: false},
		    data : {simpleData : { enable : true}}};

	var setting1 = {
		    check : {enable : true,chkboxType : {"Y" : "", "N" : ""} },
			view:{selectedMulti: false},
		    data : {simpleData : { enable : true}}};



	$(document).ready(function(){
		
		ffzx.ui(['treetable', 'ztree', 'dialog'], function(){	
			var option = {
					theme : 'vsStyle',
					expandLevel : 6
				};
		});
	});
	
</script>

</body>
</html>