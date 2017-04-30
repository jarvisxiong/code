
$(document).ready(function(){  
	$(function(){ 
		if(start!="add"){
			$("input[name=grantMode]").attr("disabled","disabled");
			$("input[name=packageSelectbutton]").attr("disabled","disabled");
			
		}
		/*失去焦点
		$("#endDate").bind("input propertychange", function() {
			  $("#tabletdredpackage").empty(); 
			   $("#redpackageId").val("");//加id//关闭弹窗窗
			   $("#packageSelect").val("");//加id//关闭弹窗窗
			});*/
	}); 
});
$("#commmonBack").click(function(){
	 //确认放弃当前录入内容
	$.frontEngineDialog.executeDialog('isReturn_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否确定放弃当前录入信息？　　','100%','100%',
		function(){
			$("#returnBack").click();
		}
	);    
});	
function showPackage(){
	var endDate=$("#endDate").val();
	if(endDate==null || endDate==""){

		$.frontEngineDialog.executeDialogContentTime("选择红包前请选择截止时间",2000);
		return false;
	}
	 var url = rootPath + '/redpackage/dialoglist.do?endDate='+endDate;
	    var dlg=dialog({
	        id: "printerForm",
	        title: '选择红包',
	        lock: false,
	        content:"<iframe src='"+url+"' id='printerForm' name='printerForm,"+window.location.href+"'  width='1000px' height='610px' frameborder='0' scrolling='auto' ></iframe>",
	        button: [
	            {
	                value: '确定',
	                focus: true
	            },
	            {
	                value: '取消'
	            }
	        ]
	    }).showModal();
	
}
function showMember(){
    var url = rootPath + '/redpackageGrant/memberlabel.do';
    var dlg=dialog({
        id: "printerForm",
        title: '选择用户标签',
        lock: false,
        content:"<iframe src='"+url+"' id='printerForm' name='printerForm,"+window.location.href+"'  width='1000px' height='610px' frameborder='0' scrolling='auto' ></iframe>",
        button: [
            {
                value: '确定',
                callback: function () {
                    document.getElementById("printerForm").contentWindow.memberlable();
                },
                focus: true
            },
            {
                value: '取消'
            }
        ]
    }).showModal();


    //iframmemembe.submit();
}

function getDel(k,id){

	var idList=$("#userlableList").val();
	if(idList.indexOf(id)>=0){
		idList=idList.substring(0,idList.indexOf(id))+idList.substring(idList.indexOf(id)+id.length+1,idList.length);
		$("#userlableList").val(idList);
		/*console.log(idList);*/
	}
$(k).parent().parent().remove(); 

}
function saveGrantRedpackage(){
	var idTrue=true;
	var userlableList=$("#userlableList").val();
	
 	var redpackageId=$("#redpackageId").val();
	if(userlableList==null  || userlableList=="" || userlableList.length<=4){
		$.frontEngineDialog.executeDialogContentTime("请至少选择一个用户标签",2000);
		idTrue= false;
	}
	else if(redpackageId==null  || redpackageId=="" || redpackageId.length<=4){
			$.frontEngineDialog.executeDialogContentTime("请选择一个红包",2000);
			idTrue= false;
	}else{
		var length= userlableList.split(",").length-1;
		if(length>5){
			$.frontEngineDialog.executeDialogContentTime("最多选择五个用户标签",2000);
			idTrue= false;
		}
	}
	return idTrue;
}