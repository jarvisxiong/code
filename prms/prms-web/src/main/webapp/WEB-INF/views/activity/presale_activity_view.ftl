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
	<title>预售</title>
</head>
<body><ul class="nav nav-tabs">
    <li class="active"><a href="#">预售活动${(activity.id)???string('编辑','新增')}</a></li>
</ul>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
		<div class="row">
			<div class="col-lg-10 col-md-12 col-sm-12">
				<form id="myform" method="post" action="${BasePath !}/presellActivity/save.do">
					<input  type="hidden" name="id" value="${(activity.id) !}" /> 
					<div class="addForm1">
						<div id="error_con" class="tips-form">
							<ul></ul>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label><i>*</i>活动标题：</label>
								<div class="div-form">
									<input id="title" name="title" disabled="disabled" value="${(activity.title) !}" class="form-control input-sm txt_mid" type="text" >
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label><i>*</i>开始时间：</label>
								<div class="div-form">
									<input name="startDateStr" disabled="disabled" id="startDateStr" class="form-control txt_mid input-sm" 
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(activity.startDate?string("yyyy-MM-dd HH:mm:ss")) !}" />
								</div>
							</div>
							<div class="form-td">
								<label><i>*</i>结束时间：</label>
								<div class="div-form">
									<input name="endDateStr" disabled="disabled" id="endDateStr" class="form-control txt_mid input-sm" 
                                    onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(activity.endDate?string("yyyy-MM-dd HH:mm:ss")) !}" />
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label>活动描述：</label>
								<div class="div-form">
									<input type="hidden" id="testDiv" name="testDiv" value="${(activity.remarks) !}">
									 <script type="text/plain" id="remarks" name="remarks">${(activity.remarks) !}</script>
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
								<input type="button" class="btn btn-default" value="返回" onclick="history.go(-1);">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--新增1--end-->

	</div>
</div>
<script type="text/javascript">
	$(function(){
		executeValidateFrom('myform');
	});
</script>
</body>
</html>