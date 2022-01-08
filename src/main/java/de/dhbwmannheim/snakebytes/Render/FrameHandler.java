package de.dhbwmannheim.snakebytes.Render;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.GUI.CharacterMenu;
import de.dhbwmannheim.snakebytes.GUI.Menus;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Author: @Kirolis Eskondis
 * This class defines the FrameHandler which renders every frame the user sees
 **/

public class FrameHandler extends StackPane {


    private Scene scene;


    //Arraylists of needed images are loaded
    ArrayList<ImageView> imagesP1 = initializeGraphics("player1");
    ArrayList<ImageView> imagesP2 = initializeGraphics("player2");
    ArrayList<ImageView> spcAttacksP1 = initializeGraphics("attackP1");
    ArrayList<ImageView> spcAttacksP2 = initializeGraphics("attackP2");

    //Constructor creaates the screen which is shown to the user at the start.
    //Creates ImageViews for Background, Arena, Platforms, Characters, Attacks and puts them into the respective positions
    public FrameHandler(Stage primaryStage, Consumer<KeyEvent> keyPressCallback) {

        //Images for entities are set in their starting positions
        ImageView p1start = imagesP1.get(1);
        p1start.setTranslateY(615);
        p1start.setTranslateX(300);
        ImageView p2start = imagesP2.get(0);
        p2start.setTranslateY(615);
        p2start.setTranslateX(1000);

        //Special attacks are set outside of viewable screen
        ImageView spcAttack1start = spcAttacksP1.get(0);
        spcAttack1start.setTranslateX(5000);
        spcAttack1start.setTranslateY(5000);
        ImageView spcAttack2start = spcAttacksP2.get(0);
        spcAttack2start.setTranslateY(5000);
        spcAttack2start.setTranslateX(5000);

        //createGameContent builds the Background and GameOverlay
        Menus.createGameContent(primaryStage);

        //Images are added to frame and scene is rendered

        Menus.root.getChildren().add(2, p1start);
        Menus.root.getChildren().add(3, p2start);
        Menus.root.getChildren().add(4, spcAttack1start);
        Menus.root.getChildren().add(5, spcAttack2start);
        scene = new Scene(Menus.root);
        //Makes it possible for key inputs to be used on the screen and records them
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent) -> {
            //System.out.println("pressed key + " + KeyEvent.toString());
            if (keyPressCallback != null) {
                keyPressCallback.accept(KeyEvent);
            }
        });
        primaryStage.setScene(scene);
    }


    //Updates the current View of the Gameplay on Screen
    public void update(Stage primaryStage) {
        //This is needed for a JavaFX Thread
        Platform.runLater(() -> {

            if(!Engine.paused && CharacterMenu.render) {
                //Get Players and their positions, playerstates
                Entity player1 = Engine.getPlayer(0);
                Entity player2 = Engine.getPlayer(1);
                var positionComponent = ComponentManager.getComponentList(PositionComponent.class);
                var playerstate = ComponentManager.getComponentList(CharacterStateComponent.class);

            /*
            The way playerstates work:
            0- looking left
            1- looking right
            2- normal attack left
            3 - normal attack right
            4 - special attack left
            5 - special attack right
             */

            /* Gets the needed image for the current state out of the ArrayList.
               If it is one of the attack states, the ImageView must be wider because the characters use their arms instead of
               them just simply hanging off the side of their bodies */
                var position1 = positionComponent.getComponent(player1);
                var p1PlayerState = playerstate.getComponent(player1);
                ImageView p1;
                if (p1PlayerState != null) {
                    if (p1PlayerState.hitState) {  //If player is currently being hit, display a different character design image
                        p1 = imagesP1.get(p1PlayerState.lookingDirection + 6); //if lookingdirection = 0 load picture 6, if lookingdirection = 1, load picture 7
                    } else {
                        p1 = imagesP1.get(p1PlayerState.state);
                        if (p1PlayerState.state > 1) {
                            p1.setFitWidth(73);
                        }
                    }
                    replace(p1, 2, position1);
                }

                var position2 = positionComponent.getComponent(player2);
                var p2PlayerState = playerstate.getComponent(player2);
                ImageView p2;
                if (p2PlayerState != null) {
                    if (p2PlayerState.hitState) {
                        p2 = imagesP2.get(p2PlayerState.lookingDirection + 6);
                    } else {
                        p2 = imagesP2.get(p2PlayerState.state);
                        if (p2PlayerState.state > 1) {
                            p2.setFitWidth(68);
                        }
                    }

                    replace(p2, 3, position2);
                }

                PositionComponent defaultPos = new PositionComponent(new Vec2(3.0, 3.0));

                //remove special attacks which already collided with another player/screenborder
                if (Engine.attackList.size() == 0) {
                    ImageView defaultAtk1 = spcAttacksP1.get(0);
                    ImageView defaultAtk2 = spcAttacksP2.get(0);
                    replace(defaultAtk1, 4, defaultPos);
                    replace(defaultAtk2, 5, defaultPos);
                }
                //If one special attack is used at a time
                else if (Engine.attackList.size() == 1) {
                    Entity attackEntity1 = Engine.attackList.get(0);
                    checkIfOver(defaultPos, attackEntity1);
                }
                //if two special attacks are used at the same time
                else if (Engine.attackList.size() == 2) {

                    for (Entity e : Engine.attackList) {
                        checkIfOver(defaultPos, e);
                    }
                }
                scene.setRoot(Menus.root);
                primaryStage.setScene(scene);
            }
        });

    }

    //This function is to prevent visual bugs e.g. the special attack being rendered even though it should've already disappeared
    private void checkIfOver(PositionComponent defaultPos, Entity e) {
        var attackMotion = ComponentManager.getComponentList(MotionComponent.class).getComponent(e);
        if (attackMotion.timeToDecay == 0 || attackMotion.velocity.x == 0) {
            ImageView defaultAtk1 = spcAttacksP1.get(0);
            ImageView defaultAtk2 = spcAttacksP2.get(0);
            replace(defaultAtk1, 4, defaultPos);
            replace(defaultAtk2, 5, defaultPos);
        } else {
            attackEntityRender(e);
        }
    }

    //This function renders the attack entity depending on player and direction
    private void attackEntityRender(Entity attackEntity1) {
        var attackPos = ComponentManager.getComponentList(PositionComponent.class).getComponent(attackEntity1);
        var characterAttack = ComponentManager.getComponentList(AttackStateComponent.class).getComponent(attackEntity1);
        if (characterAttack != null) {
            var attackState = characterAttack.state;
            //differentiates between the special attacks of the different characters: 0-1 -> Cyber-Kammerjaeger, 2-3 -> Exmatrikulator
            if (attackState < 2) {
                ImageView atk1 = spcAttacksP1.get(attackState);
                replace(atk1, 4, attackPos);
            } else if (attackState > 1) {
                ImageView atk1 = spcAttacksP2.get(attackState - 2);
                replace(atk1, 5, attackPos);
            }
        }

    }

    //replaces current Images with new ones at a new position depending on character/attack state
    public void replace(ImageView pic, int index, PositionComponent position) {
        if (position.value.x > 1.0 | position.value.y > 1.0) {
            pic.setTranslateX(5000);
            pic.setTranslateY(5000);
            Menus.root.getChildren().remove(index);
            Menus.root.getChildren().add(index, pic);
        }
        pic.setTranslateY((1 - position.value.y) * 900);
        pic.setTranslateX(position.value.x * 1350);
        Menus.root.getChildren().remove(index);
        Menus.root.getChildren().add(index, pic);

    }

    //returns ArrayLists of needed Images in the same order as characterstates are numbered
    public ArrayList initializeGraphics(String listCase) {
        ArrayList<ImageView> images = new ArrayList<>();

        switch (listCase) {
            case "player1" -> {
                ImageView p1left = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/kammerjaeger-left.png")).toString()));
                ImageView p1right = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/kammerjaeger-right.png")).toString()));
                ImageView p1atkleft = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/kammerjaeger-atk-left.png")).toString()));
                ImageView p1atkright = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/kammerjaeger-atk-right.png")).toString()));
                ImageView p1spcleft = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/kammerjaeger-special-left.png")).toString()));
                ImageView p1spcright = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/kammerjaeger-special-right.png")).toString()));
                ImageView p1dmgleft = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/kammerjaeger-dmg-left.png")).toString()));
                ImageView p1dmgright = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/kammerjaeger-dmg-right.png")).toString()));

                ImageView[] helpList = new ImageView[]{p1left, p1right, p1atkleft, p1atkright, p1spcleft, p1spcright, p1dmgleft, p1dmgright};
                images.addAll(List.of(helpList));
                for (ImageView e : images) {

                    e.setFitHeight(120);
                    e.setFitWidth(45);
                }
            }
            case "player2" -> {
                ImageView p2left = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/exmatrikulator-left.png")).toString()));
                ImageView p2right = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/exmatrikulator-right.png")).toString()));
                ImageView p2atkleft = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/exmatrikulator-atk-left.png")).toString()));
                ImageView p2atkright = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/exmatrikulator-atk-right.png")).toString()));
                ImageView p2spcleft = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/exmatrikulator-special-left.png")).toString()));
                ImageView p2spcright = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/exmatrikulator-special-right.png")).toString()));
                ImageView p2dmgleft = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/exmatrikulator-dmg-left.png")).toString()));
                ImageView p2dmgright = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/char_models/exmatrikulator-dmg-right.png")).toString()));
                ImageView[] anotherhelpList = new ImageView[]{p2left, p2right, p2atkleft, p2atkright, p2spcleft, p2spcright, p2dmgleft, p2dmgright};
                images.addAll(List.of(anotherhelpList));
                for (ImageView e : images) {
                    e.setFitHeight(120);
                    e.setFitWidth(55);
                }
            }
            case "attackP1" -> {
                ImageView pointeratkleft = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/level_assets/kammerjaeger-spcleft.png")).toString()));
                ImageView pointeratkright = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/level_assets/kammerjager-spcright.png")).toString()));
                ImageView[] helpListAtk1 = new ImageView[]{pointeratkleft, pointeratkright};
                images.addAll(List.of(helpListAtk1));
                for (ImageView e : images) {
                    e.setFitHeight(25);
                    e.setFitWidth(115);
                }
            }
            case "attackP2" -> {
                ImageView bookleft = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/level_assets/exmatrikulator-spcleft.png")).toString()));
                ImageView bookright = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/level_assets/exmatrikulator-spcright.png")).toString()));
                ImageView[] helpListAtk2 = new ImageView[]{bookleft, bookright};
                images.addAll(List.of(helpListAtk2));
                for (ImageView e : images) {
                    e.setFitHeight(70);
                    e.setFitWidth(70);
                }
            }
        }

        return images;
    }


}