package excelsior;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class Character extends ImageView {
    private Image character = null;
    private Image updated;
    private boolean defaultOrientation = true;

    private final Color defaultHairColour = Color.web("#F9FF00");
    private final Color defaultSkinColour = Color.web("#FFE8D8");
    private final Color defaultLipColour = Color.web("#FF0000");
    private Color skinColour = Color.web("#FFE8D8");
    private Color hairColour = Color.web("#F9FF00");
    private Color lipColour = Color.web("#FF0000");
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
        setEmpty(character.equals("#empty.png"));
    }

    public void flipDefaultOrientation() {
        setDefaultOrientation(!isDefaultOrientation());
    }

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

    public Color getLipColour() {
        return lipColour;
    }

    public void setLipColour(Color lipColour) {
        this.lipColour = lipColour;
        updateImage();
    }

    public Color getDefaultLipColour() {
        return defaultLipColour;
    }

    public boolean isDefaultOrientation() {
        return defaultOrientation;
    }

    public void setDefaultOrientation(boolean defaultOrientation) {
        this.defaultOrientation = defaultOrientation;
        this.setScaleX(isDefaultOrientation() ? 1 : -1);
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
        updateImage();
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
        updateImage();
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    //called after an update to update displayed image
    private void updateImage() {
        updated = character;
        updated = makeLipColourChange(updated);
        updated = changeGender(updated);
        updated = makeHairColourChange(updated);
        updated = makeSkinColourChange(updated);
        this.setImage(updated);
    }

    public Image getUpdatedImage() {
        return updated;
    }

    // changes the hair colour
    private Image makeHairColourChange(Image inputImage) {
        return updateColour(inputImage, defaultHairColour, hairColour);
    }

    // changes the skin colour
    private Image makeSkinColourChange(Image inputImage) {
        return updateColour(inputImage, defaultSkinColour, skinColour);
    }

    private Image makeLipColourChange(Image inputImage) {
        return updateColour(inputImage, defaultLipColour, lipColour);
    }

    // helper function to change skin or hair colour
    private Image updateColour(Image inputImage, Color defaultColour, Color newColour) {
        int W = (int) inputImage.getWidth();
        int H = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();

        int skinRed = (int) Math.round(defaultColour.getRed() * 255);
        int skinGreen = (int) Math.round(defaultColour.getGreen() * 255);
        int skinBlue = (int) Math.round(defaultColour.getBlue() * 255);

        int newRed = (int) Math.round(newColour.getRed() * 255);
        int newGreen = (int) Math.round(newColour.getGreen() * 255);
        int newBlue = (int) Math.round(newColour.getBlue() * 255);

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                Color current = reader.getColor(x, y);

                int curPixelRed = (int) Math.round(current.getRed() * 255);
                int curPixelGreen = (int) Math.round(current.getGreen() * 255);
                int curPixelBlue = (int) Math.round(current.getBlue() * 255);

                if (curPixelRed >= skinRed - 9 && curPixelBlue == skinBlue && curPixelGreen == skinGreen && curPixelRed <= skinRed) {
                    writer.setColor(x, y, Color.rgb(curPixelRed + (newRed - skinRed), curPixelGreen + (newGreen - skinGreen), curPixelBlue + (newBlue - skinBlue)));
                } else {
                    writer.setColor(x, y, current);
                }
            }
        }
        return outputImage;
    }

    //removes female hair, lips and pigtails to change gender
    private Image changeGender(Image inputImage) {
        if (!this.isFemale) {
            int W = (int) inputImage.getWidth();
            int H = (int) inputImage.getHeight();
            WritableImage outputImage = new WritableImage(W, H);
            PixelReader reader = inputImage.getPixelReader();
            PixelWriter writer = outputImage.getPixelWriter();

            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    Color current = reader.getColor(x, y);

                    int curPixelRed = (int) Math.round(current.getRed() * 255);
                    int curPixelGreen = (int) Math.round(current.getGreen() * 255);
                    int curPixelBlue = (int) Math.round(current.getBlue() * 255);

                    if (curPixelRed >= 240 && curPixelGreen == 255 && curPixelRed <= 255 && !current.equals(defaultHairColour)) { //hair
                        change(writer, x, y, current, Color.rgb(255, 255, 255));
                    } else if (curPixelRed == 255 && curPixelGreen <= 232 && curPixelBlue <= 216) { //lips
                        change(writer, x, y, current, Color.rgb(255, 200, 200));
                    } else if (curPixelRed >= 236 && curPixelGreen >= 180 && !current.equals(defaultHairColour)) { //pigtails
                        change(writer, x, y, current, Color.rgb(255, 255, 255));
                    } else {
                        writer.setColor(x, y, current);
                    }
                }
            }
            return outputImage;
        } else
            return this.character;
    }

    // Change gender helper function
    private void change(PixelWriter writer, int x, int y, Color current, Color newColour) {
        if (current.equals(Color.web("#FBFF5E")) || current.equals(Color.web("#FDFC9E")))          //yellow exemptions
            writer.setColor(x, y, current);
        else if (current.equals(Color.web("#FF4D00")) || current.equals(Color.web("#FFBC89")) || current.equals(Color.web("#FFE8D8")))     //red exemptions
            writer.setColor(x, y, current);
        else
            writer.setColor(x, y, newColour);
    }

    //used to change sizes for comic panels view
    public void minimise()
    {
        this.setFitHeight(115);
        this.setFitWidth(135);
        this.prefWidth(135);
        this.prefHeight(115);
    }

    //resets all character options to their default
    public void reset(){
        skinColour = defaultSkinColour;
        hairColour = defaultHairColour;
        defaultOrientation = true;
        isFemale = true;
        setCharacterPose("#empty.png");
    }
}
