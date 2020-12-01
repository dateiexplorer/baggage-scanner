package dhbw.scanner;

import java.io.*;
import java.util.ArrayList;

public class DataGenerator {

    private final String ALLOWED_CHARS =
            "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789!@#$%^&*()[]{}-_=+;:,<.>/?|~";

    public static void main(String[] args) {
        DataGenerator generator = new DataGenerator();
        generator.generate(Configuration.PASSENGER_FILE, Configuration.HAND_BAGGAGE_DIR);
    }

    public void generate(String pathToFile, String savePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
            int handBaggageID = 0;

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] inf = line.split(";");

                String name = inf[0];
                int numberOfHandBaggage = Integer.parseInt(inf[1]);

                ArrayList<String[]> handBaggageList = new ArrayList<>();
                for (int i = 0; i < numberOfHandBaggage; i++) {
                    // Generate random baggage
                    String[] handBaggage = generateHandBaggage();
                    handBaggageList.add(handBaggage);
                }

                if (!inf[2].equals("-")) {
                    generateProhibitedItems(inf, handBaggageList);
                }

                // Save hand baggage in files
                for (String[] handBaggage : handBaggageList) {
                    write(savePath, handBaggageID++, handBaggage);
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateProhibitedItems(String[] inf, ArrayList<String[]> handBaggageList) {
        for (int i = 2; i < inf.length; i++) {
            // Delete brackets
            inf[i] = inf[i].replaceAll("\\[|\\]", "");

            String[] prohibitedItemInf = inf[i].split(",");
            ProhibitedItem prohibitedItem = switch(prohibitedItemInf[0]) {
                case "K" -> ProhibitedItem.KNIFE;
                case "W" -> ProhibitedItem.WEAPON;
                case "E" -> ProhibitedItem.EXPLOSIVE;
                default -> throw new IllegalStateException("Unexpected value: " + prohibitedItemInf[0]);
            };

            // Baggage 1 has index 0
            int handBaggageIndex = Integer.parseInt(prohibitedItemInf[1]) - 1;

            // Layer 1 has index 0
            int layerIndex = Integer.parseInt(prohibitedItemInf[2]) - 1;

            String layer = handBaggageList.get(handBaggageIndex)[layerIndex];

            int position = getRandomValidIndex(layer, prohibitedItem);
            handBaggageList.get(handBaggageIndex)[layerIndex] = generateProhibitedItemInLayer(layer, prohibitedItem, position);
        }
    }

    private void write(String savePath, int handBaggageID, String[] handBaggage) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(savePath + "handBaggage_" + handBaggageID + ".txt"));
        for (String layer : handBaggage) {
            writer.write(layer);
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }

    private String[] generateHandBaggage() {
        String[] content = new String[5];
        for (int i = 0; i < 5; i++) {
            content[i] = generateHandBaggageLayer();
        }

        return content;
    }

    private String generateHandBaggageLayer() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 10_000; i++) {
            int index = (int) (Math.random() * ALLOWED_CHARS.length());
            buffer.append(ALLOWED_CHARS.charAt(index));
        }


        return buffer.toString();
    }

    private String generateProhibitedItemInLayer(String layer, ProhibitedItem item, int position) {
        String prefix = layer.substring(0, position);
        String postfix = layer.substring(position + item.getPattern().length());

        return prefix + item.getPattern() + postfix;
    }

    private int getRandomValidIndex(String layer, ProhibitedItem item) {
        int lastPossibleIndex = layer.length() - item.getPattern().length();

        // Get random index.
        return (int) (Math.random() * lastPossibleIndex);
    }
}
