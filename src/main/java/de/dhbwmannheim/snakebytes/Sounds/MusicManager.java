package de.dhbwmannheim.snakebytes.Sounds;

/*
 * Author: @Kirolis Eskondis
 *         @Thu Giang Tran
 * This class implements the background music during a fight
 * */

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicManager {

    //declaration has to be before a methods --> prevent garbage collector to cancel playback
    private MediaPlayer player;

    public MusicManager() {
        Media sound = new Media(new File("src/main/resources/music/SnakeBytes_GameMusic.mp3").toURI().toString());
        this.player = new MediaPlayer(sound);
    }

    public void playMusic(){

        //Loop
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));

        player.play();
    }

    public void pauseMusic(){

        player.pause();
    }

}