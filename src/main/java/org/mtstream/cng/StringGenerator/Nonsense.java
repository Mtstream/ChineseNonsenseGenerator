package org.mtstream.cng.StringGenerator;

import org.json.simple.parser.ParseException;
import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nonsense {
    public static String create(int i) throws IOException, ParseException {
        StringBuilder builder = new StringBuilder();
        while (builder.length() < i){
            builder.append(SentenceFactory.fillSentence(ResourceReader.getRandomSentence()));
            if(needPunc(builder.toString().charAt(builder.length()-1))) builder.append(genPunc());
        }
        if(builder.charAt(builder.length()-1) == '，'){
            builder.setCharAt(builder.length()-1, '。');
        }
        return builder.toString();
    }
    public static boolean needPunc(char c){
        Pattern canCanNeed = Pattern.compile("[\u4e00-\u9fa5“”]");
        Matcher matcher = canCanNeed.matcher(Character.toString(c));
        return matcher.matches();
    }
    public static char genPunc(){
        return switch (new Random().nextInt(3)) {
            case 0 -> '，';
            case 1 -> '。';
            case 2 -> '！';
            default -> '。';
        };
    }
}
