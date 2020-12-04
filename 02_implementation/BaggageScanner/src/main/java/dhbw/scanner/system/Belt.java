package dhbw.scanner.system;

public class Belt {

    private BaggageScanner baggageScanner;
    private Tray currentTray;

    public Belt(BaggageScanner baggageScanner) {
        this.baggageScanner = baggageScanner;
    }

    public void setCurrentTray(Tray currentTray) {
        this.currentTray = currentTray;
    }

    public Tray getCurrentTray() {
        return currentTray;
    }
}
