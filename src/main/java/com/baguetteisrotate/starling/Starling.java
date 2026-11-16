package com.baguetteisrotate.starling;

import javax.swing.SwingUtilities;

public class Starling {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MakeApp app = new MakeApp();
            app.setVisible(true);
        });
    }
}