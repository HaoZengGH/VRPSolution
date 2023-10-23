package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for reading data from a file and parsing it into a multidimensional array.
 * Each line of the file (excluding the first line, assumed to be a header) represents a set of coordinates
 * for pick-up and drop-off locations. The method parses these lines and converts the coordinates into a
 * multidimensional array of doubles.
 */
public class DataReader {
    /**
     * Reads the provided file and parses its content into a multidimensional array of doubles.
     * Each inner array represents a pair of coordinates: the first being the pickup location and
     * the second being the drop-off location.
     * <p>
     * The method expects the file to have a specific format:
     * - The first line is considered a header and is skipped.
     * - Each subsequent line should contain two parts, separated by a space:
     *   the pick-up coordinates and the drop-off coordinates.
     * - Each set of coordinates should be enclosed in parentheses and separated by a comma.
     * <p>
     * For example:
     * Header (ignored line)
     * (pickupX,pickupY) (drop-offX,drop-offY)
     * ...
     * <p>
     * If the file does not adhere to this format, the method may throw an IOException or
     * NumberFormatException.
     *
     * @param filePath The path of the file to read.
     * @return A three-dimensional array of doubles, where each inner 2D array contains two elements:
     *         an array with the pickup coordinates and an array with the drop-off coordinates.
     * @throws IOException If an I/O error occurs while reading from the file.
     */
    public static double[][][] readFile(String filePath) throws IOException {
        List<double[][]> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // to skip the header line

            // Parse the lines
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(" ");

                // Parse the coordinates, removing parentheses and splitting by comma
                String[] pickupCoords = parts[1].substring(1, parts[1].length() - 1).split(",");
                String[] dropoffCoords = parts[2].substring(1, parts[2].length() - 1).split(",");

                // Convert to double array and add to list
                double pickupX = Double.parseDouble(pickupCoords[0]);
                double pickupY = Double.parseDouble(pickupCoords[1]);
                double dropoffX = Double.parseDouble(dropoffCoords[0]);
                double dropoffY = Double.parseDouble(dropoffCoords[1]);

                dataList.add(new double[][]{new double[]{pickupX, pickupY}, new double[]{dropoffX, dropoffY}});
            }
        }


        double[][][] dataArray = dataList.toArray(new double[dataList.size()][][]);
        for (int i = 0; i < dataList.size(); i++) {
            dataArray[i] = dataList.get(i);
        }

        return dataArray;

    }
}