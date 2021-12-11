package de.dhbwmannheim.snakebytes.GUI;

//by Kai Schwab

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Scoreboard extends VBox {
    public Scoreboard(Stage primaryStage) {
        //Header
        HeaderS headerS = new HeaderS(primaryStage);
        headerS.setTranslateX(-400);

        //Items
        SB_Item item1 = new SB_Item("08.12.2021","2", "3");
        item1.setStyle("-fx-border-width: 5px");
        item1.setStyle("-fx-border-color: DARKRED");
        item1.setTranslateX(75);
        SB_Item item2 = new SB_Item("08.12.2021","2", "3");
        item2.setStyle("-fx-border-width: 5px");
        item2.setStyle("-fx-border-color: DARKRED");
        item2.setTranslateX(75);
        SB_Item item3 = new SB_Item("08.12.2021","2", "3");
        item3.setStyle("-fx-border-width: 5px");
        item3.setStyle("-fx-border-color: DARKRED");
        item3.setTranslateX(75);


        getChildren().addAll(headerS,createSeperator(),item1,item2,item3);
    }

    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(200,150);
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

class SB_Item extends HBox{
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
        getChildren().addAll(p1,createSeperator(), s_p1,createSeperator(), datum, createSeperator(), s_p2,createSeperator(), p2);
    }
    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(200,150);
        return sep;
    }


}