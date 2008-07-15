/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import javax.ejb.ApplicationException;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This is the top level general Digital Run Contest Manager exception which singals issues with the
 * processing request, it should be extended by other more specific exceptions for processing.
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
public class DigitalRunContestManagerException extends BaseCriticalException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -8815632291762183742L;

    /**
     * <p>
     * Message-only constructor with the given message.
     * </p>
     *
     * @param message Exception message.
     */
    public DigitalRunContestManagerException(String message) {
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
    public DigitalRunContestManagerException(String message, Throwable cause) {
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
    public DigitalRunContestManagerException(String message, ExceptionData data) {
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
    public DigitalRunContestManagerException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}

