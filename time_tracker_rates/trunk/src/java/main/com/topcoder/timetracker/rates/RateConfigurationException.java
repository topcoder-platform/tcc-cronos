/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>Exception used by the Rates component whenever there is a problem while initializing all the objects based on
 * configuration parameters. For example, if required parameters are missing or invalid, then this exception is
 * thrown. It is also recommended any implementation of RatePersistence throws this exception.</p>
 *  <p>Thread-Safety: This class is thread safe since it's not mutable.</p>
 *
 * @author AleaActaEst, sql_lall, TCSDEVELOPER
 * @version 3.2
 */
public class RateConfigurationException extends BaseException {
    /**
     * String-only constructor for the exception, taking a message detailing why the exception occurred.
     * @param message Descriptive reason the error occurred.
     */
    public RateConfigurationException(String message) {
        super(message);
    }

    /**
     * Cause Constructor for the exception, taking a message detailing why the exception occurred as well as the
     * initial problem causing this to be raised.
     * @param message Descriptive reason the error occurred.
     * @param cause Throwable cause which triggered this exception.
     */
    public RateConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
