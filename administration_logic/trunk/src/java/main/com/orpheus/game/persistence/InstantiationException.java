/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;


/**
 * Extends GameDataException. This exception is thrown by the constructors of most custom classes in this design
 * that require configuration. The classes that use this exception include: LocalCustomDownloadSource,
 * RemoteCustomDownloadSource, and SQLServergameDataDAO.
 * It is thrown if there is an error during the construction of these objects.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InstantiationException extends com.orpheus.game.GameDataException {
    /**
     * <p>Creates new exception with error message.</p>
     *
     * @param msg error message
     */
    public InstantiationException(String msg) {
        // your code here
    }

    /**
     * <p>Creates new exception with error message and cause of error.</p>
     *
     * @param msg error message
     * @param cause cause of error
     */
    public InstantiationException(String msg, Throwable cause) {
        // your code here
    }
}
