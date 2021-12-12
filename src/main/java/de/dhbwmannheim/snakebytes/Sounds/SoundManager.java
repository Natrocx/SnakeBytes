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

    private MediaPlayer playerJump;
    private MediaPlayer playerPunch;
    private MediaPlayer playerSpAttack1;
    private MediaPlayer playerSpAttack2;

    private Media sound = new Media(new File("src/main/resources/sounds/jump.mp3").toURI().toString());
    private Media sound2 = new Media(new File("src/main/resources/sounds/punch.mp3").toURI().toString());
    private Media sound3 = new Media(new File("src/main/resources/sounds/spAttack1.mp3").toURI().toString());
    private Media sound4 = new Media(new File("src/main/resources/sounds/spAttack2.mp3").toURI().toString());


    public SoundManager() {
        this.playerJump = new MediaPlayer(sound);
        this.playerPunch = new MediaPlayer(sound2);
        this.playerSpAttack1 = new MediaPlayer(sound3);
        this.playerSpAttack2 = new MediaPlayer(sound4);
    }

    //to test if simultanious attacks are possible
    public void playJumpSound() throws Exception {

        playerJump.play();
    }

    public void playPunchSound() throws Exception {

        playerPunch.play();
    }

    public void playSpAttack1() throws Exception {

        playerSpAttack1.play();
    }

    public void playSpAttack2() throws Exception {

        playerSpAttack2.play();
    }

}
