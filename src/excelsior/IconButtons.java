package excelsior;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconButtons extends Button{
    private ImageView graphic;

    IconButtons(String label) {
        super(label);
        this.setText(null);
        graphic = new ImageView(new Image("/Icons/" + label));
        graphic.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px;");
        graphic.setFitWidth(90);
        graphic.setFitHeight(90);
        this.setGraphic(graphic);
    }

    public void setIcon(String url){
        graphic.setImage(new Image("/Icons/" + url));
        this.setGraphic(graphic);
    }
}
