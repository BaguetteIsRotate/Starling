import games.Cards;
import javax.swing.SwingUtilities;

public class Starling {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            MakeApp happy = new MakeApp();
            happy.setVisible(true);
        });
        // Cards card = new Cards();
        // card.playgame();
    }
}