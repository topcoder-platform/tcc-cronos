/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.stresstests;

import java.util.HashMap;

import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsManager;

/**
 * Mock <code>ReviewerStatisticsManager</code>.
 * @author moon.river
 * @version 1.0
 */
public class MockReviewerStatisticsManager implements ReviewerStatisticsManager {

    /**
     * The statistics.
     */
    private HashMap<Long, ReviewerStatistics> stats = new HashMap<Long, ReviewerStatistics>();

    /**
     * Creates statistics.
     */
    public ReviewerStatistics create(ReviewerStatistics reviewStatistics) {
        stats.put(reviewStatistics.getReviewerId(), reviewStatistics);
        return reviewStatistics;
    }

    /**
     * Updates statistics.
     */
    public ReviewerStatistics update(ReviewerStatistics reviewStatistics) {
        stats.put(reviewStatistics.getReviewerId(), reviewStatistics);
        return reviewStatistics;
    }

    /**
     * Retrieves statistics.
     */
    public ReviewerStatistics retrieve(long id) {
        return stats.get(id);
    }

    /**
     * Deletes statistics.
     */
    public boolean delete(long id) {
        boolean found = stats.containsKey(id);
        stats.remove(id);
        return found;
    }

    /**
     * Mock - not used.
     */
    public ReviewerStatistics[] getReviewerAverageStatistics(long reviewerId) {
        return null;
    }

    /**
     * Mock - not used.
     */
    public ReviewerStatistics[] getReviewerStatistics(long reviewerId) {
        return null;
    }

    /**
     * Get statistics for a given type.
     */
    public ReviewerStatistics getReviewerStatisticsByCompetitionType(
            long reviewerId, int competitionTypeId) {
        return stats.get(reviewerId);
    }

}
