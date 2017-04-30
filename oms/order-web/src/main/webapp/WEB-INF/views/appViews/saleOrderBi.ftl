<#include "../global.ftl" encoding="utf-8">

<!DOCTYPE html>
<html class="${sys !} ${mod !}">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title></title>
		<style type="text/css">
			body,ul,a,li,img,dt,dl,dd,h1,h2,h3,h4,h5,h6,input,p,form{margin:0px;padding:0px;}
			input{font-family:"Arial","微软雅黑";}
			body{font-family:"Arial","微软雅黑"; line-height:30px; color:#666666; font-size:16px;background-color: #F2F2F2;}
			
			section{margin: 1%;}
			table{background-color: #ffffff;width: 100%; border-radius: 10px;text-align: center;padding: 1%;}
			table thead tr th{border-bottom: solid 1px #ccc;border-right: solid 1px #ccc;}
			table thead tr th:last-child{border-right: none;}
			
			table tbody tr td{border-right: solid 1px #ccc;}
			table tbody tr td:last-child{border-right: none;}

			.p_title{color: #999999;margin: 1%;}
		</style>
	</head>

	<body>
		<p class="p_title">近半年销量报表 单位（元）</p>
		<section>
			<table id="salesVolumeTB" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th>月份</th>
						<th>销售总额</th>
						<th>退款总额</th>
						<th>实销总额</th>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
		 </section>
		 
		 <section>
		 	<div id="container" class="highchartCont" style="margin: 0 auto"></div>
		 </section>
		 
		<#include "../common/js.ftl" encoding="utf-8">
		<script src="${BasePath !}/asset/js/appViews/highcharts.js?v=${ver !}"></script>
		<script type="text/javascript">
			$(function() {
				var _salesVolumeList=${jsonDatas};
				var _monthTotal=[];
				var _salesVolumeTotal=[];
				var _refundTotal=[];
				var _actualSalesTotal=[];
				var _salesVolumeTBHtml='';
				var jsonData = {
                        'getDataIdent': 'Y',
                        'apiNumber': '703_delUserAddress',
                        'params': '{"addressId":""}'
                    }
				/*$.ajax({
					url:"data/salesVolume.json",
					type:"get",
					dataType: 'json',
					dataType: 'json',
					async:false,
					success:function(result){
						_salesVolumeList=result;
					},
					error: function (e) {
                            //console.log(e);
                    }
				});*/
				for (var i = 0, len = _salesVolumeList.length; i < len; ++i) { 
					_monthTotal.push(_salesVolumeList[i].month);
					_salesVolumeTotal.push(_salesVolumeList[i].salesVolumeTotal);
				  	_refundTotal.push(_salesVolumeList[i].refundTotal);
				  	var total=_salesVolumeList[i].salesVolumeTotal-_salesVolumeList[i].refundTotal;
				  	_actualSalesTotal.push(total);
				  	
				}
				
					
				for (var i = 0 ;i<_salesVolumeList.length;i++) {
					var _tbtotal=_salesVolumeList[i].salesVolumeTotal-_salesVolumeList[i].refundTotal;
				  	_salesVolumeTBHtml=_salesVolumeTBHtml+'<tr><td>'+_salesVolumeList[i].month+'</td><td>'+_salesVolumeList[i].salesVolumeTotal+'</td><td>'+_salesVolumeList[i].refundTotal+'</td><td>'+_tbtotal+'</td></tr>';
				}
					
				$("#salesVolumeTB tbody").append(_salesVolumeTBHtml);
				
				$('#container').highcharts({
					title: {
						useHTML:true,
						align: 'left',
						text: '本月实销总额：<span style="color:#EF894C">'+(_actualSalesTotal.length==0?0:_actualSalesTotal[0])+'</span>',
						x: 0 //center
					},
					chart: {
			            borderRadius: 10,
			       },
					xAxis: {
						categories: _monthTotal
					},
					credits: {
			            enabled: false
			       	},
					yAxis: {
						opposite: true,
						title: {
							text: ''
						},
						gridLineDashStyle: 'longdash',
						plotLines: [{
							value: 0,
							width: 1,
							color: '#808080'
						}]
					},
					tooltip: {
						valueSuffix: '元'
					},
					
					legend: {
		            	enabled: false
		        	},
					series: [{
						name: '实销总额',
						data: _actualSalesTotal,
						color: '#EF894C'
					}]
				});
			});
		</script>
	</body>

</html>