package com.baguetteisrotate.starling.graphing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

public class Graph {
    private String xlabel;
    private String ylabel;
    private String title;
    private HashMap<Integer, ZonedDateTime> xvalues;
    private HashMap<Integer, Integer> yvalues;
    private HashMap<String, Object> map = new HashMap<>();
    private String path = "";

    // constructor method with input values
    public Graph(String title, String xlabel, HashMap<Integer, ZonedDateTime> xvalues, String ylabel,
            HashMap<Integer, Integer> yvalues) {
        this.title = title;
        this.xlabel = xlabel;
        this.ylabel = ylabel;
        this.xvalues = xvalues;
        this.yvalues = yvalues;
        map.put("xlabel", this.xlabel);
        map.put("ylabel", this.ylabel);
        map.put("xval", this.xvalues);
        map.put("yval", this.yvalues);
        map.put("title", this.title);
    }

    // constructor method that takes in path
    public Graph(String path) {
        this.path = path;
    }

    // One must imagine sisyphus happy and run loadDataFromPath if they did the
    // second constructor
    @SuppressWarnings("unchecked")
    public boolean loadDataFromPath() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            File file = new File(this.path);
            Map<String, Object> map = mapper.readValue(
                    file,
                    new TypeReference<Map<String, Object>>() {
                    });
            this.xlabel = (String) map.get("xlabel");
            this.ylabel = (String) map.get("ylabel");
            this.title = (String) map.get("title");
            this.xvalues = mapper.convertValue(
                    map.get("xval"),
                    new TypeReference<HashMap<Integer, ZonedDateTime>>() {
                    });

            this.yvalues = mapper.convertValue(
                    map.get("yval"),
                    new TypeReference<HashMap<Integer, Integer>>() {
                    });
            this.map = new HashMap<>(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addPath(String path1) {
        this.path = path1;
    }

    public void reload(String title, String xlabel, HashMap<Integer, ZonedDateTime> xvalues, String ylabel,
            HashMap<Integer, Integer> yvalues) {
        this.title = title;
        this.xlabel = xlabel;
        this.ylabel = ylabel;
        this.xvalues = xvalues;
        this.yvalues = yvalues;
        map.put("xlabel", this.xlabel);
        map.put("ylabel", this.ylabel);
        map.put("xval", this.xvalues);
        map.put("yval", this.yvalues);
        map.put("title", this.title);
    }

    public boolean save() {
        return save(this.path);
    }

    public boolean save(String path1) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.map);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path1)));
            writer.println(s);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public JPanel makePanel(boolean includeGraph, JPanel mother) {
        System.out.println(xvalues);
        JPanel x = new JPanel();
        x.setLayout(new BorderLayout());
        if (!includeGraph) {
            JLabel label = new JLabel("Rate your day on a scale of 1-5!");
            x.add(label);
        } else {
            JButton button = new JButton("Reload");
            button.addActionListener(e -> {
                mother.remove(x);
                mother.add(makePanel(includeGraph, mother), BorderLayout.NORTH);
                mother.revalidate();
                mother.repaint();
            });
            x.add(button, BorderLayout.NORTH);
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            HashMap<Integer, String> labels = new HashMap<>();
            for (int i : this.xvalues.keySet()) {
                labels.put(i, this.xvalues.get(i).format(DateTimeFormatter.ofPattern("d MMM uuuu hh:mm:ss")).toString());
            }

            for (Integer i : xvalues.keySet()) {
                dataset.addValue(yvalues.get(i), "Mood", i);
            }
        

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    xlabel,
                    ylabel,
                    dataset,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
            CategoryPlot plot = chart.getCategoryPlot();
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
            LineAndShapeRenderer renderer = new LineAndShapeRenderer();
            renderer.setSeriesLinesVisible(0, false);
            java.awt.Shape circle = new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0);
            renderer.setDefaultShape(circle);
            renderer.setSeriesShapesVisible(0, true);
            plot.setRenderer(renderer);
            ChartPanel chartpanel = new ChartPanel(chart);
            chartpanel.setPreferredSize(new Dimension(350, 300));
            x.add(chartpanel, BorderLayout.CENTER);
        }
        return x;
    }

    public HashMap<Integer, ZonedDateTime> getXvalues() { return this.xvalues; } public HashMap<Integer, Integer> getYvalues() { return this.yvalues; }
}