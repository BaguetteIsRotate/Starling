package com.baguetteisrotate.starling.mood;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.baguetteisrotate.starling.graphing.Graph;

public class MoodTracker {
    private HashMap<Integer, ZonedDateTime> x;
    private HashMap<Integer, Integer> y;
    private int currnum;
    private Graph graph;
    private JPanel panel;
    private JPanel visual;

    public MoodTracker() {
        this.x = new HashMap<Integer, ZonedDateTime>();
        this.y = new HashMap<Integer, Integer>();
        this.currnum = 0;
    }

    public JPanel makePanel(String path, boolean showGraph) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        graph = new Graph("Mood over Time","Date",this.x,"Mood",this.y);

        graph.addPath(path);

        if (graph.loadDataFromPath()) {
            this.x = graph.getXvalues();
            this.y = graph.getYvalues();
        }

        visual = graph.makePanel(showGraph, panel);
        panel.add(visual, BorderLayout.CENTER);

        if (!showGraph) {
            JButton button1 = makeButton(1, showGraph);
            JButton button2 = makeButton(2, showGraph);
            JButton button3 = makeButton(3, showGraph);
            JButton button4 = makeButton(4, showGraph);
            JButton button5 = makeButton(5, showGraph);

            JPanel small = new JPanel();
            small.setLayout(new GridLayout(1, 5));

            small.add(button1);
            small.add(button2);
            small.add(button3);
            small.add(button4);
            small.add(button5);

            panel.add(small, BorderLayout.SOUTH);
        }

        return panel;
}

    public JButton makeButton(int x, boolean showGraph) {
        JButton button = new JButton(x + "");
        button.addActionListener(e -> {
            ZonedDateTime time = ZonedDateTime.now();
            this.x = graph.getXvalues();
            this.y = graph.getYvalues();
            this.x.put(currnum, time);
            this.y.put(currnum, x);
            graph.reload("Mood over Time", "Date", this.x, "Mood", this.y);
            graph.save("mood.json");
                panel.remove(visual);
                visual = graph.makePanel(showGraph, panel);
                panel.add(graph.makePanel(showGraph,panel));
                panel.revalidate();
                panel.repaint();
            currnum += 1;
        });
        return button;
    }
}
