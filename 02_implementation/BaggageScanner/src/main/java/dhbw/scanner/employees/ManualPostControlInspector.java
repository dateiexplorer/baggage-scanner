package dhbw.scanner.employees;


import dhbw.scanner.baggage.ProhibitedItem;
import dhbw.scanner.baggage.HandBaggage;
import dhbw.scanner.records.Record;
import dhbw.scanner.system.ManualPostControl;
import dhbw.scanner.utils.Utils;

import java.time.LocalDate;

public class ManualPostControlInspector extends Inspector {

    private ManualPostControl manualPostControl;

    public ManualPostControlInspector(int id, String name, LocalDate birthDate, boolean isSenior, String idCardPin) {
        super(id, name, birthDate, isSenior, idCardPin);

    }

    public ProhibitedItem removeKnifeFromHandBaggage(HandBaggage handBaggage, Record record) {
        if (manualPostControl == null) {
            System.out.println("Inspector " + name +  " is not at work!");
        }

        return Utils.removeItemFromHandBaggageAtPosition(ProhibitedItem.KNIFE, handBaggage, record.getResult().getPosition());
    }

    public boolean swipeAndDetectedExplosive(String testStripe) {
        if (manualPostControl == null) {
            System.out.println("Inspector " + name +  " is not at work!");
        }

        return manualPostControl.getExplosiveTraceDetector().read(testStripe);
    }

    // Getter and setter
    public void setManualPostControl(ManualPostControl manualPostControl) {
        this.manualPostControl = manualPostControl;
    }

    public ManualPostControl getManualPostControl() {
        return manualPostControl;
    }
}
