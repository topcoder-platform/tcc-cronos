/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;

import java.util.Iterator;
import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for ComponentCompetitionSituationTimelineGenerator class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionSituationTimelineGeneratorUnitTests extends TestCase {

    /**
     * An instance of ComponentCompetitionSituationTimelineGenerator for the following tests.
     */
    private ComponentCompetitionSituationTimelineGenerator tester = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        tester = new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(), new Date(
                        100000), new Date(300000), 100000);
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        tester = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor. No exception is thrown.
     * </p>
     */
    public void test_ctor() {
        // no exception
        new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(), new Date(100000),
                        new Date(300000), 100000);
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for null template.
     * </p>
     */
    public void test_ctor_failure1() {
        try {
            new ComponentCompetitionSituationTimelineGenerator(null, new Date(100000), new Date(300000), 100000);
            fail("IllegalArgumentException should be thrown for null template.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for null minDate.
     * </p>
     */
    public void test_ctor_failure2() {
        try {
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(), null, new Date(
                            300000), 100000);
            fail("IllegalArgumentException should be thrown for null minDate.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for null maxDate.
     * </p>
     */
    public void test_ctor_failure3() {
        try {
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(), new Date(100000),
                            null, 100000);
            fail("IllegalArgumentException should be thrown for null maxDate.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for negative incrementFactor.
     * </p>
     */
    public void test_ctor_failure4() {
        try {
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(), new Date(100000),
                            new Date(300000), -100000);
            fail("IllegalArgumentException should be thrown for negative incrementFactory.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for zero incrementFactor.
     * </p>
     */
    public void test_ctor_failure5() {
        try {
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(), new Date(100000),
                            new Date(300000), 0);
            fail("IllegalArgumentException should be thrown for zero incrementFactory.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown if minDate is after maxDate.
     * </p>
     */
    public void test_ctor_failure6() {
        try {
            new ComponentCompetitionSituationTimelineGenerator(new ComponentCompetitionSituation(), new Date(400000),
                            new Date(300000), 100000);
            fail("IllegalArgumentException should be thrown if minDate is after maxDate.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the constructor and iterator method. The generated situations should be correct.
     * </p>
     */
    public void test_ctor_iterator() {
        Iterator<ComponentCompetitionSituation> iter = tester.iterator();
        // end date of the first situation is new Date(100000)
        assertTrue("There should be situation to get.", iter.hasNext());
        ComponentCompetitionSituation s = iter.next();
        assertEquals("The generated situation is incorrect", new Date(100000), s.getEndDate());

        // end date of the second situation is new Date(200000)
        assertTrue("There should be situation to get.", iter.hasNext());
        s = iter.next();
        assertEquals("The generated situation is incorrect", new Date(200000), s.getEndDate());

        // end date of the third situation is new Date(300000)
        assertTrue("There should be situation to get.", iter.hasNext());
        s = iter.next();
        assertEquals("The generated situation is incorrect", new Date(300000), s.getEndDate());

        assertFalse("There should be no situation.", iter.hasNext());
    }

}
