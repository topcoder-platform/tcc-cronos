/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception extends the <code>DigitalRunContestManagerException</code>, and it is thrown from the
 * <code>DigitalRunContestManager</code> interface and its implementations if the entity cannot be found.
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
public class EntityNotFoundException extends DigitalRunContestManagerException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 8105871786997009086L;

    /**
     * <p>
     * Message-only constructor with the given message.
     * </p>
     *
     * @param message Exception message.
     */
    public EntityNotFoundException(String message) {
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
    public EntityNotFoundException(String message, Throwable cause) {
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
    public EntityNotFoundException(String message, ExceptionData data) {
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
    public EntityNotFoundException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
