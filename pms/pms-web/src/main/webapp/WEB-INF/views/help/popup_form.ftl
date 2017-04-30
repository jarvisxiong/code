<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>演示添加页面</title>
</head>
<body>
<div class="page-dialog-15">
	<form id="myform" method="post" action="${BasePath !}/help/test/save.do" class="ff-form">
		<input  type="hidden" name="id" value="${(testPage.id) !}" /> 
		
		<div class="form-group no-padding">
			<label>回传信息：</label>
			<div>
				<input id="email" name="email" value="${(testPage.email) !}" type="text" data-rule-email="true" data-msg-email="必须输入正确格式的电子邮件">
			</div>
		</div>
		
		<button class="ff-btn sm">回传</button>
	</form>
	
	<table class="ff-tbl">
		<thead>
			<tr>
				<th>我真的是表格</th><th>我真的是表格</th>
			</tr>
		</thead>
		<tbody>
			<tr ondblclick='getUpdata()'><td>双击回传</td><td>双击回传</td></tr>
			<tr ondblclick='getUpdata()'><td>双击回传</td><td>双击回传</td></tr>
		</tbody>
	</table>
</div>
<script type="text/javascript">

	//子控制：弹窗页面返回结果
	function getUpdata() {
	
		//获取返回值
	    var email=$("#email").val();
	    if(email == ''){
	    	alert('请输入回传内容');
	    	return;
	    }
	    
		//设置返回值存放位置
	    var $pd=parent.document.getElementById('type');
	    $($pd).val(email);
	    
		//关闭弹窗窗
	    var $close=$(parent.document.getElementById('title:test')).prev();
	    $close.click();
	}

	//父控制：弹窗页面返回结果
	function getUpdata2() {
		//获取返回值
	    var email=$("#email").val();
	    
	    if(email == ''){
	    	alert('请输入回传内容');
	    	return;
	    }
	    
		//设置返回值存放位置
	    var $pd=parent.document.getElementById('type2');
	    $($pd).val(email);
	}
	
	function inStoragePrinter(){
		window.print();
	}

</script>
</body>
</html>