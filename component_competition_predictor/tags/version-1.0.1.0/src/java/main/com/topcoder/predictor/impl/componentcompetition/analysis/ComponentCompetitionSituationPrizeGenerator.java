/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A generator of ComponentCompetitionSituation based on a prize range, and an increment factor for the step between
 * range values. It extends BaseComponentCompetitionSituationGenerator, to which is passes the generated situations.
 * </p>
 *
 * <p>
 * Usage:
 *
 * <pre>
 * ComponentCompetitionSituationPrizeGenerator prizeGen = new ComponentCompetitionSituationPrizeGenerator(
 *                 new ComponentCompetitionSituation(), 100.0, 300.0, 100.0);
 * Iterator&lt;ComponentCompetitionSituation&gt; iterator1 = prizeGen.iterator();
 * // iterator1 contains 3 situations, their prizes are
 * // 100.0, 200.0, 300.0
 * ComponentCompetitionSituation s = iterator1.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, 100.0, s.getPrize().doubleValue());
 * s = iterator1.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, 200.0, s.getPrize().doubleValue());
 * s = iterator1.next();
 * assertEquals(&quot;The generated situation is incorrect&quot;, 300.0, s.getPrize().doubleValue());
 * assertFalse(&quot;There should be no situation.&quot;, iterator1.hasNext());
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
public class ComponentCompetitionSituationPrizeGenerator extends BaseComponentCompetitionSituationGenerator {
    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = -3851521913544294583L;

    /**
     * It is a constructor. It generates the situations from the prize range and increment factor using the passed
     * situation as a template. These situations are then made available in the iterator method.
     *
     * @param maxPrize
     *            the maximum prize
     * @param minPrize
     *            the minimum prize
     * @param template
     *            the situation that is the template for the generated situations
     * @param incrementFactor
     *            the incrementing factor for prize generation.
     * @throws IllegalArgumentException
     *             If template is null, or if any numeric value is negative, or minPrize &gt; maxPrize,
     *             or incrementFactor is not positive
     */
    public ComponentCompetitionSituationPrizeGenerator(ComponentCompetitionSituation template, double minPrize,
                    double maxPrize, double incrementFactor) {
        super(generateSituations(template, minPrize, maxPrize, incrementFactor));
    }

    /**
     * Helper method that performs the actual generation. It exists so the base constructor can be properly called. It
     * generates the situations from the prize range and increment factor using the passed situation as a template.
     *
     * @param maxPrize
     *            the maximum prize
     * @param minPrize
     *            the minimum prize
     * @param template
     *            the situation that is the template for the generated situations
     * @param incrementFactor
     *            the incrementing factor for prize generation.
     * @return the list of generated situations
     * @throws IllegalArgumentException -
     *             If template is null, or if any numeric value is negative, or minPrize &gt; maxPrize,
     *             or incrementFactory is not positive
     */
    private static List<ComponentCompetitionSituation> generateSituations(ComponentCompetitionSituation template,
                    double minPrize, double maxPrize, double incrementFactor) {
        Helper.checkNotNull(template, "template");
        Helper.checkNotNegative(minPrize, "minPrize");
        Helper.checkNotNegative(maxPrize, "maxPrize");
        Helper.checkPositive(incrementFactor, "incrementFactor");
        if (minPrize > maxPrize) {
            throw new IllegalArgumentException("The min prize should not be more than max prize.");
        }
        // Create a new list of situations:
        List<ComponentCompetitionSituation> situations = new ArrayList<ComponentCompetitionSituation>();
        // loop by prize
        for (double prize = minPrize; prize <= maxPrize; prize += incrementFactor) {
            // Create a situation from template:
            ComponentCompetitionSituation situation = (ComponentCompetitionSituation) template.clone();
            // Set prize:
            situation.setPrize(prize);
            // Append to situations list
            situations.add(situation);
        }
        return situations;
    }
}
