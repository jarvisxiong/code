<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>发放优惠券</title>
</head>
<body>

    <div class="tab-content">
         <div class="tab-pane fade in active" id="">
	<!--新增1--start-->
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="" method="post">
	        <div class="addForm1">
	       <div id="error_con" class="tips-form">
	           <ul></ul>
	       </div>
	       基本信息
	       <hr></hr>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>发放编码：</label>
	                    <div class="div-form">
	                        ${(couponGrant.number) !}                 
	       	 			</div>
	                </div>
	              </div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>发放名称：</label>
	                    <div class="div-form">
	                        ${(couponGrant.name) !}                 
	       	 			</div>
	                </div>
	              </div>
	             <div class="form-tr">
	                <div class="form-td">
	                    <label>发放类型：</label>
	                     <div class="div-form">
	                        <#if (couponGrant.grantType) ??>
	                        <#if couponGrant.grantType == '0'>优惠券
	                        <#else>
	                        	<#if couponGrant.grantType == '3'> 优惠码 </#if>
	                        </#if>
	                        </#if>
	       	 			</div>
	                </div>
	            </div>
	           	<div class="form-tr">
	                <div class="form-td">
	                    <label>发放方式：</label>
	                    <div class="div-form">
	                        <#if (couponGrant.grantMode) ??>
	                        <#if couponGrant.grantMode == '0'>系统推送
	                        <#else>
	                        	<#if couponGrant.grantMode == '1'>
	                        		用户领取
	                        	</#if>
	                        	<#if couponGrant.grantMode == '4'>
	                        		消息优惠券推送
	                        	</#if>
	                        	<#if couponGrant.grantMode == '5'>
	                        		天降优惠券推送
	                        	</#if>
	                        </#if>
	                        </#if>
	       	 			</div>
	                </div>
	            </div>	          
	            
	            <div class="form-tr">
						<div class="form-td">
							<label>发放时间：</label>
								${(couponGrant.grantDate?string('yyyy-MM-dd HH:mm:ss')) !}									
								
						</div>
	              </div>
	            <div class="form-tr">
						<div class="form-td">
							<label>结束时间：</label>
								${(couponGrant.grantEndDate?string('yyyy-MM-dd HH:mm:ss')) !}									
								
						</div>
	              </div>
	               <#if (couponGrant.grantNum) ??>
	            <div class="form-tr">
						<div class="form-td">
							<label>发放总量：</label>
								${(couponGrant.grantNum) !}	张								
								
						</div>
	              </div>
	               </#if>
	            <div class="form-tr">
						<div class="form-td">
							<label>   
							<#if (couponGrant.grantType=='0')  && (couponGrant.grantMode == '1') >
							每人限领：
							<#else>
							ID发放数量 ：
                        	</#if>
							</label>
								${(couponGrant.receiveLimit) !}	次							
								
						</div>
	              </div>
	             
	                
	             
									
              	<div class="form-tr">
                	<div class="form-td">
                    	<label>发放状态：</label>
                    	<div class="div-form">
                      	  <#if (couponGrant.isGrant) ??>
                      	  <#if couponGrant.isGrant == '0'>未发放
                      	  <#else>
                        		<#if couponGrant.isGrant == '1'>
                        			已结束
                        		</#if>
                        		<#if couponGrant.isGrant == '2'>
                        			进行中
                        		</#if>
                        	</#if>
                        	</#if>
       	 				</div>
               	 	</div>
          	  	</div>	
          	  	
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>创建人：</label>
	                    <div class="div-form">
	                        ${(couponGrant.createBy.id) !}                    
	       	 			</div>
	                </div>
	            </div> 
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>创建时间：</label>
	                    <div class="div-form">
	                        ${(couponGrant.createDate?string('yyyy-MM-dd HH:mm:ss')) !}                    
	       	 			</div>
	                </div>
	            </div> 
	            <#if (couponGrant.grantType=='3') >
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>备注：</label>
	                    <div class="div-form">
	                        ${(couponGrant.remarks) !}                    
	       	 			</div>
	                </div>
	            </div> 
	            </#if>
	            
	            <#if (couponGrant.grantType!='3') >
		              <div class="form-tr">
		                <div class="form-td">
		                    选择用户
		                   			
		                </div>
		            </div>	
		            <br/>
		                 
		   <hr></hr>
		              <div class="form-tr">
							<div class="form-td">
								<label>选择用户：</label>
									     <#if (couponGrant.userType) ??><#if couponGrant.userType == '0'>
		                     所有用户 </#if> </#if>
		                      <#if (couponGrant.userType) ??><#if couponGrant.userType == '1'>
		                     指定用户</#if> </#if>
		                      <#if (couponGrant.userType) ??><#if couponGrant.userType == '2'>
		                     新用户（发放时间内注册的用户）</#if> </#if>
		                     
		                      <#if (couponGrant.userType) ??><#if couponGrant.userType == '3'>
		                     指定用户标签	</#if> </#if>
							
							
							
		               <#if couponGrant.userType == '3'>
				               <div class="form-tr" id="selectuserlable" >
				                <div class="form-td">
				                    <label><i></i> 
				                    </label>
				                    <input class="form-control input-sm txt_mid" 
				                           type="hidden" id="userlableList" name="userlableList" value="${couponGrant.userlableList!""}" > 
					                <div class="div-form">
					               		<div  style="height:220px;overflow-y:auto;width:400px;margin-left: 40px;">
											<table class="table table-hover table-striped bor2 table-common" >
												<thead>
													<tr>
												        <th width="50%">编码</th>
														<th width="50%">名称</th>
													</tr>
												</thead>
												<tbody id="tabletd">
													<#if couponGrant?? &&  couponGrant.userLables?? > 
													<#list couponGrant.userLables as item >
													<tr>
													<td>${(item.lablename) !}</td>
													<td>${(item.lablenumber) !}</td>
													</tr>
													</#list> 
													</#if>
													<tr>
												</tbody>
											</table>
										</div>
									</div>
				                </div>
				            </div> 
				            
						</#if>
		               <#if couponGrant.userType == '1'>
			               <div class="form-tr" id="selectuser" >
			                <div class="form-td">
			                 <label></label>
			               		<div  style="height:220px;width: 300px;overflow-y:auto;">
								<table class="table table-hover table-striped bor2 table-common" >
									<thead>
										<tr>
									        <th width="%40">昵称</th>
											<th width="%60">手机账号</th>
										</tr>
									</thead>
									<tbody id="tabletd">
										<#if userList?? > 
										<#list userList as item >
										<tr>
										<td>${(item.nickName) !}</td>
										<td>${(item.phone) !}</td></tr>
										</#list> 
										</#if>
										<tr>
									</tbody>
								</table>
								</div>
			                </div>
		            
					</#if>  					
									
							</div>
		              </div>
		              
	            </#if>
	            <div class="form-tr">
	                <div class="form-td">
	                    选择优惠券
	                   			
	                </div>
	            </div>
	       <br/>
	       <hr></hr>
	       
	            <div class="form-tr">
	             <div  style="height:200px;overflow-y:auto;">
		            <table class="table table-hover table-striped bor2 table-common">
					<thead>
						<tr>
					        <th width="10%">编码</th>
							<th width="10%">优惠券名称</th>
							<th width="10%">面值</th>
							<th width="10%">消费限制</th>
							<th width="20%">有效期</th>
						</tr>
					</thead>
					<tbody id="admintabletd">
						<#if couponList?? > 
						<#list couponList as item >
						<tr>
							<td>${(item.number) !}</td>
							<td>${(item.name) !}</td>
							<td>${(item.faceValue) !}</td>
							<td>
								<#if (item.consumptionLimit) ??><#if item.consumptionLimit == -1>  无限制
								<#else>
								满${(item.consumptionLimit) !}元使用
								</#if>
								</#if> 
							</td>
							<#if (item.effectiveDateState) ??><#if item.effectiveDateState =='0'>
								<td>${(item.effectiveDateNum) !}天</td>
							<#else>
								<td>${(item.effectiveDateStart?string('yyyy-MM-dd HH:mm:ss')) !}-${(item.effectiveDateEnd?string('yyyy-MM-dd HH:mm:ss')) !}</td>			
							</#if>
							</#if>
						</tr>
						</#list> 
						</#if>
					</tbody>
				</table>
				</div>
			<br></br>
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
<script type="text/javascript">
    $(function(){
        executeValidateFrom('myform','loadIds');
        $(".input-select").select2();
    });    
</script>
</body>
</html>