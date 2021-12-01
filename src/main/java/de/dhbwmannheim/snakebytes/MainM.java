package de.dhbwmannheim.snakebytes;
//Code by Kai Schwab
//Grafiken by Robert Sedelmeier


import java.io.IOException;
import java.io.InputStream;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;



public class MainM extends Application {

    private Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage =primaryStage;
        Scene MainMenu = new Scene(createContentMainM(),Color.LIGHTBLUE);
        //primaryStage.getIcons().add(new Image(Start.class.getResourceAsStream("logo.jpg")));
        primaryStage.setTitle("SNAKE BYTES");
        primaryStage.setScene(MainMenu);
        primaryStage.show();
    }
    @FXML
    private void NavigateButton(){

    }

    //MainMenu

    private Parent createContentMainM() {
        Pane root = new Pane();

        root.setPrefSize(1050, 600);

        try(InputStream is = Files.newInputStream(Paths.get("grafik/background.jpg"))){
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(1050);
            img.setFitHeight(600);
            root.getChildren().add(img);
        }
        catch(IOException e) {
            System.out.println("Couldn't load image");
        }

        Title title = new Title ("S N A K E   B Y T E S");
        title.setTranslateY(100);
        title.setTranslateX((root.getPrefWidth()/2)-360);


        MenuBox vbox = new MenuBox(
                new MenuItem("Start Game"),
                new MenuItem("Settings"),
                new MenuItem("Impressum"));
        vbox.setTranslateX((root.getPrefWidth()/2)-200);
        System.out.println();
        vbox.setTranslateY(280);
        vbox.createSeperator();

        root.getChildren().addAll(title,vbox);

        return root;
    }

    private static class Title extends StackPane{
        public Title(String name) {
            Rectangle bg = new Rectangle(720, 80);
            bg.setStroke(Color.DARKRED);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.DARKRED);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 69));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,text);
        }
    }

    private static class MenuBox extends VBox{
        public MenuBox(MenuItem...items) {
            getChildren().add(createSeperator());

            for(MenuItem item : items) {
                getChildren().addAll(item, createSeperator());
            }
        }

        private HBox createSeperator() {
            HBox sep = new HBox();
            sep.setPrefSize(200,30);
            return sep;
        }

    }

    private static class MenuItem extends StackPane{
        public MenuItem(String name) {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.YELLOW),
                    new Stop(0.1, Color.RED),
                    new Stop(0.9, Color.RED),
                    new Stop(1, Color.DARKBLUE)

            });

            Rectangle bg = new Rectangle(400,50);
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

            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });
        }
    }

    //CharacterSelectScreen

    private Parent createContentCharM() {
        Pane root = new Pane();

        root.setPrefSize(1050, 600);

        return root;
    }
}
