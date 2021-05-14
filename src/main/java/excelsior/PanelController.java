package excelsior;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Optional;

public class PanelController {
    private final UI ui;
    private final ComicPane workPanel;
    private final HBox comicPanels;
    private final MenuItem delete = new MenuItem("Delete Panel");
    private final MenuItem moveRight = new MenuItem("Move Panel Right");
    private final MenuItem moveLeft = new MenuItem("Move Panel Left");
    private ComicPane selectedPanel;

    public PanelController(UI ui) {
        this.ui = ui;
        this.workPanel = ui.getWorkPanel();
        this.comicPanels = ui.getComicPanels();
        moveLeft.setDisable(true);
        moveRight.setDisable(true);
    }

    /**
     * Enables the delete button
     */
    public void disableDelete() {
        this.delete.setDisable(true);
    }

    public void disableMove() {
        this.moveLeft.setDisable(true);
        this.moveRight.setDisable(true);
    }

    /**
     * Disables the delete button if there are no saved panels in the comic strip
     */
    public void checkDeleteAvailability() {
        if (!ui.getComicPanels().getChildren().isEmpty()) {
            this.delete.setDisable(false);
        }
    }

    /**
     * Clears the working panel
     */
    public void clearWorkPanel() {
        unselectCurrentPanel();
        ui.resetAppFace();
        selectedPanel = null;
    }

    /**
     * This gives saved panels functionalities in the form of event handlers. Clicking a saved panel will select it.
     * Selecting a different panel while there are active unsaved changes will prompt an alert warning.
     *
     * @param panel in the comic strip
     */
    public void selectComicPanel(ComicPane panel) {
        panel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            delete.setDisable(false);
            if (!workPanel.isInEditMode()) {
                selectOtherPanel(panel);
            } else {
                Alert alert = changesAlert();
                ButtonType save = alert.getButtonTypes().get(0);
                ButtonType cont = alert.getButtonTypes().get(1);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == save) {
                        workPanel.setEditMode(false);
                        saveComicPanel();
                        selectOtherPanel(panel);
                    } else if (result.get() == cont) {
                        workPanel.setEditMode(false);
                        selectOtherPanel(panel);
                    }
                }
            }
        });
    }

    /**
     * Creates the menu that controls saving, creating and deleting panels
     *
     * @return the panel menu with items that have key bindings
     */
    public Menu PanelMenu() {
        Menu menu = new Menu("Panel");

        MenuItem create = new MenuItem("New Panel");
        MenuItem save = new MenuItem("Save Panel");

        KeyCombination newPanelKeyBinding = new KeyCodeCombination(KeyCode.N, KeyCombination.SHIFT_DOWN);
        KeyCombination SaveKeyBinding = new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN);
        KeyCombination DeleteKeyBinding = new KeyCodeCombination(KeyCode.DELETE);
        KeyCombination MoveRightKey = new KeyCodeCombination(KeyCode.D);
        KeyCombination MoveLeftKey = new KeyCodeCombination(KeyCode.A);

        create.setAccelerator(newPanelKeyBinding);
        save.setAccelerator(SaveKeyBinding);
        delete.setAccelerator(DeleteKeyBinding);
        moveRight.setAccelerator(MoveRightKey);
        moveLeft.setAccelerator(MoveLeftKey);

        menu.getItems().addAll(create, delete, moveRight, moveLeft, save);

        delete.setDisable(true);

        save.setOnAction(actionEvent -> {
            this.saveComicPanel();
            checkDeleteAvailability();
        });

        delete.setOnAction(actionEvent -> {
            this.deleteComicPanel();
            if (comicPanels.getChildren().isEmpty()) delete.setDisable(true);
            if (ui.getSelectedCharacter() == null) delete.setDisable(true);
        });

        create.setOnAction(actionEvent -> {
            this.newComicPanel();
            this.disableMove();
            if (ui.getSelectedCharacter() == null) delete.setDisable(true);
        });

        moveRight.setOnAction(actionEvent -> {
            movePanelRight();
        });

        moveLeft.setOnAction(actionEvent -> {
            movePanelLeft();
        });

        return menu;
    }

    /**
     * Function to save a panel. It clones the work panel into a new ComicPane and adds it to the comic panels.
     * Saving a selected panel will overwrite that panel.
     */
    private void saveComicPanel() {
        ComicPane newPanel = new ComicPane();

        newPanel.setTo(workPanel, false);
        selectComicPanel(newPanel);

        if (!comicPanels.getChildren().contains(selectedPanel)) {
            comicPanels.getChildren().add(newPanel);
        } else {
            int index = comicPanels.getChildren().indexOf(selectedPanel);
            comicPanels.getChildren().set(index, newPanel);
        }

        ui.resetAppFace();
    }

    /**
     * Function to delete a panel. Will prompt a warning alert asking users to confirm deletion.
     */
    private void deleteComicPanel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Do you wish to delete this panel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            comicPanels.getChildren().remove(selectedPanel);
            ui.resetAppFace();
            disableMove();
        }
    }

    /**
     * Function to bring a saved panel to the working panel for editing
     */
    private void editComicPanel() {
        ui.resetAppFace();
        workPanel.setTo(selectedPanel, true);
    }

    /**
     * Function to create a new comic panel. This doubles as the "unselect" panel.
     * Switching panels while there are active changes prompts a warning.
     */
    public void newComicPanel() {
        if (!workPanel.isInEditMode()) {
            clearWorkPanel();
        } else {
            Alert alert = changesAlert();
            ButtonType save = alert.getButtonTypes().get(0);
            ButtonType cont = alert.getButtonTypes().get(1);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == save) {
                    workPanel.setEditMode(false);
                    saveComicPanel();
                    clearWorkPanel();
                } else if (result.get() == cont) {
                    workPanel.setEditMode(false);
                    clearWorkPanel();
                }
            }
        }
    }

    /**
     * Creates an alert for when unsaved changes are detected
     *
     * @return an alert
     */
    private Alert changesAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText(null);
        alert.setContentText("You have unsaved changes in the workspace");

        ButtonType save = new ButtonType("Save");
        ButtonType cont = new ButtonType("Don't Save");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(save, cont, cancel);

        return alert;
    }

    /**
     * Function to select another panel. Selecting another panel will bring it the the working panel for instant editing.
     *
     * @param panel to select
     */
    private void selectOtherPanel(ComicPane panel) {
        DropShadow drop = new DropShadow();
        drop.setSpread(0.30);
        drop.setColor(Color.RED);

        unselectCurrentPanel();
        selectedPanel = panel;
        moveRight.setDisable(false);
        moveLeft.setDisable(false);

        if (comicPanels.getChildren().indexOf(selectedPanel) == 0) {
            moveLeft.setDisable(true);
        }

        if (comicPanels.getChildren().indexOf(selectedPanel) + 1 == comicPanels.getChildren().size()) {
            moveRight.setDisable(true);
        }

        editComicPanel();
        panel.setEffect(drop);
    }

    /**
     * Unselects all the panel (visually)
     */
    private void unselectCurrentPanel() {
        if (comicPanels.getChildren().contains(selectedPanel)) {
            int index = comicPanels.getChildren().indexOf(selectedPanel);
            comicPanels.getChildren().get(index).setEffect(null);
        }
    }

    /**
     * Swap panel order of the selected panel and the panel to its right in the comic strip
     */
    private void movePanelRight() {
        moveLeft.setDisable(false);
        int size = comicPanels.getChildren().size();
        int current = comicPanels.getChildren().indexOf(selectedPanel);

        Node next = comicPanels.getChildren().get(current + 1);
        int nextIndex = comicPanels.getChildren().indexOf(next);

        comicPanels.getChildren().set(nextIndex, new ComicPane());
        comicPanels.getChildren().set(current, next);
        comicPanels.getChildren().set(nextIndex, selectedPanel);

        int temp = comicPanels.getChildren().indexOf(selectedPanel);
        if (temp + 1 == size) moveRight.setDisable(true);
    }

    /**
     * Swap panel order of the selected panel and the panel to its left in the comic strip
     */
    private void movePanelLeft() {
        moveRight.setDisable(false);
        int current = comicPanels.getChildren().indexOf(selectedPanel);

        Node next = comicPanels.getChildren().get(current - 1);
        int nextIndex = comicPanels.getChildren().indexOf(next);

        comicPanels.getChildren().set(nextIndex, new ComicPane());
        comicPanels.getChildren().set(current, next);
        comicPanels.getChildren().set(nextIndex, selectedPanel);

        int temp = comicPanels.getChildren().indexOf(selectedPanel);
        if (temp - 1 == -1) moveLeft.setDisable(true);
    }
}
