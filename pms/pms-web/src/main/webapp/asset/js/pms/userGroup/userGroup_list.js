//选择角色
function showAllrole(id) {
	
	ffzx.ui(['dialog', 'ztree'], function(){
	    $.frontEngineAjax.executeAjaxPost(
	        rootPath + "/userGroup/assignRoleToUserGroupForm.do",
	        "userGroupId="+id,
	        function(ret) {
	            var zNodes = ret;
	            var content = '<div class="zTreeDemoBackground left" style="width:300px; height: 400px;overflow: auto;"><ul id="role_tree" class="ztree"></ul></div>';
	                      
	
		            $.frontEngineDialog.executeDialog(
		                  'selrole',
		                  '选择角色',
		                  content,
		                  "300px",
		                  "400px",
		                  function(){
		                	  
		                	 
			                      var zTree = $.fn.zTree.getZTreeObj("role_tree");
			                      var checkCountNodes = zTree.getCheckedNodes();
			                      var roleIds = "";
			                      // 这里需要过滤掉父节点
			                      for (var i = 1; i < checkCountNodes.length; i++) {
			                          roleIds += checkCountNodes[i].id + ",";
			                      }
			                      $.ajax({
			                          url : rootPath + "/userGroup/assignRoleToUserGroup.do",
			                          data : {
			                        	  roleIds : roleIds,
			                        	  id : id
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
	
	            showRoleZtree(zNodes,"角色列表","role_tree");
	      }
	    );
	});
}
var setting = {
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
	    }
	};

function showRoleZtree(zNodes,name,treeName){	

	var zRoleListNodes = [{
	    id : 0,
	    pId : 0,
	    name : name,
	    open : true
	}];

	for (var i = 0; i < zNodes.length; i++) {
	    var nodeItem = new Object();
	    nodeItem.id = zNodes[i].id;
	    nodeItem.pId = 0;
	    nodeItem.checked = zNodes[i].checked;
	    nodeItem.name = zNodes[i].name;
	    zRoleListNodes.push(nodeItem);
	}

	$("#"+treeName).empty();
		
	ffzx.ui(['ztree'], function(){
	    $.fn.zTree.init($("#"+treeName), setting, zRoleListNodes);
	    var zTree = $.fn.zTree.getZTreeObj(treeName);      
	});	
}

function isdelete(id,name) {
	
	ffzx.ui(['dialog'], function(){
	    $.frontEngineDialog.executeDialog(
	            'isdelete', 
	            '删除', 
	            '是否确定删除“'+name+'”？', 
	            "250px", 
	            "35px", 
	            function() {
	        $.ajax({
	            url : rootPath + "/userGroup/delete.do",
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
	                    setTimeout('window.location.href="' + rootPath+ '/userGroup/list.do"', 1000);
	                } else {
	                    dialog({
	                        quickClose : true,
	                        content : result.msg
	                    }).show();
	                }
	            }
	        });
	    });
	});
}

var userGroupId;
var officeId = null;
//选择用户弹窗
function showAllUser(id) {
	userGroupId = id;
	
	ffzx.ui(['dialog', 'ztree'], function() {
	    $.frontEngineAjax.executeAjaxPost(
	        rootPath + "/userGroup/assignUserForm.do",
	        "userGroupId="+id,
	        function(ret) {
	            var zNodes = ret;
	            var content = '<div style="height: 450px;padding:0;">'+
	            		'<div class="zTreeDemoBackground left col-md-4" style="height:100%;overflow:auto;border-right:0;">'+
	            		'<ul id="left_office_tree" class="ztree" style="overflow:visible;"></ul>'+
	            		'<input type="button" class="btn btn-default hide" value="选择机构" style="disabled: disabled;margin-top:15px;">'+ 
	            		'</div>'+
	            		'<div class="zTreeDemoBackground left col-md-3" style="height:100%;overflow:auto;">'+
		            		'<ul id="left_user1_tree" class="ztree"></ul>'+
	            		'</div>'+
	            		'<div class="zTreeDemoBackground left col-md-1" style="height:100%;width: 55px;padding-right: 0px;padding-left: 0px;padding-top: 25%;text-align:center;border-width:1px 0;">'+
	            			'<input type="button" class="btn btn-default" value="&nbsp;>&nbsp;" onclick="assignUserAdd()" style="padding: 5px;margin-bottom: 10px;">'+
	            			'<br /><input type="button" class="btn btn-default" value="&nbsp;<&nbsp;" onclick="assignUserRemove()" style="padding: 5px;">'+
	            		'</div>'+
	            		'<div class="zTreeDemoBackground left col-md-3" style="height:100%;overflow:auto;">'+
	            			'<ul id="left_user2_tree" class="ztree"></ul>'+
	            		'</div>'+
	            		'</div>';
	          

	            $.frontEngineDialog.executeDialog(
	                  'selUser',
	                  '选择用户',
	                  content,
	                  "800px",
	                  "450px",
	                  function(){
	                    var d = dialog({quickClose: true,content: '分配用户成功'}).show();
	                    setTimeout(function(){d.close();},1000);
	                  }
	            );

	            showAllZtree(zNodes);
	        }
	    );
	});
}

//加载机构
function showOfficeZtree(zNodes,name,treeName){
	var Office_setting = {
			data : {
				simpleData : {
					enable : true
				},
				key:{
					url:""
				}
			},
			view:{
				selectedMulti: false
			},
			callback : {
				beforeClick : OfficeClick
			}
		};
	var zRoleListNodes = [];

	for (var i = 0; i < zNodes.length; i++) {
	    var nodeItem = new Object();
	    nodeItem.id = zNodes[i].id;
	    nodeItem.pId = zNodes[i].pId;
	    nodeItem.name = zNodes[i].name;
	    nodeItem.open = true;
	    zRoleListNodes.push(nodeItem);
	}

	  $("#"+treeName).empty();
	  
	  ffzx.ui(['ztree'], function(){
	      $.fn.zTree.init($("#"+treeName), Office_setting, zRoleListNodes);
	      var zTree = $.fn.zTree.getZTreeObj(treeName);
	  });
}
//显示分配用户
function showAllZtree(zNodes){
	showOfficeZtree($.parseJSON(zNodes.office),"机构列表","left_office_tree");
	var users = $.parseJSON(zNodes.user);
	var userOk = [];
	var userNo = [];
	for (var i = 0; i < users.length; i++) {
	    var nodeItem = new Object();
		nodeItem.id = users[i].id; 
		nodeItem.name = users[i].name; 
		nodeItem.checked = false;
		if(users[i].checked){ 
			userOk.push(nodeItem);
		}else{
			userNo.push(nodeItem);
		}
	}
	showRoleZtree(userNo,"未选用户","left_user1_tree");
	showRoleZtree(userOk,"已选用户","left_user2_tree");
}

//点击机构
function OfficeClick(treeId, treeNode){
	officeId = treeNode.id;
	editZtree();
}
function editZtree(){
	 $.frontEngineAjax.executeAjaxPost(
		        rootPath + "/userGroup/assignUserForm.do",
		        "userGroupId="+userGroupId+"&officeId="+officeId,
		        function(ret) {
		            var zNodes = ret;
		            showAllZtree(zNodes);
		        }
	);
}
//添加分配的用户
function assignUserAdd(){
	  var zTree = $.fn.zTree.getZTreeObj("left_user1_tree");
      var checkCountNodes = zTree.getCheckedNodes();
      var userIds = "";
      // 这里需要过滤掉父节点
      for (var i = 1; i < checkCountNodes.length; i++) {
    	  userIds += checkCountNodes[i].id + ",";
      }

      $.frontEngineAjax.executeAjaxPost(
          rootPath + "/userGroup/assignUserAdd.do",
          "id="+userGroupId + "&userIds=" + userIds,
          function(ret) {
        	  editZtree();
     	 });
}
//移除分配的用户
function assignUserRemove(){
	
	ffzx.ui(['ztree'], function(){

	  var zTree = $.fn.zTree.getZTreeObj("left_user2_tree");
      var checkCountNodes = zTree.getCheckedNodes();
      var userIds = "";
      // 这里需要过滤掉父节点
      for (var i = 1; i < checkCountNodes.length; i++) {
    	  userIds += checkCountNodes[i].id + ",";
      }

      $.frontEngineAjax.executeAjaxPost(
          rootPath + "/userGroup/assignUserRemove.do",
          "id="+userGroupId + "&userIds=" + userIds,
          function(ret) {
        	  editZtree();
      });
	});	
}

$(document).ready(function(){
	
	ffzx.ui(['treetable'], function(){
		
		$('#TreeTable').treeTable({
			theme : 'vsStyle',
			expandLevel : 6
		});
	});	
});