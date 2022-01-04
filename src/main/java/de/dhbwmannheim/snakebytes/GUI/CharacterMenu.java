package de.dhbwmannheim.snakebytes.GUI;

import de.dhbwmannheim.snakebytes.ECS.Base.Engine;
import de.dhbwmannheim.snakebytes.EngineLoop;
import de.dhbwmannheim.snakebytes.Render.FrameHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import static de.dhbwmannheim.snakebytes.GUI.Menus.createTitleContent;

/**
 * Authors: @Kai Schwab
 *          @Kirolis Eskondis
 * This class builds the character selection menu
 **/

public class CharacterMenu extends StackPane {
    public static int rounds = 3;
    public static int time =  300 ;
    public static boolean render = false;
    public CharacterMenu(Stage primaryStage){
        //title
        Title2 title = new Title2("Wähle deinen Charakter");
        title.setTranslateY(-250);
        title.setTranslateX((1350.0 / 2.0) - 340);
        //Side Menu1
        SideMenu1 sideMenu1 = new SideMenu1(primaryStage);

        sideMenu1.setTranslateX(-200);
        sideMenu1.setTranslateY(278);

        //Player Select
        CharakterSelect charakterSelect = new CharakterSelect(primaryStage);
        charakterSelect.setTranslateX(325);
        charakterSelect.setTranslateY(187);


        //Side Menu2
        SideMenu2 sideMenu2 = new SideMenu2(primaryStage);

        sideMenu2.setTranslateX(858);
        sideMenu2.setTranslateY(90);

        setAlignment(Pos.CENTER);
        getChildren().addAll(title,sideMenu1,charakterSelect,sideMenu2);
    }
}

class Title2 extends StackPane {
    public Title2(String name) {

        Text text = new Text(name);
        text.setFill(Color.DARKRED);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));

        setAlignment(Pos.CENTER);
        getChildren().addAll(text);
    }
}

class SideMenu1 extends VBox {
    public SideMenu1( Stage primaryStage){
        SideMenuItem left1 = new SideMenuItem("Tunier", primaryStage);
        SideMenuValueSelect left2 = new SideMenuValueSelect("Punkte",String.valueOf(CharacterMenu.rounds));
        getChildren().addAll(left1,createSeperator(),left2);

    }

    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(300,100);
        return sep;
    }
}

class CharakterSelect extends VBox {
    public CharakterSelect(Stage primaryStage){
        CharakterSelect1 csl1 = new CharakterSelect1();
        CharakterSelect2 csl2 = new CharakterSelect2();
        CharakterSelect3 csl3 = new CharakterSelect3(primaryStage);

        getChildren().addAll(csl1,csl2,createSeperator(),csl3);

    }

    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(160,10);
        return sep;
    }
}

class CharakterSelect1 extends HBox {
    public CharakterSelect1(){
        Image cha1 = new Image(getClass().getResource("/char_models/kammerjaeger.png").toString());
        ImageView imgC1 = new ImageView(cha1);
        imgC1.setFitWidth(250);
        imgC1.setFitHeight(500);
        Title2 vs = new Title2("    VS    ");

        Image cha2 = new Image(getClass().getResource("/char_models/exmatrikulator.png").toString());
        ImageView imgC2 = new ImageView(cha2);
        imgC2.setFitWidth(250);
        imgC2.setFitHeight(500);

        setAlignment(Pos.CENTER);
        getChildren().addAll(imgC1,vs,imgC2);

    }
}

class CharakterSelect2 extends HBox {
    public CharakterSelect2(){
        Text name1 = new Text("Cyber-Kammerjäger");
        Text ws = new Text("                 ");
        Text name2 = new Text("Der Exmatrikulator");

        name1.setFill(Color.DARKRED);
        name1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,25));
        ws.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,50));
        name2.setFill(Color.DARKRED);
        name2.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,25));

        setAlignment(Pos.CENTER);
        getChildren().addAll(name1,ws,name2);

    }
}

class CharakterSelect3 extends HBox {
    public CharakterSelect3(Stage primaryStage) {
        final Circle p1 = new Circle(10, 42, 42);
        final Circle p2 = new Circle(10, 42, 42);
        SideMenuItem start = new SideMenuItem("Start", primaryStage);
        Title2 pn1 = new Title2("P1");
        Title2 pn2 = new Title2("P2");
        Text ws = new Text("    ");
        Text blank1 = new Text("  ");
        Text blank2 = new Text("      ");
        pn1.setTranslateX(-65);
        pn2.setTranslateX(-65);

        ws.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,50));
        blank1.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,50));
        blank2.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,50));
        p1.setFill(Color.YELLOW);
        p2.setFill(Color.BLUE);

        setAlignment(Pos.CENTER);
        getChildren().addAll(ws,p1,pn1,blank1,start,blank2,p2,pn2);
    }
}

class SideMenu2 extends VBox{
    public SideMenu2( Stage primaryStage){
        Back back =new Back(primaryStage);
        SideMenuValueSelect right1 = new SideMenuValueSelect("Zeit",String.valueOf(CharacterMenu.time));
        getChildren().addAll(back,createSeperator(),createSeperator(),right1);

    }

    private HBox createSeperator() {
        HBox sep = new HBox();
        sep.setPrefSize(300,100);
        return sep;
    }
}

class SideMenuItem extends StackPane {

    public SideMenuItem(String name, Stage primaryStage) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.YELLOW),
                new Stop(0.1, Color.RED),
                new Stop(0.9, Color.RED),
                new Stop(1, Color.DARKBLUE));

        Rectangle bg = new Rectangle(200, 60);
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(2);
        bg.setFill(Color.DARKRED);
        bg.setOpacity(0.7);

        Text text = new Text(name);
        text.setFill(Color.BLACK);
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
                    Scene scene = null;
                    bg.setFill(Color.DARKGOLDENROD);
                    if (Objects.equals(name, "Start")) {
                        JsonHandler.saveDefaultJson();
                        Engine.setup();
                        FrameHandler frameHandler = new FrameHandler(primaryStage, Engine.getKeyPressedCallback());

                        startRender(frameHandler, primaryStage);

                        EngineLoop loop = new EngineLoop();
                        loop.start();
                    }
                    if (Objects.equals(name, "Erklärungen")) {
                        try {
                            scene = new Scene(Menus.createImpressumContent(primaryStage), Color.LIGHTBLUE);
                            primaryStage.setMaxHeight(Integer.MAX_VALUE);
                            primaryStage.setMaxWidth(Integer.MAX_VALUE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        primaryStage.setScene(scene);
                    }
                    if (Objects.equals(name, "Weiter")) {
                        try {
                            scene = new Scene(Menus.createCharakterContent(primaryStage), Color.LIGHTBLUE);
                            primaryStage.setMaxHeight(Integer.MAX_VALUE);
                            primaryStage.setMaxWidth(Integer.MAX_VALUE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        primaryStage.setScene(scene);
                    }
                });
            setOnMouseReleased(event -> bg.setFill(gradient));
    }

    public void startRender(FrameHandler frameHandler, Stage primaryStage){
        CharacterMenu.render = true;
        new Thread(() -> {
            while(CharacterMenu.render){
                frameHandler.update(primaryStage);
                try {
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
}

class SideMenuValueSelect extends VBox {

    public SideMenuValueSelect(String name, String Value) {
        SideMenuTopic topic = new SideMenuTopic(name);
        SideMenuValueChanger value = new SideMenuValueChanger(Value,name);
        getChildren().addAll(topic,value);


    }
}
class SideMenuTopic  extends StackPane {
    public SideMenuTopic(String name) {
        Rectangle bg = new Rectangle(200, 60);
        bg.setStroke(Color.DARKRED);
        bg.setStrokeWidth(2);
        bg.setFill(null);

        Text text = new Text(name);
        text.setFill(Color.DARKRED);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg,text);

    }
}

class SideMenuValueChanger  extends HBox {
    public SideMenuValueChanger(String value,String name) {
        Polygon l = new Polygon(0, 15, 27, 30, 27, 0);
        l.setFill(Color.DARKRED);
        Text placeholder = new Text("    ");
        Text ph = new Text("    ");
        Text text =new Text(value);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        Polygon h = new Polygon(0, 0, 0, 30, 27, 15);
        h.setFill(Color.DARKRED);

        l.setOnMouseEntered(event -> l.setFill(Color.RED));
        l.setOnMouseExited(event -> l.setFill(Color.DARKRED));
        l.setOnMousePressed(event -> {
            l.setFill(Color.YELLOW);
            int v= 0;
            if(Objects.equals(name, "Punkte")){
                v=CharacterMenu.rounds;
                if(v>1){
                    CharacterMenu.rounds--;
                    text.setText(String.valueOf(CharacterMenu.rounds));
                }
            }
            else if (Objects.equals(name, "Zeit")){
                v=CharacterMenu.time;
                if(v>10){
                    CharacterMenu.time-=10;
                    text.setText(String.valueOf(CharacterMenu.time));
                }
            }
        });
        l.setOnMouseReleased(event -> l.setFill(Color.RED));

        h.setOnMouseEntered(event -> h.setFill(Color.RED));
        h.setOnMouseExited(event -> h.setFill(Color.DARKRED));
        h.setOnMousePressed(event -> {
            h.setFill(Color.YELLOW);
            int v= 0;
            if(Objects.equals(name, "Punkte")){
                v=CharacterMenu.rounds;
                if(v<99){
                    CharacterMenu.rounds++;
                    text.setText(String.valueOf(CharacterMenu.rounds));
                }
            }
            else if (Objects.equals(name, "Zeit")){
                v=CharacterMenu.time;
                if(v<990){
                    CharacterMenu.time+=10;
                    text.setText(String.valueOf(CharacterMenu.time));
                }
            }
        });
        h.setOnMouseReleased(event -> h.setFill(Color.RED));


        setAlignment(Pos.CENTER);
        getChildren().addAll(l,placeholder,text,ph,h);
    }
}

class  Back extends StackPane {
    public Back(Stage primaryStage) {
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
        getChildren().addAll(circle, r1,r2);

    }
}
