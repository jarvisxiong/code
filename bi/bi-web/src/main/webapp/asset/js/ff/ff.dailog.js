var ff = ff || {};

ff.dailog = {};

ff.dailog.load = function(data)
{
    data.title = data.title || '弹出框';
    data.id = data.id || 'ff_dailog';
    data.resize = data.resize || false;
    data.drag = data.drag || false;
    data.lock = data.lock || true;
    
    if(null != data.callback)
    {
    	data.button =  [{value: '确定',
    	                         callback: data.callback
    	                         },
    	                         {
    	                            value: '取消'
    	                          }
    	                          ];
    }	
    
	var obj = dialog(data).showModal(); 
	return obj;
}
ff.dailog.tree = function(para)
{ 
	 
	var treeID = para.treeID || "ff_tree";
	var search = para.search || false;
	var content = "";
	if(search)
	{
		content = '<input class="form-control input-sm txt_mid" type="text" id="ff_tree_search_name"  > <a onclick="ff.tree.search(\''+treeID+'\',$(\'#ff_tree_search_name\').val())" class="btn btn-primary btn-sm" ><i class="fa fa-search"></i>&nbsp;&nbsp;查询</a>'
	}	
	content = content + '<div class="left"><ul id="'+treeID+'" class="ztree"></ul></div>';
 	var data  = para;
	data.content = content;
 
	data.callback = para.callback || function(){};
	ff.dailog.load(data);
	
	ff.tree.load(treeID,para);
}