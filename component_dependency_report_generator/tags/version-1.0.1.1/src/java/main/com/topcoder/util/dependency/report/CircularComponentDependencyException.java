/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import com.topcoder.util.errorhandling.ExceptionData;


/**
 * <p>
 * This exception is thrown by implementations of <code>DependencyReportGenerator</code>
 * when circular component dependency is found.
 * </p>
 *
 * <p>
 * This error can occur only when the user tries to retrieve indirect dependencies of a component.
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
public class CircularComponentDependencyException extends DependencyReportGenerationException {

    /**
     * <p>
     * Creates a new instance of this exception with error message.
     * </p>
     *
     * @param message The error message.
     */
    public CircularComponentDependencyException(String message) {
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
    public CircularComponentDependencyException(String message, Throwable cause) {
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
    public CircularComponentDependencyException(String message, ExceptionData data) {
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
    public CircularComponentDependencyException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
