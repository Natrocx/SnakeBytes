module de.dhbwmannheim.snakebytes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;

    opens de.dhbwmannheim.snakebytes to javafx.fxml;
    exports de.dhbwmannheim.snakebytes;
}