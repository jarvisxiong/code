function setTag(id,view){
		var url;	
		var title;
		if(view=="edit"){			
			title="预售标签-编辑";
		}else{
			title="预售标签-新增";
		}			
		url= rootPath + '/presaletag/toSaveAndUpdate.do?id='+id+'&viewStatus='+view;	
		var dlg=dialog({
            id: "setTagForm",
            title: title,
            lock: false,
            content:"<iframe name='setTagForm,"+window.location.href+"'  src='"+url+"' width='400px' height='200px' frameborder='0' scrolling='no' id='setTagForm'></iframe>",
        	button: [
			         {
			             value: '确定',
			             callback: function () {
			            	 document.getElementById("setTagForm").contentWindow.butsubmit();
			            	 return false;
			             },
			             focus: true
			         },{
			             value: '取消',
			             callback: function () {
			             },
			             focus: true
			         }
			     ]
		}).showModal();		
}
 