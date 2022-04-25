package algorithms.discretization;

import Patient.Patient;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class Discretization implements Initializable {

    @FXML
    Pane pane;

    @FXML
    JFXTextField numberBins;

    @FXML
    Text time;

    @FXML
    private JFXTextArea area;

    public ObservableList<Patient> patients;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void start() {


        long startTime = System.currentTimeMillis();

        ObservableList<Patient> ObservablePatients = FXCollections.observableArrayList();

        ArrayList<Double> attribute1 = fitAttribute(1);
        ArrayList<Double> attribute2 = fitAttribute(2);
        ArrayList<Double> attribute3 = fitAttribute(3);
        ArrayList<Double> attribute4 = fitAttribute(4);
        ArrayList<Double> attribute5 = fitAttribute(5);

        try {
            FileWriter writer = new FileWriter(new File("discretizedDataset.txt"));
            for (int i = 0; i < patients.size(); i++) {
                String att1 = "attribut-1_"+attribute1.get(i);
                String att2 = "attribut-2_"+attribute2.get(i);
                String att3 = "attribut-3_"+attribute3.get(i);
                String att4 = "attribut-4_"+attribute4.get(i);
                String att5 = "attribut-5_"+attribute5.get(i);
                writer.write(att1+","+att2+","+att3+","+att4+","+att5+"\n");
                area.appendText(att1+","+att2+","+att3+","+att4+","+att5+"\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        time.setText(Long.toString(endTime - startTime) + " ms");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Processus terminé");
        alert.setHeaderText("Le dataset discrétisé est exporté dans le fichier discretizedDataset.txt");
        alert.showAndWait();


    }

    public ArrayList<Double> fitAttribute(int key) {

        ArrayList<Double> attribute = new ArrayList<>();

        switch (key) {
            case 1:
                for (Patient patient : patients)
                    attribute.add(patient.getAttribute_1());
                break;
            case 2:
                for (Patient patient : patients)
                    attribute.add(patient.getAttribute_2());
                break;
            case 3:
                for (Patient patient : patients)
                    attribute.add(patient.getAttribute_3());
                break;
            case 4:
                for (Patient patient : patients)
                    attribute.add(patient.getAttribute_4());
                break;
            case 5:
                for (Patient patient : patients)
                    attribute.add(patient.getAttribute_5());
                break;
        }

        Collections.sort(attribute);

        int numberBins = (int) Math.floor(1 + 3.322 * Math.log10(attribute.size()));
        int intervalSize = (int) (Math.floor((attribute.get(attribute.size() - 1) - attribute.get(0))
                / numberBins)) + 1;

        double current = attribute.get(0);
        int k = 1;
        for (int i = 0; i < attribute.size(); i++) {

            if (attribute.get(i) >= current + intervalSize) {
                k++;
                current += intervalSize;
            }
            attribute.set(i, (double) Math.floor((current + intervalSize) / 2));
        }
        return attribute;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }


}
