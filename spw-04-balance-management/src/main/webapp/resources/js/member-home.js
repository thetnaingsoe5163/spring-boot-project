let balanceChartRoot = null
let incomesPieChart = null
let expensesPieChart = null

document.addEventListener("DOMContentLoaded", () => {

	const loadData = (url, setData) => {
		fetch(url, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(resp => {
			if (!resp.ok) {
				throw new Error(`API Error: ${resp.status}`)
			}
			return resp.json()
		}).then(setData)
			.catch(error => {
				console.log(`Error while API Fetching: ${error}`)
			})
	}

	am5.ready(() => {

		const loadSummary = (url) => loadData(url, data => {
			document.getElementById('incomes').innerText = data.incomes
			document.getElementById('expenses').innerText = data.expenses
			document.getElementById('balances').innerText = data.balances
		})
		
		const loadBalance = (url) => loadData(url, data => {
			loadBalanceChart(data)
		})
	
		const loadLedgers = (url) => loadData(url, data => {
			loadPieChart(data.Incomes, 'incomesChart')
			loadPieChart(data.Expenses, 'expensesChart')
		})
		
		const monthlyInput = document.getElementById('monthly')
		const yearlyInput = document.getElementById('yearly')

		if (monthlyInput && yearlyInput) {

			loadSummary(monthlyInput.dataset['summaryUrl'])
			loadBalance(monthlyInput.dataset['balanceUrl'])
			loadLedgers(monthlyInput.dataset['ledgersUrl'])

			monthlyInput.addEventListener('click', () => {
				loadSummary(monthlyInput.dataset['summaryUrl'])
				loadBalance(monthlyInput.dataset['balanceUrl'])
				loadLedgers(monthlyInput.dataset['ledgersUrl'])
			})

			yearlyInput.addEventListener('click', () => {
				loadSummary(yearlyInput.dataset['summaryUrl'])
				loadBalance(yearlyInput.dataset['balanceUrl'])
				loadLedgers(monthlyInput.dataset['ledgersUrl'])
			})


		}
	})
})

function loadBalanceChart(data) {
	
	if(balanceChartRoot) {
		balanceChartRoot.dispose()
	}

	var root = am5.Root.new("chartdiv");
	balanceChartRoot = root
	
	// Set themes
	// https://www.amcharts.com/docs/v5/concepts/themes/
	root.setThemes([
		am5themes_Animated.new(root)
	]);


	// Create chart
	// https://www.amcharts.com/docs/v5/charts/xy-chart/
	var chart = root.container.children.push(am5xy.XYChart.new(root, {
		panX: false,
		panY: false,
		paddingLeft: 0,
		wheelX: "panX",
		wheelY: "zoomX",
		layout: root.verticalLayout
	}));


	// Add legend
	// https://www.amcharts.com/docs/v5/charts/xy-chart/legend-xy-series/
	var legend = chart.children.push(
		am5.Legend.new(root, {
			centerX: am5.p50,
			x: am5.p50
		})
	);

	// Create axes
	// https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
	var xRenderer = am5xy.AxisRendererX.new(root, {
		cellStartLocation: 0.1,
		cellEndLocation: 0.9,
		minorGridEnabled: true
	})

	var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
		categoryField: "date",
		renderer: xRenderer,
		tooltip: am5.Tooltip.new(root, {})
	}));

	xRenderer.grid.template.setAll({
		location: 1
	})

	xAxis.data.setAll(data);

	var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
		renderer: am5xy.AxisRendererY.new(root, {
			strokeOpacity: 0.1
		})
	}));


	// Add series
	// https://www.amcharts.com/docs/v5/charts/xy-chart/series/
	function makeSeries(name, fieldName) {
		var series = chart.series.push(am5xy.ColumnSeries.new(root, {
			name: name,
			xAxis: xAxis,
			yAxis: yAxis,
			valueYField: fieldName,
			categoryXField: "date"
		}));

		series.columns.template.setAll({
			tooltipText: "{name}, {categoryX}:{valueY}",
			width: am5.percent(90),
			tooltipY: 0,
			strokeOpacity: 0
		});

		series.data.setAll(data);

		// Make stuff animate on load
		// https://www.amcharts.com/docs/v5/concepts/animations/
		series.appear();

		series.bullets.push(function() {
			return am5.Bullet.new(root, {
				locationY: 0,
				sprite: am5.Label.new(root, {
					text: "{valueY}",
					fill: root.interfaceColors.get("alternativeText"),
					centerY: 0,
					centerX: am5.p50,
					populateText: true
				})
			});
		});

		legend.data.push(series);
	}

	makeSeries("Incomes", "incomes");
	makeSeries("Expenses", "expenses");


	// Make stuff animate on load
	// https://www.amcharts.com/docs/v5/concepts/animations/
	chart.appear(1000, 100);
}

function loadPieChart(data, chartdiv) {
	
	var root = null
	
	if(chartdiv === 'incomesChart') {
		if(incomesPieChart) {
			incomesPieChart.dispose()
		}
	} else {
		if(expensesPieChart) {
			expensesPieChart.dispose()
		}
	}
	
	var root = am5.Root.new(chartdiv);
	
	if(chartdiv === 'incomesChart') {
		incomesPieChart = root
	} else {
		expensesPieChart = root
	}
	
	// Set themes
	// https://www.amcharts.com/docs/v5/concepts/themes/
	root.setThemes([
		am5themes_Animated.new(root)
	]);


	// Create chart
	// https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/
	var chart = root.container.children.push(am5percent.PieChart.new(root, {
		layout: root.verticalLayout
	}));


	// Create series
	// https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/#Series
	var series = chart.series.push(am5percent.PieSeries.new(root, {
		valueField: "value",
		categoryField: "ledger"
	}));

	series.labels.template.set("forceHidden", true);
	series.ticks.template.set("forceHidden", true);

	// Set data
	// https://www.amcharts.com/docs/v5/charts/percent-charts/pie-chart/#Setting_data
	series.data.setAll(data);


	// Play initial series animation
	// https://www.amcharts.com/docs/v5/concepts/animations/#Animation_of_series
	series.appear(1000, 100);

}

