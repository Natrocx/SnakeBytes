package de.dhbwmannheim.snakebytes.Sounds;

/**
 * Author: @Kirolis Eskondis
 *         @Thu Giang Tran
 * This class implements the background music during a fight
 ***/

import de.dhbwmannheim.snakebytes.GUI.GameOverlay;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;

public class SoundManager {

    private MediaPlayer playerJump;
    private MediaPlayer playerPunch;
    private MediaPlayer playerSpAttack1;
    private MediaPlayer playerSpAttack2;
    private MediaPlayer playerDamage;
    private MediaPlayer playerKO;
    private MediaPlayer playerMatchOver;

    private Media sound = new Media(getClass().getResource("/sounds/jump.mp3").toString());
    private Media sound2 = new Media(getClass().getResource("/sounds/punch.mp3").toString());
    private Media sound3 = new Media(getClass().getResource("/sounds/spAttack1.mp3").toString());
    private Media sound4 = new Media(getClass().getResource("/sounds/spAttack2.mp3").toString());
    private Media sound5 = new Media(getClass().getResource("/sounds/damage.mp3").toString());
    private Media sound6 = new Media(getClass().getResource("/sounds/KO.mp3").toString());
    private Media sound7 = new Media(getClass().getResource("/sounds/matchover.wav").toString());



    public SoundManager() {
        this.playerJump = new MediaPlayer(sound);
        this.playerPunch = new MediaPlayer(sound2);
        this.playerSpAttack1 = new MediaPlayer(sound3);
        this.playerSpAttack2 = new MediaPlayer(sound4);
        this.playerDamage = new MediaPlayer(sound5);
        this.playerKO = new MediaPlayer(sound6);
        this.playerMatchOver = new MediaPlayer(sound7);

    }

    //to test if simultanious attacks are possible
    public void playJumpSound() throws Exception {
        if(!GameOverlay.soundMute) {
            playerJump.stop();
            playerJump.play();
        }
    }

    public void playPunchSound() throws Exception {
        if(!GameOverlay.soundMute) {
            playerPunch.stop();
            playerPunch.play();
        }
    }

    public void playSpAttack1() throws Exception {
        if(!GameOverlay.soundMute) {
            playerSpAttack1.stop();
            playerSpAttack1.play();
        }
    }

    public void playSpAttack2() throws Exception {
        if(!GameOverlay.soundMute) {
            playerSpAttack2.stop();
            playerSpAttack2.play();
        }
    }

    public void playerDamage() throws Exception {
        if(!GameOverlay.soundMute) {
            playerDamage.stop();
            playerDamage.play();
        }
    }

    public void playerKO() throws Exception {
        if(!GameOverlay.soundMute) {
            playerKO.stop();
            playerKO.play();
        }
    }

    public void playerMatchOver() throws Exception {
        if(!GameOverlay.soundMute) {
            playerMatchOver.stop();
            playerMatchOver.play();
        }
    }

}
