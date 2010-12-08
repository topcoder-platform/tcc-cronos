/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests.mock;

import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsManager;

/**
 * Mock implementation of ProjectManager.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockReviewerStatisticsManager implements ReviewerStatisticsManager {
    /**
     * Empty.
     *
     * @param reviewStatistics
     *            the v.
     * @return null.
     */
    public ReviewerStatistics create(ReviewerStatistics reviewStatistics) {
        return null;
    }

    /**
     * Empty.
     *
     * @param id
     *            the id.
     * @return false.
     */
    public boolean delete(long id) {
        return false;
    }

    /**
     * Empty.
     *
     * @param reviewerId
     *            the reviewerId.
     * @return null.
     */
    public ReviewerStatistics[] getReviewerAverageStatistics(long reviewerId) {
        return null;
    }

    /**
     * Empty.
     *
     * @param reviewerId
     *            the reviewerId.
     * @return null.
     */
    public ReviewerStatistics[] getReviewerStatistics(long reviewerId) {
        return null;
    }

    /**
     * Empty.
     *
     * @param reviewerId
     *            the reviewerId.
     * @param competitionTypeId
     *            the competitionTypeId.
     * @return null.
     */
    public ReviewerStatistics getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId) {
        return null;
    }

    /**
     * Empty.
     *
     * @param id
     *            the id.
     * @return null.
     */
    public ReviewerStatistics retrieve(long id) {
        return null;
    }

    /**
     * Empty.
     *
     * @param reviewStatistics
     *            the reviewStatistics.
     * @return null.
     */
    public ReviewerStatistics update(ReviewerStatistics reviewStatistics) {
        return null;
    }

}
