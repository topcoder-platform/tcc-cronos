/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.cronos.onlinereview.external.ExternalUser;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.service.RegistrationInfo;
import java.io.Serializable;

/**
 * <p>
 * This class is a DTO that wraps the passed registration data so it can be
 * passed as one object to the validators. This class implements
 * java.io.Serializable.
 * </p>
 * <p>
 * This entity follows java bean conventions for defining setters and getters
 * for these properties, as well as the minimum empty constructor. A full
 * constructor is also provided for convenient usage.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ValidationInfo implements Serializable {

    /**
     * <p>
     * Represents the registration information of the user.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed
     * with the getter.
     * </p>
     * <p>
     * Once set, it will never be null.
     * </p>
     *
     */
    private RegistrationInfo registration;

    /**
     * <p>
     * Represents the detailed external information about the user.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed
     * with the getter.
     * </p>
     * <p>
     * Once set, it will never be null.
     * </p>
     *
     */
    private ExternalUser user;

    /**
     * <p>
     * Represents the details project information.
     * </p>
     * <p>
     * This value can be set in the constructor or in the setter, and accessed
     * with the getter.
     * </p>
     * <p>
     * Once set, it will never be null.
     * </p>
     *
     */
    private FullProjectData project;

    /**
     * Default constructor. Does nothing.
     *
     */
    public ValidationInfo() {
    }

    /**
     * Full constructor. This convenience constructor allows for setting all
     * values in one go.
     *
     *
     * @param registration
     *            the registration information of the user.
     * @param user
     *            the detailed external information about the user
     * @param project
     *            the details project information
     * @throws IllegalArgumentException
     *             If any parameter is null
     */
    public ValidationInfo(RegistrationInfo registration, ExternalUser user,
            FullProjectData project) {
        setRegistration(registration);
        setUser(user);
        setProject(project);
    }

    /**
     * Gets the registration field value.
     *
     * @return The registration field value
     */
    public RegistrationInfo getRegistration() {
        return registration;
    }

    /**
     * Sets the registration field value. May be null if not yet set.
     *
     * @param registration
     *            The registration field value
     * @throws IllegalArgumentException
     *             If registration parameter is null
     */
    public void setRegistration(RegistrationInfo registration) {
        RegistrationValidationHelper.validateNotNull(registration,
                "registration");
        this.registration = registration;
    }

    /**
     * Gets the user field value.
     *
     * @return The user field value
     */
    public ExternalUser getUser() {
        return user;
    }

    /**
     * Sets the user field value. May be null if not yet set.
     *
     * @param user
     *            The user field value
     * @throws IllegalArgumentException
     *             If user parameter is null
     */
    public void setUser(ExternalUser user) {
        RegistrationValidationHelper.validateNotNull(user, "user");
        this.user = user;
    }

    /**
     * Gets the project field value.
     *
     * @return The project field value
     */
    public FullProjectData getProject() {
        return project;
    }

    /**
     * Sets the project field value. May be null if not yet set.
     *
     * @param project
     *            The project field value
     * @throws IllegalArgumentException
     *             If project parameter is null
     */
    public void setProject(FullProjectData project) {
        RegistrationValidationHelper.validateNotNull(project, "project");
        this.project = project;
    }
}
