<html>
<head>
<meta name="decorator" content="list" />
<title>订单详情</title>
</head>
<body class="box-body">
<div class="box-body">
    <div class="tab-content">
<div class="wrapper-html">
        <form id="afterSaleAplly">
        <input type="hidden" name="orderId" value="${(data.id) !}" />
        <input type="hidden" name="memberId" value="${(data.memberId) !}"/>
        <input type="hidden" id="orderStatus" value="${(data.status) !}"/>
        
            <div class="addForm1">
               <!--  <div class="tips-form">请选择对应的服务类型</div> -->
                <div class="form-tr">
                    <div class="form-td">
                        <label style=" width:auto;"><i>*</i>请选择服务类型：</label>
                        <div class="div-form">
                                 <label style="width: auto;" class="radio-inline"><input type="radio"  value="0" name="serviceType" id="refund"> 退款(没收到货)　</label>
                                 <label style="width: auto;" class="radio-inline"><input type="radio"  value="1" name="serviceType"  id="returnGoods" > 退货(已收到货)　</label>
                                 <label style="width: auto;" class="radio-inline"><input type="radio"  value="2" name="serviceType"  id="exchangeGoods" > 换货(已收到货) </label>
						 </div>
						<label style=" width:100%;text-align: inherit;"><i>*</i>请选择原因：</label>
						 <div class="div-form" style="padding-left: 16%;" id="reason">
                                 <label style="width: auto;" class="radio-inline"><input type="radio"  value="0" name="reasonExplain"> 退款(没收到货)　</label></br>
                                 <label style="width: auto;" class="radio-inline"><input type="radio"  value="1" name="reasonExplain"  > 退货(已收到货)　</label></br>
                                 <label style="width: auto;" class="radio-inline"><input type="radio"  value="2" name="reasonExplain" > 换货(已收到货) </label></br>
                                
                      </div>
                    </div>
                    
                </div>
              </div>  
				<#if omsOrderdetail??>
                <div class="form-tr">
                
                <p>数量选择：</p>
                <table class="table table-hover table-striped table-common" style="width:auto;">
                <thead>
                <tr>
                    <th valign="middle">名称</th>
                    <th valign="middle" style="text-align:center;">规格</th>
                    <th valign="middle" style="text-align:center;">计量单位</th>
                    <th valign="middle" style="text-align:center;">单价（元）</th>
                    <th valign="middle" style="text-align:center;">数量</th>
                  </tr>
                </thead>
                <tbody>
                <tr>
                    <td valign="middle">${(omsOrderdetail.commodityTitle)!}</td>
                    <td valign="middle" style="text-align:center;">${(omsOrderdetail.commodityTitle)!}</td>
                    <td valign="middle" style="text-align:center;">${(omsOrderdetail.commoditySpecifications)! }${(omsOrderdetail.commodityUnit)!}</td>
                    <td valign="middle">￥${(omsOrderdetail.actSalePrice)!}</td>
                    <input type="hidden" name="skuId" value="${(omsOrderdetail.skuId) !}"/>
                   <#if (omsOrderdetail.buyGifts)?exists && '2'==(omsOrderdetail.buyGifts)>
                    <td valign="middle" style="text-align:center;"><input type="text" id="num" name="count" readonly="readonly" maxNum="${(omsOrderdetail.buyNum)!}"  value="${(omsOrderdetail.buyNum)!}" style="float:left;width:40px; text-align:center;" class="form-control input-sm txt_mid">${(omsOrderdetail.buyGifts)! }</td>
					<#else>
					<td valign="middle" style="text-align:center;width: 20%;"><a href="javascript:void(0);" onclick="changeNum('down','${(omsOrderdetail.id) !}','${(omsOrderdetail.buyGifts)! }');" class="btn btn-default"  style="padding:2px 8px;border-radius:0; float:left;"><i class="fa fa-minus"></i></a><input type="text" id="num" name="count" readonly="readonly" maxNum="${(omsOrderdetail.buyNum)!}"  value="${(omsOrderdetail.buyNum)!}" style="float:left;width:40px; text-align:center;" class="form-control input-sm txt_mid"><a href="javascript:void(0);" onclick="changeNum('up','${(omsOrderdetail.id) !}','${(omsOrderdetail.buyGifts)! }');" class="btn btn-primary" style="padding:2px 8px;border-radius:0;float:left;"><i class="fa fa-plus" aria-hidden="true"></i>
					</a></td>
					</#if>
                  </tr>
<!--                   赠品相关的页面申请售后信息 -->
				<#if orderdetails?exists>
					<#list orderdetails as orderdetail>
	                  <tr name="orderdetail_tr">
	                    <td valign="middle">${(orderdetail.commodityTitle)!}</td>
	                    <td valign="middle" style="text-align:center;">${(orderdetail.commodityTitle)!}</td>
	                    <td valign="middle" style="text-align:center;">${(orderdetail.commoditySpecifications)! }${(orderdetail.commodityUnit)!}</td>
	                    <td valign="middle">￥${(orderdetail.actSalePrice)!}</td>
	                    <td valign="middle" style="text-align:center;"><input type="text" readonly="readonly" value="${(orderdetail.buyNum)!}" style="float:left;width:40px; text-align:center;" class="form-control input-sm txt_mid"></td>
	                  </tr>
	                 </#list>
                  </#if>
                </tbody>
            </table>
            </div>
      </#if>
       
      </form>
                <!-- <div class="form-tr">
                    <div class="btn-div">
                        <input type="submit" value="保存" class="btn btn-primary">
                        <input type="submit" value="返回" class="btn btn-default">
                    </div>
                </div> -->
            </div>
       
    </div>

 </div>
 </div>


<#include "../common/css.ftl" encoding="utf-8">
<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
<script type="text/javascript" src="${BasePath !}/asset/js/aftersale/refund/order_aftersaleapply.js?v=${ver !}"></script>
<script type="text/javascript">
//避免重复提交
var  flag = true;

	function changeNum(type,mainCommodityId,buyGifts){
		var num  = parseInt($("#num").val());
		var maxNum = parseInt($("#num").attr("maxNum"));
		if(type=="up"){
			++num;
			if(num>maxNum){
				num=maxNum;
			}
			
			$("#num").val(num);
		}else{
			--num;
			if(num<1){
				num=1;
			}
			$("#num").val(num);
		}
		if('1'==buyGifts){
			calculationGifts(mainCommodityId,num);
		}
	}
	function saveEdit(dlg){
		if(flag){
		var val_reason = $('#reason input[name="reason"]:checked').val();
		if((val_reason!=null && val_reason!="" && typeof(val_reason)!="undefined")){
			 $.post("${BasePath !}/aftersaleapply/applyAfterSale.do",$("form").serialize(),function(res){
				 flag = false;
				 if(res.STATUS=="SUCCESS"){
					 $.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
					 setTimeout(function(){
					 dlg.close();
					 parent.location.reload();
					 },1500);
				 }else{
					 flag = true;
					 $.frontEngineDialog.executeDialogContentTime(res.MSG,1500);
				 }
			 },'json');
		}else{
			 $.frontEngineDialog.executeDialogContentTime("请选择售后原因！",1500);
		}
	  } 
	}
</script>
</body>
</html>