package helpers;

import contants.Constants;
import objects.Load;

import java.util.List;

/**
 * Helper class providing methods for various calculations including Euclidean distance, total distance,
 * as well as finding the nearest neighbor among a set of locations.
 */
public class Helper {

    /**
     * Calculates the Euclidean distance between two points.
     *
     * @param loadOne the first point, represented as a double array of x, y coordinates.
     * @param loadTwo the second point, represented as a double array of x, y coordinates.
     * @return the Euclidean distance between loadOne and loadTwo.
     */
    public double getEuclideanDistance(double[] loadOne, double[] loadTwo) {
        double squareDistance = Math.pow(loadTwo[0] - loadOne[0], 2) + Math.pow(loadTwo[1] - loadOne[1], 2);
        return Math.sqrt(squareDistance);
    }

    /**
     * Calculates the total distance traveled, starting and ending at the depot, and going through all pick-up and drop-off points.
     *
     * @param loads the list of loads, each containing pick-up and drop-off locations.
     * @return the total distance traveled.
     */
    public double getTotalDistance(List<Load> loads) {
        double[] firstPickUp = loads.get(0).getPickUpLocation();
        double[] lastDropOff = loads.get(loads.size() - 1).getDropOffLocation();
        double totalDistance = getEuclideanDistance(Constants.DEPOT_LOCATION, firstPickUp) + getEuclideanDistance(Constants.DEPOT_LOCATION, lastDropOff);
        for (int i = 0; i < loads.size() - 1; i++) {
            double[][] prevLoad = new double[][]{loads.get(i).getPickUpLocation(),loads.get(i).getDropOffLocation()};
            double[][] nextLoad = new double[][]{loads.get(i+1).getPickUpLocation(),loads.get(i+1).getDropOffLocation()};
            totalDistance += getEuclideanDistance(prevLoad[1], nextLoad[0]);
        }
        return totalDistance;
    }

    /**
     * Identifies the nearest neighbor to a given point from a list of remaining loads using the quick select algorithm.
     *
     * @param curLoad the current load's location.
     * @param remainingLoads an array of the remaining loads' locations.
     * @return the coordinates of the nearest load.
     */
    public double[] getNearestNeighbor(double[] curLoad, double[][] remainingLoads) {
        return quickSelect(curLoad, remainingLoads);
    }

    private double[] quickSelect(double[] startPoint, double[][] loads) {
        int len = loads.length, left = 0, right = len - 1;
        while (left <= right) {
            int mid = findPivot(loads, left, right, startPoint);
            if (mid == 1) break;
            if (mid < 1) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return loads[0];

    }

    private int findPivot(double[][] loads, int l, int r, double[] startPoint) {
        double[] pivot = loads[l];
        while (l < r) {
            while (l < r && compare(loads[r], pivot, startPoint) >= 0) r--;
            loads[l] = loads[r];
            while (l < r && compare(loads[l], pivot, startPoint) <= 0) l++;
            loads[r] = loads[l];
        }
        loads[l] = pivot;
        return l;
    }

    private double compare(double[] p1, double[] p2, double[] startPoint) {
        return Math.pow(p1[0] - startPoint[0], 2) + Math.pow(p1[1] - startPoint[1], 2) - Math.pow(p2[0] - startPoint[0], 2) + Math.pow(p2[1] - startPoint[1], 2);
    }
}
