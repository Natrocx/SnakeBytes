package de.dhbwmannheim.snakebytes.GUI;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
