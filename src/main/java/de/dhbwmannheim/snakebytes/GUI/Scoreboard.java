package de.dhbwmannheim.snakebytes.GUI;

//by Kai Schwab

import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
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


public class Scoreboard extends VBox {
    static ComponentList<CharacterStateComponent> characterStateComponentComponents;
    static int scoreboardJsonArrayIndex=0;

    public static void saveScoreboardToJson() throws IOException, ParseException {
        var player1 = Engine.getPlayer(0);
        var player2 = Engine.getPlayer(1);
        characterStateComponentComponents = ComponentManager.getComponentList(CharacterStateComponent .class);
        var characterStatePlayer1 = characterStateComponentComponents.getComponent(player1);
        var characterStatePlayer2 = characterStateComponentComponents.getComponent(player2);

        String scorePlayer1 = String.valueOf(characterStatePlayer1.lives);
        String scorePlayer2 = String.valueOf(characterStatePlayer2.lives);

        String currentDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate now = LocalDate.now();
        currentDate=formatter.format(now);

        JsonHandler.toScoreboardJson(new String[]{currentDate,scorePlayer1,scorePlayer2});
    }

    public static void fillDummyDataInScoreboard() throws IOException, ParseException {
        JsonHandler.toScoreboardJson(new String[]{"12.12.2021","3","0"});
        JsonHandler.toScoreboardJson(new String[]{"13.12.2021","0","3"});
        JsonHandler.toScoreboardJson(new String[]{"14.12.2021","0","0"});
    }

    public Scoreboard(Stage primaryStage) throws IOException, ParseException {
        JSONArray jsonArray;
        jsonArray = JsonHandler.fromScoreboardJson();

        JSONObject scoreboard1 = (org.json.simple.JSONObject) (jsonArray.get(scoreboardJsonArrayIndex));
        scoreboardJsonArrayIndex++;
        JSONObject scoreboard2 = (org.json.simple.JSONObject) (jsonArray.get(scoreboardJsonArrayIndex));
        scoreboardJsonArrayIndex++;
        JSONObject scoreboard3 = (org.json.simple.JSONObject) (jsonArray.get(scoreboardJsonArrayIndex));
        scoreboardJsonArrayIndex++;

        //Header
        HeaderS headerS = new HeaderS(primaryStage);
        headerS.setTranslateX(-400);
        Button buttonNext = new Button("next page");
        buttonNext.setTranslateX(80);
        buttonNext.setTranslateY(5);
        Button buttenPrev = new Button("previous page");
        buttenPrev.setTranslateX(80);
        buttenPrev.setTranslateY(10);


        //Items
        SB_Item item1 = new SB_Item(scoreboard1.get("date").toString(), scoreboard1.get("scoreP1").toString(), scoreboard1.get("scoreP2").toString());
        item1.setStyle("-fx-border-width: 5px");
        item1.setStyle("-fx-border-color: DARKRED");
        item1.setTranslateX(75);
        SB_Item item2 = new SB_Item(scoreboard2.get("date").toString(), scoreboard2.get("scoreP1").toString(), scoreboard2.get("scoreP2").toString());
        item2.setStyle("-fx-border-width: 5px");
        item2.setStyle("-fx-border-color: DARKRED");
        item2.setTranslateX(75);
        SB_Item item3 = new SB_Item(scoreboard3.get("date").toString(), scoreboard3.get("scoreP1").toString(), scoreboard3.get("scoreP2").toString());
        item3.setStyle("-fx-border-width: 5px");
        item3.setStyle("-fx-border-color: DARKRED");
        item3.setTranslateX(75);

        buttonNext.setOnMouseClicked(mouseEvent -> {
                getChildren().clear();
                JSONObject scoreboard1_temp = (org.json.simple.JSONObject) (jsonArray.get(scoreboardJsonArrayIndex));
                scoreboardJsonArrayIndex++;
                JSONObject scoreboard2_temp = (org.json.simple.JSONObject) (jsonArray.get(scoreboardJsonArrayIndex));
                scoreboardJsonArrayIndex++;
                JSONObject scoreboard3_temp = (org.json.simple.JSONObject) (jsonArray.get(scoreboardJsonArrayIndex));
                scoreboardJsonArrayIndex++;

                SB_Item item1_temp = new SB_Item(scoreboard1_temp.get("date").toString(), scoreboard1_temp.get("scoreP1").toString(), scoreboard1_temp.get("scoreP2").toString());
                item1_temp.setStyle("-fx-border-width: 5px");
                item1_temp.setStyle("-fx-border-color: DARKRED");
                item1_temp.setTranslateX(75);
                SB_Item item2_temp = new SB_Item(scoreboard2_temp.get("date").toString(), scoreboard2_temp.get("scoreP1").toString(), scoreboard2_temp.get("scoreP2").toString());
                item2_temp.setStyle("-fx-border-width: 5px");
                item2_temp.setStyle("-fx-border-color: DARKRED");
                item2_temp.setTranslateX(75);
                SB_Item item3_temp = new SB_Item(scoreboard3_temp.get("date").toString(), scoreboard3_temp.get("scoreP1").toString(), scoreboard3_temp.get("scoreP2").toString());
                item3_temp.setStyle("-fx-border-width: 5px");
                item3_temp.setStyle("-fx-border-color: DARKRED");
                item3_temp.setTranslateX(75);
                getChildren().addAll(headerS, createSeperator(), item1_temp, item2_temp, item3_temp, buttonNext, buttenPrev);

        });
        buttenPrev.setOnMouseClicked(mouseEvent -> {
            getChildren().clear();
            JSONObject scoreboard1_temp = (org.json.simple.JSONObject) (jsonArray.get(scoreboardJsonArrayIndex));
            scoreboardJsonArrayIndex--;
            JSONObject scoreboard2_temp = (org.json.simple.JSONObject) (jsonArray.get(scoreboardJsonArrayIndex));
            scoreboardJsonArrayIndex--;
            JSONObject scoreboard3_temp = (org.json.simple.JSONObject) (jsonArray.get(scoreboardJsonArrayIndex));
            scoreboardJsonArrayIndex--;

            SB_Item item1_temp = new SB_Item(scoreboard1_temp.get("date").toString(), scoreboard1_temp.get("scoreP1").toString(), scoreboard1_temp.get("scoreP2").toString());
            item1_temp.setStyle("-fx-border-width: 5px");
            item1_temp.setStyle("-fx-border-color: DARKRED");
            item1_temp.setTranslateX(75);
            SB_Item item2_temp = new SB_Item(scoreboard2_temp.get("date").toString(), scoreboard2_temp.get("scoreP1").toString(), scoreboard2_temp.get("scoreP2").toString());
            item2_temp.setStyle("-fx-border-width: 5px");
            item2_temp.setStyle("-fx-border-color: DARKRED");
            item2_temp.setTranslateX(75);
            SB_Item item3_temp = new SB_Item(scoreboard3_temp.get("date").toString(), scoreboard3_temp.get("scoreP1").toString(), scoreboard3_temp.get("scoreP2").toString());
            item3_temp.setStyle("-fx-border-width: 5px");
            item3_temp.setStyle("-fx-border-color: DARKRED");
            item3_temp.setTranslateX(75);
            getChildren().addAll(headerS, createSeperator(), item1_temp, item2_temp, item3_temp, buttonNext, buttenPrev);
        });

        getChildren().addAll(headerS, createSeperator(), item1, item2, item3, buttonNext, buttenPrev);

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
        Text p1 = new Text("  Player 1   :");
        p1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        p1.setFill(Color.DARKRED);
        Text p2 = new Text(":   Player 2  ");
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
        sep.setPrefSize(200, 150);
        return sep;
    }



}