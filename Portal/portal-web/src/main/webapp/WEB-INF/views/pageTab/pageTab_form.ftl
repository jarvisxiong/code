<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="v2"/>
<title>页签编辑</title>
</head>
<body>
	
	<form action="${BasePath !}/pageTab/saveDate.do" method="post" id="myform" class="ff-form">
		<input type="hidden" class="form-control" id="id" name="id" value="${(pageTab.id) !}" />
		<div id="error_con" class="tips-form">
			<ul></ul>
		</div>

		<div class="form-group">
			<label><i>*</i>中文名称：</label>
			<div>
				<input type="text" id="name" name="name" value="${(pageTab.name) !}" data-rule-required="true" data-msg-required="页签名称不能为空">
			</div>
		</div>

		<div class="form-group">
			<label><i>*</i>编码：</label>
			<div>
				<input type="text" id="number" name="number" value="${(pageTab.number) !}"  data-rule-required="true" data-msg-required="编码不能为空">
			</div>
		</div>

		<div class="form-group single-row">
			<label><i>*</i>页签类型：</label>
			<div>
				<input type="type" id="pageTabType_temp" readonly="readonly" name="pageTabType_temp" value="动态页签">
				<input type="hidden" id="pageTabType" name="pageTabType" value="DYNAMIC">
			</div>
		</div>

		<div class="form-group">
			<label><i>*</i>序号：</label>
			<div>
				<input type="text" id="sort" name="sort" value="${(pageTab.sort) !}"  data-rule-required="true" data-msg-required="序号不能为空">
			</div>
		</div>

		<div class="form-group">
			<label>页面路径：</label>
			<div>
				<input type="text" id="path" name="path" value="${(pageTab.path) !}">
			</div>
		</div>

		<div class="form-group single-row">
			<label>关键字：</label>
			<div>
				<input type="text" id="key" name="key" value="${(pageTab.key) !}">
			</div>
		</div>

		<div class="form-group">
			<label>meta描述：</label>
			<div>
				<input type="text" id="meta" name="meta" value="${(pageTab.meta) !}">
			</div>
		</div>

		<div class="wrapper-btn">
			<input type="submit" class="ff-btn" value="保存">
			<input type="button" class="ff-btn white btn-close-iframeFullPage" value="返回">
		</div>	

	</form>
<script type="text/javascript">
	$(function() {
		requirejs(['ff/select2'], function(){
			$("select").select2();
		});
		
		requirejs(['ff/validate'], function(){			
			executeValidateFrom('myform', '', '', function(){
				parent.reloadData('pageTab_list');
			});
		});
	});
</script>
</body>
</html>
