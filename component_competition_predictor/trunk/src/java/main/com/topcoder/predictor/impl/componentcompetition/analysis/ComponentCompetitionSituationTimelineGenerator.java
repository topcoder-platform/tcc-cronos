/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Helper;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>
 * A generator of ComponentCompetitionSituation based on a timeline range, and an increment factor for the step between
 * range values. It extends BaseComponentCompetitionSituationGenerator, to which is passes the generated situations.
 * </p>
 *
 * <p>
 * Usage:
 *
 * <pre>
 * ComponentCompetitionSituationTimelineGenerator timeGen = new ComponentCompetitionSituationTimelineGenerator(
 *                 new ComponentCompetitionSituation(), new Date(100000), new Date(300000), 100000);
 * Iterator&lt;ComponentCompetitionSituation&gt; iterator2 = timeGen.iterator();
 * // iterator2 contains 3 situations, their end dates are
 * // new Date(100000), Date(200000), Date(300000)
 * s = iterator2.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, new Date(100000), s.getEndDate());
 * s = iterator2.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, new Date(200000), s.getEndDate());
 * s = iterator2.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, new Date(300000), s.getEndDate());
 * assertFalse(&quot;There should be no situation.&quot;, iterator2.hasNext());
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
public class ComponentCompetitionSituationTimelineGenerator extends BaseComponentCompetitionSituationGenerator {
    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = -5734902884898792194L;

    /**
     * It is a constructor. It generates the situations from the timeline range and increment factor using the passed
     * situation as a template. These situations are then made available in the iterator method.
     *
     * @param maxDate
     *            the maximum end Date
     * @param template
     *            the situation that is the template for the generated situations
     * @param incrementFactor
     *            the incrementing factor for timeline generation.
     * @param minDate
     *            the minimum end Date
     * @throws IllegalArgumentException -
     *             If any object is null, or if incrementFactor is negative, or minDate &gt; maxDate,
     *             or incrementFactor is not positive
     */
    public ComponentCompetitionSituationTimelineGenerator(ComponentCompetitionSituation template, Date minDate,
                    Date maxDate, long incrementFactor) {
        super(generateSituations(template, minDate, maxDate, incrementFactor));
    }

    /**
     * Helper method that performs the actual generation. It exists so the base constructor can be properly called. It
     * generates the situations from the timeline range and increment factor using the passed situation as a template.
     *
     * @param maxDate
     *            the maximum end Date
     * @param template
     *            the situation that is the template for the generated situations
     * @param incrementFactor
     *            the incrementing factor for timeline generation.
     * @param minDate
     *            the minimum end Date
     * @return the list of generated situations
     * @throws IllegalArgumentException
     *             If any object is null, or if incrementFactor is not positive, or minDate &gt; maxDate, or
     *             incrementFactor is not positive
     */
    private static List<ComponentCompetitionSituation> generateSituations(ComponentCompetitionSituation template,
                    Date minDate, Date maxDate, long incrementFactor) {
        Helper.checkNotNull(template, "template");
        Helper.checkNotNull(minDate, "minDate");
        Helper.checkNotNull(maxDate, "maxDate");
        Helper.checkPositive(incrementFactor, "incrementFactor");
        if (minDate.after(maxDate)) {
            throw new IllegalArgumentException("The min date should not be after max date.");
        }
        // Create a new list of situations:
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        // loop by time
        long end = maxDate.getTime();
        for (long finishDate = minDate.getTime(); finishDate <= end; finishDate += incrementFactor) {
            // Create a situation from template:
            ComponentCompetitionSituation situation = (ComponentCompetitionSituation) template.clone();
            // Set end date:
            situation.setEndDate(new Date(finishDate));
            // Append to situations list
            situations.add(situation);
        }
        return situations;
    }
}
