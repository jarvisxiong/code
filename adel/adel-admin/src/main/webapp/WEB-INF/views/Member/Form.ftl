<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="v2"/>
<title>用户编辑</title>
</head>
<body>
	
	<form action="${BasePath !}/Member/save.do" method="post" id="myform" class="ff-form">
		<input type="hidden" name="id" value="${(endity.id) !}" />
		<div id="error_con" class="tips-form">
			<ul></ul>
		</div>

		<div class="form-group">
			<label><i>*</i>用户名称：</label>
			<div>
				<input type="text"  name="name" value="${(entity.name) !}" data-rule-required="true" data-msg-required="用户名称不能为空">
			</div>
		</div>
        <div class="form-group">
            <label><i>*</i>用户编码：</label>
            <div>
                <input type="text"  name="code" value="${(entity.code) !}" data-rule-required="true" data-msg-required="用户编码不能为空">
            </div>
        </div>

		<div class="form-group">
			<label>用户备注：</label>
			<div>
				<input type="text"  name="remarks" value="${(entity.remarks) !}">
			</div>
		</div>


		<div class="wrapper-btn">

			<input type="submit" class="ff-btn" value="保存">

			<input type="button" class="ff-btn white btn-close-iframeFullPage" value="返回">
		</div>	

	</form>

	<script type="text/javascript" src="${BasePath !}/asset/js/kart/member/member_form.js?v=${ver !}"></script>
	<script type="text/javascript">

		$(function() {
		
			requirejs(['ff/select2'], function(){
				$("select").select2();
			});
			
			requirejs(['ff/validate'], function(){			
				executeValidateFrom('myform');
			});
		});

	</script>
</body>
</html>
