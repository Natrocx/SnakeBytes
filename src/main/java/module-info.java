module de.dhbwmannheim.snakebytes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.jetbrains.annotations;

    opens de.dhbwmannheim.snakebytes to javafx.fxml;
    exports de.dhbwmannheim.snakebytes;
}