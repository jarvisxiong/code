$(function() {
	
	var option = {
		theme : 'vsStyle',
		expandLevel : 6
	};	

	ffzx.ui(['treetable'], function(){
		$('#TreeTable').treeTable(option);
	});
});