var ff = ff || {};
ff.select = {};

ff.select.load = function(data)
{
    var url= ff.util.buildUrl(data.url);
	var width =  data.width || 800;
	var height = data.height || 600;
	data.title = data.title || "请选择";
	 
   var json = ff.util.objToJson(data.options);
   
   var content = "<input  id='ff_select_data' type='hidden' data-options='"+json+"'></input>"+
                         "<iframe id='ff_iframe' name='ff_iframe,"+window.location.href
                           +"' src='"+url+"' width='"+data.width+"' height='"+data.height
                           +"' frameborder='0' >" +
                           		"</iframe>";
   data.content = content;
   
   ff.dailog.load(data);
}

ff.select.clear = function(element,event)
{
	$(element).parent().find("input").val("");
	event.stopPropagation();
}
ff.select.tree = function(para)
{ 
    para.treeID =  para.treeID || "ff_tree";
	para.callback = function()
	{
		var treeObj = $.fn.zTree.getZTreeObj(para.treeID);
		var nodes = treeObj.getSelectedNodes();
		
		if (nodes.length > 0) {
			ff.select.select(nodes[0],para.options);
		}
	}
	ff.dailog.tree(para);
}
ff.select.select = function(obj,options)
{ 
	ff.log.warn("select data is ", obj);
	
    options = options || ff.com.getOptions("#ff_select_data");
    var obj = obj || {};
    if(null != options)
    {
        if(null != options.data)
        {
             var dataList = [];
             if(!jQuery.isArray( options.data ))
             {
                dataList.push(options.data);
             }
             else
             {
                 dataList = options.data;
             }
             for(var i=0;i<dataList.length;i++)
             {
                  ff.com.getJqObj(dataList[i].id).val(obj[dataList[i].field]);
             }
        }
    	if(null != options.callback)
	    {
	        options.callback(obj);
	    }
    }
    

    
    //if the dailog use confirm button, no need to close.
    try
	{
         var $close;
    	 $close=$(parent.document.getElementById('title:ff_dailog')).prev();
    	 $close.click();
	}
	catch(e)
	{
		ff.log.warn("",e);
 	}
 

   
}
