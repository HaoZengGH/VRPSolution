package contants;

/**
 * The {@code Constants} class hosts various constants used throughout the application.
 * This class is not meant to be instantiated or subclassed.
 */
public final class Constants {
    public static final int COST_OF_DRIVER = 500;
    public static final double[] DEPOT_LOCATION = {0.0, 0.0};
    public static final double MAX_WORKING_TIME = 12 * 60;
    // Prevent instantiation
    private Constants() {
        throw new AssertionError("Cannot instantiate contants.Constants class");
    }
}
