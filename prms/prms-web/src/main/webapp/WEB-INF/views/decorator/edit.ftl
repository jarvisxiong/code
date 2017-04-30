<#assign sitemesh=JspTaglibs["http://www.opensymphony.com/sitemesh/decorator"] />

<#include "../global.ftl" encoding="utf-8">

<!DOCTYPE html>
<html class="${sys !} ${mod !}">
<head>
	<meta charset="utf-8">
	<title><@sitemesh.title /> - 非凡之星管理平台</title>
	
	<#include "../common/css.ftl" encoding="utf-8"> 

	<!-- 可选样式  strat -->
	<link href="${BasePath !}/asset/js/control/validation/css/validation.css" rel="stylesheet">
	<!-- 可选样式  end -->
	
	<#include "../common/js.ftl" encoding="utf-8">  
	
	<!-- 可选js  strat -->
	<script src="${BasePath !}/asset/js/control/validation/jquery.validate.js" type="text/javascript"></script>
	<script src="${BasePath !}/asset/js/control/validation/localization/messages_zh.js" type="text/javascript"></script>
	<script src="${BasePath !}/asset/js/control/validation/validationSetDefaults.js" type="text/javascript"></script>
	<script src="${BasePath !}/asset/js/control/validation/validationUtils.js" type="text/javascript"></script>
	<script src="${BasePath !}/asset/js/control/validation/additionalMethods.js" type="text/javascript"></script>
	<!-- 可选js  strat -->
	
	<!-- 时间控件 -->
    <script src="${BasePath !}/asset/js/control/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	
	<@sitemesh.head />
</head>
<body>
<div class="row">
	<div class="col-md-12">
		<div class="box-body">	
			<@sitemesh.body />
		</div>
	</div>
</div>
</body>
</html>