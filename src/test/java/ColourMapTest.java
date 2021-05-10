import excelsior.ColourMap;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColourMapTest {

    @Test
    void colourTest() {
        assertEquals(Color.web("#0900FFFF").toString(), ColourMap.getColorNamed("blue").toString());
        assertEquals(Color.web("#964B00").toString(), ColourMap.getColorNamed("brown").toString());
        assertEquals(Color.CYAN.toString(), ColourMap.getColorNamed("cyan").toString());
        assertEquals(Color.web("#F5FFFA").toString(), ColourMap.getColorNamed("white").toString());
        assertEquals(Color.web("#09FF00").toString(), ColourMap.getColorNamed("green").toString());
        assertEquals(Color.RED.toString(), ColourMap.getColorNamed("red").toString());
    }
}
