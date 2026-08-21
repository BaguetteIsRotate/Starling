package graphing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Graph {
    private String xlabel;
    private String ylabel;
    private String title;
    private HashMap<Integer, String> xvalues;
    private HashMap<Integer, Integer> yvalues;
    private HashMap<String, Object> map = new HashMap<>();
    private String path = "";

    // constructor method with input values
    public Graph(String title, String xlabel, HashMap<Integer, String> xvalues, String ylabel,
            HashMap<Integer, Integer> yvalues) {
        this.title = title;
        this.xlabel = xlabel;
        this.ylabel = ylabel;
        this.xvalues = xvalues;
        this.yvalues = yvalues;
        map.put("xlabel", this.xlabel);
        map.put("ylabel", this.ylabel);
        map.put("xval", this.xvalues);
        map.put("yval", this.yvalues);
        map.put("title", this.title);
    }

    // constructor method that takes in path
    public Graph(String path) {
        this.path = path;
    }

    // One must imagine sisyphus happy and run loadDataFromPath if they did the
    // second constructor
    @SuppressWarnings("unchecked")
    public boolean loadDataFromPath() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(this.path);
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
            };
            HashMap<String, Object> map = mapper.readValue(file, typeRef);
            this.xlabel = map.get("xlabel").toString();
            this.xvalues = (HashMap<Integer, String>) map.get("xval");
            this.ylabel = map.get("ylabel").toString();
            this.yvalues = (HashMap<Integer, Integer>) map.get("yval");
            this.title = map.get("title").toString();
            this.map = map;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void addPath(String path1) {
        this.path = path1;
    }

    public void reload(String title, String xlabel, HashMap<Integer, String> xvalues, String ylabel,
            HashMap<Integer, Integer> yvalues) {
        this.title = title;
        this.xlabel = xlabel;
        this.ylabel = ylabel;
        this.xvalues = xvalues;
        this.yvalues = yvalues;
        map.put("xlabel", this.xlabel);
        map.put("ylabel", this.ylabel);
        map.put("xval", this.xvalues);
        map.put("yval", this.yvalues);
        map.put("title", this.title);
    }

    public boolean save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.map);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.path)));
            writer.println(s);
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean save(String path1) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.map);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path1)));
            writer.println(s);
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public JPanel makePanel() {
        JPanel x = new JPanel();
        JLabel label = new JLabel("Rate your day on a scale of 1-5!");
        x.add(label);
        return x;
    }
}