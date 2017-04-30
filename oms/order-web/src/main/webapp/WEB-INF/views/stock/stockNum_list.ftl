<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<body style="overflow: hidden;">
<ul class="nav nav-tabs" style="padding-left: 1%;">
    <li class="active"><a href="#">查看日志</a></li>
</ul>
   <div class="col-md-12">
     <div class="box-body ztree_table" style="height:90%;">
      <div class="row" style="float: left; margin-left: 0px;width: 100%;">
                                 
             <div class="box-header" style="float: left;padding-left: 0px;width: 99%; ">
                      <!--表格修改2--start-->
                <!--表格修改2--start-->
                 <div class="title-can-purchased" border-bottom:1px solid #CCC; >库存操作日志</div>
                 <table class="table table-hover table-striped bor2 table-common">
                     <thead>
                     <tr>
                         <th width="1px">序号</th>
                         <th>处理时间</th>
                         <th>处理信息</th>
                         <th>操作人</th>
                     </tr>
                     </thead>
                     <tbody>
                     <#if stockNumLogList?? >
                      <#list stockNumLogList as item >
                       <tr >
                           <td >${(item_index+1)}</td>
                           <td >
                           	<#if item.handleDate?? >${item.handleDate?string("yyyy-MM-dd HH:mm:ss") !}<#else></#if>
                           </td>
                           <td >
                           	${(item.handleMsg)}
                           </td>
                           <td >
                          	 ${(item.opUser)}
                           </td>
                       </tr>
                      </#list>
                     </#if>
                     </tbody> 
                 </table>
                 </div>  
                  <div class="form-tr">
                      <div class="btn-div">
                          <input type="button" class="btn btn-default" value="返回" onclick="history.go(-1);">
                      </div>
                  </div>
             </div>
         </div>
   	</div>
</div>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
<script type="text/javascript">

</script>
</body>
</html>