/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>This is a specific exception for situations when id generation might fail. It could happen that all ids have
 * been exhausted (or any other failure cases that may occur in IDGenerator) which would then propagate to the caller.
 * It could also be some issue with overflow.</p>
 * <p>Thread Safety: This class is thread-safe since it's immutable.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class IdGenerationException extends BaseException {
    /**
     * <p>Creates a new exception with passed error message.</p>
     *
     * @param msg error message
     */
    public IdGenerationException(String msg) {
        super(msg);
    }

    /**
     * <p>Creates a new exception with this error message and cause of error.</p>
     *
     * @param msg error message
     * @param cause the cause of the error
     */
    public IdGenerationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
