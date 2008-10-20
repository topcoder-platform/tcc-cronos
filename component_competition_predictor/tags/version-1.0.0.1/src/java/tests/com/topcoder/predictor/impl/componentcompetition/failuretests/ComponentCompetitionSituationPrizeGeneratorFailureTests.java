/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.failuretests;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationPrizeGenerator;

import junit.framework.TestCase;
/**
 * The failure tests of the class <code>ComponentCompetitionSituationPrizeGenerator</code>.
 *
 * @author telly12
 * @version 1.0
 */
public class ComponentCompetitionSituationPrizeGeneratorFailureTests extends
        TestCase {
    /**
     * The failure test of the ctor,
     * fail for the template is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure1() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(null, 1, 2, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the ctor,
     * fail for the minPrize is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure2() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(),
                    -1, 2, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the ctor,
     * fail for the maxPrize is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure3() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(),
                    1, -2, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the ctor,
     * fail for the incrementFactor is negative,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure4() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(),
                    1, 2, -1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the ctor,
     * fail for the incrementFactor is zero,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure5() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(),
                    1, 2, 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the ctor,
     * fail for the minPrizezero is greater than maxPrize,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure6() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(),
                    3, 2, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
