/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

/**
 * <p>
 * This is an entity class that contains information about the completion status of a task. The task name will be
 * provided along with the flag whether that task has been completed.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TaskCompletionStatus {

    /**
     * <p>
     * Represents the name of the task.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * It may have any value, but the ProfileCompletenessRetriever is expected to set it to a non-null/empty value.
     * Fully mutable.
     * </p>
     */
    private String taskName;

    /**
     * <p>
     * Represents the status of the task.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Either true or false. Fully mutable.
     * </p>
     */
    private boolean completed;

    /**
     * <p>
     * Creates an instance of TaskCompletionStatus.
     * </p>
     */
    public TaskCompletionStatus() {
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
    public boolean isCompleted() {
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