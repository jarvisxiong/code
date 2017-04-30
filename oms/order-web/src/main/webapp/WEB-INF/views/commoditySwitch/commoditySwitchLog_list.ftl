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
                 <div class="title-can-purchased" border-bottom:1px solid #CCC; >商品切换日志</div>
                 <table class="table table-hover table-striped bor2 table-common">
                     <thead>
                     <tr>
                         <th width="1px">序号</th>
                         <th>商品标题</th>
                         <th>商品编码</th>
                         <th>商品条形码</th>
                         <th>切换前可销售数量限制</th>
                         <th>切换前区域性</th>
                         <th>切换后可销售数量限制</th>
                         <th>切换后区域性</th>
                         <th>操作时间</th>
                         <th>操作人</th>
                     </tr>
                     </thead>
                     <tbody>
                     <#if commoditySwitchLogList?? >
                      <#list commoditySwitchLogList as item >
                       <tr >
                           <td class="tab-td-center">${(item_index+1)}</td>
                           <td class="tab-td-center">${(item.commodity.title)}</td>
                           <td class="tab-td-center">${(item.commodity.code)}</td>
                           <td class="tab-td-center">${(item.commodity.barCode)}</td>
                           <td class="tab-td-center">
								<#if (item.stockLimitBefore)?? && item.stockLimitBefore == "0">WMS决定商品可销售数量<#else>自定义商品可销售数量</#if>
						   </td>
						   <td class="tab-td-center">
								<#if (item.areaCategoryBefore)?? && item.areaCategoryBefore == "0">区域性商品<#else>非区域性商品</#if>
							</td>
							<td class="tab-td-center">
								<#if (item.stockLimitAfter)?? && item.stockLimitAfter == "0">WMS决定商品可销售数量<#else>自定义商品可销售数量</#if>
						   </td>
						   <td class="tab-td-center">
								<#if (item.areaCategoryAfter)?? && item.areaCategoryAfter == "0">区域性商品<#else>非区域性商品</#if>
							</td>      
                           <td >
                           	<#if item.lastUpdateDate?? >${item.lastUpdateDate?string("yyyy-MM-dd HH:mm:ss") !}<#else></#if>
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