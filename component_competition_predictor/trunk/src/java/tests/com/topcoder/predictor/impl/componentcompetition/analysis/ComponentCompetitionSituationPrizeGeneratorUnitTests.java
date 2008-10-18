/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for ComponentCompetitionSituationPrizeGenerator class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentCompetitionSituationPrizeGeneratorUnitTests extends TestCase {

    /**
     * An instance of ComponentCompetitionSituationPrizeGenerator for the following tests.
     */
    private ComponentCompetitionSituationPrizeGenerator tester = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        tester = new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(), 100.0, 300.0,
                        100.0);
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
        new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(), 100.0, 300.0, 1.0);
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for null template.
     * </p>
     */
    public void test_ctor_failure1() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(null, 100.0, 200.0, 50.0);
            fail("IllegalArgumentException should be thrown for null template.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for negative minPrize.
     * </p>
     */
    public void test_ctor_failure2() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(), -100.0, 300.0, 1.0);
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
    public void test_ctor_failure3() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(), 100.0, -300.0, 1.0);
            fail("IllegalArgumentException should be thrown for negative maxPrize.");
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
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(), 100.0, 300.0, -1.0);
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
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(), 100.0, 300.0, 0.0);
            fail("IllegalArgumentException should be thrown for zero incrementFactory.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown if minPrize is larger than maxPrize.
     * </p>
     */
    public void test_ctor_failure6() {
        try {
            new ComponentCompetitionSituationPrizeGenerator(new ComponentCompetitionSituation(), 400.0, 300.0, 10.0);
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
        // prize of the first situation is 100.0
        assertTrue("There should be situation to get.", iter.hasNext());
        ComponentCompetitionSituation s = iter.next();
        assertEquals("The generated situation is incorrect", 100.0, s.getPrize().doubleValue());

        // prize of the second situation is 200.0
        assertTrue("There should be situation to get.", iter.hasNext());
        s = iter.next();
        assertEquals("The generated situation is incorrect", 200.0, s.getPrize().doubleValue());

        // prize of the third situation is 300.0
        assertTrue("There should be situation to get.", iter.hasNext());
        s = iter.next();
        assertEquals("The generated situation is incorrect", 300.0, s.getPrize().doubleValue());

        assertFalse("There should be no situation.", iter.hasNext());
    }

}
