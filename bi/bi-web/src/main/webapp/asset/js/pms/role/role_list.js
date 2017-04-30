var pageii = null;
var grid = null;
function editAccount() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		$.frontEngineDialog.executeDialogContentTime('请选择一个角色!');
		return;
	}
	if (cbox.length > 1) {
		$.frontEngineDialog.executeDialogContentTime('只能选中一个!');
		return;
	}
	$.frontEngineDialog.executeIframeDialog('edit', '编辑角色', rootPath
			+ '/role/roleEditView?roleId=' + cbox, '100%', '310');
}
function addAccount() {
	$.frontEngineDialog.executeIframeDialog('add', '新增角色', rootPath
			+ '/role/roleAddView', '100%', '310');

}
function permissions() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		$.frontEngineDialog.executeDialogContentTime('请选择一个角色!');
		return;
	}
	if (cbox.length > 1) {
		$.frontEngineDialog.executeDialogContentTime('只能选中一个!');
		return;
	}
	$.frontEngineDialog.executeIframeDialog('add', '分配权限', rootPath
			+ '/role/role_menu?roleId=' + cbox, '100%', '450');
}

function editValidation(iframeWindow) {
	var roleName = iframeWindow.document.getElementById('roleName').value;
	if (roleName == "") {
		var d = dialog({
			content : '角色名不能为空',
			quickClose : true
		});
		d.show(document.getElementById('option-quickClose'));
		setTimeout(function() {
			d.close().remove();
		}, 2000);
		return false;
	}
	return true;
}

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
function roleMenu(roleId) {
	$.frontEngineAjax
			.executeAjaxPost(
					rootPath + "/role/role_menu.do?roleId=" + roleId,
					"",
					function(ret) {
						console.log(ret);
						var content = '<div class="zTreeDemoBackground left"><ul id="menu_tree" class="ztree" style="width:400px;"></ul></div>';
						$.frontEngineDialog.executeDialog('selMenu', '分配权限',
								content, "400px", "350px", function() {
									var zTree = $.fn.zTree.getZTreeObj("menu_tree");
									if (zTree.getChangeCheckedNodes().length <= 0) {
										$.frontEngineDialog.executeDialogContentTime('尚未改变任何权限!');
									} else {
										var nodes = zTree.getCheckedNodes(true);
										var menuId = '';
										for (var i=0, l=nodes.length; i<l; i++) {
											if(i==l-1){
												menuId += nodes[i].id;
											}else{
												menuId += nodes[i].id+'_'
											}
										}
										console.log(menuId);
										var data = 'menuId=' + menuId + "&roleId=" + roleId;
										common_doSave(rootPath + '/role/update_role_menu.do',"POST","",data);
									}
									return false;
								});
						$.fn.zTree.init($("#menu_tree"), tree_setting, ret);
					}, function(err) {
						$.frontEngineDialog.executeDialogOK('错误', err);
					});
}