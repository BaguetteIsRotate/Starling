package starling.src.main.games;
import java.util.HashSet;
import javax.swing.JPanel;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
public class Cards {
    private Card answer;
    private int[] cards;
    private int score;
    private int streak;
    public void makeJPanel(int curr_score,int number, int[][] cards){
        //make UI
        int x =0;
        x=x+1;
    }
    public void makeCards(int x, int lowbound, int highbound){
        int[] cards = new int[x];
        HashSet<Integer> set = new HashSet<>();
        for(int i=0; i<x; i++){
            int t = (int)(Math.random()*(highbound-lowbound)+lowbound);
            while(set.contains(t)){
                t = (int)(Math.random()*(highbound-lowbound)+lowbound);
            }
            set.add(t);
            cards[i]= t;
        }
        this.cards = cards;
    }
    public String makeQuestion(){
        int t = (int)(Math.random()*(this.cards.length-1));
        this.answer = new Card(this.cards[t],t);
        return "Which card had the number "+this.cards[t]+"?";
    }
    public int[] getCards(){
        return this.cards;
    }
    public boolean isCorrect(int c){
        Card card = new Card(answer.getNum(),c);
        if(answer.getIndex()==card.getIndex()){
            score = score + 5;
            streak+=1;
            if(this.streak>0){
                score = score+5*this.streak;
            }
            return true;
        }else{
            if(streak>0){
                streak =0;
                score = score-5;
            }
            else{
                score -= 10;
            }
            return false;
        }
    }
    public int getScore(){
        return this.score;
    }
    public int getStreak(){
        return this.streak;
    }
    public String printStat(){
        return "Score: "+this.score+", Streak: "+this.streak;
    }
    //public void save(){
    //    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //    try (FileWriter writer = new FileWriter("starling/src/main/games/Cards.json")) {
    //        gson.toJson(this.score, writer);
    //        gson.toJson(this.streak, writer); 
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //}
    public class Card{
        private int num;
        private int index;
        public Card(int num, int index){
            this.num = num;
            this.index = index;
        }
        public int getNum(){
            return this.num;
        }        
        public int getIndex(){
            return this.index;
        }
    }
}
