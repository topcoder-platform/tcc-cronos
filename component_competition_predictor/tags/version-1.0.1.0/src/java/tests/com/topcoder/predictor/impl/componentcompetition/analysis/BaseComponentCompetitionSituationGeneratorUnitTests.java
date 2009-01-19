/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition.analysis;

import com.topcoder.predictor.impl.componentcompetition.ComponentCompetitionSituation;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for BaseComponentCompetitionSituationGenerator class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseComponentCompetitionSituationGeneratorUnitTests extends TestCase {

    /**
     * An instance of BaseComponentCompetitionSituationGenerator for the following tests.
     */
    private BaseComponentCompetitionSituationGenerator tester = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        List<ComponentCompetitionSituation> generatedSituations = new ArrayList<ComponentCompetitionSituation>();
        generatedSituations.add(new ComponentCompetitionSituation());
        generatedSituations.add(new ComponentCompetitionSituation());
        tester = new BaseComponentCompetitionSituationGeneratorTester(generatedSituations);
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
        List<ComponentCompetitionSituation> generatedSituations = new ArrayList<ComponentCompetitionSituation>();
        generatedSituations.add(new ComponentCompetitionSituation());
        // no exception
        new BaseComponentCompetitionSituationGeneratorTester(generatedSituations);
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown for null argument.
     * </p>
     */
    public void test_ctor_failure1() {
        try {
            new BaseComponentCompetitionSituationGeneratorTester(null);
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown if there is null situation.
     * </p>
     */
    public void test_ctor_failure2() {
        List<ComponentCompetitionSituation> generatedSituations = new ArrayList<ComponentCompetitionSituation>();
        generatedSituations.add(null);
        try {
            new BaseComponentCompetitionSituationGeneratorTester(generatedSituations);
            fail("IllegalArgumentException should be thrown if there is null situation.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the constructor. IllegalArgumentException should be thrown if there is no situation.
     * </p>
     */
    public void test_ctor_failure3() {
        List<ComponentCompetitionSituation> generatedSituations = new ArrayList<ComponentCompetitionSituation>();
        try {
            new BaseComponentCompetitionSituationGeneratorTester(generatedSituations);
            fail("IllegalArgumentException should be thrown if there is no situation.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the iterator method. There should be two situations.
     * </p>
     */
    public void test_iterator() {
        Iterator<ComponentCompetitionSituation> iter = tester.iterator();
        assertTrue("There should be situation to get.", iter.hasNext());
        ComponentCompetitionSituation s = iter.next();
        assertNotNull("The situation should not be null", s);

        assertTrue("There should be situation to get.", iter.hasNext());
        s = iter.next();
        assertNotNull("The situation should not be null", s);

        assertFalse("There should be no situation.", iter.hasNext());
    }

}
