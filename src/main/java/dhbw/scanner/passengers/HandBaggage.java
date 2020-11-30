package dhbw.scanner.passengers;

import dhbw.scanner.Configuration;
import dhbw.scanner.utils.FileUtils;

public class HandBaggage {

    private int id;
    private String[] layers;

    private Passenger owner;

    public HandBaggage(int id, Passenger owner) {
        this.owner = owner;
        layers = new String[5];

        layers = FileUtils.loadFileAsBaggageLayers(Configuration.DIRECTORY_PATH + id + ".txt");
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

    public Passenger getOwner() {
        return owner;
    }

    public void setLayers(String[] layers) {
        this.layers = layers;
    }

    public String[] getLayers() {
        return layers;
    }

}
