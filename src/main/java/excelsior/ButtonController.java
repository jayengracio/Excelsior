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

    // button function to select left character and/or select pose
    public void leftCharacterButton(Node button) {
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

    // button function to select right character and/or select pose
    public void rightCharacterButton(Node button) {
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

    // adds event handler to flip currently selected character on x axis when button input is clicked
    public void switchOrientationButton(Node button) {
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

    // button function to change character's gender
    public void changeGenderButton(Node button) {
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

    // button function to insert a speech bubble
    public void speechBubbleButton(Node button) {
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

    // button function to insert a thought bubble
    public void thoughtBubbleButton(Node button) {
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

    // button function to insert the top narration text
    public void topNarrationButton(Node button) {
        buttonTooltip(button, "Add the top narration of the panel");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            ui.getTopNarrationInput().show(ui.getPrimaryStage());
            button.setEffect(dropShadow);
            ui.getWorkPanel().setEditMode(true);
        });
    }

    // button function to insert the bottom narration text
    public void botNarrationButton(Node button) {
        buttonTooltip(button, "Add the bottom narration of the panel");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            ui.getBottomNarrationInput().show(ui.getPrimaryStage());
            button.setEffect(dropShadow);
            ui.getWorkPanel().setEditMode(true);
        });
    }

    // insert button functionalities
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

    // help function to help create tooltips for buttons
    private void buttonTooltip(Node button, String text) {
        Button castedButton = (Button) button;
        castedButton.setTooltip(new Tooltip(text));
        castedButton.getTooltip().setShowDelay(Duration.seconds(0.1));
        castedButton.getTooltip().setStyle("-fx-font-size: 12;");
    }

    // help function to create the tooltips for character button
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
