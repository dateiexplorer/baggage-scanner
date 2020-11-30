import dhbw.scanner.Configuration;
import dhbw.scanner.Simulation;
import dhbw.scanner.system.BaggageScanner;
import dhbw.scanner.system.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    private static final String FALSE_PIN = "0000";

    private Simulation sim;
    private BaggageScanner b;

    @BeforeEach
    public void setup() {
        sim = new Simulation();
        b = sim.getBaggageScanner();
    }

    @Test
    public void testEmployeesAtBaggageScanner() {
        assertNotNull(b.getRollerConveyorInspector());
        assertNotNull(b.getOperatingStationInspector());
        assertNotNull(b.getManualPostControlInspector());
        assertNotNull(b.getSupervisor());
        assertNotNull(b.getFederalPoliceOfficer());
    }

    @Test
    public void testLockIDCardAfterThreeFailedAttempts() {
        for (int i = 0; i < Configuration.FAILED_ATTEMPTS_TO_LOCK; i++) {
            b.getOperatingStationInspector().swipeIDCardThroughReader(FALSE_PIN);
        }

        assertTrue(b.getOperatingStationInspector().getIDCard().isLocked());
    }

    @Test
    public void testProfileTypeKOrONotAccepted() {
        assertFalse(b.getOperatingStation().getReader()
                .verify(sim.getHouseKeeping().getIDCard(), Configuration.INITIAL_PW));
        assertFalse(b.getOperatingStation().getReader()
                .verify(b.getFederalPoliceOfficer().getIDCard(), Configuration.INITIAL_PW));
    }

    @Test
    public void testBaggageFunctionsByProfileType() {
        // K
        assertFalse(b.moveBeltForward(sim.getHouseKeeping()));
        assertFalse(b.moveBeltBackwards(sim.getHouseKeeping()));
        assertFalse(b.scan(sim.getHouseKeeping()));
        assertFalse(b.alarm(sim.getHouseKeeping()));
        assertFalse(b.report(sim.getHouseKeeping()));
        assertFalse(b.maintenance(sim.getHouseKeeping()));

        // O
        assertFalse(b.moveBeltForward(b.getFederalPoliceOfficer()));
        assertFalse(b.moveBeltBackwards(b.getFederalPoliceOfficer()));
        assertFalse(b.scan(b.getFederalPoliceOfficer()));
        assertFalse(b.alarm(b.getFederalPoliceOfficer()));
        assertFalse(b.report(b.getFederalPoliceOfficer()));
        assertFalse(b.maintenance(b.getFederalPoliceOfficer()));

        // I
        assertTrue(b.moveBeltForward(b.getOperatingStationInspector()));
        assertTrue(b.moveBeltBackwards(b.getOperatingStationInspector()));
        assertTrue(b.scan(b.getOperatingStationInspector()));
        assertTrue(b.alarm(b.getOperatingStationInspector()));
        assertFalse(b.report(b.getOperatingStationInspector()));
        assertFalse(b.maintenance(b.getOperatingStationInspector()));

        // S
        assertFalse(b.moveBeltForward(b.getSupervisor()));
        assertFalse(b.moveBeltBackwards(b.getSupervisor()));
        assertFalse(b.scan(b.getSupervisor()));
        assertFalse(b.alarm(b.getSupervisor()));
        assertTrue(b.report(b.getSupervisor()));
        assertFalse(b.maintenance(b.getSupervisor()));

        // T
        assertFalse(b.moveBeltForward(sim.getTechnician()));
        assertFalse(b.moveBeltBackwards(sim.getTechnician()));
        assertFalse(b.scan(sim.getTechnician()));
        assertFalse(b.alarm(sim.getTechnician()));
        assertFalse(b.report(sim.getTechnician()));
        assertTrue(b.maintenance(sim.getTechnician()));
    }

    @Test
    public void testSupervisorUnlockBaggageScanner() {
        b.setState(State.LOCKED);
        assertFalse(b.unlock(sim.getHouseKeeping(), Configuration.INITIAL_PW));
        assertFalse(b.unlock(b.getFederalPoliceOfficer(), Configuration.INITIAL_PW));
        assertFalse(b.unlock(b.getOperatingStationInspector(), Configuration.INITIAL_PW));
        assertFalse(b.unlock(sim.getTechnician(), Configuration.INITIAL_PW));
        assertTrue(b.unlock(b.getSupervisor(), Configuration.INITIAL_PW));
    }
}
