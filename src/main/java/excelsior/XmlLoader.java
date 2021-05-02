package excelsior;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlLoader {
    UI ui;

    public void load(File inputFile, UI ui) {
        this.ui = ui;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            parsePanels(doc.getElementsByTagName("panels"), ui.getComicPanels());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parsePanels(NodeList nl, HBox comicPanels) {
        nl = nl.item(0).getChildNodes();
        int counter = 0;
        for (int temp = 0; temp < nl.getLength(); temp++) {
            Node nNode = nl.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                comicPanels.getChildren().add(new ComicPane());
                parse(nNode, (ComicPane) comicPanels.getChildren().get(counter++));
            }
        }

    }

    public void parse(Node n, ComicPane comic) {
        if (n.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) n;
            comic.setTopNarration(parseAbove(eElement.getElementsByTagName("above")));
            parseLeft(eElement.getElementsByTagName("left"), comic);
            parseRight(eElement.getElementsByTagName("right"), comic);
            comic.setBottomNarrationMin(parseBelow(eElement.getElementsByTagName("below")));
            comic.minimise();
            ui.selectComicPanel(comic);
        }
    }

    public void parseLeft(NodeList left, ComicPane comic) {
        if (left.getLength() > 0) {
            Node nNode = left.item(0);

            if (nNode.getNodeType() == nNode.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                comic.setLeftCharacter(parseCharacter(eElement.getElementsByTagName("figure")));
                comic.setLeftSpeechBubble(parseBalloon(eElement.getElementsByTagName("balloon")));
            }
        }
    }

    public void parseRight(NodeList right, ComicPane comic) {
        if (right.getLength() > 0) {
            Node nNode = right.item(0);

            if (nNode.getNodeType() == nNode.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                comic.setRightCharacter(parseCharacter(eElement.getElementsByTagName("figure")));
                comic.setRightSpeechBubble(parseBalloon(eElement.getElementsByTagName("balloon")));

            }
        }
    }

    public TextBubble parseBalloon(NodeList balloon) {
        TextBubble tBub = new TextBubble();
        if (balloon.getLength() > 0) {
            Node nNode = balloon.item(0);

            if (nNode.getNodeType() == nNode.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getAttribute("status").equals("speech"))
                    tBub.setSpeech();
                else if (eElement.getAttribute("status").equals("thought"))
                    tBub.setThought();

                tBub.setText(ui.prepareTBub(eElement.getElementsByTagName("content").item(0).getTextContent(), tBub));
            }
        }

        return tBub;
    }

    public Narration parseAbove(NodeList above) {
        Narration label = new Narration();
        if (above.getLength() > 0) {
            Node nNode = above.item(0);
            if (nNode.getNodeType() == nNode.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                label.setText(ui.prepareNarration(eElement.getTextContent(), label));
            }
        }
        return label;
    }

    public Narration parseBelow(NodeList below) {
        Narration label = new Narration();
        if (below.getLength() > 0) {
            Node nNode = below.item(0);
            if (nNode.getNodeType() == nNode.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                label.setText(ui.prepareNarration(eElement.getTextContent(), label));
            }
        }
        return label;
    }

    public Character parseCharacter(NodeList figure) {
        Character c = new Character();
        for (int count = 0; count < figure.getLength(); count++) {
            Node nNode = figure.item(count);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                if (eElement.getElementsByTagName("pose").getLength() == 0) {
                    c.setCharacterPose("#empty.png");
                } else {
                    String character = eElement.getElementsByTagName("pose").item(0).getTextContent();
                    try {

                        if (character.contains(".png"))
                            c.setCharacterPose(character);
                        else
                            c.setCharacterPose(character + ".png");
                    }catch (Exception e){
                        System.out.println("could not find character image: " + character);
                    }
                }

                if (eElement.getElementsByTagName("facing").getLength() > 0 &&
                        eElement.getElementsByTagName("facing").item(0).getTextContent().equals("left"))
                    c.flipDefaultOrientation();

                if (eElement.getElementsByTagName("appearance").getLength() > 0 &&
                        eElement.getElementsByTagName("appearance").item(0).getTextContent().equals("male"))
                    c.setFemale(false);

                Color colour = ColourMap.getColorNamed(eElement.getElementsByTagName("lips").item(0).getTextContent());
                if (eElement.getElementsByTagName("lips").getLength() > 0 && colour!= null) {
                    c.setLipColour(colour);
                }

                colour = ColourMap.getColorNamed(eElement.getElementsByTagName("hair").item(0).getTextContent());
                if (eElement.getElementsByTagName("hair").getLength() > 0 && colour!= null) {
                    c.setHairColour(colour);
                }

                colour = ColourMap.getColorNamed(eElement.getElementsByTagName("skin").item(0).getTextContent());
                if (eElement.getElementsByTagName("skin").getLength() > 0 && colour!= null) {
                    c.setSkinColour(colour);
                }
            }
        }
        c.minimise();
        return c;
    }
}
