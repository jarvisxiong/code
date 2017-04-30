<html>
<head>
<meta name="decorator" content="v2" />
<title>角色管理</title>
</head>
<body>

<div class="col-lg-2 zTreeDemoBackground full-height">
    <ul id="left_menu_tree" class="ztree"></ul>
    <div class="openClose" title="显示/隐藏"></div>
</div>

<div class="col-lg-10 zTreeContent full-height">
	<iframe id='myIframeT' src='${BasePath}/menu/tableList.do' frameborder='0' class="sub-iframe"></iframe>     
</div>

<script type="text/javascript">
	var zNodes = ${result};
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
	
	$(document).ready(function(){
		
		ffzx.ui(['ztree'], function(){
			$.fn.zTree.init($("#left_menu_tree"), tree_setting, zNodes);		
		});
	});
	
	function beforeClick(treeId, treeNode) {

		$('#myIframeT').attr('src', rootPath+"/menu/tableList.do?id="+treeNode.id);
		return true;
	}
</script>

</body>
</html>