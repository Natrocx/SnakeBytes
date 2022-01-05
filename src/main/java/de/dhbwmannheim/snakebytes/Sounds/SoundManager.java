package de.dhbwmannheim.snakebytes.Sounds;

/*
  Author: @Kirolis Eskondis
          @Thu Giang Tran
  This class implements the background music during a fight
 */

import de.dhbwmannheim.snakebytes.GUI.GameOverlay;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundManager {

    private MediaPlayer playerJump;
    private MediaPlayer playerPunch;
    private MediaPlayer playerSpAttack1;
    private MediaPlayer playerSpAttack2;
    private MediaPlayer playerDamage;
    private MediaPlayer playerKO;
    private MediaPlayer playerMatchOver;




    public SoundManager() {
        Media sound = new Media(new File("src/main/resources/sounds/jump.mp3").toURI().toString());
        Media sound2 = new Media(new File("src/main/resources/sounds/punch.mp3").toURI().toString());
        Media sound3 = new Media(new File("src/main/resources/sounds/spAttack1.mp3").toURI().toString());
        Media sound4 = new Media(new File("src/main/resources/sounds/spAttack2.mp3").toURI().toString());
        Media sound5 = new Media(new File("src/main/resources/sounds/damage.mp3").toURI().toString());
        Media sound6 = new Media(new File("src/main/resources/sounds/KO.mp3").toURI().toString());
        Media sound7 = new Media(new File("src/main/resources/sounds/matchover.wav").toURI().toString());
        this.playerJump = new MediaPlayer(sound);
        this.playerPunch = new MediaPlayer(sound2);
        this.playerSpAttack1 = new MediaPlayer(sound3);
        this.playerSpAttack2 = new MediaPlayer(sound4);
        this.playerDamage = new MediaPlayer(sound5);
        this.playerKO = new MediaPlayer(sound6);
        this.playerMatchOver = new MediaPlayer(sound7);

    }

    //to test if simultaneous attacks are possible
    public void playJumpSound() {
        if(!GameOverlay.soundMute) {
            playerJump.stop();
            playerJump.play();
        }
    }

    public void playPunchSound() {
        if(!GameOverlay.soundMute) {
            playerPunch.stop();
            playerPunch.play();
        }
    }

    public void playSpAttack1() {
        if(!GameOverlay.soundMute) {
            playerSpAttack1.stop();
            playerSpAttack1.play();
        }
    }

    public void playSpAttack2() {
        if(!GameOverlay.soundMute) {
            playerSpAttack2.stop();
            playerSpAttack2.play();
        }
    }

    public void playDamage() {
        if(!GameOverlay.soundMute) {
            playerDamage.stop();
            playerDamage.play();
        }
    }

    public void playKO() {
        if(!GameOverlay.soundMute) {
            playerKO.stop();
            playerKO.play();
        }
    }

    public void playMatchOver() {
        if(!GameOverlay.soundMute) {
            playerMatchOver.stop();
            playerMatchOver.play();
        }
    }

}
