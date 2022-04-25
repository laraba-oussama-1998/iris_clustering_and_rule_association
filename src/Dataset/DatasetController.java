package Dataset;
import Patient.Patient;
import Stats.AvMedMod.AverageModeMedianController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatasetController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private TableView<Patient> table;
    @FXML
    private TableColumn<Patient, Integer> target;
    @FXML
    private TableColumn<Patient, Double> attribute_1;
    @FXML
    private TableColumn<Patient, Double> attribute_2;
    @FXML
    private TableColumn<Patient, Double> attribute_3;
    @FXML
    private TableColumn<Patient, Double> attribute_4;
    @FXML
    private TableColumn<Patient, Double> attribute_5;

    private AverageModeMedianController statController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.target.setCellValueFactory(new PropertyValueFactory<>("target"));
        this.attribute_1.setCellValueFactory(new PropertyValueFactory<>("attribute_1"));
        this.attribute_2.setCellValueFactory(new PropertyValueFactory<>("attribute_2"));
        this.attribute_3.setCellValueFactory(new PropertyValueFactory<>("attribute_3"));
        this.attribute_4.setCellValueFactory(new PropertyValueFactory<>("attribute_4"));
        this.attribute_5.setCellValueFactory(new PropertyValueFactory<>("attribute_5"));
    }

    public ObservableList<Patient> importData() {
        String line;
        Matcher matcher;
        Patient patient;
        Pattern pattern = Pattern.compile("(.*?),(.*?),(.*?),(.*?),(.*?),(.*?)");
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setTitle("Open Resource File");
        File filechooser = fileChooser.showOpenDialog(new Stage());
        boolean canProcess = true;

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(new File(filechooser.getAbsolutePath())));
            while ((line = buffer.readLine()) != null) {
                matcher = pattern.matcher(line);
                matcher.matches();
                patients.add(new Patient(
                        Integer.parseInt(matcher.group(1)),
                        Double.parseDouble(matcher.group(2)),
                        Double.parseDouble(matcher.group(3)),
                        Double.parseDouble(matcher.group(4)),
                        Double.parseDouble(matcher.group(5)),
                        Double.parseDouble(matcher.group(6))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Processus terminé");
            alert.setHeaderText("Veuillez sélectionner un dataset correct");
            alert.showAndWait();
            canProcess = false;
        }

        if(canProcess) {
            this.table.setItems(patients);
            this.table.setDisable(false);
            this.table.setOpacity(1);
            return patients;
        }
        return null;
    }


    public TableView<Patient> getTable() {
        return table;
    }

    public void setTable(TableView<Patient> table) {
        this.table = table;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

}
