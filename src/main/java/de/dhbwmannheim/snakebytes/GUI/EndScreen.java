
package de.dhbwmannheim.snakebytes.GUI;

//by Kai Schwab

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class EndScreen extends VBox {
    public static String winner = "P1";
    public EndScreen(Stage primaryStage){
        Title2 title = new Title2("The Winner is "+winner);

        StatsRow kills = new StatsRow("Kills",String.valueOf(GameOverlay.scP1),String.valueOf(GameOverlay.scP2));
        StatsRow dmg = new StatsRow("dmg","1","3");
        StatsRow treffer = new StatsRow("Treffer","1","3");
        StatsRow Tode = new StatsRow("Tode","1","3");

        try {
            Scoreboard.saveScoreboardToJson();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        SideMenuItem weiter = new SideMenuItem("Weiter",primaryStage);//Knopf Funktionen unter CharakterSelect

        getChildren().addAll(title,kills,dmg,treffer,Tode,weiter);


    }


}

class StatsRow extends HBox{
    public StatsRow(String name , String statsP1, String statsP2){
        Text Name= new Text(name);
        Name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
        Name.setFill(Color.DARKRED);
        Text Stat1 = new Text(statsP1);
        Stat1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        Stat1.setFill(Color.DARKRED);
        Text Stat2 =new Text(statsP2);
        Stat2.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        Stat2.setFill(Color.DARKRED);

        getChildren().addAll(Name,Stat1,Stat2);

    }
}

