<html>
<head>
<meta name="decorator" content="list" />
<title>查看领取记录</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/couponReceive/list.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="size" type="hidden" name="size" value="${size !}" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>发放名称：</label> 
									<div class="div-form"><input id="couponGrant.name" name="couponGrant.name" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(couponReceive.couponGrant.name) !}"></div>
								</div>
								<div class="form-td">
									<label>发放编码：</label> 
									<div class="div-form"><input id="couponGrant.number" name="couponGrant.number" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(couponReceive.couponGrant.number) !}"></div>
								</div>
								<div class="form-td">
									<label>优惠券名称：</label> 
									<div class="div-form"><input id="couponAdmin.name" name="couponAdmin.name" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(couponReceive.couponAdmin.name) !}"></div>
								
								</div>
								<div class="form-td">
									<label>优惠券编码：</label> 
									<div class="div-form"><input id="couponAdmin.number" name="couponAdmin.number" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(couponReceive.couponAdmin.number) !}"></div>
								
								</div>
								<div class="form-td">
									<label>用户昵称：</label> 
									<div class="div-form"><input id="nickName" name="nickName" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(couponReceive.nickName) !}"></div>
								
								</div>
								<div class="form-td">
									<label>联系电话：</label> 
									<div class="div-form"><input id="phone" name="phone" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(couponReceive.phone) !}"></div>
								</div>
								<div class="form-td">
									<label>领取时间：</label> 
									<div class="div-form"><input name="beginReceiveDateStr" id="beginReceiveDateStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endReceiveDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                        value="${(couponReceive.beginReceiveDateStr) !}">
                                    -
                                    <input name="endReceiveDateStr" id="endReceiveDateStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginReceiveDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                        value="${(couponReceive.endReceiveDateStr) !}">
                               		</div>
								</div>
								<div class="form-td">
									<label>使用状态：</label>
									<div class="div-form"> 
									<select id="useState" name="useState" class="form-control txt_mid input-sm"> 
										<option value="">--全部--</option>
										<option value="1" <#if (couponReceive.useState) ??><#if couponReceive.useState == '1'>selected="selected"</#if></#if> >已使用</option>
										<option value="0" <#if (couponReceive.useState) ??><#if couponReceive.useState == '0'>selected="selected"</#if></#if> >未使用</option>
									</select>
									</div>
								</div>
								<div class="form-td">
									<label>领取方式：</label>
									<div class="div-form"> 
									<select id="couponGrant.grantMode" name="couponGrant.grantMode" class="form-control txt_mid input-sm"> 
										<option value="">--全部--</option>
										<option value="0" <#if (couponReceive.couponGrant.grantMode) ??><#if couponReceive.couponGrant.grantMode == '0'>selected="selected"</#if></#if> >系统推送</option>
										<!-- <option value="2" <#if (couponReceive.couponGrant.grantMode) ??><#if couponReceive.couponGrant.grantMode == '2'>selected="selected"</#if></#if> >指定用户</option> -->
										<option value="1" <#if (couponReceive.couponGrant.grantMode) ??><#if couponReceive.couponGrant.grantMode == '1'>selected="selected"</#if></#if> >用户领取</option> 
										<option value="4" <#if (couponReceive.couponGrant.grantMode) ??><#if couponReceive.couponGrant.grantMode == '4'>selected="selected"</#if></#if> >消息优惠券推送</option> 
										<option value="5" <#if (couponReceive.couponGrant.grantMode) ??><#if couponReceive.couponGrant.grantMode == '5'>selected="selected"</#if></#if> >天降优惠券推送</option> 
									</select>
									</div>
								</div>
									<div class="form-td">
									<label>优惠券类型：</label>
									<div class="div-form"> 
									<select id="couponGrant.grantType" name="couponGrant.grantType" class="form-control txt_mid input-sm"> 
										<option value="">--全部--</option>
										<option value="0" <#if (couponReceive.couponGrant.grantType) ??><#if couponReceive.couponGrant.grantType == '0'>selected="selected"</#if></#if> >优惠券</option>
										<option value="3" <#if (couponReceive.couponGrant.grantType) ??><#if couponReceive.couponGrant.grantType == '3'>selected="selected"</#if></#if> >优惠码</option> 
									</select>
									</div>
								</div>
							</div>
						</div>
						
						<div class="btn-div3">
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							<button id="clean" onclick="onEmpty()" class="btn btn-primary btn-sm" type="button"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button> 
							<a id="" onclick="memberPrinter()" class="btn btn-primary btn-sm btn-inquire" ><i class="fa fa-search" ></i>&nbsp;&nbsp;批量导出</a>
						</div>
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="2%">序号</th>
						<th width="1%"><input type="checkbox"></th>
				        <th width="6%">发放名称</th>
				        <th width="8%">发放编码</th>
						<th width="8%">优惠券名称</th>
						<th width="4%">面值</th>
						<th width="8%">优惠券编码</th>
						<th width="8%">用户昵称</th>
						<th width="6%">联系电话</th>
						<th width="15%">有效期</th>
						<th width="8%">领取时间</th>
						<th width="5%">使用状态</th>
						<th width="8%">使用时间</th>
						<th width="5%">领取方式</th>
					</tr>
				</thead>
				<tbody>
					<#if couponReceiveList?? > 
					<#list couponReceiveList as item >
					<tr>
						<td>${item_index + 1}</td>
						<td><input type="checkbox" value="${(item.id) !}"  phone="${(item.phone) !}" name="check"></td>
						<#if item.couponGrant ?exists>
							<td>${(item.couponGrant.name) !}</td>
							<td><a href="${BasePath !}/couponGrant/viewDetail.do?id=${(item.couponGrant.id) !}">${(item.couponGrant.number) !}</a></td>
						<#else>
							<td></td>
							<td></td>
						</#if>
						<td>${(item.couponAdmin.name) !}</td>
						<td>${(item.couponAdmin.faceValue) !}</td>
						<td><a href="${BasePath !}/coupon/toDetail.do?id=${(item.couponAdmin.id) !}">${(item.couponAdmin.number) !}</a></td>
						<td>${(item.nickName) !}</td>
						<td>${(item.phone) !}</td>
						<#if (item.couponAdmin.effectiveDateState) ??><#if item.couponAdmin.effectiveDateState =='0'>
							<td>${(item.couponAdmin.effectiveDateNum) !}天</td>
						<#else>
							<td>${(item.couponAdmin.effectiveDateStart?string('yyyy-MM-dd HH:mm:ss')) !}到${(item.couponAdmin.effectiveDateEnd?string('yyyy-MM-dd HH:mm:ss')) !}</td>			
						</#if>
						</#if>
						<td>${(item.receiveDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>
						<td><#if (item.useState) ??><#if item.useState == '0'>未使用
						<#else>
							<#if item.useState == '1'>
								已使用
							</#if>
						</#if>
						</#if></td>
						<td>${(item.useDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>
						<td>
						<#if item.couponGrant ?exists>
							<#if (item.couponGrant.grantMode) ??>
								<#if item.couponGrant.grantMode == '1'>用户领取
								
								</#if>
								<#if item.couponGrant.grantMode == '0'>
									系统推送
								</#if>
								<#if item.couponGrant.grantMode == '2'>
									指定用户
								</#if>	
								<#if item.couponGrant.grantMode == '4'>
									消息优惠券推送
								</#if>	
								<#if item.couponGrant.grantMode == '5'>
									天降优惠券推送
								</#if>	
								<#else>
								系统推送
							</#if>
						<#else>
							新用户专享
						</#if>
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
	<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_receive_list.js?v=${ver !}"></script>
	<script type="text/javascript">
	 function onEmpty() {
         location.href="${BasePath !}/couponReceive/list.do";            
     }
	</script>
</body>
</html>
