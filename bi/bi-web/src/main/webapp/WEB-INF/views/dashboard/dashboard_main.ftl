<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="list" />

<title>报表管理</title>

<style>
.getnumbercount{width: 20% !important;text-align: -webkit-center;text-align: center;border: 1px solid #ccc;padding-top: 5px;}
</style>
</head>
<body>

	<div class="tab-content">
		<div class="tab-pane fade in active" id="brandList">
			<div class="col-md-12">
				<div class="search">
					<form id="find-page-orderby-form" action="${BasePath !}/commodity/list.do" method="post">
						<input type="hidden" name="status" value="${status!}" /> 
						<input type="hidden" name="tblshow" value="${tblshow!}" /> 
						
						<div class="inquire-ul">
						<div class="form-tr"><br/>
								<div class="form-td chooseDate">
									<div class="btn-group" data-toggle="buttons">
									  <label class="btn btn-default btn-sm active">
									    <input type="radio" name="options" id="yesterdayChart" autocomplete="off" checked>昨天
									  </label>
									  <label class="btn btn-default btn-sm">
									    <input type="radio" name="options" id="oneWeekChart" autocomplete="off">近一周
									  </label>
									  <label class="btn btn-default btn-sm">
									    <input type="radio" name="options" id="monthChart" autocomplete="off">近30天
									  </label>
									  <label class="btn btn-default btn-sm">
									    <input type="radio" name="options" id="sixMonthsChart" autocomplete="off">近半年
									  </label>
									</div>
								</div>
								<div class="form-td">
									
									<div class="div-form">
	                                    <input name="hiredateMinDate" id="hiredateMinDate" class="form-control txt_mid input-sm"  readonly="readonly"
	                                     onfocus="WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'hiredateMaxDate\')}'})"
	                                     value="${(hiredateMinDate)!}">
                                    </div> -         
                                    <div class="div-form">
	                                     <input name="hiredateMaxDate" id="hiredateMaxDate" class="form-control txt_mid input-sm"  readonly="readonly"
	                                     onfocus="WdatePicker({startDate:'%y',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'hiredateMinDate\')}'})"
	                                     value="${(hiredateMaxDate)!}">
	                                </div>
                              	</div>
                                <div class="form-td">
                                    <label>条形码：</label> 
                                    <div class="div-form"><input id="" name="" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.code) !}"></div>
                                </div>
                                <div class="form-td">
                                    <label>商品类目：</label> 
                                    <div class="div-form"><input id="" name="" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.code) !}"></div>
                                </div>
                                <div class="form-td">
                                    <label>供应商：</label> 
                                    <div class="div-form"><input id="" name="" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.code) !}"></div>
                                </div>
                                <div class="form-td">
                                    <label>地区：</label> 
                                    <div class="div-form"><input id="" name="" class="form-control txt_mid input-sm" type="text" placeholder="" value="${(commodity.code) !}"></div>
                                </div>
								
							</div>
						</div>
						<div class="btn-div3">
                             <button id="find-page-orderby-button" class="btn btn-primary btn-sm btn-inquire" type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;查询</button>
                             <a href="${BasePath !}/commodity/list.do?tblshow=1" class="btn btn-primary btn-sm"><i class="fa fa-remove"></i>&nbsp;&nbsp;清空</a>
							<a onclick="exportExcel()" class="btn btn-primary btn-sm" ><i class="fa fa-arrow-circle-up"></i>&nbsp;&nbsp;导出</a>
                          
						</div>
					</form>
				</div>
			</div>
			<div class="col-md-12" style="margin-bottom: 10px;">
				<div class="col-md-2 getnumbercount">总销售件数<p>2000</p></div>
				<div class="col-md-2 getnumbercount">总销售件数<p>2000</p></div>
				<div class="col-md-2 getnumbercount">总销售件数<p>2000</p></div>
				<div class="col-md-2 getnumbercount">总销售件数<p>2000</p></div>
				<div class="col-md-2 getnumbercount">总销售件数<p>2000</p></div>
			</div>
			<div class="col-md-12" style="margin-bottom: 10px;">
				<select class="form-control input-sm txt_mid input-select" id="actFlag" name="actFlag"> 
			      <option value="month">按月统计</option>
			      <option value="quarter">按季度统计</option>
			    </select>
			</div>
			<div class="col-md-12">
			 <div id="myChart1" style="float: left;width: 25%;height:400px;"></div>
			 <div id="myChart2" style="float: left;width: 25%;height:400px;"></div>
			 <div id="myChart3" style="float: left;width: 25%;height:400px;"></div>
			 <div id="myChart4" style="float: left;width: 25%;height:400px;"></div>
			<script type="text/javascript">
				
					
		    </script>
			</div>
			<div class="col-md-12 changeChart" style="    margin-bottom: 5px;text-align: right;">
				<div class="btn-group" data-toggle="buttons">
					  <label class="btn btn-default active">
					    <input type="radio" name="cHoptions" id="changelineChart" autocomplete="off" checked>折线图
					  </label>
					  <label class="btn btn-default">
					    <input type="radio" name="cHoptions" id="changeHistogramChart" autocomplete="off">柱状图
					  </label>
					  <label class="btn btn-default">
					    <input type="radio" name="cHoptions" id="changePieChart" autocomplete="off">饼图
					  </label>
					  <label class="btn btn-default">
					    <input type="radio" name="cHoptions" id="changeScatterplotChart" autocomplete="off">散点图
					  </label>
				</div>
			</div>
			<div class="col-md-12">
			 <div id="myChart5" style="height:400px;"></div>
			 
			 
			</div>
			<div class="tab-content">
				<!-- DataTable HTML -->
				<table id="ff_DataTable" class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Name</th>
							<th>Position</th>
							<th>Office</th>
							<th>Extn.</th>
							<th>Start date</th>
							<th>Salary</th>
						</tr>
					</thead>
					
				</table>
				
				<!-- END: DataTable HTML -->
			</div>
			
		</div>
	</div>



<script type="text/javascript">
	$(document).ready(function(){
		
		// Date - format
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
		
		
		changeData($('.chooseDate label.active input[type="radio"]')[0].id);
		
		$('.chooseDate label').unbind('click').on('click', function(e) {
			
			var $chooseDate = $(this).find('input[type="radio"]');
			var oldchooseDateActive=$('.chooseDate label.active input[type="radio"]')[0].id;
			if(oldchooseDateActive===$chooseDate[0].id) return;
			changeData($chooseDate[0].id);
		});
		
		
		requirejs(['ff/datatable','echarts'], function(){
			
				 $('#ff_DataTable').DataTable($.extend({}, OPT_DATATABLE, {
				
					"ajax": "${BasePath !}/asset/v2/lib/datatable/js/ajax_data.json",
					"columns": [
						{ "data": "name" },
						{ "data": "position" },
						{ "data": "office" },
						{ "data": "extn" },
						{ "data": "start_date" },
						{ "data": "salary" }
					]
				 }));				
				 var myChart1 = echarts.init(document.getElementById('myChart1'));
				 var myChart2 = echarts.init(document.getElementById('myChart2'));
		         var myChart3 = echarts.init(document.getElementById('myChart3'));
				 var myChart4 = echarts.init(document.getElementById('myChart4'));
				 var myChart5 = echarts.init(document.getElementById('myChart5'));
				 
				 var selectednum=document.getElementById('actFlag')
				 var selectednumindex = selectednum.selectedIndex; // 选中索引
				 var selectednumtext = selectednum.options[selectednumindex].text; // 选中文本
				 var selectednumvalue = selectednum.options[selectednumindex].value; // 选中值
				 
				 
				 
				 var myChart1GetData={};
				 if(selectednumvalue === 'month'){
					 
				 }else if(selectednumvalue === 'quarter'){
					 
				 }
				 
				 
				 
				changeChart($('.changeChart label.active input[type="radio"]')[0].id);
				
				$('.changeChart label').unbind('click').on('click', function(e) {
					
					var $changeChart = $(this).find('input[type="radio"]');
					var oldchangeChartDateActive=$('.changeChart label.active input[type="radio"]')[0].id;
					if(oldchangeChartDateActive===$changeChart[0].id) return;
					changeChart($changeChart[0].id);
				});
					
				 
		        var myChart1Op = {
		      		title: {
		                text: '坐标轴刻度与标签对齐'
		            },
		            color: ['#3398DB'],
		            tooltip : {
		                trigger: 'axis',
		                axisPointer : {           
		                    type : 'shadow'        
		                }
		            },
		            grid: {
		                left: '3%',
		                right: '4%',
		                bottom: '3%',
		                containLabel: true
		            },
		            xAxis : [
		                {
		                    type : 'category',
		                    data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
		                    axisTick: {
		                        alignWithLabel: true
		                    }
		                }
		            ],
		            yAxis : [
		                {
		                    type : 'value'
		                }
		            ],
		            series : [
		                {
		                    name:'直接访问',
		                    type:'bar',
		                    barWidth: '60%',
		                    data:[10, 52, 200, 334, 390, 330, 220]
		                }
		            ]
		        };
		        
		        //cahart2
		        var myChart2Op = {
		        		title : {
		        	        text: '某站点用户访问来源',
		        	        subtext: '纯属虚构',
		        	        x:'center'
		        	    },
		        	    tooltip : {
		        	        trigger: 'item',
		        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
		        	    },
		        	    legend: {
		        	    	orient: 'horizontal',
		        	        bottom: 'bottom',
		        	        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		        	    },
		        	    series : [
		        	        {
		        	            name: '访问来源',
		        	            type: 'pie',
		        	            radius : '55%',
		        	            center: ['50%', '60%'],
		        	            data:[
		        	                {value:335, name:'直接访问'},
		        	                {value:310, name:'邮件营销'},
		        	                {value:234, name:'联盟广告'},
		        	                {value:135, name:'视频广告'},
		        	                {value:1548, name:'搜索引擎'}
		        	            ],
		        	            itemStyle: {
		        	                emphasis: {
		        	                    shadowBlur: 10,
		        	                    shadowOffsetX: 0,
		        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		        	                }
		        	            }
		        	        }
		        	    ]
		        };
		        
		        //echart3
		        var myChart3Op = {
		      		title: {
		                text: '坐标轴刻度与标签对齐'
		            },
		            color: ['#3398DB'],
		            tooltip : {
		                trigger: 'axis',
		                axisPointer : {           
		                    type : 'shadow'        
		                }
		            },
		            grid: {
		                left: '3%',
		                right: '4%',
		                bottom: '3%',
		                containLabel: true
		            },
		            xAxis : [
		                {
		                    type : 'category',
		                    data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
		                    axisTick: {
		                        alignWithLabel: true
		                    }
		                }
		            ],
		            yAxis : [
		                {
		                    type : 'value'
		                }
		            ],
		            series : [
		                {
		                    name:'直接访问',
		                    type:'bar',
		                    barWidth: '60%',
		                    data:[10, 52, 200, 334, 390, 330, 220]
		                }
		            ]
		        };
		        
		        //cahart4
		        var myChart4Op = {
			        		title : {
			        	        text: '某站点用户访问来源',
			        	        subtext: '纯属虚构',
			        	        x:'center'
			        	    },
			        	    tooltip : {
			        	        trigger: 'item',
			        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
			        	    },
			        	    legend: {
			        	        orient: 'horizontal',
			        	        bottom: 'bottom',
			        	        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
			        	    },
			        	    series : [
			        	        {
			        	            name: '访问来源',
			        	            type: 'pie',
			        	            radius : '55%',
			        	            center: ['50%', '60%'],
			        	            data:[
			        	                {value:335, name:'直接访问'},
			        	                {value:310, name:'邮件营销'},
			        	                {value:234, name:'联盟广告'},
			        	                {value:135, name:'视频广告'},
			        	                {value:1548, name:'搜索引擎'}
			        	            ],
			        	            itemStyle: {
			        	                emphasis: {
			        	                    shadowBlur: 10,
			        	                    shadowOffsetX: 0,
			        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			        	                }
			        	            }
			        	        }
			        	    ]
			        };
		      
			       
			        
			        
			        myChart1.setOption(myChart1Op);
			        myChart2.setOption(myChart2Op);
			        myChart3.setOption(myChart3Op);
			        myChart4.setOption(myChart4Op);
			       
			        
			        function changeChart(changeChartId){
						console.log(changeChartId);
						myChart5.clear();
						var myChart5Op={};
						if(changeChartId==='changelineChart'){
							  	myChart5Op = {
						        		title: {
						        	        text: '对数轴示例',
						        	        left: 'center'
						        	    },
						        	    tooltip: {
						        	        trigger: 'item',
						        	        formatter: '{a} <br/>{b} : {c}'
						        	    },
						        	    legend: {
						        	        left: 'left',
						        	        data: ['1的指数', '2的指数', '3的指数'],
						        	        /* selected: {
						        	            // 选中'系列1'
						        	            '1的指数': true,
						        	            // 不选中'系列2'
						        	            '2的指数': false,
						        	            '3的指数': false
						        	        }, */
						        	        selectedMode: 'single'
						        	    },
						        	    xAxis: {
						        	        type: 'category',
						        	        name: 'x',
						        	        splitLine: {show: false},
						        	        data: ['一', '二', '三', '四', '五', '六', '七', '八', '九']
						        	    },
						        	    grid: {
						        	        left: '3%',
						        	        right: '4%',
						        	        bottom: '3%',
						        	        containLabel: true
						        	    },
						        	    yAxis: {
						        	        type: 'log',
						        	        name: 'y'
						        	    },
						        	    series: [
						        	        {
						        	            name: '1的指数',
						        	            type: 'line',
						        	            data: [1, 3, 9, 27, 81, 247, 741, 2223, 6669]
						        	        },
						        	        {
						        	            name: '2的指数',
						        	            type: 'line',
						        	            data: [1, 2, 4, 8, 16, 32, 64, 128, 256]
						        	        },
						        	        {
						        	            name: '3的指数',
						        	            type: 'line',
						        	            data: [4, 5, 8, 16, 32, 64, 128, 256, 512]
						        	        }
						        	    ]
						        };
						}else if(changeChartId==='changeHistogramChart'){
							
							myChart5Op = {
						      		title: {
						                text: '坐标轴刻度与标签对齐'
						            },
						            color: ['#3398DB'],
						            tooltip : {
						                trigger: 'axis',
						                axisPointer : {           
						                    type : 'shadow'        
						                }
						            },
						            grid: {
						                left: '3%',
						                right: '4%',
						                bottom: '3%',
						                containLabel: true
						            },
						            xAxis : [
						                {
						                    type : 'category',
						                    data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
						                    axisTick: {
						                        alignWithLabel: true
						                    }
						                }
						            ],
						            yAxis : [
						                {
						                    type : 'value'
						                }
						            ],
						            series : [
						                {
						                    name:'直接访问',
						                    type:'bar',
						                    barWidth: '60%',
						                    data:[10, 52, 200, 334, 390, 330, 220]
						                }
						            ]
						        };
						}else if(changeChartId==='changePieChart'){
							myChart5Op = {
					        		title : {
					        	        text: '某站点用户访问来源',
					        	        subtext: '纯属虚构',
					        	        x:'center'
					        	    },
					        	    tooltip : {
					        	        trigger: 'item',
					        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
					        	    },
					        	    legend: {
					        	        orient: 'horizontal',
					        	        bottom: 'bottom',
					        	        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
					        	    },
					        	    series : [
					        	        {
					        	            name: '访问来源',
					        	            type: 'pie',
					        	            radius : '55%',
					        	            center: ['50%', '60%'],
					        	            data:[
					        	                {value:335, name:'直接访问'},
					        	                {value:310, name:'邮件营销'},
					        	                {value:234, name:'联盟广告'},
					        	                {value:135, name:'视频广告'},
					        	                {value:1548, name:'搜索引擎'}
					        	            ],
					        	            itemStyle: {
					        	                emphasis: {
					        	                    shadowBlur: 10,
					        	                    shadowOffsetX: 0,
					        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					        	                }
					        	            }
					        	        }
					        	    ]
					        };
						}else if(changeChartId==='changeScatterplotChart'){
							

							var data = [
							    [[28604,77,17096869,'Australia',1990],[31163,77.4,27662440,'Canada',1990],[1516,68,1154605773,'China',1990],[13670,74.7,10582082,'Cuba',1990],[28599,75,4986705,'Finland',1990],[29476,77.1,56943299,'France',1990],[31476,75.4,78958237,'Germany',1990],[28666,78.1,254830,'Iceland',1990],[1777,57.7,870601776,'India',1990],[29550,79.1,122249285,'Japan',1990],[2076,67.9,20194354,'North Korea',1990],[12087,72,42972254,'South Korea',1990],[24021,75.4,3397534,'New Zealand',1990],[43296,76.8,4240375,'Norway',1990],[10088,70.8,38195258,'Poland',1990],[19349,69.6,147568552,'Russia',1990],[10670,67.3,53994605,'Turkey',1990],[26424,75.7,57110117,'United Kingdom',1990],[37062,75.4,252847810,'United States',1990]],
							    [[44056,81.8,23968973,'Australia',2015],[43294,81.7,35939927,'Canada',2015],[13334,76.9,1376048943,'China',2015],[21291,78.5,11389562,'Cuba',2015],[38923,80.8,5503457,'Finland',2015],[37599,81.9,64395345,'France',2015],[44053,81.1,80688545,'Germany',2015],[42182,82.8,329425,'Iceland',2015],[5903,66.8,1311050527,'India',2015],[36162,83.5,126573481,'Japan',2015],[1390,71.4,25155317,'North Korea',2015],[34644,80.7,50293439,'South Korea',2015],[34186,80.6,4528526,'New Zealand',2015],[64304,81.6,5210967,'Norway',2015],[24787,77.3,38611794,'Poland',2015],[23038,73.13,143456918,'Russia',2015],[19360,76.5,78665830,'Turkey',2015],[38225,81.4,64715810,'United Kingdom',2015],[53354,79.1,321773631,'United States',2015]]
							];

							myChart5Op = {
							    backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
							        offset: 0,
							        color: '#f7f8fa'
							    }, {
							        offset: 1,
							        color: '#cdd0d5'
							    }]),
							    title: {
							        text: '气泡图'
							    },
							    legend: {
							        right: 10,
							        data: ['1990', '2015']
							    },
							    xAxis: {
							        splitLine: {
							            lineStyle: {
							                type: 'dashed'
							            }
							        }
							    },
							    yAxis: {
							        splitLine: {
							            lineStyle: {
							                type: 'dashed'
							            }
							        },
							        scale: true
							    },
							    series: [{
							        name: '1990',
							        data: data[0],
							        type: 'scatter',
							        symbolSize: function (data) {
							            return Math.sqrt(data[2]) / 5e2;
							        },
							        label: {
							            emphasis: {
							                show: true,
							                formatter: function (param) {
							                    return param.data[3];
							                },
							                position: 'top'
							            }
							        },
							        itemStyle: {
							            normal: {
							                shadowBlur: 10,
							                shadowColor: 'rgba(120, 36, 50, 0.5)',
							                shadowOffsetY: 5,
							                color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
							                    offset: 0,
							                    color: 'rgb(251, 118, 123)'
							                }, {
							                    offset: 1,
							                    color: 'rgb(204, 46, 72)'
							                }])
							            }
							        }
							    }, {
							        name: '2015',
							        data: data[1],
							        type: 'scatter',
							        symbolSize: function (data) {
							            return Math.sqrt(data[2]) / 5e2;
							        },
							        label: {
							            emphasis: {
							                show: true,
							                formatter: function (param) {
							                    return param.data[3];
							                },
							                position: 'top'
							            }
							        },
							        itemStyle: {
							            normal: {
							                shadowBlur: 10,
							                shadowColor: 'rgba(25, 100, 150, 0.5)',
							                shadowOffsetY: 5,
							                color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
							                    offset: 0,
							                    color: 'rgb(129, 227, 238)'
							                }, {
							                    offset: 1,
							                    color: 'rgb(25, 183, 207)'
							                }])
							            }
							        }
							    }]
							};
							
						}
						
						
						 myChart5.setOption(myChart5Op);
						
					}
			 });
		
		
		function changeData(chooseDateId){
			console.log(chooseDateId);
			
			var prvDate = new Date();
			if(chooseDateId==='yesterdayChart'){
				prvDate = new Date(prvDate.valueOf() - 1*24*60*60*1000);
			}else if(chooseDateId==='oneWeekChart'){
				prvDate = new Date(prvDate.valueOf() - 7*24*60*60*1000);
			}else if(chooseDateId==='monthChart'){
				prvDate = new Date(prvDate.valueOf() - 30*24*60*60*1000);
			}else if(chooseDateId==='sixMonthsChart'){
				prvDate = new Date(prvDate.valueOf() - 182*24*60*60*1000);
			}
			document.getElementById('hiredateMinDate').value=new Date(prvDate).format('yyyy-MM-dd'); 
			document.getElementById('hiredateMaxDate').value=new Date().format('yyyy-MM-dd');
		}
		
		
		
		
	
		//document.getElementById('')
		
		
	});
</script>
</body>
</html>
