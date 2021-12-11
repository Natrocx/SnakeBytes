package de.dhbwmannheim.snakebytes.GUI;

//Code by Kai Schwab

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.FileNotFoundException;


public class Menus extends Application {

    //by Kai Schwab

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("SNAKE BYTES");
        Scene MainMenu = new Scene(createTitleContent(primaryStage), Color.LIGHTBLUE);

        primaryStage.setMaxHeight(900);
        primaryStage.setMaxWidth(1350);
        primaryStage.setMinHeight(900);
        primaryStage.setMinWidth(1350);
        primaryStage.setHeight(900);
        primaryStage.setWidth(1350);

        primaryStage.setScene(MainMenu);
        primaryStage.show();
    }

    //Main Menu
    public static Parent createTitleContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        MainMenu mainMenu = new MainMenu(primaryStage);

        root.getChildren().addAll(mainMenu);

        return root;
    }

    //CharakterMenu
    static Parent createCharakterContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        CharacterMenu characterMenu = new CharacterMenu(primaryStage);

        root.getChildren().addAll(characterMenu);

        return root;
    }

    //Scoreboard
    static Parent createScoreboardContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        Scoreboard scoreboard = new Scoreboard(primaryStage);

        root.getChildren().addAll(scoreboard);

        return root;
    }

    //Settings
    static Parent createSettingsContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        SettingsMenu settings = new SettingsMenu(primaryStage);

        root.getChildren().addAll(settings);

        return root;
    }
    static Parent createKeyBinding(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(320, 80);

        PressKeyWindow keyWindow = new PressKeyWindow(primaryStage);

        root.getChildren().addAll(keyWindow);

        return root;
    }


    //Impressum/Erklaerungen
    static Parent createImpressumContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        Impressum impressum = new Impressum(primaryStage);

        root.getChildren().addAll(impressum);

        return root;
    }

    //Game
    public static Parent createGameContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        GameOverlay gov = new GameOverlay(primaryStage);
        //MainMenu mainMenu =new MainMenu(primaryStage);

        //Einbauen der FrameHandler KLassen


        root.getChildren().addAll(gov);
        //root.getChildren().addAll(background,gov);

        return root;
    }
}