/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.weighting;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.DistanceWeighting;

/**
 * <p>
 * This implementation of the Distance Weighting will weight the passed distances using an weighted average
 * algorithm. An weighted average will provide an average of weight*distance (with any left over weight evenly
 * distributed amount types).
 * </p>
 * <p>
 * This implementation will be created by the application and passed to the DistanceGenerator to weight the
 * results. The DistanceGenerator will then call this class with the distance types and their associate weight and
 * this implementation will return the calculated weight.
 * </p>
 * <p>
 * Thread-safety: this implementation is thread safe by having no mutable state.
 * </p>
 *
 * @author Pops, romanoTC
 * @version 1.0
 */
public class WeightedAverageWeighting implements DistanceWeighting {

    /**
     * The minimum value for a weight.
     */
    private static final int MIN_WEIGHT = 0;

    /**
     * The maximum value for a weight.
     */
    private static final int MAX_WEIGHT = 100;

    /**
     * The maximum value for weights sum.
     */
    private static final int MAX_WEIGHT_SUM = 100;

    /**
     * <p>
     * This variable represents the weights for each Distance type to apply. This variable is set to a new
     * HashMap&lt;DistanceType, Integer&gt;, is immutable (the reference and contents) and will never be null. The
     * key will be a DistanceType enum and the value an Integer (representing the weight for the DistanceType).
     * This map can be empty (to evenly distribute the weights). The Integer's contained in the map will never be
     * null nor a < 0 or > 100 value. This variable is only referenced in the weightDistance method.
     * </p>
     */
    private final Map<DistanceType, Integer> weights = new HashMap<DistanceType, Integer>();

    /**
     * <p>
     * Creates an instance of this class from the given weights.
     * </p>
     *
     * @param weights
     *            A non-null, possibly empty map containing the weights for the distance type
     * @throws IllegalArgumentException
     *             if the map values is null, contains any integer &lt; 0 or &gt; 100; if the map's keys or values
     *             are null; if the sum of its values is greater than 100.
     */
    public WeightedAverageWeighting(Map<DistanceType, Integer> weights) {
        ExceptionUtils.checkNull(weights, null, null, "The [weights] argument cannot be null.");

        int sum = 0;

        // Perform argument checking - map cannot have null keys nor values
        for (Map.Entry<DistanceType, Integer> entries : weights.entrySet()) {
            ExceptionUtils
                    .checkNull(entries.getKey(), null, null, "The [weights] argument cannot have null keys.");

            Integer integerWeight = entries.getValue();

            ExceptionUtils.checkNull(integerWeight, null, null, "The [weights] argument cannot have null values.");

            int weight = integerWeight;
            if ((weight < MIN_WEIGHT) || (weight > MAX_WEIGHT)) {
                throw new IllegalArgumentException("The [weights] argument cannot have values < 0 or > 100.");
            }

            sum += weight;
        }

        if (sum > MAX_WEIGHT_SUM) {
            throw new IllegalArgumentException(
                    "The [weights] argument cannot have values summing greater than 100.");
        }

        // Copy the values
        this.weights.putAll(weights);
    }

    /**
     * <p>
     * This method will calculate the weighted average and evenly distribute any weighting left over.
     * </p>
     * <p>
     * For example: <br>
     * Let's have <code>weights = {{ Rating, 30 }, { Overlap, 20 }}</code> and
     * <code>distances = {{ Rating, .50}, { Geographical,
     * .25}}</code><br>
     * Our 'set' of weightings would be (prior to redistribution): <code>{{ Rating, 30}, { Geographical, 0}}</code>
     * <br>
     * The algorithm will redistribute the 70 evenly across the set:
     * <code>{{ Rating, 65 }, { Geographical, 35 }}</code><br>
     * And our final answer would be: <code>(.5 * 65 + .25 * 35) / 100 = .4125</code>.
     * </p>
     *
     * @param distances
     *            A non-null, possibly empty map of distances by their distance types.
     * @return A double indicating the overall weighting or &gt; 0 to indicate undefined.
     * @throws IllegalArgumentException
     *             if the given map is null, or if it contains null keys/values.
     */
    public double weightDistance(Map<DistanceType, Double> distances) {
        ExceptionUtils.checkNull(distances, null, null, "The [distances] argument cannot be null.");

        for (Map.Entry<DistanceType, Double> entries : distances.entrySet()) {
            ExceptionUtils.checkNull(entries.getKey(), null, null,
                    "The [distances] argument cannot have null keys.");

            ExceptionUtils.checkNull(entries.getValue(), null, null,
                    "The [distances] argument cannot have null values.");
        }

        // If map is empty, simply return -1.
        if (distances.isEmpty()) {
            return -1.0;
        }

        // if we have only a single distance type, it is essentially weighted as 100% so we can shortcut the
        // return accordingly.
        if (distances.entrySet().size() == 1) {
            return distances.values().iterator().next();
        }

        Map<DistanceType, Double> ourWeights = new HashMap<DistanceType, Double>();

        // Calculate the totalValue to determine if a redistribution is needed or not
        int totalWeights = 0;
        for (Map.Entry<DistanceType, Double> entries : distances.entrySet()) {
            DistanceType distanceType = entries.getKey();

            Integer weight = weights.get(distanceType);

            if (weight != null) {
                int unboxedWeight = weight;

                ourWeights.put(distanceType, (double) weight);
                totalWeights += unboxedWeight;
            } else {
                ourWeights.put(distanceType, 0.0);
            }
        }

        // A variable holding 100% value
        final int fullPercentage = 100;

        // If totalValue is not full (100%), redistribute it across the list
        if (totalWeights < fullPercentage) {
            double evenWeight = ((double) (fullPercentage - totalWeights)) / ourWeights.size();

            for (DistanceType distanceType : ourWeights.keySet()) {
                Double ourWeight = ourWeights.get(distanceType);

                ourWeights.put(distanceType, ourWeight + evenWeight);
            }
        }

        // Sum all weighted distances and return the value
        double weight = 0.0;
        for (Map.Entry<DistanceType, Double> distanceEntry : distances.entrySet()) {
            weight += distanceEntry.getValue() * ourWeights.get(distanceEntry.getKey());
        }

        return weight / fullPercentage;
    }
}
