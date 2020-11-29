package dhbw.scanner.system;

import dhbw.scanner.Configuration;
import dhbw.scanner.authentication.ProfileType;
import dhbw.scanner.employees.Employee;
import dhbw.scanner.records.Record;
import dhbw.scanner.records.RecordResultType;
import dhbw.scanner.utils.AuthUtils;

public class BaggageScanner {

    private ProvisionOfTrays provisionOfTrays;
    private RollerConveyor rollerConveyor;
    private Belt belt;
    private Scanner scanner;
    private OperatingStation operatingStation;
    private ManualPostControl manualPostControl;
    private Supervision supervision;
    private Belt track01;
    private Belt track02;

    private State state;

    public BaggageScanner(Configuration configuration) {
    }

    public boolean start(Employee employee) {
        if (!AuthUtils.checkAuthorization(employee.getIDCard(), ProfileType.S)) {
            return false;
        }

        state = State.DEACTIVATED;
        return true;
    }

    public boolean shutdown(Employee employee) {
        if (!AuthUtils.checkAuthorization(employee.getIDCard(), ProfileType.S)) {
            return false;
        }

        state = State.SHUTDOWN;
        return true;
    }

    public boolean moveBeltForward(Employee employee) {
        if (!AuthUtils.checkAuthorization(employee.getIDCard(), ProfileType.I)) {
            return false;
        }

        scanner.setTrayToScan(belt.getCurrentTray());
        return true;
    }

    public boolean moveBeltBackwards(Employee employee) {
        if (!AuthUtils.checkAuthorization(employee.getIDCard(), ProfileType.I)) {
            return false;
        }

        scanner.setTrayToScan(track02.getCurrentTray());
        return true;
    }

    public boolean scan(Employee employee) {
        if (!AuthUtils.checkAuthorization(employee.getIDCard(), ProfileType.I)) {
            return false;
        }

        Record record = scanner.scan();
        if (record == null) {
            return true;
        }

        if (record.getResult().getType() != RecordResultType.CLEAN) {
            // employee.onProhibitedItemFound(record);
        }

        return true;
    }

    public boolean alarm(Employee employee) {
        if (!AuthUtils.checkAuthorization(employee.getIDCard(), ProfileType.I)) {
            return false;
        }

        state = State.LOCKED;
        return true;

    }

    public boolean report(Employee employee) {
        if (!AuthUtils.checkAuthorization(employee.getIDCard(), ProfileType.S)) {
            return false;
        }

        return true;
    }

    public boolean maintenance(Employee employee) {
        if (!AuthUtils.checkAuthorization(employee.getIDCard(), ProfileType.T)) {
            return false;
        }

        return true;
    }

    public boolean unlock(Employee employee, String pin) {
        if (!authorizedToUnlock(employee, pin)) {
            return false;
        }

        state = State.ACTIVATED;
        return true;
    }

    private boolean authorizedToUnlock(Employee employee, String pin) {
        return AuthUtils.checkAuthorization(employee.getIDCard(), ProfileType.S)
                && employee.getIDCard().getMagnetStripe().getPin(Configuration.SECRET_KEY).equals(pin);
    }

    // Getter and setter
    public ProvisionOfTrays getProvisionOfTrays() {
        return provisionOfTrays;
    }

    public RollerConveyor getRollerConveyor() {
        return rollerConveyor;
    }

    public Belt getBelt() {
        return belt;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public OperatingStation getOperatingStation() {
        return operatingStation;
    }

    public ManualPostControl getManualPostControl() {
        return manualPostControl;
    }

    public Supervision getSupervision() {
        return supervision;
    }

    public Belt getTrack01() {
        return track01;
    }

    public Belt getTrack02() {
        return track02;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
