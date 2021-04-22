package excelsior;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;

public class UI {
    private final Stage primaryStage;
    private final HBox comicPanels = new HBox(15);
    private final ComicPane comic = new ComicPane();
    private ComicPane selectedPanel;
    private TilePane buttonBox;
    private Character selectedCharacter;
    private Boolean isSpeechBubble = true;
    private HighlightedPopup charWarningPopup;
    private HighlightedPopup charPosesPopup;
    private HighlightedPopup bottomNarrationInput;
    private HighlightedPopup topNarrationInput;
    private HighlightedPopup textBubbleInput;

    private final DropShadow dropShadow = new DropShadow();

    public UI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //sets up the stage
    public void setStage() {
        VBox root = new VBox();
        root.getChildren().add(createMenu());
        root.getChildren().add(createView());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1250, 800));
        createPopups();
        primaryStage.show();
    }

    //create all of the popups at the start of the application instead of creating them each time a popup is used
    private void createPopups()
    {
        createSelectCharacterWarning();
        createBottomNarrationPopup();
        createTopNarrationPopup();
        createCharacterPoses();
        createTextBubbleInput();
    }

    //top menu pane for file dropdown
    public MenuBar createMenu() {
        Menu file = new Menu("File");
        Menu editPanel = comicPanelMenu();

        MenuItem m1 = new MenuItem("New");
        MenuItem m2 = new MenuItem("Delete");
        MenuItem m3 = new MenuItem("Save");

        file.getItems().addAll(m1, m2, m3);

        MenuBar mb = new MenuBar();
        mb.getMenus().add(file);
        mb.getMenus().add(editPanel);

        return mb;
    }

    private Menu comicPanelMenu() {
        Menu menu = new Menu("Panel");

        MenuItem create = new MenuItem("New");
        MenuItem save = new MenuItem("Save");
        MenuItem delete = new MenuItem("Delete");

        KeyCombination newPanelKeyBinding = new KeyCodeCombination(KeyCode.N, KeyCombination.SHIFT_DOWN);
        KeyCombination SaveKeyBinding = new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN);
        KeyCombination DeleteKeyBinding = new KeyCodeCombination(KeyCode.X, KeyCombination.SHIFT_DOWN);

        create.setAccelerator(newPanelKeyBinding);
        save.setAccelerator(SaveKeyBinding);
        delete.setAccelerator(DeleteKeyBinding);

        menu.getItems().addAll(create, delete, save);

        delete.setDisable(true);

        save.setOnAction(actionEvent -> {
            saveComicPanel();
            if (!comicPanels.getChildren().isEmpty()) {
                delete.setDisable(false);
            }
        });

        delete.setOnAction(actionEvent -> {
            deleteComicPanel();
            if (comicPanels.getChildren().isEmpty()) {
                delete.setDisable(true);
            }
        });

        create.setOnAction(actionEvent -> {
            unselectComicPanel();
        });

        return menu;
    }

    //view for everything below the top menu
    public GridPane createView() {
        GridPane view = new GridPane();
        view.setPadding(new Insets(15));
        view.setHgap(30);
        view.setVgap(15);
        view.add(comic, 0, 0);
        view.add(createComicsView(), 0, 1, 3, 1);
        view.add(createColourPalette(), 1, 0);
        view.add(createButtons(), 2, 0);

        return view;
    }

    //colour pallet pane
    public GridPane createColourPalette() {
        GridPane palette = new GridPane();
        palette.setPadding(new Insets(5));
        palette.setPrefSize(450, 500);
        palette.setHgap(30);
        palette.setAlignment(Pos.TOP_CENTER);
        palette.setVgap(15);
        palette.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 2;");
        palette.add(new Label("Skin Colour"), 0, 0);
        palette.add(new Label("Hair Colour"), 1, 0);
        for (int i = 0; i < 12; i++) {
            palette.add(getColours(i, true), 0, i + 1);
            palette.add(getColours(i, false), 1, i + 1);
        }

        // palette.getChildren().add(3, getColours(0,false));
        return palette;
    }

    private HBox getColours(int colourInd, boolean isSkin) {
        HBox choices = new HBox();
        choices.setStyle("-fx-border-color: #000000;");
        choices.setPrefSize(100, 20);

        //each row in this colour array will represent on bar of similar shade colours aka black/grays in one greens in another
        //colour choice constraints, cannot have r value below 9 as male hair is always 9 r val higher than hair colour
        String[][] colours = {{"#0A0A0A", "#2B2B2B", "#3B3B3B", "#474747", "#575757"},
                {"#6B6B6B", "#7F7F7F", "#989898", "#B7B7B7", "#C5C5C5"},
                {"#342316", "#463417", "#624F1A", "#776035", "#9D846A"},
                {"#5F4C46", "#947D76", "#CBAEA3", "#FFE8D8", "#FFF3EE"},
                {"#741E01", "#981D01", "#CB443D", "#DD5B55", "#EF6D67"},
                {"#B36F00", "#CE8400", "#FE9F00", "#FEAF57", "#FED884"},
                {"#979E00", "#CBD200", "#F9FF00", "#DBFF69", "#E6FFAE"},
                {"#0F4217", "#12621E", "#17962C", "#1FFF43", "#7CFF76"},
                {"#156363", "#189797", "#1ACECE", "#1BFFFF", "#71FFEC"},
                {"#09055F", "#0B0690", "#0C06E2", "#4632FF", "#6395FF"},
                {"#420057", "#570072", "#7A0097", "#9611B8", "#D556FF"},
                {"#7F0065", "#AC008E", "#CE00A7", "#FF00D2", "#FF75D9"}};

        for (int i = 0; i < 5; i++) {
            ColourBox cur = new ColourBox(colours[colourInd][i]);
            if (isSkin) {
                skinChange(cur);
            } else {

                hairChange(cur);
            }
            choices.getChildren().add(i, cur);
        }
        return choices;
    }

    private void hairChange(Node button) {
        ColourBox cur = (ColourBox) button;
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                charWarningPopup.show(primaryStage);
            } else {
                selectedCharacter.setHairColour(cur.getBackground());
                if (!comic.getLeftCharacter().isEmpty() || !comic.getRightCharacter().isEmpty()) {
                    comic.setEditMode(true);
                }
            }
        });
    }

    private void skinChange(Node button) {
        ColourBox cur = (ColourBox) button;
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                charWarningPopup.show(primaryStage);
            } else {
                selectedCharacter.setSkinColour(cur.getBackground());
                if (!comic.getLeftCharacter().isEmpty() || !comic.getRightCharacter().isEmpty()) {
                    comic.setEditMode(true);
                }
            }
        });
    }

    //pane for the buttons
    public TilePane createButtons() {
        buttonBox = new TilePane();
        buttonBox.setPrefSize(375, 500);
        buttonBox.setPrefColumns(2);
        buttonBox.setVgap(11);
        buttonBox.setHgap(14);
        buttonBox.setAlignment(Pos.TOP_RIGHT);
        buttonBox.setPrefRows(4);
        buttonBox.getChildren().add(0, new IconButtons("Left.png"));
        buttonBox.getChildren().add(1, new IconButtons("Right.png"));
        buttonBox.getChildren().add(2, new IconButtons("Flip.png"));
        buttonBox.getChildren().add(3, new IconButtons("Gender.png"));
        buttonBox.getChildren().add(4, new IconButtons("Speech Bubble.png"));
        buttonBox.getChildren().add(5, new IconButtons("Top Narration.png"));
        buttonBox.getChildren().add(6, new IconButtons("Thought Bubble.png"));
        buttonBox.getChildren().add(7, new IconButtons("Bot Narration.png"));
        leftCharacterButton(buttonBox.getChildren().get(0));
        rightCharacterButton(buttonBox.getChildren().get(1));
        switchOrientationButton(buttonBox.getChildren().get(2));
        changeGenderButton(buttonBox.getChildren().get(3));
        speechBubbleButton(buttonBox.getChildren().get(4));
        topNarrationButton(buttonBox.getChildren().get(5));
        thoughtBubbleButton(buttonBox.getChildren().get(6));
        botNarrationButton(buttonBox.getChildren().get(7));
        return buttonBox;
    }

    //pane to view past created comic panels
    public ScrollPane createComicsView() {
        ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
        scroll.setPrefHeight(300);
        comicPanels.setPrefSize(2000, 235);
        comicPanels.setAlignment(Pos.CENTER_LEFT);
        comicPanels.setPadding(new Insets(0, 0, 0, 15));
        scroll.setContent(comicPanels);
        scroll.setPannable(true);
        return scroll;
    }

    private void saveComicPanel() {
        ComicPane newPanel = new ComicPane();

        // sets the new panel as the current workspace panel
        newPanel.setTo(comic);
        makePanelSelectable(newPanel);

        // checks if the character's orientation is flipped in the current panel
        if (!comic.getRightCharacter().isDefaultOrientation()) {
            newPanel.getRightCharacter().flipDefaultOrientation();
        } else if (!comic.getLeftCharacter().isDefaultOrientation()) {
            newPanel.getLeftCharacter().flipDefaultOrientation();
        }

        // checks if the selected panel already exists within in comicPanels before saving/overwriting
        if (comicPanels.getChildren().contains(selectedPanel)) {
            int index = comicPanels.getChildren().indexOf(selectedPanel);
            comicPanels.getChildren().set(index, newPanel);
        } else {
            comicPanels.getChildren().add(newPanel);
        }

        comic.clear();
    }

    private void deleteComicPanel() {
        comic.clear();
        comicPanels.getChildren().remove(selectedPanel);
    }

    private void editComicPanel() {
        comic.setWorkspaceTo(selectedPanel);
    }

    private void unselectComicPanel() {
        comic.clear();
        for (int i = 0; i < comicPanels.getChildren().size(); i++) {
            Node temp = comicPanels.getChildren().get(i);
            temp.setEffect(null);
        }
        selectedPanel = null;
    }

    private void makePanelSelectable(ComicPane panel) {
        DropShadow drop = new DropShadow();
        drop.setSpread(0.30);
        drop.setColor(Color.DARKORANGE);

        panel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (comic.isInEditMode()) {
                Alert alert = changesAlert();

                ButtonType save = new ButtonType("Save");
                ButtonType cont = new ButtonType("Continue anyway");
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(save, cont, cancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == save) {
                    comic.setEditMode(false);
                    saveComicPanel();

                    selectedPanel = panel;
                    editComicPanel();

                    unselectAllPanels();
                    panel.setEffect(drop);
                } else if (result.get() == cont) {
                    comic.setEditMode(false);
                    selectedPanel = panel;
                    editComicPanel();

                    unselectAllPanels();
                    panel.setEffect(drop);
                }
            } else {
                selectedPanel = panel;
                editComicPanel();

                unselectAllPanels();
                panel.setEffect(drop);
            }
        });
    }

    private Alert changesAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText(null);
        alert.setContentText("You have unsaved changes in the workspace");

        return alert;
    }

    private void unselectAllPanels() {
        for (int i = 0; i < comicPanels.getChildren().size(); i++) {
            Node temp = comicPanels.getChildren().get(i);
            temp.setEffect(null);
        }
    }

    // button function to select left character and/or select pose
    private void leftCharacterButton(Node button) {
        Button cur = (Button) button;
        Button right = (Button) buttonBox.getChildren().get(1);
        buttonTooltip(button, "Select Character");

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == comic.getLeftCharacter()) {
                charPosesPopup.show(primaryStage);
            } else {
                selectedCharacter = comic.getLeftCharacter();
                comic.getLeftCharacter().setEffect(dropShadow);
                comic.getRightCharacter().setEffect(null);

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
    private void rightCharacterButton(Node button) {
        Button cur = (Button) button;
        Button left = (Button) buttonBox.getChildren().get(0);
        buttonTooltip(button, "Select Character");

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == comic.getRightCharacter()) {
                charPosesPopup.show(primaryStage);
            } else {
                selectedCharacter = comic.getRightCharacter();
                comic.getRightCharacter().setEffect(dropShadow);
                comic.getLeftCharacter().setEffect(null);

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

    // help function to create the tooltips for character button
    private void createCharacterButtonTooltip(Button current, Button next) {

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

    // adds event handler to flip currently selected character on x axis when button input is clicked
    private void switchOrientationButton(Node button) {
        buttonTooltip(button, "Flip where the character is facing");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                charWarningPopup.show(primaryStage);
            } else {
                selectedCharacter.flipDefaultOrientation();
                button.setEffect(dropShadow);
                comic.setEditMode(true);
            }
        });
    }

    // button function to change character's gender
    private void changeGenderButton(Node button) {
        buttonTooltip(button, "Change the gender of the character");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                charWarningPopup.show(primaryStage);
            } else {
                selectedCharacter.setFemale(!selectedCharacter.isFemale());
                comic.setEditMode(true);
            }
            button.setEffect(dropShadow);
        });
    }

    // button function to insert a speech bubble
    private void speechBubbleButton(Node button) {
        buttonTooltip(button, "Add a speech bubble for the selected character");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                charWarningPopup.show(primaryStage);
            } else {
                isSpeechBubble = true;
                textBubbleInput.show(primaryStage);
                button.setEffect(dropShadow);
                comic.setEditMode(true);
            }
        });
    }

    // button function to insert a thought bubble
    private void thoughtBubbleButton(Node button) {
        buttonTooltip(button, "Add a thought bubble for the selected character");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                charWarningPopup.show(primaryStage);
            } else {
                isSpeechBubble = false;
                textBubbleInput.show(primaryStage);
                button.setEffect(dropShadow);
                comic.setEditMode(true);
            }
        });
    }

    // button function to insert the top narration text
    private void topNarrationButton(Node button) {
        buttonTooltip(button, "Add the top narration of the panel");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            topNarrationInput.show(primaryStage);
            button.setEffect(dropShadow);
            comic.setEditMode(true);
        });
    }

    // button function to insert the bottom narration text
    private void botNarrationButton(Node button) {
        buttonTooltip(button, "Add the bottom narration of the panel");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            bottomNarrationInput.show(primaryStage);
            button.setEffect(dropShadow);
            comic.setEditMode(true);
        });
    }

    // help function to help create tooltips for buttons
    private void buttonTooltip(Node button, String text) {
        Button castedButton = (Button) button;
        castedButton.setTooltip(new Tooltip(text));
        castedButton.getTooltip().setShowDelay(Duration.seconds(0.1));
        castedButton.getTooltip().setStyle("-fx-font-size: 12;");
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
            if(output != null) {
                input.setText(textBox.getText());
                tBub.setText(output);
                textBubbleInput.hide();
                textBox.selectAll();
                if (tBub.isEmpty())
                    tBub.setEmpty();
                else {
                    if (isSpeechBubble)
                        tBub.setSpeech();
                    else
                        tBub.setThought();
                }
            }
            else
                warning.setText("Text Too Long");
        };
        textBox.setOnAction(eventHandler);
    }

    private TextBubble getCurrentSpeechBubble()
    {
        return selectedCharacter == comic.getRightCharacter() ? comic.getRightSpeechBubble() : comic.getLeftSpeechBubble();
    }
    // top narration helper
    private void createTopNarrationPopup() {
        Label narration;
        Label text = new Label();
        narration = comic.getTopNarration();
        topNarrationInput = createNarrationInput(narration, text);
    }

    // bottom narration helper
    private void createBottomNarrationPopup() {
        Label narration;
        Label text = new Label();
        narration = comic.getBottomNarration();
        bottomNarrationInput = createNarrationInput(narration, text);
    }

    // input text box for top and bottom narration
    private HighlightedPopup createNarrationInput(Label narration, Label input) {
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
            String output = prepareString(textBox.getText(), 1, 54);
            if (output != null) {
                input.setText(textBox.getText());
                narration.setText(input.getText());
                inputWindow.hide();
                textBox.selectAll();
            } else {
                int count = textBox.getText().length();
                warning.setText("Text cannot be longer than 53 characters. \nCurrent length: " + count);
            }
        };
        textBox.setOnAction(eventHandler);
        return inputWindow;
    }

    //method to sort through fonts for text bubble
    public String prepareTBub(String s, TextBubble tBub){
        String output;
        int largeFont = 20;
        int mediumFont = 16;
        int smallFont = 13;
        output = prepareString(s, 3, 17);
        if(output != null) {
            tBub.textSize(largeFont);
            return output;
        }
        output = prepareString(s, 4, 18);
        if(output != null){
            tBub.textSize(mediumFont);
            return output;
        }
        output = prepareString(s, 5, 25);
        if(output != null){
            tBub.textSize(smallFont);
            return output;
        }

        return null;
    }

    //prepares String for text bubbles and returns null if exceeds acceptable length
    public String prepareString(String s, int numLines, int charPerLine){
        String output = "";
        int lastSpace =0;
        int lineLength=0;
        char curr;
        int index = 0;
        for(int i=0; i< s.length(); i++){
            curr = s.charAt(i);
            lineLength++;
            if(curr == ' '){
                lastSpace = i;
            }
            if(lineLength == charPerLine){
                if(i == lastSpace || (i-lastSpace >= charPerLine-1))
                    output = output + curr +"\n";
                else {
                    output = output.substring(0, lastSpace+index+1) + "\n" + output.substring(lastSpace+index+1) + curr;
                }
                lineLength = 0;
                index++;
            }
            else
                output = output + curr;
        }
        if(index +1 > numLines)
            output = null;
        return output;
    }

    // adds event handler to close popup input as parameter when clicked
    private void closePopupButton(Node button,Popup popup) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            popup.hide();
        });
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
        container.setPadding(new Insets(10,1,0,1));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(closeButton,selection);

        charPosesPopup = new HighlightedPopup(primaryStage);
        charPosesPopup.getContent().add(container);
        closePopupButton(closeButton, charPosesPopup);
    }

    //creates and gets character image options in Tilepane
    public TilePane createPoses() {
        TilePane Poses = new TilePane();
        Poses.setMaxSize(500, 10);
        Poses.setPrefColumns(2);
        Poses.setPrefRows(6);
        Poses.setVgap(11);
        Poses.setHgap(14);
        Poses.setAlignment(Pos.CENTER_RIGHT);
        Poses.setStyle("-fx-background-color: white;-fx-font-size: 18px;");

        /*//dynamic version adds based on files in folder
        File folder = new File("src/Character_Images");
        File[] listOfFiles = folder.listFiles();
        int i = 0;
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    Poses.getChildren().add(i, new CharacterPoseButton(file.getName()));
                    Poses.setTileAlignment(Pos.TOP_LEFT);
                    changePose(Poses.getChildren().get(i), file.getName());
                    i++;
                }
            }
        }*/

        //Hardcoded version because of jar issues (used for the jar)
        String[] names = {"#empty.png","angry.png","accusing.png","attacking.png","biting.png","charming.png","confident.png",
                "confused.png","conquering.png","denouncing.png","disappointed.png","disgusted.png","disgusting.png","embracing.png",
                "goofy.png","guiding.png","hitting.png","inspired.png","inspiring.png","joy.png","laughing.png","neutral.png",
                "posing.png","radicalizing.png","rude.png","sad.png","scared.png","sick.png","sneaky.png","surprised.png","toppled.png",
                "working.png"};
        int i = 0;
        for(String name: names)
        {
            Poses.getChildren().add(i,new CharacterPoseButton(name));
            Poses.setTileAlignment(Pos.TOP_LEFT);
            changePose(Poses.getChildren().get(i) , name);
            i++;
        }
        return Poses;
    }

    //  waits for update for button being pressed and then changes the pose to the selected
    private void changePose(Node button, String pose) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            selectedCharacter.setCharacterPose(pose);
            selectedCharacter.setCurrentPose(pose);
            comic.setEditMode(true);
            if (selectedCharacter.isEmpty()) {
                if (comic.getLeftCharacter() == selectedCharacter)
                    comic.getLeftSpeechBubble().setEmpty();
                else
                    comic.getRightSpeechBubble().setEmpty();
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
}
