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
            setTitle("废话生成器");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.getContentPane().setBackground(new Color(40, 40, 40));
            setSize(420,550);
            //文本
            JLabel label0 = new JLabel("大约长度");
            label0.setBounds(20, 20, 60, 20);
            label0.setForeground(Color.lightGray);
            add(label0);
            //输入框
            JTextField field = new JTextField();
            field.setBounds(80,20,300,20);
            add(field);
            //按钮
            JButton button0 = new JButton("生成");
            button0.setSize(100, 50);
            button0.setPreferredSize(new Dimension(100,50));
            button0.setBackground(new Color(84, 135, 211));
            button0.setBounds(20, 60, 360, 20);
            add(button0);
            //按钮
            JButton button1 = new JButton("复制到剪贴板");
            button1.setPreferredSize(new Dimension(100,50));
            button1.setBackground(new Color(84, 135, 211));
            button1.setBounds(20, 470, 360, 20);
            add(button1);
            //文本
            JLabel label1 = new JLabel("");
            label1.setBounds(20, 100, 60, 20);
            label1.setForeground(Color.lightGray);
            add(label1);
            button0.addActionListener(e -> {
                try {
                    String nonsense = Nonsense.create(Integer.parseInt(field.getText()));
                    label1.setText("<html>"+nonsense+"<html>");
                    label1.setBounds(20, 100, 60, 20);
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            });
            button1.addActionListener(e -> {
                Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
                c.setContents(new StringSelection(label1.getText().replaceAll("<html>", "")), null);
            });

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
