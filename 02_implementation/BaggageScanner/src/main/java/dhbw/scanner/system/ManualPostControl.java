package dhbw.scanner.system;

import dhbw.scanner.employees.ManualPostControlInspector;

public class ManualPostControl {

    private BaggageScanner baggageScanner;
    private ExplosiveTraceDetector explosiveTraceDetector;

    public ManualPostControl(BaggageScanner baggageScanner, ManualPostControlInspector inspector) {
        this.baggageScanner = baggageScanner;
        this.explosiveTraceDetector = new ExplosiveTraceDetector();

        inspector.setManualPostControl(this);
    }

    // Getter and setter
    public ExplosiveTraceDetector getExplosiveTraceDetector() {
        return explosiveTraceDetector;
    }
}
