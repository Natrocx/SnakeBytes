package de.dhbwmannheim.snakebytes.Sounds;

/*
 * Author: @Kirolis Eskondis
 *         @Thu Giang Tran
 *         @Eric Stefan
 * This class implements the background music during a fight
 * */

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;

public class MusicManager {

    //declaration has to be before a methods --> prevent garbage collector to cancel playback
    private MediaPlayer player;
    private Media sound = new Media(getClass().getResource("/music/SnakeBytes_GameMusic.mp3").toString());

    public MusicManager() {
        this.player = new MediaPlayer(sound);
    }

    public void playMusic() throws Exception {

        //Loop
        player.setOnEndOfMedia(new Runnable() {
            public void run() {
                player.seek(Duration.ZERO);
            }
        });

        player.play();
    }

    public void pauseMusic() throws Exception {

        player.pause();
    }

}