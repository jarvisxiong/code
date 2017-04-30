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


/*****
 * 弹窗
 */
function memberPrinter(){
	/*var size = $("#size").val();
	if(size == 0){
		$.frontEngineDialog.executeDialogContentTime("无可导出数据",1000);
	}else{*/
		$.frontEngineAjax.executeAjaxPost(
		    	rootPath + '/couponReceive/couponReceiveLimitExport.do?couponReceive=',
		    	$("#find-page-orderby-form").serialize(),
		        function(ret) {
		    		if(ret && 'success'==ret){
		    		var url = rootPath + '/couponReceive/couponReceiveExport.do?couponReceive='+$("#find-page-orderby-form").serialize();		
		    		window.location.href=url;
		    		}
		    		else if(ret && '0'==ret){
		    			$.frontEngineDialog.executeDialogContentTime("无可导出数据",2000);
		    		}
		    		else{
		    			$.frontEngineDialog.executeDialogContentTime("导出数据超过5万，请控制在5w以内",2000);
		    		}
		      }
		    );
	/*}*/
}