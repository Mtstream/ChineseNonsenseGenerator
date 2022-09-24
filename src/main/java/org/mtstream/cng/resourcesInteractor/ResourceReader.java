package org.mtstream.cng.resourcesInteractor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings({"unchecked","rawtypes"})
public class ResourceReader {

    public static final String NOT_FOUND = "*NotFound";
    public static final String SENTENCE_LOC = "sentence_structure/sentence_structure.json";
    public static final String WORD_LOC = "word/word.json";

    public static JSONObject getJsonObj(String loc) {
        InputStream is = ClassLoader.getSystemResourceAsStream(loc);
        assert is != null;
        JSONObject result = (JSONObject) JSONValue.parse(new InputStreamReader(is, StandardCharsets.UTF_8));
        try {
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String getRandomSentence() {
        JSONObject obj = (JSONObject) getRandomElement(getJsonObj(SENTENCE_LOC).values().stream().toList());
        List<String> list = calcWeight(obj, true);
        if(list == null)return getRandomSentence();
        return (String) getRandomElement(list);
    }

    public static String getRandomSentence(String type) {
        JSONObject obj = (JSONObject) getJsonObj(SENTENCE_LOC).get(type);
        List<String> list = calcWeight(obj, false);
        if(list == null)return getRandomSentence(type);
        return (String) getRandomElement(list);
    }

    public static List calcWeight(JSONObject obj, boolean ignoreLocked){
        double weightSum = 0;
        for(Object innerObj : obj.values()){
            weightSum += (double)((JSONObject) innerObj).get("weight");
        }
        double weight = weightSum * new Random().nextDouble();
        double temp = 0;
        for(Object innerObj : obj.values()){
            if(ignoreLocked){
                if(((JSONObject) innerObj).containsKey("locked") && (boolean)((JSONObject) innerObj).get("locked")){
                    continue;
                }
            }
            double objWeight = (double)((JSONObject) innerObj).get("weight");
            if(weight >= temp && weight < temp + objWeight){
                return ((JSONArray)((JSONObject) innerObj).get("sentences")).stream().toList();
            }

            temp += objWeight;
        }
        return null;
    }

    public static String getRandomWord(String type) {
        JSONArray arr = (JSONArray) getJsonObj(WORD_LOC).get(type);
        if(arr == null)return NOT_FOUND;
        List<String> list = arr.stream().toList();
        return (String) getRandomElement(list);
    }


    public static Object getRandomElement(List<?> coll){
        Random r = new Random();
        return coll.get(r.nextInt(coll.size()));
    }

}
