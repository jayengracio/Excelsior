package excelsior;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class ColourMap {
    static HashMap<String, Color> COLOR_MAP = new HashMap<>();

    public static Color getColorNamed(String name) {
        if (COLOR_MAP.isEmpty()) createColorMap();
        return COLOR_MAP.get(name);
    }

    private static void createColorMap() {
        COLOR_MAP.put("brown",  Color.web("#964B00"));
        COLOR_MAP.put("blue", Color.web("#0900FFFF"));
        COLOR_MAP.put("pink", Color.PINK);
        COLOR_MAP.put("black", Color.web("#090000"));
        COLOR_MAP.put("white", Color.web("#F5FFFA"));
        COLOR_MAP.put("cyan", Color.CYAN);
        COLOR_MAP.put("gray", Color.GRAY);
        COLOR_MAP.put("grey", Color.GRAY);
        COLOR_MAP.put("green", Color.web("#09FF00"));
        COLOR_MAP.put("orange", Color.ORANGE);
        COLOR_MAP.put("magenta", Color.MAGENTA);
        COLOR_MAP.put("red", Color.RED);
        COLOR_MAP.put("yellow", Color.YELLOW);
        COLOR_MAP.put("dark gray", Color.DARKGRAY);
        COLOR_MAP.put("dark grey", Color.DARKGRAY);
        COLOR_MAP.put("light gray", Color.LIGHTGRAY);
        COLOR_MAP.put("light grey", Color.LIGHTGRAY);
        COLOR_MAP.put("gold", Color.web("#FFD700"));
        COLOR_MAP.put("olive", Color.web("#FFE4B5"));
        COLOR_MAP.put("violet", Color.web("#EE82EE"));
        COLOR_MAP.put("silver", Color.web("#C0C0C0"));
        COLOR_MAP.put("slate", Color.web("#778899"));
        COLOR_MAP.put("dark slate", Color.web("#2F4F4F"));
        COLOR_MAP.put("purple", Color.web("#800080"));
        COLOR_MAP.put("plum", Color.web("#DDA0DD"));
        COLOR_MAP.put("lavender", Color.web("#E6E6FA"));
        COLOR_MAP.put("crimson", Color.web("#DC143C"));
        COLOR_MAP.put("brick red", Color.web("#B22222"));
        COLOR_MAP.put("maroon", Color.web("#800000"));
        COLOR_MAP.put("lime green", Color.web("#32CD32"));
        COLOR_MAP.put("forest green", Color.web("#228B22"));
        COLOR_MAP.put("sea green", Color.web("#8FBC8F"));
        COLOR_MAP.put("khaki", Color.web("#F0E68C"));
        COLOR_MAP.put("aquamarine", Color.web("#7FFFD4"));
        COLOR_MAP.put("lemon chiffon", Color.web("#FFFACD"));
        COLOR_MAP.put("light yellow", Color.web("#FFFFCC"));
        COLOR_MAP.put("light pink", Color.web("#FFB6C1"));
        COLOR_MAP.put("hot pink", Color.web("#FF69B4"));
        COLOR_MAP.put("dark yellow", Color.web("#CCCC00"));
        COLOR_MAP.put("indigo", Color.web("#4B0082"));
        COLOR_MAP.put("coral", Color.web("#FF7F50"));
        COLOR_MAP.put("chocolate", Color.web("#D2691E"));
        COLOR_MAP.put("tomato", Color.web("#FF6347"));
        COLOR_MAP.put("dark orange", Color.web("#FF8C00"));
    }
}
