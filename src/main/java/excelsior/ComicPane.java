package excelsior;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ComicPane extends GridPane {
    private  Narration topNarration = new Narration();
    private  Character leftCharacter = new Character();
    private  Character rightCharacter = new Character();
    private  Narration bottomNarration = new Narration();
    private  TextBubble leftSpeechBubble = new TextBubble();
    private  TextBubble rightSpeechBubble = new TextBubble();
    private boolean inEditMode = false;

    public ComicPane() {
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
        this.getColumnConstraints().addAll(col1, col2);
        fillPane();
    }

    public Narration getTopNarration() {
        return topNarration;
    }

    public Narration getBottomNarration() {
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

    public void setTopNarration(Narration topNarration) {
        this.getChildren().remove(this.topNarration);
        this.topNarration = topNarration;
        this.add(this.topNarration, 0, 0, 2, 1);
    }

    public void setLeftCharacter(Character leftCharacter) {
        this.getChildren().remove(this.leftCharacter);
        this.leftCharacter = leftCharacter;
        this.add(this.leftCharacter, 0, 2);
    }

    public void setRightCharacter(Character rightCharacter) {
        this.getChildren().remove(this.rightCharacter);
        this.rightCharacter = rightCharacter;
        this.add(this.rightCharacter, 1, 2);
    }

    public void setBottomNarrationMin(Narration bottomNarration) {
        this.getChildren().remove(this.bottomNarration);
        this.bottomNarration = bottomNarration;
        this.add(this.bottomNarration, 0, 3, 2, 1);
        minimise();
    }

    public void setLeftSpeechBubble(TextBubble leftSpeechBubble) {
        this.getChildren().remove(this.leftSpeechBubble);
        this.leftSpeechBubble = leftSpeechBubble;
        this.add(this.leftSpeechBubble, 0, 1);
    }

    public void setRightSpeechBubble(TextBubble rightSpeechBubble) {
        this.getChildren().remove(this.rightSpeechBubble);
        this.rightSpeechBubble = rightSpeechBubble;
        this.add(this.rightSpeechBubble, 1, 1);
    }

    public void setEditMode(boolean inEditMode) {
        this.inEditMode = inEditMode;
    }

    public boolean isInEditMode() {
        return inEditMode;
    }

    // empties the class
    public void clear() {
        this.setEditMode(false);
        this.getTopNarration().setText(null);
        this.getBottomNarration().setText(null);
        this.getRightCharacter().reset();
        this.getLeftCharacter().reset();
        this.getRightSpeechBubble().setEmpty();
        this.getLeftSpeechBubble().setEmpty();
    }

    // replace the contents the panel with another
    public void setTo(ComicPane panel, boolean isWorkSpace) {
        clone(panel);
        if(!isWorkSpace)
        {
            this.minimise();
        }
    }

    // 'clones' the Comic Panel
    private void clone(ComicPane panel) {
        if (!panel.getRightCharacter().isEmpty()) {
            this.getRightCharacter().setCharacter(panel.getRightCharacter().getCharacter());
            this.getRightCharacter().setEmpty(false);
        }

        if (!panel.getLeftCharacter().isEmpty()) {
            this.getLeftCharacter().setCharacter(panel.getLeftCharacter().getCharacter());
            this.getLeftCharacter().setEmpty(false);
        }

        this.getLeftCharacter().setPoseString(panel.getLeftCharacter().getPoseString());
        this.getRightCharacter().setPoseString(panel.getRightCharacter().getPoseString());

        this.getRightCharacter().setDefaultOrientation(panel.getRightCharacter().isDefaultOrientation());
        this.getLeftCharacter().setDefaultOrientation(panel.getLeftCharacter().isDefaultOrientation());

        this.getTopNarration().setText(panel.getTopNarration().getText());
        this.getTopNarration().setTextSize(panel.getTopNarration().getTextSize());
        this.getBottomNarration().setText(panel.getBottomNarration().getText());
        this.getBottomNarration().setTextSize(panel.getBottomNarration().getTextSize());

        this.getLeftSpeechBubble().getBubble().setImage(panel.getLeftSpeechBubble().getBubble().getImage());
        this.getLeftSpeechBubble().getText().setText(panel.getLeftSpeechBubble().getText().getText());
        this.getLeftSpeechBubble().setTextSize(panel.getLeftSpeechBubble().getTextSize());
        this.getLeftSpeechBubble().setBubbleType(panel.getLeftSpeechBubble().getBubbleType());

        this.getRightSpeechBubble().getBubble().setImage(panel.getRightSpeechBubble().getBubble().getImage());
        this.getRightSpeechBubble().getText().setText(panel.getRightSpeechBubble().getText().getText());
        this.getRightSpeechBubble().setTextSize(panel.getRightSpeechBubble().getTextSize());
        this.getRightSpeechBubble().setBubbleType(panel.getRightSpeechBubble().getBubbleType());

        this.getRightCharacter().setSkinColour(panel.getRightCharacter().getSkinColour());
        this.getRightCharacter().setHairColour(panel.getRightCharacter().getHairColour());

        this.getLeftCharacter().setSkinColour(panel.getLeftCharacter().getSkinColour());
        this.getLeftCharacter().setHairColour(panel.getLeftCharacter().getHairColour());

        this.getLeftCharacter().setLipColour(panel.getLeftCharacter().getLipColour());
        this.getRightCharacter().setLipColour(panel.getRightCharacter().getLipColour());

        this.getRightCharacter().setFemale(panel.getRightCharacter().isFemale());
        this.getLeftCharacter().setFemale(panel.getLeftCharacter().isFemale());

        this.getLeftCharacter().setPoseString(panel.getLeftCharacter().getPoseString());
        this.getRightCharacter().setPoseString(panel.getRightCharacter().getPoseString());
    }

    private void fillPane() {
        topNarration.setAlignment(Pos.CENTER);
        bottomNarration.setAlignment(Pos.CENTER);
        leftSpeechBubble.setAlignment(Pos.CENTER);
        rightSpeechBubble.setAlignment(Pos.CENTER);
        topNarration.setPrefSize(this.getPrefWidth(), 50);
        bottomNarration.setPrefSize(this.getPrefWidth(), 50);
        leftSpeechBubble.setPrefSize(300, 160);
        rightSpeechBubble.setPrefSize(300, 160);
        this.add(topNarration, 0, 0, 2, 1);
        this.add(leftSpeechBubble, 0, 1);
        this.add(rightSpeechBubble, 1, 1);
        this.add(leftCharacter, 0, 2);
        this.add(rightCharacter, 1, 2);
        this.add(bottomNarration, 0, 3, 2, 1);
    }

    //used to change sizes for comic panels view
    public void minimise(){
        this.setPrefSize(280, 230);
        this.setHgap(5);
        topNarration.minimise();
        bottomNarration.minimise();
        this.setPadding(new Insets(5));
        leftSpeechBubble.minimise();
        rightSpeechBubble.minimise();
        leftCharacter.minimise();
        rightCharacter.minimise();
    }
}
