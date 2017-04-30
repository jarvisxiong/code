

$(document).ready(function(){  

});  
/**
 * 隐藏数据
 */
function hidedate(){
	$(".modehidden").hide();
	$(".nomodehidden").show();
}
function userTypechange(userType){
	if(userType==""){
		$("#selectuser").hide();
	}
	else if(userType!=1){
		$("#selectuser").hide();
	}else{
		$("#selectuser").show();
	}
}

function modetype(grantMode){//发放类型 显示和隐藏数据
	if(grantMode==""){
		$("input[name='userType']:eq(0)").attr("disabled",'true');
		$("input[name='userType']:eq(0)").removeAttr("checked");//将按钮可用
		hidedate();
	}
	else if(grantMode==0){//系统推送
		$("input[name='userType']:eq(0)").attr("disabled",'true');
		$("input[name='userType']:eq(0)").attr("disabled",'true');
		$("input[name='userType']:eq(1)").removeAttr("disabled");//将按钮可用
		$("input[name='userType']:eq(2)").removeAttr("disabled");//将按钮可用
		$("input[name='userType']:eq(0)").removeAttr("checked");//将按钮可用
		hidedate();
		
	}else if(grantMode==1){//用户领取 
		$("input[name='userType']:eq(1)").attr("disabled",'true'); 
		$("input[name='userType']:eq(2)").attr("disabled",'true');
		$("input[name='userType']:eq(0)").removeAttr("disabled");//将按钮可用
		$("input[name='userType']:eq(1)").removeAttr("checked");//将按钮可用
		$("input[name='userType']:eq(2)").removeAttr("checked");//将按钮可用
		$("#tabletd").empty();
		$("#userList").val("");
		userTypechange("")
		showdate();
	}
	
}
/**
 * 隐藏数据
 */
function showdate(){
	$(".modehidden").show();
	$(".nomodehidden").hide();
}
function showMember(){
    var url = rootPath + '/couponGrant/memberlist.do';
    var dlg=dialog({
        id: "printerForm",
        title: '选择用户',
        lock: false,
        content:"<iframe src='"+url+"' id='printerForm' name='printerForm,"+window.location.href+"'  width='1000px' height='610px' frameborder='0' scrolling='auto' ></iframe>",
        button: [
            {
                value: '确定',
                callback: function () {
                    document.getElementById("printerForm").contentWindow.getUpdata2();
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
function importview(id){
	 var url = rootPath + '/couponGrant/importView.do?id='+id;
	    var dlg=dialog({
	        id: "printerForm",
	        title: '导入excel',
	        lock: false,
	        content:"<iframe name='printerForm,"+window.location.href+"' src="+url+" width='500px' height='210px' frameborder='0' scrolling='no' id='printerForm'></iframe>",
	        button: [
	            
	            {
	                value: '关闭'
	            }
	        ]
	    }).showModal();


}

function showCouponAdmin(){

	var grantDate = $("#grantDateStr").val();
	if(grantDate != null && grantDate != ""&&grantDate != "null"){
		var url = rootPath + '/couponGrant/couponAdminList.do?grantDate='+grantDate;
		var dlg=dialog({
		    id: "printerCouponForm",
		    title: '选择优惠券',
		    lock: false,
		    content:"<iframe src='"+url+"'  name='printerCouponForm,"+window.location.href+"'  width='1000px' height='650px' frameborder='0' scrolling='auto' id='printerCouponForm'></iframe>",
		    button: [
		        {
		            value: '确定',
		            callback: function () {
		                document.getElementById("printerCouponForm").contentWindow.getUpdata2();
		            },
		            focus: true
		        },
		        {
		            value: '取消'
		        }
		    ]
		}).showModal();
	}else{
		$.frontEngineDialog.executeDialogContentTime("请先确认优惠券发放时间",2000);
	}
    //iframcoupon.submit();
}


function getDel(k,id){

	var idList=$("#userList").val();
	if(idList.indexOf(id)>=0){
		idList=idList.substring(0,idList.indexOf(id))+idList.substring(idList.indexOf(id)+id.length+1,idList.length);
		$("#userList").val(idList);
		$("#noidList").val(idList);
		/*console.log(idList);*/
	}
$(k).parent().parent().remove(); 

}

function delCoupon(k,id){

	var idList=$("#couponAdminList").val();
	if(idList.indexOf(id)>=0){
		idList=idList.substring(0,idList.indexOf(id))+idList.substring(idList.indexOf(id)+id.length+1,idList.length);
		$("#couponAdminList").val(idList);
		$("#noidList2").val(idList);
		/*console.log(idList);*/
	}
$(k).parent().parent().remove(); 

}

function saveCoupon(){
 	
	
	var grantDateStr=$("#grantDateStr").val();
	var grantEndDateStr=$("#grantEndDateStr").val();

 	var grantMode=$("input[name='grantMode']:checked").val();
 	var userType=$("input[name='userType']:checked").val();
	var grantType = $("#grantType").val();
	var userList = $("#userList").val();
	var receiveLimit=$("#receiveLimit").val();
	
	var couponList = $("#couponAdminList").val();
	var grantNum = $("#grantNum").val();
	var idTrue=true;
	if(receiveLimit>10){

		$.frontEngineDialog.executeDialogContentTime("ID限领数量不能大于10",2000);
		idTrue= false;
	}
//	var receiveLimit=$("#receiveLimit").val();
	if(grantNum>50000){//逗号一条，1001=1000  1002=1001异常
		$.frontEngineDialog.executeDialogContentTime("发放总量不能大于50000",2000);
		idTrue= false;
	}
	if(grantDateStr!="" && grantEndDateStr!="" && grantDateStr==grantEndDateStr){
		$.frontEngineDialog.executeDialogContentTime("开始时间和结束不能相等",2000);
		idTrue= false;
	}
	if(couponList == null || couponList == ""){
		$.frontEngineDialog.executeDialogContentTime("请选择添加优惠券",2000);
		idTrue= false;
	}
	return idTrue;
	//$("#myform").submit();
}