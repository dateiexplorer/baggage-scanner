package dhbw.scanner.system;

import dhbw.scanner.employees.ManualPostControlInspector;

public class ManualPostControl {

    private BaggageScanner baggageScanner;

    public ManualPostControl(BaggageScanner baggageScanner, ManualPostControlInspector inspector) {
        this.baggageScanner = baggageScanner;
        inspector.setManualPostControl(this);
    }

}
