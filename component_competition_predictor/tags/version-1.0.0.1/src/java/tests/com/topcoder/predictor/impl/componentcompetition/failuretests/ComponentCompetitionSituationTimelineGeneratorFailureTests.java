/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.failuretests;

import java.util.Date;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;
import com.topcoder.predictor.impl.componentcompetition.analysis.ComponentCompetitionSituationTimelineGenerator;

import junit.framework.TestCase;
/**
 * The failure tests of the class <code>ComponentCompetitionSituationTimelineGenerator</code>.
 *
 * @author telly12
 * @version 1.0
 */
public class ComponentCompetitionSituationTimelineGeneratorFailureTests extends
        TestCase {
    /**
     * The failure test of the ctor,
     * fail for the template is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure1() {
        try {
            new ComponentCompetitionSituationTimelineGenerator(null,
                    new Date(), new Date(), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the ctor,
     * fail for the minDate is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure2() {
        try {
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(),
                    null, new Date(), 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the ctor,
     * fail for the maxDate is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure3() {
        try {
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(),
                    new Date(), null, 1);
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
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(),
                    new Date(), new Date(), -1);
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
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(),
                    new Date(), new Date(), 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the ctor,
     * fail for the minDate is greater than maxDate,
     * IllegalArgumentException should be thrown.
     *
     */
    public void test_ctor_failure6() {
        Date date1 = new Date();
        Date date2 = new Date(date1.getTime() + 1);
        try {
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(),
                    date2, date1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
