package excelsior;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ComicPane extends GridPane {
    private Label topNarration = new Label();
    private Character leftCharacter = new Character();
    private Character rightCharacter = new Character();
    private Label bottomNarration = new Label();
    private TextBubble leftSpeechBubble = new TextBubble();
    private TextBubble rightSpeechBubble = new TextBubble();
    private int width = 300;

    public ComicPane(){
        this.setPrefSize(615, 500);
        this.setStyle("-fx-background-color: white;-fx-border-color: #000000; -fx-border-width: 2; -fx-border-radius: 2;");
        this.setHgap(15);
        this.setGridLinesVisible(false);
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

    public TextBubble getLeftSpeechBubble() {
        return leftSpeechBubble;
    }

    public void setLeftSpeechBubble(TextBubble leftSpeechBubble) {
        this.leftSpeechBubble = leftSpeechBubble;
    }

    public TextBubble getRightSpeechBubble() {
        return rightSpeechBubble;
    }

    public void setRightSpeechBubble(TextBubble rightSpeechBubble) {
        this.rightSpeechBubble = rightSpeechBubble;
    }

    private void fillPane(){
        topNarration.setAlignment(Pos.CENTER);
        bottomNarration.setAlignment(Pos.CENTER);
        leftSpeechBubble.setAlignment(Pos.CENTER);
        rightSpeechBubble.setAlignment(Pos.CENTER);
        topNarration.setPrefSize(this.getPrefWidth(), 50);
        topNarration.setStyle("-fx-font-size: 19; -fx-font-weight: bold; -fx-font-family: Monospaced");
        bottomNarration.setPrefSize(this.getPrefWidth(), 50);
        bottomNarration.setStyle("-fx-font-size: 19; -fx-font-weight: bold; -fx-font-family: Monospaced");
        leftSpeechBubble.setPrefSize(width, 160);
        rightSpeechBubble.setPrefSize(width, 160);
        /*ImageView leftSpeech = new ImageView();
        ImageView rightSpeech = new ImageView();
        leftSpeech.prefHeight(60);
        leftSpeech.setFitHeight(60);
        rightSpeech.prefHeight(60);
        rightSpeech.setFitHeight(60);
        leftSpeech.prefWidth(width);
        leftSpeech.setFitWidth(width);
        rightSpeech.prefWidth(width);
        rightSpeech.setFitWidth(width);*/
        this.add(topNarration, 0, 0, 2, 1);
        this.add(leftSpeechBubble, 0, 1);
        this.add(rightSpeechBubble, 1, 1);
        this.add(leftCharacter, 0, 2);
        this.add(rightCharacter, 1, 2);
        this.add(bottomNarration, 0, 3, 2, 1);
    }

    public void clear() {
        this.getTopNarration().setText(null);
        this.getBottomNarration().setText(null);
        this.getRightCharacter().setCharacter("/Character_Images/#empty.png");
        this.getLeftCharacter().setCharacter("/Character_Images/#empty.png");
        this.getRightCharacter().setCurrentPose(null);
        this.getLeftCharacter().setCurrentPose(null);
        this.getRightSpeechBubble().setEmpty();
        this.getLeftSpeechBubble().setEmpty();
    }

    public void setTo(ComicPane panel) {
        this.clear();
        this.getLeftCharacter().setCharacter(panel.getLeftCharacter().getUpdatedImage());
        this.getRightCharacter().setCharacter(panel.getRightCharacter().getUpdatedImage());

        this.getLeftCharacter().setCurrentPose(panel.getLeftCharacter().getCurrentPose());
        this.getRightCharacter().setCurrentPose(panel.getRightCharacter().getCurrentPose());

        getNarrationsAndBubblesAndColours(panel);
    }

    public void setWorkspaceTo(ComicPane panel) {
        this.clear();

        if (panel.getRightCharacter().getCurrentPose() == null) {
            this.getRightCharacter().setCharacter("/Character_Images/#empty.png");
        } else {
            this.getRightCharacter().setCharacter("/Character_Images/" + panel.getRightCharacter().getCurrentPose());
        }

        if (panel.getLeftCharacter().getCurrentPose() == null) {
            this.getLeftCharacter().setCharacter("/Character_Images/#empty.png");
        } else {
            this.getLeftCharacter().setCharacter("/Character_Images/" + panel.getLeftCharacter().getCurrentPose());
        }

        this.getRightCharacter().setCurrentPose(panel.getRightCharacter().getCurrentPose());
        this.getLeftCharacter().setCurrentPose(panel.getLeftCharacter().getCurrentPose());

        getNarrationsAndBubblesAndColours(panel);
    }

    private void getNarrationsAndBubblesAndColours(ComicPane panel) {
        this.getTopNarration().setText(panel.getTopNarration().getText());
        this.getBottomNarration().setText(panel.getBottomNarration().getText());

        this.getLeftSpeechBubble().getBubble().setImage(panel.getLeftSpeechBubble().getBubble().getImage());
        this.getLeftSpeechBubble().getText().setText(panel.getLeftSpeechBubble().getText().getText());

        this.getRightSpeechBubble().getBubble().setImage(panel.getRightSpeechBubble().getBubble().getImage());
        this.getRightSpeechBubble().getText().setText(panel.getRightSpeechBubble().getText().getText());

        this.getRightCharacter().setSkinColour(panel.getRightCharacter().getSkinColour());
        this.getRightCharacter().setHairColour(panel.getRightCharacter().getHairColour());

        this.getLeftCharacter().setSkinColour(panel.getLeftCharacter().getSkinColour());
        this.getLeftCharacter().setHairColour(panel.getLeftCharacter().getHairColour());
    }
}
