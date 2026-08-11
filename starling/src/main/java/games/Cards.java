package games;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.plaf.basic.BasicArrowButton;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Cards {
    private Card answer;
    private int[] cards;
    private int currscore;
    private int currstreak;
    private String currmessage;
    private HashMap<String, Integer> statmap = load();
    private Cards x;
    private JPanel panel;
    private JPanel theMother;
    private JLabel statsArea;
    private JButton continueahhh;
    private ArrayList<JButton> listiesofbuttonsies = new ArrayList<>();

    public JPanel getPanel() {
        makeJPanel(currstreak, currscore, cards, currmessage, x);
        return panel;
    }

    private JPanel makeJPanel(int curr_score, int number, int[] cards, String message, Cards card) {
        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());
        JPanel title = new JPanel();
        JLabel label = new JLabel("Cards");
        title.add(label);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, BorderLayout.NORTH);

        JPanel game = new JPanel();
        game.setLayout(new GridLayout(2, 4, 8, 8));
        listiesofbuttonsies.clear();

        int index = 0;
        if (cards != null) {
            for (int i : cards) {
                Card card1 = new Card(i, index);
                index++;
                JButton bhutan = card1.makeButton(card);
                listiesofbuttonsies.add(bhutan);
                game.add(bhutan);
            }
        }
        panel.add(game, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());

        if (this.statsArea == null) {
            this.statsArea = new JLabel();
            this.statsArea.setHorizontalAlignment(SwingConstants.CENTER);
        }
        this.statsArea.setText("Memorize the cards or you shall suffers");
        southPanel.add(this.statsArea, BorderLayout.CENTER);

        JPanel happybuttonsies = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.continueahhh = new JButton("I WISH TO CONTINUE :D");
        this.continueahhh.setEnabled(false);
        this.continueahhh.addActionListener(e -> {
            nextiesheehee();
        });

        JButton buttonofDeatttthhhh = new JButton("KILL GAME");
        buttonofDeatttthhhh.addActionListener(e -> {
            if (theMother != null) {
                theMother.removeAll();
                JButton button = new JButton("PLAY CARDS!!!");
                button.addActionListener(f -> {
                    gamegamegamegame(this.theMother);
                });
                theMother.add(button);
                theMother.revalidate();
                theMother.repaint();
            }
        });

        happybuttonsies.add(this.continueahhh);
        happybuttonsies.add(buttonofDeatttthhhh);
        southPanel.add(happybuttonsies, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);
        return panel;
    }

    public void gamegamegamegame(JPanel panel2) {
        this.theMother = panel2;
        this.x = this;
        statmap = load();
        statmap.put("total_games", statmap.get("total_games") + 1);

        nextiesheehee();
    }

    private void nextiesheehee() {
        makeCards(8, 1, 20);
        currmessage = makeQuestion();

        if (theMother != null) {
            if (this.panel != null) {
                theMother.remove(this.panel);
            }
            this.panel = makeJPanel(currstreak, currscore, getCards(), currmessage, this);
            theMother.add(this.panel);
            theMother.revalidate();
            theMother.repaint();

            startTheBomb();
        }
    }

    private void startTheBomb() {
        for (JButton bhutan : listiesofbuttonsies) {
            bhutan.setEnabled(false);
        }

        Timer timer = new Timer(10000, e -> {
            for (JButton bhutan : listiesofbuttonsies) {
                bhutan.setText("?");
                bhutan.setEnabled(true);
            }
            if (this.statsArea != null) {
                this.statsArea.setText(currmessage + "\n Score: "
                        + this.currscore + ", Streak: " + this.currstreak +"\n"+ ", Highest Score:"+statmap.get("highest_score")+", Highest Streak: "+statmap.get("highest_streak"));
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void updateLiveStats(String whoDied) {
        if (this.statsArea != null) {
            this.statsArea.setText(whoDied + "<br>Score: "
                    + this.currscore+", Streak: " + this.currstreak +"\n"+ ", Highest Score:"+statmap.get("highest_score")+", Highest Streak: "+statmap.get("highest_streak"));
        }
        if (this.continueahhh != null) {
            this.continueahhh.setEnabled(true);
        }
    }

    public void makeCards(int x, int lowbound, int highbound) {
        int[] cards = new int[x];
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < x; i++) {
            int t = (int) (Math.random() * (highbound - lowbound) + lowbound);
            while (set.contains(t)) {
                t = (int) (Math.random() * (highbound - lowbound) + lowbound);
            }
            set.add(t);
            cards[i] = t;
        }
        this.cards = cards;
    }

    public String makeQuestion() {
        int t = (int) (Math.random() * (this.cards.length - 1));
        this.answer = new Card(this.cards[t], t);
        return "Which card had the number " + this.cards[t] + "?";
    }

    public int[] getCards() {
        return this.cards;
    }

    public boolean isCorrect(int c) {
        Card card = new Card(answer.getNum(), c);
        if (answer.getIndex() == card.getIndex()) {
            currscore = currscore + 5;
            currstreak += 1;
            if (this.currstreak > 0) {
                currscore = currscore + 5 * this.currstreak;
            }
            return true;
        } else {
            if (currstreak > 0) {
                currstreak = 0;
                currscore = currscore - 5;
            } else {
                currscore -= 10;
            }
            return false;
        }
    }

    public int getScore() {
        return this.currscore;
    }

    public int getStreak() {
        return this.currstreak;
    }

    public String printStat() {
        return "Score: " + this.currscore + ", Streak: " + this.currstreak+", High Score: "+statmap.get("highest_score")+", Highest Streak: "+statmap.get("highest_streak");
    }

    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("starling/src/main/java/games/Cards.json");
            TypeReference<HashMap<String, HashMap<String, Integer>>> typeRef = new TypeReference<HashMap<String, HashMap<String, Integer>>>() {
            };
            HashMap<String, HashMap<String, Integer>> map = mapper.readValue(file, typeRef);
            map.put("cards", statmap);
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter("starling/src/main/java/games/Cards.json")));
            writer.println(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> load() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("starling/src/main/java/games/Cards.json");
            TypeReference<HashMap<String, HashMap<String, Integer>>> typeRef = new TypeReference<HashMap<String, HashMap<String, Integer>>>() {
            };
            HashMap<String, HashMap<String, Integer>> map = mapper.readValue(file, typeRef);
            HashMap<String, Integer> smallMap = map.get("cards");
            if (smallMap == null) {
                smallMap = new HashMap<>();
                smallMap.put("highest_score", 0);
                smallMap.put("total_games", 0);
                smallMap.put("total_wins", 0);
                smallMap.put("curr_score", 0);
                smallMap.put("highest_streak", 0);
                smallMap.put("curr_streak", 0);
            }
            return smallMap;
        } catch (Exception e) {
            HashMap<String, Integer> smallMap = new HashMap<>();
            smallMap.put("highest_score", 0);
            smallMap.put("total_games", 0);
            smallMap.put("total_wins", 0);
            smallMap.put("curr_score", 0);
            smallMap.put("highest_streak", 0);
            smallMap.put("curr_streak", 0);
            return smallMap;
        }
    }

    public class Card {
        private int num;
        private int index;

        public Card(int num, int index) {
            this.num = num;
            this.index = index;
        }

        public int getNum() {
            return this.num;
        }

        public int getIndex() {
            return this.index;
        }

        public JButton makeButton(Cards card) {
            JButton button = new JButton(String.valueOf(this.num));
            button.setPreferredSize(new Dimension(80, 115));

            button.addActionListener(e -> {
                String outcome = "";
                if (card.isCorrect(this.index)) {
                    outcome = "Correct! ";
                    statmap.put("total_wins", statmap.get("total_wins") + 1);
                } else {
                    outcome = "Incorrect! ";
                }
                button.setText(String.valueOf(this.num));
                if (currscore > statmap.get("highest_score")) {
                    statmap.put("highest_score", currscore);
                }
                statmap.put("curr_score", currscore);
                statmap.put("curr_streak", currstreak);
                if (statmap.get("highest_streak") < currstreak) {
                    statmap.put("highest_streak", currstreak);
                }
                card.save();
                card.updateLiveStats(outcome);
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
}
