package excelsior;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HighlightedPopup extends Popup {
    private final Parent sceneRoot;

    /**
     * Constructor.
     * Contains an event handler which toggles blur for background to highlight popup
     */
    public HighlightedPopup(Stage stage) {
        sceneRoot = stage.getScene().getRoot();
        this.setAutoHide(true);

        EventHandler<WindowEvent> toggleBlur = new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                sceneRoot.setEffect((sceneRoot.getEffect() == null) ? new GaussianBlur() : null);
            }
        };

        this.setOnShowing(toggleBlur);
        this.setOnHiding(toggleBlur);
    }
}
