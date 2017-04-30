$(document).ready(function(){
  $(".redpackagePage").click(function(){
	  window.location.href=rootPath + '/redpackage/list.do';
  });
});
function gen_action(item) {
	var id = item.id;
	
	var objAction = {
		edit:[],
		/*deletes:[],*/
		isEnableds:[]
	};
	
	objAction['edit'].push({
		label: '编辑',
		href: "javascript: iframeFullPage('"+ rootPath +"/redpackageGrant/form.do?id="+ id +"')"
	});
	/*objAction['deletes'].push({
		label: '删除',
		href: "javascript:isdelete('"+item.id+"')"
	});*/
	
	objAction['isEnableds'].push({
		// SSUI: 前面如果有 render 过此列数据，要用转换后的数据来判断！
		label: (item.state == stateType['1']) ? stateType['0'] : stateType['1'],
		href: "javascript:isEnabled('"+id+"', '"+(item.state == stateType['1'] ? '0' : '1')+"')"
	});
	return objAction;
}
function floatNum(value,message){
	if(value.value!=null && value.value.replace(/(^\s*)|(\s*$)/g, "")!=""){
		var numvalue=value.value.replace(/(^\s*)|(\s*$)/g, "");
		if(/^[-\+]?\d+$/.test(numvalue) || /^[-\+]?\d+(\.\d+)?$/.test(numvalue)){
		}else{
			$("#"+value.id).val("");
			$.frontEngineDialog.executeDialogContentTime(message+"面值格式不正确",2000);
		}
	}/*
	if(value.value!=null && value.value.replace(/(^\s*)|(\s*$)/g, "")!=""){
		if(/^[-\+]?\d+$/.test(value.value) || /^[-\+]?\d+(\.\d+)?$/.test(value.value)){
		}else{
			$("#"+value.id).val("");
			$.frontEngineDialog.executeDialogContentTime(message+"格式不正确",2000);
		}
	}*/
}
function returnRedpackage(){
	window.location.href=rootPath + '/redpackage/list.do';
}
function isEnabled(id, type) {
	var titleStr;
	if (type == 1) {//禁用
		titleStr = "启用";
	}
	if (type == 0) {
		titleStr = "禁用";
	}

	$.frontEngineDialog.executeDialog(
			'selMenu', 
			titleStr, 
			'是否确定' + titleStr+ '此模板？', 
			"200px", 
			"35px", 
			function() {
		$.ajax({
			url : rootPath + "/redpackageGrant/updateState.do",
			data : {
				id : id,
				state : type
			},// 给服务器的参数
			type : "POST",
			dataType : "json",
			async : false,
			cache : false,
			success : function(result) {
				if (result.status == 'success' || result.code == 0) {
					$.frontEngineDialog.executeDialogContentTime(result.infoStr);
					// SSUI: 重新载入当前页的数据
					reloadData('redpackageGrant_list2');
				} else {
					$.frontEngineDialog.executeDialogContentTime(result.infoStr);
				}
			}
		});
	});
}

/**
 * 删除
 * @param id
 * @param name
 */
function isdelete(id) {
    $.frontEngineDialog.executeDialog(
            'isdelete', 
            '删除', 
            '是否确定删除此模板？', 
            "250px", 
            "35px", 
            function() {
        $.ajax({
            url : rootPath + "/redpackageGrant/delete.do",
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

					// SSUI: 重新载入当前页的数据
					reloadData('redpackageGrant_list2');
//                    setTimeout('window.location.href="' + rootPath+ '/redpackageGrant/list.do"', 1000);
                } else {
                    dialog({
                        quickClose : true,
                        content : result.msg
                    }).show();
                }
            }
        });
    });
}

function toDetailed(data, type, full, meta){
	var codeLing='<a href="javascript:iframeFullPage(\''+ rootPath + '/redpackageGrant/detail.do?id='+ full.id + '&viewType=1\')">'+data+'</a>';
	return codeLing;
}



