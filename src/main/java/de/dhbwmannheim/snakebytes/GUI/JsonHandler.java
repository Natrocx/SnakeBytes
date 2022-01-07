package de.dhbwmannheim.snakebytes.GUI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Hashtable;
import java.util.Objects;

import org.json.simple.parser.ParseException;
/**
 * Author:  @Robert Sedlmeier
 *          @Eric Stefan
 **/

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

    //save the default keySettings as keySettings.json into the working directory if it does not exist
    public static void saveDefaultJson(){
        setDirectory();
        File file0 = new File(workingDirectory+"/keySettings.json");
        if (!file0.exists()){
            file0.getParentFile().mkdirs();
            try {
                file0.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Hashtable<String, String> p1 = new Hashtable<>();
            p1.put("left","A");
            p1.put("attack","F");
            p1.put("right","D");
            p1.put("specialAttack","G");
            p1.put("jump","W");
            Hashtable<String,String> p2 = new Hashtable<>();
            p2.put("left","LEFT");
            p2.put("attack","J");
            p2.put("right","RIGHT");
            p2.put("specialAttack","H");
            p2.put("jump","UP");
            try {
                toJson(p1,p2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //add the String values (date, scoreP1, scoreP2) to the scoreboard.json
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

        try (FileWriter file = new FileWriter(workingDirectory+"/scoreboard.json")) {
            file.write(arr.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    //read the scoreboard.json and return a JSONArray
    public static JSONArray fromScoreboardJson() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(workingDirectory+"/scoreboard.json");
        Object obj = jsonParser.parse(reader);
        JSONArray arr = (JSONArray) obj;

        return arr;
    }

    //write the key settings (given by two Hashtables) for controlling the players into the keySettings.json
    public static void toJson(Hashtable<String, String> p1table, Hashtable<String, String> p2table) throws IOException {

        JSONObject player1 = new JSONObject();
        JSONObject player2 = new JSONObject();
        JSONObject p1name = new JSONObject();
        JSONObject p2name = new JSONObject();

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

        //nest the settings for each player
        p1name.put("player1", player1);
        p2name.put("player2", player2);

        //save into JSONArray
        JSONArray settingsList = new JSONArray();
        settingsList.add(p1name);
        settingsList.add(p2name);

        try (FileWriter file = new FileWriter(workingDirectory+ "/keySettings.json")) {
            file.write(settingsList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //gets the keySettings.json of a player
    //thereby the enum KeyOfHashMap is used to change the key, value order given in the Hashtable output
    public static Hashtable<String, String> fromJson(String player, KeyOfHashMap keyOfHashMap) throws IOException, ParseException {
        Hashtable<String, String> playersettings = new Hashtable<>();

        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(workingDirectory+ "/keySettings.json");
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

    //save an empty scoreboard.json if it does not exist
    public static void saveScoreboardDefaultJson() throws IOException, ParseException {
        setDirectory();
        File file0 = new File(workingDirectory+"/scoreboard.json");

        if (!file0.exists()) {
            file0.getParentFile().mkdirs();
            try {
                file0.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (FileWriter file = new FileWriter(workingDirectory + "/scoreboard.json")) {
                file.write("[]");
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



