/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import java.util.Date;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Helper;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;

import com.topcoder.predictor.analysis.PredictionComparator;

/**
 * <p>
 * This PredictionComparator implementation is used to sort the predictions according to their expected submission
 * count, their prize target, and timeline duration, in that order of importance.
 * </p>
 *
 * <p>
 * Usage:
 *
 * <pre>
 * // expected range is [1.0, 3.0], target prize is 500.0
 * ComponentCompetitionFulfillmentPredictionTimelineComparator timeComparator =
 *     new ComponentCompetitionFulfillmentPredictionTimelineComparator(1.0, 3.0, 500.0);
 * situation.setPrize(300.0);
 * prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1, situation, predictor);
 * situation = new ComponentCompetitionSituation();
 * situation.setPrize(600.0);
 * prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0, situation, predictor);
 * // both predictions are in the range;
 * // prediction1's prize is less than target, but prediction2's prize is larger than target,
 * // so result should be -1
 * result = timeComparator.compare(prediction1, prediction2);
 * assertEquals(&quot;The compare method returns incorrect result.&quot;, -1, result);
 *
 * situation = new ComponentCompetitionSituation();
 * prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1, situation, predictor);
 * prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0, situation, predictor);
 * // both predictions are in the range;
 * // their situations' posting dates, end dates and prizes are not set,
 * // so result should be 0
 * result = timeComparator.compare(prediction1, prediction2);
 * assertEquals(&quot;The compare method returns incorrect result.&quot;, 0, result);
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread-Safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionFulfillmentPredictionTimelineComparator implements
                PredictionComparator<ComponentCompetitionFulfillmentPrediction> {
    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = 1685391316780715147L;

    /**
     * It indicates the expected passed review submission count is in the expected range.
     */
    private static final int IN_RANGE = 1;

    /**
     * It indicates the expected passed review submission count is above the expected range.
     */
    private static final int ABOVE_RANGE = 2;

    /**
     * It indicates the expected passed review submission count is below the expected range.
     */
    private static final int BELOW_RANGE = 3;

    /**
     * Represents the minimum desired prediction. It is set in the constructor. It is used in the compare and equals
     * method, and accessed via the getter. It will not be negative and it will not be bigger than maxPrediction. Once
     * set, it will never change.
     */
    private final double minPrediction;

    /**
     * Represents the maximum desired prediction. It is set in the constructor. It is used in the compare and equals
     * method, and accessed via the getter. It will not be negative and it will not be smaller than minPrediction. Once
     * set, it will never change.
     */
    private final double maxPrediction;

    /**
     * Represents the target prize for the competition. It is set in the constructor. It is used in the compare and
     * equals method, and accessed via the getter. The value will not be negative. Once set, it will never change.
     */
    private final double targetPrize;

    /**
     * Constructor accepting the variables.
     *
     * @param targetPrize
     *            the target prize for the competition
     * @param minPrediction
     *            the mimimum desired prediction
     * @param maxPrediction
     *            the maximum desired prediction
     * @throws IllegalArgumentException
     *             If any value is negative, or minPrediction &gt; maxPrediction
     */
    public ComponentCompetitionFulfillmentPredictionTimelineComparator(double minPrediction, double maxPrediction,
                    double targetPrize) {
        Helper.checkNotNegative(minPrediction, "minPrediction");
        Helper.checkNotNegative(maxPrediction, "maxPrediction");
        Helper.checkNotNegative(targetPrize, "targetPrize");
        if (minPrediction > maxPrediction) {
            throw new IllegalArgumentException("The minPrediction should not be larger than maxPrediction.");
        }
        this.maxPrediction = maxPrediction;
        this.minPrediction = minPrediction;
        this.targetPrize = targetPrize;
    }

    /**
     * <p>
     *
     * <pre>
     *   Compares the two predictions, testing which one is better, using the following rules (in order of importance):
     *
     *   1. Predictions in range &lt; predictions above the range &lt; predictions below the range
     *   2. Prizes lower than or equal to the target &lt; prizes higher than the target
     *   3. Shorter timelines &lt; longer timelines
     * </pre>
     *
     * </p>
     *
     * <p>
     *
     * <pre>
     *   The results of the comparison are as follows:
     *   1. If prediction1 &lt; prediction2, then result is -1
     *   2. If prediction1 = prediction2, then result is 0
     *   3. If prediction1 &gt; prediction2, then result is 1
     * </pre>
     *
     * </p>
     *
     * @param prediction1
     *            the first prediction being compared
     * @param prediction2
     *            the second prediction being compared
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater
     *         than the second.
     * @throws IllegalArgumentException
     *             If either parameter is null
     */
    public int compare(ComponentCompetitionFulfillmentPrediction prediction1,
                    ComponentCompetitionFulfillmentPrediction prediction2) {
        Helper.checkNotNull(prediction1, "prediction1");
        Helper.checkNotNull(prediction2, "prediction2");
        // Test first condition by checking with the predictions' expectedPassedReviewSubmissionCount value
        // ranges of the preditions, 1 - in range, 2 - above range, 3 - below range
        int range1;
        int range2;
        double count1 = prediction1.getExpectedPassedReviewSubmissionCount();
        double count2 = prediction2.getExpectedPassedReviewSubmissionCount();
        if (count1 < this.minPrediction) {
            range1 = BELOW_RANGE;
        } else if (count1 <= this.maxPrediction) {
            range1 = IN_RANGE;
        } else {
            range1 = ABOVE_RANGE;
        }
        if (count2 < this.minPrediction) {
            range2 = BELOW_RANGE;
        } else if (count2 <= this.maxPrediction) {
            range2 = IN_RANGE;
        } else {
            range2 = ABOVE_RANGE;
        }
        // If the test is decisive (i.e. does not result in equivalence of predictions), return appropriate result.
        if (range1 < range2) {
            return -1;
        } else if (range1 > range2) {
            return 1;
        }

        // note that if prize or date is not set, the corresponding consider is considered to be equal,
        // see http://forums.topcoder.com/?module=Thread&threadID=625585&start=0

        // Test second condition by checking with the predictions' situations' prize value
        ComponentCompetitionSituation s1 = prediction1.getSituation();
        ComponentCompetitionSituation s2 = prediction2.getSituation();
        Double p1 = s1.getPrize();
        Double p2 = s2.getPrize();
        if (p1 != null && p2 != null) {
            // If the test is decisive (i.e. does not result in equivalence of predictions), return appropriate result.
            if (p1 <= this.targetPrize && p2 > this.targetPrize) {
                return -1;
            } else if (p2 <= this.targetPrize && p1 > this.targetPrize) {
                return 1;
            }
        }

        // Test third condition by checking with the predictions' situations' duration (endDate - postingDate)
        Date endDate1 = s1.getEndDate();
        Date postingDate1 = s1.getPostingDate();
        Date endDate2 = s2.getEndDate();
        Date postingDate2 = s2.getPostingDate();
        if (endDate1 != null && endDate2 != null && postingDate1 != null && postingDate2 != null) {
            long t1 = endDate1.getTime() - postingDate1.getTime();
            long t2 = endDate2.getTime() - postingDate2.getTime();
            // If the test is decisive (i.e. does not result in equivalence of predictions), return appropriate result.
            if (t1 < t2) {
                return -1;
            } else if (t1 > t2) {
                return 1;
            }
        }

        // no difference, return 0
        return 0;
    }

    /**
     * Indicates whether some other object is "equal to" this Comparator. A comparator is equal to this comparator if it
     * is of the same type and its minPrediction, maxPrediction, and targetPrize are the same.
     *
     * @param obj
     *            the reference object with which to compare.
     * @return true only if the specified object is also a comparator and it imposes the same ordering as this
     *         comparator.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof ComponentCompetitionFulfillmentPredictionTimelineComparator)) {
            return false;
        }
        ComponentCompetitionFulfillmentPredictionTimelineComparator c =
            (ComponentCompetitionFulfillmentPredictionTimelineComparator) obj;
        return new Double(c.minPrediction).equals(this.minPrediction)
                        && new Double(c.maxPrediction).equals(this.maxPrediction)
                        && new Double(c.targetPrize).equals(this.targetPrize);
    }

    /**
     * Gets hash code of this comparator.
     *
     * @return hash code of this comparator.
     */
    public int hashCode() {
        return new Double(this.maxPrediction).hashCode() ^ new Double(this.minPrediction).hashCode()
                        ^ new Double(this.targetPrize).hashCode();
    }

    /**
     * Gets the minPrediction field value.
     *
     * @return the minPrediction field value
     */
    public double getMinPrediction() {
        return this.minPrediction;
    }

    /**
     * Gets the maxPrediction field value.
     *
     * @return the maxPrediction field value
     */
    public double getMaxPrediction() {
        return this.maxPrediction;
    }

    /**
     * Gets the targetPrize field value.
     *
     * @return the targetPrize field value
     */
    public double getTargetPrize() {
        return this.targetPrize;
    }
}
