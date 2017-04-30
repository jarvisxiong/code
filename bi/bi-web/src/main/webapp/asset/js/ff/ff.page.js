/**
 * @Description 
 * @author tangjun
 * @date 2016年8月14日
 * 
 */
var ff = ff || {};

ff.page = {};


ff.page.obj = function()
{
	var obj = ff.com.getJqObj("#ff_page_config");
	return obj;
}
ff.page.config = function()
{
	var obj = ff.page.obj();
    config = ff.com.getOptions(obj);
	return config;
}

ff.page.url = function(method)
{
	var obj = ff.page.config();
	url = obj.action + "/" + method;
	return url;
}

