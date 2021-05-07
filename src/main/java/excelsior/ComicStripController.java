package excelsior;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.util.Optional;

public class ComicStripController {
    private final UI ui;
    private HtmlSaver htmlSaver;

    public ComicStripController(UI ui) {
        this.ui = ui;
    }

    /**
     * Creates a menu that saves and load comic strips in XML and publishing a comic strip in HTML.
     *
     * @return the panel menu with items that have key bindings
     */
    public Menu FileMenu() {
        XmlSaver xmlSaver = new XmlSaver(ui);
        XmlLoader xmlLoader = new XmlLoader(ui);
        htmlSaver = new HtmlSaver(ui);
        Menu menu = new Menu("File");

        MenuItem newStrip = new MenuItem("New");
        MenuItem saveXML = new MenuItem("Save as XML");
        MenuItem loadXML = new MenuItem("Load XML");
        MenuItem saveHTML = new MenuItem("Save as HTML");

        KeyCombination NewKeyBinding = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        KeyCombination SaveXmlKeyBinding = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        KeyCombination LoadXmlKeyBinding = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
        KeyCombination SaveHtmlKeyBinding = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);

        newStrip.setAccelerator(NewKeyBinding);
        loadXML.setAccelerator(LoadXmlKeyBinding);
        saveXML.setAccelerator(SaveXmlKeyBinding);
        saveHTML.setAccelerator(SaveHtmlKeyBinding);

        menu.getItems().addAll(newStrip, loadXML, saveXML,saveHTML);

        saveXML.setOnAction(actionEvent -> xmlSaver.save());

        newStrip.setOnAction(actionEvent -> {
            createNewComicStrip();
            ui.getPanelController().disableDelete();
        });

        loadXML.setOnAction(actionEvent -> {
            xmlLoader.load();
            ui.getPanelController().disableDelete();
        });

        saveHTML.setOnAction(actionEvent -> htmlSaver.save());

        return menu;
    }

    /**
     * An alert to ask user for confirmation when they prompt to create a new comic strip
     */
    private void createNewComicStrip() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Do you wish to work on a new comic strip?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ui.getPanelController().clearWorkPanel();
            ui.getComicPanels().getChildren().clear();
        }
    }

    public HtmlSaver getHtmlSaver()
    {
        return htmlSaver;
    }
}
