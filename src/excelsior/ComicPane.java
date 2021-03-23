package excelsior;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class ComicPane extends GridPane {
    Label topNarration = new Label();
    Character leftCharacter = new Character();
    Character rightCharacter = new Character();
    Label bottomNarration = new Label();
    Label leftSpeechBubble = new Label();
    Label rightSpeechBubble = new Label();
    int leftSpeechType;
    int rightSpeechType;
    int width = 300;

    public ComicPane(){
        this.setPrefSize(615, 500);
        this.setStyle("-fx-background-color: white;-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 2;");
        this.setHgap(15);
        this.setGridLinesVisible(true);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(300);
        col1.setHalignment(HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMaxWidth(300);
        col2.setHalignment(HPos.CENTER);
        this.getColumnConstraints().addAll(col1,col2);
        fillPane();
    }

    public Label getTopNarration() {
        return topNarration;
    }

    public void setTopNarration(Label topNarration) {
        this.topNarration = topNarration;
    }

    public Character getLeftCharacter() {
        return leftCharacter;
    }

    public void setLeftCharacter(Character leftCharacter) {
        this.leftCharacter = leftCharacter;
    }

    public Character getRightCharacter() {
        return rightCharacter;
    }

    public void setRightCharacter(Character rightCharacter) {
        this.rightCharacter = rightCharacter;
    }

    public Label getBottomNarration() {
        return bottomNarration;
    }

    public void setBottomNarration(Label bottomNarration) {
        this.bottomNarration = bottomNarration;
    }

    public Label getLeftSpeechBubble() {
        return leftSpeechBubble;
    }

    public void setLeftSpeechBubble(Label leftSpeechBubble) {
        this.leftSpeechBubble = leftSpeechBubble;
    }

    public Label getRightSpeechBubble() {
        return rightSpeechBubble;
    }

    public void setRightSpeechBubble(Label rightSpeechBubble) {
        this.rightSpeechBubble = rightSpeechBubble;
    }

    public int getLeftSpeechType() {
        return leftSpeechType;
    }

    public void setLeftSpeechType(int leftSpeechType) {
        this.leftSpeechType = leftSpeechType;
    }

    public int getRightSpeechType() {
        return rightSpeechType;
    }

    public void setRightSpeechType(int rightSpeechType) {
        this.rightSpeechType = rightSpeechType;
    }

    private void fillPane(){
        topNarration.setAlignment(Pos.CENTER);
        bottomNarration.setAlignment(Pos.CENTER);
        leftSpeechBubble.setAlignment(Pos.CENTER);
        rightSpeechBubble.setAlignment(Pos.CENTER);
        topNarration.setPrefSize(width, 50);
        bottomNarration.setPrefSize(width, 50);
        leftSpeechBubble.setPrefSize(width, 100);
        rightSpeechBubble.setPrefSize(width, 100);
        ImageView leftSpeech = new ImageView();
        ImageView rightSpeech = new ImageView();
        leftSpeech.prefHeight(60);
        leftSpeech.setFitHeight(60);
        rightSpeech.prefHeight(60);
        rightSpeech.setFitHeight(60);
        leftSpeech.prefWidth(width);
        leftSpeech.setFitWidth(width);
        rightSpeech.prefWidth(width);
        rightSpeech.setFitWidth(width);
        this.add(topNarration, 0, 0, 2, 1);
        this.add(leftSpeechBubble, 0, 1);
        this.add(rightSpeechBubble, 1, 1);
        this.add(leftSpeech, 0, 2);
        this.add(rightSpeech, 1, 2);
        this.add(leftCharacter, 0, 3);
        this.add(rightCharacter, 1, 3);
        this.add(bottomNarration, 0, 4, 2, 1);
    }
}
