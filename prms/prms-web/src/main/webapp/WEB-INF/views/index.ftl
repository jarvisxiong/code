<#include "global.ftl" encoding="utf-8">

<!DOCTYPE html>
<html class="${sys !} ${mod !}">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <meta name="description" content="">
    <meta name="author" content="">
	<title>非凡之星管理平台</title>
	
	<#include "common/css.ftl" encoding="utf-8"> 

	<!-- 可选样式  strat -->
	<link href="${BasePath !}/asset/js/control/pagetab/css/jquery.pagetab.css" rel="stylesheet">
	<!-- 可选样式  end -->
	
	

	<#include "common/js.ftl" encoding="utf-8"> 

	<!-- 可选js  strat -->
	<script src="${BasePath !}/asset/js/control/pagetab/js/jquery.pagetab.js"></script>
	<!-- 可选js  strat -->
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<#include "header.ftl" encoding="utf-8"> 
	
		<#include "menu.ftl" encoding="utf-8">
	
		<#include "content.ftl" encoding="utf-8">  
	
		<#include "foot.ftl" encoding="utf-8">
		
		<!-- Add the sidebar's background. This div must be placed immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS -->
	<!-- AdminLTE App -->
	
	<script src="${BasePath !}/asset/js/control/dist/js/app.min.js"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/layer-v1.9.2/layer/layer.js"></script>
	
	<input type="hidden" name="ctx" id="ctx" value="${BasePath !}"/>
	<script src="${BasePath !}/asset/js/index.js"></script>
	
	
	
	
	
	
</body>
</html>