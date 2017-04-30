<html>
<head>
<meta name="decorator" content="list" />
<title>发放优惠券列表</title>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="memberList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/couponGrant/list.do" method="post">
						<input id="find-page-index" type="hidden" name="pageIndex" value="1" /> 
						<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
						<input id="find-page-size" type="hidden" name="pageSize" value="${(pageObj.pageSize) !}" />
						<div class="inquire-ul">
							<div class="form-tr">
								<div class="form-td">
									<label>发放名称：</label> 
									<div class="div-form"><input id="name" name="name" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(couponGrant.name) !}">							
									</div>
								</div>
								<div class="form-td">
									<label>发放时间：</label> 
									<div class="div-form"><input name="beginGrantDateStr" id="beginGrantDateStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endGrantDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                        value="${(couponGrant.beginGrantDateStr) !}">
                                    -
                                    <input name="endGrantDateStr" id="endGrantDateStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginGrantDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                        value="${(couponGrant.endGrantDateStr) !}">
                               		</div>
								</div>
								<div class="form-td">
									<label>发放编码：</label> 
									<div class="div-form"><input id="number" name="number" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(couponGrant.number) !}">							
									</div>
								</div>
								<div class="form-td">
									<label>发放方式：</label>
									<div class="div-form"> 
									<select id="grantMode" name="grantMode" class="form-control txt_mid input-sm"> 
										<option value="">--全部--</option>
										<option value="0" <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '0'> selected="selected" </#if></#if> >系统推送</option>
										<!--<option value="2" <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '2'> selected="selected" </#if></#if> >指定用户</option>-->
										<option value="1" <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '1'> selected="selected" </#if></#if> >用户领取</option> 
										<option value="4" <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '4'> selected="selected" </#if></#if> >消息优惠券推送</option> 
										<option value="5" <#if (couponGrant.grantMode) ??><#if couponGrant.grantMode == '5'> selected="selected" </#if></#if> >天降优惠券推送</option> 
									</select>
									</div>
								</div>
								<div class="form-td">
									<label>发放状态：</label>
									<div class="div-form"> 
									<select id="isGrant" name="isGrant" class="form-control txt_mid input-sm"> 
										<option value="">--全部--</option>
										<option value="0"  <#if (couponGrant.isGrant) ??><#if couponGrant.isGrant == '0'> selected="selected" </#if></#if> >未开始</option>
										<!-- <option value="1"  <#if (couponGrant.receiveState) ??><#if couponGrant.receiveState == '1'> selected="selected" </#if></#if> >领取中</option> -->
										<!--<option value="2"  <#if (couponGrant.receiveState) ??><#if couponGrant.receiveState == '2'> selected="selected" </#if></#if> >已领完</option> -->
										<option value="1"  <#if (couponGrant.isGrant) ??><#if couponGrant.isGrant == '1'> selected="selected" </#if></#if> >进行中</option><!--进行中只有一个-->
										<option value="2"  <#if (couponGrant.isGrant) ??><#if couponGrant.isGrant == '2'> selected="selected" </#if></#if> >已结束</option><!--已结束有多个，跟列表显示不同-->
									</select>
									</div>
								</div>	
								
								<div class="form-td">
									<label>发放类型：</label>
									<div class="div-form"> 
									<select id="grantType" name="grantType" class="form-control txt_mid input-sm"> 
										<option value="">--全部--</option>
										<option value="0"  <#if (couponGrant.grantType) ??><#if couponGrant.grantType == '0'> selected="selected" </#if></#if>  >优惠券</option>
										<option value="3"  <#if (couponGrant.grantType) ??><#if couponGrant.grantType == '3'> selected="selected" </#if></#if>  >优惠码</option>
										<!--<option value="1"  <#if (couponGrant.grantType) ??><#if couponGrant.grantType == '1'> selected="selected" </#if></#if>  >注册</option>
										<option value="2"  <#if (couponGrant.grantType) ??><#if couponGrant.grantType == '2'> selected="selected" </#if></#if>  >分享</option>
									
								--></select>
									</div>
								</div>
								
								<div class="form-td">
									<label>用户类型：</label>
									<div class="div-form"> 
									<select id="userType" name="userType" class="form-control txt_mid input-sm"> 
										<option value="">--全部--</option>
										<option value="0"  <#if (couponGrant.userType) ??><#if couponGrant.userType == '0'> selected="selected" </#if></#if>  >所有用户</option>
										<option value="1"  <#if (couponGrant.userType) ??><#if couponGrant.userType == '1'> selected="selected" </#if></#if>  >指定用户</option>
										<option value="2"  <#if (couponGrant.userType) ??><#if couponGrant.userType == '2'> selected="selected" </#if></#if>  >新用户</option>
										<option value="2"  <#if (couponGrant.userType) ??><#if couponGrant.userType == '3'> selected="selected" </#if></#if>  >指定用户标签</option>
										<!--<option value="1"  <#if (couponGrant.grantType) ??><#if couponGrant.grantType == '1'> selected="selected" </#if></#if>  >注册</option>
										<option value="2"  <#if (couponGrant.grantType) ??><#if couponGrant.grantType == '2'> selected="selected" </#if></#if>  >分享</option>
									
								--></select>
									</div>
								</div>
								
							</div>
						</div>
						
						<div class="btn-div3">
							<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
							<button id="clean" onclick="onEmpty()" class="btn btn-primary btn-sm" type="button"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</button> 
						<@permission name="prms:grant:add">	
						<a href="${BasePath !}/couponGrant/form.do?viewstatus=save" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;添加</a>
						</@permission>
						<@permission name="prms:grant:add">	
							<a href="${BasePath !}/couponGrant/codeform.do?viewstatus=save" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>&nbsp;&nbsp;优惠码</a>
						</@permission>
						<@permission name="prms:grant:add">
							<a href="javascript:iframeFullPage('${BasePath !}/couponGrant/recommendform.do')" class="btn btn-primary btn-sm">&nbsp;&nbsp;推荐有奖管理</a>
						</@permission>
						</div>
					</form>
				</div>
			</div>
			<table class="table table-hover table-striped bor2 table-common">
				<thead>
					<tr>
						<th width="1%"></th>
						<th width="4%"><input type="checkbox"></th>
						<th width="8%">编码</th>
				        <th width="8%">发放名称</th>
						<th width="10%">发放时间</th>
						<th width="8%">发放方式</th>
						<th width="8%">发放类型</th>
						<th width="8%">发放状态</th>
						<th width="8%">用户类型</th>
						<!-- <th width="8%">每人限领</th>-->
						<!-- <th width="5%">剩余量</th>
						<th width="5%">领取量</th> -->
						<th width="10%">创建时间</th>
						<th width="16%">操作</th>
					</tr>
				</thead>
				<tbody>
					<#if couponGrantList?? > 
					<#list couponGrantList as item >
					<tr id="${(item.id) !}" pId="${(item.pId) !}" ondblclick="view('${(item.id) !}')">
						<td>${item_index + 1}</td>
						<td><input type="checkbox" value="${(item.id) !}"   name="check"></td>
						<td><a href="${BasePath !}/couponGrant/viewDetail.do?id=${(item.id) !}">${(item.number) !}</a></td>
						<td>${(item.name) !}</td>
						<td>${(item.grantDate?string('yyyy-MM-dd HH:mm:ss')) !}
						<#if (item.grantEndDate) ??>
						至${(item.grantEndDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>
						 </#if>
						<td>
							 <#if (item.grantMode) ??><#if item.grantMode == '0'>  系统推送  </#if></#if> 
							 <#if (item.grantMode) ??><#if item.grantMode == '1'>  用户领取  </#if></#if> 
							 <#if (item.grantMode) ??><#if item.grantMode == '4'>  消息优惠券推送</#if></#if> 
							 <#if (item.grantMode) ??><#if item.grantMode == '5'>  天降优惠券推送  </#if></#if> 
							<!-- <#if (item.grantMode) ??><#if item.grantMode == '2'>  指定用户  </#if></#if> -->
						</td>
						<td>
						
							 <#if (item.grantType) ??><#if item.grantType == '0'> 优惠券 </#if></#if> 
							 <#if (item.grantType) ??><#if item.grantType == '1'> 注册  </#if></#if> 
							 <#if (item.grantType) ??><#if item.grantType == '2'> 分享  </#if></#if> 
							 <#if (item.grantType) ??><#if item.grantType == '3'> 优惠码  </#if></#if> 
						</td>	
						<td>
							 <#if (item.isGrant) ??><#if item.isGrant == '0'>  未开始  </#if></#if> 
							 <#if (item.isGrant) ??><#if item.isGrant == '1'>  已结束  </#if></#if> 
							 <#if (item.isGrant) ??><#if item.isGrant == '2'>  进行中 </#if></#if> 
						</td>
						
						<td>
						
							 <#if (item.userType) ??><#if item.userType == '0'> 所有用户</#if></#if> 
							 <#if (item.userType) ??><#if item.userType == '1'> 指定用户 </#if></#if> 
							 <#if (item.userType) ??><#if item.userType == '2'> 新用户 </#if></#if> 
							 <#if (item.userType) ??><#if item.userType == '3'> 指定用户标签 </#if></#if> 
						</td>	
						<!--						
						<td>
						<#if (item.receiveLimit) ??><#if item.receiveLimit == -1>  无限制
						<#else>
						${(item.receiveLimit) !}
						</#if>
						</#if> 
						</td>
						-->			
						<!-- <td>${(item.surplusNum )!}</td>
						<td>${(item.receiveNum )!}</td> -->
						<td>${(item.createDate?string('yyyy-MM-dd HH:mm:ss')) !}</td>
						<div id="a" style="width:200px;"></div>
						<td>
							<#if (item.receiveState) ??><#if item.receiveState == '3' || item.receiveState = '2'>
								<i>已经结束</i>
							<#else>
								<#if (item.url) ??><#if item.url == '1'>
									<@permission name="">
									<a href="javascript:couponLink('${(item.id) !}','link${item_index + 1}')" id="link${item_index + 1}">链接</a> | 
									</@permission>
								</#if>
								</#if>
								<#if (item.grantType) ??><#if item.grantType == '3'> <!--看看新增会不会生成优惠码 -->
								<@permission name="prms:grant:vcode">
								<a href="javascript:void(0);" class="f7" onclick="vcodeKu('${(item.id) !}');">码库 </a>
								</@permission>
								
								<@permission name="prms:grant:vcode">
									<@permission name="prms:grant:edit"> |
									</@permission>
								</@permission>
								</#if>
								</#if>
								<#if item.isGrant == '0'>
									<@permission name="prms:grant:edit">
									<#if (item.grantType) ??>
									<#if item.grantType == '3'> 
									<a href="${BasePath !}/couponGrant/codeform.do?id=${(item.id) !}&viewstatus=edit"">编辑</a>
									<#else>
									<a href="${BasePath !}/couponGrant/form.do?id=${(item.id) !}&viewstatus=edit"">编辑</a>
									
									</#if>
									</#if>
									 | 
									</@permission>
									<@permission name="prms:grant:delete">
									<a href="javascript:isdelete('${(item.id) !}') ">删除</a> 
									</@permission>
								</#if>
								<#if item.isGrant == '1' || item.isGrant == '2'>
									<@permission name="pms:role:edit">
									<i>编辑</i> 
									</@permission>
									<@permission name="prms:grant:stop">
									<!-- <a name="stop_item" href="${BasePath !}/couponGrant/stop.do?id=${(item.id) !}">暂停领取</a> -->
									</@permission>
								</#if>								
							</#if>
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
	<link rel="stylesheet" href="${BasePath}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
	<script type="text/javascript" src="${BasePath !}/asset/js/coupon/coupon_grant_list.js?v=${ver !}"></script>
	<script type="text/javascript">
	 function onEmpty() {
         location.href="${BasePath !}/couponGrant/list.do";            
     }
	//查看
	function view(id) {
		window.location.href = '${BasePath !}/couponGrant/viewDetail.do?id=' + id;
	}
	</script>
</body>
</html>
