<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="edit" />
	<style>
.endclassOk{color:#E0E0E0;}
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
	                    <label><i>*</i>发放名称：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="发放名称不能为空" type="text" id="name" name="name" value="${(couponGrant.name) !}" data-rule-rangelength="[1,25]" data-msg-rangelength="名称长度必须小 25个字符"  />   
	                                      
	       	 			</div>
	                </div>
	              </div>
	               <div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>发放类型：</label>
	                     <input class="form-control input-sm txt_mid"  type="text"  value="优惠码" disabled  />   
	                     
	                    <input class="form-control input-sm txt_mid"  type="hidden" id="grantType" name="grantType" value="3"   />  <!--hidden --> 
	                     
	                </div>
	            </div>
	            
	            
	           	<div class="form-tr">
	                <div class="form-td">
	                    <label><i>*</i>发放方式：</label>
							<select id="grantMode" name="grantMode" class="form-control txt_mid input-sm"   > 
										
										<option value="1" <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '1'> selected="selected" </#if></#if> >用户领取</option> 
									
										</select>
	                </div>
	            </div>	
	                 
	            <div class="form-tr">
						<div class="form-td">
							<label><i>*</i>发放时间：</label>
								<div class="div-form"> 
									<input name="grantDateStr" id="grantDateStr" class="form-control txt_mid input-sm"  <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '1'>disabled </#if></#if> 
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:%d',maxDate:'#F{$dp.$D(\'grantEndDateStr\')}'})" value="${(couponGrant.grantDate?string('yyyy-MM-dd HH:mm:ss')) !}" data-rule-required="true" data-msg-required="发放时间不能为空"  >  									
								</div>
						</div>
	              </div>
	              
	            <div class="form-tr">
						<div class="form-td" id="endclass">
							<label><i>*</i>结束时间：</label>
								<div class="div-form"> 
									<input name="grantEndDateStr" id="grantEndDateStr" class="form-control txt_mid input-sm"  <#if (couponGrant.grantMode) ??>disabled </#if>
								 onfocus="WdatePicker({minDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'grantDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(couponGrant.grantEndDate?string('yyyy-MM-dd HH:mm:ss')) !}"
								data-rule-required="true" data-msg-required="发放结束时间不能为空"  >  									
								</div>
						</div>
	              </div>
	            <div class="form-tr">
	                <div class="form-td"  id="endclass1">
	                    <label><i>*</i>发放总量：</label>
	                    <div class="div-form">
	                        <input class="form-control input-sm txt_mid" data-rule-isIntGtZero="true" data-msg-isIntGtZero="发放总量必须大于0"   data-rule-required="true" data-msg-required="发放总量不能为空" type="text" id="grantNum" name="grantNum" value="${(couponGrant.grantNum) !}" data-rule-isIntGtZero="true"  data-msg-isIntGtZero="发放总量必须为整数"
	                        <#if (couponGrant.grantNum) ??>disabled</#if>
	                          />&nbsp;张                       
	       	 			</div>
	                </div>
	              </div>
	               
	             <div class="form-tr">
								<div class="form-td"  id="endclass2">
									<label><i>*</i>ID限领数量：</label>
									<div class="div-form"> 
										<input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="领取限制不能为空"  data-rule-isIntGtZero="true" data-msg-isIntGtZero="发放总量必须大于0" 
										type="text" id="receiveLimit" name="receiveLimit" value="${(couponGrant.receiveLimit) !'1'}"   data-rule-isIntGtZero="true"  data-msg-isIntGtZero="每人限领必须为整数"      />&nbsp;次                
									</div>
								</div>
	                </div>
	               
	            <!-- <div class="form-tr">
	                     <div class="form-td">
	                    <label>推广设置：</label>
	                    <div class="div-form">   
	                           <input type="checkbox" id="url" name="url" value="1" <#if (couponGrant.url) ??><#if couponGrant.url == '1'> checked</#if></#if> />允许分享领取链接       
	                    </div>
	                </div>
	            </div>
	             -->
	            <div class="form-tr">
	                <div class="form-td">
	                    <label>备注：</label>
	                    <div class="div-form">
	                     <textarea style="height:100px;width:98%" name="remarks" id="remarks"  data-rule-rangelength="[0,250]" data-msg-rangelength="名称长度必须小 250个字符" >${(couponGrant.remarks) !""}</textarea>         
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
	            <br></br><br></br>
	       基本规则
	       <hr></hr>
	       <div class="form-tr" id="selectuser" >
	                <div class="form-td">
	                    <label><i>*</i></label>
	                    <input id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire"
	                     onClick='showCouponAdmin()' type ="button" value="+选择优惠券" / >
	                    &nbsp;&nbsp;
						<br><br>	
                        <input class="form-control input-sm txt_mid" 
                               type="hidden" id="couponAdminList" name="couponAdminList"  value="${couponIdList!""}" > 
            
	               		<div  style="height:200px;overflow-y:scroll;margin-left: 40px;">
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
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css" type="text/css">
	<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_grant_code_form.js"></script>
<script type="text/javascript">
var  rootPath="${BasePath !}";
var viewstatus="${viewstatus !}";
var grantMode="${(couponGrant.grantMode)!}";
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