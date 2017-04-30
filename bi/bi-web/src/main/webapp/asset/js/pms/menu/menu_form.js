//选择上级菜单
function showAllMenu() {
	var tree_setting = {
		data : {// 数据
			simpleData : {
				enable : true,// true / false 分别表示 使用 / 不使用 简单数据模式
								// 默认false，一般使用简单数据方式
				idKey : "id",// 节点数据中保存唯一标识的属性名称 默认值："id"
				pIdKey : "pId"// 点数据中保存其父节点唯一标识的属性名称 默认值："pId"
			},
			key : {
				url : ""
			}
		},
		view:{
			selectedMulti: false
		}
	};
	$("#parentName").blur();
	$.frontEngineAjax.executeAjaxPost(
					rootPath + "/menu/ajaxgetMenuList.do",
					"",
					function(ret) {
						zNodes = [];
						var dataTree = transData(ret, 'id', 'pId', 'chindren');
						remove(dataTree, $("#selectMenuId").val());
						var content = '<div class="zTreeDemoBackground left"><ul id="menu_tree" class="ztree"></ul></div>';
						$.frontEngineDialog.executeDialog(
								'selMenu',
								'选择菜单',
								content,
								"400px",
								"350px",
								function(){
									var treeObj = $.fn.zTree.getZTreeObj("menu_tree");
									var nodes = treeObj.getSelectedNodes();
									if(nodes != null && nodes != ""){
										$("#parentId").val(nodes[0].id);
										$("#parentName").val(nodes[0].name);
									}
								}
						);
						$.fn.zTree.init($("#menu_tree"), tree_setting, zNodes);
					}, function(err) {
						$.frontEngineDialog.executeDialogOK('错误', err);
					});
}

/**
 * json格式转树状结构
 * 
 * @param {json}
 *            json数据
 * @param {String}
 *            id的字符串
 * @param {String}
 *            父id的字符串
 * @param {String}
 *            children的字符串
 * @return {Array} 数组
 */
function transData(a, idStr, pidStr, chindrenStr) {
	var r = [], hash = {}, id = idStr, pid = pidStr, children = chindrenStr, i = 0, j = 0, len = a.length;
	for (; i < len; i++) {
		hash[a[i][id]] = a[i];
	}
	for (; j < len; j++) {
		var aVal = a[j], hashVP = hash[aVal[pid]];
		if (hashVP) {
			!hashVP[children] && (hashVP[children] = []);
			hashVP[children].push(aVal);
		} else {
			r.push(aVal);
		}
	}
	return r;
}
function remove(dataTree, currParentId) {
	$.each(dataTree, function(i) {
		if (dataTree[i].pId != currParentId) {
			zNodes.push(dataTree[i]);
			if (typeof (dataTree[i].chindren) != "undefined") {
				remove(dataTree[i].chindren, currParentId);
			}
		}
	});
}
function onClose() {
	window.location.href = rootPath + "/menu/list.do";
	return false;
}
