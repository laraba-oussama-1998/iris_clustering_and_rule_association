package Stats.Scatters;

import Patient.*;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javax.swing.plaf.IconUIResource;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScatterController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private ScatterChart chart;
    @FXML
    private JFXComboBox comboX;
    @FXML
    private JFXComboBox comboY;
    @FXML
    private Text corr_coe;

    ArrayList<ScatterChart> garbage;

    public ObservableList<Patient> patients;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        garbage = new ArrayList<>();

        comboX.setItems(FXCollections.observableArrayList(
                "T3-resin uptake test",
                "Total Serum Thyroxin",
                "Total Serum Triiodothyronine",
                "Basal Thyroid-Stimulating Hormone",
                "Maximal absolute difference of TSH value"
        ));
        comboY.setItems(FXCollections.observableArrayList(
                "T3-resin uptake test",
                "Total Serum Thyroxin",
                "Total Serum Triiodothyronine",
                "Basal Thyroid-Stimulating Hormone",
                "Maximal absolute difference of TSH value"
        ));
        comboX.getSelectionModel().selectFirst();
        comboY.getSelectionModel().select(1);
        comboX.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

                    int x = 0, y = 0;

                    switch ((String) newValue) {
                        case "T3-resin uptake test":
                            x = 1;
                            break;
                        case "Total Serum Thyroxin":
                            x = 2;
                            break;
                        case "Total Serum Triiodothyronine":
                            x = 3;
                            break;
                        case "Basal Thyroid-Stimulating Hormone":
                            x = 4;
                            break;
                        case "Maximal absolute difference of TSH value":
                            x = 5;
                            break;
                    }

                    switch ((String) comboY.getSelectionModel().getSelectedItem()) {
                        case "T3-resin uptake test":
                            y = 1;
                            break;
                        case "Total Serum Thyroxin":
                            y = 2;
                            break;
                        case "Total Serum Triiodothyronine":
                            y = 3;
                            break;
                        case "Basal Thyroid-Stimulating Hormone":
                            y = 4;
                            break;
                        case "Maximal absolute difference of TSH value":
                            y = 5;
                            break;
                    }
                    drawScatter(x, y);
                    calculateCoeff(x, y);
                }
        );

        comboY.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                    int x = 0, y = 0;

                    switch ((String) newValue) {
                        case "T3-resin uptake test":
                            y = 1;
                            break;
                        case "Total Serum Thyroxin":
                            y = 2;
                            break;
                        case "Total Serum Triiodothyronine":
                            y = 3;
                            break;
                        case "Basal Thyroid-Stimulating Hormone":
                            y = 4;
                            break;
                        case "Maximal absolute difference of TSH value":
                            y = 5;
                            break;
                    }

                    switch ((String) comboX.getSelectionModel().getSelectedItem()) {
                        case "T3-resin uptake test":
                            x = 1;
                            break;
                        case "Total Serum Thyroxin":
                            x = 2;
                            break;
                        case "Total Serum Triiodothyronine":
                            x = 3;
                            break;
                        case "Basal Thyroid-Stimulating Hormone":
                            x = 4;
                            break;
                        case "Maximal absolute difference of TSH value":
                            x = 5;
                            break;
                    }

                    drawScatter(x, y);
                    calculateCoeff(x, y);
                }
        );
    }


    public void calculateCoeff(int x, int y) {

        ArrayList<Double> listX = getList(x);
        ArrayList<Double> listY = getList(y);
        double coeff = 0;
        double meanX = average(listX);
        double meanY = average(listY);
        double standardX = standardDev(listX, meanX);
        double standardY = standardDev(listY, meanY);

        for (int i = 0; i < listX.size(); i++) {
            double a = (listX.get(i) - meanX) / standardX;
            double b = (listY.get(i) - meanY) / standardY;
            coeff += a * b;
        }
        coeff *= (double) 1 / (listX.size() - 1);
        corr_coe.setText(new DecimalFormat("##.##").format(coeff));

    }

    public double standardDev(ArrayList<Double> list, double mean) {

        double standardDev = 0;
        for (double i : list)
            standardDev += Math.pow(i - mean, 2);

        return Math.sqrt(standardDev / list.size());
    }

    public double average(ArrayList<Double> list) {
        double average = 0;
        for (double i : list)
            average += i;

        return average / (double) list.size();
    }

    public ArrayList<Double> getList(int x) {

        ArrayList<Double> list = new ArrayList<>();
        switch (x) {
            case 1:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_1());
                break;
            case 2:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_2());
                break;
            case 3:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_3());
                break;
            case 4:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_4());
                break;
            case 5:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_5());
                break;

        }
        return list;
    }

    public void draw() {
        drawScatter(1, 2);
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void drawScatter(int x, int y) {

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Graphe de dispersion");

        for (Patient patient : patients) {
            if (x == 1 && y == 2)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_1(), patient.getAttribute_2()));
            if (x == 1 && y == 3)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_1(), patient.getAttribute_3()));
            if (x == 1 && y == 4)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_1(), patient.getAttribute_4()));
            if (x == 1 && y == 5)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_1(), patient.getAttribute_5()));

            if (x == 2 && y == 1)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_2(), patient.getAttribute_1()));
            if (x == 2 && y == 3)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_2(), patient.getAttribute_3()));
            if (x == 2 && y == 4)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_2(), patient.getAttribute_4()));
            if (x == 2 && y == 5)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_2(), patient.getAttribute_5()));

            if (x == 3 && y == 1)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_3(), patient.getAttribute_1()));
            if (x == 3 && y == 2)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_3(), patient.getAttribute_2()));
            if (x == 3 && y == 4)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_3(), patient.getAttribute_4()));
            if (x == 3 && y == 5)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_3(), patient.getAttribute_5()));

            if (x == 4 && y == 1)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_4(), patient.getAttribute_1()));
            if (x == 4 && y == 2)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_4(), patient.getAttribute_2()));
            if (x == 4 && y == 3)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_4(), patient.getAttribute_3()));
            if (x == 4 && y == 5)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_4(), patient.getAttribute_5()));

            if (x == 5 && y == 1)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_5(), patient.getAttribute_1()));
            if (x == 5 && y == 2)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_5(), patient.getAttribute_2()));
            if (x == 5 && y == 3)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_5(), patient.getAttribute_3()));
            if (x == 5 && y == 4)
                dataSeries.getData().add(new XYChart.Data(patient.getAttribute_5(), patient.getAttribute_4()));
        }

        switch (x) {
            case 1:
                xAxis.setLabel("T3-resin uptake test");
                break;
            case 2:
                xAxis.setLabel("Total Serum Thyroxin");
                break;
            case 3:
                xAxis.setLabel("Total Serum Triiodothyronine");
                break;
            case 4:
                xAxis.setLabel("Basal Thyroid-Stimulating Hormone");
                break;
            case 5:
                xAxis.setLabel("Maximal absolute difference of TSH value");
                break;
        }

        switch (y) {
            case 1:
                yAxis.setLabel("T3-resin uptake test");
                break;
            case 2:
                yAxis.setLabel("Total Serum Thyroxin");
                break;
            case 3:
                yAxis.setLabel("Total Serum Triiodothyronine");
                break;
            case 4:
                yAxis.setLabel("Basal Thyroid-Stimulating Hormone");
                break;
            case 5:
                yAxis.setLabel("Maximal absolute difference of TSH value");
                break;
        }

        scatterChart.getData().add(dataSeries);
        scatterChart.setLayoutY(40);
        scatterChart.setPrefHeight(380);
        scatterChart.setPrefWidth(900);
        this.pane.getChildren().removeAll(garbage);
        this.pane.getChildren().add(scatterChart);
        garbage.add(scatterChart);

    }


}
