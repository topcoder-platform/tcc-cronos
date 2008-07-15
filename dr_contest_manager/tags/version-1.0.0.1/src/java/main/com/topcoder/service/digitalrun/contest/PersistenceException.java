/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This is a general Persistence Exception which will specify that there were issues with actual persistence
 * such as connection issues or SQL issues (if applicable) or JPA issues (if applicable).
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceException extends BaseCriticalException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 3211783700538079374L;

    /**
     * <p>
     * Message-only constructor with the given message.
     * </p>
     *
     * @param message Exception message.
     */
    public PersistenceException(String message) {
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
    public PersistenceException(String message, Throwable cause) {
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
    public PersistenceException(String message, ExceptionData data) {
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
    public PersistenceException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
