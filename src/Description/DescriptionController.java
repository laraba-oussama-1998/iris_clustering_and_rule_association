package Description;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class DescriptionController {


    @FXML
    private Pane description_pane;

    public Pane getDescription_pane() {
        return description_pane;
    }

    public void setDescription_pane(Pane description_pane) {
        this.description_pane = description_pane;
    }

}

