package dhbw.scanner;

import dhbw.scanner.baggage.ProhibitedItem;
import dhbw.scanner.employees.*;
import dhbw.scanner.baggage.HandBaggage;
import dhbw.scanner.police.FederalPoliceOffice;
import dhbw.scanner.police.FederalPoliceOfficer;
import dhbw.scanner.police.Robot;
import dhbw.scanner.records.Record;
import dhbw.scanner.records.RecordResultType;
import dhbw.scanner.system.BaggageScanner;
import dhbw.scanner.system.Tray;
import dhbw.scanner.utils.FileUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Simulation {

    private static int employeeID = 0;

    private FederalPoliceOfficer o2;
    private FederalPoliceOfficer o3;

    private Technician technician;
    private HouseKeeping houseKeeping;

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

        houseKeeping = new HouseKeeping(employeeID++, "Jason Clarke",
            LocalDate.of(1969, 7, 17), Configuration.INITIAL_PW);

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
        addPassengers(passengers);
    }

    private void addPassengers(ArrayList<Passenger> passengers) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Configuration.PASSENGER_FILE));
            int handBaggageID = 0;

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] inf = line.split(";");

                Passenger p = new Passenger(inf[0]);
                int numberOfHandBaggage = Integer.parseInt(inf[1]);
                for (int i = 0; i < numberOfHandBaggage; i++) {
                    p.addHandBaggage(new HandBaggage(handBaggageID++, p));
                }

                passengers.add(p);
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scanAllPassengers() {
        for (Passenger p : passengers) {
            scan(p);
        }

        // After all scans do maintenance.
        b.maintenance(technician);
    }

    public boolean scan(Passenger passenger) {
        o2 = null;
        o3 = null;
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
                continue;
            }

            // Prohibited item detected.

            // DETECTED_KNIFE
            if (record.getResult().getType() == RecordResultType.DETECTED_KNIFE) {
                b.getManualPostControlInspector().removeKnifeFromHandBaggage(tray.getHandBaggage(), record);

                b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonLeftArrow());
                b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRectangle());

                // If knife detected, do no more scans.
                return true;
            }

            // DETECTED_WEAPON | DETECTED_EXPLOSIVE
            // Alarm, baggage scanner is locked!

            b.getFederalPoliceOfficer().arrest(passenger);
            o2 = office.getFederalPoliceOfficer();
            o3 = office.getFederalPoliceOfficer();

            // Check, officers are on Duty
            if (!o2.isOnDuty() || !o3.isOnDuty()) {
                return false;
            }

            // DETECTED_WEAPON
            if (record.getResult().getType() == RecordResultType.DETECTED_WEAPON) {
                ProhibitedItem weapon =
                        b.getFederalPoliceOfficer().removeWeaponFromBaggage(tray.getHandBaggage(), record);
                o3.setProhibitedItem(weapon);

                // If weapon detected, scan other baggage.
                // Do not return!
            }

            // DETECTED_EXPLOSIVE
            if (record.getResult().getType() == RecordResultType.DETECTED_EXPLOSIVE) {
                Robot robot = office.getRandomRobot(o2);
                if (b.getManualPostControlInspector().swipeAndDetectedExplosive(testStripe)) {
                    robot.destroy(tray.getHandBaggage());
                }

                break;
            }
        }

        // After all scanned, leave control area
        if (o2 != null) {
            o2.setOnDuty(false);
        }

        if (o3 != null) {
            o3.setOnDuty(false);
        }

        // Unlock baggage scanner.
        b.unlock(b.getSupervisor(), b.getSupervisor().getIDCard().getMagnetStripe().getPin(Configuration.SECRET_KEY));
        return true;
    }

    // Getter and setter
    public Technician getTechnician() {
        return technician;
    }

    public HouseKeeping getHouseKeeping() {
        return houseKeeping;
    }

    public BaggageScanner getBaggageScanner() {
        return b;
    }

    public FederalPoliceOffice getOffice() {
        return office;
    }

    public FederalPoliceOfficer getO2() {
        return o2;
    }

    public FederalPoliceOfficer getO3() {
        return o3;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }
}
