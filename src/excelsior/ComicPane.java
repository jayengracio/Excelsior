package excelsior;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ComicPane extends GridPane {
    private final Label topNarration = new Label();
    private final Character leftCharacter = new Character();
    private final Character rightCharacter = new Character();
    private final Label bottomNarration = new Label();
    private final TextBubble leftSpeechBubble = new TextBubble();
    private final TextBubble rightSpeechBubble = new TextBubble();

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

    public Label getBottomNarration() {
        return bottomNarration;
    }

    public Character getLeftCharacter() {
        return leftCharacter;
    }

    public Character getRightCharacter() {
        return rightCharacter;
    }

    public TextBubble getLeftSpeechBubble() {
        return leftSpeechBubble;
    }

    public TextBubble getRightSpeechBubble() {
        return rightSpeechBubble;
    }

    // empties the class
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

    //Used for saving a comic panel from the workspace panel
    public void setTo(ComicPane panel) {
        this.clear();
        this.getLeftCharacter().setCharacter(panel.getLeftCharacter().getUpdatedImage());
        this.getRightCharacter().setCharacter(panel.getRightCharacter().getUpdatedImage());

        this.getLeftCharacter().setCurrentPose(panel.getLeftCharacter().getCurrentPose());
        this.getRightCharacter().setCurrentPose(panel.getRightCharacter().getCurrentPose());

        getNarrationsAndBubblesAndColours(panel);
    }

    // used for bringing a saved comic panel into the workspace panel for editing
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

    // essentially 'clones' the rest of the Comic Panel
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

    private void fillPane(){
        topNarration.setAlignment(Pos.CENTER);
        bottomNarration.setAlignment(Pos.CENTER);
        leftSpeechBubble.setAlignment(Pos.CENTER);
        rightSpeechBubble.setAlignment(Pos.CENTER);
        topNarration.setPrefSize(this.getPrefWidth(), 50);
        topNarration.setStyle("-fx-font-size: 19; -fx-font-weight: bold; -fx-font-family: Monospaced");
        bottomNarration.setPrefSize(this.getPrefWidth(), 50);
        bottomNarration.setStyle("-fx-font-size: 19; -fx-font-weight: bold; -fx-font-family: Monospaced");
        leftSpeechBubble.setPrefSize(300, 160);
        rightSpeechBubble.setPrefSize(300, 160);
        this.add(topNarration, 0, 0, 2, 1);
        this.add(leftSpeechBubble, 0, 1);
        this.add(rightSpeechBubble, 1, 1);
        this.add(leftCharacter, 0, 2);
        this.add(rightCharacter, 1, 2);
        this.add(bottomNarration, 0, 3, 2, 1);
    }
}
