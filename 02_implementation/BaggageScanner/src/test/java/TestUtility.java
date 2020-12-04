import dhbw.scanner.baggage.ProhibitedItem;
import dhbw.scanner.Simulation;
import dhbw.scanner.baggage.HandBaggage;
import dhbw.scanner.Passenger;
import dhbw.scanner.records.Record;
import dhbw.scanner.records.RecordResultType;

import java.util.List;

public class TestUtility {

    public static boolean testProcedureNothingFound(Simulation sim) {
        Passenger passenger = sim.getPassengers().get(0);
        List<HandBaggage> handBaggage = passenger.getHandBaggage();

        boolean success = sim.scan(passenger);

        return success && !passenger.isArrested() && sim.getO2() == null && sim.getO3() == null &&
                passenger.getHandBaggage().equals(handBaggage) &&
                Record.getLastRecord().getResult().getType() == RecordResultType.CLEAN;

    }

    public static boolean testProcedureKnifeFound(Simulation sim) {
        Passenger passenger = sim.getPassengers().get(6);

        boolean success = sim.scan(passenger);

        return success && !passenger.isArrested() && sim.getO2() == null && sim.getO3() == null &&
                passenger.getHandBaggage().isEmpty() &&
                Record.getLastRecord().getResult().getType() == RecordResultType.CLEAN;
    }

    public static boolean testProcedureWeaponFound(Simulation sim) {
        Passenger passenger = sim.getPassengers().get(14);

        boolean success = sim.scan(passenger);

        return success & passenger.isArrested() && sim.getO2() != null && sim.getO3() != null &&
                sim.getO3().getProhibitedItem() == ProhibitedItem.WEAPON &&
                Record.getRecords().get(1).getResult().getType() == RecordResultType.DETECTED_WEAPON;
    }

    public static boolean testProcedureExplosiveFound(Simulation sim) {
        Passenger passenger = sim.getPassengers().get(418);

        boolean success = sim.scan(passenger);

        String[] layers = sim.getBaggageScanner().getTrack01().getCurrentTray().getHandBaggage().getLayers();
        return success && passenger.isArrested() && sim.getO2() != null && sim.getO3() != null &&
                passenger.getHandBaggage().isEmpty() &&
                Record.getLastRecord().getResult().getType() == RecordResultType.DETECTED_EXPLOSIVE &&
                layers.length == 1000;

    }
}
