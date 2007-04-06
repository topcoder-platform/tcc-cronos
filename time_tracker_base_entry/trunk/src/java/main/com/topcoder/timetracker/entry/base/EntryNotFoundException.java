/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

/**
 * <p>This is a specific persistence exception (it is extended from it) which signals that an entry that was
 * expected to be found was not (in persistence). Users of this exception should associate the id of the entity that
 * caused it through the appropriate constructor.</p>
 *  <p>Thread Safety: This class is thread-safe since it's immutable.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class EntryNotFoundException extends PersistenceException {
    /**
     * <p><strong>Purpose:</strong></p>
     *  <p>This id identifies the entity that caused this exception. In our case it would be the id of an
     * entity that we were expecting to exists (like in an update or fetch) BUT found out that it doesn't actually
     * exist. It is initialized through a constructor and accessed by caller through a getter.</p>
     */
    private final long entityId;

    /**
     * Creates a new exception with passed error message.
     *
     * @param msg error message
     */
    public EntryNotFoundException(String msg) {
        this(msg, -1);
    }

    /**
     * Creates a new exception with this error message and cause of error.
     *
     * @param msg error message
     * @param cause the cause of the error
     */
    public EntryNotFoundException(String msg, Throwable cause) {
        this(msg, cause, -1);
    }

    /**
     * <p>Creates a new exception with this error message and entity id.</p>
     *
     *
     * @param msg error message
     * @param entityId id of the entity that is missing
     */
    public EntryNotFoundException(String msg, long entityId) {
        super(msg);
        this.entityId = entityId;
    }

    /**
     * <p>Creates a new exception with this error message, cause of error, and entity id.</p>
     *
     *
     * @param msg error message
     * @param cause inner, cause exception
     * @param entityId id of the entity that is missing
     */
    public EntryNotFoundException(String msg, Throwable cause, long entityId) {
        super(msg, cause);
        this.entityId = entityId;
    }

    /**
     *  <p>This is used to get the associated entity id for an entity that caused this exception. In this case
     * it would be an entry that we were updating/deleting/fetching but did not find in the database.</p>
     *
     * @return entity id if the entry that cause this exception
     */
    public long getEntityId() {
        return entityId;
    }
}
