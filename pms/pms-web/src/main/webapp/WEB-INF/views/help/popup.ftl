<!DOCTYPE html>
<html>
<head>
    <meta name="decorator" content="v2"/>
    <title>演示添加页面</title>
</head>
<body>

    <form id="myform" method="post" action="${BasePath !}/help/test/save.do" class="ff-form">
        <input  type="hidden" name="id" value="${(testPage.id) !}" />
       
        <div id="error_con" class="tips-form">
            <ul></ul>
        </div>

        <div class="form-group">
            <label><i style="color:#FF0000">*</i> 子页面回传案例：</label>                    
            <div class="f7" onclick="popupFrom()">
                <input data-rule-required="true" data-msg-required="类型不能为空"
                       type="text" id="type" name="type" value="${(unit.type) !}">
                <span class="selectBtn">选</span>
            </div>
        </div>

        <div class="form-group">
            <label><i style="color:#FF0000">*</i> 弹出框带按钮回传案例 ：</label> 
            <div class="f7" onclick="popupFrom2()">
                <input data-rule-required="true" data-msg-required="类型不能为空"
                       type="text" id="type2" name="type" value="${(unit.type) !}">
                <span class="selectBtn">选</span>
            </div>
        </div>
        
        <a class="ff-btn sm" href="${BasePath !}/help/test/form.do?id=${(testPage.id) !}"><i class="fa fa-plus"></i>&nbsp;&nbsp;${(testPage.id)???string('编辑','新增')}</a>
    </form>
    
    
<script type="text/javascript">
    $(function(){
    
    	ffzx.ui(['validate'], function(){
        	executeValidateFrom('myform');
        });
    });

    function popupFrom() {    
	    
	    ffzx.ui(['dialog'], function(){
	        $.frontEngineDialog.executeIframeDialog('test', '弹出页面', rootPath
	                + '/help/test/popupFrom.do', '1000', '610');
		});
    }

    function popupFrom2() {
        var url = rootPath + '/help/test/popupFrom.do';
        
        ffzx.ui(['dialog'], function(){
	        var dlg=dialog({
	            id: "printerForm",
	            title: '批量打印会员',
	            lock: false,
	            content:"<iframe src='"+url+"' style='width:1000px;height:610px' frameborder='0' scrolling='no' id='printerForm' name='printerForm,"+window.location.href+"' ></iframe>",
	            button: [
	                {
	                    value: '打印',
	                    callback: function () {
	                        document.getElementById("printerForm").contentWindow.inStoragePrinter();
	                        return false;
	                    },
	                    focus: true
	                },
	                {
	                    value: '回传',
	                    callback: function () {
	                        document.getElementById("printerForm").contentWindow.getUpdata2();
	                    },
	                    focus: true
	                },
	                {
	                    value: '取消'
	                }
	            ]
	        }).showModal();
        });

    }
</script>
</body>
</html>