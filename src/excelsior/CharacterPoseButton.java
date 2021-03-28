package excelsior;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CharacterPoseButton extends Button{
    CharacterPoseButton(String label) {
        super(label);
        this.setText(null);
        ImageView graphic = new ImageView(new Image("/Character_Images/" + label));
        graphic.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px;");
        graphic.setFitWidth(90);
        graphic.setFitHeight(90);
        this.setGraphic(graphic);
    }
}
