package excelsior;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class Character extends ImageView {
    private Image character = null;
    private boolean defaultOrientation = true;
    private int skinColour = 1;
    private int hairColour = 1;
    private boolean empty = true;

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

    public int getSkinColour() {
        return skinColour;
    }

    public void setSkinColour(int skinColour) {
        this.skinColour = skinColour;
    }

    public int getHairColour() {
        return hairColour;
    }

    public void setHairColour(int hairColour) {
        this.hairColour = hairColour;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void toMale() {
        Image temp = character;

        int width = (int) temp.getWidth();
        int height = (int) temp.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelReader reader = temp.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        Color bowtie = Color.web("#ECB4B5");
        Color hair = Color.web("#F0FF00");
        Color background = Color.web("#FFFFFF");
        Color lipstick = Color.web("FF0000");
        Color skin = Color.web("#FFE8D8");

        for (int w = 0; w < width; w++) {
            for (int x = 0; x < height; x++) {
                if (reader.getColor(w, x).equals(hair) || reader.getColor(w, x).equals(bowtie)) {
                    writer.setColor(w, x, background);
                } else if (reader.getColor(w, x).equals(lipstick)) {
                    writer.setColor(w, x, skin);
                } else {
                    writer.setColor(w, x, reader.getColor(w, x));
                }
            }
        }
        this.setCharacter(output);
    }

    public void toFemale() {

    }
}
