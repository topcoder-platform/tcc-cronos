/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.reg.ProfileActionConfigurationException;

/**
 * <p>
 * This is a base class for all ProfileTaskCheckers. It provides the task name field and initialization check that it
 * is provided.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseProfileTaskChecker implements ProfileTaskChecker {

    /**
     * <p>
     * This is the name of the task.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * On initialization check, must be not null/empty. This field will be injected by the container (expected to be
     * done only once), and is not expected to change afterward.
     * </p>
     */
    private String taskName;

    /**
     * <p>
     * Creates an instance of BaseProfileTaskChecker.
     * </p>
     */
    protected BaseProfileTaskChecker() {
    }

    /**
     * <p>
     * Checks that all required values have been injected by the system right after construction and injection.
     * </p>
     * <p>
     * This is used to obviate the need to check these values each time in the getTaskReport() method.
     * </p>
     * @throws ProfileActionConfigurationException - If any required value has not been injected into this class
     */
    @PostConstruct
    protected void checkInitialization() {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(taskName, "taskName",
                ProfileActionConfigurationException.class);
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
}