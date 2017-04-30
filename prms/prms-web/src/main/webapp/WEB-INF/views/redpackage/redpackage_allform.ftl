<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>红包新增/编辑</title>
<style type="text/css">
   
</style>	
</head>
<body>
       <div class="navtabs-title">
        <ul class="nav nav-tabs">
            <li class="active"><a key="basicInfo" data-toggle="tab">红包基本信息</a><>
            <li><a key="datacount" data-toggle="tab">数据统计</a><>
            <li><a key="receiverecord" data-toggle="tab">领取记录</a><>
        </ul>
        </div>
		
		
		<div class="tab-content">
			<div class="tab-pane fade in active" id="basicInfo">
				<!--  第一个页签的内容，active是当前  -->
			       <input type="hidden" name="iframeUrl" value="${BasePath !}/redpackage/detail.do?id=${(id !)}">
					<iframe src="" width="100%" height="610px" id="basicInfo" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" ></iframe>
				
			</div>
			<div class="tab-pane fade in" id="datacount">
				<!--  第二个页签的内容，以此类推  -->
			       <input type="hidden" name="iframeUrl" value="${BasePath !}/redpackageCount/datacount.do?redpackageId=${(id)!}">
					<iframe src="" width="100%" height="600px" id="datacount" frameborder="0" scrolling="auto" marginheight="0" marginwidth="0" ></iframe>
			
			</div>
			<div class="tab-pane fade in" id="receiverecord">
				<!--  第3个页签的内容，以此类推  -->
			       <input type="hidden" name="iframeUrl" value="${BasePath !}/redpackageReceive/list.do?redpackageId=${(id) !}">
				<iframe src="" width="100%" height="600px" id="receiverecord" frameborder="0" scrolling="auto" marginheight="0" marginwidth="0" ></iframe>
			</div>
		</div>
           <div>
          <input type="button" class="btn btn-default btn-close-iframeFullPage" value="返回" />
          </div>
                  
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?ver=${ver !}" type="text/css"> 
<script type="text/javascript">
 $(document).ready(function(){
	   $(".nav-tabs a").bind("click",function(){
		   tabChange(this);
	   });
	   divChange();
   });
   
   function tabChange(obj){
	   //先清除所有li样式
	   $(".nav-tabs li").each(function(){
		   $(this).removeClass();
	   });
	   
	   //加上选中li样式
	   $(obj).parent().addClass("active");
	   divChange();
   }
   
   function divChange(){
	   //查出选中li
	   var _key=$(".nav-tabs").find(".active").find("a").attr("key");
	   $(".tab-content .tab-pane").each(function(){
		   $(this).removeClass("active");//先全部隐藏
	   });
	   //显示对应div
	   var _div=$("#"+_key);
	   _div.addClass("active");
	   var _iframe=_div.find("iframe");
	   if(_iframe.attr("src") == ''){
		   _iframe.attr("src",_div.find("input").val());
	   }
   }
    
</script>
</body>
</html>