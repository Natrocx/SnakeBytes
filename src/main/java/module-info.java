module de.dhbwmannheim.snakebytes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;

    opens de.dhbwmannheim.snakebytes to javafx.fxml;
    exports de.dhbwmannheim.snakebytes;
    exports de.dhbwmannheim.snakebytes.Sounds;
    opens de.dhbwmannheim.snakebytes.Sounds to javafx.fxml;
}