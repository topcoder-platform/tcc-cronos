/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.util.List;

/**
 * <p>
 * This class represents the side by side statistics for two reviewers.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: it's mutable and not thread safe.
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class SideBySideStatistics {
    /**
     * Represents statistics for the first reviewer. It's initialized in constructor to null. Has setter and getter,
     * can be any value
     */
    private List<ReviewerStatistics> firstReviewerStatistics;

    /**
     * Represents statistics for the second reviewer. It's initialized in constructor to null. Has setter and getter,
     * can be any value
     */
    private List<ReviewerStatistics> secondReviewerStatistics;

    /**
     * Creates an instance of SideBySideStatistics class.
     */
    public SideBySideStatistics() {
        // does nothing
    }

    /**
     * Gets statistics for the first reviewer.
     *
     * @return the statistics for the first reviewer
     */
    public List<ReviewerStatistics> getFirstReviewerStatistics() {
        return firstReviewerStatistics;
    }

    /**
     * Sets statistics for the first reviewer.
     *
     * @param statistics the statistics for the first reviewer
     */
    public void setFirstReviewerStatistics(List<ReviewerStatistics> statistics) {
        this.firstReviewerStatistics = statistics;
    }

    /**
     * Gets statistics for the second reviewer.
     *
     * @return the statistics for the second reviewer
     */
    public List<ReviewerStatistics> getSecondReviewerStatistics() {
        return secondReviewerStatistics;
    }

    /**
     * Sets statistics for the second reviewer.
     *
     * @param statistics the statistics for the second reviewer
     */
    public void setSecondReviewerStatistics(List<ReviewerStatistics> statistics) {
        this.secondReviewerStatistics = statistics;
    }
}
