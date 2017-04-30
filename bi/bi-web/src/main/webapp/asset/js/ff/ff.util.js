var ff = ff || {};
ff.util = {};


ff.util.date = {};
ff.util.date.format = function()
{
	if (!Date.prototype.format) {
	    Date.prototype.format = function(fmt) {
	        var o = {
	            'M+': this.getMonth() + 1,
	            'd+': this.getDate(),
	            'h+': this.getHours(),
	            'm+': this.getMinutes(),
	            's+': this.getSeconds(),
	            'q+': Math.floor((this.getMonth() + 3) / 3),
	            'S': this.getMilliseconds()
	        }

	        if (/(y+)/.test(fmt))
	            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));

	        for (var k in o)
	            if (new RegExp('(' + k + ')').test(fmt))

	                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));

	        return fmt;
	    }
	}
};

ff.util.date.format();





ff.util.show = function(message)
{
	alert(message);
}

ff.util.buildUrl = function (url)
{
	var abUrl = rootPath;
	if(url.indexOf("/") != 0)
	{
		abUrl = abUrl + "/"; 
	}	
	abUrl = abUrl +  url + ".do";
 
	return abUrl; 
};



ff.util.objToJson = function (obj)
{
	return JSON.stringify(obj);
};
ff.util.jsonToObj = function (json)
{
	try
	{
		var obj = JSON.parse(json);
	}
	catch(e)
	{
		ff.log.error("json convert error", json);
	}
	return obj;
};

ff.util.isSuccess = function (obj) {
    var result = false;
    if (obj.errorCode == 0) {
        result = true;
    }
    return result;
};


ff.util.submit = function(data)
{
	if(null == data)
	{
		 ff.log.error("data is null when submit");
		 return;
	}
	if((null != data.data)&&(Object.prototype.toString.call(data.data) === "[object String]")&&(data.data.indexOf("#")>=0))
	{
//		if ($(data.data).form('validate') == false)      处理form验证
//	    {
//			ff.util.warn("输入验证失败");
//	    	return;
//	    }
    	ff.req.obj = $(data.data).serializeObject();			
	}
	else if(null != data.data){
		ff.req.obj = data.data;
	}

    var json = ff.util.objToJson(ff.req);
    data.url = ff.util.buildUrl(data.url) + "?para=" + json;;
    data.data = json;
    var callback = data.success;
    data.success = function(rsp)
    {
         if (ff.util.isSuccess(rsp)) {
        	callback(rsp.obj);
        }
        else {
            ff.util.show(rsp.message);
        }
    	
    };
 
    ff.util.ajax(data);
};

 

ff.util.ajax = function(para)
{
	try
	{
		if(para.wait)
		{
			//showWaitDialog();    //need show wait dialog
		}
		var contentType = "";
	    {
	        contentType = "text/html";
	    }
	    
        $.ajax({
            type: "post",
            url: para.url ,
            contentType: contentType,
            dataType: "json",
            success:function (data)
            {
        		if(para.wait)
            	{
            		//removeWaitDialog();    //need remove wait dialog
            	}	
        		if(data.errorCode == 10)
        		{
        			//top.location = ff.util.buildPageUrl("Index/Login.html");      //error to login
        		}
        		para.success(data);
            },
            error: function (error) {
        		if(para.wait)
            	{
            		//removeWaitDialog();
            	}	
        		ff.util.show("网路请求失败");
            }
        });
	}
	catch(e)
	{
		alert(e);
	}
};

