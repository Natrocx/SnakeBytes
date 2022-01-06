package de.dhbwmannheim.snakebytes.GUI;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

import static de.dhbwmannheim.snakebytes.GUI.Menus.createTitleContent;

/**
 * Author:  @Kai Schwab
 **/

public class EndScreen extends VBox {
    public static String winner = "P1";
    public EndScreen(Stage primaryStage){
        Title2 title = new Title2("Der Gewinner ist "+winner);
        title.setTranslateX(300);
        title.setTranslateY(250);

        StatsRow kills = new StatsRow("Punkte",String.valueOf(GameOverlay.scP1),String.valueOf(GameOverlay.scP2));
       // kills.setTranslateX(450);
        kills.setTranslateY(275);
        var knockback = ComponentManager.getComponentList(CharacterStateComponent.class);
        var p1knockback = Math.round(knockback.getComponent(Engine.getPlayer(0)).knockback * 100.0) / 100.0;
        var p2knockback = Math.round(knockback.getComponent(Engine.getPlayer(1)).knockback * 100.0) / 100.0;
        StatsRow dmg = new StatsRow("Schadensmultiplikator",String.valueOf(p1knockback),String.valueOf(p2knockback));
       // dmg.setTranslateX(285);
        dmg.setTranslateY(300);
        //StatsRow treffer = new StatsRow("Treffer","1","3");
        //treffer.setTranslateX(450);
        //treffer.setTranslateY(325);

        try {
            Scoreboard.saveScoreboardToJson();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        MenuItem weiter = new MenuItem("Zurück",primaryStage);//Knopf Funktionen unter CharakterSelect
        weiter.setTranslateX(300);
        weiter.setTranslateY(350);

        //getChildren().addAll(title,kills,dmg,treffer,weiter);
        getChildren().addAll(title,kills,dmg,weiter);

    }


}

class StatsRow extends HBox{
    public StatsRow(String name , String statsP1, String statsP2){
        Text Name= new Text(name);
        Name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
        Name.setFill(Color.DARKRED);
        Name.setTranslateX(250);
        Text Stat1 = new Text(statsP1);
        Stat1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        Stat1.setFill(Color.DARKRED);
        Stat1.setTranslateX(500);
        Text vs = new Text(":");
        vs.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        vs.setFill(Color.DARKRED);
        vs.setTranslateX(600);
        Text Stat2 =new Text(statsP2);
        Stat2.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        Stat2.setFill(Color.DARKRED);
        Stat2.setTranslateX(700);
        getChildren().addAll(Name,Stat1,vs,Stat2);

    }
}

