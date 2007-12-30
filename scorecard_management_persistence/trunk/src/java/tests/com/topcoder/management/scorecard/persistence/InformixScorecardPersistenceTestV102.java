/*
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.scorecard.persistence;

import com.topcoder.management.scorecard.ScorecardIDInfo;
import com.topcoder.management.scorecard.ScorecardPersistence;


/**
 * The unit tests for the method getScorecardsIDInfo
 * in InformixScorecardPersistence class of version 1.0.2.
 *
 * @author Angen
 * @version 1.0.2
 */
public class InformixScorecardPersistenceTestV102 extends DbTestCase {
    /**
     * The InformixScorecardPersistence instance to test on.
     */
    private ScorecardPersistence persistence = null;

    /**
     * Sets up the test environment. It creates required objects and references.
     *
     * @throws Exception to Junit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        testDBManager.loadDataSet(
            "test_files/dataset_for_default_scorecard.xml");
        persistence = new InformixScorecardPersistence(NAMESPACE);
    }

    /**
     * Tests getScorecardsIDInfo with valid parameter.
     *
     * @throws Exception to Junit.
     */
    public void testGetScorecardsIDInfo1() throws Exception {
        ScorecardIDInfo[] results = persistence.getScorecardsIDInfo(1);

        assertTrue(results.length == 3);
        assertTrue(results[0].getScorecardTypeId() == 1);
        assertTrue(results[0].getScorecardId() == 1);
        assertTrue(results[1].getScorecardTypeId() == 1);
        assertTrue(results[1].getScorecardId() == 2);
        assertTrue(results[2].getScorecardTypeId() == 2);
        assertTrue(results[2].getScorecardId() == 3);
    }

    /**
     * Tests getScorecardsIDInfo with valid parameter.
     *
     * @throws Exception to Junit.
     */
    public void testGetScorecardsIDInfo2() throws Exception {
        ScorecardIDInfo[] results = persistence.getScorecardsIDInfo(2);

        assertTrue(results.length == 1);
        assertTrue(results[0].getScorecardTypeId() == 2);
        assertTrue(results[0].getScorecardId() == 4);
    }

    /**
     * Tests getScorecardsIDInfo with invalid parameter.
     *
     * @throws Exception to Junit.
     */
    public void testGetScorecardsIDInfo3() throws Exception {
        try {
            persistence.getScorecardsIDInfo(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests getScorecardsIDInfo with invalid parameter.
     *
     * @throws Exception to Junit.
     */
    public void testGetScorecardsIDInfo4() throws Exception {
        try {
            persistence.getScorecardsIDInfo(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
}
