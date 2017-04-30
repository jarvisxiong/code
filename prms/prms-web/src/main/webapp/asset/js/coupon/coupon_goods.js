/****
 * 获取已经选择商品ID
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
	return value;
	} 
/****
 * 获取已经选择商品名称
 * @returns
 */
function getCheckedName(){
	var value=[];
	var checkbox=document.getElementsByName("check");	
    for(var i = 0; i < checkbox.length; i++){  	
	      if(checkbox[i].checked){
	    	  value.push($(checkbox[i]).attr("coupon")); 
	      }
     } 
	return value;
	} 







