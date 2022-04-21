package CSC227_Project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {
    @FXML
    protected void fileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
//        if (f != null){
//
//        }
    }
}