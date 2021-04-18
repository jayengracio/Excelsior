package excelsior;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HighlightedPopup extends Popup
{
    private Parent sceneRoot;

    public HighlightedPopup(Stage stage)
    {
        sceneRoot = stage.getScene().getRoot();
        this.setAutoHide(true);

        //event handler which toggles blur for background to highlight popup
        EventHandler<WindowEvent> toggleBlur = new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event)
            {
                sceneRoot.setEffect( (sceneRoot.getEffect() == null) ? new GaussianBlur() : null);
            }
         };

        this.setOnShowing(toggleBlur);
        this.setOnHiding(toggleBlur);
    }
}
