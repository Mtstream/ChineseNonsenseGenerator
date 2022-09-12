package org.mtstream.cng;

import org.json.simple.parser.ParseException;
import org.mtstream.cng.StringGenerator.Nonsense;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.IOException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) throws IOException, ParseException {
        GeneratorGUI g = new GeneratorGUI();
//        int i = Integer.parseInt(JOptionPane.showInputDialog(null, "请输入大约字数", "20"));
//        String nonsense = Nonsense.create(i);
//        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
//        c.setContents(new StringSelection(nonsense), null);
    }

    public static class GeneratorGUI extends JFrame implements MouseListener{

        GeneratorGUI() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.getContentPane().setBackground(new Color(40, 40, 40));
            setSize(360,650);
            //输入框
            JTextField field = new JTextField();
            field.setBounds(20,20,300,20);
            add(field);
            //按钮
            JButton button = new JButton("生成");
            button.setSize(100, 50);
            button.setPreferredSize(new Dimension(100,50));
            button.setBackground(new Color(84, 135, 211));
            button.setBounds(20, 60, 300, 20);
            add(button);
            //文本
            JLabel label = new JLabel("");
            label.setForeground(Color.lightGray);
            add(label);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String nonsense = Nonsense.create(Integer.parseInt(field.getText()));
                        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
                        c.setContents(new StringSelection(nonsense), null);
                        label.setText("<html>"+nonsense+"<html>");
                    } catch (IOException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            add(new JLabel("大约字数："), BorderLayout.AFTER_LAST_LINE);


            setVisible(true);
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
