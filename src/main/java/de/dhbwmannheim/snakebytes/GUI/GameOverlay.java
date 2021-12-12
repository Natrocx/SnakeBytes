package de.dhbwmannheim.snakebytes.GUI;


import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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
import java.util.Timer;
import java.util.TimerTask;

import static de.dhbwmannheim.snakebytes.GUI.Menus.createTitleContent;

/**
 * Author: @Kai Schwab
 *         @Thu Giang Tran
 * This class...
 */

public class GameOverlay extends StackPane {
    int counter = 3;
    int scP1 = 0;
    int scP2 = 0;
    public GameOverlay(Stage primaryStage) {
        MiniBack back = new MiniBack(primaryStage);
        back.setTranslateX(-80);
        back.setTranslateY(-70);
        Music music = new Music(primaryStage);
        music.setTranslateX(900);
        music.setTranslateY(-70);
        Sound sound = new Sound(primaryStage);
        sound.setTranslateX(1050);
        sound.setTranslateY(-70);
        Pause pause = new Pause(primaryStage);
        pause.setTranslateX(1180);
        pause.setTranslateY(-70);
        Score score = new Score(scP1,scP2,primaryStage);
        score.setTranslateX(550);
        score.setTranslateY(-70);
        Game_Timer timer =new Game_Timer(CharacterMenu.time,primaryStage);
        timer.setTranslateX(200);
        timer.setTranslateY(-70);
        CountDown countDown = new CountDown();
        countDown.setTranslateX(550);
        countDown.setTranslateY(300);
        Schadenanzeige dmgP1 = new Schadenanzeige("P1",3);
        dmgP1.setTranslateX(10);
        dmgP1.setTranslateY(700);
        Schadenanzeige dmgP2 = new Schadenanzeige("P2",400);
        dmgP2.setTranslateX(1150);
        dmgP2.setTranslateY(700);


        setAlignment(Pos.TOP_CENTER);

        getChildren().addAll(countDown,dmgP1,dmgP2,score,timer,pause,sound,music,back);

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
        c=3;
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
//To do music
//Todo Music Stop beim backen
class  MiniBack extends StackPane {
    public MiniBack(Stage primaryStage) {
        final Circle circle = new Circle(10, 20, 20);
        final Rectangle r1 = new Rectangle(5, 25);
        final Rectangle r2 = new Rectangle(5, 25);
        r1.setRotate(45);
        r2.setRotate(315);
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
            Scene scene = null;
            try {
                Menus.mediaplayer.pauseMusic();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                scene = new Scene(createTitleContent(primaryStage), Color.LIGHTBLUE);
                primaryStage.setMaxHeight(Integer.MAX_VALUE);
                primaryStage.setMaxWidth(Integer.MAX_VALUE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(scene);
        });
        r1.setOnMouseEntered(event -> {
            circle.setFill(Color.RED);
        });
        r1.setOnMouseExited(event -> {
            circle.setFill(Color.DARKRED);
        });
        r1.setOnMousePressed(event -> {
            circle.setFill(Color.YELLOW);
            Scene scene = null;
            try {
                Menus.mediaplayer.pauseMusic();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                scene = new Scene(createTitleContent(primaryStage), Color.LIGHTBLUE);
                primaryStage.setMaxHeight(Integer.MAX_VALUE);
                primaryStage.setMaxWidth(Integer.MAX_VALUE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(scene);
        });
        r2.setOnMouseEntered(event -> {
            circle.setFill(Color.RED);
        });
        r2.setOnMouseExited(event -> {
            circle.setFill(Color.DARKRED);
        });
        r2.setOnMousePressed(event -> {
            circle.setFill(Color.YELLOW);
            try {
                Menus.mediaplayer.pauseMusic();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Scene scene = null;
            try {
                scene = new Scene(createTitleContent(primaryStage), Color.LIGHTBLUE);
                primaryStage.setMaxHeight(Integer.MAX_VALUE);
                primaryStage.setMaxWidth(Integer.MAX_VALUE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(scene);
        });

        setAlignment(Pos.CENTER);
        getChildren().addAll(circle, r1,r2);

    }
}
class  Sound extends StackPane {
    boolean mute =false;
    public Sound(Stage primaryStage) {
        final Circle circle = new Circle(10, 20, 20);
        final Text r1 = new Text("S");
        r1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        circle.setFill(Color.DARKGREEN);
        circle.setStroke(Color.BLACK);
        circle.setOnMouseEntered(event -> {
            if(mute==true) {
                circle.setFill(Color.RED);
            }else{
                circle.setFill(Color.GREEN);
            }
        });
        circle.setOnMouseExited(event -> {
            if(mute==true) {
                circle.setFill(Color.DARKRED);
            }else{
                circle.setFill(Color.DARKGREEN);
            }
        });
        circle.setOnMousePressed(event -> {
            if(mute==true){
                mute=false;
            }else {
                mute=true;
            }
            circle.setFill(Color.YELLOW);
            if(mute==true){
                try {
                   // Menus.soundplayer.setVolume(0,0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    Menus.mediaplayer.playMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        circle.setOnMouseReleased(event -> {
            if(mute==true) {
                circle.setFill(Color.RED);
            }else{
                circle.setFill(Color.GREEN);
            }
        });
        r1.setOnMouseEntered(event -> {
            if(mute==true) {
                circle.setFill(Color.RED);
            }else{
                circle.setFill(Color.GREEN);
            }
        });
        r1.setOnMouseExited(event -> {
            if(mute==true) {
                circle.setFill(Color.DARKRED);
            }else{
                circle.setFill(Color.DARKGREEN);
            }
        });
        r1.setOnMousePressed(event -> {
            if(mute==true){
                mute=false;
            }else {
                mute=true;
            }
            if(mute==true){
                try {
                    Menus.mediaplayer.pauseMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    Menus.mediaplayer.playMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        r1.setOnMouseReleased(event -> {
            if(mute==true) {
                circle.setFill(Color.RED);
            }else{
                circle.setFill(Color.GREEN);
            }
        });
        setAlignment(Pos.CENTER);
        getChildren().addAll(circle, r1);

    }
}

class  Music extends StackPane {
    boolean mute =false;
    public Music(Stage primaryStage) {
        final Circle circle = new Circle(10, 20, 20);
        final Text r1 = new Text("M");
        r1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        circle.setFill(Color.DARKGREEN);
        circle.setStroke(Color.BLACK);
        circle.setOnMouseEntered(event -> {
            if(mute==true) {
                circle.setFill(Color.RED);
            }else{
                circle.setFill(Color.GREEN);
            }
        });
        circle.setOnMouseExited(event -> {
            if(mute==true) {
                circle.setFill(Color.DARKRED);
            }else{
                circle.setFill(Color.DARKGREEN);
            }
        });
        circle.setOnMousePressed(event -> {
            if(mute==true){
                mute=false;
            }else {
                mute=true;
            }
            circle.setFill(Color.YELLOW);
            if(mute==true){
                try {
                    Menus.mediaplayer.pauseMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    Menus.mediaplayer.playMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        circle.setOnMouseReleased(event -> {
            if(mute==true) {
                circle.setFill(Color.RED);
            }else{
                circle.setFill(Color.GREEN);
            }
        });
        r1.setOnMouseEntered(event -> {
            if(mute==true) {
                circle.setFill(Color.RED);
            }else{
                circle.setFill(Color.GREEN);
            }
        });
        r1.setOnMouseExited(event -> {
            if(mute==true) {
                circle.setFill(Color.DARKRED);
            }else{
                circle.setFill(Color.DARKGREEN);
            }
        });
        r1.setOnMousePressed(event -> {
            if(mute==true){
                mute=false;
            }else {
                mute=true;
            }
            if(mute==true){
                try {
                    Menus.mediaplayer.pauseMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    Menus.mediaplayer.playMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        r1.setOnMouseReleased(event -> {
            if(mute==true) {
                circle.setFill(Color.RED);
            }else{
                circle.setFill(Color.GREEN);
            }
        });
        setAlignment(Pos.CENTER);
        getChildren().addAll(circle, r1);

    }
}


class Schadenanzeige extends HBox {
    public Schadenanzeige(String player,double Schaden){

        Title2 p1 = new Title2(player+" : ");
        Title2 dmg = new Title2(String.valueOf(Schaden));

        getChildren().addAll(p1,dmg);
    }

    //TODO children müssen noch ermittelt werden und eingefügt werden
    public Schadensberechnung(double ){
        ComponentList<CharacterStateComponent> characterState = null;

        for (Entity entity: Engine.getPlayers()) {
            CharacterStateComponent playerKnockback = characterState.getComponent(entity);
            Schaden = playerKnockback.knockback * 100;
            if (entity == Engine.getPlayer(1)){

            }else
            if (entity == Engine.getPlayer(2)){
                double damage2 = Schaden;
                Title2 dmg = new Title2(String.valueOf(damage2));
            }
        }
    }
}