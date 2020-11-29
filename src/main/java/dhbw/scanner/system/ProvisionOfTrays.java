package dhbw.scanner.system;

public class ProvisionOfTrays {

    private BaggageScanner baggageScanner;
    private final int maxNumberOfTrays;

    private int numberOfTrays;

    public ProvisionOfTrays(BaggageScanner baggageScanner, int maxNumberOfTrays) {
        this.baggageScanner = baggageScanner;
        this.numberOfTrays = maxNumberOfTrays;
        this.maxNumberOfTrays = maxNumberOfTrays;
    }

    public boolean onTakeATray() {
        if (numberOfTrays <= 0) {
            return false;
        }

        numberOfTrays--;
        return true;
    }

    public void onGiveTrayBack() {
        if (numberOfTrays >= maxNumberOfTrays) {
            return;
        }

        numberOfTrays++;
    }

    // Getter and setter
    public int getNumberOfTrays() {
        return numberOfTrays;
    }
}
