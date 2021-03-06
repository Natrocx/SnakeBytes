package de.dhbwmannheim.snakebytes.Render;

import javafx.geometry.Side;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Objects;

/**
 * Author: @Kirolis Eskondis
 * This class builds the arena when match is started
 **/

public class                                                                                                                                             BackgroundBuilder extends StackPane{




    public BackgroundBuilder() {
        //set Background image
        Background bckgrnd = new Background(new BackgroundImage(new Image(Objects.requireNonNull(getClass().getResource("/level_assets/background.png")).toString()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)));

        setPrefSize(1350, 900);
        setBackground(bckgrnd);

        //set platform images as well as their positions and size
        Image floor = new Image(Objects.requireNonNull(getClass().getResource("/level_assets/Floor.png")).toString());
        Image step = new Image(Objects.requireNonNull(getClass().getResource("/level_assets/Steps.png")).toString());


        ImageView floorIMG = new ImageView(floor);
        floorIMG.setTranslateX(0);
        floorIMG.setTranslateY(350);
        getChildren().add(floorIMG);

        ImageView stepIMG = new ImageView(step);
        stepIMG.setTranslateX(-250);
        stepIMG.setTranslateY(135);
        stepIMG.setFitHeight(30);
        getChildren().add(stepIMG);

        ImageView stepIMG2 = new ImageView(step);
        stepIMG2.setTranslateX(250);
        stepIMG2.setTranslateY(135);
        stepIMG2.setFitHeight(30);
        getChildren().add(stepIMG2);

        ImageView stepIMG3 = new ImageView(step);
        stepIMG3.setTranslateX(0);
        stepIMG3.setTranslateY(-20);
        stepIMG3.setFitHeight(30);
        getChildren().add(stepIMG3);


    }
}
