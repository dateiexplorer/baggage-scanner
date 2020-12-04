package dhbw.scanner.records;

public enum RecordResultType {

    CLEAN("clean"),
    DETECTED_KNIFE("knife"),
    DETECTED_WEAPON("weapon"),
    DETECTED_EXPLOSIVE("explosive");

    private String type;

    RecordResultType(String type) {
        this.type = type;
    }

    // Getter and setter
    public String getType() {
        return type;
    }
}
