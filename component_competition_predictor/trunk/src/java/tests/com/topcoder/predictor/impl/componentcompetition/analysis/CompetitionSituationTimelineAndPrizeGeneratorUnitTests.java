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
 * Unit tests for CompetitionSituationTimelineAndPrizeGenerator class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompetitionSituationTimelineAndPrizeGeneratorUnitTests extends TestCase {

    /**
     * An instance of CompetitionSituationTimelineAndPrizeGenerator for the following tests.
     */
    private CompetitionSituationTimelineAndPrizeGenerator tester = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        tester = new CompetitionSituationTimelineAndPrizeGenerator(
                        new ComponentCompetitionSituation(),
                        100.0, 200.0, 100.0,
                        new Date(100000), new Date(200000), 100000);
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
        new CompetitionSituationTimelineAndPrizeGenerator(
                        new ComponentCompetitionSituation(),
                        100.0, 200.0, 100.0,
                        new Date(100000), new Date(300000), 100000);
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for null template.
     * </p>
     */
    public void test_ctor_failure1() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(
                            null,
                            100.0, 200.0, 100.0,
                            new Date(100000), new Date(300000), 100000);
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
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            100.0, 200.0, 100.0,
                            null, new Date(300000), 100000);
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
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            100.0, 200.0, 100.0,
                            new Date(100000), null, 100000);
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
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            100.0, 200.0, 100.0,
                            new Date(100000), new Date(300000), -100000);
            fail("IllegalArgumentException should be thrown for negative incrementFactory.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for zero timelineIncrementFactor.
     * </p>
     */
    public void test_ctor_failure5() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            100.0, 200.0, 100.0,
                            new Date(100000), new Date(300000), 0);
            fail("IllegalArgumentException should be thrown for zero timelineIncrementFactor.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for negative minPrize.
     * </p>
     */
    public void test_ctor_failure6() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            -100.0, 200.0, 100.0,
                            new Date(100000), new Date(300000), 100000);
            fail("IllegalArgumentException should be thrown for negative minPrize.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for negative maxPrize.
     * </p>
     */
    public void test_ctor_failure7() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            100.0, -200.0, 100.0,
                            new Date(100000), new Date(300000), 100000);
            fail("IllegalArgumentException should be thrown for negative maxPrize.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for negative prizeIncrementFactor.
     * </p>
     */
    public void test_ctor_failure8() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            100.0, 200.0, -100.0,
                            new Date(100000), new Date(300000), 100000);
            fail("IllegalArgumentException should be thrown for negative prizeIncrementFactory.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for zero prizeIncrementFactor.
     * </p>
     */
    public void test_ctor_failure9() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            100.0, 200.0, 0.0,
                            new Date(100000), new Date(300000), 100000);
            fail("IllegalArgumentException should be thrown for zero prizeIncrementFactory.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown if minDate is after maxDate.
     * </p>
     */
    public void test_ctor_failure10() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            100.0, 200.0, 10.0,
                            new Date(400000), new Date(300000), 100000);
            fail("IllegalArgumentException should be thrown if minDate is after maxDate.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown if minPrize is larger
     * than maxPrize.
     * </p>
     */
    public void test_ctor_failure11() {
        try {
            new CompetitionSituationTimelineAndPrizeGenerator(
                            new ComponentCompetitionSituation(),
                            300.0, 200.0, 10.0,
                            new Date(100000), new Date(300000), 100000);
            fail("IllegalArgumentException should be thrown if minPrize is larger than maxPrize.");
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
        // prize of first situation is 100.0
        assertTrue("There should be situation to get.", iter.hasNext());
        ComponentCompetitionSituation s = iter.next();
        assertEquals("The generated situation is incorrect", new Date(100000), s.getEndDate());
        assertEquals("The generated situation is incorrect", 100.0, s.getPrize().doubleValue());

        // end date of the second situation is new Date(100000)
        // prize of second situation is 200.0
        assertTrue("There should be situation to get.", iter.hasNext());
        s = iter.next();
        assertEquals("The generated situation is incorrect", new Date(100000), s.getEndDate());
        assertEquals("The generated situation is incorrect", 200.0, s.getPrize().doubleValue());

        // end date of the third situation is new Date(200000)
        // prize of third situation is 100.0
        assertTrue("There should be situation to get.", iter.hasNext());
        s = iter.next();
        assertEquals("The generated situation is incorrect", new Date(200000), s.getEndDate());
        assertEquals("The generated situation is incorrect", 100.0, s.getPrize().doubleValue());

        // end date of the fourth situation is new Date(200000)
        // prize of fourth situation is 200.0
        assertTrue("There should be situation to get.", iter.hasNext());
        s = iter.next();
        assertEquals("The generated situation is incorrect", new Date(200000), s.getEndDate());
        assertEquals("The generated situation is incorrect", 200.0, s.getPrize().doubleValue());

        assertFalse("There should be no situation.", iter.hasNext());
    }

}
