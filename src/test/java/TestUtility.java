import dhbw.scanner.ProhibitedItem;
import dhbw.scanner.Simulation;
import dhbw.scanner.employees.FederalPoliceOfficer;
import dhbw.scanner.passengers.HandBaggage;
import dhbw.scanner.passengers.Passenger;
import dhbw.scanner.police.Robot;
import dhbw.scanner.records.Record;
import dhbw.scanner.records.RecordResultType;
import dhbw.scanner.system.BaggageScanner;
import dhbw.scanner.system.State;
import dhbw.scanner.system.Tray;

import java.util.Arrays;
import java.util.List;

public class TestUtility {

    public static boolean testProcedureNothingFound(Simulation sim) {
        Passenger passenger = sim.getPassengers().get(0);
        List<HandBaggage> handBaggage = passenger.getHandBaggage();

        Record record = sim.scan(passenger);

        return !passenger.isArrested() && sim.getO2() == null && sim.getO3() == null &&
                handBaggage.equals(passenger.getHandBaggage()) &&
                record.getResult().getType() == RecordResultType.CLEAN;

    }

    public static boolean testProcedureKnifeFound(Simulation sim) {
        Passenger passenger = sim.getPassengers().get(6);

        Record record = sim.scan(passenger);

        return !passenger.isArrested() && sim.getO2() == null && sim.getO3() == null &&
                passenger.getHandBaggage().isEmpty() &&
                record.getResult().getType() == RecordResultType.DETECTED_KNIFE &&
                Record.getLastRecord().getResult().getType() == RecordResultType.CLEAN;
    }

    public static boolean testProcedureWeaponFound(Simulation sim) {
        Passenger passenger = sim.getPassengers().get(14);

        Record record = sim.scan(passenger);

        return passenger.isArrested() && sim.getO2() != null && sim.getO3() != null &&
                sim.getO3().getProhibitedItem() == ProhibitedItem.WEAPON &&
                Record.getRecords().get(1).getResult().getType() == RecordResultType.DETECTED_WEAPON &&
                record.getResult().getType() == RecordResultType.CLEAN;
    }

    public static boolean testProcedureExplosiveFound(Simulation sim) {
        Passenger passenger = sim.getPassengers().get(418);

        Record record = sim.scan(passenger);

        String[] layers = sim.getBaggageScanner().getTrack01().getCurrentTray().getHandBaggage().getLayers();
        return passenger.isArrested() && sim.getO2() != null && sim.getO3() != null &&
                record.getResult().getType() == RecordResultType.DETECTED_EXPLOSIVE &&
                layers.length == 1000;

    }
}
