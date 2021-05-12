package excelsior;

public class StringPreparer {
    // Sort through fonts for text bubble
    public String prepareTBub(String s, TextBubble tBub) {
        String output;
        int largeFont = 20;
        int mediumFont = 16;
        int smallFont = 13;
        output = prepareString(s, 3, 17);
        if (output != null) {
            tBub.setTextSize(largeFont);
            return output;
        }
        output = prepareString(s, 4, 18);
        if (output != null) {
            tBub.setTextSize(mediumFont);
            return output;
        }
        output = prepareString(s, 5, 25);
        if (output != null) {
            tBub.setTextSize(smallFont);
            return output;
        }

        return null;
    }

    // Dynamically change font size for narration depending on input length
    public String prepareNarration(String s, Narration narration) {
        String output;

        output = prepareString(s, 2, 50);
        if (output != null) {
            narration.setTextSize(20);
            return output;
        }
        output = prepareString(s, 2, 63);
        if (output != null) {
            narration.setTextSize(16);
            return output;
        }

        output = prepareString(s, 3, 77);
        if (output != null) {
            narration.setTextSize(13);
            return output;
        }

        return null;
    }

    // Prepares String for text bubbles and returns null if exceeds acceptable length
    public String prepareString(String s, int numLines, int charPerLine) {
        String output;
        int lastSpace = 0;
        int lineLength = 0;
        int index = 0;
        char curr;
        StringBuilder outputBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            curr = s.charAt(i);
            lineLength++;
            if (curr == ' ') {
                lastSpace = i;
            }
            if (lineLength == charPerLine && i != (s.length() - 1)) {
                if (i == lastSpace || (i - lastSpace >= charPerLine - 1)) {
                    outputBuilder.append(curr).append("\n");
                } else {
                    outputBuilder = new StringBuilder(outputBuilder.substring(0, lastSpace + index + 1) + "\n" + outputBuilder.substring(lastSpace + index + 1) + curr);
                }
                lineLength = (i - lastSpace < charPerLine - 1) ? (i - lastSpace) : 0;
                index++;
            } else outputBuilder.append(curr);
        }
        output = outputBuilder.toString();
        if (index + 1 > numLines) output = null;
        return output;
    }
}
