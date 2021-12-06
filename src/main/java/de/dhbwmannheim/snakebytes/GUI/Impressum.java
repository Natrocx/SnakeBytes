package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//by Kai Schwab

public class Impressum extends VBox {
    public Impressum(Stage primaryStage) {
        //Title
        HeaderI headerI = new HeaderI(primaryStage);

        //


        getChildren().addAll(headerI);
    }
}

class HeaderI extends HBox {
    public HeaderI(Stage primaryStage){
        Title2 titleI = new Title2("Impressum und Erklärungen");
        titleI.setTranslateX(400);
        titleI.setTranslateY(20);

        Back back = new Back(primaryStage);
        back.setTranslateX(600);
        back.setTranslateY(20);

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(titleI,back);
    }

}