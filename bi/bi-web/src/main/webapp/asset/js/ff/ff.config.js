var ff = ff || {};

ff.config = {};

ff.config.url = {};


ff.config.load = function()
{
	var data =  {url:"SystemConfig/Load"};
	data.url = ff.util.buildUrl(data.url);
    data.success = function(rsp)
    {
         if (ff.util.isSuccess(rsp)) {
        	var obj = rsp.obj;
        	for(var i=0;i<obj.length;i++)
        	{
        		 var code = obj[i].subSystemCode.replace(/-/g, "_");
        		 ff.config.url[code] = "http://" + obj[i].subSystemDomain + ":" + obj[i].subSystemPort  + obj[i].subSystemBasePath;
        	}
        	
        }
        else {
            ff.log.warn("can not load system config",rsp);
        }
    	
    };
    ff.util.ajax(data);
};

ff.config.load();