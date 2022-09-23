package org.mtstream.cng.StringGenerator;

import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceFactory {

    public static String fillSentence(String structure) throws IOException {
        String wordregx = "/(.{4})";
        String sentregx = "~(.{4})";

        Pattern wordpattern = Pattern.compile(wordregx, Pattern.CASE_INSENSITIVE);
        Pattern sentpattern = Pattern.compile(sentregx, Pattern.CASE_INSENSITIVE);

        Matcher wordmatcher = wordpattern.matcher(structure);
        Matcher sentmatcher = sentpattern.matcher(structure);


        String origin = structure;
        //装填特殊元素格式
        origin = fillSpecialElement(origin);
        //装填随机抽取格式
        origin = randomSelect(origin);
        //装填词语
        while (wordmatcher.find()){
            String replacement = ResourceReader.getRandomWord(wordmatcher.group(1));
            if (replacement.equals(ResourceReader.NOT_FOUND)){
                replacement = "*找不到词语："+replacement+"*";
            }
            origin = origin.replaceFirst(wordmatcher.group(), replacement);
        }
        //装填句子模板
        while (sentmatcher.find()){
            origin = origin.replaceFirst(sentmatcher.group(), fillSentence(ResourceReader.getRandomSentence(sentmatcher.group(1))));
        }
        if(origin.contains("/") || origin.contains("~")){
            return fillSentence(origin);
        }else {
            return origin;
        }
    }

    private static String randomSelect(String structure){
        Pattern ranpattern = Pattern.compile("<(.|[^<>]+)>");
        Matcher matcher = ranpattern.matcher(structure);
        String origin = structure;
        while (matcher.find()){
            String found = matcher.group(1);
            String[] items = found.split(", *");
            Random ran = new Random();
            String replacement = items[ran.nextInt(items.length)];
            origin = origin.replaceFirst(matcher.group(), replacement);
        }
        return origin;
    }

    private static String fillSpecialElement(String structure){
        Pattern pattern = Pattern.compile("@(.|[^@#]+)#");
        Matcher matcher = pattern.matcher(structure);
        String origin = structure;
        while (matcher.find()){
            String found = matcher.group(1);
            origin = origin.replaceFirst(matcher.group(), SpecialElementHandler.resolve(found));
        }
        if(origin.contains("@")&&origin.contains("#")){
            return fillSpecialElement(origin);
        }else {
            return origin;
        }
    }
}
