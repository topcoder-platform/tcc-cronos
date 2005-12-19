/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;


/**
 * <p>
 * This exception extends {@link BuildScriptGeneratorException} and will be thrown if a failure occurs while creating a
 * generator in {@link BuildScriptGeneratorFactory} instances.
 * </p>
 *
 * @author dmks, marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class GeneratorCreationException extends BuildScriptGeneratorException {
    /**
     * <p>
     * Construct the exception with an error message but no inner cause.
     * </p>
     *
     * @param message the error message
     */
    public GeneratorCreationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Construct the exception with an error message and an inner cause exception.
     * </p>
     *
     * @param message the error message
     * @param cause the inner cause exception
     */
    public GeneratorCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
