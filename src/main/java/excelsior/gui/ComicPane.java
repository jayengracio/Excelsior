package excelsior.gui;

import excelsior.panel.Character;
import excelsior.panel.Narration;
import excelsior.panel.TextBubble;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ComicPane extends GridPane {
    private Narration topNarration = new Narration();
    private Character leftCharacter = new Character();
    private  Character rightCharacter = new Character();
    private  Narration bottomNarration = new Narration();
    private TextBubble leftSpeechBubble = new TextBubble();
    private  TextBubble rightSpeechBubble = new TextBubble();
    private boolean inEditMode = false;

    /**
     * Sets up the structure of the ComicPane
     */
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

    /**
     * Empty the object
     */
    public void clear() {
        this.setEditMode(false);
        this.getTopNarration().setText(null);
        this.getBottomNarration().setText(null);
        this.getRightCharacter().reset();
        this.getLeftCharacter().reset();
        this.getRightSpeechBubble().setEmpty();
        this.getLeftSpeechBubble().setEmpty();
    }

    /**
     * Clone the contents of a panel to another
     * @param panel the cloned panel
     * @param isWorkSpace if the panel is the workspace
     */
    public void setTo(ComicPane panel, boolean isWorkSpace) {
        clone(panel);
        if(!isWorkSpace)
        {
            this.minimise();
        }
    }

    /**
     * Actual functionality in cloning the comic panel
     * @param panel the cloned panel
     */
    private void clone(ComicPane panel) {
        if (!panel.getRightCharacter().isEmpty()) {
            this.getRightCharacter().setCharacter(panel.getRightCharacter().getCharacter());
            this.getRightCharacter().setEmpty(false);
        }else if(panel.getRightCharacter().getPoseString().equals("default.png"))
        {
            this.getLeftCharacter().setDefaultPose();
        }

        if (!panel.getLeftCharacter().isEmpty()) {
            this.getLeftCharacter().setCharacter(panel.getLeftCharacter().getCharacter());
            this.getLeftCharacter().setEmpty(false);
        }else if(panel.getLeftCharacter().getPoseString().equals("default.png"))
        {
            this.getLeftCharacter().setDefaultPose();
        }
        this.getLeftCharacter().setPoseString(panel.getLeftCharacter().getPoseString());
        this.getRightCharacter().setPoseString(panel.getRightCharacter().getPoseString());

        this.getRightCharacter().setDefaultOrientation(panel.getRightCharacter().isDefaultOrientation());
        this.getLeftCharacter().setDefaultOrientation(panel.getLeftCharacter().isDefaultOrientation());

        this.getTopNarration().setText(panel.getTopNarration().getText());
        this.getTopNarration().setTextSize(panel.getTopNarration().getTextSize());
        this.getBottomNarration().setText(panel.getBottomNarration().getText());
        this.getBottomNarration().setTextSize(panel.getBottomNarration().getTextSize());

        if( panel.getLeftSpeechBubble().getBubbleType().equals("speech"))
            this.getLeftSpeechBubble().setSpeech();
        else if(panel.getLeftSpeechBubble().getBubbleType().equals("thought"))
            this.getLeftSpeechBubble().setThought();
        this.getLeftSpeechBubble().getText().setText(panel.getLeftSpeechBubble().getText().getText());
        this.getLeftSpeechBubble().setTextSize(panel.getLeftSpeechBubble().getTextSize());

        if( panel.getRightSpeechBubble().getBubbleType().equals("speech"))
            this.getRightSpeechBubble().setSpeech();
        else if(panel.getRightSpeechBubble().getBubbleType().equals("thought"))
            this.getRightSpeechBubble().setThought();
        this.getRightSpeechBubble().getText().setText(panel.getRightSpeechBubble().getText().getText());
        this.getRightSpeechBubble().setTextSize(panel.getRightSpeechBubble().getTextSize());

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

    /**
     * Fills up the pane with data
     */
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

    /**
     * Change the size of the comic panels
     */
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

    /**
     * Save the panel as a PNG file
     * @param name file name
     * @param fileLocation directory
     */
    public void saveAsPng(String name, String fileLocation) {
        ComicPane clone = new ComicPane();
        clone.clone(this);
        WritableImage image = this.snapshot(new SnapshotParameters(), null);
        try {
            File file = new File(fileLocation + "\\" + name);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            System.out.println("Image not found");
        }
    }
}
