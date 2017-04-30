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
    <li class="active"><a href="#">普通活动查看</a></li>
</ul>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
		<div class="row">
			<div class="col-lg-10 col-md-12 col-sm-12">
				<form id="myform" method="post" action="${BasePath !}/ordinaryActivity/dataSave.do">
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
									value="${(activity.startDate?string("yyyy-MM-dd HH:mm:ss")) !}" />
								</div>
							</div>
							<div class="form-td">
								<label><i>*</i>结束时间：</label>
								<div class="div-form">
									<input name="endDateStr" disabled="disabled" id="endDateStr" class="form-control txt_mid input-sm" 
                                    value="${(activity.endDate?string("yyyy-MM-dd HH:mm:ss")) !}" />
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label><i>*</i>活动置顶序号：</label>
								<div class="div-form">
									<input id="topSore" name="topSore" disabled="disabled" value="<#if (activity.topSore) ??>${(activity.topSore) !}<#else>0</#if>" class="form-control input-sm txt_mid" type="text";">
								</div>
							</div>
							<div class="form-td">
								<label style="width: 226px;">app活动主页不显示倒计时：</label>
								<div class="div-form">
								<input type="checkbox" disabled="disabled" <#if (activity.showCountDown) ??><#if activity.showCountDown == '1'>checked="checked"</#if></#if> />
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
														contentEditor.execCommand( 'imagefloat', 'left' );
													});
												</script>
								</div>
							</div>
						</div>
						<div  class="memberHead">
				        	<div class="form-tr">
				        		<div class="form-td">
				        		<label><i>*</i>活动主页图片：</label>
				                    <div class="div-form">	
			                             <img id="photoPath_img" style="width:100px; height:100px;" <#if (activity.picPath)?? >src="${(image_path)!}/${activity.picPath?replace('size','origin')}"</#if>   onerror="this.src='${BasePath !}/asset/img/noPic.jpg'" />
                                    </div>
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
<script type="text/javascript" src="${BasePath !}/asset/js/activity/ordinary/ordinary_activity_form.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath}/asset/js/control/SWFUpload/swfupload.js?v=${ver !}"></script>
<script type="text/javascript" src="${BasePath}/asset/js/common/uploadImage.js?v=${ver !}"></script>
<script type="text/javascript">
var image_path = "${image_path}";
	$(function(){
		executeValidateFrom('myform',validate);
		// 上传图片
		initUploadImage(showImg,"activityImage","photoPathBtn",100);	
	});
</script>
</body>
</html>