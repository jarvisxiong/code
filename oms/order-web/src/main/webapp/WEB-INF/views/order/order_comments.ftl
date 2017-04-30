<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>客服留言</title>
</head>
<body>

<div class="tab-content">
	<div class="tab-pane fade in active" id="addArea">
		<div class="row">
                    <div class="col-lg-10 col-md-12 col-sm-12">
                    <form id="myform" method="post" action="${BasePath !}/help/test/save.do">
                    		<div class="addForm1">
                    				<div id="error_con" class="tips-form">
                            <ul></ul>
                        </div>
                             <div class="tips-form">
                             </div>
							<div class="form-tr">
	                                    <div class="form-td">
	                                        <label><i>*</i>接收人：</label>
	                                        <div class="div-form">
	                                        <textarea rows="3" readonly="readonly" class="form-control input-sm txt_big2" id ="receivers" name="receivers">${(phones_Str)!}</textarea>
	                                        </div>
	                                    </div>
	                          </div>
                    	      <div class="form-tr">
	                                    <div class="form-td">
	                                        <label><i>*</i>消息内容：</label>
	                                        <div class="div-form">
	                                        <textarea rows="3" placeholder="请输入留言内容，最多140字" class="form-control input-sm txt_big2" id="content"  data-rule-required="true" data-rule-rangelength="[1,100]" data-msg-required="内容不能为空" data-msg-rangelength="用户名长度必须介于 1 和 100之间的字符串"  name="content"></textarea>
	                                        </div>
	                                    </div>
	                                </div>
                                <div class="form-tr">
                                    <div class="form-td">
                                        <label><i>*</i>推送方式：</label>
                                        <div class="div-form">
                                        	<select class="input-select2" id="type" name="sendType" data-rule-required="true"  >											  											                                       
					                                <option value="APP_MSG">APP信息</option>
					                                <option value="PHONE_MSG">手机信息</option>
											</select> 
                                        	</div>
                                    </div>
                                </div>
	
                                <div class="form-tr">
                                    <div class="btn-div">
                                        <input type="button" class="btn btn-primary" onclick="toSend();" value="发送">
                                        <input type="button" class="btn btn-default" value="取消" onclick="closeCurrentWin();">
                                    </div>
                                </div>
                        </div>
                        </form>
                    </div>
                 </div>
             </div>
</div>

<link rel="stylesheet"	href="${BasePath !}/asset/js/control/ztree/css/tree_artdialog.css?v=${ver !}" type="text/css">
<script type="text/javascript">
function toSend(){
	var content = $("#content").val();
	if(content==null||content==""){
		$.frontEngineDialog.executeDialogContentTime("内容不能为空",3000);
		return;
	}else if(content.length>140){
		$.frontEngineDialog.executeDialogContentTime("内容最多140个字",3000);
		return;
	}
	var receivers = $.trim($("#receivers").val());
	if(receivers==null||receivers==""){
		$.frontEngineDialog.executeDialogContentTime("接收人不能为空",3000);
		return;
	}
	
	$.ajax({
		url : rootPath + "/order/ajaxToSend.do",
		data :$("#myform").serialize(),// 给服务器的参数
		type : "POST",
		dataType : "json",
		async : false,
		cache : false,
		success : function(result) {
			if (result.status == 'success' || result.code == 0) {
				$.frontEngineDialog.executeDialogContentTime("操作成功！",1000);
				setTimeout(function(){
					closeCurrentWin();
				},1000);
			}else{
				$.frontEngineDialog.executeDialogContentTime(result.msg,3000);
			}
		}
	});
}

function closeCurrentWin(){
	var art=$(parent.document.getElementById('title:comments')).prev();
	art.click();
} 
</script> 

</body>
</html>
