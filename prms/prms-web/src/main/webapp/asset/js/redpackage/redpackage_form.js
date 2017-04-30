
$(document).ready(function(){  
	$(function(){ 
	
		
		$("input:radio[name='dateStart']").change(function (){ 
			var dateStart= $("input[name='dateStart']:checked").val();
			if(dateStart=='1'){//使用有效期，剔除时间段
				$("#startDate").val("");
				$("#endDate").val("");
			}else{
				$("#receivedate").val("");
			}
		});
	}); 
});

$("#commmonBack").click(function(){
		 //确认放弃当前录入内容
		$.frontEngineDialog.executeDialog('isReturn_table_info','信息','<i class="fa fa-question-circle fa-3x" style="color: #86CFF9;vertical-align:middle;"></i>　是否确定放弃当前录入信息？　　','100%','100%',
			function(){
				$("#returnBack").click();
			}
		);    
});	
function saveRedpackage(){

	var idTrue=true;
	var dateStart= $("input[name='dateStart']:checked").val();
	var name=$("#name").val();
	var fackValue=$("#fackValue").val();
	if(fackValue<=0){
		
		$.frontEngineDialog.executeDialogContentTime("面值必须大于0",2000);
		idTrue= false;
	}
	if(dateStart==null || dateStart==''){

		$.frontEngineDialog.executeDialogContentTime("请选择使用有效期",2000);
		idTrue= false;
	}
	else if(dateStart=='0'){//时间段

		var startDate=$("#startDate").val();
		var endDate=$("#endDate").val();
		if(startDate==null  || startDate==""){
			$.frontEngineDialog.executeDialogContentTime("开始时间不为空",2000);
			idTrue= false;
		}else if(endDate==null  || endDate==""){
			$.frontEngineDialog.executeDialogContentTime("结束时间不为空",2000);
			idTrue= false;
		}
	}else if(dateStart=='1'){//领取后天数

		var receivedate=$("#receivedate").val();
		if(receivedate==null  || receivedate==""){
			$.frontEngineDialog.executeDialogContentTime("领取天数不为空",2000);
			idTrue= false;
		}else if(receivedate<=0){

			$.frontEngineDialog.executeDialogContentTime("领取天数不为0",2000);
			idTrue= false;
		}
	}
	if(name.length>12){

		$.frontEngineDialog.executeDialogContentTime("输入12个汉字以内",2000);
		idTrue= false;
	}
	return idTrue;
}