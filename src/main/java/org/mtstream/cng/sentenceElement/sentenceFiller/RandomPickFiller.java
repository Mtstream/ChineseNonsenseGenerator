package org.mtstream.cng.sentenceElement.sentenceFiller;

import org.mtstream.cng.sentenceElement.AbstractSentenceFiller;

import java.util.Random;
import java.util.regex.Pattern;

public class RandomPickFiller extends AbstractSentenceFiller {
    @Override
    public Pattern getPattern() {
        return Pattern.compile("<(.|[^<>]+)>");
    }

    @Override
    public String getReplacement(String group) {
        String[] items = group.split(", *");
        Random ran = new Random();
        return items[ran.nextInt(items.length)];
    }
}
