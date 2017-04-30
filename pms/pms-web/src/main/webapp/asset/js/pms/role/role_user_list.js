$(function(){
	
	ffzx.ui(['dialog', 'ztree'], function(){
		//批量删除按钮
		$("#assign_user_to_role").click("click", function() {
			openAssignUserPage();
		});
		
		$("#assign_userGroup_to_role").click("click", function() {
			openAssignUserGroupPage();
		});	
	});
});
//分配用户
function openAssignUserPage() {
	var role_id = $('#current_role_id').val();
	var url = rootPath + '/role/assignUserToRole.do?id='+role_id;
	$.frontEngineDialog.executeIframeDialog('select_user_to_role', '分配用户', url, '800', '400');
}

//分配用户组
function openAssignUserGroupPage() {
	var role_id = $('#current_role_id').val();
	$.frontEngineAjax.executeAjaxPost(
        rootPath + "/role/assignUserGroupToRole.do",
        "id="+role_id,
        function(ret) {
            var zNodes = ret;
            var content = '<ul id="role_tree" class="ztree" style="margin-top:0;"></ul>';
          
            $.frontEngineDialog.executeDialog(
                  'selrole',
                  '选择用户组',
                  content,
                  "400px",
                  "450px",
                  function(){
                      var zTree = $.fn.zTree.getZTreeObj("role_tree");
                      var checkCountNodes = zTree.getCheckedNodes();
                      var userGroupIds = "";
                      // 这里需要过滤掉父节点
                      for (var i = 0; i < checkCountNodes.length; i++) {
                    	  userGroupIds += checkCountNodes[i].id + ",";
                      }
                      $.ajax({
                          url : rootPath + "/role/saveUserGroup.do",
                          data : {
                        	  userGroupIds : userGroupIds,
                        	  roleId : role_id
                          },// 给服务器的参数
                          type : "POST",
                          dataType : "json",
                          async : false,
                          cache : false,
                          success : function(result) {
                              if (result.status == 'success') {
                            	  var d = dialog({quickClose: true,content: '分配用户组成功'}).show();
                                  setTimeout(function(){
	                                	  d.close();
	                                	  window.location.href = rootPath + result.url;
                                	  },2000);
                              } else {
                                  dialog({quickClose: true,content: result.infoStr}).show();
                              }
                          }
                      });
                  }
          );
            showRoleZtree(zNodes,"用户组列表","role_tree");
      }
    );
}

var setting = {
	    check : {
	        enable : true,
	        chkboxType : {
	            "Y" : "",
	            "N" : ""
	        }
	    },
	    data : {
	        simpleData : {
	            enable : true
	        }
	    }
	};

function showRoleZtree(zNodes,name,treeName){

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
      $.fn.zTree.init($("#"+treeName), setting, zRoleListNodes);
      var zTree = $.fn.zTree.getZTreeObj(treeName);
}