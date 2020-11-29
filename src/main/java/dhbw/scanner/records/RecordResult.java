package dhbw.scanner.records;

public class RecordResult {

    private RecordResultType type;
    private Position position;

    public RecordResult(RecordResultType type, Position position) {
        this.type = type;
        this.position = position;
    }

    @Override
    public String toString() {
        if (type == RecordResultType.CLEAN) {
            return "clean";
        } else {
            return "prohibited item | " + type.getType() + " detected at position " + position.toString();
        }
    }

    // Getter and setter
    public RecordResultType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }
}
