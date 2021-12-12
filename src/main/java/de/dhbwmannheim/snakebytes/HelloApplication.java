package de.dhbwmannheim.snakebytes;

import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloApplication extends Application {
    public static void main(String[] args) {

        Engine.setup();
        try {
            Engine.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Hello, World!");


        stage.show();
    }

    public static void main(String[] args) throws Exception {

        Engine.setup();
        Engine.run();

        launch();
    }

    public static class Test extends Application {

        Stage stage;

        public static void main(String[] args) {
            launch(args);
        }

        Stage stage;

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