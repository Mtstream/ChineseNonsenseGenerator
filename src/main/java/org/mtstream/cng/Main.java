package org.mtstream.cng;


import org.mtstream.cng.stringGenerator.SentenceFactory;
import org.mtstream.cng.sentenceElement.SpecialElementHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main{

    public static boolean DEBUG_MODE = false;

    public static void main(String[] args) {
        bootstrap();
        if (DEBUG_MODE) {
            startTestMode();
        } else {
            startWindow();
        }
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

    public static void bootstrap(){
        SpecialElementHandler.bootstrap();
    }

    public static void startTestMode() {
        System.out.println("Test mode started");
        while (true) {
            Scanner sc = new Scanner(System.in);
            String str = sc.next();
            System.out.println(SentenceFactory.fillSentence(str));
        }
    }
}
