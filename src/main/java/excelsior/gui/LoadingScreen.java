package excelsior.gui;

import excelsior.UI;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingScreen extends Stage {
    public LoadingScreen(UI ui){
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(ui.getPrimaryStage());
        this.initStyle(StageStyle.UNDECORATED);
        VBox container = new VBox();
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");
        ImageView load = new ImageView("/Icons/load.png");
        load.setFitWidth(500);
        load.setPreserveRatio(true);
        container.getChildren().add(load);
        this.setScene(new Scene(container));
    }
}
