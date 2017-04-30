<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="edit" />
	<style>
.endclassOk{color:#E0E0E0;}
.couponoverclass{
height:200px;
overflow:auto;overflow-x: hidden;
}
</style>
	<title>新增</title>
</head>
<body>
    <div class="tab-content">
         <div class="tab-pane fade in active" id="addUser">
	
</form>
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/activityGive/save.do" method="post">
	        <input type="hidden" id="id" name="id" value="${(give.id) !}">  
	        <div class="addForm1"><br></br>
	  
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>出库仓库选择：</label>
							<div class="div-form">
						<input type="radio" name="storageType" id="storageType" value="0"   <#if (give.storageType) ??><#if give.storageType == '0'>checked="checked"</#if></#if>>
						中央仓
						<input type="radio" name="storageType" id="storageType"  value="1"  <#if (give.storageType) ??><#if give.storageType == '1'>checked="checked"</#if></#if>>
						配货地址对应仓
	                </div>
	             </div>
	             
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>标题：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="标题不能为空"
	                         Placeholder="请输入活动标题" type="text" id="giveTitle" name="giveTitle" value="${(give.giveTitle) !}" data-rule-rangelength="[1,25]" data-msg-rangelength="名称长度必须小 20个字符"  />   
	                                      
	       	 			</div>
	                </div>
	              </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>触发数量：</label>
							<div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="触发数量不能为空" type="text" id="triggerCount" name="triggerCount" value="${(give.triggerCount) !}"
	                       Placeholder="请填写触发数量"
	                        data-rule-isIntGtZero="true" data-msg-isIntGtZero="触发数量必须大于0的整数"
	                         data-rule-rangelength="[1,5]" data-msg-rangelength="触发数量小 5个字符"  data-rule-isInteger="true" data-msg-isInteger="限定数量必须是整数" />   
	                                      
	       	 			</div>
	                </div>
	             </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label><i></i>ID 限购量：</label>
							<div class="div-form">
	                        <input class="form-control input-sm txt_mid" 
	                        Placeholder="不填则为不限购"
	                        type="text" id="idLimit" name="idLimit" value="${(give.idLimit) !}" data-rule-rangelength="[0,5]" data-msg-rangelength="名称长度必须小 5个字符" data-rule-isInteger="true" data-msg-isInteger="限定数量必须是整数"  />   
	                                      
	       	 			</div>
	                </div>
	             </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label>备注：</label>
	                    <div class="div-form">
	                     <textarea style="height:140px;width:98%" name="remark" id="remark"  data-rule-rangelength="[0,500]" data-msg-rangelength="名称长度必须小 500个字符" >${(give.remark) !""}</textarea>         
	                    </div>
	                </div>
	            </div> 
	            
	            
	            
	            
	            
	            
	             
	               <div class="form-tr"  >
	                <div class="form-td">
	                    <label><i></i>主商品：
                       </label><!--hidden-->
	                    
							<div class="div-form">
                          
                          
	               		<div  style="margin-left: 40px;">
	               		
						<table class="table table-hover table-striped bor2 table-common" >
							<thead  >
								<tr>
							        <th width="30px">商品条形码</th>
									<th width="80px">商品名称</th>
									<th width="25px">限定数量</th>
									<th width="25px">优惠价/元</th>
									<th width="15px">操作</th>
								</tr>
							</thead>
							<tbody id="tabletdmain">
								<#if mainCommodityList?? > 
								<tr>
								<td>${(mainCommodityList.barCode) !}</td>
								<td>${(mainCommodityList.name) !}</td>
								<td>
								<input  type="text" name="limitCount"  id="limitCount" class="limitCount"  style="width:80px;" data-rule-required="true"  data-msg-required="限定数量不能为空"
	      						data-rule-isIntGteZero="true"  data-msg-isIntGteZero="限定数量为大于等于0的整数" value="${(give.limitCount) !}"  data-rule-isInteger="true" data-msg-isInteger="限定数量必须是整数" />
								</td>
								<td>${(mainCommodityList.preferentialPrice) !}</td>
								<td><a href="javascript:;" onClick="delMain(this,'${(mainCommodityList.commodityId) !}')">删除</a></td></tr>
								
								<tr>
								</#if>
							</tbody>
						</table>
						</div>
	                </div>
	            </div>
	            
	               <div class="form-tr" id="selectuser1" >
	                <div class="form-td">
	                    <label><i></i>赠品（商品）：
	                    
                         </label><!--hidden-->
	                    
							<div class="div-form">
                        <!--商品id-skuid,.....-->
	               		<div   id="zpover" style="margin-left: 40px;" class="<#if (giftSkuList??) && (giftSkuList?size>2) > couponoverclass </#if>" >
	               		
						<table class="table table-hover table-striped bor2 table-common" >
							<thead>
								<tr>
							        <th width="30px">商品条形码</th>
									<th width="25px">商品名称</th>
									<th width="25px">属性组合</th>
									<th width="25px">SKU条形码</th>
									<th width="25px">限定数量</th>
									<th width="25px">优惠价/元</th>
									<th width="25px">活动优惠价/元</th>
									<th width="25px">单次赠送数量</th>
									<th width="15px">操作</th>
								</tr>
							</thead>
							<tbody id="tabletdsku">
								<#if giftSkuList?? > 
								<#list giftSkuList as item >
								<tr>
								<td>${(item.barCode) !}</td>
								<td>${(item.name) !}</td>
								<td>${(item.commodityAttributeValues) !}</td>
								<td>${(item.skubarcode) !}</td>
								<td>
								<input  type="text" name="giftLimtCount${item_index + 1}"   
    							 style="width:80px;"  data-rule-required="true"  value="${(item.limitCount) !}"
    							 data-msg-required="限定数量不能为空"  class="giftLimtCount"  skuid="${(item.skuid) !}"  data-rule-isIntGtZero="true"  data-msg-isIntGtZero="限定数量为大于0的整数"  data-rule-isInteger="true" data-msg-isInteger="限定数量必须是整数" >
								</td>
								<td>${(item.preferentialPrice) !}</td>
								<td>${(item.activityPrice) !}</td>
								<td><input  type="text" name="giftCount${item_index + 1}"  
    							  style="width:80px;"  value="${(item.giftCount) !}" class="giftCount" data-rule-required="true" data-msg-required="单次赠送数量不能为空"
    							    data-rule-isIntGtZero="true"  data-msg-isIntGtZero="单次赠送数量为大于0的整数" data-rule-isInteger="true" data-msg-isInteger="限定数量必须是整数"  >
    							</td>
								
								<td><a href="javascript:;" onClick="delzp(this,'${(item.skuid) !}')">删除</a></td></tr>
								
								</tr>
								</#list> 
								</#if>
							</tbody>
						</table>
						</div>
	                </div>
	            </div>
	               <div class="form-tr"  >
	                <div class="form-td" style="width: 1100px;">
	                    <label><i></i>赠品（优惠券）：</label>
                        
						<div class="div-form"  >
	               		<div id="couponover"  style="margin-left: 40px;" class="<#if (couponList??) && (couponList?size>2) > couponoverclass </#if>">
	               		
						<table class="table table-hover table-striped bor2 table-common" >
							<thead>
								<tr>
							        <th>优惠券编码</th>
									<th >优惠券名称</th>
									<th >面值</th>
									<th>消费限制</th>
									<th>有效期</th>
									<th >操作</th>
								</tr>
							</thead>
							<tbody  id="admintabletd">
								<#if couponList?? > 
								<#list couponList as item >
								<tr>
								<td>${(item.couponCode) !}</td>
								<td>${(item.couponName) !}</td>
								<td>${(item.couponFace) !}</td>
								<#if item.couponLimit == '-1.00'>
									<td>
											无限制
									</td>
								<#else>
									<td>
											满${(item.couponLimit) !}元可使用
									</td>
								</#if>	
								<td>${(item.couponNum) !}</td>
								<td><a href="javascript:;" onClick="delCoupon(this,'${(item.couponId) !}')">删除</a></td></tr>
								</#list> 
								</#if>
								<tr>
							</tbody>
						</table>
						</div>
	                </div>
	            </div>
	            <div class="form-tr">
	                <div class="btn-div2">
	                    <input type="button" class="btn btn-default" value="返回" onclick="back()">
	                </div>
	            </div>
	        </div>
	        </form>
	    </div>
	</div>
	<!--新增1--end-->	
                                      
     </div>
 </div>
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
	<script type="text/javascript" src="${BasePath !}/asset/js/activityGive/formdetail.js?v=${ver !}"></script>
<script type="text/javascript">
var  rootPath="${BasePath !}";
    $(function(){
        executeValidateFrom('myform','saveDate');
        $(".input-select").select2();
    });
function back(){
window.location.href= rootPath + "/activityGive/list.do";
}
</script>
</body>
</html>