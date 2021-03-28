package excelsior;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Character extends ImageView {
    Image character = null;
    boolean defaultOrientation = true;
    int skinColour = 1;
    int hairColour = 1;

    public Character(){
        this.setFitHeight(240);
        this.setFitWidth(300);
        this.preserveRatioProperty().setValue(true);
        this.prefWidth(50);
        this.prefHeight(240);
       // setCharacter("/Character_Images/angry.png");    //setting a character as a default for testing
    }

    public void setCharacterPose(String character){
        String address = "/Character_Images/" + character ;
        setCharacter(address);

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

}
