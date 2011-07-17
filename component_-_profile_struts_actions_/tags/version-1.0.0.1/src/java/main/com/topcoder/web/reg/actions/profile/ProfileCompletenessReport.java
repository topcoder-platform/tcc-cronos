/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import java.util.List;

/**
 * <p>
 * This is an entity class that contains information about the completion status of the registration for a user. It
 * provides the info about the percentage of completion and the completion status of individual tasks associated with
 * the completion.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ProfileCompletenessReport {

    /**
     * <p>
     * Represents the percentage of completion for a profile information registration.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value, but the ProfileCompletenessRetriever is expected to set it to a value of [0,100]. Fully
     * mutable.
     * </p>
     */
    private int completionPercentage;

    /**
     * <p>
     * Represents the list of tasks involved in the completion analysis.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value, but the ProfileCompletenessRetriever is expected to set it to a non null value with at
     * least 1 status. The ProfileCompletenessRetriever will set 3 of them. Fully mutable.
     * </p>
     */
    private List<TaskCompletionStatus> taskCompletionStatuses;

    /**
     * <p>
     * Creates an instance of ProfileCompletenessReport.
     * </p>
     */
    public ProfileCompletenessReport() {
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public int getCompletionPercentage() {
        return completionPercentage;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param completionPercentage the namesake field instance value to set
     */
    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public List<TaskCompletionStatus> getTaskCompletionStatuses() {
        return taskCompletionStatuses;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param taskCompletionStatuses the namesake field instance value to set
     */
    public void setTaskCompletionStatuses(List<TaskCompletionStatus> taskCompletionStatuses) {
        this.taskCompletionStatuses = taskCompletionStatuses;
    }
}