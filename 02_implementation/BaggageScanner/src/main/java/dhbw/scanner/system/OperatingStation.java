package dhbw.scanner.system;

import dhbw.scanner.employees.OperatingStationInspector;

public class OperatingStation {

    private BaggageScanner baggageScanner;

    // IDCard Reader
    private Reader reader;

    // Buttons
    private Button buttonRightArrow;
    private Button buttonLeftArrow;
    private Button buttonRectangle;

    public OperatingStation(BaggageScanner baggageScanner, OperatingStationInspector inspector) {
        this.baggageScanner = baggageScanner;
        reader = new Reader(this);
        inspector.setOperatingStation(this);

        buttonRightArrow = new Button((employee) -> baggageScanner.moveBeltForward(employee));
        buttonLeftArrow = new Button((employee) -> baggageScanner.moveBeltBackwards(employee));
        buttonRectangle = new Button((employee) -> baggageScanner.scan(employee));
    }

    // Getter and setter
    public BaggageScanner getBaggageScanner() {
        return baggageScanner;
    }

    public Reader getReader() {
        return reader;
    }

    public Button getButtonRightArrow() {
        return buttonRightArrow;
    }

    public Button getButtonLeftArrow() {
        return buttonLeftArrow;
    }

    public Button getButtonRectangle() {
        return buttonRectangle;
    }
}
