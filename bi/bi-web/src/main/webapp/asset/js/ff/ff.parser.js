var ff = ff || {};
ff.parser = {};

ff.parser.parse = function(id)
{
	if(null == id ||  ""==id)
	{
		ff.parser.parseAll();
	}
	else
	{
	    $(id).each(function()
	    	    {
	    	    	 ff.parser.dispatch($(this));
	    	    }  
	    );
	}
};
ff.parser.parseAll= function()
{
	$("[ff-class]").each(function()
	    {
	    	ff.parser.dispatch($(this));
	    }  
    );
    
};


ff.parser.dispatch = function(element)
{
    var ffClass = ff.com.getffClass(element);
    
	
    if(ffClass == "ff.form.time")
    {
    	ff.form.time(element);
    }
    
};