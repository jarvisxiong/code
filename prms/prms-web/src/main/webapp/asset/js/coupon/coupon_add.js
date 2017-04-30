var buttonType;
var goodsId=[];
var goodsName=[];
var activityIds=[];
var activitytitles=[];
var categoryId=[];
var categoryName=[];
 


function checkGoods(){
	$("#tableValue").empty();	
	var goodsSelect=$('input[name="goodsSelect"]:checked').val();
	if(goodsSelect=="1"){	
		cleantcategoryId();
		cleantgoods();
		$("#button_activity").show();
		$("#button_goods").hide();
		$("#button_category").hide();
		if(activitytitles.length>0){
			$("#goodsForm").show();
		}else{
			$("#goodsForm").hide();
		}
	}
	else if(goodsSelect=="2"){	//商品	
		cleantactivityIds();
		cleantcategoryId();
		$("#button_goods").show();
		$("#button_category").hide();
		$("#button_activity").hide();
		buttonType=0;
		if(goodsId.length>0){
			$("#goodsForm").show();
		}else{
			$("#goodsForm").hide();
		}
	}else if(goodsSelect=="3"){//类目

		cleantactivityIds();
		cleantgoods();
		$("#button_goods").hide();
		$("#button_category").show();
		$("#button_activity").hide();
		buttonType=1;
		if(categoryId.length>0){
			$("#goodsForm").show();
		}else{
			$("#goodsForm").hide();
		}
	}else if(goodsSelect=="0"){
		$("#goodsForm").hide();
		cleantactivityIds();
		cleantcategoryId();
		cleantgoods();
		$("#button_goods").hide();
		$("#button_category").hide();
	}
}

function cleantactivityIds(){

	activityIds=[];
	activitytitles=[]

	$("#activityManageIds").val("");
	$("#activitytitles").val("");
}
function cleantcategoryId(){
	categoryId=[];
	categoryName=[];
	$("#categoryId").val("");
}
function cleantgoods(){

	goodsId=[];
	goodsName=[];
	$("#goodsId").val("");
}

/*****
 * 弹窗
 */
function selectGoods(value,flag){
	if(value!=null && value!=""){		
		var gn=$("#goodsName").val().split(",");
		if(gn!=null && gn!=""){
			goodsName=$("#goodsName").val().split(",");
		}
		goodsId=$("#goodsId").val().split(",");
	}
		var url = rootPath + '/coupon/toSelectGoods.do?goodsId='+goodsId;		
		var dlg=dialog({
            id: "couponFrom",
            title: '选择商品',
            lock: false,
            content:"<iframe id='couponFrom' name='couponFrom,"+window.location.href+"' src='"+url+"' width='1000px' height='610px' frameborder='0' scrolling='auto' id='couponFrom' ></iframe>",
			button: [
			         {
			             value: '确定',
			             callback: function () {
			            	 showGoodsTable(document.getElementById("couponFrom").contentWindow.getCheckedName(),document.getElementById("couponFrom").contentWindow.getChecked()); 
			             },
			             focus: true
			         },{
			             value: '取消',
			             callback: function () {
			             },
			             focus: true
			         }
			     ]
	        }).showModal();
}


/*****
 * 弹窗
 */
function selectActivity(flag){
	
	var activityManageIds=$("#activityManageIds").val();
	var activitytitlesvalue=$("#activitytitles").val();
	console.log("t");
	if(view=="edit"){
		if(activityManageIds!=null && activityManageIds!=""){
			activityIds=activityManageIds.split(",");
			activitytitles=activitytitlesvalue.split(",");
		}
	}
	var url = rootPath + '/coupon/toSelectptActivity.do?activityManageIds='+activityManageIds;		
	var dlg=dialog({
        id: "couponFrom",
        title: '选择活动',
        lock: false,
        content:"<iframe id='couponFrom' name='couponFrom,"+window.location.href+"' src='"+url+"' width='1000px' height='610px' frameborder='0' scrolling='auto' id='couponFrom' ></iframe>",
		button: [
		         {
		             value: '确定',
		             callback: function () {
		            	 showActivityTable(document.getElementById("couponFrom").contentWindow.getCheckedName(),document.getElementById("couponFrom").contentWindow.getChecked()); 
		             },
		             focus: true
		         },{
		             value: '取消',
		             callback: function () {
		             },
		             focus: true
		         }
		     ]
        }).showModal();
}

function showActivityTable(name,id){
	$("#goodsForm").show();
	var tdStr="";
	if(activityIds.length==0){
		activityIds=id;
		activitytitles=name;
	}else{
		$.merge(activitytitles,name);
		$.merge(activityIds,id);
	}		
	for(var i=0;i<activitytitles.length;i++){
		tdStr+='<tr id="tr'+activityIds[i]+'">'
			+'<td>'+activitytitles[i]+'</td>'						
			+'<td><a href="javascript:void(0);" onclick="deleteActivityTr(\''+activityIds[i]+'\')">删除</a></td>	'		 	
			+'</tr>';
	}
	var showStr='<thead><tr>'
				+'<th width="150px;">活动名称</th>'
				+'<th width="50px;">操作</th>'
				+'</tr>'
				+'</thead>'
				+'<tbody>'	 	
				+tdStr
				+'</tbody>'
	$("#tableValue").html(showStr);
	$("#activityManageIds").val(activityIds);

	$("#activitytitles").val(activitytitles);
}
function showGoodsTable(name,id){
	$("#goodsForm").show();
	var tdStr="";
	if(goodsName.length==0){
		goodsName=name;
		goodsId=id;
	}else{
		$.merge(goodsName,name);
		$.merge(goodsId,id);
	}		
	for(var i=0;i<goodsName.length;i++){
		tdStr+='<tr id="tr'+goodsId[i]+'">'
			+'<td>'+goodsName[i]+'</td>'						
			+'<td><a href="javascript:void(0);" onclick="deleteTr(\''+goodsId[i]+'\')">删除</a></td>	'		 	
			+'</tr>';
	}
	var showStr='<thead><tr>'
				+'<th width="150px;">商品名称</th>'
				+'<th width="50px;">操作</th>'
				+'</tr>'
				+'</thead>'
				+'<tbody>'	 	
				+tdStr
				+'</tbody>'
	$("#tableValue").html(showStr);
	$("#goodsId").val(goodsId);
	$("#goodsName").val(goodsName);
}

//删除已经选中的值
function deleteTr(value){
	$("#tr"+value+"").remove();
	goodsId=$("#goodsId").val().split(",");
	goodsName=$("#goodsName").val().split(",");
	for(var i=0;i<goodsId.length;i++){
		if(value==goodsId[i]){
			goodsId.splice(i,1);
			goodsName.splice(i,1);
		}
	}	
	$("#goodsId").val(goodsId);
	$("#goodsName").val(goodsName);
}

//删除已经选中的值
function deleteActivityTr(value){
	$("#tr"+value+"").remove();
	activityIds=$("#activityManageIds").val().split(",");
	activitytitles=$("#activitytitles").val().split(",");
	for(var i=0;i<activityIds.length;i++){
		if(value==activityIds[i]){
			activityIds.splice(i,1);
			activitytitles.splice(i,1);
		}
	}	
	$("#activityManageIds").val(activityIds);
	$("#activitytitles").val(activitytitles);
}
function setConsumpValue(value){
	$("#consumptionLimit").val(value);
}
//选择类目
function showAllrole() {
    $.frontEngineAjax.executeAjaxPost(
    	rootPath + "/coupon/toCouponCategory.do",
    	"categoryId="+$("#categoryId").val(),
        function(ret) {
            var zNodes = ret;
            var content = '<div class="zTreeDemoBackground left" style="width: 250px; height: 400px;overflow: scroll;" ><ul id="category_tree" class="ztree"></ul></div>';
          
            $.frontEngineDialog.executeDialog(
                  'selrole',
                  '选择商品类目',
                  content,
                  "250px",
                  "400px",
                  function(){
                      var zTree = $.fn.zTree.getZTreeObj("category_tree");
                      var checkCountNodes = zTree.getCheckedNodes();
                      var categoryIds = [];
                      var categoryName=[];
                      // 这里需要过滤掉父节点
                      for (var i = 0; i < checkCountNodes.length; i++) {
                    	  if(checkCountNodes[i].id!=0){
                    		  categoryIds.push(checkCountNodes[i].id);
                        	  categoryName.push(checkCountNodes[i].name );
                    	  }                	 
                      }
                      
                      showCategoryTable(categoryName,categoryIds);
                  }
          );
            showRoleZtree(zNodes,"商品类目列表","category_tree");
      }
    );
}
/*
	 Y 属性定义 checkbox 被勾选后的情况；
	N 属性定义 checkbox 取消勾选后的情况；
	"p" 表示操作会影响父级节点；
	"s" 表示操作会影响子级节点。
*/
var setting = {
	    check : {
	        enable : true,
	        chkboxType : {
	            "Y" : "s",
	            "N" : "s"
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
	    nodeItem.checked = zNodes[i].checeked;
	    nodeItem.name = zNodes[i].name;
	    nodeItem.open=true;
	    zRoleListNodes.push(nodeItem);
	}

		$("#"+treeName).empty();
      $.fn.zTree.init($("#"+treeName), setting, zRoleListNodes);
      var zTree = $.fn.zTree.getZTreeObj(treeName);
}

function showCategoryTable(name,id){
	$("#goodsForm").show();
	var tdStr="";
	if(categoryName.length==0){
		categoryName=name;
		categoryId=id;
	}else{		
		$.merge(categoryName,name);
		$.merge(categoryId,id);
	}		
	categoryName=uniqueArray(categoryName);
	categoryId=uniqueArray(categoryId);
	for(var i=0;i<categoryName.length;i++){
		tdStr+='<tr id="tr'+categoryId[i]+'">'
			+'<td>'+categoryName[i]+'</td>'						
			+'<td><a href="javascript:void(0);" onclick="deleteCategoryTr(\''+categoryId[i]+'\')">删除</a></td>	'		 	
			+'</tr>';
	}
	var showStr='<thead><tr>'
				+'<th width="150px;">商品类目</th>'
				+'<th width="50px;">操作</th>'
				+'</tr>'
				+'</thead>'
				+'<tbody>'	 	
				+tdStr
				+'</tbody>'
	$("#tableValue").html(showStr);
	$("#categoryId").val(categoryId);
}
function uniqueArray(a){
    temp = new Array();
    for(var i = 0; i < a.length; i ++){
        if(!contains(temp, a[i])){
            temp.length+=1;
            temp[temp.length-1] = a[i];
        }
    }
    return temp;
}
function contains(a, e){
    for(j=0;j<a.length;j++)if(a[j]==e)return true;
    return false;
}
//删除已经选中的值
function deleteCategoryTr(value){
	$("#tr"+value+"").remove();
	categoryId=$("#categoryId").val().split(",");
	for(var i=0;i<categoryId.length;i++){
		if(value==categoryId[i]){
			categoryId.splice(i,1);
			categoryName.splice(i,1);
		}
	}	
	$("#categoryId").val(categoryId);
}

function butonsubmit(){
	var ex = /^\d+$/;
//	var consumptionLimit=$('input[name="consumptionLimit"]:checked').val(); 
//	if(consumptionLimit!="-1"){	
		var limit=$("#consumptionLimit").val();
		if(limit=="" || limit==null){
			$.frontEngineDialog.executeDialogOK('提示信息','满多少元不能为空！','300px');
			return false;
		}
//		else if(!ex.test(limit) || parseInt(limit)<=0){
//			$.frontEngineDialog.executeDialogOK('提示信息','满多少元只能是大于0的整数！','300px');
//			return false;
//		}
//		修改能支持输入小数  ying.cai
		else if( !(/^[-\+]?\d+$/.test(limit) || /^[-\+]?\d+(\.\d+)?$/.test(limit)) ){
			$.frontEngineDialog.executeDialogOK('提示信息','满多少元只能是大于0的整数或者小数！','300px');
			return false;
		}
		// 满多少元只能存在两位小数
		if(!(/^\d+(\.\d{1,2})?$/.test(limit))){
			$.frontEngineDialog.executeDialogOK('提示信息','满多少元只能是大于0的整数或有两位小数的值！','300px');
			return false;
		}
//	}else{
//		$("#limit").val("");
//	}
	var faceValue = $("#faceValue").val();
	if(faceValue&&faceValue.length>0 && parseFloat(faceValue) >= parseFloat(limit) ){
		$.frontEngineDialog.executeDialogOK('提示信息','满多少元需大于优惠券面值！','300px');
		return false;
	}
	var effectiveDateState=$('input[name="effectiveDateState"]:checked').val(); 
	if(effectiveDateState=="1"){
		var effectiveDateStartStr=$("#effectiveDateStartStr").val();
		var effectiveDateEndStr=$("#effectiveDateEndStr").val();
		if(effectiveDateStartStr=="" || effectiveDateStartStr==null){
			$.frontEngineDialog.executeDialogOK('提示信息','有效期开始日期不能为空！','300px');
			return false;
		}
		if(effectiveDateEndStr=="" || effectiveDateEndStr==null){
			$.frontEngineDialog.executeDialogOK('提示信息','有效期结束日期不能为空！','300px');
			return false;
		}
		if(Date.parse(effectiveDateEndStr.replace(/-/g, "/")) <=Date.parse(effectiveDateStartStr.replace(/-/g, "/"))){
			$.frontEngineDialog.executeDialogOK('提示信息','结束时间不能小于开始时间！','300px');
			return false;
		}
	}else if(effectiveDateState=="0"){
		var effectiveDateNum=$("#effectiveDateNum").val();
		if(effectiveDateNum=="" || effectiveDateNum==null){
			$.frontEngineDialog.executeDialogOK('提示信息','自定义有效期天数不能为空！','300px');
			return false;
		}else if(!ex.test(effectiveDateNum)){
			$.frontEngineDialog.executeDialogOK('提示信息','天数只能是整数！','300px');
			return false;
		}else if(parseInt(effectiveDateNum)<=0){
			$.frontEngineDialog.executeDialogOK('提示信息','多少天有效必须大于0！','300px');
			return false;
		}
	}
	var goodsSelect=$('input[name="goodsSelect"]:checked').val(); 
	if(goodsSelect=="2"){
		var goods=$("#goodsId").val();
		if(goods==null || goods==""){
			$.frontEngineDialog.executeDialogOK('提示信息','指定商品，商品不能为空！','300px');
			return false;
		}
	}else if(goodsSelect=="3"){
		var category=$("#categoryId").val();
		if(category==null || category==""){
			$.frontEngineDialog.executeDialogOK('提示信息','指定商品类目，商品类目不能为空！','300px');
			return false;
		}
	}
	var remarks=$("#remarks").val();
	if(remarks.length>250){
		$.frontEngineDialog.executeDialogOK('提示信息','备注不能超过250个字！','300px');
		return false;
	}
	$("#myform").submit();
}