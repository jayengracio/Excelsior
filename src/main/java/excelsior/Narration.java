package excelsior;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Narration extends Label
{
    private int fontSize = 20;

    public Narration(){
        this.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-font-family: 'monospaced';");
        this.setAlignment(Pos.CENTER);
    }

    public void setTextSize(int size) {
        String style = "-fx-font-weight: bold; -fx-font-family: 'monospaced'; -fx-font-size: " + size + ";";
        fontSize = size;
        this.setStyle(style);
    }

    public int getTextSize() {
        return fontSize;
    }

    public void minimise(){
        this.setStyle("-fx-font-size:"+ (fontSize/2) +"; -fx-font-weight: bold; -fx-font-family: 'monospaced';");
    }

}