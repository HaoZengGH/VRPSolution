package objects;

/**
 * Represents a load that needs to be transported, characterized by its unique number and geographical coordinates for pickup and drop-off locations.
 * Immutable after creation, this class provides methods to access the load's details but not to modify them.
 */
public class Load {
    private final int loadNumber;
    private final double[] pickUpLocation;
    private final double[] dropOffLocation;

    /**
     * Constructs a new Load with a specified load number, pickup location, and drop-off location.
     *
     * @param loadNumber      The unique identifier for the load.
     * @param pickUpLocation  An array of two doubles representing the geographical coordinates (latitude, longitude) of the pickup location.
     * @param dropOffLocation An array of two doubles representing the geographical coordinates (latitude, longitude) of the drop-off location.
     */
    public Load(int loadNumber, double[] pickUpLocation, double[] dropOffLocation) {
        this.loadNumber=loadNumber;
        this.pickUpLocation = pickUpLocation;
        this.dropOffLocation = dropOffLocation;
    }

    /**
     * Retrieves the unique load number of this load.
     *
     * @return The unique identifier for this load.
     */
    public int getLoadNumber(){
        return this.loadNumber;
    }

    /**
     * Retrieves the geographical coordinates of the pickup location for this load.
     *
     * @return An array of two doubles representing the geographical coordinates (latitude, longitude).
     */
    public double[] getPickUpLocation() {
        return this.pickUpLocation;
    }

    /**
     * Retrieves the geographical coordinates of the drop-off location for this load.
     *
     * @return An array of two doubles representing the geographical coordinates (latitude, longitude).
     */
    public double[] getDropOffLocation() {
        return this.dropOffLocation;
    }
}
