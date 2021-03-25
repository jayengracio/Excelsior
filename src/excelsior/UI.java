package excelsior;

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

public class UI {
    Stage primaryStage;
    Label narrationTop;
    Label narrationBottom;
    //GridPane comic;
    HBox allComics = new HBox(15);
    ComicPane comic = new ComicPane();

    Character selectedCharacter;

    DropShadow dropShadow = new DropShadow();

    public UI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setStage(){
        VBox root = new VBox();
        root.getChildren().add(createMenu());
        root.getChildren().add(createView());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1250, 800));
        primaryStage.show();
    }

    public MenuBar createMenu(){
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
    public GridPane createView(){
        GridPane view = new GridPane();
        view.setPadding(new Insets(15));
        view.setHgap(30);
        view.setVgap(15);
        view.add(createComic(), 0, 0);
        view.add(createComicsView(), 0, 1, 3, 1);
        view.add(createColourPallet(), 1, 0);
        view.add(createButtons(), 2, 0);

        return view;
    }

    public GridPane createComic() {
        /*VBox box = new VBox();
        box.setPrefSize(615, 500);
        box.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 2;");
        box.setAlignment(Pos.CENTER);*/

        //box.getChildren().add(comic);

        /*comic = new GridPane();
        narrationTop = new Label("Top Narration...");
        narrationBottom = new Label("Bottom Narration...");

        narrationTop.setAlignment(Pos.CENTER);
        narrationTop.setPrefSize(500, 70);
        narrationBottom.setAlignment(Pos.CENTER);
        narrationBottom.setPrefSize(500, 70);
        comic.setPrefSize(500, 400);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(45);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(45);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(25);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(15);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(60);
        comic.getRowConstraints().addAll(row1,row2,row3);
        comic.getColumnConstraints().addAll(col1,col2,col3);


        comic.setGridLinesVisible(true);
        box.getChildren().addAll(narrationTop, comic, narrationBottom);*/
        return comic;
    }

    public HBox createColourPallet(){
        HBox box = new HBox(10);
        box.setPadding(new Insets(5));
        box.setPrefSize(450, 500);
        box.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 2;");
        VBox skin = new VBox(10);
        VBox hair = new VBox(10);
        box.getChildren().addAll(skin, hair);
        return box;
    }

    public TilePane createButtons(){
        TilePane buttonBox = new TilePane();
        buttonBox.setPrefSize(375, 500);
        buttonBox.setPrefColumns(2);
        buttonBox.setVgap(11);
        buttonBox.setHgap(14);
        buttonBox.setAlignment(Pos.TOP_RIGHT);
        buttonBox.setPrefRows(4);
       /* for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                buttonBox.getChildren().add(new Button("Btn " + ((i*4)+j + 1)));
                buttonBox.setTileAlignment(Pos.TOP_LEFT);
            }
        }*/

        buttonBox.getChildren().add(0, new Button("Left"));
        buttonBox.getChildren().add(1, new Button("Right"));
        buttonBox.getChildren().add(2, new Button("Flip"));
        leftCharacterButton(buttonBox.getChildren().get(0));
        rightCharacterButton(buttonBox.getChildren().get(1));
        switchOrientationButton(buttonBox.getChildren().get(2));

        return buttonBox;
    }

    public ScrollPane createComicsView(){
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

    private void leftCharacterButton(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == comic.getLeftCharacter()) {
                selectedCharacter = null;
                comic.getLeftCharacter().setEffect(null);
            } else {
                selectedCharacter = comic.getLeftCharacter();
                comic.getLeftCharacter().setEffect(dropShadow);
                comic.getRightCharacter().setEffect(null);
            }
            button.setEffect(dropShadow);
        });
    }

    private void rightCharacterButton(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (selectedCharacter == comic.getRightCharacter()) {
                selectedCharacter = null;
                comic.getRightCharacter().setEffect(null);
            } else {
                selectedCharacter = comic.getRightCharacter();
                comic.getRightCharacter().setEffect(dropShadow);
                comic.getLeftCharacter().setEffect(null);
            }
            button.setEffect(dropShadow);
        });
    }

    private void switchOrientationButton(Node button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(selectedCharacter == null || selectedCharacter.getCharacter() == null)
            {
                displaySelectCharacterWarning();
            }else {
                selectedCharacter.flipDefaultOrientation();
                button.setEffect(dropShadow);
            }
        });
    }

    private void displaySelectCharacterWarning()
    {
        VBox container = new VBox();
        container.setPadding(new Insets(15));
        container.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-size: 18px;");
        container.setAlignment(Pos.CENTER);
        final Label warning = new Label("Warning!");
        warning.setStyle("-fx-font-size: 30px; -fx-text-fill: red; -fx-font-weight: bold;");
        final Label selectCharacterWarning = new Label("You must select a character first.");
        final Label closePrompt = new Label("Click anywhere to close.");
        container.getChildren().addAll(warning,selectCharacterWarning,closePrompt);
        Popup charWarningPopup = new Popup();
        charWarningPopup.getContent().add(container);
        charWarningPopup.setAutoHide(true);
        charWarningPopup.show(primaryStage);
    }
}
