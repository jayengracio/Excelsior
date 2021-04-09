package excelsior;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;

public class UI {
    private final Stage primaryStage;
    private final HBox allComics = new HBox(15);
    private final ComicPane comic = new ComicPane();
    private TilePane buttonBox;
    private Character selectedCharacter;

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
        primaryStage.show();
    }

    //top menu pane for file dropdown
    public MenuBar createMenu() {
        Menu m = new Menu("File");

        MenuItem m1 = new MenuItem("New");
        MenuItem m2 = new MenuItem("Delete");
        MenuItem m3 = new MenuItem("Save");

        m.getItems().add(m1);
        m.getItems().add(m2);
        m.getItems().add(m3);

        MenuBar mb = new MenuBar();

        mb.getMenus().add(m);

        return mb;
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
                displaySelectCharacterWarning();
            } else {
                selectedCharacter.setHairColour(cur.getBackground());
            }
        });
    }

    private void skinChange(Node button) {
        ColourBox cur = (ColourBox) button;
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                displaySelectCharacterWarning();
            } else {
                selectedCharacter.setSkinColour(cur.getBackground());
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
        buttonBox.getChildren().add(0, new Button("Left"));
        buttonBox.getChildren().add(1, new Button("Right"));
        buttonBox.getChildren().add(2, new Button("Flip"));
        buttonBox.getChildren().add(3, new Button("M/F"));
        buttonBox.getChildren().add(4, new Button("Speech Bubble"));
        buttonBox.getChildren().add(5, new Button("Top Narration"));
        buttonBox.getChildren().add(6, new Button("Thought Bubble"));
        buttonBox.getChildren().add(7, new Button("Bot Narration"));
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
        allComics.setPrefSize(2000, 235);
        allComics.setAlignment(Pos.CENTER_LEFT);
        allComics.setPadding(new Insets(0, 0, 0, 15));
        scroll.setContent(allComics);
        scroll.setPannable(true);
        return scroll;
    }

    //adds event handler for selecting or deselecting the left character
    private void leftCharacterButton(Node button) {
        Button cur = (Button) button;
        Button right = (Button) buttonBox.getChildren().get(1);
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == comic.getLeftCharacter()) {
                displayCharacterPoses();
            } else {
                selectedCharacter = comic.getLeftCharacter();
                comic.getLeftCharacter().setEffect(dropShadow);
                comic.getRightCharacter().setEffect(null);
                right.setText("Right");
                cur.setText("Select Pose");
            }
            button.setEffect(dropShadow);
        });
    }

    //adds event handler for selecting or deselecting the right character
    private void rightCharacterButton(Node button) {
        Button cur = (Button) button;
        Button left = (Button) buttonBox.getChildren().get(0);
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == comic.getRightCharacter()) {
                displayCharacterPoses();
            } else {
                selectedCharacter = comic.getRightCharacter();
                comic.getRightCharacter().setEffect(dropShadow);
                comic.getLeftCharacter().setEffect(null);
                left.setText("Left");
                cur.setText("Select Pose");
            }
            button.setEffect(dropShadow);
        });
    }

    //adds event handler to flip currently selected character on x axis when button input is clicked
    private void switchOrientationButton(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                displaySelectCharacterWarning();
            } else {
                selectedCharacter.flipDefaultOrientation();
                button.setEffect(dropShadow);
            }
        });
    }

    // button function to change character's gender
    private void changeGenderButton(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                displaySelectCharacterWarning();
            } else {
                selectedCharacter.setFemale(!selectedCharacter.isFemale());
            }
            button.setEffect(dropShadow);
        });
    }

    // button function to insert a speech bubble
    private void speechBubbleButton(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                displaySelectCharacterWarning();
            } else {
                displayTextForBubble(0);
                button.setEffect(dropShadow);
            }
        });
    }

    // button function to insert a thought bubble
    private void thoughtBubbleButton(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == null || selectedCharacter.isEmpty()) {
                displaySelectCharacterWarning();
            } else {
                displayTextForBubble(1);
                button.setEffect(dropShadow);
            }
        });
    }

    // button function to insert the top narration text
    private void topNarrationButton(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            displayTopNarrate();
            button.setEffect(dropShadow);
        });
    }

    // button function to insert the bottom narration text
    private void botNarrationButton(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            displayBottomNarrate();
            button.setEffect(dropShadow);
        });
    }

    // speech bubble helper
    private void displayTextForBubble(int type) {
        TextBubble speechBubble;
        Label text = new Label();
        if (selectedCharacter == comic.getRightCharacter()) {
            speechBubble = comic.getRightSpeechBubble();
        } else {
            speechBubble = comic.getLeftSpeechBubble();
        }
        createTextBox(speechBubble, text, type);
    }

    // top narration helper
    private void displayTopNarrate() {
        Label narration;
        Label text = new Label();
        narration = comic.getTopNarration();
        createTextBox(narration, text);
    }

    // bottom narration helper
    private void displayBottomNarrate() {
        Label narration;
        Label text = new Label();
        narration = comic.getBottomNarration();
        createTextBox(narration, text);
    }

    // input text box for speech/thought bubble
    private void createTextBox(Label type, Label input) {
        VBox container = new VBox();
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 3; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);
        TextField textBox = new TextField("Enter text");
        container.getChildren().add(textBox);

        Popup inputWindow = new Popup();
        inputWindow.getContent().add(container);
        inputWindow.show(primaryStage);
        inputWindow.setAutoHide(true);

        EventHandler<ActionEvent> eventHandler = e -> {
            input.setText(textBox.getText());
            type.setText(input.getText());
            inputWindow.hide();
        };
        textBox.setOnAction(eventHandler);
    }
    private void createTextBox(TextBubble tBub, Label input, int type) {
        VBox container = new VBox();
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 3; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);
        TextField textBox = new TextField("Enter text");
        container.getChildren().add(textBox);

        Popup inputWindow = new Popup();
        inputWindow.getContent().add(container);
        inputWindow.show(primaryStage);
        inputWindow.setAutoHide(true);

        EventHandler<ActionEvent> eventHandler = e -> {
            input.setText(textBox.getText());
            tBub.setText(input.getText());
            inputWindow.hide();
            if(tBub.isEmpty())
                tBub.setEmpty();
            else {
                if(type == 0)
                    tBub.setSpeech();
                else
                    tBub.setThought();
            }
        };
        textBox.setOnAction(eventHandler);
    }

    //displays options for character choices in scrollable popup
    private void displayCharacterPoses() {
        ScrollPane selection = new ScrollPane();
        selection.setPrefSize(300, 750);

        selection.setContent(createPoses());
        selection.setPannable(true);


        selection.setPadding(new Insets(22));
        selection.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-size: 18px;");


        Popup charPosesPopup = new Popup();
        charPosesPopup.getContent().add(selection);
        charPosesPopup.setAutoHide(true);
        charPosesPopup.show(primaryStage);

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
        Poses.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-size: 18px;");

        //dynamic version adds based on files in folder
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
        }

        //Hardcoded version because of jar issues (used for the jar)
        /*String[] names = {"#empty.png","angry.png","accusing.png","attacking.png","biting.png","charming.png","confident.png",
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
        }*/
        return Poses;
    }

    //  waits for update for button being pressed and then changes the pose to the selected
    private void changePose(Node button, String pose) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            selectedCharacter.setCharacterPose(pose);
            if(selectedCharacter.isEmpty()) {
                if (comic.getLeftCharacter() == selectedCharacter)
                    comic.getLeftSpeechBubble().setEmpty();
                else
                    comic.getRightSpeechBubble().setEmpty();
            }
        });
    }

    //displays a popup telling the user to select a character
    private void displaySelectCharacterWarning() {
        VBox container = new VBox();
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);
        final Label warning = new Label("Warning!");
        warning.setStyle("-fx-font-size: 30px; -fx-text-fill: red; -fx-font-weight: bold;");
        final Label selectCharacterWarning = new Label("You must select a character first.");
        final Label closePrompt = new Label("Click anywhere to close.");
        container.getChildren().addAll(warning, selectCharacterWarning, closePrompt);
        Popup charWarningPopup = new Popup();
        charWarningPopup.getContent().add(container);
        charWarningPopup.setAutoHide(true);
        charWarningPopup.show(primaryStage);
    }
}
