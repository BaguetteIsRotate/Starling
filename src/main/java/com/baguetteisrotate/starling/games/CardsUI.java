package com.baguetteisrotate.starling.games;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.baguetteisrotate.starling.games.CardsGame.Card;

public class CardsUI {
    private JPanel panel;
    private JPanel theMother;
    private TextArea statsArea;
    private JButton continueahhh;
    private int currscore;
    private Card[] cards;
    private int currstreak;
    private String currmessage;
    private CardsUI x;
    private HashMap<String, Integer> statmap = CardsStats.load();
    private ArrayList<JButton> listiesOfButtonsies = new ArrayList<>();
    private CardsGame currentGame;

    /**
     * 
     * @return
     */
    public JPanel getPanel() {
        makeJPanel(currstreak, currscore, cards, currmessage, x);
        return panel;
    }

    private JPanel makeJPanel(int curr_score, int number, Card[] cards, String message, CardsUI card) {
        // make main JPanel
        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());

        // make title JPanel
        JPanel title = new JPanel();
        JLabel label = new JLabel("Cards");
        title.add(label);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, BorderLayout.NORTH);

        // make game JPanel
        JPanel game = new JPanel();
        game.setLayout(new GridLayout(3, 2, 8, 8));
        game.setMinimumSize(new Dimension(0, 115));
        game.setPreferredSize(new Dimension(0, 115));
        panel.add(game, BorderLayout.CENTER);

        // initialize buttons
        listiesOfButtonsies.clear();
        currentGame = new CardsGame(6, 1, 20);
        CardsGame.Card[] cards2 = currentGame.getCards();
        for (CardsGame.Card c : cards2) {
            JButton button = makeButton(c);
            listiesOfButtonsies.add(button);
            game.add(button);
        }

        // make stat JPanel
        JPanel panelsiesOfButtonsies = new JPanel(new BorderLayout());

        if (this.statsArea == null) {
            this.statsArea = new TextArea();
        }
        this.statsArea.setText("Memorize the cards... \n");
        panelsiesOfButtonsies.add(this.statsArea, BorderLayout.CENTER);

        JPanel happybuttonsies = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.continueahhh = new JButton("Next Round");
        this.continueahhh.setEnabled(false);
        this.continueahhh.addActionListener(e -> {
            updatePanel();
        });

        JButton buttonofDeatttthhhh = new JButton("End Current Game");
        buttonofDeatttthhhh.addActionListener(e -> {
            if (theMother != null) {
                theMother.removeAll();
                JButton button = new JButton("Play Cards");
                button.setPreferredSize(new Dimension(100, 100));
                button.addActionListener(f -> {
                    addGametoPanel(this.theMother);
                });
                theMother.add(button);
                theMother.revalidate();
                theMother.repaint();
            }
        });

        happybuttonsies.add(this.continueahhh);
        happybuttonsies.add(buttonofDeatttthhhh);
        panelsiesOfButtonsies.add(happybuttonsies, BorderLayout.SOUTH);
        panel.add(panelsiesOfButtonsies, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Adds the game to a given JPanel and initializes the game.
     * 
     * @param panel2 the JPanel which the game is adde to
     */
    public void addGametoPanel(JPanel panel2) {
        this.theMother = panel2;
        this.x = this.x;
        statmap = CardsStats.load();
        statmap.put("total_games", statmap.get("total_games") + 1);

        updatePanel();
    }

    private void updatePanel() {
        CardsGame g = new CardsGame(6, 1, 20);
        CardsGame.makeCards(6, 1, 20);
        currmessage = "Which card had the number " + g.makeQuestion() + "?";

        if (theMother != null) {
            if (this.panel != null) {
                theMother.remove(this.panel);
            }
            this.panel = makeJPanel(currstreak, currscore, g.getCards(), currmessage, this);
            theMother.add(this.panel);
            theMother.revalidate();
            theMother.repaint();

            startTheBomb();
        }
    }

    public void screamOutTheStatsPlease(String whoDied) {
        if (this.statsArea != null) {
            this.statsArea.setText(whoDied + "\n Score: "
                    + this.currscore + "\n Streak: " + this.currstreak + "\n Highest Score:"
                    + statmap.get("highest_score") + "\n Highest Streak: " + statmap.get("highest_streak"));
        }
        if (this.continueahhh != null) {
            this.continueahhh.setEnabled(true);
        }
    }

    private void startTheBomb() {
        for (JButton bhutan : listiesOfButtonsies) {
            bhutan.setEnabled(false);
        }

        Timer timer = new Timer(10000, e -> {
            for (JButton bhutan : listiesOfButtonsies) {
                bhutan.setText("?");
                bhutan.setEnabled(true);
            }
            if (this.statsArea != null) {
                this.statsArea.setText(currmessage + "\n Score: "
                        + this.currscore + "\n Streak: " + this.currstreak + "\n Highest Score:"
                        + statmap.get("highest_score") + "\n Highest Streak: " + statmap.get("highest_streak"));
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public JButton makeButton(CardsGame.Card card) {
        JButton button = new JButton(String.valueOf(card.getNum()));
        button.setMinimumSize(new Dimension(0, 115));
        button.setPreferredSize(new Dimension(0, 115));
        button.addActionListener(e -> {
            String outcome = "";
            if (currentGame.isCorrect(card)) {
                outcome = "Correct! ";
                statmap.put("total_wins", statmap.get("total_wins") + 1);
            } else {
                outcome = "Incorrect! ";
            }
            button.setText(String.valueOf(card.getNum()));
            if (currscore > statmap.get("highest_score")) {
                statmap.put("highest_score", currscore);
            }
            statmap.put("curr_score", currscore);
            statmap.put("curr_streak", currstreak);
            if (statmap.get("highest_streak") < currstreak) {
                statmap.put("highest_streak", currstreak);
            }
            CardsStats stats = new CardsStats();
            stats.set(statmap);
            stats.save();
            screamOutTheStatsPlease(outcome);
            if (panel != null) {
                for (java.awt.Component comp : panel.getComponents()) {
                    if (comp instanceof JPanel) {
                        for (java.awt.Component internalComp : ((JPanel) comp).getComponents()) {
                            if (internalComp instanceof JButton
                                    && !"Continue".equals(((JButton) internalComp).getText())
                                    && !"End Current Game".equals(((JButton) internalComp).getText())) {
                                internalComp.setEnabled(false);
                            }
                        }
                    }
                }
            }
        });
        return button;
    }
}
