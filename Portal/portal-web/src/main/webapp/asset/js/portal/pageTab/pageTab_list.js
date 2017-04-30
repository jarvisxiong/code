function gen_action(item) {
	var id = item.id;
	var objAction = {
		edit: [
			{
				label: '编辑',
				href: "javascript: iframeFullPage('"+ rootPath + '/pageTab/form.do?id=' + id +"')"
			}
		],
		delete: [
			{
				label: '删除',
				href: rootPath + '/pageTab/delete.do?id=' + id
			}
		]
	};
	
	return objAction;
}

