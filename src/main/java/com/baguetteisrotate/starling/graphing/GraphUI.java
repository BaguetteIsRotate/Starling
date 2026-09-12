package com.baguetteisrotate.starling.graphing;

import java.util.ArrayList;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.baguetteisrotate.starling.Entry;
import com.baguetteisrotate.starling.Tracker;
import com.baguetteisrotate.starling.games.CardEntry;
import com.baguetteisrotate.starling.mood.MoodEntry;

public class GraphUI {
    private final Tracker<MoodEntry> tracker;
    private final Tracker<CardEntry> tracker2;

    public GraphUI() {
        tracker = new Tracker(Path.of("mood.json"), MoodEntry.class); 
        tracker2 = new Tracker(Path.of("cards.json"), CardEntry.class); 
    }

    public JPanel makePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        List<MoodEntry> entries = tracker.loadData();
        
        Graph graph = new Graph("Mood over Time","Date","Mood",entries);


        List<CardEntry> entries2 = tracker2.loadData();
        
        Graph graph2 = new Graph("Activity Performance over Time","Date","Score",entries2);

        JPanel chartPanel = graph.makePanel();

        JPanel chartPanel2 = graph2.makePanel();

        chartPanel.setMinimumSize(new Dimension(0, 0));
        chartPanel.setPreferredSize(new Dimension(0, 0));

        chartPanel2.setMinimumSize(new Dimension(0, 0));
        chartPanel2.setPreferredSize(new Dimension(0, 0));

        panel.add(chartPanel, BorderLayout.CENTER);

        panel.add(chartPanel2, BorderLayout.SOUTH);

        return panel;
    }
}