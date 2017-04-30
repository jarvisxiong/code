
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
	    var $tabletd=parent.document.getElementById("tabletdsku");//主商品表格id
	//设置返回值存放位置
	    var $pd=parent.document.getElementById('commodityCommoditySku');
	    var value=getChecked().split(";");
	    if(value==null || value==""){
	    	return false;
	    }
	    
	    var idList="";
	    for(var i=0;i<value.length;i++){
	    var tabletrvalue=value[i].split(",");
	     var tabletr="";
	    		$.ajax({
	    			url : rootPath + "/activityGive/getCommoditySku.do",
	    			data : {
	    				commodityCode : tabletrvalue[1]
	    			},
	    			type : "POST",
	    			dataType : "json",
	    			async : false,
	    			cache : false,
	    			success : function(result) {
	    				//把商品SKU返回	
	    				var length = result.length;
	    				for (var i = 0; i < length; i++)
	    				{		
	    					if($($pd).val().indexOf(result[i].id)>=0){
	    						continue;
	    					}
	    				    idList+=result[i].id+","
	    					tabletr+='<tr>'
    							+'<td>'+result[i].commodity.barCode+'</td>'
    							+'<td>'+result[i].commodity.name+'</td>'
    							+'<td>'+(result[i].commodityAttributeValues==null ?"无":result[i].commodityAttributeValues)+'</td>'
    							+'<td>'+result[i].barcode+'</td>'
    							+'<td><input  type="text" name="giftLimtCount'+i+'" class="giftLimtCount"  skuid="'+result[i].id+'"    '
    							+' style="width:80px;"  '
    							+'   data-rule-isIntGtZero="true"  data-msg-isIntGtZero="限定数量为大于0的整数" data-rule-isInteger="true" data-msg-isInteger="限定数量必须是整数"  /></td>'
    							+'<td>'+result[i].favourablePrice+'</td>'
    							+'<td>0</td>'
    							+'<td><input  type="text" name="giftCount'+i+'"   class="giftCount"   '
    							+' style="width:80px;"    '
    							+'   data-rule-isIntGtZero="true"  data-msg-isIntGtZero="单次赠送数量为大于0的整数"  data-rule-required="true" data-msg-required="单次赠送数量不能为空" value="1" data-rule-isInteger="true" data-msg-isInteger="单次赠送数量必须是整数"  /></td>'
	    						+    '<td><a href="#" onClick="delzp(this,\''+result[i].id+'\')">删除</a></td></tr>';
	    				}
	    			}
	    		});	

	     $($tabletd).append(tabletr);
	     
	    }
	    $($pd).val($($pd).val()+idList);
	    

		var commodityCommoditySku=parent.$("#commodityCommoditySku").val();//$("#couponAdminList").val();
		if(commodityCommoditySku!="" && commodityCommoditySku!=null){
			var  listlength =commodityCommoditySku.split(",");
			if(listlength.length>=4){
				parent.$("#zpover").addClass("couponoverclass");
			}
		}
	    
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