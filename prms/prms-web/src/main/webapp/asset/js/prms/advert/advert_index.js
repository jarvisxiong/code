
$(function() {	
	//初始化数树形
	var tree = $.fn.zTree.init($("#left_menu_tree"), {
		async: {
			enable: false
		},
		callback:{
			onClick:function(event, treeId, treeNode){
				if(typeof(onTreeNodeClick) == "function"){
					onTreeNodeClick(event, treeId, treeNode);
				}
			},
			onAsyncSuccess:function(event, treeId, node, msg){
				if(typeof(onTreeAsyncSuccess) == "function"){
					onTreeAsyncSuccess(event, treeId, node, msg);
				}
			}
		},
		data:{
			simpleData:{
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: null
			}
		}
	},zNodes);
	var nodes = tree.getNodes();
	if(nodes && nodes.length > 0){
		tree.expandNode(nodes[0], true, false, true);
		tree.selectNode(nodes[0]);
	}
	toRightTable();
});


function treeAdd(){
	var $tree_editUrl = rootPath+"/advert/advertRegion_edit.do";
	var node =getSelectedNodes();
	var paramStr = '';
	if(node!=null && node.length!=0){
		if(node[0].pIds!="0,"){
			 $.frontEngineDialog.executeDialogContentTime("请选择父级节点",2000);
			 return;
		}
		advertRegion_id=node[0].id;
		paramStr = '?advertRegion_id=' + node[0].id;
	}
	window.location.href = $tree_editUrl+paramStr;
}
function getSelectedNodes(){
	var tree = $.fn.zTree.getZTreeObj("left_menu_tree");
	return tree.getSelectedNodes();
}

function treeEdit(){
	var $tree_editUrl = rootPath+"/advert/advertRegion_edit.do";
	var node =getSelectedNodes();
	if(node==null || node.length==0){
		return;
	}
	paramStr = '?id=' + node[0].id;
	window.location.href = $tree_editUrl+paramStr;
}



function treeDel(){
	var width =  window.screen.width*0.6;
	var height = window.screen.height*0.5;
	var dlg = dialog({
		title: '提示',
        resize: false,
        drag: false,
        lock: true,
        content:"确定删除？",
	    button: [
	        {
	            value: '删除',
	            callback: function () {
	            	var $tree_delUrl = rootPath+"/advert/advertRegion_delete.do";
	            	var node =getSelectedNodes();
	            	if(node==null || node.length==0){
	            		return;
	            	}
	            	if(node[0].isParent == true){
	            		
	            		return;
	            	}
	            	$.post($tree_delUrl,{id:node[0].id},function(res){
	            		if(res.status=="success"){
	            			location.reload();
	            		}else{
	            			dlg.close();
	            			$.frontEngineDialog.executeDialogContentTime(res.infoStr,2000);
	            		}
	            		
	            	},'json');
	    			return false;
	            }
	        },
	        {
	            value: '以后再说',
	            callback: function () {
	            }
	        }
	    ]
	}).showModal();
}
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
function onTreeNodeClick(event, treeId, treeNode) {
	toRightTable();
	return true;
}
function toRightTable(){
	var node =getSelectedNodes();
	if(node[0].isParent == true){
		$("#myIframeT").attr("src",rootPath+"/advert/advert_list.do?parent_regionIds="+node[0].pIds+node[0].id);
	}else{
		$("#myIframeT").attr("src",rootPath+"/advert/advert_list.do?regionId="+node[0].id);
	}
}
