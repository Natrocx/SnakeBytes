package de.dhbwmannheim.snakebytes;

//by Robert Sedlmeier and Eric Stefan
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.json.simple.parser.ParseException;

public class JsonHandler {

    static String workingDirectory;
    public enum KeyOfHashMap{
        KEYBOARD_KEY,
        ACTION
    }

    //set the String workingDirectory based on the operating systems appdata specific folder and create a folder "SnakeBytes" if needed
    public static void setDirectory() {
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
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "/Library/Application Support/SnakeBytes";
        }
        File file = new File(workingDirectory);
        file.mkdirs();
    }

    //save the default keySettings json into the working directory
    public static void saveDefaultJson(){
        setDirectory();
        File file = new File("src/main/resources/keySettings.json");
        file.renameTo(new File(workingDirectory+"/keySettings.json"));

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

        try (FileWriter file = new FileWriter(workingDirectory+"/keySettings.json")) {
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
        FileReader reader = new FileReader(workingDirectory+"/keySettings.json");
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
}



