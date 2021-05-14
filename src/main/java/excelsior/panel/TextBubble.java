package excelsior.panel;

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

    /**
     * Constructor
     */
    public TextBubble() {
        setAlignment(text, Pos.CENTER);
        bubble.setFitWidth(300);
        bubble.setFitHeight(160);
        text.setPadding(new Insets(15, 38, 55, 50));
        text.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: 'monospaced';");
        text.setAlignment(Pos.CENTER);
        text.maxWidth(300);
        this.getChildren().add(bubble);
        this.getChildren().add(text);
    }

    /**
     * Bubble Getter
     *
     * @return bubble
     */
    public ImageView getBubble() {
        return bubble;
    }

    /**
     * Bubble Setter
     *
     * @param bubble to set as
     */
    public void setBubble(ImageView bubble) {
        this.bubble = bubble;
    }

    /**
     * Text Getter
     *
     * @return the text in Label form
     */
    public Label getText() {
        return text;
    }

    /**
     * Text Setter in Label form
     *
     * @param text to set as
     */
    public void setText(Label text) {
        this.text = text;
    }

    /**
     * Text Setter in String form
     *
     * @param text to set as
     */
    public void setText(String text) {
        this.text.setText(text);
    }

    /**
     * Speech Setter
     */
    public void setSpeech() {
        bubble.setImage(new Image("/Icons/speech_bubble.png"));
        this.bubbleType = "speech";
    }

    /**
     * Thought Setter
     */
    public void setThought() {
        bubble.setImage(new Image("/Icons/thought_bubble.png"));
        this.bubbleType = "thought";
    }

    /**
     * Bubble type Getter
     *
     * @return null or bubble type
     */
    public String getBubbleType() {
        if (bubbleType == null) return "null";
        else return bubbleType;
    }

    /**
     * Bubble type Setter in String form
     *
     * @param type to set as
     */
    public void setBubbleType(String type) {
        this.bubbleType = type;
    }

    /**
     * Empties the bubble
     */
    public void setEmpty() {
        bubble.setImage(null);
        text.setText("");
        bubbleType = "";
    }

    /**
     * Checks if bubble is empty
     *
     * @return true if text is empty
     */
    public boolean isEmpty() {
        String s = text.getText();
        return s.isEmpty();
    }

    /**
     * Text size getter
     *
     * @return the font size
     */
    public int getTextSize() {
        return fontSize;
    }

    /**
     * Text size setter
     *
     * @param size to change to
     */
    public void setTextSize(int size) {
        String style = "-fx-font-weight: bold; -fx-font-family: 'monospaced'; -fx-font-size: " + size + ";";
        fontSize = size;
        text.setStyle(style);
    }

    /**
     * Change sizes for comic panels view
     */
    public void minimise() {
        bubble.setFitWidth(135);
        bubble.setFitHeight(80);
        text.setPadding(new Insets(5, 15, 23, 22));
        text.setStyle("-fx-font-size:" + (fontSize / 2) + "; -fx-font-weight: bold; -fx-font-family: 'monospaced';");
        text.maxWidth(135);
    }
}
