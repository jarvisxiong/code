
/****
 * 获取已经选择的项
 * var dt_demo_list = new initDataTable({…});
 *var data_selected = dt_demo_list.getSelectedData();
 * @returnsvar dtInstance = FFZX.DT.get(‘dataTableID’);
//然后dtInstance可以直接调用dataTable所有原生的API，详见 http://datatables.club/reference/api/
 
//例如：重新加载数据源
 *dtInstance.ajax.reload();
 */
	//父控制：弹窗页面返回结果
function memberlable() {
//获取返回值
    var $tabletd=parent.document.getElementById("tabletdLable");
//设置返回值存放位置
    var $pd=parent.document.getElementById('userlableList');
    var userList=$($pd).val();
    var value = dt_data_list.getSelectedData();
    if(value==null || value=="" || value.raw==null  ||  value.raw.length==0){
    	return false;
    }
    var idList="";
    for(var i=0;i<value.raw.length;i++){
    var tabletrvalue=value.raw[i];
     var tabletr=
    '<tr><td>'+tabletrvalue['labelCode']+'</td>'+
    '<td>'+tabletrvalue['labelName']+'</td>'+
    '<td><a href="#" onClick="getDelLable(this,\''+tabletrvalue['id']+'\')">删除</a></td></tr>';
    
     if(userList==null || userList=="" || userList.indexOf(tabletrvalue['id'])<0){
	     $($tabletd).append(tabletr);
	    idList+=tabletrvalue['id']+","
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