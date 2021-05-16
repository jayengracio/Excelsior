package excelsior.gui;

import excelsior.UI;
import excelsior.control.Button;
import excelsior.control.ImageButton;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Controller {
    private final DropShadow dropShadow = new DropShadow();
    private final UI ui;
    private boolean isSpeechBubble = true;

    public Controller(UI ui) {
        this.ui = ui;
    }

    public boolean isSpeechBubble() {
        return isSpeechBubble;
    }

    /**
     * Gives all the buttons in the button box of UI its functionalities
     */
    public void initialize() {
        ui.getButtonBox().getChildren().add(0, new ImageButton("/Icons/","Left.png"));
        ui.getButtonBox().getChildren().add(1, new ImageButton("/Icons/","Right.png"));
        ui.getButtonBox().getChildren().add(2, new ImageButton("/Icons/","Flip.png"));
        ui.getButtonBox().getChildren().add(3, new ImageButton("/Icons/","Gender.png"));
        ui.getButtonBox().getChildren().add(4, new ImageButton("/Icons/","Speech Bubble.png"));
        ui.getButtonBox().getChildren().add(5, new ImageButton("/Icons/","Top Narration.png"));
        ui.getButtonBox().getChildren().add(6, new ImageButton("/Icons/","Thought Bubble.png"));
        ui.getButtonBox().getChildren().add(7, new ImageButton("/Icons/","Bot Narration.png"));

        this.leftCharacterButton((ImageButton) ui.getButtonBox().getChildren().get(0));
        this.rightCharacterButton((ImageButton)ui.getButtonBox().getChildren().get(1));
        this.switchOrientationButton((ImageButton)ui.getButtonBox().getChildren().get(2));
        this.changeGenderButton((ImageButton)ui.getButtonBox().getChildren().get(3));
        this.speechBubbleButton((ImageButton)ui.getButtonBox().getChildren().get(4));
        this.topNarrationButton((ImageButton)ui.getButtonBox().getChildren().get(5));
        this.thoughtBubbleButton((ImageButton)ui.getButtonBox().getChildren().get(6));
        this.botNarrationButton((ImageButton)ui.getButtonBox().getChildren().get(7));
    }

    /**
     * Adds button event handler for selecting the left character in the work panel when clicked.
     * Its functionality to changing the character's pose when this button is already active.
     *
     * @param button node of the left character in the button box
     */
    private void leftCharacterButton(ImageButton button) {
        ImageButton right = (ImageButton) ui.getButtonBox().getChildren().get(1);
        buttonTooltip(button, "Select Character");

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == ui.getWorkPanel().getLeftCharacter()) {
                ui.getCharPosesPopup().show(ui.getPrimaryStage());
            } else {
                ui.setSelectedCharacter(ui.getWorkPanel().getLeftCharacter());
                ui.getWorkPanel().getLeftCharacter().setEffect(dropShadow);
                ui.getWorkPanel().getRightCharacter().setEffect(null);

                right.setImage("/Icons/","Right.png");

                createCharacterButtonTooltip(button, right);
            }
            button.setEffect(dropShadow);
        });
    }

    /**
     * Adds button event handler for selecting the right character in the work panel when clicked.
     * Its functionality to changing the character's pose when this button is already active.
     *
     * @param button node of the right character in the button box
     */
    private void rightCharacterButton(ImageButton button) {
        ImageButton left = (ImageButton) ui.getButtonBox().getChildren().get(0);
        buttonTooltip(button, "Select Character");

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == ui.getWorkPanel().getRightCharacter()) {
                ui.getCharPosesPopup().show(ui.getPrimaryStage());
            } else {
                ui.setSelectedCharacter(ui.getWorkPanel().getRightCharacter());
                ui.getWorkPanel().getRightCharacter().setEffect(dropShadow);
                ui.getWorkPanel().getLeftCharacter().setEffect(null);

                left.setImage("/Icons/","Left.png");

                createCharacterButtonTooltip(button, left);
            }
            button.setEffect(dropShadow);
        });
    }

    /**
     * Adds button event handler for flipping the selected character on its x axis when clicked
     *
     * @param button node for flip orientation in the button box
     */
    private void switchOrientationButton(ImageButton button) {
        buttonTooltip(button, "Flip where the character is facing");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == null || ui.getSelectedCharacter().isEmpty()) {
                ui.getCharWarningPopup().show(ui.getPrimaryStage());
            } else {
                ui.getSelectedCharacter().flipDefaultOrientation();
                button.setEffect(dropShadow);
                ui.getWorkPanel().setEditMode(true);
            }
        });
    }

    /**
     * Adds button event handler for changing a character's gender.
     *
     * @param button node for gender in the button box
     */
    private void changeGenderButton(ImageButton button) {
        buttonTooltip(button, "Change the gender of the character");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == null || ui.getSelectedCharacter().isEmpty()) {
                ui.getCharWarningPopup().show(ui.getPrimaryStage());
            } else {
                ui.getSelectedCharacter().setFemale(!ui.getSelectedCharacter().isFemale());
                ui.getWorkPanel().setEditMode(true);
            }
            button.setEffect(dropShadow);
        });
    }

    /**
     * Adds button event handler for inserting a speech bubble for the selected character
     *
     * @param button node for the speech bubble in the button box
     */
    private void speechBubbleButton(ImageButton button) {
        buttonTooltip(button, "Add a speech bubble for the selected character");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == null || ui.getSelectedCharacter().isEmpty()) {
                ui.getCharWarningPopup().show(ui.getPrimaryStage());
            } else {
                isSpeechBubble = true;
                ui.getTextBubbleInput().show(ui.getPrimaryStage());
                button.setEffect(dropShadow);
                ui.getWorkPanel().setEditMode(true);
            }
        });
    }

    /**
     * Adds button event handler for inserting a thought bubble for the selected character
     *
     * @param button node for the thought bubble in the button box
     */
    private void thoughtBubbleButton(ImageButton button) {
        buttonTooltip(button, "Add a thought bubble for the selected character");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == null || ui.getSelectedCharacter().isEmpty()) {
                ui.getCharWarningPopup().show(ui.getPrimaryStage());
            } else {
                isSpeechBubble = false;
                ui.getTextBubbleInput().show(ui.getPrimaryStage());
                button.setEffect(dropShadow);
                ui.getWorkPanel().setEditMode(true);
            }
        });
    }

    /**
     * Adds button event handler to insert the top narration text of the comic panel
     *
     * @param button node for the top narration in the button box
     */
    private void topNarrationButton(ImageButton button) {
        buttonTooltip(button, "Add the top narration of the panel");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            ui.getTopNarrationInput().show(ui.getPrimaryStage());
            button.setEffect(dropShadow);
            ui.getWorkPanel().setEditMode(true);
        });
    }

    /**
     * Adds button event handler to insert the bottom narration text of the comic panel
     *
     * @param button node for the bottom narration in the button box
     */
    private void botNarrationButton(ImageButton button) {
        buttonTooltip(button, "Add the bottom narration of the panel");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            ui.getBottomNarrationInput().show(ui.getPrimaryStage());
            button.setEffect(dropShadow);
            ui.getWorkPanel().setEditMode(true);
        });
    }

    /**
     * Help function to create tooltips for buttons
     *
     * @param button of which to add the tooltip for
     * @param text   for tooltip information
     */
    private void buttonTooltip(Button button, String text) {
        button.setTooltip(new Tooltip(text));
        button.getTooltip().setShowDelay(Duration.seconds(0.1));
        button.getTooltip().setStyle("-fx-font-size: 12;");
    }

    /**
     * Help function to create character button as well as the change character pose button tooltips
     *
     * @param current button
     * @param next    button
     */
    public void createCharacterButtonTooltip(ImageButton current, ImageButton next) {
        if (next == null) {
            current.getTooltip().setText("Select Character");
        } else {
            current.setImage("/Icons/","SelectPose.png");

            current.setTooltip(new Tooltip("Change character pose"));
            next.setTooltip(new Tooltip("Select Character"));
            current.getTooltip().setShowDelay(Duration.seconds(0.1));
            next.getTooltip().setShowDelay(Duration.seconds(0.1));
            current.getTooltip().setStyle("-fx-font-size: 12;");
            next.getTooltip().setStyle("-fx-font-size: 12;");
        }
    }
}
