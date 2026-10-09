package com.baguetteisrotate.starling.games;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.baguetteisrotate.starling.Tracker;
import com.baguetteisrotate.starling.games.CardsGame.Card;

public class CardsUI {
    private static final Color PAGE_COLOR = new Color(255, 248, 231);

    private JPanel panel;
    private JPanel theMother;
    private JTextArea statsArea;
    private JButton continueButton;
    private String currmessage;
    private HashMap<String, Integer> statmap = CardEntry.load();
    private final ArrayList<JButton> cardButtons = new ArrayList<>();
    private CardsGame currentGame;
    private Timer memorizeTimer;
    private final Tracker<CardScoreEntry> scoreTracker =
            new Tracker<>(Path.of("cards_history.json"), CardScoreEntry.class);

    public JPanel getPanel() {
        theMother = new JPanel(new BorderLayout());
        showStartScreen();
        return theMother;
    }

    /**
     * Shows the game in the supplied container, replacing its current contents.
     *
     * @param parent the container which will display the game
     */
    public void addGametoPanel(JPanel parent) {
        theMother = Objects.requireNonNull(parent, "parent");
        theMother.setLayout(new BorderLayout());
        statmap = CardEntry.load();
        statmap.put("total_games", statmap.get("total_games") + 1);
        updatePanel();
    }

    public void resetToStart() {
        if (theMother != null) {
            showStartScreen();
        }
    }

    private void showStartScreen() {
        stopMemorizeTimer();
        theMother.setLayout(new BorderLayout());
        theMother.removeAll();
        panel = null;

        JButton playButton = new JButton("Play Cards");
        playButton.setPreferredSize(new Dimension(180, 80));
        playButton.addActionListener(e -> addGametoPanel(theMother));

        JPanel launchPanel = new JPanel(new BorderLayout());
        launchPanel.setBackground(PAGE_COLOR);
        launchPanel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        launchPanel.add(playButton, BorderLayout.CENTER);
        theMother.add(launchPanel, BorderLayout.CENTER);
        theMother.revalidate();
        theMother.repaint();
    }

    private void updatePanel() {
        stopMemorizeTimer();
        currentGame = new CardsGame(6, 1, 20);
        currmessage = "Which card had the number " + currentGame.makeQuestion() + "?";
        panel = makeGamePanel(currentGame.getCards());

        theMother.removeAll();
        theMother.add(panel, BorderLayout.CENTER);
        theMother.revalidate();
        theMother.repaint();

        startMemorizeTimer();
    }

    private JPanel makeGamePanel(Card[] cards) {
        JPanel gamePanel = new JPanel(new BorderLayout(0, 16));
        gamePanel.setBackground(PAGE_COLOR);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Cards", SwingConstants.CENTER);
        gamePanel.add(title, BorderLayout.NORTH);

        JPanel cardGrid = new JPanel(new GridLayout(2, 3, 12, 12));
        cardGrid.setBackground(PAGE_COLOR);
        cardGrid.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        cardButtons.clear();
        for (Card card : cards) {
            JButton button = makeButton(card);
            cardButtons.add(button);
            cardGrid.add(button);
        }
        gamePanel.add(cardGrid, BorderLayout.CENTER);

        statsArea = new JTextArea(5, 20);
        statsArea.setEditable(false);
        statsArea.setLineWrap(true);
        statsArea.setWrapStyleWord(true);
        statsArea.setText(getStatsText("Memorize the cards..."));
        JScrollPane statsScrollPane = new JScrollPane(statsArea);
        statsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        continueButton = new JButton("Next Round");
        continueButton.setEnabled(false);
        continueButton.addActionListener(e -> updatePanel());

        JButton endButton = new JButton("End Game");
        endButton.addActionListener(e -> showStartScreen());

        JPanel controls = new JPanel(new GridLayout(1, 2, 12, 0));
        controls.setBackground(PAGE_COLOR);
        controls.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        controls.add(continueButton);
        controls.add(endButton);

        JPanel footer = new JPanel(new BorderLayout(0, 8));
        footer.setBackground(PAGE_COLOR);
        footer.add(statsScrollPane, BorderLayout.CENTER);
        footer.add(controls, BorderLayout.SOUTH);
        gamePanel.add(footer, BorderLayout.SOUTH);
        return gamePanel;
    }

    private void startMemorizeTimer() {
        for (JButton button : cardButtons) {
            button.setEnabled(false);
        }

        memorizeTimer = new Timer(10000, e -> {
            for (JButton button : cardButtons) {
                button.setText("?");
                button.setEnabled(true);
            }
            statsArea.setText(getStatsText(currmessage));
        });
        memorizeTimer.setRepeats(false);
        memorizeTimer.start();
    }

    private void stopMemorizeTimer() {
        if (memorizeTimer != null) {
            memorizeTimer.stop();
            memorizeTimer = null;
        }
    }

    private String getStatsText(String message) {
        return message + "\nScore: " + statmap.getOrDefault("curr_score", 0)
                + "\nStreak: " + statmap.getOrDefault("curr_streak", 0)
                + "\nHighest score: " + statmap.getOrDefault("highest_score", 0)
                + "\nHighest streak: " + statmap.getOrDefault("highest_streak", 0);
    }

    public JButton makeButton(Card card) {
        JButton button = new JButton(String.valueOf(card.getNum()));
        button.addActionListener(e -> {
            boolean correct = currentGame.isCorrect(card);
            CardEntry.update(statmap, correct);

            for (int i = 0; i < cardButtons.size(); i++) {
                JButton cardButton = cardButtons.get(i);
                cardButton.setText(String.valueOf(currentGame.getCards()[i].getNum()));
                cardButton.setEnabled(false);
            }

            CardEntry stats = new CardEntry();
            stats.set(statmap);
            stats.save();
            List<CardScoreEntry> scoreHistory = scoreTracker.loadData();
            scoreHistory.add(new CardScoreEntry(ZonedDateTime.now(), statmap.get("curr_score")));
            scoreTracker.save(scoreHistory);
            statsArea.setText(getStatsText(correct ? "Correct!" : "Incorrect!"));
            continueButton.setEnabled(true);
        });
        return button;
    }
}
