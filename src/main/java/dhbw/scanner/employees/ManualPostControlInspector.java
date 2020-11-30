package dhbw.scanner.employees;


import dhbw.scanner.ProhibitedItem;
import dhbw.scanner.passengers.HandBaggage;
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

    // Getter and setter
    public void setManualPostControl(ManualPostControl manualPostControl) {
        this.manualPostControl = manualPostControl;
    }

    public ManualPostControl getManualPostControl() {
        return manualPostControl;
    }
}
