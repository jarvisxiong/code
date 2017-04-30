
<html>
<head>
	<meta name="decorator" content="list" />
	<title>换货列表</title>
</head>
<body>
<#assign pageAnswer="http://${(orderDomain) !}:${(orderPort) !}/order-web/exchangeorder/exchangeOrderList.do?tblshow=${(tblshow) !}"/>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="brandList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/exchangeorder/exchangeOrderList.do?tblshow=3" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>订单编号：</label> 
									<div class="div-form">
										<input id="orderNo" name="orderNo_like" value="${(params.orderNo_like) !}" class="form-control txt_mid input-sm" type="text">
									</div>
								</div>
								<div class="form-td">
									<label>手机号：</label> 
									<div class="div-form">
										<input id="phone" name="phone_like" value="${(params.phone_like) !}" class="form-control txt_mid input-sm" type="text">
									</div>
								</div>
								<div class="form-td">
									<label>付款时间：</label> 
									<div class="div-form"><input name="payTimeStart" id="payTimeStart" class="form-control txt_mid input-sm" 
	                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'payTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<#if (params.payTimeStart) ??>${(params.payTimeStart)?string('yyyy-MM-dd HH:mm:ss') !}</#if>">
	                                   -
	                                   <input name="payTimeEnd" id="payTimeEnd" class="form-control txt_mid input-sm" 
	                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'payTimeStart\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<#if (params.payTimeEnd) ??>${(params.payTimeEnd)?string('yyyy-MM-dd HH:mm:ss') !}</#if>">
	                              		</div>
								</div>
							</div>
						</div>
						<div class="btn-div3">
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							<button id='table-cleanBtn' class="btn btn-primary btn-sm" onclick="cleanParams();"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button>
						</div>
					</form>
				</div>
			</div>
			<div class="navtabs-title">
				<ul class="nav nav-tabs" style="padding-left: 1%;">								
				  <@permission name="oms:aftersale:applyOrder">
								 <li <#if tblshow?? && tblshow == "0">class="active"</#if>><a href="${BasePath !}/aftersaleapply/afterList.do?tblshow=0">售后申请单</a></li>
				  </@permission>	
				  
				  <@permission name="oms:aftersale:pickup">
								<li <#if tblshow?? && tblshow == "1">class="active"</#if>><a href="${BasePath !}/aftersaleapickup/afterPickupList.do?tblshow=1" >售后取货单</a></li>
				  </@permission>
				  
				  <@permission name="oms:aftersale:refundList">
								 <li <#if tblshow?? && tblshow == "2">class="active"</#if>><a href="${BasePath !}/aftersalerefund/refundList.do?tblshow=2" >退款单</a></li>
				  </@permission>
				  
				  <@permission name="oms:aftersale:changeOrder">
								<li <#if tblshow?? && tblshow == "3">class="active"</#if>><a href="${BasePath !}/exchangeorder/exchangeOrderList.do?tblshow=3" >换货订单</a></li>		
				  </@permission>
				
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
	                            <div class="tab-order-num" onclick="toOrderView('${(item.id) !}');" style="cursor:pointer">订单编号：<span>${(item.orderNo) !}</span></div>
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
	                                <img <#if (detailItem.commodityImage) ??> src="${(image_path)!}/${detailItem.commodityImage?replace('size','origin')}"</#if> onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" alt="商品图片" />
	                                <div class="order-goods-text">
	                                    <p class="goods-title">${(detailItem.commodityTitle)!}</p>
	                                    <p class="goods-barcode">条形码：${(detailItem.commodityBarcode) !}</p>
	                                    <p class="goods-attr">${(detailItem.commodityAttributeValues) !}</p>
	                                </div>
	                            </div>
	                        </td>
	                        <td valign="middle">${(detailItem.actSalePrice) !}</td>
	                        <td>${(detailItem.buyNum) !}</td>
	                        <td>&nbsp;</td>
	                      	 <#if (detailItem_index==0) >
	                       	<td <#if item.detailList??>rowspan="${item.detailList?size}"><a href="http://${(ucDomain)!}:${(ucPort)!}/uc-web/member/form.do?id=${(item.memberId) !}&viewstatus=view&pageType=${pageAnswer!}"> ${(item.memberPhone) !}</a></#if></td>
	                        <td <#if item.detailList??>rowspan="${item.detailList?size}"</#if>>${(item.statusName)!}</td>
	                        </#if>
	                        <td>${(detailItem.actPayAmount) !}</td>
	                        <#if (detailItem_index==0) >
	                        <td <#if item.detailList??>rowspan="${item.detailList?size}"</#if>>
	                        <@permission name="oms:order:printer">
	                        	<a href="javascript:void(0);" onclick="toPrintView('${(item.id) !}');">打印</a>
	                        </@permission>
	                         <@permission name="oms:order:viewRecord">
	                         	  | <a href="${BasePath !}/order/view.do?id=${(item.id) !}"  title="${(item.orderNo) !}"  key="viewWms" orderNo="${(item.orderNo) !}"  class="viewlogistics logistics-hover" >查看物流
                        			<div class="logisticspage" style="height:200px;"  id="status${(item.orderNo) !}"></div>
	                          		</a> 
	                         </@permission>
	                     	
	                        <#if item.status=='1'>
	                         <@permission name="oms:order:editAddress">
	                         	| <a href="javascript:void(0);" onclick="updateAddr('${(item.id) !}');" >改地址</a> 
	                         </@permission>
	                        </#if>
	                         <@permission name="oms:order:words">
	                         	 | <a href="javascript:void(0);" onclick="toComment('${(item.memberPhone) !}');">客服留言</a>
	                         </@permission>
	                         <@permission name="oms:order:dispat">
	                          	 | <a href="javascript:void();" onclick="updatePartner('${(item.id) !}');">重新分配配送人
	                         </@permission>   
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
	    	</div>
			<#include "../../common/page_macro.ftl" encoding="utf-8"> 
			<@my_page pageObj/>
		</div>
	</div>
	<div style="display:none;"  ><iframe id="orderImport_iframe" ></iframe></div> 
<#include "../../common/tree.ftl" encoding="utf-8">
<#include "../../common/select.ftl" encoding="utf-8">
<script type="text/javascript" src="${BasePath}/asset/js/aftersale/apply/apply_list.js?v=${ver !}"></script>
<script type="text/javascript">
function onEmpty() {
    window.location.href="${BasePath !}/exchangeorder/exchangeOrderList.do?tblshow=3";            
}
$(function(){
	
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
});

// 查看详情
function toOrderView(id){
	location.href="${BasePath !}/order/view.do?id="+id+"&pageType=aftersale";    
}

// 客服留言
function toComment(phone){
	var phones_Str = phone;
	$.frontEngineDialog.executeIframeDialog('comments', '客户留言', rootPath
	+ '/order/comments.do?phones_Str='+phones_Str, '600', '300');
}

// 打印
function toPrintView(ids){
	ids = "%27"+ids+"%27";
	var url="${BasePath !}/order/print.do?ids="+ids;
	var width =  window.screen.width*0.6;
	var height = window.screen.height*0.5;
	var dlg = dialog({
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

// 改地址
function updateAddr(id){
	var url="${BasePath !}/order/updateAddr.do?id="+id;
	var width =  window.screen.width*0.4;
	var height = window.screen.height*0.2;
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

// 修改合伙人
function updatePartner(id){
	var url="${BasePath !}/order/updatePartner.do?id="+id;
	var width =  1000; // window.screen.width*0.8;
	var height = 500; // window.screen.height*0.6;
	var dlg = dialog({
		title: '修改合伙人',
        resize: false,
        drag: false,
        lock: true,
        content:"<iframe id='spframe' name='spframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>",
	    button: [
	        {
	            value: '保存',
	            callback: function () {
	            	document.getElementById("spframe").contentWindow.saveEdit(dlg);
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

//清空检索条件
function cleanParams(){
	$("#orderNo").val(""); // 订单编号
	$("#phone").val(""); // 手机号
	$("#payTimeStart").val(""); // 付款开始时间
	$("#payTimeEnd").val(""); // 付款结束时间
}
</script>
</body>
</html>
