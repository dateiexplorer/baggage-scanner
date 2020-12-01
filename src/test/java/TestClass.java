import dhbw.scanner.Configuration;
import dhbw.scanner.Simulation;
import dhbw.scanner.authentication.IDCard;
import dhbw.scanner.authentication.MagnetStripe;
import dhbw.scanner.authentication.ProfileType;
import dhbw.scanner.employees.Employee;
import dhbw.scanner.passengers.HandBaggage;
import dhbw.scanner.passengers.Passenger;
import dhbw.scanner.records.Record;
import dhbw.scanner.records.RecordResultType;
import dhbw.scanner.system.BaggageScanner;
import dhbw.scanner.system.State;
import dhbw.scanner.system.Tray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    private static final String FALSE_PIN = "0000";

    private Simulation sim;
    private BaggageScanner b;

    private Employee employeeI;
    private Employee employeeS;
    private Employee employeeO;
    private Employee employeeT;
    private Employee employeeK;

    @BeforeEach
    public void setup() {
        sim = new Simulation();
        b = sim.getBaggageScanner();

        employeeI = buildGenericEmployee(ProfileType.I);
        employeeS = buildGenericEmployee(ProfileType.S);
        employeeO = buildGenericEmployee(ProfileType.O);
        employeeT = buildGenericEmployee(ProfileType.T);
        employeeK = buildGenericEmployee(ProfileType.K);
    }

    @Test
    public void testSimulationInitialization() {
        var passengers = sim.getPassengers();
        assertEquals(568, passengers.size());

        int totalHandBaggage = 0;
        for (Passenger p : passengers) {
            totalHandBaggage += p.getHandBaggage().size();
        }

        assertEquals(609, totalHandBaggage);
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
                .verify(employeeK.getIDCard(), Configuration.INITIAL_PW));
        assertFalse(b.getOperatingStation().getReader()
                .verify(employeeO.getIDCard(), Configuration.INITIAL_PW));
    }

    @Test
    public void testBaggageFunctionsByProfileType() {
        // K
        assertFalse(b.moveBeltForward(employeeK));
        assertFalse(b.moveBeltBackwards(employeeK));
        assertFalse(b.scan(employeeK));
        assertFalse(b.alarm(employeeK));
        assertFalse(b.report(employeeK));
        assertFalse(b.maintenance(employeeK));

        // O
        assertFalse(b.moveBeltForward(employeeO));
        assertFalse(b.moveBeltBackwards(employeeO));
        assertFalse(b.scan(employeeO));
        assertFalse(b.alarm(employeeO));
        assertFalse(b.report(employeeO));
        assertFalse(b.maintenance(employeeO));

        // I
        assertTrue(b.moveBeltForward(employeeI));
        assertTrue(b.moveBeltBackwards(employeeI));
        assertTrue(b.scan(employeeI));
        assertTrue(b.alarm(employeeI));
        assertFalse(b.report(employeeI));
        assertFalse(b.maintenance(employeeI));

        // S
        assertFalse(b.moveBeltForward(employeeS));
        assertFalse(b.moveBeltBackwards(employeeS));
        assertFalse(b.scan(employeeS));
        assertFalse(b.alarm(employeeS));
        assertTrue(b.report(employeeS));
        assertFalse(b.maintenance(employeeS));

        // T
        assertFalse(b.moveBeltForward(employeeT));
        assertFalse(b.moveBeltBackwards(employeeT));
        assertFalse(b.scan(employeeT));
        assertFalse(b.alarm(employeeT));
        assertFalse(b.report(employeeT));
        assertTrue(b.maintenance(employeeT));
    }

    @Test
    public void testSupervisorUnlockBaggageScanner() {
        b.setState(State.LOCKED);
        assertFalse(b.unlock(employeeK, Configuration.INITIAL_PW));
        assertFalse(b.unlock(employeeO, Configuration.INITIAL_PW));
        assertFalse(b.unlock(employeeI, Configuration.INITIAL_PW));
        assertFalse(b.unlock(employeeT, Configuration.INITIAL_PW));
        assertTrue(b.unlock(employeeS, Configuration.INITIAL_PW));
    }

    @Test
    public void testDetectKnife() {
        // Baggage (0) from passenger (6) has a knife
        assertTrue(testProhibitedItem(sim.getPassengers().get(6).getHandBaggage().get(0),
                RecordResultType.DETECTED_KNIFE));
    }

    @Test
    public void testDetectWeapon() {
        // Baggage (1) from passenger (14) has a weapon
        assertTrue(testProhibitedItem(sim.getPassengers().get(14).getHandBaggage().get(1),
                RecordResultType.DETECTED_WEAPON));
    }

    @Test
    public void testDetectExplosive() {
        // Baggage (0) from passenger (418) has an explosive
        assertTrue(testProhibitedItem(sim.getPassengers().get(418).getHandBaggage().get(0),
                RecordResultType.DETECTED_EXPLOSIVE));
    }

    private Employee buildGenericEmployee(ProfileType profileType) {
        return new Employee(0, null, null,
                new IDCard(0, null, new MagnetStripe(profileType, Configuration.INITIAL_PW)));
    }

    private boolean testProhibitedItem(HandBaggage handBaggage, RecordResultType expectedValue) {
        Tray tray = new Tray(handBaggage);
        b.getScanner().scan(tray);

        RecordResultType recordResultType = Record.getLastRecord().getResult().getType();
        return recordResultType == expectedValue;
    }
}
