/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test for ReviewerStatistics class.
 * </p>
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class ReviewerStatisticsUnitTests extends TestCase {
    /**
     * ReviewerStatistics instance for testing.
     */
    private final ReviewerStatistics reviewerStatistics = new ReviewerStatistics();

    /**
     * Accuracy test for all getters and setters.
     *
     * @throws Exception for JUnit.
     */
    public void testGettersAndSetters() throws Exception {
        TestsHelper.assertBasicGetterSetterBehavior(reviewerStatistics, ReviewerStatistics.class);
    }

    /**
     * Failure test of <code>setCompetitionTypeId(long competitionTypeId)</code> constructor.
     *
     * <p>
     * competitionTypeId <= 0.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatistics_SetCompetitionTypeId() throws Exception {
        try {
            reviewerStatistics.setCompetitionTypeId(-1);

            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test of <code>setProjectId(long competitionTypeId)</code> constructor.
     *
     * <p>
     * projectId <= 0.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatistics_SetProjectId() throws Exception {
        try {
            reviewerStatistics.setProjectId(-1);

            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test of <code>setStatisticsType(StatisticsType statisticsType)</code> constructor.
     *
     * <p>
     * statisticsType is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatistics_SetStatisticsType() throws Exception {
        try {
            reviewerStatistics.setStatisticsType(null);

            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
