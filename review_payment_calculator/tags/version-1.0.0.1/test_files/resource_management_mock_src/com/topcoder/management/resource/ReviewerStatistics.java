package com.topcoder.management.resource;

/**
 * This is the entity class that represents the reviewer statistics, it implements ResourceStatistics interface and extends AuditableResourceStructure that provides auditing capabilities.
 * 
 * Thread Safety: it's mutable and not thread safe
 */
public class ReviewerStatistics extends AuditableResourceStructure implements ResourceStatistics {
    /**
     * Represents id of reviewer statistics.
     * 
     * It's initialized in ctor to 0, cannot be negative, has getter and setter.
     */
    private long id;

    /**
     * Represents accuracy of reviewer statistics.
     * 
     * It's initialized in ctor to 0, can be any value, has getter and setter.
     */
    private double accuracy;

    /**
     * Represents coverage of reviewer statistics.
     * 
     * It's initialized in ctor to 0, can be any value, has getter and setter.
     */
    private double coverage;

    /**
     * Represents timeline reliability of reviewer statistics.
     * 
     * It's initialized in ctor to 0, can be any value, has getter and setter.
     */
    private double timelineReliability;

    /**
     * Represents total evaluation coefficient of reviewer statistics, a coefficient computed from the other stored coefficient designed to represent the reviewer performance in the given project and to control the relative amount of payment that will be distributed to the reviewer.
     * 
     * It's initialized in ctor to 0, can be any value, has getter and setter.
     */
    private double totalEvaluationCoefficient;

    /**
     * Represents id of the reviewer. The id of the reviewer for which the statistics are maintained
     * 
     * It's initialized in ctor to 0, cannot be negative, has getter and setter.
     */
    private long reviewerId;

    /**
     * Represents id of the competition type.  The competition type for which the statistics is maintained (eg design, architecture etc). Lookup table.
     * 
     * It's initialized in ctor to 0, cannot be negative, has getter and setter.
     */
    private long competitionTypeId;

    /**
     * Represents id of the project. The last project for the average statistics and the current project for the history statistics
     * 
     * It's initialized in ctor to 0, cannot be negative, has getter and setter.
     */
    private long projectId;

    /**
     * Represents the statistics type.  if the statistic is a history or average statistic (this property is not persisted)
     * 
     * It's initialized in ctor to null, can be any value, has getter and setter.
     */
    private StatisticsType statisticsType;

    /**
     * Represents the eligibility points. the number of eligibility points the reviewer has for the given competition type. It's stored per competition type.
     * 
     * It's initialized in ctor to 0, can be any value, has getter and setter.
     */
    private double eligibilityPoints;

    /**
     * Creates an instance of this class.
     * 
     * ###Params
     * None
     * 
     * ###Impl
     * Does nothing.
     * 
     * ###Exceptions
     * None
     */
    public ReviewerStatistics() {
    }

    /**
     * Gets the Id.
     * 
     * ###Params
     * return - the id
     * 
     * ###Impl
     * return this.id;
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * ###Params
     * id - the id to set, cannot be negative
     * 
     * ###Implthis.id=id;
     * 
     * ###Exceptions
     * IllegalArgumentException - if id is negative
     * @param id 
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the accuracy value.
     * 
     * ###Params
     * return - the accuracy value
     * 
     * ###Impl
     * return this.accuracy.
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the accuracy value.
     * 
     * ###Params
     * accuracy - the accuracy value to set, can be any value
     * 
     * ###Impl
     * this.accuracy = accuracy
     * 
     * ###Exceptions
     * None
     * @param accuracy 
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * Gets the coverage value.
     * 
     * ###Params
     * return - the coverage value
     * 
     * ###Impl
     * return this.coverage.
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public double getCoverage() {
        return coverage;
    }

    /**
     * Sets the coverage value.
     * 
     * ###Params
     * coverage - the coverage value to set, can be any value
     * 
     * ###Impl
     * this.coverage = coverage
     * 
     * ###Exceptions
     * None
     * @param coverage 
     */
    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    /**
     * Gets the reviewer id.
     * 
     * ###Params
     * return - the reviewer id
     * 
     * ###Impl
     * return this.reviewerId.
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public long getReviewerId() {
        return reviewerId;
    }

    /**
     * Sets the reviewer id.
     * 
     * ###Params
     * reviewerId - the id to set, cannot be negative
     * 
     * ###Impl
     * this.reviewerId=reviewerId;
     * 
     * ###Exceptions
     * IllegalArgumentException - if argument is negative
     * @param reviewerId 
     */
    public void setReviewerId(long reviewerId) {
        this.reviewerId = reviewerId;
    }

    /**
     * Gets the competition type id.
     * 
     * ###Params
     * return - the competition type id
     * 
     * ###Impl
     * return this.competitionTypeId.
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public long getCompetitionTypeId() {
        return competitionTypeId;
    }

    /**
     * Sets the competition type id.
     * 
     * ###Params
     * competitionTypeId - the competition type id to set, cannot be negative
     * 
     * ###Impl
     * this.competitionTypeId=id;
     * 
     * ###Exceptions
     * IllegalArgumentException - if argument is negative
     * @param id 
     */
    public void setCompetitionTypeId(long id) {
        this.competitionTypeId = id;
    }

    /**
     * Gets the project id.
     * 
     * ###Params
     * return - the project id
     * 
     * ###Impl
     * return this.projectId;
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     * 
     * ###Params
     * projectId - the project id to set, cannot be negative
     * 
     * ###Impl
     * this.projectId=projectId;
     * 
     * ###Exceptions
     * IllegalArgumentException - if argument is negative
     * @param projectId 
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the statistics type.
     * 
     * ###Params
     * return - the statistics type.
     * 
     * ###Impl
     * return this.statisticsType
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public StatisticsType getStatisticsType() {
        return statisticsType;
    }

    /**
     * Sets the statistics type.
     * 
     * ###Params
     * type - the statistics type to set, cannot be null
     * 
     * ###Impl
     * this.statisticsType=type;
     * 
     * ###Exceptions
     * IllegalArgumentException - if argument is null
     * @param type 
     */
    public void setStatisticsType(StatisticsType type) {
        this.statisticsType = type;
    }

    /**
     * Gets the eligibility points.
     * 
     * ###Params
     * return - the eligibility points
     * 
     * ###Impl
     * return this.eligibilityPoints.
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public double getEligibilityPoints() {
        return eligibilityPoints;
    }

    /**
     * Sets the eligibility points.
     * 
     * ###Params
     * points - the eligibility points to set, can be any value
     * 
     * ###Impl
     * this.eligibilityPoints=points;
     * 
     * ###Exceptions
     * None
     * @param points 
     */
    public void setEligibilityPoints(double points) {
        this.eligibilityPoints = points;
    }

    /**
     * Gets the timeline reliability.
     * 
     * ###Params
     * return - the timeline reliability
     * 
     * ###Impl
     * return this.timelineReliability.
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public double getTimelineReliability() {
        return timelineReliability;
    }

    /**
     * Sets the timeline reliability.
     * 
     * ###Params
     * points - the eligibility points to set, can be any value
     * 
     * ###Impl
     * this.eligibilityPoints=points;
     * 
     * ###Exceptions
     * None
     * @param reliability 
     */
    public void setTimelineReliability(double reliability) {
        this.timelineReliability = reliability;
    }

    /**
     * Gets the total evaluation coefficient.
     * 
     * ###Params
     * return - the total evaluation coefficient.
     * 
     * ###Impl
     * return this.totalEvaluationCoefficient.
     * 
     * ###Exceptions
     * None
     * @return 
     */
    public double getTotalEvaluationCoefficient() {
        return totalEvaluationCoefficient;
    }

    /**
     * Sets the total evaluation coefficient.
     * 
     * ###Params
     * coefficient - the total evaluation coefficient value to set, can be any value
     * 
     * ###Impl
     * this.totalEvaluationCoefficient=coefficient.
     * 
     * ###Exceptions
     * None
     * @param coefficient 
     */
    public void setTotalEvaluationCoefficient(double coefficient) {
        this.totalEvaluationCoefficient = coefficient;
    }
}

