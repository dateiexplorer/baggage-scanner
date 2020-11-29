package dhbw.scanner.records;

public class Position {

    private int layerIndex;
    private int charIndex;

    public Position(int layerIndex, int charIndex) {
        this.layerIndex = layerIndex;
        this.charIndex = charIndex;
    }

    @Override
    public String toString() {
        return layerIndex + ":" + charIndex;
    }

    // Getter and setter
    public int getLayerIndex() {
        return layerIndex;
    }

    public int getCharIndex() {
        return charIndex;
    }
}
