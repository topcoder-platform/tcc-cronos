/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.orpheus.game.GameDataException;


/**
 * <p>
 * Extends GameDataException. This exception is thrown by the constructors of most custom classes in this design that
 * require configuration.
 * </p>
 *
 * <p>
 * The classes that use this exception include: LocalCustomDownloadSource, RemoteCustomDownloadSource, and
 * SQLServergameDataDAO. It is thrown if there is an error during the construction of these objects.
 * </p>
 *
 * @author argolite, waits
 * @version 1.0
 */
public class InstantiationException extends GameDataException {
    /**
     * <p>
     * Creates new exception with error message.
     * </p>
     *
     * @param msg error message
     */
    public InstantiationException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates new exception with error message and cause of error.
     * </p>
     *
     * @param msg error message
     * @param cause cause of error
     */
    public InstantiationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
