package org.mtstream.cng.sentenceElement;

import org.json.simple.JSONArray;
import org.mtstream.cng.resourcesInteractor.ResourceReader;
import org.mtstream.cng.sentenceElement.sentenceFiller.WordFiller;
import org.mtstream.cng.stringGenerator.SentenceFactory;

import java.util.*;
import java.util.regex.Pattern;

public class SpecialElementHandler {

    private static Map<Pattern, SpecialElement> specialElementMap = new HashMap<>();
    private static Map<String, String> storageMap = new HashMap<>();

    private static final Random RANDOM = new Random();

    public static void bootstrap(){
        specialElementMap.put(Pattern.compile("RandomInt"), (s)->{
            Random r = new Random();
            return String.valueOf(r.nextInt());
        });
        specialElementMap.put(Pattern.compile("RandomInt=[0-9]+"), (s)->{
            int i = Integer.parseInt(s.replaceAll("RandomInt=", ""));
            Random r = new Random();
            return String.valueOf(r.nextInt(i));
        });
        specialElementMap.put(Pattern.compile("RandomInt=-?[0-9]+=to=-?[0-9]+"), (s)->{
            String[] range = s.replaceAll("RandomInt=", "").split("=to=");
            Random r = new Random();
            return String.valueOf(Integer.parseInt(range[0])+r.nextInt(Integer.parseInt(range[1])));
        });
        specialElementMap.put(Pattern.compile("Repeat=.*=for=[0-9]+"), (s)->{
            String[] results = s.replaceFirst("Repeat=", "").split("=for=");
            int times = Integer.parseInt(results[1]);
            StringBuilder product = new StringBuilder();
            for (int i = 0;i<times;i++){
                product.append(SentenceFactory.getMainFactory().fillSentence(results[0]));
            }
            return product.toString();
        });
        specialElementMap.put(Pattern.compile("Store=.*(?!=)"), (s)->{
            String result = s.replaceFirst("Store=", "");
            String filledResult = SentenceFactory.getMainFactory().fillSentence(result);
            storageMap.put("Storage-Unnamed", filledResult);
            return filledResult;
        });
        specialElementMap.put(Pattern.compile("Store=.*=key=.*"), (s)->{
            String[] results = s.replaceFirst("Store=", "").split("=key=");
            String filledResult = SentenceFactory.getMainFactory().fillSentence(results[0]);
            storageMap.put("Storage-"+results[1], filledResult);
            return filledResult;
        });
        specialElementMap.put(Pattern.compile("Take(?!=)"), (s)-> {
            String took = storageMap.get("Storage-Unnamed");
            return took==null?"*提取的字符串为null*":took;
        });
        specialElementMap.put(Pattern.compile("Take=.*"), (s)->{
            String result = s.replaceFirst("Take=", "");
            String took = storageMap.get("Storage-"+result);
            return took==null?"*提取的字符串为null*":took;
        });
        specialElementMap.put(Pattern.compile("Clear=.*=in=.*"), (s)->{
            String[] results = s.replaceFirst("Clear=", "").split("in");
            String filled = SentenceFactory.getMainFactory().fillSentence(results[1]);
            return filled.replaceAll(results[0], "");
        });
        specialElementMap.put(Pattern.compile("EndPunc"), (s)-> new Random().nextBoolean()? "。" : "！");

        specialElementMap.put(Pattern.compile("StipulateWordLength=.*=length=[0-9]+"), (s)->{
            String[] results = s.replaceFirst("StipulateWordLength=", "").split("=length=");
            String wordType = results[0];
            JSONArray arr = (JSONArray) ResourceReader.getJsonObj(ResourceReader.WORD_LOC).get(wordType);
            if(arr == null)return ResourceReader.NOT_FOUND;
            List<String> allWords = arr.stream().toList();
            if(allWords.isEmpty()) return "*找不到词语*";
            List<String> targetWords = new ArrayList<>();
            for(String word : allWords) {
                if(word.length() == Integer.parseInt(results[1])) {
                    targetWords.add(word);
                }
            }
            return (String) ResourceReader.getRandomElement(targetWords);
        });
        specialElementMap.put(Pattern.compile("DrawWord=.*"), (s)-> {
            String result = s.replaceFirst("DrawWord=", "");
            String took = storageMap.get("DrawTube-"+result);
            if (took == null) {
                took = new WordFiller().getReplacement(result);
                storageMap.put("DrawTube-"+result, took);
            }
            if(RANDOM.nextInt(3)==0) {
                storageMap.put("DrawTube-"+result, new WordFiller().getReplacement(result));
            }
            return took;
        });
        specialElementMap.put(Pattern.compile("UpdateDrawWord=.*"), (s)-> {
            String result = s.replaceFirst("UpdateDrawWord=", "");
            String word = new WordFiller().getReplacement(result);
            storageMap.put("DrawTube-"+new WordFiller().getReplacement(result), word);
            return word;
        });
    }

    public static String resolve(String str){
        for(Map.Entry<Pattern, SpecialElement> entry : specialElementMap.entrySet()){
            if(!entry.getKey().matcher(str).matches())continue;
            return entry.getValue().convert(str);
        }
        return ResourceReader.NOT_FOUND;
    }

    public interface SpecialElement {
        String get(String s);
        default String convert(String s){
            return get(s);
        }
    }

}
