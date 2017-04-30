<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>商品导入信息提示</title>
</head>
<body>
 <input type="button" class="btn btn-default" value="返回" onClick="history.go(-1);"> 
 <#if errorVaildate?? && (errorVaildate!="")>
 <font color="red">excel手机号码必须为文本格式</font>
 </#if>
<div class="tab-pane fade in active"  style="height:160px; overflow:auto;overflow-x: hidden;">
${message !}<br />
${errorVaildate!}
<div>
<script type="text/javascript">

var valuedate="${date !}";

</script>
<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_importmsg.js"></script>

</body>
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">

</html>