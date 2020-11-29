package dhbw.scanner.employees;

import dhbw.scanner.system.RollerConveyor;

import java.time.LocalDate;

public class RollerConveyorInspector extends Inspector {

    private RollerConveyor rollerConveyor;

    public RollerConveyorInspector(int id, String name, LocalDate birthDate, boolean isSenior, String idCardPin) {
        super(id, name, birthDate, isSenior, idCardPin);
    }

    public void pushNextTray() {
        if (rollerConveyor == null) {
            System.out.println("Inspector " + name +  " is not at work!");
            return;
        }

        // Push next baggage to the belt.
        rollerConveyor.getBaggageScanner().getBelt().setCurrentTray(rollerConveyor.getNextTray());
    }

    public void setRollerConveyor(RollerConveyor rollerConveyor) {
        this.rollerConveyor = rollerConveyor;
    }

    public RollerConveyor getRollerConveyor() {
        return rollerConveyor;
    }
}
