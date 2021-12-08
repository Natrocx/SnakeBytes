package de.dhbwmannheim.snakebytes.GUI;

//by Kai Schwab

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Scoreboard extends VBox {
    public Scoreboard(Stage primaryStage) {
        //Header
        HeaderS headerS = new HeaderS(primaryStage);

        //


        getChildren().addAll(headerS);
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