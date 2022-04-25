package algorithms.kmedoid;

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

public class Kmedoid{

    @FXML
    private Pane pane;

    @FXML
    private JFXTextField iterField;

    @FXML
    private JFXTextField clusterField;

    @FXML
    private JFXTextArea area;

    public ObservableList<Patient> patients;
    @FXML
    private Text time;


    public void start() {

        int iter=0, K=0;
        boolean canProceed = true;

        try {
            iter = Integer.parseInt(iterField.getText());
            if (iter < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le champ itération doit être un entier positif");
            alert.showAndWait();
            canProceed = false;
        }

        try {
            K = Integer.parseInt(clusterField.getText());
            if (K < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le champ nombre de cluster doit être un entier positif");
            alert.showAndWait();
            canProceed = false;
        }

        if(canProceed) {
            area.clear();
            long start = System.currentTimeMillis();
            startKMedoid(iter, K);
            long end = System.currentTimeMillis();
            time.setText(Long.toString(end - start) + "ms");
        }

    }

    public ArrayList<ArrayList<Double>> matrix(ArrayList<ArrayList<Double>> points){
        ArrayList<ArrayList<Double>> dis_matrix = new ArrayList<>();
        for (int i=0;i<points.size()-1;i++){
            for (int j=i+1;j<points.size();j++){


            }

        }

        return null;
    }

    public void startKMedoid(int iter, int K) {

        ArrayList<ArrayList<Double>> points = getPoints();

        // Shuffle points
        Collections.shuffle(points);

        // Pick 3 random centroids
        ArrayList<ArrayList<Double>> centroids = getInitialCentroids(points, K);


        // Hashmap to save the clusters
        HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters = new HashMap<>();

        // For a given centroid, we initialize it cluster
        for (ArrayList<Double> centroid : centroids)
            clusters.put(centroid, new ArrayList<>());

        boolean converge = false;
        int i=1;
        double costFunction;
        centroids.clear();
        for (ArrayList<Double> centroid : clusters.keySet()) {
            centroids.add(centroid);
        }
        clusters = clusterconstruct(points, centroids);
        for (i = 1; i <= iter; i++) {



            // Updating each centroids with the mean of it cluster points
            HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> newClusters = getnewclusters(clusters);



            // Check if the algorithm converges to a global minimum
            if (clusters.keySet().equals(newClusters.keySet())) {
                converge = true;

                break;
            }

            // Update initial cluster with the new one
            clusters = new HashMap<>(newClusters);
            //

         //   centroids.clear();

        }
        area.appendText("Itération " + i + ", Cost Function  = " + getcost(clusters) + "\n");


        if(converge)
            area.appendText("L'algorithme a convergé à l'itération " + i + "\n\n");
        else
            area.appendText("L'algorithme n'a pas convergé\n");

        i = 1;
        if(K==3)
            area.appendText("\n F_meseure : " + f_measure(clusters)+". \n");

        for(ArrayList<Double> centroid : clusters.keySet()) {
            area.appendText("// Cluster " + i + " \\\\\n");
            area.appendText("Centroid :" + centroid + "\n");
            area.appendText("Cluster :" + clusters.get(centroid) + "\n");
            area.appendText("_____________________________________________________________________\n\n");
            i++;
        }


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



    public HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>>  clusterconstruct(
            ArrayList<ArrayList<Double>> points,ArrayList<ArrayList<Double>> centroids){


        HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters = new HashMap<>();

        // For a given centroid, we initialize it cluster
        for (ArrayList<Double> centroid : centroids)
            clusters.put(centroid, new ArrayList<>());
        // Hash map to store the K distances for a given non-centroid
        HashMap<ArrayList<Double>, Double> distances;

        // Constructing the clusters
        for (ArrayList<Double> nonCentroids : points) {

            // For each non centroid point
            if (!centroids.contains(nonCentroids)) {

                // Calculate distance between this non-centroid and the K centroids points
                distances = getDistance(clusters, nonCentroids);
                //

                // We associate this non-centroid with the corresponding close centroid cluster
                AssociateToCluster(distances, clusters, nonCentroids);
                //

            }
        }

        return clusters;
    }

    public ArrayList<ArrayList<Double>> getPoints() {
        // Get points
        ArrayList<ArrayList<Double>> points = new ArrayList<>();
        ArrayList<Double> point;
        for(Patient patient : patients) {
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

    public double getcost(HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters){
        double costFunction = 0.;
        for (ArrayList<Double> centroid : clusters.keySet()) {
            // Foreach centroid
            for (ArrayList<Double> ClusterMember : clusters.get(centroid)) { // Foreach point in the cluster
                double distance = 0;
                for (int j = 0; j < ClusterMember.size(); j++)
                    distance += Math.pow(centroid.get(j) - ClusterMember.get(j), 2);
                costFunction += Math.sqrt(distance);
            }
        }
        return costFunction;
    }

    public ArrayList<ArrayList<Double>> getInitialCentroids(ArrayList<ArrayList<Double>> points, int K) {

        ArrayList<ArrayList<Double>> centroids = new ArrayList<>();
        for (int i = 1; i <= K; i++) {
            // pick random point
            ArrayList<Double> centroid = points.get(new Random().nextInt(points.size()));

            if (!centroids.contains(centroid)) {
                centroids.add(centroid);
            } else
                i--;
        }
        return centroids;
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

    public HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> getnewclusters(
            HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusters) {

        HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> newClustersgeneral ;
        HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> clusterscopy = new HashMap<>(clusters) ;
        boolean a = false;
        double cost,newcost;
        int i = 1;
        for (ArrayList<Double> centroid : clusterscopy.keySet()) {

            //System.out.println(i);
            i++;


            for (ArrayList<Double> ClusterMember : clusterscopy.get(centroid)) { // Foreach point in the cluster
                double distance = 0;
                HashMap<ArrayList<Double>, ArrayList<ArrayList<Double>>> newCluster = new HashMap<>(clusterscopy);
                ArrayList<ArrayList<Double>> newMember = new ArrayList<>(clusterscopy.get(centroid));

                newMember.add(centroid);

                newMember.remove(ClusterMember);

                newCluster.put(ClusterMember,newMember);

                newCluster.remove(centroid);

                ArrayList<ArrayList<Double>> centroids_cons = new ArrayList<>();
                for (ArrayList<Double> centroid_cons : newCluster.keySet()) {
                    centroids_cons.add(centroid_cons);
                }
                newCluster = clusterconstruct(getPoints(),centroids_cons);

               // System.out.println(clusterscopy.keySet());
               // System.out.println("------------------------------");
               // System.out.println(newCluster.keySet());
                cost = getcost(clusterscopy);
                newcost = getcost(newCluster);


                if(newcost<cost){
                   // System.out.println("**********************");
                   // System.out.println("*********"+ClusterMember+"************");
                   // System.out.println("**********************");
                   // System.out.println(clusterscopy.keySet());
                   // System.out.println("------------------------------");
                  //  System.out.println(newCluster.keySet());
                  //  System.out.println("**********************");
                   // System.out.println("*********"+centroid+"************");
                   // System.out.println("**********************");
                    clusterscopy = new HashMap<>(newCluster);

                    a = true;
                    break;
                }

            }

            //
            if(a==true)break;
        }

        newClustersgeneral = new HashMap<>(clusterscopy);

        return newClustersgeneral;
    }



    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

}

