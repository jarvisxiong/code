//选择角色
function showAllrole() {
    $.frontEngineAjax.executeAjaxPost(
    	rootPath + "/coupon/toCouponCategory.do",
    	"type=0",
        function(ret) {
    		console.log(ret);
            var zNodes = ret;
            var content = '<div class="zTreeDemoBackground left" style="width: 250px; height: 400px;"><ul id="category_tree" class="ztree"></ul></div>';
          
            $.frontEngineDialog.executeDialog(
                  'selrole',
                  '选择商品类目',
                  content,
                  "250px",
                  "400px",
                  function(){
                      var zTree = $.fn.zTree.getZTreeObj("role_tree");
                      var checkCountNodes = zTree.getCheckedNodes();
                      var roleIds = "";
                      // 这里需要过滤掉父节点
                      for (var i = 1; i < checkCountNodes.length; i++) {
                          roleIds += checkCountNodes[i].id + ",";
                      }
                  }
          );
            showRoleZtree(zNodes,"商品类目列表","category_tree");
      }
    );
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
	var zRoleListNodes = [ {
	    id : 0,
	    pId : 0,
	    name : name,
	    open : true
	} ];

	for (var i = 0; i < zNodes.length; i++) {
	    var nodeItem = new Object();
	    nodeItem.id = zNodes[i].id;
	    nodeItem.pId =zNodes[i].parentId;
	    nodeItem.checked = false;
	    nodeItem.name = zNodes[i].name;
	    zRoleListNodes.push(nodeItem);
	}

		$("#"+treeName).empty();
      $.fn.zTree.init($("#"+treeName), setting, zRoleListNodes);
      var zTree = $.fn.zTree.getZTreeObj(treeName);
}
