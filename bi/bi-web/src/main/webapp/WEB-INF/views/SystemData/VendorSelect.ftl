<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<head>
<script type="text/javascript" src="../asset/js/ff/ff.res.js?v=${ver !}"/></script>
</head>
<body style="overflow: hidden;"> 
<div class="row">
       <div class="col-md-12">
           <div class="tab-content">
               <div class="tab-pane fade in active" id="myAccount">
                   <div class="col-md-12">
                        <div class="search">
                        <form id="find-page-orderby-form" action="${BasePath !}/SystemData/VendorSelect.do" method="post">
                            <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
							<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
                            <input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
                            <div class="inquire-ul" style="margin-top:0;">
                                <div class="form-tr">
					                <div class="form-td">
					                    <label>供应商名称：</label>
					                    <div class="div-form">
					                    	<input class="form-control input-sm txt_mid" type="text" id="name" name="name" value="${(vendor.name) !}">
					                    </div>
					                </div>
                                    <div class="form-td">
                                        <button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit">
                                        <i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
                                    </div>
                                </div>                              
                            </div>
                        </form>
                    </div>
                   </div>

               		<!--表格修改2--start-->
                    <table class="table table-hover table-striped bor2 table-common">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>供应商编码</th>
                            <th>供应商名称</th>
                            <th>合作模式</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if vendorList?? >
	                        <#list vendorList as item >
		                        <tr ondblclick="ff.select.select({vendor_id:'${(item.id)!}',vendor_name:'${item.name}'})">
		                            <td>${(item_index+1)}</td>
		                            <td>${(item.code) !}</td>
		                            <td>${(item.name) !}</td>
		                            <td>
		                            	<#if purchaseTypeList?? > 
											<#list purchaseTypeList as items >
												<#if (items.value)==(item.purchaseType)>${(items.label)}</#if>
											</#list>  
										</#if>
		                            </td>
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
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 


</body>

</html>