package excelsior;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class UI {
    Stage primaryStage;
    Label narrationTop;
    Label narrationBottom;
    //GridPane comic;
    HBox allComics = new HBox(15);
    ComicPane comic = new ComicPane();



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

    public GridPane createComic(){
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
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                buttonBox.getChildren().add(new Button("Btn " + ((i*4)+j + 1)));
                buttonBox.setTileAlignment(Pos.TOP_LEFT);
            }
        }

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
}
