


//父控制：弹窗页面返回结果
function dbclickredpackage(row,data) {//获取返回值
	var stateType = {"1" : "启用", "0":"禁用"};
	    var $tabletd=parent.document.getElementById("tabletdredpackage");//红包表格id
	    //设置返回值存放位置
	    var $pd=parent.document.getElementById('redpackageId');
	    var $packageSelect=parent.document.getElementById('packageSelect');
	    
	    var tabletr=
	    '<tr><td>'+data.number+'</td>'+
	    '<td>'+data.name+'</td>'+
	    '<td>'+data.fackValue+'</td>'+
	    '<td>'+data.effectiveStr+'</td>'+
	    '<td>'+data.grantNum+'</td>'+
	    '<td>'+
	    stateType[data.state]
	    	+'</td>'+
	    '</tr>';
	   $($tabletd).empty(); 
	     $($tabletd).append(tabletr);
		  $($pd).val(data.id);//加id//关闭弹窗窗
		  $($packageSelect).val(data.number);//加id//关闭弹窗窗
		    var $close=$(parent.document.getElementById('title:printerForm')).prev();
		    $close.click();
}



