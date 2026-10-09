package com.baguetteisrotate.starling.graphing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JPanel;

import com.baguetteisrotate.starling.Tracker;
import com.baguetteisrotate.starling.games.CardEntry;
import com.baguetteisrotate.starling.mood.MoodEntry;

public class GraphUI {
    private final Tracker<MoodEntry> tracker;
    private final Tracker<CardEntry> tracker2;

    public GraphUI() {
        tracker = new Tracker<>(Path.of("mood.json"), MoodEntry.class);
        tracker2 = new Tracker<>(Path.of("cards_history.json"), CardEntry.class);
    }

    public JPanel makePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        List<MoodEntry> entries = tracker.loadData();
        Graph<MoodEntry> graph = new Graph<>("Mood over Time", "Date", "Mood", entries);

        List<CardEntry> entries2 = tracker2.loadData();
        Graph<CardEntry> graph2 = new Graph<>("Activity Performance over Time", "Date", "Score", entries2);

        JPanel chartPanel = graph.makePanel();
        JPanel chartPanel2 = graph2.makePanel();

        chartPanel.setMinimumSize(new Dimension(300, 180));
        chartPanel.setPreferredSize(new Dimension(350, 220));

        chartPanel2.setMinimumSize(new Dimension(300, 180));
        chartPanel2.setPreferredSize(new Dimension(350, 220));

        panel.add(chartPanel, BorderLayout.NORTH);
        panel.add(chartPanel2, BorderLayout.CENTER);

        return panel;
    }
}