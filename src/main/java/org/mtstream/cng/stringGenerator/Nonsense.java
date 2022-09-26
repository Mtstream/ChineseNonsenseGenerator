package org.mtstream.cng.stringGenerator;

import org.json.simple.parser.ParseException;
import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nonsense {
    public static String create(int i) throws IOException, ParseException {
        StringBuilder builder = new StringBuilder();
        while (builder.length() < i){
            builder.append(SentenceFactory.fillSentence(ResourceReader.getRandomSentence()));
            boolean needPunc = needPunc(builder.toString().charAt(builder.length()-1));
            if(needPunc) builder.append(genPunc());
        }
        if(builder.charAt(builder.length()-1) == '，'){
            builder.setCharAt(builder.length()-1, '。');
        }
        String str = builder.toString();
        str = clearDuplicatedPunc(str);
        return str;
    }

    public static boolean needPunc(char c) {
        Pattern puncPattern = Pattern.compile("[^。？！]");
        Matcher matcher = puncPattern.matcher(Character.toString(c));
        return matcher.matches();
    }

    public static char genPunc() {
        return switch (new Random().nextInt(3)) {
            case 0 -> '，';
            case 1 -> '。';
            case 2 -> '！';
            default -> '。';
        };
    }

    public static String clearDuplicatedPunc(String str) {
        Pattern pattern = Pattern.compile("[。？！]+");
        Matcher matcher = pattern.matcher(str);
        String origin = str;
        while (matcher.find()) {
            String puncs = matcher.group();
            char replacement = '。';
            if (puncs.contains("！")) replacement = '！';
            if (puncs.contains("？")) replacement = '？';
            origin = origin.replaceFirst(matcher.group(), String.valueOf(replacement));
        }
        return origin;
    }
}
