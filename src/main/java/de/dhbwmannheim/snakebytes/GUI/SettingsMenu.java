package de.dhbwmannheim.snakebytes.GUI;

//by Kai Schwab

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsMenu extends VBox {
    public SettingsMenu(Stage primaryStage) {
        //Header
        Header header = new Header();
        Back back = new Back(primaryStage);

        //


        getChildren().addAll(header,back);
    }
}

class Header extends HBox {
    public Header(){
        Title2 P1 = new Title2("               P1               ");
        Title2 title = new Title2("            Settings            ");
        Title2 P2 = new Title2("               P2               ");

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(P1,title,P2);
    }

}