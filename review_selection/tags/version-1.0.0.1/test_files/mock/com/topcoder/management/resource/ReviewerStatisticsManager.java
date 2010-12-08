package com.topcoder.management.resource;

/**
 */
public interface ReviewerStatisticsManager {
    public ReviewerStatistics create(ReviewerStatistics reviewStatistics) throws ReviewerStatisticsManagerException;
    public ReviewerStatistics update(ReviewerStatistics reviewStatistics) throws ReviewerStatisticsManagerException;
    public ReviewerStatistics retrieve(long id) throws ReviewerStatisticsManagerException;
    public boolean delete(long id) throws ReviewerStatisticsManagerException;

    public ReviewerStatistics[] getReviewerAverageStatistics(long reviewerId) throws ReviewerStatisticsManagerException;
    public ReviewerStatistics[] getReviewerStatistics(long reviewerId) throws ReviewerStatisticsManagerException;

    public ReviewerStatistics getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId) throws ReviewerStatisticsManagerException;
    //public SideBySideStatistics getSideBySideStatistics(long firstReviewerId, long secondReviewerId, int competitionTypeId);
}