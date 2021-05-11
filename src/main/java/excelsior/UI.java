package excelsior;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

public class UI {
    private final Stage primaryStage;
    private final HBox comicPanels = new HBox(15);
    private final ComicPane workPanel = new ComicPane();
    private final PanelController panelController = new PanelController(this);
    private final ButtonController buttonController = new ButtonController(this);
    private final ComicStripController comicStripController = new ComicStripController(this);
    private TilePane buttonBox;
    private Character selectedCharacter;
    private HighlightedPopup charWarningPopup;
    private HighlightedPopup charPosesPopup;
    private HighlightedPopup bottomNarrationInput;
    private HighlightedPopup topNarrationInput;
    private HighlightedPopup textBubbleInput;
    private HighlightedPopup htmlTitleInput;
    private HighlightedPopup xmlLoaderWarning;
    private ColourPalette palette;

    public UI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ComicPane getWorkPanel() {
        return workPanel;
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public HBox getComicPanels() {
        return comicPanels;
    }

    public HighlightedPopup getCharWarningPopup() {
        return charWarningPopup;
    }

    public PanelController getPanelController() {
        return panelController;
    }

    public HighlightedPopup getCharPosesPopup() {
        return charPosesPopup;
    }

    public HighlightedPopup getBottomNarrationInput() {
        return bottomNarrationInput;
    }

    public HighlightedPopup getTopNarrationInput() {
        return topNarrationInput;
    }

    public HighlightedPopup getTextBubbleInput() {
        return textBubbleInput;
    }

    public HighlightedPopup getHtmlTitleInput() {
        return htmlTitleInput;
    }

    public TilePane getButtonBox() {
        return buttonBox;
    }

    public HighlightedPopup getXmlLoaderWarning() {
        return xmlLoaderWarning;
    }

    public void setXmlLoaderWarning(HighlightedPopup xmlLoaderWarning) {
        this.xmlLoaderWarning = xmlLoaderWarning;
    }

    /**
     * Sets up the stage.
     */
    public void setStage() {
        VBox root = new VBox();
        root.getChildren().add(createMenu());
        root.getChildren().add(createView());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1250, 800));
        createPopups();
        primaryStage.show();
    }

    /**
     * Creates all of the popups at the start of the application rather than creating them each time a popup is used.
     */
    private void createPopups() {
        createSelectCharacterWarning();
        createBottomNarrationPopup();
        createTopNarrationPopup();
        createCharacterPoses();
        createTextBubbleInput();
        createHtmlTitlePopup();
    }

    /**
     * Creates a menu bar at the top of the application.
     *
     * @return a menu bar with its menus
     */
    public MenuBar createMenu() {
        Menu file = comicStripController.FileMenu();
        Menu panel = panelController.PanelMenu();
        Menu help = new HelpMenu(primaryStage).create();

        MenuBar mb = new MenuBar();
        mb.getMenus().add(file);
        mb.getMenus().add(panel);
        mb.getMenus().add(help);
        return mb;
    }

    /**
     * Creates the structure of the application.
     *
     * @return a grid pane that contains all the UI elements
     */
    public GridPane createView() {
        GridPane view = new GridPane();
        view.setPadding(new Insets(15));
        view.setHgap(30);
        view.setVgap(15);
        view.add(workPanel, 0, 0);
        view.add(createComicsView(), 0, 1, 3, 1);
        view.add(createColourPalette(), 1, 0);
        view.add(createButtons(), 2, 0);

        return view;
    }

    /**
     * Creates colour groups for changing a character's features.
     *
     * @return a colour palette for this UI
     */
    public ColourPalette createColourPalette() {
        palette = new ColourPalette(this);
        return palette;
    }

    /**
     * Creates the buttons that is used to modify content on the working panel.
     *
     * @return a tile pane with all the buttons
     */
    public TilePane createButtons() {
        buttonBox = new TilePane();
        buttonBox.setPrefSize(375, 500);
        buttonBox.setPrefColumns(2);
        buttonBox.setVgap(11);
        buttonBox.setHgap(14);
        buttonBox.setAlignment(Pos.TOP_RIGHT);
        buttonBox.setPrefRows(4);
        buttonController.start();
        return buttonBox;
    }

    /**
     * Creates the scrollable pane that contains every saved comic panels.
     *
     * @return a scroll pane with comic panels as its content
     */
    public ScrollPane createComicsView() {
        ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
        scroll.setPrefHeight(300);
        comicPanels.setPrefSize(0, 235);
        comicPanels.setAlignment(Pos.CENTER_LEFT);
        comicPanels.setPadding(new Insets(0, 0, 0, 6));
        scroll.setContent(comicPanels);
        scroll.setPannable(true);
        return scroll;
    }

    private void createHtmlTitlePopup() {
        VBox container = new VBox();
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 3; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);

        TextField textBox = new TextField("Enter Comic Title");
        container.getChildren().add(textBox);


        htmlTitleInput = new HighlightedPopup(primaryStage);
        htmlTitleInput.getContent().add(container);

        EventHandler<ActionEvent> eventHandler = e -> {
            htmlTitleInput.hide();
        };
        textBox.setOnAction(eventHandler);
        htmlTitleInput.setOnHidden(e -> {
            comicStripController.getHtmlSaver().setComicTitle(textBox.getText());
            comicStripController.getHtmlSaver().htmlFormer();
            textBox.setText("Enter Comic Title");
            textBox.selectAll();
        });
    }

    // speech bubble helper
    private void createTextBubbleInput() {
        Label input = new Label();
        VBox container = new VBox();
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 3; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);

        TextField textBox = new TextField("Enter text");
        container.getChildren().add(textBox);
        Label warning = new Label();
        warning.setStyle("-fx-font-size: 16; -fx-text-fill: red");
        container.getChildren().add(warning);

        textBubbleInput = new HighlightedPopup(primaryStage);
        textBubbleInput.getContent().add(container);

        EventHandler<ActionEvent> eventHandler = e -> {
            TextBubble tBub = getCurrentSpeechBubble();
            String output = prepareTBub(textBox.getText(), tBub);
            if (output == null) {
                warning.setText("Text Too Long");
            } else {
                input.setText(textBox.getText());
                tBub.setText(output);
                textBubbleInput.hide();
                if (tBub.isEmpty())
                    tBub.setEmpty();
                else {
                    if (buttonController.isSpeechBubble())
                        tBub.setSpeech();
                    else
                        tBub.setThought();
                }
            }
        };
        textBox.setOnAction(eventHandler);
        textBubbleInput.setOnHidden(e -> {
            warning.setText(null);
            textBox.setText("Enter text");
            textBox.selectAll();
        });
    }

    private TextBubble getCurrentSpeechBubble() {
        return selectedCharacter == workPanel.getRightCharacter() ? workPanel.getRightSpeechBubble() : workPanel.getLeftSpeechBubble();
    }

    // top narration helper
    private void createTopNarrationPopup() {
        Narration narration;
        Label text = new Label();
        narration = workPanel.getTopNarration();
        topNarrationInput = createNarrationInput(narration, text);
    }

    // bottom narration helper
    private void createBottomNarrationPopup() {
        Narration narration;
        Label text = new Label();
        narration = workPanel.getBottomNarration();
        bottomNarrationInput = createNarrationInput(narration, text);
    }

    // input text box for top and bottom narration
    private HighlightedPopup createNarrationInput(Narration narration, Label input) {
        VBox container = new VBox();
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 3; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);

        TextField textBox = new TextField("Enter text");
        Label warning = new Label();
        warning.setStyle("-fx-font-size: 16px; -fx-text-fill: red");
        container.getChildren().addAll(textBox, warning);

        HighlightedPopup inputWindow = new HighlightedPopup(primaryStage);
        inputWindow.getContent().add(container);

        EventHandler<ActionEvent> eventHandler = e -> {
            String output = prepareNarration(textBox.getText(), narration);
            if (output == null) {
                warning.setText("Text Too Long");
            } else {
                input.setText(textBox.getText());
                narration.setText(output);
                inputWindow.hide();
            }
        };
        textBox.setOnAction(eventHandler);
        inputWindow.setOnHidden(e -> {
            warning.setText(null);
            textBox.setText("Enter text");
            textBox.selectAll();
        });
        return inputWindow;
    }

    //method to sort through fonts for text bubble
    public String prepareTBub(String s, TextBubble tBub) {
        String output;
        int largeFont = 20;
        int mediumFont = 16;
        int smallFont = 13;
        output = prepareString(s, 3, 17);
        if (output != null) {
            tBub.setTextSize(largeFont);
            return output;
        }
        output = prepareString(s, 4, 18);
        if (output != null) {
            tBub.setTextSize(mediumFont);
            return output;
        }
        output = prepareString(s, 5, 25);
        if (output != null) {
            tBub.setTextSize(smallFont);
            return output;
        }

        return null;
    }

    //method to dynamically change font size for narration depending on input length
    public String prepareNarration(String s, Narration narration) {
        String output;

        output = prepareString(s, 2, 50);
        if (output != null) {
            narration.setTextSize(20);
            return output;
        }
        output = prepareString(s, 2, 63);
        if (output != null) {
            narration.setTextSize(16);
            return output;
        }

        output = prepareString(s, 3, 77);
        if (output != null) {
            narration.setTextSize(13);
            return output;
        }

        return null;
    }

    //prepares String for text bubbles and returns null if exceeds acceptable length
    public String prepareString(String s, int numLines, int charPerLine) {
        String output;
        int lastSpace = 0;
        int lineLength = 0;
        char curr;
        int index = 0;
        StringBuilder outputBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            curr = s.charAt(i);
            lineLength++;
            if (curr == ' ') {
                lastSpace = i;
            }
            if (lineLength == charPerLine && i != (s.length() - 1)) {
                if (i == lastSpace || (i - lastSpace >= charPerLine - 1))
                    outputBuilder.append(curr).append("\n");
                else {
                    outputBuilder = new StringBuilder(outputBuilder.substring(0, lastSpace + index + 1) + "\n" + outputBuilder.substring(lastSpace + index + 1) + curr);
                }

                lineLength = (i - lastSpace < charPerLine - 1) ? (i - lastSpace) : 0;
                index++;
            } else
                outputBuilder.append(curr);
        }
        output = outputBuilder.toString();
        if (index + 1 > numLines)
            output = null;
        return output;
    }

    // adds event handler to close popup input as parameter when clicked
    private void closePopupButton(Node button, Popup popup) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> popup.hide());
    }

    //displays options for character choices in scrollable popup
    private void createCharacterPoses() {
        ScrollPane selection = new ScrollPane();
        selection.setPrefSize(261, 360);

        selection.setContent(createPoses());
        selection.setPannable(true);


        selection.setPadding(new Insets(5));
        selection.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-size: 18px;");

        Button closeButton = new Button("Confirm");
        closeButton.setPrefSize(255, 10);
        closeButton.setStyle("-fx-border-color: #000000; -fx-border-radius: 10px; -fx-text-fill : #ffffff; -fx-background-color: #1CC415; -fx-background-radius: 10px");

        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 1, 0, 1));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(closeButton, selection);

        charPosesPopup = new HighlightedPopup(primaryStage);
        charPosesPopup.getContent().add(container);
        closePopupButton(closeButton, charPosesPopup);
    }

    //creates and gets character image options in Tilepane
    private TilePane createPoses() {
        TilePane Poses = new TilePane();
        Poses.setMaxSize(500, 10);
        Poses.setPrefColumns(2);
        Poses.setPrefRows(6);
        Poses.setVgap(11);
        Poses.setHgap(14);
        Poses.setAlignment(Pos.CENTER_RIGHT);
        Poses.setStyle("-fx-background-color: white;-fx-font-size: 18px;");

        //dynamic version adds based on files in folder
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] charPoseFiles = resolver.getResources("Character_Images/*.png");

            int i = 0;
            for (Resource charPose : charPoseFiles) {
                Poses.getChildren().add(i, new CharacterPoseButton(charPose.getFilename()));
                Poses.setTileAlignment(Pos.TOP_LEFT);
                changePose(Poses.getChildren().get(i), charPose.getFilename());
                i++;
            }
        } catch (IOException e) {
            System.out.println("Error: Failed to retrieve character images");
            e.printStackTrace();
        }

        return Poses;
    }

    //  waits for update for button being pressed and then changes the pose to the selected
    private void changePose(Node button, String pose) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            selectedCharacter.setCharacterPose(pose);
            selectedCharacter.setPoseString(pose);
            workPanel.setEditMode(true);
            if (selectedCharacter.isEmpty()) {
                if (workPanel.getLeftCharacter() == selectedCharacter)
                    workPanel.getLeftSpeechBubble().setEmpty();
                else
                    workPanel.getRightSpeechBubble().setEmpty();
            }
        });
    }

    //creates a popup telling the user to select a character
    private void createSelectCharacterWarning() {
        VBox container = new VBox();
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);

        final Label warning = new Label("Warning!");
        warning.setStyle("-fx-font-size: 30px; -fx-text-fill: red; -fx-font-weight: bold;");
        final Label selectCharacterWarning = new Label("You must select a character first.");
        final Label closePrompt = new Label("Click anywhere to close.");

        container.getChildren().addAll(warning, selectCharacterWarning, closePrompt);

        charWarningPopup = new HighlightedPopup(primaryStage);
        charWarningPopup.getContent().add(container);
    }

    //used to reset the entire appFace to its original startup look
    public void resetAppFace() {
        workPanel.clear();
        palette.reset();
        if (selectedCharacter != null) {
            selectedCharacter.setEffect(null);
            boolean isLeft = (selectedCharacter == workPanel.getLeftCharacter());
            IconButtons curCharBtn = (IconButtons) buttonBox.getChildren().get(isLeft ? 0 : 1);
            curCharBtn.setIcon(isLeft ? "Left.png" : "Right.png");
            buttonController.createCharacterButtonTooltip(curCharBtn, null);
            selectedCharacter = null;
        }
    }
}
