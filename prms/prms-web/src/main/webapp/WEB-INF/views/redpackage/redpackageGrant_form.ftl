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
      <li class="active"><a href="#"><#if redpackageGrant.id ??>编辑<#else>新增</#if>红包发放</a></li>
  </ul>

<div class="row">
       <div class="col-md-12">
         <div class="box-body">
           <div class="tab-content">
               <div class="tab-pane fade in active" id="addUser">
                   <!--新增1--start-->
                   <div class="row">
                       <div class="col-lg-10 col-md-12 col-sm-12">
                           <form id="myform"  action="${BasePath !}/redpackageGrant/save.do" method="post">
	                         <input type="hidden" id="id" name="id" value="${(redpackageGrant.id) !}">                
					        <div class="addForm1">
					       <div id="error_con" class="tips-form">
					           <ul></ul>
					       </div>
					            <div class="tips-form"></div>
					            <div class="form-tr">
					                <div class="form-td">
					                    <label><i>*</i>发放名称：</label>
					                    <div class="div-form">
					                        <input class="form-control input-sm txt_mid"  type="text" id="name" name="name" value="${(redpackageGrant.name) !}"   data-rule-required="true" data-msg-required="红包名称不能为空"         />
					                        （仅限输入12个汉字以内）            
					       	 			</div>
					                </div>
					              </div>
				              	  <div class="form-tr">
					                <div class="form-td">
					                    <label><i>*</i>发放方式：</label>
					                    <input type="radio" name="grantMode" class="grantMode"   value="1" 
					                      <#if (redpackageGrant.grantMode) ??>
					                        <#if redpackageGrant.grantMode == '1'>checked </#if>
					                    	<#else> checked  
					                     </#if> />消息红包推送
										<input type="radio" name="grantMode" class="grantMode"  value="2" 
										<#if (redpackageGrant.grantMode) ??>
										  <#if redpackageGrant.grantMode == '2'>checked </#if>
										</#if> />天降红包推送
													
					                </div>
					             </div>	
					             <div class="form-tr">
									<div class="form-td">
											<label><i>*</i>发放开始时间：</label>
												<div class="div-form"> 
													<input name="startDate" id="startDate" class="form-control txt_mid input-sm" 
												onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:%d',maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${(redpackageGrant.startDate?string('yyyy-MM-dd HH:mm:ss')) !}" data-rule-required="true" data-msg-required="发放开始时间不能为空"  >  									
												</div>
										</div>
					              </div>
					              
					            <div class="form-tr">
										<div class="form-td" id="endclass">
											<label><i>*</i>领取截止时间：</label>
												<div class="div-form"> 
													<input name="endDate" id="endDate" class="endDate form-control txt_mid input-sm"  
												 onfocus="WdatePicker({minDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(redpackageGrant.endDate?string('yyyy-MM-dd HH:mm:ss')) !}"
												data-rule-required="true" data-msg-required="领取截止时间不能为空"  >  									
												</div>
										</div>
					              </div>
					             <div class="form-tr">
					                <div class="form-td">
					                    <label><i>*</i>红包选择：</label>
					                    <div class="div-form">
						                    <input class="form-control input-sm txt_mid" 
							                    type="text" id="packageSelect" name="packageSelect"
												<#if redpackageGrant?? && redpackageGrant.redpackage?? > 
							                     value="${(redpackageGrant.redpackage.number) !}"  
							                     </#if> disabled="disabled"  / > 
						                    <input  class="btn btn-primary btn-sm btn-inquire"
	                     						onClick='showPackage()' id="packageSelectbutton" 
	                     						name="packageSelectbutton"
	                     						 type ="button" value="选" / >                    
					       	 			</div>
					                </div>
					            </div>
					            
				               <div class="form-tr"  >
				                <div class="form-td">
			                        <input type="hidden" id="redpackageId" name="redpackageId" value="${(redpackageGrant.redpackageId) !}"> 
										<div class="div-form">
			                          
						               		<div  style="margin-left: 40px;">
						               		
												<table class="table table-hover table-striped bor2 table-common" >
													<thead  >
														<tr>
													        <th width="30px">红包编号</th>
															<th width="80px">红包名称</th>
															<th width="25px">面值(元)</th>
															<th width="25px">有效期</th>
															<th width="25px">发放数量</th>
															<th width="25px">状态</th>
														</tr>
													</thead>
													<tbody id="tabletdredpackage">
														<#if redpackageGrant?? &&  redpackageGrant.redpackage?? > 
														<tr>
														<td>${(redpackageGrant.redpackage.number) !}</td>
														<td>${(redpackageGrant.redpackage.name) !}</td>
														<td>${(redpackageGrant.redpackage.fackValue) !}</td>
														<td>${(redpackageGrant.redpackage.effectiveStr) !}</td>
														<td>${(redpackageGrant.redpackage.grantNum) !}</td>
														<td><#if (redpackageGrant.redpackage.state) ??>
															<#if redpackageGrant.redpackage.state == '1'>启用</#if>
														   <#if redpackageGrant.redpackage.state == '0'>禁用</#if>
														   </#if></td>
														<tr>
														</#if>
													</tbody>
												</table>
											</div>
					                   </div>
					              </div>
					            </div>
					            
					            
					            
				               <div class="form-tr" id="selectuser" >
				                <div class="form-td">
				                    <label><i></i> <input  class="btn btn-primary btn-sm btn-inquire"
				                     onClick='showMember()' type ="button" value="选择用户标签" / >
				                    </label>
				                    <input class="form-control input-sm txt_mid" 
				                           type="hidden" id="userlableList" name="userlableList" value="${redpackageGrant.userlableList!""}" > 
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
												<tbody id="tabletd">
													<#if redpackageGrant?? &&  redpackageGrant.userLables?? > 
													<#list redpackageGrant.userLables as item >
													<tr>
													<td>${(item.lablenumber) !}</td>
													<td>${(item.lablename) !}</td>
													<td><a href="javascript:;" onClick="getDel(this,'${(item.lableId) !}')">删除</a></td></tr>
													</#list> 
													</#if>
													<tr>
												</tbody>
											</table>
										</div>
									</div>
				                </div>
				            </div> 
				            
					                 <div class="form-tr">
					                <div class="form-td">
					                    <label><i>*</i>状态：</label>
					                    <div class="div-form"> 
											<input type="radio" name="state"  value="1"  <#if (redpackageGrant.state) ??>
											<#if redpackageGrant.state == '1'>value="${(redpackageGrant.state) !}" checked="checked"</#if>
											</#if>>
											启用 
											<input type="radio" name="state"  value="0"  <#if (redpackageGrant.state) ??>
											<#if redpackageGrant.state == '0'>value="${(redpackageGrant.state) !}" checked="checked"</#if>
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
<script type="text/javascript" src="${BasePath !}/asset/js/redpackage/redpackageGrant_form.js?ver=${ver !}"></script>
<script type="text/javascript">
    var start="${redpackageGrant.id !"add"}";
$(function(){
        executeValidateFrom('myform','saveGrantRedpackage');
    }); 
</script>
</body>
</html>