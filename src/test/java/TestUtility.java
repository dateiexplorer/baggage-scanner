import dhbw.scanner.ProhibitedItem;
import dhbw.scanner.employees.FederalPoliceOfficer;
import dhbw.scanner.passengers.HandBaggage;
import dhbw.scanner.passengers.Passenger;
import dhbw.scanner.police.Robot;
import dhbw.scanner.records.Record;
import dhbw.scanner.records.RecordResultType;
import dhbw.scanner.system.BaggageScanner;
import dhbw.scanner.system.State;
import dhbw.scanner.system.Tray;

public class TestUtility {

//    private static FederalPoliceOfficer o2;
//    private static FederalPoliceOfficer o3;
//
//    private static boolean testBaggageScanner(BaggageScanner b, Passenger passenger) {
//        passenger.tryToPutHandBaggageOnRollerConveyor(b);
//
//        while (!b.getRollerConveyor().isEmpty()) {
//            if (pushNextTrayIntoScanner(b)) {
//                return false;
//            }
//
//            Tray tray = b.getScanner().getTrayToScan();
//
//            // Start scan.
//            b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRectangle());
//
//            // Get last record result.
//            Record lastRecord = ;
//
//            boolean success = switch (lastRecord.getResult().getType()) {
//                case CLEAN -> onClean(b, tray, lastRecord);
//                case DETECTED_KNIFE -> onKnifeDetected(b, tray, lastRecord);
//                case DETECTED_WEAPON -> onWeaponDetected(b, tray, lastRecord);
//            };
//
//            if (!success) {
//                return false;
//            }
//        }
//    }
//
//    private static boolean onClean(BaggageScanner b, Tray tray, Record record) {
//        return b.getTrack02().getCurrentTray() == tray;
//    }
//
//    private static boolean onKnifeDetected(BaggageScanner b, Tray tray, Record record) {
//        if (b.getTrack01().getCurrentTray() != tray) {
//            return false;
//        }
//
//        // Remove knife from hand baggage (look b.report())
//        // Push back to scanner.
//        b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonLeftArrow());
//
//        if (b.getScanner().getTrayToScan() != tray) {
//            return false;
//        }
//
//        // Repeat scan.
//        b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRectangle());
//
//        // TODO: Test last record result.
//        // Must be CLEAN
//
//        // Tray at track02 must be the initial tray now.
//        return b.getTrack02().getCurrentTray() == tray;
//    }
//
//    private static boolean onWeaponDetected(BaggageScanner b, Tray tray, Record record) {
//        if (b.getTrack01().getCurrentTray() != tray) {
//            return false;
//        }
//
//        // Alarm -> baggage scanner ist locked
//        if (b.getState() != State.LOCKED) {
//            return false;
//        }
//
//        // Arrest passenger
//        Passenger passenger = tray.getHandBaggage().getOwner();
//        b.getFederalPoliceOfficer().arrest(passenger);
//
//        // Call o2 and o3
//        o2 = b.getFederalPoliceOffice().getFederalPoliceOfficer();
//        o3 = b.getFederalPoliceOffice().getFederalPoliceOfficer();
//
//        ProhibitedItem weapon = b.getFederalPoliceOfficer().removeWeaponFromBaggage(tray.getHandBaggage(), record);
//        o3.setProhibitedItem(weapon);
//
//        return o2.isOnDuty() && o3.isOnDuty();
//    }
//
//    private static boolean pushNextTrayIntoScanner(BaggageScanner b) {
//        b.getRollerConveyorInspector().pushNextTray();
//        b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRightArrow());
//
//        // Tray is in scanner now.
//        return b.getScanner().getTrayToScan() != null;
//    }
//
//    private static boolean onAlarm(BaggageScanner b, Record record) {
//        // State must be LOCKED.
//        if (b.getState() != State.LOCKED) {
//            return false;
//        }
//
//        HandBaggage handBaggage = b.getTrack01().getCurrentTray().getHandBaggage();
//        b.getFederalPoliceOfficer().arrest(handBaggage.getOwner());
//
//        o2 = b.getFederalPoliceOffice().getFederalPoliceOfficer();
//        o3 = b.getFederalPoliceOffice().getFederalPoliceOfficer();
//
//        // Remove weapon from hand baggage
//        if (record.getResult().getType() == RecordResultType.DETECTED_WEAPON) {
//            ProhibitedItem weapon = b.getFederalPoliceOfficer().removeWeaponFromBaggage(handBaggage, record);
//
//            // Give item officer O3
//            o3.setProhibitedItem(weapon);
//
//            // Officer O3 has weapon now.
//            if (o3.getProhibitedItem() != ProhibitedItem.WEAPON) {
//                return false;
//            }
//        }
//
//        if (record.getResult().getType() == RecordResultType.DETECTED_EXPLOSIVE) {
//            Robot robot = b.getFederalPoliceOffice().getRandomRobot(o2);
//
//            if (robot.getOwner() != o2) {
//                return false;
//            }
//
//            // TODO: Test for explosive
//
//            // Destroy hand baggage
//            robot.destroy(handBaggage);
//        }
//
//        Passenger passenger = handBaggage.getOwner();
//        if (!passenger.isArrested()) {
//            return false;
//        }
//
//        return o2.isOnDuty() && o3.isOnDuty();
//    }
//
//    private static boolean testNoProhibitedItemsFound(Tray tray, BaggageScanner b) {
//        // TODO: Tray with clean baggage.
//        if (!putNextTrayIntoScanner(tray, b)) {
//            return false;
//        }
//
//        // Start scan.
//        b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRectangle());
//
//        // Test last record result.
//
//        // Tray at track02 must be the initial tray
//        return b.getTrack02().getCurrentTray() == tray;
//    }
//
//    private static boolean testOnKnifeFound(Tray tray, BaggageScanner b) {
//        // TODO: Tray with knife.
//        if (!putTrayIntoScanner(tray, b)) {
//            return false;
//        }
//
//        // Start scan.
//        b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRectangle());
//
//        // Test last record result.
//        // Must be DETECTED_KNIFE
//
//        // Tray at track01 must be the initial tray now.
//        if (b.getTrack01().getCurrentTray() != tray) {
//            return false;
//        }
//
//        // Remove knife from hand baggage.
//        // Push back to scanner.
//        b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonLeftArrow());
//
//        if (b.getScanner().getTrayToScan() != tray) {
//            return false;
//        }
//
//        // Repeat scan.
//        b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRectangle());
//
//        // Test last record result.
//        // Must be CLEAN
//
//        // Tray at track02 must be the initial tray now.
//        return b.getTrack02().getCurrentTray() == tray;
//    }
//
//    public boolean testOnWeaponFound(Passenger passenger, BaggageScanner b) {
//        passenger.tryToPutHandBaggageOnRollerConveyor(b);
//
//        // TODO: Tray with weapon
//        if (!putNextTrayIntoScanner(b)) {
//            return false;
//        }
//
//        Tray tray = b.getScanner().getTrayToScan();
//
//        // Start scan.
//        b.getOperatingStationInspector().pushButton(b.getOperatingStation().getButtonRectangle());
//
//        // Test last Record result.
//        Record record = ;
//        // Must be DETECTED_WEAPON
//
//        // Tray at track01 must be the initial tray now.
//        if (b.getTrack01().getCurrentTray() != tray) {
//            return false;
//        }
//
//        if (!onAlarm(b, record)) {
//            return false;
//        }
//
//        // Scan next baggage
//        // TODO: Are there more trays?
//        while (!b.getRollerConveyor().isEmpty()) {
//            putNextTrayIntoScanner(b);
//        }
//
//        // Leave control area.
//        o2.setOnDuty(false);
//        o3.setOnDuty(false);
//        o3.setProhibitedItem(null);
//    }
}
