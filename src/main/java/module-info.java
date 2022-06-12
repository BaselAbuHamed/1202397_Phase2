module com.example.ProjectPhase {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.Phase to javafx.fxml;
    exports com.example.Phase;
}