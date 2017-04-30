<#include "../global.ftl" encoding="utf-8">

<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="v2"/>
	<title>演示添加页面</title>
</head>
<body>

<ul class="nav nav-tabs">
    <li class="active"><a href="#">演示${(id)???string('编辑','新增')}</a></li>
</ul>

<!--新增1--start-->			

<form id="myform" method="post" action="${BasePath !}/help/test/save.do" class="ff-form">
	<input  type="hidden" name="id" value="${(id) !}" /> 
	
	<div id="error_con" class="tips-form">
		<ul></ul>
	</div>
	
	<div class="form-group">
		<label><i>*</i>用户名：</label>
		<div>
			<input id="name" name="name" value="" type="text" data-rule-required="true" data-rule-rangelength="[1,32]" data-msg-required="用户名不能为空" data-msg-rangelength="用户名长度必须介于 5 和 32之间的字符串">
		</div>
	</div>
	
	<div class="form-group">
		<label><i>*</i>年龄：</label>
		<div>
			<input id="age" name="age" value="" type="text" data-rule-required="true"  data-msg-required="年龄不能为空" data-msg-range="年龄必须介于 0 和 100 之间">
		</div>
	</div>

	<div class="form-group">
		<label>邮箱：</label>
		<div>
			<input id="email" name="email" value="" type="text" data-msg-email="必须输入正确格式的电子邮件">
		</div>
	</div>
	
	<div class="form-group single-row">
		<label>上传头像（单）：</label>
		<div class="webuploader" id="upload_img_single"></div>
	</div>
	
	<div class="form-group single-row">
		<label>上传图片（多）：</label>
		<div class="webuploader" id="upload_img_multiple"></div>
	</div>	
	
	
	<div class="form-group single-row">
		<label>上传文件（单）：</label>
		<div class="webuploader" id="upload_file_single"></div>
	</div>
	
	<div class="form-group single-row">
		<label>上传文件（多）：</label>
		<div class="webuploader" id="upload_file_multiple"></div>
	</div>

	<div class="form-group">
		<label>商品描述：</label>
		<div>
			<script type="text/plain" id="description" name="description" class="ueditor"><i>默认加载的内容...</i></script>
		</div>
	 </div>
	 
	<div class="form-group">
		<label>备注：</label>
		<div>
			<textarea id="remarks" name="remarks"></textarea>
		</div>
	</div>

	<div class="wrapper-btn">
		<input type="submit" class="ff-btn" value="保存" />
		<input type="button" class="ff-btn white btn-close-iframeFullPage" value="后退" />
	</div>
</form>


<script type="text/javascript">
//var instanceUpload = null;

$(document).ready(function(){

	/*
	$.ajax({  
	      url:'${BasePath !}/help/test/formDealWith.do',// 跳转到 action  
	      data:{  
	              id:'${(id) !}'
	      },  
	     type:'post',  
	     cache:false,  
	     dataType:'json',  
	     success:function(data) {
	      $('#myform').setForm(data);
	      },  
	      error : function() {   
	           alert("异常！");  
	      }  
	});
	*/
	
	ffzx.ui(['validate'], function(){		
		executeValidateFrom('myform','', '', function(){
			parent.reloadData('demo_list');
			parent.closeIframeFullPage('demo_list');
		});
	});
	
	ffzx.ui(['upload'], function(){
	
		//上传单个图片
		ffzx.init.upload({
			id: 'upload_img_single',
			type: 'image', // type: image, file
			multiple: false, // false: 限制只上传一个图片/文件; 默认为 true: 可上传多个
			server: '/pms-web/index.do', // Backend script receiving the file(s)
			uploaded: [
				{
					id: 1,
					name: '表单-前端商业化.jpg',
					src: rootPath + '/asset/img/thumbnail_01.jpg'
				}
			],
			
			// 以下为可选
			callback: {
				
				// Before single file selected
				beforeFileQueued: function(file){
					//console.log(uploaderInstance.getStats())
				},
						
				// When single file selected
				fileQueued: function(file){ },
				
				// When multiple files selected
				filesQueued: function(files){ },
				
				// When single file deleted
				fileDeleted: function(file){ },
				
				// Uploading
				uploadProgress: function(file, percentage){ },
				
				// 'response' is returned from server
				uploadSuccess: function(file, response){
					console.log(response);
					//$('#'+file.id).append('<input value="" />')
				},
				
				// Detailed error messages are printed in console
				uploadError: function(file){ },
				
				// Single file finished no matter it is uploaded successfully or not
				uploadComplete: function(file){ },
				
				// All finished
				uploadFinished: function(){ }
			}
		});
		
		//上传多个图片
		instanceUpload = ffzx.init.upload({
			id: 'upload_img_multiple',
			type: 'image', // type: image, file
			server: '/pms-web/index.do', // Backend script receiving the file(s)
			uploaded: [
				{
					id: 1,
					name: '表单-前端商业化.jpg',
					src: rootPath + '/asset/img/thumbnail_01.jpg'
				},
				{
					id: 2,
					name: '表格-前端商业化.jpg',
					src: rootPath + '/asset/img/thumbnail_02.jpg'
				}
			]			
		});
		
		//上传单个文件
		ffzx.init.upload({
			id: 'upload_file_single',
			type: 'file', // type: image, file
			multiple: false, // false: 限制只上传一个图片/文件; 默认为 true: 可上传多个
			server: '/pms-web/index.do', // Backend script receiving the file(s)		
			uploaded: [
				{
					id: 1,
					name: '表单-前端商业化.jpg'
				}
			]	
		});	
		
		//上传多个文件
		ffzx.init.upload({
			id: 'upload_file_multiple',
			type: 'file', // type: image, file
			server: '/pms-web/index.do', // Backend script receiving the file(s)
			uploaded: [
				{
					id: 1,
					name: '表单-前端商业化.jpg'
				},
				{
					id: 2,
					name: '表格-前端商业化.jpg'
				}
			]		
		});
		
	});
});

</script>
</body>
</html>