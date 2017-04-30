
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
	//加载地址树形
	function showAddressZtree(zNodes,treeName,settingType){
		
		var zRoleListNodes = [];

		for (var i = 0; i < zNodes.length; i++) {
			var nodeItem = new Object();
			nodeItem.id = zNodes[i].id;
			nodeItem.pId = zNodes[i].pId;
			nodeItem.checked = zNodes[i].checked;
			nodeItem.name = zNodes[i].name;
			nodeItem.addressCode = zNodes[i].addressCode;
			nodeItem.chkDisabled = zNodes[i].chkDisabled;
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
	//加载仓库树形
	function showWarehouseZtree(zNodes,treeName,settingType){
		
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
			$.fn.zTree.init($("#"+treeName), tree_setting1, zRoleListNodes);
		}else{
			$.fn.zTree.init($("#"+treeName), tree_setting, zRoleListNodes);
		}
    }
	//加载菜单树形
	function showMenuZtree(zNodes,treeName,settingType){
		
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
			$.fn.zTree.init($("#"+treeName), tree_setting1, zRoleListNodes);
		}else{
			$.fn.zTree.init($("#"+treeName), tree_setting, zRoleListNodes);
		}
    }
		
    $(function() {

    	ffzx.ui(['ztree'], function(){
	        showRoleZtree(roleNodes,"选择角色","left_role_tree",0);
	        showRoleZtree(userGroupNodes,"选择用户组","left_userGroup_tree",0);
	        showRoleZtree(OfficeNodes,"选择公司","left_office_tree",0);
	        showAddressZtree(addressNodes,"left_address_tree",0);
	        showWarehouseZtree(warehouseNodes,"left_warehouse_tree",1);
	        showMenuZtree(menuNodes,"left_menu_tree",0);
    	});
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
      
      //关联公司
      var zTree2 = $.fn.zTree.getZTreeObj("left_office_tree");
      var checkCountNodes2 = zTree2.getCheckedNodes();
      var userOfficeIds = "";
      // 这里需要过滤掉父节点
      for (var i = 0; i < checkCountNodes2.length; i++) {
    	  if(checkCountNodes2[i].id != '0'){
    		  userOfficeIds += checkCountNodes2[i].id + ",";
    	  }
      }
      $("#userOfficeIds").val(userOfficeIds);
      
      //关联地址
      var zTree3 = $.fn.zTree.getZTreeObj("left_address_tree");
      var checkCountNodes3 = zTree3.getCheckedNodes();
      var userAddressIds = "";
      // 这里需要过滤掉父节点
      for (var i = 0; i < checkCountNodes3.length; i++) {
    	  userAddressIds += checkCountNodes3[i].id + "_" + checkCountNodes3[i].addressCode + ",";
      }
      $("#userAddressIds").val(userAddressIds);
      
      //关联仓库
      var zTree4 = $.fn.zTree.getZTreeObj("left_warehouse_tree");
      var checkCountNodes4 = zTree4.getCheckedNodes();
      var userWarehouseIds = "";
      // 这里需要过滤掉父节点
      for (var i = 0; i < checkCountNodes4.length; i++) {
    	  userWarehouseIds += checkCountNodes4[i].id + ",";
      }
      $("#userWarehouseIds").val(userWarehouseIds);
      
      //关联菜单
      var zTree5 = $.fn.zTree.getZTreeObj("left_menu_tree");
      var checkCountNodes5 = zTree5.getCheckedNodes();
      var userMenuIds = "";
      // 这里需要过滤掉父节点
      for (var i = 0; i < checkCountNodes5.length; i++) {
    	  userMenuIds += checkCountNodes5[i].id + ",";
      }
      $("#userMenuIds").val(userMenuIds);
      return true;
  }

$(function() {
	$("#name").click(function() {
		var url=rootPath+"/user/popDetails.do";
		var width =  1000;
		var height = 560;
		var dlg = dialog({
			id:'popDetails',
			title: '职员搜索',
	        resize: false,
	        drag: false,
	        lock: true,
	        content:"<iframe  id='spframe' name='spframe,"+window.location.href+"' src='"+url+"' style='width:"+width+"px;height:"+height+"px;' frameborder='0' ></iframe>",
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

	// SSUI: 将当前行的数据填充到表单
	
	$("#name").val(obj.name);
	$("#workNo").val(obj.code);
	
	$("#companyName").val(obj.company.name);
	$("#companyId").val(obj.company.id);
	
	$("#officeName").val(obj.office.name);
	$("#officeId").val(obj.office.id);
	
	$("#mobile").val(obj.phone);
	$("#email").val(obj.email);	
}

//选择供应商
$(function() {
	$("#vendorName").click(function() {
		var url=rootPath+"/user/vendorDetails.do";
		var dlg = dialog({
			id:'vendorDetails',
			title: '供应商搜索',
	        resize: false,
	        drag: false,
	        lock: true,
	        content:"<iframe  id='spframe' name='spframe,"+window.location.href+"' src='"+url+"' style='width:1000px;height:560px;' frameborder='0' ></iframe>",
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
//供应商弹窗页面返回结果 操作
function afterSelectedVendor(obj,dlg) {
	dlg.click();
	//设置返回值存放位置

	// SSUI: 将当前行的数据填充到表单
	
	$("#vendorName").val(obj.name);
	$("#vendorId").val(obj.id);
	$("#vendorCode").val(obj.code);
}

function changeAllOfficeFlag(allOfficeFlag){
	if(allOfficeFlag == '0'){
		$("#left_office_tree").show();
	}else{
		$("#left_office_tree").hide(); 
	}
}

function changeAllAddressFlag(allAddressFlag){
	if(allAddressFlag == '0'){
		$("#left_address_tree").show();
	}else{
		$("#left_address_tree").hide(); 
	}
}

function changeAllWarehouseFlag(allWarehouseFlag){
	if(allWarehouseFlag == '0'){
		$("#left_warehouse_tree").show();
	}else{
		$("#left_warehouse_tree").hide(); 
	}
}