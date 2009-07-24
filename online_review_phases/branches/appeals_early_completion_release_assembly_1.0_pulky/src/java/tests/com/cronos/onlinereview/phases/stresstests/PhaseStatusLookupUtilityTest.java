/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.stresstests;

import com.cronos.onlinereview.phases.lookup.PhaseStatusLookupUtility;



/**
 * <p>Stress tests for <code>PhaseStatusLookupUtility</code>.</p>
 * <p>
 * The multi thread problem happens when two or more threads try to loop up id at the same time.
 * But this also won't become error since we will always get the same thing from the database.
 * The only problem is maybe we tried more than one time in the db.
 * That's ok so here I also won't do thread test.
 * </p>
 *
 * @author assistant
 * @version 1.0
 */
public class PhaseStatusLookupUtilityTest extends StressBaseTest {

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test the method lookUpId().
     * @throws Exception to JUnit
     */
    public void testLookUpId_FirstLevel() throws Exception {
        startRecord("lookUpId", FIRST_LEVEL);
        for (int i = 0; i < FIRST_LEVEL; i++) {
            PhaseStatusLookupUtility.lookUpId(conn, "Scheduled");
        }
        endRecord("lookUpId", FIRST_LEVEL);
    }

    /**
     * Test the method lookUpId().
     * @throws Exception to JUnit
     */
    public void testLookUpId_SecondLevel() throws Exception {
        startRecord("lookUpId", SECOND_LEVEL);
        for (int i = 0; i < SECOND_LEVEL; i++) {
            PhaseStatusLookupUtility.lookUpId(conn, "Scheduled");
        }
        endRecord("lookUpId", SECOND_LEVEL);
    }

    /**
     * Test the method lookUpId().
     * @throws Exception to JUnit
     */
    public void testLookUpId_ThirdLevel() throws Exception {
        startRecord("lookUpId", THIRD_LEVEL);
        for (int i = 0; i < THIRD_LEVEL; i++) {
            PhaseStatusLookupUtility.lookUpId(conn, "Scheduled");
        }
        endRecord("lookUpId", THIRD_LEVEL);
    }

}
