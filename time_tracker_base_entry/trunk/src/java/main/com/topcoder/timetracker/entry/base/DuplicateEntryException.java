/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

/**
 * <p>This is a specific persistence exception (it is extended from it.) This would be thrown in the very rare
 * situation where an entry record is being persisted but the id (i.e. entry) already exists. Users of this exception
 * should associate the id of the entity that caused it through the appropriate constructor.</p>
 *  <p>Thread Safety: This class is thread-safe since it's immutable.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class DuplicateEntryException extends PersistenceException {
    /**
     *  <p>This id identifies the entity that caused this exception. In our case it would be the id of an
     * entity that we were creating BUT found out that it already exists (which makes the creation semantically
     * incorrect as we cannot create what already exists) It is initialized through a constructor and accessed by
     * caller through a getter.</p>
     */
    private final long entityId;

    /**
     * Creates a new exception with passed error message.
     *
     *
     * @param msg error message
     */
    public DuplicateEntryException(String msg) {
        this(msg, -1);
    }

    /**
     * Creates a new exception with this error message and cause of error.
     *
     *
     * @param msg error message
     * @param cause the cause of the error
     */
    public DuplicateEntryException(String msg, Throwable cause) {
        this(msg, cause, -1);
    }

    /**
     * <p>
     * Creates a new exception with this error message and entityId.
     * </p>
     *
     *
     * @param msg error message
     * @param entityId id of the entity that caused this issue
     */
    public DuplicateEntryException(String msg, long entityId) {
        super(msg);
        this.entityId = entityId;
    }

    /**
     * <p>
     * Creates a new exception with this error message , cause, and entityId.
     * </p>
     *
     *
     * @param msg error message
     * @param cause inner, cause exception
     * @param entityId id of the entity that is duplicate
     */
    public DuplicateEntryException(String msg, Throwable cause, long entityId) {
        super(msg, cause);
        this.entityId = entityId;
    }

    /**
     *  <p>This is used to get the associated entity id for an entity that caused this exception. In this case
     * it would be an entry that we were creating but found that it is already present in the database.</p>
     *
     * @return id of the duplicate entity
     */
    public long getEntityId() {
        return this.entityId;
    }
}
