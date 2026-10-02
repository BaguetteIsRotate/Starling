package com.baguetteisrotate.starling.games;

import java.awt.*;

import javax.swing.*;
import com.baguetteisrotate.starling.games.CardsUI;
public class CardsTest {
    public static void main(String[] args){
        CardsUI ui = new CardsUI();
        JFrame frame =new JFrame("CardsTest");
        frame.setSize(393, 793);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        JPanel page = new JPanel();
        CardsUI cards = new CardsUI();
        JPanel big = new JPanel();
        JButton button = new JButton("Play Cards");
        button.setPreferredSize(new Dimension(100,100));
        big.add(button);
        button.addActionListener(e -> {
            cards.addGametoPanel(big);
        });
        page.setLayout(new BorderLayout());

        page.add(big, BorderLayout.CENTER);
        frame.add(page);
        frame.revalidate();
    }
}
