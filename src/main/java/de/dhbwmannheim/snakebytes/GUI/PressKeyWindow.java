package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static de.dhbwmannheim.snakebytes.GUI.Menus.createTitleContent;

public class PressKeyWindow extends VBox {
    public static String key = "";

    public PressKeyWindow(Stage primaryStage) throws FileNotFoundException {

        Text text = new Text("PRESS A BUTTON");
        text.setFill(Color.DARKRED);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 24));


        Back back = new Back(primaryStage);
        VBox test = new VBox();
        test.getChildren().addAll(text,back);
        final Scene scene = new Scene(test);
        System.out.println("btest2");
        scene.setOnKeyPressed(event->{
            key=event.getCode().toString();
            System.out.println(event.getCode().toString());
            Stage s = (Stage) scene.getWindow();
            s.show();
        });
        primaryStage.setScene(scene);

    }
}
