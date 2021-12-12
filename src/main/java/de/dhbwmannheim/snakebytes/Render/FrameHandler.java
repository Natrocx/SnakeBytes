package de.dhbwmannheim.snakebytes.Render;



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


    public void update(){

     /*   Entity player1 = Engine.getPlayer(0);
        Entity player2 = Engine.getPlayer(1);
        var position1 = ComponentManager.getComponentList(PositionComponent.class).getComponent(player1).value;
        var position2 = ComponentManager.getComponentList(PositionComponent.class).getComponent(player2).value;
        var player1state = ComponentManager.getComponentList(CharacterStateComponent.class).getComponent(player1).state;
        var player2state = ComponentManager.getComponentList(CharacterStateComponent.class).getComponent(player2).state;

        ImageView p1 = imagesP1.get(player1state);
        p1.setTranslateX(position1.x*1350);
        p1.setTranslateY((1-position1.y)*900);

        root.getChildren().remove(2);
        root.getChildren().add(2,p1);

        ImageView p2 = imagesP2.get(player2state);
        p1.setTranslateX(position1.x*1350);
        p1.setTranslateY((1-position1.y)*900);

        root.getChildren().remove(3);
        root.getChildren().add(3,p2);




        if(EntityAnzahl = 1){
            Entity attackentity1 =
            var attackstate1 =
            var attackpos1 = ComponentManager.getComponentList(PositionComponent.class).getComponent(attackentity1);

            if(attackstate1 < 2) {
                ImageView atk1 = spcAttack1.get(attackstate);
            else if (attackstate > 1)
                    ImageView atk1 = spcAttack2.get(attackstate - 2);
            }

            atk1.setTranslateY((1 - attackpos1.y) * 900);
            atk1.setTranslateX(attackpos1.x * 1350);

        } else if (entityAnzahl = 2) {
            Entity attackentity1 =
            Entity attackentity2 =
            var attackstate1 =
            var attackstate2 =
            var attackpos1 = ComponentManager.getComponentList(PositionComponent.class).getComponent(attackentity1);
            var attackpos2 = ComponentManager.getComponentList(PositionComponent.class).getComponent(attackentity2);

            if (attackstate1 < 2) {
                ImageView atk1 = spcAttack1.get(attackstate1);
            else if (attackstate > 1)
                    ImageView atk1 = spcAttack2.get(attackstate1 - 2);
            }
            if (attackstate2 < 2) {
                ImageView atk2 = spcAttack1.get(attackstate2);
            else if (attackstate2 > 1)
                    ImageView atk2 = spcAttack2.get(attackstate2 - 2);
            }

            atk1.setTranslateY((1 - attackpos1.y) * 900);
            atk1.setTranslateX(attackpos1.x * 1350);
            atk1.setTranslateY((1 - attackpos2.y) * 900);
            atk1.setTranslateX(attackpos2.x * 1350);
        }

        }*/

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
