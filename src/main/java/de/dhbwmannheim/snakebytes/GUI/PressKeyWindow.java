package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PressKeyWindow extends VBox {
    public static String Key;

    public PressKeyWindow(Stage primaryStage) {
        PressKey title = new PressKey("P R E S S  A  B U T T O N");
        title.setTranslateY(20);
        title.setTranslateX(100);

       getChildren().addAll(title);

    }
}
class PressKey extends StackPane {
    public PressKey(String name) {
        Rectangle bg = new Rectangle(300, 40);
        bg.setStroke(Color.DARKRED);
        bg.setStrokeWidth(2);
        bg.setFill(null);

        Text text = new Text(name);
        text.setFill(Color.DARKRED);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 24));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg,text);

        // KeyPressedEvent -- Funktioniert nicht obviously
        setOnKeyPressed(event -> {

        });
    }
}
