package dhbw.scanner.system;

import dhbw.scanner.employees.RollerConveyorInspector;

import java.util.LinkedList;

public class RollerConveyor {

    private BaggageScanner baggageScanner;
    private LinkedList<Tray> queue;

    public RollerConveyor(BaggageScanner baggageScanner, RollerConveyorInspector inspector) {
        this.baggageScanner = baggageScanner;
        inspector.setRollerConveyor(this);
        queue = new LinkedList<>();
    }

    public boolean addTrayToQueue(Tray tray) {
        if (!baggageScanner.getProvisionOfTrays().onTakeATray()) {
            System.out.println("No trays available.");
            return false;
        }

        queue.add(tray);
        return true;
    }

    public Tray getNextTray() {
        if (isEmpty()) {
            System.out.println("The roller conveyor is empty.");
            return null;
        }

        return queue.removeFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public BaggageScanner getBaggageScanner() {
        return baggageScanner;
    }
}
