package com.baguetteisrotate.starling.mood;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.net.URL;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.baguetteisrotate.starling.Tracker;

public class MoodUI {
    private final Tracker<MoodEntry> tracker;
    private final List<MoodEntry> entries;

    private JPanel panel;

    public MoodUI() {
        tracker = new Tracker<>(Path.of("mood.json"), MoodEntry.class);
        entries = new ArrayList<>(tracker.loadData());
    }

    public JPanel makePanel() {
        panel = new JPanel(new BorderLayout());
        showButtonsies();
        return panel;
    }

    public void showButtonsies() {
        panel.removeAll();

        JPanel content = new JPanel(new BorderLayout(0, 20));
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel label = new JLabel("Rate your day on a scale of 1-5!", JLabel.CENTER);
        content.add(label, BorderLayout.NORTH);

        JPanel small = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        for (int i = 1; i <= 5; i++) {
            small.add(makeButton(i));
        }

        content.add(small, BorderLayout.CENTER);
        JLabel label2 = new JLabel();
        label2.setPreferredSize(new Dimension(200, 200));
        content.add(label2, BorderLayout.SOUTH);
        panel.add(content, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    public JButton makeButton(int x) {
        JButton button = new JButton(String.valueOf(x));
        Dimension buttonSize = new Dimension(44, 44);
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.addActionListener(e -> {
            MoodEntry entry = new MoodEntry(ZonedDateTime.now(), x);
            entries.add(entry);
            tracker.save(entries);
            showButtonsies();
        });
        return button;
    }
}
