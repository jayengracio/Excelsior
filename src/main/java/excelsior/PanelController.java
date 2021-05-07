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
    private ComicPane selectedPanel;

    public PanelController(UI ui) {
        this.ui = ui;
        this.workPanel = ui.getWorkPanel();
        this.comicPanels = ui.getComicPanels();
    }

    /**
     * Enables the delete button
     */
    public void disableDelete() {
        this.delete.setDisable(true);
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
        unselectAllPanels();
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

        create.setAccelerator(newPanelKeyBinding);
        save.setAccelerator(SaveKeyBinding);
        delete.setAccelerator(DeleteKeyBinding);

        menu.getItems().addAll(create, delete, save);

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
            if (ui.getSelectedCharacter() == null) delete.setDisable(true);
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
    private void newComicPanel() {
        DropShadow drop = new DropShadow();
        drop.setSpread(0.30);
        drop.setColor(Color.DARKORANGE);

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

        unselectAllPanels();
        selectedPanel = panel;
        editComicPanel();
        panel.setEffect(drop);
    }

    /**
     * Unselects all the panel (visually)
     */
    private void unselectAllPanels() {
        int size = comicPanels.getChildren().size();
        for (int i = size - 1; i >= 0; i--) {
            Node temp = comicPanels.getChildren().get(i);
            temp.setEffect(null);
        }
    }
}
