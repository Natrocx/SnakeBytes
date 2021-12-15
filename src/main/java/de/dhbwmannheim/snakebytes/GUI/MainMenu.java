package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

//by Kai Schwab

public class MainMenu extends VBox {
    public MainMenu(Stage primaryStage) {
        //Titel
        Title title = new Title("S N A K E   B Y T E S");
        title.setTranslateY(100);
        title.setTranslateX((1350 / 2) - 360);

        //Menu mit Schaltflaechen
        MenuBox vbox = new MenuBox(primaryStage);
        vbox.setTranslateY(150);
        vbox.setTranslateX((1350 / 2) - 350);

        getChildren().addAll(title, vbox);

    }
}

//Titel-Objekt
class Title extends StackPane {
    public Title(String name) {
        Rectangle bg = new Rectangle(720, 80);
        bg.setStroke(Color.DARKRED);
        bg.setStrokeWidth(2);
        bg.setFill(null);

        Text text = new Text(name);
        text.setFill(Color.DARKRED);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 69));

        getChildren().addAll(bg, text);
    }
}

class MenuBox extends VBox {
    public MenuBox(Stage primaryStage) {
        getChildren().add(createSeperator());
        MenuItem item1 = new MenuItem("Start Game", primaryStage);
        MenuItem item2 = new MenuItem("Scoreboard", primaryStage);
        MenuItem item3 = new MenuItem("Settings", primaryStage);
        MenuItem item4 = new MenuItem("Erklärungen", primaryStage);

        getChildren().addAll(item1, createSeperator(), item2, createSeperator(), item3, createSeperator(), item4);
    }

    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(200, 69);
        return sep;
    }

}

class MenuItem extends StackPane {

    public MenuItem(String name, Stage primaryStage) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.YELLOW),
                new Stop(0.1, Color.RED),
                new Stop(0.9, Color.RED),
                new Stop(1, Color.DARKBLUE));

        Rectangle bg = new Rectangle(600, 60);
        bg.setStroke(Color.BLACK);
        bg.setFill(Color.DARKRED);
        bg.setOpacity(0.7);

        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));

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

        //Funktionen bei einbem Knopfdruck auf ein beliebiges Item
        setOnMousePressed(event -> {
            bg.setFill(Color.DARKGOLDENROD);
            Scene scene = null;
            if (name == "Start Game") {
                try {
                    scene = new Scene(Menus.createCharakterContent(primaryStage), Color.LIGHTBLUE);
                    primaryStage.setMaxHeight(Integer.MAX_VALUE);
                    primaryStage.setMaxWidth(Integer.MAX_VALUE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                primaryStage.setScene(scene);
            }
            if (name == "Settings") {
                try {
                    scene = new Scene(Menus.createSettingsContent(primaryStage), Color.LIGHTBLUE);
                    primaryStage.setMaxHeight(Integer.MAX_VALUE);
                    primaryStage.setMaxWidth(Integer.MAX_VALUE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                primaryStage.setScene(scene);

            }
            if (name == "Erklärungen") {
                try {
                    scene = new Scene(Menus.createImpressumContent(primaryStage), Color.LIGHTBLUE);
                    primaryStage.setMaxHeight(Integer.MAX_VALUE);
                    primaryStage.setMaxWidth(Integer.MAX_VALUE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                primaryStage.setScene(scene);
            }
            if (name == "Scoreboard") {
                try {
                    scene = new Scene(Menus.createScoreboardContent(primaryStage), Color.LIGHTBLUE);
                    primaryStage.setMaxHeight(Integer.MAX_VALUE);
                    primaryStage.setMaxWidth(Integer.MAX_VALUE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                primaryStage.setScene(scene);
            }

        });

        setOnMouseReleased(event -> {
            bg.setFill(gradient);
        });
    }
}


