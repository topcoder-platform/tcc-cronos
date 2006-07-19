/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.scoreaggregator.impl;

import com.topcoder.management.review.scoreaggregator.ScoreAggregationAlgorithm;
import com.topcoder.management.review.scoreaggregator.Util;

/**
 * <p>
 * This class defines the standard score aggregation algorithm, whereby the aggregated score is the arithmetic average
 * of the individual scores.
 * </p>
 *
 * <p>
 * This class is thread-safe, since it has no instance variables.
 * </p>
 *
 * @author dplass, daiwb
 * @version 1.0
 */
public class AveragingAggregationAlgorithm implements ScoreAggregationAlgorithm {

    /**
     * <p>
     * Default constructor. Does nothing.
     * </p>
     */
    public AveragingAggregationAlgorithm() {
    }

    /**
     * <p>
     * Calculate the aggregate score of the given scores, by returning the arithmetic average of the values.
     * Return 0 if the input array is empty.
     * </p>
     *
     * @return the arithmetic average of the scores
     * @param scores
     *            the scores that a submission recieved from each reviewer (return 0 if the input array is empty)
     * @throws IllegalArgumentException
     *             if scores is null or if any entry in the array is negative/NaN/Infinite.
     */
    public float calculateAggregateScore(float[] scores) {
        Util.checkNull(scores, "scores");
        for (int i = 0; i < scores.length; ++i) {
            Util.checkFloatValue(scores[i], "scores[" + i + "]");
        }

        // If scores is empty, just return 0.
        if (scores.length == 0) {
            return 0;
        }

        // Get the sum of the values.
        float sum = 0;
        for (int i = 0; i < scores.length; ++i) {
            sum += scores[i];
        }

        return sum / scores.length;
    }
}
