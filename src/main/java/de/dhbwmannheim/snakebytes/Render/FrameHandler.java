package de.dhbwmannheim.snakebytes.Render;

// Author: @Kirolis Eskondis

import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import de.dhbwmannheim.snakebytes.ECS.PositionComponent;
import de.dhbwmannheim.snakebytes.GUI.Menus;
import de.dhbwmannheim.snakebytes.Sounds.MusicManager;
import javafx.animation.Timeline;
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

public class FrameHandler extends StackPane {


    private Scene scene;
    Pane root;

    ArrayList<ImageView> imagesP1 = initializeGraphics("player1");
    ArrayList<ImageView> imagesP2 = initializeGraphics("player2");
    ArrayList<ImageView> spcAttacks = initializeGraphics("graphics");

    public FrameHandler (Stage primaryStage) throws Exception {

        ImageView p1start = imagesP1.get(1);
        p1start.setTranslateY(615);
        p1start.setTranslateX(300);
        ImageView p2start= imagesP2.get(0);
        p2start.setTranslateY(615);
        p2start.setTranslateX(1000);

        this.root = Menus.createGameContent(primaryStage);
        root.getChildren().add(p1start);
        root.getChildren().add(p2start);
        this.scene = new Scene(root);
        primaryStage.setScene(scene);
    }


    public void update(FrameHandler frameHandler){

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

        var attackstate = */

    }

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

            case "graphics":
                ImageView pointeratkleft = new ImageView(new Image(new File("src/main/resources/level_assets/kammerjaeger-spcleft.png").toURI().toString()));
                ImageView pointeratkright = new ImageView(new Image(new File("src/main/resources/level_assets/kammerjager-spcright.png").toURI().toString()));
                ImageView bookleft = new ImageView(new Image(new File("src/main/resources/level_assets/exmatrikulator-spcleft.png").toURI().toString()));
                ImageView bookright = new ImageView(new Image(new File("src/main/resources/level_assets/exmatrikulator-spcright.png").toURI().toString()));
                ImageView[] helpListAtk = new ImageView[]{pointeratkleft,pointeratkright,bookleft,bookright};
                images.addAll(List.of(helpListAtk));
                break;
                }

        return images;
        }

}
