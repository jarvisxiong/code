<html>
<head>
<meta name="decorator" content="list" />
<title>订单详情</title>
</head>
<body class="box-body">
	<#assign pageAnswer="http://${(orderDomain) !}:${(orderPort) !}/order-web/order/view.do?id=${(data.id) !}"/>
	<#if pageType?exists> 
	<#assign pageTypeInt=(pageType?index_of("order-web"))/> 
	</#if>
	<div class="tab-content">
    <div class="ordertop">
        <div class="orderleft">订单号：${(data.orderNo) !}　订单状态：<span class="txt">${(data.statusName) !}</span></div>
        <div class="orderright">
            <div class="btn-div3">

            <#if pageType?exists && pageType=='order'>
                <button class="btn btn-primary btn-sm btn-go-back" type="button" onclick="window.location.href='${BasePath !}/order/list.do';" >&nbsp;&nbsp;返回</button>
                <#elseif pageType?exists && pageType=='aftersale'>
                <button class="btn btn-primary btn-sm btn-go-back" type="button" onclick="window.location.href='${BasePath !}/exchangeorder/exchangeOrderList.do?tblshow=3';" >&nbsp;&nbsp;返回</button>
                <#elseif pageTypeInt?exists && (pageTypeInt> 0) >
                <button class="btn btn-primary btn-sm btn-go-back" type="button" onclick="window.location.href='${pageType !}';" >&nbsp;&nbsp;返回</button>
                <#else>
                <button class="btn btn-primary btn-sm" type="button" onclick="history.go(-3);" >&nbsp;&nbsp;返回</button>
                </#if>
                <!-- 待付款 -->
            	<#if data.status =='0'>
            	<!-- <button class="btn btn-primary btn-sm" type="button" onclick="updatePrice('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;改价</button> -->
                <!--<button class="btn btn-primary btn-sm" type="button" onclick="print('${(data.id) !}');"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</button>-->
                <@permission name="oms:order:editAddress">
                <button class="btn btn-primary btn-sm" type="button" onclick="updateAddr('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;改地址</button>
                </@permission>
                <@permission name="oms:order:words">
                <button class="btn btn-primary btn-sm" type="button" onclick="toComments(${(data.memberPhone) !});"><i class="fa fa-pencil"></i>&nbsp;&nbsp;客服留言</button>
                </@permission>
                <@permission name="oms:order:cancelorder">
                <button class="btn btn-primary btn-sm" type="button" onclick="cancelOrder('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;取消订单</button>
              	</@permission>
              	 <!-- <button class="btn btn-primary btn-sm" type="button" onclick="updateServicePoint('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;重新分配配送人</button> -->
                <!-- 待收货-->
                <#elseif data.status =='1'>
                <@permission name="oms:order:printer">
                <button class="btn btn-primary btn-sm" type="button" onclick="print('${(data.id) !}');"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</button>
                </@permission>
                <@permission name="oms:order:viewRecord">
                <button class="btn btn-primary btn-sm" type="button" onclick="viewWms();"><i class="fa fa-search"></i>&nbsp;&nbsp;查看物流</button>
                </@permission>
                
                <@permission name="oms:order:aftersaleapply">
                <button class="btn btn-primary btn-sm" type="button" onclick="aftersaleapply('${(data.id) !}','order')"><i class="fa fa-search"></i>&nbsp;&nbsp;申请售后</button>
                </@permission>
                <#if !(data.curLoisticsStatus ??)>
                <!-- 物流状态为空 (未调拨)-->
                <@permission name="oms:order:editAddress">
                <button class="btn btn-primary btn-sm" type="button" onclick="updateAddr('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;改地址</button>
                </@permission>
                </#if>
                <@permission name="oms:order:words">
                <button class="btn btn-primary btn-sm" type="button" onclick="toComments(${(data.memberPhone) !});"><i class="fa fa-pencil"></i>&nbsp;&nbsp;客服留言</button>
                </@permission>
                <!-- 待收货-未参与调拨 -->
                <!--<#if !(data.curLoisticsStatus ??)>
                <button class="btn btn-primary btn-sm" type="button" onclick="aftersaleapply('${(data.id) !}','order')"><i class="fa fa-search"></i>&nbsp;&nbsp;申请售后</button>
                <@permission name="oms:order:dispat">
                <button class="btn btn-primary btn-sm" type="button" onclick="updatePartner('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;重新分配配送人</button>
                </@permission>
                </#if>-->
                <@permission name="oms:order:dispat">
                <button class="btn btn-primary btn-sm" type="button" onclick="updatePartner('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;重新分配配送人</button>
                </@permission>
                <!-- 退款申请中-->
                <#elseif data.status =='2'>
                <@permission name="oms:order:printer">
                <button class="btn btn-primary btn-sm" type="button" onclick="print('${(data.id) !}');"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</button>
                </@permission>
                <@permission name="oms:order:viewRecord">
                <button class="btn btn-primary btn-sm" type="button" onclick="viewWms();"><i class="fa fa-search"></i>&nbsp;&nbsp;查看物流</button>
                </@permission>
                <@permission name="oms:order:words">
                <button class="btn btn-primary btn-sm" type="button" onclick="toComments(${(data.memberPhone) !});"><i class="fa fa-pencil"></i>&nbsp;&nbsp;客服留言</button>
                </@permission>
                <#if !(data.curLoisticsStatus??)>
                <!-- 物流状态为空 -->
                <!--<@permission name="oms:order:dispat">
				<button class="btn btn-primary btn-sm" type="button" onclick="updatePartner('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;重新分配配送人</button>
                </@permission>-->
                </#if>
                <!-- 交易关闭-->
                <#elseif data.status =='3'>
                <@permission name="oms:order:viewRecord">
                <button class="btn btn-primary btn-sm" type="button" onclick="viewWms();"><i class="fa fa-search"></i>&nbsp;&nbsp;查看物流</button>
                </@permission>
                <@permission name="oms:order:words">
                <button class="btn btn-primary btn-sm" type="button" onclick="toComments(${(data.memberPhone) !});"><i class="fa fa-pencil"></i>&nbsp;&nbsp;客服留言</button>
                </@permission>
                <!-- 退款申请中-参与调拨 -->
                <!--<#if !(data.curLoisticsStatus) ??>
                                          物流状态为空 
                <button class="btn btn-primary btn-sm" type="button" onclick="updatePartner('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;重新分配配送人</button>
                <#elseif data.curLoisticsStatus=='4' >
                                          物流状态为已出库 说明要送到合伙人了 即可重新分配合伙人 
                <@permission name="oms:order:dispat">
                <button class="btn btn-primary btn-sm" type="button" onclick="updatePartner('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;重新分配配送人</button>
                </@permission>
                </#if>-->
                
                <@permission name="oms:order:dispat">
                <button class="btn btn-primary btn-sm" type="button" onclick="updatePartner('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;重新分配配送人</button>
                </@permission>
                <!--  交易完成  -->
                <#elseif data.status=='4'>
                <@permission name="oms:order:printer">
                <button class="btn btn-primary btn-sm" type="button" onclick="print('${(data.id) !}');"><i class="fa fa-print"></i>&nbsp;&nbsp;打印</button>
                </@permission>
                <@permission name="oms:order:viewRecord">
                <button class="btn btn-primary btn-sm" type="button" onclick="viewWms();"><i class="fa fa-search"></i>&nbsp;&nbsp;查看物流</button>
                </@permission>
                <@permission name="oms:order:words">
                <button class="btn btn-primary btn-sm" type="button" onclick="toComments(${(data.memberPhone) !});"><i class="fa fa-pencil"></i>&nbsp;&nbsp;客服留言</button>
                </@permission>
                <@permission name="oms:order:dispat">
                <button class="btn btn-primary btn-sm" type="button" onclick="updatePartner('${(data.id) !}');"><i class="fa fa-pencil"></i>&nbsp;&nbsp;重新分配配送人</button>
                </@permission>
                <!--  订单已取消  -->
                <#elseif data.status=='5'>
                <@permission name="oms:order:words">
                <button class="btn btn-primary btn-sm" type="button" onclick="toComments(${(data.memberPhone) !});"><i class="fa fa-pencil"></i>&nbsp;&nbsp;客服留言</button>
                </@permission>
                </#if>
            </div>
        </div>
    </div>
    <div class="orderline">
        <div class="ordertitle">订单信息</div>
        <div class="ordertitle2">收货人信息</div>
        <div class="addpege">
            <ul>
                <li>会员账号：<a href="http://${(ucDomain)!}:${(ucPort)!}/uc-web/member/form.do?id=${(data.memberId) !}&viewstatus=view&pageType=${pageAnswer!}">${(data.memberPhone) !}</a></li>
                <li>客户姓名：${(data.consignName) !}</li>
                <li>收货地址：${(data.addressInfo) !}</li>
                <li>联系方式：${(data.consignPhone) !}</li>
            </ul>
        </div>
        <div class="ordertitle2">其他信息</div>
        <div class="addpege">
            <ul>
          		<li>订单类型：<#if (data.orderType)?? && (data.orderType)=='COMMON_ORDER'>普通订单<#else>换货订单</#if></li>
                <li>支付方式：${(data.payTypeName) !}</li>
                <li>配送人电话：${(data.sendPersonPhone) !}</li>
                <li>出库仓库：<#if data.status =='0'||data.status=='5'><#else>${(data.storageName)!}</#if></li>
                <li>配送人：    <#if (data.sendPerson) ?? && (data.sendPerson != '')>${(data.sendPersonName)!}</#if></li>
                <li>下单时间：<#if (data.createDate) ??>${(data.createDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if></li>
                <li>付款时间：<#if (data.payTime) ??>${(data.payTime)?string('yyyy-MM-dd HH:mm:ss') !}</#if></li>
                <li>发票信息：<#if (data.isInvoice) ??><#if data.isInvoice=="0">无<#else>有</#if><#else>无</#if></li>
                <li>配送服务点：${(data.servicePoint)!}</li>
                <li>在供应商仓库：<#if (data.isSupplier)?? && (data.isSupplier)=='0'>是<#elseif (data.isSupplier)?? && (data.isSupplier)=='1' >否</#if></li>
                <li>所在县仓：${(data.countyStoreName) !}</li>
                <li>是否补货：<#if (data.supplyFlag)?? && (data.supplyFlag)=='0'>不补货<#elseif (data.supplyFlag)?? && (data.supplyFlag)=='1' >补货</#if></li>
                <li>预计到货日期：<#if (data.supplyDate) ??>${(data.supplyDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if></li>
            </ul>
        </div>
    </div>
    <div class="ordertitle2">商品清单</div>
    <div class="tab-content mgb10">
        <div class="tab-pane fade in active" id="myAccount">
            <table class="table table-hover table-striped table-common">
                <thead>
                <tr>
                    <th style="text-align:center;">编号</th>
                    <th style="text-align:center;">商品图片</th>
                    <th>商品名称</th>
                    <th style="text-align:center;">销售属性</th>
                    <th style="text-align:center;">条形码</th>
                    <th style="text-align:center;">数量</th>
                    <th style="text-align:center;">规格</th>
                    <th style="text-align:center;">计量单位</th>
                    <th style="text-align:center;">单价（元）</th>
                    <th style="text-align:center;">实付金额（元）</th>
                    <th style="text-align:center;">购买类型</th>
                    <th style="text-align:center;">售后</th>
                    <th style="text-align:center;">操作</th>
                </tr>
                </thead>
                <tbody>
                <#if data.detailList?? >
                        <#list data.detailList as detailItem >
                <tr>
                    <td align="center">${detailItem_index+1}</td>
                    <td align="center">
                    <img width="80" height="80" <#if (detailItem.commodityImage) ??>src="${(image_path)!}/${detailItem.commodityImage?replace('size','origin')}"</#if>  onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" alt="商品图片">
                    </td>
                    <td>${(detailItem.commodityTitle)!}</td>
                    <td align="center"> ${(detailItem.commodityAttributeValues) !}</td>
                    <td align="center">${(detailItem.commodityBarcode)!}</td>
                   
                    <td align="center">${(detailItem.buyNum)!}</td>
                    <td align="center">${(detailItem.commoditySpecifications)!}</td>
                    <td align="center">${(detailItem.commodityUnit)!}</td>
                    <td align="center">${(detailItem.actSalePrice)!}</td>
                    <td align="center">${(detailItem.actPayAmount)!}</td>
                    <td align="center">${(detailItem.buyTypeName)!}</td>
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
                    <td align="center">
                    <#if data.status=='4' && detailItem.orderDetailStatus == '0'>
                    	<#if (detailItem.promotions)?exists && '1'==(detailItem.promotions) && (detailItem.buyGifts)?exists && '2'==(detailItem.buyGifts) >
		                    &nbsp; 
	                    <#else>
		                    <@permission name="oms:order:aftersaleapply">
		                    <a href="javascript:void(0);" onclick="aftersaleapply('${(detailItem.id) !}','detail');">申请售后</a>
		                    </@permission>
	                    </#if>
                    <#else>
	                    &nbsp; 
                    </#if>
                    

                    </td>
                </tr>
                		</#list>
            	</#if>
                <tr>
                    <td colspan="13" align="right">商品总金额：${(data.totalPrice)!}元 + 运费：${(data.sendCost)!}元 - 优惠：<#if (data.couponAmount) ??>${(data.couponAmount)!}<#else>0</#if>元 - 红包：<#if (data.totalRedAmount) ??>${(data.totalRedAmount)!}<#else>0</#if>元</td>
                </tr>
                </tbody>
            </table>
            <div class="payment">订单支付金额：<span class="txt">¥${(data.actualPrice)!}</span></div>
            <div class="navtabs-title">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#order-tracking" data-toggle="tab">订单追踪（物流状态）</a></li>
                    <li><a href="#order-operation-log" data-toggle="tab">订单操作日志</a></li>
                </ul>
            </div>
            <div class="mgb10"></div>
            <div class="tab-content">
                <div class="tab-pane fade in active" id="order-tracking">
                    <table class="table table-hover table-striped table-common">
                        <colgroup>
                            <col class="tab-order-2-col0">
                            <col class="tab-order-2-col1">
                            <col class="tab-order-2-col2">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>处理时间</th>
                            <th>处理信息</th>
                            <th>操作人</th>
                        </tr>
                        </thead>
                        <tbody>
                         <#if wms_OrderRecordList?? >
                        <#list wms_OrderRecordList as item >
                        <tr>
                            <td><#if (item.createDate) ??>${(item.createDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if></td>
                            <td class="color-gray">${(item.description)!}</td>
                            <td>${(item.oprName)!}</td>
                        </tr>
                     	</#list>
            			</#if>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade in" id="order-operation-log">
                    <table class="table table-hover table-striped table-common">
                        <colgroup>
                            <col class="tab-order-2-col0">
                            <col class="tab-order-2-col1">
                            <col class="tab-order-2-col2">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>处理时间</th>
                            <th>处理信息</th>
                            <th>操作人</th>
                        </tr>
                        </thead>
                        <tbody>
                        
                        <#if orderRecordList?? >
                        <#list orderRecordList as item >
                        <tr>
                            <td><#if (item.createDate) ??>${(item.createDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if></td>
                            <td class="color-gray">${(item.description)!}</td>
                            <td>${(item.oprName)!}</td>
                        </tr>
                     	</#list>
            			</#if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<#include "../common/select.ftl" encoding="utf-8">
<script type="text/javascript">
function toComments(phone){
	var phones_Str = phone;
	$.frontEngineDialog.executeIframeDialog('comments', '客服留言', rootPath
			+ '/order/comments.do?phones_Str='+phones_Str, '650', '300');

}
function print(id){
	var ids= "%27"+id+"%27";
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

function updatePrice(id){
	var url="${BasePath !}/order/updateOrderPrice.do?id="+id;
	var width =  window.screen.width*0.6;
	var height = window.screen.height*0.5;
	var dlg = dialog({
		title: '订单改价',
        resize: false,
        drag: false,
        lock: true,
        content:"<iframe id='priceframe' name='priceframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>",
	    button: [
	        {
	            value: '保存',
	            callback: function () {
	            	document.getElementById("priceframe").contentWindow.saveEdit(dlg);
	    			/* if(window.priceframe&&window.priceframe.saveEdit){
	    				window.priceframe.saveEdit(dlg);
	    			} */
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
	    			if(document.getElementById("addrframe").contentWindow&&document.getElementById("addrframe").contentWindow.saveEdit){
	    				document.getElementById("addrframe").contentWindow.saveEdit(dlg);
	    			}
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
		title: '修改合伙人',
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
function aftersaleapply(id,type){
	var url="";
	if(type=="detail"){
	url="${BasePath !}/order/aftersaleapply.do?detailId="+id;
	}else{
	url="${BasePath !}/order/aftersaleapply.do?orderId="+id;	
	}
	var width =  window.screen.width*0.35;
	var height = window.screen.height*0.3;
	var dlg = dialog({
		title: '申请售后',
        resize: false,
        drag: false,
        lock: true,
        content:"<iframe id='aftersaleframe' name='aftersaleframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>",
	    button: [
	        {
	            value: '保存',
	            callback: function () {
	    				document.getElementById("aftersaleframe").contentWindow.saveEdit(dlg);
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

function cancelOrder(id){
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

function viewWms() {
	location.hash="#order-tracking";
	location=location;
}
</script>
</body>
</html>