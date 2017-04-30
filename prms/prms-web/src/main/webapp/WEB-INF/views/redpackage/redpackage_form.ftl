<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>红包新增/编辑</title>
<style type="text/css">
   
</style>	
</head>
<body>
 <ul class="nav nav-tabs" style="padding-left: 1%;">
      <li class="active"><a href="#">红包<#if redpackage.id ??>编辑<#else>新增</#if></a></li>
  </ul>

<div class="row">
       <div class="col-md-12">
         <div class="box-body">
           <div class="tab-content">
               <div class="tab-pane fade in active" id="addUser">
                   <!--新增1--start-->
                   <div class="row">
                       <div class="col-lg-10 col-md-12 col-sm-12">
                           <form id="myform"  action="${BasePath !}/redpackage/save.do" method="post">
	                         <input type="hidden" id="id" name="id" value="${(redpackage.id) !}">   
					        <input type="hidden" id="number" name="number" value="${(redpackage.number) !}">                 
					        <div class="addForm1">
					       <div id="error_con" class="tips-form">
					           <ul></ul>
					       </div>
					            <div class="tips-form"></div>
					            <div class="form-tr">
					                <div class="form-td">
					                    <label><i>*</i>红包名称：</label>
					                    <div class="div-form">
					                        <input class="form-control input-sm txt_mid"  type="text" id="name" name="name" value="${(redpackage.name) !}"   data-rule-required="true" data-msg-required="红包名称不能为空"   />
					                        （仅限输入12个汉字以内）                   
					       	 			</div>
					                </div>
					              </div>
					             <div class="form-tr">
					                <div class="form-td">
					                    <label><i>*</i>面值(单位元)：</label>
					                    <div class="div-form">
						                    <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="面值不能为空"  
						                      data-rule-decimal="true"  data-msg-decimal="面值浮点数不能超过两位"  
						                    type="text" id="fackValue" name="fackValue" value="${(redpackage.fackValue) !}">                     
					       	 			</div>
					                </div>
					            </div>
					             <div class="form-tr">
					                <div class="form-td">
					                    <label><i>*</i>发放数量：</label>
					                    <div class="div-form">
						                    <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="发放数量不能为空"  data-rule-isIntGtZero="true"  data-msg-isIntGtZero="发放数量必须为大于0" 
						                      data-rule-isInteger="true"  data-msg-isInteger="发放数量必须是整数"   
						                    type="text" id="grantNum" name="grantNum" value="${(redpackage.grantNum) !}">                     
					       	 			</div>
					                </div>
					            </div>
					            <div class="form-tr">
					           </div>
					             <div class="form-tr">
									<div class="form-td">
										<label><i>*</i>使用限制：</label>
										<div class="div-form"> 
										  订单商品金额-已使用优惠券金额≥1张红包面额或多张红包面额之和；无限制，可叠加。     
										</div>
									</div>
					              </div>
					               <div class="form-tr">
									<div class="form-td">
										<label><i>*</i>领取限制：</label>
										<div class="div-form"> 
										  活动期间每人限领1张  
										</div>
									</div>
					              </div>
					              
					             <div class="form-tr">
					                <div class="form-td">
					                    <label><i>*</i>使用有效期：</label>
					                    <div class="div-form"> 
													<input type="radio" name="dateStart"  value="1"  <#if (redpackage.dateStart) ??><#if redpackage.dateStart == '1'>value="${(redpackage.dateStart) !}" checked="checked"</#if></#if>>
													自领取后<input type="text"  name="receivedate" id="receivedate" value="${(redpackage.receivedate) !}"    data-rule-isInteger="true"  data-msg-isInteger="自领取天数必须为正整数"  / >天内有效
										 </div>
					                </div>
					            </div>
					              
					             
					            <div class="form-tr">
										<div class="form-td">
											<label></label>
												<div class="div-form"> 
												<input type="radio" name="dateStart"  value="0" <#if (redpackage.dateStart) ??><#if redpackage.dateStart == '0'>checked="checked" value="${(redpackage.dateStart) !}"</#if></#if>>
												
													 	<input name="startDate" id="startDate" class="form-control txt_mid input-sm"  
												onfocus="WdatePicker({minDate:'%y-%M-%d %H:%m:%d',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\')}'})"  value="${(redpackage.startDate?string('yyyy-MM-dd HH:mm:ss')) !}" / >  
													至
														<input name="endDate" id="endDate" class="form-control txt_mid input-sm"  
												onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(redpackage.endDate?string('yyyy-MM-dd HH:mm:ss')) !}"> 									
												</div>
										</div>
					              </div>
					                
					                 <div class="form-tr">
					                <div class="form-td">
					                    <label><i>*</i>状态：</label>
					                    <div class="div-form"> 
											<input type="radio" name="state"  value="1"  <#if (redpackage.state) ??><#if redpackage.state == '1'>value="${(redpackage.state) !}" checked="checked"</#if></#if>>
											启用 
											<input type="radio" name="state"  value="0"  <#if (redpackage.state) ??>
											<#if redpackage.state == '0'>value="${(redpackage.state) !}" checked="checked"</#if>
											<#else>
											checked="checked"
											</#if>>
											禁用
										</div>
					                </div>
					            </div>              
			                    <div class="form-tr">
			                        <div class="btn-div">
			                            <#if viewType == '0'><input type="submit" class="btn btn-primary" value="保存"></#if>
			                        	<input type="button" id="returnBack"  class="btn btn-default btn-close-iframeFullPage hide" value="返回" />
			                        	
                                         <input type="button" class="btn btn-default" id="commmonBack" value="返回" >
			                        </div>
			                    </div>
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
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?ver=${ver !}" type="text/css"> 
<script type="text/javascript" src="${BasePath !}/asset/js/redpackage/redpackage_form.js?ver=${ver !}"></script>
<script type="text/javascript">
$(function(){
        executeValidateFrom('myform','saveRedpackage');
        
    }); 
    
</script>
</body>
</html>