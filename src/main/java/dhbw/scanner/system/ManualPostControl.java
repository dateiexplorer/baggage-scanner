package dhbw.scanner.system;

import dhbw.scanner.employees.ManualPostControlInspector;
import dhbw.scanner.records.Record;

public class ManualPostControl {

    private BaggageScanner baggageScanner;

    public ManualPostControl(BaggageScanner baggageScanner, ManualPostControlInspector inspector) {
        this.baggageScanner = baggageScanner;
        inspector.setManualPostControl(this);
    }

    public void onProhibitedItemDetected(Record record) {
        Tray tray = baggageScanner.getTrack01().getCurrentTray();
        // inspector.removeKnifeFromHandBaggage(tray, record);

        // Tray remains on the track.
    }

}
