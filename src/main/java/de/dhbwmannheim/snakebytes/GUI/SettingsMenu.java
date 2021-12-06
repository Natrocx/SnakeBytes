package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//by Kai Schwab

public class SettingsMenu extends VBox {
    public SettingsMenu(Stage primaryStage) {
        //Header
        Header header = new Header();

        //


        getChildren().addAll(header);
    }
}

class Header extends HBox{
    public Header(){
        Title2 P1 = new Title2("               P1               ");
        Title2 title = new Title2("            Settings            ");
        Title2 P2 = new Title2("               P2               ");

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(P1,title,P2);
    }

}
