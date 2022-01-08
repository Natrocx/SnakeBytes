package de.dhbwmannheim.snakebytes.Sounds;

import de.dhbwmannheim.snakebytes.GUI.GameOverlay;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

/**
 * Author:  @Kirolis Eskondis
 *          @Thu Giang Tran
 * This class allows the implementation of sounds during the fight
 **/


public class SoundManager {

    private final MediaPlayer playerJump;
    private final MediaPlayer playerPunch;
    private final MediaPlayer playerSpAttack1;
    private final MediaPlayer playerSpAttack2;
    private final MediaPlayer playerDamage;
    private final MediaPlayer playerKO;
    private final MediaPlayer playerMatchOver;


    public SoundManager() {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource("/sounds/jump.mp3")).toString());
        this.playerJump = new MediaPlayer(sound);
        Media sound2 = new Media(Objects.requireNonNull(getClass().getResource("/sounds/punch.mp3")).toString());
        this.playerPunch = new MediaPlayer(sound2);
        Media sound3 = new Media(Objects.requireNonNull(getClass().getResource("/sounds/spAttack1.mp3")).toString());
        this.playerSpAttack1 = new MediaPlayer(sound3);
        Media sound4 = new Media(Objects.requireNonNull(getClass().getResource("/sounds/spAttack2.mp3")).toString());
        this.playerSpAttack2 = new MediaPlayer(sound4);
        Media sound5 = new Media(Objects.requireNonNull(getClass().getResource("/sounds/damage.mp3")).toString());
        this.playerDamage = new MediaPlayer(sound5);
        Media sound6 = new Media(Objects.requireNonNull(getClass().getResource("/sounds/KO.mp3")).toString());
        this.playerKO = new MediaPlayer(sound6);
        Media sound7 = new Media(Objects.requireNonNull(getClass().getResource("/sounds/matchover.wav")).toString());
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
