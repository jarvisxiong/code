var dataTree = [];//树形菜单
var parentId = "";
var tableId = "";
var tree_setting = {
	data : {
		simpleData : {
			enable : true
		},
		key : {
			url : ""
		}
	},
	view : {
		selectedMulti : false
	},
	callback : {
		beforeClick : beforeClick
	}
};
$(function() {
	$.fn.zTree.init($("#left_tree"), tree_setting, zNodes);
	var option = {
		theme : 'vsStyle',
		expandLevel : 6
	};
	$('#TreeTable').treeTable(option);
});

/**
 * openClose 显示和隐藏树形栏
 */
$("#openClose").click(function () {
    if ($(this).hasClass("closeac")) {
        $(this).removeClass("closeac");
        $(this).addClass("open");
        
        $(".zTreeDemoBackground .ztree").addClass("disalyact");
        $(".box-body").addClass("disalyact");
        $(".zTreeDemoBackground").addClass("disalyact");
      
    } else {
        $(this).addClass("closeac");
        $(this).removeClass("open");
        
        
        $(".zTreeDemoBackground .ztree").removeClass("disalyact");
        $(".box-body").removeClass("disalyact");
        $(".zTreeDemoBackground").removeClass("disalyact");
       
    }
});

//左侧树形单击
function beforeClick(treeId, treeNode) {
	window.location.href = rootPath+"/office/list.do?id="+treeNode.id;
	return true;
}
