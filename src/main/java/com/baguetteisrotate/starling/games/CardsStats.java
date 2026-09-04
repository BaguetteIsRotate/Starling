package com.baguetteisrotate.starling.games;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CardsStats {
    private HashMap<String, Integer> statmap;
    public void set(HashMap<String,Integer> map){
        statmap=map;
    }
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("cards.json");
            TypeReference<HashMap<String, HashMap<String, Integer>>> typeRef = new TypeReference<HashMap<String, HashMap<String, Integer>>>() {
            };
            HashMap<String, HashMap<String, Integer>> map = mapper.readValue(file, typeRef);
            map.put("cards", statmap);
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter("cards.json")));
            writer.println(s);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Integer> load() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("cards.json");
            TypeReference<HashMap<String, HashMap<String, Integer>>> typeRef = new TypeReference<HashMap<String, HashMap<String, Integer>>>() {
            };
            HashMap<String, HashMap<String, Integer>> map = mapper.readValue(file, typeRef);
            HashMap<String, Integer> smallMap = map.get("cards");
            if (smallMap == null) {
                smallMap = new HashMap<>();
                smallMap.put("highest_score", 0);
                smallMap.put("total_games", 0);
                smallMap.put("total_wins", 0);
                smallMap.put("curr_score", 0);
                smallMap.put("highest_streak", 0);
                smallMap.put("curr_streak", 0);
            }
            return smallMap;
        } catch (Exception e) {
            HashMap<String, Integer> smallMap = new HashMap<>();
            smallMap.put("highest_score", 0);
            smallMap.put("total_games", 0);
            smallMap.put("total_wins", 0);
            smallMap.put("curr_score", 0);
            smallMap.put("highest_streak", 0);
            smallMap.put("curr_streak", 0);
            return smallMap;
        }
    }

    public static void update(HashMap<String, Integer> statmap, boolean theUserseshasTheWinses) {
        statmap.put("total_games", statmap.get("total_games") + 1);

        int curr_streak = statmap.get("curr_streak");
        if (theUserseshasTheWinses) {
            statmap.put("curr_streak", curr_streak + 1);
            statmap.put("highest_streak",Math.max(statmap.get("highest_streak"), curr_streak + 1));
            statmap.put("total_wins", statmap.get("total_wins")+1);

            int curr_score = statmap.get("curr_score") + 5*statmap.get("curr_streak");
            statmap.put("curr_score", curr_score);
            statmap.put("highest_score",Math.max(statmap.get("highest_score"), curr_score));


            if (curr_streak < 0) {
                curr_streak = 0;
            }
            statmap.put("curr_streak", statmap.get("curr_streak")+1);
        } else {
            if (curr_streak > 0) {
                curr_streak = 0;
            }
            statmap.put("curr_streak", curr_streak - 1);

            int curr_score = statmap.get("curr_score") - 5*statmap.get("curr_streak");
            statmap.put("curr_score", curr_score);
        }
    }
}
