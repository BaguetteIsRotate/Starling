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
    private HashMap<Integer, String> x;
    private HashMap<Integer, Integer> y;
    private int currnum;
    private Graph graph;
    private JPanel panel;
    private JPanel visual;

    public MoodTracker() {
        this.x = new HashMap<Integer, String>();
        this.y = new HashMap<Integer, Integer>();
        this.currnum = 0;
    }

    public JPanel makePanel(String path) {
        currnum = 0;
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        graph = new Graph("Mood over Time", "Date", this.x, "Mood", this.y);
        graph.addPath(path);
        graph.save();
        visual = graph.makePanel();
        panel.add(visual, BorderLayout.NORTH);

        JButton button1 = makeButton(1);
        JButton button2 = makeButton(2);
        JButton button3 = makeButton(3);
        JButton button4 = makeButton(4);
        JButton button5 = makeButton(5);

        JPanel small = new JPanel();
        small.setLayout(new GridLayout(1, 5));
        small.add(button1);
        small.add(button2);
        small.add(button3);
        small.add(button4);
        small.add(button5);
        panel.add(small, BorderLayout.SOUTH);
        return panel;
    }

    public JButton makeButton(int x) {
        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        JButton button = new JButton(x + "");
        button.addActionListener(e -> {
            ZonedDateTime time = ZonedDateTime.now();
            time.format(formatter);
            this.x.put(currnum, time.toString());
            this.y.put(currnum, x);
            graph.loadDataFromPath();
            graph.reload("Mood over Time", "Date", this.x, "Mood", this.y);
            graph.save("starling/src/main/java/mood.json");
            JPanel visual2 = graph.makePanel();
            panel.remove(visual);
            panel.add(visual2);
            panel.repaint();
            currnum += 1;
        });
        return button;
    }
}
