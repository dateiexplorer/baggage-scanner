package dhbw.scanner.system;

import dhbw.scanner.employees.Employee;
import dhbw.scanner.employees.Supervisor;

public class Supervision {

    private BaggageScanner baggageScanner;
    private Button buttonStartOrStop;

    public Supervision(BaggageScanner baggageScanner, Supervisor supervisor) {
        this.baggageScanner = baggageScanner;
        supervisor.setSupervision(this);

        buttonStartOrStop = new Button((employee) -> startOrStopBaggageScanner(employee));
    }

    private void startOrStopBaggageScanner(Employee employee) {
        State state = baggageScanner.getState();
        if (state != State.SHUTDOWN) {
            baggageScanner.shutdown(employee);
        } else {
            baggageScanner.start(employee);
        }
    }

    // Getter and setter
    public BaggageScanner getBaggageScanner() {
        return baggageScanner;
    }

    public Button getButtonStartOrStop() {
        return buttonStartOrStop;
    }
}
