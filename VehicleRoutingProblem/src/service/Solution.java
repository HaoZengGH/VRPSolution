package service;

import contants.Constants;
import helpers.Helper;
import objects.Driver;
import objects.Load;
import objects.LoadQueue;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a solution for assigning loads to drivers efficiently.
        * It manages a list of loads and provides methods for solving the distribution problem,
        * checking the compatibility of existing drivers with new loads, and printing the results.
        * <p>
 * A load represents a task assigned to a driver, which includes pickup and drop-off locations.
         * The solution aims to minimize the total cost while considering constraints such as the maximum working time for drivers.
         * The solution would be printed in multiple lines, each line suggests the load numbers for a driver to work in order
         * </p>
        * <p>
 * Usage example:
         * <pre>
 *     Solution solution = new Solution(inputLoads);
         *     List<Driver> drivers = solution.solve();
        *     solution.printResult(drivers);
        * </pre>
        * </p>
        */

public class Solution {
    private static final Logger LOGGER = Logger.getLogger(Solution.class.getName());
    private Helper helper=new Helper();
    private List<Load> loads = new ArrayList<>();

    public Solution(double[][][] inputLoads) {
        for (int i=0;i<inputLoads.length;i++) {
            this.loads.add(new Load(i+1, inputLoads[i][0], inputLoads[i][1]));
        }
    }

    /**
     * Checks if an existing driver can accommodate a new load based on the driver's working time and the distance between locations.
     *
     * @param drivers The list of current drivers.
     * @param curLoad The load to be considered for assignment.
     * @return The index of a compatible existing driver in the drivers list;
     * returns null if no suitable existing driver is found(meaning a new driver is needed).
     */
    public Integer checkIfExistingDriverFits(List<Driver> drivers, Load curLoad) {
        double[] curPickUpLocation=curLoad.getPickUpLocation();
        double[] curDropOffLocation=curLoad.getDropOffLocation();
        List<double[]> visitedDropOffLocations = new ArrayList<>();
        double minCost = Double.MAX_VALUE;
        int existingDriver = -1;
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getWorkingTime() > Constants.MAX_WORKING_TIME)
                continue;
            for (Load load : drivers.get(i).getLoads()) {
                visitedDropOffLocations.add(load.getDropOffLocation());
            }
            double[] nearestDropOffLocation = helper.getNearestNeighbor(curPickUpLocation, visitedDropOffLocations.toArray(new double[visitedDropOffLocations.size()][]));

            // Minimum possible cost for an existing driver: distance(nearestDropOffLocation, curPickUpLocation) + distance(DEPOT_LOCATION, curPickUpLocation)
            // Minimum possible cost for adding a new driver:  distance(DEPOT_LOCATION, curPickUpLocation) + distance(DEPOT_LOCATION, curPickUpLocation) + cost of driver
            // When minimum possible cost for adding a new driver< minimum possible cost,
            //   i.e. distance(nearestDropOffLocation, curPickUpLocation) < cost of driver + distance(DEPOT_LOCATION, curPickUpLocation)
            // We consider using an existing driver

            // Calculate the 3 types of distances mentioned above:
            double depotToCurPickup = helper.getEuclideanDistance(Constants.DEPOT_LOCATION, curPickUpLocation);
            double nearestDropOffToCurPickup= helper.getEuclideanDistance(nearestDropOffLocation, curPickUpLocation);
            double curPickupToCurDropoff = helper.getEuclideanDistance(curPickUpLocation,curDropOffLocation);

            // If an existing driver could be used
            if (nearestDropOffToCurPickup < Constants.COST_OF_DRIVER + depotToCurPickup) {
                double curCost = nearestDropOffToCurPickup + curPickupToCurDropoff;

                // Iterate the drivers to find an existing driver who has enough working hour and would cost the minimum
                if ( nearestDropOffToCurPickup < minCost
                        && drivers.get(i).getWorkingTime() + curCost + depotToCurPickup < Constants.MAX_WORKING_TIME) {
                    existingDriver = i;
                    minCost=curCost;
                }
            }
        }

        // If an existing driver is found, we update his/her working hour and return the index
        // The working time would be the cost from the driver's last drop off location to the current pick up location
        if (existingDriver != -1) {
            drivers.get(existingDriver).updateWorkingTime(minCost);
            return existingDriver;
        }
        return null;
    }

    /**
     * Attempts to solve the load distribution problem by assigning loads to drivers while minimizing cost and adhering to constraints.
     * New drivers are created if existing drivers cannot accommodate new loads.
     * Throws an IllegalStateException if no drivers are available after processing all loads.
     *
     * @return A list of drivers with their assigned loads; returns an empty list if an error occurs during processing.
     * @throws IllegalStateException if there are no drivers available.
     */
    public List<Driver> solve() {
        try {
            LoadQueue lq = new LoadQueue();
            Queue<Load> loadsQueue = lq.getLoadQueue();

            List<Driver> drivers = new ArrayList<>();
            loadsQueue.addAll(this.loads);

            while (!loadsQueue.isEmpty()) {
                Load curLoad = loadsQueue.poll();
                double[] curPickUpLocation = curLoad.getPickUpLocation();
                double[] curDropOffLocation = curLoad.getDropOffLocation();

                Integer existingDriverIndex = checkIfExistingDriverFits(drivers, curLoad);

                // If there's no driver now or there's no available driver for the current load, we need a new driver
                if (drivers.isEmpty() || existingDriverIndex == null) {
                    Driver newDriver = new Driver();
                    newDriver.addLoad(curLoad);
                    // Update the driver's working hour
                    double workingTime = helper.getEuclideanDistance(Constants.DEPOT_LOCATION, curPickUpLocation) + helper.getEuclideanDistance(curPickUpLocation, curDropOffLocation);
                    newDriver.updateWorkingTime(workingTime);
                    drivers.add(newDriver);
                }
                // Otherwise there's an existing driver found, add the current load to an existing driver
                else {
                    drivers.get(existingDriverIndex).addLoad(curLoad);
                }
            }
            if (drivers.isEmpty())
                throw new IllegalStateException("No drivers available!");

            return drivers;

        }catch(IllegalStateException e){

            LOGGER.log(Level.SEVERE, "An error occurred: " + e.getMessage());

            return Collections.emptyList();
        }
    }

    /**
     * Prints the results of the load distribution, including the loads assigned to each driver and the total cost.
     *
     * @param drivers The list of drivers with their assigned loads.
     */
    public void printResult(List<Driver> drivers) {
        double totalCost = 0;
        for (int i=0;i<drivers.size();i++) {
            Driver driver=drivers.get(i);
            List<Load> loads = driver.getLoads();
            List<Integer> loadNumbers = new ArrayList<>();
            loads.forEach(load -> loadNumbers.add(load.getLoadNumber()));
            System.out.println(loadNumbers);
            // Calculate the correct total cost for each driver
            totalCost += helper.getTotalDistance(loads) + Constants.COST_OF_DRIVER;
        }
    }

}
