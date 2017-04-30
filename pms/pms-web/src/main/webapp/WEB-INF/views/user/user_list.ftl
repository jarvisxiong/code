<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>演示添加页面</title>
</head>
<body>

<div class="col-lg-2 zTreeDemoBackground left ztree_table_zt full-height">
    <ul id="left_menu_tree" class="ztree"></ul>
    <div class="openClose" title="显示/隐藏"></div>
</div>

<div class="col-lg-10 zTreeContent full-height">
	<iframe id='myIframeT' src='${BasePath}/user/tableList.do' frameborder='0' class="sub-iframe"></iframe>     
</div>

<script type="text/javascript">

$(function() {

	ffzx.ui(['ztree'], function(){
	
	    ztreeInit(${result});
	});	
});

// SSUI：访问 iframe 中的 DOM，重新载入列表
function beforeClick(treeId, treeNode) {

	FFZX.clickOnTree('myIframeT', treeId, treeNode);
	return true;
}

</script>

</body>
</html>