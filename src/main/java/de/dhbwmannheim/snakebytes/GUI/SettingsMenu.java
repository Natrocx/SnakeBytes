package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Objects;

import static de.dhbwmannheim.snakebytes.GUI.Menus.createTitleContent;

/**
 * Author:  @Kai Schwab
 *          @Eric Stefan
 **/

public class SettingsMenu extends StackPane {

    public SettingsMenu(Stage primaryStage) {
        // Title
        HeaderP title = new HeaderP(primaryStage);
        title.setTranslateX(400);
        title.setTranslateY(-100);

        //so that the focus is not set on the input field "tes" automatically, since its prompt text should be visible
        title.setFocusTraversable(true);
        title.requestFocus();

        SettingsTable settings = new SettingsTable(primaryStage);
        Button b = new Button();
        b.setText("Aktualisieren");
        b.setTranslateX(750);
        b.setTranslateY(100);
        //if the refresh ("Aktualisieren") button is pressed, reload all values
        b.setOnMouseClicked(event->{
            getChildren().clear();
            SettingsTable settings1 = new SettingsTable(primaryStage);
            settings1.setTranslateX(50);
            settings1.setTranslateY(100);
            getChildren().addAll(title, settings1,b);
        });
        settings.setTranslateX(50);
        settings.setTranslateY(100);
        setAlignment(Pos.CENTER);
        getChildren().addAll(title, settings,b);
    }
}

class HeaderP extends HBox {
    public HeaderP(Stage primaryStage) {
        Title2 titleI = new Title2("Einstellungen");
        titleI.setTranslateX(150);
        titleI.setTranslateY(20);

        Back back = new Back(primaryStage);
        back.setTranslateX(480);
        back.setTranslateY(20);

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(titleI, back);
    }
}

class SettingsTable extends StackPane {

    public static Hashtable<String, String> controlsp1;
    public static Hashtable<String, String> controlsp2;
    String buttonPressed;
    ButtonItem buttonPressedButton;
    String buttonOfPlayer;
    ButtonItem[] focusedButtonItems;
    static TextField tes;

    public SettingsTable(Stage primaryStage) {
        //load the keySettings of each player in the related Hashtable "controlsp1" or "controlsp2"
        try {
            controlsp1 = JsonHandler.fromJson("player1", JsonHandler.KeyOfHashMap.ACTION);
            controlsp2 = JsonHandler.fromJson("player2", JsonHandler.KeyOfHashMap.ACTION);
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        // Settings Topics
        SettingsTopic moveSetTitle = new SettingsTopic("AKTIONEN");
        moveSetTitle.setTranslateX(215);
        moveSetTitle.setTranslateY(220);

        SettingsTopic player1Title = new SettingsTopic("CYBER-KAMMERJAEGER");
        player1Title.setTranslateX(440);
        player1Title.setTranslateY(220);

        SettingsTopic player2Title = new SettingsTopic("EXMATRIKULATOR");
        player2Title.setTranslateX(880);
        player2Title.setTranslateY(220);

        // Settings Elements
        TextBox moveSet = new TextBox(
                new TextItem("Springen"),
                new TextItem("Links"),
                new TextItem("Rechts"),
                new TextItem("Attacke"),
                new TextItem("Spezial-Attacke"));
        moveSet.setTranslateX(100);
        moveSet.setTranslateY(250);

        Button saveButton = new Button();
        saveButton.setText("Speichern");
        saveButton.setTranslateX(620);
        saveButton.setTranslateY(0);

        //create a ButtonItem for player1 with the related values
        ButtonItem b1 = new ButtonItem(controlsp1.get("jump"), primaryStage);
        ButtonItem b2 = new ButtonItem(controlsp1.get("left"), primaryStage);
        ButtonItem b3 = new ButtonItem(controlsp1.get("right"), primaryStage);
        ButtonItem b4 =  new ButtonItem(controlsp1.get("attack"), primaryStage);
        ButtonItem b5 = new ButtonItem(controlsp1.get("specialAttack"), primaryStage);

        //Event Handler to know which button is clicked
        b1.setOnMousePressed(event-> setButtonValues("jump","player1",b1));
        b2.setOnMousePressed(event-> setButtonValues("left","player1",b2));
        b3.setOnMousePressed(event-> setButtonValues("right","player1",b3));
        b4.setOnMousePressed(event-> setButtonValues("attack","player1",b4));
        b5.setOnMousePressed(event-> setButtonValues("specialAttack","player1",b5));

        ButtonBox player1Box = new ButtonBox(
                b1, // up
                b2, // left
                b3, // right
                b4, // Attack 1
                b5); // Attack 2
        player1Box.setTranslateX(450);
        player1Box.setTranslateY(250);

        //equivalent to player1 above
        ButtonItem b6 = new ButtonItem(controlsp2.get("jump"), primaryStage);
        ButtonItem b7 = new ButtonItem(controlsp2.get("left"), primaryStage);
        ButtonItem b8 = new ButtonItem(controlsp2.get("right"), primaryStage);
        ButtonItem b9 =  new ButtonItem(controlsp2.get("attack"), primaryStage);
        ButtonItem b10 = new ButtonItem(controlsp2.get("specialAttack"), primaryStage);

        b6.setOnMousePressed(event-> setButtonValues("jump","player2",b6));
        b7.setOnMousePressed(event-> setButtonValues("left","player2",b7));
        b8.setOnMousePressed(event-> setButtonValues("right","player2",b8));
        b9.setOnMousePressed(event-> setButtonValues("attack","player2",b9));
        b10.setOnMousePressed(event-> setButtonValues("specialAttack","player2",b10));

        focusedButtonItems= new ButtonItem[]{b1, b2,b3,b4,b5,b6,b7,b8,b9,b10};

        ButtonBox player2Box = new ButtonBox(
                b6, // up
                b7, // left
                b8, // right
                b9, // Attack 1
                b10); // Attack 2
        player2Box.setTranslateX((840));
        player2Box.setTranslateY(250);
        createSeperator();

        tes = new TextField();
        tes.setTranslateX(450);
        tes.setMaxWidth(260);
        tes.setPromptText("Gebe eine Taste ein. (z.B.: 'UP', 'RIGHT', 's', 'A')");
        //on enter save the string (as uppercase) entered into the input field "tes" into the keySettings.json
        tes.setOnAction(actionEvent-> saveInput());

        //on click save the string (as uppercase) entered into the input field "tes" into the keySettings.json
        saveButton.setOnMouseClicked(event-> saveInput());

        getChildren().addAll(moveSetTitle, player1Title, player2Title, moveSet, player1Box, player2Box, tes, saveButton);

    }

    //save the string (as uppercase) entered into the input field "tes" into the keySettings.json
    void saveInput(){
        if (Objects.equals(buttonOfPlayer, "player1")){
            controlsp1.put(buttonPressed,tes.getCharacters().toString().toUpperCase());
        }else if(Objects.equals(buttonOfPlayer, "player2")){
            controlsp2.put(buttonPressed,tes.getCharacters().toString().toUpperCase());
        }
        try {
            JsonHandler.toJson(controlsp1,controlsp2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //remove the text inside the input field
        tes.setText(null);
    }

    //set the button values (which button is clicked) and focus
    public void setButtonValues(String btn, String player, ButtonItem buttonItem){
        buttonPressed=btn;
        buttonPressedButton=buttonItem;
        buttonOfPlayer=player;
        setButtonFocus(buttonItem);
    }

    //setting the current clicked ButtonItem to focused
    public void setButtonFocus(ButtonItem buttonItem){
        for (ButtonItem button:focusedButtonItems) {
            button.setStyle("");
        }
        buttonItem.setStyle("-fx-background-color: #00ff00");
        tes.requestFocus();
    }

    public static HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(50, 15);
        return sep;
    }
}

class SettingsTopic extends HBox {
    public SettingsTopic(String name) {
        Label playerTitle = new Label(name);
        playerTitle.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 32));

        getChildren().addAll(playerTitle);
    }

}

class TextBox extends VBox {
    public TextBox(TextItem... items) {
        getChildren().add(createSeperator());

        for (TextItem item : items) {
            getChildren().addAll(item, createSeperator());
        }
    }

    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(50, 15);
        return sep;
    }

}

class TextItem extends StackPane {
    public TextItem(String name) {

        Rectangle bg = new Rectangle(200, 50);
        bg.setFill(Color.DARKRED);
        bg.setOpacity(0.7);

        Text text = new Text(name);
        text.setFill(Color.WHITESMOKE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);
    }
}

class ButtonBox extends VBox {
    public ButtonBox(ButtonItem... items) {
        getChildren().add(SettingsTable.createSeperator());

        for (ButtonItem item : items) {
            getChildren().addAll(item, SettingsTable.createSeperator());
        }

    }
}

class ButtonItem extends StackPane {

    public ButtonItem(String name, Stage primaryStage) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.YELLOW),
                new Stop(0.1, Color.RED),
                new Stop(0.9, Color.RED),
                new Stop(1, Color.DARKBLUE));

        Rectangle bg = new Rectangle(200, 50);
        bg.setFill(Color.DARKRED);
        bg.setOpacity(0.7);

        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);
        setOnMouseEntered(event -> {
            bg.setFill(gradient);
            text.setFill(Color.WHITE);
        });

        setOnMouseExited(event -> {
            bg.setFill(Color.DARKRED);
            text.setFill(Color.DARKGREY);
        });

        setOnMousePressed(event -> {
            bg.setFill(Color.DARKGOLDENROD);
            primaryStage.setMaxHeight(Integer.MAX_VALUE);
            primaryStage.setMaxWidth(Integer.MAX_VALUE);
        });

        setOnMouseReleased(event -> bg.setFill(gradient));
    }
}

class BackS extends StackPane {
    public BackS(Stage primaryStage) {
        final Circle circle = new Circle(10, 42, 42);
        final Rectangle r1 = new Rectangle(10, 50);
        final Rectangle r2 = new Rectangle(10, 50);
        r1.setRotate(45);
        r2.setRotate(315);
        circle.setFill(Color.DARKRED);
        circle.setStroke(Color.BLACK);
        circle.setOnMouseEntered(event -> circle.setFill(Color.RED));
        circle.setOnMouseExited(event -> circle.setFill(Color.DARKRED));
        circle.setOnMousePressed(event -> {
            circle.setFill(Color.YELLOW);
            Scene scene = null;
            // speicher Funktion einbauen 1/3
            try {
                scene = new Scene(createTitleContent(primaryStage), Color.LIGHTBLUE);
                primaryStage.setMaxHeight(Integer.MAX_VALUE);
                primaryStage.setMaxWidth(Integer.MAX_VALUE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(scene);
        });
        r1.setOnMouseEntered(event -> circle.setFill(Color.RED));
        r1.setOnMouseExited(event -> circle.setFill(Color.DARKRED));
        r1.setOnMousePressed(event -> {
            circle.setFill(Color.YELLOW);
            Scene scene = null;
            // speicher Funktion einbauen 2/3
            try {
                scene = new Scene(createTitleContent(primaryStage), Color.LIGHTBLUE);
                primaryStage.setMaxHeight(Integer.MAX_VALUE);
                primaryStage.setMaxWidth(Integer.MAX_VALUE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(scene);
        });
        r2.setOnMouseEntered(event -> circle.setFill(Color.RED));
        r2.setOnMouseExited(event -> circle.setFill(Color.DARKRED));
        r2.setOnMousePressed(event -> {
            circle.setFill(Color.YELLOW);
            Scene scene = null;
            // speicher Funktion einbauen 3/3
            try {
                scene = new Scene(createTitleContent(primaryStage), Color.LIGHTBLUE);
                primaryStage.setMaxHeight(Integer.MAX_VALUE);
                primaryStage.setMaxWidth(Integer.MAX_VALUE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            primaryStage.setScene(scene);
        });

        setAlignment(Pos.CENTER);
        getChildren().addAll(circle, r1, r2);

    }
}