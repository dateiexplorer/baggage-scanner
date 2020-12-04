package dhbw.scanner.baggage;

import dhbw.scanner.records.RecordResultType;

public enum ProhibitedItem {
    KNIFE("kn!fe", RecordResultType.DETECTED_KNIFE),
    WEAPON("glock|7", RecordResultType.DETECTED_WEAPON),
    EXPLOSIVE("exp|os!ve", RecordResultType.DETECTED_EXPLOSIVE);

    private String pattern;
    private RecordResultType recordResultType;

    ProhibitedItem(String pattern, RecordResultType recordResultType) {
        this.pattern = pattern;
        this.recordResultType = recordResultType;
    }

    // Getter and setter
    public String getPattern() {
        return pattern;
    }

    public RecordResultType getRecordResultType() {
        return recordResultType;
    }
}
