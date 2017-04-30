<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>演示添加页面</title>
</head>
<body>
<div class="tab-content">
	<div class="tab-pane fade in active" id="addUser">

		<!--新增1--start-->
			<div class="col-lg-10 col-md-12 col-sm-12">
				<form id="myform" method="post" action="${BasePath !}/help/test/save.do">
					<input  type="hidden" name="id" value="${(testPage.id) !}" /> 
					<div class="inquire-ul">
						<div class="form-tr">
							<div class="form-td">
								<label>回传信息：</label>
								<div class="div-form">
									<input id="email" name="email" value="${(testPage.email) !}" class="form-control input-sm txt_mid" type="text" data-rule-email="true" data-msg-email="必须输入正确格式的电子邮件">
								</div>
							</div>
							<div class="form-td">
								<div class="div-form"><button class="btn btn-sm btn-primary">回传</button></div>
							</div>
						</div>
					</div>
				</form>
			</div>
		<!--新增1--end-->
    	<!--表格修改2--start-->
        <table class="table table-common">
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