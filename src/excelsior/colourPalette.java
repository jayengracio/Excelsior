package excelsior;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class colourPalette extends GridPane{

    UI ui;
    int option = 0;
    Label buttonPressed = new Label("Hair Colour");
    IconButtons selected;

    public colourPalette(UI ui) {
        this.ui = ui;
        this.setPadding(new Insets(5));
        this.setPrefSize(450, 500);
        this.setHgap(30);
        this.setAlignment(Pos.TOP_CENTER);
        this.setVgap(15);
        this.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 2;");
        buttonPressed.setAlignment(Pos.CENTER);
        buttonPressed.setStyle("-fx-font-weight: bold; -fx-font-family: 'monospaced'; -fx-font-size: 15");
        this.add(buttonPressed, 1, 0);
        for (int i = 0; i < 12; i++) {
            this.add(getColours(i), 1, i + 1);
        }
        addButtons();
    }

    private void addButtons(){
        IconButtons hairButton = new IconButtons("HairButton.png");
        IconButtons skinButton = new IconButtons("SkinButton.png");
        IconButtons lipButton = new IconButtons("LipButton.png");
        this.add(setButton(0, hairButton), 0, 1, 1, 3);
        this.add(setButton(1, skinButton), 0, 5, 1, 3);
        this.add(setButton(2, lipButton), 0, 9, 1, 3);
        selected = hairButton;
        selected.setStyle("-fx-border-color: #000070; -fx-border-radius: 20px;");
    }

    private IconButtons setButton(int option, IconButtons button){
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == null || ui.getSelectedCharacter().isEmpty()) {
                ui.getCharWarningPopup().show(ui.getPrimaryStage());
            } else {
                this.option = option;

                if(option == 0){
                    buttonPressed.setText("Hair Colour");
                }
                else if(option == 1){
                    buttonPressed.setText("Skin Colour");
                }
                else if(option == 2){
                    buttonPressed.setText("Lip Colour");
                }
                selected.setStyle("-fx-border-color: #000000; -fx-border-radius: 20px;");
                selected = button;
                selected.setStyle("-fx-border-color: #000080; -fx-border-radius: 20px;");
            }
        });
        return button;
    }

    private HBox getColours(int colourInd) {
        HBox choices = new HBox();
        choices.setStyle("-fx-border-color: #000000;");
        choices.setPrefSize(100, 20);

        //each row in this colour array will represent on bar of similar shade colours aka black/grays in one greens in another
        //colour choice constraints, cannot have r value below 9 as male hair is always 9 r val higher than hair colour
        String[][] colours = {{"#0A0A0A", "#2B2B2B", "#3B3B3B", "#474747", "#575757"},
                {"#6B6B6B", "#7F7F7F", "#989898", "#B7B7B7", "#C5C5C5"},
                {"#342316", "#463417", "#624F1A", "#776035", "#9D846A"},
                {"#5F4C46", "#947D76", "#CBAEA3", "#FFE8D9", "#FFF3EE"},
                {"#741E01", "#981D01", "#CB443D", "#DD5B55", "#EF6D67"},
                {"#B36F00", "#CE8400", "#FE9F00", "#FEAF57", "#FED884"},
                {"#979E00", "#CBD200", "#F9FF01", "#DBFF69", "#E6FFAE"},
                {"#0F4217", "#12621E", "#17962C", "#1FFF43", "#7CFF76"},
                {"#156363", "#189797", "#1ACECE", "#1BFFFF", "#71FFEC"},
                {"#09055F", "#0B0690", "#0C06E2", "#4632FF", "#6395FF"},
                {"#420057", "#570072", "#7A0097", "#9611B8", "#D556FF"},
                {"#7F0065", "#AC008E", "#CE00A7", "#FF00D2", "#FF75D9"}};

        for (int i = 0; i < 5; i++) {
            ColourBox cur = new ColourBox(colours[colourInd][i]);
            colourChange(cur);
            choices.getChildren().add(i, cur);
        }
        return choices;
    }

    private void colourChange(Node button) {
        ColourBox cur = (ColourBox) button;
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == null || ui.getSelectedCharacter().isEmpty()) {
                ui.getCharWarningPopup().show(ui.getPrimaryStage());
            } else {
                if(option == 0)
                    ui.getSelectedCharacter().setHairColour(cur.getBackground());
                else if(option == 1)
                    ui.getSelectedCharacter().setSkinColour(cur.getBackground());
                else if(option == 2)
                    ui.getSelectedCharacter().setLipColour(cur.getBackground());
                if (!ui.getWorkPanel().getLeftCharacter().isEmpty() || !ui.getWorkPanel().getRightCharacter().isEmpty()) {
                    ui.getWorkPanel().setEditMode(true);
                }
            }
        });
    }
}
