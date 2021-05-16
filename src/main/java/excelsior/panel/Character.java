package excelsior.panel;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class Character extends ImageView {
    private final Color defaultHairColour = Color.web("#F9FF00");
    private final Color defaultSkinColour = Color.web("#FFE8D8");
    private final Color defaultLipColour = Color.web("#FF0000");
    private Image character = null;
    private boolean defaultOrientation = true;
    private Color skinColour = Color.web("#FFE8D8");
    private Color hairColour = Color.web("#F9FF00");
    private Color lipColour = Color.web("#FF0000");
    private String poseString;
    private boolean empty = true;
    private boolean isFemale = true;

    public Character() {
        this.setFitHeight(220);
        this.setFitWidth(300);
        this.preserveRatioProperty().setValue(true);
        this.prefWidth(300);
        this.prefHeight(220);
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

    public String getPoseString() {
        if (poseString == null) return "#empty.png";
        else return poseString;
    }

    public void setPoseString(String poseString) {
        this.poseString = poseString;
    }

    public Color getLipColour() {
        return lipColour;
    }

    public void setLipColour(Color lipColour) {
        this.lipColour = checkDefaultColour(lipColour, 0);
        updateImage();
    }

    public Color getDefaultLipColour() {
        return defaultLipColour;
    }

    public boolean isDefaultOrientation() {
        return defaultOrientation;
    }

    public void setDefaultOrientation(boolean defaultOrientation) {
        if(!isEmpty()) {
            this.defaultOrientation = defaultOrientation;
            this.setScaleX(isDefaultOrientation() ? 1 : -1);
        }
    }

    public String getFacing() {
        if (defaultOrientation) return "right";
        else return "left";
    }

    public Color getHairColour() {
        return hairColour;
    }

    public void setHairColour(Color hairColour) {
        this.hairColour = checkDefaultColour(hairColour,2);
        updateImage();
    }

    public Color getSkinColour() {
        return skinColour;
    }

    public void setSkinColour(Color skinColour) {
        this.skinColour = checkDefaultColour(skinColour, 1);
        updateImage();
    }

    public String getHairColourAsHex() {
        int r = (int) (hairColour.getRed() * 255);
        int g = (int) (hairColour.getGreen() * 255);
        int b = (int) (hairColour.getBlue() * 255);

        return String.format("#%02X%02X%02X", r, g, b);
    }

    public String getSkinColourAsHex() {
        int r = (int) (skinColour.getRed() * 255);
        int g = (int) (skinColour.getGreen() * 255);
        int b = (int) (skinColour.getBlue() * 255);

        return String.format("#%02X%02X%02X", r, g, b);
    }

    public String getLipColourAsHex() {
        int r = (int) (lipColour.getRed() * 255);
        int g = (int) (lipColour.getGreen() * 255);
        int b = (int) (lipColour.getBlue() * 255);

        return String.format("#%02X%02X%02X", r, g, b);
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
        updateImage();
    }

    public String getGender() {
        if (isFemale) return "female";
        else return "male";
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    /**
     * Called after an update to update displayed image
     */
    private void updateImage() {
        Image updated = character;
        updated = changeGender(updated);
        updated = makeLipColourChange(updated);
        updated = makeHairColourChange(updated);
        updated = makeSkinColourChange(updated);
        this.setImage(updated);
    }

    /**
     * Changes the hair colour
     *
     * @param inputImage Character image to modify
     * @return the updated image
     */
    private Image makeHairColourChange(Image inputImage) {
        return updateColour(inputImage, defaultHairColour, hairColour);
    }

    /**
     * Changes the skin colour
     *
     * @param inputImage Character image to modify
     * @return the updated image
     */
    private Image makeSkinColourChange(Image inputImage) {
        return updateColour(inputImage, defaultSkinColour, skinColour);
    }

    /**
     * Changes the lip colour
     *
     * @param inputImage Character image to modify
     * @return the updated image
     */
    private Image makeLipColourChange(Image inputImage) {
        return updateColour(inputImage, defaultLipColour, lipColour);
    }

    /**
     * Actual function to update the colour of a character's feature
     *
     * @param inputImage    Character image to modify
     * @param defaultColour The default colour of the character's feature
     * @param newColour     The new colour
     * @return the updated image with a new colour of a character's feature
     */
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
        try {
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    Color current = reader.getColor(x, y);

                    int curPixelRed = (int) Math.round(current.getRed() * 255);
                    int curPixelGreen = (int) Math.round(current.getGreen() * 255);
                    int curPixelBlue = (int) Math.round(current.getBlue() * 255);

                    //removes lip Anti Aliasing to improve look of brighter coloured lips
                    if (defaultColour == defaultLipColour && curPixelRed == 255 && curPixelGreen < 232 && curPixelBlue < 216 && !current.equals(defaultLipColour)) {
                        change(writer, x, y, current, defaultSkinColour);
                    } else if (curPixelRed >= skinRed - 9 && curPixelBlue == skinBlue && curPixelGreen == skinGreen && curPixelRed <= skinRed) {
                        writer.setColor(x, y, Color.rgb(curPixelRed + (newRed - skinRed), curPixelGreen + (newGreen - skinGreen), curPixelBlue + (newBlue - skinBlue)));
                    } else {
                        writer.setColor(x, y, current);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Colour error");
        }
        return outputImage;
    }

    /**
     * Function to change the character's gender.
     *
     * @param inputImage Character image to modify
     * @return the updated image with no long hair and pigtails
     */
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
                    } else if (curPixelRed == 255 && curPixelGreen < 232 && curPixelBlue < 216 && !current.equals(defaultLipColour)) { //lip aa for male
                        change(writer, x, y, current, defaultSkinColour);
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

    /**
     * Used to change sizes for comic panels view
     */
    public void minimise() {
        this.setFitHeight(100);
        this.setFitWidth(135);
        this.prefWidth(135);
        this.prefHeight(100);
    }

    /**
     * Resets all character options to their default
     */
    public void reset() {
        skinColour = defaultSkinColour;
        hairColour = defaultHairColour;
        lipColour = defaultLipColour;
        this.setDefaultOrientation(true);
        isFemale = true;
        poseString = "#empty.png";
        setCharacterPose("#empty.png");
    }

    private Color checkDefaultColour(Color col, int ignore){
        if(col == defaultLipColour && ignore != 0)
            return Color.web("#FF0002");
        if(col == defaultSkinColour && ignore != 1)
            return Color.web("#FFE8DA");
        if(col == defaultHairColour && ignore != 2)
            return Color.web("#F9FF02");

        return col;
    }

    public void setDefaultPose(){
        String address = "/Icons/default.png";
        setCharacter(address);
        setPoseString("default.png");
    }
}
