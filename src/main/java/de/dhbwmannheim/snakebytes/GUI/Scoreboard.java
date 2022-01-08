package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Author:  @Kai Schwab
 *          @Eric Stefan
 **/


public class Scoreboard extends VBox {
    static int scoreboardIndex=0;
    static ArrayList<SB_Item> scoreboardList = new ArrayList<>();

    //this function calls the JsonHandler function for saving the current date and game results into the scoreboard.json
    public static void saveScoreboardToJson() throws IOException, ParseException {
        String currentDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate now = LocalDate.now();
        currentDate=formatter.format(now);
        JsonHandler.toScoreboardJson(new String[]{currentDate,String.valueOf(GameOverlay.scP1),String.valueOf(GameOverlay.scP2)});
    }

    public Scoreboard(Stage primaryStage) throws IOException, ParseException {
        JSONArray jsonArray;
        jsonArray = JsonHandler.fromScoreboardJson();
        scoreboardIndex=0;
        scoreboardList.clear();
        //iterate over each JSONObject out of the scoreboard.json and create a SB_Item out of each, whereby all SB_Items are saved into the scoreboardList
        for (Object o : jsonArray) {
            JSONObject scoreboardElement = (JSONObject) o;
            SB_Item item = new SB_Item(scoreboardElement.get("date").toString(), scoreboardElement.get("scoreP1").toString(), scoreboardElement.get("scoreP2").toString());
            item.setStyle("-fx-border-width: 5px");
            item.setStyle("-fx-border-color: DARKRED");
            item.setTranslateX(75);
            scoreboardList.add(item);
        }

        //Header
        HeaderS headerS = new HeaderS(primaryStage);
        headerS.setTranslateX(-400);
        Button buttonNext = new Button("Nächste Seite");
        buttonNext.setTranslateX(80);
        buttonNext.setTranslateY(5);
        Button buttenPrev = new Button("Vorherige Seite");
        buttenPrev.setTranslateX(80);
        buttenPrev.setTranslateY(10);

        //since only the next three SB_Items should be shown, the scoreboardIndex is added by three
        //and all new needed items are loaded
        buttonNext.setOnMouseClicked(mouseEvent -> {
            if (scoreboardIndex+3<scoreboardList.size()){
                scoreboardIndex=scoreboardIndex+3;
                getChildren().clear();
                getChildren().addAll(headerS, createSeperator());
                showScoreboardItems();
                getChildren().addAll(buttonNext, buttenPrev);
            }
        });
        //since only the previous three SB_Items should be shown, the scoreboardIndex is subtracted by three
        //and all new needed items are loaded
        buttenPrev.setOnMouseClicked(mouseEvent -> {
            if (scoreboardIndex-3>=0){
                scoreboardIndex=scoreboardIndex-3;
                getChildren().clear();
                getChildren().addAll(headerS, createSeperator());
                showScoreboardItems();
                getChildren().addAll(buttonNext, buttenPrev);
            }
        });

        //needed so that the heading stays at its place when there are no scoreboard items
        if (scoreboardIndex==scoreboardList.size()){
            headerS.setTranslateX(0);
        }
        getChildren().addAll(headerS, createSeperator());
        showScoreboardItems();
        getChildren().addAll(buttonNext, buttenPrev);
    }

    //loads 0 until 3 scoreboard items, depending on the amount of items in the scoreboardList after the scoreboardIndex-item
    private void showScoreboardItems(){
        if (scoreboardIndex+3<=scoreboardList.size()){
            getChildren().addAll(scoreboardList.get(scoreboardIndex),scoreboardList.get(scoreboardIndex+1),scoreboardList.get(scoreboardIndex+2));
        }else if(scoreboardIndex+2<=scoreboardList.size()){
            getChildren().addAll(scoreboardList.get(scoreboardIndex),scoreboardList.get(scoreboardIndex+1));
        }else if(scoreboardIndex+1<=scoreboardList.size()){
            getChildren().addAll(scoreboardList.get(scoreboardIndex));
        }else{
            Text text = new Text("Keine Einträge vorhanden.");
            text.setStyle(" -fx-fill: RED; -fx-font-size: 20pt");
            text.setTranslateX(500);
            getChildren().add(text);
        }
    }

    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(200, 150);
        return sep;
    }

}

class HeaderS extends HBox {
    public HeaderS(Stage primaryStage) {
        Title2 titleS = new Title2("Scoreboard");
        titleS.setTranslateX(525);
        titleS.setTranslateY(20);

        Back back = new Back(primaryStage);
        back.setTranslateX(900);
        back.setTranslateY(20);

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(titleS, back);
    }
}

class SB_Item extends HBox {
    public SB_Item(String date, String score_P1, String score_P2) {
        Text p1 = new Text("  Cyber-Kammerjäger   :");
        p1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        p1.setFill(Color.DARKRED);
        Text p2 = new Text(":   Der Exmatrikulator  ");
        p2.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        p2.setFill(Color.DARKRED);
        Text s_p1 = new Text(score_P1);
        s_p1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 30));
        s_p1.setFill(Color.DARKRED);
        Text s_p2 = new Text(score_P2);
        s_p2.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 30));
        s_p2.setFill(Color.DARKRED);
        Text datum = new Text(date);
        datum.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));

        setAlignment(Pos.CENTER);
        getChildren().addAll(p1, createSeperator(), s_p1, createSeperator(), datum, createSeperator(), s_p2, createSeperator(), p2);
    }

    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(140, 150);
        return sep;
    }



}