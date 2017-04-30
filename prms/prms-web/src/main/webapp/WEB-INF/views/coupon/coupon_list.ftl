<html>
<head>
<meta name="decorator" content="list" />
<title>优惠券列表</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="couponList">
			<div class="col-md-12">
				<div class="search">
					<form id="myform" action="${BasePath !}/coupon/list.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>优惠券名称：</label> 
									<div class="div-form"><input id="name" name="name" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(coupon.name) !}">							
									</div>
								</div>
								<div class="form-td">
									<label>优惠券编码：</label> 
									<div class="div-form"><input id="number" name="number" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(coupon.number) !}">							
									</div>
								</div>
								<div class="form-td">
									<label>面值：</label> 
									<div class="div-form"><input id="faceValue" name="faceValue" class="form-control txt_mid input-sm" onBlur="FFZX.blurvalidationInteger(this),FFZX.blurvalidationIntegerContrast('faceValue','maxFaceValue')" type="text" data-rule-isIntGtZero="true"  data-msg-isIntGtZero="面值必须为整数"  value="${(coupon.faceValue) !}">
									-									
									<input id="maxFaceValue" name="maxFaceValue" class="form-control txt_mid input-sm" type="text" onBlur="FFZX.blurvalidationInteger(this),FFZX.blurvalidationIntegerContrast('faceValue','maxFaceValue')" data-rule-isIntGtZero="true"  data-msg-isIntGtZero="面值必须为整数"  value="${(coupon.maxFaceValue) !}" >
									</div>
								
								</div>
<!-- 							<div class="form-td">
									<label>优惠券状态：</label>
									<div class="div-form"> 
									<select id="couponState" name="couponState" class="form-control txt_mid input-sm"> 
										<option value="">--全部--</option>
										<option value="0"  <#if (coupon.couponState ?? )><#if coupon.couponState=='0'>selected="selected"</#if></#if>>未生效</option>
										<option value="1" <#if (coupon.couponState ?? )><#if coupon.couponState=='1'>selected="selected"</#if></#if>>已生效</option>
										<option value="2" <#if (coupon.couponState ?? )><#if coupon.couponState=='2'>selected="selected"</#if></#if>>已过期</option>
									</select>
									</div>
								</div> --> 
								<div class="form-td">
									<label>有效期：</label> 
									<div class="div-form"><input name="effectiveDateStartStr" id="effectiveDateStartStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'effectiveDateEndStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(coupon.effectiveDateStartStr) !}">
                                    -
                                    <input name="effectiveDateEndStr" id="effectiveDateEndStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'effectiveDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(coupon.effectiveDateEndStr) !}">
                               		</div>
								</div>	
								<div class="form-td">
									<label>启用状态：</label>
									<div class="div-form"> 
									<select id="delFlagTemp" name="delFlagTemp" class="form-control txt_mid input-sm"> 
										<option value="">--全部--</option>
										<option value="0"  <#if (coupon.delFlagTemp ?? )><#if coupon.delFlagTemp=='0'>selected="selected"</#if></#if>>未启用</option>
										<option value="1" <#if (coupon.delFlagTemp ?? )><#if coupon.delFlagTemp=='1'>selected="selected"</#if></#if>>已启用</option>
									</select>
									</div>
								</div> 
								<div class="form-td">
									<label>&nbsp;&nbsp;&nbsp;创建时间：</label>
									<div class="div-form"><input name="createDateStart" id="createDateStart" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'createDateEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(coupon.createDateStart) !}">
                                    -
                                    <input name="createDateEnd" id="createDateEnd" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'createDateStart\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(coupon.createDateEnd) !}">
                               		</div>
								</div>
							</div>
						</div>
						
						<div class="btn-div3">
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm"  type="submit" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							<a id="clean" onclick="onEmpty()" class="btn btn-primary btn-sm" ><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a> 
							<a href="${BasePath !}/coupon/toSave.do?view=save" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
						</div>
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="3%">序号</th>
						<th width="3%"><input type="checkbox"></th>
						<th width="8%">优惠券编码</th>
						<th width="15%">优惠券名称</th>
				        <th width="8%">面值(元)</th>						
						<th width="10%">消费限制</th>
						<th width="15%">有效期</th>
						<!-- <th width="8%">优惠券状态</th> -->
						<th width="15%">创建时间</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
					<#if couponList?? > 
					<#list couponList as item >
					<tr id="${(item.id) !}" pId="${(item.pId) !}" ondblclick="view('${(item.id) !}')">
						<td>${item_index + 1}</td>
						<td><input type="checkbox" value="${(item.id) !}"  name="check"></td>
						<td><a href="${BasePath !}/coupon/toDetail.do?id=${(item.id) !}">${(item.number) !}</a></td>					
						<td>${(item.name) !}</td>		
						<td>${(item.faceValue) !}</td>										
						<#if item.consumptionLimit == -1>
							<td>
									无限制
							</td>
						<#else>
							<td>
									满${(item.consumptionLimit) !}元
							</td>
						</#if>		
						<#if item.effectiveDateState== '1'>
							<td>
								${(item.effectiveDateStart?string('yyyy-MM-dd HH:mm:ss')) !}至${(item.effectiveDateEnd?string('yyyy-MM-dd HH:mm:ss')) !}
							</td>
						<#elseif item.effectiveDateState== '0'>
							<td>
									${(item.effectiveDateNum) !}天
							</td>
						</#if>				
					<!-- 	<#if item.effectiveDateState=='1'>
							<#if item.couponState== '0'>
								<td>
										未生效
								</td>
						   <#elseif item.couponState== '1'>
								<td>
										已生效
								</td>
							<#else>		
								<td>
										已过期
								</td>
							</#if>
							<#else>
								<td></td>				
							</#if>			 -->															
						<td>${(item.createDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>
						<#if item.couponState !='2'>							
								<td>
									<@permission name="prms:cupon:status">
										<#if item.delFlag=='0'><a href="javascript:void(0);" onclick="runCoupon('${(item.id) !}')">启用</a></#if>
									</@permission>
									<@permission name="prms:cupon:edit">
										<#if item.delFlag=='0'>
											<a href="${BasePath !}/coupon/toSave.do?id=${(item.id) !}&view=edit">编辑</a>
										<#else>
											编辑
										</#if>
									</@permission>
								</td>
						<#else>
							<td></td>
						</#if>
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
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
	<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_list.js?v=${ver !}"></script>
	<script type="text/javascript"> 
	
	 function onEmpty() {		 
         location.href="${BasePath !}/coupon/list.do";    
     }
	//查看
	function view(id) {
		window.location.href = '${BasePath !}/coupon/toDetail.do?id=' + id;
	}
    
	</script>
</body>
</html>
