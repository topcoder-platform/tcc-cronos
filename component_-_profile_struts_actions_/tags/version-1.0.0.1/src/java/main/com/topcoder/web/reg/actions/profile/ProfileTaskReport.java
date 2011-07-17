/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

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
public class ProfileTaskReport {

    /**
     * <p>
     * This is the name of the task.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value, but is expected to be set to a non-null/empty value. Fully mutable.
     * </p>
     */
    private String taskName;

    /**
     * <p>
     * Represents the number of fields that have been completed from the set of fields that have been requested to be
     * completed for this task.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value, but is expected to be a positive number less than or equal to totalFieldCount. Fully
     * mutable.
     * </p>
     */
    private int completedFieldCount = 0;

    /**
     * <p>
     * Represents the total number of fields that have been requested to be completed for this task.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value, but is expected to be a positive number more than or equal to completedFieldCount. Fully
     * mutable.
     * </p>
     */
    private int totalFieldCount = 0;

    /**
     * <p>
     * Represents the flag whether all fields that need to be provided are indeed provided.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value. Fully mutable.
     * </p>
     */
    private boolean completed = false;

    /**
     * <p>
     * Creates an instance of .
     * </p>
     */
    public ProfileTaskReport() {
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param taskName the namesake field instance value to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public int getCompletedFieldCount() {
        return completedFieldCount;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param completedFieldCount the namesake field instance value to set
     */
    public void setCompletedFieldCount(int completedFieldCount) {
        this.completedFieldCount = completedFieldCount;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public int getTotalFieldCount() {
        return totalFieldCount;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param totalFieldCount the namesake field instance value to set
     */
    public void setTotalFieldCount(int totalFieldCount) {
        this.totalFieldCount = totalFieldCount;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public boolean getCompleted() {
        return completed;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param completed the namesake field instance value to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}