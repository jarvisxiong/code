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
	        <form id="myform" action="${BasePath !}/ordinaryActivity/commoditySave.do" method="post">
	        <input type="hidden" id="id" name="id" value="${id !}">    
	        <input type="hidden" id="activity.id" name="activity.id" value="${activityId !}">
	        <input type="hidden" id="activityId" name="activityId" value="${activityId !}"/>    
	        <input type="hidden" id="activityNo" name="activityNo" value="${(commodity.activityNo) !}"> 
	        <input type="hidden" id="commodityId" name="commodityId" value="${(commodity.commodityId) !}">   
	        <input type="hidden" id="commodityNo" name="commodityNo" value="${(commodity.commodityNo) !}">  
	        <input type="hidden" id="commodityBarcode" name="commodityBarcode" value="${(commodity.commodityBarcode) !}">  
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
	                        <input class="form-control input-sm txt_mid" data-rule-required="true" value="${(commodity.activityTitle) !}" 
	                        data-msg-required="标题不能为空"type="text" id="activityTitle" name="activityTitle"  style="width:450px;">          
	       	 			</div>
	                </div>
	             </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>选择商品：</label>
	                    <div class="div-form">
		                    <div class="f7" onclick="toSelectCommdity()">
                                <input class="form-control input-sm txt_mid"  type="text" id="commName" name="commName" value="${(commodity.activityTitle) !}" data-rule-required="true"  data-msg-required="商品不能为空">
                                <span class="selectBtn">选</span>
                            </div>    
	       	 			</div>
	                </div>
	                <div class="form-td">
	                    <label><i>*</i>置顶序号：</label>
	                    <div class="div-form">
		                       <input type="text" name="sortTopNo"  id="sortTopNo" value="${(commodity.sortTopNo) !"0"}"  onkeyup="onlyInputNum(this);" data-rule-required="true"  data-msg-required="置顶序号不能为空">           
	       	 			</div>
	                </div>
	                <div class="form-td">
						<label>优惠价：</label>
						<div class="div-form"> 
							<input type="text" name="showPrice"  id="showPrice" readonly="readonly" value="${(commodity.showPrice) !}" data-rule-required="true"  data-msg-required="优惠价不能为空">
						</div>
					</div>
	            </div>
              	<div class="form-tr"  style="padding-top:6px;">
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
									</tr>
								</thead>
								<tbody id="dateTr">
								<#if dataList?? > 
								<#list dataList as item >
									<tr>
										<td align="center">${item.commoditySkuTitle !}</td>
										<td align="center">${item.attrGroup !}</td>
										<td align="center">${item.commoditySkuBarcode !}</td>
										<td align="center">${(item.commoditySkuPrice) !}</td>
										<td align="center"><input type="text" name="activityPrice" onblur="changPrice()" onkeyup="onlyInputPrice(this);" value="${item.activityPrice !}" />
											<input type="hidden" name="skuId" value="${(item.commoditySkuId) !}" />
											<input type="hidden" name="skuCode" value="${(item.commoditySkuNo) !}" />
										</td>
									</tr>
								</#list> 
								</#if>
								</tbody>
							</table>
						</div>
					</div>
	             </div>
	              <#if view??> 
		        	<#if view=="view"> 
	                <a href="javascript:void(0);" class="btn btn-default" onclick="toBack();">&nbsp;&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		        	</#if>  
	        	</#if>
	        </div>
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
<script type="text/javascript" src="${BasePath !}/asset/js/activity/ordinary/ordinary_commodity_form.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/onlyInputNumber.js?v=${ver !}"></script>
<script type="text/javascript">
$(function(){
    executeValidateFrom('myform','','resultData');
    $(".input-select").select2();
});  
</script>
</body>
</html>