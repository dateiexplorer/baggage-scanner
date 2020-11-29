package dhbw.scanner.passengers;

public class HandBaggage {

    private int id;
    private String[] layers;

    public HandBaggage(int id) {
        layers = new String[5];

        // TODO: Read file into layers
    }

    public String getContent() {
        String content = "";
        for (String layer : layers) {
            content += layer;
        }

        return content;
    }

    // Getter and setter
    public int getID() {
        return id;
    }

    public void setLayers(String[] layers) {
        this.layers = layers;
    }

    public String[] getLayers() {
        return layers;
    }

}
