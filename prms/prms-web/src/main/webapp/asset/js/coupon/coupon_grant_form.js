

$(document).ready(function(){  
	if(viewstatus=='edit'){
		
//		$(".grantMode").attr("disabled",'true'); 
//		$("input[name='grantMode']:eq(0)").attr("disabled",'true');
	}
	userTypechange(userType);
	modetype(grantMode);//用户类型，隐藏数据
	 $(".userType").click(function(){
	     	var value=$("input[name='userType']:checked").val();
	     	userTypechange(value);//用户类型，隐藏数据
	     	});
	
     $(".grantMode").click(function(){
     	var value=$("input[name='grantMode']:checked").val();
     	modetype(value);//发放类型，隐藏数据
     	});

});  
/**
 * 隐藏数据
 */
function hidedate(flag){//true仅仅执行显示idlimit
	$(".idLimithide").show();//隐藏发放总量  ID发放数量
	if(flag){
		$(".modehidden").hide();//隐藏发放总量  ID发放数量
		$(".nomodehidden").show();//显示每人限制领取
	}
}
function hideidReceiveLimitshowLimit(){//隐藏id限制数量，显示总限制数量,天降和消息用的

	$(".idLimithide").hide();//隐藏发放总量  ID发放数量
	$(".modehidden").show();//显示总限制
}
function userTypechange(userType){
	if(userType==""){
		$("#selectuser").hide();
		$("#selectuserlable").hide();//隐藏用户标签
	}
	else if(userType==0 || userType==2){
		$("#grantEndDateStr").removeAttr("disabled");//将按钮可用
		$("#selectuser").hide();
		$("#selectuserlable").hide();//隐藏用户标签
	}else if(userType==1){

		$("#selectuser").show();
		$("#selectuserlable").hide();//隐藏用户标签
		$("#grantEndDateStr").val("");
		$("#grantEndDateStr").attr("disabled",'true');
	}else if(userType==3){
		$("#grantEndDateStr").removeAttr("disabled");//将按钮可用
		$("#selectuser").hide();
		$("#selectuserlable").show();//显示用户标签
	}
}

function modetype(grantMode){//发放类型 显示和隐藏数据
	if(grantMode==""){
		disabledcheckeduserType();
		disabledcheckeduserTypethree();
//		$("input[name='userType']:eq(0)").attr("disabled",'true');
//		$("input[name='userType']:eq(0)").removeAttr("checked");//将按钮可用
		hidedate(true);
	}
	else if(grantMode==0){//系统推送
		disabledcheckeduserType();
		disabledcheckeduserTypethree();
		$("input[name='userType']:eq(1)").removeAttr("disabled");//将按钮可用
		$("input[name='userType']:eq(2)").removeAttr("disabled");//将按钮可用
		hidedate(true);
		
	}else if(grantMode==1){//用户领取 
		hidedate(false);
		disabledcheckeduserTypeone();
		disabledcheckeduserTypetwo();
		disabledcheckeduserTypethree();
//		$("input[name='userType']:eq(2)").attr("disabled",'true');
//		$("input[name='userType']:eq(2)").removeAttr("checked");//将按钮可用
		$("input[name='userType']:eq(0)").removeAttr("disabled");//将按钮可用
		$("#tabletd").empty();
		$("#userList").val("");
		userTypechange("")
		showdate();
	}else if(grantMode==4 || grantMode==5){//天降和消息

		$("input[name='userType']:eq(3)").removeAttr("disabled");//将按钮可用
		disabledcheckeduserType();
		disabledcheckeduserTypeone();
		disabledcheckeduserTypetwo();
		hideidReceiveLimitshowLimit();
	}
	
}


function showMemberlable(){
    var url = rootPath + '/couponGrant/memberlabel.do';
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

function getDelLable(k,id){
	var idList=$("#userlableList").val();
	if(idList.indexOf(id)>=0){
		idList=idList.substring(0,idList.indexOf(id))+idList.substring(idList.indexOf(id)+id.length+1,idList.length);
		$("#userlableList").val(idList);
		/*console.log(idList);*/
	}
$(k).parent().parent().remove(); 

}
function disabledcheckeduserTypeone(){
	$("input[name='userType']:eq(1)").attr("disabled",'true'); 
	$("input[name='userType']:eq(1)").removeAttr("checked");//将按钮可用
}
function disabledcheckeduserTypetwo(){
	$("input[name='userType']:eq(2)").attr("disabled",'true'); 
	$("input[name='userType']:eq(2)").removeAttr("checked");//将按钮可用
}

function disabledcheckeduserTypethree(){
	$("input[name='userType']:eq(3)").attr("disabled",'true'); 
	$("input[name='userType']:eq(3)").removeAttr("checked");//将按钮可用
}
function disabledcheckeduserType(){
	$("input[name='userType']:eq(0)").attr("disabled",'true'); 
	$("input[name='userType']:eq(0)").removeAttr("checked");//将按钮可用
}
/**
 * 隐藏数据
 */
function showdate(){
	$(".modehidden").show();//显示ID发放数量
	$(".nomodehidden").hide();//隐藏没人限制领取，发放总量
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

	//0 系统推送  1用户领取   4消息优惠券推送  5天降优惠券推送
 	var grantMode=$("input[name='grantMode']:checked").val();
 	var userType=$("input[name='userType']:checked").val();
	var grantType = $("#grantType").val();
	var userList = $("#userList").val();
	var wang=1000000;
	
	var couponList = $("#couponAdminList").val();
	var grantNum = $("#grantNum").val();

	var receiveLimit=$("#receiveLimit").val();
	var userlableList=$("#userlableList").val();
	if(receiveLimit>10){

		$.frontEngineDialog.executeDialogContentTime("ID限领数量不能大于10",2000);
		idTrue= false;
	}
	var idTrue=true;
//	var receiveLimit=$("#receiveLimit").val();
	if(userList.split(",").length>=1002){//逗号一条，1001=1000  1002=1001异常
		$.frontEngineDialog.executeDialogContentTime("用户必须最多1000",2000);
		idTrue= false;
	}
	//grantMode 0 系统推送  1用户领取   4消息优惠券推送  5天降优惠券推送

	if(grantMode==1){
		if(grantNum=="" || grantNum==null  ){
	
			$.frontEngineDialog.executeDialogContentTime("用户领取必须填写发放总量",2000);
			idTrue= false;
		}else if(grantNum>wang){
			$.frontEngineDialog.executeDialogContentTime("发放总量必须小于100万",2000);
			idTrue= false;
		}
	}else if(grantMode==4 || grantMode==5){
		if(grantNum==null || grantNum==""){

			$.frontEngineDialog.executeDialogContentTime("发放总量不能为空",2000);
			idTrue= false;
		}
		else if(userlableList==null  || userlableList=="" || userlableList.length<=4){
			$.frontEngineDialog.executeDialogContentTime("请至少选择一个用户标签",2000);
			idTrue= false;
		}else{
			var length= userlableList.split(",").length-1;
			if(length>5){
				$.frontEngineDialog.executeDialogContentTime("最多选择五个用户标签",2000);
				idTrue= false;
			}
		}
	}
	if((userType=="" || userType==null)){

		$.frontEngineDialog.executeDialogContentTime("用户类型必须选择",2000);
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