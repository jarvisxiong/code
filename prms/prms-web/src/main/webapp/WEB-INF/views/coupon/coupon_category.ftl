<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">

<body style="overflow: hidden;"> 
<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
<div class="row">
       <div class="col-md-12">
        <div class="zTreeDemoBackground left ztree_table_zt"  >
            <ul id="left_menu_tree" class="ztree"></ul>
        </div>
         <div id="openClose" class="closeac" ></div>
         <div class="box-body ztree_table"  >
       
           <div class="tab-content">
               <div class="tab-pane fade in active" id="myAccount">
                   <div class="col-md-12">
                        <div class="search">
                    </div>
                   </div>

               		<!--表格修改2--start-->
                    <table class="table table-hover table-striped bor2 table-common">
                        <thead>
                        <tr>
                            <th width="1px"></th>
                            <th width="15px"><input type="checkbox"></th>                           
                            <th>名称</th>
                            <th>编码</th>
                            <th>描述</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if categoryList?? >
                        <#list categoryList as item >
                        <tr>
                            <td>${(item_index+1)}</td>
                            <td><input type="checkbox" name="check" value="${(item.id) !}"  coupon="${(item.name)! }" ></td>                            
                            <td>${(item.name) !}</td>
                            <td>${(item.code) !}</td>
                            <td>${(item.remarks) !}</td>                        
                        </tr>
                        </#list>
                        </#if>
                        </tbody>
                    </table>
                    
                    <#include "../common/page_macro.ftl" encoding="utf-8"> 
						<@my_page pageObj/>                   
               </div>               
           </div>
         </div>
       </div>
   </div>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_category.js?v=${ver !}"></script>
<script type="text/javascript">
$(function() {
   		 ztreeInit(${result});    
        $(".input-select").select2();
});
</script>
</body>
<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/> 
</html>