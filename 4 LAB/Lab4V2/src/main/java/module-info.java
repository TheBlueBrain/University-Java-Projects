module com.lab4v2 {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires org.apache.pdfbox;
	requires org.apache.poi.ooxml;

	opens com.lab4v2 to javafx.fxml;
	exports com.lab4v2;
}