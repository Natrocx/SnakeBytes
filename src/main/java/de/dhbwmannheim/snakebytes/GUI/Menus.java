package de.dhbwmannheim.snakebytes.GUI;

//Code by Kai Schwab

import de.dhbwmannheim.snakebytes.JsonHandler;
import de.dhbwmannheim.snakebytes.Render.BackgroundBuilder;
import de.dhbwmannheim.snakebytes.Sounds.MusicManager;
import de.dhbwmannheim.snakebytes.Sounds.SoundManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.IOException;

public class Menus extends Application {

    public static Pane root;

    //by Kai Schwab
    public static MusicManager mediaplayer;
    public static SoundManager soundplayer;

    public static void main(String[] args) {
        root = new Pane();
        mediaplayer = new MusicManager();
        soundplayer = new SoundManager();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        root = new Pane();
        mediaplayer = new MusicManager();
        soundplayer = new SoundManager();
        JsonHandler.saveDefaultJson();
        try {
            JsonHandler.saveScoreboardDefaultJson();
            Scoreboard.fillDummyDataInScoreboard();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("SNAKE BYTES");
        primaryStage.setResizable(false);
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
    static Parent createScoreboardContent(Stage primaryStage) throws IOException, ParseException {
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

    static Parent createKeyBindingContent(Stage primaryStage) throws FileNotFoundException {
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
    public static void createGameContent(Stage primaryStage) throws FileNotFoundException {

        root.setPrefSize(1350, 900);

        GameOverlay gov = new GameOverlay(primaryStage);
        BackgroundBuilder background = new BackgroundBuilder(primaryStage);
        try {
            mediaplayer.playMusic();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Menus.root.getChildren().add(0,background);
        Menus.root.getChildren().add(1,gov);

    }
    static Parent createEndScreenContent(Stage primaryStage) throws FileNotFoundException {

        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        EndScreen endscreen = new EndScreen(primaryStage);

        root.getChildren().addAll(endscreen);
        try {
            Scoreboard.saveScoreboardToJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return root;
    }

}