package com.baguetteisrotate.starling.graphing;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import com.baguetteisrotate.starling.Entry;

public class Graph<T extends Entry> {
    private String xlabel;
    private String ylabel;
    private String title;
    private List<T> entries;

    private Double minimum;
    private Double maximum;

    public Graph(String title, String xlabel, String ylabel,
        List<T> entries) {
        this(title, xlabel, ylabel, entries, null, null);                
    }

    public Graph(String title, String xlabel, String ylabel,
        List<T> entries, Double minimum, Double maximum) {
        this.title = title;
        this.xlabel = xlabel;
        this.ylabel = ylabel;
        this.entries = entries;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public JPanel makePanel() {
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        TimeSeries series = new TimeSeries("Mood");

        for (Entry entry : entries) {

            ZonedDateTime time = entry.getTime();
            if (time == null) { continue; }
            Millisecond millisecond = new Millisecond(
                    java.util.Date.from(
                            time.toInstant()
                    )
            );

            series.addOrUpdate(
                    millisecond,
                    entry.getValue()
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


        if (minimum != null && maximum != null) {
                rangeAxis.setRange(minimum, maximum);
        }

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