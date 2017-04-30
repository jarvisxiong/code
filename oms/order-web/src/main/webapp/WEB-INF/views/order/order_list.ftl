<html>
<head>
<meta name="decorator" content="list" />
<title>订单列表</title>
</head>
<body > 
<div class="box-body">
<#assign pageAnswer="http://${(orderDomain) !}:${(orderPort) !}/order-web/order/list.do"/>
    <div class="tab-content">
             <form id="find-page-orderby-form" action="${BasePath !}/order/list.do" method="post">
             <input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
			 <input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
			 <input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
			  <input id="_find-page-totalCount" type="hidden" name="totalCount" value="${(pageObj.totalCount) !}" />
			 
			 <input id="queryType" name="queryType"  type="hidden" value="${(params.queryType)!}" />
			 <input id="isPerson" name="isPerson" type="hidden" value="0" />
			 <input id="isErrorOrder" name="isErrorOrder" type="hidden" value="0" />
			 
            <div class="inquire-ul" style="margin-top:0;">
                <div class="form-tr">
                    <div class="form-td">
                        <label>订单编号：</label>
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid"  id="orderNo" onblur="this.value=Trim(this.value);" name="orderNo_like" value="${(params.orderNo_like) !}" />
                        </div>
                    </div>
                    <!-- <div class="form-td">
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid3"  id="createDateRange" name="createDateRange" value="" placeholder="下单时间：2016/04/01 00:00:00-2016" >
                        </div>
                    </div> -->
                    <div class="form-td">
                        <label>下单时间：</label>
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid" readonly = "readonly"  id="createDateStart" name="createDateStart" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});changeQueryTypeByTime();" value="${(params.createDateStart)!}"/>
                            		 至 
                            <input type="text" class="form-control input-sm txt_mid" readonly = "readonly"  id="createDateEnd" name="createDateEnd"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});changeQueryTypeByTime();" value="${(params.createDateEnd)!}"/> 
                        </div>
                    </div>
                    <!-- <div class="form-td">
                         <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid3"  placeholder="确认收货时间：不限" >
                        </div> 
                    </div> -->
                    <div class="form-td">
                        <label>配送员：</label>
                        <div class="div-form">
                            <div class="f7">
                            	
                            	<input type="hidden" id="sendPerson" name="sendPerson" value="${(params.sendPerson) !}" />
                                <input class="form-control input-sm txt_mid" type="text" id="sendPersonName" value="${(params.sendPersonName) !}" readonly="readonly" name="sendPersonName" readonly="readonly">
                                <span class="selectDel" onclick="$(this).parent().find('input').val('');">×</span> 
                                 <span class="selectBtn" onclick='selectPartner();'>选</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-tr">
                    <div class="form-td">
                        <label>手机号码：</label>
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid" id="memberPhone" onblur="this.value=Trim(this.value);" name="memberPhone_like" value="${(params.memberPhone_like) !}" />
                        </div>
                    </div>
                    <div class="form-td">
                        <!-- <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid3" placeholder="支付时间：不限" >
                        </div> -->
                        <label>支付时间：</label>
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid" readonly = "readonly"   id="payTimeStart" name="payTimeStart" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});changeQueryTypeByTime();" value="${(params.payTimeStart) !}"/>
                                        	  至 
                            <input type="text" class="form-control input-sm txt_mid" readonly = "readonly"    id="payTimeEnd"  name="payTimeEnd"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});changeQueryTypeByTime();" value="${(params.payTimeEnd)!}"/>
                        </div>
                    </div>
                    <!-- <div class="form-td">
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid3" placeholder="申请售后时间：不限" >
                        </div>
                    </div> -->
                    <div class="form-td">
                        <label>售后状态：</label>
                        <div class="div-form">
                            <select class="sec1" name="orderDetailStatus">
                             <option value="">全部</option>
                             <#if afterSaleStatusList ??>
                                   <#list afterSaleStatusList as item > 
                                      <option value="${item.value}"  <#if (params.orderDetailStatus) ??><#if params.orderDetailStatus == item.value>selected="selected"</#if></#if> >${item.name}</option>
                                    </#list>
                             </#if>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-tr">
                    <div class="form-td">
                        <label>商品名称：</label>
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid" id="commodityTitle_like" onblur="this.value=Trim(this.value);" name="commodityTitle_like" value="${(params.commodityTitle_like) !}" />
                        </div>
                    </div>
                    <div class="form-td">
                        <label>sku条形码：</label>
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid" id="skubarcode_like" onblur="this.value=Trim(this.value);" name="skubarcode_like" value="${(params.skubarcode_like) !}" />
                        </div>
                    </div>
                    
                    <div class="form-td">
                        <label>收货地址：</label>
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid" id="addressInfo_like" onblur="this.value=Trim(this.value);" name="addressInfo_like" value="${(params.addressInfo_like) !}"/>
                        </div>
                    </div>
                    <div class="form-td">
                        <div class="div-form">
                            <label class="radio-inline" style="width: auto;" onclick="checkAllocationError();" ><input type="checkbox" name="allocationError" id="allocationError" value="1"  <#if (params.allocationError) ??><#if params.allocationError == '1'>checked="checked"</#if></#if>> 订单分配错误</label>
                        </div>
                    </div>
                    <div class="form-td">
                        <div class="div-form">
                            <label class="radio-inline" style="width: auto;" onclick="checkIsInvoice();" ><input type="checkbox" name="isInvoice" id="isInvoice" value="1"  <#if (params.isInvoice) ??><#if params.isInvoice == '1'>checked="checked"</#if></#if>>要发票</label>
                        </div>
                    </div>
                    
                    <div class="form-td">
                        <div class="div-form">
                            <label class="radio-inline" style="width: auto;" onclick="checkCommodityType();" ><input type="checkbox" name="commodityType" id="commodityType" value="1"  <#if (params.commodityType) ??><#if params.commodityType == '1'>checked="checked"</#if></#if>>虚拟商品</label>
                        </div>
                    </div>
                </div>
                <div class="form-tr">
                    <div class="form-td">
                        <label>订单状态：</label>
                        <div class="div-form" >
                            <select class="sec1" name="status" id="status" onchange="onChangeStatus();">
                            <!-- <option value="L3MONTH">近三个月订单</option> -->
                            <option value="">全部</option>
                             <#if orderStatusList ??>
                                   <#list orderStatusList as item > 
                                      <option value="${item.value}"  <#if (params.status) ??><#if params.status == item.value>selected="selected"</#if></#if> >${item.name}</option>
                                    </#list>
                             </#if>
                             </select>
                        </div>
                    </div>
                    <div class="form-td">
                        <label>购买类型：</label>
                        <div class="div-form">
                            <select class="sec1" name="buyType" >
                            <option value="">全部</option>
                            <#if buyTypeList ??>
                                   <#list buyTypeList as item > 
                                      <option value="${item.value}"  <#if (params.buyType) ??><#if params.buyType == item.value>selected="selected"</#if></#if> >${item.name}</option>
                                    </#list>
                             </#if>
                            </select>
                        </div>
                    </div>
                    <div class="form-td">
                        <label>支付金额：</label>
                        <div class="div-form">
                            <input type="text" class="form-control input-sm txt_mid2"  onblur="this.value=Trim(this.value);" name="actualPriceStart" value="${(params.actualPriceStart) !}" /> 至 <input type="text" class="form-control input-sm txt_mid2" onblur="this.value=Trim(this.value);" name="actualPriceEnd" value="${(params.actualPriceEnd) !}"/>
                        </div>
                    </div>
                    <div class="form-td">
                        <div class="div-form">
                            <label class="radio-inline" style="width: auto;"><input type="checkbox" name="checkPerson" id="checkPerson" value="0"> 配送员为空</label>
                        </div>
                    </div>
                    <div class="form-td">
                        <div class="div-form">
                            <label class="radio-inline" style="width: auto;"><input type="checkbox" name="supplyFlag" id="supplyFlag" value="0"> 不补货</label>
                        </div>
                    </div>
                    
                </div>
                <!-- <div class="form-tr">
                    <div class="form-td">
                        <label>物流状态：</label>
                        <div class="div-form">
                            <select class="sec1"><option>全部</option></select>
                        </div>
                    </div>
                </div> -->
            </div>

            <div class="btn-div3">
                <button id="find-page-orderby-button" class="btn btn-primary btn-sm" type="button" onclick="new_searchData();"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
                <a href="${BasePath !}/order/list.do" class="btn btn-primary btn-sm"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a>
                <@permission name="oms:order:batchexport">
                <a href="javascript:void(0);" onclick="importList();" class="btn btn-primary btn-sm"><i class="fa fa-pencil"></i>&nbsp;&nbsp;批量导出</a>
                </@permission>
                <@permission name="oms:order:printer">
                <a href="javascript:void(0);" onclick="printBatch();" class="btn btn-primary btn-sm"><i class="fa fa-print"></i>&nbsp;&nbsp;批量打印订单</a>
                </@permission>
                <@permission name="oms:order:words">
                <a href="javascript:void(0);" onclick="toComments();" class="btn btn-primary btn-sm"><i class="fa fa-pencil"></i>&nbsp;&nbsp;批量客服留言</a>
                </@permission>
                <@permission name="oms:order:importbuildorder"> 
				<a href="javascript:void(0);"  id="import_button" class="btn btn-primary btn-sm" ><i class="fa fa-plus" ></i>&nbsp;&nbsp;导入成单</a>
                </@permission>
            </div>
        </form>
        <div class="mgb20"></div>
        <div class="navtabs-title">
            <ul class="nav nav-tabs" id="ul_tab">
                <li key="L3MONTH" onclick="tab2submit(this);"><a href="#dataList" data-toggle="tab">近三个月订单</a></li> 
                <li key="" onclick="tab2submit(this);"><a href="#dataList" data-toggle="tab">全部</a></li>
                <li key="0" onclick="tab2submit(this);"><a href="#dataList" data-toggle="tab">待付款</a></li>
                <li key="1" onclick="tab2submit(this);"><a href="#dataList" data-toggle="tab">待收货</a></li>
                <li key="2" onclick="tab2submit(this);"><a href="#dataList" data-toggle="tab">退款申请中</a></li>
                <li key="3" onclick="tab2submit(this);"><a href="#dataList" data-toggle="tab">交易关闭</a></li>
                <li key="4" onclick="tab2submit(this);"><a href="#dataList" data-toggle="tab">交易完成</a></li>
                <li key="5" onclick="tab2submit(this);"><a href="#dataList" data-toggle="tab">订单已取消</a></li>
                <li class="simple-pagination">
                    <div class="simple-pagination-div">
                        <button class="btn btn-primary btn-sm" onclick="$('#example2_previous').find('a').click();" <#if pageObj.pageIndex==1 >disabled</#if> ><i class="fa fa-angle-left"></i>&nbsp;&nbsp;上一页</button>
                        <button class="btn btn-primary btn-sm" onclick="$('#example2_next').find('a').click();" <#if !pageObj.hasNext >disabled</#if> >下一页&nbsp;&nbsp;<i class="fa fa-angle-right"></i></button>
                    </div>
                </li>
            </ul>
        </div>

        <div class="order-table-head mgb10">
            <table class="table table-common">
                <colgroup data-reactid="tab-order-0" align="center">
                    <col class="tab-order-0-col1">
                    <col class="tab-order-0-col2" align="center">
                    <col class="tab-order-0-col3">
                    <col class="tab-order-0-col4">
                    <col class="tab-order-0-col5">
                    <col class="tab-order-0-col6">
                    <col class="tab-order-0-col7">
                    <col class="tab-order-0-col8">
                </colgroup>
                <thead>
                <tr>
                    <th><div style="margin-right: 5px; border-right: 1px solid #ccc; display: inline-block; width: 30px; text-align: left;"><input id="th_check" type="checkbox"></div> 商品信息 </th>
                    <th>单价(元)</th>
                    <th>数量</th>
                    <th>售后</th>
                    <th>买家</th>
                    <th>订单状态</th>
                    <th>实收款(元)</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>

        <div class="tab-content">
            <div class="tab-pane fade in active" id="dataList">
            
            <!-- 订单行Start  -->
             <#if list?? >
                        <#list list as item >
                <table  class="table table-order mgb10" id="${(item.id) !}">
                    <colgroup>
                        <col class="tab-order-1-col0">
                        <col class="tab-order-1-col1">
                        <col class="tab-order-1-col2">
                        <col class="tab-order-1-col3">
                        <col class="tab-order-1-col4">
                        <col class="tab-order-1-col5">
                        <col class="tab-order-1-col6">
                        <col class="tab-order-1-col7">
                        <col class="tab-order-1-col8">
                    </colgroup>
                    <thead>
                    <tr>
                        <th colspan="8">
                            <input phone="${(item.memberPhone) !}" id="${(item.id) !}" type="checkbox">
                            <div class="tab-order-num" onclick="toOrderView('${(item.id) !}');" >订单编号：<span style="cursor:pointer;">${(item.orderNo) !}</span></div>
                            <div>下单时间：<span><#if (item.createDate) ??>${(item.createDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if></span></div>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- 订单明细列 Start  -->
                     <#if item.detailList?? >
                        <#list item.detailList as detailItem >
                    <tr>
                        <td>
                            <div class="order-goods">
                                <img <#if (detailItem.commodityImage) ??> src="${(image_path)!}/${detailItem.commodityImage?replace('size','origin')}"</#if> onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" alt="商品图片"/>
                                <div class="order-goods-text">
                                    <p class="goods-title">${(detailItem.commodityTitle)!}</p>
                                    <p class="goods-barcode">条形码：${(detailItem.commodityBarcode) !}</p>
                                    <p class="goods-attr">${(detailItem.commodityAttributeValues) !}</p>
                                </div>
                            </div>
                        </td>
                        <td valign="middle">${(detailItem.actSalePrice) !}</td>
                        <td>${(detailItem.buyNum) !}</td>
                        <td>
                        	<#if (detailItem.orderDetailStatus) ??>
								<#if detailItem.orderDetailStatus == "0">
									正常
								<#elseif detailItem.orderDetailStatus == "1">
									退款处理中
								<#elseif detailItem.orderDetailStatus == "2">
									换货处理中
								<#elseif detailItem.orderDetailStatus == "3">
									退款成功
								<#elseif detailItem.orderDetailStatus == "4">
									换货成功
								</#if>
							</#if>
						</td>
                      	<#if (detailItem_index==0) >
                       	<td <#if item.detailList??>rowspan="${item.detailList?size}"> <a href="http://${(ucDomain)!}:${(ucPort)!}/uc-web/member/form.do?id=${(item.memberId) !}&viewstatus=view&pageType=${pageAnswer!}">${(item.memberPhone) !}</a></#if></td>
                        <td <#if item.detailList??>rowspan="${item.detailList?size}"</#if>>${(item.statusName)!}</td>
                        </#if>
                        <td>${(detailItem.actPayAmount) !}</td>
                        <#if (detailItem_index==0) >
                        <td <#if item.detailList??>rowspan="${item.detailList?size}"</#if>>
                        <#if item.status=='0'>
                        <!--<a href="javascript:void(0);" onclick="updatePrice('${(item.id) !}');">改价</a> | --> 
                        <@permission name="oms:order:editAddress">
                        <a href="javascript:void(0);" onclick="updateAddr('${(item.id) !}');" >改地址</a> 
                        </@permission>
                        <@permission name="oms:order:words">
                        | <a href="javascript:void(0);" onclick="toComment('${(item.memberPhone) !}');">客服留言</a> 
                        </@permission>
                        <@permission name="oms:order:cancelorder">
                        | <a href="javascript:void(0);" onclick="javascript:cancelOrder('${item.id}');" >取消订单</a>
                        </@permission>
                        <#elseif item.status=='1'>
                        
                        <@permission name="oms:order:printer">
                        <a href="javascript:void(0);" onclick="toPrintView('${(item.id) !}');">打印</a>
                        </@permission>
                        <@permission name="oms:order:viewRecord">
                        | <a href="javascript:void(0);"  title="${(item.orderNo) !}"  key="viewWms" orderNo="${(item.orderNo) !}"  class="viewlogistics logistics-hover" >查看物流
						<div class="logisticspage" style="height:200px;"  id="status${(item.orderNo) !}"></div>
						</a> 
						</@permission>
						<#if !(item.curLoisticsStatus??)>
						<!-- 待收货-未参与调拨 -->
						<@permission name="oms:order:editAddress">
						|<a href="javascript:void(0);" onclick="updateAddr('${(item.id) !}');">改地址</a>
						</@permission>
						</#if> 
						<@permission name="oms:order:words">
						| <a href="javascript:void(0);" onclick="toComment('${(item.memberPhone) !}');">客服留言</a>
						</@permission>
						
						<!--<#if !(item.curLoisticsStatus??)>-->
						<!-- 待收货-未参与调拨 -->
						<!--</#if>-->
						 
						<@permission name="oms:order:dispat">
						|<a  href="javascript:void(0);" onclick="updatePartner('${(item.id) !}');">重新分配配送人</a>
						</@permission>
						
                        <#elseif item.status=='2'>
                        <@permission name="oms:order:printer">
                        <a href="javascript:void(0);" onclick="toPrintView('${(item.id) !}');">打印</a>
                        </@permission>
                        <@permission name="oms:order:viewRecord">
                        |<a href="javascript:void(0);"  title="${(item.orderNo) !}"  key="viewWms" orderNo="${(item.orderNo) !}"  class="viewlogistics logistics-hover" >查看物流
						<div class="logisticspage" style="height:200px;"  id="status${(item.orderNo) !}"></div>
						</a>
						</@permission>
						<@permission name="oms:order:words">
                        | <a href="javascript:void(0);" onclick="toComment('${(item.memberPhone) !}');">客服留言</a>
						</@permission>
                        <#if !(item.curLoisticsStatus??)>
                         <!-- 物流状态为空 -->
                        <!--<@permission name="oms:order:dispat">
						|<a  href="javascript:void(0);" onclick="updatePartner('${(item.id) !}');">重新分配配送人</a>
						</@permission>-->
                        </#if>
                        <#elseif item.status=='3'>
                        <@permission name="oms:order:viewRecord">
                        <a href="javascript:void(0);"  title="${(item.orderNo) !}"  key="viewWms" orderNo="${(item.orderNo) !}"  class="viewlogistics logistics-hover" >查看物流
                        <div class="logisticspage" style="height:200px;"  id="status${(item.orderNo) !}"></div>
                        </a>
                        </@permission>
                        <@permission name="oms:order:words">
                        | <a href="javascript:void(0);" onclick="toComment('${(item.memberPhone) !}');">客服留言</a>
                        </@permission>
                        <!--<#if !(item.curLoisticsStatus??)>
                         	物流状态为空 
                        <!--<@permission name="oms:order:dispat">
						|<a  href="javascript:void(0);" onclick="updatePartner('${(item.id) !}');">重新分配配送人</a>
						</@permission>
						<#elseif item.curLoisticsStatus=='4' >
						  物流状态为已出库 说明要送到合伙人了 即可重新分配合伙人 
						<@permission name="oms:order:dispat">
						|<a  href="javascript:void(0);" onclick="updatePartner('${(item.id) !}');">重新分配配送人</a>
						</@permission>
						</#if>-->
						<@permission name="oms:order:dispat">
						|<a  href="javascript:void(0);" onclick="updatePartner('${(item.id) !}');">重新分配配送人</a>
						</@permission>
						
                        <#elseif item.status=='4'>
                        <@permission name="oms:order:printer">
                        <a href="javascript:void(0);" onclick="toPrintView('${(item.id) !}');">打印</a>
                        </@permission>
                        <@permission name="oms:order:viewRecord">
                        |<a href="javascript:void(0);"  title="${(item.orderNo) !}"  key="viewWms" orderNo="${(item.orderNo) !}"  class="viewlogistics logistics-hover" >查看物流
                        <div class="logisticspage" style="height:200px;"  id="status${(item.orderNo) !}"></div>
                        </a>
                        </@permission>
                        <@permission name="oms:order:words">
                        | <a href="javascript:void(0);" onclick="toComment('${(item.memberPhone) !}');">客服留言</a>
                        </@permission>
                        <!-- 交易完成可以重新分配合伙人  -->
                        <@permission name="oms:order:dispat">
                        | <a  href="javascript:void();" onclick="updatePartner('${(item.id) !}');">重新分配配送人</a>
                        </@permission>
                        <#elseif item.status=='5'>
                        <@permission name="oms:order:words">
                        <a href="javascript:void(0);" onclick="toComment('${(item.memberPhone) !}');">客服留言</a>
                        </@permission>
                        </#if>
                        </td>
                      	</#if>
                    </tr>
                    	</#list>
            		</#if>
                    <!-- 订单明细列 End  -->
                    </tbody>
                </table>
                 </#list>
            </#if>
			<!-- 订单行End  -->
            </div>
            
			<#include "../common/page_macro.ftl" encoding="utf-8"> 
                        <@my_page pageObj/>  
    </div>
</div>
<div style="display:none;"  ><iframe id="orderImport_iframe" ></iframe></div> 

<#include "../common/select.ftl" encoding="utf-8">
<script type="text/javascript">

document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode==13){ 
    	 btn2submit();
    }
}; 
var L3MONTH ="${L3MONTH !}";
var person = "${person !}";
var errorOrder = "${errorOrder !}";
var supplyFlag="${params.supplyFlag !}";
		$(function(){
			bindEvent();
		})
		function bindEvent(){
			tabsSelect();
			$("#th_check").click("click", function() { 
				var evt = arguments[0] || window.event;
				var chkbox = evt.srcElement || evt.target;
				var checkboxes = $("#dataList input[type='checkbox']");
				if (chkbox.checked) {
					checkboxes.prop('checked', true);
				} else {
					checkboxes.prop('checked', false);
				}
			});
			if(person == 1) {
				$("#checkPerson").attr("checked", true);
			} else {
				$("#checkPerson").attr("checked", false);
			}
			if(errorOrder == 1) {
				$("#checkErrorOrder").attr("checked", true);
			} else {
				$("#checkErrorOrder").attr("checked", false);
			}
			console.log("___"+supplyFlag+"___");
			if(supplyFlag == 0 && supplyFlag!=null  && supplyFlag!="") {
				$("#supplyFlag").attr("checked", true);
			} else {
				$("#supplyFlag").attr("checked", false);
			}
			
			//物流状态查看
			$('a[key="viewWms"]').hover(function(e){
				var orderNo =  $(this).attr("orderNo");
				if($(".logisticspage",this).css("display")=="block"){			
					var lists=$(this).parents("tr").children(0);
					$.ajax({
						url : rootPath + "/order/getOmsOrderWms.do",
						data : {
							orderNo :orderNo
						},
						type : "POST",
						dataType : "json",
						async : false,
						cache : false,
						success : function(result) {		
							var length = result.length;
							var showStr='';
							if(length>0){
									showStr+='<h3>关联订单号：'+result[0].orderNo+'  物流状态状态</h3>';
								for (var i = 0; i < length; i++)
								{				
									showStr+='<dl><dt class="orangetxt">●'+result[i].description+'</dt>';
									showStr+='<dd>'+result[i].createDate+'</dd></dl>';                
								}
								showStr+='<dl><dt>●以上为最新跟踪信息</dt></dl>';
								$("#status"+orderNo+"").html(showStr);
							}else{
								showStr='<h3>暂时没有跟踪信息</h3>';
								$("#status"+orderNo+"").html(showStr);
							}					
						}
					});	
				}
			});
			
		}
		function changeQueryTypeByTime(){
			var key =  $("#ul_tab .active").attr("key");
			if(key=="L3MONTH"){
				$("#queryType").val("STATUS");
	        	$("#status").val(key);
	        	$("#ul_tab .active").removeClass();
	        	if($("#status").val()!=null){
	        	$("#ul_tab").find("li[key='"+$("#status").val()+"']").addClass("active");
	        	}else{
	        	$("#ul_tab").find("li[key='']").addClass("active");
	        	}
        	}
		}
        //$(".input-select1").select2();
        //$(".input-select").select2();
        function onEmpty() {
            location.href="${BasePath !}/order/list.do?clearParams=YES";            
        }
        function tabsSelect(){
        	var status = $("#status").val();
        	var queryType = $("#queryType").val();
        	if(queryType=="L3MONTH"){
        		$("li[key='L3MONTH']").addClass("active");
        	}else{
        	$("li[key='"+status+"']").addClass("active");
        	}
        }
        function tab2submit(obj){
        	var key = $(obj).attr("key");
        	checkStatus(key);
        	$("form").submit();
        }
        function checkStatus(key){
    		if($("#checkPerson").is(":checked")) {
        		$("#isPerson").val(1);
        	} else {
        		$("#isPerson").val(0);
        	}
        	if($("#checkErrorOrder").is(":checked")) {
        		$("#isErrorOrder").val(1);
        	} else {
        		$("#isErrorOrder").val(0);
        	}
    		if($("#supplyFlag").is(":checked")) {
        		$("#supplyFlag").val(0);
        	} else {
        		$("#supplyFlag").val("");
        	}
        	if(key=="L3MONTH"){
        		$("#createDateStart").val(L3MONTH);
        		$("#createDateEnd").val("");
        		$("#queryType").val("L3MONTH");
        		//$("#status").val("");
        	}else{
	        	if($("#createDateStart").val()==L3MONTH){
	        		if($("#createDateEnd").val()==null||$("#createDateEnd").val()==""){
	        		$("#createDateStart").val("");
	        		}
	        	}
	        	$("#queryType").val("STATUS");
	        	$("#status").val(key);
        	}
        }
        
        function onChangeStatus(){
        	$("#ul_tab .active").removeClass();
        	$("#ul_tab").find("li[key='"+$("#status").val()+"']").addClass("active");
        }
        
        function new_searchData(){
        	btn2submit();
        }
        function btn2submit(){
        	var key =  $("#ul_tab .active").attr("key");
        	checkStatus(key);
        	$("form").submit();
        }
        function toOrderView(id){
        	location.href="${BasePath !}/order/view.do?id="+id+"&pageType=order";    
        }
        function cancelOrder(id){
        	console.log(id);
        	console.log(rootPath);
        	$.frontEngineDialog.executeDialog('delete_table_info','取消订单提示','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>取消订单后，将不可恢复，请确认是否执行取消订单','100%','100%',
     				function(){
        	 	 $.post(rootPath+"/order/ajax_cancelOrder.do",{id:id},function(res){
            		 if(res.STATUS=="SUCCESS"){
            			 $.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
            			 location.reload(); 
            		 }else{
            			 $.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
            			
            		 }
            	 },'json');
     				}
     			);
        }
        function toComment(phone){
			var phones_Str = phone;
			$.frontEngineDialog.executeIframeDialog('comments', '客服留言', rootPath
			+ '/order/comments.do?phones_Str='+phones_Str, '650', '300');
		}
        function toComments(){
        	var phones_Str = "";
        	$("#dataList input[type='checkbox']:checkbox:checked").each(function(index) {
        		var phone = $(this).attr("phone");
        		if(phones_Str.indexOf(phone)<0){
        		phones_Str+=phone+";";		
        		}
        	});

        	if (phones_Str.indexOf(";") != -1) {
        		phones_Str = phones_Str.substring(0, phones_Str.length - 1);
        	}
        	if(phones_Str==""){
        		$.frontEngineDialog.executeDialogContentTime("请至少选择一条数据");	
        		return; 
        	}
        	$.frontEngineDialog.executeIframeDialog('comments', '客服留言', rootPath
        			+ '/order/comments.do?phones_Str='+phones_Str, '650', '300');

        }
        var  btn_enable_flag = true;
        function importList(){
        	/* if(!btn_enable_flag){
        		$.frontEngineDialog.executeDialogContentTime("禁用中……");	
        		return false;}
        	btn_enable_flag = false; */
        	var params = $("#find-page-orderby-form").serialize();
        	if(checkImportDate()){
        	var _find_page_totalCount=$("#_find-page-totalCount").val();
        	if(_find_page_totalCount>50000){
        	$.frontEngineDialog.executeDialogContentTime("导出数据最大数据不超过50000条订单");
        	return false;
        	}
        	/* setTimeout(function(){
        	btn_enable_flag = true;	
        	},3000); */
			if(!check_ajax_checkExport()){
			$.frontEngineDialog.executeDialogContentTime("亲，不好意思啦，服务器正忙于处理导出数据，要排队哦，请稍后重试【批量导出】功能！",5000);
			return false;
        	}
        	$.frontEngineDialog.executeDialogContentTime("导出数据量大，可能会导致较长导出时间，请耐心等待",5000);
        	$("#orderImport_iframe").attr("src",rootPath+"/order/orderExport.do?"+params);
        	}
        }
        function check_ajax_checkExport(){
        	var  flag = false;
        	jQuery.ajax({
                type: "post",
                async: false,
                url: rootPath+"/order/ajax_checkExport.do",
                data: "{}",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                cache: false,
                success: function (res) {
                	if(res.STATUS== 'SUCCESS'){
                		flag = true;
                	}
                },
                error: function (err) {
                    $.frontEngineDialog.executeDialogContentTime("网络异常",5000);
                }
            });
        	return flag;
        }
        function checkImportDate(){
        	var createDateStart = $("#createDateStart").val();
        	var createDateEnd = $("#createDateEnd").val();
        	
        	var payTimeStart  = $("#payTimeStart").val();
        	var payTimeEnd  = $("#payTimeEnd").val();
        	var nowDate = new Date();
        	var diffDays = 0;
        	if(isEmptyOrNull(createDateStart)&&isEmptyOrNull(payTimeStart)){
        		$.frontEngineDialog.executeDialogContentTime("导出数据时，下单时间和支付时间的结束时间请至少选择一项设置");
        		return false;
        	}else if(!isEmptyOrNull(createDateStart)){//下单开始时间不为空
        		//若下单结束时间不为空
        		if(!isEmptyOrNull(createDateEnd)){
        			diffDays = GetDateDiff(createDateStart,createDateEnd);
        		}else{
        			diffDays = GetDateDiff(createDateStart,null);
        		}
        	if(diffDays>31){
        		$.frontEngineDialog.executeDialogContentTime("下单时间间距超过31天,不允许导出");
        		return false;
        	}
        	}else if(!isEmptyOrNull(payTimeStart)){
        		if(!isEmptyOrNull(payTimeEnd)){
        			diffDays = GetDateDiff(createDateStart,payTimeEnd);
        		}else{
        			diffDays = GetDateDiff(createDateStart,null);
        		}
        		if(diffDays>31){
            		$.frontEngineDialog.executeDialogContentTime("下单时间间距超过31天,不允许导出");
            		return false;
            	}
        	}
        	return true;
        }
        function isEmptyOrNull(val){
        	if(val==null||Trim(val)==""){
        		return true;
        	}else{
        		return false;
        	}
        }
        function Trim(str)
        { 
            return str.replace(/(^\s*)|(\s*$)/g, ""); 
    }
        function GetDateDiff(startDate,endDate)  
        {  
            var startTime = new Date(Date.parse(startDate.replace(/-/g,   "/"))).getTime(); 
            
            var endTime = new Date();
            if(endDate!=null){
            	endTime = new Date(Date.parse(endDate.replace(/-/g,   "/"))).getTime();     
            }
            var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
            return  dates;    
        }
        function printBatch(){
        	var ids= "";
        	$("#dataList input[type='checkbox']:checkbox:checked").each(function(index) {
        		ids+="%27"+$(this).attr("id")+"%27,";		
        	});
        	if (ids.indexOf(",") != -1) {
        		ids = ids.substring(0, ids.length - 1);
        	}
        	if(ids==""){
        		$.frontEngineDialog.executeDialogContentTime("请至少选择一条数据");	
        		return; 
        	}
        	toPrintView(ids);
        }
        function toPrintView(ids){
        	if(ids.indexOf("%27")==-1){
        	 ids= "%27"+ids+"%27";
        	}
        	var url="${BasePath !}/order/print.do?ids="+ids;
        	var width =  window.screen.width*0.6;
        	var height = window.screen.height*0.5;
        	var dlg = dialog({
        		id:'printframe',
        		title: '打印',
                resize: false,
                drag: false,
                lock: true,
                content:"<iframe id='printframe' name='printframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>",
        	    button: [
        	        {
        	            value: '打印',
        	            callback: function () {
        	    			document.getElementById("printframe").contentWindow.printPage(dlg);
        	    			return false;
        	            }
        	        },
        	        {
        	            value: '关闭',
        	            callback: function () {
        	            }
        	        }
        	    ]
        	}).showModal();
        }
        function updatePrice(id){
        	var url="${BasePath !}/order/updateOrderPrice.do?id="+id;
        	var width =  window.screen.width*0.6;
        	var height = window.screen.height*0.5;
        	var dlg = dialog({
        		id:'priceframe',
        		title: '订单改价',
                resize: false,
                drag: false,
                lock: true,
                content:"<iframe  id='priceframe' name='priceframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>",
        	    button: [
        	        {
        	            value: '保存',
        	            callback: function () {
        	    			document.getElementById("priceframe").contentWindow.saveEdit(dlg);
        	    			return false;
        	            }
        	        },
        	        {
        	            value: '关闭',
        	            callback: function () {
        	            }
        	        }
        	    ]
        	}).showModal();
        }
        function updateAddr(id){
        	var url="${BasePath !}/order/updateAddr.do?id="+id;
        	var width =  window.screen.width*0.6;
        	var height = window.screen.height*0.5;
        	var dlg = dialog({
        		title: '修改地址',
                resize: false,
                drag: false,
                lock: true,
                content:"<iframe id='addrframe' name='addrframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>",
        	    button: [
        	        {
        	            value: '保存',
        	            callback: function () {
        	            	document.getElementById("addrframe").contentWindow.saveEdit(dlg);
        	    			return false;
        	            }
        	        },
        	        {
        	            value: '关闭',
        	            callback: function () {
        	            }
        	        }
        	    ]
        	}).showModal();
        }
        
        function updatePartner(id){
        	var url="${BasePath !}/order/updatePartner.do?id="+id;
        	var width =  window.screen.width*0.35;
        	var height = window.screen.height*0.25;
        	var dlg = dialog({
        		title: '重新分配配送人',
                resize: false,
                drag: false,
                lock: true,
                content:"<iframe id='spframe' name='spframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>",
        	    button: [
        	          {
        	 	            value: '确认分配合伙人',
        	 	            callback: function () {
        	 	    				document.getElementById("spframe").contentWindow.saveEdit(dlg);
        	 	    			return false;
        	 	            }
        	          },{
        	            value: '关闭',
        	            callback: function () {
        	            }
        	        }
        	    ]
        	}).showModal();
        }
        
        var dlg_partner;
        function selectPartner(){
        	var url="${BasePath !}/order/partnerlistSelect.do";
        	var width =  window.screen.width*0.5;
        	var height = window.screen.height*0.3;
        	dlg_partner = dialog({
        		id:'partnerframe',
        		title: '配送员',
                resize: false,
                drag: false,
                lock: true,
                content:"<iframe id='partnerframe' name='partnerframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>"
        	    
        	}).showModal();
        }
        function getSelectPartner(obj){
        	dlg_partner.close();	
        	var partnerId = $(obj).attr("id");
        	var partnerCode = $(obj).attr("partnerCode");
        	var sendPerson = partnerId;
        	var sendPersonName = $(obj).attr("name");
        	var servicePoint =  $(obj).attr("addrssInfo");
        	$("#partnerId").val(partnerId);
        	$("#partnerCode").val(partnerCode);
        	$("#servicePoint").val(servicePoint);
        	$("#sendPerson").val(sendPerson);
        	//$("input['name=sendPerson']")(0).val(sendPerson);
        	$("#sendPersonName").val(sendPersonName);
        }
        
        function checkAllocationError(){
        	if($('#allocationError').is(':checked')) {
        		$('#allocationError').val(1);
        	}else{
        		$('#allocationError').val("");
        	}
        }
        function checkIsInvoice(){
        	if($('#isInvoice').is(':checked')) {
        		$('#isInvoice').val(1);
        	}else{
        		$('#isInvoice').val("");
        	}
        }
        
        function checkCommodityType(){
        	if($('#commodityType').is(':checked')) {
        		$('#commodityType').val("1");
        	}else{
        		$('#commodityType').val("");
        	}
        }
        
        Date.prototype.Format = function(fmt)   
        { //author: meizz   
          var o = {   
            "M+" : this.getMonth()+1,                 //月份   
            "d+" : this.getDate(),                    //日   
            "h+" : this.getHours(),                   //小时   
            "m+" : this.getMinutes(),                 //分   
            "s+" : this.getSeconds(),                 //秒   
            "q+" : Math.floor((this.getMonth()+3)/3), //季度   
            "S"  : this.getMilliseconds()             //毫秒   
          };   
          if(/(y+)/.test(fmt))   
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
          for(var k in o)   
            if(new RegExp("("+ k +")").test(fmt))   
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
          return fmt;   
        }
        
      //敏感词导入
        $("#import_button").click(function() {
           var url = rootPath + '/order/importBuildOrder.do';
           var dlg = dialog({
               id: "importbuild",
               title: '导入成单',
               lock: false,
               content : "<iframe  id='importbuild' name='importbuild,"+window.location.href+"' src="+url+" width='500px' height='210px' frameborder='0' scrolling='no' id='printerForm'></iframe>",
        	    button: [
        	        {
        	            value: '导入',
        	            callback: function () {
        	            	document.getElementById("importbuild").contentWindow.submitImport();
        	            	//document.getElementById("spframe").contentWindow.saveEdit(dlg);
        	            	//reloadData('other_setting_list');
        	            	return false;
        	            },
        	        },
        	        {
        	            value: '取消',
        	            callback: function () {
        	            }
        	        }
        	    ]
        	}).show();
        });
</script>
</body>
</html>