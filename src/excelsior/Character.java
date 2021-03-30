package excelsior;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class Character extends ImageView {
    private Image character = null;
    private boolean defaultOrientation = true;

    private final Color bowtie = Color.web("#ECB4B5");
    private final Color lipstick = Color.web("FF0000");
    private Color skinColour = Color.web("#FFE8D8");
    private Color hairColour = Color.web("#F0FF00");

    private boolean empty = true;
    private boolean isFemale = true;

    public Character(){
        this.setFitHeight(240);
        this.setFitWidth(300);
        this.preserveRatioProperty().setValue(true);
        this.prefWidth(300);
        this.prefHeight(240);
        setCharacter("/Character_Images/#empty.png");    //setting a character as a default for testing
    }

    public void setCharacterPose(String character){
        String address = "/Character_Images/" + character ;
        setCharacter(address);
        if(!character.equals("#empty.png"))
        {
            setEmpty(false);
        }
        else{
            setEmpty(true);
        }
    }
    public Image getCharacter() {
        return character;
    }

    public void setCharacter(Image character) {
        this.character = character;
        this.setImage(this.character);
    }

    public void setCharacter(String url){
        this.character = new Image(url);
        this.setImage(this.character);
    }

    public boolean isDefaultOrientation() {
        return defaultOrientation;
    }

    public void flipDefaultOrientation()
    {
        this.setScaleX(isDefaultOrientation() ? -1 : 1);
        setDefaultOrientation(!isDefaultOrientation());
    }
    public void setDefaultOrientation(boolean defaultOrientation) {
        this.defaultOrientation = defaultOrientation;
    }

    public Color getSkinColour() {
        return skinColour;
    }

    public void setSkinColour(Color skinColour) {
        this.skinColour = skinColour;
    }

    public Color getHairColour() {
        return hairColour;
    }

    public void setHairColour(Color hairColour) {
        this.hairColour = hairColour;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isFemale() { return isFemale; }

    public void setFemale(boolean female) { isFemale = female; }

    public void toMale() {
        this.setFemale(false);

        int width = (int) this.character.getWidth();
        int height = (int) this.character.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelReader reader = this.character.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        Color hairBG = Color.web("#FEFEFE");
        Color bowtieBG = Color.web("#FDFDFD");
        Color lipstickBG = Color.web("#FFE8D7");

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if (reader.getColor(y, x).equals(hairColour)) { writer.setColor(y, x, hairBG); }
                else if (reader.getColor(y, x).equals(bowtie)) { writer.setColor(y, x, bowtieBG); }
                else if (reader.getColor(y, x).equals(lipstick)) { writer.setColor(y, x, lipstickBG); }
                else { writer.setColor(y, x, reader.getColor(y, x)); }
            }
        }
        this.setCharacter(output);
    }

    public void toFemale() {
        this.setFemale(true);

        int width = (int) this.character.getWidth();
        int height = (int) this.character.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelReader reader = this.character.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        Color hairBG = Color.web("#FEFEFE");
        Color bowtieBG = Color.web("#FDFDFD");
        Color lipstickBG = Color.web("#FFE8D7");

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if (reader.getColor(y, x).equals(hairBG)) { writer.setColor(y, x, hairColour); }
                else if (reader.getColor(y, x).equals(bowtieBG)) { writer.setColor(y, x, bowtie); }
                else if (reader.getColor(y, x).equals(lipstickBG)) { writer.setColor(y, x, lipstick); }
                else { writer.setColor(y, x, reader.getColor(y, x)); }
            }
        }
        this.setCharacter(output);
    }
}
