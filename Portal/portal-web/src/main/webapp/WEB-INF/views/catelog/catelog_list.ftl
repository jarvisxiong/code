<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>栏目列表</title>
</head>
<body>

<div class="col-lg-2 zTreeDemoBackground left ztree_table_zt full-height">
    <ul id="left_menu_tree" class="ztree"></ul>
    <div class="openClose" title="显示/隐藏"></div>
</div>

<div class="col-lg-10 zTreeContent full-height">
	 <iframe id='myIframeT' src='${BasePath}/catelog/tableList.do' frameborder='0' class="sub-iframe"></iframe> 
</div>

<script type="text/javascript">
$(function() {
	console.log(${result});
	  ffzx.ui(['ztree'], function(){
		    ztreeInit(${result});
		});  
		 //initTreeData();
});

// SSUI：访问 iframe 中的 DOM，重新载入列表
function beforeClick(treeId, treeNode) {
	$("#myIframeT").attr("src",'${BasePath}/catelog/tableList.do?id='+treeNode.id);
	//FFZX.clickOnTree('myIframeT', treeId, treeNode);
	return true;
}

function reload(){
	location.reload(); 
}

function initTreeData(){
	$.ajax({
		url : rootPath + "/catelog/ajaxTreeData.do",
		type : "POST",
		dataType : "json",
		async : false,
		cache : false,
		success : function(result) {
			  var data = result.infoData;
			  console.log(data);
			  ffzx.ui(['ztree'], function(){
				    ztreeInit(data);
				});  
		}
	});
}

</script>

</body>
</html>