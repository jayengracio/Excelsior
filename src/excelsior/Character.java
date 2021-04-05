package excelsior;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class Character extends ImageView {
    private Image character = null;
    private boolean defaultOrientation = true;

    private final Color defaultHairColour = Color.web("#F9FF00");
    private final Color defaultSkinColour = Color.web("#FFE8D8");
    private Color skinColour = Color.web("#FFE8D8");
    private Color hairColour = Color.web("#F9FF00");
    private Color femaleHairColour = Color.web("#F0FF00");
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
        this.setScaleX(isDefaultOrientation() ? -1 : 1);
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
        updateImage();
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
        updateImage();
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

    //called after an update to update displayed image
    private void updateImage() {
        Image updated = character;
        updated = changeGender(updated);
        updated = makeHairColourChange(updated);
        updated = makeSkinColourChange(updated);
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

    private Image makeSkinColourChange(Image inputImage) {
        int W = (int) inputImage.getWidth();
        int H = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();

        int skinRed = (int) Math.round(defaultSkinColour.getRed() * 255);
        int skinGreen = (int) Math.round(defaultSkinColour.getGreen() * 255);
        int skinBlue = (int) Math.round(defaultSkinColour.getBlue() * 255);

        int newRed = (int) Math.round(skinColour.getRed() * 255);
        int newGreen = (int) Math.round(skinColour.getGreen() * 255);
        int newBlue = (int) Math.round(skinColour.getBlue() * 255);

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {

                Color current = reader.getColor(x, y);

                int curPixelRed = (int) Math.round(current.getRed() * 255);
                int curPixelGreen = (int) Math.round(current.getGreen() * 255);
                int curPixelBlue = (int) Math.round(current.getBlue() * 255);

                if (curPixelRed >= skinRed - 9 && curPixelBlue == skinBlue && curPixelGreen == skinGreen && curPixelRed <= skinRed) {
                    //System.out.println(curPixelRed-red);
                    //System.out.println(curPixelGreen-green);
                    //System.out.println(curPixelBlue-blue);
                    writer.setColor(x, y, Color.rgb(curPixelRed + (newRed - skinRed), curPixelGreen + (newGreen - skinGreen), curPixelBlue + (newBlue - skinBlue)));
                } else {
                    writer.setColor(x, y, current);
                }
            }
        }
        return outputImage;
    }

    private Image changeGender(Image inputImage) {
        if (!this.isFemale) {
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

                    if (curPixelRed >= 240 && curPixelGreen == 255 && curPixelRed <= 255 && !current.equals(defaultHairColour)) { //hair
                        change(writer, x, y, current, Color.rgb(255, 255, 255));
                    } else if (curPixelRed == 255 && curPixelGreen <= 232 && curPixelBlue <= 216) { //lips
                        change(writer, x, y, current, Color.rgb(255, 232, 216));
                    } else if (curPixelRed >= 236 && curPixelGreen >= 180 && !current.equals(defaultHairColour)) { //pigtails
                        change(writer, x, y, current, Color.rgb(255, 255, 255));
                    } else {
                        writer.setColor(x, y, current);
                    }
                }
            }
            return outputImage;
        }
        else
            return this.character;
    }

    // Change gender helper function
    private void change(PixelWriter writer, int x, int y, Color current, Color newColour) {
        if(current.equals(Color.web("#FBFF5E")) || current.equals(Color.web("#FDFC9E")))            //yellow exemptions
            writer.setColor(x, y, current);
        else if(current.equals(Color.web("#FF4D00")) || current.equals(Color.web("#FFBC89")))           //red exemptions
            writer.setColor(x, y, current);
        else
            writer.setColor(x, y, newColour);
    }



    /*private Image changeGender(Image inputImage) {
        int skinRed = (int) Math.round(skinColour.getRed() * 255);
        int skinGreen = (int) Math.round(skinColour.getGreen() * 255);
        int skinBlue = (int) Math.round(skinColour.getBlue() * 255);

        Color bowtie = Color.web("#ECB4B5");
        Color lipstick = Color.web("FF0000");

        Color noFemaleHair = Color.web("#FEFEFE");
        Color noBowtie = Color.web("#FDFDFD");
        Color noLipstick = Color.rgb(skinRed, skinGreen, skinBlue);

        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage output = new WritableImage(width, height);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if (!this.isFemale) {
                    change(femaleHairColour, bowtie, lipstick, reader, writer, y, x, noFemaleHair, noBowtie, noLipstick);
                } else {
                    return this.character;
                }
            }
        }
        return output;
    }

    // Change gender helper function
    private void change(Color noFemaleHair, Color noBowtie, Color noLipstick, PixelReader reader, PixelWriter writer,
                        int y, int x, Color femaleHairColour, Color bowtie, Color lipstick) {
        if (reader.getColor(y, x).equals(noFemaleHair)) writer.setColor(y, x, femaleHairColour);
        else if (reader.getColor(y, x).equals(noBowtie)) writer.setColor(y, x, bowtie);
        else if (reader.getColor(y, x).equals(noLipstick)) writer.setColor(y, x, lipstick);
        else writer.setColor(y, x, reader.getColor(y, x));
    }*/
}
