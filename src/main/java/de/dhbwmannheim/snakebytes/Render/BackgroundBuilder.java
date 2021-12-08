package de.dhbwmannheim.snakebytes.Render;

// Author: @Kirolis Eskondis

import de.dhbwmannheim.snakebytes.GUI.GameOverlay;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class BackgroundBuilder extends StackPane{




    public BackgroundBuilder(Stage primaryStage) throws FileNotFoundException {
        Background bckgrnd = new Background(new BackgroundImage(new Image(new File("src/main/resources/level_assets/background.png").toURI().toString()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)));

        //Pane root = new Pane();
        setPrefSize(1350, 900);
        setBackground(bckgrnd);

        Image floor = new Image(new File("src/main/resources/level_assets/Floor.png").toURI().toString());
        Image step = new Image(new File("src/main/resources/level_assets/Steps.png").toURI().toString());


        ImageView floorIMG = new ImageView(floor);
        floorIMG.setTranslateX(100);
        floorIMG.setTranslateY(100);
        getChildren().add(floorIMG);

        ImageView stepIMG = new ImageView(step);
        stepIMG.setTranslateX(300);
        stepIMG.setTranslateY(550);
        getChildren().add(stepIMG);

        ImageView stepIMG2 = new ImageView(step);
        stepIMG2.setTranslateX(850);
        stepIMG2.setTranslateY(550);
        getChildren().add(stepIMG2);

        ImageView stepIMG3 = new ImageView(step);
        stepIMG3.setTranslateX(575);
        stepIMG3.setTranslateY(400);
        getChildren().add(stepIMG3);

        /*GameOverlay gov = new GameOverlay(primaryStage);
        getChildren().addAll(gov);

        Scene scene=new Scene(root);

        primaryStage.setMaxHeight(Integer.MAX_VALUE);
        primaryStage.setMaxWidth(Integer.MAX_VALUE);

        primaryStage.setScene(scene);*/

    }
}
