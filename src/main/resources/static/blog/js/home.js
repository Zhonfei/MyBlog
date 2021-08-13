function loadHome(){
	console.log("shuaxin...");
	var resData;
	$.ajax({
		type:"get",
		url:"/blog/usr/homePie",
		async:false,
		dataType:"json",
		success:function(data){
			resData = data;
		}
	});
	
	console.log(444444);
	new Highcharts.chart('main', {
		chart: {
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false,
				type: 'pie'
		},
		title: {
				text: '个人任务完成情况'
		},
		tooltip: {
				pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
		},
		plotOptions: {
				pie: {
						allowPointSelect: true,
						cursor: 'pointer',
						dataLabels: {
								enabled: true,
								format: '<b>{point.name}</b>: {point.percentage:.2f} %',
								style: {
										color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
								}
						}
				}
		},
		series: [{
				name: '任务数占比',
				colorByPoint: true,
				data:resData
//				data: [{
//						name: 'Chrome',
//						y: 61.41,
//						sliced: true,
//						selected: true
//				}, {
//						name: 'Internet Explorer',
//						y: 11.84
//				}, {
//						name: 'Firefox',
//						y: 10.85
//				}, {
//						name: 'Edge',
//						y: 4.67
//				}, {
//						name: 'Safari',
//						y: 4.18
//				}, {
//						name: 'Sogou Explorer',
//						y: 1.64
//				}, {
//						name: 'Opera',
//						y: 1.6
//				}, {
//						name: 'QQ',
//						y: 1.2
//				}, {
//						name: 'Other',
//						y: 2.61
//				}]
		}]
});
	console.log(555555);
	var chart = Highcharts.chart('main1', {
			    chart: {
			        type: 'line'
			    },
			    title: {
			        text: '月平均气温'
			    },
			    subtitle: {
			        text: '数据来源: WorldClimate.com'
			    },
			    xAxis: {
			        categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
			    },
			    yAxis: {
			        title: {
			            text: '气温 (°C)'
			        }
			    },
			    plotOptions: {
			        line: {
			            dataLabels: {
			                // 开启数据标签
			                enabled: true          
			            },
			            // 关闭鼠标跟踪，对应的提示框、点击事件会失效
			            //enableMouseTracking: false
			        }
			    },
			    series: [{
			        name: '东京',
			        data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
			    }, {
			        name: '伦敦',
			        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
			    }]
			});

}
