package simulator;
import java.util.HashMap;

import grid.Grid;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;

public class Graph {
	private VBox sidebar;
	private HashMap<Integer,Integer> stats;
	private HashMap<Integer,Series<Number,Number>> lineSeries;
	private int steps;
	private final NumberAxis xAxis = new NumberAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private final LineChart<Number, Number> lineChart;
			
	public Graph(double size) {
		lineChart = new LineChart<Number,Number>(xAxis,yAxis);
		lineSeries = new HashMap<Integer,Series<Number,Number>>();
		
		lineChart.setPrefHeight(size);
		lineChart.setPrefWidth(size);
		lineChart.setLayoutX(size);
		lineChart.setLayoutY(size);
		lineChart.setCreateSymbols(false);
		
		xAxis.setLabel("Steps");
		xAxis.setTickUnit(1);
		steps = 0;
		stats = new HashMap<Integer,Integer>();
	}
	
	public void updateGraph(Grid grid) {
		steps++;
		updateStats(grid);
	}

	private void updateStats(Grid grid) {
		HashMap<Integer, Integer> temp = grid.getStats();
		for (int key: temp.keySet()) {
			if (!stats.containsKey(key))
				createNewSeries(key);
			stats.put(key,temp.get(key));
			updateImage(key);
			
		}
		for (int key: stats.keySet())
			if (!temp.containsKey(key))
				stats.put(key,0);
	}
	private void createNewSeries(int key) {
		Series<Number, Number> series = new XYChart.Series<Number,Number>();
		series.setName(Integer.toString(key));
		lineSeries.put(key, series);
		lineChart.getData().add(series);
	}

	private void updateImage(int key) {
		Series<Number,Number> series = lineSeries.get(key);
		series.getData().add(new Data<Number, Number>(steps,stats.get(key)));
	}

	public Parent getLineGraph() {
		return lineChart;
	}
	
}
