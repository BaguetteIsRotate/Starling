package games;

import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CardsGame {
    private Card answer;
    private Card[] cards;
    private int currscore;
    private int currstreak;
    private String currmessage;

    public CardsGame(int numCards, int lowBound, int highBound) {
        cards = makeCards(numCards, lowBound, highBound);
    }

    private static int randomInt(int lowBound, int highBound) {
        return (int) (Math.random() * (highBound - lowBound) + lowBound);
    }

    public static Card[] makeCards(int numCards, int lowBound, int highBound) {
        Card[] cards = new Card[numCards];
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < numCards; i++) {
            int t = randomInt(lowBound, highBound);
            while (set.contains(t)) {
                t = randomInt(lowBound, highBound);
            }
            set.add(t);
            cards[i] = new Card(t, i);
        }
        return cards;
    }

    public boolean isCorrect(int c) {
        return c == answer.getIndex();
    }

    public int getScore() {
        return this.currscore;
    }

    public int getStreak() {
        return this.currstreak;
    }

    // public String printStat() {
    // return "Score: " + this.currscore + ", Streak: " + this.currstreak+", High
    // Score: "+statmap.get("highest_score")+", Highest Streak:
    // "+statmap.get("highest_streak");
    // }

    public int makeQuestion() {
        int t = randomInt(0, this.cards.length);
        return t;
    }

    // public String makeQuestion() {
    // int t = (int) (Math.random() * (this.cards.length - 1));
    // this.answer = new Card(this.cards[t], t);
    // return "Which card had the number " + this.cards[t] + "?";
    // }

    public Card[] getCards() {
        return this.cards;
    }

    public static class Card {
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
