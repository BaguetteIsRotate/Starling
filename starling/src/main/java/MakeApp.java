package starling.src.main.java;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import javax.swing.*;
//
//make function that returns JText Area with text from file later
//
public class MakeApp extends JFrame{
    JFrame app;
    JPanel pageMain;
    JPanel pageOne;
    JPanel pageTwo;
    JPanel pageInfoA;
    JPanel pageInfoB;
    JPanel pageInfoC;
    public MakeApp(){
        //make JFrame
        super("App Title Here");
        //make title
        setSize(700,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        pageOne = PageOne();
        pageTwo = PageTwo();
        pageMain = PageMain();
        pageInfoA = PageInfoA("congressapp/src/main/java/filler.txt");
        pageInfoB = PageInfoA("congressapp/src/main/java/filler.txt");
        pageInfoC = PageInfoA("congressapp/src/main/java/filler.txt");
        add(pageMain);
        revalidate();
    }
    public JPanel PageMain(){
        JPanel page = new JPanel();
        Color color = new Color(255, 248, 231);
        page.setBackground(color);
        JPanel smol = new JPanel();
        smol.setBackground(color);
        page.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        smol.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        JLabel pondering = new JLabel("Title");
        JLabel pondering2 = new JLabel("Bottom text");
        smol.setLayout(new GridLayout(2,1));
        page.setLayout(new BorderLayout());
        page.add(pondering,BorderLayout.NORTH);
        pondering.setHorizontalAlignment(SwingConstants.CENTER);
        JButton button1 = new JButton();
        button1.setSize(200,100);
        smol.add(button1);
        ImageIcon icon2 = resizeImageIcon("congressapp/src/main/images/happy_icon.png",50);
        button1.setIcon(icon2);
        
        
        JButton button2 = new JButton();
        smol.add(button2);
        button2.setSize(200,100);
        ImageIcon icon = resizeImageIcon("congressapp/src/main/images/info_icon.png",50);
        button2.setIcon(icon);
        button1.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e){
                ImageIcon icon = resizeImageIcon("congressapp/src/main/images/happy_icon.png",(button1.getHeight()/2));
                button1.setIcon(icon);
            }
        });
        button1.addActionListener(e->{
           remove(pageMain);
           add(pageOne);
           revalidate();
           repaint();
        });
        button2.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e){
                ImageIcon icon = resizeImageIcon("congressapp/src/main/images/info_icon.png",(button2.getHeight()/2));
                button2.setIcon(icon);
            }
        });
        button2.addActionListener(e->{
           remove(pageMain);
           add(pageTwo);
           revalidate();
           repaint();
        });
        page.add(smol, BorderLayout.CENTER);
        page.add(pondering2,BorderLayout.SOUTH);
        pondering2.setHorizontalAlignment(SwingConstants.CENTER);
        return page;
    }
    public JPanel PageOne(){
        JPanel page = new JPanel();
        Color color = new Color(255, 248, 231);
        page.setBackground(color);
        JPanel smol = new JPanel();
        page.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        smol.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        smol.setBackground(color);
        JLabel pondering = new JLabel("Feel Happy. ✨😟✨");
        smol.setLayout(new BorderLayout());
        page.setLayout(new BorderLayout());
        smol.add(pondering, BorderLayout.NORTH);
        pondering.setHorizontalAlignment(SwingConstants.CENTER);
        JButton button1 = new JButton("go back");
        JTextArea text = new JTextArea();

        text.setWrapStyleWord(true);
        smol.add(text);
        text.setEditable(true);
        page.add(smol,BorderLayout.CENTER);
        page.add(button1,BorderLayout.SOUTH);
        button1.addActionListener(e->{
           remove(pageOne);
           add(pageMain);
           revalidate();
           repaint();
        });
        return page;
    }
    public JPanel PageTwo(){
        JPanel page = new JPanel();
        JPanel smol = new JPanel();

        page.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        smol.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        Color color = new Color(255, 248, 231);
        page.setBackground(color);
        smol.setBackground(color);
        JLabel pondering = new JLabel("Page two");
        smol.setLayout(new BorderLayout());
        page.setLayout(new BorderLayout());
        smol.add(pondering, BorderLayout.NORTH);
        pondering.setHorizontalAlignment(SwingConstants.CENTER);
        JButton button1 = new JButton("go back");
        JTextArea text = new JTextArea();
        smol.add(text);
        text.setEditable(true);
        text.setLineWrap(true);
        JPanel smolsmol = new JPanel();
        smolsmol.setLayout(new GridLayout(4,1));
        JButton buttonA = new JButton("A");
        smolsmol.add(buttonA);
        buttonA.setPreferredSize(new Dimension(300,100));
        JButton buttonB = new JButton("B");
        smolsmol.add(buttonB);
        buttonB.setPreferredSize(new Dimension(300,100));
        JButton buttonC = new JButton("C");
        smolsmol.add(buttonC);
        buttonC.setPreferredSize(new Dimension(300,100));
        JButton buttonD = new JButton("D");
        buttonD.setPreferredSize(new Dimension(300,100));
        smolsmol.add(buttonD);
        smolsmol.setBackground(color);
        JScrollPane pane = new JScrollPane(smolsmol,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        smol.add(pane);
        buttonA.addActionListener(e->{
           remove(pageTwo);
           add(pageInfoA);
           revalidate();
           repaint();
        });

        buttonB.addActionListener(e->{
           remove(pageTwo);
           add(pageInfoA);
           revalidate();
           repaint();
        });

        buttonC.addActionListener(e->{
           remove(pageTwo);
           add(pageInfoA);
           revalidate();
           repaint();
        });
        page.add(smol,BorderLayout.CENTER);
        page.add(button1,BorderLayout.SOUTH);
        button1.addActionListener(e->{
           remove(pageTwo);
           add(pageMain);
           revalidate();
           repaint();
        });
        return page;
    }
    public JPanel PageInfoA(String path){
        JPanel page = new JPanel();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Color color = new Color(255, 248, 231);
        page.setBackground(color);
        JPanel smol = new JPanel();
        page.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        smol.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        smol.setBackground(color);
        JPanel smol2 = new JPanel();
        panel.setBackground(color);
        smol2.setBackground(color);
        JLabel pondering = new JLabel("A");
        smol2.setLayout(new BorderLayout());
        smol.setLayout(new BorderLayout());
        page.setLayout(new BorderLayout());
        smol2.add(pondering, BorderLayout.NORTH);
        pondering.setHorizontalAlignment(SwingConstants.CENTER);
        JButton buttonMain = new JButton();
        panel.add(buttonMain);
        buttonMain.setPreferredSize(new Dimension(100,100));
        ImageIcon icon = resizeImageIcon("congressapp/src/main/images/main_icon.png",50);
        buttonMain.setIcon(icon);
        buttonMain.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e){
                ImageIcon icon = resizeImageIcon("congressapp/src/main/images/main_icon.png",(buttonMain.getHeight()/2));
                buttonMain.setIcon(icon);
            }
        });
        buttonMain.addActionListener(e->{
           remove(pageInfoA);
           add(pageMain);
           revalidate();
           repaint();
        });


        JButton button2 = new JButton();
        panel.add(button2,BorderLayout.EAST);
        button2.setSize(200,100);
        ImageIcon icon2 = resizeImageIcon("congressapp/src/main/images/info_icon.png",50);
        button2.setIcon(icon2);
        button2.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e){
                ImageIcon icon = resizeImageIcon("congressapp/src/main/images/info_icon.png",(button2.getHeight()/2));
                button2.setIcon(icon);
            }
        });
        button2.addActionListener(e->{
           remove(pageInfoA);
           add(pageTwo);
           revalidate();
           repaint();
        });
        button2.setPreferredSize(new Dimension(100,100));

        buttonMain.setHorizontalAlignment(SwingConstants.CENTER);
        button2.setHorizontalAlignment(SwingConstants.CENTER);
        smol2.add(panel,BorderLayout.SOUTH);
        smol.add(smol2,BorderLayout.NORTH);
        JButton button1 = new JButton("go back");
        JTextArea text = new JTextArea();
        JScrollPane pane = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        text.setLineWrap(true);
        try{
            FileReader reader = new FileReader(path);
            text.setText(reader.readAllAsString());
            reader.close();
        }catch(Exception e){
            text.setText("oh no the file aint reading");
        }
        text.setEditable(false);
        text.setWrapStyleWord(true);
        smol.add(pane,BorderLayout.CENTER);
        page.add(smol,BorderLayout.CENTER);
        page.add(button1,BorderLayout.SOUTH);
        button1.addActionListener(e->{
           remove(pageInfoA);
           add(pageMain);
           revalidate();
           repaint();
        });
        return page;
    }
    public ImageIcon resizeImageIcon(String path, int height){
        ImageIcon icon = new ImageIcon(path);
        double ogheight = icon.getIconHeight();
        double ratio = height/ogheight;
        Image image = icon.getImage().getScaledInstance((int)(icon.getIconWidth()*ratio), height, Image.SCALE_SMOOTH);
        ImageIcon icon2 = new ImageIcon(image);
        return icon2;
    }
}