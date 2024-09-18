module com.lab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lab3 to javafx.fxml;
    exports com.lab3;
}