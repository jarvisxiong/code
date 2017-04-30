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
        <div class="row">
            <div class="col-lg-10 col-md-12 col-sm-12">
                <form id="myform" method="post" action="${BasePath !}/help/test/save.do">
                    <input  type="hidden" name="id" value="${(testPage.id) !}" />
                    <div class="inquire-ul">
                        <div id="error_con" class="tips-form">
                            <ul></ul>
                        </div>
                        <div class="form-tr">
                            <div class="form-td">
                                <label><i style="color:#FF0000">*</i> 子页面回传案例：</label>
                                <div class="div-form">
                                    <div class="f7" onclick="popupFrom()">
                                        <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="类型不能为空"
                                               type="text" id="type" name="type" value="${(unit.type) !}">
                                        <span class="selectBtn">选</span>
                                    </div>
                                </div>
                            </div>

                            <div class="form-td">
                                <label><i style="color:#FF0000">*</i>弹出框带按钮回传案例 ：</label>
                                <div class="div-form">
                                    <div class="f7" onclick="popupFrom2()">
                                        <input class="form-control input-sm txt_mid" data-rule-required="true" data-msg-required="类型不能为空"
                                               type="text" id="type2" name="type" value="${(unit.type) !}">
                                        <span class="selectBtn">选</span>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </form>
                <div class="btn-div3"><a class="btn btn-primary btn-sm" href="${BasePath !}/help/test/form.do?id=${(testPage.id) !}"><i class="fa fa-plus"></i>&nbsp;&nbsp;${(testPage.id)???string('编辑','新增')}</a></div>
            </div>
        </div>
        <!--新增1--end-->

    </div>
</div>
<script type="text/javascript">
    $(function(){
        executeValidateFrom('myform');

    });

    function popupFrom() {
        $.frontEngineDialog.executeIframeDialog('test', '弹出页面', rootPath
                + '/help/test/popupFrom.do', '1000', '610');


    }

    function popupFrom2() {
        var url = rootPath + '/help/test/popupFrom.do';
        var dlg=dialog({
            id: "printerForm",
            title: '批量打印会员',
            lock: false,
            content:"<iframe src='"+url+"' width='1000px' height='610px' frameborder='0' scrolling='no' id='printerForm' name='printerForm,"+window.location.href+"' ></iframe>",
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

    }
</script>
</body>
</html>