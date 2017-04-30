
/****
 * 获取已经选择的项
 * @returns
 */
function getChecked(){
	var value=[];
	var checkbox=document.getElementsByName("check");
    for(var i = 0; i < checkbox.length; i++){
      if(checkbox[i].checked){
    	  value.push(checkbox[i].value); 
      }
     } 
	return value.join(";");
	} 
	 
function getSuper(){
	//获取返回值
	    var $tabletd=parent.document.getElementById("admintabletd");
	//设置返回值存放位置
	    var $pd=parent.document.getElementById('couponAdminList');
	    var value=getChecked().split(";");
	    if(value==null || value==""){
	    	return false;
	    }
	    var idList="";
	    for(var i=0;i<value.length;i++){
	    var tabletrvalue=value[i].split(",");
	     var tabletr=
	    '<tr><td>'+tabletrvalue[1]+'</td>'+
	    '<td>'+tabletrvalue[2]+'</td>'+
	    '<td>'+tabletrvalue[3]+'</td>';
	    if(tabletrvalue[4] == -1){
	    	tabletr += '<td>无限制</td>';
	    }else{
	    	tabletr += '<td>'+tabletrvalue[4]+'</td>';
	    }
	    tabletr +='<td>'+tabletrvalue[5]+'</td>'+
	    '<td><a href="#" onClick="delCoupon(this,\''+tabletrvalue[0]+'\')">删除</a></td></tr>';
	    
	     $($tabletd).append(tabletr);
	     
	    idList+=tabletrvalue[0]+","
	    }
	    $($pd).val($($pd).val()+idList);
	    

		var couponAdminList=parent.$("#couponAdminList").val();//$("#couponAdminList").val();
		if(couponAdminList!="" && couponAdminList!=null){
			var couponAdminListlength =couponAdminList.split(",");
			if(couponAdminListlength.length>=4){
				parent.$("#couponover").addClass("couponoverclass");
			}
		}
}
	//父控制：弹窗页面返回结果
function getUpdata2() {
//获取返回值
    var $tabletd=parent.document.getElementById("admintabletd");
//设置返回值存放位置
    var $pd=parent.document.getElementById('couponAdminList');
    var value=getChecked().split(";");
    if(value==null || value==""){
    	return false;
    }
    var idList="";
    for(var i=0;i<value.length;i++){
    var tabletrvalue=value[i].split(",");
     var tabletr=
    '<tr><td>'+tabletrvalue[1]+'</td>'+
    '<td>'+tabletrvalue[2]+'</td>'+
    '<td>'+tabletrvalue[3]+'</td>';
    if(tabletrvalue[4] == -1){
    	tabletr += '<td>无限制</td>';
    }else{
    	tabletr += '<td>'+tabletrvalue[4]+'</td>';
    }
    tabletr +='<td>'+tabletrvalue[5]+'</td>'+
    '<td><a href="#" onClick="delCoupon(this,\''+tabletrvalue[0]+'\')">删除</a></td></tr>';
    
     $($tabletd).append(tabletr);
     
    idList+=tabletrvalue[0]+","
    }
    $($pd).val($($pd).val()+idList);
    var $noidList=parent.document.getElementById('noidList2');//post提交的id
    $($noidList).val($($pd).val()+idList);//加id
    
}

function getCouponAdmin(){
	
}

/****
 * 获取已经选择的项
 * @returns
 */
function getChecked(){
	var value=[];
	var checkbox=document.getElementsByName("check");
    for(var i = 0; i < checkbox.length; i++){
      if(checkbox[i].checked){
    	  value.push(checkbox[i].value); 
      }
     } 
	return value.join(";");
	} 