package Stats.AvMedMod;

import Patient.Patient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class AverageModeMedianController {

    @FXML
    private Pane pane;
    @FXML
    private Text t3_average;
    @FXML
    private Text t3_median;
    @FXML
    private Text t3_mode;
    @FXML
    private Text total_serum_thyroxin_average;
    @FXML
    private Text total_serum_thyroxin_median;
    @FXML
    private Text total_serum_thyroxin_mode;
    @FXML
    private Text total_serum_triiodothyronine_average;
    @FXML
    private Text total_serum_triiodothyronine_median;
    @FXML
    private Text total_serum_triiodothyronine_mode;


    private ObservableList<Patient> patients;

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }


    public void calculate(ObservableList<Patient> patients) {

        ArrayList<Double> T3_resin_uptake_test = new ArrayList<>();
        ArrayList<Double> Total_Serum_thyroxin = new ArrayList<>();
        ArrayList<Double> Total_serum_triiodothyronine = new ArrayList<>();

        for (int i = 0; i < patients.size(); i++) {
            T3_resin_uptake_test.add(patients.get(i).getAttribute_1());
            Total_Serum_thyroxin.add(patients.get(i).getAttribute_2());
            Total_serum_triiodothyronine.add(patients.get(i).getAttribute_3());
        }

        t3_average.setText(new DecimalFormat("##.##").format(average(T3_resin_uptake_test)));
        t3_median.setText(new DecimalFormat("##.##").format(median(T3_resin_uptake_test)));
        t3_mode.setText(new DecimalFormat("##.##").format(mode(T3_resin_uptake_test)));
        total_serum_thyroxin_average.setText(new DecimalFormat("##.##").format(average(Total_Serum_thyroxin)));
        total_serum_thyroxin_median.setText(new DecimalFormat("##.##").format(median(Total_Serum_thyroxin)));
        total_serum_thyroxin_mode.setText(new DecimalFormat("##.##").format(mode(Total_Serum_thyroxin)));
        total_serum_triiodothyronine_average.setText(new DecimalFormat("##.##")
                .format(average(Total_serum_triiodothyronine)));
        total_serum_triiodothyronine_median.setText(new DecimalFormat("##.##")
                .format(median(Total_serum_triiodothyronine)));
        total_serum_triiodothyronine_mode.setText(new DecimalFormat("##.##")
                .format(mode(Total_serum_triiodothyronine)));


    }

    public double average(ArrayList<Double> list) {
        double average = 0;
        for (int i = 0; i < list.size(); i++)
            average += list.get(i);

        return average / list.size();
    }

    public double median(ArrayList<Double> list) {
        Collections.sort(list);
        if (list.size() % 2 == 1)
            return list.get(list.size() / 2);
        else
            return (list.get(list.size() / 2)) + list.get(list.size() / 2 - 1) / 2.0;
    }

    public double mode(ArrayList<Double> list) {
        double max = 0;
        double mode = 0;
        for (double i: list) {
            int frequency = Collections.frequency(list, i);
            if(frequency > max) {
                mode = i;
                max = frequency;
            }
        }
        return mode;
    }


    public ObservableList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
    }


}
