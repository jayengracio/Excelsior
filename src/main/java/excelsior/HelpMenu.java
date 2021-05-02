package excelsior;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpMenu {
    private final Stage primaryStage;

    public HelpMenu(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Menu create() {
        Menu menu = new Menu("Help");
        MenuItem help = new MenuItem("Panels");

        KeyCombination keyBinding = new KeyCodeCombination(KeyCode.F9);
        help.setAccelerator(keyBinding);

        menu.getItems().add(help);

        help.setOnAction(actionEvent -> {
            Label secondLabel = new Label("PANELS: " +
                    "\nAll saved panels can be seen in the scroll pane" +
                    "\nClicking a saved a panel will select it" +
                    "\nA selected panel is automatically brought to the workspace for editing" +
                    "\n\n" +
                    "\nUse the Panel menu or use these key bindings to begin working with panels:" +
                    "\nShift+N : work on a new panel/unselect the selected panel" +
                    "\nShift+S : save the working panel" +
                    "\nDEL : delete the selected panel" +
                    "\n" +
                    "\nUnsaved changes in the work panel will show a warning"
            );
            secondLabel.setStyle("-fx-font-size: 16px;");

            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);

            Scene secondScene = new Scene(secondaryLayout, 600, 600);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Help");
            newWindow.setScene(secondScene);

            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);

            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(primaryStage);
            newWindow.setResizable(false);

            // Set position of second window, related to primary window.
            newWindow.setX(primaryStage.getX() * 2);
            newWindow.setY(primaryStage.getY() * 2);

            newWindow.show();
        });
        return menu;
    }
}
