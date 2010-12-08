package com.topcoder.management.resource;

/**
 */
public class ReviewerStatistics {
    /**
     */
    private long id;

    /**
     */
    private double accuracy;

    /**
     */
    private double coverage;

    /**
     */
    private double timelineReliability;

    /**
     */
    private double totalEvaluationCoefficient;

    /**
     */
    private long reviewerId;

    /**
     */
    private long competitionTypeId;

    /**
     */
    private long projectId;

    /**
     */
    private StatisticsType statisticsType;

    /**
     */
    private double eligibilityPoints;

    /**
     */
    public ReviewerStatistics() {
    }

    /**
     */
    public long getId() {
        return this.id;
    }

    /**
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     */
    public double getAccuracy() {
        return this.accuracy;
    }

    /**
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    /**
     */
    public double getCoverage() {
        return this.coverage;
    }

    /**
     */
    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    /**
     */
    public long getReviewerId() {
        return this.reviewerId;
    }

    /**
     */
    public void setReviewerId(long reviewerId) {
        this.reviewerId = reviewerId;
    }

    /**
     */
    public long getCompetitionTypeId() {
        return this.competitionTypeId;
    }

    /**
     */
    public void setCompetitionTypeId(long competitionTypeId) {
        this.competitionTypeId = competitionTypeId;
    }

    /**
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     */
    public StatisticsType getStatisticsType() {
        return this.statisticsType;
    }

    /**
     */
    public void setStatisticsType(StatisticsType statisticsType) {
        this.statisticsType = statisticsType;
    }

    /**
     */
    public double getEligibilityPoints() {
        return this.eligibilityPoints;
    }

    /**
     */
    public void setEligibilityPoints(double eligibilityPoints) {
        this.eligibilityPoints = eligibilityPoints;
    }

    /**
     */
    public double getTimelineReliability() {
        return this.timelineReliability;
    }

    /**
     */
    public void setTimelineReliability(double timelineReliability) {
        this.timelineReliability = timelineReliability;
    }

    /**
     */
    public double getTotalEvaluationCoefficient() {
        return this.totalEvaluationCoefficient;
    }

    /**
     */
    public void setTotalEvaluationCoefficient(double totalEvaluationCoefficient) {
        this.totalEvaluationCoefficient = totalEvaluationCoefficient;
    }
}