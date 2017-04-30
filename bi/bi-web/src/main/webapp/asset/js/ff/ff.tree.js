var ff = ff || {};
ff.tree = {};

ff.tree.load = function(element,data)
{
	var domObj = ff.com.getJqObj(element);
	
	var defualtSetting = {
			data : {// 数据
			    simpleData : {
				enable : true,// true / false 分别表示 使用 / 不使用 简单数据模式// 默认false，一般使用简单数据方式
				idKey : "id",// 节点数据中保存唯一标识的属性名称 默认值："id"
				pIdKey : 'pId'// 点数据中保存其父节点唯一标识的属性名称 默认值："pId"
			},
			key : {
				name:'name',
				url : ""
			}
		},
		view:{
			selectedMulti: false
		}
	};
	var tree_setting = data.setting || defualtSetting;
	tree_setting.data = tree_setting.data || defualtSetting.data;
	tree_setting.data.key = tree_setting.key || defualtSetting.data.key;
	tree_setting.data.simpleData = tree_setting.data.simpleData || defualtSetting.data.simpleData;
    if(null != tree_setting.field)
    {
    	tree_setting.data.simpleData.idKey = tree_setting.field.idKey || tree_setting.data.simpleData.idKey;
    	tree_setting.data.simpleData.pIdKey = tree_setting.field.pIdKey || tree_setting.data.simpleData.pIdKey;
    	tree_setting.data.key.name = tree_setting.field.name || tree_setting.data.key.name;
    	tree_setting.data.key.url = tree_setting.field.idKey || tree_setting.data.key.url;
    }
   
	
	data.success = function(ret) {
						$.fn.zTree.init(domObj, tree_setting, ret);
					}
	data.url = ff.util.buildUrl(data.url);
	ff.util.ajax(data);
	
}
ff.tree.search = function(element,data)
{
 
 	if(null == data || data == "")
	{
		return;
	}
	
	//调用当前common 里面的方法实现定位，后续可以优化
	treeLocate(element,'name',data);
	   
}
