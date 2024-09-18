module org.lab6 {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;

	opens org.lab6 to javafx.fxml;
	exports org.lab6;
}