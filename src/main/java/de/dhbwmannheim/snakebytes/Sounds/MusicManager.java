package de.dhbwmannheim.snakebytes.Sounds;

/*
 * Author: @Kirolis Eskondis
 *         @Thu Giang Tran
 *         @Eric Stefan
 * This class implements the background music during a fight
 * */

import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


import java.util.Objects;

import static de.dhbwmannheim.snakebytes.GUI.Menus.mediaplayer;

/**
 * Author:  @Thu Giang Tran
 *          @Kirolis Eskondis
 *          @Eric Stefan
 * This class allows the implementation of background music during the fight
 **/

public class MusicManager {

    //declaration has to be before a methods --> prevent garbage collector to cancel playback
    private final MediaPlayer player;

    public MusicManager() {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource("/music/SnakeBytes_GameMusic.mp3")).toString());
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

    public static void toggleMusic(){
        if(!Engine.paused){
            mediaplayer.playMusic();
        }else{
            mediaplayer.pauseMusic();
        }
    }

}