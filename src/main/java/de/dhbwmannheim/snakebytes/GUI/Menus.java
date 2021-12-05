package de.dhbwmannheim.snakebytes.GUI;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.FileNotFoundException;


public class Menus extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("SNAKE BYTES");
        Scene MainMenu = new Scene(createTitleContent(primaryStage), Color.LIGHTBLUE);

        primaryStage.show();
        primaryStage.setScene(MainMenu);
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

    //Settings
    Parent createSettingsContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        root.getChildren().addAll();

        return root;
    }

    //Impressum/Erklaerungen
    Parent createImpressumContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        root.getChildren().addAll();

        return root;
    }
}
