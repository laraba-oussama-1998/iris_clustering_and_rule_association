package Stats.Boxplot;

import Patient.Patient;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


public class BoxPlotController {

    @FXML
    private Pane pane;
    @FXML
    private Pane summaryPane;
    @FXML
    private Text min;
    @FXML
    private Text max;
    @FXML
    private Text firstQuartile;
    @FXML
    private Text thirdQuartile;
    @FXML
    private Text interquartileRange;
    @FXML
    private Text outliers;
    @FXML
    private ImageView boxPlotPicture;
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

    public void draw(int attribute) {


        ArrayList<Double> list = new ArrayList<>();

        switch (attribute) {
            case 1:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_1());
                this.boxPlotPicture.setImage(new Image("Stats/Boxplot/t3.jpg"));
                break;
            case 2:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_2());
                this.boxPlotPicture.setImage(new Image("Stats/Boxplot/total_serum_thyroxin.jpg"));
                break;
            case 3:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_3());
                this.boxPlotPicture.setImage(new Image("Stats/Boxplot/total_serum_triiodothyronine.jpg"));
                break;
            case 4:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_4());
                this.boxPlotPicture.setImage(new Image("Stats/Boxplot/basal_thyroid_stimulating_hormone.jpg"));

                break;
            case 5:
                for (Patient patient : patients)
                    list.add(patient.getAttribute_5());
                this.boxPlotPicture.setImage(new Image("Stats/Boxplot/maximal_absolute_difference_of_TSH_value.jpg"));
                break;
        }

        double min = getMin(list);
        double max = getMax(list);
        double firstQuartile = getMedian(getFirstQuartile(list));
        double thirdQuartile = getMedian(getThirdQuartile(list));
        double interquartileRange = getInterquartileRange(firstQuartile, thirdQuartile);
        ArrayList<Double> outliers = getOutliers(list, firstQuartile, thirdQuartile, interquartileRange);

        this.min.setText(new DecimalFormat("##.##").format(min));
        this.max.setText(new DecimalFormat("##.##").format(max));
        this.firstQuartile.setText(new DecimalFormat("##.##").format(firstQuartile));
        this.thirdQuartile.setText(new DecimalFormat("##.##").format(thirdQuartile));
        this.interquartileRange.setText(new DecimalFormat("##.##").format(interquartileRange));
        this.outliers.setText(outliers.toString().replace("[","").replace("]", ""));
        this.summaryPane.setDisable(false);
        this.summaryPane.setOpacity(1);
        updateButtons(attribute);

    }

    public void drawT3() {
        draw(1);
    }

    public void drawTotalSerumThyroxin() {
        draw(2);
    }

    public void drawTotalSerumTriiodothyronine() {
        draw(3);
    }

    public void drawBasalThyroidStimulatingHormone() {
        draw(4);
    }

    public void drawMaximalAbsoluteDifferenceOfTSHValue() {
        draw(5);
    }

    public double getMin(ArrayList<Double> list) {
        Collections.sort(list);
        return list.get(0);
    }

    public double getMax(ArrayList<Double> list) {
        Collections.sort(list);
        return list.get(list.size() - 1);
    }

    public ArrayList<Double> getFirstQuartile(ArrayList<Double> list) {
        Collections.sort(list);
        ArrayList<Double> q1_list = new ArrayList<>();
        for (int i = 0; i < list.size() / 2; i++)
            q1_list.add(list.get(i));
        return q1_list;
    }

    public ArrayList<Double> getThirdQuartile(ArrayList<Double> list) {

        ArrayList<Double> q3_list = new ArrayList<>();
        int i;

        if (list.size() % 2 == 1)
            i = list.size() / 2 + 1;
        else
            i = list.size() / 2;

        while (i < list.size()) {
            q3_list.add(list.get(i));
            i++;
        }
        return q3_list;
    }

    public double getMedian(ArrayList<Double> list) {
        Collections.sort(list);
        if (list.size() % 2 == 1)
            return list.get(list.size() / 2);
        else
            return (list.get(list.size() / 2)) + list.get(list.size() / 2 - 1) / 2.0;
    }

    public double getInterquartileRange(Double q1, Double q3) {
        return q3 - q1;
    }

    public ArrayList<Double> getOutliers(ArrayList<Double> list, double firstQuartile, double thirdQuartile, double interquartileRange) {

        ArrayList<Double> outliers = new ArrayList<>();

        for(Double i : list)
            if(i > thirdQuartile + 1.5 * interquartileRange || i < firstQuartile - 1.5 * interquartileRange)
                outliers.add(i);

        return outliers;
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
