package algorithms.clarans;

import Patient.Patient;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Clarans {

    @FXML
    private Pane pane;
    @FXML
    JFXTextField max_iter_field;
    @FXML
    JFXTextField max_neighbor_field;
    @FXML
    JFXTextField k_field;
    @FXML
    JFXTextArea area;
    @FXML
    private Text time;

    public ObservableList<Patient> patients;

    public void start() {

        int max_iter = 0, max_neighbor = 0, K = 0;
        boolean canProceed = true;

        try {
            max_iter = Integer.parseInt(max_iter_field.getText());
            if (max_iter < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le champ max itération doit être un entier positif");
            alert.showAndWait();
            canProceed = false;
        }

        try {
            max_neighbor = Integer.parseInt(max_neighbor_field.getText());
            if (max_neighbor < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le champ nombre de voisins doit être un entier positif");
            alert.showAndWait();
            canProceed = false;
        }

        try {
            K = Integer.parseInt(k_field.getText());
            if (K < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le champ nombre de cluster doit être un entier positif");
            alert.showAndWait();
            canProceed = false;
        }

        if (canProceed) {
            area.clear();
            long start = System.currentTimeMillis();
            startClarans(max_iter, max_neighbor, K);
            long end = System.currentTimeMillis();
            time.setText(Long.toString(end - start) + "ms");
        }
    }

    public void startClarans(int max_iter, int max_neighbor, int K) {

        // Initialize min cost to infinity
        double minCost = Double.POSITIVE_INFINITY;

        // Initialize best medoids to empty
        ArrayList<ArrayList<Double>> bestMedoids = new ArrayList<>();

        // Get dataset
        ArrayList<ArrayList<Double>> points = getPoints();


        int j = 1, i = 1;

        // Select k random medoids
        ArrayList<ArrayList<Double>> currentMedoids = getRandomMedoids(points, K);

        // Create clusters on those medoids
        HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters = getClusters(points, currentMedoids);

        // Calculate cost
        double cost = getTotalCost(clusters);

        while (i <= max_iter) {

            // Pick random neighbor of currentMedoids
            ArrayList<ArrayList<Double>> neighbor = getNeighbor(currentMedoids, points);

            // Calculate cost on neighbbor
            HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> new_clusters
                    = getClusters(points, neighbor);
            double new_cost = getTotalCost(clusters);

            if (new_cost < cost) {
                currentMedoids = new ArrayList<>(neighbor);
                j = 1;
            } else {
                j++;
                if (j >= max_neighbor) {
                    if (cost < minCost) {
                        minCost = cost;
                        bestMedoids = new ArrayList<>(currentMedoids);
                    }
                    i++;
                    // Generate new node

                    // Select k random medoids
                    currentMedoids = getRandomMedoids(points, K);

                    // Create clusters on those medoids
                    clusters = getClusters(points, currentMedoids);

                    // Calculate cost
                    cost = getTotalCost(clusters);
                }
            }

        }
        area.appendText("Itération " + i + ", Cost Function  = " + getTotalCost(clusters) + "\n");

        i = 1;
        if(K==3)
            area.appendText("\n F_meseure : " + f_measure(clusters)+". \n");
        for (ArrayList<Double> centroid : clusters.keySet()) {
            area.appendText("// Cluster " + i + " \\\\\n");
            area.appendText("Medoid :" + centroid + "\n");
            area.appendText("Cluster :" + clusters.get(centroid) + "\n");
            area.appendText("_____________________________________________________________________\n\n");
            i++;
        }
    }



    public ArrayList<ArrayList<Double>> getNeighbor(ArrayList<ArrayList<Double>> currentMedoids,
                                                    ArrayList<ArrayList<Double>> points) {

        ArrayList<ArrayList<Double>> neighbor = new ArrayList<>();

        // neighbors differ by one medoid only (cardinality of the intersection is k - 1)
        int i = 1;
        while (i < currentMedoids.size()) {

            // Select random from medoids
            ArrayList<Double> medoid = currentMedoids.get(new Random().nextInt(currentMedoids.size()));

            if (!neighbor.contains(medoid)) {
                neighbor.add(medoid);
                i++;
            }
        }

        // Add one random medoid from dataset
        while (true) {

            // Select random from dataset
            ArrayList<Double> medoid = points.get(new Random().nextInt(points.size()));

            if (!neighbor.contains(medoid) && currentMedoids.contains(medoid)) {
                neighbor.add(medoid);
                break;
            }
        }

        return neighbor;
    }



    public double f_measure(HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters){
        HashMap <Integer, ArrayList<ArrayList<Double>>> classes = new HashMap<>();
        classes.put(1, new ArrayList<>());
        classes.put(2, new ArrayList<>());
        classes.put(3, new ArrayList<>());

        for(Patient patient : patients) {
            ArrayList<Double> point = new ArrayList<>();
            point = new ArrayList<>();
            point.add(patient.getAttribute_1());
            point.add(patient.getAttribute_2());
            point.add(patient.getAttribute_3());
            point.add(patient.getAttribute_4());
            point.add(patient.getAttribute_5());
            classes.get(patient.getTarget()).add(point);
        }
        HashMap <Integer, ArrayList<Integer>> cluster_class = new HashMap<>();
        ArrayList<Integer> init = new ArrayList<>(4);
        init.add(0);
        init.add(0);
        init.add(0);


        cluster_class.put(1, new ArrayList<>(init));
        cluster_class.put(2, new ArrayList<>(init));
        cluster_class.put(3, new ArrayList<>(init));

        int size_dataset=0;

        int j=1;
        for (ArrayList <Double> centroid : clusters.keySet()){
            for(int key_class : classes.keySet()){
                if(classes.get(key_class).contains(centroid)){
                    cluster_class.get(j).set(key_class-1,cluster_class.get(j).get(key_class-1)+1);
                    break;
                }
            }

            for (ArrayList<Double> ClusterMember : clusters.get(centroid)) {
                for(int key_class : classes.keySet()){
                    if(classes.get(key_class).contains(ClusterMember)){
                        cluster_class.get(j).set(key_class-1,cluster_class.get(j).get(key_class-1)+1);
                        break;
                    }
                }
            }

            j++;
            cluster_class.get(j-1).add(clusters.get(centroid).size());
            size_dataset+=clusters.get(centroid).size();
        }



        int index_size_clus = cluster_class.get(1).size();
        //  System.out.println(index_size_clus);
        double precesion=0;
        double recall=0;
        double f_meseure=0;
        ArrayList<Double> max_f=new ArrayList<>();
        for (int clas = 0;clas<3;clas++){
            //System.out.println("f-meseure de class : "+(clas+1));
            double max = 0.0;
            for (int clus =1;clus<4;clus++){

                precesion=0.0;
                recall=0.0;
                f_meseure=0.0;

                precesion = (double) (cluster_class.get(clus).get(clas))/(cluster_class.get(clus).get(index_size_clus-1));
                recall = (double) cluster_class.get(clus).get(clas)/classes.get(clas+1).size();

                if(recall != 0.0  || precesion != 0.0){
                    f_meseure = (2*precesion*recall)/(recall+precesion);
                }
                if(f_meseure>max) max=f_meseure;

                //System.out.println("f("+(clas+1)+","+clus+") ="+f_meseure);
            }
            max_f.add(max);

        }
        // System.out.println(max_f);
        double f_mes=0.0;
        int i = 1;
        for(double f_class:max_f){
            f_mes+=f_class*classes.get(i).size()/size_dataset;
            i++;
        }

        //System.out.println(f_mes);
        return f_mes;
    }

    public double getTotalCost(HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters) {
        double cost = 0;

        for (ArrayList<Double> medoid : clusters.keySet())
            cost += getCost(medoid, clusters.get(medoid));

        return cost;
    }

    public double getCost(ArrayList<Double> medoid,
                          ArrayList<ArrayList<Double>> cluster) {
        double cost = 0;
        for (ArrayList<Double> non_medoid : cluster)
            for (int j = 0; j < medoid.size(); j++)
                cost += Math.pow(medoid.get(j) - non_medoid.get(j), 2);
            cost=Math.sqrt(cost);
        return cost;
    }

    HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> getClusters(ArrayList<ArrayList<Double>> points,
                                                                         ArrayList<ArrayList<Double>> medoids) {

        // Initialize cluster
        HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters = new HashMap<>();
        for (ArrayList<Double> medoid : medoids)
            clusters.put(medoid, new ArrayList<>());
        //

        for (ArrayList<Double> non_medoid : points) {

            if (!clusters.containsKey(non_medoid)) {

                // Calculate distance
                HashMap<ArrayList<Double>, Double> distances = getDistance(clusters, non_medoid);
                //

                // associate non-medoid with closest medoid
                AssociateToCluster(distances, clusters, non_medoid);
                //
            }
        }

        return clusters;
    }


    public ArrayList<ArrayList<Double>> getRandomMedoids(ArrayList<ArrayList<Double>> points, int k) {
        ArrayList<ArrayList<Double>> medoids = new ArrayList<>();
        for (int j = 1; j <= k; j++) {
            ArrayList<Double> medoid = points.get(new Random().nextInt(points.size()));
            if (!medoids.contains(medoid)) {
                medoids.add(medoid);
            } else
                j--;
        }
        return medoids;
    }

    public static HashMap<ArrayList<Double>, Double> getDistance(HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters, ArrayList<Double> nonCentroids) {
        HashMap<ArrayList<Double>, Double> distances = new HashMap<>();
        for (ArrayList<Double> centroid : clusters.keySet()) {
            double distance = 0;
            for (int j = 0; j < centroid.size(); j++)
                distance += Math.pow(centroid.get(j) - nonCentroids.get(j), 2);
            distances.put(centroid, Math.sqrt(distance));
        }
        return distances;
    }

    public static void AssociateToCluster(HashMap<ArrayList<Double>, Double> distances,
                                          HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters,
                                          ArrayList<Double> nonCentroids) {

        double min = Collections.min(distances.values());

        for (ArrayList<Double> centroid : distances.keySet())
            if (distances.get(centroid) == min) { // Getting the correct centroid
                ArrayList<ArrayList<Double>> centroidCluster = new ArrayList<>(clusters.get(centroid));
                centroidCluster.add(nonCentroids); // Add non centroid tp the centroid cluster
                clusters.put(centroid, centroidCluster); // Update cluster
            }
    }

    public ArrayList<ArrayList<Double>> getPoints() {
        ArrayList<ArrayList<Double>> points = new ArrayList<>();
        ArrayList<Double> point;
        for (Patient patient : this.patients) {
            point = new ArrayList<>();
            point.add(patient.getAttribute_1());
            point.add(patient.getAttribute_2());
            point.add(patient.getAttribute_3());
            point.add(patient.getAttribute_4());
            point.add(patient.getAttribute_5());
            points.add(point);
        }
        return points;
    }


    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }


}
