package de.dhbwmannheim.snakebytes.Render;



import de.dhbwmannheim.snakebytes.ECS.AttackStateComponent;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import de.dhbwmannheim.snakebytes.ECS.PositionComponent;
import de.dhbwmannheim.snakebytes.GUI.Menus;
import de.dhbwmannheim.snakebytes.Sounds.MusicManager;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/** Author: @Kirolis Eskondis
 * This class defines the FrameHandler which renders every frame the user sees
 */

public class FrameHandler extends StackPane {


    private Pane root;
    private PositionComponent oldPositionP1;
    private PositionComponent oldPositionP2;
    private PositionComponent oldPositionATK1;
    private PositionComponent oldPositionATK2;


    //Arraylists of needed images are loaded
    ArrayList<ImageView> imagesP1 = initializeGraphics("player1");
    ArrayList<ImageView> imagesP2 = initializeGraphics("player2");
    ArrayList<ImageView> spcAttacksP1 = initializeGraphics("attackP1");
    ArrayList<ImageView> spcAttacksP2 = initializeGraphics("attackP2");

    public FrameHandler (Stage primaryStage) throws Exception {

        //Images for entities are set in their starting positions
        ImageView p1start = imagesP1.get(1);
        p1start.setTranslateY(615);
        p1start.setTranslateX(300);
        ImageView p2start= imagesP2.get(0);
        p2start.setTranslateY(615);
        p2start.setTranslateX(1000);
        ImageView spcAttack1start = spcAttacksP1.get(0);
        spcAttack1start.setTranslateX(5000);
        spcAttack1start.setTranslateY(5000);
        ImageView spcAttack2start = spcAttacksP2.get(0);
        spcAttack2start.setTranslateY(5000);
        spcAttack2start.setTranslateX(5000);

        //createGameContent builds the Background and GameOverlay
        this.root = Menus.createGameContent(primaryStage);

        //Images are added to frame and scene is rendered
        root.getChildren().add(p1start);
        root.getChildren().add(p2start);
        root.getChildren().add(spcAttack1start);
        root.getChildren().add(spcAttack2start);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }


    public void update(Stage primaryStage){

        Entity player1 = Engine.getPlayer(0);
        Entity player2 = Engine.getPlayer(1);
        var position1 = ComponentManager.getComponentList(PositionComponent.class).getComponent(player1);
        var position2 = ComponentManager.getComponentList(PositionComponent.class).getComponent(player2);
        var player1state = ComponentManager.getComponentList(CharacterStateComponent.class).getComponent(player1).state;
        var player2state = ComponentManager.getComponentList(CharacterStateComponent.class).getComponent(player2).state;

        ImageView p1 = imagesP1.get(player1state);
        replace(p1,2,position1);

        ImageView p2 = imagesP2.get(player2state);
        replace(p2,3,position2);


        if(Engine.attackList.size() == 1){

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

        } else if (Engine.attackList.size() == 2) {

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
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

    }

    //
    public void replace(ImageView pic, int index, PositionComponent position){
        pic.setTranslateY((1 - position.value.y) * 900);
        pic.setTranslateX(position.value.x * 1350);
        root.getChildren().remove(index);
        root.getChildren().add(index,pic);
    }

    //returns ArrayList of needed Images that are sized as needed
    public ArrayList initializeGraphics(String player){
        ArrayList<ImageView> images = new ArrayList<ImageView>();

        switch (player){
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
                ImageView p2spcleft = new ImageView(new Image(new File("src/main/resources/char_models/exmatrikulator-spc-left.png").toURI().toString()));
                ImageView p2spcright = new ImageView(new Image(new File("src/main/resources/char_models/exmatrikulator-spc-right.png").toURI().toString()));
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
