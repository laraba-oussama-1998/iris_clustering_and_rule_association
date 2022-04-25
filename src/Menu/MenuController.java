package Menu;

import Dataset.DatasetController;
import Description.DescriptionController;
import GUI.GUIController;
import Stats.StatController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Pane menu;
    private GUIController parentController;
    private StatController statController;


    public Pane getMenu() {
        return menu;
    }

    public void setMenu(Pane menu) {
        this.menu = menu;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void stats() {
        this.parentController.showStats();
    }

    public void showDescription() {
        this.parentController.showDescription();
    }

    public void importData() {
        this.parentController.showData();
    }

    public void algo() {
        this.parentController.algo();
    }


    public GUIController getParentController() {
        return parentController;
    }

    public void setParentController(GUIController parentController) {
        this.parentController = parentController;
    }
}
