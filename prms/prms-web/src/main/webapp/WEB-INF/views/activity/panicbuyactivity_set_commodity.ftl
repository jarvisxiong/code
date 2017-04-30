<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>商品设置-新增</title>
</head>
<body>
<div class="row">
       <div class="col-md-12">
         <div class="box-body">
    <div class="tab-content">
         <div class="tab-pane fade in active" id="addUser">
	<!--新增1--start-->
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/panicbuyActivity/saveCommodity.do" method="post">
	          <input type="hidden" id="id" name="id" value="${(activityCommodity.id) !}"/>    
			<input type="hidden" id="activityId" name="activityId" value="${(activity.id) !}"/>    
	       	<input type="hidden" id="activityNo" name="activityNo" value="${(activity.activityNo) !}"/>
	       	<input type="hidden" id="activityStartDate" name="activityStartDate" value="${(activity.startDate?string('yyyy-MM-dd HH:mm:ss')) !}" />    
	       	<input type="hidden" id="activityEndDate" name="activityEndDate" value="${(activity.endDate?string('yyyy-MM-dd HH:mm:ss')) !}" />
	        <input type="hidden" id="commodityId" name="commodityId" value="${(activityCommodity.commodityId) !}">   
	        <input type="hidden" id="commodityNo" name="commodityNo" value="${(activityCommodity.commodityNo) !}">  
	        <input type="hidden" id="commodityBarcode" name="commodityBarcode" value="${(activityCommodity.commodityBarcode) !}">  
	       	<input type="hidden" id="oldlimitCount" name="oldlimitCount" value="${(activityCommodity.limitCount) !}">
	        <input type="hidden" id="activityCommodityStatus" name="activityCommodityStatus" value="${(activityCommodity.activityCommodityStatus) !}">  
	        <input type="hidden" id="activityitemDate" name="activityitemDate">           
	        <input type="hidden" id="newCommodityId" name="newCommodityId" >       
	        <input type="hidden" id="startDateStr" name="startDateStr" value="${(activity.startDate?string('yyyy-MM-dd HH:mm:ss')) !}" />    
	       	<input type="hidden" id="endDateStr" name="endDateStr" value="${(activity.endDate?string('yyyy-MM-dd HH:mm:ss')) !}" />  
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
	                        data-msg-required="标题不能为空"type="text" id="activityTitle" style="width:450px;" name="activityTitle" value="${(activityCommodity.activityTitle) !}">              
	       	 			</div>
	                </div>
	              </div>
	              <!-- 
	            <div class="form-tr">
						<div class="form-td">
							<label><i>*</i>开始时间：</label>
								<div class="div-form">  
									 		<input name="startDateStr" id="startDateStr" class="form-control txt_mid input-sm"   data-rule-required="true"  data-msg-required="开始时间不能为空"
								  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  value="${(activityCommodity.startDate?string('yyyy-MM-dd HH:mm:ss')) !}">  																		
								</div>
						</div>
						<div class="form-td">
							<label><i>*</i>结束时间：</label>
								<div class="div-form"> 
										<input name="endDateStr" id="endDateStr" class="form-control txt_mid input-sm" data-rule-required="true"  data-msg-required="结束时间不能为空"
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								 value="${(activityCommodity.endDate?string('yyyy-MM-dd HH:mm:ss')) !}"> 										
								</div>
						</div>
	              </div>
	            <!--  <div class="form-tr">
						
						<div class="form-td">
							<label></label>
								
								<div class="div-form"> 
										APP不显示限定数量<input type="checkbox" name="islimit" id="islimit"  value="1" <#if (activityCommodity.islimit) ??><#if activityCommodity.islimit=='1'>checked="checked" </#if></#if>>           							
								</div>
						</div>
	              </div> -->
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
		                       <input type="text" name="sortTopNo"  id="sortTopNo"  onkeyup="onlyInputNum(this);" value="${(activityCommodity.sortTopNo) !"0"}">             
	       	 			</div>
	                </div>
	            </div>
	            <div class="form-tr">
					<div class="form-td">
							<label><i>*</i>优惠价：</label>
							<div class="div-form"> 
								<input type="text" name="showPrice"  id="showPrice" readonly="readonly" data-rule-required="true"  data-msg-required="商品优惠价不能为空"  value="${(activityCommodity.showPrice) !}">
							</div>
					</div>
					<div class="form-td">
							<label>ID限购量：</label>
							<div class="div-form"> 
								<input type="text" name="idLimitCount"  id="idLimitCount" onkeyup="onlyInputNum(this);" value="${(activityCommodity.idLimitCount) !}">
							</div>
					</div>
	               </div>
	             <div class="form-tr">
					<div class="form-td">
							<label><i>*</i>限定数量：</label>
								<div class="div-form"> 
									<input type="text" name="limitCount"  id="limitCount" readonly="readonly" data-rule-required="true"  data-msg-required="限定数量不能为空"  value="${(activityCommodity.limitCount) !}">
								</div>
						</div>
						<div class="form-td">
							<label>销量增量值：</label>
							<div class="div-form"> 
								<input type="text" name="saleIncrease"  id="saleIncrease"    value="${(activityCommodity.saleIncrease) !}">
							</div>
						</div>
	              </div>
	             <div class="form-tr">
	                   <div class="form-td">
							<label></label>
								<div class="div-form"> 
								<div class="div-form"> 
										启用特殊增量<input type="checkbox" name="enableSpecialCount"  id="enableSpecialCount" onclick="radioAmount();" value="1" <#if (activityCommodity.enableSpecialCount) ??><#if activityCommodity.enableSpecialCount=='1'>checked="checked" </#if></#if>>           							
								</div>          							
								</div>
						</div>
						<div class="form-td">
							<label>特殊增量值：</label>
							<div class="div-form"> 
								<input type="text" name="specialCount"  id="specialCount" onkeyup="onlyInputNum(this);" value="${(activityCommodity.specialCount) !}">
							</div>
						</div>
					
	            </div>
	            <div class="form-tr">
	            		<div class="form-td">
							<label>排序号：</label>
							<div class="div-form"> 
								<input type="text" name="sortNo" id="sortNo"  onkeyup="onlyInputNum(this);" value="${(activityCommodity.sortNo) !"0"}">
							</div>
						</div>
						<div class="form-td">
							<label style="width:186px">显示最新已下单用户</label>
							<div class="div-form"> 
								<input type="checkbox" name="isNeworder"  id="isNeworder" value="1" <#if (activityCommodity.isNeworder) ??><#if activityCommodity.isNeworder=='1'>checked="checked" </#if></#if>>           							
							</div>          							
						</div>
	            </div>
              <div class="form-tr">
					<div class="form-td">
						<div class="tab-content">
						<div class="tab-pane fade in active" id="memberList">
							<table class="table table-hover table-striped bor2 table-common" id="tableValue" >
								<thead>
									<tr>
						             		<th width="150px">商品标题</th>
						             		<th width="150px">属性组合</th>
											<th width="150px">商品条码</th>
											<th width="150px">优惠价/元</th>
											<th width="150px">活动优惠价/元</th>
											<th width="150px">用户可购买数量</th>
											<th width="150px">限定数量</th>							
									</tr>
								</thead>
								<tbody id="dateTr">
								
								<#if activityCommoditySkuList?? > 
									<#list activityCommoditySkuList as item >
										<tr>									
											<td width="15%">${(item.commoditySkuTitle) !}</td>
							             	<td width="5%">${(item.commodityAttributeValues) !}</td>
											<td width="8%">${(item.barcode) !}</td>
											<td width="8%">${(item.favourablePrice) !}</td>
											<td width="8%"><input  type="text" name="acprice${item_index + 1}" class="acprice"  onkeyup="onlyInputPrice(this);" skuCode="${(item.skuCode) !}" skuId="${(item.id) !}" style="width:80px;" onBlur="caclTotalprice()" value="${(item.activityPrice) !}" data-rule-isFloatGtZero="true"   data-msg-isFloatGtZero="活动优惠价大于0" 
											data-rule-decimal="true"   data-msg-decimal="小数点不能超过两位数"  data-rule-required="true"  data-msg-required="SKU优惠价不能为空" ></td>
											<td width="8%"><input  type="hidden" name="userCanBuy${item_index + 1}"  class="userCanBuy" value="${(item.userCanBuy) !}">${(item.userCanBuy) !}</td>
											<td width="8%"><input  type="text" name="limitNum${item_index + 1}" class="limitNum" onkeyup="onlyInputNum(this);" style="width:80px;" onBlur="caclTotalNum()" value="${(item.limitCount) !}" data-rule-required="true"  data-msg-required="SKU限定数量不能为空"  data-rule-isIntGteZero="true"  data-msg-isIntGteZero="SKU限定数量为大于0的整数" ></td>		
										</tr>			
									</#list> 
								</#if>	
								</tbody>
							</table>
						</div>
						</div>
					</div>
	             </div>
	        <div  class="memberHead">
	        	<div class="form-tr">
	        		<div class="form-td">
	        		<label>商品logo图片：</label>
      	 				<div class="div-form">	
	                    	<div style="border:1px solid #ccc; width:100px; height:100px;">
	                            <img id="photoPath_img" style="width:100px; height:100px;" <#if (activityCommodity.picPath)?? >src="${(image_path)!}/${activityCommodity.picPath?replace('size','origin')}"</#if>   onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
	                        </div>
	                        <#if view??> 
		        				<#if view=="view"> 
		        				<#else>
	                          	<input type="hidden" id="picPath" name="picPath" value="${(activityCommodity.picPath) !}"  >
	                          	<input type="hidden"  id="photoPathBtn">
	                          	<input type="hidden" value="${(jsessionId) !}" id="jsessionid">
	                          	</#if>  
	                          	<#else>
	                          	<input type="hidden" id="picPath" name="picPath" value="${(activityCommodity.picPath) !}"  >
	                          	<input type="hidden"  id="photoPathBtn">
	                          	<input type="hidden" value="${(jsessionId) !}" id="jsessionid">
	        				</#if> 
	                       	<br><span style="color: red;  ">注：图片内存大小，100KB以内</span>
                        </div>
	                </div>
	                
	        	</div>
	        	
	        	<#if view??> 
		        	<#if view=="view"> 
		        	<div class="form-tr">
	  	 				<div class="div-form">
	                    <a href="javascript:void(0);" class="btn btn-default" onclick="toBack();">&nbsp;&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		                </div>
		        	</div>   
		        	</#if>  
	        	</#if>    
	        </div>
	        </form>
	    </div>
	</div>
	<!--新增1--end-->	
                                      
     </div>
 </div>
         </div>
       </div>
   </div>
<#include "../common/select.ftl" encoding="utf-8">
<style type="text/css">
.memberForm{float:left;}
.memberHead{float:left; margin-top:20px;}
</style>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/panicbuyactivity_set_commodity.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath !}/asset/js/activity/onlyInputNumber.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/swfupload.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath}/asset/js/common/uploadImage.js?v=${ver !}"></script>

<script type="text/javascript">
var viewStatus = "${view!}";
var image_path = "${image_path}";
    $(function(){
        executeValidateFrom('myform','','resultData');
        $(".input-select").select2();
     	// 上传图片
		initUploadImage(showImg,"activityImage","photoPathBtn",100);
		var sale=$("#saleIncrease").val();
	     if(sale!=null && sale!=""){
	    	 $("#enableSpecialCount").css("readonly","readonly");
	     }
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