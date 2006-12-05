package com.orpheus.administration.persistence;

/**
 * Extends AdministrationException. This exception is thrown by the constructors of all custom classes in this design that require configuration. The classes that use this exception include: OrpheusMessageDataStore (and its descendants), OrpheusMessengerPlugin (and its descendants), SQLServerAdminDataDAO, SQLServerMessageDAO, and DAOFactory. It is thrown if there is an error during the construction of these objects.
 * <p></p>
 * 
 */
public class InstantiationException extends
        com.orpheus.administration.AdministrationException {

    /**
     * <p>Creates new exception with error message</p>
     * 
     * 
     * @param msg error message
     */
    public InstantiationException(String msg) {
        super(msg);
    }

    /**
     * <p>Creates new exception with error message and cause of error</p>
     * 
     * 
     * @param msg error message
     * @param cause cause of error
     */
    public InstantiationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
