package excelsior;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class TextBubble extends StackPane {
    private ImageView bubble = new ImageView();
    private Label text = new Label();
    private String bubbleType;
    private int fontSize = 16;

    public TextBubble(){
        this.setAlignment(text, Pos.CENTER);
        bubble.setFitWidth(300);
        bubble.setFitHeight(160);
        text.setPadding(new Insets(15, 38 , 55, 50));
        text.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'monospaced';");
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
        bubble.setImage(new Image("/Icons/speech_bubble.png"));
        this.bubbleType = "speech";
    }

    public void setThought(){
        bubble.setImage(new Image("/Icons/thought_bubble.png"));
        this.bubbleType = "thought";
    }

    public void setBubbleType(String type) {
        this.bubbleType = type;
    }

    public String getBubbleType() {
        if (bubbleType == null) return "null";
        else return bubbleType;
    }

    public void setEmpty(){
        bubble.setImage(null);
        text.setText("");
    }

    public boolean isEmpty(){
        String s = text.getText();
        return s.isEmpty();
    }

    public void setTextSize(int size){
        String style = "-fx-font-weight: bold; -fx-font-family: 'monospaced'; -fx-font-size: " + size + ";";
        fontSize = size;
        text.setStyle(style);
    }

    public int getTextSize(){
        return fontSize;
    }

    //used to change sizes for comic panels view
    public void minimise()
    {
        bubble.setFitWidth(135);
        bubble.setFitHeight(80);
        text.setPadding(new Insets(5, 15 , 23, 22));
        text.setStyle("-fx-font-size:"+ (fontSize/2) +"; -fx-font-weight: bold; -fx-font-family: 'monospaced';");
        text.maxWidth(135);
    }
}
