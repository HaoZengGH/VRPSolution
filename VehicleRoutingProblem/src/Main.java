import objects.Driver;
import service.DataReader;
import service.Solution;

import java.io.IOException;
import java.util.List;

/**
 * The Main class is the entry point for this application, which involves reading a data file containing
 * coordinates, processing this data to assign loads to drivers, and outputting the result.
 * This class specifically handles the initialization and orchestration of the application's core
 * functionalities by leveraging other components such as DataReader and Solution.
 */
public class Main {

    /**
     * Main method which serves as the entry point of the application.
     * It expects a single command-line argument: the path to the data file.
     * <p>
     * The method performs the following operations:
     * 1. Validates the presence of the file path argument.
     * 2. Utilizes the DataReader class to read and parse the data from the file.
     * 3. Initializes the Solution class with the parsed data.
     * 4. Invokes the solution logic to assign drivers to loads.
     * 5. Outputs the results of the assignment.
     * <p>
     * If any step fails, especially the file reading, the method informs the user and prints the stack trace.
     *
     * @param args Command-line arguments, expects only one argument representing the file path.
     * @throws IOException If there's an error in reading the file.
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Error: No file path provided. Please provide the file path as an argument.");
            System.exit(1);
        }

        String filePath = args[0];

        try {
            double[][][] testData = DataReader.readFile(filePath);
            Solution s = new Solution(testData);
            List<Driver> drivers = s.solve();
            s.printResult(drivers);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}