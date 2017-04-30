<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<script type="text/javascript" src="${BasePath !}/asset/js/common/commonEditUI.js"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/validation/jquery.validate.js"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/validation/jquery.metadata.js"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.config.js"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.all.min.js"></script>
	<title>推荐有奖编辑页面</title>
	
	<style type="text/css">
		.fa-question{border: 1px #ccc solid; padding: 0px 4px;color: #867E7E !important;}
		.hiddenSave{display:none;}
		.fa-question{background-color: yellow;}
		.addForm1 label{width:17%}
		.div-form{margin-left: 10px;}
	</style>
	
</head>

<body><ul class="nav nav-tabs">
    <li class="active"><a href="#">推荐有奖管理</a></li>
</ul>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
		<div class="row">
			<div class="col-lg-10 col-md-12 col-sm-12">
				<form id="myform" method="post" action="${BasePath !}/couponGrant/recommendsave.do">
					<input  type="hidden" name="id" value="${(data.id) !}" /> 
					<div class="addForm1" style="margin-top: 20px;">
						<div id="error_con" class="tips-form">
							<ul></ul>
						</div>
						<!-- 
						<div class="form-tr">
							<div class="form-td col-sm-12" >
								<label style="padding-top: 9px;">APP推荐有奖状态：<i name="appState" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input id="recommendStatus" name="recommendStatus" value=""  type="radio" disabled="disabled">启用&nbsp;&nbsp;&nbsp;
									<input id="recommendStatus" name="recommendStatus" value=""  type="radio" disabled="disabled">禁用
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>APP推荐有奖提示语：<i name="appTitle" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input name="recommendTitle" id="recommendTitle" value="" class="form-control txt_big input-sm" disabled="disabled" type="text"/>
								</div>
							</div>
						</div>
						 -->
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>引导分享页面X取值：<i name="pXValue" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input name="pageXValue" id="pageXValue" value="${(data.pageXValue) !}" class="form-control txt_big input-sm" type="text"/>
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>引导分享页面规则：<i name="pRule" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input type="hidden" id="testDiv" name="testDiv" value="${(data.pageRule) !}">									
								    <script type="text/plain" id="pageRule" name="pageRule">${(data.pageRule) !}</script>
									<script type="text/javascript">
										var contentEditor = UE.getEditor('pageRule', {
											initialFrameWidth : 732,
											initialFrameHeight : 360
										});
										contentEditor.addListener('ready', function() {
											 var value = $("#testDiv").val();
											UE.getEditor('pageRule').setContent(value,false); 
											UE.getEditor('pageRule').execCommand( 'imagefloat', 'left' );
										});
									</script>
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>引导分享页面Y取值：<i name="pYValue" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input name="pageYValue" id="pageYValue" value="${(data.pageYValue) !}" class="form-control txt_big input-sm" type="text"/>
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>分享过程标题：<i name="sTitle" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input id="shareTitle" name="shareTitle" value="${(data.shareTitle) !}" class="form-control input-sm txt_big" type="text">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>分享过程描述：<i name="sDescription" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input id="shareDescription" name="shareDescription" value="${(data.shareDescription) !}" class="form-control input-sm txt_big" type="text">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>分享后页面标题：<i name="sfTitle" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input id="shareafterTitle" name="shareafterTitle" value="${(data.shareafterTitle) !}" class="form-control input-sm txt_big" type="text">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>分享后页面X取值：<i name="sfXValue" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input id="shareafterXValue" name="shareafterXValue" value="${(data.shareafterXValue) !}" class="form-control input-sm txt_big" type="text">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>分享后页面规则：<i name="sfRule" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input type="hidden" id="safterRule" name="safterRule" value="${(data.shareafterRule) !}">									
								    <script type="text/plain" id="shareafterRule" name="shareafterRule">${(data.shareafterRule) !}</script>
									<script type="text/javascript">
										var contentEditor = UE.getEditor('shareafterRule', {
											initialFrameWidth : 732,
											initialFrameHeight : 360
										});
										contentEditor.addListener('ready', function() {
											 var value = $("#safterRule").val();
											UE.getEditor('shareafterRule').setContent(value,false); 
											UE.getEditor('shareafterRule').execCommand( 'imagefloat', 'left' );
										});
									</script>
								</div>    
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>新用户注册红包发放编码：<i name="nuGrantcode" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input id="newuserGrantcode" name="newuserGrantcode" value="${(data.newuserGrantcode) !}" class="form-control input-sm txt_big" type="text">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td col-sm-12">
								<label>老用户反推荐红包发放编码：<i name="ouGrantcode" class="fa fa-question partnerfc"></i></label>
								<div class="div-form">
									<input id="olduserGrantcode" name="olduserGrantcode" value="${(data.olduserGrantcode) !}" class="form-control input-sm txt_big" type="text">
								</div>
							</div>
						</div>
						<div class="form-tr">
	                        <div class="form-group btn-div">
								<input type="submit"  class="btn btn-primary" value="保存">
								<input type="button" id="back" class="btn btn-default btn-close-iframeFullPage hide" value="返回" >
								<a href="javascript:void(0);" class="btn btn-default" onclick="backPage();">返回</a>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--新增1--end-->

	</div>
</div>
<script type="text/javascript" src="${BasePath !}/asset/js/coupon/recommendawards_form.js"></script>
<script type="text/javascript">
	$(function(){
		executeValidateFrom('myform',validate);
	});
</script>
</body>
</html>