package org.mtstream.cng.StringGenerator;

import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceGenerator {
    public static String fillSentence(String structure) throws IOException {
        String wordregx = "/.{4}";
        String sentregx = "~.{4}";

        Pattern wordpattern = Pattern.compile(wordregx, Pattern.CASE_INSENSITIVE);
        Pattern sentpattern = Pattern.compile(sentregx, Pattern.CASE_INSENSITIVE);

        Matcher wordmatcher = wordpattern.matcher(structure);
        Matcher sentmatcher = sentpattern.matcher(structure);

        String origin = structure;
        origin = random(origin);
        while (wordmatcher.find()){
            origin = origin.replaceFirst(wordmatcher.group(), ResourceReader.getRandomWord(wordmatcher.group().replaceFirst("/", "")));
        }
        while (sentmatcher.find()){
            origin = origin.replaceFirst(sentmatcher.group(), fillSentence(ResourceReader.getRandomSentence(sentmatcher.group().replaceFirst("~", ""))));
        }
        if(origin.contains("/") || origin.contains("~")){
            return fillSentence(origin);
        }else {
            return origin;
        }
    }

    public static String random(String structure){
        Pattern ranpattern = Pattern.compile("<.+>");
        Matcher matcher = ranpattern.matcher(structure);
        String origin = structure;
        while (matcher.find()){
            String str = matcher.group();
            String[] items = str.replaceAll("[<>]", "").split(", *");
            Random ran = new Random();
            String replacement = items[ran.nextInt(items.length)];
            origin = origin.replaceFirst(str, replacement);
        }
        return origin;
    }
}
