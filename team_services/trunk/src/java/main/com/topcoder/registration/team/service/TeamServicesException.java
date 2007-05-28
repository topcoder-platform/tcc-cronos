/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service;

import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * <p>
 * This class extends <code>BaseRuntimeException</code>. It is used by
 * <code>TeamServicesImpl#getTeamMembers</code> and
 * <code>TeamServicesImpl#getTeamPositionDetails</code> to wrap
 * <code>ResourcePersistenceException</code> thrown by <code>ResourceManager</code>.
 * </p>
 * <p>
 * Thread safety: This class is thread safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TeamServicesException extends BaseRuntimeException {

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     * @param msg
     *            the error message
     */
    public TeamServicesException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates a new exception instance with this error message and cause of error.
     * </p>
     * @param msg
     *            the error message
     * @param cause
     *            cause of error
     */
    public TeamServicesException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
