document.addEventListener("DOMContentLoaded", () => {
	
	const monthly = document.getElementById('monthly')
	const yearly = document.getElementById('yearly')
	
	am5.ready(() => {
		if(monthly) {
			loadData(monthly)	
		}
		if(monthly && yearly) {
			monthly.addEventListener('click', () => {
				loadData(monthly)
			})
				
			yearly.addEventListener('click', () => {
				loadData(yearly)
			})
		}
	});
});

function loadLineGraph(data) {
	// Dispose previous root if exists
	if (am5.registry.rootElements.length) {
		am5.registry.rootElements.forEach(root => root.dispose());
	}
	// Create root element
	// https://www.amcharts.com/docs/v5/getting-started/#Root_element
	var root = am5.Root.new("adminChart");


	// Set themes
	// https://www.amcharts.com/docs/v5/concepts/themes/
	root.setThemes([
	  am5themes_Animated.new(root)
	]);


	// Create chart
	// https://www.amcharts.com/docs/v5/charts/xy-chart/
	var chart = root.container.children.push(am5xy.XYChart.new(root, {
	  panX: true,
	  panY: true,
	  wheelX: "panX",
	  wheelY: "zoomX",
	  pinchZoomX:true,
	  paddingLeft: 0
	}));


	// Add cursor
	// https://www.amcharts.com/docs/v5/charts/xy-chart/cursor/
	var cursor = chart.set("cursor", am5xy.XYCursor.new(root, {
	  behavior: "none"
	}));
	cursor.lineY.set("visible", false);

	// Create axes
	// https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
	var xAxis = chart.xAxes.push(am5xy.DateAxis.new(root, {
	  maxDeviation: 0.2,
	  baseInterval: {
	    timeUnit: "day",
	    count: 1
	  },
	  renderer: am5xy.AxisRendererX.new(root, {
	    minorGridEnabled:true
	  }),
	  tooltip: am5.Tooltip.new(root, {})
	}));

	var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
	  renderer: am5xy.AxisRendererY.new(root, {
	    pan:"zoom"
	  })  
	}));


	// Add series
	// https://www.amcharts.com/docs/v5/charts/xy-chart/series/
	var series = chart.series.push(am5xy.LineSeries.new(root, {
	  name: "Series",
	  xAxis: xAxis,
	  yAxis: yAxis,
	  valueYField: "value",
	  valueXField: "date",
	  tooltip: am5.Tooltip.new(root, {
	    labelText: "{valueY}"
	  })
	}));


	// Add scrollbar
	// https://www.amcharts.com/docs/v5/charts/xy-chart/scrollbars/
	chart.set("scrollbarX", am5.Scrollbar.new(root, {
	  orientation: "horizontal"
	}));


	// Set data
	series.data.setAll(data);


	// Make stuff animate on load
	// https://www.amcharts.com/docs/v5/concepts/animations/
	series.appear(1000);
	chart.appear(1000, 100);
}

function loadData(type) {
	const url = type.dataset['restApi']
	fetch(url, {
		method : 'GET',
		headers : {
			'Content-Type' : 'application/json' 
		}
	}).then(resp => {
		if(!resp.ok) {
			throw new Error(`API Error: ${resp.status}`)
		}
		return resp.json()
	}).then(data => {
		const convertedData = data.map(item => ({
			...item, date : new Date(item.date).getTime()
		}))
		console.log(convertedData)
		loadLineGraph(convertedData)
	}).catch(error => {
		console.log(`Error while API Fetching: ${error}`)
	}) 
}
