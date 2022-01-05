package de.dhbwmannheim.snakebytes.GUI;

import javafx.geometry.Insets;
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
        PyTitle pytit = new PyTitle("Exmatrikulator");
        PyText pytex = new PyText("Angriff:", "Die Special-Fähigkeit des Exmatrikukalors stellt die vielen mitgelieferten Bibliotheken, als eine der stärksten Waffen dar. Ebenso ist Python Code einfach zu lesen und benötigt kein explizites Speichermanagement.");
        PyText pytex2 = new PyText("Geschichte:", "Der Exmatrikulator ist der harte Hammer der Gerechtigkeit für all jene, die während der Vorlesung schlafen oder Agar.io spielen. Seine unnahbare Aura lässt vor allem Erstsemester erschüttern. Sein Arsenal ist ebenso wie die Typen in Python, wandelbar und orientiert sich an den neusten Trends. Es kursieren einige Legenden über den Exmatrikulator, welcher in längst vergangener Zeit mit der Programmiersprache SetlX im Bunde gewesen sein soll. Doch mit dem aufkommen einer neuen Technologie wurde der Exmatrikulator hungrig nach Macht im Bereich der Data Science und wandte sich der Programmiersprache Python zu, die bereits die meisten Funktionen vorab in Form von Bibliotheken bereitstellt. Und so striff der Exmatrikulator seine alten Gewohnheiten ab wie eine Schlangenhaut und hervor stieg das neueste Mitglied des Python-Kults, ein würdiger Gegner im Kampf der Programmiersprachen.");
        CTitle ctit = new CTitle("Cyber-Kammerjäger");
        CText ctex = new CText("Angriff:", "Die Special-Fähigkeit des Kammerjägers stellt einen Pointer dar. Pointer sind ein Markenzeichen von C und sorgen für ein unvergleichlich effektives Speichermanagement. Infolge dessen ist C eine der performantesten Sprachen die es gibt.");
        CText ctex2 = new CText("Geschichte:", "Der Cyber-Kammerjäger ist die Kraft mit der man rechnen muss, wenn man das Semikolon vergisst. Ein antiker Titan, erweckt nur durch den Klang von ineffizient genutztem Systemspeicher. Aus um jene mit seinen geschweiften Klammern und, wie in der Programmiersprache C, unheimlich performanten Methoden zu jagen, die vom rechten Weg abgekommen sind. Der Cyber-Kammerjäger bedient sich dabei den ausgeklügeltsten Methoden und bleibt dabei dem System nahe. Auch bei der Typisierung überlässt der Cyber-Kammerjäger nichts dem Zufallsrad und greift authoritär auf die Hardware zu. Als graue Eminenz, hatte der dem C-Kult angehörige Cyber-Kammerjäger seinen Code beinahe jeder anderen Programmiersprache in Form von Bibliotheken hinzugefügt. Die enorme Hingabe gegenüber C ist eine der größten Stärken des Cyber-Kammerjägers, was ihn zu einem gefährlichen Gegner im Kampf der Programmiersprachen macht.");

        getChildren().addAll(headerI, ws, pytit, pytex, pytex2, ctit, ctex, ctex2);
    }
}

class HeaderI extends HBox {
    public HeaderI(Stage primaryStage) {

        Title2 titleI = new Title2("Impressum und Erklärungen");

        titleI.setTranslateX(150);
        titleI.setTranslateY(20);

        BackS back = new BackS(primaryStage);

        back.setTranslateX(400);
        back.setTranslateY(20);

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(titleI, back);
    }
}

//by Robert Sedlmeier
class PyText extends HBox {
    public PyText(String title, String text) {

        PyTitle titleE = new PyTitle(title);
        PyTextpart textpart = new PyTextpart(text);

        getChildren().addAll(titleE, textpart);
    }
}

class CText extends HBox {
    public CText(String title, String text) {

        CTitle titleE = new CTitle(title);
        CTextpart textpart = new CTextpart(text);

        getChildren().addAll(titleE, textpart);
    }
}

class PyTitle extends StackPane {
    public PyTitle(String name) {

        Text text = new Text(name);

        text.setFill(Color.YELLOW);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        text.setWrappingWidth(250);

        setMargin(text, new Insets(5, 5, 5, 5));
        setAlignment(Pos.CENTER);
        getChildren().addAll(text);
    }
}

class CTitle extends StackPane {
    public CTitle(String name) {

        Text text = new Text(name);

        text.setFill(Color.BLUE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));
        text.setWrappingWidth(250);

        setMargin(text, new Insets(5, 5, 5, 5));
        setAlignment(Pos.CENTER);
        getChildren().addAll(text);
    }
}

class PyTextpart extends StackPane {
    public PyTextpart(String explenation) {

        Text text = new Text(explenation);

        text.setFill(Color.YELLOW);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        text.setWrappingWidth(900);

        setMargin(text, new Insets(5, 5, 5, 5));
        setAlignment(Pos.CENTER);
        getChildren().addAll(text);
    }
}

class CTextpart extends StackPane {
    public CTextpart(String explenation) {

        Text text = new Text(explenation);

        text.setFill(Color.BLUE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        text.setWrappingWidth(900);

        setMargin(text, new Insets(5, 5, 5, 5));
        setAlignment(Pos.CENTER);
        getChildren().addAll(text);
    }
}
