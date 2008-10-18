/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.Helper;
import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * <p>
 * A generator of ComponentCompetitionSituation based on timeline and prize ranges, and respective increment factors for
 * the step between these range values. It extends BaseComponentCompetitionSituationGenerator, to which is passes the
 * generated situations.
 * </p>
 *
 * <p>
 * Usage:
 *
 * <pre>
 * CompetitionSituationTimelineAndPrizeGenerator prizeAndTimeGen = new CompetitionSituationTimelineAndPrizeGenerator(
 *     new ComponentCompetitionSituation(), 100.0, 200.0, 100.0, new Date(100000), new Date(200000), 100000);
 * Iterator&lt;ComponentCompetitionSituation&gt; iterator3 = prizeAndTimeGen.iterator();
 * // there should be 4 situations
 * // end date of the first situation is new Date(100000)
 * // prize of first situation is 100.0
 * s = iterator3.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, new Date(100000), s.getEndDate());
 * assertEquals(&quot;The generated situation is incorrect&quot;, 100.0, s.getPrize().doubleValue());
 * // end date of the second situation is new Date(100000)
 * // prize of second situation is 200.0
 * s = iterator3.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, new Date(100000), s.getEndDate());
 * assertEquals(&quot;The generated situation is incorrect&quot;, 200.0, s.getPrize().doubleValue());
 * // end date of the third situation is new Date(200000)
 * // prize of third situation is 100.0
 * s = iterator3.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, new Date(200000), s.getEndDate());
 * assertEquals(&quot;The generated situation is incorrect&quot;, 100.0, s.getPrize().doubleValue());
 * // end date of the fourth situation is new Date(200000)
 * // prize of fourth situation is 200.0
 * s = iterator3.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, new Date(200000), s.getEndDate());
 * assertEquals(&quot;The generated situation is incorrect&quot;, 200.0, s.getPrize().doubleValue());
 * assertFalse(&quot;There should be no situation.&quot;, iterator3.hasNext());
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
public class CompetitionSituationTimelineAndPrizeGenerator extends BaseComponentCompetitionSituationGenerator {
    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = -4913549393007198857L;

    /**
     * It is a constructor. It generates the situations from the prize and timeline range and increment factor using the
     * passed situation as a template. These situations are then made available in the iterator method.
     *
     * @param maxPrize
     *            the maximum prize
     * @param maxDate
     *            the maximum end Date
     * @param minPrize
     *            the minimum prize
     * @param prizeIncrementFactor
     *            the incrementing factor for prize generation.
     * @param template
     *            the situation that is the template for the generated situations
     * @param timelineIncrementFactor
     *            the incrementing factor for timeline generation.
     * @param minDate
     *            the minimum end Date
     * @throws IllegalArgumentException
     *             If any object is null, or if any numeric value is negative, or minPrize &gt; maxPrize, or minDate
     *             &gt; maxDate, or prizeIncrementFactor is not positive, or timelineIncrementFactor is not positive
     */
    public CompetitionSituationTimelineAndPrizeGenerator(ComponentCompetitionSituation template, double minPrize,
                    double maxPrize, double prizeIncrementFactor, Date minDate, Date maxDate,
                    long timelineIncrementFactor) {
        super(generateSituations(template, minPrize, maxPrize, prizeIncrementFactor, minDate, maxDate,
                        timelineIncrementFactor));
    }

    /**
     * Helper method that performs the actual generation. It exists so the base constructor can be properly called. It
     * generates the situations from the timeline and prize ranges and increment factors using the passed situation as a
     * template.
     *
     * @param maxPrize
     *            the maximum prize
     * @param maxDate
     *            the maximum end Date
     * @param minPrize
     *            the minimum prize
     * @param prizeIncrementFactor
     *            the incrementing factor for prize generation.
     * @param template
     *            the situation that is the template for the generated situations
     * @param timelineIncrementFactor
     *            the incrementing factor for timeline generation.
     * @param minDate
     *            the minimum end Date
     * @return a list of the generated situations
     * @throws IllegalArgumentException
     *             If any object is null, or if any numeric value is negative, or minPrize &gt; maxPrize, or minDate
     *             &gt; maxDate, or prizeIncrementFactor is not positive, or timelineIncrementFactor is not positive
     */
    private static List<ComponentCompetitionSituation> generateSituations(ComponentCompetitionSituation template,
                    double minPrize, double maxPrize, double prizeIncrementFactor, Date minDate, Date maxDate,
                    long timelineIncrementFactor) {
        Helper.checkNotNull(template, "template");
        Helper.checkNotNull(minDate, "minDate");
        Helper.checkNotNull(maxDate, "maxDate");
        Helper.checkPositive(timelineIncrementFactor, "incrementFactor");
        if (minDate.after(maxDate)) {
            throw new IllegalArgumentException("The min date should not be after max date.");
        }
        Helper.checkNotNull(template, "template");
        Helper.checkNotNegative(minPrize, "minPrize");
        Helper.checkNotNegative(maxPrize, "maxPrize");
        Helper.checkPositive(prizeIncrementFactor, "incrementFactor");
        if (minPrize > maxPrize) {
            throw new IllegalArgumentException("The min prize should not be more than max prize.");
        }
        // Create a new list of situations:
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        // loop by time
        long end = maxDate.getTime();
        for (long finishDate = minDate.getTime(); finishDate <= end; finishDate += timelineIncrementFactor) {
            // loop by prize
            for (double prize = minPrize; prize <= maxPrize; prize += prizeIncrementFactor) {
                // Create a situation from template:
                ComponentCompetitionSituation situation = (ComponentCompetitionSituation) template.clone();
                // Set end date:
                situation.setEndDate(new Date(finishDate));
                // SEt prize:
                situation.setPrize(prize);
                // Append to situations list
                situations.add(situation);
            }
        }
        return situations;
    }
}
