package de.dhbwmannheim.snakebytes.GUI;

//by Kai Schwab

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.*;
import java.io.FileNotFoundException;

public class SettingsMenu extends StackPane {
    public SettingsMenu(Stage primaryStage) {
        //Title
        HeaderP title = new HeaderP(primaryStage);
        title.setTranslateX(400);
        title.setTranslateY(-100);

        SettingsTable settings = new SettingsTable(primaryStage);

        setAlignment(Pos.CENTER);

        getChildren().addAll(title,settings);
    }
}

class HeaderP extends HBox {
    public HeaderP(Stage primaryStage) {
        Title2 titleI = new Title2("Settings");
        titleI.setTranslateX(150);
        titleI.setTranslateY(20);


        Back back = new Back(primaryStage);
        back.setTranslateX(600);
        back.setTranslateY(20);

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(titleI,back);
    }
}

class SettingsTable extends StackPane {

    public SettingsTable(Stage primaryStage){
        //Settings Topics
        SettingsTopic moveSetTitle = new SettingsTopic("A C T I O N S");
        moveSetTitle.setTranslateX(200);
        moveSetTitle.setTranslateY(220);

        SettingsTopic player1Title = new SettingsTopic("P L A Y E R 1");
        player1Title.setTranslateX(550);
        player1Title.setTranslateY(220);

        SettingsTopic player2Title = new SettingsTopic("P L A Y E R 2");
        player2Title.setTranslateX(800);
        player2Title.setTranslateY(220);

        //Settings Elements
        TextBox moveSet = new TextBox(
                new TextItem("Up"),
                new TextItem("left"),
                new TextItem("right"),
                new TextItem("Attack 1"),
                new TextItem("Attack 2"));
        moveSet.setTranslateX(200);
        System.out.println();
        moveSet.setTranslateY(250);

        ButtonBox player1Box = new ButtonBox(
                new ButtonItem("w",primaryStage),     //up
                new ButtonItem("a",primaryStage),     //left
                new ButtonItem("d",primaryStage),     //right
                new ButtonItem("j",primaryStage),     //Attack 1
                new ButtonItem("k",primaryStage));    //Attack 2
        player1Box.setTranslateX(550);
        System.out.println();
        player1Box.setTranslateY(250);

        ButtonBox player2Box = new ButtonBox(
                new ButtonItem("w",primaryStage),     //up
                new ButtonItem("a",primaryStage),     //left
                new ButtonItem("d",primaryStage),     //right
                new ButtonItem("j",primaryStage),     //Attack 1
                new ButtonItem("k",primaryStage));    //Attack 2
        player2Box.setTranslateX((800));
        System.out.println();
        player2Box.setTranslateY(250);
        createSeperator();

        getChildren().addAll(moveSetTitle,player1Title,player2Title,moveSet,player1Box,player2Box);

    }
    public static HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(50,15);
        return sep;
    }
}

class SettingsTopic extends HBox {
    public SettingsTopic(String name){
        Label playerTitle = new Label(name);
        playerTitle.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 32));

        getChildren().addAll(playerTitle);
    }

}

class TextBox extends VBox{
    public TextBox(TextItem...items) {
        getChildren().add(createSeperator());

        for(TextItem item : items) {
            getChildren().addAll(item, createSeperator());
        }
    }

    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(50,15);
        return sep;
    }

}
class TextItem extends StackPane {
    public TextItem(String name) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{

        });

        Rectangle bg = new Rectangle(200, 50);
        bg.setFill(Color.DARKRED);
        bg.setOpacity(0.7);

        Text text = new Text(name);
        text.setFill(Color.WHITESMOKE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);
    }
}
class ButtonBox extends VBox{
    public ButtonBox(ButtonItem...items) {
        getChildren().add(SettingsTable.createSeperator());

        for(ButtonItem item : items) {
            getChildren().addAll(item,SettingsTable.createSeperator());
        }

    }
}
class ButtonItem extends StackPane{
    public ButtonItem(String name, Stage primaryStage) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(0, Color.YELLOW),
                new Stop(0.1, Color.RED),
                new Stop(0.9, Color.RED),
                new Stop(1, Color.DARKBLUE)
        });

        Rectangle bg = new Rectangle(200,50);
        bg.setFill(Color.DARKRED);
        bg.setOpacity(0.7);

        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,25));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);
        setOnMouseEntered(event -> {
            bg.setFill(gradient);
            text.setFill(Color.WHITE);

        });

        setOnMouseExited(event -> {
            bg.setFill(Color.DARKRED);
            text.setFill(Color.DARKGREY);
        });
        setOnMousePressed(event -> {
            bg.setFill(Color.DARKGOLDENROD);
            Scene scene = null;
            try {
                scene = new Scene(Menus.createKeyBinding(primaryStage), Color.LIGHTBLUE);
                primaryStage.setMaxHeight(Integer.MAX_VALUE);
                primaryStage.setMaxWidth(Integer.MAX_VALUE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            primaryStage.setMaxHeight(Integer.MAX_VALUE);
            primaryStage.setMaxWidth(Integer.MAX_VALUE);
            primaryStage.setScene(scene);
        });

        setOnMouseReleased(event -> {
            bg.setFill(gradient);
        });
    }
}



