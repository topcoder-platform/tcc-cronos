/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

/**
 * <p>
 * This class extends <code>RegistrationServiceException</code>. Called by
 * <code>RegistrationServices.removeRegistration</code> method if the passed <code>roleId</code>
 * does not correspond to the <code>roleId</code> that the existing registration (and only if
 * there is one) has.
 * </p>
 * <p>
 * <b>Thread safety:</b> This class is thread safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class InvalidRoleException extends RegistrationServiceException {

    /**
     * <p>
     * Represents the <code>roleId</code> that is not valid. It is set in the constructor and will
     * not change.
     * </p>
     */
    private final long roleId;

    /**
     * <p>
     * Creates new exception instance with an error message and the invalid roleId.
     * </p>
     * @param msg
     *            the error message
     * @param roleId
     *            the roleId that is not valid
     */
    public InvalidRoleException(String msg, long roleId) {
        super(msg);
        this.roleId = roleId;
    }

    /**
     * <p>
     * Gets the <code>roleId</code> that is not valid.
     * </p>
     * @return the roleId that is not valid
     */
    public long getRoleId() {
        return roleId;
    }
}
