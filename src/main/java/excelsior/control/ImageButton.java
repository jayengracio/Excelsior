package excelsior.control;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends Button {
    private ImageView graphic;

    public ImageButton(String resourcePath, String filename) {
        super(filename);
        this.setText(null);
        graphic = new ImageView(new Image(resourcePath+filename));
        graphic.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px;");
        graphic.setFitWidth(90);
        graphic.setFitHeight(90);
        this.setGraphic(graphic);
    }

    public void setImage(String resourcePath, String filename)
    {
        this.setValue(filename);
        this.setText(null);
        graphic.setImage(new Image(resourcePath+filename));
        this.setGraphic(graphic);
    }
}

