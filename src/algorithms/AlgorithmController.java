package algorithms;

import Patient.Patient;
import Stats.Histograms.HistogramsController;
import algorithms.apriori.Apriori;
import algorithms.clarans.Clarans;
import algorithms.discretization.Discretization;
import algorithms.kmeans.Kmeans;
import algorithms.kmedoid.Kmedoid;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AlgorithmController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private Pane algo_pane_results;


    public ObservableList<Patient> patients;

    ArrayList<Region> garbage;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        garbage = new ArrayList<>();
    }

    public void discretization() {

        boolean canProceed = true;
        if (patients == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun dataset sélectionné");
            alert.showAndWait();
            canProceed = false;
        }
        if (canProceed)
            try {
                FXMLLoader discretizationLoader = new FXMLLoader(Discretization.class.getResource("discretization.fxml"));
                Parent discretizationRoot = discretizationLoader.load();
                Discretization discretization = discretizationLoader.getController();
                discretization.patients = patients;
                this.algo_pane_results.getChildren().removeAll(garbage);
                this.algo_pane_results.getChildren().add(discretization.getPane());
                garbage.add(discretization.getPane());
                discretization.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void apriori() {
        try {
            FXMLLoader aprioriLoader = new FXMLLoader(Apriori.class.getResource("apriori.fxml"));
            Parent aprioriRoot = aprioriLoader.load();
            Apriori apriori = aprioriLoader.getController();
            this.algo_pane_results.getChildren().removeAll(garbage);
            this.algo_pane_results.getChildren().add(apriori.getPane());
            garbage.add(apriori.getPane());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void kmeans() {

        boolean canProceed = true;
        if (patients == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun dataset sélectionné");
            alert.showAndWait();
            canProceed = false;
        }
        if (canProceed)
            try {
                FXMLLoader kmeansLoader = new FXMLLoader(Kmeans.class.getResource("kmeans.fxml"));
                kmeansLoader.load();
                Kmeans kmeans = kmeansLoader.getController();
                kmeans.patients = patients;
                this.algo_pane_results.getChildren().removeAll(garbage);
                this.algo_pane_results.getChildren().add(kmeans.getPane());
                garbage.add(kmeans.getPane());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }



    public void kmedoid() {

        boolean canProceed = true;
        if (patients == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun dataset sélectionné");
            alert.showAndWait();
            canProceed = false;
        }
        if (canProceed)
            try {
                FXMLLoader kmedoidLoader = new FXMLLoader(Kmedoid.class.getResource("kmedoid.fxml"));
                kmedoidLoader.load();
                Kmedoid kmedoid = kmedoidLoader.getController();
                kmedoid.patients = patients;
                this.algo_pane_results.getChildren().removeAll(garbage);
                this.algo_pane_results.getChildren().add(kmedoid.getPane());
                garbage.add(kmedoid.getPane());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }



    public void clarans() {
        boolean canProceed = true;
        if (patients == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun dataset sélectionné");
            alert.showAndWait();
            canProceed = false;
        }
        if (canProceed)
            try {
                FXMLLoader claransLoader = new FXMLLoader(Clarans.class.getResource("clarans.fxml"));
                Parent claransRoot = claransLoader.load();
                Clarans clarans = claransLoader.getController();
                clarans.patients = patients;
                this.algo_pane_results.getChildren().removeAll(garbage);
                this.algo_pane_results.getChildren().add(clarans.getPane());
                garbage.add(clarans.getPane());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }


}
