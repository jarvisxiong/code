/**
 * @Description 
 * @author tangjun
 * @date 2016年8月14日
 * 
 */
var ff = ff || {};

ff.rpt = {};

ff.rpt.load = function(element,data)
{
	var obj = ff.com.getJqObj(element);
	
	var gridObj;
	var chartObj;
	obj.find('[ff-class]').each(function()
     {
		var element = $(this);
	    var ffClass = ff.com.getFFClass(element);
	    
		
	    if(ffClass == "ff.rpt.sum")
	    {
	    	var sumData = data;
	    	ff.rpt.sum(element,sumData);
	    }
	    else if(ffClass == "ff.rpt.chart")
	    {
	    	chartObj = element;
	    }
	    else if(ffClass == "ff.rpt.grid")
	    {
	    	gridObj = element;
	    }
		
     }  
    );
	
	var data = ff.rpt.getConfig(data);
	var loadSuccess = function(rspObj)
	{
		rspObj = rspObj || {chart_data:[],table_data:[]};
        var table_data = rspObj.table_data || {};
 
        var gridData = data;
        gridData.data = table_data[data.dim.dim];
        ff.rpt.grid(gridObj,gridData);
        var chart_data = rspObj.chart_data;
        
        ff.chart.load(chartObj,data.title,chart_data);
	};
	ff.rpt.loadData(data,loadSuccess);
	
 
};


ff.rpt.loadDim = function(element,data)
{
	var obj = ff.com.getJqObj(element);
	
	var data = data || ff.com.getOptions(obj) || {};

	var html = "<select class=\"form-control input-sm txt_mid input-select\" id=\"ff_rpt_dim\" >";
	
	var timeDim = [{dim:'day',field_name:'day',name:'按日统计'},
	               {dim:'week',field_name:'week',name:'按周统计'},
	               {dim:'month',field_name:'month',name:'按月统计'},
	               {dim:'quarter',field_name:'quarter',name:'按季统计'},
	               {dim:'year',field_name:'year',name:'按年统计'}];
	var dims = data.dim || timeDim;
     
	for(var i=0;i<dims.length;i++)
    {
		var dim = dims[i];
		var json = ff.util.objToJson(dim);
		//json=json.replace(/\"/g,"'"); 
		html = html + "<option data-options=\'"+json+"\' value=\""+dim.field_name+"\">"+dim.name+"</option>";
    }	
  
	obj.html(html);
 
};

ff.rpt.loadIndicator = function(element,data)
{
	
	var obj = ff.com.getJqObj(element);
	var options = ff.com.getOptions(element);
	var data = data || {};
    
	data.indicator = data.indicator || options.indicator;
	data.url = data.url || options.url || ff.page.url("Indicator");
	data.callback = data.callback || options.callback;
    var callback = function(rspObj)
    {
    	var json = ff.util.objToJson(rspObj);
    	obj.attr("data-options","indicator:'"+json + "'");
    	if(null != data.callback)
    	{
    		data.callback(rspObj);
    	}	
    }
    ff.util.submit({
		url : data.url,
		data : data.indicator,
		success : callback
	});
 
};

ff.rpt.getIndicator = function(element,data)
{
	if(element instanceof Array)
	{
		data = element;
		element = null;
		
	}
		
	var indicatorID = element || "ff_rpt_indicator";
 
    var indicators =  ff.com.getOptions(indicatorID);
    var indiList = ff.util.jsonToObj(indicators.indicator);
    
    var result = [];
    
    if(null != data && data.length != 0)
    {
    	 for(var i=0;i<data.length;i++)
    	 {
    		 var indi = ff.obj.find(indiList,'indicator',data[i])
    		 if(null != indi)
    		 {
        		 result.push(indi);
    		 }	 
    	 }
    }	
    else
    {
    	result = indiList;
    }
   	
    
    return result;
};

ff.rpt.getDim = function(element,data)
{
	var dim = {};
	try
	{
		var dimID = element || "ff_rpt_dim";
		var select = ff.com.getJqObj(dimID);
		var obj = select.find("option:selected");
		 
	    dim =  ff.com.getOptions(obj);
  
	}
	catch(e)
	{
		ff.log.warn("error",e);
	}

    
    return dim;
};





ff.rpt.sum = function(element,data)
{
	var obj = ff.com.getJqObj(element);
	data = data || {};
	var dataN = {};
	dataN.url = data.url || ff.page.url("LoadSum");
	dataN.dim = data.dim || ff.rpt.getDim();
	dataN.indicator = data.indicator || ff.rpt.getIndicator();
	dataN.filter = data.filter;
	   
	if(null != dataN.indicator)
	{
		   var html = "";
		   var indicators = dataN.indicator;
  
		   for(var i=0;i<indicators.length;i++)
		   {
			   var indicator = indicators[i];
			   html = html + "<div class=\"col-md-2 getnumbercount\">"+indicator.name+"<p><input name=\""+indicator.field_name +"\"  style=\"border:0px;text-align:center;\" readonly=\"readonly\" /></p></div>";
			   
		   }	   
		   
 		   obj.html(html);
	}
	
	var loadSum = function(obj)
	{
		ff.form.load(element,obj);
	};
	
	if(null != dataN.url)
	{
		var condition = ff.com.getFilterObj(dataN.filter);
		ff.req.condition = condition;
		ff.util.submit({url:dataN.url,
               data:dataN.indicator,
               success:loadSum
               });
	}
	else
	{
		ff.form.load(element,dataN.data);
	}	
	
};

ff.rpt.excel = function(data)
{
	var dataN = ff.rpt.getConfig(data);
	var indicators = dataN.indicator;
	var dim = dataN.dim;
	
	var dims = [];
	for (var i = 0; i < indicators.length; i++) {
		var obj = {};
		obj.indicator = indicators[i].indicator;
		obj.dim = dim.dim;
		dims.push(obj);
	}
	
	ff.table.excel({data:dims});
}

ff.rpt.loadData = function(data,callback)
{
	var indicators = data.indicator;
	var dim = data.dim;
	if (null != indicators && null != dim) {

		var dims = [];
		for (var i = 0; i < indicators.length; i++) {
			var obj = {};
			obj.indicator = indicators[i].indicator;
			obj.dim = dim.dim;
			dims.push(obj);
		}
		var condition = ff.com.getFilterObj(data.filter);
		ff.req.condition = condition;
		ff.req.obj = dims;

		ff.util.submit({
			url : data.url,
			data : dims,
			success : callback
		});
	}
};

ff.rpt.getConfig = function(data)
{
 	data = data || {};
    data.url = data.url || ff.page.url("Load");
    data.dim = data.dim || ff.rpt.getDim();
	data.indicator = data.indicator || ff.rpt.getIndicator();
	
	return data;
}
ff.rpt.chart = function(element,data)
{
	data = ff.rpt.getConfig(data);
	
	var loadSuccess = function(rspObj)
	{
		rspObj = rspObj || {chart_data:[],table_data:[]};
        var chart_data = rspObj.chart_data;
        
        ff.chart.load(element,data.title,chart_data);
	};
	

	
	if(null == data.data)
	{
		//ff.chart.load(element,data.title,{});
		ff.rpt.loadData(data,loadSuccess);
 	}
	else
	{
		var chart_data = [];
        for(var i=0;i<data.indicator.length;i++)
        {
              var temp = ff.obj.find(data.data,'indicator',data.indicator[i].indicator)
              temp = temp || {};
              chart_data.push(temp);
        }
		ff.chart.load(element,data.title,chart_data);
	}
};
 
ff.rpt.grid = function(element,data)
{
   var obj = ff.com.getJqObj(element);
 
   
   data = ff.rpt.getConfig(data);
   
   var dataN = {};
   
   dataN.dim = data.dim;
   dataN.indicator = data.indicator;
   dataN.data = data.data;
   dataN.filter = data.filter;
   
   
   if(null !=  dataN.dim && null != dataN.indicator)
   {
	   var html = "<thead> <tr>";
	   var indicator = dataN.indicator;
	   var dim = dataN.dim;
	   html = html + "<th data-options=\"field:'" + dim.field_name +"'\">" + dim.name +"</th>";

	   for(var i=0;i<indicator.length;i++)
	   {
		   html = html + "<th data-options=\"field:'" +indicator[i].field_name +"'\">" +indicator[i].name +"</th>";
	   }	   
	   
	   html = html + "</tr>" + "</thead>";
	  // obj.html(html);
   } 
   
   
	var loadSuccess = function(rspObj)
	{
		obj = obj || {chart_data:[],table_data:[]};
        var table_data = rspObj.table_data;
        ff.grid.load(obj,{ paging: true,
            data:table_data[dim.dim],
           });
	};
	
   requirejs(['ff/datatable'], function() {
       

		if ($.fn.dataTable.isDataTable('#' + obj.attr('id'))) {
			obj.DataTable().destroy();
 
		}
		obj.html(html);
		if (null == dataN.data) {
			ff.grid.load(obj, {
				paging : true,
				data : []
			});
			ff.rpt.loadData(dataN, loadSuccess);
		} else {
			ff.grid.load(obj, {
				paging : true,
				data : dataN.data,
			});
		}
 							
     });
 

	


 
};


