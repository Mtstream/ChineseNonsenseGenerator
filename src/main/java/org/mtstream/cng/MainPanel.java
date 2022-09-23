package org.mtstream.cng;

import org.json.simple.parser.ParseException;
import org.mtstream.cng.StringGenerator.Nonsense;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MainPanel extends JPanel {

    public static final Color TRANSPARENCY = new Color(0, 0, 0, 0);
    public static final Color BLUE = new Color(84, 135, 211);
    public static final Color LIGHT_BLUE = new Color(124, 175, 251);


    public MainPanel() {

//        ImageIcon githubIcon = new ImageIcon("icon/github.png");

        JLabel title = new JLabel("废话生成器");
        RoundRectangleTextField lengthField = new RoundRectangleTextField (5);
        JLabel length = new JLabel("输入长度");
        JTextArea area = new JTextArea (5, 5);
        RoundRectangleScrollPane scrollPane = new RoundRectangleScrollPane();
        RoundRectangleButton copyButton = new RoundRectangleButton("复制到剪贴板", LIGHT_BLUE, BLUE);
        RoundRectangleButton generateButton = new RoundRectangleButton("生成", LIGHT_BLUE, BLUE);
//        RoundRectangleButton githubButton = new RoundRectangleButton("源代码", grayWithBrightness(160), grayWithBrightness(140));



        setPreferredSize (new Dimension (950, 550));
        setLayout (null);

        add (title);
        add (lengthField);
        add (length);
        add (copyButton);
        add (generateButton);
//        add (githubButton);

        title.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 35));
        title.setForeground(Color.LIGHT_GRAY);
        title.setBounds(20, 0, 200, 60);
        lengthField.setBounds (20, 110, 200, 25);
        lengthField.setForeground(Color.LIGHT_GRAY);
        lengthField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        length.setBounds (20, 80, 100, 25);
        length.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        length.setForeground(Color.LIGHT_GRAY);
        area.setBounds (275, 80, 620, 450);
        area.setBackground(grayWithBrightness(60));
        area.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        area.setForeground(Color.LIGHT_GRAY);
        area.setEditable(false);
        RoundRectangleScrollBarUI scrollBarUI = new RoundRectangleScrollBarUI();
        JScrollBar bar = new JScrollBar();
        bar.setUnitIncrement(10);
        bar.setBlockIncrement(50);
        bar.setUI(scrollBarUI);
        bar.setForeground(grayWithBrightness(80));
        bar.setBackground(grayWithBrightness(60));
        scrollPane.setBackground(grayWithBrightness(60));
        scrollPane.setVerticalScrollBar(bar);
        scrollPane.setBounds(275, 80, 620, 450);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().add(area);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add (scrollPane);
        copyButton.setBounds (20, 485, 200, 45);
        copyButton.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
        copyButton.setBackground(BLUE);
        copyButton.setBorderPainted(false);
        copyButton.setFocusPainted(false);
        generateButton.setBounds (20, 160, 200, 45);
        generateButton.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
        generateButton.setBackground(BLUE);
        generateButton.setBorderPainted(false);
        generateButton.setFocusPainted(false);
//        githubButton.setBounds(700, 10, 200, 40);
//        githubButton.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
//        githubButton.setBackground(grayWithBrightness(140));
//        githubButton.setBorderPainted(false);
//        githubButton.setFocusPainted(false);
//        githubButton.setIcon(githubIcon);

        area.setLineWrap(true);

        generateButton.addActionListener((e)->{
            try {
                String nonsense = Nonsense.create(Integer.parseInt(lengthField.getText()));
                area.setForeground(Color.LIGHT_GRAY);
                area.setText(nonsense);
                scrollPane.setBorder(scrollPane.getBorder());
            } catch (IOException | ParseException ex) {
                area.setForeground(Color.RED);
                area.setText("生成时遇到错误，请重试");
                throw new RuntimeException(ex);
            }
        });


        copyButton.addActionListener((e)->{
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(new StringSelection(area.getText()), null);
        });

//        githubButton.addActionListener((e)->{
//        });
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(grayWithBrightness(40));
        g2D.fillRect(0, 0, getWidth(), getHeight());
        g2D.setColor(grayWithBrightness(50));
        g2D.fillRect(0, 0, 240, getHeight());
        g2D.setColor(grayWithBrightness(60));
        g2D.fillRect(0, 0, getWidth(), 60);
        super.paint(g);
    }

    public static Color grayWithBrightness(int brightness){
        return new Color(brightness, brightness, brightness);
    }

    public static class RoundRectangleScrollPane extends JScrollPane {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setColor(getBackground());
            g2D.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g);
        }
    }

    public static class RoundRectangleButton extends JButton{

        private Color enteredColor;
        private Color exitedColor;

        RoundRectangleButton(String s, Color enter, Color exit){
            super(s);
            this.enteredColor = enter;
            this.exitedColor = exit;
            setContentAreaFilled(false);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(enteredColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(exitedColor);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setColor(getBackground());
            g2D.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            super.paintComponent(g);
        }

    }

    public static class RoundRectangleTextField extends JTextField{
        RoundRectangleTextField(int i){
            super(i);
            setBorder(new EmptyBorder(4,4,4,4));
            setBackground(new Color(0, 0, 0, 0));
            setOpaque(false);
            setSelectionColor(grayWithBrightness(120));
            setFont(new Font("sansserif", 0, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2D.setColor(grayWithBrightness(80));
            g2D.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g);
        }
    }
    public static class RoundRectangleScrollBarUI extends BasicScrollBarUI {

        public int size = 60;

        @Override
        protected Dimension getMaximumThumbSize() {
            return new Dimension(0, size);
        }

        @Override
        protected Dimension getMinimumThumbSize() {
            return new Dimension(0, size);
        }

        @Override
        protected JButton createIncreaseButton(int i) {
            return new EmptyButton();
        }

        @Override
        protected JButton createDecreaseButton(int i) {
            return new EmptyButton();
        }

        @Override
        protected void paintTrack(Graphics grphcs, JComponent jc, Rectangle thumbBounds) {
        }

        @Override
        protected void paintThumb(Graphics grphcs, JComponent jc, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(scrollbar.getForeground());
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
        }

        private static class EmptyButton extends JButton {

            public EmptyButton() {
                setBorder(BorderFactory.createEmptyBorder());
            }
            @Override
            public void paint(Graphics grphcs) {}
        }
    }
}
