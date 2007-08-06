/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.framework.game.completion;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>A custom checked exception to be used for reporting on unrecoverable errors occurring in context of game
 * completion time evaluation. In most cases this exception will wrap the original exceptions which have caused the
 * error to occur.</p>
 *
 * @author isv
 * @version 1.0
 */
public class GameCompletionException extends BaseException {

    /**
     * <p>Constructs new <code>GameCompletionException</code> instance. This implementation does nothing.</p>
     */
    public GameCompletionException() {
    }

    /**
     * <p>Constructs new <code>GameCompletionException</code> instance with specified message.</p>
     *
     * @param message a <code>String</code> providing the details for the error.
     */
    public GameCompletionException(String message) {
        super(message);
    }

    /**
     * <p>Constructs new <code>GameCompletionException</code> instance with specified message and original cause of
     * error. </p>
     *
     * @param message a <code>String</code> providing the details for the error.
     * @param throwable a <code>Throwable</code> providing the original cause of error.
     */
    public GameCompletionException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * <p>Constructs new <code>GameCompletionException</code> instance with specified cause of error.</p>
     *
     * @param throwable a <code>Throwable</code> providing the original cause of error.
     */
    public GameCompletionException(Throwable throwable) {
        super(throwable);
    }
}
