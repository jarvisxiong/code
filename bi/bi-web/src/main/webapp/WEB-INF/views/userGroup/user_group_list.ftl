<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<head>
<#include "../common/css.ftl" encoding="utf-8">
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css">
</head>
<body style="overflow: hidden;"> 
<div class="row">
    <div class="col-md-12">
        <div class="zTreeDemoBackground left ztree_table_zt"  >
            <ul id="left_tree" class="ztree"></ul>
        </div>
        <div id="openClose" class="closeac" ></div>
        <div class="box-body ztree_table"  >
        	<iframe id='myIframeT' src='${BasePath}/userGroup/tableList.do'
            width='100%' height='100%' frameborder='0'></iframe>  
        </div>
    </div>
</div>

<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">

<script type="text/javascript">

	$(function() {
		ztreeInit(${result});   
		
		function ztreeInit(zNodes){
			var tree_setting = {
				data : {
					simpleData : {
						enable : true
					},
					key:{
						url:""
					}
				},
				view:{
					selectedMulti: false
				},
				callback : {
					beforeClick : beforeClick
				}
			};
			$.fn.zTree.init($("#left_tree"), tree_setting, zNodes);	
		}
		
		/**
		 * openClose 显示和隐藏树形栏
		 */
		$("#openClose").click(function () {
			if ($(this).hasClass("closeac")) {
				$(this).removeClass("closeac");
				$(this).addClass("open");
				
				$(".zTreeDemoBackground .ztree").addClass("disalyact");
				$(".box-body").addClass("disalyact");
				$(".zTreeDemoBackground").addClass("disalyact");
			
			} else {
				$(this).addClass("closeac");
				$(this).removeClass("open");
				
				
				$(".zTreeDemoBackground .ztree").removeClass("disalyact");
				$(".box-body").removeClass("disalyact");
				$(".zTreeDemoBackground").removeClass("disalyact");
			   
			}
		});
		
	});

	function beforeClick(treeId, treeNode) {
		$('#myIframeT').attr('src', rootPath+"/userGroup/tableList.do?id="+treeNode.id);
		return true;
	}

</script>

<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/> 

</body>
</html>