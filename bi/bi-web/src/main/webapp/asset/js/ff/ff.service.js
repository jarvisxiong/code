var ff = ff || {};

ff.service = {};

ff.service.vendor = function (options)
{
	ff.select.load({title:'供应商选择',url:'SystemData/VendorSelect',width:1200,height:600,options:options});
}

ff.service.address = function (options)
{
	ff.select.load({title:'地址选择',url:'SystemData/AddressSelect',width:1200,height:600,options:options});
}

ff.service.category = function (options)
{
 	ff.select.tree({title:'商品类别选择',url:"SystemData/CategoryTree",search:true,setting:{field:{pIdKey:'parentId'}},options:options});
}
