/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This runtime exception extends the <code>BaseRuntimeException</code>, and it is thrown from the
 * <code>DigitalRunContestManagerBean</code> class' <code>initialize()</code> method if the configured
 * value(s) is/are invalid, it is also used to wrap the underlying exceptions when loading the configured
 * values.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class ContestManagerConfigurationException extends BaseRuntimeException {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -2729577208952890388L;

    /**
     * <p>
     * Message-only constructor with the given message.
     * </p>
     *
     * @param message Exception message.
     */
    public ContestManagerConfigurationException(String message) {
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
    public ContestManagerConfigurationException(String message, Throwable cause) {
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
    public ContestManagerConfigurationException(String message, ExceptionData data) {
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
    public ContestManagerConfigurationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
