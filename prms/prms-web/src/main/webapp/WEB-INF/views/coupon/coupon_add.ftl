<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>新增优惠券</title>
</head>
<body>

    <div class="tab-content">
         <div class="tab-pane fade in active" id="addUser">
	<!--新增1--start-->
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/coupon/save.do" method="post">
	        <input type="hidden" id="id" name="id" value="${(coupon.id) !}">   
	        <input type="hidden" id="number" name="number" value="${(coupon.number) !}">                 
	        <div class="addForm1">
	       <div id="error_con" class="tips-form">
	           <ul></ul>
	       </div>
	            <div class="tips-form"></div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>优惠券名称：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid"   data-rule-rangelength="[0,10]" data-msg-rangelength="名称长度必须小 10个字符" type="text" id="name" name="name" value="${(coupon.name) !}"   data-rule-required="true" data-msg-required="优惠券名称不能为空"     />                 
	       	 			</div>
	                </div>
	              </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>面值：</label>
	                    <div class="div-form">
		                    <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="面值不能为空"  data-rule-isIntGtZero="true"  data-msg-isIntGtZero="面值必须为大于0" 
		                      data-rule-isInteger="true"  data-msg-isInteger="面值必须是整数" 
		                    type="text" id="faceValue" name="faceValue" value="${(coupon.faceValue) !}">                     
	       	 			</div>
	                </div>
	            </div>
	            <div class="form-tr">
					<!-- <div class="form-td">
							<label><i>*</i>消费限制：</label>
							<div class="div-form"> 
								<input type="radio" name="consumptionLimit"  value="-1"  <#if (coupon.consumptionLimit) ??><#if coupon.consumptionLimit == -1>value="${(coupon.consumptionLimit) !}" checked="checked"</#if></#if>>不限制
							</div>
					</div> -->
               </div>
	             <div class="form-tr">
					<div class="form-td">
						<label><i>*</i>消费限制：</label>
						<div class="div-form"> 
						         满<input type="text" id="consumptionLimit" name="consumptionLimit" value="${(coupon.consumptionLimit) !}" onblur="setConsumpValue(this.value)">元可使用
						</div>
					</div>
	              </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>有效日期：</label>
	                    <div class="div-form"> 
								<input type="radio" name="effectiveDateState"  value="1" <#if (coupon.effectiveDateState) ??><#if coupon.effectiveDateState == '1'>checked="checked" value="${(coupon.effectiveDateState) !}"</#if></#if>>指定有效期
						</div>
	                </div>
	            </div>
	            <div class="form-tr">
						<div class="form-td">
							<label></label>
								<div class="div-form"> 
									 	<input name="effectiveDateStartStr" id="effectiveDateStartStr" class="form-control txt_mid input-sm"  
								onfocus="WdatePicker({minDate:'%y-%M-%d %H:%m:%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(coupon.effectiveDateStart?string('yyyy-MM-dd HH:mm:ss')) !}" >  
									至
										<input name="effectiveDateEndStr" id="effectiveDateEndStr" class="form-control txt_mid input-sm"  
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'effectiveDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(coupon.effectiveDateEnd?string('yyyy-MM-dd HH:mm:ss')) !}"> 									
								</div>
						</div>
	              </div>
	              <div class="form-tr">
						<div class="form-td">
							<label></label>
								<div class="div-form"> 
									<input type="radio" name="effectiveDateState"  value="0"  <#if (coupon.effectiveDateState) ??><#if coupon.effectiveDateState == '0'>value="${(coupon.effectiveDateState) !}" checked="checked"</#if></#if>>自定义有效期，自领取后<input type="text"  name="effectiveDateNum" id="effectiveDateNum" value="${(coupon.effectiveDateNum) !}" >天内有效
								</div>
						</div>
	              </div>
	            <div class="form-tr">
						<div class="form-td">
								<label><i>*</i>选择商品：</label>
								<div class="div-form"> 
									<input type="radio" name="goodsSelect" id="goodsSelect" value="0" onclick="checkGoods()" <#if (coupon.goodsSelect) ??><#if coupon.goodsSelect == '0'>checked="checked"</#if></#if>>全部商品
									<input type="radio" name="goodsSelect" id="goodsSelect"  value="2" onclick="checkGoods()"<#if (coupon.goodsSelect) ??><#if coupon.goodsSelect == '2'>checked="checked"</#if></#if>>指定商品
									<input type="radio" name="goodsSelect" id="goodsSelect"  value="3" onclick="checkGoods()"<#if (coupon.goodsSelect) ??><#if coupon.goodsSelect == '3'>checked="checked"</#if></#if>>指定商品类目
									<input type="radio" name="goodsSelect" id="goodsSelect"  value="1" onclick="checkGoods()"<#if (coupon.goodsSelect) ??><#if coupon.goodsSelect == '1'>checked="checked"</#if></#if>>指定活动
								</div>
						</div>
	             </div>
	             <div class="form-tr" >
					<div class="form-td">
						<label></label>
							<div class="div-form"> 
								<a href="javascript:void(0);" class="btn btn-primary btn-sm" id="button_goods"  onclick="selectGoods('${goodsName! }','edit')" ><i class="fa fa-plus"></i>&nbsp;&nbsp;选择商品</a>
								<a href="javascript:void(0);" class="btn btn-primary btn-sm" id="button_category"  onclick="showAllrole()"><i class="fa fa-plus"></i>&nbsp;&nbsp;选择商品类目</a>
								<a href="javascript:void(0);" class="btn btn-primary btn-sm" id="button_activity"  onclick="selectActivity('edit')" ><i class="fa fa-plus"></i>&nbsp;&nbsp;选择活动</a>
							</div>
					</div>
	             </div>
	             
	              <div class="form-tr" id="goodsForm">
					<div class="form-td">
					<input name="goodsId" id="goodsId" type="hidden"  value="${goodsId! }">
					<input name="activityManageIds" id="activityManageIds" type="hidden"  value="${activityManageIds! }">
					<input name="activitytitles" id="activitytitles" type="hidden"  value="${activitytitles! }">
					<input name="goodsName" id="goodsName" type="hidden"  value="${goodsName! }">
					<input name="categoryId" id="categoryId" type="hidden" value="${categoryId! }">
						<div class="tab-content">
						<div class="tab-pane fade in active" id="memberList" style="height:300px; overflow:auto;overflow-x: hidden; margin-left:120px;width: 700px;">
							<table class="table table-hover table-striped bor2 table-common" id="tableValue" >
								<thead>
									<tr>
									<#if (coupon.goodsSelect) ??>
						             	<#if coupon.goodsSelect=='2'>
						             		<th width="20%">商品名称</th>
						             		<th width="15%">商品编码</th>
											<th width="19%">零售价/元</th>
											<th width="15%">优惠价/元</th>
											<th>操作</th>
						             	<#elseif coupon.goodsSelect == '3'>
						             		<th>商品类目名称</th>
						             		<th>操作</th>
						             	<#elseif coupon.goodsSelect == '1'>
						             		<th>活动名称</th>
						             		<th>操作</th>
						             </#if>	
						           </#if>									
									</tr>
								</thead>
								<tbody>
									<#if (coupon.goodsSelect) ??>
						             	<#if coupon.goodsSelect=='2'>
						             		<#if commodityList?? > 
												<#list commodityList as item >
													<tr id="tr${(item.id) !}">
														<td>${(item.name) !}</td>
														<td>${(item.code) !}</td>
														<td>${(item.retailPrice) !}</td>
														<td>${(item.preferentialPrice) !}</td>		
														<td><a href="javascript:void(0);" onclick="deleteTr('${(item.id) !}')">删除</a></td>					
													</tr>
												</#list> 
											</#if>
						             	<#elseif coupon.goodsSelect == '3'>
						             		<#if categoryList?? > 
												<#list categoryList as item >
													<tr id="tr${(item.id) !}">
														<td>${(item.name) !}</td>
														<td><a href="javascript:void(0);" onclick="deleteCategoryTr('${(item.id) !}')">删除</a></td>							 	
													</tr>
												</#list> 
											</#if>
											<#elseif coupon.goodsSelect == '1'>
						             		<#if activityList?? > 
												<#list activityList as item >
													<tr id="tr${(item.id) !}">
														<td>${(item.title) !}</td>
														<td><a href="javascript:void(0);" onclick="deleteActivityTr('${(item.id) !}')">删除</a></td>							 	
													</tr>
												</#list> 
											</#if>
						             	</#if>
						             </#if>		
								</tbody>
							</table>
						</div>
						</div>
					</div>
	             </div>
	             <!-- 
	             <div class="form-tr">
	                     <div class="form-td">
	                    <label>其他限制：</label>
	                    <div class="div-form">   
	                            <input type="checkbox" name="otherLimit" id="otherLimit" value="1"<#if (coupon.otherLimit) ??><#if coupon.otherLimit == '1'>checked="checked" value="${(coupon.remarks) !}"</#if></#if>>预售,抢购不适用             
	                    </div>
	                </div>
	            </div>
	             -->
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>备注：</label>
	                    <div class="div-form">
	                     <textarea style="height:100px;width:250px;" name="remarks" id="remarks" >${(coupon.remarks) !}</textarea>         
	                    </div>
	                </div>
	            </div> 
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>注：</label>
	                    <div class="div-form">
	                       <label><i>*</i>为必填项</label>         
	                    </div>
	                </div>
	            </div>                     
	            <div class="form-tr">
	                <div class="btn-div2">
	                 	 <a onclick="butonsubmit()" class="btn btn-primary" >保存</a>
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
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css">
<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_add.js?v=${ver !}"></script>

<script  type="text/javascript">
var view="${view!}";
$(function(){
	executeValidateFrom('myform');
    $(".input-select").select2();
	var state='${(coupon.effectiveDateState) !}';
	if(state==null || state==""){
		$("input[type=radio][name=effectiveDateState][value=1]").attr("checked",true); 
	}
	var con='${(coupon.consumptionLimit) !}';
	if(con==null || con==""){
		$("input[type=radio][name=consumptionLimit][value=-1]").attr("checked",true); 
	}
	var gos='${(coupon.goodsSelect) !}';	
	if(gos==null || gos==""){
		$("input[type=radio][name=goodsSelect][value=0]").attr("checked",true); 
	}	
    var goodsSelect=$('input[name="goodsSelect"]:checked').val();
    if(goodsSelect=="1"){	

		$("#button_activity").show();
		$("#button_goods").hide();
		$("#button_category").hide();
	}
    else if(goodsSelect=="2"){
    	$("#goodsForm").show();
		$("#button_goods").css("display","block");
		$("#button_category").css("display","none");
		$("#button_activity").hide();
		buttonType=1;
	}else if(goodsSelect=="3"){
		$("#goodsForm").show();
		$("#button_goods").css("display","none");
		$("#button_category").css("display","block");
		$("#button_activity").hide();
		buttonType=0;
	}else if(goodsSelect=="0"){
		$("#goodsForm").hide();
		$("#button_goods").css("display","none");
		$("#button_category").css("display","none");
		$("#button_activity").hide();
	}
});   

function back(){
	$.frontEngineDialog.executeDialog('isReturn_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否确定放弃当前录入信息？　　','100%','100%',
				function(){
					window.location.href= rootPath + "/coupon/list.do";
				}
			);
	}
</script>
</body>
</html>