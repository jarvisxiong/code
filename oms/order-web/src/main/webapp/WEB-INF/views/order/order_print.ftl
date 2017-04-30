<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>订单打印</title>
</head>
<body>
<div class="box-body">
    <div class="tab-content">
    <input type="hidden" id="print_ids" value="${(ids)!}"/>
<!--begin-->
    <#if list?? >
        <#list list as item >
        <div class="order-print pageNext"><!--pageNext 打印分页-->
            <!--<div class="page-print-header">
                <div class="print-header-img"> <img src="xxx" width="215" height="60" alt="大麦场logo"></div>
            </div>--><!--page-print-header 现在不做，所以先隐藏掉了-->
            <div class="print-order-info">
                <div class="print-orderInfo-div">
                    <ul class="print-orderInfo-ul">
                        <li>订单编号：${(item.orderNo) !}</li>
                        <li>订购时间：<#if (item.createDate) ??>${(item.createDate)?string('yyyy-MM-dd HH:mm:ss') !}</#if></li>
                        <li>客户姓名：${(item.consignName) !}</li>
                        <li>联系方式：${(item.consignPhone) !}</li>
                        <li style="width: 100%;">收货地址：${(item.addressInfo) !}</li>
                        <li style="width: 100%;">配送合伙人地址：${(item.servicePoint) !}</li>
                        <li>承运人：非凡大麦场</li>
                    </ul><div class="clearfix"></div>
                    <!--<div class="print-header-img"> <img src="xxx" width="150" height="150" alt="商品二维码"><p>146046792246240</p></div>-->
                    <!--print-header-img 现在不做，所以先隐藏掉了-->

                </div>

                <table class="table table-common table-print">
                    <thead>
                    <tr>
                        <th align="center">编号</th>
                        <th>商品名称</th>
                        <th>销售属性</th>
                        <th>条形码</th>
                        <th>数量</th>
                        <th>规格</th>
                        <th>计量单位</th>
                        <th>单价(元)</th>
                        <th>实付金额(元)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if item.detailList?? >
                        <#list item.detailList as detailItem >
                    <tr>
                        <td>${detailItem_index+1}</td>
                        <td>${(detailItem.commodityTitle)!}</td>
                        <td>${(detailItem.commodityAttributeValues)!}</td>
                        <td>${(detailItem.commodityBarcode)!}</td>
                        <td>${(detailItem.buyNum)!}</td>
                        <td>${(detailItem.commoditySpecifications)!}</td>
                        <td>${(detailItem.commodityUnit)!}</td>
                        <td>${(detailItem.commodityPrice)!}</td>
                        <td>${(detailItem.actPayAmount)!}</td>
                    </tr>
                    </#list>
            	</#if>
                    </tbody>
                </table>
                <div class="order-num">商品总金额：${(item.totalPrice)!}元 + 运费：${(item.sendCost)!}元 - 优惠：${(item.favorablePrice)!}元</div>
                <div class="order-num">订单支付金额：<span class="price-num">¥${(item.actualPrice)!}</span></div>
            </div>
            <div class="page-print-footer">
                <p class="">便宜好货送到家，老少无欺放心买！</p>
            </div>
        </div>
 		</#list>
       </#if>
    </div>
    <!--end-->  
    </div>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">

<script type="text/javascript">

var HKEY_Root,HKEY_Path,HKEY_Key; 
HKEY_Root="HKEY_CURRENT_USER"; 
HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"; 
//设置网页打印的页眉页脚为空 
function PageSetup_Null() 
{ 
try 
{ 
var Wsh=new ActiveXObject("WScript.Shell"); 
HKEY_Key="header"; 
Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
HKEY_Key="footer"; 
Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
} 
catch(e) 
{} 

}
//设置网页打印的页眉页脚为默认值 
function PageSetup_Default() 
{ 
try 
{ 
var Wsh=new ActiveXObject("WScript.Shell"); 
HKEY_Key="header"; 
Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"&w&b页码,&p/&P"); 
HKEY_Key="footer"; 
Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"&u&b&d"); 
} 
catch(e) 
{} 

} 

var oldHtml = '' ;
function printPage(){ 
	PageSetup_Null();
	//PageSetup_Default(); 
	oldHtml = document.body.innerHTML ;
	//var prnhtml = document.getElementById('tableContainer').innerHTML;
	sprnstr="<!--begin-->";//设置打印开始区域  
	eprnstr="<!--end-->";//设置打印结束区域  
	var prnhtml='';
	prnhtml=oldHtml.substring(oldHtml.indexOf(sprnstr)+18); //从开始代码向后取html  
	prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html  
	
	
	document.body.innerHTML=prnhtml;
	insertOprRecord();
	window.print();
	document.body.innerHTML = oldHtml ;
	parent.location.reload();
}
var  ids = "${(ids)!}";
function insertOprRecord(){
	$.post("${BasePath !}/order/ajax_printInsertOmsOrderRecord.do",{ids:ids},function(res){},'json');
}


</script> 
</body>
</html>
