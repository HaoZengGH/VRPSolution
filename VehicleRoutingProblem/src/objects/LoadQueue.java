package objects;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Provides a priority queue for managing Load objects,
 * prioritizing them based on the Euclidean distance of their pickup locations from the depot.
 */
public class LoadQueue {

    /**
     * Generates a priority queue of Load objects, with priority determined by the Euclidean distance from the origin (0,0) to the load's pickup location.
     * Loads with shorter distances have higher priority.
     *
     * @return A PriorityQueue of Load objects sorted by the Euclidean distance of their pickup locations from the origin.
     */
    public Queue<Load> getLoadQueue() {
        Comparator<Load> loadComparator = (point1, point2) -> {
            double distance1 = Math.sqrt(Math.pow(point1.getPickUpLocation()[0], 2) + Math.pow(point1.getPickUpLocation()[1], 2));
            double distance2 = Math.sqrt(Math.pow(point2.getPickUpLocation()[0], 2) + Math.pow(point2.getPickUpLocation()[1], 2));
            return Double.compare(distance1, distance2);
        };

        return new PriorityQueue<>(loadComparator);
    }
}
