
$(document).ready(function(){ 
	memberdate();
});

//父控制：弹窗页面返回结果
function memberdate() {
	console.log("abcde");
//获取返回值
var $tabletd=parent.document.getElementById("tabletd");
//设置返回值存放位置
var $pd=parent.document.getElementById('userList');
var userList=$($pd).val();
var value=valuedate.split(";");;
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

 if((userList==null || userList=="" || userList.indexOf(tabletrvalue[0])<0) 
		 && (tabletrvalue[0]!=null && tabletrvalue[0]!="")){
     $($tabletd).append(tabletr);
    idList+=tabletrvalue[0]+","
 }
}

$($pd).val(userList+idList);//加id   
}