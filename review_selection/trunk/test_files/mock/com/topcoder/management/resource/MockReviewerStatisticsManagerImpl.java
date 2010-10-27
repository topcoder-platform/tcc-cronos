package com.topcoder.management.resource;

public class MockReviewerStatisticsManagerImpl implements ReviewerStatisticsManager {

    public MockReviewerStatisticsManagerImpl() {
    }

    public ReviewerStatistics create(ReviewerStatistics reviewStatistics) {
        return null;
    }

    public boolean delete(long id) {
        return false;
    }

    public ReviewerStatistics[] getReviewerAverageStatistics(long reviewerId) {
        return null;
    }

    public ReviewerStatistics[] getReviewerStatistics(long reviewerId) {
        return null;
    }

    public ReviewerStatistics getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId) {
        return null;
    }

    public ReviewerStatistics retrieve(long id) {
        return null;
    }

    public ReviewerStatistics update(ReviewerStatistics reviewStatistics) {
        return null;
    }

}
