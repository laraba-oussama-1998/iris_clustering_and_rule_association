package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root, 1360, 650);
        GUIController controller = new GUIController();
        stage.setTitle("Data Mining");
        stage.setScene(scene);
        stage.setResizable(false);
        controller.setStage(stage);
        stage.show();
    }
}


