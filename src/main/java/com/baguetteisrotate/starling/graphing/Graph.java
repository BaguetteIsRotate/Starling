package com.baguetteisrotate.starling.graphing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;

import com.baguetteisrotate.starling.mood.MoodEntry;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

public class Graph {
    private String xlabel;
    private String ylabel;
    private String title;
    private List<MoodEntry> entries;
    private HashMap<String, Object> map = new HashMap<>();
    private String path = "";

    // constructor method with input values
    public Graph(String title, String xlabel, String ylabel,
            List<MoodEntry> entries) {
        this.title = title;
        this.xlabel = xlabel;
        this.ylabel = ylabel;
        this.entries = entries;
    }

    public JPanel makePanel() {
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        TimeSeries series = new TimeSeries("Mood");

        for (MoodEntry entry : entries) {

            ZonedDateTime time = entry.getTime();
            Millisecond millisecond = new Millisecond(
                    java.util.Date.from(
                            time.toInstant()
                    )
            );

            series.addOrUpdate(
                    millisecond,
                    entry.getMood()
            );
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection(series);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            title,
            xlabel,
            ylabel,
            dataset,
            false,
            true,
            false);

        formatChart(chart);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(null);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(false);

        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;

            // CategoryPlot plot = chart.getCategoryPlot();
            // CategoryAxis domainAxis = plot.getDomainAxis();
            // domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
            // LineAndShapeRenderer renderer = new LineAndShapeRenderer();
            // renderer.setSeriesLinesVisible(0, false);
            // java.awt.Shape circle = new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0);
            // renderer.setDefaultShape(circle);
            // renderer.setSeriesShapesVisible(0, true);
            // plot.setRenderer(renderer);
            // ChartPanel chartpanel = new ChartPanel(chart);
            // chartpanel.setPreferredSize(new Dimension(350, 300));
            // x.add(chartpanel, BorderLayout.CENTER);
            // return x
    }

    private void formatChart(JFreeChart chart) {
        chart.setBackgroundPaint(null);

        chart.getTitle().setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );

        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(Color.WHITE);

        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

        plot.setDomainGridlinePaint(new Color(220, 220, 220));
        plot.setRangeGridlinePaint(new Color(220, 220, 220));

        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();

        dateAxis.setLabelFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );

        dateAxis.setTickLabelFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        dateAxis.setDateFormatOverride(
            new SimpleDateFormat("MMM d")
        );

        dateAxis.setAutoRange(true);

        NumberAxis rangeAxis =
                (NumberAxis) plot.getRangeAxis();

        rangeAxis.setRange(1.0, 5.0);

        rangeAxis.setStandardTickUnits(
                NumberAxis.createIntegerTickUnits()
        );

        rangeAxis.setLabelFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );

        rangeAxis.setTickLabelFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        XYLineAndShapeRenderer renderer =
                new XYLineAndShapeRenderer();

        renderer.setDefaultLinesVisible(true);
        renderer.setDefaultShapesVisible(true);

        plot.setRenderer(renderer);
    }

    
}