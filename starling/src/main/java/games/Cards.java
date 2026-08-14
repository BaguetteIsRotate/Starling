package games;

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

import org.w3c.dom.Text;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Cards {
    //non-Swing 
    private Card answer;
    private int[] cards;
    private int currscore;
    private int currstreak;
    private String currmessage;
    private HashMap<String, Integer> statmap = load();
    private Cards x;
    //Swing
    private JPanel panel;
    private JPanel theMother;
    private TextArea statsArea;
    private JButton continueahhh;
    private ArrayList<JButton> listiesofbuttonsies = new ArrayList<>();

    






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
            button.setMinimumSize(new Dimension(0,115));
            button.setPreferredSize(new Dimension(0,115));
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
                card.screamOutTheStatsPlease(outcome);
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
