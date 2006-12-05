
package com.orpheus.administration.persistence;

/**
 * Extends AdministrationException. This exception is the base exception for persistence operations in this component. As such, it and its three subclasses are thrown by the ejbXXX method in the clients, the business methods in the EJBs, and the DAOs. In effect, the client helper methods, for messages, and EJB business methods act as a pass-though for these exceptions.
 * <p></p>
 * 
 */
public class PersistenceException extends com.orpheus.administration.AdministrationException {

/**
 * <p>Creates new exception with error message</p>
 * 
 * 
 * @param msg error message
 */
    public  PersistenceException(String msg) {        
        super(msg);
    } 

/**
 * <p>Creates new exception with error message and cause of error</p>
 * 
 * 
 * @param msg error message
 * @param cause cause of error
 */
    public  PersistenceException(String msg, Throwable cause) {        
        super(msg,cause);// your code here
    } 
 }
