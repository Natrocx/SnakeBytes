package de.dhbwmannheim.snakebytes.ECS.Systems;

//by Robert Sedlmeier
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import org.json.simple.parser.ParseException;

class JsonHandler {

    static String workingDirectory;

    public void getDirectory() {
        String OS = (System.getProperty("os.name")).toUpperCase();
        if (OS.contains("WIN"))
        {
            workingDirectory = System.getenv("AppData");
            workingDirectory += "/SnakeBytes/keySettings.json";
        }
        else if(OS.contains("LINUX"))
        {
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "/.local/share/SnakeBytes/keySettings.json";
        }else{
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "/Library/Application Support/SnakeBytes/keySettings.json";
        }
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
        player1.put("up", p1table.get("up"));
        player1.put("down", p1table.get("down"));
        player1.put("attack", p1table.get("attack"));
        player1.put("special", p1table.get("special"));
        player2.put("left", p2table.get("left"));
        player2.put("right", p2table.get("right"));
        player2.put("up", p2table.get("up"));
        player2.put("down", p2table.get("down"));
        player2.put("attack", p2table.get("attack"));
        player2.put("special", p2table.get("special"));

        //Einstellungen für Zugriff auf Spieler 1 bzw Spieler 2 schachteln
        p1name.put("player1", player1);
        p2name.put("player2", player2);

        //Einstellungen in JSON-Array zum Speichern packen
        JSONArray settingsList = new JSONArray();
        settingsList.add(p1name);
        settingsList.add(p2name);

        try (FileWriter file = new FileWriter(workingDirectory)) {
            file.write(settingsList.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    //Holt die Steuerungseinstellungen eines Spielers
    public static Hashtable<String, String> fromJson(String player) throws IOException, ParseException {
        Hashtable<String, String> playersettings = new Hashtable<>();

        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(workingDirectory);
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
            //key= keyboard key; and value= action to execute
            playersettings.put(obj2.get(temp).toString(), temp);
        }

        return playersettings;
    }
}



