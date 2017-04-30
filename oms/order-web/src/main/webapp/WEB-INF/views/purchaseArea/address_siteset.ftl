<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="edit"/>
	<title>设置购买区域</title>
<style type="text/css">
	.xb{
		background-color: red;
	}
	.btn22{
	display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
    }
</style>	
</head>
<body>
 <ul class="nav nav-tabs" style="padding-left: 1%;">
      <li class="active"><a href="#">设置购买区域</a></li>
  </ul>

<div class="row">
       <div class="col-md-12">
         <div class="box-body">
           <div class="tab-content">
               <div class="tab-pane fade in active" id="addUser">

                   <!--新增1--start-->
                   <div class="row">
                       <div style="padding:0 0 10px 0;">
                           <form id="myform" action="${BasePath !}/purchaseArea/setArea.do" method="post">
                           <input type="hidden" value="${(commodityCode) !}" name="commodityCode" id="commodityCode">               
                           <div class="addForm1">
		                        <div id="error_con" class="tips-form">
		                            <ul></ul>
		                        </div>
                               <div class="tips-form"></div>
            <h3 class="title-h3">已设置可购买区域</h3>
			<hr style="border: 1px solid #ccc; width: 97%;"/>
            <div style=" width: 97%; padding:0 0 5px 10px; font-weight: bold;">商品已入库区域</div>
            <div class="area-in-storage" id="inAreaCheckAll" style=" width: 97%;">
            <dl>
           	 	 <#if stockWmsList?? >
           	 	 	<#list stockWmsList as item >
			          	<dd>
			          			<input name="wmsAddressCodes" 
			          				 <#if commodityAddressList?? > 
			          				 	<#list commodityAddressList as adList >
			          				 		<#if item.addressCode == adList.addressCode >
			          				 			checked="checked"
			          				 		<#else>
			          				 		
			          				 		</#if>
			          				 	</#list>
			          				 </#if>
			          			value="${(item.addressCode) !}" type="checkbox"> ${(item.addressName) !}
			          	</dd>
		          	</#list>
	          	</#if>
	            </dl><div class="clearfix"></div>
            <div class="clearfix"></div>
        </div>
		<br/>
		<div style=" width: 97%; padding:0 0 5px 10px; font-weight: bold;">其他可购买区域 </div>
        <div class="area-in-storage" style=" width: 97%;">
            
            <dl>
	            		<#if newCommodityAddressList?? >
	           	 	 		<#list newCommodityAddressList as item >
			          			<dd><input name="customUsedAddressCodes" checked="checked" value="${(item.addressCode) !}" type="checkbox"> ${(item.addressName) !}</dd>
           	 	 			</#list>
           	 	 		</#if>
	            </dl><div class="clearfix"></div>
            <div class="clearfix"></div>
        </div>
        
        <div style=" width: 97%; padding:0 0 5px 10px; font-weight: bold;">共享库存切换</div>
        <div class="area-in-storage" style=" width: 97%;">
            
            	<dl>
         			<dd>
         				<label class="radio-inline" style="width: auto;"><input name="isMany" type="radio" value="0" <#if (isMany) ??><#if isMany == '0'>checked="checked"</#if><#else>checked="checked"</#if>/>非共享</label>
                        <label class="radio-inline" style="width: auto;"><input name="isMany" type="radio" value="1" <#if (isMany) ??><#if isMany == '1'>checked="checked"</#if></#if>/>共享</label>
         			</dd>
	            </dl><div class="clearfix"></div>
            <div class="clearfix"></div>
        </div>

        <h3 class="title-h3">设置可购买区域</h3>
		<hr style="border: 1px solid #ccc; width: 97%;"/>
         <div class="area-in-storage area-in-storage2" id="checkx1">
	         <div class="title-can-purchased" id="check1"><input type="checkbox" /> 一级全选</div>
	         <dl>
	          	 <#if oneressList ??>
	                 <#list oneressList as item >  
	                 	<dd><input type="checkbox" id="number1" value="${(item.id) !}" />${(item.name) !}</dd>
	                 </#list>
	             </#if>   
	         </dl><div class="clearfix"></div>	
	     </div>
        <div class="area-in-storage area-in-storage2" id="checkx2">
          <div class="title-can-purchased" id="check2"><input type="checkbox" /> 二级全选</div>
	         <dl id="dl2">
<!-- 	          	 <#if tworessList ??> -->
<!-- 	                 	<#list tworessList as item >   -->
<%-- 	                 		<dd><input type="checkbox" onclick='cheerc2(this)' id="number2${item_index}" value="${(item.id) !}" />${(item.name) !}</dd> --%>
<!-- 	                 	</#list> -->
<!-- 	               </#if>    -->
	         </dl><div class="clearfix"></div>
        </div>
        <div class="area-in-storage area-in-storage2" id="checkx3">
           <div class="title-can-purchased" id="check3"><input type="checkbox" /> 三级全选</div>
	         <dl id="dl3">
<!-- 	          	 <#if addressList ??> -->
<!-- 	                 	<#list addressList as item >   -->
<%-- 	                 		<dd><input type="checkbox" onclick='cheerc3(this)' id="number3${item_index}" value="${(item.id) !}" />${(item.name) !}</dd> --%>
<!-- 	                 	</#list> -->
<!-- 	              </#if>    -->
	         </dl><div class="clearfix"></div>
       </div><div class="clearfix"></div>
	                </div>
	            </div> 
	            
                               <div class="form-tr" style="text-align: center;">
                                   <div class="form-group btn-div">
                                       <input type="submit" class="btn btn-primary" value="保存">
                                       <input type="button" class="btn btn-default" value="返回" onclick="history.go(-1);">
                                   </div>
                               </div>
                           </div>
                           
                           </form>
                       </div>
                   </div>
                   <!--新增1--end-->
                                      
               </div>
           </div>
         </div>
       </div>
   </div>
<script src="${BasePath !}/asset/js/common/IdCard.js?v=${ver !}" type="text/javascript"></script>
<link rel="stylesheet"  href="${BasePath}/asset/js/control/ztree/css/pageleft.css?v=${ver !}" type="text/css"> 
<script type="text/javascript">

$(function(){
    executeValidateFrom('myform');
});

//一级全选框  全选/反选
$("#check1 input[type='checkbox']").click("click", function() { 
 	var evt = arguments[0] || window.event;
 	var chkbox = evt.srcElement || evt.target; 
	var checkboxes = $("#checkx1 input[type='checkbox']");
	if (chkbox.checked) {
		checkboxes.prop('checked', true);
		selectone();
	} else {
		checkboxes.prop('checked', false);
		selectone();
	}
});

$("#inAreaCheck input[type='checkbox']").click("click", function() { 
 	var evt = arguments[0] || window.event;
 	var chkbox = evt.srcElement || evt.target; 
	var checkboxes = $("#inAreaCheckAll input[type='checkbox']");
	if (chkbox.checked) {
		checkboxes.prop('checked', true);
		selectone();
	} else {
		checkboxes.prop('checked', false);
		selectone();
	}
});

//一级   多选框 单击事件
$("#checkx1 dl dd input[type='checkbox']").click("click", function() { 
	selectone();
});
//一级   事件处理
function selectone(){
	var ids="";
	var number=$("#checkx1 input[type='checkbox']:checked");
	for (var i = 0; i < number.length; i++) {
		if(i==number.length-1){
			ids=ids+number[i].value;
		}else{
			ids=ids+number[i].value+",";
		}
	}
	if(ids == ""){ //当一级节点为空的时候清空二级和三级的节点
		$("#dl2").html("");  
		$("#dl3").html("");
		return;
	}
	$.ajax({
		url: rootPath+"/purchaseArea/twosetsite.do",
		type:'POST',
		data:{"ids":ids},
		dataType: 'json',
		success: function(data){		
			var ddStr = "";
			//显示新添加的
			var checkboxes = $("#checkx2 input[type='checkbox']");
			for(var o in data){  
				var result = true;
				for (var i = 0; i < checkboxes.length; i++) {
					if(checkboxes[i].value == data[o].id){
						result = false;						
					}
				}
				if(result){
					ddStr += "<dd><input type='checkbox' onclick='cheerc2(this)' id='number2"+data[o].id+"' value='"+data[o].id+"' />"+data[o].name+"</dd>";
				}
			}  
			//隐藏去除的
			for(var j = 0; j < checkboxes.length; j++){  
				var result1 = true;
				for (var o in data) {
					if(checkboxes[j].value == data[o].id){
						result1 = false;	
						break;
					}
				}
				if(result1){
					$("#"+checkboxes[j].id).parent().remove(); 
				}
			} 
			$("#dl2").append(ddStr); 
		}
	});
}
//二级全选框  全选/反选
$("#check2 input[type='checkbox']").click("click", function() { 
 	var evt = arguments[0] || window.event;
 	var chkbox = evt.srcElement || evt.target; 
	var checkboxes = $("#checkx2 input[type='checkbox']");
	if (chkbox.checked) {
		checkboxes.prop('checked', true);
		slecttwo();
	} else {
		checkboxes.prop('checked', false);
		slecttwo();
	}
});
//二级   多选框 单击事件  onclick
function cheerc2(address){
	slecttwo();
}
//二级   事件处理
function slecttwo(){
	var ids="";
	var number=$("#checkx2 input[type='checkbox']:checked");
	for (var i = 0; i < number.length; i++) {
		if(i==number.length-1){
			ids=ids+number[i].value;
		}else{
			ids=ids+number[i].value+",";
		}
	}
	if(ids == ""){
		$("#dl3").html("");
		return;
	}
	var commodityCode=$("#commodityCode").val();
	$.ajax({
		url: rootPath+"/purchaseArea/twosetsite.do",
		type:'POST',
		data:{"ids":ids,"commodityCode":commodityCode},
		dataType: 'json',
		success: function(data){		
			var ddStr = "";
			//显示新添加的
			var checkboxes = $("#checkx3 input[type='checkbox']");
			for(var o in data){  
				var result = true;
				for (var i = 0; i < checkboxes.length; i++) {
					if(checkboxes[i].value == data[o].id){
						result = false;						
					}
				}
				if(result){
					//如果仓库表查出来的结果等于 多选框查询出的结果（data[o].id） 就不在第三级添加数据/否则添加
					if((data[o].warehouseCode==null || data[o].warehouseCode=="" || typeof(data[o].warehouseCode)=="undefined")){
						
					}else{
						ddStr += "<dd><input type='checkbox' name='customAddressCodes' onclick='cheerc3(this)' id='number3"+data[o].id+"' value='"+data[o].addressCode+"' />"+data[o].name+"</dd>";
						
					}
				}
			}  
			//隐藏去除的
			for(var j = 0; j < checkboxes.length; j++){  
				var result1 = true;
				for (var o in data) {
					if(checkboxes[j].value == data[o].id){
						result1 = false;	
						break;
					}
				}
				if(result1){
					$("#"+checkboxes[j].id).parent().remove(); 
				}
			} 
			$("#dl3").append(ddStr); 
		}
	});
}

//三级全选框  全选/反选
$("#check3 input[type='checkbox']").click("click", function() { 
 	var evt = arguments[0] || window.event;
 	var chkbox = evt.srcElement || evt.target; 
	var checkboxes = $("#checkx3 input[type='checkbox']");
	if (chkbox.checked) {
		checkboxes.prop('checked', true);
	} else {
		checkboxes.prop('checked', false);
	}
});
</script>
</body>
</html>