package games;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CardsStats {
    
    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("starling/src/main/java/games/Cards.json");
            TypeReference<HashMap<String, HashMap<String, Integer>>> typeRef = new TypeReference<HashMap<String, HashMap<String, Integer>>>() {
            };
            HashMap<String, HashMap<String, Integer>> map = mapper.readValue(file, typeRef);
            map.put("cards", statmap);
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter("starling/src/main/java/games/Cards.json")));
            writer.println(s);
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> load() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("starling/src/main/java/games/Cards.json");
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
}
