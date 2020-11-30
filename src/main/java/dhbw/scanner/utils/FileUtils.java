package dhbw.scanner.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static String[] loadFileAsBaggageLayers(String pathToFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
            String[] array = new String[5];
            String line = reader.readLine();
            for (int i = 0; line != null; line = reader.readLine(), i++) {
                array[i] = line;
            }

            reader.close();
            return array;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
