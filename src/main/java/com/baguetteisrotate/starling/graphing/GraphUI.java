package com.baguetteisrotate.starling.graphing;

import java.util.ArrayList;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import com.baguetteisrotate.starling.mood.MoodEntry;
import com.baguetteisrotate.starling.mood.MoodTracker;

public class GraphUI {
    private final MoodTracker tracker;

    public GraphUI() {
        tracker = new MoodTracker(Path.of("mood.json")); 
    }

    public JPanel makePanel() {
        JPanel panel = new JPanel();

        List<MoodEntry> entries = tracker.loadData();
        Graph graph = new Graph("Mood over Time","Date","Mood",entries);
        panel.add(graph.makePanel(), BorderLayout.CENTER);
        return panel;
    }
}