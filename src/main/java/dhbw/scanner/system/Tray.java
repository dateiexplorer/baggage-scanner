package dhbw.scanner.system;

import dhbw.scanner.passengers.HandBaggage;

public class Tray {

    private HandBaggage handBaggage;

    public Tray(HandBaggage handBaggage) {
        this.handBaggage = handBaggage;
    }

    // Getter and setter
    public void setHandBaggage(HandBaggage handBaggage) {
        this.handBaggage = handBaggage;
    }

    public HandBaggage getHandBaggage() {
        return handBaggage;
    }
}
