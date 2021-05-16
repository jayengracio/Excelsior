import excelsior.panel.Character;
import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class CharacterTest {
    private static JFXPanel fxPanel;

    @BeforeAll
    public static void init()
    {
        fxPanel = new JFXPanel();
    }

    @Test
    void testEmpty(){
        Character c = new Character();
        assertTrue(c.isEmpty());
    }

    @Test
    void testSetCharacterPose() throws IOException {
        Character c = new Character();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] charPoseFiles = resolver.getResources("Character_Images/*.png");
        for(Resource charPose : charPoseFiles){
            c.setCharacterPose(charPose.getFilename());
            assertEquals(charPose.getFilename(), c.getPoseString());
        }
    }

    @Test
    void testOrientation(){
        Character c = new Character();
        c.setCharacterPose("accusing.png");
        assertEquals("right", c.getFacing(), "Facing the wrong way upon initialising");
        c.flipDefaultOrientation();
        assertEquals("left", c.getFacing(), "Facing the wrong way after flipping once");
        c.flipDefaultOrientation();
        assertEquals("right", c.getFacing(), "Facing the wrong way after flipping twice");
    }

    @Test
    void testGender(){
        Character c = new Character();
        c.setCharacterPose("accusing.png");
        assertEquals("female", c.getGender() );
        c.setFemale(false);
        assertEquals("male", c.getGender());
        c.setFemale(true);
        assertEquals("female", c.getGender());
    }

    @Test
    void testSkinColour(){
        Character c = new Character();
        c.setCharacterPose("accusing.png");
        c.setSkinColour(Color.web("#0A0A0A"));
        assertEquals("0x0a0a0aff", c.getSkinColour().toString());
        c.setSkinColour(Color.web("#981D01"));
        assertEquals("0x981d01ff", c.getSkinColour().toString());
        c.setSkinColour(Color.web("#1ACECE"));
        assertEquals("0x1aceceff", c.getSkinColour().toString());
        c.setSkinColour(Color.web("#FF75D9"));
        assertEquals("0xff75d9ff", c.getSkinColour().toString());
    }

    @Test
    void testHairColour(){
        Character c = new Character();
        c.setCharacterPose("accusing.png");
        c.setHairColour(Color.web("#0A0A0A"));
        assertEquals("0x0a0a0aff", c.getHairColour().toString());
        c.setHairColour(Color.web("#981D01"));
        assertEquals("0x981d01ff", c.getHairColour().toString());
        c.setHairColour(Color.web("#1ACECE"));
        assertEquals("0x1aceceff", c.getHairColour().toString());
        c.setHairColour(Color.web("#FF75D9"));
        assertEquals("0xff75d9ff", c.getHairColour().toString());
    }

    @Test
    void testLipColour(){
        Character c = new Character();
        c.setCharacterPose("accusing.png");
        c.setLipColour(Color.web("#0A0A0A"));
        assertEquals("0x0a0a0aff", c.getLipColour().toString());
        c.setLipColour(Color.web("#981D01"));
        assertEquals("0x981d01ff", c.getLipColour().toString());
        c.setLipColour(Color.web("#1ACECE"));
        assertEquals("0x1aceceff", c.getLipColour().toString());
        c.setLipColour(Color.web("#FF75D9"));
        assertEquals("0xff75d9ff", c.getLipColour().toString());
    }

    @Test
    void testDefaultColours(){
        Character c = new Character();
        c.setCharacterPose("accusing.png");

        //Testing setLipColour
        c.setLipColour(Color.web("#F9FF00")); // default hair colour
        assertNotEquals(Color.web("#F9FF00"), c.getLipColour(), "Lip colour should not be set to default hair colour");

        c.setLipColour(Color.web("#FFE8D8")); // default skin colour
        assertNotEquals(Color.web("#FFE8D8").toString(), c.getLipColour().toString(),  "Lip colour should not be set to default skin colour");

        c.setLipColour(Color.web("#FF0000")); // default lip colour
        assertEquals(Color.web("#FF0000").toString(), c.getLipColour().toString(), "Lip colour should be set to default lip colour");

        //Testing setHairColour
        c.setHairColour(Color.web("#F9FF00")); // default hair colour
        assertEquals(Color.web("#F9FF00"), c.getHairColour(), "Hair colour should not be set to default hair colour");

        c.setHairColour(Color.web("#FFE8D8")); // default skin colour
        assertNotEquals(Color.web("#FFE8D8").toString(), c.getHairColour().toString(),  "Hair colour should not be set to default skin colour");

        c.setHairColour(Color.web("#FF0000")); // default lip colour
        assertNotEquals(Color.web("#FF0000").toString(), c.getHairColour().toString(), "Hair colour should be set to default lip colour");

        //Testing setSkinColour
        c.setSkinColour(Color.web("#F9FF00")); // default hair colour
        assertNotEquals(Color.web("#F9FF00"), c.getSkinColour(), "Skin colour should not be set to default hair colour");

        c.setSkinColour(Color.web("#FFE8D8")); // default skin colour
        assertEquals(Color.web("#FFE8D8").toString(), c.getSkinColour().toString(),  "Skin colour should not be set to default skin colour");

        c.setSkinColour(Color.web("#FF0000")); // default lip colour
        assertNotEquals(Color.web("#FF0000").toString(), c.getSkinColour().toString(), "Skin colour should be set to default lip colour");
    }

    @Test
    void testSetDefaultPose(){
        Character c = new Character();
        c.setDefaultPose();
        assertEquals("default.png", c.getPoseString());
    }
}
