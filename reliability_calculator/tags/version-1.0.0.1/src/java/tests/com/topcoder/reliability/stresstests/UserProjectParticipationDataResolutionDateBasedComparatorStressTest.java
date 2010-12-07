/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import java.util.Date;

import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.comparators.UserProjectParticipationDataResolutionDateBasedComparator;

/**
 * <p>
 * This class contains Stress tests for UserProjectParticipationDataResolutionDateBasedComparator.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class UserProjectParticipationDataResolutionDateBasedComparatorStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents UserProjectParticipationDataResolutionDateBasedComparator instance for testing.
     * </p>
     */
    private UserProjectParticipationDataResolutionDateBasedComparator comparator;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        comparator = new UserProjectParticipationDataResolutionDateBasedComparator();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        comparator = null;
    }

    /**
     * <p>
     * Tests UserProjectParticipationDataResolutionDateBasedComparator#compare() with null arguments passed.
     * </p>
     * <p>
     * 0 should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCompare_Small() throws Exception {
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("Data should be equal.", 0, comparator.compare(null, null));
        }
    }

    /**
     * <p>
     * Tests UserProjectParticipationDataResolutionDateBasedComparator#compare() with second argument has less project
     * id.
     * </p>
     * <p>
     * -1 should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCompare_Large() throws Exception {
        Date date = new Date();
        UserProjectParticipationData data1 = createUserProjectParticipationData(date, 1, true);
        UserProjectParticipationData data2 = createUserProjectParticipationData(date, 2, true);
        for (int i = 0; i < getRunCount(); i++) {
            assertEquals("Data should be equal.", -1, comparator.compare(data1, data2));
        }
    }
}
