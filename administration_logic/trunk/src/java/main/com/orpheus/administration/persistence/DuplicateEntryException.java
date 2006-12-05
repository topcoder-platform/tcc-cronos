
package com.orpheus.administration.persistence;

/**
 * Extends PersistenceException. This is a specific persistence exception when inserting a record with a primary id that already exists. The DAOs and the associated EJBs and, for messages, client helper methods use it.
 * <p></p>
 * 
 */
public class DuplicateEntryException extends com.orpheus.administration.persistence.PersistenceException {

/**
 * <p>Represents the duplicate identifier. It is set in the constructor, will not be null (or empty if a String), and will not change.</p>
 * 
 */
    private final Object identifier;

/**
 * <p>Creates new exception with error message and the duplicate identifier</p>
 * 
 * 
 * @param msg error message
 * @param identifier the duplicate identifier
 */
    public  DuplicateEntryException(String msg, Object identifier) {        
        super(msg);
        this.identifier = identifier;
    } 

/**
 * Gets the duplicate identifier
 * 
 * 
 * @return the duplicate identifier
 */
    public Object getIdentifier() {        
        return identifier;
    } 
 }
