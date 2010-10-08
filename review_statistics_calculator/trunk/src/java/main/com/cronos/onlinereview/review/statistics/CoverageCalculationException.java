/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by ReviewerStatisticsCalculator and implementations of CoverageCalculator when some error
 * occurs while performing the calculation of coverage coefficients.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CoverageCalculationException extends StatisticsCalculatorException {
    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message the detailed error message of this exception
     */
    public CoverageCalculationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public CoverageCalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of this exception with the given message and exception data.
     *
     * Parameters: message - the detailed error message of this exception data - the exception data
     *
     * @param data the exception data
     * @param message the detailed error message of this exception
     */
    public CoverageCalculationException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates a new instance of this exception with the given message, cause and exception data. * @param data the
     * exception data
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public CoverageCalculationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
