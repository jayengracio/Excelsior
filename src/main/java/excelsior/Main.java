package excelsior;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Excelsior");
        UI ui = new UI(primaryStage);
        primaryStage.getIcons().add(new Image("Icons/icon.png"));
        ui.setStage();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
