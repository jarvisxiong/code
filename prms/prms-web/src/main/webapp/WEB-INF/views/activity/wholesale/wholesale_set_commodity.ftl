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
	        <form id="myform" action="${BasePath !}/wholesaleActivity/saveCommodity.do" method="post">
	        <input type="hidden" id="id" name="id" value="${(activityCommodity.id) !}"/>  
	        <input type="hidden" id="activityId" name="activityId" value="${(activity.id) !}"/>    
	       	<input type="hidden" id="activityNo" name="activityNo" value="${(activity.activityNo) !}"/>
	        <input type="hidden" id="commodityId" name="commodityId" value="${(activityCommodity.commodityId) !}">   
	        <input type="hidden" id="commodityNo" name="commodityNo" value="${(activityCommodity.commodityNo) !}">  
	        <input type="hidden" id="commodityBarcode" name="commodityBarcode" value="${(activityCommodity.commodityBarcode) !}">
	        <input type="hidden" id="price" name="price" value="">
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
		                      <input type="text" name="sortTopNo"  id="sortTopNo" onkeyup="onlyInputNum(this);" value="${(activityCommodity.sortTopNo) !"0"}" data-rule-required="true"  data-msg-required="置顶序号不能为空">           
	       	 			</div>
	                </div>
	                <div class="form-td">
						<label><i>*</i>优惠价：</label>
						<div class="div-form"> 
							<input type="text" name="showPrice"  id="showPrice" readonly="readonly" data-rule-required="true"  data-msg-required="优惠价不能为空" value="${(activityCommodity.showPrice) !}">
						</div>
					</div>
	            </div>
	            <div class="form-tr">
					<div class="form-td">
						<label>购买数量选择：</label>
						<div class="div-form"> 
							&nbsp;&nbsp;&nbsp;<input type="radio" name="buyCount" onchange="radioChecked(this)" value="0"
							 <#if (activityCommodity.buyCount) ??><#if activityCommodity.buyCount == '0'>checked="checked"</#if><#else>checked="checked"</#if> />1个购买数量
							&nbsp;&nbsp;&nbsp;<input type="radio" name="buyCount" onchange="radioChecked(this)" value="1" 
							 <#if (activityCommodity.buyCount) ??><#if activityCommodity.buyCount == '1'>checked="checked"</#if></#if> />3个购买数量
							&nbsp;&nbsp;&nbsp;<input type="radio" name="buyCount" onchange="radioChecked(this)" value="2" 
							 <#if (activityCommodity.buyCount) ??><#if activityCommodity.buyCount == '2'>checked="checked"</#if></#if> />5个购买数量
						</div>
					</div>
	            </div>
              	<div class="form-tr">
					
						<label>数量区间及价格：</label>
						<div class="tab-content">
						<div class="tab-pane fade in active" id="memberList">
							<table class="table table-hover table-striped bor2 table-common" id="tableValue" >
								<thead>
									<tr>
					             		<th width="50%">购买数量区间</th>
					             		<th width="25%">数量区间</th>
										<th width="25%">价格</th>
									</tr>
								</thead>
								<tbody id="dateTr">
								<#if activityCommoditySkuList?? > 
								<#list activityCommoditySkuList as item >
									<tr>
										<td align='center'>
											<input type="text" name="selectionStart" onblur="changNumber(this,${item_index},0)" onkeyup="onlyInputNum(this);" value="${(item.selectionStart) !}" 
											 <#if (item_index!=0)>readonly="readonly"</#if> />&nbsp;&nbsp;&nbsp;
											<#if (item.selectionEnd) ??><input type="text" name="selectionEnd" onblur="changNumber(this,${item_index},1)"  onkeyup="onlyInputNum(this);" value="${(item.selectionEnd) !}"/></#if>
										</td>
										<td align="center"><#if (item.selectionEnd) ??>[${(item.selectionStart) !} , ${(item.selectionEnd) !}]<#else>[${(item.selectionStart) !} , ]</#if></td>
										<td align="center"><input type="hidden" id="skuPrice" value="${(item.commoditySkuPrice) !}">
											<input type="text" name="activityPrice" onblur="changPrice()" onkeyup="onlyInputPrice(this);" value="${(item.activityPrice) !}"/>
										</td>
									</tr>
								</#list>
								<#else>
								<tr>
									<td align='center'>
										<input type="text" name="selectionStart" onblur="changNumber(this,0,0)" onkeyup="onlyInputNum(this);" value=""/>
									</td>
									<td align="center"></td>
									<td align="center"><input type="text" name="activityPrice" onblur="changPrice()" onkeyup="onlyInputPrice(this);" value=""/></td>
								</tr>
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
<script type="text/javascript" src="${BasePath !}/asset/js/activity/wholesale/wholesale_set_commodity.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/onlyInputNumber.js?v=${ver !}"></script>
<script type="text/javascript">
$(function(){
	// 商品价格
	$("#price").val($("#skuPrice").val());
	
    executeValidateFrom('myform','','resultData');
});  
</script>
</body>
</html>