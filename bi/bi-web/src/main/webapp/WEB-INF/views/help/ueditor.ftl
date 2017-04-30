<#include "../global.ftl" encoding="utf-8">

<!DOCTYPE html>
<html class="${sys !} ${mod !}">
<head>
	<title>演示ueditor页面</title>
</head>
<body>
<div class="row">
       <div class="col-md-12">
         <div class="box-body">
           <div class="tab-content">
	           <script type="text/javascript">
					function getPath(){
						return "${BasePath !}";
					}
				</script>
		    	<script type="text/javascript" charset="utf-8" src="${BasePath !}/asset/js/control/ueditor/ueditor.config.js?v=${ver !}"></script>
		    	<script type="text/javascript" charset="utf-8" src="${BasePath !}/asset/js/control/ueditor/ueditor.all.min.js?v=${ver !}"> </script>
		    
		    	<script id="editor" type="text/plain" style="width:1024px;height:500px;">分公司的风格</script>  
				<script type="text/javascript">var ue = UE.getEditor('editor');</script>             
   
           </div>
         </div>
       </div>
   </div>
</body>
</html>