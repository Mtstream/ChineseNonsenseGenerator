package org.mtstream.cng.stringGenerator;

import org.json.simple.parser.ParseException;
import org.mtstream.cng.resourcesInteractor.ResourceReader;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nonsense {

    public static final String PUNC_REGEX = "[，。！？]";

    public static String create(int i) throws IOException, ParseException {
        StringBuilder builder = new StringBuilder();
        while (builder.length() < i){
            builder.append(SentenceFactory.getMainFactory().fillSentence(ResourceReader.getRandomSentence()));
            boolean needPunc = !isPunc(builder.toString().charAt(builder.length()-1));
            if(needPunc) builder.append(genPunc());
        }
        if(builder.charAt(builder.length()-1) == '，'){
            builder.setCharAt(builder.length()-1, '。');
        }
        String str = builder.toString();
        str = clearDuplicatedPunc(str);
        return str;
    }

    public static boolean isPunc(char c) {
        Pattern puncPattern = Pattern.compile(PUNC_REGEX);
        Matcher matcher = puncPattern.matcher(Character.toString(c));
        return matcher.matches();
    }

    public static char genPunc() {
        List<Character> punctuations = List.of('，', '。', '。', '！');
        return (char) ResourceReader.getRandomElement(punctuations);
    }

    public static String clearDuplicatedPunc(String str) {
        System.out.println("Tidying: "+"正在清除重叠标点");
        if(isPunc(str.charAt(0))) str = str.replaceFirst(PUNC_REGEX, "");
        Pattern pattern = Pattern.compile("[，。？！]+");
        Matcher matcher = pattern.matcher(str);
        String origin = str;
        while (matcher.find()) {
            String puncs = matcher.group();
            char replacement = '，';
            if (puncs.contains("。")) replacement = '。';
            if (puncs.contains("！")) replacement = '！';
            if (puncs.contains("？")) replacement = '？';
            origin = origin.replaceFirst(matcher.group(), String.valueOf(replacement));
        }
        return origin;
    }
}
