package de.dhbwmannheim.snakebytes.GUI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JasonReaderScoreboard {
    public static void run() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("./res/test.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray namearr = (JSONArray) jsonObject.get("history");
            for (Object objInArr : namearr) {
                JSONObject jsonKunde = (JSONObject) objInArr;
                System.out.println(jsonKunde.get("datum"));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class ScoreboardData {

}
