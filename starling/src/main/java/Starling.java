import games.CardsUI;
import games.CardsGame;
import graphing.Graph;
import mood.MoodTracker;
import javax.swing.SwingUtilities;

public class Starling {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            MakeApp happy = new MakeApp();
            happy.setVisible(true);
        });
    }
}