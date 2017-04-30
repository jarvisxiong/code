<#include "../global.ftl" encoding="utf-8">

<!DOCTYPE html>
<html class="${sys !} ${mod !}">
<head>
	<title>演示My97DatePicker页面</title>
    <script src="${BasePath !}/asset/js/control/My97DatePicker/WdatePicker.js?v=${ver !}" type="text/javascript"></script>
<style type="text/css">
.STYLE1 {
	color: #FF0000;
}
</head>
<body>
<div class="row">
       <div class="col-md-12">
         <div class="box-body">
           <div class="tab-content">
           <form method="post" action="${BasePath !}/testsubmit.do">
		<p>
			<input class="Wdate" name="dateStr1" type="text" onClick="WdatePicker()"
				value="<#if (dateStr1)??>${dateStr1?string("yyyy-MM-dd HH:mm:ss")}</#if>">
			<font color=red>&lt;- 点我弹出日期控件</font> <br>当前输入日期:
			<#if (dateStr1)??>${dateStr1?string("yyyy-MM-dd HH:mm:ss")}</#if>
		</p>


		<p>
			<input type="text" class="Wdate" name="dateStr2"
				onfocus="WdatePicker({dateFmt:'H:mm:ss',minDate:'8:00:00',maxDate:'11:30:00'})" /><br />

			&lt;input type=&quot;text&quot; class=&quot;Wdate&quot;
			id=&quot;d414&quot; onfocus=&quot;WdatePicker({<span class="STYLE2">dateFmt:</span>
			<span class="STYLE1">'H:mm:ss',</span><span class="STYLE2">minDate:</span><span
				class="STYLE1">'8:00:00', </span><span class="STYLE2">maxDate:</span><span
				class="STYLE1">'11:30:00'</span>})&quot;/&gt; <br> 当前输入时间:
			<#if (dateStr2)??>${dateStr2?string("HH:mm:ss")}</#if>
		</p>

		<p>
			<input type="text" style="width: 200px" name="dateStr3"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				value="<#if (dateStr3)??>${dateStr3?string("yyyy-MM-dd HH:mm:ss")}</#if>" />
			当前输入时间:<#if (dateStr3)??>${dateStr3?string("yyyy-MM-dd HH:mm:ss")}</#if>
			<font color=red>&lt;- 修改当前登录用户的LastLoginTime登录时间</font>
		</p>
		</p>
		<input type="submit" value="   提交   ">
	</form>
	在线演示和使用说明
	<a href="http://www.my97.net/dp/demo/" target="_blank">http://www.my97.net/dp/demo/</a>

	<br>
	<pre>
使用方法:
1. 去官方网站看看,你当前下载的是否是最新的版本,很多bug都是因为使用的不是最新版本造成的
官方主页:<a href="http://www.my97.net" target="_blank">http://www.my97.net</a>

2. 将My97DatePicker整个目录包,放入您的项目的相应目录下
My97DatePicker目录下各文件的作用:
  1.1 My97DatePicker目录是一个整体,不可破坏里面的目录结构,也不可对里面的文件改名,可以改目录名
  1.2 My97DatePicker.htm是必须文件,不可删除
  1.3 各目录及文件的用途:
    WdatePicker.js 配置文件,在调用的地方仅需使用该文件,可多个共存,以xx_WdatePicker.js方式命名
    config.js 语言和皮肤配置文件,无需引入
    calendar.js 日期库主文件,无需引入
    My97DatePicker.htm 临时页面文件,不可删除
    目录lang 存放语言文件,你可以根据需要清理或添加语言文件
    目录skin 存放皮肤的相关文件,你可以根据需要清理或添加皮肤文件包

3. 您可以根据您自己的需要,删除不必要的皮肤和语言文件

---------------------------------------------------------------------
官方主页
<a href="http://www.my97.net" target="_blank">http://www.my97.net</a>

在线演示和使用说明
<a href="http://www.my97.net/dp/demo/" target="_blank">http://www.my97.net/dp/demo/</a>

皮肤中心:
<a href="http://www.my97.net/dp/skin.asp" target="_blank">http://www.my97.net/dp/skin.asp</a>

技术支持页面
<a href="http://www.my97.net/dp/support.asp" target="_blank">http://www.my97.net/dp/support.asp</a>
        
           
           </div>
         </div>
       </div>
   </div>
</body>
</html>