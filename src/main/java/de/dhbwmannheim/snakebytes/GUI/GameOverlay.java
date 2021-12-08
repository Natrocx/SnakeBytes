package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static de.dhbwmannheim.snakebytes.GUI.Menus.createTitleContent;

//by Kai Schwab

public class GameOverlay extends StackPane {
    public GameOverlay(Stage primaryStage) {
        Pause pause = new Pause(primaryStage);
        pause.setTranslateX(1200);
        Score score = new Score(0,0,primaryStage);
        score.setTranslateX(550);
        Timer timer =new Timer(60,primaryStage);
        timer.setTranslateX(850);
        setAlignment(Pos.TOP_RIGHT);

        getChildren().addAll(pause,score, timer);
    }
}

class  Score extends StackPane {
    public Score(int p1, int p2, Stage primaryStage) {
        Title2 score = new Title2("   "+p1+"   :   "+p2+"   ");

        getChildren().addAll(score);
    }
}

class  Timer extends StackPane {
    public Timer(int t1, Stage primaryStage) {
        Title2 timer = new Title2("   "+t1+" sek"+"   ");

        getChildren().addAll(timer);
    }
}


class  Pause extends StackPane {
    public Pause(Stage primaryStage) {
        final Circle circle = new Circle(10, 20, 20);
        final Rectangle r1 = new Rectangle(5, 25);
        final Rectangle r2 = new Rectangle(5, 25);
        r1.setTranslateX(-5);
        r2.setTranslateX(5);
        circle.setFill(Color.DARKRED);
        circle.setStroke(Color.BLACK);
        circle.setOnMouseEntered(event -> {
            circle.setFill(Color.RED);
        });
        circle.setOnMouseExited(event -> {
            circle.setFill(Color.DARKRED);
        });
        circle.setOnMousePressed(event -> {
            circle.setFill(Color.YELLOW);
        });
        circle.setOnMouseReleased(event -> {
            circle.setFill(Color.RED);
        });
        r1.setOnMouseEntered(event -> {
            circle.setFill(Color.RED);
        });
        r1.setOnMouseExited(event -> {
            circle.setFill(Color.DARKRED);
        });
        r1.setOnMousePressed(event -> {

        });
        r2.setOnMouseEntered(event -> {
            circle.setFill(Color.RED);
        });
        r2.setOnMouseExited(event -> {
            circle.setFill(Color.DARKRED);
        });
        r2.setOnMousePressed(event -> {
            circle.setFill(Color.YELLOW);

        });

        setAlignment(Pos.CENTER);
        getChildren().addAll(circle, r1,r2);

    }
}
