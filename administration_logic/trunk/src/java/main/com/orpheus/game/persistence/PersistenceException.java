/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;


/**
 * Extends GameDataException. This exception is the base exception for persistence operations in this component.
 * As such, it and its three subclasses are thrown by the ejbXXX method in the clients,
 * the business methods in the EJBs, and the DAOs. In effect,
 * the client helper method and EJB business methods act as a pass-though for these exceptions.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceException extends com.orpheus.game.GameDataException {
    /**
     * <p>Creates new exception with error message.</p>
     *
     * @param msg error message
     */
    public PersistenceException(String msg) {
        // your code here
    }

    /**
     * <p>Creates new exception with error message and cause of error.</p>
     *
     * @param msg error message
     * @param cause cause of error
     */
    public PersistenceException(String msg, Throwable cause) {
        // your code here
    }
}
