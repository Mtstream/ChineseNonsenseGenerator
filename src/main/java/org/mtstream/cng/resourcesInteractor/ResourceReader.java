package org.mtstream.cng.resourcesInteractor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@SuppressWarnings({"unchecked","rawtypes"})
public class ResourceReader {


    public static JSONObject getJsonObj(String loc) throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream(loc);
        assert is != null;
        JSONObject obj = (JSONObject) JSONValue.parse(new InputStreamReader(is));
        is.close();
        return obj;
    }

    public static String getRandomSentence() throws IOException {
        JSONObject obj = (JSONObject) getRandomElement(getJsonObj("sentenceStructure/sentence_structure.json").values().stream().toList());
        List<String> list = calcWeight(obj, true);
        if(list == null)return getRandomSentence();
        return (String) getRandomElement(list);
    }

    public static String getRandomSentence(String type) throws IOException {
        JSONObject obj = (JSONObject) getJsonObj("sentenceStructure/sentence_structure.json").get(type);
        System.out.println(obj);
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
            if(ignoreLocked && ((JSONObject) innerObj).containsKey("locked") && (boolean)((JSONObject) innerObj).get("locked")){
                continue;
            }
            double objWeight = (double)((JSONObject) innerObj).get("weight");
            if(weight >= temp && weight < temp + objWeight){
                return ((JSONArray)((JSONObject) innerObj).get("sentences")).stream().toList();
            }
            temp += objWeight;
        }
        return null;
    }

    public static String getRandomWord(String type) throws IOException {
        JSONArray arr = (JSONArray) getJsonObj("word/word.json").get(type);
        if(arr == null)return "*Not Found*";
        List<String> list = arr.stream().toList();
        return (String) getRandomElement(list);
    }

    public static List<String> getAllWordType() throws IOException, ParseException {
        List<Map.Entry> outerList = getJSONList(getJsonObj("word/word.json"));
        List<String> list = new ArrayList<>();
        for(Map.Entry entry : outerList){
            list.add((String) entry.getKey());
        }
        return list;
    }

    public static Object getRandomElement(List<? extends Object> coll){
        Random r = new Random();
        return coll.get(r.nextInt(coll.size()));
    }

    public static Object getRandomValue(List<Map.Entry> coll) throws ParseException {
        Random r = new Random();
        return coll.get(r.nextInt(coll.size())).getValue();
    }

    public static Object getRandomKey(List<Map.Entry> coll) throws ParseException {
        Random r = new Random();
        return coll.get(r.nextInt(coll.size())).getKey();
    }

    public static List getJSONList(String loc) throws IOException, ParseException {
        return getJSONList(getJsonObj(loc));
    }
    public static List getJSONList(JSONObject obj) throws IOException, ParseException {
        ContainerFactory factory = new ContainerFactory() {
            @Override
            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

            @Override
            public List creatArrayContainer() {
                return new LinkedList();
            }
        };
        JSONParser parser = new JSONParser();
        String str = obj.toJSONString();
        Map JSONMap = (Map)parser.parse(str, factory);

        return (List<Map.Entry>) JSONMap.entrySet().stream().toList();
    }
}
