package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Graph extends JFrame {
    private final int DEFAULT_PADDING = 0;

    public Graph(Map<String, Integer> map){
        init(map);
    }

    private void init(Map<String,Integer> map) {
        CategoryDataset dataset = createDataset(map);
        JFreeChart chart = createChart(dataset );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(DEFAULT_PADDING,DEFAULT_PADDING,DEFAULT_PADDING,DEFAULT_PADDING));
        chartPanel.setBackground(Color.WHITE);
        add(chartPanel);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 8));

        pack();
        setTitle("Объекты спорта в областях");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Объекты спорта в областях",
                "",
                "Количество объектов",
                dataset
        );
        return chart;
    }

    private CategoryDataset createDataset(Map<String,Integer> map) {
        var dataset = new DefaultCategoryDataset();
        map.forEach((key, value) -> {
            dataset.setValue(value, "Области", key);
        });
        return dataset;
    }
}
