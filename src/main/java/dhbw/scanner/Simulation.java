package dhbw.scanner;

import dhbw.scanner.employees.*;
import dhbw.scanner.passengers.HandBaggage;
import dhbw.scanner.passengers.Passenger;
import dhbw.scanner.police.FederalPoliceOffice;
import dhbw.scanner.police.Robot;
import dhbw.scanner.records.Record;
import dhbw.scanner.records.RecordResultType;
import dhbw.scanner.system.BaggageScanner;
import dhbw.scanner.system.Tray;
import dhbw.scanner.utils.FileUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class Simulation {

    private static int employeeID = 0;

    private FederalPoliceOfficer o2;
    private FederalPoliceOfficer o3;

    private Technician technician;

    private BaggageScanner b;
    private FederalPoliceOffice office;

    private ArrayList<Passenger> passengers;

    private String testStripe;

    public Simulation() {
        initialize();
    }

    private void initialize() {
        office = new FederalPoliceOffice();

        office.register(new FederalPoliceOfficer(employeeID++, "Toto",
                LocalDate.of(1969, 1, 1), "Officer", office, Configuration.INITIAL_PW));
        office.register(new FederalPoliceOfficer(employeeID++, "Harry",
                LocalDate.of(1969, 1, 1), "Officer", office, Configuration.INITIAL_PW));

        technician = new Technician(employeeID++, "Jason Statham",
                LocalDate.of(1967, 7, 26), Configuration.INITIAL_PW);

        testStripe = FileUtils.readFileAsString(Configuration.DIRECTORY_PATH + "testStripe.txt");

        b = new BaggageScanner(
                new RollerConveyorInspector(employeeID++, "Clint Eastwood",
                        LocalDate.of(1930, 5, 31), true, Configuration.INITIAL_PW),
                new OperatingStationInspector(employeeID++, "Natalie Portman",
                        LocalDate.of(1981, 6, 9), false, Configuration.INITIAL_PW),
                new ManualPostControlInspector(employeeID++, "Bruce Willis",
                        LocalDate.of(1955, 3, 19), true, Configuration.INITIAL_PW),
                new Supervisor(employeeID++, "Jodie Foster", LocalDate.of(1962, 11, 19),
                        false, true, Configuration.INITIAL_PW),
                new FederalPoliceOfficer(employeeID++, "Wesley Snipes",
                        LocalDate.of(1962, 07, 31), "Officer", office,
                        Configuration.INITIAL_PW));

        passengers = new ArrayList<>();
        // TODO: Add passengers
    }

    public void scanAllPassengers() {
        for (Passenger p : passengers) {
            scan(p);
        }

        // After all scans do maintenance.
        b.maintenance(technician);
    }

    public void scan(Passenger passenger) {
        passenger.tryToPutHandBaggageOnRollerConveyor(b);

        // Scan all hand baggage
        while (!b.getRollerConveyor().isEmpty()) {
            b.getRollerConveyorInspector().pushNextTray();
            b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRightArrow());
            b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRectangle());

            Tray tray = b.getScanner().getTrayToScan();

            // Get last record.
            Record record = Record.getLastRecord();

            // CLEAN
            if (record.getResult().getType() == RecordResultType.CLEAN) {
                passenger.takeHandBaggageFromTrack02(b);
                return;
            }

            // Prohibited item detected.

            // DETECTED_KNIFE
            if (record.getResult().getType() == RecordResultType.DETECTED_KNIFE) {
                b.getManualPostControlInspector().removeKnifeFromHandBaggage(tray.getHandBaggage(), record);

                b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonLeftArrow());
                b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRectangle());
                return;
            }

            // DETECTED_WEAPON | DETECTED_EXPLOSIVE
            // Alarm, baggage scanner is locked!

            b.getFederalPoliceOfficer().arrest(passenger);
            o2 = office.getFederalPoliceOfficer();
            o3 = office.getFederalPoliceOfficer();

            // DETECTED_WEAPON
            if (record.getResult().getType() == RecordResultType.DETECTED_WEAPON) {
                ProhibitedItem weapon =
                        b.getFederalPoliceOfficer().removeWeaponFromBaggage(tray.getHandBaggage(), record);
                o3.setProhibitedItem(weapon);

                // Scan next baggage
                continue;
            }

            // DETECTED_EXPLOSIVE
            Robot robot = office.getRandomRobot(o2);
            if (record.getResult().getType() == RecordResultType.DETECTED_EXPLOSIVE) {
                if (b.getManualPostControlInspector().swipeAndDetectedExplosive(testStripe)) {
                    robot.destroy(tray.getHandBaggage());
                }
            }

            // Leave control area.
            o2.setOnDuty(false);
            o3.setOnDuty(false);

            // Unlock baggage scanner.
            b.unlock(b.getSupervisor(),
                    b.getSupervisor().getIDCard().getMagnetStripe().getPin(Configuration.SECRET_KEY));
        }
    }

}
