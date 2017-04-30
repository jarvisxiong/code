var ff = ff || {};
ff.req = ff.req || {};
ff.com = {};

ff.com.name = "ff.com";

ff.com.log  = {};
ff.com.log.debug =function (message,obj)
{
	 ff.log.debug(message,obj,ff.com.name);
};
ff.com.log.info =function (message,obj)
{
	 ff.log.info(message,obj,ff.com.name);
};
ff.com.log.warn =function (message,obj)
{
	 ff.log.warn(message,obj,ff.com.name);
};
ff.com.log.error =function (message,obj)
{
	 ff.log.error(message,obj,ff.com.name);
};


 
ff.com.getJqObj = function(element)
{
	
	  var domObj;
	  if((typeof element=='string')&&element.constructor==String)
	  {
		  var index = element.indexOf("#");
		  var origin = element;
		  if(index != 0){
			  element = "#"+element;
		  }
		  else
		  {
			  origin = element.substr(1,element.length);
		  }
 
		  
		  if($(element).size()>0){
			  domObj = $(element); 
		  }
		  else
		  {
			  try
			  {
				  var $obj = parent.document.getElementById(origin);
				  domObj = $($obj);  
			  }
			  catch(e)
			  {
				  ff.log.warn("can not get the element" + origin,e);
			  }
			 
 		  }
		  
	  }
	  else
	  {
		  domObj = element;
	  }	 

	  return domObj;
};

ff.com.getJson = function(element,name)
{
	  var domObj = ff.com.getJqObj(element);
	  var data=$.trim(domObj.attr(name));
	  var obj = {};
	  if(data)
	  {
		if(data.substring(0,1)!="{")
		{
			data="{"+data+"}";
		}
		obj =(new Function("return "+data))();
	  }
 
	  return obj;
};

ff.com.getOptions = function(element)
{
	  var domObj = ff.com.getJqObj(element);
	  var data=$.trim(domObj.attr("data-options"));
	  var obj = {};
	  if(data)
	  {
		if(data.substring(0,1)!="{")
		{
			data="{"+data+"}";
		}
		obj =(new Function("return "+data))();
	  }
 
	  return obj;
};

ff.com.getFFClass= function(element)
{
	  var domObj = ff.com.getJqObj(element);
	  var ffClass = domObj.attr("ff-class");
	  return ffClass;
};

ff.com.getFilterObj = function(element,otherCondition)
{
	
	element = element || "ff_filter";
	try
	{
	    var conditionList = [];
	    //获取搜索form中所有的input
	    var inputs = ff.com.getJqObj(element).find("input");
	    //获取searchForm中的input内容。
	    //引用EasyUI格式的input运行后审查元素会发现生成了3个input，所以需要根据filterType属性筛选。
	    //循环这个input数组取值
	    var attrName;
	    var condition = new Object;
	    inputs.each(function () {
	        
	        if (null != $(this).attr("filter"))
	        {
	            condition.filterType = $(this).attr("filter");
	            //attrName = $(this).attr("name");
	            condition.attrName = $(this).attr("name");
	            condition.attrValue = $(this).val();
	            if(condition.attrName !=""  && condition.attrValue != "")
	            {
	            	conditionList.push(condition);
		            condition = new Object;
	            }
	        }
	    });
	    if(null != otherCondition)
    	{
	    	conditionList.push(searchObj);
    	}
	}
	catch(e)
	{
		ff.com.log.warn("get filter error",e);
	}

    return conditionList;
};
