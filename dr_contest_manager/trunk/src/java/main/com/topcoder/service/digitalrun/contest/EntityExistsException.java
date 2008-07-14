/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception extends the <code>DigitalRunContestManagerException</code>, and it is thrown from the
 * <code>DigitalRunContestManager</code> interface and its implementations if the entity already exists
 * in such situations as creation.
 * </p>
 *
 * <p>
 * NOTE: It is marked with "@ApplicationException" annotation.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class EntityExistsException extends DigitalRunContestManagerException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -2076375381051915979L;

    /**
     * <p>
     * Message-only constructor with the given message.
     * </p>
     *
     * @param message Exception message.
     */
    public EntityExistsException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor which takes a message and the additional data to attach to the critical exception.
     * </p>
     *
     * @param message Exception message.
     * @param cause Inner exception cause.
     */
    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Constructor which takes a message and a cause detailing why the critical exception occurs.
     * </p>
     *
     * @param message Exception message.
     * @param data The additional data to attach to the exception.
     */
    public EntityExistsException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Constructor which takes a message and a cause, as well as
     * the additional data to attach to the critical exception.
     * </p>
     *
     * @param message Exception message.
     * @param cause Inner exception cause.
     * @param data The additional data to attach to the exception.
     */
    public EntityExistsException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
