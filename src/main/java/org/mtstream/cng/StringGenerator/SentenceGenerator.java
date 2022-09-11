package org.mtstream.cng.StringGenerator;

import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.io.IOException;
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
}
