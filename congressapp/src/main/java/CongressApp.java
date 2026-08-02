package congressapp.src.main.java;

import javax.swing.SwingUtilities;

public class CongressApp{
    public static void main(String[] args) throws Exception{
        SwingUtilities.invokeLater (() -> {
            MakeApp happy = new MakeApp();
            happy.setVisible(true);
        });
    }
}