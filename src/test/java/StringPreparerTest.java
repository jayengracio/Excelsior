import excelsior.input.StringPreparer;
import excelsior.panel.Narration;
import excelsior.panel.TextBubble;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringPreparerTest {

    private static StringPreparer preparer;
    private static JFXPanel fxPanel;
    private static String trueMax;

    @BeforeAll
    public static void init()
    {
        preparer = new StringPreparer();
        fxPanel = new JFXPanel();
        //max string len accepted is 231, for ease of string making in tests i just usesubstrings of trueMax
        trueMax = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111";
    }

    /**
     * Tests narration limits for string length when string has no spaces or is all spaces
     */
    @Test
    void prepareNarrationTestLimitWhole() {
        Narration narration = new Narration();
        assertEquals(preparer.prepareNarration("",narration), "");
        String max = trueMax;

        assertNotNull(preparer.prepareNarration(max.replace('1',' '),narration));
        assertNull(preparer.prepareNarration(max+"1",narration));
        assertTrue((preparer.prepareNarration(max,narration).split("\n", -1).length - 1) == 2);

        max = max.replace('1',' ');
        assertNotNull(preparer.prepareNarration(max,narration));
        assertNull(preparer.prepareNarration(max+" ",narration));
        assertTrue((preparer.prepareNarration(max,narration).split("\n", -1).length - 1) == 2);
    }

    /**
            * Tests text bubble limits for string length when string has no spaces or is all spaces
     */
    @Test
    void prepareTBubTestLimitWhole() {
        TextBubble tbub = new TextBubble();
        assertEquals(preparer.prepareTBub("",tbub), "");
        String max = trueMax.substring(0,125);

        assertNotNull(preparer.prepareTBub(max,tbub));
        assertNull(preparer.prepareTBub(max+"1",tbub));
        assertTrue((preparer.prepareTBub(max,tbub).split("\n", -1).length - 1) == 4);

        max = max.replace('1',' ');
        assertNotNull(preparer.prepareTBub(max,tbub));
        assertNull(preparer.prepareTBub(max+"1",tbub));
        assertTrue((preparer.prepareTBub(max,tbub).split("\n", -1).length - 1) == 4);
    }

    /**
     * Tests Tests that strings of certain length fall into the right if statements
     */
    @Test
    void prepareNarrationTestScenario() {
        Narration narration = new Narration();

        preparer.prepareNarration("",narration);
        assertTrue(narration.getTextSize() == 20);
        preparer.prepareNarration(trueMax.substring(0,100),narration);
        assertTrue(narration.getTextSize() == 20);

        preparer.prepareNarration(trueMax.substring(0,101),narration);
        assertTrue(narration.getTextSize() == 16);
        preparer.prepareNarration(trueMax.substring(0,126),narration);
        assertTrue(narration.getTextSize() == 16);

        preparer.prepareNarration(trueMax.substring(0,127),narration);
        assertTrue(narration.getTextSize() == 13);
        preparer.prepareNarration(trueMax.substring(0,231),narration);
        assertTrue(narration.getTextSize() == 13);

    }



    /**
     * Tests that strings of certain length fall into the right if statements
     */
    @Test
    void prepareTBubTestScenario() {
        TextBubble tbub = new TextBubble();
        preparer.prepareTBub("",tbub);
        assertTrue(tbub.getTextSize() == 20);
        preparer.prepareTBub(trueMax.substring(0,51),tbub);
        assertTrue(tbub.getTextSize() == 20);

        preparer.prepareTBub(trueMax.substring(0,52),tbub);
        assertTrue(tbub.getTextSize() == 16);
        preparer.prepareTBub(trueMax.substring(0,72),tbub);
        assertTrue(tbub.getTextSize() == 16);

        preparer.prepareTBub(trueMax.substring(0,73),tbub);
        assertTrue(tbub.getTextSize() == 13);
        preparer.prepareTBub(trueMax.substring(0,125),tbub);
        assertTrue(tbub.getTextSize() == 13);
    }

    /**
     * Tests last line limit with space for Narration
     */
    @Test
    void prepareNarrationTestSpace() {
        Narration narration = new Narration();
        String underWithSpace = trueMax.substring(0,78)+" "+trueMax.substring(0,77);
        assertNotNull(preparer.prepareNarration(underWithSpace,narration));
        assertNull(preparer.prepareNarration(underWithSpace+"1",narration));
        assertTrue((preparer.prepareNarration(underWithSpace,narration).split("\n", -1).length - 1) == 2);
    }

    /**
     * Tests last line limit with space for TextBubble
     */
    @Test
    void prepareTBubTestSpace() {
        TextBubble tbub = new TextBubble();
        String underWithSpace = trueMax.substring(0,76)+" "+trueMax.substring(0,25);
        assertNotNull(preparer.prepareTBub(underWithSpace,tbub));
        assertNull(preparer.prepareTBub(underWithSpace+"1",tbub));
        assertTrue((preparer.prepareTBub(underWithSpace,tbub).split("\n", -1).length - 1) == 4);
    }
}
