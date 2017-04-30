function gen_action(item) {
	var id = item.id;
	var objAction = {
		edit: [
			{
				label: '编辑',
				href: "javascript: iframeFullPage('"+ rootPath +"/user/form.do?id="+ id +"')"
			}
		],
		loginFlag: [
			{
				// SSUI: 前面如果有 render 过此列数据，要用转换后的数据来判断！
				label: (item.loginFlag == dict_loginFlag['1']) ? dict_loginFlag['0'] : dict_loginFlag['1'],
				href: "javascript:isEnabled('"+id+"', '"+(item.loginFlag == dict_loginFlag['1'] ? '0' : '1')+"')"
			}
		],
		delete: [
			{
				label: '删除',
				href: rootPath + '/user/delete.do?id=' + id
			}
		],
		role: [
			{
				label: '分配角色',
				href: "javascript:showAllrole('"+id+"')"
			}
		],
		userGroup: [
			{
				label: '分配用户组',
				href: "javascript:showAlluserGroup('"+id+"')"
			}
		],
		userMenu: [
			{
				label: '分配权限',
				href: "javascript:showAlluserMenu('"+id+"')"
			}
		]
	};
	
	return objAction;
}

function isEnabled(userId, type) {
	var titleStr = "启用登录";
	if (type == 0) {
		titleStr = "禁用登录";
	}

	$.frontEngineDialog.executeDialog(
			'selMenu', 
			titleStr, 
			'是否确定' + titleStr+ '？', 
			"300px", 
			"35px", 
			function() {
		$.ajax({
			url : rootPath + "/user/updateLoginFlag.do",
			data : {
				userId : userId,
				loginFlag : type
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
					reloadData('user_list');
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

function isdelete(id,name) {
    $.frontEngineDialog.executeDialog(
            'isdelete', 
            '删除', 
            '是否确定删除“'+name+'”？', 
            "300px", 
            "35px", 
            function() {
        $.ajax({
            url : rootPath + "/user/delete.do",
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
                    setTimeout('window.location.href="' + rootPath+ '/user/tableList.do"', 1000);
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

//选择用户组
function showAlluserGroup(userId) {
    $.frontEngineAjax.executeAjaxPost(
        rootPath + "/user/assignUserGroupForm.do",
        "userId="+userId,
        function(ret) {
            var zNodes = ret;
            var content = '<div class="zTreeDemoBackground left" style="width: 100%; height: 400px;overflow: auto;"><ul id="UserGroup_tree" class="ztree"></ul></div>';
          
            $.frontEngineDialog.executeDialog(
                  'selUserGroup',
                  '选择用户组1',
                  content,
                  "300px",
                  "400px",
                  function(){
                      var zTree = $.fn.zTree.getZTreeObj("UserGroup_tree");
                      var checkCountNodes = zTree.getCheckedNodes();
                      var roleIds = "";
                      // 这里需要过滤掉父节点
                      for (var i = 0; i < checkCountNodes.length; i++) {
                          roleIds += checkCountNodes[i].id + ",";
                      }
                      $.ajax({
                          url : rootPath + "/user/assignUserGroup.do",
                          data : {
                        	  userGroupIds : roleIds,
                        	  userId : userId
                          },// 给服务器的参数
                          type : "POST",
                          dataType : "json",
                          async : false,
                          cache : false,
                          success : function(result) {
                              if (result.code == '0' || result.code == 0) {
                                  var d = dialog({quickClose: true,content: '分配用户组成功'}).show();
                                  setTimeout(function(){d.close();},1000);
                              } else {
                                  dialog({quickClose: true,content: result.msg}).show();
                              }
                          }
                      });
                  }
          );
            showRoleZtree(zNodes,'用户组树形',"UserGroup_tree",1);
      }
    );
}

//选择角色
function showAllrole(userId) {
    $.frontEngineAjax.executeAjaxPost(
        rootPath + "/user/assignRoleForm.do",
        "userId="+userId,
        function(ret) {
            var zNodes = ret;
            var content = '<div class="zTreeDemoBackground left" style="width: 100%; height: 400px;overflow: auto;"><ul id="menu_tree" class="ztree"></ul></div>';
          
            $.frontEngineDialog.executeDialog(
                  'selMenu',
                  '选择角色',
                  content,
                  "300px",
                  "400px",
                  function(){
                      var zTree = $.fn.zTree.getZTreeObj("menu_tree");
                      var checkCountNodes = zTree.getCheckedNodes();
                      var roleIds = "";
                      // 这里需要过滤掉父节点
                      for (var i = 1; i < checkCountNodes.length; i++) {
                          roleIds += checkCountNodes[i].id + ",";
                      }
                      $.ajax({
                          url : rootPath + "/user/assignRole.do",
                          data : {
                        	  roleIds : roleIds,
                        	  userId : userId
                          },// 给服务器的参数
                          type : "POST",
                          dataType : "json",
                          async : false,
                          cache : false,
                          success : function(result) {
                              if (result.code == '0' || result.code == 0) {
                                  var d = dialog({quickClose: true,content: '分配角色成功'}).show();
                                  setTimeout(function(){d.close();},1000);
                              } else {
                                  dialog({quickClose: true,content: result.msg}).show();
                              }
                          }
                      });
                  }
          );
            showRoleZtree(zNodes,'角色列表',"menu_tree",0);
      }
    );
}

//选择用户菜单
function showAlluserMenu(userId) {
    $.frontEngineAjax.executeAjaxPost(
        rootPath + "/user/assignUserMenuForm.do",
        "userId="+userId,
        function(ret) {
            var zNodes = ret;
            var content = '<div class="zTreeDemoBackground left" style="width: 100%; height: 400px;overflow: auto;"><ul id="UserMenu_tree" class="ztree"></ul></div>';
          
            $.frontEngineDialog.executeDialog(
                  'selUserMenu',
                  '选择用户菜单',
                  content,
                  "300px",
                  "400px",
                  function(){
                      var zTree = $.fn.zTree.getZTreeObj("UserMenu_tree");
                      var checkCountNodes = zTree.getCheckedNodes();
                      var menuIds = "";
                      // 这里需要过滤掉父节点
                      for (var i = 0; i < checkCountNodes.length; i++) {
                    	  menuIds += checkCountNodes[i].id + ",";
                      }
                      $.ajax({
                          url : rootPath + "/user/assignUserMenu.do",
                          data : {
                        	  userMenuIds : menuIds,
                        	  userId : userId
                          },// 给服务器的参数
                          type : "POST",
                          dataType : "json",
                          async : false,
                          cache : false,
                          success : function(result) {
                              if (result.code == '0' || result.code == 0) {
                                  var d = dialog({quickClose: true,content: '分配用户菜单成功'}).show();
                                  setTimeout(function(){d.close();},1000);
                              } else {
                                  dialog({quickClose: true,content: result.msg}).show();
                              }
                          }
                      });
                  }
          );
          showUserMenuZtree(zNodes,'用户菜单树形',"UserMenu_tree",0);
      }
    );
}

var setting = {
	    check : {enable : true,chkboxType : {"Y" : "ps", "N" : "ps"} },
		view:{selectedMulti: false},
	    data : {simpleData : { enable : true}}};

var setting1 = {
	    check : {enable : true,chkboxType : {"Y" : "", "N" : ""} },
		view:{selectedMulti: false},
	    data : {simpleData : { enable : true}}};

function showRoleZtree(zNodes,name,treeName,settingType){
	
	var zRoleListNodes = [ {
	    id : 0,
	    pId : 0,
	    name : name,
	    open : true
	} ];

	for (var i = 0; i < zNodes.length; i++) {
	    var nodeItem = new Object();
	    nodeItem.id = zNodes[i].id;
	    nodeItem.pId = zNodes[i].pId;
	    nodeItem.checked = zNodes[i].checked;
	    nodeItem.name = zNodes[i].name;
	    nodeItem.open = true;
	    zRoleListNodes.push(nodeItem);
	}

		$("#"+treeName).empty();
	 if(settingType == 1){
      $.fn.zTree.init($("#"+treeName), setting1, zRoleListNodes);
	 }else{
	      $.fn.zTree.init($("#"+treeName), setting, zRoleListNodes);
	 }
      var zTree = $.fn.zTree.getZTreeObj(treeName);
}

function showUserMenuZtree(zNodes,name,treeName,settingType){
	
	var zRoleListNodes = [];

	for (var i = 0; i < zNodes.length; i++) {
	    var nodeItem = new Object();
	    nodeItem.id = zNodes[i].id;
	    nodeItem.pId = zNodes[i].pId;
	    nodeItem.checked = zNodes[i].checked;
	    nodeItem.name = zNodes[i].name;
	    nodeItem.open = true;
	    zRoleListNodes.push(nodeItem);
	}

	$("#"+treeName).empty();
	if(settingType == 1){
		$.fn.zTree.init($("#"+treeName), setting1, zRoleListNodes);
	}else{
		$.fn.zTree.init($("#"+treeName), setting, zRoleListNodes);
	}
    var zTree = $.fn.zTree.getZTreeObj(treeName);
}

$(document).ready(function(){
	
	ffzx.ui(['treetable', 'ztree', 'dialog'], function(){	
		var option = {
				theme : 'vsStyle',
				expandLevel : 6
			};
		$('#newMenuTreeTable').treeTable(option);
	});
});