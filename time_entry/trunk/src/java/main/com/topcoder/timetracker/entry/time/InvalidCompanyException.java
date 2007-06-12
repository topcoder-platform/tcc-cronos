/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

/**
 * <p>
 * This exception is thrown when there is an attempt to relate a Time Tracker entities to a
 * TimeEntry whose companies do not match.
 * </p>
 *
 * <p>
 * For example, adding a TaskType to a TimeEntry when their companies are different.
 * </p>
 *
 * <p>
 * Thread-Safety: This class is thread safe as it has no state and its super class is also thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class InvalidCompanyException extends DataAccessException {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -7605766581978692164L;

    /**
     * <p>
     * This is the company id of the time entry being associated
     * with an entity.
     * </p>
     */
    private final long timeEntryCompanyId;

    /**
     * <p>
     * This is the company id of the other Time Tracker entity that
     * was being associated with the time entry.
     * </p>
     */
    private final long otherEntityCompanyId;

    /**
     * <p>
     * Constructor with default message.
     * </p>
     *
     * @param entryId the company id of the time entry being associated with an entity.
     * @param otherId the company id of the other Time Tracker entity that was being
     * associated with the time entry.
     */
    public InvalidCompanyException(long entryId, long otherId) {
        super("The company ids for the time entry and its associated entry are not the same, the expected one is ["
            + entryId + "], but the found one is [" + otherId + "].");

        this.timeEntryCompanyId = entryId;
        this.otherEntityCompanyId = otherId;
    }

    /**
     * <p>
     * Constructor with custom message.
     * </p>
     *
     * @param entryId the company id of the time entry being associated with an entity.
     * @param otherId  the company id of the other Time Tracker entity that was being associated with the time entry.
     * @param message A custom message.
     */
    public InvalidCompanyException(long entryId, long otherId, String message) {
        super(message);
        this.timeEntryCompanyId = entryId;
        this.otherEntityCompanyId = otherId;
    }

    /**
     * <p>
     * Returns the company id of the time entry being associated with an entity.
     * </p>
     *
     * @return the company id of the time entry being associated with an entity.
     */
    public long getTimeEntryCompanyId() {
        return timeEntryCompanyId;
    }

    /**
     * <p>
     * Returns the company id of the other Time Tracker entity that was being associated with the time entry.
     * </p>
     *
     * @return the company id of the other Time Tracker entity that was being associated with the time entry.
     */
    public long getOtherEntityCompanyId() {
        return otherEntityCompanyId;
    }
}
