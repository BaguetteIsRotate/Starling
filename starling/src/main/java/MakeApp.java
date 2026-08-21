import javax.swing.*;
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
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import games.CardsGame;
import games.CardsUI;
import graphing.Graph;
import mood.MoodTracker;
public class MakeApp extends JFrame {
    JFrame app;
    JPanel pageMain;
    JPanel pageOne;
    JPanel pageTwo;
    JPanel pageInfoA;
    JPanel pageInfoB;
    JPanel pageInfoC;
    JPanel pageInfoD;

    public MakeApp() {
        // make JFrame
        super("Starling");
        // make title
        setSize(393, 793);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        pageOne = PageOne();
        pageTwo = PageTwo();
        pageMain = PageMain();
        pageInfoA = PageInfoA("starling/src/main/java/text1.txt", "What is Alzheimer's Disease?");
        pageInfoB = PageInfoA("starling/src/main/java/text2.txt", "Onset and Early Symptoms of Alzheimer's");
        pageInfoC = PageInfoA("starling/src/main/java/text3.txt", "Middle-Stage Alzheimer's and Late-Stage ALzheimers");
        pageInfoD = PageInfoA("starling/src/main/java/text4.txt", "Preventative measures against Alzheimer's symptons");
        add(pageMain);
        revalidate();
    }

    public JPanel PageMain() {
        JPanel page = new JPanel();
        Color color = new Color(255, 248, 231);
        page.setBackground(color);
        JPanel smol = new JPanel();
        smol.setBackground(color);
        page.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        smol.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        ImageIcon title = resizeImageIcon("starling/src/main/images/app_title.png", 100, page.getWidth() / 2);
        JLabel pondering = new JLabel(title);
        pondering.setSize(200, 100);
        JLabel pondering2 = new JLabel("An app for Alzheimer's patients");
        smol.setLayout(new GridLayout(2, 1));
        page.setLayout(new BorderLayout());
        page.add(pondering, BorderLayout.NORTH);
        pondering.setHorizontalAlignment(SwingConstants.CENTER);
        JButton button1 = new JButton();
        button1.setSize(200, 100);
        smol.add(button1);
        ImageIcon icon2 = resizeImageIcon("starling/src/main/images/happy_icon.png", 50, button1.getWidth() / 4);
        button1.setIcon(icon2);

        JButton button2 = new JButton();
        smol.add(button2);
        button2.setSize(200, 100);
        ImageIcon icon = resizeImageIcon("starling/src/main/images/info_icon.png", 50, button2.getWidth() / 4);
        button2.setIcon(icon);
        button1.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                ImageIcon icon = resizeImageIcon("starling/src/main/images/happy_icon.png", (button1.getHeight() / 4),
                        button1.getWidth() / 4);
                button1.setIcon(icon);
            }
        });
        button1.addActionListener(e -> {
            remove(pageMain);
            add(pageOne);
            revalidate();
            repaint();
        });

        pondering.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                ImageIcon title1 = resizeImageIcon("starling/src/main/images/app_title.png",
                        (MakeApp.this.getHeight() / 2), MakeApp.this.getWidth());
                pondering.setIcon(title1);
            }
        });
        button2.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                ImageIcon icon = resizeImageIcon("starling/src/main/images/info_icon.png", (button2.getHeight() / 4),
                        button2.getWidth() / 4);
                button2.setIcon(icon);
            }
        });
        button2.addActionListener(e -> {
            remove(pageMain);
            add(pageTwo);
            revalidate();
            repaint();
        });
        page.add(smol, BorderLayout.CENTER);
        page.add(pondering2, BorderLayout.SOUTH);
        pondering2.setHorizontalAlignment(SwingConstants.CENTER);
        return page;
    }

    public JPanel PageOne() {
        MoodTracker tracker = new MoodTracker();
        JPanel graph = tracker.makePanel("mood.json");
        JPanel page = new JPanel();
        Color color = new Color(255, 248, 231);
        page.setBackground(color);
        JPanel smol = new JPanel();
        page.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        smol.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        smol.setBackground(color);
        JButton buttonMain = new JButton();
        buttonMain.setPreferredSize(new Dimension(100, 100));
        ImageIcon icon = resizeImageIcon("starling/src/main/images/main_icon.png", 50, buttonMain.getWidth());
        buttonMain.setIcon(icon);
        buttonMain.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                ImageIcon icon = resizeImageIcon("starling/src/main/images/main_icon.png", (buttonMain.getHeight() / 2),
                        buttonMain.getWidth() / 2);
                buttonMain.setIcon(icon);
            }
        });
        buttonMain.addActionListener(e -> {
            remove(pageOne);
            add(pageMain);
            revalidate();
            repaint();
        });
        smol.setLayout(new BorderLayout());
        smol.add(buttonMain);
        CardsUI cards = new CardsUI();
        JPanel big = new JPanel();
        JButton button = new JButton("Play Cards");
        big.add(button);
        button.addActionListener(e -> {
            cards.addGametoPanel(big);
        });
        page.setLayout(new BorderLayout());

        page.add(graph, BorderLayout.CENTER);
        page.add(smol, BorderLayout.NORTH);

        page.add(big, BorderLayout.SOUTH);

        return page;
    }

    public JPanel PageTwo() {
        JPanel page = new JPanel();
        JPanel smol = new JPanel();

        smol.setLayout(new BorderLayout());
        page.setLayout(new BorderLayout());
        JButton buttonMain = new JButton();
        buttonMain.setPreferredSize(new Dimension(100, 100));
        ImageIcon icon = resizeImageIcon("starling/src/main/images/main_icon.png", 50, buttonMain.getWidth());
        buttonMain.setIcon(icon);
        buttonMain.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                ImageIcon icon = resizeImageIcon("starling/src/main/images/main_icon.png", (buttonMain.getHeight() / 2),
                        buttonMain.getWidth() / 2);
                buttonMain.setIcon(icon);
            }
        });
        buttonMain.addActionListener(e -> {
            remove(pageTwo);
            add(pageMain);
            revalidate();
            repaint();
        });

        page.add(buttonMain, BorderLayout.NORTH);
        buttonMain.setHorizontalAlignment(SwingConstants.CENTER);
        page.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        smol.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Color color = new Color(255, 248, 231);
        page.setBackground(color);
        smol.setBackground(color);
        JLabel pondering = new JLabel("Information");
        smol.add(pondering, BorderLayout.NORTH);
        pondering.setHorizontalAlignment(SwingConstants.CENTER);
        JTextArea text = new JTextArea();
        smol.add(text);
        text.setEditable(true);
        text.setLineWrap(true);
        JPanel smolsmol = new JPanel();
        smolsmol.setLayout(new GridLayout(4, 1));
        JButton buttonA = new JButton("What is Alzheimer's Disease?");
        smolsmol.add(buttonA);
        buttonA.setPreferredSize(new Dimension(300, 100));
        JButton buttonB = new JButton("B");
        smolsmol.add(buttonB);
        buttonB.setPreferredSize(new Dimension(300, 100));
        JButton buttonC = new JButton("C");
        smolsmol.add(buttonC);
        buttonC.setPreferredSize(new Dimension(300, 100));
        JButton buttonD = new JButton("D");
        buttonD.setPreferredSize(new Dimension(300, 100));
        smolsmol.add(buttonD);
        smolsmol.setBackground(color);
        JScrollPane pane = new JScrollPane(smolsmol, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        smol.add(pane);
        buttonA.addActionListener(e -> {
            remove(pageTwo);
            add(pageInfoA);
            revalidate();
            repaint();
        });

        buttonB.addActionListener(e -> {
            remove(pageTwo);
            add(pageInfoB);
            revalidate();
            repaint();
        });

        buttonC.addActionListener(e -> {
            remove(pageTwo);
            add(pageInfoC);
            revalidate();
            repaint();
        });
        buttonD.addActionListener(e -> {
            remove(pageTwo);
            add(pageInfoD);
            revalidate();
            repaint();
        });
        page.add(smol, BorderLayout.CENTER);
        return page;
    }

    public JPanel PageInfoA(String path, String title) {
        JPanel page = new JPanel();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Color color = new Color(255, 248, 231);
        page.setBackground(color);
        JPanel smol = new JPanel();
        page.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        smol.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        smol.setBackground(color);
        JPanel smol2 = new JPanel();
        panel.setBackground(color);
        smol2.setBackground(color);
        JLabel pondering = new JLabel(title);
        smol2.setLayout(new BorderLayout());
        smol.setLayout(new BorderLayout());
        page.setLayout(new BorderLayout());
        smol2.add(pondering, BorderLayout.NORTH);
        pondering.setHorizontalAlignment(SwingConstants.CENTER);
        JButton buttonMain = new JButton();
        panel.add(buttonMain);
        buttonMain.setPreferredSize(new Dimension(100, 100));
        ImageIcon icon = resizeImageIcon("starling/src/main/images/main_icon.png", 50, buttonMain.getWidth());
        buttonMain.setIcon(icon);
        buttonMain.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                ImageIcon icon = resizeImageIcon("starling/src/main/images/main_icon.png", (buttonMain.getHeight() / 2),
                        buttonMain.getWidth() / 2);
                buttonMain.setIcon(icon);
            }
        });
        buttonMain.addActionListener(e -> {
            remove(pageInfoA);
            add(pageMain);
            revalidate();
            repaint();
        });

        JButton button2 = new JButton();
        panel.add(button2, BorderLayout.EAST);
        button2.setSize(200, 100);
        ImageIcon icon2 = resizeImageIcon("starling/src/main/images/info_icon.png", 50, 50);
        button2.setIcon(icon2);
        button2.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                ImageIcon icon = resizeImageIcon("starling/src/main/images/info_icon.png", (button2.getHeight() / 2),
                        button2.getWidth() / 2);
                button2.setIcon(icon);
            }
        });
        button2.addActionListener(e -> {
            remove(pageInfoA);
            add(pageTwo);
            revalidate();
            repaint();
        });
        button2.setPreferredSize(new Dimension(100, 100));

        buttonMain.setHorizontalAlignment(SwingConstants.CENTER);
        button2.setHorizontalAlignment(SwingConstants.CENTER);
        smol2.add(panel, BorderLayout.SOUTH);
        smol.add(smol2, BorderLayout.NORTH);
        JTextArea text = new JTextArea();
        JScrollPane pane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        text.setLineWrap(true);
        try {
            FileReader reader = new FileReader(path);
            String x = Files.readString(Path.of(path));
            String[] a = x.split("\\n");
            for (int i = 0; i < a.length; i++) {
                text.append(a[i] + "\n" + "\n");
            }
            reader.close();

        } catch (Exception e) {
            text.setText("File not found");
        }
        text.setEditable(false);
        text.setWrapStyleWord(true);
        smol.add(pane, BorderLayout.CENTER);
        page.add(smol, BorderLayout.CENTER);
        return page;
    }

    public ImageIcon resizeImageIcon(String path, int height, int width) {
        File f = new File(path);
        if (!f.exists()) {
            return new ImageIcon();
        }
        ImageIcon icon = new ImageIcon(path);
        double ogheight = icon.getIconHeight();
        if (ogheight <= 0 || icon.getIconWidth() <= 0)
            return icon;
        int targetHeight = Math.max(1, height);
        int targetWidth = Math.max(1, width);
        double ratio = targetHeight / ogheight;
        double ratio2 = targetWidth / (double) icon.getIconWidth();
        if (ratio < ratio2) {
            int finalWidth = Math.max(1, (int) (icon.getIconWidth() * ratio));
            Image image = icon.getImage().getScaledInstance(finalWidth, targetHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } else {
            int finalHeight = Math.max(1, (int) (icon.getIconHeight() * ratio2));
            Image image = icon.getImage().getScaledInstance(targetWidth, finalHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        }
    }

}