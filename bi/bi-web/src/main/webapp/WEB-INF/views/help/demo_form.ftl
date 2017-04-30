<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>演示添加页面</title>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.config.js?v=${ver !}"></script>
	<script type="text/javascript" src="${BasePath !}/asset/js/control/ueditorjsp/ueditor.all.js?v=${ver !}"></script>
	
</head>
<body><ul class="nav nav-tabs">
    <li class="active"><a href="#">演示${(testPage.id)???string('编辑','新增')}</a></li>
</ul>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
		<div class="row">
			<div class="col-lg-10 col-md-12 col-sm-12">
				<form id="myform" method="post" action="${BasePath !}/help/test/save.do">
					<input  type="hidden" name="id" value="${(testPage.id) !}" /> 
					<div class="addForm1">
						<div id="error_con" class="tips-form">
							<ul></ul>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label><i>*</i>用户名：</label>
								<div class="div-form">
									<input id="name" name="name" value="${(testPage.name) !}" class="form-control input-sm txt_mid" type="text" data-rule-required="true" data-rule-rangelength="[5,32]" data-msg-required="用户名不能为空" data-msg-rangelength="用户名长度必须介于 5 和 32之间的字符串">
								</div>
							</div>
							<div class="form-td">
								<label><i>*</i>年龄：</label>
								<div class="div-form">
									<input id="age" name="age" value="${(testPage.age) !}" class="form-control input-sm txt_mid" type="text" data-rule-required="true" data-rule-range="[0,100]" data-msg-required="年龄不能为空" data-msg-range="年龄必须介于 0 和 100 之间">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label>邮箱：</label>
								<div class="div-form">
									<input id="email" name="email" value="${(testPage.email) !}" class="form-control input-sm txt_mid" type="text" data-rule-email="true" data-msg-email="必须输入正确格式的电子邮件">
								</div>
							</div>
							<div class="form-td">
								<label>备注：</label>
								<div class="div-form">
									<input id="remarks" name="remarks" value="${(testPage.remarks) !}" class="form-control input-sm txt_mid" type="text">
								</div>
							</div>
						</div>
						<div class="form-tr">
							<div class="form-td">
								<label>商品描述：</label>
								<div class="div-form">
									<script type="text/plain" id="description" name="description"></script>
									<script type="text/javascript">
										var contentEditor = UE.getEditor('description', {
											initialFrameWidth : 700,
											initialFrameHeight : 360,
											wordCount : true,
											maximumWords : 300 
										});
								    </script>
								 </div>
							 </div>
						 </div>
						<div class="form-tr">
	                    	<div class="form-group btn-div">
								<input type="submit" class="btn btn-primary" value="保存">
								<input type="button" class="btn btn-default" value="返回" onclick="<#if viewtype??><#if viewtype != '0'>history.go(-1);<#else>isReturn()</#if><#else>isReturn()</#if>">
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