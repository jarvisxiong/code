<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>订单改价</title>
</head>
<body>

<div class="box-body">
  <div class="tab-content">
    <div class="order-page">
      
      <div class="order-t1">订单编号：${(data.orderNo) !}</div>
      <ul>
        <li>客户姓名：${(data.memberName) !}</li>
        <li>联系方式：${(data.memberPhone) !}</li>
        <li>收货地址：${(data.addressInfo) !}</li>
      </ul>
    </div>
    <div id="myAccount" class="tab-pane fade in active">
      <table class="table table-hover table-striped table-common">
        <thead>
          <tr>
            <th style="text-align:center;">编号</th>
            <th>商品名称</th>
            <th style="text-align:center;">条形码</th>
            <th style="text-align:center;">数量</th>
            <th style="text-align:center;">规格</th>
            <th style="text-align:center;">计量单位</th>
            <th style="text-align:center;">单价（元）</th>
            <th style="text-align:center;">实付金额（元）</th>
          </tr>
        </thead>
        <tbody id="detailList">
        <#if data.detailList?? >
          <#list data.detailList as detailItem >
          <tr>
            <td align="center">${detailItem_index+1}</td>
            <td>${(detailItem.commodityTitle)!}</td>
            <td align="center">${(detailItem.commodityBarcode)!}</td>
            <td align="center">${(detailItem.buyNum)!}</td>
            <td align="center">${(detailItem.commoditySpecifications)!}</td>
            <td align="center">${(detailItem.commodityUnit)!}</td>
            <td align="center">${(detailItem.commodityPrice)!}</td>
            <td align="center"><input type="text" class="form-control input-sm txt_mid2" name="actPayAmount"  onblur="_onlyInputNum(this);sumPrice();"  key="${(detailItem.id)!}"  value="${(detailItem.actPayAmount)!}" defaultValue="${(detailItem.actPayAmount)!}" /></td>
          </tr>
          </#list>
        </#if>
        </tbody>
      </table>
      <div class="orderinfo">
        <div class="statistic">
          <div class="list"> <span class="label">商品总金额：</span> <span class="price" id="totalPrice" val="${(data.totalPrice)!}"><#if (data.totalPrice)??>${data.totalPrice?string('#.00')}元</#if></span> </div> 
          <div class="list"> <span class="label">+ 运费：</span> <span class="price" id="sendCost" val="${(data.sendCost)!}" >${(data.sendCost)!}元  </span> </div>
          <div class="list"> <span class="label">- 优惠：</span> <span class="price" id="favorablePrice" val="<#if (data.favorablePrice)??>${data.favorablePrice}<#else>0</#if>" > <#if (data.favorablePrice)??>${data.favorablePrice}<#else>0</#if> 元</span> </div>
          <div class="list"> <span class="label">实付总金额：</span> <span class="price" ><em id="actualPrice" val="${(data.actualPrice)!}" ><#if (data.actualPrice)??>¥${data.actualPrice?string('#.00')}元</#if></em></span> </div>
        </div>
        <div class="clr"></div>
      </div>
      
    </div>
  <!--   <div class="form-tr">
            <div class="btn-div5">
              <input type="submit" class="btn btn-primary btn-sm" value="保存">
              <input type="submit" class="btn btn-default btn-sm" value="返回">
            </div>
          </div> -->
  </div>
</div>
<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<script type="text/javascript">
function sumPrice(){
		var totalPrice = 0;
	$("#detailList input[name='actPayAmount']").each(function(index) {
		var actPayAmount =  parseFloat($(this).val());
		totalPrice+=actPayAmount;
	});
	$("#totalPrice").text(totalPrice.toFixed(2));
	$("#totalPrice").attr("val",totalPrice.toFixed(2));
	var actualPrice = totalPrice+parseFloat($("#sendCost").attr("val") * 1)-parseFloat($("#favorablePrice").attr("val") * 1);
	$("#actualPrice").html("¥"+actualPrice.toFixed(2));
	$("#actualPrice").attr("val",actualPrice.toFixed(2));
	
}

function _onlyInputNum(obj) {
	var  re_int = /^\d+$/;
	var  re_float =/^\+{0,1}\d+(\.\d{1,2})?$/;
	var res= /^\d{0,8}\.{0,1}(\d{1,2})?$/;
	if(!res.test(obj.value)){
		$.frontEngineDialog.executeDialogContentTime('请输入正整数或者带两位小数的价格');
		obj.value=1;
	}
	//字符转整形
	if(obj.value * 1 > obj.defaultValue * 1) {
		$.frontEngineDialog.executeDialogContentTime('实付金额不能大于默认值');
		obj.value=1;
	} 
	if(obj.value == 0) {
		$.frontEngineDialog.executeDialogContentTime('实付金额不能为0');
		obj.value=1;
	}
}
function saveEdit(dlg){
	var detailArr = [];
	$("#detailList input[name='actPayAmount']").each(function(index) {
			var actPayAmount =  parseFloat($(this).val());
			var detailId = $(this).attr("key");
		    var data = {};
		    data.id = detailId;
		    data.actPayAmount = actPayAmount;
		    detailArr.push(data);
		});
	var params = {};
	params.dataId = "${(data.id) !}";
	params.totalPrice = $("#totalPrice").attr("val");
	params.actualPrice = $("#actualPrice").attr("val");
	params.detailJson = JSON.stringify(detailArr);
	 $.post(rootPath+"/order/ajax_savePrice.do",params,function(res){
		 if(res.STATUS=="SUCCESS"){
		 	$.frontEngineDialog.executeDialogContentTime(res.MSG,1000);
		 	setTimeout(function(){
				window.close();
				parent.location.reload();
			},1500);
		 }else{
		 	$.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
		 }
	 },'json');
}

		
</script> 
</body>
</html>
