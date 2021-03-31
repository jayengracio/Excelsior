package excelsior;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColourBox extends Rectangle
{
    private Color background;

    ColourBox(String colourHex)
    {
        super(20,20);
        background = Color.web(colourHex);
        this.setFill(background);
    }

    public Color getBackground() {
        return background;
    }
}
