/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;
import java.io.Serializable;
/**
 * <p>
 * The interface that defines the properties of a registration, which include a project, user, and
 * role ID. This information basically represents the role a user has in a project.
 * </p>
 * <p>
 * This interface follows java bean conventions for defining setters and getters for these
 * properties.
 * </p>
 * <p>
 * It has one implementation: RegistrationInfoImpl.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public interface RegistrationInfo extends Serializable {

    /**
     * <p>
     * Gets the project ID. Can be negative if not yet set.
     * </p>
     * @return the projectId
     */
    public long getProjectId();

    /**
     * <p>
     * Sets the project ID. Must not be negative.
     * </p>
     * @param projectId
     *            the project ID
     * @throws IllegalArgumentException
     *             if projectId is negative
     */
    public void setProjectId(long projectId);

    /**
     * <p>
     * Gets the user ID. Can be negative if not yet set.
     * </p>
     * @return the userId
     */
    public long getUserId();

    /**
     * <p>
     * Sets the user ID. Must not be negative.
     * </p>
     * @param userId
     *            the user ID
     * @throws IllegalArgumentException
     *             if userId is negative
     */
    public void setUserId(long userId);

    /**
     * <p>
     * Gets the role ID. Can be negative if not yet set.
     * </p>
     * @return the roleId
     */
    public long getRoleId();

    /**
     * <p>
     * Sets the role ID. Must not be negative.
     * </p>
     * @param roleId
     *            the role ID
     * @throws IllegalArgumentException
     *             if roleId is negative
     */
    public void setRoleId(long roleId);
}
