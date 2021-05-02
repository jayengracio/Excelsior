package excelsior;

import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlSaver {
    private final UI ui;

    public XmlSaver(UI ui) {
        this.ui = ui;
    }

    public void save() {
        HBox comicPanels = ui.getComicPanels();
        try {
            // document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element comic = doc.createElement("comic");
            doc.appendChild(comic);

            Element panels = doc.createElement("panels");
            comic.appendChild(panels);

            for (int i = 0; i < comicPanels.getChildren().size(); i++) {
                ComicPane pane = (ComicPane) comicPanels.getChildren().get(i);

                // PANEL START
                Element panel = doc.createElement("panel");
                panels.appendChild(panel);

                Element above = doc.createElement("above");
                above.appendChild(doc.createTextNode(pane.getTopNarration().getText()));
                panel.appendChild(above);

                // LEFT CHARACTER START
                Element left = doc.createElement("left");
                panel.appendChild(left);

                Element figure = doc.createElement("figure");
                left.appendChild(figure);

                Element appearance = doc.createElement("appearance");
                appearance.appendChild(doc.createTextNode(pane.getLeftCharacter().getGender()));
                figure.appendChild(appearance);

                Element skin = doc.createElement("skin");
                skin.appendChild(doc.createTextNode(pane.getLeftCharacter().getSkinColourAsHex()));
                figure.appendChild(skin);

                Element hair = doc.createElement("hair");
                hair.appendChild(doc.createTextNode(pane.getLeftCharacter().getHairColourAsHex()));
                figure.appendChild(hair);

                Element lips = doc.createElement("lips");
                lips.appendChild(doc.createTextNode(pane.getLeftCharacter().getLipColourAsHex()));
                figure.appendChild(lips);

                Element pose = doc.createElement("pose");
                pose.appendChild(doc.createTextNode(pane.getLeftCharacter().getPose()));
                figure.appendChild(pose);

                Element facing = doc.createElement("facing");
                facing.appendChild(doc.createTextNode(pane.getLeftCharacter().getFacing()));
                figure.appendChild(facing);

                Element balloon = doc.createElement("balloon");
                left.appendChild(balloon);

                Attr status = doc.createAttribute("status");
                if (pane.getLeftSpeechBubble().getBubbleType().equals("speech")) status.setValue("speech");
                else if (pane.getLeftSpeechBubble().getBubbleType().equals("thought")) status.setValue("thought");
                else status.setValue("none");
                balloon.setAttributeNode(status);

                Element content = doc.createElement("content");
                String s = pane.getLeftSpeechBubble().getText().getText();
                s = s.replaceAll("\n", " ");
                content.appendChild(doc.createTextNode(s));
                balloon.appendChild(content);

                // RIGHT CHARACTER START
                Element right = doc.createElement("right");
                panel.appendChild(right);

                Element rFigure = doc.createElement("figure");
                right.appendChild(rFigure);

                Element rAppearance = doc.createElement("appearance");
                rAppearance.appendChild(doc.createTextNode(pane.getRightCharacter().getGender()));
                rFigure.appendChild(rAppearance);

                Element rSkin = doc.createElement("skin");
                rSkin.appendChild(doc.createTextNode(pane.getRightCharacter().getSkinColourAsHex()));
                rFigure.appendChild(rSkin);

                Element rHair = doc.createElement("hair");
                rHair.appendChild(doc.createTextNode(pane.getRightCharacter().getHairColourAsHex()));
                rFigure.appendChild(rHair);

                Element rLips = doc.createElement("lips");
                rLips.appendChild(doc.createTextNode(pane.getRightCharacter().getLipColourAsHex()));
                rFigure.appendChild(rLips);

                Element rPose = doc.createElement("pose");
                rPose.appendChild(doc.createTextNode(pane.getRightCharacter().getPose()));
                rFigure.appendChild(rPose);

                Element rFacing = doc.createElement("facing");
                rFacing.appendChild(doc.createTextNode(pane.getRightCharacter().getFacing()));
                rFigure.appendChild(rFacing);

                Element rBalloon = doc.createElement("balloon");
                right.appendChild(rBalloon);

                Attr rStatus = doc.createAttribute("status");
                if (pane.getRightSpeechBubble().getBubbleType().equals("speech")) rStatus.setValue("speech");
                else if (pane.getRightSpeechBubble().getBubbleType().equals("thought")) rStatus.setValue("thought");
                else rStatus.setValue("none");
                rBalloon.setAttributeNode(rStatus);

                Element rContent = doc.createElement("content");
                s = pane.getRightSpeechBubble().getText().getText();
                s = s.replaceAll("\n", " ");
                rContent.appendChild(doc.createTextNode(s));
                rBalloon.appendChild(rContent);

                Element below = doc.createElement("below");
                below.appendChild(doc.createTextNode(pane.getBottomNarration().getText()));
                panel.appendChild(below);
                // PANEL END
            }
            // SAVE
            openSaveDialog(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSaveDialog(Document doc) throws TransformerException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Comic Strip");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(ui.getPrimaryStage());

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "comic.dtd");
        DOMSource source = new DOMSource(doc);

        if (file != null) {
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            // debug
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        }
    }
}
