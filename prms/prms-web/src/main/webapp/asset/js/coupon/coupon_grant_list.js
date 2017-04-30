var dlg = null;
function isdelete(id,name) {
    $.frontEngineDialog.executeDialog(
            'isdelete', 
            '删除', 
            '是否确定删除这组优惠券？', 
            "250px", 
            "35px", 
            function() {
        $.ajax({
            url : rootPath + "/couponGrant/delete.do",
            data : {
                id : id
            },// 给服务器的参数
            type : "POST",
            dataType : "json",
            async : false,
            cache : false,
            success : function(result) {
                if (result.status == 'success' || result.code == 0) {
                    dialog({
                        quickClose : true,
                        content : '操作成功！'
                    }).show();
                    setTimeout('window.location.href="' + rootPath+ '/couponGrant/list.do"', 1000);
                } else {
                    dialog({
                        quickClose : true,
                        content : result.infoStr
                    }).show();
                }
            }
        });
    });
}

/**
 *  优惠码-弹出窗
 */
function vcodeKu(id) {
    var url = rootPath + '/couponVcode/list.do?couponGrantId='+id;
    console.log(url);
    var dlg=dialog({
        id: "printerForm",
        title: '优惠码',
        lock: false,
        content:"<iframe name='printerForm,"+window.location.href+"' src="+url+" width='900px' height='600px' frameborder='0' scrolling='yes' id='printerForm'></iframe>",
        button: [
            
            
	             {
		             value: '全部导出',
		             callback: function () {
		         		var url = rootPath + '/couponVcode/couponVcodeExport.do?couponGrantId='+id;	
		         		window.location.href=url;
		            	 return false;
		             },
		             focus: true
		         },{
		             value: '关闭',
		             callback: function () {
		             },
		             focus: true
		         }
            
        ]
    }).showModal();
}

//stop_item  ：暂停领取
$(".table tr td a[name='stop_item']").click("click", function() { 
	var url =this.href;
	$.frontEngineDialog.executeDialog('delete_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　确定要暂停领取这组优惠券？　　','100%','100%',
			function(){
				//window.location.href=url;
				common_doSave(url,"get");
			}
		);
	return false;
});

function couponLink(id,linkId){
	var url = rootPath + '/couponGrant/link.do?id='+id;
    dlg=dialog({
        id: "printerForm",
        lock: false,
        content:"<iframe id='printerForm' name='printerForm,"+window.location.href+"' src='"+url+"' width='480px' height='38px' frameborder='0' scrolling='no' id='printerForm'></iframe>",
        align: 'left',
        quickClose:true
    }).show(document.getElementById(linkId));
}

function closeDialog(){
	dlg.close();
}
