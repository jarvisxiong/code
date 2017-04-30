<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ligerUI/js/ligerui.min.js?v=${ver !}"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/common/commonEditUI.js?v=${ver !}"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/validation/jquery.validate.js?v=${ver !}"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/validation/jquery.metadata.js?v=${ver !}"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.config.js?v=${ver !}"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.all.min.js?v=${ver !}"></script>
	<title>演示添加页面</title>
</head>

<body><ul class="nav nav-tabs">
    <li class="active"><a href="#">抢购活动${(activity.id)???string('编辑','新增')}</a></li>
</ul>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
		<div class="row">
			<div class="col-lg-10 col-md-12 col-sm-12">
				<form id="myform" method="post" action="${BasePath !}/panicbuyActivity/dataSave.do">
					<input  type="hidden" name="id" value="${(activity.id) !}" /> 
					<div class="addForm1">
						<div id="error_con" class="tips-form">
							<ul></ul>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label><i>*</i>活动标题：</label>
								<div class="div-form">
									<input id="title" name="title" value="${(activity.title) !}" class="form-control input-sm txt_mid" type="text" data-rule-required="true" data-msg-required="标题不能为空" >
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label><i>*</i>开始时间：</label>
								<div class="div-form">
									<input name="startDateStr" id="startDateStr" class="form-control txt_mid input-sm"  data-rule-required="true" data-msg-required="开始时间不能为空"
									onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(activity.startDate?string("yyyy-MM-dd HH:mm:ss")) !}" />
								</div>
							</div>
							<div class="form-td">
								<label><i>*</i>结束时间：</label>
								<div class="div-form">
									<input name="endDateStr" id="endDateStr" class="form-control txt_mid input-sm"  data-rule-required="true" data-msg-required="结束时间不能为空"
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDateStr\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(activity.endDate?string("yyyy-MM-dd HH:mm:ss")) !}" />
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label>活动描述：</label>
								<div class="div-form">
								<input type="hidden" id="testDiv" name="testDiv" value="${(activity.remarks) !}">			
								 <script type="text/plain" id="remarks" name="remarks"></script>
									<script type="text/javascript">
										var contentEditor = UE.getEditor('remarks', {
											initialFrameWidth : 700,
											initialFrameHeight : 360
										});
										contentEditor.addListener('ready', function() {
											 var value = $("#testDiv").val();
											UE.getEditor('remarks').setContent(value,false); 
										});
									</script>
								</div>
							</div>
						</div>
						<div class="form-tr">
	                                    <div class="form-group btn-div">
								
								<input type="submit" class="btn btn-primary" value="保存">
								
								<input type="button" class="btn btn-default" value="返回" onclick="isReturn();">
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
<script type="text/javascript">
$(function(){
	executeValidateFrom('myform', valid, 'resultData');
});

function valid(){
	// 时间验证
	var startDateStr=$("#startDateStr").val();//活动商品开始时间
	var endDateStr=$("#endDateStr").val();//活动商品结束时间
	if(Date.parse(startDateStr.replace(/-/g, "/")) == Date.parse(endDateStr.replace(/-/g, "/"))){
		$.frontEngineDialog.executeDialogOK('提示信息','新增活动失败，活动的结束时间必须晚于开始时间，请修改后重试！','300px');
		return false;
	}
	if((UE.getEditor('remarks').getContentTxt().length)>300){
		$.frontEngineDialog.executeDialogOK('提示信息','活动描述300字以内！','300px');
		return false;
	}
	
	return true;
}

function resultData(result){
	 if(result && result.status=="success"){
		 $.frontEngineDialog.executeDialogContentTime(result.infoStr,2000);
		 setTimeout("window.location.href=\'" + rootPath + result.url +"'", 1000);
    }else{
   	 	$.frontEngineDialog.executeDialogOK('提示信息', result.infoStr, '300px');
    }    
}


</script>
</body>
</html>