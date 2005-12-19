/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.buildutility;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception extends <code>BaseException</code> and is the base class for all exceptions thrown in this component.
 * </p>
 *
 * @see com.topcoder.buildutility.GenerationProcessException
 * @see com.topcoder.buildutility.GeneratorCreationException
 * @author dmks, marijnk
 * @version 1.0
 * @copyright &copy; 2005, TopCoder, Inc. All rights reserved.
 */
public class BuildScriptGeneratorException extends BaseException {
    /**
     * <p>
     * Construct the exception with an error message but no inner cause.
     * </p>
     *
     * @param message the error message
     */
    public BuildScriptGeneratorException(String message) {
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
    public BuildScriptGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
