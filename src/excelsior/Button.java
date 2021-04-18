package excelsior;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Button extends Label {
    private String value;
    private Glow glow = new Glow();

    Button(String label) {
        this.value = label;
        this.setText(value);
        this.setPrefSize(107, 107);
        this.setStyle("-fx-border-color: #000000; -fx-border-radius: 20px;");
        this.setAlignment(Pos.CENTER);
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> { this.setEffect(glow); });
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> { this.setEffect(glow); });
        this.addEventHandler(MouseEvent.MOUSE_EXITED, event -> { this.setEffect(null); });


    }

    String getValue() {
        return this.value;
    }

    void setValue(String value) {
        this.value = value;
        this.setText(value);
    }

}
