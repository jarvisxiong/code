<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="list"/>
	<title>免费抽奖管理</title>
	<#include "../common/share_macro.ftl" encoding="utf-8">
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="myAccount">
		<div class="col-md-12">
			<div class="search">
				<form id="find-page-orderby-form" action="${BasePath !}/reward/rewardList.do" method="post">
					
					<input id="find-page-count" type="hidden" value="${(pageObj.pageCount) !}" />
					<input id="rewardStatus" name="rewardStatus" type="hidden" class="form-control txt_mid input-sm"  />
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>活动标题：</label> 
								<div class="div-form">
								<input id="title" name="title" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<div class="form-td">
								<label>活动期号：</label> 
								<div class="div-form">
								<input id="dateNo" name="dateNo" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
													
							<div class="form-td">
								<label>活动编号：</label> 
								<div class="div-form">
								<input id="rewardNo" name="rewardNo" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							<div class="form-td">
								<label>开始时间：</label> 
                                    <div class="div-form"><input name="startDateStartStr" id="startDateStartStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'startDateEndStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                    -
                                    <input name="startDateEndStr" id="startDateEndStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                               </div>
                            </div>
							<div class="form-td">
								<label>发布状态：</label> 
								<div class="div-form">
									<select id="sendStaus" name="sendStaus" data-selected="" class="input-sm">
										<option value="">全部</option>
										<option value="1" >已发布</option>
										<option value="0" >未发布</option>
									</select>
								</div>
							</div>
							</div>	
                            <div class="form-tr">
								<div class="form-td">
									<label>结束时间：</label> 
	                                    <div class="div-form"><input name="endDateStartStr" id="endDateStartStr" class="form-control txt_mid input-sm" 
	                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDateEndStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                                    -
	                                    <input name="endDateEndStr" id="endDateEndStr" class="form-control txt_mid input-sm" 
	                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                               </div>
	                            </div>
                                <div class="form-td">
                                    <label>开奖时间：</label> 
                                   <div class="div-form"> <input name="rewardDateStartStr" id="rewardDateStartStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'rewardDateEndStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                                    -
                                    <input name="rewardDateEndStr" id="rewardDateEndStr" class="form-control txt_mid input-sm"
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'rewardDateStartStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">								
							</div></div>
							
							<div class="form-td">
								<label>操作人：</label> 
								<div class="div-form">
								<input id="createName" name="createName" class="form-control txt_mid input-sm" type="text">
								</div>
							</div>
							
						</div>
					</div>
					<div class="btn-div3">
					
						<!-- SSUI: 查询按钮的 type 改为 button -->
						<button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="button"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
						
						<!-- SSUI: 注意添加 class: btn_delete_batch 和 data-target="DataTable的ID" -->
						<button class="btn btn-primary btn-sm btn-clear-keyword" data-target="reward_list"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;清空</button>
						
						<!-- SSUI: 在全幅新窗口打开页面，使用 iframeFullPage('path/to/page') -->
						<a href="javascript:iframeFullPage('${BasePath !}/reward/form.do?viewStatus=save')" class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;新增</a>
						
						<!-- SSUI: 注意添加 class: btn-clear-keyword 和 data-target="DataTable的ID"-->
						<a href=" javascript:iframeFullPage('${BasePath !}/reward/choiceShareOrder.do') " class="btn btn-primary btn-sm" ><i class="fa fa-plus"></i>&nbsp;&nbsp;精选晒单管理</a>
					</div>
				</form>
			</div>
		</div>
		
		
		<div class="navtabs-title">
				<ul class="nav nav-tabs" >
					<li><a href="javascript:findBystatus('',this)">&nbsp;&nbsp;全部</a></li>
					<li><a href="javascript:findBystatus('0',this)" >&nbsp;&nbsp;即将开始</a></li>
					<li><a href="javascript:findBystatus('1',this)" >&nbsp;&nbsp;进行中</a></li>
					<li><a href="javascript:findBystatus('2',this)" >&nbsp;&nbsp;等待开奖/已结束</a>
					<#if count??>
						<span style=" position: absolute;   top:-8px ;  right: 40px;   border-radius: 20px;      border: 1px solid #F00; display: block;   width: 20px;  height: 20px;  color: #f00;   text-align: center;">${(count) !}</span>					
					</#if></li>
					<li><a href="javascript:findBystatus('3',this)" >&nbsp;&nbsp;已开奖</a></li>
				</ul>
		</div>
		<!-- SSUI: 只需定义 DataTable 外围的 DIV ID，注意添加统一的 class: ff_DataTable 便于全局控制 -->
		<div id="reward_list" class="ff_DataTable"></div>
	</div>
</div>
<br />
<script type="text/javascript" src="${BasePath !}/asset/js/reward/reward_list.js?v=${ver !}"></script>
<script>
$(document).ready(function(){	
	requirejs(['ff/init_datatable'], function(initDataTable){		
	dt_demo_list = new initDataTable({
			div_id: 'reward_list',
			url: rootPath + "/reward/rewardListDate.do",
			columns:[
				{ data: "dateNo", label: '抽奖期号',render:function(data,type,item){
					return  '<a href="javascript:iframeFullPage(\''+ rootPath + '/reward/form.do?viewStatus=view&id=' + item.id + '\')">'+data+'</a>'; 
				}},
				{ data: "rewardNo", label: '活动编号'},
				{ data: "title", label: '活动标题' },
				{ data: "sendStaus", label: '发布状态', class:'text-nowrap',render:function(data){
					if(data=="0"){return '未发布';}else if(data=="1"){return '已发布';}
				}},		
				{ data: "rewardStatus", label: '活动状态', class:'text-nowrap',render:function(data){
					if(data=="0"){return '即将开始';}else if(data=="1"){return '进行中';}else if(data=="2"){return '等待开奖中';}else if(data=="3"){return '已开奖';}
				}},		
				{ data: "startDate", label: '开始时间', class:'text-nowrap clm_createDate', format:{datetime:'yyyy-MM-dd HH:mm:ss'}},
				{ data: "endDate", label: '结束时间', class:'text-nowrap clm_endDate', format:{datetime:'yyyy-MM-dd HH:mm:ss'}},
				{ data: "rewardDate", label: '开奖时间', class:'text-nowrap clm_rewardDate', format:{datetime:'yyyy-MM-dd HH:mm:ss'}},
				{ data: "createName", label:'操作人', class: 'clm_createName'},
				{ data: "lastUpdateDate", label: '最近操作时间', class:'text-nowrap', format:{datetime:'yyyy-MM-dd HH:mm:ss'}}
			],
			
			//index_label: 'No.',
			gen_permission: function(){
			
				// SSUI: 生成权限
				var map = [];
				
				<@permission name="prms:reward:send">
					map.push('send');
				</@permission>				
				<@permission name="prms:reward:cancel">
					map.push('cancel');
				</@permission>
				<@permission name="prms:reward:luck">
					map.push('luck');
				</@permission>
				<@permission name="prms:reward:shareOrder">
					map.push('shareOrder');
				</@permission>
				<@permission name="prms:reward:edit">
					map.push('edit');
				</@permission>
				<@permission name="prms:reward:delete">
					map.push('delete');
				</@permission>
				return map;		
			},
			clm_action: function(item){
				// SSUI: 方法 gen_action 的具体逻辑，在外链的 demo_list.js 中定义
				return gen_action(item);
			}
		});
	});
});

</script>
</body>
</html>