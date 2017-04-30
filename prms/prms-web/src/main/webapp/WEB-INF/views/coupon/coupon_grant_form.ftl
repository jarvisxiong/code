<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="edit" />
	<style>
.endclassOk{color:#E0E0E0;}
.couponoverclass{
height:200px;
overflow-y:scroll;
}
</style>
	<title>发放优惠券</title>
</head>
<body>
    <div class="tab-content">
         <div class="tab-pane fade in active" id="addUser">
	<!--新增1--start-->
	<form action="${BasePath !}/couponGrant/memberlist.do" method="post" target="my_ifram_memember" name="iframmemembe">
    <input name="noidList" id="noidList" type="hidden" value="${idList!""}"  />
</form>

	<form action="${BasePath !}/couponGrant/couponAdminList.do" method="post" target="my_ifram_coupon" name="iframcoupon">
    <input name="noidList" id="noidList2" type="hidden" value="${couponIdList!""}"   />
</form>
	<div class="row">
	    <div class="col-lg-10 col-md-12 col-sm-12">
	        <form id="myform" action="${BasePath !}/couponGrant/save.do" method="post">
	        <input type="hidden" id="id" name="id" value="${(couponGrant.id) !}">  
	        <input type="hidden" id="receiveState" name="receiveState" value="${(couponGrant.receiveState) !"0"}">                    
	        <div class="addForm1"><br></br>
	   基础信息
	   <hr></hr>
	       <div id="error_con" class="tips-form">
	           <ul></ul>
	       </div>
	            <div class="tips-form"></div>
	            <div class="form-tr">
	                <div class="form-td">
	                    <label><i></i>发放名称：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="发放名称不能为空" type="text" id="name" name="name" value="${(couponGrant.name) !}" data-rule-rangelength="[1,25]" data-msg-rangelength="名称长度必须小 25个字符"  />   
	                                      
	       	 			</div>
	                </div>
	              </div>
	               <div class="form-tr">
	                <div class="form-td">
	                    <label><i></i>发放类型：</label>
	                    <input class="form-control input-sm txt_mid"  type="text"  value="优惠券" disabled  />   
	                     
	                    <input class="form-control input-sm txt_mid"  type="hidden" id="grantType" name="grantType" value="0"   />  <!--hidden --> 
	                     
	                </div>
	            </div>
	            
	            
	           	<div class="form-tr">
	                <div class="form-td">
	                    <label><i></i>发放方式：</label>
	                    <input type="radio" name="grantMode" class="grantMode"   value="0"   <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '0'>
	                    checked <#else> disabled="disabled" </#if><#else> checked   </#if> />系统推送
						<input type="radio" name="grantMode" class="grantMode"  value="1" <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '1'>
						checked <#else> disabled="disabled" </#if></#if> />用户领取
						<input type="radio" name="grantMode" class="grantMode"  value="4" <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '4'>
						checked <#else> disabled="disabled" </#if></#if> />消息优惠券推送
						<input type="radio" name="grantMode" class="grantMode"  value="5" <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '5'>
						checked <#else> disabled="disabled" </#if></#if> />天降优惠券推送
									
	                </div>
	            </div>	
	               
	            <div class="form-tr">
						<div class="form-td">
							<label><i></i>发放时间：</label>
								<div class="div-form"> 
									<input name="grantDateStr" id="grantDateStr" class="form-control txt_mid input-sm"  <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '1'>disabled </#if></#if> 
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:%d',maxDate:'#F{$dp.$D(\'grantEndDateStr\')}'})" value="${(couponGrant.grantDate?string('yyyy-MM-dd HH:mm:ss')) !}" data-rule-required="true" data-msg-required="发放时间不能为空"  >  									
								</div>
						</div>
	              </div>
	              
	            <div class="form-tr">
						<div class="form-td" id="endclass">
							<label><i></i>结束时间：</label>
								<div class="div-form"> 
									<input name="grantEndDateStr" id="grantEndDateStr" class="form-control txt_mid input-sm"  <#if (couponGrant.grantMode) ??>disabled </#if>
								 onfocus="WdatePicker({minDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'grantDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(couponGrant.grantEndDate?string('yyyy-MM-dd HH:mm:ss')) !}"
								data-rule-required="true" data-msg-required="发放结束时间不能为空"  >  									
								</div>
						</div>
	              </div>
	            <div class="form-tr modehidden">
	                <div class="form-td" >
	                    <label><i></i>发放总量：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-isInteger="true" data-msg-isInteger="发放总量必须大于0" data-rule-isIntGtZero="true" data-msg-isIntGtZero="发放总量必须大于0"  type="text" id="grantNum" name="grantNum" value="${(couponGrant.grantNum) !}" data-rule-isIntGtZero="true"  data-msg-isIntGtZero="发放总量必须为整数"  />&nbsp;张                       
	       	 			</div>
	                </div>
	              </div>
	               
	             <div class="form-tr idLimithide">
								<div class="form-td"  id="endclass2">
									<label><i></i>
	            <span class="nomodehidden">  ID发放数量：</span>
	            <span class="modehidden"> 	每人限领：</span>
	            </label>
									<div class="div-form"> 
										<input class="form-control input-sm txt_mid"   data-rule-isIntGtZero="true" data-msg-isIntGtZero="发放总量必须大于0" 
										type="text" id="receiveLimit" name="receiveLimit" value="${(couponGrant.receiveLimit) !'1'}"   data-rule-isIntGtZero="true"  data-msg-isIntGtZero="每人限领必须为整数"      />
										<span class="modehidden">
										&nbsp;次         
										</span>       
									</div>
								</div>
	                </div>
	    
	            
	            <div class="form-tr">
	                <div class="form-td">
	                    选择用户
	                   			
	                </div>
	            </div>	
	            <br/>
	                    
	   <hr></hr>
	                   			
	                
	            	<div class="form-tr">
	                <div class="form-td">
	                    <label><i></i></label>
	                    <input type="radio" name="userType" class="userType"   value="0"   <#if (couponGrant.userType) ??><#if couponGrant.userType == '0'>
	                    checked="checked" <#else> disabled</#if> </#if>>所有用户
						<input type="radio" name="userType" class="userType"   value="1" <#if (couponGrant.userType) ??><#if couponGrant.userType == '1'>
						checked="checked" <#else> disabled</#if></#if>>指定用户
						<input type="radio" name="userType" class="userType"   value="2" <#if (couponGrant.userType) ??><#if couponGrant.userType == '2'>
						checked="checked" <#else> disabled</#if></#if>>新用户（发放时间内注册的用户）
						<input type="radio" name="userType" class="userType"   value="3" <#if (couponGrant.userType) ??><#if couponGrant.userType == '3'>
						checked="checked" <#else> disabled</#if></#if>>指定用户标签		
	                </div>
	            </div>	
	               
				               <div class="form-tr" id="selectuserlable" >
				                <div class="form-td">
				                    <label><i></i> <input  class="btn btn-primary btn-sm btn-inquire"
				                     onClick='showMemberlable()' type ="button" value="选择用户标签" / >
				                    </label>
				                    <input class="form-control input-sm txt_mid" 
				                           type="hidden" id="userlableList" name="userlableList" value="${couponGrant.userlableList!""}" > 
					                <div class="div-form">
					               		<div  style="height:220px;overflow-y:auto;width:400;margin-left: 40px;">
											<table class="table table-hover table-striped bor2 table-common" >
												<thead>
													<tr>
												        <th width="50%">编码</th>
														<th width="40%">名称</th>
														<th width="10%">操作</th>
													</tr>
												</thead>
												<tbody id="tabletdLable">
													<#if couponGrant?? &&  couponGrant.userLables?? > 
													<#list couponGrant.userLables as item >
													<tr>
													<td>${(item.lablenumber) !}</td>
													<td>${(item.lablename) !}</td>
													<td><a href="javascript:;" onClick="getDelLable(this,'${(item.lableId) !}')">删除</a></td></tr>
													</#list> 
													</#if>
													<tr>
												</tbody>
											</table>
										</div>
									</div>
				                </div>
				            </div> 
				            
	               
	               
	               <div class="form-tr" id="selectuser" >
	                <div class="form-td">
	                    <label><i></i> 
	                    </label>
	                    <input  class="btn btn-primary btn-sm btn-inquire"
	                     onClick='showMember()' type ="button" value="选择用户" / >
                        <input class="form-control input-sm txt_mid" 
                               type="hidden" id="userList" name="userList" value="${idList!""}" > 
                               &nbsp;&nbsp;
	                    <input  class="btn btn-primary btn-sm btn-inquire" onclick="importview('${couponGrant.id !}')"
	                      type ="button" value="批量导入" / >
 						
                        <br/><br/>
	                   
                          
	               		<div  style="height:220px;overflow-y:auto;width:400;margin-left: 40px;">
						<table class="table table-hover table-striped bor2 table-common" >
							<thead>
								<tr>
							        <th width="50%">昵称</th>
									<th width="40%">手机账号</th>
									<th width="10%">操作</th>
								</tr>
							</thead>
							<tbody id="tabletd">
								<#if userList?? > 
								<#list userList as item >
								<tr>
								<td>${(item.nickName) !}</td>
								<td>${(item.phone) !}</td>
								<td><a href="javascript:;" onClick="getDel(this,'${(item.id) !}')">删除</a></td></tr>
								</#list> 
								</#if>
								<tr>
							</tbody>
						</table>
						</div>
	                </div>
	            </div> 
	              <div class="form-tr">
	                <div class="form-td">
	                    选择优惠券
	                   			
	                </div>
	            </div>
	       <br/>
	       <hr></hr>
	       <div class="form-tr" id="selectuser" >
	                <div class="form-td">
	                    <label><i></i></label>
	                    <input id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire"
	                     onClick='showCouponAdmin()' type ="button" value="+选择优惠券" / >
	                    &nbsp;&nbsp;
						<br><br>	
                        <input class="form-control input-sm txt_mid" 
                               type="hidden" id="couponAdminList" name="couponAdminList"  value="${couponIdList!""}" > 
            
	               		<div  style="height:200px;overflow-y:auto;margin-left: 40px;">
						<table class="table table-hover table-striped bor2 table-common">
							<thead>
								<tr>
							        <th width="10%">编码</th>
									<th width="10%">名称</th>
									<th width="10%">面值</th>
									<th width="10%">消费限制</th>
									<th width="20%">有效期</th>
									<th width="10%">操作</th>
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
									<td><a href="javascript:;" onClick="delCoupon(this,'${(item.id) !}')">删除</a></td></tr>
								</tr>
								</#list> 
								</#if>
							</tbody>
						</table>
						</div>
	                </div>
	                
	            </div>           
	            <div class="form-tr">
	                <div class="btn-div2">
	                 	 <button type="submit" class="btn btn-primary"  >保存</button>
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
	<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_grant_form.js?v=${ver !}"></script>
<script type="text/javascript">
var  rootPath="${BasePath !}";
var viewstatus="${viewstatus !}";
var grantMode="${(couponGrant.grantMode)!}";
var userType="${(couponGrant.userType)!}";
    $(function(){
        executeValidateFrom('myform','saveCoupon');
        $(".input-select").select2();
    });

function back(){
$.frontEngineDialog.executeDialog('isReturn_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否确定放弃当前录入信息？　　','100%','100%',
			function(){
				window.location.href= rootPath + "/couponGrant/list.do";
			}
		);
}
</script>
</body>
</html>