/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;


/**
 * Extends PersistenceException. This is a specific persistence exception when inserting a record with a primary
 * id that is not valid, which excludes issues such as duplicate ids.
 * The DAO and the associated EJB and client helper methods use it.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InvalidEntryException
    extends com.orpheus.game.persistence.PersistenceException {
    /**
     * <p>
     * Represents the invalid identifier.
     * It is set in the constructor, may be null and can be generally any value, and will not change.
     * </p>
     *
     */
    private final Object identifier;

    /**
     * <p>Creates new exception with error message and the invalid identifier.</p>
     *
     * @param msg error message
     * @param identifier the invalid identifier
     */
    public InvalidEntryException(String msg, Object identifier) {
        super(msg);
        this.identifier = identifier;
    }

    /**
     * Gets the invalid identifier, if one was given in the constructor.
     *
     * @return the invalid identifier
     */
    public Object getIdentifier() {
        return identifier;
    }
}
