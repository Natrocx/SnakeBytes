
package de.dhbwmannheim.snakebytes.GUI;

//by Kai Schwab

import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Author:  @Kai Schwab
 **/

public class EndScreen extends VBox {
    public static String winner = "Nobody" +
            "";
    public EndScreen(){
        Title2 title = new Title2("Der Gewinner ist "+winner);
        title.setTranslateX(200);
        title.setTranslateY(100);

        var player1 = Engine.getPlayer(0);
        var player2 = Engine.getPlayer(1);
        StatsRow kills = new StatsRow("Punkte                 ",String.valueOf(GameOverlay.scP1),String.valueOf(GameOverlay.scP2));
        kills.setTranslateY(275);
        title.setTranslateX(200);

        var characterState = ComponentManager.getComponentList(CharacterStateComponent.class);
        var p1knockback = Math.round(characterState.getComponent(player1).knockback * 100.0) / 100.0;
        var p2knockback = Math.round(characterState.getComponent(player2).knockback * 100.0) / 100.0;
        StatsRow dmg = new StatsRow("Schadensmultiplikator    ",String.valueOf(p1knockback),String.valueOf(p2knockback));
        dmg.setTranslateY(300);
        title.setTranslateX(200);

        var p1Hits = characterState.getComponent(player1).timesHit;
        var p2Hits = characterState.getComponent(player2).timesHit;
        StatsRow treffer = new StatsRow("Getroffen            ",String.valueOf(p1Hits),String.valueOf(p2Hits));
        treffer.setTranslateY(325);
        title.setTranslateX(200);

        var text = new Title2("Starten Sie das Spiel neu, um nochmal zu spielen!");
        text.setTranslateX(200);
        text.setTranslateY(500);

        //add the current game result to the scoreboard.json
        try {
            Scoreboard.saveScoreboardToJson();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        getChildren().addAll(title,kills,dmg,treffer,text);



    }


}

class StatsRow extends StackPane {
    public StatsRow(String name , String statsP1, String statsP2){
        Text Name= new Text(name);
        Name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
        Name.setFill(Color.DARKRED);
        Text Stat1 = new Text(statsP1);
        Stat1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        Stat1.setFill(Color.DARKRED);
        Stat1.setTranslateX(250);
        Text vs = new Text(":");
        vs.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        vs.setFill(Color.DARKRED);
        vs.setTranslateX(350);
        Text Stat2 =new Text(statsP2);
        Stat2.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        Stat2.setFill(Color.DARKRED);
        Stat2.setTranslateX(450);
        getChildren().addAll(Name,Stat1,vs,Stat2);

    }
}

