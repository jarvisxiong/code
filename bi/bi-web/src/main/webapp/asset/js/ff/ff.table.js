var ff = ff || {};
ff.table = {};

ff.table.load = function(element,data)
{
}

ff.table.excel = function(data)
{
	data = data || {};
	
     
    var obj = ff.page.obj();
 
    var url = data.url || ff.page.url("Export");
    
    var excelFrameID = "ff_frame_excel";
    if(obj.children( "#"+excelFrameID ).size()<=0)
    {
    	obj.append('<div style="display:none;"  ><iframe id="'+excelFrameID + '" ></iframe></div>');
    }
 
	if((null != data.data)&&(Object.prototype.toString.call(data.data) === "[object String]")&&(data.data.indexOf("#")>=0))
	{
    	ff.req.obj = $(data.data).serializeObject();			
	}
	else if(null != data.data){
		ff.req.obj = data.data;
	}
	var condition = ff.com.getFilterObj(data.filter);
	ff.req.condition = condition;
    var json = ff.util.objToJson(ff.req);
    $("#"+excelFrameID).attr("src",ff.util.buildUrl(url)+"?para="+json);
}

