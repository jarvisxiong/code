var tree_setting = {
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeClick : beforeClick
	}
};
var log, className = "dark";

// 初始化的方法
jQuery(function($) {
	console.log(zNodes);
	$.fn.zTree.init($("#menu_tree"), tree_setting, zNodes);
	var zTree = $.fn.zTree.getZTreeObj("menu_tree");
	zTree.setting.edit.drag.prev = true;
	zTree.setting.edit.drag.inner = true;
	zTree.setting.edit.drag.next = true;
	zTree.setting.edit.drag.isMove = true;

});


//选中节点调用的方法
function beforeClick(treeId, treeNode) {
	$("#treeNodeId").val(treeNode.id);
	$("#treeNodeIsParent").val(treeNode.isParent);
	if (treeNode.isParent) {
		$("#tableList_Action tbody").empty();
		currMenuId = "";
		return true;
	} else {
		var menuId = treeNode.id;
		var params = '{"menuId": "' + menuId + '"}';
		currMenuId = menuId;
		var searchParams = jQuery.parseJSON(params);
		console.log(searchParams);
		grid.setOptions({
			data : searchParams
		});
	}
}


var isModuleType=null;
var moduleType=null;
var flag = "";
var tabKey = "PARAMSET";



$(document).ready(function(){
	var height = $(top).height()-138;
	$("#homeIframe").css("height",height);
	$("div[class=system_tab_title] li").click(
			function() {
				$(this).addClass("hover").siblings("li").removeClass("hover");
				var key = $(this).attr("key");
				var srcUrl = "";
				if(key=="PARAMSET"){
					tabKey = "PARAMSET";
					srcUrl = "http://localhost:8089/web/basedata/param/list?isModuleType="+isModuleType+"&moduleType="+moduleType;
				}else if(key=="BILLTYPE"){
					tabKey = "BILLTYPE";
					srcUrl = "http://localhost:8089/web/basedata/billType/list?isModuleType="+isModuleType+"&moduleType="+moduleType;
				}else if(key=="CODERULE"){
					tabKey = "CODERULE";
					srcUrl = "http://localhost:8089/web/basedata/code/list?isModuleType="+isModuleType+"&moduleType="+moduleType;
				}else if(key=="PERMISSION"){
					tabKey = "PERMISSION";
					srcUrl = "http://localhost:8089/web/permission/permissionItem/list?isModuleType="+isModuleType+"&moduleType="+moduleType;
				}else if(key=="PRINTCONFIG"){
					tabKey = "PRINTCONFIG";
					srcUrl = "http://localhost:8089/web/basedata/printConfig/list?isModuleType="+isModuleType+"&moduleType="+moduleType;
				}else if(key=="APICENTER"){
					tabKey = "APICENTER";
					srcUrl = "http://localhost:8089/web/basedata/apiCenter/list?isModuleType="+isModuleType+"&moduleType="+moduleType;
				}
				$("#homeIframe").attr("src",srcUrl);
			});
	
	
	
	//是否从岗位管理菜单点击进来
	if(flag=="PERMISSION"){
 		$("div[class=system_tab_title] li[key='PERMISSION']").click();
	}
});







