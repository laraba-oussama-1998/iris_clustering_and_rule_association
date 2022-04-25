package algorithms.apriori;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Apriori implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private Text time;

    @FXML
    private JFXTextArea results;

    @FXML
    private JFXTextArea dataset;

    @FXML
    private JFXTextField support;

    @FXML
    private JFXTextField confidence;

    private ArrayList<String> data = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.results.setEditable(false);
        this.dataset.setEditable(false);
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }


    public void importDataset() {

        data = new ArrayList<>();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setTitle("Open Resource File");
        File filechooser = fileChooser.showOpenDialog(new Stage());
        String line;

        if(filechooser != null) {
            dataset.clear();
            try {
                BufferedReader buffer = new BufferedReader(new FileReader(new File(filechooser.getAbsolutePath())));
                while ((line = buffer.readLine()) != null) {
                    dataset.appendText(line + "\n");
                    data.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void start() {

        int sup = 0;
        double conf = 0;
        boolean canProceed = true;

        // Error in support field
        try {
            sup = Integer.parseInt(support.getText());
            if (sup <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le champ support doit être un entier positif");
            alert.showAndWait();
            canProceed = false;
        }
        //

        // Error in confidence field
        try {
            conf = Double.parseDouble(confidence.getText());
            if (conf < 0 || conf > 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le champ confiance doit être entre 0 et 1");
            alert.showAndWait();
            canProceed = false;
        }
        //

        // No dataset imported
        if (data == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun dataset sélectionné");
            alert.showAndWait();
            canProceed = false;
        }
        //

        if (canProceed) {
            long start = System.currentTimeMillis();
            runApriori(sup, conf);
            long end = System.currentTimeMillis();
            time.setText(Long.toString(end-start) + "ms");
        }

    }

    public void runApriori(int sup, double conf) {

        // Clear areas
        results.clear();
        //


        // Get frequent Itemsets
        HashMap<String, Integer> frequentItemsets = getFrequentItemsets(sup);
        //

        if(frequentItemsets == null)
            results.appendText("Aucun itemset fréquent trouvé.\n");
        else
            results.appendText("Les itemsets fréquents : \n");


        for(String frequentItemset : frequentItemsets.keySet())
            results.appendText("{" + frequentItemset + "}\n");


        results.appendText("\n" + "les règles d'associations intéressantes sont : \n");


        // Get association rules
        for (String key : frequentItemsets.keySet()) {

            // Get combination
            ArrayList<String> memberRules = getMembersRules(key);

            // Get rules
            for (String leftSide : memberRules) {

                ArrayList<String> leftSideAsList = new ArrayList<>(Arrays.asList(leftSide.split(",")));
                ArrayList<String> l = new ArrayList<>(Arrays.asList(key.split(",")));
                l.removeAll(leftSideAsList);
                double confi = getConfidence(leftSideAsList, l);
                if (confi >= conf)
                    results.appendText("{" + leftSideAsList.toString().replace("[", "").replace("]",
                            "") + "} => {" + l.toString().replace("[", "").replace("]",
                            "") + "} : " + Math.floor(confi * 100) + "%\n");
            }
            break;
        }
    }

    public HashMap<String, Integer> getFrequentItemsets(int min_support) {

        HashMap<String, Integer> L1 = new HashMap<String, Integer>();
        HashMap<String, Integer> frequents = null;
        BufferedReader buffer = null;

        // Build L1
        for (String line : data) {
            String[] items = line.split(",");

            // Capturing singleton items and their support from the dataset
            for (String item : items)
                if (L1.containsKey(item))
                    L1.put(item, L1.get(item) + 1);
                else
                    L1.put(item, 1);
            //

        }

        // Remove non frequent itemsets from L1
        L1.entrySet().removeIf(entry -> min_support > entry.getValue());


        // Build next combinations from L1
        int K = 2;
        /**
         * K is the current itemsets size to consider on each iteration of the following loop
         * */
        while (L1.size() != 0) {

            HashMap<String, Integer> combination = new HashMap<String, Integer>();

            // Build Lk combination
            for (String first_key : L1.keySet()) {
                for (String second_key : L1.keySet()) {
                    if (!first_key.equals(second_key)) {
                        ArrayList<String> first_element = new ArrayList<>(Arrays.asList(first_key.split(",")));
                        ArrayList<String> second_element = new ArrayList<>(Arrays.asList(second_key.split(",")));

                        for (String sec : second_element)
                            if (!first_element.contains(sec))
                                first_element.add(sec);

                        Collections.sort(first_element);

                        if (first_element.size() == K) {
                            String key = String.join(",", first_element);
                            if (!combination.containsKey(key))
                                combination.put(String.join(",", first_element), 0);
                        }
                    }
                }
            }
            //

            // Get support for each new itemsets
            for (String line : data) {
                ArrayList<String> transaction = new ArrayList<>(Arrays.asList(line.split(",")));
                for (String first_key : combination.keySet()) {
                    ArrayList<String> items = new ArrayList<>(Arrays.asList(first_key.split(",")));
                    if (transaction.containsAll(items))
                        combination.put(first_key, combination.get(first_key) + 1);
                }

            }
            //

            // Remove non frequent itemsets
            combination.entrySet().removeIf(entry -> min_support > entry.getValue());
            L1 = new HashMap<>(combination);
            if (!L1.isEmpty())
                frequents = new HashMap<>(L1);
            K++;
            //
            L1 = new HashMap<>(combination);
        }
        return frequents;
    }

    public static ArrayList<String> getMembersRules(String itemset) {

        ArrayList<String> working_list = new ArrayList<>(Arrays.asList(itemset.split(",")));
        ArrayList<String> list = new ArrayList<>(working_list);
        ArrayList<String> rules_members = null;
        int size = 1;

        while (size < working_list.size()) {
            rules_members = new ArrayList<>();
            for (String first : list) {
                for (String second : list) {

                    ArrayList<String> first_element = new ArrayList<>(Arrays.asList(first.split(",")));
                    ArrayList<String> second_element = new ArrayList<>(Arrays.asList(second.split(",")));

                    for (String sec : second_element)
                        if (!first_element.contains(sec))
                            first_element.add(sec);

                    Collections.sort(first_element);
                    String rule_member = String.join(",", first_element);
                    if (!rules_members.contains(rule_member) && first_element.size() < working_list.size())
                        rules_members.add(String.join(",", first_element));
                }
            }
            size++;
            list = new ArrayList<>(rules_members);
        }
        return rules_members;
    }

    public static double getConfidence(ArrayList<String> left, ArrayList<String> right) {

        BufferedReader buffer = null;
        String line;

        ArrayList<String> concat = new ArrayList<>();
        concat.addAll(left);
        concat.addAll(right);

        double numerator = 0;
        double denominator = 0;


        try {
            buffer = new BufferedReader(new FileReader(new File("discretizedDataset.txt")));
            ArrayList<String> lineAsList = null;
            while ((line = buffer.readLine()) != null) {
                lineAsList = new ArrayList<>(Arrays.asList(line.split(",")));
                if (lineAsList.containsAll(concat))
                    numerator++;
                if (lineAsList.containsAll(left))
                    denominator++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //

        return numerator / denominator;
    }


}
