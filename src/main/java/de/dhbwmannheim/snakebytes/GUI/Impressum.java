package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//by Kai Schwab

public class Impressum extends VBox {
    public Impressum(Stage primaryStage) {
        //Title
        HeaderI headerI = new HeaderI(primaryStage);
        Title2 ws = new Title2("");

        //Items
        TextElement t1 = new TextElement("Exmatrikulator", "Die Special-Fähigkeit des Exmatrikukalors stellt die verschiedenen Biblitheken, als eine der stärksten Waffen, dar. ");
        TextElement t2 = new TextElement("Cyber-Kammerjäger", "Die Special-Fähigkeit des Kammernjägers stellt einen Pointer , als Killer-Feature, dar. Die Pointer hat die Eigenschaft, die der Programmiersprache C zugeschriebn wird, schnell zu sein ");


        getChildren().addAll(headerI, ws, t1, t2);
    }
}

class HeaderI extends HBox {
    public HeaderI(Stage primaryStage) {
        Title2 titleI = new Title2("Impressum und Erklärungen");
        titleI.setTranslateX(150);
        titleI.setTranslateY(20);


        Back back = new Back(primaryStage);
        back.setTranslateX(400);
        back.setTranslateY(20);

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(titleI, back);
    }
}

class TextElement extends HBox {
    public TextElement(String title, String text) {
        Title3 titleE = new Title3(title);

        Textpart textpart = new Textpart(text);

        getChildren().addAll(titleE, textpart);
    }
}

class Title3 extends StackPane {
    public Title3(String name) {

        Text text = new Text(name);
        text.setFill(Color.DARKRED);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        text.setWrappingWidth(250);

        setAlignment(Pos.CENTER);
        getChildren().addAll(text);
    }
}

class Textpart extends StackPane {
    public Textpart(String explenation) {

        Text text = new Text(explenation);
        text.setFill(Color.DARKRED);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        text.setWrappingWidth(900);

        setAlignment(Pos.CENTER);
        getChildren().addAll(text);
    }
}
