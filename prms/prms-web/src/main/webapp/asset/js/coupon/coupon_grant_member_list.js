
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
	 
	//父控制：弹窗页面返回结果
function getUpdata2() {
//获取返回值
    var $tabletd=parent.document.getElementById("tabletd");
//设置返回值存放位置
    var $pd=parent.document.getElementById('userList');
    var userList=$($pd).val();
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
    '<td><a href="#" onClick="getDel(this,\''+tabletrvalue[0]+'\')">删除</a></td></tr>';
    
     if(userList==null || userList=="" || userList.indexOf(tabletrvalue[0])<0){
	     $($tabletd).append(tabletr);
	    idList+=tabletrvalue[0]+","
     }
    }
    
    $($pd).val(userList+idList);//加id

    /*var $noidList=parent.document.getElementById('noidList');//post提交的id
    $($noidList).val($($pd).val()+idList);//加id
*/    
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