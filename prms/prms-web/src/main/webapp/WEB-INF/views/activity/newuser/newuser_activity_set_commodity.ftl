<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>商品设置-新增</title>
</head>
<body>

    <div class="tab-content">
         <div class="tab-pane fade in active" id="addUser">
	<!--新增1--start-->
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/newUserActivity/saveCommodity.do" method="post">
	       <input type="hidden" id="id" name="id" value="${(activityCommodity.id) !}"/>    
			<input type="hidden" id="activityId" name="activityId" value="${(activity.id) !}"/>    
	       	<input type="hidden" id="activityNo" name="activityNo" value="${(activity.activityNo) !}"/>
	        <input type="hidden" id="commodityId" name="commodityId" value="${(activityCommodity.commodityId) !}">   
	        <input type="hidden" id="commodityNo" name="commodityNo" value="${(activityCommodity.commodityNo) !}">  
	        <input type="hidden" id="commodityBarcode" name="commodityBarcode" value="${(activityCommodity.commodityBarcode) !}">  
	        <input type="hidden" id="activityitemDate" name="activityitemDate">
	        <input type="hidden" id="newCommodityId" name="newCommodityId" > 
	        <div class="addForm1">
	       <div id="error_con" class="tips-form">
	           <ul></ul>
	       </div>
	            <div class="tips-form"></div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>标题：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-required="true" 
	                        data-msg-required="标题不能为空"type="text" id="activityTitle" name="activityTitle"  style="width:450px;" value="${(activityCommodity.activityTitle) !}">          
	       	 			</div>
	                </div>
	              </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>选择商品：</label>
	                    <div class="div-form">
		                    <div class="f7" onclick="toSelectCommdity()">
                                <input class="form-control input-sm txt_mid"  type="text" id="commName" name="commName"  data-rule-required="true"  data-msg-required="商品不能为空"  value="${(activityCommodity.activityTitle) !}">
                                <span class="selectBtn">选</span>
                            </div>    
	       	 			</div>
	                </div>
	                <div class="form-td">
	                    <label><i>*</i>置顶序号：</label>
	                    <div class="div-form">
		                       <input type="text" name="sortTopNo"  id="sortTopNo" onkeyup="onlyInputNum(this);" data-rule-required="true"  data-msg-required="置顶序号不能为空" value="${(activityCommodity.sortTopNo) !"0"}">           
	       	 			</div>
	                </div>
	                <div class="form-td">
							<label><i>*</i>优惠价：</label>
							<div class="div-form"> 
								<input type="text" name="showPrice"  id="showPrice" readonly="readonly" data-rule-required="true"  data-msg-required="商品优惠价不能为空" value="${(activityCommodity.showPrice) !}">
							</div>
					</div>
	            </div>
	            <div class="form-tr">
					<div class="form-td">
							<label><i>*</i>ID限购量：</label>
							<div class="div-form"> 
								<input type="text" name="idLimitCount"  id="idLimitCount"  onkeyup="onlyInputNum(this);" data-rule-isIntGtZero="true" data-msg-isIntGtZero="ID限购量必须大于0" data-rule-required="true"  data-msg-required="ID限购量不能为空" value="${(activityCommodity.idLimitCount) !"1"}">
							</div>
					</div>
					<div class="form-td">
							<label><i>*</i>限定数量：</label>
								<div class="div-form"> 
									<input type="text" name="limitCount"  id="limitCount" readonly="readonly"   data-rule-isIntGteZero="true" data-msg-isIntGteZero="限定数量必须大于或等于0"  data-rule-required="true"  data-msg-required="限定数量不能为空" value="${(activityCommodity.limitCount) !}">
								</div>
						</div>
						<div class="form-td">
							<label>销量增量值：</label>
							<div class="div-form"> 
								<input type="text" name="saleIncrease"  onkeyup="onlyInputNum(this);" id="saleIncrease" value="${(activityCommodity.saleIncrease) !}">
							</div>
						</div>
	               </div>
              <div class="form-tr" style="padding-top:6px;">
				
						<div class="tab-content">
						<div class="tab-pane fade in active" id="memberList">
							<table class="table table-hover table-striped bor2 table-common" id="tableValue"  width="100%">
								<thead>
									<tr>
						             		<th width="15%">商品标题</th>
						             		<th>属性组合</th>
											<th>商品条码</th>
											<th>优惠价/元</th>
											<th>活动优惠价/元</th>
											<th>用户可购买数量</th>
											<th>限定数量</th>							
									</tr>
								</thead>
								<tbody id="dateTr">
									<#if activityCommoditySkuList?? > 
										<#list activityCommoditySkuList as item >
											<tr>									
												<td width="15%">${(item.commoditySkuTitle) !}</td>
								             	<td width="5%">${(item.attrGroup) !}</td>
												<td width="8%">${(item.barcode) !}</td>
												<td width="8%">${(item.favourablePrice) !}</td>
												<td width="8%"><input  type="text" name="acprice${item_index + 1}" onkeyup="onlyInputPrice(this);" class="acprice"  skuCode="${(item.skuCode) !}" skuId="${(item.id) !}" style="width:80px;" onBlur="caclTotalprice()" value="${(item.activityPrice) !}" data-rule-isFloatGtZero="true"   data-msg-isFloatGtZero="活动优惠价大于0" 
											data-rule-decimal="true"   data-msg-decimal="小数点不能超过两位数"  data-rule-required="true"  data-msg-required="SKU优惠价不能为空" ></td>
												<td width="8%"><input  type="hidden" name="userCanBuy${item_index + 1}"  class="userCanBuy" value="${(item.userCanBuy) !}">${(item.userCanBuy) !}</td>
												<td width="8%"><input  type="text" name="limitNum${item_index + 1}" class="limitNum" style="width:80px;" onkeyup="onlyInputNum(this);" onBlur="caclTotalNum()" value="${(item.limitCount) !}" data-rule-required="true"  data-msg-required="SKU限定数量不能为空"  data-rule-isIntGteZero="true"  data-msg-isIntGteZero="SKU限定数量为大于等于0的整数" ></td>		
											</tr>			
										</#list> 
									</#if>	
								</tbody>
							</table>
						</div>
						</div>
	
	             </div>
	        </div>
	        <#if view??> 
		        	<#if view=="view"> 
	                <a href="javascript:void(0);" class="btn btn-default" onclick="toBack();">&nbsp;&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		        	</#if>  
	        	</#if>    
	        </form>
	    </div>
	</div>
	<!--新增1--end-->	
                                      
     </div>
 </div>
<#include "../../common/select.ftl" encoding="utf-8">
<style type="text/css">
.memberForm{float:left;}
.memberHead{float:left; margin-top:20px;}
</style>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/newuser_activity_set_commodity.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/onlyInputNumber.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/swfupload.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath}/asset/js/common/uploadImage.js?v=${ver !}"></script>

<script type="text/javascript">
var image_path = "${image_path}";
$(function(){
    executeValidateFrom('myform','','resultData');
    $(".input-select").select2();
 	// 上传图片
	initUploadImage(showImg,"activityImage","photoPathBtn",100);
});  

//图片上传成功   业务处理事件
function showImg(data){	
	var res = eval('(' + data + ')');	
	if(res.status == "0"){
		$("#picPath").val(res.path);		//path  图片地址用于数据库存储
		$("#photoPath_img").attr("src",res.imgPath.replace("_size","_origin"));	//imgPath 用户图片显示	
	}
	
	//上传成功
	$.frontEngineDialog.executeDialogContentTime(res.infoStr);
}
</script>
</body>
</html>