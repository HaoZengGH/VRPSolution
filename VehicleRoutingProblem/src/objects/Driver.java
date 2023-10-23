package objects;

import objects.Load;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a driver who carries loads from one location to another.
 * Each driver maintains a list of loads assigned to them and the cumulative working time
 * which represents the total time spent carrying all the loads.
 * The class provides methods to manage the loads for the driver and to get and update the working time.
 */
public class Driver {
    private List<Load> loads;
    private double workingTime;

    /**
     * Constructs a new Driver with an empty list of loads and zero working time.
     */
    public Driver() {
        this.loads = new ArrayList<>();
        this.workingTime = 0.0;
    }

    /**
     * Returns the list of loads assigned to this driver.
     *
     * @return A list of Load objects.
     */
    public List<Load> getLoads(){
        return this.loads;
    }

    /**
     * Retrieves the total working time of this driver.
     *
     * @return The cumulative working time spent on all loads.
     */
    public double getWorkingTime(){
        return this.workingTime;
    }

    /**
     * Adds a new load to the list of loads assigned to this driver.
     *
     * @param newLoad The new Load object to be added.
     */
    public void addLoad(Load newLoad){
        this.loads.add(newLoad);
    }

    /**
     * Updates the total working time for this driver by adding the provided value.
     *
     * @param value The time value (in hours or appropriate unit) to add to the current working time.
     */
    public void updateWorkingTime(double value){
        this.workingTime +=value;
    }
}
