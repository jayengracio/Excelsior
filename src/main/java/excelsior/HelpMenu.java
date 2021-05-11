package excelsior;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpMenu {
    private final Stage primaryStage;
    private Stage helpWindow;
    private Image[] helpScreens;
    private String[] helpMessages;
    private ImageView helpScreenlocation;
    private Label helpMessage;
    private Button prev;
    private Button next;
    int currentInd = 0;

    public HelpMenu(Stage primaryStage) {
        this.primaryStage = primaryStage;
        helpWindow = createHelpMenu();
    }

    //creates the actual help panels
    private Stage createHelpMenu()
    {
        formHelpScreens();
        formHelpMessages();

        GridPane container = new GridPane();
        container.setStyle("-fx-background-color: white;");
        container.setPadding(new Insets(15));
        container.setVgap(10);
        helpScreenlocation = new ImageView();
        helpScreenlocation.setFitHeight(380);
        helpScreenlocation.setFitWidth(570);
        helpScreenlocation.preserveRatioProperty().setValue(true);
        helpScreenlocation.prefWidth(380);
        helpScreenlocation.prefHeight(570);
        helpScreenlocation.setEffect(new DropShadow());

        helpMessage = new Label();
        helpMessage.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;-fx-font-family: 'Calibri';");
        helpMessage.setAlignment(Pos.CENTER);
        helpMessage.setPadding(new Insets(0,0,0,70));
        helpMessage.setMinHeight(140);

        prev = new Button("prev");
        prev.setPrefSize(60, 30);

        next = new Button("next");
        next.setPrefSize(60, 30);

        next.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> prepareHelp(currentInd+1));
        prev.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> prepareHelp(currentInd-1));

        container.add(helpScreenlocation,0,0,3,2);
        container.add(helpMessage,0,3,3,1);
        container.add(prev,0,4);
        container.add(next,3,4);

        Scene secondScene = new Scene(container, 600, 600);
        // New window (Stage)
        Stage helpWindow = new Stage();
        helpWindow.setTitle("Help");
        helpWindow.setScene(secondScene);

        // Specifies the modality for new window.
        helpWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        helpWindow.initOwner(primaryStage);
        helpWindow.setResizable(false);

        // Set position of second window, related to primary window.
        helpWindow.setX(primaryStage.getX() * 2);
        helpWindow.setY(primaryStage.getY() * 2);

        return helpWindow;
    }

    //creates the menu bar for help
    public Menu create() {
        Menu menu = new Menu("Help");
        MenuItem help = new MenuItem("Walkthrough");
        MenuItem fileHelp = new MenuItem("File Help");
        MenuItem panelHelp = new MenuItem("Panel Help");

        menu.getItems().addAll(help,panelHelp,fileHelp);
        help.setOnAction(actionEvent -> {
            prepareHelp(0);
            helpWindow.show();
        });

        panelHelp.setOnAction(actionEvent -> {
            prepareHelp(10);
            helpWindow.show();
        });

        fileHelp.setOnAction(actionEvent -> {
            prepareHelp(11);
            helpWindow.show();
        });
        return menu;
    }

    private void formHelpMessages()
    {
        helpMessages = new String[12];
        helpMessages[0]="This is the Editing Pane." +
                "\nIt is where you can edit your comic panels"+
                "\nand change the characters, narration and story!";

        helpMessages[1]="This is where the comic panels you have saved are stored"+
                "\nYou can save multiple comic panels to form a" +
                "\ncomic of your own design.";

        helpMessages[2]="For each new panel.You can either select a character or" +
                "\nadd top or bottom narration using the buttons shown.";

        helpMessages[3]="By clicking one of the arrow buttons" +
                "\nYou select either the left or right character." +
                "\nYour selected character is now highlighted";

        helpMessages[4]="You can now choose the character pose for" +
                "\nyour selected character by clicking the button shown";

        helpMessages[5]="You can switch the direction of your selected character" +
                "\nby pressing the flip button shown";

        helpMessages[6]="You can change the gender of your selected character" +
                "\nby pressing the change gender button shown";

        helpMessages[7]="You can add speech for your selected character" +
                "\nby pressing the change speech bubble button shown";

        helpMessages[8]="You can add thoughts for your selected character" +
                "\nby pressing the change thought bubble button shown";

        helpMessages[9]="You can add can change the colour of your character" +
                "\nby using the colour palette shown." +
                "\nChoose to change skin,hair or lip colour" +
                "\nusing their respective buttons and then click on the" +
                "\ncolour you want to change it to from our selection";

        helpMessages[10]= "Save your currently edited panel using Shift+S" +
                "\nYou can re-edit a saved panel by selecting it."+
                "\nSelect a panel from the selection by clicking it" +
                "\nDelete the selected panel by pressing Delete on keyboard"+
                "\nYou can also just use the buttons in the Panel dropdown shown";

        helpMessages[11]="Save your comic as a Html file with Ctrl+H" +
                "\nSave your comic as XML with Ctrl+S" +
                "\nImport xml comics using Ctrl+T" +
                "\nCreate a new comic with Ctrl+N" +
                "\nYou can also just use the buttons in the File dropdown shown";
    }

    private void formHelpScreens()
    {
        helpScreens = new Image[12];
        for (int i = 0; i < 12; i++) {
            helpScreens[i] = new Image("/Help_Screens/" + i + ".png");
        }
    }

    private void prepareHelp(int helpScreenIndex)
    {
        if(helpScreenIndex == 0)
        {
            prev.setVisible(false);
            next.setVisible(true);
        }
        else if(helpScreenIndex == 11)
        {
            next.setVisible(false);
            prev.setVisible(true);
        }
        else if(!prev.isVisible())
            prev.setVisible(true);
        else if(!next.isVisible())
            next.setVisible(true);

        currentInd = helpScreenIndex;
        helpScreenlocation.setImage(helpScreens[helpScreenIndex]);
        helpMessage.setText(helpMessages[helpScreenIndex]);
    }
}
