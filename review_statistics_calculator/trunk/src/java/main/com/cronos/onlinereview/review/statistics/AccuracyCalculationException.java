/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by ReviewerStatisticsCalculator and implementations of AccuracyCalculator when some error
 * occurs while performing the calculation of accuracy coefficients.
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
public class AccuracyCalculationException extends StatisticsCalculatorException {
    /**
     * Creates a new instance of this exception with the given message.
     *
     * @param message the detailed error message of this exception
     */
    public AccuracyCalculationException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this exception with the given message and cause.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public AccuracyCalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of this exception with the given message and exception data.
     *
     * @param data the exception data
     * @param message the detailed error message of this exception
     */
    public AccuracyCalculationException(String message, ExceptionData data) {
        super(message, data);

    }

    /**
     * Creates a new instance of this exception with the given message, cause and exception data.
     *
     * @param data the exception data
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     */
    public AccuracyCalculationException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
