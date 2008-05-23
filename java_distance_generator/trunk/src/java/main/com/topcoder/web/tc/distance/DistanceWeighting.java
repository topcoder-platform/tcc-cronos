/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import java.util.Map;

/**
 * <p>
 * This interface defines the contract for a DistanceWeighting implementation. A distance weighting
 * implementation will take a map of distances (by their DistanceType) and calculate the overall distance for
 * them.
 * </p>
 * <p>
 * This implementation will be created by the application and passed to the DistanceGenerator. The
 * DistanceGenerator will then call the implementation to weight the distances and return the overall
 * distance.
 * </p>
 * <p>
 * Thread-safety: implementations of this interface must be thread-safe.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public interface DistanceWeighting {
    /**
     * This method will take a map of distances by their DistanceType, it will then weight those distances and
     * return the overall distance. If the map is empty or contains any negative value, a -1 (meaning
     * undefined) should be returned.
     *
     * @param distances A non-null, possibly empty map of distances by their distance types.
     * @return A double indicating the overall weighting or &gt; 0 to indicate undefined.
     * @throws IllegalArgumentException if the given map is null, or if it contains null values.
     * @throws DistanceWeightingException if any exception occurs weighing the distances.
     */
    public double weightDistance(Map<DistanceType, Double> distances);
}
