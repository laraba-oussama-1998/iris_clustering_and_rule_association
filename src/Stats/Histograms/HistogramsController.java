package Stats.Histograms;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import Patient.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class HistogramsController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private ScrollPane scroll;
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXButton t3_button;
    @FXML
    private JFXButton total_serum_thyroxin_button;
    @FXML
    private JFXButton total_serum_triiodothyronine_button;
    @FXML
    private JFXButton basal_thyroid_stimulating_hormone_button;
    @FXML
    private JFXButton maximal_absolute_difference_of_TSH_value_button;


    public ObservableList<Patient> patients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void drawData(int attribute) {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        xAxis.setLabel("Valeurs");
        yAxis.setLabel("");
        ArrayList<Double> data = new ArrayList<>();
        XYChart.Series series = new XYChart.Series();

        switch (attribute) {
            case 1:
                for (Patient patient : patients)
                    data.add(patient.getAttribute_1());
                bc.setTitle("Histogramme de l'attribut T3-resin uptake test");
                series.setName("T3-resin uptake test");
                break;
            case 2:
                for (Patient patient : patients)
                    data.add(patient.getAttribute_2());
                bc.setTitle("Histogramme de l'attribut Total Serum Thyroxin");
                series.setName("Total Serum Thyroxin");
                break;
            case 3:
                for (Patient patient : patients)
                    data.add(patient.getAttribute_3());
                bc.setTitle("Histogramme de l'attribut Total Serum Triiodothyronine");
                series.setName("Total Serum Triiodothyronine");

                break;
            case 4:
                for (Patient patient : patients)
                    data.add(patient.getAttribute_4());
                bc.setTitle("Histogramme de l'attribut Basal Thyroid-Stimulating Hormone");
                series.setName("Basal Thyroid-Stimulating Hormone");

                break;
            case 5:
                for (Patient patient : patients)
                    data.add(patient.getAttribute_5());
                bc.setTitle("Histogramme de l'attribut Maximal absolute difference of TSH value");
                series.setName("Maximal absolute difference of TSH value");

                break;
        }
        updateButtons(attribute);
        List<Double> distinct = data.stream().distinct().collect(Collectors.toList());

        for (Double i : distinct)
            series.getData().add(new XYChart.Data(Double.toString(i), Collections.frequency(data, i)));


        bc.getData().add(series);
        bc.setLayoutX(0);
        bc.setLayoutY(0);
        bc.setMinWidth(2000);
        bc.getStylesheets().add("css.css");
        this.anchor.getChildren().clear();
        this.anchor.getChildren().add(bc);
        this.scroll.setContent(anchor);
    }


    public void t3Histogram() {
        drawData(1);
    }

    public void drawTotalSerumThyroxin() {
        drawData(2);
    }

    public void drawTotalSerumTriiodothyronine() {
        drawData(3);
    }

    public void draw_basal_thyroid_stimulating_hormone() {
        drawData(4);
    }

    public void draw_maximal_absolute_difference_of_TSH_value_button() {
        drawData(5);
    }


    public void updateButtons(int state) {
        switch (state) {
            case 1:
                if (!t3_button.getStyleClass().contains("button-selected"))
                    t3_button.getStyleClass().add("button-selected");
                total_serum_thyroxin_button.getStyleClass().remove("button-selected");
                total_serum_triiodothyronine_button.getStyleClass().remove("button-selected");
                basal_thyroid_stimulating_hormone_button.getStyleClass().remove("button-selected");
                maximal_absolute_difference_of_TSH_value_button.getStyleClass().remove("button-selected");
                break;
            case 2:
                if (!total_serum_thyroxin_button.getStyleClass().contains("button-selected"))
                    total_serum_thyroxin_button.getStyleClass().add("button-selected");
                t3_button.getStyleClass().remove("button-selected");
                total_serum_triiodothyronine_button.getStyleClass().remove("button-selected");
                basal_thyroid_stimulating_hormone_button.getStyleClass().remove("button-selected");
                maximal_absolute_difference_of_TSH_value_button.getStyleClass().remove("button-selected");
                break;
            case 3:
                if (!total_serum_triiodothyronine_button.getStyleClass().contains("button-selected"))
                    total_serum_triiodothyronine_button.getStyleClass().add("button-selected");
                total_serum_thyroxin_button.getStyleClass().remove("button-selected");
                t3_button.getStyleClass().remove("button-selected");
                basal_thyroid_stimulating_hormone_button.getStyleClass().remove("button-selected");
                maximal_absolute_difference_of_TSH_value_button.getStyleClass().remove("button-selected");
                break;
            case 4:
                if (!basal_thyroid_stimulating_hormone_button.getStyleClass().contains("button-selected"))
                    basal_thyroid_stimulating_hormone_button.getStyleClass().add("button-selected");
                total_serum_triiodothyronine_button.getStyleClass().remove("button-selected");
                total_serum_thyroxin_button.getStyleClass().remove("button-selected");
                t3_button.getStyleClass().remove("button-selected");
                maximal_absolute_difference_of_TSH_value_button.getStyleClass().remove("button-selected");
                break;
            case 5:
                if (!maximal_absolute_difference_of_TSH_value_button.getStyleClass().contains("button-selected"))
                    maximal_absolute_difference_of_TSH_value_button.getStyleClass().add("button-selected");
                basal_thyroid_stimulating_hormone_button.getStyleClass().remove("button-selected");
                total_serum_triiodothyronine_button.getStyleClass().remove("button-selected");
                total_serum_thyroxin_button.getStyleClass().remove("button-selected");
                t3_button.getStyleClass().remove("button-selected");
                break;
        }
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }


}
