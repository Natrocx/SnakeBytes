module de.dhbwmannheim.snakebytes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires json.simple;
    requires org.jetbrains.annotations;

    exports de.dhbwmannheim.snakebytes.GUI;
    opens de.dhbwmannheim.snakebytes.GUI to javafx.fxml;

    exports de.dhbwmannheim.snakebytes.Sounds;
    opens de.dhbwmannheim.snakebytes.Sounds to javafx.fxml;

}