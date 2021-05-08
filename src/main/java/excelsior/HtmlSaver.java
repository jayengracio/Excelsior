package excelsior;

import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.io.PrintWriter;

public class HtmlSaver {
    private final UI ui;
    private String comicTitle;
    private File comicDir;
    private int currentComicFolderIndex;

    public HtmlSaver(UI ui) {
        this.ui = ui;
        this.comicTitle="Title";
    }

    public void save() {
        try {
            //could store title in this html saver or in a comic class idk
            HBox comicPanels = ui.getComicPanels();
            String dir = chooseDirectory();

            //if they chose a directory
            if (dir != null)
            {
                //System.out.println(dir);

                int i = 0;
                Boolean folderCreated = false;

                //Creates comic directory with name "comic-0", iterating through numbers until it creates a directory that hadn't existed
                while(!folderCreated)
                {
                    //System.out.println(dir + "\\comic-"+i);
                    comicDir = new File(dir + "\\comic-"+i);
                    if(comicDir.mkdirs())
                    {
                        folderCreated = true;
                        currentComicFolderIndex = i;
                        ui.getHtmlTitleInput().show(ui.getPrimaryStage());
                        createImages(comicDir.getAbsolutePath()); //which will place comic panes png images in the newly created folder
                    }
                    i++;
                }

            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void htmlFormer()
    {
        try {
            File comic = new File(comicDir.getAbsolutePath() + "\\comic" + currentComicFolderIndex + ".html");
            PrintWriter writer = new PrintWriter(comic);
            writer.print("<html>\n" +
                    "<head>\n" +
                    "\t<style>\n" +
                    "\t\tbody{\n" +
                    "\t\t\tbackground-color: #23272a;\n" +
                    "\t\t\tmargin: 0px 0px 0px 0px;\n" +
                    "\t\t\t\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\th2{\n" +
                    "\t\t\ttext-align: center;\n" +
                    "\t\t\tcolor: #ffffff;\n" +
                    "\t\t\tfont-style: italic;\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t\tfont-family: Copperplate;\n" +
                    "\t\t\tpadding: 25px 10px 25px 10px;\n" +
                    "\t\t\tbackground-color: #B22222;\n" +
                    "\t\t\tborder-radius: 10px;\n" +
                    "\t\t\tmargin: 10px 140px 15px 140px;\n" +
                    "\t\t\ttext-shadow: 2px 2px 3px #2c2f33;\n" +
                    "\t\t}\t\n" +
                    "\t\t\t\n" +
                    "\t\t#mainSlider{\n" +
                    "\t\t\tmargin: 0px 15vw 0px 15vw;\n" +
                    "\t\t\tbackground-color: #2c2f33;\n" +
                    "\t\t\tpadding: 10px 30px 10px 30px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttable{\n" +
                    "\t\t\tborder: 3px solid #B22222;\n" +
                    "\t\t\tborder-radius: 15px;\n" +
                    "\t\t\tpadding: 10px;\n" +
                    "\t\t\tmargin-left: auto;\n" +
                    "\t\t\tmargin-right: auto;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\timg{\n" +
                    "\t\t\twidth: 45vw;\n" +
                    "\t\t\theight: 80vh;\n" +
                    "\t\t\tborder-radius: 10px;\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\t<div id=\"mainSlider\">\n" +
                    "\t\t<h2>" + comicTitle + "</h2>\n" +
                    "\t\t<table>\n");

            //get all of the comic Panes loaded into the folder and add them to the html comic
            File[] comicImagePanels = comicDir.listFiles();
            for (File comicPanel : comicImagePanels) {
                if (comicPanel.getName().endsWith(".png")) {
                    writer.print("\t\t\t<tr><td><img src=\"" + comicPanel.getName() + "\" ></td></tr>\n");
                }
            }

            writer.print("\t\t</table>\n" +
                    "\t</div>\n" +
                    "</body>\n" +
                    "</html>");
            writer.close();
            //System.out.println("done");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private String chooseDirectory(){
        DirectoryChooser dirToSaveHtmlFolder = new DirectoryChooser();
        dirToSaveHtmlFolder.setTitle("Choose Directory to Save HTML comic folder");
        File chosenDirectory = dirToSaveHtmlFolder.showDialog(ui.getPrimaryStage());
        if(chosenDirectory != null)
        {
            return chosenDirectory.getAbsolutePath();
        }else
        {
            return null;
        }
    }

    public void setComicTitle(String comicTitle) {
        this.comicTitle = comicTitle;
    }

    public void createImages(String fileLocation){
        for (int i = 0; i < ui.getComicPanels().getChildren().size(); i++) {
            ComicPane pane = (ComicPane) ui.getComicPanels().getChildren().get(i);
            ui.getWorkPanel().setTo(pane, true);
            ui.getWorkPanel().saveAsPng(i + ".png", fileLocation);
            ui.resetAppFace();
        }
    }
}