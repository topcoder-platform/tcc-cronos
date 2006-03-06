/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * FormatterException.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception will be thrown by ValidationOutputFormatter instances to indicate any problems in the
 * format() method which prevent correct formatting of the given validation output.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class FormatterException extends BaseException {

    /**
     * <p>
     * Creates an exception with the given parameter.
     * </p>
     *
     * @param details the exception details
     */
    public FormatterException(String details) {
        super(details);
    }

    /**
     * <p>
     * Creates an exception with the given parameters.
     * </p>
     *
     * @param details        the exception details
     * @param innerException the inner exception
     */
    public FormatterException(String details, Exception innerException) {
        super(details, innerException);
    }
}