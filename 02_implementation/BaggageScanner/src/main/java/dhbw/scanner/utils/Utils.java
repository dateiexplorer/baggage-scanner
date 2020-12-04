package dhbw.scanner.utils;

import dhbw.scanner.baggage.ProhibitedItem;
import dhbw.scanner.baggage.HandBaggage;
import dhbw.scanner.records.Position;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Utils {

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("dd.MM.yyyy hh:mm:ss,SSS").format(new Date());
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
