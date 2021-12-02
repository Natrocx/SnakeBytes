package de.dhbwmannheim.snakebytes.GUI;

import de.dhbwmannheim.snakebytes.HelloApplication;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    private Parent createTitleContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);

        try (InputStream is = Files.newInputStream(Paths.get("grafik/background.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(1350);
            img.setFitHeight(900);
            root.getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        Title title = new Title("S N A K E   B Y T E S");
        title.setTranslateY(100);
        title.setTranslateX((root.getPrefWidth() / 2) - 360);


        MenuBox vbox = new MenuBox(
                new MenuItem("Start Game", primaryStage),
                new MenuItem("Settings", primaryStage),
                new MenuItem("Impressum", primaryStage));
        vbox.setTranslateX((root.getPrefWidth() / 2) - 200);
        vbox.setTranslateY(280);
        vbox.createSeperator();

        root.getChildren().addAll(title, vbox);

        return root;
    }

    Parent createCharakterContent(Stage primaryStage) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(1350, 900);
        return root;
    }

    private static class Title extends StackPane {
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

    private static class MenuBox extends VBox {
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

    public class MenuItem extends StackPane{


        public MenuItem(String name, Stage primaryStage) {
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
                Scene scene = null;
                try {
                    scene = new Scene(createCharakterContent(primaryStage),Color.DARKBLUE);
                    primaryStage.setMaxHeight(Integer.MAX_VALUE);
                    primaryStage.setMaxWidth(Integer.MAX_VALUE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                primaryStage.setScene(scene);

            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });
        }
    }

}
