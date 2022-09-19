package org.mtstream.cng;


import javax.swing.*;
import java.awt.*;

public class Main{

    public static void main(String[] args) {
        startWindow();
    }

    public static void startWindow(){
        JFrame jFrame = new JFrame();
        jFrame.setSize(950, 590);
        jFrame.setResizable(false);
        jFrame.setTitle("废话生成器");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(new MainPanel());
        jFrame.getContentPane().setBackground(new Color(0, 0, 0, 0));
        jFrame.setVisible(true);
    }
}
