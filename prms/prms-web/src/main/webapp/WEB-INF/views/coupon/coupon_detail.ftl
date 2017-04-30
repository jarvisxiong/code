<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>优惠券详情</title>
</head>
<body>

    <div class="tab-content">
         <div class="tab-pane fade in active" id="addUser">
	<!--新增1--start-->
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/member/editMember.do" method="post">
	        <input type="hidden" id="id" name="id" value="${(coupon.id) !}">                    
	        <div class="addForm1">
	       <div id="error_con" class="tips-form">
	           <ul></ul>
	       </div>
	            <div class="tips-form"></div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>优惠券名称：</label>
	                    <div class="div-form">
	       	 				${(coupon.name) !}
	       	 			</div>
	                </div>
	              </div>
	              <div class="form-tr">
	                <div class="form-td">
	                    <label>编码：</label>
	                    <div class="div-form">
	       	 				${(coupon.number) !}
	       	 			</div>
	                </div>
	              </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label>面值：</label>
	                    <div class="div-form">
		                    ${(coupon.faceValue) !}                    
	       	 			</div>
	                </div>
	            </div>
	            <div class="form-tr">
					<div class="form-td">
							<label>消费限制：</label>
							<div class="div-form"> 
								<#if (coupon.consumptionLimit) ??>
									<#if coupon.consumptionLimit == -1>
										不限制
									<#else>
										满${(coupon.consumptionLimit) !}元使用									
									</#if>
								</#if> 								
							</div>
					</div>
	               </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label>有效日期：</label>
	                    <div class="div-form"> 
	                    		<#if (coupon.effectiveDateState) ??>
		                    		<#if coupon.effectiveDateState == '1'>
										${(coupon.effectiveDateStart?string('yyyy-MM-dd HH:mm:ss')) !}
										至${(coupon.effectiveDateEnd?string('yyyy-MM-dd HH:mm:ss')) !}								
									<#else>
										自领取后${(coupon.effectiveDateNum) !}天有效
									</#if>
								</#if> 	
						</div>
	                </div>
	            </div>
	            <div class="form-tr">
						<div class="form-td">
								<label>选择商品：</label>
								<div class="div-form"> 
									<#if (coupon.goodsSelect) ??>
										<#if coupon.goodsSelect == '0'>
											全部商品
										<#elseif coupon.goodsSelect == '2'>
											指定商品
										<#elseif coupon.goodsSelect == '3'>
											指定商品类目
										 <#elseif coupon.goodsSelect == '1'>
											指定活动
										</#if> 
									</#if>		
								</div>
						</div>
	             </div>

	             <div class="form-tr">
						<div class="form-td">
						<div class="tab-content">
						<div class="tab-pane fade in active" id="memberList" style="height:300px; overflow:auto;overflow-x: hidden; margin-left:120px;width: 700px;">
							<table class="table table-hover table-striped bor2 table-common" id="tableValue" >
								<thead>
									<tr>
									<#if (coupon.goodsSelect) ??>
						             	<#if coupon.goodsSelect=='2'>
						             		<th width="15%">商品名称</th>
						             		<th width="15%">商品编码</th>
											<th width="19%">零售价/元</th>
											<th width="15%">优惠价/元</th>
						             	<#elseif coupon.goodsSelect == '3'>
						             		<th>商品类目名称</th>
					             		<#elseif coupon.goodsSelect == '1'>
					             		<th>活动名称</th>
						             	</#if>
						             </#if>										
									</tr>
								</thead>
								<tbody>
									<#if (coupon.goodsSelect) ??>
						             	<#if coupon.goodsSelect=='2'>
						             		<#if commodityList?? > 
												<#list commodityList as item >
												<tr>
													<td>${(item.name) !}</td>
													<td>${(item.code) !}</td>
													<td>${(item.retailPrice) !}</td>
													<td>${(item.preferentialPrice) !}</td>							
												</tr>
												</#list> 
											</#if>
						             	<#elseif coupon.goodsSelect == '3'>
						             		<#if categoryList?? > 
												<#list categoryList as item >
												<tr>
													<td>${(item.name) !}</td>						 	
												</tr>
												</#list> 
											</#if>
											<#elseif coupon.goodsSelect == '1'>
						             		<#if activityList?? > 
												<#list activityList as item >
													<tr id="tr${(item.id) !}">
														<td>${(item.title) !}</td>						 	
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
	                         <#if (coupon.otherLimit) ??>
	                         	<#if coupon.otherLimit == '1'>
									预售，抢购不使用
								<#else>
									不限制
								</#if>
							 </#if> 	     
	                    </div>
	                </div>
	            </div>
	             -->
	          	<div class="form-tr">
	                     <div class="form-td">
	                    <label>启用状态：</label>
	                    <div class="div-form">   
	                        <#if (coupon.delFlag) ??>
		                        <#if coupon.delFlag == '0'>
										未启用
								<#elseif coupon.delFlag =='1'>
										启用
								</#if> 	 
							</#if>	     
	                    </div>
	                </div>
	           </div>
	            <div class="form-tr">
	                     <div class="form-td">
	                    <label>创建人：</label>
	                    <div class="div-form">   
							${(coupon.createBy.name) !}  
	                    </div>
	                </div>
	            </div>
	            <div class="form-tr">
	                     <div class="form-td">
	                    <label>创建时间：</label>
	                    <div class="div-form">   
								${(coupon.createDate?string('yyyy-MM-dd HH:mm:ss')) !}
	                    </div>
	                </div>
	            </div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>备注：</label>
	                    <div class="div-form">
	                     ${(coupon.remarks) !}         
	                    </div>
	                </div>
	            </div> 
	                                 
	            <div class="form-tr">
	                <div class="btn-div2">
	                    <input type="button" class="btn btn-default" value="返回" onclick="history.go(-1);">
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
</body>
</html>