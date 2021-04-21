package excelsior;

import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColourBox extends Rectangle {
    DropShadow dropShadow = new DropShadow();
    private Color background;

    ColourBox(String colourHex) {
        super(20, 20);
        background = Color.web(colourHex);
        this.setFill(background);

        this.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            this.setEffect(dropShadow);
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            this.setEffect(dropShadow);
        });

        this.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            this.setEffect(null);
        });
        
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            this.setEffect(null);
        });
    }

    public Color getBackground() {
        return background;
    }
}
