import excelsior.panel.TextBubble;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextBubbleTest {
    private static JFXPanel fxPanel;
    private static TextBubble tbub;

    @BeforeAll
    public static void init()
    {
        fxPanel = new JFXPanel();
    }

    @BeforeEach
    public  void initTbub()
    {
        tbub = new TextBubble();
    }

    /**
     * Tests textBubble setup
     */
    @Test
    void setupTest() {
        assertTrue(tbub.isEmpty());
        assertTrue(tbub.getTextSize() == 16);
        assertTrue(tbub.getBubbleType().equals("null"));
        assertNull(tbub.getBubble().getImage());
    }

    /**
     * Tests textBubble setSpeech
     */
    @Test
    void setSpeechTest() {
        tbub.setSpeech();
        assertTrue(tbub.getBubbleType().equals("speech"));
        assertNotNull(tbub.getBubble().getImage());
    }

    /**
     * Tests textBubble setThought
     */
    @Test
    void setThoughtTest() {
        tbub.setThought();
        assertTrue(tbub.getBubbleType().equals("thought"));
        assertNotNull(tbub.getBubble().getImage());
    }

    /**
     * Tests textBubble setEmpty
     */
    @Test
    void setEmptyTest() {
        tbub.setThought();
        tbub.setText("test");
        assertFalse(tbub.isEmpty());
        assertTrue(tbub.getBubbleType().equals("thought"));
        assertNotNull(tbub.getBubble().getImage());
        tbub.setEmpty();
        assertTrue(tbub.isEmpty());
        assertTrue(tbub.getBubbleType().equals("null"));
        assertNull(tbub.getBubble().getImage());
    }

    /**
     * Tests textBubble minimise
     */
    @Test
    void minimiseTest() {
        assertTrue(tbub.getBubble().getFitHeight() == 160);
        assertTrue(tbub.getBubble().getFitWidth() == 300);
        assertTrue(((int) tbub.getText().getMaxWidth()) ==300);
        tbub.minimise();
        assertTrue(tbub.getBubble().getFitHeight() == 80);
        assertTrue(tbub.getBubble().getFitWidth() == 135);
        assertTrue(((int) tbub.getText().getMaxWidth()) == 135);
    }


}
