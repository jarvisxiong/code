function onClose() {
	window.location.href = rootPath + "/userGroup/list.do";
	return false;
}

$(function() {
	//加载角色树形
//	$.fn.zTree.init($("#left_role_tree"), tree_setting, roleNodes);
	showRoleZtree(roleNodes,"选择角色","left_role_tree");
	//showRoleZtree(userNodes,"选择用户","left_userGroup_tree");
});

var tree_setting = {
	check : {enable : true,chkboxType : {"Y" : "ps","N" : "ps"}},
	data : {simpleData : {enable : true}},
	view: {showIcon: false,showLine: false}
};
//加载树形
function showRoleZtree(zNodes,name,treeName){
	
	var zRoleListNodes = [ {
	    id : 0,
	    pId : 0,
	    name : name,
	    open : true
	} ];

	for (var i = 0; i < zNodes.length; i++) {
	    var nodeItem = new Object();
	    nodeItem.id = zNodes[i].id;
	    nodeItem.pId = 0;
	    nodeItem.checked = zNodes[i].checked;
	    nodeItem.name = zNodes[i].name;
	    zRoleListNodes.push(nodeItem);
	}

	$("#"+treeName).empty();
    $.fn.zTree.init($("#"+treeName), tree_setting, zRoleListNodes);
    //var zTree = $.fn.zTree.getZTreeObj(treeName);
}

//设置选择的roleIds
function loadIds(){
    var zTree = $.fn.zTree.getZTreeObj("left_role_tree");
    var checkCountNodes = zTree.getCheckedNodes();
    var roleIds = "";
    // 这里需要过滤掉父节点
    for (var i = 1; i < checkCountNodes.length; i++) {
        roleIds += checkCountNodes[i].id + ",";
    }
	$("#roleIds").val(roleIds);
	
//    var zTree1 = $.fn.zTree.getZTreeObj("left_user_tree");
//    var checkCountNodes1 = zTree1.getCheckedNodes();
//    var userIds = "";
//    // 这里需要过滤掉父节点
//    for (var i = 1; i < checkCountNodes1.length; i++) {
//    	userIds += checkCountNodes1[i].id + ",";
//    }
//	$("#userIds").val(userIds);
	return true;
}