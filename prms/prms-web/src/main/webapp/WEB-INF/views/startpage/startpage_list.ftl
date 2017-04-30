<html>
<head>
<meta name="decorator" content="list" />
<title>APP启动页列表</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="couponList">
			<div class="col-md-12">
				<div class="search">
					<form id="myform" action="${BasePath !}/startpage/datalist.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>终端：</label> 
									<div class="div-form">
										<select id="terminal" name="terminal" class="form-control txt_mid input-sm"> 
											<option value="">--不限--</option>
											<option value="Android"  <#if (appStartPage.terminal ?? )><#if appStartPage.terminal=='Android'>selected="selected"</#if></#if>>安卓</option>
											<option value="IOS" <#if (appStartPage.terminal ?? )><#if appStartPage.terminal=='IOS'>selected="selected"</#if></#if>>IOS</option>
										</select>							
									</div>
								</div>
								<div class="form-td">
									<label>状态：</label>
									<div class="div-form"> 
									<select id="status" name="status" class="form-control txt_mid input-sm"> 
										<option value="">--不限--</option>
										<option value="0" <#if (appStartPage.status ?? )><#if appStartPage.status=='0'>selected="selected"</#if></#if>>未生效</option>
										<option value="1" <#if (appStartPage.status ?? )><#if appStartPage.status=='1'>selected="selected"</#if></#if>>已生效</option>
										<option value="2" <#if (appStartPage.status ?? )><#if appStartPage.status=='2'>selected="selected"</#if></#if>>已过期</option>
									</select>
									</div>
								</div>
								<div class="form-td">
									<label>生效时间：</label> 
									<div class="div-form"><input name="effectiveStartDate" id="effectiveStartDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'effectiveEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(appStartPage.effectiveStartDate) !}">
                                    -
                                    <input name="effectiveEndDate" id="effectiveEndDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'effectiveStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(appStartPage.effectiveEndDate) !}">
                               		</div>
								</div>
								<div class="form-td">
									<label>过期时间：</label> 
									<div class="div-form"><input name="expiredStartDate" id="expiredStartDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'expiredEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(appStartPage.expiredStartDate) !}">
                                    -
                                    <input name="expiredEndDate" id="expiredEndDate" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'expiredStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(appStartPage.expiredEndDate) !}">
                               		</div>
								</div>	
							</div>
						</div>
						
						<div class="btn-div3">
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm"  type="submit" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							<button id="table-cleanBtn" onclick="cleanParams()" class="btn btn-primary btn-sm" ><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button> 
							<a href="${BasePath !}/startpage/dataform.do" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a>
						</div>
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th class="tab-td-center">序号</th>
						<th class="tab-td-center">终端</th>
						<th class="tab-td-center">广告类型</th>
				        <th class="tab-td-center">生效时间</th>						
						<th class="tab-td-center">过期时间</th>
						<th class="tab-td-center">状态</th>
						<th class="tab-td-center">操作人</th>
						<th class="tab-td-center">最近操作时间</th>
						<th class="tab-td-center">操作</th>
					</tr>
				</thead>
				<tbody>
					<#if startPageList?? > 
					<#list startPageList as item >
					<tr>
						<td align="center">${item_index + 1}</td>
						<td align="center"><#if (item.terminal ?? )><#if item.terminal=='Android'>安卓<#else>IOS</#if></#if></td>
						<td align="center">${(item.advertTypeStr) !}</td>					
						<td align="center">${(item.effectiveDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>		
						<td align="center">${(item.expiredDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>										
						<td align="center">
						<#if (item.status ??)>
						   <#if item.status== '0'>
							未生效
						   <#elseif item.status== '1'>
							已生效
						   <#else>		
							已过期
						   </#if>				
						</#if>			
						</td>
						<td align="center">${item.operator !}</td>
						<td align="center">${(item.lastUpdateDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>
						<td align="center">
						<@permission name="prms:appStartPage:edit">
						<a href="${BasePath !}/startpage/dataform.do?id=${(item.id) !}">编辑</a>
						</@permission>
						<@permission name="prms:appStartPage:delete">
						| <a href="javascript:void(0);" onclick="deleteItem('${(item.id) !}');">删除</a>
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
	<#include "../common/tree.ftl" encoding="utf-8">
	<#include "../common/select.ftl" encoding="utf-8">
	<script type="text/javascript" src="${BasePath !}/asset/js/startpage/startpage_list.js"></script>
	
</body>
</html>
