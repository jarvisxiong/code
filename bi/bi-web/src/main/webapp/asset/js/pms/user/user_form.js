
    var tree_setting = {
    	    check : {enable : true,chkboxType : {"Y" : "ps","N" : "ps"}},
    	    data : {simpleData : {enable : true}},
    	    view: {showIcon: false,showLine: false}
    	};
    var tree_setting1 = {
            check : {enable : true,chkboxType : {"Y" : "","N" : ""}},
            data : {simpleData : {enable : true}},
            view: {showIcon: false,showLine: false}
        };
	//加载树形
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
		   $.fn.zTree.init($("#"+treeName), tree_setting1, zRoleListNodes);
			}else{
				  $.fn.zTree.init($("#"+treeName), tree_setting, zRoleListNodes);
			}
    	}
		
    $(function() {
        //加载角色树形
        showRoleZtree(roleNodes,"选择角色","left_role_tree",0);
        showRoleZtree(userGroupNodes,"选择用户组","left_userGroup_tree",1);
    });

  //设置选择的roleIds
  function loadIds(){
      var zTree = $.fn.zTree.getZTreeObj("left_role_tree");
      var checkCountNodes = zTree.getCheckedNodes();
      var roleIds = "";
      // 这里需要过滤掉父节点
      for (var i = 0; i < checkCountNodes.length; i++) {
          roleIds += checkCountNodes[i].id + ",";
      }
      $("#roleIds").val(roleIds);
      
      var zTree1 = $.fn.zTree.getZTreeObj("left_userGroup_tree");
      var checkCountNodes1 = zTree1.getCheckedNodes();
      var userGroupIds = "";
      // 这里需要过滤掉父节点
      for (var i = 0; i < checkCountNodes1.length; i++) {
    	  userGroupIds += checkCountNodes1[i].id + ",";
      }
      $("#userGroupIds").val(userGroupIds);
      return true;
  }

$(function() {
	$("#name").click(function() {
		var url=rootPath+"/user/popDetails.do";
		var width =  window.screen.width*0.4;
		var height = window.screen.height*0.4;
		var dlg = dialog({
			id:'popDetails',
			title: '职员搜索',
	        resize: false,
	        drag: false,
	        lock: true,
	        content:"<iframe  id='spframe' name='spframe,"+window.location.href+"' src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0' ></iframe>",
		    button: [
		        {
		            value: '确定',
		            callback: function () {
		    			document.getElementById("spframe").contentWindow.selectCheck(dlg);
		    			return false;
		            }
		        },
		        {
		            value: '关闭',
		            callback: function () {
		            }
		        }
		    ]
		}).showModal();
	});
});
	
//弹窗页面返回结果 操作
function afterSelectedEmployee(obj,dlg) {
	dlg.click();
	//设置返回值存放位置
	var $dataId = $(obj).attr("id");
	var $phone = $(obj).attr("phone");
	$("#name").val($(obj).attr("name"));
	$("#workNo").val($(obj).attr("workNo"));
	
	$("#companyName").val($(obj).attr("company"));
	$("#companyId").val($(obj).attr("companyId"));
	
	$("#officeName").val($(obj).attr("office"));
	$("#officeId").val($(obj).attr("officeId"));
	
	$("#mobile").val($(obj).attr("phone"));
	$("#email").val($(obj).attr("email"));
}
