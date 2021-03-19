package excelsior;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Button extends Label {
    String value;

    Button(String label) {
        this.value = label;
        this.setText(value);
        this.setPrefSize(107, 107);
        this.setStyle("-fx-border-color: black; -fx-border-radius: 20px;");
        this.setAlignment(Pos.CENTER);
    }

    String getValue() {
        return this.value;
    }

    void setValue(String value) {
        this.value = value;
        this.setText(value);
    }
}
