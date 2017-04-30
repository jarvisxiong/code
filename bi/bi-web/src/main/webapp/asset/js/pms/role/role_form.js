var tree_setting = {
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		view: {
			showIcon: false,
			showLine: false
		}

};

$(function() {
	$.fn.zTree.init($("#left_Menu_tree"), tree_setting, menuNodes);
});

$(function() {
	$.fn.zTree.init($("#right_office_tree"), tree_setting, officeNodes);
});

/*
$(function() {
	alert('....bb');
	$("#dataScope").bind("onchange", function(event) { 
		alert('....aaaa');
		if($("#dataScope ").val() == '9'){
			$("#right_office_tree").show(); 
		}else{
			$("#right_office_tree").hide(); 
		}
	});
});*/
function changeDataScope(dataScope){
	if(dataScope == '9'){
		$("#right_office_tree").show();
	}else{
		$("#right_office_tree").hide(); 
	}
}

function roleMenuOffice() {
	//设置角色菜单
	var menuTree = $.fn.zTree.getZTreeObj("left_Menu_tree");
	var menuNodes = menuTree.getCheckedNodes(true);
	var menuId = '';
	for (var i=0, l=menuNodes.length; i<l; i++) {
		if(i==l-1){
			menuId += menuNodes[i].id;
		}else{
			menuId += menuNodes[i].id+'_'
		}
	}
	$("#roleMenuIds").val(menuId);
	
	//设置角色机构
	if($("#dataScope ").val() == '9'){
		var officeTree = $.fn.zTree.getZTreeObj("right_office_tree");
		var officeNodes = officeTree.getCheckedNodes(true);
		var officeId = '';
		for (var i=0, l=officeNodes.length; i<l; i++) {
			if(i==l-1){
				officeId += officeNodes[i].id;
			}else{
				officeId += officeNodes[i].id+'_'
			}
		}
		$("#roleOfficeIds").val(officeId);
	}
	
	return true;
}