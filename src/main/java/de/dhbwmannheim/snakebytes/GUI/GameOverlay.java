package de.dhbwmannheim.snakebytes.GUI;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

//by Kai Schwab

public class GameOverlay extends StackPane {
    int counter = 3;
    int scP1 = 0;
    int scP2 = 0;
    public GameOverlay(Stage primaryStage) {
        Pause pause = new Pause(primaryStage);
        pause.setTranslateX(1180);
        pause.setTranslateY(-70);
        Score score = new Score(scP1,scP2,primaryStage);
        score.setTranslateX(550);
        score.setTranslateY(-70);
        Game_Timer timer =new Game_Timer(CharacterMenu.time,primaryStage);
        timer.setTranslateX(900);
        timer.setTranslateY(-70);
        setAlignment(Pos.TOP_RIGHT);
        CountDown countDown = new CountDown();
        countDown.setTranslateX(550);
        countDown.setTranslateY(300);

        getChildren().addAll(pause,score, timer,countDown);

    }
}

class  Score extends StackPane {
    public Score(int p1, int p2, Stage primaryStage) {
        Text score = new Text(p1+"   :   "+p2);
        score.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 69));
        score.setFill(Color.DARKRED);

        getChildren().addAll(score);
    }
}

class  Game_Timer extends StackPane {
    public Game_Timer(int t1, Stage primaryStage) {
        Title2 timer = new Title2("   "+t1+" sek"+"   ");

        getChildren().addAll(timer);
    }
}

//ToDo:Countdown wie in CHarakterSelect
class CountDown extends StackPane{
    static int c = 3;
    public CountDown(){
        Text countd = new Text(String.valueOf(c));
        countd.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 169));
        countd.setFill(Color.DARKRED);

        getChildren().addAll(countd);

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(c > 0)
                {
                    countd.setText(String.valueOf(c));
                    System.out.println(c);
                    c--;
                }
                else if (c==0){
                    countd.setText("GO");
                    System.out.println(c);
                    c--;
                }
                else {
                    countd.setText("");
                    timer.cancel();
                }
            }
        }, 420,1000);
        countd.setText("");
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
            //Pause();
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
            circle.setFill(Color.YELLOW);
            //Pause();
        });
        r1.setOnMouseReleased(event -> {
            circle.setFill(Color.RED);
        });
        r2.setOnMouseEntered(event -> {
            circle.setFill(Color.RED);
        });
        r2.setOnMouseExited(event -> {
            circle.setFill(Color.DARKRED);
        });
        r2.setOnMousePressed(event -> {
            circle.setFill(Color.YELLOW);
            //Pause();
        });
        r2.setOnMouseReleased(event -> {
            circle.setFill(Color.RED);
        });

        setAlignment(Pos.CENTER);
        getChildren().addAll(circle, r1,r2);

    }
}
