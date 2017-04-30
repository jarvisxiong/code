<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>演示添加页面</title>	
</head>
<body>
	<style>.md-loading{display:none;}</style>
	<#include "../common/share_macro.ftl" encoding="utf-8">

	<div id="myAccount">
	        
         <form id="find-page-orderby-form" action="${BasePath !}/user/tableList.do" method="post" class="ff-form">
         	<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
            <input id="find-page-index" type="hidden" name="pageIndex" value="1" />
			<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
			<input id="hidden-office-id" type="hidden" name="officeId" value="${(officeId) !}" />
            
                
            <div class="form-group">
                <label>姓名：</label>
                <div>
                	<input type="text" id="name" name="name" value="${(user.name) !}">
                </div>                  
            </div>
            
             <div class="form-group">
                 <label>登录名：</label>
                 <div>
                 	<input id="loginName" type="text" placeholder="" name="loginName" value="${(user.loginName) !}">
            	</div>
			</div>
			
             <div class="form-group">
                 <label>用户类型：</label>                                        
                 <div>
                 	<select id="userType" name="userType" class="input-sm" data-hint="--全部--" data-option='<@JSONArray dataDict="sys_user_type"/>'></select>
             	</div>
            </div>
                                     
            <div class="form-group col-span-2">
                <label>是否启用：</label>				                    
                <div>
                    <label class="radio-inline"><input name="loginFlag" type="radio" value="1" <#if (user.loginFlag) ??><#if user.loginFlag == '1'>checked="checked"</#if></#if>/>启用</label>
                    <label class="radio-inline"><input name="loginFlag" type="radio" value="0" <#if (user.loginFlag) ??><#if user.loginFlag == '0'>checked="checked"</#if></#if>/>禁用</label>
                </div>
            </div>

        <button id="find-page-orderby-button" class="ff-btn sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
        <a class="ff-btn sm"  href="javascript:iframeFullPage('${BasePath !}/user/form.do')"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
		<button class="ff-btn sm white btn-clear-keyword" data-target="user_list"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>

 </form>

	
	<!-- SSUI: 定义 DataTable 的 HTML 节点 -->
	<div id="user_list" class="ff_DataTable"></div>
         
</div>






<script>
	// SSUI: 生成各种字典
	var dict_loginFlag = {"1":"启用", "0":"禁用"};
	var dict_userType = <@JSONObject dataDict="sys_user_type"/>;
	
	$(document).ready(function(){
	
		ffzx.ui(['datatable'], function(){
			var dt_user_list = new initDataTable({
				div_id: 'user_list',
				url: rootPath + "/user/tableQueryData.do",
				columns:[
					{ data: "name", label: '姓名', class:'text-nowrap'},
					{ data: "loginName", label: '登录名', class:'text-nowrap'},
					{ data: "workNo", label: '工号', class:'text-nowrap'},
					{ data: "company.name", label: '归属公司', class:'text-nowrap'},
					{ data: "office.name", label: '归属部门'},
					{ data: "email", label: '邮箱', class:'text-nowrap'},
					{ data: "phone", label: '电话', class:'text-nowrap'},
					{ data: "mobile", label: '手机', class:'text-nowrap'},
					{ data: "userType", label: '用户类型', class:'text-nowrap', data_dict: dict_userType },
					{ data: "loginFlag", label: '是否启用', class:'text-nowrap', data_dict: dict_loginFlag }
				],
				gen_permission: function(){
					var map = [];
					
					// SSUI: 生成权限
					<@permission name="pms:user:edit">
						map.push('edit');
						map.push('loginFlag');
					</@permission>
					<@permission name="pms:user:delete">
						map.push('delete');
					</@permission>
					<@permission name="pms:user:assign">
						map.push('role');
						map.push('userGroup');
						map.push('userMenu');
					</@permission>

					return map;
				},
				clm_action: function(item){
					// SSUI: gen_action() 在外链的 user_list.js 中详细定义
					return gen_action(item);
				}
			});
		});
	});

</script>
<script type="text/javascript" src="${BasePath !}/asset/js/pms/user/user_list.js?v=${ver !}"></script>

</body>
</html>