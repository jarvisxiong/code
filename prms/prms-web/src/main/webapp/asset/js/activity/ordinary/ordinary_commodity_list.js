
/**
 * 加载
 */
$(function(){
	
});


/**
 *  活动商品设置-新增/编辑弹出窗
 */
function ordinaryCommodityForm(id,actId) {
	var url = rootPath + '/ordinaryActivity/commdityFrom.do?id='+id+'&activityId='+actId;		
	var dlg=dialog({
        id: "setCommodityForm",
        title: '商品设置',
        lock: false,
        content:"<iframe  name='setCommodityForm,"+window.location.href+"' src='"+url+"' width='1100px' height='400px' frameborder='0' scrolling='auto' id='setCommodityForm' style='overflow-x:hidden; '></iframe>",
		button: [
		         {
		             value: '确定',
		             callback: function () {
		            	 document.getElementById("setCommodityForm").contentWindow.butsubmit();
		            	 return false;
		             },
		             focus: true
		         },{
		             value: '关闭',
		             callback: function () {
		             },
		             focus: true
		         }
		     ]
        }).showModal();
}

function exportData(){
	var size = $("#size").val();
	if(size == 0){
		$.frontEngineDialog.executeDialogContentTime("无可导出数据",1000);
	}else{
		var url = rootPath + '/ordinaryActivity/ordinaryActivityCommodityExport.do?activityCommodity='+$("#find-page-orderby-form").serialize();		
		window.location.href=url;
	}
}   


//查看
	function view(id,value,view) {
		window.location.href = rootPath + '/ordinaryActivity/commdityFrom.do?id='+id+'&activityId='+value+'&view=view';	
	}
