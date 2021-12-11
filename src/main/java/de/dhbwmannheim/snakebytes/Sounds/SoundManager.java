package de.dhbwmannheim.snakebytes.Sounds;

/*
 * Author: @Kirolis Eskondis
 *         @Thu Giang Tran
 * This class implements the sound effects during a fight
 * */

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundManager {

    MediaPlayer player;

    public SoundManager() {
    }

    public void playJumpSound() throws Exception {
        //Media Creation
        Media sound = new Media(new File("src/main/resources/sounds/jump.mp3").toURI().toString());
        player = new MediaPlayer(sound);

        player.play();
        player.setVolume(0.0);
    }

    public void playPunchSound() throws Exception {
        //Media Creation
        Media sound = new Media(new File("src/main/resources/sounds/punch.mp3").toURI().toString());
        player = new MediaPlayer(sound);

        player.play();
        player.setVolume(0.0);
    }

    public void playSpAttack1() throws Exception {
        //Media Creation
        Media sound = new Media(new File("src/main/resources/sounds/spAttack1.mp3").toURI().toString());
        player = new MediaPlayer(sound);

        player.play();
        player.setVolume(0.0);
    }

    public void playSpAttack2() throws Exception {
        //Media Creation
        Media sound = new Media(new File("src/main/resources/sounds/spAttack2.mp3").toURI().toString());
        player = new MediaPlayer(sound);

        player.play();
        player.setVolume(0.0);
    }

}
