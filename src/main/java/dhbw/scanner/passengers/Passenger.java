package dhbw.scanner.passengers;

import dhbw.scanner.system.BaggageScanner;
import dhbw.scanner.system.Tray;

import java.util.ArrayList;

public class Passenger {

    private String name;
    private boolean arrested;
    private ArrayList<HandBaggage> handBaggage;

    public Passenger(String name) {
        this.name = name;
        handBaggage = new ArrayList<HandBaggage>();
    }

    public void addHandBaggage(HandBaggage handBaggage) {
        this.handBaggage.add(handBaggage);
    }

    public boolean tryToPutHandBaggageOnRollerConveyor(BaggageScanner baggageScanner) {
        for (HandBaggage b : handBaggage) {
            boolean succeeded = baggageScanner.getRollerConveyor().addTrayToQueue(new Tray(b));
            if (!succeeded) {
                return false;
            }
        }

        handBaggage.clear();
        return true;
    }

    public void takeHandBaggageFromTrack02(BaggageScanner baggageScanner) {
        handBaggage.add(baggageScanner.getTrack02().getCurrentTray().getHandBaggage());
        baggageScanner.getProvisionOfTrays().onGiveTrayBack();
    }

    // Getter and setter
    public String getName() {
        return name;
    }

    public void setArrested(boolean arrested) {
        this.arrested = arrested;
    }

    public boolean isArrested() {
        return arrested;
    }

    public ArrayList<HandBaggage> getHandBaggage() {
        return handBaggage;
    }
}
