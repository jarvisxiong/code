
var sendUrl=rootPath + "/reward/rewardSend.do";
var deleteUrl=rootPath + "/reward/delete.do";
function gen_action(item) {	
	var objAction = {
			send:[],
	        edit:[],
	        delete: [
	                 {
	                	 label: '删除',
	                	 href: rootPath + '/reward/delete.do?id=' + item.id
	                 }
	             ],
	        luck: [],
	        cancel:[],
	        shareOrder:[]
	    };
	
	if(item.sendStaus==0){
		objAction['send'].push({
			label: '发布',
			href: "javascript:sendOrCancel('" + item.id + "','1','200')",
			class: 'testCSS'
		});	
	}					
		objAction['edit'].push({
			label: '编辑',
			href: "javascript:iframeFullPage('"+ rootPath + "/reward/form.do?viewStatus=edit&id=" + item.id + "')",
			class: 'testCSS'
		});
		if(item.sendStaus==1){
			objAction['cancel'].push({
				label: '撤销',
				href: "javascript:sendOrCancel('"+item.id+"','0','300')",
				class: 'testCSS'
			});
		}
		
		if(item.sendStaus==1 && isReward(item.rewardStatus)){
			objAction['luck'].push({
				label: '计算幸运号',
				href: "javascript:iframeFullPage('"+ rootPath + "/reward/luckNo.do?id=" + item.id + "')",
				class: 'testCSS'
			});
		}
		if(item.sendStaus==1 && item.rewardStatus==3){
			objAction['shareOrder'].push({
				label: '设置晒单',
				href: "javascript:isShareOrder('"+item.id+"')",
				class: 'testCSS'
			});
		}		
	return objAction;
}

function isReward(status){
	if(status==2 || status==3){
		return true;
	}
}

function isShareOrder(id){
	$.ajax({
		url : rootPath + "/reward/isShareOrder.do?",
		data : {
			id : id
		},
		type : "POST",
		dataType : "json",
		async : false,
		cache : false,
		success : function(result) {
			if (result.status == 'exception') {
				$.frontEngineDialog.executeDialogOK('提示信息',result.infoStr,'300px');	
			} else {
				setTimeout('window.location.href="' + rootPath+ '/reward/shareOrder.do?id='+id+'"');
			}
		}
	});
}

/**
 * 删除，发布，撤销
 * @param id
 * @param sendStatus
 * @param type
 * @returns
 */
function sendOrCancel(id,sendStatus,type) {
	var info='';
	var urlStr="";
	if(type=='100'){
		info='请确认要执行删除吗？';
		urlStr=deleteUrl;
	}else if(type=='200'){
		info='请确认要执行发布吗？';
		urlStr=sendUrl;
	}else if(type=='300'){
		info='请确认要执行撤销吗？';
		urlStr=sendUrl;
	}
	$.frontEngineDialog.executeDialog(
		'selMenu', 
		'提示信息', 
		info, 
		"200px", 
		"35px", 
		function() {			
			$.ajax({
				url : urlStr,
				data : {
					id : id,
					sendStatus:sendStatus
				},
				type : "POST",
				dataType : "json",
				async : false,
				cache : false,
				success : function(result) {
					if (result.status == 'success' || result.code == 0) {
						dialog({quickClose : true,content : '操作成功！'}).show();
						setTimeout('window.location.href="' + rootPath+ '/reward/rewardList.do"', 1000);
					} else {
						dialog({quickClose : true,content : result.infoStr}).show();
						setTimeout('window.location.href="' + rootPath+ '/reward/rewardList.do"', 1000);
					}
				}
			});
	});
}

function findBystatus(status,obj){
	$(".navtabs-title li").attr("class",""); // 清理所有li的样式
	$(obj).parent().attr("class","active"); // 添加当前对象所在li的样式
	$("#rewardStatus").val(status);
	$("#find-page-orderby-button").click();
}


