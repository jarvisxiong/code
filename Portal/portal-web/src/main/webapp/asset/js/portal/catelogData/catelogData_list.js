function gen_action(item) {
	var id = item.id;
	var releaseType = item.isLaunch=="未发布"?"RELEASE":"CANCEL";
	var topType = item.isTop==0?"TOP":"CANCEL";
	var objAction = {
		edit: [
			{
				label: '编辑',
				href: "javascript: iframeFullPage('"+ rootPath + '/catelogData/form.do?id=' + id +"')"
			}
		],
		delete: [
			{
				label: '删除',
				href: rootPath + '/catelogData/delete.do?id=' + id
			}
		],
		isRelease: [
			{
				label: item.isLaunch=="未发布"?"发布":"取消发布",
				href:"javascript:opertionRelease('"+releaseType+"','"+id+"')"
			}
		],
		isTop: [
			{
				label: item.isTop==0?"置顶":"取消置顶",
				href:"javascript:opertionTop('"+topType+"','"+id+"')"
			}
		]
	};
	
	return objAction;
}


function batchOpertionRelease(type){
	var selectedIds = "";
	var arrar = dt_catelogData_list.getSelectedData().raw;
	$.each(arrar,function(index,value){
		selectedIds+=(value.id+",");
	});
	
	if(selectedIds==""){
		return;
	}
	opertionRelease(type,selectedIds);
}

function opertionRelease(releaseType,id){
	var href = rootPath + '/catelogData/release.do?type='+releaseType+'&id='+id
	$.ajax({
		url : href,
		type : "GET",
		dataType : "json",
		async : false,
		cache : false,
		success : function(result) {
			if (result.status == 'success' || result.code == 0) {
				dialog({
					quickClose : true,
					content : '操作成功！'
				}).show();
				
				reloadData('catelogData_list');
			} else {
				dialog({
					quickClose : true,
					content : result.msg
				}).show();
			}
		}
	});
}


function opertionTop(topType,id){
	var href = rootPath + '/catelogData/top.do?type='+topType+'&id='+id
	$.ajax({
		url : href,
		type : "GET",
		dataType : "json",
		async : false,
		cache : false,
		success : function(result) {
			if (result.status == 'success' || result.code == 0) {
				dialog({
					quickClose : true,
					content : '操作成功！'
				}).show();
				
				reloadData('catelogData_list');
			} else {
				dialog({
					quickClose : true,
					content : result.msg
				}).show();
			}
		}
	});
}