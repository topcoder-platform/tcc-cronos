/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.framework.bounce;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>A custom checked exception to be used for reporting on unrecoverable errors occurring in context of bounce point
 * calculation. In most cases this exception will wrap the original exceptions which have caused the error to occur.</p>
 *
 * @author isv
 * @version 1.0
 */
public class BouncePointException extends BaseException {

    /**
     * <p>Constructs new <code>BouncePointException</code> instance. This implementation does nothing.</p>
     */
    public BouncePointException() {
    }

    /**
     * <p>Constructs new <code>BouncePointException</code> instance with specified message.</p>
     *
     * @param message a <code>String</code> providing the details for the error.
     */
    public BouncePointException(String message) {
        super(message);
    }

    /**
     * <p>Constructs new <code>BouncePointException</code> instance with specified message and original cause of error.
     * </p>
     *
     * @param message a <code>String</code> providing the details for the error.
     * @param throwable a <code>Throwable</code> providing the original cause of error.
     */
    public BouncePointException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * <p>Constructs new <code>BouncePointException</code> instance with specified cause of error.</p>
     *
     * @param throwable a <code>Throwable</code> providing the original cause of error.
     */
    public BouncePointException(Throwable throwable) {
        super(throwable);
    }
}
