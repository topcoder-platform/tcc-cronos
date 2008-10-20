/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.Helper;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionFulfillmentPrediction;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.analysis.PredictionComparator;
import java.util.Date;

/**
 * <p>
 * This PredictionComparator implementation is used to sort the predictions according to their expected submission
 * count, timeline duration, and their prize target, in that order of importance.
 * </p>
 *
 * <p>
 * Usage:
 *
 * <pre>
 * // expected range is [1.0, 3.0], target timeline is 100000
 * ComponentCompetitionFulfillmentPredictionPrizeComparator prizeComparator =
 *     new ComponentCompetitionFulfillmentPredictionPrizeComparator(1.0, 3.0, 100000);
 * ComponentCompetitionSituation situation = new ComponentCompetitionSituation();
 * ComponentCompetitionPredictor predictor = new ComponentCompetitionPredictor();
 * ComponentCompetitionFulfillmentPrediction prediction1 =
 *     new ComponentCompetitionFulfillmentPrediction(0.5, situation, predictor);
 * ComponentCompetitionFulfillmentPrediction prediction2 =
 *     new ComponentCompetitionFulfillmentPrediction(2.0, situation, predictor);
 * // prediction1 is below the range, prediction2 is in the range, so result should be 1
 * int result = prizeComparator.compare(prediction1, prediction2);
 * assertEquals(&quot;The compare method returns incorrect result.&quot;, 1, result);
 *
 * situation.setEndDate(new Date(150000));
 * situation.setPostingDate(new Date(100000));
 * situation.setPrize(1000.0);
 * prediction1 = new ComponentCompetitionFulfillmentPrediction(1.1, situation, predictor);
 * situation = new ComponentCompetitionSituation();
 * situation.setEndDate(new Date(160000));
 * situation.setPostingDate(new Date(100000));
 * situation.setPrize(900.0);
 * prediction2 = new ComponentCompetitionFulfillmentPrediction(2.0, situation, predictor);
 * // both predictions are in the range;
 * // both timelines are shorter than target
 * // prediction1's situation has larger prize
 * // so result should be 1
 * result = prizeComparator.compare(prediction1, prediction2);
 * assertEquals(&quot;The compare method returns incorrect result.&quot;, 1, result);
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
public class ComponentCompetitionFulfillmentPredictionPrizeComparator implements
                PredictionComparator<ComponentCompetitionFulfillmentPrediction> {
    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = 5688104226255695626L;

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
     * Represents the mimimum desired prediction. It is set in the constructor. It is used in the compare and equals
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
     * Represents the target duration for the competition, in milliseconds. It is set in the constructor. It is used in
     * the compare and equals method, and accessed via the getter. The value will not be negative. Once set, it will
     * never change.
     */
    private final long targetDuration;

    /**
     * Constructor accepting the variables.
     *
     * @param minPrediction
     *            the mimimum desired prediction
     * @param targetDuration
     *            the maximum desired prediction
     * @param maxPrediction
     *            the target duration for the competition, in milliseconds
     * @throws IllegalArgumentException
     *             If any value is negative, or minPrediction &gt; maxPrediction
     */
    public ComponentCompetitionFulfillmentPredictionPrizeComparator(double minPrediction, double maxPrediction,
                    long targetDuration) {
        Helper.checkNotNegative(minPrediction, "minPrediction");
        Helper.checkNotNegative(maxPrediction, "maxPrediction");
        Helper.checkNotNegative(targetDuration, "targetDuration");
        if (minPrediction > maxPrediction) {
            throw new IllegalArgumentException("The minPrediction should not be larger than maxPrediction.");
        }
        this.minPrediction = minPrediction;
        this.maxPrediction = maxPrediction;
        this.targetDuration = targetDuration;
    }

    /**
     * <p>
     *
     * <pre>
     *  Compares the two predictions, testing which one is better, using the following rules (in order of importance):
     *
     *  1. Predictions in range &lt; predictions above the range &lt; predictions below the range
     *  2. Timelines shorter than or equal to the target &lt; timelines longer than the target
     *  3. Smaller prizes &lt; longer prizes
     * </pre>
     *
     * </p>
     *
     * <p>
     *
     * <pre>
     *  The results of the comparison are as follows:
     *  1. If prediction1 &lt; prediction2, then result is -1
     *  2. If prediction1 = prediction2, then result is 0
     *  3. If prediction1 &gt; prediction2, then result is 1
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

        // Test second condition by checking with the predictions' situations' duration (endDate - postingDate)
        ComponentCompetitionSituation s1 = prediction1.getSituation();
        Date endDate1 = s1.getEndDate();
        Date postingDate1 = s1.getPostingDate();
        ComponentCompetitionSituation s2 = prediction2.getSituation();
        Date endDate2 = s2.getEndDate();
        Date postingDate2 = s2.getPostingDate();
        if (endDate1 != null && endDate2 != null && postingDate1 != null && postingDate2 != null) {
            long t1 = endDate1.getTime() - postingDate1.getTime();
            long t2 = endDate2.getTime() - postingDate2.getTime();
            // If the test is decisive (i.e. does not result in equivalence of predictions), return appropriate result.
            if (t1 <= this.targetDuration && t2 > this.targetDuration) {
                return -1;
            } else if (t2 <= this.targetDuration && t1 > this.targetDuration) {
                return 1;
            }
        }

        // Test third condition by checking with the predictions' situations' prize value
        Double p1 = s1.getPrize();
        Double p2 = s2.getPrize();
        if (p1 != null && p2 != null) {
            // If the test is decisive (i.e. does not result in equivalence of predictions), return appropriate result.
            if (p1 < p2) {
                return -1;
            } else if (p1 > p2) {
                return 1;
            }
        }

        // no difference, return 0
        return 0;
    }

    /**
     * Indicates whether some other object is "equal to" this Comparator. A comparator is equal to this comparator if it
     * is of the same type and its minPrediction, maxPrediction, and targetDuration are the same.
     *
     * @param obj
     *            the reference object with which to compare.
     * @return true only if the specified object is also a comparator and it imposes the same ordering as this
     *         comparator.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof ComponentCompetitionFulfillmentPredictionPrizeComparator)) {
            return false;
        }
        ComponentCompetitionFulfillmentPredictionPrizeComparator c =
            (ComponentCompetitionFulfillmentPredictionPrizeComparator) obj;
        return new Double(c.minPrediction).equals(this.minPrediction)
                        && new Double(c.maxPrediction).equals(this.maxPrediction)
                        && c.targetDuration == this.targetDuration;
    }

    /**
     * Gets hash code of this comparator.
     *
     * @return hash code of this comparator.
     */
    public int hashCode() {
        return new Double(this.maxPrediction).hashCode()
            ^ new Double(this.minPrediction).hashCode()
            ^ new Long(this.targetDuration).hashCode();
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
     * Gets the targetDuration field value.
     *
     * @return the targetDuration field value
     */
    public long getTargetDuration() {
        return this.targetDuration;
    }
}
