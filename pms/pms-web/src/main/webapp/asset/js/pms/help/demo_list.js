function gen_action(item) {
	
	var objAction = {
		info: [],
		edit: [],
		'delete': [
			{
				label: '删除',//,
				//href: rootPath + '/help/test/delete.do?id=' + item.id
				class: 'gray',
				style: 'color:red;'
			}
		]
	};
	
	//if (item.status == 1) {
		objAction['info'].push({
			label: '查看1',
			href: "javascript:iframeFullPage('"+ rootPath + "/help/test/form.do?id=" + item.id + "')"
		});
		
		objAction['edit'].push({
			label: '保存',
			href: "javascript:saveRow('"+ item.id +"')",
			class: 'testCSS'
		});
	//}
	
	//if (item.status == 2) {
		objAction['info'].push({
			label: '查看2',
			href: "javascript:iframeFullPage('"+ rootPath + "/help/test/form.do?id=" + item.id + "')"
		});
		
		objAction['edit'].push({
			label: '编辑2',
			href: "javascript:iframeFullPage('"+ rootPath + "/help/test/form.do?id=" + item.id + "')",
			target: '_blank',
			class: 'gray'
		});
	//}
	
	return objAction;
}

function saveRow(id) {
	var $row = $('input[value="'+ id +'"]').closest('tr');
	$row.find(':checkbox').prop('checked', true);	
	
	//console.log($row.find(':input').form2Json());
	//reloadData('demo_list');
}