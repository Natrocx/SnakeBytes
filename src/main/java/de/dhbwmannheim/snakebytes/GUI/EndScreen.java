package de.dhbwmannheim.snakebytes.GUI;


import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class EndScreen extends VBox {
    String winner = "P1";
    public EndScreen(Stage primaryStage){
        Title2 title = new Title2("The Winner is "+winner);

        StatsRow kills = new StatsRow("Kills","1","3");
        StatsRow dmg = new StatsRow("dmg","1","3");
        StatsRow treffer = new StatsRow("Treffer","1","3");
        StatsRow Tode = new StatsRow("Tode","1","3");


        SideMenuItem weiter = new SideMenuItem("Weiter",primaryStage);//Knopf Funktionen unter CharakterSelect

        getChildren().addAll(title,kills,dmg,treffer,Tode,weiter);


    }

}

class StatsRow extends HBox{
    public StatsRow(String name , String statsP1, String statsP2){
        Text Name= new Text(name);
        Text Stat1 = new Text(statsP1);
        Text Stat2 =new Text(statsP2);

        getChildren().addAll(Name,Stat1,Stat2);

    }
        }

