package de.dhbwmannheim.snakebytes;

import de.dhbwmannheim.snakebytes.Sounds.MusicManager;
import de.dhbwmannheim.snakebytes.Sounds.SoundManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Erstellung der Stage, unwichtig für Music
        primaryStage.setTitle("SNAKE BYTES");
        Pane root = new Pane();

        root.setPrefSize(1350, 900);
        Scene MainMenu = new Scene(root, Color.LIGHTBLUE);

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


        //Aufruf des MusicManager
        /*MusicManager playMusicManager = new MusicManager();
        playMusicManager.playMusic();*/

        //Aufruf des SoundManager
        SoundManager playSoundManager = new SoundManager();
        playSoundManager.playSpAttack2();

    }


}