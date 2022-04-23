module CSC227_Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires jfreechart;
    requires java.desktop;
    requires jcommon;


    opens CSC227_Project to javafx.fxml;
    exports CSC227_Project;
}