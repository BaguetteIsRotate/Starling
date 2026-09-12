package com.baguetteisrotate.starling.mood;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.baguetteisrotate.starling.graphing.Graph;
import com.baguetteisrotate.starling.Tracker;
import com.baguetteisrotate.starling.Entry;

public class MoodUI {
    private final Tracker<MoodEntry> tracker;
    private final List<MoodEntry> entries;

    private JPanel panel;
    private JPanel visual;

    public MoodUI() {
        tracker = new Tracker<MoodEntry>(Path.of("mood.json"), MoodEntry.class); 
        entries = new ArrayList<>(tracker.loadData());
    }

    public JPanel makePanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        showButtonsies();
        return panel;
}

    public void showButtonsies() {
        JPanel content = new JPanel(new BorderLayout(0, 20));

        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel label = new JLabel("Rate your day on a scale of 1-5!", JLabel.CENTER);
        content.add(label, BorderLayout.CENTER);

        JPanel small = new JPanel();
        small.setLayout(new GridLayout(1, 5));

        for (int i = 1; i <= 5; i++) {
            small.add(makeButton(i));
        }

        content.add(small, BorderLayout.SOUTH); 
        panel.add(content, BorderLayout.CENTER);
    }

    public JButton makeButton(int x) {
            JButton button = new JButton(x + "");
            button.addActionListener(e -> {
                MoodEntry entry = new MoodEntry(
                    ZonedDateTime.now(),
                    x
            );

            entries.add(entry);
            tracker.save(entries);

            panel.removeAll();
            showButtonsies();
            panel.revalidate();
            panel.repaint();
        });
        return button;
    }
}
