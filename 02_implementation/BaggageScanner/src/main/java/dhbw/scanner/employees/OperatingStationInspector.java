package dhbw.scanner.employees;

import dhbw.scanner.records.Record;
import dhbw.scanner.system.Button;
import dhbw.scanner.system.OperatingStation;

import java.time.LocalDate;

public class OperatingStationInspector extends Inspector {

    private OperatingStation operatingStation;

    public OperatingStationInspector(int id, String name, LocalDate birthDate, boolean isSenior, String idCardPin) {
        super(id, name, birthDate, isSenior, idCardPin);
    }

    public boolean swipeIDCardThroughReader(String pin) {
        if (operatingStation == null) {
            System.out.println("Inspector " + name +  " is not at work!");
            return false;
        }

        return operatingStation.getReader().verify(idCard, pin);
    }

    public void pushButton(Button button) {
        if (operatingStation == null) {
            System.out.println("Inspector " + name +  " is not at work!");
            return;
        }

        button.push(this);
    }

    public void onProhibitedItemFound(Record record) {
        switch (record.getResult().getType()) {
            case DETECTED_KNIFE -> report();
            case DETECTED_WEAPON, DETECTED_EXPLOSIVE -> alarm();
        }
    }

    private void report() {
        operatingStation.getBaggageScanner().report(this);
    }

    private void alarm() {
        operatingStation.getBaggageScanner().alarm(this);
    }

    // Getter and setter
    public void setOperatingStation(OperatingStation operatingStation) {
        this.operatingStation = operatingStation;
    }

    public OperatingStation getOperatingStation() {
        return operatingStation;
    }
}
