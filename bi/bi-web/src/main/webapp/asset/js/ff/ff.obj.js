var ff = ff || {};
ff.obj = {};
 
ff.obj.find = function(objList,name,value)
{
	if(null == objList)
	{
		return null;
	}
    for(var i=0;i<objList.length;i++)
    {
        if(objList[i][name] == value)
        {
            return objList[i];
        }
    }
    return null;
};

ff.obj.findList = function(objList,name,value)
{
     
};