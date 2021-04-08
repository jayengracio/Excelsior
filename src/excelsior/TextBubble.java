package excelsior;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class TextBubble extends StackPane {
    ImageView bubble = new ImageView();
    Label text = new Label();

    public TextBubble(){
        this.setAlignment(text, Pos.CENTER);
        bubble.setFitWidth(300);
        bubble.setFitHeight(160);
        text.setPadding(new Insets(20, 45 , 50, 45));
        text.setStyle("-fx-font-size: 14; -fx-wrap-text:true;");
        text.setAlignment(Pos.CENTER);
        text.maxWidth(300);
        this.getChildren().add(bubble);
        this.getChildren().add(text);
    }

    public ImageView getBubble() {
        return bubble;
    }

    public void setBubble(ImageView bubble) {
        this.bubble = bubble;
    }

    public Label getText() {
        return text;
    }

    public void setText(Label text) {
        this.text = text;
    }

    public void setText(String text){
        this.text.setText(text);
    }

    public void setSpeech(){
        bubble.setImage(new Image("/Character_Images/speech_bubble.png"));
    }

    public void setThought(){

    }

    public void setEmpty(){
        bubble.setImage(null);
        text.setText("");
    }

    public boolean isEmpty(){
        String s = text.getText();
        return s.isEmpty();
    }
}
