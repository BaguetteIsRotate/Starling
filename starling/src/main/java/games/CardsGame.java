package games;

import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CardsGame {
    private Card answer;
    private int[] cards;
    private int currscore;
    private int currstreak;
    private String currmessage;

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

    public String makeQuestion() {
        int t = (int) (Math.random() * (this.cards.length - 1));
        this.answer = new Card(this.cards[t], t);
        return "Which card had the number " + this.cards[t] + "?";
    }
    public int[] getCards() {
        return this.cards;
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
    }
}
