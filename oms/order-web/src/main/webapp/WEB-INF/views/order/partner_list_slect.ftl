<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<body> 
<div class="row">
       <div class="col-md-12">
           <div class="tab-content">
               <div class="tab-pane fade in active" id="myAccount">
                   <div class="col-md-12">
                        <div class="search">
                        <form id="find-page-orderby-form" action="${BasePath !}/order/partnerlistSelect.do" method="post">
                            <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
                            <input type="hidden" name="addressId" value="${(addressId) !}" />
							<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
                            <input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
                            <div class="inquire-ul" style="margin-top:0;">
                                <div class="form-tr">
					                <div class="form-td">
					                	<label>编号：</label>
					                    <div class="div-form"><input class="form-control input-sm txt_mid" type="text" id="partnerCode" name="partnerCode" value="${(partner.partnerCode) !}"></div>
					                </div>
					                <div class="form-td">
					                    <label>姓名：</label>
					                    <div class="div-form"><input class="form-control input-sm txt_mid" type="text" id="name" name="name" value="${(partner.name) !}"></div>
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
                            <th>编号</th>
                            <th>姓名</th>
                            <th>手机号码</th>
                            <th>地址</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if partnerList?? >
	                        <#list partnerList as item >
		                        <tr id="${(item.id)!}"  name="${(item.name)!}" phone="${(item.mobilePhone) !}"  addressId = "${(item.address.id) !}" addrssInfo="${(item.addressName) !}${(item.address.name) !}${(item.addressDeal) !}"  partnerCode="${(item.partnerCode) !}"  ondblclick="getSelected(this);">
		                            <td>${(item_index+1)}</td>
		                            <td>${(item.partnerCode) !}</td>
		                            <td>${(item.name) !}</td>
		                            <td>${(item.mobilePhone) !}</td>
		                            <td>${(item.addressName) !}${(item.address.name) !}${(item.addressDeal) !}</td>
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
<script type="text/javascript" src="${BasePath !}/asset/js/basedata/partner/partner_list.js?v=${ver !}"></script>
<script type="text/javascript">

//弹窗页面返回结果
function getSelected(obj) {
	//父窗口调用扩展
	window.parent.getSelectPartner(obj);
}
</script>
<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/>
</body>
</html>