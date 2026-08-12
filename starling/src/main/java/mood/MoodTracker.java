package mood;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.swing.*;
import graphing.Graph;

public class MoodTracker{
    private HashMap<Integer, String> x;
    private HashMap<Integer, Integer> y;
    private int currnum;
    public JPanel makePanel(String path){
        LocalDate time = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd, yyyy");
        time.format(formatter);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        Graph graph = new Graph("Mood over Time", "Date (mm/dd, yyyy)",this.x,"Mood",this.y);
        JPanel visual = graph.makePanel();
        panel.add(visual,BorderLayout.NORTH);

        JButton button1 = new JButton("1");
        JButton button2 = new JButton("2");
        JButton button3 = new JButton("3");
        JButton button4 = new JButton("4");
        JButton button5 = new JButton("5");

        button1.addActionListener(e -> {
            this.x.put(currnum, time.toString());
            this.y.put(currnum, 1);
            graph.reload("Mood over Time", "Date (mm/dd, yyyy)",this.x,"Mood",this.y);
            JPanel visual2 = graph.makePanel();
            panel.remove(visual);
            panel.add(visual2);
            panel.repaint();
        });
        button2.addActionListener(e->{
            this.x.put(currnum, time.toString());
            this.y.put(currnum, 2);
            graph.reload("Mood over Time", "Date (mm/dd, yyyy)",this.x,"Mood",this.y);
            JPanel visual2 = graph.makePanel();
            panel.remove(visual);
            panel.add(visual2);
            panel.repaint();
        });
        button3.addActionListener(e->{
            this.x.put(currnum, time.toString());
            this.y.put(currnum, 2);
            graph.reload("Mood over Time", "Date (mm/dd, yyyy)",this.x,"Mood",this.y);
            JPanel visual2 = graph.makePanel();
            panel.remove(visual);
            panel.add(visual2);
            panel.repaint();
        });
        button4.addActionListener(e->{
            this.x.put(currnum, time.toString());
            this.y.put(currnum, 2);
            graph.reload("Mood over Time", "Date (mm/dd, yyyy)",this.x,"Mood",this.y);
            JPanel visual2 = graph.makePanel();
            panel.remove(visual);
            panel.add(visual2);
            panel.repaint();
        });
        button5.addActionListener(e->{
            this.x.put(currnum, time.toString());
            this.y.put(currnum, 2);
            graph.reload("Mood over Time", "Date (mm/dd, yyyy)",this.x,"Mood",this.y);
            JPanel visual2 = graph.makePanel();
            panel.remove(visual);
            panel.add(visual2);
            panel.repaint();
        });

        JPanel small = new JPanel();
        small.setLayout(new GridLayout(1,5));
        small.add(button1);
        small.add(button2);
        small.add(button3);
        small.add(button4);
        small.add(button5);
        panel.add(small, BorderLayout.SOUTH);
        return panel;
    }
}
