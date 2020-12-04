package dhbw.scanner.police;

import dhbw.scanner.baggage.HandBaggage;

public class Robot {

    private FederalPoliceOfficer owner;

    public String[] destroy(HandBaggage handBaggage) {
        String content = handBaggage.getContent();
        String[] destroyedHandBaggage = new String[1000];
        for (int i = 0; i < destroyedHandBaggage.length - 1; i++) {
            destroyedHandBaggage[0] = content.substring(i * 50, (i + 1) * 50);
        }

        handBaggage.setLayers(destroyedHandBaggage);
        return destroyedHandBaggage;
    }

    // Getter and setter
    public void setOwner(FederalPoliceOfficer owner) {
        this.owner = owner;
    }

    public FederalPoliceOfficer getOwner() {
        return owner;
    }
}
