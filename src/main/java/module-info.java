module CSC227_Project {
    requires javafx.controls;
    requires javafx.fxml;


    opens CSC227_Project to javafx.fxml;
    exports CSC227_Project;
}