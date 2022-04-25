package Stats;

import Patient.Patient;
import Stats.AvMedMod.AverageModeMedianController;
import Stats.Boxplot.BoxPlotController;
import Stats.Histograms.HistogramsController;
import Stats.Scatters.ScatterController;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Pane charts_pane;

    public ObservableList<Patient> patients;

    ArrayList<Region> garbage;

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        garbage = new ArrayList<>();
    }

    public void medianModeAvg() {
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
                // Loader Average, median, mode Pane
                FXMLLoader statLoader = new FXMLLoader(AverageModeMedianController.class.getResource("average_mode_median.fxml"));
                Parent statRoot = statLoader.load();
                AverageModeMedianController statController = statLoader.getController();
                //
                statController.calculate(patients);
                this.charts_pane.getChildren().removeAll(garbage);
                this.charts_pane.getChildren().add(statController.getPane());
                garbage.add(statController.getPane());
                //
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void histograms() {
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
                // Loader Average, median, mode Pane
                FXMLLoader statLoader = new FXMLLoader(HistogramsController.class.getResource("histograms.fxml"));
                Parent statRoot = statLoader.load();
                HistogramsController statController = statLoader.getController();
                statController.patients = patients;
                //
                this.charts_pane.getChildren().removeAll(garbage);
                this.charts_pane.getChildren().add(statController.getPane());
                garbage.add(statController.getPane());
                //
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void boxPlot() {
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
                // Loader Average, median, mode Pane
                FXMLLoader statLoader = new FXMLLoader(BoxPlotController.class.getResource("boxplot.fxml"));
                Parent statRoot = statLoader.load();
                BoxPlotController statController = statLoader.getController();
                statController.patients = patients;
                //
                this.charts_pane.getChildren().removeAll(garbage);
                this.charts_pane.getChildren().add(statController.getPane());
                garbage.add(statController.getPane());
                //
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void scatter() {
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
                // Loader Average, median, mode Pane
                FXMLLoader statLoader = new FXMLLoader(ScatterController.class.getResource("scatter.fxml"));
                Parent statRoot = statLoader.load();
                ScatterController statController = statLoader.getController();
                statController.patients = patients;
                statController.calculateCoeff(1, 2);
                //
                this.charts_pane.getChildren().removeAll(garbage);
                statController.draw();
                this.charts_pane.getChildren().add(statController.getPane());
                garbage.add(statController.getPane());
                //
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


}
