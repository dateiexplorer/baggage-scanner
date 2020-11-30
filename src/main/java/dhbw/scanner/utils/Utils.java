package dhbw.scanner.utils;

import dhbw.scanner.ProhibitedItem;
import dhbw.scanner.passengers.HandBaggage;
import dhbw.scanner.records.Position;

import java.time.LocalDateTime;

public class Utils {

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static ProhibitedItem removeItemFromHandBaggageAtPosition(ProhibitedItem item, HandBaggage handBaggage, Position position) {
        String layer = handBaggage.getLayers()[position.getLayerIndex()];
        int index = position.getCharIndex();
        String contentBeforeItem = layer.substring(0, index);
        String contentAfterItem = layer.substring(index + item.getPattern().length());

        layer = contentBeforeItem + contentAfterItem;
        handBaggage.getLayers()[position.getLayerIndex()] = layer;
        return item;
    }
}
