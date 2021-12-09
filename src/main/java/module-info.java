module de.dhbwmannheim.snakebytes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires json.simple;

    opens de.dhbwmannheim.snakebytes to javafx.fxml;
    exports de.dhbwmannheim.snakebytes;

    exports de.dhbwmannheim.snakebytes.GUI;
    opens de.dhbwmannheim.snakebytes.GUI to javafx.fxml;

    exports de.dhbwmannheim.snakebytes.Sounds;
    opens de.dhbwmannheim.snakebytes.Sounds to javafx.fxml;

}