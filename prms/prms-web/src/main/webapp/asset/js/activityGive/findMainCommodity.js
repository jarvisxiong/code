
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
	 
function getMainSuper(id,name,code,preferentialPrice){
	//获取返回值
	    var $tabletd=parent.document.getElementById("tabletdmain");//主商品表格id
	//设置返回值存放位置
	    var $pd=parent.document.getElementById('commodityId');
		/*var userBuyCount='';*/
		//设置返回tr
		/*$.ajax({
			url : rootPath + "/activityGive/getuserBuyCount.do",
			data : {
				commodityCode : code
			},
			type : "POST",
			dataType : "json",
			async : false,
			cache : false,
			success : function(result) {
				//把商品SKU返回	
				userBuyCount=result;
			}
		});	*/
	     var tabletr=
	    '<tr><td>'+code+'</td>'+
	    '<td>'+name+'</td>'+
	    '<td><input  type="text" name="limitCount" id="limitCount"  class="limitCount"  style="width:80px;" onBlur="caclTotalNum()" '+
	    '  data-rule-isIntGteZero="true"  data-msg-isIntGteZero="限定数量为大于等于0的整数"  data-rule-isInteger="true" data-msg-isInteger="限定数量必须是整数" ></td>'+
	    '<td>'+preferentialPrice+'</td>'+
	    '<td><a href="#" onClick="delMain(this,\''+id+'\')">删除</a></td></tr>';
	   $($tabletd).empty(); 
	     $($tabletd).append(tabletr);
	   var giveTitle=parent.document.getElementById('giveTitle');
	   var giveCommodityId=parent.document.getElementById('giveCommodityId');
		$(giveCommodityId).val(id);
	   $(giveTitle).val(name);  
		  $($pd).val(id);//加id//关闭弹窗窗
		    var $close=$(parent.document.getElementById('title:checkmain')).prev();
		    $close.click();
		   
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