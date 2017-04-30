<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>商品导入信息提示</title>
</head>
<body>
 <input type="button" class="btn btn-default" value="返回" onclick="history.go(-1);">
<div class="tab-pane fade in active"  style="height:160px; overflow:auto;overflow-x: hidden;">
${msg}
<div>
</body>
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
</html>