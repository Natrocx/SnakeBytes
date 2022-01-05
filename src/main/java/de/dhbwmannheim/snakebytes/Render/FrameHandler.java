package de.dhbwmannheim.snakebytes.Render;



import de.dhbwmannheim.snakebytes.ECS.AttackStateComponent;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import de.dhbwmannheim.snakebytes.ECS.PositionComponent;
import de.dhbwmannheim.snakebytes.ECS.Systems.InputSystem;
import de.dhbwmannheim.snakebytes.ECS.Vec2;
import de.dhbwmannheim.snakebytes.GUI.Menus;
import de.dhbwmannheim.snakebytes.GUI.PressKeyWindow;
import de.dhbwmannheim.snakebytes.Sounds.MusicManager;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/** Author: @Kirolis Eskondis
 * This class defines the FrameHandler which renders every frame the user sees
 */

public class FrameHandler extends StackPane {


    private Scene scene;
    private PositionComponent oldPositionP1;
    private PositionComponent oldPositionP2;
    private PositionComponent oldPositionATK1;
    private PositionComponent oldPositionATK2;


    //Arraylists of needed images are loaded
    ArrayList<ImageView> imagesP1 = initializeGraphics("player1");
    ArrayList<ImageView> imagesP2 = initializeGraphics("player2");
    ArrayList<ImageView> spcAttacksP1 = initializeGraphics("attackP1");
    ArrayList<ImageView> spcAttacksP2 = initializeGraphics("attackP2");

    //Constructor creaates the screen which is shown to the user at the start.
    //Creates ImageViews for Background, Arena, Platforms, Characters, Attacks and puts them into the respective positions
    public FrameHandler (Stage primaryStage, Consumer<KeyEvent> keyPressCallback) throws Exception {

        //Images for entities are set in their starting positions
        ImageView p1start = imagesP1.get(1);
        p1start.setTranslateY(615);
        p1start.setTranslateX(300);
        ImageView p2start= imagesP2.get(0);
        p2start.setTranslateY(615);
        p2start.setTranslateX(1000);
        ImageView spcAttack1start = spcAttacksP1.get(0);
        spcAttack1start.setTranslateX(100);
        spcAttack1start.setTranslateY(100);
        ImageView spcAttack2start = spcAttacksP2.get(0);
        spcAttack2start.setTranslateY(100);
        spcAttack2start.setTranslateX(100);

        //createGameContent builds the Background and GameOverlay
        Menus.createGameContent(primaryStage);

        //Images are added to frame and scene is rendered

        Menus.root.getChildren().add(2,p1start);
        Menus.root.getChildren().add(3,p2start);
        Menus.root.getChildren().add(4,spcAttack1start);
        Menus.root.getChildren().add(5,spcAttack2start);
        scene = new Scene(Menus.root);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent) -> {
            System.out.println("pressed key + " + KeyEvent.toString());
                    PressKeyWindow.Key = KeyEvent.getCode().toString();
                    if(keyPressCallback != null){
                        keyPressCallback.accept(KeyEvent);
                    }
                });
        primaryStage.setScene(scene);
    }


    //Updates the current View of the Gameplay
    public void update(Stage primaryStage){
        //This is needed for a JavaFX Thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

        //Get Players and their positions, playerstates
        Entity player1 = Engine.getPlayer(0);
        Entity player2 = Engine.getPlayer(1);
        var positionComponent = ComponentManager.getComponentList(PositionComponent.class);
        var playerstate = ComponentManager.getComponentList(CharacterStateComponent.class);

        var position1 = positionComponent.getComponent(player1);
        /*
        The way playerstates work:
        0- looking left
        1- looking right
        2- normal attack left
        3 - normal attack right
        4 - special attack left
        5 - special attack right
         */
        //Gets the needed image for the current state out of the ArrayList.
        //If it is one of the attack states, the ImageView must be wider because the characters use their arms instead of
        //them just simply hanging off the side of their bodies
        if(playerstate.getComponent(player1) != null){
            ImageView p1 = imagesP1.get(playerstate.getComponent(player1).state);
            if(playerstate.getComponent(player1).state >1){
                p1.setFitWidth(73);
            }
        replace(p1,2,position1);
        }

        var position2 = positionComponent.getComponent(player2);
        if(playerstate.getComponent(player2) != null){
            ImageView p2 = imagesP2.get(playerstate.getComponent(player2).state);
            if(playerstate.getComponent(player2).state>1){
                p2.setFitWidth(68);
            }
        replace(p2,3,position2);
        }


        //remove special attacks which already collided with another player/screenborder
        if(Engine.attackList.size() == 0){
            PositionComponent defaultPos = new PositionComponent(new Vec2(3.0,3.0));
            ImageView defaultAtk1 = spcAttacksP1.get(0);
            ImageView defaultAtk2 = spcAttacksP2.get(0);
            replace(defaultAtk1,4,defaultPos);
            replace(defaultAtk2,5,defaultPos);
        }
        //If one special attack is used at a time
        else if(Engine.attackList.size() == 1){

            Entity attackEntity1 = Engine.attackList.get(0);
            var attackstate = ComponentManager.getComponentList(AttackStateComponent.class).getComponent(attackEntity1).state;
            var attackpos = ComponentManager.getComponentList(PositionComponent.class).getComponent(attackEntity1);

            if(attackstate < 2) {
                ImageView atk1 = spcAttacksP1.get(attackstate);
                replace(atk1,4,attackpos);
            }
            else if (attackstate > 1) {
                    ImageView atk1 = spcAttacksP2.get(attackstate - 2);
                    replace(atk1,5,attackpos);
            }

        }
        //if two special attacks are used at the same time
        else if (Engine.attackList.size() == 2) {

            for(Entity e: Engine.attackList){
                var attackstate = ComponentManager.getComponentList(AttackStateComponent.class).getComponent(e).state;
                var attackpos = ComponentManager.getComponentList(PositionComponent.class).getComponent(e);

                if (attackstate < 2) {
                    ImageView atk1 = spcAttacksP1.get(attackstate);
                    replace(atk1,4,attackpos);
                }
                else if (attackstate > 1){
                    ImageView atk1 = spcAttacksP2.get(attackstate - 2);
                    replace(atk1,5,attackpos);
                }
            }
        }

                scene.setRoot(Menus.root);
                primaryStage.setScene(scene);
            }
        });


    }

    //replaces current Images with new ones at a new position depending on character/attack state
    public void replace(ImageView pic, int index, PositionComponent position){
        if(position.value.x > 1.0 | position.value.y > 1.0){
            pic.setTranslateX(5000);
            pic.setTranslateY(5000);
            Menus.root.getChildren().remove(index);
            Menus.root.getChildren().add(index,pic);
        }
        pic.setTranslateY((1 - position.value.y) * 900);
        pic.setTranslateX(position.value.x * 1350);
        Menus.root.getChildren().remove(index);
        Menus.root.getChildren().add(index,pic);

    }

    //returns ArrayLists of needed Images in the same order as characterstates are numbered
    public ArrayList initializeGraphics(String listCase){
        ArrayList<ImageView> images = new ArrayList<ImageView>();

        switch (listCase){
            case "player1":
                ImageView p1left = new ImageView(new Image(new File("src/main/resources/char_models/kammerjaeger-left.png").toURI().toString()));
                ImageView p1right = new ImageView(new Image(new File("src/main/resources/char_models/kammerjaeger-right.png").toURI().toString()));
                ImageView p1atkleft = new ImageView(new Image(new File("src/main/resources/char_models/kammerjaeger-atk-left.png").toURI().toString()));
                ImageView p1atkright = new ImageView(new Image(new File("src/main/resources/char_models/kammerjaeger-atk-right.png").toURI().toString()));
                ImageView p1spcleft = new ImageView(new Image(new File("src/main/resources/char_models/kammerjaeger-special-left.png").toURI().toString()));
                ImageView p1spcright = new ImageView(new Image(new File("src/main/resources/char_models/kammerjaeger-special-right.png").toURI().toString()));
                ImageView[] helpList = new ImageView[] {p1left, p1right, p1atkleft,p1atkright,p1spcleft,p1spcright};
                images.addAll(List.of(helpList));

                for(ImageView e: images) {

                    e.setFitHeight(120);
                    e.setFitWidth(45);
                }
                break;

            case "player2":
                ImageView p2left = new ImageView(new Image(new File("src/main/resources/char_models/exmatrikulator-left.png").toURI().toString()));
                ImageView p2right = new ImageView(new Image(new File("src/main/resources/char_models/exmatrikulator-right.png").toURI().toString()));
                ImageView p2atkleft = new ImageView(new Image(new File("src/main/resources/char_models/exmatrikulator-atk-left.png").toURI().toString()));
                ImageView p2atkright = new ImageView(new Image(new File("src/main/resources/char_models/exmatrikulator-atk-right.png").toURI().toString()));
                ImageView p2spcleft = new ImageView(new Image(new File("src/main/resources/char_models/exmatrikulator-special-left.png").toURI().toString()));
                ImageView p2spcright = new ImageView(new Image(new File("src/main/resources/char_models/exmatrikulator-special-right.png").toURI().toString()));
                ImageView[] anotherhelpList = new ImageView[] {p2left,p2right,p2atkleft,p2atkright,p2spcleft,p2spcright};
                images.addAll(List.of(anotherhelpList));

                for(ImageView e: images) {
                    e.setFitHeight(120);
                    e.setFitWidth(55);
                }
                break;

            case "attackP1":
                ImageView pointeratkleft = new ImageView(new Image(new File("src/main/resources/level_assets/kammerjaeger-spcleft.png").toURI().toString()));
                ImageView pointeratkright = new ImageView(new Image(new File("src/main/resources/level_assets/kammerjager-spcright.png").toURI().toString()));
                ImageView[] helpListAtk1 = new ImageView[]{pointeratkleft,pointeratkright};
                images.addAll(List.of(helpListAtk1));

                for(ImageView e: images){
                    e.setFitHeight(25);
                    e.setFitWidth(115);
                }
                break;

            case "attackP2":
                ImageView bookleft = new ImageView(new Image(new File("src/main/resources/level_assets/exmatrikulator-spcleft.png").toURI().toString()));
                ImageView bookright = new ImageView(new Image(new File("src/main/resources/level_assets/exmatrikulator-spcright.png").toURI().toString()));
                ImageView[] helpListAtk2 = new ImageView[]{bookleft,bookright};
                images.addAll(List.of(helpListAtk2));

                for (ImageView e: images){
                    e.setFitHeight(70);
                    e.setFitWidth(70);
                }
                break;
        }

        return images;
        }



}
