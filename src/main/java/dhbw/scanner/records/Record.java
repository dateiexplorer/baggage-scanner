package dhbw.scanner.records;

import java.util.ArrayList;

public class Record {

    private static ArrayList<Record> records = new ArrayList<>();

    public static ArrayList<Record> getRecords() {
        return records;
    }

    public static Record getLastRecord() {
        if (records.isEmpty()) {
            return null;
        }

        return records.get(records.size() - 1);
    }

    private int id;
    private String timestamp;
    private RecordResult result;

    public Record(int id, String timestamp, RecordResult result) {
        this.id = id;
        this.timestamp = timestamp;
        this.result = result;
    }

    // Getter and setter
    public int getID() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public RecordResult getResult() {
        return result;
    }
}
