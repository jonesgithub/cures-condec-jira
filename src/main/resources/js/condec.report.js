/*
 This module fills the box plots and pie charts used in the report page.

 Requires
 * echart

 Is referenced in HTML by
 * decisionKnowledgeReport.vm
 */
(function(global) {

	/** holds references to echarts.js objects */
	var charts = []
	/** dataIndex last time clicked within some chart */
	var dataIndexClicked = null // null is invalid index
	/* data/text last time clicked within some chart */
	var dataClicked = null // null is invalid
	/* reference to chart html container last time clicked */
	var node = null // null is invalid

	var ConDecReport = function ConDecReport() {
	};

	ConDecReport.prototype.showClickedSource = function(chart, dataIndexClicked, dataClicked) {
		alert(chart.issuesForData[dataIndexClicked])
	}

	ConDecReport.prototype.setClickedChart = function(domNode) {
		node = domNode;
	}

	ConDecReport.prototype.setClickedData = function(idx,data) {
		dataIndexClicked = idx;
		dataClicked = data;
	}

	ConDecReport.prototype.initializeDivWithBoxPlot = function initializeDivWithBoxPlot(divId, dataMap, xAxis, title) {
		var boxplot = echarts.init(document.getElementById(divId));
		var data = echarts.dataTool.prepareBoxplotData(new Array(dataMap));
		boxplot.setOption(getOptionsForBoxplot(title, xAxis, "", data));
		document.getElementById(divId).setAttribute("list", data);
		boxplot.on('click',echartDataClicked)
		charts.push(boxplot)
	};

	ConDecReport.prototype.initializeDivWithBoxPlotFromMap = function initializeDivWithBoxPlotFromMap(divId, dataMap,
			xAxis, title) {
		var listToShowUserWithAllValues = "";
		var values = [];
		for (var i = Array.from(dataMap.keys()).length - 1; i >= 0; i--) {
			var key = Array.from(dataMap.keys())[i];
			var value = dataMap.get(key);
			listToShowUserWithAllValues = listToShowUserWithAllValues + key + ": " + value + "; ";
			values.push(value);
		}

		var boxplot = echarts.init(document.getElementById(divId));
		var data = echarts.dataTool.prepareBoxplotData(new Array(values));
		boxplot.setOption(getOptionsForBoxplot(title, xAxis, "", data));
		document.getElementById(divId).setAttribute("list", listToShowUserWithAllValues);
		boxplot.on('click',echartDataClicked)
		charts.push(boxplot)
	};

	ConDecReport.prototype.initializeDivWithPieChart = function initializeDivWithPieChart(divId, title, subtitle,
			dataMap) {
		var data = [];
		var list = "";

		for (var i = Array.from(dataMap.keys()).length - 1; i >= 0; i--) {
			var key = Array.from(dataMap.keys())[i];
			var entry = new Object();
			entry["value"] = dataMap.get(key);
			entry["name"] = key;
			data.push(entry);
			list = list + " " + key + ": " + dataMap.get(key) + "; ";
		}

		var piechart = echarts.init(document.getElementById(divId));
		piechart.setOption(getOptionsForPieChart(title, subtitle, Array.from(dataMap.keys()), data));
		piechart.on('click',echartDataClicked)
		charts.push(piechart)
		document.getElementById(divId).setAttribute("list", list);
	};

	ConDecReport.prototype.initializeDivWithPieChartIssueData = function (divId, title, subtitle,
			issuesMap) {
		var domElement = document.getElementById(divId);
		var data = [];
		var issues = [];

		var issuesCounter = function(accumulator, currentValue) {
			if (currentValue.trim()==="") {
				return accumulator + 0;
			}
			return accumulator + 1
		}

		var dataAsArray = Array.from(issuesMap.keys());
		for (var i = dataAsArray.length - 1; i >= 0; i--) {
			var key = dataAsArray[i];
			var value = issuesMap.get(key)
			var entry = new Object();
			if (typeof value === 'string' || value instanceof String) {
				entry["value"] = value.split(' ').reduce(issuesCounter,0);
			}
			else {
				entry["value"] = value;
			}
			entry["name"] = key;
			data.push(entry);
			issues.push(value);
		}


		var piechart = echarts.init(domElement);
		piechart.setOption(getOptionsForPieChart(title, subtitle, dataAsArray, data));
		piechart.on('click',echartDataClicked);
		domElement.addEventListener('click', echartDivClicked,{capture : true, once : false, passive : false})
		piechart.issuesForData = issues;
		charts.push(piechart)
	};

	function getOptionsForBoxplot(name, xLabel, ylabel, data) {
		return {
			title : [ {
				text : name,
				left : "center",
			}, ],
			tooltip : {
				trigger : "item",
				axisPointer : {
					type : "shadow"
				}
			},
			grid : {
				left : "15%",
				right : "10%",
				bottom : "15%"
			},
			xAxis : {
				type : "category",
				data : data.axisData,
				boundaryGap : true,
				nameGap : 30,
				splitArea : {
					show : false
				},
				axisLabel : {
					formatter : xLabel
				},
			},
			yAxis : {
				type : "value",
				name : ylabel,
				splitArea : {
					show : true
				}
			},
			series : [ {
				name : "boxplot",
				type : "boxplot",
				data : data.boxData,

			}, {
				name : "outlier",
				type : "scatter",
				data : data.outliers
			} ]
		};
	}

	function getOptionsForPieChart(title, subtitle, dataKeys, dataMap) {
		return option = {
			title : {
				text : title,
				subtext : subtitle,
				x : "center"
			},
			tooltip : {
				trigger : "item",
				formatter : "{b} : {c} ({d}%)"
			},
			legend : {
				orient : "horizontal",
				bottom : "bottom",
				data : dataKeys
			},
			series : [ {
				type : "pie",
				radius : "60%",
				center : [ "50%", "50%" ],
				data : dataMap,
				itemStyle : {
					emphasis : {
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : "rgba(0, 0, 0, 0.5)"
					}
				},
				avoidLabelOverlap : true,
				label : {
					normal : {
						show : false,
						position : 'center'
					},
					emphasis : {
						show : false
					}
				},
				labelLine : {
					normal : {
						show : false
					}
				}
			} ]
		};
	}

	global.conDecReport = new ConDecReport();
})(window);

function echartDataClicked(param) {
	if (typeof param.seriesIndex != 'undefined') {
		conDecReport.showClickedSource(this,param.dataIndex,param.data)
	}
	else {
		conDecReport.setClickedData(null,null)
	}
}
