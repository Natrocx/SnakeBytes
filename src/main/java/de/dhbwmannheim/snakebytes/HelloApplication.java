package de.dhbwmannheim.snakebytes;

import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloApplication extends Application {
    public static void main(String[] args) {


        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Hello, World!");


        stage.show();
    }

    public static class Test extends Application {

        Stage stage;

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            this.stage = primaryStage;
            Scene MainMenu = new Scene(createContentMainM(), Color.LIGHTBLUE);
            //primaryStage.getIcons().add(new Image(Start.class.getResourceAsStream("logo.jpg")));
            primaryStage.setTitle("SNAKE BYTES");
            primaryStage.setScene(MainMenu);
            primaryStage.show();
        }


        //MainMenu

        private Parent createContentMainM() {
            Pane root = new Pane();

            root.setPrefSize(1050, 600);

            try (InputStream is = Files.newInputStream(Paths.get("grafik/background.jpg"))) {
                ImageView img = new ImageView(new Image(is));
                img.setFitWidth(1050);
                img.setFitHeight(600);
                root.getChildren().add(img);
            } catch (IOException e) {
                System.out.println("Couldn't load image");
            }
            return root;
        }
    }

}