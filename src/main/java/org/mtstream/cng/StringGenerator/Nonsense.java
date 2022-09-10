package org.mtstream.cng.StringGenerator;

import org.json.simple.parser.ParseException;
import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.io.IOException;
import java.util.Random;

public class Nonsense {
    public static String create(int i) throws IOException, ParseException {
        StringBuilder builder = new StringBuilder("");
        for(int j = 0 ; j < i ; j++){
            builder.append(SentenceGenerator.fillSentence(ResourceReader.getRandomSentence()));
            if(needPunc(builder.toString().charAt(builder.length()-1))) builder.append(new Random().nextBoolean()?"，":"。");
        }
        return builder.toString();
    }
    public static boolean needPunc(char c){
        return c > 0x4E00 && c < 0x9FBF || c == '“' || c == '”';
    }
}
