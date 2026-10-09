package com.baguetteisrotate.starling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.baguetteisrotate.starling.games.CardsUI;
import com.baguetteisrotate.starling.graphing.GraphUI;
import com.baguetteisrotate.starling.mood.MoodUI;

public class MakeApp extends JFrame {
    // Cosmic Latte (#FFF8E7)
    private static final Color PAGE_COLOR = new Color(255, 248, 231);
    private static final Border PAGE_PADDING = BorderFactory.createEmptyBorder(20, 20, 20, 20);

    private final Map<String, ImageIcon> iconCache = new HashMap<>();

    JPanel pageMain;
    JPanel pageOne;
    JPanel pageTwo;
    JPanel pageThree;
    JPanel pageFour;
    JPanel pageInfoA;
    JPanel pageInfoB;
    JPanel pageInfoC;
    JPanel pageInfoD;

    JPanel pageInfoE;
    private CardsUI cardsUI;

    public MakeApp() {
        super("Starling");
        setSize(393, 793);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pageOne = PageOne();
        pageTwo = PageTwo();
        pageFour = PageFour();
        pageMain = PageMain();

        pageInfoA = PageInfo("/text/text1.txt", "What is Alzheimer's Disease?", new JPanel());
        pageInfoB = PageInfo("/text/text2.txt", "Onset and Early Symptoms of Alzheimer's", new JPanel());
        pageInfoC = PageInfo("/text/text3.txt", "Middle-Stage Alzheimer's",
                new JPanel());
        pageInfoD = PageInfo("/text/text4.txt", "Late-Stage Alzheimer's",
                new JPanel());

        pageInfoE = PageInfo("/text/text5.txt", "What Does this App Do?",
                new JPanel());
        add(pageMain);
    }

    public JPanel PageMain() {
        JPanel page = makeColoredPanel();
        page.setBorder(PAGE_PADDING);
        page.setLayout(new BorderLayout());

        JPanel top = buildMainTopBar(page);
        JPanel activityPanel = buildMainActivityPanel(page);
        JLabel subtitle = new JLabel("An app for Alzheimer's patients");
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        page.add(top, BorderLayout.NORTH);
        page.add(activityPanel, BorderLayout.CENTER);
        page.add(subtitle, BorderLayout.SOUTH);
        return page;
    }

    private JPanel buildMainTopBar(JPanel page) {
        JPanel top = makeColoredPanel();
        top.setLayout(new BorderLayout());

        JPanel left = makeColoredPanel();
        JButton infoButton = makeButton("/images/info_icon.png", 70, 70, 60, page.getWidth() / 3, page);
        infoButton.addActionListener(e -> showPage(page, pageTwo));
        left.add(infoButton);

        JPanel center = makeColoredPanel();
        JLabel titleLabel = new JLabel(resizeImageIcon("/images/app_title.png", 90, page.getWidth() / 3));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(titleLabel);
        titleLabel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                titleLabel.setIcon(resizeImageIcon("/images/app_title.png",
                        page.getHeight() / 6, page.getWidth() / 2));
            }
        });

        JPanel right = makeColoredPanel();
        JButton graphButton = makeButton("/images/graph_icon.png", 70, 70, 60, page.getWidth() / 3, page);
        graphButton.addActionListener(e -> {
            pageThree = PageThree();
            showPage(page, pageThree);
        });
        right.add(graphButton);

        top.add(left, BorderLayout.WEST);
        top.add(center, BorderLayout.CENTER);
        top.add(right, BorderLayout.EAST);
        return top;
    }

    private JPanel buildMainActivityPanel(JPanel page) {
        JPanel panel = makeColoredPanel();
        panel.setBorder(PAGE_PADDING);
        panel.setLayout(new GridLayout(2, 1));

        JButton cardsButton = makeButton("/images/happy_icon.png", 200, 100, 80, page.getWidth() / 4, page);
        cardsButton.addActionListener(e -> showPage(page, pageOne));
        JButton moodButton = makeButton("/images/record_icon.png", 200, 100, 80, page.getWidth() / 4, page);
        moodButton.addActionListener(e -> showPage(page, pageFour));

        panel.add(cardsButton);
        panel.add(moodButton);
        return panel;
    }

    public JButton makeButton(String iconPath, int x, int y, int imgx, int imgy, JPanel page) {
        return makeIconButton(iconPath, x, y, imgx, imgy, 4);
    }

    public JPanel PageOne() {
        cardsUI = new CardsUI();
        return makeBackPage(new BorderLayout(), cardsUI.getPanel());
    }

    public JPanel PageTwo() {
        JPanel page = makeColoredPanel();
        page.setLayout(new BorderLayout());
        page.setBorder(PAGE_PADDING);
        JButton buttonMain = makeMainButton(page);
        page.add(buttonMain, BorderLayout.NORTH);

        JPanel smol = makeColoredPanel();
        smol.setLayout(new BorderLayout());
        smol.setBorder(PAGE_PADDING);
        JLabel pondering = new JLabel("Information");
        smol.add(pondering, BorderLayout.NORTH);
        pondering.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel smolsmol = makeColoredPanel();
        smolsmol.setLayout(new GridLayout(5, 1));
        JButton buttonA = makeTextButton("What is Alzheimer's Disease?");
        JButton buttonB = makeTextButton("Onset and Early Symptoms of Alzheimer's");
        JButton buttonC = makeTextButton("Middle-Stage Alzheimer's");
        JButton buttonD = makeTextButton("Late-Stage Alzheimer's");
        JButton buttonE = makeTextButton("What Does this App Do?");
        smolsmol.add(buttonA);
        smolsmol.add(buttonB);
        smolsmol.add(buttonC);
        smolsmol.add(buttonD);
        smolsmol.add(buttonE);
        buttonA.setPreferredSize(new Dimension(50,300));

        buttonB.setPreferredSize(new Dimension(50,300));

        buttonC.setPreferredSize(new Dimension(50,300));

        buttonD.setPreferredSize(new Dimension(50,300));

        buttonE.setPreferredSize(new Dimension(50,300));
        JScrollPane pane = new JScrollPane(smolsmol, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        smol.add(pane, BorderLayout.CENTER);

        buttonA.addActionListener(e -> showPage(page, pageInfoA));
        buttonB.addActionListener(e -> showPage(page, pageInfoB));
        buttonC.addActionListener(e -> showPage(page, pageInfoC));
        buttonD.addActionListener(e -> showPage(page, pageInfoD));
        buttonE.addActionListener(e -> showPage(page, pageInfoE));
        page.add(smol, BorderLayout.CENTER);
        return page;
    }

    public JPanel PageThree() {
        GraphUI tracker = new GraphUI();
        JPanel graph = tracker.makePanel();
        return makeBackPage(new BorderLayout(), graph);
    }

    public JPanel PageFour() {
        MoodUI tracker = new MoodUI();
        JPanel moodPanel = tracker.makePanel();
        return makeBackPage(new BorderLayout(), moodPanel);
    }

    public JPanel PageInfo(String path, String title, JPanel page) {
        page.setBackground(PAGE_COLOR);
        page.setBorder(PAGE_PADDING);
        page.setLayout(new BorderLayout());

        JPanel smol = makeColoredPanel();
        smol.setBorder(PAGE_PADDING);
        smol.setLayout(new BorderLayout());

        JPanel smol2 = makeColoredPanel();
        smol2.setLayout(new BorderLayout());
        JLabel pondering = new JLabel(title);
        pondering.setHorizontalAlignment(SwingConstants.CENTER);
        smol2.add(pondering, BorderLayout.NORTH);

        JPanel panel = makeColoredPanel();
        JButton buttonMain = makeMainButton(page);
        panel.add(buttonMain);
        JButton button2 = makeIconButton("/images/info_icon.png", 100, 100, 50, 50, 2);
        button2.addActionListener(e -> showPage(page, pageTwo));
        panel.add(button2);
        smol2.add(panel, BorderLayout.SOUTH);
        smol.add(smol2, BorderLayout.NORTH);

        JTextArea text = new JTextArea();
        JScrollPane pane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        text.setLineWrap(true);
        try (InputStream input = getClass().getResourceAsStream(path)) {
            if (input == null) {
                throw new IOException("Resource not found: " + path);
            }
            text.setText(new String(input.readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            text.setText("File not found");
        }
        text.setEditable(false);
        text.setWrapStyleWord(true);
        smol.add(pane, BorderLayout.CENTER);
        page.add(smol, BorderLayout.CENTER);
        return page;
    }

    public ImageIcon resizeImageIcon(String path, int height, int width) {
        ImageIcon icon = iconCache.get(path);
        if (icon == null) {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                return new ImageIcon();
            }
            icon = new ImageIcon(resource);
            iconCache.put(path, icon);
        }

        double ogheight = icon.getIconHeight();
        if (ogheight <= 0 || icon.getIconWidth() <= 0)
            return icon;
        int targetHeight = Math.max(1, height);
        int targetWidth = Math.max(1, width);
        double ratio = targetHeight / ogheight;
        double ratio2 = targetWidth / (double) icon.getIconWidth();
        int finalWidth;
        int finalHeight;
        if (ratio < ratio2) {
            finalWidth = Math.max(1, (int) (icon.getIconWidth() * ratio));
            finalHeight = targetHeight;
        } else {
            finalWidth = targetWidth;
            finalHeight = Math.max(1, (int) (icon.getIconHeight() * ratio2));
        }
        Image image = icon.getImage().getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    // ---- shared helpers ----

    private JPanel makeColoredPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PAGE_COLOR);
        return panel;
    }

    private void showPage(JPanel from, JPanel to) {
        remove(from);
        add(to);
        revalidate();
        repaint();
    }

    /** A button whose icon is rescaled to (height / divisor, width / divisor) whenever it is resized. */
    private JButton makeIconButton(String iconPath, int x, int y, int imgx, int imgy, int divisor) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(x, y));
        button.setIcon(resizeImageIcon(iconPath, imgy, imgx));
        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                button.setIcon(resizeImageIcon(iconPath, button.getHeight() / divisor,
                        button.getWidth() / divisor));
            }
        });
        return button;
    }

    /** The "home" button that returns from the given page to pageMain. */
    private JButton makeMainButton(JPanel from) {
        JButton button = makeIconButton("/images/main_icon.png", 100, 100, 50, 50, 2);
        button.addActionListener(e -> {
            if (from == pageOne && cardsUI != null) {
                cardsUI.resetToStart();
            }
            showPage(from, pageMain);
        });
        return button;
    }

    private JButton makeTextButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(300, 100));
        return button;
    }

    /** Page with a home button along the top and the given content filling the rest. */
    private JPanel makeBackPage(LayoutManager layout, JComponent content) {
        JPanel page = makeColoredPanel();
        page.setLayout(layout);
        page.setBorder(PAGE_PADDING);

        JPanel smol = makeColoredPanel();
        smol.setLayout(new BorderLayout());
        smol.setBorder(PAGE_PADDING);
        smol.add(makeMainButton(page), BorderLayout.CENTER);

        page.add(smol, BorderLayout.NORTH);
        page.add(content, BorderLayout.CENTER);
        return page;
    }
}