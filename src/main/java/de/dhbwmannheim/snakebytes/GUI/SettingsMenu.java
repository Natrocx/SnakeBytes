package de.dhbwmannheim.snakebytes.GUI;

//by Kai Schwab

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Hashtable;

public class SettingsMenu extends VBox {

    /*

    //Saving the KeySettings of player 1 into a HashTable, so that: <keyboard key as String, connected action as String>
    static Hashtable<String,String> player1KeySettings;
    static {
        try {
            player1KeySettings = new Hashtable<>(getKeySettings("player1"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //Saving the KeySettings of player 2 into a HashTable, so that: <keyboard key as String, connected action as String>
    static Hashtable<String,String> player2KeySettings;
    static {
        try {
            player2KeySettings = new Hashtable<>(getKeySettings("player2"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

     */
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

