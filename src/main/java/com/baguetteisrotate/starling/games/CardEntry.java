package com.baguetteisrotate.starling.games;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.baguetteisrotate.starling.Entry;
import com.baguetteisrotate.starling.Tracker;
import com.fasterxml.jackson.core.type.TypeReference;

public class CardEntry extends Entry {
    private static final File FILE = new File("cards_history.json");
    private static final TypeReference<HashMap<String, HashMap<String, Integer>>> FILE_TYPE = new TypeReference<>() {
    };

    private HashMap<String, Integer> statmap;

    public void set(HashMap<String, Integer> map) {
        statmap = map;
    }

    public void save() {
        try {
            HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
            if (FILE.exists()) {
                map = Tracker.MAPPER.readValue(FILE, FILE_TYPE);
            }
            map.put("cards", statmap);
            Tracker.MAPPER.writerWithDefaultPrettyPrinter().writeValue(FILE, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Integer> load() {
        HashMap<String, Integer> smallMap = defaultStats();
        try {
            HashMap<String, HashMap<String, Integer>> map = Tracker.MAPPER.readValue(FILE, FILE_TYPE);
            HashMap<String, Integer> saved = map.get("cards");
            if (saved != null) {
                smallMap.putAll(saved); 
            }
        } catch (Exception e) {
        }
        return smallMap;
    }

    private static HashMap<String, Integer> defaultStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        stats.put("highest_score", 0);
        stats.put("total_games", 0);
        stats.put("total_wins", 0);
        stats.put("curr_score", 0);
        stats.put("highest_streak", 0);
        stats.put("curr_streak", 0);
        return stats;
    }

    public static void update(HashMap<String, Integer> statmap, boolean theUserseshasTheWinses) {
        statmap.put("total_games", statmap.get("total_games") + 1);

        int curr_streak = statmap.get("curr_streak");
        if (theUserseshasTheWinses) {
            statmap.put("curr_streak", curr_streak + 1);
            statmap.put("total_wins", statmap.get("total_wins") + 1);
            statmap.put("curr_score", statmap.get("curr_score") + 5 * statmap.get("curr_streak"));
        } else {
            if (curr_streak > 0) {
                curr_streak = 0;
            }
            statmap.put("curr_streak", curr_streak - 1);
            // streak is negative here, so a loss would ADD points
            statmap.put("curr_score", statmap.get("curr_score") + 5 * statmap.get("curr_streak"));
        }

        statmap.put("highest_score", Math.max(statmap.get("highest_score"), statmap.get("curr_score")));
        statmap.put("highest_streak", Math.max(statmap.get("highest_streak"), statmap.get("curr_streak")));
    }

    public int getValue() {
        return statmap.get("curr_score");
    }

    public void setValue(int value) {
        this.statmap.put("curr_score", value);
    }
}