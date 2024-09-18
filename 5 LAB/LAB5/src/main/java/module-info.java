module org.lab5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
	requires java.desktop;

	opens org.lab5 to javafx.fxml;
    exports org.lab5;
}