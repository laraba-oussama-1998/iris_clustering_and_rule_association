package GUI;

import Dataset.DatasetController;
import Description.DescriptionController;
import Menu.MenuController;
import Patient.Patient;
import Stats.StatController;
import algorithms.AlgorithmController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    private Stage stage;
    @FXML
    private Pane main_pane;

    // Garbage panes
    ArrayList<Pane> garbage;
    //

    ObservableList<Patient> patients;
    ImageView iv1 = new ImageView(new Image("dm.png"));
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load Menu FXML and Controller
        try {
            FXMLLoader menuLoader = new FXMLLoader(MenuController.class.getResource("menu.fxml"));
            Parent menuRoot = menuLoader.load();
            MenuController menuController = menuLoader.getController();
            menuController.setParentController(this);
            this.main_pane.getChildren().add(menuRoot);

            iv1.setFitHeight(600);
            iv1.setFitWidth(1400);
            iv1.setX(100);
            iv1.setY(30);
            this.main_pane.getChildren().add(iv1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        garbage = new ArrayList<>();

    }

    public void showDescription() {

        try {
            // Load pane
            FXMLLoader descriptionLoader = new FXMLLoader(DescriptionController.class.getResource("description.fxml"));
            Parent descriptionRoot = descriptionLoader.load();
            DescriptionController descriptionController = descriptionLoader.getController();
            //

            // Display description
            this.main_pane.getChildren().removeAll(garbage);
            this.main_pane.getChildren().removeAll(iv1);
            Pane pane = new Pane(descriptionController.getDescription_pane());
            pane.setLayoutX(250);
            pane.setLayoutY(0);
            this.main_pane.getChildren().add(pane);
            garbage.add(pane);
            //
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showData() {

        try {
            // Load pane
            FXMLLoader datasetLoader = new FXMLLoader(DatasetController.class.getResource("dataset.fxml"));
            Parent datasetRoot = datasetLoader.load();
            DatasetController datasetController = datasetLoader.getController();
            //

            // Import data
            patients = datasetController.importData();
            //

            // Display data
            this.main_pane.getChildren().removeAll(garbage);
            this.main_pane.getChildren().removeAll(iv1);
            Pane pane = new Pane(datasetController.getPane());
            pane.setLayoutX(250);
            pane.setLayoutY(0);
            this.main_pane.getChildren().add(pane);
            garbage.add(pane);
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStats() {

        try {
            // Load Stat Pane
            FXMLLoader statLoader = new FXMLLoader(StatController.class.getResource("stats.fxml"));
            Parent statRoot = statLoader.load();
            StatController statController = statLoader.getController();
            statController.patients = patients;
            //

            // Display pane
            this.main_pane.getChildren().removeAll(garbage);
            this.main_pane.getChildren().removeAll(iv1);

            Pane pane = new Pane(statController.getPane());
            pane.setLayoutX(250);
            pane.setLayoutY(0);
            this.main_pane.getChildren().add(pane);
            garbage.add(pane);
            //

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void algo() {

        try {
            FXMLLoader algoLoader = new FXMLLoader(AlgorithmController.class.getResource("algo.fxml"));
            Parent algoRoot = algoLoader.load();
            AlgorithmController algoController = algoLoader.getController();
            algoController.patients = patients;

            // Display pane
            this.main_pane.getChildren().removeAll(garbage);
            this.main_pane.getChildren().removeAll(iv1);
            Pane pane = new Pane(algoController.getPane());
            pane.setLayoutX(250);
            pane.setLayoutY(0);
            this.main_pane.getChildren().add(pane);
            garbage.add(pane);
            //

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
