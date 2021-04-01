package excelsior;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class Character extends ImageView {
    private Image character = null;
    private boolean defaultOrientation = true;

    private final Color bowtie = Color.web("#ECB4B5");
    private final Color lipstick = Color.web("FF0000");
    private Color skinColour = Color.web("#FFE8D8");
    private Color hairColour = Color.web("#F9FF00");
    private Color femaleHairColour = Color.web("#F0FF00");
    private Color defaultHairColour = Color.web("#F9FF00");
    private boolean empty = true;
    private boolean isFemale = true;

    public Character() {
        this.setFitHeight(240);
        this.setFitWidth(300);
        this.preserveRatioProperty().setValue(true);
        this.prefWidth(300);
        this.prefHeight(240);
        setCharacter("/Character_Images/#empty.png");    //setting a character as a default for testing
    }

    public void setCharacterPose(String character) {
        String address = "/Character_Images/" + character;
        setCharacter(address);
        if (!character.equals("#empty.png")) {
            setEmpty(false);
        } else {
            setEmpty(true);
        }
    }

    public void flipDefaultOrientation() {
        this.setScaleX(isDefaultOrientation() ? -1 : 1);
        setDefaultOrientation(!isDefaultOrientation());
    }

    //called after an update to update displayed image
    private void updateImage() {
        Image updated = character;
        updated = makeHairColourChange(updated);
        this.setImage(updated);
    }

    private Image makeHairColourChange(Image inputImage) {
        int W = (int) inputImage.getWidth();
        int H = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();

        int hairRed = (int) Math.round(defaultHairColour.getRed() * 255);
        int hairGreen = (int) Math.round(defaultHairColour.getGreen() * 255);
        int hairBlue = (int) Math.round(defaultHairColour.getBlue() * 255);

        int newRed = (int) Math.round(hairColour.getRed() * 255);
        int newGreen = (int) Math.round(hairColour.getGreen() * 255);
        int newBlue = (int) Math.round(hairColour.getBlue() * 255);

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {

                Color current = reader.getColor(x, y);

                int curPixelRed = (int) Math.round(current.getRed() * 255);
                int curPixelGreen = (int) Math.round(current.getGreen() * 255);
                int curPixelBlue = (int) Math.round(current.getBlue() * 255);

                if (curPixelRed >= hairRed - 9 && curPixelBlue == hairBlue && curPixelGreen == hairGreen && curPixelRed <= hairRed) {
                    //System.out.println(curPixelRed-red);
                    //System.out.println(curPixelGreen-green);
                    //System.out.println(curPixelBlue-blue);
                    writer.setColor(x, y, Color.rgb(curPixelRed + (newRed - hairRed), curPixelGreen + (newGreen - hairGreen), curPixelBlue + (newBlue - hairBlue)));
                } else {
                    writer.setColor(x, y, current);
                }
            }
        }
        return outputImage;
    }

    public void changeGender() {
        this.setFemale(!this.isFemale);

        int skinRed = (int) Math.round(skinColour.getRed() * 255);
        int skinGreen = (int) Math.round(skinColour.getGreen() * 255);
        int skinBlue = (int) Math.round(skinColour.getBlue() * 255);

        Color noFemaleHair = Color.web("#FEFEFE");
        Color noBowtie = Color.web("#FDFDFD");
        Color noLipstick = Color.rgb(skinRed - 1, skinGreen - 1, skinBlue - 1);

        int width = (int) this.character.getWidth();
        int height = (int) this.character.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelReader reader = this.character.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if (this.isFemale) {
                    change(noFemaleHair, noBowtie, noLipstick, reader, writer, y, x, femaleHairColour, bowtie, lipstick);
                } else {
                    change(femaleHairColour, bowtie, lipstick, reader, writer, y, x, noFemaleHair, noBowtie, noLipstick);
                }
            }
        }
        this.setCharacter(output);
    }

    // Change gender helper function
    private void change(Color noFemaleHair, Color noBowtie, Color noLipstick, PixelReader reader, PixelWriter writer,
                        int y, int x, Color femaleHairColour, Color bowtie, Color lipstick) {
        if (reader.getColor(y, x).equals(noFemaleHair)) writer.setColor(y, x, femaleHairColour);
        else if (reader.getColor(y, x).equals(noBowtie)) writer.setColor(y, x, bowtie);
        else if (reader.getColor(y, x).equals(noLipstick)) writer.setColor(y, x, lipstick);
        else writer.setColor(y, x, reader.getColor(y, x));
    }

    /*    public void changeGender() {
        this.setFemale(!this.isFemale);

        Color noFemaleHair = Color.web("#FEFEFE");
        Color noBowtie = Color.web("#FDFDFD");

        Color noLipstick = Color.web("#FFE8D7");

        int width = (int) this.character.getWidth();
        int height = (int) this.character.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelReader reader = this.character.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if (this.isFemale) {
                    if (reader.getColor(y, x).equals(noFemaleHair)) { writer.setColor(y, x, femaleHairColour); }
                    else if (reader.getColor(y, x).equals(noBowtie)) { writer.setColor(y, x, bowtie); }
                    else if (reader.getColor(y, x).equals(noLipstick)) { writer.setColor(y, x, lipstick); }
                    else { writer.setColor(y, x, reader.getColor(y, x)); }
                }

                else {
                    if (reader.getColor(y, x).equals(femaleHairColour)) { writer.setColor(y, x, noFemaleHair); }
                    else if (reader.getColor(y, x).equals(bowtie)) { writer.setColor(y, x, noBowtie); }
                    else if (reader.getColor(y, x).equals(lipstick)) { writer.setColor(y, x, noLipstick); }
                    else { writer.setColor(y, x, reader.getColor(y, x)); }
                }
            }
        }
        this.setCharacter(output);
    }*/

    public Image getCharacter() {
        return character;
    }

    public void setCharacter(Image character) {
        this.character = character;
        updateImage();
    }

    public void setCharacter(String url) {
        this.character = new Image(url);
        updateImage();
    }

    public boolean isDefaultOrientation() {
        return defaultOrientation;
    }

    public void setDefaultOrientation(boolean defaultOrientation) {
        this.defaultOrientation = defaultOrientation;
    }

    public Color getHairColour() {
        return hairColour;
    }

    public void setHairColour(Color hairColour) {
        this.hairColour = hairColour;
        updateImage();
    }

    public Color getSkinColour() {
        return skinColour;
    }

    public void setSkinColour(Color skinColour) {
        this.skinColour = skinColour;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public Color getFemaleHairColour() {
        return femaleHairColour;
    }

    public void setFemaleHairColour(Color femaleHairColour) {
        this.femaleHairColour = femaleHairColour;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
