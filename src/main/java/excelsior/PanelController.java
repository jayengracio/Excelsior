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

    // disables delete button if there are no panels in the comic strip
    public void deleteAvailability() {
        if (!ui.getComicPanels().getChildren().isEmpty()) {
            this.delete.setDisable(false);
        }
    }

    public void saveComicPanel() {
        ComicPane newPanel = new ComicPane();

        // sets the new panel as the current workspace panel
        newPanel.setTo(workPanel, false);
        selectComicPanel(newPanel);

        // checks if the selected panel already exists within in comicPanels before saving/overwriting
        if (comicPanels.getChildren().contains(selectedPanel)) {
            int index = comicPanels.getChildren().indexOf(selectedPanel);
            comicPanels.getChildren().set(index, newPanel);
        } else {
            comicPanels.getChildren().add(newPanel);
        }

        ui.resetAppFace();
    }

    public void deleteComicPanel() {
        // confirmation alert
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

    public void editComicPanel() {
        ui.resetAppFace();
        workPanel.setTo(selectedPanel, true);
    }

    public void newComicPanel() {
        DropShadow drop = new DropShadow();
        drop.setSpread(0.30);
        drop.setColor(Color.DARKORANGE);

        if (workPanel.isInEditMode()) {
            // switching panels while there are active change prompts a warning
            Alert alert = changesAlert();
            ButtonType save = alert.getButtonTypes().get(0);
            ButtonType cont = alert.getButtonTypes().get(1);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == save) {         // when choosing to save
                    workPanel.setEditMode(false);
                    saveComicPanel();
                    clearWorkPanel();
                } else if (result.get() == cont) {  // when choosing to not save & continue
                    workPanel.setEditMode(false);
                    clearWorkPanel();
                }
            }
        } else {
            clearWorkPanel();
        }
    }

    // clears the working panel
    public void clearWorkPanel() {
        unselectAllPanels();
        ui.resetAppFace();
        selectedPanel = null;
    }

    // gives the panel functions to act as a "button"
    public void selectComicPanel(ComicPane panel) {
        panel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // switching panels while there are active change prompts a warning
            if (workPanel.isInEditMode()) {
                Alert alert = changesAlert();
                ButtonType save = alert.getButtonTypes().get(0);
                ButtonType cont = alert.getButtonTypes().get(1);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == save) {         // when choosing to save
                        workPanel.setEditMode(false);
                        saveComicPanel();
                        selectOtherPanel(panel);
                    } else if (result.get() == cont) {  // when choosing to not save & continue
                        workPanel.setEditMode(false);
                        selectOtherPanel(panel);
                    }
                }
            } else {
                selectOtherPanel(panel);
            }
        });
    }

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
            deleteAvailability();
        });

        delete.setOnAction(actionEvent -> {
            this.deleteComicPanel();
            if (comicPanels.getChildren().isEmpty()) {
                delete.setDisable(true);
            }
        });

        create.setOnAction(actionEvent -> this.newComicPanel());

        return menu;
    }

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

    private void selectOtherPanel(ComicPane panel) {
        DropShadow drop = new DropShadow();
        drop.setSpread(0.30);
        drop.setColor(Color.RED);

        unselectAllPanels();
        selectedPanel = panel;
        editComicPanel();
        panel.setEffect(drop);
    }

    private void unselectAllPanels() {
        for (int i = 0; i < comicPanels.getChildren().size(); i++) {
            Node temp = comicPanels.getChildren().get(i);
            temp.setEffect(null);
        }
    }
}
