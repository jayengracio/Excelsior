package excelsior;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;

import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

public class HtmlSaver {
    private final UI ui;
    private String comicTitle;
    private File comicDir;
    private int currentComicFolderIndex;
    private Image endScreen;
    private int selectedType = 0;

    public HtmlSaver(UI ui) {
        this.ui = ui;
        this.comicTitle="Title";
        endScreen = new Image("/Icons/end_screen.png");
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
                        createImages(comicDir.getAbsolutePath()); //which will place comic panes png images in the newly created folder
                        createHtmlTypeInputPopup();
                        ui.getHtmlTitleInput().show(ui.getPrimaryStage());
                    }
                    i++;
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void htmlFormer() throws IOException {
        if(selectedType == 3){
            createGif();
        }
        else {
            try {
                File comic = new File(comicDir.getAbsolutePath() + "\\comic" + currentComicFolderIndex + ".html");
                PrintWriter writer = new PrintWriter(comic);
                if (selectedType != 3) {
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
                            "\t\t\twidth: " + (selectedType == 0 || selectedType == 2 ? 45 : 35) + "vw;\n" +
                            "\t\t\theight: " + (selectedType == 0 || selectedType == 2 ? 80 : 60) + "vh;\n" +
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
                    if (selectedType == 2) {
                        writer.print("\t\t\t<tr>");
                        for (File comicPanel : comicImagePanels) {
                            if (comicPanel.getName().endsWith(".png")) {
                                writer.print("<td><img src=\"" + comicPanel.getName() + "\" ></td>");
                            }
                        }
                        writer.print("</tr>\n");
                    }
                    else if (selectedType == 0) {
                        for (File comicPanel : comicImagePanels) {
                            if (comicPanel.getName().endsWith(".png")) {
                                writer.print("\t\t\t<tr><td><img src=\"" + comicPanel.getName() + "\" ></td></tr>\n");
                            }
                        }
                    }
                    else if (selectedType == 1) {
                        comicImagePanels = clean(comicImagePanels);

                        for (int i = 0; i < comicImagePanels.length; i++) {
                            if (i % 2 == 0)
                                writer.print("\t\t\t<tr><td><img src=\"" + comicImagePanels[i].getName() + "\" ></td>\n");
                            else
                                writer.print("\t\t\t<td><img src=\"" + comicImagePanels[i].getName() + "\" ></td></tr>\n");
                        }
                    }

                    writer.print("\t\t</table>\n" +
                            "\t</div>\n" +
                            "</body>\n" +
                            "</html>");
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public void createImages(String fileLocation) throws IOException, URISyntaxException {
        for (int i = 0; i < ui.getComicPanels().getChildren().size(); i++) {
            ComicPane pane = (ComicPane) ui.getComicPanels().getChildren().get(i);
            ui.getWorkPanel().setTo(pane, true);
            ui.getWorkPanel().saveAsPng(i + ".png", fileLocation);
            ui.resetAppFace();
        }


        File file = new File(fileLocation + "\\" + ui.getComicPanels().getChildren().size() + ".png");
        ImageIO.write(SwingFXUtils.fromFXImage(endScreen, null), "png", file);
        //Path source = Paths.get(getClass().getResource("/Icons/end_screen.png").toURI());
        //Path target = Paths.get(fileLocation + "\\" + ui.getComicPanels().getChildren().size() + ".png");
        //Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    public void createHtmlTypeInputPopup(){
        String[] options = { "Vertical-1", "Vertical-2", "Horizontal",
                "GIF"};
        ChoiceDialog d = new ChoiceDialog(options[0], options);
        d.setHeaderText("Select preferred HTML layout");
        d.showAndWait();
        switch (d.getSelectedItem().toString()) {
            case "Vertical-1" -> selectedType = 0;
            case "Vertical-2" -> selectedType = 1;
            case "Horizontal" -> selectedType = 2;
            case "GIF" -> selectedType = 3;
        }
    }

    public void createGif() throws IOException {
        File[] comicImagePanels = comicDir.listFiles();
        comicImagePanels = clean(comicImagePanels);
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.start(comicDir.getAbsolutePath() + "/GIF" + ".gif");
        e.setDelay(4000);   // number of milliseconds between frames

        for (File comicImagePanel : comicImagePanels) {
            BufferedImage nextImage = ImageIO.read(comicImagePanel);
            e.addFrame(nextImage);
        }
        e.finish();
    }

    private File[] clean(File[] images){
        int count = 0;
        for (File image : images) {
            if (image.getName().endsWith(".png")) {
                count++;
            }
        }
        File[] array = new File[count];
        for(int i=0, j=0; i< images.length; i++){
            if(images[i].getName().endsWith(".png")){
                array[j++] = images[i];
                System.out.println("count = " + i + ", name: " + images[i].getName() + "\n");
            }
        }
        return array;
    }
}