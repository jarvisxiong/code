<#include "../global.ftl" encoding="utf-8">

<html class="${sys !} ${mod !}">
<head>
<#include "../common/css.ftl" encoding="utf-8">
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
<style>.md-loading{display:none;}</style>
</head>
<body style="overflow: hidden;"> 
<div class="row">
	<div class="col-md-12">
		<div class="box-body">
		    <div id="myAccount">
		        
		             <div class="search">
		             <form id="find-page-orderby-form" action="${BasePath !}/user/tableList.do" method="post">
		             	<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
		                 <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="hidden-office-id" type="hidden" name="officeId" value="${(officeId) !}" />
		                 <div class="inquire-ul">
		                     <div class="form-tr">
				                <div class="form-td">
				                    <label>姓名：</label>
				                    <div class="div-form"><input class="form-control input-sm txt_mid" type="text" id="name" name="name" value="${(user.name) !}"> </div>                  
				                </div>
		                         <div class="form-td">
		                             <label>登录名：</label>
		                            <div class="div-form"> <input id="loginName" class="form-control txt_mid input-sm" type="text" placeholder="" name="loginName" value="${(user.loginName) !}">
		                        </div> </div>
		                         <div class="form-td">
		                             <label>用户类型：</label>                                        
				                        <select class="form-control input-sm txt_mid input-select" id="userType" name="userType">
				                          <option value="">--全部--</option>
		                                 <#if userTypes ??>
		                                 <#list userTypes as item >                                      
		                                     <option value="${(item.value) !}" <#if (user.userType) ??><#if (user.userType) == (item.value)>selected="selected"</#if></#if>>${(item.label) !}</option>
		                                 </#list>
		                                 </#if>					                        
		                                </select>                    
		                         </div>                                    
				                <div class="form-td">
				                    <label>是否启用：</label>				                    
				                    <div class="div-form wMonospaced">
				                        <label class="radio-inline"><input name="loginFlag" type="radio" value="1" <#if (user.loginFlag) ??><#if user.loginFlag == '1'>checked="checked"</#if></#if>/>启用</label>
				                        <label class="radio-inline"><input name="loginFlag" type="radio" value="0" <#if (user.loginFlag) ??><#if user.loginFlag == '0'>checked="checked"</#if></#if>/>禁用</label>
				                    </div>
				                </div>
		                       
		                     </div>                              
		                 </div>
		                 <div class="btn-div3">
		                    <button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
		                    <a class="btn btn-primary btn-sm"  href="${BasePath !}/user/form.do"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
		                 </div>
		             </form>
		         </div>		        
		
		    	
		         <table class="table table-hover table-striped bor2 table-common">
		             <thead>
		             <tr>
		                 <th width="1px"></th>
		                 <th width="15px"><input type="checkbox"></th>
		                
		                 <th>姓名</th>
		                 <th>登录名</th>
		                 <th>工号</th>
		                 <th>归属公司</th>
		                 <th>归属部门</th>
		                 <th>邮箱</th>
		                 <th>电话</th>
		                 <th>手机</th>
		                 <th>用户类型</th>
		                 <th class="tab-td-center">是否启用</th>
		                 <!-- <th>登陆日期</th> -->
		                <!--    <th>修改时间</th> -->
		                 <!--<th>创建时间</th> -->
		                  <th>操作</th>
		             </tr>
		             </thead>
		             <tbody>
		             <#if userList?? >
		             <#list userList as item >
		             <tr>
		                 <td>${(item_index+1)}</td>
		                 <td><input type="checkbox"></td>
		                 
		                 <td>${(item.name) !}</td>
		                 <td>${(item.loginName) !}</td>
		                 <td>${(item.workNo) !}</td>
		                 <td>${(item.company.name) !}</td>
		                 <td>${(item.office.name) !}</td>
		                 <td>${(item.email) !}</td>
		                 <td>${(item.phone) !}</td>
		                 <td>${(item.mobile) !}</td>
		                 <td>
		                 <#if (item.userType) ??>
		                 <#if userTypes ??>
		                 <#list userTypes as itemType >                                      
		                     <#if (item.userType) == (itemType.value)>${(itemType.label) !}</#if>
		                 </#list>
		                 </#if></#if>
		                 </td>
		                 <td align="center"><#if item.loginFlag == '1'>启用<#else>禁用</#if></td>
		                 <!-- <td><#if (item.loginDate) ??>${item.loginDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td> -->
		                <!-- <td><#if (item.lastUpdateDate) ??>${item.lastUpdateDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td> -->
		                <!-- <td><#if item.createDate ??>${item.createDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td> -->
		                <td>
		                     <@permission name="pms:user:edit">
		                 	<a href="${BasePath !}/user/form.do?id=${(item.id) !}">编辑</a> | 
		                 	<a href="javascript:isEnabled('${(item.id) !}',<#if item.loginFlag == '0'>1<#else>0</#if>) "><#if item.loginFlag == '1'>禁用<#else>启用</#if></a>
		                 	</@permission>
		                 	<@permission name="pms:user:delete">
		                      | <a name="delete_item" href="${BasePath !}/user/delete.do?id=${(item.id) !}">删除</a>
		                     </@permission>
		                     <@permission name="pms:user:assign">
		                 	 | <a href="javascript:showAllrole('${(item.id) !}')">分配角色</a> |   
		                     <a href="javascript:showAlluserGroup('${(item.id) !}')">分配用户组</a>
		                     </@permission>                                                               
		                 </td>
		             </tr>
		             </#list>
		             </#if>
		             </tbody>
		         </table>
		         
		         <#include "../common/page_macro.ftl" encoding="utf-8"> 
					<@my_page pageObj/> 
				</div>
		    </div>               

	</div>
</div>

<#include "../common/js.ftl" encoding="utf-8">
<#include "../common/tree.ftl" encoding="utf-8">
<#include "../common/select.ftl" encoding="utf-8">
<script type="text/javascript" src="${BasePath !}/asset/js/pms/user/user_list.js?v=${ver !}"></script>

<#include "../common/loadingffzx.ftl" encoding="utf-8"> 
<@load_content content="small"/>

</body>
</html>