var officeTree;
		var selectedTree;//zTree已选择对象
		// 初始化
		$(document).ready(function(){
			officeTree = $.fn.zTree.init($("#officeTree"), setting, officeNodes);
			selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
		});
		
		var setting = {view: {selectedMulti:false,nameIsHTML:true,showTitle:false,dblClickExpand:false},
				data: {simpleData: {enable: true}},
				callback: {onClick: treeOnClick}};
		
		//点击选择项回调
		function treeOnClick(event, treeId, treeNode, clickFlag){
			$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
			if("officeTree"==treeId){
				$.get(rootPath + "/role/findUserByOffice.do?officeId=" + treeNode.id, function(userNodes){
					$.fn.zTree.init($("#userTree"), setting, userNodes);
				});
			}
			if("userTree"==treeId){
				//alert(treeNode.id + " | " + ids);
				//alert(typeof ids[0] + " | " +  typeof treeNode.id);
				if($.inArray(String(treeNode.id), ids)<0){
					selectedTree.addNodes(null, treeNode);
					ids.push(String(treeNode.id));
				}
			};
			if("selectedTree"==treeId){
				if($.inArray(String(treeNode.id), pre_ids)<0){
					selectedTree.removeNode(treeNode);
					ids.splice($.inArray(String(treeNode.id), ids), 1);
				}else{
					top.$.jBox.tip("角色原有成员不能清除！", 'info');
				}
			}
		};
	
		
		//var dialog = top.dialog.get(window);
			
		function submitRoleUser(){
			$("#userIds").val(ids);
			//var isok=$("#myform").submit();
			//console.log(top.dialog.get(window));
			var url=$('#myform').attr('action');
   	    	var type=$('#myform').attr('method');
   	    	common_doSave(url,type,'myform');
   	    	setTimeout(function() {
   	    					window.parent.location.reload();
			 				//$(parent.document.getElementsByClassName('ui-dialog-close')).click();
			 				//dialog.close("#mytest"); // 关闭（隐藏）对话框
							//dialog.remove();				 // 主动销毁对话框
						}, 1000);
		}
		//关闭
		function onClose() {
			$(parent.document.getElementsByClassName('ui-dialog-close')).click();
			return false;
		}