package excelsior;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ButtonController {
    private final DropShadow dropShadow = new DropShadow();
    private final UI ui;
    private boolean isSpeechBubble = true;

    public ButtonController(UI ui) {
        this.ui = ui;
    }

    public boolean isSpeechBubble() {
        return isSpeechBubble;
    }

    /**
     * Gives all the buttons in the button box of UI its functionalities
     */
    public void start() {
        ui.getButtonBox().getChildren().add(0, new IconButtons("Left.png"));
        ui.getButtonBox().getChildren().add(1, new IconButtons("Right.png"));
        ui.getButtonBox().getChildren().add(2, new IconButtons("Flip.png"));
        ui.getButtonBox().getChildren().add(3, new IconButtons("Gender.png"));
        ui.getButtonBox().getChildren().add(4, new IconButtons("Speech Bubble.png"));
        ui.getButtonBox().getChildren().add(5, new IconButtons("Top Narration.png"));
        ui.getButtonBox().getChildren().add(6, new IconButtons("Thought Bubble.png"));
        ui.getButtonBox().getChildren().add(7, new IconButtons("Bot Narration.png"));

        this.leftCharacterButton(ui.getButtonBox().getChildren().get(0));
        this.rightCharacterButton(ui.getButtonBox().getChildren().get(1));
        this.switchOrientationButton(ui.getButtonBox().getChildren().get(2));
        this.changeGenderButton(ui.getButtonBox().getChildren().get(3));
        this.speechBubbleButton(ui.getButtonBox().getChildren().get(4));
        this.topNarrationButton(ui.getButtonBox().getChildren().get(5));
        this.thoughtBubbleButton(ui.getButtonBox().getChildren().get(6));
        this.botNarrationButton(ui.getButtonBox().getChildren().get(7));
    }

    /**
     * Adds button event handler for selecting the left character in the work panel when clicked.
     * Its functionality to changing the character's pose when this button is already active.
     *
     * @param button node of the left character in the button box
     */
    private void leftCharacterButton(Node button) {
        Button cur = (Button) button;
        Button right = (Button) ui.getButtonBox().getChildren().get(1);
        buttonTooltip(button, "Select Character");

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == ui.getWorkPanel().getLeftCharacter()) {
                ui.getCharPosesPopup().show(ui.getPrimaryStage());
            } else {
                ui.setSelectedCharacter(ui.getWorkPanel().getLeftCharacter());
                ui.getWorkPanel().getLeftCharacter().setEffect(dropShadow);
                ui.getWorkPanel().getRightCharacter().setEffect(null);

                ImageView graphic = new ImageView(new Image("/Icons/Right.png"));
                graphic.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px;");
                graphic.setFitWidth(90);
                graphic.setFitHeight(90);
                right.setGraphic(graphic);

                createCharacterButtonTooltip(cur, right);
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
    private void rightCharacterButton(Node button) {
        Button cur = (Button) button;
        Button left = (Button) ui.getButtonBox().getChildren().get(0);
        buttonTooltip(button, "Select Character");

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (ui.getSelectedCharacter() == ui.getWorkPanel().getRightCharacter()) {
                ui.getCharPosesPopup().show(ui.getPrimaryStage());
            } else {
                ui.setSelectedCharacter(ui.getWorkPanel().getRightCharacter());
                ui.getWorkPanel().getRightCharacter().setEffect(dropShadow);
                ui.getWorkPanel().getLeftCharacter().setEffect(null);

                ImageView graphic = new ImageView(new Image("/Icons/Left.png"));
                graphic.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px;");
                graphic.setFitWidth(90);
                graphic.setFitHeight(90);
                left.setGraphic(graphic);

                createCharacterButtonTooltip(cur, left);
            }
            button.setEffect(dropShadow);
        });
    }

    /**
     * Adds button event handler for flipping the selected character on its x axis when clicked
     *
     * @param button node for flip orientation in the button box
     */
    private void switchOrientationButton(Node button) {
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
    private void changeGenderButton(Node button) {
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
    private void speechBubbleButton(Node button) {
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
    private void thoughtBubbleButton(Node button) {
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
    private void topNarrationButton(Node button) {
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
    private void botNarrationButton(Node button) {
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
    private void buttonTooltip(Node button, String text) {
        Button castedButton = (Button) button;
        castedButton.setTooltip(new Tooltip(text));
        castedButton.getTooltip().setShowDelay(Duration.seconds(0.1));
        castedButton.getTooltip().setStyle("-fx-font-size: 12;");
    }

    /**
     * Help function to create character button as well as the change character pose button tooltips
     *
     * @param current button
     * @param next    button
     */
    public void createCharacterButtonTooltip(Button current, Button next) {
        if (next == null) {
            current.getTooltip().setText("Select Character");
        } else {
            ImageView graphic = new ImageView(new Image("/Icons/SelectPose.png"));
            graphic.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px;");
            graphic.setFitWidth(90);
            graphic.setFitHeight(90);
            current.setGraphic(graphic);

            current.setTooltip(new Tooltip("Change character pose"));
            next.setTooltip(new Tooltip("Select Character"));
            current.getTooltip().setShowDelay(Duration.seconds(0.1));
            next.getTooltip().setShowDelay(Duration.seconds(0.1));
            current.getTooltip().setStyle("-fx-font-size: 12;");
            next.getTooltip().setStyle("-fx-font-size: 12;");
        }

    }
}
