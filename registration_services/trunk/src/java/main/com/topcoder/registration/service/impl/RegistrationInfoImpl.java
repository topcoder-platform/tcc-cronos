/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.topcoder.registration.service.RegistrationInfo;

/**
 * <p>
 * Simple implementation of the <code>RegistrationInfo</code> interface. Implements all methods in
 * that interface, and provides a default constructor if the user wants to set all values via the
 * setters, and one full constructor to set these values in one go.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread-safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class RegistrationInfoImpl implements RegistrationInfo {

    /**
     * <p>
     * Represents the ID of the project that a resource is part of. This value can be set in the
     * full constructor or in the bean setter method, and retrieved in the bean getter method. Once
     * set, the value will never be negative.
     * </p>
     */
    private long projectId = -1;

    /**
     * <p>
     * Represents the ID of the user that a resource represents. This value can be set in the full
     * constructor or in the bean setter method, and retrieved in the bean getter method. Once set,
     * the value will never be negative.
     * </p>
     */
    private long userId = -1;

    /**
     * <p>
     * Represents the ID of the role that a resource is to assume. This value can be set in the full
     * constructor or in the bean setter method, and retrieved in the bean getter method. Once set,
     * the value will never be negative.
     * </p>
     */
    private long roleId = -1;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public RegistrationInfoImpl() {
        // empty
    }

    /**
     * <p>
     * Constucts an instance of this class with full parameters.
     * </p>
     * <p>
     * This convenience constructor allows for setting all values in one go. All must be
     * non-negative.
     * </p>
     * @param projectId
     *            a non-negative ID of the project that a resource is part of
     * @param userId
     *            a non-negative ID of the user that a resource represents
     * @param roleId
     *            a non-negative ID of the role that a resource is to assume
     * @throws IllegalArgumentException
     *             if any parameter is negative
     */
    public RegistrationInfoImpl(long projectId, long userId, long roleId) {
        setProjectId(projectId);
        setUserId(userId);
        setRoleId(roleId);
    }

    /**
     * <p>
     * Gets the project ID. Can be negative if not yet set.
     * </p>
     * @return the projectId
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>
     * Sets the project ID. Must not be negative.
     * </p>
     * @param projectId
     *            the project ID
     * @throws IllegalArgumentException
     *             if projectId is negative
     */
    public void setProjectId(long projectId) {
        Util.checkIDNotNegative(projectId, "projectId");
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the user ID. Can be negative if not yet set.
     * </p>
     * @return The userId
     */
    public long getUserId() {
        return this.userId;
    }

    /**
     * <p>
     * Sets the user ID. Must not be negative.
     * </p>
     * @param userId
     *            the user ID
     * @throws IllegalArgumentException
     *             if userId is negative
     */
    public void setUserId(long userId) {
        Util.checkIDNotNegative(userId, "userId");
        this.userId = userId;
    }

    /**
     * <p>
     * Gets the role ID. Can be negative if not yet set.
     * </p>
     * @return the roleId
     */
    public long getRoleId() {
        return this.roleId;
    }

    /**
     * <p>
     * Sets the role ID. Must not be negative.
     * </p>
     * @param roleId
     *            the role ID
     * @throws IllegalArgumentException
     *             if roleId is negative
     */
    public void setRoleId(long roleId) {
        Util.checkIDNotNegative(roleId, "roleId");
        this.roleId = roleId;
    }
}
