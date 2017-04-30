

$(document).ready(function(){  
	  $("input").attr("disabled",true);   // 禁用文本框（设置disabled属性为true）
	  $("textarea").attr("disabled",true); 
	  $(".btn-default").attr("disabled",false); 
	  
});  

function checkmain(){
	var name="选择主商品";
	var  url = rootPath + '/activityGive/findMainCommodity.do';
	var dlg=dialog({
        id: "checkmain",
        title: name,
        lock: false,
        content:"<iframe src='"+url+"' id='checkmain' name='checkmain,"+window.location.href+"'  width='1000px' height='610px' frameborder='0' scrolling='auto' ></iframe>",
        button: [
            {
                value: '确定',
                callback: function () {
                    document.getElementById("checkmain").contentWindow.getSuper();
                },
                focus: true
            },
            {
                value: '取消'
            }
        ]
    }).showModal();
}
function checkzp(){
	var name="选择优惠券";
	var zpSelect=$("input[name='zpSelect']:checked").val();
	var url ="";
	if(zpSelect=='1'){
	   url = rootPath + '/couponGrant/couponAdminMzList.do?isDate=1';
	}else if(zpSelect=='0'){
		name="选择商品";
		url = rootPath + '/activityGive/findzpCommodity.do';
	}else{
		$.frontEngineDialog.executeDialogContentTime("请选择商品或优惠券单选按钮",2000);
		return false;
	}
	var dlg=dialog({
        id: "printerForm",
        title: name,
        lock: false,
        content:"<iframe src='"+url+"' id='printerForm' name='printerForm,"+window.location.href+"'  width='1000px' height='610px' frameborder='0' scrolling='auto' ></iframe>",
        button: [
            {
                value: '确定',
                callback: function () {
                    document.getElementById("printerForm").contentWindow.getSuper();
                },
                focus: true
            },
            {
                value: '取消'
            }
        ]
    }).showModal();

	
}

function showMember(){
    var url = rootPath + '/couponGrant/memberlist.do?noidList='+$("#userList").val();
    var dlg=dialog({
        id: "printerForm",
        title: '选择用户',
        lock: false,
        content:"<iframe src='"+url+"' id='printerForm' name='printerForm,"+window.location.href+"'  width='1000px' height='610px' frameborder='0' scrolling='auto' ></iframe>",
        button: [
            {
                value: '确定',
                callback: function () {
                    document.getElementById("printerForm").contentWindow.getCouponAdmin();
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

function showCouponAdmin(){

	var grantDate = $("#grantDateStr").val();
	if(grantDate != null && grantDate != ""&&grantDate != "null"){
		var url = rootPath + '/couponGrant/couponAdminMzList.do?noidList='+$("#couponAdminList").val()+'&grantDate='+grantDate;
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

function delMain(k,id){
	$(k).parent().parent().remove(); 
	$("#commodityId").val("");
}


function delzp(k,id){
	var idList=$("#commodityCommoditySku").val();
	if(idList.indexOf(id)>=0){
		idList=idList.substring(0,idList.indexOf(id))+idList.substring(idList.indexOf(id)+id.length+1,idList.length);
		$("#commodityCommoditySku").val(idList);
		/*console.log(idList);*/
	}
	$(k).parent().parent().remove(); 
	var commodityCommoditySku=$("#commodityCommoditySku").val();//$("#couponAdminList").val();
	if(commodityCommoditySku!="" && commodityCommoditySku!=null){
		var listlength =commodityCommoditySku.split(",");
		if(listlength.length<4){
			parent.$("#zpover").removeClass("couponoverclass");
		}
	}

}
function delCoupon(k,id){
	var idList=$("#couponAdminList").val();
	if(idList.indexOf(id)>=0){
		idList=idList.substring(0,idList.indexOf(id))+idList.substring(idList.indexOf(id)+id.length+1,idList.length);
		$("#couponAdminList").val(idList);
		/*console.log(idList);*/
	}
	$(k).parent().parent().remove(); 
	var couponAdminList=$("#couponAdminList").val();//$("#couponAdminList").val();
	if(couponAdminList!="" && couponAdminList!=null){
		var couponAdminListlength =couponAdminList.split(",");
		if(couponAdminListlength.length<4){
			parent.$("#couponover").removeClass("couponoverclass");
		}
	}

}

function saveDate(){

	var idTrue=true;
	var storageType=$("input[name='storageType']:checked").val();
	if(storageType == null || storageType=="" ){
		$.frontEngineDialog.executeDialogContentTime("请选择出库仓库选择",2000);
		idTrue=false;
	}
	
	var commodityId=$("#commodityId").val();
	if(commodityId==null || commodityId==""){

		$.frontEngineDialog.executeDialogContentTime("请选择主商品",2000);
		idTrue=false;
	}
	var commodityCommoditySkuNum ="";				
	$("#tabletdsku tr").each(function(index){// 过滤克隆tr
		var giftLimtCount=$(this).find("input.giftLimtCount").val();
		if(giftLimtCount==null || giftLimtCount==""){
			giftLimtCount="-1";
		}
		var giftCount=$(this).find("input.giftCount").val();
		if(giftCount==null || giftCount==""){
			giftCount="-1";
		}
		commodityCommoditySkuNum+=$(this).find("input.giftLimtCount").attr("skuid")+",";
		commodityCommoditySkuNum+=giftLimtCount+",";
		commodityCommoditySkuNum+=giftCount;
		commodityCommoditySkuNum +=";";
	});	
	

	$("#commodityCommoditySkuNum").val(commodityCommoditySkuNum);
	return idTrue;
	//$("#myform").submit();
}