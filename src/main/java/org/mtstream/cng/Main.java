package org.mtstream.cng;

import org.json.simple.parser.ParseException;
import org.mtstream.cng.StringGenerator.Nonsense;
import org.mtstream.cng.StringGenerator.SentenceGenerator;
import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class Main{

    public static void main(String[] args) throws IOException, ParseException {
//        String s = ResourceReader.getRandomSentence();
//        System.out.println(s);
//        System.out.println(SentenceGenerator.fillSentence(s));
        String nonsense = Nonsense.create(20);
        System.out.println(nonsense);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(new StringSelection(nonsense), null);
    }

}
