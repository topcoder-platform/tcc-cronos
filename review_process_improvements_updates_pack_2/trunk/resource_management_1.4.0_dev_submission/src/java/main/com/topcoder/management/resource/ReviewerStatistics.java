/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

/**
 * <p>
 * This is the entity class that represents the reviewer statistics, it implements ResourceStatistics interface and
 * extends AuditableResourceStructure that provides auditing capabilities.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: it's mutable and not thread safe
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class ReviewerStatistics extends AuditableResourceStructure implements ResourceStatistics {
    /**
     * Represents id of reviewer statistics.
     *
     * It's initialized in constructor to 0, cannot be negative, has getter and setter.
     */
    private long id;

    /**
     * Represents accuracy of reviewer statistics.
     *
     * It's initialized in constructor to 0, can be any value, has getter and setter.
     */
    private double accuracy;

    /**
     * Represents coverage of reviewer statistics.
     *
     * It's initialized in constructor to 0, can be any value, has getter and setter.
     */
    private double coverage;

    /**
     * Represents timeline reliability of reviewer statistics.
     *
     * It's initialized in constructor to 0, can be any value, has getter and setter.
     */
    private double timelineReliability;

    /**
     * Represents total evaluation coefficient of reviewer statistics, a coefficient computed from the other stored
     * coefficient designed to represent the reviewer performance in the given project and to control the relative
     * amount of payment that will be distributed to the reviewer.
     *
     * It's initialized in constructor to 0, can be any value, has getter and setter.
     */
    private double totalEvaluationCoefficient;

    /**
     * Represents id of the reviewer. The id of the reviewer for which the statistics are maintained
     *
     * It's initialized in constructor to 0, cannot be negative, has getter and setter.
     */
    private long reviewerId;

    /**
     * Represents id of the competition type. The competition type for which the statistics is maintained (eg design,
     * architecture etc). Lookup table.
     *
     * It's initialized in constructor to 0, cannot be negative, has getter and setter.
     */
    private int competitionTypeId;

    /**
     * Represents id of the project. The last project for the average statistics and the current project for the
     * history statistics
     *
     * It's initialized in constructor to 0, cannot be negative, has getter and setter.
     */
    private long projectId;

    /**
     * Represents the statistics type. if the statistic is a history or average statistic (this property is not
     * persisted)
     *
     * It's initialized in constructor to null, can be any value, has getter and setter.
     */
    private StatisticsType statisticsType;

    /**
     * Represents the eligibility points. the number of eligibility points the reviewer has for the given competition
     * type. It's stored per competition type.
     *
     * It's initialized in constructor to 0, can be any value, has getter and setter.
     */
    private double eligibilityPoints;

    /**
     * Creates an instance of this class.
     */
    public ReviewerStatistics() {
    }

    /**
     * Gets the Id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     * @throws IllegalArgumentException if id is negative
     */
    public void setId(long id) {
        Helper.checkLongPositive(id, "id");
        this.id = id;
    }

    /**
     * Gets the accuracy value.
     *
     * @return the accuracy value
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the accuracy value.
     *
     * @param accuracy the accuracy value
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * Gets the coverage value.
     *
     * @return the coverage value
     */
    public double getCoverage() {
        return coverage;
    }

    /**
     * Sets the coverage value.
     *
     * @param coverage the coverage value to set
     */
    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    /**
     * Gets the reviewer id.
     *
     * @return the reviewer id
     */
    public long getReviewerId() {
        return reviewerId;
    }

    /**
     * Sets the reviewer id.
     *
     * @param reviewerId the id to set
     * @throws IllegalArgumentException if id is negative
     */
    public void setReviewerId(long reviewerId) {
        Helper.checkLongPositive(reviewerId, "reviewerId");
        this.reviewerId = reviewerId;
    }

    /**
     * Gets the competition type id.
     *
     * @return the competition type id
     */
    public int getCompetitionTypeId() {
        return competitionTypeId;
    }

    /**
     * Sets the competition type id.
     *
     * @param competitionTypeId the competition type id to set
     * @throws IllegalArgumentException if id is negative
     */
    public void setCompetitionTypeId(int competitionTypeId) {
        Helper.checkLongPositive(competitionTypeId, "competitionTypeId");
        this.competitionTypeId = competitionTypeId;
    }

    /**
     * Gets the project id.
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId the project id to set
     * @throws IllegalArgumentException if projectId is negative
     */
    public void setProjectId(long projectId) {
        Helper.checkLongPositive(projectId, "projectId");
        this.projectId = projectId;
    }

    /**
     * Gets the statistics type.
     *
     * @return the statistics type
     */
    public StatisticsType getStatisticsType() {
        return statisticsType;
    }

    /**
     * Sets the statistics type.
     *
     * @param statisticsType the statistics type to set
     * @throws IllegalArgumentException if statisticsType is null
     */
    public void setStatisticsType(StatisticsType statisticsType) {
        Helper.checkNull(statisticsType, "statisticsType");
        this.statisticsType = statisticsType;
    }

    /**
     * Gets the eligibility points.
     *
     * @return the eligibility points
     */
    public double getEligibilityPoints() {
        return eligibilityPoints;
    }

    /**
     * Sets the eligibility points.
     *
     * @param points the eligibility points to set
     */
    public void setEligibilityPoints(double points) {
        this.eligibilityPoints = points;
    }

    /**
     * Gets the timeline reliability.
     *
     * @return the timeline reliability
     */
    public double getTimelineReliability() {
        return timelineReliability;
    }

    /**
     * Sets the timeline reliability.
     *
     * @param reliability the eligibility points to set
     */
    public void setTimelineReliability(double reliability) {
        this.timelineReliability = reliability;
    }

    /**
     * Gets the total evaluation coefficient.
     *
     * @return the total evaluation coefficient
     */
    public double getTotalEvaluationCoefficient() {
        return totalEvaluationCoefficient;
    }

    /**
     * Sets the total evaluation coefficient.
     *
     * @param coefficient the total evaluation coefficient value to set
     */
    public void setTotalEvaluationCoefficient(double coefficient) {
        this.totalEvaluationCoefficient = coefficient;
    }
}
