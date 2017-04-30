/**
 * @Description 
 * @author tangjun
 * @date 2016年8月14日
 * 
 */
var ff = ff || {};

ff.chart = {};

ff.chart.load = function(obj,tilte,data,type)
{
	 obj = ff.com.getJqObj(obj);
	if(null == type)
	{
		type ="line";
	}
	
	var options;
	if(type == "line" || type =="pie" || type=="bar")
	{
		var dataList = [];
		if(null != data)
		{
			if(!jQuery.isArray( data ))
			{
				dataList.push(data);
			}
			else
			{
				dataList = data;
			}
	      
		}

		options = ff.chart.getLines(obj,tilte,dataList,type);
	}
 
	var chart =  echarts.init(obj[0]);
	chart.setOption(options,true);
	return chart;
};
ff.chart.getLines = function(obj,tilte,data,type,selectedMode)
{
 
	if(null == selectedMode)
	{
		selectedMode = "single"
	}
	var dataList = [];
	var nameList = [];
	var x_dataList = [];
	for(var i=0;i<data.length;i++)
	{
		var obj = new Object();
		obj.name = data[i].y_name;
		obj.type = type;
		obj.data = data[i].y_data;
 		dataList.push(obj);
 		x_dataList = data[i].x_data;
 		nameList[i] = data[i].y_name;
 	}
	var types =  ['line', 'bar'];
	if(selectedMode == "single" )
	{
		types =  ['line', 'bar'];
	}
	if(nameList.length == 1)
	{
		nameList = [];
	}
	var option = {
    		title: {
    	        text: tilte,
    	        left: 'center'
    	    },
    	    tooltip: {
    	        trigger: 'item',
    	        formatter: '{a} <br/>{b} : {c}'
    	    },
    	    legend: {
    	        left: 'left',
    	        data: nameList,
    	        selectedMode: selectedMode
    	    },
  
    	    toolbox: {
    	        show : true,
 
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: true},
    	            magicType : {show: true, type: types},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    xAxis: {
    	        name: 'x',
    	        splitLine: {show: false},
    	        data:x_dataList
    	    },
    	    grid: {
    	        left: '3%',
    	        right: '4%',
    	        bottom: '3%',
    	        containLabel: true
    	    },
    	    yAxis: {
    	        type: 'value',
    	        name: 'y'
    	    },
    	    series: dataList
  
    };
	return option;
} 
 