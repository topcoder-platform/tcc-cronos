/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This exception is thrown by implementations of <code>DependencyReportGenerator</code>
 * when component with the given ID cannot be found in the input dependencies entry list.
 * Component ID includes information about component language, name and version.
 * </p>
 *
 * <p>
 * This error can occur only when <code>generate()</code> method is used, not <code>generateAll()</code>.
 * </p>
 *
 * <p>
 *     <strong>Thread Safety:</strong>
 *     This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ComponentIdNotFoundException extends DependencyReportGenerationException {

    /**
     * <p>
     * Creates a new instance of this exception with error message.
     * </p>
     *
     * @param message The error message.
     */
    public ComponentIdNotFoundException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this exception with error message and inner cause.
     * </p>
     *
     * @param message The error message.
     * @param cause The cause of this exception.
     */
    public ComponentIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * Creates a new instance of this exception with error message and exception data.
     * </p>
     *
     * @param data The exception data.
     * @param message The error message.
     */
    public ComponentIdNotFoundException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * <p>
     * Creates a new instance of this exception with error message, inner cause and exception data.
     * </p>
     *
     * @param message The error message.
     * @param cause The cause of this exception.
     * @param data The exception data.
     */
    public ComponentIdNotFoundException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
