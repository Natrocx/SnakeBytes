package de.dhbwmannheim.snakebytes;

//by Robert Sedlmeier and Eric Stefan
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.Hashtable;
import java.util.Objects;

import org.json.simple.parser.ParseException;

public class JsonHandler {

    static String workingDirectory;
    public enum KeyOfHashMap{
        KEYBOARD_KEY,
        ACTION
    }

    //set the String workingDirectory based on the operating systems appdata specific folder and create a folder "SnakeBytes" if needed
    private static void setDirectory() {
        String OS = (System.getProperty("os.name")).toUpperCase();
        if (OS.contains("WIN"))//Windows
        {
            workingDirectory = System.getenv("AppData");
            workingDirectory += "/SnakeBytes";
        }
        else if(OS.contains("LINUX"))//Linux
        {
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "/.local/share/SnakeBytes";
        }else{//macOS
            workingDirectory = ".";
        }
        File file = new File(workingDirectory);
        file.mkdirs();
    }

    //save the default keySettings json into the working directory
    public static void saveDefaultJson(){
        setDirectory();
        File file = new File(Objects.requireNonNull(JsonHandler.class.getResource("/settings/keySettings.json")).toString());
        File file2 = new File(workingDirectory+ "/settings/keySettings.json");
        file.renameTo(new File(workingDirectory+ "/settings/keySettings.json"));
//        try {
//            Files.copy(file.toPath(),file2.toPath());
//        } catch (IOException e) {
//            e.printStackTrace();
////        }
//        JSONParser jsonParser = new JSONParser();
//        FileReader reader = null;
//        try {
//            reader = new FileReader(JsonHandler.class.getResource("/settings/keySettings.json").toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Object obj = null;
//        try {
//            obj = jsonParser.parse(reader);
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
//        JSONArray arr = (JSONArray) obj;
//        File file0 = new File(workingDirectory+ "/settings/keySettings.json");
//        file0.getParentFile().mkdirs();
//        try {
//            file0.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try (FileWriter fileWriter = new FileWriter(workingDirectory+ "/settings/keySettings.json")) {
//            fileWriter.write(arr.toJSONString());
//            fileWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static JSONArray toScoreboardJson(String[] stringArray) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(workingDirectory+"/scoreboard.json");
        Object obj = jsonParser.parse(reader);
        JSONArray arr = (JSONArray) obj;

        JSONObject scoreboard = new JSONObject();
        scoreboard.put("date",stringArray[0]);
        scoreboard.put("scoreP1",stringArray[1]);
        scoreboard.put("scoreP2",stringArray[2]);

        //adding the JsonObject scoreboard at the beginning of JsonArray
        arr.add(0,scoreboard);

        File file0 = new File(workingDirectory+"/scoreboard.json");
        file0.getParentFile().mkdirs();
        file0.createNewFile();

        try (FileWriter file = new FileWriter(workingDirectory+"/scoreboard.json")) {
            file.write(arr.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static JSONArray fromScoreboardJson() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(workingDirectory+"/scoreboard.json");
        Object obj = jsonParser.parse(reader);
        JSONArray arr = (JSONArray) obj;

        return arr;
    }

    //Schreibt die Steuerungseinstellungen in JSON-Datei
    public static void toJson(Hashtable<String, String> p1table, Hashtable<String, String> p2table) throws IOException {

        JSONObject player1 = new JSONObject();
        JSONObject player2 = new JSONObject();
        JSONObject p1name = new JSONObject();
        JSONObject p2name = new JSONObject();

        //Steuerungseinstellungen in JSON-Objekt
        player1.put("left", p1table.get("left"));
        player1.put("right", p1table.get("right"));
        player1.put("jump", p1table.get("jump"));
        player1.put("attack", p1table.get("attack"));
        player1.put("specialAttack", p1table.get("specialAttack"));

        player2.put("left", p2table.get("left"));
        player2.put("right", p2table.get("right"));
        player2.put("jump", p2table.get("jump"));
        player2.put("attack", p2table.get("attack"));
        player2.put("specialAttack", p2table.get("specialAttack"));

        //Einstellungen für Zugriff auf Spieler 1 bzw Spieler 2 schachteln
        p1name.put("player1", player1);
        p2name.put("player2", player2);

        //Einstellungen in JSON-Array zum Speichern packen
        JSONArray settingsList = new JSONArray();
        settingsList.add(p1name);
        settingsList.add(p2name);

        try (FileWriter file = new FileWriter(workingDirectory+ "/settings/keySettings.json")) {
            file.write(settingsList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //gets the keySettings Json of a player
    public static Hashtable<String, String> fromJson(String player, KeyOfHashMap keyOfHashMap) throws IOException, ParseException {
        Hashtable<String, String> playersettings = new Hashtable<>();

        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(workingDirectory+ "/settings/keySettings.json");
        Object obj = jsonParser.parse(reader);
        JSONArray arr = (JSONArray) obj;

        int help;
        if (player.equals("player1")) {
            help = 0;
        } else {
            help = 1;
        }

        JSONObject obj2 = (JSONObject) ((JSONObject) arr.get(help)).get(player);
        for (int i = 0; i < obj2.size(); i++) {
            String temp = obj2.keySet().stream().toList().get(i).toString();
            if (keyOfHashMap==KeyOfHashMap.ACTION){
                //key= action to execute; and value= keyboard key
                playersettings.put(temp,obj2.get(temp).toString());
            }else if(keyOfHashMap==KeyOfHashMap.KEYBOARD_KEY){
                //for the InputSystem it seems useful that the keyboard key is the key of the HashMap
                //key= keyboard key; and value= action to execute
                playersettings.put(obj2.get(temp).toString(),temp);
            }
        }
        return playersettings;
    }

    public static void saveScoreboardDefaultJson() throws IOException, ParseException {
        setDirectory();
        File file0 = new File(workingDirectory+"/scoreboard.json");
        file0.createNewFile();

        try (FileWriter file = new FileWriter(workingDirectory+"/scoreboard.json")) {
            file.write("[]");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



