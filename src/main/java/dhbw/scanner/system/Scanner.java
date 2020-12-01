package dhbw.scanner.system;

import dhbw.scanner.Configuration;
import dhbw.scanner.ProhibitedItem;
import dhbw.scanner.records.Position;
import dhbw.scanner.records.Record;
import dhbw.scanner.records.RecordResult;
import dhbw.scanner.records.RecordResultType;
import dhbw.scanner.utils.Utils;

public class Scanner {

    private BaggageScanner baggageScanner;
    private Tray trayToScan;

    public Scanner(BaggageScanner baggageScanner) {
        this.baggageScanner = baggageScanner;
    }

    public Record scan(Tray trayToScan) {
        this.trayToScan = trayToScan;

        if (trayToScan == null) {
            System.out.println("There is no tray to scan. Aborted.");
            return null;
        }

        baggageScanner.setState(State.IN_USE);

        // Start scan here.
        int layerIndex = 0;
        int charIndex = -1;
        RecordResultType recordResultType = RecordResultType.CLEAN;

        // Scan contents of hand baggage.
        String[] layers = trayToScan.getHandBaggage().getLayers();
        search: for (int i = 0; i < layers.length; i++) {
            layerIndex = i;
            for (ProhibitedItem item : ProhibitedItem.values()) {
                charIndex = Configuration.SEARCH_ALGORITHM.search(layers[i], item.getPattern());
                if (prohibitedItemFound(charIndex)) {
                    recordResultType = item.getRecordResultType();
                    break search;
                }
            }
        }

        // Create a record.
        Record record = new Record(trayToScan.getHandBaggage().getID(), Utils.getCurrentTimestamp(),
                new RecordResult(recordResultType, new Position(layerIndex, charIndex)));
        Record.getRecords().add(record);

        baggageScanner.setState(State.ACTIVATED);

        // Send Tray to correct track.
        if (recordResultType == RecordResultType.CLEAN) {
            baggageScanner.getTrack02().setCurrentTray(trayToScan);
        } else {
            baggageScanner.getTrack01().setCurrentTray(trayToScan);
        }

        setTrayToScan(null);
        return record;
    }

    private boolean prohibitedItemFound(int charIndex) {
        return charIndex > -1;
    }

    // Getter and setter
    public void setTrayToScan(Tray trayToScan) {
        this.trayToScan = trayToScan;
    }

    public Tray getTrayToScan() {
        return trayToScan;
    }
}
