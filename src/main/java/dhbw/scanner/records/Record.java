package dhbw.scanner.records;

public class Record {

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
